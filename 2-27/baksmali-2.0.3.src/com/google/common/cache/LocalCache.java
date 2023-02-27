/*      */ package com.google.common.cache;
/*      */ 
/*      */ import com.google.common.base.Equivalence;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.base.Stopwatch;
/*      */ import com.google.common.base.Supplier;
/*      */ import com.google.common.base.Ticker;
/*      */ import com.google.common.collect.AbstractSequentialIterator;
/*      */ import com.google.common.collect.Iterators;
/*      */ import com.google.common.primitives.Ints;
/*      */ import com.google.common.util.concurrent.ExecutionError;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import com.google.common.util.concurrent.ListenableFuture;
/*      */ import com.google.common.util.concurrent.ListeningExecutorService;
/*      */ import com.google.common.util.concurrent.MoreExecutors;
/*      */ import com.google.common.util.concurrent.SettableFuture;
/*      */ import com.google.common.util.concurrent.UncheckedExecutionException;
/*      */ import com.google.common.util.concurrent.Uninterruptibles;
/*      */ import java.io.Serializable;
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.AbstractMap;
/*      */ import java.util.AbstractQueue;
/*      */ import java.util.AbstractSet;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.Queue;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.ConcurrentLinkedQueue;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import java.util.concurrent.ExecutionException;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.atomic.AtomicReferenceArray;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ 
/*      */ class LocalCache<K, V> extends AbstractMap<K, V>
/*      */   implements ConcurrentMap<K, V>
/*      */ {
/*  155 */   static final Logger logger = Logger.getLogger(LocalCache.class.getName());
/*      */ 
/*  157 */   static final ListeningExecutorService sameThreadExecutor = MoreExecutors.sameThreadExecutor();
/*      */   final int segmentMask;
/*      */   final int segmentShift;
/*      */   final Segment<K, V>[] segments;
/*      */   final int concurrencyLevel;
/*      */   final Equivalence<Object> keyEquivalence;
/*      */   final Equivalence<Object> valueEquivalence;
/*      */   final Strength keyStrength;
/*      */   final Strength valueStrength;
/*      */   final long maxWeight;
/*      */   final Weigher<K, V> weigher;
/*      */   final long expireAfterAccessNanos;
/*      */   final long expireAfterWriteNanos;
/*      */   final long refreshNanos;
/*      */   final Queue<RemovalNotification<K, V>> removalNotificationQueue;
/*      */   final RemovalListener<K, V> removalListener;
/*      */   final Ticker ticker;
/*      */   final EntryFactory entryFactory;
/*      */   final AbstractCache.StatsCounter globalStatsCounter;
/*      */   final CacheLoader<? super K, V> defaultLoader;
/*  687 */   static final ValueReference<Object, Object> UNSET = new ValueReference()
/*      */   {
/*      */     public Object get() {
/*  690 */       return null;
/*      */     }
/*      */ 
/*      */     public int getWeight()
/*      */     {
/*  695 */       return 0;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<Object, Object> getEntry()
/*      */     {
/*  700 */       return null;
/*      */     }
/*      */ 
/*      */     public LocalCache.ValueReference<Object, Object> copyFor(ReferenceQueue<Object> queue, Object value, LocalCache.ReferenceEntry<Object, Object> entry)
/*      */     {
/*  706 */       return this;
/*      */     }
/*      */ 
/*      */     public boolean isLoading()
/*      */     {
/*  711 */       return false;
/*      */     }
/*      */ 
/*      */     public boolean isActive()
/*      */     {
/*  716 */       return false;
/*      */     }
/*      */ 
/*      */     public Object waitForValue()
/*      */     {
/*  721 */       return null;
/*      */     }
/*      */ 
/*      */     public void notifyNewValue(Object newValue)
/*      */     {
/*      */     }
/*  687 */   };
/*      */ 
/* 1018 */   static final Queue<? extends Object> DISCARDING_QUEUE = new AbstractQueue()
/*      */   {
/*      */     public boolean offer(Object o) {
/* 1021 */       return true;
/*      */     }
/*      */ 
/*      */     public Object peek()
/*      */     {
/* 1026 */       return null;
/*      */     }
/*      */ 
/*      */     public Object poll()
/*      */     {
/* 1031 */       return null;
/*      */     }
/*      */ 
/*      */     public int size()
/*      */     {
/* 1036 */       return 0;
/*      */     }
/*      */ 
/*      */     public Iterator<Object> iterator()
/*      */     {
/* 1041 */       return Iterators.emptyIterator();
/*      */     }
/* 1018 */   };
/*      */   Set<K> keySet;
/*      */   Collection<V> values;
/*      */   Set<Map.Entry<K, V>> entrySet;
/*      */ 
/*      */   LocalCache(CacheBuilder<? super K, ? super V> builder, CacheLoader<? super K, V> loader)
/*      */   {
/*  237 */     this.concurrencyLevel = Math.min(builder.getConcurrencyLevel(), 65536);
/*      */ 
/*  239 */     this.keyStrength = builder.getKeyStrength();
/*  240 */     this.valueStrength = builder.getValueStrength();
/*      */ 
/*  242 */     this.keyEquivalence = builder.getKeyEquivalence();
/*  243 */     this.valueEquivalence = builder.getValueEquivalence();
/*      */ 
/*  245 */     this.maxWeight = builder.getMaximumWeight();
/*  246 */     this.weigher = builder.getWeigher();
/*  247 */     this.expireAfterAccessNanos = builder.getExpireAfterAccessNanos();
/*  248 */     this.expireAfterWriteNanos = builder.getExpireAfterWriteNanos();
/*  249 */     this.refreshNanos = builder.getRefreshNanos();
/*      */ 
/*  251 */     this.removalListener = builder.getRemovalListener();
/*  252 */     this.removalNotificationQueue = (this.removalListener == CacheBuilder.NullListener.INSTANCE ? discardingQueue() : new ConcurrentLinkedQueue());
/*      */ 
/*  256 */     this.ticker = builder.getTicker(recordsTime());
/*  257 */     this.entryFactory = EntryFactory.getFactory(this.keyStrength, usesAccessEntries(), usesWriteEntries());
/*  258 */     this.globalStatsCounter = ((AbstractCache.StatsCounter)builder.getStatsCounterSupplier().get());
/*  259 */     this.defaultLoader = loader;
/*      */ 
/*  261 */     int initialCapacity = Math.min(builder.getInitialCapacity(), 1073741824);
/*  262 */     if ((evictsBySize()) && (!customWeigher())) {
/*  263 */       initialCapacity = Math.min(initialCapacity, (int)this.maxWeight);
/*      */     }
/*      */ 
/*  271 */     int segmentShift = 0;
/*  272 */     int segmentCount = 1;
/*      */ 
/*  274 */     while ((segmentCount < this.concurrencyLevel) && ((!evictsBySize()) || (segmentCount * 20 <= this.maxWeight))) {
/*  275 */       segmentShift++;
/*  276 */       segmentCount <<= 1;
/*      */     }
/*  278 */     this.segmentShift = (32 - segmentShift);
/*  279 */     this.segmentMask = (segmentCount - 1);
/*      */ 
/*  281 */     this.segments = newSegmentArray(segmentCount);
/*      */ 
/*  283 */     int segmentCapacity = initialCapacity / segmentCount;
/*  284 */     if (segmentCapacity * segmentCount < initialCapacity) {
/*  285 */       segmentCapacity++;
/*      */     }
/*      */ 
/*  288 */     int segmentSize = 1;
/*  289 */     while (segmentSize < segmentCapacity) {
/*  290 */       segmentSize <<= 1;
/*      */     }
/*      */ 
/*  293 */     if (evictsBySize())
/*      */     {
/*  295 */       long maxSegmentWeight = this.maxWeight / segmentCount + 1L;
/*  296 */       long remainder = this.maxWeight % segmentCount;
/*  297 */       for (int i = 0; i < this.segments.length; i++) {
/*  298 */         if (i == remainder) {
/*  299 */           maxSegmentWeight -= 1L;
/*      */         }
/*  301 */         this.segments[i] = createSegment(segmentSize, maxSegmentWeight, (AbstractCache.StatsCounter)builder.getStatsCounterSupplier().get());
/*      */       }
/*      */     }
/*      */     else {
/*  305 */       for (int i = 0; i < this.segments.length; i++)
/*  306 */         this.segments[i] = createSegment(segmentSize, -1L, (AbstractCache.StatsCounter)builder.getStatsCounterSupplier().get());
/*      */     }
/*      */   }
/*      */ 
/*      */   boolean evictsBySize()
/*      */   {
/*  313 */     return this.maxWeight >= 0L;
/*      */   }
/*      */ 
/*      */   boolean customWeigher() {
/*  317 */     return this.weigher != CacheBuilder.OneWeigher.INSTANCE;
/*      */   }
/*      */ 
/*      */   boolean expiresAfterWrite()
/*      */   {
/*  325 */     return this.expireAfterWriteNanos > 0L;
/*      */   }
/*      */ 
/*      */   boolean expiresAfterAccess() {
/*  329 */     return this.expireAfterAccessNanos > 0L;
/*      */   }
/*      */ 
/*      */   boolean refreshes() {
/*  333 */     return this.refreshNanos > 0L;
/*      */   }
/*      */ 
/*      */   boolean usesAccessQueue() {
/*  337 */     return (expiresAfterAccess()) || (evictsBySize());
/*      */   }
/*      */ 
/*      */   boolean usesWriteQueue() {
/*  341 */     return expiresAfterWrite();
/*      */   }
/*      */ 
/*      */   boolean recordsWrite() {
/*  345 */     return (expiresAfterWrite()) || (refreshes());
/*      */   }
/*      */ 
/*      */   boolean recordsAccess() {
/*  349 */     return expiresAfterAccess();
/*      */   }
/*      */ 
/*      */   boolean recordsTime() {
/*  353 */     return (recordsWrite()) || (recordsAccess());
/*      */   }
/*      */ 
/*      */   boolean usesWriteEntries() {
/*  357 */     return (usesWriteQueue()) || (recordsWrite());
/*      */   }
/*      */ 
/*      */   boolean usesAccessEntries() {
/*  361 */     return (usesAccessQueue()) || (recordsAccess());
/*      */   }
/*      */ 
/*      */   boolean usesKeyReferences() {
/*  365 */     return this.keyStrength != Strength.STRONG;
/*      */   }
/*      */ 
/*      */   boolean usesValueReferences() {
/*  369 */     return this.valueStrength != Strength.STRONG;
/*      */   }
/*      */ 
/*      */   static <K, V> ValueReference<K, V> unset()
/*      */   {
/*  733 */     return UNSET;
/*      */   }
/*      */ 
/*      */   static <K, V> ReferenceEntry<K, V> nullEntry()
/*      */   {
/* 1015 */     return NullEntry.INSTANCE;
/*      */   }
/*      */ 
/*      */   static <E> Queue<E> discardingQueue()
/*      */   {
/* 1050 */     return DISCARDING_QUEUE;
/*      */   }
/*      */ 
/*      */   static int rehash(int h)
/*      */   {
/* 1861 */     h += (h << 15 ^ 0xFFFFCD7D);
/* 1862 */     h ^= h >>> 10;
/* 1863 */     h += (h << 3);
/* 1864 */     h ^= h >>> 6;
/* 1865 */     h += (h << 2) + (h << 14);
/* 1866 */     return h ^ h >>> 16;
/*      */   }
/*      */ 
/*      */   int hash(Object key)
/*      */   {
/* 1899 */     int h = this.keyEquivalence.hash(key);
/* 1900 */     return rehash(h);
/*      */   }
/*      */ 
/*      */   void reclaimValue(ValueReference<K, V> valueReference) {
/* 1904 */     ReferenceEntry entry = valueReference.getEntry();
/* 1905 */     int hash = entry.getHash();
/* 1906 */     segmentFor(hash).reclaimValue(entry.getKey(), hash, valueReference);
/*      */   }
/*      */ 
/*      */   void reclaimKey(ReferenceEntry<K, V> entry) {
/* 1910 */     int hash = entry.getHash();
/* 1911 */     segmentFor(hash).reclaimKey(entry, hash);
/*      */   }
/*      */ 
/*      */   Segment<K, V> segmentFor(int hash)
/*      */   {
/* 1931 */     return this.segments[(hash >>> this.segmentShift & this.segmentMask)];
/*      */   }
/*      */ 
/*      */   Segment<K, V> createSegment(int initialCapacity, long maxSegmentWeight, AbstractCache.StatsCounter statsCounter)
/*      */   {
/* 1936 */     return new Segment(this, initialCapacity, maxSegmentWeight, statsCounter);
/*      */   }
/*      */ 
/*      */   V getLiveValue(ReferenceEntry<K, V> entry, long now)
/*      */   {
/* 1947 */     if (entry.getKey() == null) {
/* 1948 */       return null;
/*      */     }
/* 1950 */     Object value = entry.getValueReference().get();
/* 1951 */     if (value == null) {
/* 1952 */       return null;
/*      */     }
/*      */ 
/* 1955 */     if (isExpired(entry, now)) {
/* 1956 */       return null;
/*      */     }
/* 1958 */     return value;
/*      */   }
/*      */ 
/*      */   boolean isExpired(ReferenceEntry<K, V> entry, long now)
/*      */   {
/* 1967 */     Preconditions.checkNotNull(entry);
/* 1968 */     if ((expiresAfterAccess()) && (now - entry.getAccessTime() >= this.expireAfterAccessNanos))
/*      */     {
/* 1970 */       return true;
/*      */     }
/*      */ 
/* 1974 */     return (expiresAfterWrite()) && (now - entry.getWriteTime() >= this.expireAfterWriteNanos);
/*      */   }
/*      */ 
/*      */   static <K, V> void connectAccessOrder(ReferenceEntry<K, V> previous, ReferenceEntry<K, V> next)
/*      */   {
/* 1983 */     previous.setNextInAccessQueue(next);
/* 1984 */     next.setPreviousInAccessQueue(previous);
/*      */   }
/*      */ 
/*      */   static <K, V> void nullifyAccessOrder(ReferenceEntry<K, V> nulled)
/*      */   {
/* 1989 */     ReferenceEntry nullEntry = nullEntry();
/* 1990 */     nulled.setNextInAccessQueue(nullEntry);
/* 1991 */     nulled.setPreviousInAccessQueue(nullEntry);
/*      */   }
/*      */ 
/*      */   static <K, V> void connectWriteOrder(ReferenceEntry<K, V> previous, ReferenceEntry<K, V> next)
/*      */   {
/* 1996 */     previous.setNextInWriteQueue(next);
/* 1997 */     next.setPreviousInWriteQueue(previous);
/*      */   }
/*      */ 
/*      */   static <K, V> void nullifyWriteOrder(ReferenceEntry<K, V> nulled)
/*      */   {
/* 2002 */     ReferenceEntry nullEntry = nullEntry();
/* 2003 */     nulled.setNextInWriteQueue(nullEntry);
/* 2004 */     nulled.setPreviousInWriteQueue(nullEntry);
/*      */   }
/*      */ 
/*      */   void processPendingNotifications()
/*      */   {
/*      */     RemovalNotification notification;
/* 2014 */     while ((notification = (RemovalNotification)this.removalNotificationQueue.poll()) != null)
/*      */       try {
/* 2016 */         this.removalListener.onRemoval(notification);
/*      */       } catch (Throwable e) {
/* 2018 */         logger.log(Level.WARNING, "Exception thrown by removal listener", e);
/*      */       }
/*      */   }
/*      */ 
/*      */   final Segment<K, V>[] newSegmentArray(int ssize)
/*      */   {
/* 2025 */     return new Segment[ssize];
/*      */   }
/*      */ 
/*      */   public boolean isEmpty()
/*      */   {
/* 3939 */     long sum = 0L;
/* 3940 */     Segment[] segments = this.segments;
/* 3941 */     for (int i = 0; i < segments.length; i++) {
/* 3942 */       if (segments[i].count != 0) {
/* 3943 */         return false;
/*      */       }
/* 3945 */       sum += segments[i].modCount;
/*      */     }
/*      */ 
/* 3948 */     if (sum != 0L) {
/* 3949 */       for (int i = 0; i < segments.length; i++) {
/* 3950 */         if (segments[i].count != 0) {
/* 3951 */           return false;
/*      */         }
/* 3953 */         sum -= segments[i].modCount;
/*      */       }
/* 3955 */       if (sum != 0L) {
/* 3956 */         return false;
/*      */       }
/*      */     }
/* 3959 */     return true;
/*      */   }
/*      */ 
/*      */   long longSize() {
/* 3963 */     Segment[] segments = this.segments;
/* 3964 */     long sum = 0L;
/* 3965 */     for (int i = 0; i < segments.length; i++) {
/* 3966 */       sum += segments[i].count;
/*      */     }
/* 3968 */     return sum;
/*      */   }
/*      */ 
/*      */   public int size()
/*      */   {
/* 3973 */     return Ints.saturatedCast(longSize());
/*      */   }
/*      */ 
/*      */   public V get(Object key)
/*      */   {
/* 3979 */     if (key == null) {
/* 3980 */       return null;
/*      */     }
/* 3982 */     int hash = hash(key);
/* 3983 */     return segmentFor(hash).get(key, hash);
/*      */   }
/*      */ 
/*      */   V get(K key, CacheLoader<? super K, V> loader)
/*      */     throws ExecutionException
/*      */   {
/* 3999 */     int hash = hash(Preconditions.checkNotNull(key));
/* 4000 */     return segmentFor(hash).get(key, hash, loader);
/*      */   }
/*      */ 
/*      */   V getOrLoad(K key) throws ExecutionException {
/* 4004 */     return get(key, this.defaultLoader);
/*      */   }
/*      */ 
/*      */   public boolean containsKey(Object key)
/*      */   {
/* 4159 */     if (key == null) {
/* 4160 */       return false;
/*      */     }
/* 4162 */     int hash = hash(key);
/* 4163 */     return segmentFor(hash).containsKey(key, hash);
/*      */   }
/*      */ 
/*      */   public boolean containsValue(Object value)
/*      */   {
/* 4169 */     if (value == null) {
/* 4170 */       return false;
/*      */     }
/*      */ 
/* 4178 */     long now = this.ticker.read();
/* 4179 */     Segment[] segments = this.segments;
/* 4180 */     long last = -1L;
/* 4181 */     for (int i = 0; i < 3; i++) {
/* 4182 */       long sum = 0L;
/* 4183 */       for (Segment segment : segments)
/*      */       {
/* 4186 */         int c = segment.count;
/*      */ 
/* 4188 */         AtomicReferenceArray table = segment.table;
/* 4189 */         for (int j = 0; j < table.length(); j++) {
/* 4190 */           for (ReferenceEntry e = (ReferenceEntry)table.get(j); e != null; e = e.getNext()) {
/* 4191 */             Object v = segment.getLiveValue(e, now);
/* 4192 */             if ((v != null) && (this.valueEquivalence.equivalent(value, v))) {
/* 4193 */               return true;
/*      */             }
/*      */           }
/*      */         }
/* 4197 */         sum += segment.modCount;
/*      */       }
/* 4199 */       if (sum == last) {
/*      */         break;
/*      */       }
/* 4202 */       last = sum;
/*      */     }
/* 4204 */     return false;
/*      */   }
/*      */ 
/*      */   public V put(K key, V value)
/*      */   {
/* 4209 */     Preconditions.checkNotNull(key);
/* 4210 */     Preconditions.checkNotNull(value);
/* 4211 */     int hash = hash(key);
/* 4212 */     return segmentFor(hash).put(key, hash, value, false);
/*      */   }
/*      */ 
/*      */   public V putIfAbsent(K key, V value)
/*      */   {
/* 4217 */     Preconditions.checkNotNull(key);
/* 4218 */     Preconditions.checkNotNull(value);
/* 4219 */     int hash = hash(key);
/* 4220 */     return segmentFor(hash).put(key, hash, value, true);
/*      */   }
/*      */ 
/*      */   public void putAll(Map<? extends K, ? extends V> m)
/*      */   {
/* 4225 */     for (Map.Entry e : m.entrySet())
/* 4226 */       put(e.getKey(), e.getValue());
/*      */   }
/*      */ 
/*      */   public V remove(Object key)
/*      */   {
/* 4232 */     if (key == null) {
/* 4233 */       return null;
/*      */     }
/* 4235 */     int hash = hash(key);
/* 4236 */     return segmentFor(hash).remove(key, hash);
/*      */   }
/*      */ 
/*      */   public boolean remove(Object key, Object value)
/*      */   {
/* 4241 */     if ((key == null) || (value == null)) {
/* 4242 */       return false;
/*      */     }
/* 4244 */     int hash = hash(key);
/* 4245 */     return segmentFor(hash).remove(key, hash, value);
/*      */   }
/*      */ 
/*      */   public boolean replace(K key, V oldValue, V newValue)
/*      */   {
/* 4250 */     Preconditions.checkNotNull(key);
/* 4251 */     Preconditions.checkNotNull(newValue);
/* 4252 */     if (oldValue == null) {
/* 4253 */       return false;
/*      */     }
/* 4255 */     int hash = hash(key);
/* 4256 */     return segmentFor(hash).replace(key, hash, oldValue, newValue);
/*      */   }
/*      */ 
/*      */   public V replace(K key, V value)
/*      */   {
/* 4261 */     Preconditions.checkNotNull(key);
/* 4262 */     Preconditions.checkNotNull(value);
/* 4263 */     int hash = hash(key);
/* 4264 */     return segmentFor(hash).replace(key, hash, value);
/*      */   }
/*      */ 
/*      */   public void clear()
/*      */   {
/* 4269 */     for (Segment segment : this.segments)
/* 4270 */       segment.clear();
/*      */   }
/*      */ 
/*      */   public Set<K> keySet()
/*      */   {
/* 4286 */     Set ks = this.keySet;
/* 4287 */     return this.keySet = new KeySet(this);
/*      */   }
/*      */ 
/*      */   public Collection<V> values()
/*      */   {
/* 4295 */     Collection vs = this.values;
/* 4296 */     return this.values = new Values(this);
/*      */   }
/*      */ 
/*      */   public Set<Map.Entry<K, V>> entrySet()
/*      */   {
/* 4305 */     Set es = this.entrySet;
/* 4306 */     return this.entrySet = new EntrySet(this);
/*      */   }
/*      */ 
/*      */   static class LocalLoadingCache<K, V> extends LocalCache.LocalManualCache<K, V>
/*      */     implements LoadingCache<K, V>
/*      */   {
/*      */     LocalLoadingCache(CacheBuilder<? super K, ? super V> builder, CacheLoader<? super K, V> loader)
/*      */     {
/* 4867 */       super(null);
/*      */     }
/*      */ 
/*      */     public V get(K key)
/*      */       throws ExecutionException
/*      */     {
/* 4874 */       return this.localCache.getOrLoad(key);
/*      */     }
/*      */ 
/*      */     public V getUnchecked(K key)
/*      */     {
/*      */       try {
/* 4880 */         return get(key); } catch (ExecutionException e) {
/*      */       }
/* 4882 */       throw new UncheckedExecutionException(e.getCause());
/*      */     }
/*      */ 
/*      */     public final V apply(K key)
/*      */     {
/* 4898 */       return getUnchecked(key);
/*      */     }
/*      */   }
/*      */ 
/*      */   static class LocalManualCache<K, V>
/*      */     implements Cache<K, V>, Serializable
/*      */   {
/*      */     final LocalCache<K, V> localCache;
/*      */ 
/*      */     private LocalManualCache(LocalCache<K, V> localCache)
/*      */     {
/* 4775 */       this.localCache = localCache;
/*      */     }
/*      */ 
/*      */     public void put(K key, V value)
/*      */     {
/* 4804 */       this.localCache.put(key, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   final class EntrySet extends LocalCache<K, V>.AbstractCacheSet<Map.Entry<K, V>>
/*      */   {
/*      */     EntrySet()
/*      */     {
/* 4565 */       super(map);
/*      */     }
/*      */ 
/*      */     public Iterator<Map.Entry<K, V>> iterator()
/*      */     {
/* 4570 */       return new LocalCache.EntryIterator(LocalCache.this);
/*      */     }
/*      */ 
/*      */     public boolean contains(Object o)
/*      */     {
/* 4575 */       if (!(o instanceof Map.Entry)) {
/* 4576 */         return false;
/*      */       }
/* 4578 */       Map.Entry e = (Map.Entry)o;
/* 4579 */       Object key = e.getKey();
/* 4580 */       if (key == null) {
/* 4581 */         return false;
/*      */       }
/* 4583 */       Object v = LocalCache.this.get(key);
/*      */ 
/* 4585 */       return (v != null) && (LocalCache.this.valueEquivalence.equivalent(e.getValue(), v));
/*      */     }
/*      */ 
/*      */     public boolean remove(Object o)
/*      */     {
/* 4590 */       if (!(o instanceof Map.Entry)) {
/* 4591 */         return false;
/*      */       }
/* 4593 */       Map.Entry e = (Map.Entry)o;
/* 4594 */       Object key = e.getKey();
/* 4595 */       return (key != null) && (LocalCache.this.remove(key, e.getValue()));
/*      */     }
/*      */   }
/*      */ 
/*      */   final class Values extends LocalCache<K, V>.AbstractCacheSet<V>
/*      */   {
/*      */     Values()
/*      */     {
/* 4548 */       super(map);
/*      */     }
/*      */ 
/*      */     public Iterator<V> iterator()
/*      */     {
/* 4553 */       return new LocalCache.ValueIterator(LocalCache.this);
/*      */     }
/*      */ 
/*      */     public boolean contains(Object o)
/*      */     {
/* 4558 */       return this.map.containsValue(o);
/*      */     }
/*      */   }
/*      */ 
/*      */   final class KeySet extends LocalCache<K, V>.AbstractCacheSet<K>
/*      */   {
/*      */     KeySet()
/*      */     {
/* 4526 */       super(map);
/*      */     }
/*      */ 
/*      */     public Iterator<K> iterator()
/*      */     {
/* 4531 */       return new LocalCache.KeyIterator(LocalCache.this);
/*      */     }
/*      */ 
/*      */     public boolean contains(Object o)
/*      */     {
/* 4536 */       return this.map.containsKey(o);
/*      */     }
/*      */ 
/*      */     public boolean remove(Object o)
/*      */     {
/* 4541 */       return this.map.remove(o) != null;
/*      */     }
/*      */   }
/*      */ 
/*      */   abstract class AbstractCacheSet<T> extends AbstractSet<T>
/*      */   {
/*      */     final ConcurrentMap<?, ?> map;
/*      */ 
/*      */     AbstractCacheSet()
/*      */     {
/* 4504 */       this.map = map;
/*      */     }
/*      */ 
/*      */     public int size()
/*      */     {
/* 4509 */       return this.map.size();
/*      */     }
/*      */ 
/*      */     public boolean isEmpty()
/*      */     {
/* 4514 */       return this.map.isEmpty();
/*      */     }
/*      */ 
/*      */     public void clear()
/*      */     {
/* 4519 */       this.map.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   final class EntryIterator extends LocalCache<K, V>.HashIterator<Map.Entry<K, V>>
/*      */   {
/*      */     EntryIterator()
/*      */     {
/* 4492 */       super();
/*      */     }
/*      */ 
/*      */     public Map.Entry<K, V> next() {
/* 4496 */       return nextEntry();
/*      */     }
/*      */   }
/*      */ 
/*      */   final class WriteThroughEntry
/*      */     implements Map.Entry<K, V>
/*      */   {
/*      */     final K key;
/*      */     V value;
/*      */ 
/*      */     WriteThroughEntry(V key)
/*      */     {
/* 4449 */       this.key = key;
/* 4450 */       this.value = value;
/*      */     }
/*      */ 
/*      */     public K getKey()
/*      */     {
/* 4455 */       return this.key;
/*      */     }
/*      */ 
/*      */     public V getValue()
/*      */     {
/* 4460 */       return this.value;
/*      */     }
/*      */ 
/*      */     public boolean equals(Object object)
/*      */     {
/* 4466 */       if ((object instanceof Map.Entry)) {
/* 4467 */         Map.Entry that = (Map.Entry)object;
/* 4468 */         return (this.key.equals(that.getKey())) && (this.value.equals(that.getValue()));
/*      */       }
/* 4470 */       return false;
/*      */     }
/*      */ 
/*      */     public int hashCode()
/*      */     {
/* 4476 */       return this.key.hashCode() ^ this.value.hashCode();
/*      */     }
/*      */ 
/*      */     public V setValue(V newValue)
/*      */     {
/* 4481 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public String toString()
/*      */     {
/* 4488 */       return getKey() + "=" + getValue();
/*      */     }
/*      */   }
/*      */ 
/*      */   final class ValueIterator extends LocalCache<K, V>.HashIterator<V>
/*      */   {
/*      */     ValueIterator()
/*      */     {
/* 4432 */       super();
/*      */     }
/*      */ 
/*      */     public V next() {
/* 4436 */       return nextEntry().getValue();
/*      */     }
/*      */   }
/*      */ 
/*      */   final class KeyIterator extends LocalCache<K, V>.HashIterator<K>
/*      */   {
/*      */     KeyIterator()
/*      */     {
/* 4424 */       super();
/*      */     }
/*      */ 
/*      */     public K next() {
/* 4428 */       return nextEntry().getKey();
/*      */     }
/*      */   }
/*      */ 
/*      */   abstract class HashIterator<T>
/*      */     implements Iterator<T>
/*      */   {
/*      */     int nextSegmentIndex;
/*      */     int nextTableIndex;
/*      */     LocalCache.Segment<K, V> currentSegment;
/*      */     AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> currentTable;
/*      */     LocalCache.ReferenceEntry<K, V> nextEntry;
/*      */     LocalCache<K, V>.WriteThroughEntry nextExternal;
/*      */     LocalCache<K, V>.WriteThroughEntry lastReturned;
/*      */ 
/*      */     HashIterator()
/*      */     {
/* 4322 */       this.nextSegmentIndex = (LocalCache.this.segments.length - 1);
/* 4323 */       this.nextTableIndex = -1;
/* 4324 */       advance();
/*      */     }
/*      */ 
/*      */     final void advance()
/*      */     {
/* 4331 */       this.nextExternal = null;
/*      */ 
/* 4333 */       if (nextInChain()) {
/* 4334 */         return;
/*      */       }
/*      */ 
/* 4337 */       if (nextInTable()) {
/* 4338 */         return;
/*      */       }
/*      */ 
/* 4341 */       while (this.nextSegmentIndex >= 0) {
/* 4342 */         this.currentSegment = LocalCache.this.segments[(this.nextSegmentIndex--)];
/* 4343 */         if (this.currentSegment.count != 0) {
/* 4344 */           this.currentTable = this.currentSegment.table;
/* 4345 */           this.nextTableIndex = (this.currentTable.length() - 1);
/* 4346 */           if (nextInTable())
/* 4347 */             return;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     boolean nextInChain()
/*      */     {
/* 4357 */       if (this.nextEntry != null) {
/* 4358 */         for (this.nextEntry = this.nextEntry.getNext(); this.nextEntry != null; this.nextEntry = this.nextEntry.getNext()) {
/* 4359 */           if (advanceTo(this.nextEntry)) {
/* 4360 */             return true;
/*      */           }
/*      */         }
/*      */       }
/* 4364 */       return false;
/*      */     }
/*      */ 
/*      */     boolean nextInTable()
/*      */     {
/* 4371 */       while (this.nextTableIndex >= 0) {
/* 4372 */         if (((this.nextEntry = (LocalCache.ReferenceEntry)this.currentTable.get(this.nextTableIndex--)) != null) && (
/* 4373 */           (advanceTo(this.nextEntry)) || (nextInChain()))) {
/* 4374 */           return true;
/*      */         }
/*      */       }
/*      */ 
/* 4378 */       return false;
/*      */     }
/*      */ 
/*      */     boolean advanceTo(LocalCache.ReferenceEntry<K, V> entry)
/*      */     {
/*      */       try
/*      */       {
/* 4387 */         long now = LocalCache.this.ticker.read();
/* 4388 */         Object key = entry.getKey();
/* 4389 */         Object value = LocalCache.this.getLiveValue(entry, now);
/* 4390 */         if (value != null) {
/* 4391 */           this.nextExternal = new LocalCache.WriteThroughEntry(LocalCache.this, key, value);
/* 4392 */           i = 1;
/*      */           return i;
/*      */         }
/* 4395 */         int i = 0;
/*      */         return i; } finally { this.currentSegment.postReadCleanup(); } throw localObject1;
/*      */     }
/*      */ 
/*      */     public boolean hasNext()
/*      */     {
/* 4404 */       return this.nextExternal != null;
/*      */     }
/*      */ 
/*      */     LocalCache<K, V>.WriteThroughEntry nextEntry() {
/* 4408 */       if (this.nextExternal == null) {
/* 4409 */         throw new NoSuchElementException();
/*      */       }
/* 4411 */       this.lastReturned = this.nextExternal;
/* 4412 */       advance();
/* 4413 */       return this.lastReturned;
/*      */     }
/*      */ 
/*      */     public void remove()
/*      */     {
/* 4418 */       Preconditions.checkState(this.lastReturned != null);
/* 4419 */       LocalCache.this.remove(this.lastReturned.getKey());
/* 4420 */       this.lastReturned = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class AccessQueue<K, V> extends AbstractQueue<LocalCache.ReferenceEntry<K, V>>
/*      */   {
/* 3795 */     final LocalCache.ReferenceEntry<K, V> head = new LocalCache.AbstractReferenceEntry()
/*      */     {
/* 3805 */       LocalCache.ReferenceEntry<K, V> nextAccess = this;
/*      */ 
/* 3817 */       LocalCache.ReferenceEntry<K, V> previousAccess = this;
/*      */ 
/*      */       public long getAccessTime()
/*      */       {
/* 3799 */         return 9223372036854775807L;
/*      */       }
/*      */ 
/*      */       public void setAccessTime(long time)
/*      */       {
/*      */       }
/*      */ 
/*      */       public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue()
/*      */       {
/* 3809 */         return this.nextAccess;
/*      */       }
/*      */ 
/*      */       public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next)
/*      */       {
/* 3814 */         this.nextAccess = next;
/*      */       }
/*      */ 
/*      */       public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue()
/*      */       {
/* 3821 */         return this.previousAccess;
/*      */       }
/*      */ 
/*      */       public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous)
/*      */       {
/* 3826 */         this.previousAccess = previous;
/*      */       }
/* 3795 */     };
/*      */ 
/*      */     public boolean offer(LocalCache.ReferenceEntry<K, V> entry)
/*      */     {
/* 3835 */       LocalCache.connectAccessOrder(entry.getPreviousInAccessQueue(), entry.getNextInAccessQueue());
/*      */ 
/* 3838 */       LocalCache.connectAccessOrder(this.head.getPreviousInAccessQueue(), entry);
/* 3839 */       LocalCache.connectAccessOrder(entry, this.head);
/*      */ 
/* 3841 */       return true;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> peek()
/*      */     {
/* 3846 */       LocalCache.ReferenceEntry next = this.head.getNextInAccessQueue();
/* 3847 */       return next == this.head ? null : next;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> poll()
/*      */     {
/* 3852 */       LocalCache.ReferenceEntry next = this.head.getNextInAccessQueue();
/* 3853 */       if (next == this.head) {
/* 3854 */         return null;
/*      */       }
/*      */ 
/* 3857 */       remove(next);
/* 3858 */       return next;
/*      */     }
/*      */ 
/*      */     public boolean remove(Object o)
/*      */     {
/* 3864 */       LocalCache.ReferenceEntry e = (LocalCache.ReferenceEntry)o;
/* 3865 */       LocalCache.ReferenceEntry previous = e.getPreviousInAccessQueue();
/* 3866 */       LocalCache.ReferenceEntry next = e.getNextInAccessQueue();
/* 3867 */       LocalCache.connectAccessOrder(previous, next);
/* 3868 */       LocalCache.nullifyAccessOrder(e);
/*      */ 
/* 3870 */       return next != LocalCache.NullEntry.INSTANCE;
/*      */     }
/*      */ 
/*      */     public boolean contains(Object o)
/*      */     {
/* 3876 */       LocalCache.ReferenceEntry e = (LocalCache.ReferenceEntry)o;
/* 3877 */       return e.getNextInAccessQueue() != LocalCache.NullEntry.INSTANCE;
/*      */     }
/*      */ 
/*      */     public boolean isEmpty()
/*      */     {
/* 3882 */       return this.head.getNextInAccessQueue() == this.head;
/*      */     }
/*      */ 
/*      */     public int size()
/*      */     {
/* 3887 */       int size = 0;
/* 3888 */       for (LocalCache.ReferenceEntry e = this.head.getNextInAccessQueue(); e != this.head; )
/*      */       {
/* 3890 */         size++;
/*      */ 
/* 3889 */         e = e.getNextInAccessQueue();
/*      */       }
/*      */ 
/* 3892 */       return size;
/*      */     }
/*      */ 
/*      */     public void clear()
/*      */     {
/* 3897 */       LocalCache.ReferenceEntry e = this.head.getNextInAccessQueue();
/* 3898 */       while (e != this.head) {
/* 3899 */         LocalCache.ReferenceEntry next = e.getNextInAccessQueue();
/* 3900 */         LocalCache.nullifyAccessOrder(e);
/* 3901 */         e = next;
/*      */       }
/*      */ 
/* 3904 */       this.head.setNextInAccessQueue(this.head);
/* 3905 */       this.head.setPreviousInAccessQueue(this.head);
/*      */     }
/*      */ 
/*      */     public Iterator<LocalCache.ReferenceEntry<K, V>> iterator()
/*      */     {
/* 3910 */       return new AbstractSequentialIterator(peek())
/*      */       {
/*      */         protected LocalCache.ReferenceEntry<K, V> computeNext(LocalCache.ReferenceEntry<K, V> previous) {
/* 3913 */           LocalCache.ReferenceEntry next = previous.getNextInAccessQueue();
/* 3914 */           return next == LocalCache.AccessQueue.this.head ? null : next;
/*      */         }
/*      */       };
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class WriteQueue<K, V> extends AbstractQueue<LocalCache.ReferenceEntry<K, V>>
/*      */   {
/* 3658 */     final LocalCache.ReferenceEntry<K, V> head = new LocalCache.AbstractReferenceEntry()
/*      */     {
/* 3668 */       LocalCache.ReferenceEntry<K, V> nextWrite = this;
/*      */ 
/* 3680 */       LocalCache.ReferenceEntry<K, V> previousWrite = this;
/*      */ 
/*      */       public long getWriteTime()
/*      */       {
/* 3662 */         return 9223372036854775807L;
/*      */       }
/*      */ 
/*      */       public void setWriteTime(long time)
/*      */       {
/*      */       }
/*      */ 
/*      */       public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue()
/*      */       {
/* 3672 */         return this.nextWrite;
/*      */       }
/*      */ 
/*      */       public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next)
/*      */       {
/* 3677 */         this.nextWrite = next;
/*      */       }
/*      */ 
/*      */       public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue()
/*      */       {
/* 3684 */         return this.previousWrite;
/*      */       }
/*      */ 
/*      */       public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous)
/*      */       {
/* 3689 */         this.previousWrite = previous;
/*      */       }
/* 3658 */     };
/*      */ 
/*      */     public boolean offer(LocalCache.ReferenceEntry<K, V> entry)
/*      */     {
/* 3698 */       LocalCache.connectWriteOrder(entry.getPreviousInWriteQueue(), entry.getNextInWriteQueue());
/*      */ 
/* 3701 */       LocalCache.connectWriteOrder(this.head.getPreviousInWriteQueue(), entry);
/* 3702 */       LocalCache.connectWriteOrder(entry, this.head);
/*      */ 
/* 3704 */       return true;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> peek()
/*      */     {
/* 3709 */       LocalCache.ReferenceEntry next = this.head.getNextInWriteQueue();
/* 3710 */       return next == this.head ? null : next;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> poll()
/*      */     {
/* 3715 */       LocalCache.ReferenceEntry next = this.head.getNextInWriteQueue();
/* 3716 */       if (next == this.head) {
/* 3717 */         return null;
/*      */       }
/*      */ 
/* 3720 */       remove(next);
/* 3721 */       return next;
/*      */     }
/*      */ 
/*      */     public boolean remove(Object o)
/*      */     {
/* 3727 */       LocalCache.ReferenceEntry e = (LocalCache.ReferenceEntry)o;
/* 3728 */       LocalCache.ReferenceEntry previous = e.getPreviousInWriteQueue();
/* 3729 */       LocalCache.ReferenceEntry next = e.getNextInWriteQueue();
/* 3730 */       LocalCache.connectWriteOrder(previous, next);
/* 3731 */       LocalCache.nullifyWriteOrder(e);
/*      */ 
/* 3733 */       return next != LocalCache.NullEntry.INSTANCE;
/*      */     }
/*      */ 
/*      */     public boolean contains(Object o)
/*      */     {
/* 3739 */       LocalCache.ReferenceEntry e = (LocalCache.ReferenceEntry)o;
/* 3740 */       return e.getNextInWriteQueue() != LocalCache.NullEntry.INSTANCE;
/*      */     }
/*      */ 
/*      */     public boolean isEmpty()
/*      */     {
/* 3745 */       return this.head.getNextInWriteQueue() == this.head;
/*      */     }
/*      */ 
/*      */     public int size()
/*      */     {
/* 3750 */       int size = 0;
/* 3751 */       for (LocalCache.ReferenceEntry e = this.head.getNextInWriteQueue(); e != this.head; )
/*      */       {
/* 3753 */         size++;
/*      */ 
/* 3752 */         e = e.getNextInWriteQueue();
/*      */       }
/*      */ 
/* 3755 */       return size;
/*      */     }
/*      */ 
/*      */     public void clear()
/*      */     {
/* 3760 */       LocalCache.ReferenceEntry e = this.head.getNextInWriteQueue();
/* 3761 */       while (e != this.head) {
/* 3762 */         LocalCache.ReferenceEntry next = e.getNextInWriteQueue();
/* 3763 */         LocalCache.nullifyWriteOrder(e);
/* 3764 */         e = next;
/*      */       }
/*      */ 
/* 3767 */       this.head.setNextInWriteQueue(this.head);
/* 3768 */       this.head.setPreviousInWriteQueue(this.head);
/*      */     }
/*      */ 
/*      */     public Iterator<LocalCache.ReferenceEntry<K, V>> iterator()
/*      */     {
/* 3773 */       return new AbstractSequentialIterator(peek())
/*      */       {
/*      */         protected LocalCache.ReferenceEntry<K, V> computeNext(LocalCache.ReferenceEntry<K, V> previous) {
/* 3776 */           LocalCache.ReferenceEntry next = previous.getNextInWriteQueue();
/* 3777 */           return next == LocalCache.WriteQueue.this.head ? null : next;
/*      */         }
/*      */       };
/*      */     }
/*      */   }
/*      */ 
/*      */   static class LoadingValueReference<K, V>
/*      */     implements LocalCache.ValueReference<K, V>
/*      */   {
/*      */     volatile LocalCache.ValueReference<K, V> oldValue;
/* 3531 */     final SettableFuture<V> futureValue = SettableFuture.create();
/* 3532 */     final Stopwatch stopwatch = new Stopwatch();
/*      */ 
/*      */     public LoadingValueReference() {
/* 3535 */       this(LocalCache.unset());
/*      */     }
/*      */ 
/*      */     public LoadingValueReference(LocalCache.ValueReference<K, V> oldValue) {
/* 3539 */       this.oldValue = oldValue;
/*      */     }
/*      */ 
/*      */     public boolean isLoading()
/*      */     {
/* 3544 */       return true;
/*      */     }
/*      */ 
/*      */     public boolean isActive()
/*      */     {
/* 3549 */       return this.oldValue.isActive();
/*      */     }
/*      */ 
/*      */     public int getWeight()
/*      */     {
/* 3554 */       return this.oldValue.getWeight();
/*      */     }
/*      */ 
/*      */     public boolean set(V newValue) {
/* 3558 */       return this.futureValue.set(newValue);
/*      */     }
/*      */ 
/*      */     public boolean setException(Throwable t) {
/* 3562 */       return setException(this.futureValue, t);
/*      */     }
/*      */ 
/*      */     private static boolean setException(SettableFuture<?> future, Throwable t) {
/*      */       try {
/* 3567 */         return future.setException(t);
/*      */       } catch (Error e) {
/*      */       }
/* 3570 */       return false;
/*      */     }
/*      */ 
/*      */     private ListenableFuture<V> fullyFailedFuture(Throwable t)
/*      */     {
/* 3575 */       SettableFuture future = SettableFuture.create();
/* 3576 */       setException(future, t);
/* 3577 */       return future;
/*      */     }
/*      */ 
/*      */     public void notifyNewValue(V newValue)
/*      */     {
/* 3582 */       if (newValue != null)
/*      */       {
/* 3585 */         set(newValue);
/*      */       }
/*      */       else
/* 3588 */         this.oldValue = LocalCache.unset();
/*      */     }
/*      */ 
/*      */     public ListenableFuture<V> loadFuture(K key, CacheLoader<? super K, V> loader)
/*      */     {
/* 3595 */       this.stopwatch.start();
/* 3596 */       Object previousValue = this.oldValue.get();
/*      */       try {
/* 3598 */         if (previousValue == null) {
/* 3599 */           Object newValue = loader.load(key);
/* 3600 */           return set(newValue) ? this.futureValue : Futures.immediateFuture(newValue);
/*      */         }
/* 3602 */         ListenableFuture newValue = loader.reload(key, previousValue);
/*      */ 
/* 3604 */         return newValue != null ? newValue : Futures.immediateFuture(null);
/*      */       }
/*      */       catch (Throwable t) {
/* 3607 */         if ((t instanceof InterruptedException)) {
/* 3608 */           Thread.currentThread().interrupt();
/*      */         }
/* 3610 */         if (setException(t)) tmpTernaryOp = this.futureValue; 
/* 3610 */       }return fullyFailedFuture(t);
/*      */     }
/*      */ 
/*      */     public long elapsedNanos()
/*      */     {
/* 3615 */       return this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
/*      */     }
/*      */ 
/*      */     public V waitForValue() throws ExecutionException
/*      */     {
/* 3620 */       return Uninterruptibles.getUninterruptibly(this.futureValue);
/*      */     }
/*      */ 
/*      */     public V get()
/*      */     {
/* 3625 */       return this.oldValue.get();
/*      */     }
/*      */ 
/*      */     public LocalCache.ValueReference<K, V> getOldValue() {
/* 3629 */       return this.oldValue;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getEntry()
/*      */     {
/* 3634 */       return null;
/*      */     }
/*      */ 
/*      */     public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, LocalCache.ReferenceEntry<K, V> entry)
/*      */     {
/* 3640 */       return this;
/*      */     }
/*      */   }
/*      */ 
/*      */   static class Segment<K, V> extends ReentrantLock
/*      */   {
/*      */     final LocalCache<K, V> map;
/*      */     volatile int count;
/*      */     int totalWeight;
/*      */     int modCount;
/*      */     int threshold;
/*      */     volatile AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> table;
/*      */     final long maxSegmentWeight;
/*      */     final ReferenceQueue<K> keyReferenceQueue;
/*      */     final ReferenceQueue<V> valueReferenceQueue;
/*      */     final Queue<LocalCache.ReferenceEntry<K, V>> recencyQueue;
/* 2130 */     final AtomicInteger readCount = new AtomicInteger();
/*      */     final Queue<LocalCache.ReferenceEntry<K, V>> writeQueue;
/*      */     final Queue<LocalCache.ReferenceEntry<K, V>> accessQueue;
/*      */     final AbstractCache.StatsCounter statsCounter;
/*      */ 
/*      */     Segment(LocalCache<K, V> map, int initialCapacity, long maxSegmentWeight, AbstractCache.StatsCounter statsCounter)
/*      */     {
/* 2151 */       this.map = map;
/* 2152 */       this.maxSegmentWeight = maxSegmentWeight;
/* 2153 */       this.statsCounter = ((AbstractCache.StatsCounter)Preconditions.checkNotNull(statsCounter));
/* 2154 */       initTable(newEntryArray(initialCapacity));
/*      */ 
/* 2156 */       this.keyReferenceQueue = (map.usesKeyReferences() ? new ReferenceQueue() : null);
/*      */ 
/* 2159 */       this.valueReferenceQueue = (map.usesValueReferences() ? new ReferenceQueue() : null);
/*      */ 
/* 2162 */       this.recencyQueue = (map.usesAccessQueue() ? new ConcurrentLinkedQueue() : LocalCache.discardingQueue());
/*      */ 
/* 2166 */       this.writeQueue = (map.usesWriteQueue() ? new LocalCache.WriteQueue() : LocalCache.discardingQueue());
/*      */ 
/* 2170 */       this.accessQueue = (map.usesAccessQueue() ? new LocalCache.AccessQueue() : LocalCache.discardingQueue());
/*      */     }
/*      */ 
/*      */     AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> newEntryArray(int size)
/*      */     {
/* 2176 */       return new AtomicReferenceArray(size);
/*      */     }
/*      */ 
/*      */     void initTable(AtomicReferenceArray<LocalCache.ReferenceEntry<K, V>> newTable) {
/* 2180 */       this.threshold = (newTable.length() * 3 / 4);
/* 2181 */       if ((!this.map.customWeigher()) && (this.threshold == this.maxSegmentWeight))
/*      */       {
/* 2183 */         this.threshold += 1;
/*      */       }
/* 2185 */       this.table = newTable;
/*      */     }
/*      */ 
/*      */     LocalCache.ReferenceEntry<K, V> newEntry(K key, int hash, LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 2190 */       return this.map.entryFactory.newEntry(this, Preconditions.checkNotNull(key), hash, next);
/*      */     }
/*      */ 
/*      */     LocalCache.ReferenceEntry<K, V> copyEntry(LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newNext)
/*      */     {
/* 2199 */       if (original.getKey() == null)
/*      */       {
/* 2201 */         return null;
/*      */       }
/*      */ 
/* 2204 */       LocalCache.ValueReference valueReference = original.getValueReference();
/* 2205 */       Object value = valueReference.get();
/* 2206 */       if ((value == null) && (valueReference.isActive()))
/*      */       {
/* 2208 */         return null;
/*      */       }
/*      */ 
/* 2211 */       LocalCache.ReferenceEntry newEntry = this.map.entryFactory.copyEntry(this, original, newNext);
/* 2212 */       newEntry.setValueReference(valueReference.copyFor(this.valueReferenceQueue, value, newEntry));
/* 2213 */       return newEntry;
/*      */     }
/*      */ 
/*      */     void setValue(LocalCache.ReferenceEntry<K, V> entry, K key, V value, long now)
/*      */     {
/* 2221 */       LocalCache.ValueReference previous = entry.getValueReference();
/* 2222 */       int weight = this.map.weigher.weigh(key, value);
/* 2223 */       Preconditions.checkState(weight >= 0, "Weights must be non-negative");
/*      */ 
/* 2225 */       LocalCache.ValueReference valueReference = this.map.valueStrength.referenceValue(this, entry, value, weight);
/*      */ 
/* 2227 */       entry.setValueReference(valueReference);
/* 2228 */       recordWrite(entry, weight, now);
/* 2229 */       previous.notifyNewValue(value);
/*      */     }
/*      */ 
/*      */     V get(K key, int hash, CacheLoader<? super K, V> loader)
/*      */       throws ExecutionException
/*      */     {
/* 2235 */       Preconditions.checkNotNull(key);
/* 2236 */       Preconditions.checkNotNull(loader);
/*      */       try {
/* 2238 */         if (this.count != 0)
/*      */         {
/* 2240 */           e = getEntry(key, hash);
/* 2241 */           if (e != null) {
/* 2242 */             long now = this.map.ticker.read();
/* 2243 */             Object value = getLiveValue(e, now);
/* 2244 */             if (value != null) {
/* 2245 */               recordRead(e, now);
/* 2246 */               this.statsCounter.recordHits(1);
/* 2247 */               Object localObject1 = scheduleRefresh(e, key, hash, value, now, loader);
/*      */               return localObject1;
/*      */             }
/* 2249 */             LocalCache.ValueReference valueReference = e.getValueReference();
/* 2250 */             if (valueReference.isLoading()) {
/* 2251 */               Object localObject2 = waitForLoadingValue(e, key, valueReference);
/*      */               return localObject2;
/*      */             }
/*      */           }
/*      */         }
/* 2257 */         LocalCache.ReferenceEntry e = lockedGetOrLoad(key, hash, loader);
/*      */         return e;
/*      */       }
/*      */       catch (ExecutionException ee)
/*      */       {
/* 2259 */         Throwable cause = ee.getCause();
/* 2260 */         if ((cause instanceof Error))
/* 2261 */           throw new ExecutionError((Error)cause);
/* 2262 */         if ((cause instanceof RuntimeException)) {
/* 2263 */           throw new UncheckedExecutionException(cause);
/*      */         }
/* 2265 */         throw ee;
/*      */       } finally {
/* 2267 */         postReadCleanup(); } throw localObject3;
/*      */     }
/* 2274 */     V lockedGetOrLoad(K key, int hash, CacheLoader<? super K, V> loader) throws ExecutionException { LocalCache.ValueReference valueReference = null;
/* 2275 */       LocalCache.LoadingValueReference loadingValueReference = null;
/* 2276 */       boolean createNewEntry = true;
/*      */ 
/* 2278 */       lock();
/*      */       LocalCache.ReferenceEntry e;
/*      */       try { long now = this.map.ticker.read();
/* 2282 */         preWriteCleanup(now);
/*      */ 
/* 2284 */         int newCount = this.count - 1;
/* 2285 */         AtomicReferenceArray table = this.table;
/* 2286 */         int index = hash & table.length() - 1;
/* 2287 */         LocalCache.ReferenceEntry first = (LocalCache.ReferenceEntry)table.get(index);
/*      */ 
/* 2289 */         for (e = first; e != null; e = e.getNext()) {
/* 2290 */           Object entryKey = e.getKey();
/* 2291 */           if ((e.getHash() != hash) || (entryKey == null) || (!this.map.keyEquivalence.equivalent(key, entryKey)))
/*      */             continue;
/* 2293 */           valueReference = e.getValueReference();
/* 2294 */           if (valueReference.isLoading()) {
/* 2295 */             createNewEntry = false; break;
/*      */           }
/* 2297 */           Object value = valueReference.get();
/* 2298 */           if (value == null) {
/* 2299 */             enqueueNotification(entryKey, hash, valueReference, RemovalCause.COLLECTED);
/* 2300 */           } else if (this.map.isExpired(e, now))
/*      */           {
/* 2303 */             enqueueNotification(entryKey, hash, valueReference, RemovalCause.EXPIRED);
/*      */           } else {
/* 2305 */             recordLockedRead(e, now);
/* 2306 */             this.statsCounter.recordHits(1);
/*      */ 
/* 2308 */             Object localObject2 = value;
/*      */             return localObject2;
/*      */           }
/* 2312 */           this.writeQueue.remove(e);
/* 2313 */           this.accessQueue.remove(e);
/* 2314 */           this.count = newCount;
/*      */ 
/* 2316 */           break;
/*      */         }
/*      */ 
/* 2320 */         if (createNewEntry) {
/* 2321 */           loadingValueReference = new LocalCache.LoadingValueReference();
/*      */ 
/* 2323 */           if (e == null) {
/* 2324 */             e = newEntry(key, hash, first);
/* 2325 */             e.setValueReference(loadingValueReference);
/* 2326 */             table.set(index, e);
/*      */           } else {
/* 2328 */             e.setValueReference(loadingValueReference);
/*      */           }
/*      */         }
/*      */       } finally {
/* 2332 */         unlock();
/* 2333 */         postWriteCleanup();
/*      */       }
/*      */ 
/* 2336 */       if (createNewEntry)
/*      */       {
/*      */         try
/*      */         {
/* 2341 */           synchronized (e) {
/* 2342 */             Object localObject1 = loadSync(key, hash, loadingValueReference, loader);
/*      */ 
/* 2345 */             this.statsCounter.recordMisses(1); return localObject1;
/*      */           } } finally { this.statsCounter.recordMisses(1);
/*      */         }
/*      */       }
/*      */ 
/* 2349 */       return waitForLoadingValue(e, key, valueReference);
/*      */     }
/*      */ 
/*      */     V waitForLoadingValue(LocalCache.ReferenceEntry<K, V> e, K key, LocalCache.ValueReference<K, V> valueReference)
/*      */       throws ExecutionException
/*      */     {
/* 2355 */       if (!valueReference.isLoading()) {
/* 2356 */         throw new AssertionError();
/*      */       }
/*      */ 
/* 2359 */       Preconditions.checkState(!Thread.holdsLock(e), "Recursive load");
/*      */       try
/*      */       {
/* 2362 */         Object value = valueReference.waitForValue();
/* 2363 */         if (value == null) {
/* 2364 */           throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + key + ".");
/*      */         }
/*      */ 
/* 2367 */         long now = this.map.ticker.read();
/* 2368 */         recordRead(e, now);
/* 2369 */         Object localObject1 = value;
/*      */         return localObject1; } finally { this.statsCounter.recordMisses(1); } throw localObject2;
/*      */     }
/*      */ 
/*      */     V loadSync(K key, int hash, LocalCache.LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> loader)
/*      */       throws ExecutionException
/*      */     {
/* 2379 */       ListenableFuture loadingFuture = loadingValueReference.loadFuture(key, loader);
/* 2380 */       return getAndRecordStats(key, hash, loadingValueReference, loadingFuture);
/*      */     }
/*      */ 
/*      */     ListenableFuture<V> loadAsync(K key, int hash, LocalCache.LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> loader)
/*      */     {
/* 2385 */       ListenableFuture loadingFuture = loadingValueReference.loadFuture(key, loader);
/* 2386 */       loadingFuture.addListener(new Runnable(key, hash, loadingValueReference, loadingFuture)
/*      */       {
/*      */         public void run()
/*      */         {
/*      */           try {
/* 2391 */             Object newValue = LocalCache.Segment.this.getAndRecordStats(this.val$key, this.val$hash, this.val$loadingValueReference, this.val$loadingFuture);
/*      */ 
/* 2393 */             this.val$loadingValueReference.set(newValue);
/*      */           } catch (Throwable t) {
/* 2395 */             LocalCache.logger.log(Level.WARNING, "Exception thrown during refresh", t);
/* 2396 */             this.val$loadingValueReference.setException(t);
/*      */           }
/*      */         }
/*      */       }
/*      */       , LocalCache.sameThreadExecutor);
/*      */ 
/* 2400 */       return loadingFuture;
/*      */     }
/*      */ 
/*      */     V getAndRecordStats(K key, int hash, LocalCache.LoadingValueReference<K, V> loadingValueReference, ListenableFuture<V> newValue)
/*      */       throws ExecutionException
/*      */     {
/* 2408 */       Object value = null;
/*      */       try {
/* 2410 */         value = Uninterruptibles.getUninterruptibly(newValue);
/* 2411 */         if (value == null) {
/* 2412 */           throw new CacheLoader.InvalidCacheLoadException("CacheLoader returned null for key " + key + ".");
/*      */         }
/* 2414 */         this.statsCounter.recordLoadSuccess(loadingValueReference.elapsedNanos());
/* 2415 */         storeLoadedValue(key, hash, loadingValueReference, value);
/* 2416 */         Object localObject1 = value;
/*      */         return localObject1;
/*      */       }
/*      */       finally
/*      */       {
/* 2418 */         if (value == null) {
/* 2419 */           this.statsCounter.recordLoadException(loadingValueReference.elapsedNanos());
/* 2420 */           removeLoadingValue(key, hash, loadingValueReference); } 
/* 2420 */       }throw localObject2;
/*      */     }
/*      */ 
/*      */     V scheduleRefresh(LocalCache.ReferenceEntry<K, V> entry, K key, int hash, V oldValue, long now, CacheLoader<? super K, V> loader)
/*      */     {
/* 2427 */       if ((this.map.refreshes()) && (now - entry.getWriteTime() > this.map.refreshNanos) && (!entry.getValueReference().isLoading()))
/*      */       {
/* 2429 */         Object newValue = refresh(key, hash, loader, true);
/* 2430 */         if (newValue != null) {
/* 2431 */           return newValue;
/*      */         }
/*      */       }
/* 2434 */       return oldValue;
/*      */     }
/*      */ 
/*      */     V refresh(K key, int hash, CacheLoader<? super K, V> loader, boolean checkTime)
/*      */     {
/* 2445 */       LocalCache.LoadingValueReference loadingValueReference = insertLoadingValueReference(key, hash, checkTime);
/*      */ 
/* 2447 */       if (loadingValueReference == null) {
/* 2448 */         return null;
/*      */       }
/*      */ 
/* 2451 */       ListenableFuture result = loadAsync(key, hash, loadingValueReference, loader);
/* 2452 */       if (result.isDone())
/*      */         try {
/* 2454 */           return Uninterruptibles.getUninterruptibly(result);
/*      */         }
/*      */         catch (Throwable localThrowable)
/*      */         {
/*      */         }
/* 2459 */       return null;
/*      */     }
/*      */ 
/*      */     LocalCache.LoadingValueReference<K, V> insertLoadingValueReference(K key, int hash, boolean checkTime)
/*      */     {
/* 2469 */       LocalCache.ReferenceEntry e = null;
/* 2470 */       lock();
/*      */       try {
/* 2472 */         long now = this.map.ticker.read();
/* 2473 */         preWriteCleanup(now);
/*      */ 
/* 2475 */         AtomicReferenceArray table = this.table;
/* 2476 */         int index = hash & table.length() - 1;
/* 2477 */         LocalCache.ReferenceEntry first = (LocalCache.ReferenceEntry)table.get(index);
/*      */ 
/* 2480 */         for (e = first; e != null; e = e.getNext()) {
/* 2481 */           Object entryKey = e.getKey();
/* 2482 */           if ((e.getHash() != hash) || (entryKey == null) || (!this.map.keyEquivalence.equivalent(key, entryKey)))
/*      */           {
/*      */             continue;
/*      */           }
/* 2486 */           valueReference = e.getValueReference();
/* 2487 */           if ((valueReference.isLoading()) || ((checkTime) && (now - e.getWriteTime() < this.map.refreshNanos)))
/*      */           {
/* 2492 */             Object localObject1 = null;
/*      */             return localObject1;
/*      */           }
/* 2496 */           this.modCount += 1;
/* 2497 */           LocalCache.LoadingValueReference loadingValueReference = new LocalCache.LoadingValueReference(valueReference);
/*      */ 
/* 2499 */           e.setValueReference(loadingValueReference);
/* 2500 */           LocalCache.LoadingValueReference localLoadingValueReference1 = loadingValueReference;
/*      */           return localLoadingValueReference1;
/*      */         }
/* 2504 */         this.modCount += 1;
/* 2505 */         LocalCache.LoadingValueReference loadingValueReference = new LocalCache.LoadingValueReference();
/* 2506 */         e = newEntry(key, hash, first);
/* 2507 */         e.setValueReference(loadingValueReference);
/* 2508 */         table.set(index, e);
/* 2509 */         LocalCache.ValueReference valueReference = loadingValueReference;
/*      */         return valueReference;
/*      */       }
/*      */       finally
/*      */       {
/* 2511 */         unlock();
/* 2512 */         postWriteCleanup(); } throw localObject2;
/*      */     }
/*      */ 
/*      */     void tryDrainReferenceQueues()
/*      */     {
/* 2522 */       if (tryLock())
/*      */         try {
/* 2524 */           drainReferenceQueues();
/*      */         } finally {
/* 2526 */           unlock();
/*      */         }
/*      */     }
/*      */ 
/*      */     void drainReferenceQueues()
/*      */     {
/* 2537 */       if (this.map.usesKeyReferences()) {
/* 2538 */         drainKeyReferenceQueue();
/*      */       }
/* 2540 */       if (this.map.usesValueReferences())
/* 2541 */         drainValueReferenceQueue();
/*      */     }
/*      */ 
/*      */     void drainKeyReferenceQueue()
/*      */     {
/* 2548 */       int i = 0;
/*      */       Reference ref;
/* 2549 */       while ((ref = this.keyReferenceQueue.poll()) != null)
/*      */       {
/* 2551 */         LocalCache.ReferenceEntry entry = (LocalCache.ReferenceEntry)ref;
/* 2552 */         this.map.reclaimKey(entry);
/* 2553 */         i++; if (i == 16)
/*      */           break;
/*      */       }
/*      */     }
/*      */ 
/*      */     void drainValueReferenceQueue()
/*      */     {
/* 2562 */       int i = 0;
/*      */       Reference ref;
/* 2563 */       while ((ref = this.valueReferenceQueue.poll()) != null)
/*      */       {
/* 2565 */         LocalCache.ValueReference valueReference = (LocalCache.ValueReference)ref;
/* 2566 */         this.map.reclaimValue(valueReference);
/* 2567 */         i++; if (i == 16)
/*      */           break;
/*      */       }
/*      */     }
/*      */ 
/*      */     void clearReferenceQueues()
/*      */     {
/* 2577 */       if (this.map.usesKeyReferences()) {
/* 2578 */         clearKeyReferenceQueue();
/*      */       }
/* 2580 */       if (this.map.usesValueReferences())
/* 2581 */         clearValueReferenceQueue();
/*      */     }
/*      */ 
/*      */     void clearKeyReferenceQueue() {
/* 2586 */       while (this.keyReferenceQueue.poll() != null);
/*      */     }
/*      */ 
/*      */     void clearValueReferenceQueue() {
/* 2590 */       while (this.valueReferenceQueue.poll() != null);
/*      */     }
/*      */ 
/*      */     void recordRead(LocalCache.ReferenceEntry<K, V> entry, long now)
/*      */     {
/* 2603 */       if (this.map.recordsAccess()) {
/* 2604 */         entry.setAccessTime(now);
/*      */       }
/* 2606 */       this.recencyQueue.add(entry);
/*      */     }
/*      */ 
/*      */     void recordLockedRead(LocalCache.ReferenceEntry<K, V> entry, long now)
/*      */     {
/* 2618 */       if (this.map.recordsAccess()) {
/* 2619 */         entry.setAccessTime(now);
/*      */       }
/* 2621 */       this.accessQueue.add(entry);
/*      */     }
/*      */ 
/*      */     void recordWrite(LocalCache.ReferenceEntry<K, V> entry, int weight, long now)
/*      */     {
/* 2631 */       drainRecencyQueue();
/* 2632 */       this.totalWeight += weight;
/*      */ 
/* 2634 */       if (this.map.recordsAccess()) {
/* 2635 */         entry.setAccessTime(now);
/*      */       }
/* 2637 */       if (this.map.recordsWrite()) {
/* 2638 */         entry.setWriteTime(now);
/*      */       }
/* 2640 */       this.accessQueue.add(entry);
/* 2641 */       this.writeQueue.add(entry);
/*      */     }
/*      */ 
/*      */     void drainRecencyQueue()
/*      */     {
/*      */       LocalCache.ReferenceEntry e;
/* 2653 */       while ((e = (LocalCache.ReferenceEntry)this.recencyQueue.poll()) != null)
/*      */       {
/* 2658 */         if (this.accessQueue.contains(e))
/* 2659 */           this.accessQueue.add(e);
/*      */       }
/*      */     }
/*      */ 
/*      */     void tryExpireEntries(long now)
/*      */     {
/* 2670 */       if (tryLock())
/*      */         try {
/* 2672 */           expireEntries(now);
/*      */         } finally {
/* 2674 */           unlock();
/*      */         }
/*      */     }
/*      */ 
/*      */     void expireEntries(long now)
/*      */     {
/* 2682 */       drainRecencyQueue();
/*      */       LocalCache.ReferenceEntry e;
/* 2685 */       while (((e = (LocalCache.ReferenceEntry)this.writeQueue.peek()) != null) && (this.map.isExpired(e, now))) {
/* 2686 */         if (!removeEntry(e, e.getHash(), RemovalCause.EXPIRED)) {
/* 2687 */           throw new AssertionError();
/*      */         }
/*      */       }
/* 2690 */       while (((e = (LocalCache.ReferenceEntry)this.accessQueue.peek()) != null) && (this.map.isExpired(e, now)))
/* 2691 */         if (!removeEntry(e, e.getHash(), RemovalCause.EXPIRED))
/* 2692 */           throw new AssertionError();
/*      */     }
/*      */ 
/*      */     void enqueueNotification(LocalCache.ReferenceEntry<K, V> entry, RemovalCause cause)
/*      */     {
/* 2701 */       enqueueNotification(entry.getKey(), entry.getHash(), entry.getValueReference(), cause);
/*      */     }
/*      */ 
/*      */     void enqueueNotification(K key, int hash, LocalCache.ValueReference<K, V> valueReference, RemovalCause cause)
/*      */     {
/* 2707 */       this.totalWeight -= valueReference.getWeight();
/* 2708 */       if (cause.wasEvicted()) {
/* 2709 */         this.statsCounter.recordEviction();
/*      */       }
/* 2711 */       if (this.map.removalNotificationQueue != LocalCache.DISCARDING_QUEUE) {
/* 2712 */         Object value = valueReference.get();
/* 2713 */         RemovalNotification notification = new RemovalNotification(key, value, cause);
/* 2714 */         this.map.removalNotificationQueue.offer(notification);
/*      */       }
/*      */     }
/*      */ 
/*      */     void evictEntries()
/*      */     {
/* 2724 */       if (!this.map.evictsBySize()) {
/* 2725 */         return;
/*      */       }
/*      */ 
/* 2728 */       drainRecencyQueue();
/* 2729 */       while (this.totalWeight > this.maxSegmentWeight) {
/* 2730 */         LocalCache.ReferenceEntry e = getNextEvictable();
/* 2731 */         if (!removeEntry(e, e.getHash(), RemovalCause.SIZE))
/* 2732 */           throw new AssertionError();
/*      */       }
/*      */     }
/*      */ 
/*      */     LocalCache.ReferenceEntry<K, V> getNextEvictable()
/*      */     {
/* 2739 */       for (LocalCache.ReferenceEntry e : this.accessQueue) {
/* 2740 */         int weight = e.getValueReference().getWeight();
/* 2741 */         if (weight > 0) {
/* 2742 */           return e;
/*      */         }
/*      */       }
/* 2745 */       throw new AssertionError();
/*      */     }
/*      */ 
/*      */     LocalCache.ReferenceEntry<K, V> getFirst(int hash)
/*      */     {
/* 2753 */       AtomicReferenceArray table = this.table;
/* 2754 */       return (LocalCache.ReferenceEntry)table.get(hash & table.length() - 1);
/*      */     }
/*      */ 
/*      */     LocalCache.ReferenceEntry<K, V> getEntry(Object key, int hash)
/*      */     {
/* 2761 */       for (LocalCache.ReferenceEntry e = getFirst(hash); e != null; e = e.getNext()) {
/* 2762 */         if (e.getHash() != hash)
/*      */         {
/*      */           continue;
/*      */         }
/* 2766 */         Object entryKey = e.getKey();
/* 2767 */         if (entryKey == null) {
/* 2768 */           tryDrainReferenceQueues();
/*      */         }
/* 2772 */         else if (this.map.keyEquivalence.equivalent(key, entryKey)) {
/* 2773 */           return e;
/*      */         }
/*      */       }
/*      */ 
/* 2777 */       return null;
/*      */     }
/*      */ 
/*      */     LocalCache.ReferenceEntry<K, V> getLiveEntry(Object key, int hash, long now)
/*      */     {
/* 2782 */       LocalCache.ReferenceEntry e = getEntry(key, hash);
/* 2783 */       if (e == null)
/* 2784 */         return null;
/* 2785 */       if (this.map.isExpired(e, now)) {
/* 2786 */         tryExpireEntries(now);
/* 2787 */         return null;
/*      */       }
/* 2789 */       return e;
/*      */     }
/*      */ 
/*      */     V getLiveValue(LocalCache.ReferenceEntry<K, V> entry, long now)
/*      */     {
/* 2797 */       if (entry.getKey() == null) {
/* 2798 */         tryDrainReferenceQueues();
/* 2799 */         return null;
/*      */       }
/* 2801 */       Object value = entry.getValueReference().get();
/* 2802 */       if (value == null) {
/* 2803 */         tryDrainReferenceQueues();
/* 2804 */         return null;
/*      */       }
/*      */ 
/* 2807 */       if (this.map.isExpired(entry, now)) {
/* 2808 */         tryExpireEntries(now);
/* 2809 */         return null;
/*      */       }
/* 2811 */       return value;
/*      */     }
/*      */ 
/*      */     V get(Object key, int hash)
/*      */     {
/*      */       try {
/* 2817 */         if (this.count != 0) {
/* 2818 */           now = this.map.ticker.read();
/* 2819 */           LocalCache.ReferenceEntry e = getLiveEntry(key, hash, now);
/* 2820 */           if (e == null) {
/* 2821 */             Object localObject1 = null;
/*      */             return localObject1;
/*      */           }
/* 2824 */           Object value = e.getValueReference().get();
/* 2825 */           if (value != null) {
/* 2826 */             recordRead(e, now);
/* 2827 */             Object localObject2 = scheduleRefresh(e, e.getKey(), hash, value, now, this.map.defaultLoader);
/*      */             return localObject2;
/*      */           }
/* 2829 */           tryDrainReferenceQueues();
/*      */         }
/* 2831 */         long now = null;
/*      */         return now; } finally { postReadCleanup(); } throw localObject3;
/*      */     }
/*      */ 
/*      */     boolean containsKey(Object key, int hash)
/*      */     {
/*      */       try {
/* 2839 */         if (this.count != 0) {
/* 2840 */           now = this.map.ticker.read();
/* 2841 */           LocalCache.ReferenceEntry e = getLiveEntry(key, hash, now);
/* 2842 */           if (e == null) {
/* 2843 */             i = 0;
/*      */             return i;
/*      */           }
/* 2845 */           int i = e.getValueReference().get() != null ? 1 : 0;
/*      */           return i;
/*      */         }
/* 2848 */         long now = 0;
/*      */         return now; } finally { postReadCleanup(); } throw localObject;
/*      */     }
/*      */ 
/*      */     V put(K key, int hash, V value, boolean onlyIfAbsent)
/*      */     {
/* 2886 */       lock();
/*      */       try {
/* 2888 */         long now = this.map.ticker.read();
/* 2889 */         preWriteCleanup(now);
/*      */ 
/* 2891 */         int newCount = this.count + 1;
/* 2892 */         if (newCount > this.threshold) {
/* 2893 */           expand();
/* 2894 */           newCount = this.count + 1;
/*      */         }
/*      */ 
/* 2897 */         AtomicReferenceArray table = this.table;
/* 2898 */         int index = hash & table.length() - 1;
/* 2899 */         LocalCache.ReferenceEntry first = (LocalCache.ReferenceEntry)table.get(index);
/*      */ 
/* 2902 */         for (LocalCache.ReferenceEntry e = first; e != null; e = e.getNext()) {
/* 2903 */           entryKey = e.getKey();
/* 2904 */           if ((e.getHash() != hash) || (entryKey == null) || (!this.map.keyEquivalence.equivalent(key, entryKey)))
/*      */           {
/*      */             continue;
/*      */           }
/* 2908 */           LocalCache.ValueReference valueReference = e.getValueReference();
/* 2909 */           Object entryValue = valueReference.get();
/*      */ 
/* 2911 */           if (entryValue == null) {
/* 2912 */             this.modCount += 1;
/* 2913 */             if (valueReference.isActive()) {
/* 2914 */               enqueueNotification(key, hash, valueReference, RemovalCause.COLLECTED);
/* 2915 */               setValue(e, key, value, now);
/* 2916 */               newCount = this.count;
/*      */             } else {
/* 2918 */               setValue(e, key, value, now);
/* 2919 */               newCount = this.count + 1;
/*      */             }
/* 2921 */             this.count = newCount;
/* 2922 */             evictEntries();
/* 2923 */             localObject1 = null;
/*      */             return localObject1;
/*      */           }
/* 2924 */           if (onlyIfAbsent)
/*      */           {
/* 2928 */             recordLockedRead(e, now);
/* 2929 */             localObject1 = entryValue;
/*      */             return localObject1;
/*      */           }
/* 2932 */           this.modCount += 1;
/* 2933 */           enqueueNotification(key, hash, valueReference, RemovalCause.REPLACED);
/* 2934 */           setValue(e, key, value, now);
/* 2935 */           evictEntries();
/* 2936 */           Object localObject1 = entryValue;
/*      */           return localObject1;
/*      */         }
/* 2942 */         this.modCount += 1;
/* 2943 */         LocalCache.ReferenceEntry newEntry = newEntry(key, hash, first);
/* 2944 */         setValue(newEntry, key, value, now);
/* 2945 */         table.set(index, newEntry);
/* 2946 */         newCount = this.count + 1;
/* 2947 */         this.count = newCount;
/* 2948 */         evictEntries();
/* 2949 */         Object entryKey = null;
/*      */         return entryKey;
/*      */       }
/*      */       finally
/*      */       {
/* 2951 */         unlock();
/* 2952 */         postWriteCleanup(); } throw localObject2;
/*      */     }
/*      */ 
/*      */     void expand()
/*      */     {
/* 2961 */       AtomicReferenceArray oldTable = this.table;
/* 2962 */       int oldCapacity = oldTable.length();
/* 2963 */       if (oldCapacity >= 1073741824) {
/* 2964 */         return;
/*      */       }
/*      */ 
/* 2977 */       int newCount = this.count;
/* 2978 */       AtomicReferenceArray newTable = newEntryArray(oldCapacity << 1);
/* 2979 */       this.threshold = (newTable.length() * 3 / 4);
/* 2980 */       int newMask = newTable.length() - 1;
/* 2981 */       for (int oldIndex = 0; oldIndex < oldCapacity; oldIndex++)
/*      */       {
/* 2984 */         LocalCache.ReferenceEntry head = (LocalCache.ReferenceEntry)oldTable.get(oldIndex);
/*      */ 
/* 2986 */         if (head != null) {
/* 2987 */           LocalCache.ReferenceEntry next = head.getNext();
/* 2988 */           int headIndex = head.getHash() & newMask;
/*      */ 
/* 2991 */           if (next == null) {
/* 2992 */             newTable.set(headIndex, head);
/*      */           }
/*      */           else
/*      */           {
/* 2997 */             LocalCache.ReferenceEntry tail = head;
/* 2998 */             int tailIndex = headIndex;
/* 2999 */             for (LocalCache.ReferenceEntry e = next; e != null; e = e.getNext()) {
/* 3000 */               int newIndex = e.getHash() & newMask;
/* 3001 */               if (newIndex == tailIndex)
/*      */                 continue;
/* 3003 */               tailIndex = newIndex;
/* 3004 */               tail = e;
/*      */             }
/*      */ 
/* 3007 */             newTable.set(tailIndex, tail);
/*      */ 
/* 3010 */             for (LocalCache.ReferenceEntry e = head; e != tail; e = e.getNext()) {
/* 3011 */               int newIndex = e.getHash() & newMask;
/* 3012 */               LocalCache.ReferenceEntry newNext = (LocalCache.ReferenceEntry)newTable.get(newIndex);
/* 3013 */               LocalCache.ReferenceEntry newFirst = copyEntry(e, newNext);
/* 3014 */               if (newFirst != null) {
/* 3015 */                 newTable.set(newIndex, newFirst);
/*      */               } else {
/* 3017 */                 removeCollectedEntry(e);
/* 3018 */                 newCount--;
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 3024 */       this.table = newTable;
/* 3025 */       this.count = newCount;
/*      */     }
/*      */ 
/*      */     boolean replace(K key, int hash, V oldValue, V newValue) {
/* 3029 */       lock();
/*      */       try {
/* 3031 */         long now = this.map.ticker.read();
/* 3032 */         preWriteCleanup(now);
/*      */ 
/* 3034 */         AtomicReferenceArray table = this.table;
/* 3035 */         int index = hash & table.length() - 1;
/* 3036 */         LocalCache.ReferenceEntry first = (LocalCache.ReferenceEntry)table.get(index);
/*      */ 
/* 3038 */         for (LocalCache.ReferenceEntry e = first; e != null; e = e.getNext()) {
/* 3039 */           Object entryKey = e.getKey();
/* 3040 */           if ((e.getHash() != hash) || (entryKey == null) || (!this.map.keyEquivalence.equivalent(key, entryKey)))
/*      */             continue;
/* 3042 */           LocalCache.ValueReference valueReference = e.getValueReference();
/* 3043 */           Object entryValue = valueReference.get();
/* 3044 */           if (entryValue == null) {
/* 3045 */             if (valueReference.isActive())
/*      */             {
/* 3047 */               newCount = this.count - 1;
/* 3048 */               this.modCount += 1;
/* 3049 */               LocalCache.ReferenceEntry newFirst = removeValueFromChain(first, e, entryKey, hash, valueReference, RemovalCause.COLLECTED);
/*      */ 
/* 3051 */               newCount = this.count - 1;
/* 3052 */               table.set(index, newFirst);
/* 3053 */               this.count = newCount;
/*      */             }
/* 3055 */             newCount = 0;
/*      */             return newCount;
/*      */           }
/* 3058 */           if (this.map.valueEquivalence.equivalent(oldValue, entryValue)) {
/* 3059 */             this.modCount += 1;
/* 3060 */             enqueueNotification(key, hash, valueReference, RemovalCause.REPLACED);
/* 3061 */             setValue(e, key, newValue, now);
/* 3062 */             evictEntries();
/* 3063 */             newCount = 1;
/*      */             return newCount;
/*      */           }
/* 3067 */           recordLockedRead(e, now);
/* 3068 */           int newCount = 0;
/*      */           return newCount;
/*      */         }
/* 3073 */         e = 0;
/*      */         return e;
/*      */       }
/*      */       finally
/*      */       {
/* 3075 */         unlock();
/* 3076 */         postWriteCleanup(); } throw localObject1;
/*      */     }
/*      */ 
/*      */     V replace(K key, int hash, V newValue)
/*      */     {
/* 3082 */       lock();
/*      */       try {
/* 3084 */         long now = this.map.ticker.read();
/* 3085 */         preWriteCleanup(now);
/*      */ 
/* 3087 */         AtomicReferenceArray table = this.table;
/* 3088 */         int index = hash & table.length() - 1;
/* 3089 */         LocalCache.ReferenceEntry first = (LocalCache.ReferenceEntry)table.get(index);
/*      */ 
/* 3091 */         for (LocalCache.ReferenceEntry e = first; e != null; e = e.getNext()) {
/* 3092 */           Object entryKey = e.getKey();
/* 3093 */           if ((e.getHash() != hash) || (entryKey == null) || (!this.map.keyEquivalence.equivalent(key, entryKey)))
/*      */             continue;
/* 3095 */           LocalCache.ValueReference valueReference = e.getValueReference();
/* 3096 */           Object entryValue = valueReference.get();
/* 3097 */           if (entryValue == null) {
/* 3098 */             if (valueReference.isActive())
/*      */             {
/* 3100 */               newCount = this.count - 1;
/* 3101 */               this.modCount += 1;
/* 3102 */               LocalCache.ReferenceEntry newFirst = removeValueFromChain(first, e, entryKey, hash, valueReference, RemovalCause.COLLECTED);
/*      */ 
/* 3104 */               newCount = this.count - 1;
/* 3105 */               table.set(index, newFirst);
/* 3106 */               this.count = newCount;
/*      */             }
/* 3108 */             newCount = null;
/*      */             return newCount;
/*      */           }
/* 3111 */           this.modCount += 1;
/* 3112 */           enqueueNotification(key, hash, valueReference, RemovalCause.REPLACED);
/* 3113 */           setValue(e, key, newValue, now);
/* 3114 */           evictEntries();
/* 3115 */           int newCount = entryValue;
/*      */           return newCount;
/*      */         }
/* 3119 */         e = null;
/*      */         return e;
/*      */       }
/*      */       finally
/*      */       {
/* 3121 */         unlock();
/* 3122 */         postWriteCleanup(); } throw localObject1;
/*      */     }
/*      */ 
/*      */     V remove(Object key, int hash)
/*      */     {
/* 3128 */       lock();
/*      */       try {
/* 3130 */         long now = this.map.ticker.read();
/* 3131 */         preWriteCleanup(now);
/*      */ 
/* 3133 */         int newCount = this.count - 1;
/* 3134 */         AtomicReferenceArray table = this.table;
/* 3135 */         int index = hash & table.length() - 1;
/* 3136 */         LocalCache.ReferenceEntry first = (LocalCache.ReferenceEntry)table.get(index);
/*      */ 
/* 3138 */         for (LocalCache.ReferenceEntry e = first; e != null; e = e.getNext()) {
/* 3139 */           Object entryKey = e.getKey();
/* 3140 */           if ((e.getHash() != hash) || (entryKey == null) || (!this.map.keyEquivalence.equivalent(key, entryKey)))
/*      */             continue;
/* 3142 */           LocalCache.ValueReference valueReference = e.getValueReference();
/* 3143 */           Object entryValue = valueReference.get();
/*      */           RemovalCause cause;
/* 3146 */           if (entryValue != null) {
/* 3147 */             cause = RemovalCause.EXPLICIT;
/*      */           }
/*      */           else
/*      */           {
/*      */             RemovalCause cause;
/* 3148 */             if (valueReference.isActive()) {
/* 3149 */               cause = RemovalCause.COLLECTED;
/*      */             }
/*      */             else {
/* 3152 */               Object localObject1 = null;
/*      */               return localObject1;
/*      */             }
/*      */           }
/*      */           RemovalCause cause;
/* 3155 */           this.modCount += 1;
/* 3156 */           LocalCache.ReferenceEntry newFirst = removeValueFromChain(first, e, entryKey, hash, valueReference, cause);
/*      */ 
/* 3158 */           newCount = this.count - 1;
/* 3159 */           table.set(index, newFirst);
/* 3160 */           this.count = newCount;
/* 3161 */           Object localObject2 = entryValue;
/*      */           return localObject2;
/*      */         }
/* 3165 */         e = null;
/*      */         return e;
/*      */       }
/*      */       finally
/*      */       {
/* 3167 */         unlock();
/* 3168 */         postWriteCleanup(); } throw localObject3;
/*      */     }
/*      */ 
/*      */     boolean storeLoadedValue(K key, int hash, LocalCache.LoadingValueReference<K, V> oldValueReference, V newValue)
/*      */     {
/* 3174 */       lock();
/*      */       try {
/* 3176 */         long now = this.map.ticker.read();
/* 3177 */         preWriteCleanup(now);
/*      */ 
/* 3179 */         int newCount = this.count + 1;
/* 3180 */         if (newCount > this.threshold) {
/* 3181 */           expand();
/* 3182 */           newCount = this.count + 1;
/*      */         }
/*      */ 
/* 3185 */         AtomicReferenceArray table = this.table;
/* 3186 */         int index = hash & table.length() - 1;
/* 3187 */         LocalCache.ReferenceEntry first = (LocalCache.ReferenceEntry)table.get(index);
/*      */ 
/* 3189 */         for (LocalCache.ReferenceEntry e = first; e != null; e = e.getNext()) {
/* 3190 */           entryKey = e.getKey();
/* 3191 */           if ((e.getHash() != hash) || (entryKey == null) || (!this.map.keyEquivalence.equivalent(key, entryKey)))
/*      */             continue;
/* 3193 */           LocalCache.ValueReference valueReference = e.getValueReference();
/* 3194 */           Object entryValue = valueReference.get();
/*      */ 
/* 3197 */           if ((oldValueReference == valueReference) || ((entryValue == null) && (valueReference != LocalCache.UNSET)))
/*      */           {
/* 3199 */             this.modCount += 1;
/* 3200 */             if (oldValueReference.isActive()) {
/* 3201 */               cause = entryValue == null ? RemovalCause.COLLECTED : RemovalCause.REPLACED;
/*      */ 
/* 3203 */               enqueueNotification(key, hash, oldValueReference, cause);
/* 3204 */               newCount--;
/*      */             }
/* 3206 */             setValue(e, key, newValue, now);
/* 3207 */             this.count = newCount;
/* 3208 */             evictEntries();
/* 3209 */             cause = 1;
/*      */             return cause;
/*      */           }
/* 3213 */           valueReference = new LocalCache.WeightedStrongValueReference(newValue, 0);
/* 3214 */           enqueueNotification(key, hash, valueReference, RemovalCause.REPLACED);
/* 3215 */           RemovalCause cause = 0;
/*      */           return cause;
/*      */         }
/* 3219 */         this.modCount += 1;
/* 3220 */         LocalCache.ReferenceEntry newEntry = newEntry(key, hash, first);
/* 3221 */         setValue(newEntry, key, newValue, now);
/* 3222 */         table.set(index, newEntry);
/* 3223 */         this.count = newCount;
/* 3224 */         evictEntries();
/* 3225 */         Object entryKey = 1;
/*      */         return entryKey;
/*      */       }
/*      */       finally
/*      */       {
/* 3227 */         unlock();
/* 3228 */         postWriteCleanup(); } throw localObject1;
/*      */     }
/*      */ 
/*      */     boolean remove(Object key, int hash, Object value)
/*      */     {
/* 3233 */       lock();
/*      */       try {
/* 3235 */         long now = this.map.ticker.read();
/* 3236 */         preWriteCleanup(now);
/*      */ 
/* 3238 */         int newCount = this.count - 1;
/* 3239 */         AtomicReferenceArray table = this.table;
/* 3240 */         int index = hash & table.length() - 1;
/* 3241 */         LocalCache.ReferenceEntry first = (LocalCache.ReferenceEntry)table.get(index);
/*      */ 
/* 3243 */         for (LocalCache.ReferenceEntry e = first; e != null; e = e.getNext()) {
/* 3244 */           Object entryKey = e.getKey();
/* 3245 */           if ((e.getHash() != hash) || (entryKey == null) || (!this.map.keyEquivalence.equivalent(key, entryKey)))
/*      */             continue;
/* 3247 */           LocalCache.ValueReference valueReference = e.getValueReference();
/* 3248 */           Object entryValue = valueReference.get();
/*      */           RemovalCause cause;
/* 3251 */           if (this.map.valueEquivalence.equivalent(value, entryValue)) {
/* 3252 */             cause = RemovalCause.EXPLICIT;
/*      */           }
/*      */           else
/*      */           {
/*      */             RemovalCause cause;
/* 3253 */             if ((entryValue == null) && (valueReference.isActive())) {
/* 3254 */               cause = RemovalCause.COLLECTED;
/*      */             }
/*      */             else {
/* 3257 */               int i = 0;
/*      */               return i;
/*      */             }
/*      */           }
/*      */           RemovalCause cause;
/* 3260 */           this.modCount += 1;
/* 3261 */           LocalCache.ReferenceEntry newFirst = removeValueFromChain(first, e, entryKey, hash, valueReference, cause);
/*      */ 
/* 3263 */           newCount = this.count - 1;
/* 3264 */           table.set(index, newFirst);
/* 3265 */           this.count = newCount;
/* 3266 */           int j = cause == RemovalCause.EXPLICIT ? 1 : 0;
/*      */           return j;
/*      */         }
/* 3270 */         e = 0;
/*      */         return e;
/*      */       }
/*      */       finally
/*      */       {
/* 3272 */         unlock();
/* 3273 */         postWriteCleanup(); } throw localObject1;
/*      */     }
/*      */ 
/*      */     void clear()
/*      */     {
/* 3278 */       if (this.count != 0) {
/* 3279 */         lock();
/*      */         try {
/* 3281 */           AtomicReferenceArray table = this.table;
/* 3282 */           for (int i = 0; i < table.length(); i++) {
/* 3283 */             for (LocalCache.ReferenceEntry e = (LocalCache.ReferenceEntry)table.get(i); e != null; e = e.getNext())
/*      */             {
/* 3285 */               if (e.getValueReference().isActive()) {
/* 3286 */                 enqueueNotification(e, RemovalCause.EXPLICIT);
/*      */               }
/*      */             }
/*      */           }
/* 3290 */           for (int i = 0; i < table.length(); i++) {
/* 3291 */             table.set(i, null);
/*      */           }
/* 3293 */           clearReferenceQueues();
/* 3294 */           this.writeQueue.clear();
/* 3295 */           this.accessQueue.clear();
/* 3296 */           this.readCount.set(0);
/*      */ 
/* 3298 */           this.modCount += 1;
/* 3299 */           this.count = 0;
/*      */         } finally {
/* 3301 */           unlock();
/* 3302 */           postWriteCleanup();
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     LocalCache.ReferenceEntry<K, V> removeValueFromChain(LocalCache.ReferenceEntry<K, V> first, LocalCache.ReferenceEntry<K, V> entry, K key, int hash, LocalCache.ValueReference<K, V> valueReference, RemovalCause cause)
/*      */     {
/* 3312 */       enqueueNotification(key, hash, valueReference, cause);
/* 3313 */       this.writeQueue.remove(entry);
/* 3314 */       this.accessQueue.remove(entry);
/*      */ 
/* 3316 */       if (valueReference.isLoading()) {
/* 3317 */         valueReference.notifyNewValue(null);
/* 3318 */         return first;
/*      */       }
/* 3320 */       return removeEntryFromChain(first, entry);
/*      */     }
/*      */ 
/*      */     LocalCache.ReferenceEntry<K, V> removeEntryFromChain(LocalCache.ReferenceEntry<K, V> first, LocalCache.ReferenceEntry<K, V> entry)
/*      */     {
/* 3328 */       int newCount = this.count;
/* 3329 */       LocalCache.ReferenceEntry newFirst = entry.getNext();
/* 3330 */       for (LocalCache.ReferenceEntry e = first; e != entry; e = e.getNext()) {
/* 3331 */         LocalCache.ReferenceEntry next = copyEntry(e, newFirst);
/* 3332 */         if (next != null) {
/* 3333 */           newFirst = next;
/*      */         } else {
/* 3335 */           removeCollectedEntry(e);
/* 3336 */           newCount--;
/*      */         }
/*      */       }
/* 3339 */       this.count = newCount;
/* 3340 */       return newFirst;
/*      */     }
/*      */ 
/*      */     void removeCollectedEntry(LocalCache.ReferenceEntry<K, V> entry)
/*      */     {
/* 3345 */       enqueueNotification(entry, RemovalCause.COLLECTED);
/* 3346 */       this.writeQueue.remove(entry);
/* 3347 */       this.accessQueue.remove(entry);
/*      */     }
/*      */ 
/*      */     boolean reclaimKey(LocalCache.ReferenceEntry<K, V> entry, int hash)
/*      */     {
/* 3354 */       lock();
/*      */       try {
/* 3356 */         int newCount = this.count - 1;
/* 3357 */         AtomicReferenceArray table = this.table;
/* 3358 */         int index = hash & table.length() - 1;
/* 3359 */         LocalCache.ReferenceEntry first = (LocalCache.ReferenceEntry)table.get(index);
/*      */ 
/* 3361 */         for (LocalCache.ReferenceEntry e = first; e != null; e = e.getNext())
/* 3362 */           if (e == entry) {
/* 3363 */             this.modCount += 1;
/* 3364 */             LocalCache.ReferenceEntry newFirst = removeValueFromChain(first, e, e.getKey(), hash, e.getValueReference(), RemovalCause.COLLECTED);
/*      */ 
/* 3366 */             newCount = this.count - 1;
/* 3367 */             table.set(index, newFirst);
/* 3368 */             this.count = newCount;
/* 3369 */             int i = 1;
/*      */             return i;
/*      */           }
/* 3373 */         e = 0;
/*      */         return e;
/*      */       }
/*      */       finally
/*      */       {
/* 3375 */         unlock();
/* 3376 */         postWriteCleanup(); } throw localObject;
/*      */     }
/*      */ 
/*      */     boolean reclaimValue(K key, int hash, LocalCache.ValueReference<K, V> valueReference)
/*      */     {
/* 3384 */       lock();
/*      */       try {
/* 3386 */         int newCount = this.count - 1;
/* 3387 */         AtomicReferenceArray table = this.table;
/* 3388 */         int index = hash & table.length() - 1;
/* 3389 */         LocalCache.ReferenceEntry first = (LocalCache.ReferenceEntry)table.get(index);
/*      */ 
/* 3391 */         for (LocalCache.ReferenceEntry e = first; e != null; e = e.getNext()) {
/* 3392 */           Object entryKey = e.getKey();
/* 3393 */           if ((e.getHash() != hash) || (entryKey == null) || (!this.map.keyEquivalence.equivalent(key, entryKey)))
/*      */             continue;
/* 3395 */           LocalCache.ValueReference v = e.getValueReference();
/* 3396 */           if (v == valueReference) {
/* 3397 */             this.modCount += 1;
/* 3398 */             newFirst = removeValueFromChain(first, e, entryKey, hash, valueReference, RemovalCause.COLLECTED);
/*      */ 
/* 3400 */             newCount = this.count - 1;
/* 3401 */             table.set(index, newFirst);
/* 3402 */             this.count = newCount;
/* 3403 */             int i = 1;
/*      */             return i;
/*      */           }
/* 3405 */           LocalCache.ReferenceEntry newFirst = 0;
/*      */           return newFirst;
/*      */         }
/* 3409 */         e = 0;
/*      */         return e;
/*      */       }
/*      */       finally
/*      */       {
/* 3411 */         unlock();
/* 3412 */         if (!isHeldByCurrentThread())
/* 3413 */           postWriteCleanup(); 
/* 3413 */       }throw localObject1;
/*      */     }
/*      */ 
/*      */     boolean removeLoadingValue(K key, int hash, LocalCache.LoadingValueReference<K, V> valueReference)
/*      */     {
/* 3419 */       lock();
/*      */       try {
/* 3421 */         AtomicReferenceArray table = this.table;
/* 3422 */         int index = hash & table.length() - 1;
/* 3423 */         LocalCache.ReferenceEntry first = (LocalCache.ReferenceEntry)table.get(index);
/*      */ 
/* 3425 */         for (LocalCache.ReferenceEntry e = first; e != null; e = e.getNext()) {
/* 3426 */           Object entryKey = e.getKey();
/* 3427 */           if ((e.getHash() != hash) || (entryKey == null) || (!this.map.keyEquivalence.equivalent(key, entryKey)))
/*      */             continue;
/* 3429 */           LocalCache.ValueReference v = e.getValueReference();
/* 3430 */           if (v == valueReference) {
/* 3431 */             if (valueReference.isActive()) {
/* 3432 */               e.setValueReference(valueReference.getOldValue());
/*      */             } else {
/* 3434 */               newFirst = removeEntryFromChain(first, e);
/* 3435 */               table.set(index, newFirst);
/*      */             }
/* 3437 */             newFirst = 1;
/*      */             return newFirst;
/*      */           }
/* 3439 */           LocalCache.ReferenceEntry newFirst = 0;
/*      */           return newFirst;
/*      */         }
/* 3443 */         e = 0;
/*      */         return e;
/*      */       }
/*      */       finally
/*      */       {
/* 3445 */         unlock();
/* 3446 */         postWriteCleanup(); } throw localObject1;
/*      */     }
/*      */ 
/*      */     boolean removeEntry(LocalCache.ReferenceEntry<K, V> entry, int hash, RemovalCause cause)
/*      */     {
/* 3452 */       int newCount = this.count - 1;
/* 3453 */       AtomicReferenceArray table = this.table;
/* 3454 */       int index = hash & table.length() - 1;
/* 3455 */       LocalCache.ReferenceEntry first = (LocalCache.ReferenceEntry)table.get(index);
/*      */ 
/* 3457 */       for (LocalCache.ReferenceEntry e = first; e != null; e = e.getNext()) {
/* 3458 */         if (e == entry) {
/* 3459 */           this.modCount += 1;
/* 3460 */           LocalCache.ReferenceEntry newFirst = removeValueFromChain(first, e, e.getKey(), hash, e.getValueReference(), cause);
/*      */ 
/* 3462 */           newCount = this.count - 1;
/* 3463 */           table.set(index, newFirst);
/* 3464 */           this.count = newCount;
/* 3465 */           return true;
/*      */         }
/*      */       }
/*      */ 
/* 3469 */       return false;
/*      */     }
/*      */ 
/*      */     void postReadCleanup()
/*      */     {
/* 3477 */       if ((this.readCount.incrementAndGet() & 0x3F) == 0)
/* 3478 */         cleanUp();
/*      */     }
/*      */ 
/*      */     void preWriteCleanup(long now)
/*      */     {
/* 3490 */       runLockedCleanup(now);
/*      */     }
/*      */ 
/*      */     void postWriteCleanup()
/*      */     {
/* 3497 */       runUnlockedCleanup();
/*      */     }
/*      */ 
/*      */     void cleanUp() {
/* 3501 */       long now = this.map.ticker.read();
/* 3502 */       runLockedCleanup(now);
/* 3503 */       runUnlockedCleanup();
/*      */     }
/*      */ 
/*      */     void runLockedCleanup(long now) {
/* 3507 */       if (tryLock())
/*      */         try {
/* 3509 */           drainReferenceQueues();
/* 3510 */           expireEntries(now);
/* 3511 */           this.readCount.set(0);
/*      */         } finally {
/* 3513 */           unlock();
/*      */         }
/*      */     }
/*      */ 
/*      */     void runUnlockedCleanup()
/*      */     {
/* 3520 */       if (!isHeldByCurrentThread())
/* 3521 */         this.map.processPendingNotifications();
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class WeightedStrongValueReference<K, V> extends LocalCache.StrongValueReference<K, V>
/*      */   {
/*      */     final int weight;
/*      */ 
/*      */     WeightedStrongValueReference(V referent, int weight)
/*      */     {
/* 1839 */       super();
/* 1840 */       this.weight = weight;
/*      */     }
/*      */ 
/*      */     public int getWeight()
/*      */     {
/* 1845 */       return this.weight;
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class WeightedSoftValueReference<K, V> extends LocalCache.SoftValueReference<K, V>
/*      */   {
/*      */     final int weight;
/*      */ 
/*      */     WeightedSoftValueReference(ReferenceQueue<V> queue, V referent, LocalCache.ReferenceEntry<K, V> entry, int weight)
/*      */     {
/* 1816 */       super(referent, entry);
/* 1817 */       this.weight = weight;
/*      */     }
/*      */ 
/*      */     public int getWeight()
/*      */     {
/* 1822 */       return this.weight;
/*      */     }
/*      */ 
/*      */     public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, LocalCache.ReferenceEntry<K, V> entry)
/*      */     {
/* 1827 */       return new WeightedSoftValueReference(queue, value, entry, this.weight);
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class WeightedWeakValueReference<K, V> extends LocalCache.WeakValueReference<K, V>
/*      */   {
/*      */     final int weight;
/*      */ 
/*      */     WeightedWeakValueReference(ReferenceQueue<V> queue, V referent, LocalCache.ReferenceEntry<K, V> entry, int weight)
/*      */     {
/* 1792 */       super(referent, entry);
/* 1793 */       this.weight = weight;
/*      */     }
/*      */ 
/*      */     public int getWeight()
/*      */     {
/* 1798 */       return this.weight;
/*      */     }
/*      */ 
/*      */     public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, LocalCache.ReferenceEntry<K, V> entry)
/*      */     {
/* 1804 */       return new WeightedWeakValueReference(queue, value, entry, this.weight);
/*      */     }
/*      */   }
/*      */ 
/*      */   static class StrongValueReference<K, V>
/*      */     implements LocalCache.ValueReference<K, V>
/*      */   {
/*      */     final V referent;
/*      */ 
/*      */     StrongValueReference(V referent)
/*      */     {
/* 1741 */       this.referent = referent;
/*      */     }
/*      */ 
/*      */     public V get()
/*      */     {
/* 1746 */       return this.referent;
/*      */     }
/*      */ 
/*      */     public int getWeight()
/*      */     {
/* 1751 */       return 1;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getEntry()
/*      */     {
/* 1756 */       return null;
/*      */     }
/*      */ 
/*      */     public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, LocalCache.ReferenceEntry<K, V> entry)
/*      */     {
/* 1762 */       return this;
/*      */     }
/*      */ 
/*      */     public boolean isLoading()
/*      */     {
/* 1767 */       return false;
/*      */     }
/*      */ 
/*      */     public boolean isActive()
/*      */     {
/* 1772 */       return true;
/*      */     }
/*      */ 
/*      */     public V waitForValue()
/*      */     {
/* 1777 */       return get();
/*      */     }
/*      */ 
/*      */     public void notifyNewValue(V newValue)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   static class SoftValueReference<K, V> extends SoftReference<V>
/*      */     implements LocalCache.ValueReference<K, V>
/*      */   {
/*      */     final LocalCache.ReferenceEntry<K, V> entry;
/*      */ 
/*      */     SoftValueReference(ReferenceQueue<V> queue, V referent, LocalCache.ReferenceEntry<K, V> entry)
/*      */     {
/* 1695 */       super(queue);
/* 1696 */       this.entry = entry;
/*      */     }
/*      */ 
/*      */     public int getWeight()
/*      */     {
/* 1701 */       return 1;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getEntry()
/*      */     {
/* 1706 */       return this.entry;
/*      */     }
/*      */ 
/*      */     public void notifyNewValue(V newValue)
/*      */     {
/*      */     }
/*      */ 
/*      */     public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, LocalCache.ReferenceEntry<K, V> entry)
/*      */     {
/* 1715 */       return new SoftValueReference(queue, value, entry);
/*      */     }
/*      */ 
/*      */     public boolean isLoading()
/*      */     {
/* 1720 */       return false;
/*      */     }
/*      */ 
/*      */     public boolean isActive()
/*      */     {
/* 1725 */       return true;
/*      */     }
/*      */ 
/*      */     public V waitForValue()
/*      */     {
/* 1730 */       return get();
/*      */     }
/*      */   }
/*      */ 
/*      */   static class WeakValueReference<K, V> extends WeakReference<V>
/*      */     implements LocalCache.ValueReference<K, V>
/*      */   {
/*      */     final LocalCache.ReferenceEntry<K, V> entry;
/*      */ 
/*      */     WeakValueReference(ReferenceQueue<V> queue, V referent, LocalCache.ReferenceEntry<K, V> entry)
/*      */     {
/* 1648 */       super(queue);
/* 1649 */       this.entry = entry;
/*      */     }
/*      */ 
/*      */     public int getWeight()
/*      */     {
/* 1654 */       return 1;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getEntry()
/*      */     {
/* 1659 */       return this.entry;
/*      */     }
/*      */ 
/*      */     public void notifyNewValue(V newValue)
/*      */     {
/*      */     }
/*      */ 
/*      */     public LocalCache.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, LocalCache.ReferenceEntry<K, V> entry)
/*      */     {
/* 1668 */       return new WeakValueReference(queue, value, entry);
/*      */     }
/*      */ 
/*      */     public boolean isLoading()
/*      */     {
/* 1673 */       return false;
/*      */     }
/*      */ 
/*      */     public boolean isActive()
/*      */     {
/* 1678 */       return true;
/*      */     }
/*      */ 
/*      */     public V waitForValue()
/*      */     {
/* 1683 */       return get();
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class WeakAccessWriteEntry<K, V> extends LocalCache.WeakEntry<K, V>
/*      */     implements LocalCache.ReferenceEntry<K, V>
/*      */   {
/* 1561 */     volatile long accessTime = 9223372036854775807L;
/*      */ 
/* 1573 */     LocalCache.ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
/*      */ 
/* 1586 */     LocalCache.ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();
/*      */ 
/* 1601 */     volatile long writeTime = 9223372036854775807L;
/*      */ 
/* 1613 */     LocalCache.ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
/*      */ 
/* 1626 */     LocalCache.ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
/*      */ 
/*      */     WeakAccessWriteEntry(ReferenceQueue<K> queue, K key, int hash, LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 1556 */       super(key, hash, next);
/*      */     }
/*      */ 
/*      */     public long getAccessTime()
/*      */     {
/* 1565 */       return this.accessTime;
/*      */     }
/*      */ 
/*      */     public void setAccessTime(long time)
/*      */     {
/* 1570 */       this.accessTime = time;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue()
/*      */     {
/* 1578 */       return this.nextAccess;
/*      */     }
/*      */ 
/*      */     public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 1583 */       this.nextAccess = next;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue()
/*      */     {
/* 1591 */       return this.previousAccess;
/*      */     }
/*      */ 
/*      */     public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous)
/*      */     {
/* 1596 */       this.previousAccess = previous;
/*      */     }
/*      */ 
/*      */     public long getWriteTime()
/*      */     {
/* 1605 */       return this.writeTime;
/*      */     }
/*      */ 
/*      */     public void setWriteTime(long time)
/*      */     {
/* 1610 */       this.writeTime = time;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue()
/*      */     {
/* 1618 */       return this.nextWrite;
/*      */     }
/*      */ 
/*      */     public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 1623 */       this.nextWrite = next;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue()
/*      */     {
/* 1631 */       return this.previousWrite;
/*      */     }
/*      */ 
/*      */     public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous)
/*      */     {
/* 1636 */       this.previousWrite = previous;
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class WeakWriteEntry<K, V> extends LocalCache.WeakEntry<K, V>
/*      */     implements LocalCache.ReferenceEntry<K, V>
/*      */   {
/* 1513 */     volatile long writeTime = 9223372036854775807L;
/*      */ 
/* 1525 */     LocalCache.ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
/*      */ 
/* 1538 */     LocalCache.ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
/*      */ 
/*      */     WeakWriteEntry(ReferenceQueue<K> queue, K key, int hash, LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 1508 */       super(key, hash, next);
/*      */     }
/*      */ 
/*      */     public long getWriteTime()
/*      */     {
/* 1517 */       return this.writeTime;
/*      */     }
/*      */ 
/*      */     public void setWriteTime(long time)
/*      */     {
/* 1522 */       this.writeTime = time;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue()
/*      */     {
/* 1530 */       return this.nextWrite;
/*      */     }
/*      */ 
/*      */     public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 1535 */       this.nextWrite = next;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue()
/*      */     {
/* 1543 */       return this.previousWrite;
/*      */     }
/*      */ 
/*      */     public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous)
/*      */     {
/* 1548 */       this.previousWrite = previous;
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class WeakAccessEntry<K, V> extends LocalCache.WeakEntry<K, V>
/*      */     implements LocalCache.ReferenceEntry<K, V>
/*      */   {
/* 1465 */     volatile long accessTime = 9223372036854775807L;
/*      */ 
/* 1477 */     LocalCache.ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
/*      */ 
/* 1490 */     LocalCache.ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();
/*      */ 
/*      */     WeakAccessEntry(ReferenceQueue<K> queue, K key, int hash, LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 1460 */       super(key, hash, next);
/*      */     }
/*      */ 
/*      */     public long getAccessTime()
/*      */     {
/* 1469 */       return this.accessTime;
/*      */     }
/*      */ 
/*      */     public void setAccessTime(long time)
/*      */     {
/* 1474 */       this.accessTime = time;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue()
/*      */     {
/* 1482 */       return this.nextAccess;
/*      */     }
/*      */ 
/*      */     public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 1487 */       this.nextAccess = next;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue()
/*      */     {
/* 1495 */       return this.previousAccess;
/*      */     }
/*      */ 
/*      */     public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous)
/*      */     {
/* 1500 */       this.previousAccess = previous;
/*      */     }
/*      */   }
/*      */ 
/*      */   static class WeakEntry<K, V> extends WeakReference<K>
/*      */     implements LocalCache.ReferenceEntry<K, V>
/*      */   {
/*      */     final int hash;
/*      */     final LocalCache.ReferenceEntry<K, V> next;
/* 1433 */     volatile LocalCache.ValueReference<K, V> valueReference = LocalCache.unset();
/*      */ 
/*      */     WeakEntry(ReferenceQueue<K> queue, K key, int hash, LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 1355 */       super(queue);
/* 1356 */       this.hash = hash;
/* 1357 */       this.next = next;
/*      */     }
/*      */ 
/*      */     public K getKey()
/*      */     {
/* 1362 */       return get();
/*      */     }
/*      */ 
/*      */     public long getAccessTime()
/*      */     {
/* 1369 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setAccessTime(long time)
/*      */     {
/* 1374 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue()
/*      */     {
/* 1379 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 1384 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue()
/*      */     {
/* 1389 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous)
/*      */     {
/* 1394 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public long getWriteTime()
/*      */     {
/* 1401 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setWriteTime(long time)
/*      */     {
/* 1406 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue()
/*      */     {
/* 1411 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 1416 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue()
/*      */     {
/* 1421 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous)
/*      */     {
/* 1426 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public LocalCache.ValueReference<K, V> getValueReference()
/*      */     {
/* 1437 */       return this.valueReference;
/*      */     }
/*      */ 
/*      */     public void setValueReference(LocalCache.ValueReference<K, V> valueReference)
/*      */     {
/* 1442 */       this.valueReference = valueReference;
/*      */     }
/*      */ 
/*      */     public int getHash()
/*      */     {
/* 1447 */       return this.hash;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getNext()
/*      */     {
/* 1452 */       return this.next;
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class StrongAccessWriteEntry<K, V> extends LocalCache.StrongEntry<K, V>
/*      */     implements LocalCache.ReferenceEntry<K, V>
/*      */   {
/* 1271 */     volatile long accessTime = 9223372036854775807L;
/*      */ 
/* 1283 */     LocalCache.ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
/*      */ 
/* 1296 */     LocalCache.ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();
/*      */ 
/* 1311 */     volatile long writeTime = 9223372036854775807L;
/*      */ 
/* 1323 */     LocalCache.ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
/*      */ 
/* 1336 */     LocalCache.ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
/*      */ 
/*      */     StrongAccessWriteEntry(K key, int hash, LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 1266 */       super(hash, next);
/*      */     }
/*      */ 
/*      */     public long getAccessTime()
/*      */     {
/* 1275 */       return this.accessTime;
/*      */     }
/*      */ 
/*      */     public void setAccessTime(long time)
/*      */     {
/* 1280 */       this.accessTime = time;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue()
/*      */     {
/* 1288 */       return this.nextAccess;
/*      */     }
/*      */ 
/*      */     public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 1293 */       this.nextAccess = next;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue()
/*      */     {
/* 1301 */       return this.previousAccess;
/*      */     }
/*      */ 
/*      */     public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous)
/*      */     {
/* 1306 */       this.previousAccess = previous;
/*      */     }
/*      */ 
/*      */     public long getWriteTime()
/*      */     {
/* 1315 */       return this.writeTime;
/*      */     }
/*      */ 
/*      */     public void setWriteTime(long time)
/*      */     {
/* 1320 */       this.writeTime = time;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue()
/*      */     {
/* 1328 */       return this.nextWrite;
/*      */     }
/*      */ 
/*      */     public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 1333 */       this.nextWrite = next;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue()
/*      */     {
/* 1341 */       return this.previousWrite;
/*      */     }
/*      */ 
/*      */     public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous)
/*      */     {
/* 1346 */       this.previousWrite = previous;
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class StrongWriteEntry<K, V> extends LocalCache.StrongEntry<K, V>
/*      */     implements LocalCache.ReferenceEntry<K, V>
/*      */   {
/* 1224 */     volatile long writeTime = 9223372036854775807L;
/*      */ 
/* 1236 */     LocalCache.ReferenceEntry<K, V> nextWrite = LocalCache.nullEntry();
/*      */ 
/* 1249 */     LocalCache.ReferenceEntry<K, V> previousWrite = LocalCache.nullEntry();
/*      */ 
/*      */     StrongWriteEntry(K key, int hash, LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 1219 */       super(hash, next);
/*      */     }
/*      */ 
/*      */     public long getWriteTime()
/*      */     {
/* 1228 */       return this.writeTime;
/*      */     }
/*      */ 
/*      */     public void setWriteTime(long time)
/*      */     {
/* 1233 */       this.writeTime = time;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue()
/*      */     {
/* 1241 */       return this.nextWrite;
/*      */     }
/*      */ 
/*      */     public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 1246 */       this.nextWrite = next;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue()
/*      */     {
/* 1254 */       return this.previousWrite;
/*      */     }
/*      */ 
/*      */     public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous)
/*      */     {
/* 1259 */       this.previousWrite = previous;
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class StrongAccessEntry<K, V> extends LocalCache.StrongEntry<K, V>
/*      */     implements LocalCache.ReferenceEntry<K, V>
/*      */   {
/* 1177 */     volatile long accessTime = 9223372036854775807L;
/*      */ 
/* 1189 */     LocalCache.ReferenceEntry<K, V> nextAccess = LocalCache.nullEntry();
/*      */ 
/* 1202 */     LocalCache.ReferenceEntry<K, V> previousAccess = LocalCache.nullEntry();
/*      */ 
/*      */     StrongAccessEntry(K key, int hash, LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 1172 */       super(hash, next);
/*      */     }
/*      */ 
/*      */     public long getAccessTime()
/*      */     {
/* 1181 */       return this.accessTime;
/*      */     }
/*      */ 
/*      */     public void setAccessTime(long time)
/*      */     {
/* 1186 */       this.accessTime = time;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue()
/*      */     {
/* 1194 */       return this.nextAccess;
/*      */     }
/*      */ 
/*      */     public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 1199 */       this.nextAccess = next;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue()
/*      */     {
/* 1207 */       return this.previousAccess;
/*      */     }
/*      */ 
/*      */     public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous)
/*      */     {
/* 1212 */       this.previousAccess = previous;
/*      */     }
/*      */   }
/*      */ 
/*      */   static class StrongEntry<K, V>
/*      */     implements LocalCache.ReferenceEntry<K, V>
/*      */   {
/*      */     final K key;
/*      */     final int hash;
/*      */     final LocalCache.ReferenceEntry<K, V> next;
/* 1146 */     volatile LocalCache.ValueReference<K, V> valueReference = LocalCache.unset();
/*      */ 
/*      */     StrongEntry(K key, int hash, LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 1068 */       this.key = key;
/* 1069 */       this.hash = hash;
/* 1070 */       this.next = next;
/*      */     }
/*      */ 
/*      */     public K getKey()
/*      */     {
/* 1075 */       return this.key;
/*      */     }
/*      */ 
/*      */     public long getAccessTime()
/*      */     {
/* 1082 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setAccessTime(long time)
/*      */     {
/* 1087 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue()
/*      */     {
/* 1092 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 1097 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue()
/*      */     {
/* 1102 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous)
/*      */     {
/* 1107 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public long getWriteTime()
/*      */     {
/* 1114 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setWriteTime(long time)
/*      */     {
/* 1119 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue()
/*      */     {
/* 1124 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/* 1129 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue()
/*      */     {
/* 1134 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous)
/*      */     {
/* 1139 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public LocalCache.ValueReference<K, V> getValueReference()
/*      */     {
/* 1150 */       return this.valueReference;
/*      */     }
/*      */ 
/*      */     public void setValueReference(LocalCache.ValueReference<K, V> valueReference)
/*      */     {
/* 1155 */       this.valueReference = valueReference;
/*      */     }
/*      */ 
/*      */     public int getHash()
/*      */     {
/* 1160 */       return this.hash;
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getNext()
/*      */     {
/* 1165 */       return this.next;
/*      */     }
/*      */   }
/*      */ 
/*      */   static abstract class AbstractReferenceEntry<K, V>
/*      */     implements LocalCache.ReferenceEntry<K, V>
/*      */   {
/*      */     public LocalCache.ValueReference<K, V> getValueReference()
/*      */     {
/*  929 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setValueReference(LocalCache.ValueReference<K, V> valueReference)
/*      */     {
/*  934 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getNext()
/*      */     {
/*  939 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public int getHash()
/*      */     {
/*  944 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public K getKey()
/*      */     {
/*  949 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public long getAccessTime()
/*      */     {
/*  954 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setAccessTime(long time)
/*      */     {
/*  959 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInAccessQueue()
/*      */     {
/*  964 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setNextInAccessQueue(LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/*  969 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getPreviousInAccessQueue()
/*      */     {
/*  974 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<K, V> previous)
/*      */     {
/*  979 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public long getWriteTime()
/*      */     {
/*  984 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setWriteTime(long time)
/*      */     {
/*  989 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getNextInWriteQueue()
/*      */     {
/*  994 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setNextInWriteQueue(LocalCache.ReferenceEntry<K, V> next)
/*      */     {
/*  999 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<K, V> getPreviousInWriteQueue()
/*      */     {
/* 1004 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<K, V> previous)
/*      */     {
/* 1009 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static enum NullEntry
/*      */     implements LocalCache.ReferenceEntry<Object, Object>
/*      */   {
/*  852 */     INSTANCE;
/*      */ 
/*      */     public LocalCache.ValueReference<Object, Object> getValueReference()
/*      */     {
/*  856 */       return null;
/*      */     }
/*      */ 
/*      */     public void setValueReference(LocalCache.ValueReference<Object, Object> valueReference)
/*      */     {
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<Object, Object> getNext() {
/*  864 */       return null;
/*      */     }
/*      */ 
/*      */     public int getHash()
/*      */     {
/*  869 */       return 0;
/*      */     }
/*      */ 
/*      */     public Object getKey()
/*      */     {
/*  874 */       return null;
/*      */     }
/*      */ 
/*      */     public long getAccessTime()
/*      */     {
/*  879 */       return 0L;
/*      */     }
/*      */ 
/*      */     public void setAccessTime(long time)
/*      */     {
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<Object, Object> getNextInAccessQueue() {
/*  887 */       return this;
/*      */     }
/*      */ 
/*      */     public void setNextInAccessQueue(LocalCache.ReferenceEntry<Object, Object> next)
/*      */     {
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<Object, Object> getPreviousInAccessQueue() {
/*  895 */       return this;
/*      */     }
/*      */ 
/*      */     public void setPreviousInAccessQueue(LocalCache.ReferenceEntry<Object, Object> previous)
/*      */     {
/*      */     }
/*      */ 
/*      */     public long getWriteTime() {
/*  903 */       return 0L;
/*      */     }
/*      */ 
/*      */     public void setWriteTime(long time)
/*      */     {
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<Object, Object> getNextInWriteQueue() {
/*  911 */       return this;
/*      */     }
/*      */ 
/*      */     public void setNextInWriteQueue(LocalCache.ReferenceEntry<Object, Object> next)
/*      */     {
/*      */     }
/*      */ 
/*      */     public LocalCache.ReferenceEntry<Object, Object> getPreviousInWriteQueue() {
/*  919 */       return this;
/*      */     }
/*      */ 
/*      */     public void setPreviousInWriteQueue(LocalCache.ReferenceEntry<Object, Object> previous)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   static abstract interface ReferenceEntry<K, V>
/*      */   {
/*      */     public abstract LocalCache.ValueReference<K, V> getValueReference();
/*      */ 
/*      */     public abstract void setValueReference(LocalCache.ValueReference<K, V> paramValueReference);
/*      */ 
/*      */     public abstract ReferenceEntry<K, V> getNext();
/*      */ 
/*      */     public abstract int getHash();
/*      */ 
/*      */     public abstract K getKey();
/*      */ 
/*      */     public abstract long getAccessTime();
/*      */ 
/*      */     public abstract void setAccessTime(long paramLong);
/*      */ 
/*      */     public abstract ReferenceEntry<K, V> getNextInAccessQueue();
/*      */ 
/*      */     public abstract void setNextInAccessQueue(ReferenceEntry<K, V> paramReferenceEntry);
/*      */ 
/*      */     public abstract ReferenceEntry<K, V> getPreviousInAccessQueue();
/*      */ 
/*      */     public abstract void setPreviousInAccessQueue(ReferenceEntry<K, V> paramReferenceEntry);
/*      */ 
/*      */     public abstract long getWriteTime();
/*      */ 
/*      */     public abstract void setWriteTime(long paramLong);
/*      */ 
/*      */     public abstract ReferenceEntry<K, V> getNextInWriteQueue();
/*      */ 
/*      */     public abstract void setNextInWriteQueue(ReferenceEntry<K, V> paramReferenceEntry);
/*      */ 
/*      */     public abstract ReferenceEntry<K, V> getPreviousInWriteQueue();
/*      */ 
/*      */     public abstract void setPreviousInWriteQueue(ReferenceEntry<K, V> paramReferenceEntry);
/*      */   }
/*      */ 
/*      */   static abstract interface ValueReference<K, V>
/*      */   {
/*      */     public abstract V get();
/*      */ 
/*      */     public abstract V waitForValue()
/*      */       throws ExecutionException;
/*      */ 
/*      */     public abstract int getWeight();
/*      */ 
/*      */     public abstract LocalCache.ReferenceEntry<K, V> getEntry();
/*      */ 
/*      */     public abstract ValueReference<K, V> copyFor(ReferenceQueue<V> paramReferenceQueue, V paramV, LocalCache.ReferenceEntry<K, V> paramReferenceEntry);
/*      */ 
/*      */     public abstract void notifyNewValue(V paramV);
/*      */ 
/*      */     public abstract boolean isLoading();
/*      */ 
/*      */     public abstract boolean isActive();
/*      */   }
/*      */ 
/*      */   static abstract enum EntryFactory
/*      */   {
/*  443 */     STRONG, 
/*      */ 
/*  450 */     STRONG_ACCESS, 
/*      */ 
/*  465 */     STRONG_WRITE, 
/*      */ 
/*  480 */     STRONG_ACCESS_WRITE, 
/*      */ 
/*  497 */     WEAK, 
/*      */ 
/*  504 */     WEAK_ACCESS, 
/*      */ 
/*  519 */     WEAK_WRITE, 
/*      */ 
/*  534 */     WEAK_ACCESS_WRITE;
/*      */ 
/*      */     static final EntryFactory[] factories;
/*      */ 
/*      */     static EntryFactory getFactory(LocalCache.Strength keyStrength, boolean usesAccessQueue, boolean usesWriteQueue)
/*      */     {
/*  568 */       int flags = (keyStrength == LocalCache.Strength.WEAK ? 4 : 0) | (usesAccessQueue ? 1 : 0) | (usesWriteQueue ? 2 : 0);
/*      */ 
/*  571 */       return factories[flags];
/*      */     }
/*      */ 
/*      */     abstract <K, V> LocalCache.ReferenceEntry<K, V> newEntry(LocalCache.Segment<K, V> paramSegment, K paramK, int paramInt, LocalCache.ReferenceEntry<K, V> paramReferenceEntry);
/*      */ 
/*      */     <K, V> LocalCache.ReferenceEntry<K, V> copyEntry(LocalCache.Segment<K, V> segment, LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newNext)
/*      */     {
/*  594 */       return newEntry(segment, original.getKey(), original.getHash(), newNext);
/*      */     }
/*      */ 
/*      */     <K, V> void copyAccessEntry(LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newEntry)
/*      */     {
/*  601 */       newEntry.setAccessTime(original.getAccessTime());
/*      */ 
/*  603 */       LocalCache.connectAccessOrder(original.getPreviousInAccessQueue(), newEntry);
/*  604 */       LocalCache.connectAccessOrder(newEntry, original.getNextInAccessQueue());
/*      */ 
/*  606 */       LocalCache.nullifyAccessOrder(original);
/*      */     }
/*      */ 
/*      */     <K, V> void copyWriteEntry(LocalCache.ReferenceEntry<K, V> original, LocalCache.ReferenceEntry<K, V> newEntry)
/*      */     {
/*  613 */       newEntry.setWriteTime(original.getWriteTime());
/*      */ 
/*  615 */       LocalCache.connectWriteOrder(original.getPreviousInWriteQueue(), newEntry);
/*  616 */       LocalCache.connectWriteOrder(newEntry, original.getNextInWriteQueue());
/*      */ 
/*  618 */       LocalCache.nullifyWriteOrder(original);
/*      */     }
/*      */ 
/*      */     static
/*      */     {
/*  561 */       factories = new EntryFactory[] { STRONG, STRONG_ACCESS, STRONG_WRITE, STRONG_ACCESS_WRITE, WEAK, WEAK_ACCESS, WEAK_WRITE, WEAK_ACCESS_WRITE };
/*      */     }
/*      */   }
/*      */ 
/*      */   static abstract enum Strength
/*      */   {
/*  378 */     STRONG, 
/*      */ 
/*  393 */     SOFT, 
/*      */ 
/*  409 */     WEAK;
/*      */ 
/*      */     abstract <K, V> LocalCache.ValueReference<K, V> referenceValue(LocalCache.Segment<K, V> paramSegment, LocalCache.ReferenceEntry<K, V> paramReferenceEntry, V paramV, int paramInt);
/*      */ 
/*      */     abstract Equivalence<Object> defaultEquivalence();
/*      */   }
/*      */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.cache.LocalCache
 * JD-Core Version:    0.6.0
 */