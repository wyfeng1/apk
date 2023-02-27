/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.base.Equivalence;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.base.Ticker;
/*      */ import com.google.common.primitives.Ints;
/*      */ import java.io.Serializable;
/*      */ import java.lang.ref.Reference;
/*      */ import java.lang.ref.ReferenceQueue;
/*      */ import java.lang.ref.SoftReference;
/*      */ import java.lang.ref.WeakReference;
/*      */ import java.util.AbstractCollection;
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
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.concurrent.atomic.AtomicReferenceArray;
/*      */ import java.util.concurrent.locks.ReentrantLock;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ 
/*      */ class MapMakerInternalMap<K, V> extends AbstractMap<K, V>
/*      */   implements Serializable, ConcurrentMap<K, V>
/*      */ {
/*  135 */   private static final Logger logger = Logger.getLogger(MapMakerInternalMap.class.getName());
/*      */   final transient int segmentMask;
/*      */   final transient int segmentShift;
/*      */   final transient Segment<K, V>[] segments;
/*      */   final int concurrencyLevel;
/*      */   final Equivalence<Object> keyEquivalence;
/*      */   final Equivalence<Object> valueEquivalence;
/*      */   final Strength keyStrength;
/*      */   final Strength valueStrength;
/*      */   final int maximumSize;
/*      */   final long expireAfterAccessNanos;
/*      */   final long expireAfterWriteNanos;
/*      */   final Queue<MapMaker.RemovalNotification<K, V>> removalNotificationQueue;
/*      */   final MapMaker.RemovalListener<K, V> removalListener;
/*      */   final transient EntryFactory entryFactory;
/*      */   final Ticker ticker;
/*  578 */   static final ValueReference<Object, Object> UNSET = new ValueReference()
/*      */   {
/*      */     public Object get() {
/*  581 */       return null;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<Object, Object> getEntry()
/*      */     {
/*  586 */       return null;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ValueReference<Object, Object> copyFor(ReferenceQueue<Object> queue, Object value, MapMakerInternalMap.ReferenceEntry<Object, Object> entry)
/*      */     {
/*  592 */       return this;
/*      */     }
/*      */ 
/*      */     public boolean isComputingReference()
/*      */     {
/*  597 */       return false;
/*      */     }
/*      */ 
/*      */     public void clear(MapMakerInternalMap.ValueReference<Object, Object> newValue)
/*      */     {
/*      */     }
/*  578 */   };
/*      */ 
/*  868 */   static final Queue<? extends Object> DISCARDING_QUEUE = new AbstractQueue()
/*      */   {
/*      */     public boolean offer(Object o) {
/*  871 */       return true;
/*      */     }
/*      */ 
/*      */     public Object peek()
/*      */     {
/*  876 */       return null;
/*      */     }
/*      */ 
/*      */     public Object poll()
/*      */     {
/*  881 */       return null;
/*      */     }
/*      */ 
/*      */     public int size()
/*      */     {
/*  886 */       return 0;
/*      */     }
/*      */ 
/*      */     public Iterator<Object> iterator()
/*      */     {
/*  891 */       return Iterators.emptyIterator();
/*      */     }
/*  868 */   };
/*      */   transient Set<K> keySet;
/*      */   transient Collection<V> values;
/*      */   transient Set<Map.Entry<K, V>> entrySet;
/*      */ 
/*      */   MapMakerInternalMap(MapMaker builder)
/*      */   {
/*  196 */     this.concurrencyLevel = Math.min(builder.getConcurrencyLevel(), 65536);
/*      */ 
/*  198 */     this.keyStrength = builder.getKeyStrength();
/*  199 */     this.valueStrength = builder.getValueStrength();
/*      */ 
/*  201 */     this.keyEquivalence = builder.getKeyEquivalence();
/*  202 */     this.valueEquivalence = this.valueStrength.defaultEquivalence();
/*      */ 
/*  204 */     this.maximumSize = builder.maximumSize;
/*  205 */     this.expireAfterAccessNanos = builder.getExpireAfterAccessNanos();
/*  206 */     this.expireAfterWriteNanos = builder.getExpireAfterWriteNanos();
/*      */ 
/*  208 */     this.entryFactory = EntryFactory.getFactory(this.keyStrength, expires(), evictsBySize());
/*  209 */     this.ticker = builder.getTicker();
/*      */ 
/*  211 */     this.removalListener = builder.getRemovalListener();
/*  212 */     this.removalNotificationQueue = (this.removalListener == GenericMapMaker.NullListener.INSTANCE ? discardingQueue() : new ConcurrentLinkedQueue());
/*      */ 
/*  216 */     int initialCapacity = Math.min(builder.getInitialCapacity(), 1073741824);
/*  217 */     if (evictsBySize()) {
/*  218 */       initialCapacity = Math.min(initialCapacity, this.maximumSize);
/*      */     }
/*      */ 
/*  224 */     int segmentShift = 0;
/*  225 */     int segmentCount = 1;
/*      */ 
/*  227 */     while ((segmentCount < this.concurrencyLevel) && ((!evictsBySize()) || (segmentCount * 2 <= this.maximumSize))) {
/*  228 */       segmentShift++;
/*  229 */       segmentCount <<= 1;
/*      */     }
/*  231 */     this.segmentShift = (32 - segmentShift);
/*  232 */     this.segmentMask = (segmentCount - 1);
/*      */ 
/*  234 */     this.segments = newSegmentArray(segmentCount);
/*      */ 
/*  236 */     int segmentCapacity = initialCapacity / segmentCount;
/*  237 */     if (segmentCapacity * segmentCount < initialCapacity) {
/*  238 */       segmentCapacity++;
/*      */     }
/*      */ 
/*  241 */     int segmentSize = 1;
/*  242 */     while (segmentSize < segmentCapacity) {
/*  243 */       segmentSize <<= 1;
/*      */     }
/*      */ 
/*  246 */     if (evictsBySize())
/*      */     {
/*  248 */       int maximumSegmentSize = this.maximumSize / segmentCount + 1;
/*  249 */       int remainder = this.maximumSize % segmentCount;
/*  250 */       for (int i = 0; i < this.segments.length; i++) {
/*  251 */         if (i == remainder) {
/*  252 */           maximumSegmentSize--;
/*      */         }
/*  254 */         this.segments[i] = createSegment(segmentSize, maximumSegmentSize);
/*      */       }
/*      */     }
/*      */     else {
/*  258 */       for (int i = 0; i < this.segments.length; i++)
/*  259 */         this.segments[i] = createSegment(segmentSize, -1);
/*      */     }
/*      */   }
/*      */ 
/*      */   boolean evictsBySize()
/*      */   {
/*  266 */     return this.maximumSize != -1;
/*      */   }
/*      */ 
/*      */   boolean expires() {
/*  270 */     return (expiresAfterWrite()) || (expiresAfterAccess());
/*      */   }
/*      */ 
/*      */   boolean expiresAfterWrite() {
/*  274 */     return this.expireAfterWriteNanos > 0L;
/*      */   }
/*      */ 
/*      */   boolean expiresAfterAccess() {
/*  278 */     return this.expireAfterAccessNanos > 0L;
/*      */   }
/*      */ 
/*      */   boolean usesKeyReferences() {
/*  282 */     return this.keyStrength != Strength.STRONG;
/*      */   }
/*      */ 
/*      */   boolean usesValueReferences() {
/*  286 */     return this.valueStrength != Strength.STRONG;
/*      */   }
/*      */ 
/*      */   static <K, V> ValueReference<K, V> unset()
/*      */   {
/*  614 */     return UNSET;
/*      */   }
/*      */ 
/*      */   static <K, V> ReferenceEntry<K, V> nullEntry()
/*      */   {
/*  865 */     return NullEntry.INSTANCE;
/*      */   }
/*      */ 
/*      */   static <E> Queue<E> discardingQueue()
/*      */   {
/*  900 */     return DISCARDING_QUEUE;
/*      */   }
/*      */ 
/*      */   static int rehash(int h)
/*      */   {
/* 1813 */     h += (h << 15 ^ 0xFFFFCD7D);
/* 1814 */     h ^= h >>> 10;
/* 1815 */     h += (h << 3);
/* 1816 */     h ^= h >>> 6;
/* 1817 */     h += (h << 2) + (h << 14);
/* 1818 */     return h ^ h >>> 16;
/*      */   }
/*      */ 
/*      */   int hash(Object key)
/*      */   {
/* 1851 */     int h = this.keyEquivalence.hash(key);
/* 1852 */     return rehash(h);
/*      */   }
/*      */ 
/*      */   void reclaimValue(ValueReference<K, V> valueReference) {
/* 1856 */     ReferenceEntry entry = valueReference.getEntry();
/* 1857 */     int hash = entry.getHash();
/* 1858 */     segmentFor(hash).reclaimValue(entry.getKey(), hash, valueReference);
/*      */   }
/*      */ 
/*      */   void reclaimKey(ReferenceEntry<K, V> entry) {
/* 1862 */     int hash = entry.getHash();
/* 1863 */     segmentFor(hash).reclaimKey(entry, hash);
/*      */   }
/*      */ 
/*      */   Segment<K, V> segmentFor(int hash)
/*      */   {
/* 1883 */     return this.segments[(hash >>> this.segmentShift & this.segmentMask)];
/*      */   }
/*      */ 
/*      */   Segment<K, V> createSegment(int initialCapacity, int maxSegmentSize) {
/* 1887 */     return new Segment(this, initialCapacity, maxSegmentSize);
/*      */   }
/*      */ 
/*      */   V getLiveValue(ReferenceEntry<K, V> entry)
/*      */   {
/* 1896 */     if (entry.getKey() == null) {
/* 1897 */       return null;
/*      */     }
/* 1899 */     Object value = entry.getValueReference().get();
/* 1900 */     if (value == null) {
/* 1901 */       return null;
/*      */     }
/*      */ 
/* 1904 */     if ((expires()) && (isExpired(entry))) {
/* 1905 */       return null;
/*      */     }
/* 1907 */     return value;
/*      */   }
/*      */ 
/*      */   boolean isExpired(ReferenceEntry<K, V> entry)
/*      */   {
/* 1916 */     return isExpired(entry, this.ticker.read());
/*      */   }
/*      */ 
/*      */   boolean isExpired(ReferenceEntry<K, V> entry, long now)
/*      */   {
/* 1924 */     return now - entry.getExpirationTime() > 0L;
/*      */   }
/*      */ 
/*      */   static <K, V> void connectExpirables(ReferenceEntry<K, V> previous, ReferenceEntry<K, V> next)
/*      */   {
/* 1929 */     previous.setNextExpirable(next);
/* 1930 */     next.setPreviousExpirable(previous);
/*      */   }
/*      */ 
/*      */   static <K, V> void nullifyExpirable(ReferenceEntry<K, V> nulled)
/*      */   {
/* 1935 */     ReferenceEntry nullEntry = nullEntry();
/* 1936 */     nulled.setNextExpirable(nullEntry);
/* 1937 */     nulled.setPreviousExpirable(nullEntry);
/*      */   }
/*      */ 
/*      */   void processPendingNotifications()
/*      */   {
/*      */     MapMaker.RemovalNotification notification;
/* 1949 */     while ((notification = (MapMaker.RemovalNotification)this.removalNotificationQueue.poll()) != null)
/*      */       try {
/* 1951 */         this.removalListener.onRemoval(notification);
/*      */       } catch (Exception e) {
/* 1953 */         logger.log(Level.WARNING, "Exception thrown by removal listener", e);
/*      */       }
/*      */   }
/*      */ 
/*      */   static <K, V> void connectEvictables(ReferenceEntry<K, V> previous, ReferenceEntry<K, V> next)
/*      */   {
/* 1961 */     previous.setNextEvictable(next);
/* 1962 */     next.setPreviousEvictable(previous);
/*      */   }
/*      */ 
/*      */   static <K, V> void nullifyEvictable(ReferenceEntry<K, V> nulled)
/*      */   {
/* 1967 */     ReferenceEntry nullEntry = nullEntry();
/* 1968 */     nulled.setNextEvictable(nullEntry);
/* 1969 */     nulled.setPreviousEvictable(nullEntry);
/*      */   }
/*      */ 
/*      */   final Segment<K, V>[] newSegmentArray(int ssize)
/*      */   {
/* 1974 */     return new Segment[ssize];
/*      */   }
/*      */ 
/*      */   public boolean isEmpty()
/*      */   {
/* 3395 */     long sum = 0L;
/* 3396 */     Segment[] segments = this.segments;
/* 3397 */     for (int i = 0; i < segments.length; i++) {
/* 3398 */       if (segments[i].count != 0) {
/* 3399 */         return false;
/*      */       }
/* 3401 */       sum += segments[i].modCount;
/*      */     }
/*      */ 
/* 3404 */     if (sum != 0L) {
/* 3405 */       for (int i = 0; i < segments.length; i++) {
/* 3406 */         if (segments[i].count != 0) {
/* 3407 */           return false;
/*      */         }
/* 3409 */         sum -= segments[i].modCount;
/*      */       }
/* 3411 */       if (sum != 0L) {
/* 3412 */         return false;
/*      */       }
/*      */     }
/* 3415 */     return true;
/*      */   }
/*      */ 
/*      */   public int size()
/*      */   {
/* 3420 */     Segment[] segments = this.segments;
/* 3421 */     long sum = 0L;
/* 3422 */     for (int i = 0; i < segments.length; i++) {
/* 3423 */       sum += segments[i].count;
/*      */     }
/* 3425 */     return Ints.saturatedCast(sum);
/*      */   }
/*      */ 
/*      */   public V get(Object key)
/*      */   {
/* 3430 */     if (key == null) {
/* 3431 */       return null;
/*      */     }
/* 3433 */     int hash = hash(key);
/* 3434 */     return segmentFor(hash).get(key, hash);
/*      */   }
/*      */ 
/*      */   public boolean containsKey(Object key)
/*      */   {
/* 3451 */     if (key == null) {
/* 3452 */       return false;
/*      */     }
/* 3454 */     int hash = hash(key);
/* 3455 */     return segmentFor(hash).containsKey(key, hash);
/*      */   }
/*      */ 
/*      */   public boolean containsValue(Object value)
/*      */   {
/* 3460 */     if (value == null) {
/* 3461 */       return false;
/*      */     }
/*      */ 
/* 3469 */     Segment[] segments = this.segments;
/* 3470 */     long last = -1L;
/* 3471 */     for (int i = 0; i < 3; i++) {
/* 3472 */       long sum = 0L;
/* 3473 */       for (Segment segment : segments)
/*      */       {
/* 3476 */         int c = segment.count;
/*      */ 
/* 3478 */         AtomicReferenceArray table = segment.table;
/* 3479 */         for (int j = 0; j < table.length(); j++) {
/* 3480 */           for (ReferenceEntry e = (ReferenceEntry)table.get(j); e != null; e = e.getNext()) {
/* 3481 */             Object v = segment.getLiveValue(e);
/* 3482 */             if ((v != null) && (this.valueEquivalence.equivalent(value, v))) {
/* 3483 */               return true;
/*      */             }
/*      */           }
/*      */         }
/* 3487 */         sum += segment.modCount;
/*      */       }
/* 3489 */       if (sum == last) {
/*      */         break;
/*      */       }
/* 3492 */       last = sum;
/*      */     }
/* 3494 */     return false;
/*      */   }
/*      */ 
/*      */   public V put(K key, V value)
/*      */   {
/* 3499 */     Preconditions.checkNotNull(key);
/* 3500 */     Preconditions.checkNotNull(value);
/* 3501 */     int hash = hash(key);
/* 3502 */     return segmentFor(hash).put(key, hash, value, false);
/*      */   }
/*      */ 
/*      */   public V putIfAbsent(K key, V value)
/*      */   {
/* 3507 */     Preconditions.checkNotNull(key);
/* 3508 */     Preconditions.checkNotNull(value);
/* 3509 */     int hash = hash(key);
/* 3510 */     return segmentFor(hash).put(key, hash, value, true);
/*      */   }
/*      */ 
/*      */   public void putAll(Map<? extends K, ? extends V> m)
/*      */   {
/* 3515 */     for (Map.Entry e : m.entrySet())
/* 3516 */       put(e.getKey(), e.getValue());
/*      */   }
/*      */ 
/*      */   public V remove(Object key)
/*      */   {
/* 3522 */     if (key == null) {
/* 3523 */       return null;
/*      */     }
/* 3525 */     int hash = hash(key);
/* 3526 */     return segmentFor(hash).remove(key, hash);
/*      */   }
/*      */ 
/*      */   public boolean remove(Object key, Object value)
/*      */   {
/* 3531 */     if ((key == null) || (value == null)) {
/* 3532 */       return false;
/*      */     }
/* 3534 */     int hash = hash(key);
/* 3535 */     return segmentFor(hash).remove(key, hash, value);
/*      */   }
/*      */ 
/*      */   public boolean replace(K key, V oldValue, V newValue)
/*      */   {
/* 3540 */     Preconditions.checkNotNull(key);
/* 3541 */     Preconditions.checkNotNull(newValue);
/* 3542 */     if (oldValue == null) {
/* 3543 */       return false;
/*      */     }
/* 3545 */     int hash = hash(key);
/* 3546 */     return segmentFor(hash).replace(key, hash, oldValue, newValue);
/*      */   }
/*      */ 
/*      */   public V replace(K key, V value)
/*      */   {
/* 3551 */     Preconditions.checkNotNull(key);
/* 3552 */     Preconditions.checkNotNull(value);
/* 3553 */     int hash = hash(key);
/* 3554 */     return segmentFor(hash).replace(key, hash, value);
/*      */   }
/*      */ 
/*      */   public void clear()
/*      */   {
/* 3559 */     for (Segment segment : this.segments)
/* 3560 */       segment.clear();
/*      */   }
/*      */ 
/*      */   public Set<K> keySet()
/*      */   {
/* 3568 */     Set ks = this.keySet;
/* 3569 */     return this.keySet = new KeySet();
/*      */   }
/*      */ 
/*      */   public Collection<V> values()
/*      */   {
/* 3576 */     Collection vs = this.values;
/* 3577 */     return this.values = new Values();
/*      */   }
/*      */ 
/*      */   public Set<Map.Entry<K, V>> entrySet()
/*      */   {
/* 3584 */     Set es = this.entrySet;
/* 3585 */     return this.entrySet = new EntrySet();
/*      */   }
/*      */ 
/*      */   final class EntrySet extends AbstractSet<Map.Entry<K, V>>
/*      */   {
/*      */     EntrySet()
/*      */     {
/*      */     }
/*      */ 
/*      */     public Iterator<Map.Entry<K, V>> iterator()
/*      */     {
/* 3835 */       return new MapMakerInternalMap.EntryIterator(MapMakerInternalMap.this);
/*      */     }
/*      */ 
/*      */     public boolean contains(Object o)
/*      */     {
/* 3840 */       if (!(o instanceof Map.Entry)) {
/* 3841 */         return false;
/*      */       }
/* 3843 */       Map.Entry e = (Map.Entry)o;
/* 3844 */       Object key = e.getKey();
/* 3845 */       if (key == null) {
/* 3846 */         return false;
/*      */       }
/* 3848 */       Object v = MapMakerInternalMap.this.get(key);
/*      */ 
/* 3850 */       return (v != null) && (MapMakerInternalMap.this.valueEquivalence.equivalent(e.getValue(), v));
/*      */     }
/*      */ 
/*      */     public boolean remove(Object o)
/*      */     {
/* 3855 */       if (!(o instanceof Map.Entry)) {
/* 3856 */         return false;
/*      */       }
/* 3858 */       Map.Entry e = (Map.Entry)o;
/* 3859 */       Object key = e.getKey();
/* 3860 */       return (key != null) && (MapMakerInternalMap.this.remove(key, e.getValue()));
/*      */     }
/*      */ 
/*      */     public int size()
/*      */     {
/* 3865 */       return MapMakerInternalMap.this.size();
/*      */     }
/*      */ 
/*      */     public boolean isEmpty()
/*      */     {
/* 3870 */       return MapMakerInternalMap.this.isEmpty();
/*      */     }
/*      */ 
/*      */     public void clear()
/*      */     {
/* 3875 */       MapMakerInternalMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   final class Values extends AbstractCollection<V>
/*      */   {
/*      */     Values()
/*      */     {
/*      */     }
/*      */ 
/*      */     public Iterator<V> iterator()
/*      */     {
/* 3807 */       return new MapMakerInternalMap.ValueIterator(MapMakerInternalMap.this);
/*      */     }
/*      */ 
/*      */     public int size()
/*      */     {
/* 3812 */       return MapMakerInternalMap.this.size();
/*      */     }
/*      */ 
/*      */     public boolean isEmpty()
/*      */     {
/* 3817 */       return MapMakerInternalMap.this.isEmpty();
/*      */     }
/*      */ 
/*      */     public boolean contains(Object o)
/*      */     {
/* 3822 */       return MapMakerInternalMap.this.containsValue(o);
/*      */     }
/*      */ 
/*      */     public void clear()
/*      */     {
/* 3827 */       MapMakerInternalMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   final class KeySet extends AbstractSet<K>
/*      */   {
/*      */     KeySet()
/*      */     {
/*      */     }
/*      */ 
/*      */     public Iterator<K> iterator()
/*      */     {
/* 3774 */       return new MapMakerInternalMap.KeyIterator(MapMakerInternalMap.this);
/*      */     }
/*      */ 
/*      */     public int size()
/*      */     {
/* 3779 */       return MapMakerInternalMap.this.size();
/*      */     }
/*      */ 
/*      */     public boolean isEmpty()
/*      */     {
/* 3784 */       return MapMakerInternalMap.this.isEmpty();
/*      */     }
/*      */ 
/*      */     public boolean contains(Object o)
/*      */     {
/* 3789 */       return MapMakerInternalMap.this.containsKey(o);
/*      */     }
/*      */ 
/*      */     public boolean remove(Object o)
/*      */     {
/* 3794 */       return MapMakerInternalMap.this.remove(o) != null;
/*      */     }
/*      */ 
/*      */     public void clear()
/*      */     {
/* 3799 */       MapMakerInternalMap.this.clear();
/*      */     }
/*      */   }
/*      */ 
/*      */   final class EntryIterator extends MapMakerInternalMap<K, V>.HashIterator<Map.Entry<K, V>>
/*      */   {
/*      */     EntryIterator()
/*      */     {
/* 3762 */       super();
/*      */     }
/*      */ 
/*      */     public Map.Entry<K, V> next() {
/* 3766 */       return nextEntry();
/*      */     }
/*      */   }
/*      */ 
/*      */   final class WriteThroughEntry extends AbstractMapEntry<K, V>
/*      */   {
/*      */     final K key;
/*      */     V value;
/*      */ 
/*      */     WriteThroughEntry(V key)
/*      */     {
/* 3724 */       this.key = key;
/* 3725 */       this.value = value;
/*      */     }
/*      */ 
/*      */     public K getKey()
/*      */     {
/* 3730 */       return this.key;
/*      */     }
/*      */ 
/*      */     public V getValue()
/*      */     {
/* 3735 */       return this.value;
/*      */     }
/*      */ 
/*      */     public boolean equals(Object object)
/*      */     {
/* 3741 */       if ((object instanceof Map.Entry)) {
/* 3742 */         Map.Entry that = (Map.Entry)object;
/* 3743 */         return (this.key.equals(that.getKey())) && (this.value.equals(that.getValue()));
/*      */       }
/* 3745 */       return false;
/*      */     }
/*      */ 
/*      */     public int hashCode()
/*      */     {
/* 3751 */       return this.key.hashCode() ^ this.value.hashCode();
/*      */     }
/*      */ 
/*      */     public V setValue(V newValue)
/*      */     {
/* 3756 */       Object oldValue = MapMakerInternalMap.this.put(this.key, newValue);
/* 3757 */       this.value = newValue;
/* 3758 */       return oldValue;
/*      */     }
/*      */   }
/*      */ 
/*      */   final class ValueIterator extends MapMakerInternalMap<K, V>.HashIterator<V>
/*      */   {
/*      */     ValueIterator()
/*      */     {
/* 3707 */       super();
/*      */     }
/*      */ 
/*      */     public V next() {
/* 3711 */       return nextEntry().getValue();
/*      */     }
/*      */   }
/*      */ 
/*      */   final class KeyIterator extends MapMakerInternalMap<K, V>.HashIterator<K>
/*      */   {
/*      */     KeyIterator()
/*      */     {
/* 3699 */       super();
/*      */     }
/*      */ 
/*      */     public K next() {
/* 3703 */       return nextEntry().getKey();
/*      */     }
/*      */   }
/*      */ 
/*      */   abstract class HashIterator<E>
/*      */     implements Iterator<E>
/*      */   {
/*      */     int nextSegmentIndex;
/*      */     int nextTableIndex;
/*      */     MapMakerInternalMap.Segment<K, V> currentSegment;
/*      */     AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> currentTable;
/*      */     MapMakerInternalMap.ReferenceEntry<K, V> nextEntry;
/*      */     MapMakerInternalMap<K, V>.WriteThroughEntry nextExternal;
/*      */     MapMakerInternalMap<K, V>.WriteThroughEntry lastReturned;
/*      */ 
/*      */     HashIterator()
/*      */     {
/* 3601 */       this.nextSegmentIndex = (MapMakerInternalMap.this.segments.length - 1);
/* 3602 */       this.nextTableIndex = -1;
/* 3603 */       advance();
/*      */     }
/*      */ 
/*      */     final void advance()
/*      */     {
/* 3609 */       this.nextExternal = null;
/*      */ 
/* 3611 */       if (nextInChain()) {
/* 3612 */         return;
/*      */       }
/*      */ 
/* 3615 */       if (nextInTable()) {
/* 3616 */         return;
/*      */       }
/*      */ 
/* 3619 */       while (this.nextSegmentIndex >= 0) {
/* 3620 */         this.currentSegment = MapMakerInternalMap.this.segments[(this.nextSegmentIndex--)];
/* 3621 */         if (this.currentSegment.count != 0) {
/* 3622 */           this.currentTable = this.currentSegment.table;
/* 3623 */           this.nextTableIndex = (this.currentTable.length() - 1);
/* 3624 */           if (nextInTable())
/* 3625 */             return;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     boolean nextInChain()
/*      */     {
/* 3635 */       if (this.nextEntry != null) {
/* 3636 */         for (this.nextEntry = this.nextEntry.getNext(); this.nextEntry != null; this.nextEntry = this.nextEntry.getNext()) {
/* 3637 */           if (advanceTo(this.nextEntry)) {
/* 3638 */             return true;
/*      */           }
/*      */         }
/*      */       }
/* 3642 */       return false;
/*      */     }
/*      */ 
/*      */     boolean nextInTable()
/*      */     {
/* 3649 */       while (this.nextTableIndex >= 0) {
/* 3650 */         if (((this.nextEntry = (MapMakerInternalMap.ReferenceEntry)this.currentTable.get(this.nextTableIndex--)) != null) && (
/* 3651 */           (advanceTo(this.nextEntry)) || (nextInChain()))) {
/* 3652 */           return true;
/*      */         }
/*      */       }
/*      */ 
/* 3656 */       return false;
/*      */     }
/*      */ 
/*      */     boolean advanceTo(MapMakerInternalMap.ReferenceEntry<K, V> entry)
/*      */     {
/*      */       try
/*      */       {
/* 3665 */         Object key = entry.getKey();
/* 3666 */         Object value = MapMakerInternalMap.this.getLiveValue(entry);
/* 3667 */         if (value != null) {
/* 3668 */           this.nextExternal = new MapMakerInternalMap.WriteThroughEntry(MapMakerInternalMap.this, key, value);
/* 3669 */           i = 1;
/*      */           return i;
/*      */         }
/* 3672 */         int i = 0;
/*      */         return i; } finally { this.currentSegment.postReadCleanup(); } throw localObject1;
/*      */     }
/*      */ 
/*      */     public boolean hasNext()
/*      */     {
/* 3680 */       return this.nextExternal != null;
/*      */     }
/*      */ 
/*      */     MapMakerInternalMap<K, V>.WriteThroughEntry nextEntry() {
/* 3684 */       if (this.nextExternal == null) {
/* 3685 */         throw new NoSuchElementException();
/*      */       }
/* 3687 */       this.lastReturned = this.nextExternal;
/* 3688 */       advance();
/* 3689 */       return this.lastReturned;
/*      */     }
/*      */ 
/*      */     public void remove() {
/* 3693 */       Preconditions.checkState(this.lastReturned != null);
/* 3694 */       MapMakerInternalMap.this.remove(this.lastReturned.getKey());
/* 3695 */       this.lastReturned = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class ExpirationQueue<K, V> extends AbstractQueue<MapMakerInternalMap.ReferenceEntry<K, V>>
/*      */   {
/* 3240 */     final MapMakerInternalMap.ReferenceEntry<K, V> head = new MapMakerInternalMap.AbstractReferenceEntry()
/*      */     {
/* 3250 */       MapMakerInternalMap.ReferenceEntry<K, V> nextExpirable = this;
/*      */ 
/* 3262 */       MapMakerInternalMap.ReferenceEntry<K, V> previousExpirable = this;
/*      */ 
/*      */       public long getExpirationTime()
/*      */       {
/* 3244 */         return 9223372036854775807L;
/*      */       }
/*      */ 
/*      */       public void setExpirationTime(long time)
/*      */       {
/*      */       }
/*      */ 
/*      */       public MapMakerInternalMap.ReferenceEntry<K, V> getNextExpirable()
/*      */       {
/* 3254 */         return this.nextExpirable;
/*      */       }
/*      */ 
/*      */       public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */       {
/* 3259 */         this.nextExpirable = next;
/*      */       }
/*      */ 
/*      */       public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousExpirable()
/*      */       {
/* 3266 */         return this.previousExpirable;
/*      */       }
/*      */ 
/*      */       public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<K, V> previous)
/*      */       {
/* 3271 */         this.previousExpirable = previous;
/*      */       }
/* 3240 */     };
/*      */ 
/*      */     public boolean offer(MapMakerInternalMap.ReferenceEntry<K, V> entry)
/*      */     {
/* 3280 */       MapMakerInternalMap.connectExpirables(entry.getPreviousExpirable(), entry.getNextExpirable());
/*      */ 
/* 3283 */       MapMakerInternalMap.connectExpirables(this.head.getPreviousExpirable(), entry);
/* 3284 */       MapMakerInternalMap.connectExpirables(entry, this.head);
/*      */ 
/* 3286 */       return true;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> peek()
/*      */     {
/* 3291 */       MapMakerInternalMap.ReferenceEntry next = this.head.getNextExpirable();
/* 3292 */       return next == this.head ? null : next;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> poll()
/*      */     {
/* 3297 */       MapMakerInternalMap.ReferenceEntry next = this.head.getNextExpirable();
/* 3298 */       if (next == this.head) {
/* 3299 */         return null;
/*      */       }
/*      */ 
/* 3302 */       remove(next);
/* 3303 */       return next;
/*      */     }
/*      */ 
/*      */     public boolean remove(Object o)
/*      */     {
/* 3309 */       MapMakerInternalMap.ReferenceEntry e = (MapMakerInternalMap.ReferenceEntry)o;
/* 3310 */       MapMakerInternalMap.ReferenceEntry previous = e.getPreviousExpirable();
/* 3311 */       MapMakerInternalMap.ReferenceEntry next = e.getNextExpirable();
/* 3312 */       MapMakerInternalMap.connectExpirables(previous, next);
/* 3313 */       MapMakerInternalMap.nullifyExpirable(e);
/*      */ 
/* 3315 */       return next != MapMakerInternalMap.NullEntry.INSTANCE;
/*      */     }
/*      */ 
/*      */     public boolean contains(Object o)
/*      */     {
/* 3321 */       MapMakerInternalMap.ReferenceEntry e = (MapMakerInternalMap.ReferenceEntry)o;
/* 3322 */       return e.getNextExpirable() != MapMakerInternalMap.NullEntry.INSTANCE;
/*      */     }
/*      */ 
/*      */     public boolean isEmpty()
/*      */     {
/* 3327 */       return this.head.getNextExpirable() == this.head;
/*      */     }
/*      */ 
/*      */     public int size()
/*      */     {
/* 3332 */       int size = 0;
/* 3333 */       for (MapMakerInternalMap.ReferenceEntry e = this.head.getNextExpirable(); e != this.head; e = e.getNextExpirable()) {
/* 3334 */         size++;
/*      */       }
/* 3336 */       return size;
/*      */     }
/*      */ 
/*      */     public void clear()
/*      */     {
/* 3341 */       MapMakerInternalMap.ReferenceEntry e = this.head.getNextExpirable();
/* 3342 */       while (e != this.head) {
/* 3343 */         MapMakerInternalMap.ReferenceEntry next = e.getNextExpirable();
/* 3344 */         MapMakerInternalMap.nullifyExpirable(e);
/* 3345 */         e = next;
/*      */       }
/*      */ 
/* 3348 */       this.head.setNextExpirable(this.head);
/* 3349 */       this.head.setPreviousExpirable(this.head);
/*      */     }
/*      */ 
/*      */     public Iterator<MapMakerInternalMap.ReferenceEntry<K, V>> iterator()
/*      */     {
/* 3354 */       return new AbstractSequentialIterator(peek())
/*      */       {
/*      */         protected MapMakerInternalMap.ReferenceEntry<K, V> computeNext(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
/* 3357 */           MapMakerInternalMap.ReferenceEntry next = previous.getNextExpirable();
/* 3358 */           return next == MapMakerInternalMap.ExpirationQueue.this.head ? null : next;
/*      */         }
/*      */       };
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class EvictionQueue<K, V> extends AbstractQueue<MapMakerInternalMap.ReferenceEntry<K, V>>
/*      */   {
/* 3112 */     final MapMakerInternalMap.ReferenceEntry<K, V> head = new MapMakerInternalMap.AbstractReferenceEntry()
/*      */     {
/* 3114 */       MapMakerInternalMap.ReferenceEntry<K, V> nextEvictable = this;
/*      */ 
/* 3126 */       MapMakerInternalMap.ReferenceEntry<K, V> previousEvictable = this;
/*      */ 
/*      */       public MapMakerInternalMap.ReferenceEntry<K, V> getNextEvictable()
/*      */       {
/* 3118 */         return this.nextEvictable;
/*      */       }
/*      */ 
/*      */       public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */       {
/* 3123 */         this.nextEvictable = next;
/*      */       }
/*      */ 
/*      */       public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousEvictable()
/*      */       {
/* 3130 */         return this.previousEvictable;
/*      */       }
/*      */ 
/*      */       public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<K, V> previous)
/*      */       {
/* 3135 */         this.previousEvictable = previous;
/*      */       }
/* 3112 */     };
/*      */ 
/*      */     public boolean offer(MapMakerInternalMap.ReferenceEntry<K, V> entry)
/*      */     {
/* 3144 */       MapMakerInternalMap.connectEvictables(entry.getPreviousEvictable(), entry.getNextEvictable());
/*      */ 
/* 3147 */       MapMakerInternalMap.connectEvictables(this.head.getPreviousEvictable(), entry);
/* 3148 */       MapMakerInternalMap.connectEvictables(entry, this.head);
/*      */ 
/* 3150 */       return true;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> peek()
/*      */     {
/* 3155 */       MapMakerInternalMap.ReferenceEntry next = this.head.getNextEvictable();
/* 3156 */       return next == this.head ? null : next;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> poll()
/*      */     {
/* 3161 */       MapMakerInternalMap.ReferenceEntry next = this.head.getNextEvictable();
/* 3162 */       if (next == this.head) {
/* 3163 */         return null;
/*      */       }
/*      */ 
/* 3166 */       remove(next);
/* 3167 */       return next;
/*      */     }
/*      */ 
/*      */     public boolean remove(Object o)
/*      */     {
/* 3173 */       MapMakerInternalMap.ReferenceEntry e = (MapMakerInternalMap.ReferenceEntry)o;
/* 3174 */       MapMakerInternalMap.ReferenceEntry previous = e.getPreviousEvictable();
/* 3175 */       MapMakerInternalMap.ReferenceEntry next = e.getNextEvictable();
/* 3176 */       MapMakerInternalMap.connectEvictables(previous, next);
/* 3177 */       MapMakerInternalMap.nullifyEvictable(e);
/*      */ 
/* 3179 */       return next != MapMakerInternalMap.NullEntry.INSTANCE;
/*      */     }
/*      */ 
/*      */     public boolean contains(Object o)
/*      */     {
/* 3185 */       MapMakerInternalMap.ReferenceEntry e = (MapMakerInternalMap.ReferenceEntry)o;
/* 3186 */       return e.getNextEvictable() != MapMakerInternalMap.NullEntry.INSTANCE;
/*      */     }
/*      */ 
/*      */     public boolean isEmpty()
/*      */     {
/* 3191 */       return this.head.getNextEvictable() == this.head;
/*      */     }
/*      */ 
/*      */     public int size()
/*      */     {
/* 3196 */       int size = 0;
/* 3197 */       for (MapMakerInternalMap.ReferenceEntry e = this.head.getNextEvictable(); e != this.head; e = e.getNextEvictable()) {
/* 3198 */         size++;
/*      */       }
/* 3200 */       return size;
/*      */     }
/*      */ 
/*      */     public void clear()
/*      */     {
/* 3205 */       MapMakerInternalMap.ReferenceEntry e = this.head.getNextEvictable();
/* 3206 */       while (e != this.head) {
/* 3207 */         MapMakerInternalMap.ReferenceEntry next = e.getNextEvictable();
/* 3208 */         MapMakerInternalMap.nullifyEvictable(e);
/* 3209 */         e = next;
/*      */       }
/*      */ 
/* 3212 */       this.head.setNextEvictable(this.head);
/* 3213 */       this.head.setPreviousEvictable(this.head);
/*      */     }
/*      */ 
/*      */     public Iterator<MapMakerInternalMap.ReferenceEntry<K, V>> iterator()
/*      */     {
/* 3218 */       return new AbstractSequentialIterator(peek())
/*      */       {
/*      */         protected MapMakerInternalMap.ReferenceEntry<K, V> computeNext(MapMakerInternalMap.ReferenceEntry<K, V> previous) {
/* 3221 */           MapMakerInternalMap.ReferenceEntry next = previous.getNextEvictable();
/* 3222 */           return next == MapMakerInternalMap.EvictionQueue.this.head ? null : next;
/*      */         }
/*      */       };
/*      */     }
/*      */   }
/*      */ 
/*      */   static class Segment<K, V> extends ReentrantLock
/*      */   {
/*      */     final MapMakerInternalMap<K, V> map;
/*      */     volatile int count;
/*      */     int modCount;
/*      */     int threshold;
/*      */     volatile AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> table;
/*      */     final int maxSegmentSize;
/*      */     final ReferenceQueue<K> keyReferenceQueue;
/*      */     final ReferenceQueue<V> valueReferenceQueue;
/*      */     final Queue<MapMakerInternalMap.ReferenceEntry<K, V>> recencyQueue;
/* 2074 */     final AtomicInteger readCount = new AtomicInteger();
/*      */     final Queue<MapMakerInternalMap.ReferenceEntry<K, V>> evictionQueue;
/*      */     final Queue<MapMakerInternalMap.ReferenceEntry<K, V>> expirationQueue;
/*      */ 
/*      */     Segment(MapMakerInternalMap<K, V> map, int initialCapacity, int maxSegmentSize)
/*      */     {
/* 2091 */       this.map = map;
/* 2092 */       this.maxSegmentSize = maxSegmentSize;
/* 2093 */       initTable(newEntryArray(initialCapacity));
/*      */ 
/* 2095 */       this.keyReferenceQueue = (map.usesKeyReferences() ? new ReferenceQueue() : null);
/*      */ 
/* 2098 */       this.valueReferenceQueue = (map.usesValueReferences() ? new ReferenceQueue() : null);
/*      */ 
/* 2101 */       this.recencyQueue = ((map.evictsBySize()) || (map.expiresAfterAccess()) ? new ConcurrentLinkedQueue() : MapMakerInternalMap.discardingQueue());
/*      */ 
/* 2105 */       this.evictionQueue = (map.evictsBySize() ? new MapMakerInternalMap.EvictionQueue() : MapMakerInternalMap.discardingQueue());
/*      */ 
/* 2109 */       this.expirationQueue = (map.expires() ? new MapMakerInternalMap.ExpirationQueue() : MapMakerInternalMap.discardingQueue());
/*      */     }
/*      */ 
/*      */     AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> newEntryArray(int size)
/*      */     {
/* 2115 */       return new AtomicReferenceArray(size);
/*      */     }
/*      */ 
/*      */     void initTable(AtomicReferenceArray<MapMakerInternalMap.ReferenceEntry<K, V>> newTable) {
/* 2119 */       this.threshold = (newTable.length() * 3 / 4);
/* 2120 */       if (this.threshold == this.maxSegmentSize)
/*      */       {
/* 2122 */         this.threshold += 1;
/*      */       }
/* 2124 */       this.table = newTable;
/*      */     }
/*      */ 
/*      */     MapMakerInternalMap.ReferenceEntry<K, V> newEntry(K key, int hash, MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/* 2129 */       return this.map.entryFactory.newEntry(this, key, hash, next);
/*      */     }
/*      */ 
/*      */     MapMakerInternalMap.ReferenceEntry<K, V> copyEntry(MapMakerInternalMap.ReferenceEntry<K, V> original, MapMakerInternalMap.ReferenceEntry<K, V> newNext)
/*      */     {
/* 2138 */       if (original.getKey() == null)
/*      */       {
/* 2140 */         return null;
/*      */       }
/*      */ 
/* 2143 */       MapMakerInternalMap.ValueReference valueReference = original.getValueReference();
/* 2144 */       Object value = valueReference.get();
/* 2145 */       if ((value == null) && (!valueReference.isComputingReference()))
/*      */       {
/* 2147 */         return null;
/*      */       }
/*      */ 
/* 2150 */       MapMakerInternalMap.ReferenceEntry newEntry = this.map.entryFactory.copyEntry(this, original, newNext);
/* 2151 */       newEntry.setValueReference(valueReference.copyFor(this.valueReferenceQueue, value, newEntry));
/* 2152 */       return newEntry;
/*      */     }
/*      */ 
/*      */     void setValue(MapMakerInternalMap.ReferenceEntry<K, V> entry, V value)
/*      */     {
/* 2160 */       MapMakerInternalMap.ValueReference valueReference = this.map.valueStrength.referenceValue(this, entry, value);
/* 2161 */       entry.setValueReference(valueReference);
/* 2162 */       recordWrite(entry);
/*      */     }
/*      */ 
/*      */     void tryDrainReferenceQueues()
/*      */     {
/* 2171 */       if (tryLock())
/*      */         try {
/* 2173 */           drainReferenceQueues();
/*      */         } finally {
/* 2175 */           unlock();
/*      */         }
/*      */     }
/*      */ 
/*      */     void drainReferenceQueues()
/*      */     {
/* 2186 */       if (this.map.usesKeyReferences()) {
/* 2187 */         drainKeyReferenceQueue();
/*      */       }
/* 2189 */       if (this.map.usesValueReferences())
/* 2190 */         drainValueReferenceQueue();
/*      */     }
/*      */ 
/*      */     void drainKeyReferenceQueue()
/*      */     {
/* 2197 */       int i = 0;
/*      */       Reference ref;
/* 2198 */       while ((ref = this.keyReferenceQueue.poll()) != null)
/*      */       {
/* 2200 */         MapMakerInternalMap.ReferenceEntry entry = (MapMakerInternalMap.ReferenceEntry)ref;
/* 2201 */         this.map.reclaimKey(entry);
/* 2202 */         i++; if (i == 16)
/*      */           break;
/*      */       }
/*      */     }
/*      */ 
/*      */     void drainValueReferenceQueue()
/*      */     {
/* 2211 */       int i = 0;
/*      */       Reference ref;
/* 2212 */       while ((ref = this.valueReferenceQueue.poll()) != null)
/*      */       {
/* 2214 */         MapMakerInternalMap.ValueReference valueReference = (MapMakerInternalMap.ValueReference)ref;
/* 2215 */         this.map.reclaimValue(valueReference);
/* 2216 */         i++; if (i == 16)
/*      */           break;
/*      */       }
/*      */     }
/*      */ 
/*      */     void clearReferenceQueues()
/*      */     {
/* 2226 */       if (this.map.usesKeyReferences()) {
/* 2227 */         clearKeyReferenceQueue();
/*      */       }
/* 2229 */       if (this.map.usesValueReferences())
/* 2230 */         clearValueReferenceQueue();
/*      */     }
/*      */ 
/*      */     void clearKeyReferenceQueue() {
/* 2235 */       while (this.keyReferenceQueue.poll() != null);
/*      */     }
/*      */ 
/*      */     void clearValueReferenceQueue() {
/* 2239 */       while (this.valueReferenceQueue.poll() != null);
/*      */     }
/*      */ 
/*      */     void recordRead(MapMakerInternalMap.ReferenceEntry<K, V> entry)
/*      */     {
/* 2252 */       if (this.map.expiresAfterAccess()) {
/* 2253 */         recordExpirationTime(entry, this.map.expireAfterAccessNanos);
/*      */       }
/* 2255 */       this.recencyQueue.add(entry);
/*      */     }
/*      */ 
/*      */     void recordLockedRead(MapMakerInternalMap.ReferenceEntry<K, V> entry)
/*      */     {
/* 2267 */       this.evictionQueue.add(entry);
/* 2268 */       if (this.map.expiresAfterAccess()) {
/* 2269 */         recordExpirationTime(entry, this.map.expireAfterAccessNanos);
/* 2270 */         this.expirationQueue.add(entry);
/*      */       }
/*      */     }
/*      */ 
/*      */     void recordWrite(MapMakerInternalMap.ReferenceEntry<K, V> entry)
/*      */     {
/* 2281 */       drainRecencyQueue();
/* 2282 */       this.evictionQueue.add(entry);
/* 2283 */       if (this.map.expires())
/*      */       {
/* 2286 */         long expiration = this.map.expiresAfterAccess() ? this.map.expireAfterAccessNanos : this.map.expireAfterWriteNanos;
/*      */ 
/* 2289 */         recordExpirationTime(entry, expiration);
/* 2290 */         this.expirationQueue.add(entry);
/*      */       }
/*      */     }
/*      */ 
/*      */     void drainRecencyQueue()
/*      */     {
/*      */       MapMakerInternalMap.ReferenceEntry e;
/* 2303 */       while ((e = (MapMakerInternalMap.ReferenceEntry)this.recencyQueue.poll()) != null)
/*      */       {
/* 2308 */         if (this.evictionQueue.contains(e)) {
/* 2309 */           this.evictionQueue.add(e);
/*      */         }
/* 2311 */         if ((this.map.expiresAfterAccess()) && (this.expirationQueue.contains(e)))
/* 2312 */           this.expirationQueue.add(e);
/*      */       }
/*      */     }
/*      */ 
/*      */     void recordExpirationTime(MapMakerInternalMap.ReferenceEntry<K, V> entry, long expirationNanos)
/*      */     {
/* 2321 */       entry.setExpirationTime(this.map.ticker.read() + expirationNanos);
/*      */     }
/*      */ 
/*      */     void tryExpireEntries()
/*      */     {
/* 2328 */       if (tryLock())
/*      */         try {
/* 2330 */           expireEntries();
/*      */         } finally {
/* 2332 */           unlock();
/*      */         }
/*      */     }
/*      */ 
/*      */     void expireEntries()
/*      */     {
/* 2340 */       drainRecencyQueue();
/*      */ 
/* 2342 */       if (this.expirationQueue.isEmpty())
/*      */       {
/* 2345 */         return;
/*      */       }
/* 2347 */       long now = this.map.ticker.read();
/*      */       MapMakerInternalMap.ReferenceEntry e;
/* 2349 */       while (((e = (MapMakerInternalMap.ReferenceEntry)this.expirationQueue.peek()) != null) && (this.map.isExpired(e, now)))
/* 2350 */         if (!removeEntry(e, e.getHash(), MapMaker.RemovalCause.EXPIRED))
/* 2351 */           throw new AssertionError();
/*      */     }
/*      */ 
/*      */     void enqueueNotification(MapMakerInternalMap.ReferenceEntry<K, V> entry, MapMaker.RemovalCause cause)
/*      */     {
/* 2359 */       enqueueNotification(entry.getKey(), entry.getHash(), entry.getValueReference().get(), cause);
/*      */     }
/*      */ 
/*      */     void enqueueNotification(K key, int hash, V value, MapMaker.RemovalCause cause) {
/* 2363 */       if (this.map.removalNotificationQueue != MapMakerInternalMap.DISCARDING_QUEUE) {
/* 2364 */         MapMaker.RemovalNotification notification = new MapMaker.RemovalNotification(key, value, cause);
/* 2365 */         this.map.removalNotificationQueue.offer(notification);
/*      */       }
/*      */     }
/*      */ 
/*      */     boolean evictEntries()
/*      */     {
/* 2377 */       if ((this.map.evictsBySize()) && (this.count >= this.maxSegmentSize)) {
/* 2378 */         drainRecencyQueue();
/*      */ 
/* 2380 */         MapMakerInternalMap.ReferenceEntry e = (MapMakerInternalMap.ReferenceEntry)this.evictionQueue.remove();
/* 2381 */         if (!removeEntry(e, e.getHash(), MapMaker.RemovalCause.SIZE)) {
/* 2382 */           throw new AssertionError();
/*      */         }
/* 2384 */         return true;
/*      */       }
/* 2386 */       return false;
/*      */     }
/*      */ 
/*      */     MapMakerInternalMap.ReferenceEntry<K, V> getFirst(int hash)
/*      */     {
/* 2394 */       AtomicReferenceArray table = this.table;
/* 2395 */       return (MapMakerInternalMap.ReferenceEntry)table.get(hash & table.length() - 1);
/*      */     }
/*      */ 
/*      */     MapMakerInternalMap.ReferenceEntry<K, V> getEntry(Object key, int hash)
/*      */     {
/* 2401 */       if (this.count != 0) {
/* 2402 */         for (MapMakerInternalMap.ReferenceEntry e = getFirst(hash); e != null; e = e.getNext()) {
/* 2403 */           if (e.getHash() != hash)
/*      */           {
/*      */             continue;
/*      */           }
/* 2407 */           Object entryKey = e.getKey();
/* 2408 */           if (entryKey == null) {
/* 2409 */             tryDrainReferenceQueues();
/*      */           }
/* 2413 */           else if (this.map.keyEquivalence.equivalent(key, entryKey)) {
/* 2414 */             return e;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 2419 */       return null;
/*      */     }
/*      */ 
/*      */     MapMakerInternalMap.ReferenceEntry<K, V> getLiveEntry(Object key, int hash) {
/* 2423 */       MapMakerInternalMap.ReferenceEntry e = getEntry(key, hash);
/* 2424 */       if (e == null)
/* 2425 */         return null;
/* 2426 */       if ((this.map.expires()) && (this.map.isExpired(e))) {
/* 2427 */         tryExpireEntries();
/* 2428 */         return null;
/*      */       }
/* 2430 */       return e;
/*      */     }
/*      */ 
/*      */     V get(Object key, int hash) {
/*      */       try {
/* 2435 */         MapMakerInternalMap.ReferenceEntry e = getLiveEntry(key, hash);
/* 2436 */         if (e == null) {
/* 2437 */           Object localObject1 = null;
/*      */           return localObject1;
/*      */         }
/* 2440 */         Object value = e.getValueReference().get();
/* 2441 */         if (value != null)
/* 2442 */           recordRead(e);
/*      */         else {
/* 2444 */           tryDrainReferenceQueues();
/*      */         }
/* 2446 */         Object localObject2 = value;
/*      */         return localObject2; } finally { postReadCleanup(); } throw localObject3;
/*      */     }
/*      */ 
/*      */     boolean containsKey(Object key, int hash)
/*      */     {
/*      */       try {
/* 2454 */         if (this.count != 0) {
/* 2455 */           e = getLiveEntry(key, hash);
/* 2456 */           if (e == null) {
/* 2457 */             i = 0;
/*      */             return i;
/*      */           }
/* 2459 */           int i = e.getValueReference().get() != null ? 1 : 0;
/*      */           return i;
/*      */         }
/* 2462 */         MapMakerInternalMap.ReferenceEntry e = 0;
/*      */         return e; } finally { postReadCleanup(); } throw localObject;
/*      */     }
/*      */ 
/*      */     V put(K key, int hash, V value, boolean onlyIfAbsent)
/*      */     {
/* 2498 */       lock();
/*      */       try {
/* 2500 */         preWriteCleanup();
/*      */ 
/* 2502 */         int newCount = this.count + 1;
/* 2503 */         if (newCount > this.threshold) {
/* 2504 */           expand();
/* 2505 */           newCount = this.count + 1;
/*      */         }
/*      */ 
/* 2508 */         AtomicReferenceArray table = this.table;
/* 2509 */         int index = hash & table.length() - 1;
/* 2510 */         MapMakerInternalMap.ReferenceEntry first = (MapMakerInternalMap.ReferenceEntry)table.get(index);
/*      */ 
/* 2513 */         for (MapMakerInternalMap.ReferenceEntry e = first; e != null; e = e.getNext()) {
/* 2514 */           entryKey = e.getKey();
/* 2515 */           if ((e.getHash() != hash) || (entryKey == null) || (!this.map.keyEquivalence.equivalent(key, entryKey)))
/*      */           {
/*      */             continue;
/*      */           }
/* 2519 */           MapMakerInternalMap.ValueReference valueReference = e.getValueReference();
/* 2520 */           Object entryValue = valueReference.get();
/*      */ 
/* 2522 */           if (entryValue == null) {
/* 2523 */             this.modCount += 1;
/* 2524 */             setValue(e, value);
/* 2525 */             if (!valueReference.isComputingReference()) {
/* 2526 */               enqueueNotification(key, hash, entryValue, MapMaker.RemovalCause.COLLECTED);
/* 2527 */               newCount = this.count;
/* 2528 */             } else if (evictEntries()) {
/* 2529 */               newCount = this.count + 1;
/*      */             }
/* 2531 */             this.count = newCount;
/* 2532 */             localObject1 = null;
/*      */             return localObject1;
/*      */           }
/* 2533 */           if (onlyIfAbsent)
/*      */           {
/* 2537 */             recordLockedRead(e);
/* 2538 */             localObject1 = entryValue;
/*      */             return localObject1;
/*      */           }
/* 2541 */           this.modCount += 1;
/* 2542 */           enqueueNotification(key, hash, entryValue, MapMaker.RemovalCause.REPLACED);
/* 2543 */           setValue(e, value);
/* 2544 */           Object localObject1 = entryValue;
/*      */           return localObject1;
/*      */         }
/* 2550 */         this.modCount += 1;
/* 2551 */         MapMakerInternalMap.ReferenceEntry newEntry = newEntry(key, hash, first);
/* 2552 */         setValue(newEntry, value);
/* 2553 */         table.set(index, newEntry);
/* 2554 */         if (evictEntries()) {
/* 2555 */           newCount = this.count + 1;
/*      */         }
/* 2557 */         this.count = newCount;
/* 2558 */         Object entryKey = null;
/*      */         return entryKey;
/*      */       }
/*      */       finally
/*      */       {
/* 2560 */         unlock();
/* 2561 */         postWriteCleanup(); } throw localObject2;
/*      */     }
/*      */ 
/*      */     void expand()
/*      */     {
/* 2570 */       AtomicReferenceArray oldTable = this.table;
/* 2571 */       int oldCapacity = oldTable.length();
/* 2572 */       if (oldCapacity >= 1073741824) {
/* 2573 */         return;
/*      */       }
/*      */ 
/* 2586 */       int newCount = this.count;
/* 2587 */       AtomicReferenceArray newTable = newEntryArray(oldCapacity << 1);
/* 2588 */       this.threshold = (newTable.length() * 3 / 4);
/* 2589 */       int newMask = newTable.length() - 1;
/* 2590 */       for (int oldIndex = 0; oldIndex < oldCapacity; oldIndex++)
/*      */       {
/* 2593 */         MapMakerInternalMap.ReferenceEntry head = (MapMakerInternalMap.ReferenceEntry)oldTable.get(oldIndex);
/*      */ 
/* 2595 */         if (head != null) {
/* 2596 */           MapMakerInternalMap.ReferenceEntry next = head.getNext();
/* 2597 */           int headIndex = head.getHash() & newMask;
/*      */ 
/* 2600 */           if (next == null) {
/* 2601 */             newTable.set(headIndex, head);
/*      */           }
/*      */           else
/*      */           {
/* 2606 */             MapMakerInternalMap.ReferenceEntry tail = head;
/* 2607 */             int tailIndex = headIndex;
/* 2608 */             for (MapMakerInternalMap.ReferenceEntry e = next; e != null; e = e.getNext()) {
/* 2609 */               int newIndex = e.getHash() & newMask;
/* 2610 */               if (newIndex == tailIndex)
/*      */                 continue;
/* 2612 */               tailIndex = newIndex;
/* 2613 */               tail = e;
/*      */             }
/*      */ 
/* 2616 */             newTable.set(tailIndex, tail);
/*      */ 
/* 2619 */             for (MapMakerInternalMap.ReferenceEntry e = head; e != tail; e = e.getNext()) {
/* 2620 */               int newIndex = e.getHash() & newMask;
/* 2621 */               MapMakerInternalMap.ReferenceEntry newNext = (MapMakerInternalMap.ReferenceEntry)newTable.get(newIndex);
/* 2622 */               MapMakerInternalMap.ReferenceEntry newFirst = copyEntry(e, newNext);
/* 2623 */               if (newFirst != null) {
/* 2624 */                 newTable.set(newIndex, newFirst);
/*      */               } else {
/* 2626 */                 removeCollectedEntry(e);
/* 2627 */                 newCount--;
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 2633 */       this.table = newTable;
/* 2634 */       this.count = newCount;
/*      */     }
/*      */ 
/*      */     boolean replace(K key, int hash, V oldValue, V newValue) {
/* 2638 */       lock();
/*      */       try {
/* 2640 */         preWriteCleanup();
/*      */ 
/* 2642 */         AtomicReferenceArray table = this.table;
/* 2643 */         int index = hash & table.length() - 1;
/* 2644 */         MapMakerInternalMap.ReferenceEntry first = (MapMakerInternalMap.ReferenceEntry)table.get(index);
/*      */ 
/* 2646 */         for (MapMakerInternalMap.ReferenceEntry e = first; e != null; e = e.getNext()) {
/* 2647 */           Object entryKey = e.getKey();
/* 2648 */           if ((e.getHash() != hash) || (entryKey == null) || (!this.map.keyEquivalence.equivalent(key, entryKey)))
/*      */           {
/*      */             continue;
/*      */           }
/* 2652 */           MapMakerInternalMap.ValueReference valueReference = e.getValueReference();
/* 2653 */           Object entryValue = valueReference.get();
/* 2654 */           if (entryValue == null) {
/* 2655 */             if (isCollected(valueReference)) {
/* 2656 */               newCount = this.count - 1;
/* 2657 */               this.modCount += 1;
/* 2658 */               enqueueNotification(entryKey, hash, entryValue, MapMaker.RemovalCause.COLLECTED);
/* 2659 */               MapMakerInternalMap.ReferenceEntry newFirst = removeFromChain(first, e);
/* 2660 */               newCount = this.count - 1;
/* 2661 */               table.set(index, newFirst);
/* 2662 */               this.count = newCount;
/*      */             }
/* 2664 */             newCount = 0;
/*      */             return newCount;
/*      */           }
/* 2667 */           if (this.map.valueEquivalence.equivalent(oldValue, entryValue)) {
/* 2668 */             this.modCount += 1;
/* 2669 */             enqueueNotification(key, hash, entryValue, MapMaker.RemovalCause.REPLACED);
/* 2670 */             setValue(e, newValue);
/* 2671 */             newCount = 1;
/*      */             return newCount;
/*      */           }
/* 2675 */           recordLockedRead(e);
/* 2676 */           int newCount = 0;
/*      */           return newCount;
/*      */         }
/* 2681 */         e = 0;
/*      */         return e;
/*      */       }
/*      */       finally
/*      */       {
/* 2683 */         unlock();
/* 2684 */         postWriteCleanup(); } throw localObject1;
/*      */     }
/*      */ 
/*      */     V replace(K key, int hash, V newValue)
/*      */     {
/* 2689 */       lock();
/*      */       try {
/* 2691 */         preWriteCleanup();
/*      */ 
/* 2693 */         AtomicReferenceArray table = this.table;
/* 2694 */         int index = hash & table.length() - 1;
/* 2695 */         MapMakerInternalMap.ReferenceEntry first = (MapMakerInternalMap.ReferenceEntry)table.get(index);
/*      */ 
/* 2697 */         for (MapMakerInternalMap.ReferenceEntry e = first; e != null; e = e.getNext()) {
/* 2698 */           Object entryKey = e.getKey();
/* 2699 */           if ((e.getHash() != hash) || (entryKey == null) || (!this.map.keyEquivalence.equivalent(key, entryKey)))
/*      */           {
/*      */             continue;
/*      */           }
/* 2703 */           MapMakerInternalMap.ValueReference valueReference = e.getValueReference();
/* 2704 */           Object entryValue = valueReference.get();
/* 2705 */           if (entryValue == null) {
/* 2706 */             if (isCollected(valueReference)) {
/* 2707 */               newCount = this.count - 1;
/* 2708 */               this.modCount += 1;
/* 2709 */               enqueueNotification(entryKey, hash, entryValue, MapMaker.RemovalCause.COLLECTED);
/* 2710 */               MapMakerInternalMap.ReferenceEntry newFirst = removeFromChain(first, e);
/* 2711 */               newCount = this.count - 1;
/* 2712 */               table.set(index, newFirst);
/* 2713 */               this.count = newCount;
/*      */             }
/* 2715 */             newCount = null;
/*      */             return newCount;
/*      */           }
/* 2718 */           this.modCount += 1;
/* 2719 */           enqueueNotification(key, hash, entryValue, MapMaker.RemovalCause.REPLACED);
/* 2720 */           setValue(e, newValue);
/* 2721 */           int newCount = entryValue;
/*      */           return newCount;
/*      */         }
/* 2725 */         e = null;
/*      */         return e;
/*      */       }
/*      */       finally
/*      */       {
/* 2727 */         unlock();
/* 2728 */         postWriteCleanup(); } throw localObject1;
/*      */     }
/*      */ 
/*      */     V remove(Object key, int hash)
/*      */     {
/* 2733 */       lock();
/*      */       try {
/* 2735 */         preWriteCleanup();
/*      */ 
/* 2737 */         int newCount = this.count - 1;
/* 2738 */         AtomicReferenceArray table = this.table;
/* 2739 */         int index = hash & table.length() - 1;
/* 2740 */         MapMakerInternalMap.ReferenceEntry first = (MapMakerInternalMap.ReferenceEntry)table.get(index);
/*      */ 
/* 2742 */         for (MapMakerInternalMap.ReferenceEntry e = first; e != null; e = e.getNext()) {
/* 2743 */           Object entryKey = e.getKey();
/* 2744 */           if ((e.getHash() != hash) || (entryKey == null) || (!this.map.keyEquivalence.equivalent(key, entryKey)))
/*      */             continue;
/* 2746 */           MapMakerInternalMap.ValueReference valueReference = e.getValueReference();
/* 2747 */           Object entryValue = valueReference.get();
/*      */           MapMaker.RemovalCause cause;
/* 2750 */           if (entryValue != null) {
/* 2751 */             cause = MapMaker.RemovalCause.EXPLICIT;
/*      */           }
/*      */           else
/*      */           {
/*      */             MapMaker.RemovalCause cause;
/* 2752 */             if (isCollected(valueReference)) {
/* 2753 */               cause = MapMaker.RemovalCause.COLLECTED;
/*      */             } else {
/* 2755 */               Object localObject1 = null;
/*      */               return localObject1;
/*      */             }
/*      */           }
/*      */           MapMaker.RemovalCause cause;
/* 2758 */           this.modCount += 1;
/* 2759 */           enqueueNotification(entryKey, hash, entryValue, cause);
/* 2760 */           MapMakerInternalMap.ReferenceEntry newFirst = removeFromChain(first, e);
/* 2761 */           newCount = this.count - 1;
/* 2762 */           table.set(index, newFirst);
/* 2763 */           this.count = newCount;
/* 2764 */           Object localObject2 = entryValue;
/*      */           return localObject2;
/*      */         }
/* 2768 */         e = null;
/*      */         return e;
/*      */       }
/*      */       finally
/*      */       {
/* 2770 */         unlock();
/* 2771 */         postWriteCleanup(); } throw localObject3;
/*      */     }
/*      */ 
/*      */     boolean remove(Object key, int hash, Object value)
/*      */     {
/* 2776 */       lock();
/*      */       try {
/* 2778 */         preWriteCleanup();
/*      */ 
/* 2780 */         int newCount = this.count - 1;
/* 2781 */         AtomicReferenceArray table = this.table;
/* 2782 */         int index = hash & table.length() - 1;
/* 2783 */         MapMakerInternalMap.ReferenceEntry first = (MapMakerInternalMap.ReferenceEntry)table.get(index);
/*      */ 
/* 2785 */         for (MapMakerInternalMap.ReferenceEntry e = first; e != null; e = e.getNext()) {
/* 2786 */           Object entryKey = e.getKey();
/* 2787 */           if ((e.getHash() != hash) || (entryKey == null) || (!this.map.keyEquivalence.equivalent(key, entryKey)))
/*      */             continue;
/* 2789 */           MapMakerInternalMap.ValueReference valueReference = e.getValueReference();
/* 2790 */           Object entryValue = valueReference.get();
/*      */           MapMaker.RemovalCause cause;
/* 2793 */           if (this.map.valueEquivalence.equivalent(value, entryValue)) {
/* 2794 */             cause = MapMaker.RemovalCause.EXPLICIT;
/*      */           }
/*      */           else
/*      */           {
/*      */             MapMaker.RemovalCause cause;
/* 2795 */             if (isCollected(valueReference)) {
/* 2796 */               cause = MapMaker.RemovalCause.COLLECTED;
/*      */             } else {
/* 2798 */               int i = 0;
/*      */               return i;
/*      */             }
/*      */           }
/*      */           MapMaker.RemovalCause cause;
/* 2801 */           this.modCount += 1;
/* 2802 */           enqueueNotification(entryKey, hash, entryValue, cause);
/* 2803 */           MapMakerInternalMap.ReferenceEntry newFirst = removeFromChain(first, e);
/* 2804 */           newCount = this.count - 1;
/* 2805 */           table.set(index, newFirst);
/* 2806 */           this.count = newCount;
/* 2807 */           int j = cause == MapMaker.RemovalCause.EXPLICIT ? 1 : 0;
/*      */           return j;
/*      */         }
/* 2811 */         e = 0;
/*      */         return e;
/*      */       }
/*      */       finally
/*      */       {
/* 2813 */         unlock();
/* 2814 */         postWriteCleanup(); } throw localObject1;
/*      */     }
/*      */ 
/*      */     void clear()
/*      */     {
/* 2819 */       if (this.count != 0) {
/* 2820 */         lock();
/*      */         try {
/* 2822 */           AtomicReferenceArray table = this.table;
/* 2823 */           if (this.map.removalNotificationQueue != MapMakerInternalMap.DISCARDING_QUEUE) {
/* 2824 */             for (int i = 0; i < table.length(); i++) {
/* 2825 */               for (MapMakerInternalMap.ReferenceEntry e = (MapMakerInternalMap.ReferenceEntry)table.get(i); e != null; e = e.getNext())
/*      */               {
/* 2827 */                 if (!e.getValueReference().isComputingReference()) {
/* 2828 */                   enqueueNotification(e, MapMaker.RemovalCause.EXPLICIT);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/* 2833 */           for (int i = 0; i < table.length(); i++) {
/* 2834 */             table.set(i, null);
/*      */           }
/* 2836 */           clearReferenceQueues();
/* 2837 */           this.evictionQueue.clear();
/* 2838 */           this.expirationQueue.clear();
/* 2839 */           this.readCount.set(0);
/*      */ 
/* 2841 */           this.modCount += 1;
/* 2842 */           this.count = 0;
/*      */         } finally {
/* 2844 */           unlock();
/* 2845 */           postWriteCleanup();
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     MapMakerInternalMap.ReferenceEntry<K, V> removeFromChain(MapMakerInternalMap.ReferenceEntry<K, V> first, MapMakerInternalMap.ReferenceEntry<K, V> entry)
/*      */     {
/* 2864 */       this.evictionQueue.remove(entry);
/* 2865 */       this.expirationQueue.remove(entry);
/*      */ 
/* 2867 */       int newCount = this.count;
/* 2868 */       MapMakerInternalMap.ReferenceEntry newFirst = entry.getNext();
/* 2869 */       for (MapMakerInternalMap.ReferenceEntry e = first; e != entry; e = e.getNext()) {
/* 2870 */         MapMakerInternalMap.ReferenceEntry next = copyEntry(e, newFirst);
/* 2871 */         if (next != null) {
/* 2872 */           newFirst = next;
/*      */         } else {
/* 2874 */           removeCollectedEntry(e);
/* 2875 */           newCount--;
/*      */         }
/*      */       }
/* 2878 */       this.count = newCount;
/* 2879 */       return newFirst;
/*      */     }
/*      */ 
/*      */     void removeCollectedEntry(MapMakerInternalMap.ReferenceEntry<K, V> entry) {
/* 2883 */       enqueueNotification(entry, MapMaker.RemovalCause.COLLECTED);
/* 2884 */       this.evictionQueue.remove(entry);
/* 2885 */       this.expirationQueue.remove(entry);
/*      */     }
/*      */ 
/*      */     boolean reclaimKey(MapMakerInternalMap.ReferenceEntry<K, V> entry, int hash)
/*      */     {
/* 2892 */       lock();
/*      */       try {
/* 2894 */         int newCount = this.count - 1;
/* 2895 */         AtomicReferenceArray table = this.table;
/* 2896 */         int index = hash & table.length() - 1;
/* 2897 */         MapMakerInternalMap.ReferenceEntry first = (MapMakerInternalMap.ReferenceEntry)table.get(index);
/*      */ 
/* 2899 */         for (MapMakerInternalMap.ReferenceEntry e = first; e != null; e = e.getNext())
/* 2900 */           if (e == entry) {
/* 2901 */             this.modCount += 1;
/* 2902 */             enqueueNotification(e.getKey(), hash, e.getValueReference().get(), MapMaker.RemovalCause.COLLECTED);
/*      */ 
/* 2904 */             MapMakerInternalMap.ReferenceEntry newFirst = removeFromChain(first, e);
/* 2905 */             newCount = this.count - 1;
/* 2906 */             table.set(index, newFirst);
/* 2907 */             this.count = newCount;
/* 2908 */             int i = 1;
/*      */             return i;
/*      */           }
/* 2912 */         e = 0;
/*      */         return e;
/*      */       }
/*      */       finally
/*      */       {
/* 2914 */         unlock();
/* 2915 */         postWriteCleanup(); } throw localObject;
/*      */     }
/*      */ 
/*      */     boolean reclaimValue(K key, int hash, MapMakerInternalMap.ValueReference<K, V> valueReference)
/*      */     {
/* 2923 */       lock();
/*      */       try {
/* 2925 */         int newCount = this.count - 1;
/* 2926 */         AtomicReferenceArray table = this.table;
/* 2927 */         int index = hash & table.length() - 1;
/* 2928 */         MapMakerInternalMap.ReferenceEntry first = (MapMakerInternalMap.ReferenceEntry)table.get(index);
/*      */ 
/* 2930 */         for (MapMakerInternalMap.ReferenceEntry e = first; e != null; e = e.getNext()) {
/* 2931 */           Object entryKey = e.getKey();
/* 2932 */           if ((e.getHash() != hash) || (entryKey == null) || (!this.map.keyEquivalence.equivalent(key, entryKey)))
/*      */             continue;
/* 2934 */           MapMakerInternalMap.ValueReference v = e.getValueReference();
/* 2935 */           if (v == valueReference) {
/* 2936 */             this.modCount += 1;
/* 2937 */             enqueueNotification(key, hash, valueReference.get(), MapMaker.RemovalCause.COLLECTED);
/* 2938 */             newFirst = removeFromChain(first, e);
/* 2939 */             newCount = this.count - 1;
/* 2940 */             table.set(index, newFirst);
/* 2941 */             this.count = newCount;
/* 2942 */             int i = 1;
/*      */             return i;
/*      */           }
/* 2944 */           MapMakerInternalMap.ReferenceEntry newFirst = 0;
/*      */           return newFirst;
/*      */         }
/* 2948 */         e = 0;
/*      */         return e;
/*      */       }
/*      */       finally
/*      */       {
/* 2950 */         unlock();
/* 2951 */         if (!isHeldByCurrentThread())
/* 2952 */           postWriteCleanup(); 
/* 2952 */       }throw localObject1;
/*      */     }
/*      */ 
/*      */     boolean removeEntry(MapMakerInternalMap.ReferenceEntry<K, V> entry, int hash, MapMaker.RemovalCause cause)
/*      */     {
/* 2990 */       int newCount = this.count - 1;
/* 2991 */       AtomicReferenceArray table = this.table;
/* 2992 */       int index = hash & table.length() - 1;
/* 2993 */       MapMakerInternalMap.ReferenceEntry first = (MapMakerInternalMap.ReferenceEntry)table.get(index);
/*      */ 
/* 2995 */       for (MapMakerInternalMap.ReferenceEntry e = first; e != null; e = e.getNext()) {
/* 2996 */         if (e == entry) {
/* 2997 */           this.modCount += 1;
/* 2998 */           enqueueNotification(e.getKey(), hash, e.getValueReference().get(), cause);
/* 2999 */           MapMakerInternalMap.ReferenceEntry newFirst = removeFromChain(first, e);
/* 3000 */           newCount = this.count - 1;
/* 3001 */           table.set(index, newFirst);
/* 3002 */           this.count = newCount;
/* 3003 */           return true;
/*      */         }
/*      */       }
/*      */ 
/* 3007 */       return false;
/*      */     }
/*      */ 
/*      */     boolean isCollected(MapMakerInternalMap.ValueReference<K, V> valueReference)
/*      */     {
/* 3015 */       if (valueReference.isComputingReference()) {
/* 3016 */         return false;
/*      */       }
/* 3018 */       return valueReference.get() == null;
/*      */     }
/*      */ 
/*      */     V getLiveValue(MapMakerInternalMap.ReferenceEntry<K, V> entry)
/*      */     {
/* 3026 */       if (entry.getKey() == null) {
/* 3027 */         tryDrainReferenceQueues();
/* 3028 */         return null;
/*      */       }
/* 3030 */       Object value = entry.getValueReference().get();
/* 3031 */       if (value == null) {
/* 3032 */         tryDrainReferenceQueues();
/* 3033 */         return null;
/*      */       }
/*      */ 
/* 3036 */       if ((this.map.expires()) && (this.map.isExpired(entry))) {
/* 3037 */         tryExpireEntries();
/* 3038 */         return null;
/*      */       }
/* 3040 */       return value;
/*      */     }
/*      */ 
/*      */     void postReadCleanup()
/*      */     {
/* 3049 */       if ((this.readCount.incrementAndGet() & 0x3F) == 0)
/* 3050 */         runCleanup();
/*      */     }
/*      */ 
/*      */     void preWriteCleanup()
/*      */     {
/* 3062 */       runLockedCleanup();
/*      */     }
/*      */ 
/*      */     void postWriteCleanup()
/*      */     {
/* 3069 */       runUnlockedCleanup();
/*      */     }
/*      */ 
/*      */     void runCleanup() {
/* 3073 */       runLockedCleanup();
/* 3074 */       runUnlockedCleanup();
/*      */     }
/*      */ 
/*      */     void runLockedCleanup() {
/* 3078 */       if (tryLock())
/*      */         try {
/* 3080 */           drainReferenceQueues();
/* 3081 */           expireEntries();
/* 3082 */           this.readCount.set(0);
/*      */         } finally {
/* 3084 */           unlock();
/*      */         }
/*      */     }
/*      */ 
/*      */     void runUnlockedCleanup()
/*      */     {
/* 3091 */       if (!isHeldByCurrentThread())
/* 3092 */         this.map.processPendingNotifications();
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class StrongValueReference<K, V>
/*      */     implements MapMakerInternalMap.ValueReference<K, V>
/*      */   {
/*      */     final V referent;
/*      */ 
/*      */     StrongValueReference(V referent)
/*      */     {
/* 1768 */       this.referent = referent;
/*      */     }
/*      */ 
/*      */     public V get()
/*      */     {
/* 1773 */       return this.referent;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getEntry()
/*      */     {
/* 1778 */       return null;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, MapMakerInternalMap.ReferenceEntry<K, V> entry)
/*      */     {
/* 1784 */       return this;
/*      */     }
/*      */ 
/*      */     public boolean isComputingReference()
/*      */     {
/* 1789 */       return false;
/*      */     }
/*      */ 
/*      */     public void clear(MapMakerInternalMap.ValueReference<K, V> newValue)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class SoftValueReference<K, V> extends SoftReference<V>
/*      */     implements MapMakerInternalMap.ValueReference<K, V>
/*      */   {
/*      */     final MapMakerInternalMap.ReferenceEntry<K, V> entry;
/*      */ 
/*      */     SoftValueReference(ReferenceQueue<V> queue, V referent, MapMakerInternalMap.ReferenceEntry<K, V> entry)
/*      */     {
/* 1730 */       super(queue);
/* 1731 */       this.entry = entry;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getEntry()
/*      */     {
/* 1736 */       return this.entry;
/*      */     }
/*      */ 
/*      */     public void clear(MapMakerInternalMap.ValueReference<K, V> newValue)
/*      */     {
/* 1741 */       clear();
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, MapMakerInternalMap.ReferenceEntry<K, V> entry)
/*      */     {
/* 1747 */       return new SoftValueReference(queue, value, entry);
/*      */     }
/*      */ 
/*      */     public boolean isComputingReference()
/*      */     {
/* 1752 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class WeakValueReference<K, V> extends WeakReference<V>
/*      */     implements MapMakerInternalMap.ValueReference<K, V>
/*      */   {
/*      */     final MapMakerInternalMap.ReferenceEntry<K, V> entry;
/*      */ 
/*      */     WeakValueReference(ReferenceQueue<V> queue, V referent, MapMakerInternalMap.ReferenceEntry<K, V> entry)
/*      */     {
/* 1691 */       super(queue);
/* 1692 */       this.entry = entry;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getEntry()
/*      */     {
/* 1697 */       return this.entry;
/*      */     }
/*      */ 
/*      */     public void clear(MapMakerInternalMap.ValueReference<K, V> newValue)
/*      */     {
/* 1702 */       clear();
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ValueReference<K, V> copyFor(ReferenceQueue<V> queue, V value, MapMakerInternalMap.ReferenceEntry<K, V> entry)
/*      */     {
/* 1708 */       return new WeakValueReference(queue, value, entry);
/*      */     }
/*      */ 
/*      */     public boolean isComputingReference()
/*      */     {
/* 1713 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class WeakExpirableEvictableEntry<K, V> extends MapMakerInternalMap.WeakEntry<K, V>
/*      */     implements MapMakerInternalMap.ReferenceEntry<K, V>
/*      */   {
/* 1616 */     volatile long time = 9223372036854775807L;
/*      */ 
/* 1628 */     MapMakerInternalMap.ReferenceEntry<K, V> nextExpirable = MapMakerInternalMap.nullEntry();
/*      */ 
/* 1641 */     MapMakerInternalMap.ReferenceEntry<K, V> previousExpirable = MapMakerInternalMap.nullEntry();
/*      */ 
/* 1656 */     MapMakerInternalMap.ReferenceEntry<K, V> nextEvictable = MapMakerInternalMap.nullEntry();
/*      */ 
/* 1669 */     MapMakerInternalMap.ReferenceEntry<K, V> previousEvictable = MapMakerInternalMap.nullEntry();
/*      */ 
/*      */     WeakExpirableEvictableEntry(ReferenceQueue<K> queue, K key, int hash, MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/* 1611 */       super(key, hash, next);
/*      */     }
/*      */ 
/*      */     public long getExpirationTime()
/*      */     {
/* 1620 */       return this.time;
/*      */     }
/*      */ 
/*      */     public void setExpirationTime(long time)
/*      */     {
/* 1625 */       this.time = time;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getNextExpirable()
/*      */     {
/* 1633 */       return this.nextExpirable;
/*      */     }
/*      */ 
/*      */     public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/* 1638 */       this.nextExpirable = next;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousExpirable()
/*      */     {
/* 1646 */       return this.previousExpirable;
/*      */     }
/*      */ 
/*      */     public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<K, V> previous)
/*      */     {
/* 1651 */       this.previousExpirable = previous;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getNextEvictable()
/*      */     {
/* 1661 */       return this.nextEvictable;
/*      */     }
/*      */ 
/*      */     public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/* 1666 */       this.nextEvictable = next;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousEvictable()
/*      */     {
/* 1674 */       return this.previousEvictable;
/*      */     }
/*      */ 
/*      */     public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<K, V> previous)
/*      */     {
/* 1679 */       this.previousEvictable = previous;
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class WeakEvictableEntry<K, V> extends MapMakerInternalMap.WeakEntry<K, V>
/*      */     implements MapMakerInternalMap.ReferenceEntry<K, V>
/*      */   {
/* 1580 */     MapMakerInternalMap.ReferenceEntry<K, V> nextEvictable = MapMakerInternalMap.nullEntry();
/*      */ 
/* 1593 */     MapMakerInternalMap.ReferenceEntry<K, V> previousEvictable = MapMakerInternalMap.nullEntry();
/*      */ 
/*      */     WeakEvictableEntry(ReferenceQueue<K> queue, K key, int hash, MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/* 1575 */       super(key, hash, next);
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getNextEvictable()
/*      */     {
/* 1585 */       return this.nextEvictable;
/*      */     }
/*      */ 
/*      */     public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/* 1590 */       this.nextEvictable = next;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousEvictable()
/*      */     {
/* 1598 */       return this.previousEvictable;
/*      */     }
/*      */ 
/*      */     public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<K, V> previous)
/*      */     {
/* 1603 */       this.previousEvictable = previous;
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class WeakExpirableEntry<K, V> extends MapMakerInternalMap.WeakEntry<K, V>
/*      */     implements MapMakerInternalMap.ReferenceEntry<K, V>
/*      */   {
/* 1532 */     volatile long time = 9223372036854775807L;
/*      */ 
/* 1544 */     MapMakerInternalMap.ReferenceEntry<K, V> nextExpirable = MapMakerInternalMap.nullEntry();
/*      */ 
/* 1557 */     MapMakerInternalMap.ReferenceEntry<K, V> previousExpirable = MapMakerInternalMap.nullEntry();
/*      */ 
/*      */     WeakExpirableEntry(ReferenceQueue<K> queue, K key, int hash, MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/* 1527 */       super(key, hash, next);
/*      */     }
/*      */ 
/*      */     public long getExpirationTime()
/*      */     {
/* 1536 */       return this.time;
/*      */     }
/*      */ 
/*      */     public void setExpirationTime(long time)
/*      */     {
/* 1541 */       this.time = time;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getNextExpirable()
/*      */     {
/* 1549 */       return this.nextExpirable;
/*      */     }
/*      */ 
/*      */     public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/* 1554 */       this.nextExpirable = next;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousExpirable()
/*      */     {
/* 1562 */       return this.previousExpirable;
/*      */     }
/*      */ 
/*      */     public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<K, V> previous)
/*      */     {
/* 1567 */       this.previousExpirable = previous;
/*      */     }
/*      */   }
/*      */ 
/*      */   static class WeakEntry<K, V> extends WeakReference<K>
/*      */     implements MapMakerInternalMap.ReferenceEntry<K, V>
/*      */   {
/*      */     final int hash;
/*      */     final MapMakerInternalMap.ReferenceEntry<K, V> next;
/* 1498 */     volatile MapMakerInternalMap.ValueReference<K, V> valueReference = MapMakerInternalMap.unset();
/*      */ 
/*      */     WeakEntry(ReferenceQueue<K> queue, K key, int hash, MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/* 1430 */       super(queue);
/* 1431 */       this.hash = hash;
/* 1432 */       this.next = next;
/*      */     }
/*      */ 
/*      */     public K getKey()
/*      */     {
/* 1437 */       return get();
/*      */     }
/*      */ 
/*      */     public long getExpirationTime()
/*      */     {
/* 1444 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setExpirationTime(long time)
/*      */     {
/* 1449 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getNextExpirable()
/*      */     {
/* 1454 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/* 1459 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousExpirable()
/*      */     {
/* 1464 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<K, V> previous)
/*      */     {
/* 1469 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getNextEvictable()
/*      */     {
/* 1476 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/* 1481 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousEvictable()
/*      */     {
/* 1486 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<K, V> previous)
/*      */     {
/* 1491 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ValueReference<K, V> getValueReference()
/*      */     {
/* 1502 */       return this.valueReference;
/*      */     }
/*      */ 
/*      */     public void setValueReference(MapMakerInternalMap.ValueReference<K, V> valueReference)
/*      */     {
/* 1507 */       MapMakerInternalMap.ValueReference previous = this.valueReference;
/* 1508 */       this.valueReference = valueReference;
/* 1509 */       previous.clear(valueReference);
/*      */     }
/*      */ 
/*      */     public int getHash()
/*      */     {
/* 1514 */       return this.hash;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getNext()
/*      */     {
/* 1519 */       return this.next;
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class StrongExpirableEvictableEntry<K, V> extends MapMakerInternalMap.StrongEntry<K, V>
/*      */     implements MapMakerInternalMap.ReferenceEntry<K, V>
/*      */   {
/* 1101 */     volatile long time = 9223372036854775807L;
/*      */ 
/* 1113 */     MapMakerInternalMap.ReferenceEntry<K, V> nextExpirable = MapMakerInternalMap.nullEntry();
/*      */ 
/* 1126 */     MapMakerInternalMap.ReferenceEntry<K, V> previousExpirable = MapMakerInternalMap.nullEntry();
/*      */ 
/* 1141 */     MapMakerInternalMap.ReferenceEntry<K, V> nextEvictable = MapMakerInternalMap.nullEntry();
/*      */ 
/* 1154 */     MapMakerInternalMap.ReferenceEntry<K, V> previousEvictable = MapMakerInternalMap.nullEntry();
/*      */ 
/*      */     StrongExpirableEvictableEntry(K key, int hash, MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/* 1096 */       super(hash, next);
/*      */     }
/*      */ 
/*      */     public long getExpirationTime()
/*      */     {
/* 1105 */       return this.time;
/*      */     }
/*      */ 
/*      */     public void setExpirationTime(long time)
/*      */     {
/* 1110 */       this.time = time;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getNextExpirable()
/*      */     {
/* 1118 */       return this.nextExpirable;
/*      */     }
/*      */ 
/*      */     public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/* 1123 */       this.nextExpirable = next;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousExpirable()
/*      */     {
/* 1131 */       return this.previousExpirable;
/*      */     }
/*      */ 
/*      */     public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<K, V> previous)
/*      */     {
/* 1136 */       this.previousExpirable = previous;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getNextEvictable()
/*      */     {
/* 1146 */       return this.nextEvictable;
/*      */     }
/*      */ 
/*      */     public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/* 1151 */       this.nextEvictable = next;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousEvictable()
/*      */     {
/* 1159 */       return this.previousEvictable;
/*      */     }
/*      */ 
/*      */     public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<K, V> previous)
/*      */     {
/* 1164 */       this.previousEvictable = previous;
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class StrongEvictableEntry<K, V> extends MapMakerInternalMap.StrongEntry<K, V>
/*      */     implements MapMakerInternalMap.ReferenceEntry<K, V>
/*      */   {
/* 1066 */     MapMakerInternalMap.ReferenceEntry<K, V> nextEvictable = MapMakerInternalMap.nullEntry();
/*      */ 
/* 1079 */     MapMakerInternalMap.ReferenceEntry<K, V> previousEvictable = MapMakerInternalMap.nullEntry();
/*      */ 
/*      */     StrongEvictableEntry(K key, int hash, MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/* 1061 */       super(hash, next);
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getNextEvictable()
/*      */     {
/* 1071 */       return this.nextEvictable;
/*      */     }
/*      */ 
/*      */     public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/* 1076 */       this.nextEvictable = next;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousEvictable()
/*      */     {
/* 1084 */       return this.previousEvictable;
/*      */     }
/*      */ 
/*      */     public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<K, V> previous)
/*      */     {
/* 1089 */       this.previousEvictable = previous;
/*      */     }
/*      */   }
/*      */ 
/*      */   static final class StrongExpirableEntry<K, V> extends MapMakerInternalMap.StrongEntry<K, V>
/*      */     implements MapMakerInternalMap.ReferenceEntry<K, V>
/*      */   {
/* 1019 */     volatile long time = 9223372036854775807L;
/*      */ 
/* 1031 */     MapMakerInternalMap.ReferenceEntry<K, V> nextExpirable = MapMakerInternalMap.nullEntry();
/*      */ 
/* 1044 */     MapMakerInternalMap.ReferenceEntry<K, V> previousExpirable = MapMakerInternalMap.nullEntry();
/*      */ 
/*      */     StrongExpirableEntry(K key, int hash, MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/* 1014 */       super(hash, next);
/*      */     }
/*      */ 
/*      */     public long getExpirationTime()
/*      */     {
/* 1023 */       return this.time;
/*      */     }
/*      */ 
/*      */     public void setExpirationTime(long time)
/*      */     {
/* 1028 */       this.time = time;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getNextExpirable()
/*      */     {
/* 1036 */       return this.nextExpirable;
/*      */     }
/*      */ 
/*      */     public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/* 1041 */       this.nextExpirable = next;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousExpirable()
/*      */     {
/* 1049 */       return this.previousExpirable;
/*      */     }
/*      */ 
/*      */     public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<K, V> previous)
/*      */     {
/* 1054 */       this.previousExpirable = previous;
/*      */     }
/*      */   }
/*      */ 
/*      */   static class StrongEntry<K, V>
/*      */     implements MapMakerInternalMap.ReferenceEntry<K, V>
/*      */   {
/*      */     final K key;
/*      */     final int hash;
/*      */     final MapMakerInternalMap.ReferenceEntry<K, V> next;
/*  986 */     volatile MapMakerInternalMap.ValueReference<K, V> valueReference = MapMakerInternalMap.unset();
/*      */ 
/*      */     StrongEntry(K key, int hash, MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/*  918 */       this.key = key;
/*  919 */       this.hash = hash;
/*  920 */       this.next = next;
/*      */     }
/*      */ 
/*      */     public K getKey()
/*      */     {
/*  925 */       return this.key;
/*      */     }
/*      */ 
/*      */     public long getExpirationTime()
/*      */     {
/*  932 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setExpirationTime(long time)
/*      */     {
/*  937 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getNextExpirable()
/*      */     {
/*  942 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/*  947 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousExpirable()
/*      */     {
/*  952 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<K, V> previous)
/*      */     {
/*  957 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getNextEvictable()
/*      */     {
/*  964 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/*  969 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousEvictable()
/*      */     {
/*  974 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<K, V> previous)
/*      */     {
/*  979 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ValueReference<K, V> getValueReference()
/*      */     {
/*  990 */       return this.valueReference;
/*      */     }
/*      */ 
/*      */     public void setValueReference(MapMakerInternalMap.ValueReference<K, V> valueReference)
/*      */     {
/*  995 */       MapMakerInternalMap.ValueReference previous = this.valueReference;
/*  996 */       this.valueReference = valueReference;
/*  997 */       previous.clear(valueReference);
/*      */     }
/*      */ 
/*      */     public int getHash()
/*      */     {
/* 1002 */       return this.hash;
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getNext()
/*      */     {
/* 1007 */       return this.next;
/*      */     }
/*      */   }
/*      */ 
/*      */   static abstract class AbstractReferenceEntry<K, V>
/*      */     implements MapMakerInternalMap.ReferenceEntry<K, V>
/*      */   {
/*      */     public MapMakerInternalMap.ValueReference<K, V> getValueReference()
/*      */     {
/*  789 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setValueReference(MapMakerInternalMap.ValueReference<K, V> valueReference)
/*      */     {
/*  794 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getNext()
/*      */     {
/*  799 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public int getHash()
/*      */     {
/*  804 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public K getKey()
/*      */     {
/*  809 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public long getExpirationTime()
/*      */     {
/*  814 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setExpirationTime(long time)
/*      */     {
/*  819 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getNextExpirable()
/*      */     {
/*  824 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/*  829 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousExpirable()
/*      */     {
/*  834 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<K, V> previous)
/*      */     {
/*  839 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getNextEvictable()
/*      */     {
/*  844 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<K, V> next)
/*      */     {
/*  849 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<K, V> getPreviousEvictable()
/*      */     {
/*  854 */       throw new UnsupportedOperationException();
/*      */     }
/*      */ 
/*      */     public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<K, V> previous)
/*      */     {
/*  859 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static enum NullEntry
/*      */     implements MapMakerInternalMap.ReferenceEntry<Object, Object>
/*      */   {
/*  720 */     INSTANCE;
/*      */ 
/*      */     public MapMakerInternalMap.ValueReference<Object, Object> getValueReference()
/*      */     {
/*  724 */       return null;
/*      */     }
/*      */ 
/*      */     public void setValueReference(MapMakerInternalMap.ValueReference<Object, Object> valueReference)
/*      */     {
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<Object, Object> getNext() {
/*  732 */       return null;
/*      */     }
/*      */ 
/*      */     public int getHash()
/*      */     {
/*  737 */       return 0;
/*      */     }
/*      */ 
/*      */     public Object getKey()
/*      */     {
/*  742 */       return null;
/*      */     }
/*      */ 
/*      */     public long getExpirationTime()
/*      */     {
/*  747 */       return 0L;
/*      */     }
/*      */ 
/*      */     public void setExpirationTime(long time)
/*      */     {
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<Object, Object> getNextExpirable() {
/*  755 */       return this;
/*      */     }
/*      */ 
/*      */     public void setNextExpirable(MapMakerInternalMap.ReferenceEntry<Object, Object> next)
/*      */     {
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<Object, Object> getPreviousExpirable() {
/*  763 */       return this;
/*      */     }
/*      */ 
/*      */     public void setPreviousExpirable(MapMakerInternalMap.ReferenceEntry<Object, Object> previous)
/*      */     {
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<Object, Object> getNextEvictable() {
/*  771 */       return this;
/*      */     }
/*      */ 
/*      */     public void setNextEvictable(MapMakerInternalMap.ReferenceEntry<Object, Object> next)
/*      */     {
/*      */     }
/*      */ 
/*      */     public MapMakerInternalMap.ReferenceEntry<Object, Object> getPreviousEvictable() {
/*  779 */       return this;
/*      */     }
/*      */ 
/*      */     public void setPreviousEvictable(MapMakerInternalMap.ReferenceEntry<Object, Object> previous)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   static abstract interface ReferenceEntry<K, V>
/*      */   {
/*      */     public abstract MapMakerInternalMap.ValueReference<K, V> getValueReference();
/*      */ 
/*      */     public abstract void setValueReference(MapMakerInternalMap.ValueReference<K, V> paramValueReference);
/*      */ 
/*      */     public abstract ReferenceEntry<K, V> getNext();
/*      */ 
/*      */     public abstract int getHash();
/*      */ 
/*      */     public abstract K getKey();
/*      */ 
/*      */     public abstract long getExpirationTime();
/*      */ 
/*      */     public abstract void setExpirationTime(long paramLong);
/*      */ 
/*      */     public abstract ReferenceEntry<K, V> getNextExpirable();
/*      */ 
/*      */     public abstract void setNextExpirable(ReferenceEntry<K, V> paramReferenceEntry);
/*      */ 
/*      */     public abstract ReferenceEntry<K, V> getPreviousExpirable();
/*      */ 
/*      */     public abstract void setPreviousExpirable(ReferenceEntry<K, V> paramReferenceEntry);
/*      */ 
/*      */     public abstract ReferenceEntry<K, V> getNextEvictable();
/*      */ 
/*      */     public abstract void setNextEvictable(ReferenceEntry<K, V> paramReferenceEntry);
/*      */ 
/*      */     public abstract ReferenceEntry<K, V> getPreviousEvictable();
/*      */ 
/*      */     public abstract void setPreviousEvictable(ReferenceEntry<K, V> paramReferenceEntry);
/*      */   }
/*      */ 
/*      */   static abstract interface ValueReference<K, V>
/*      */   {
/*      */     public abstract V get();
/*      */ 
/*      */     public abstract MapMakerInternalMap.ReferenceEntry<K, V> getEntry();
/*      */ 
/*      */     public abstract ValueReference<K, V> copyFor(ReferenceQueue<V> paramReferenceQueue, V paramV, MapMakerInternalMap.ReferenceEntry<K, V> paramReferenceEntry);
/*      */ 
/*      */     public abstract void clear(ValueReference<K, V> paramValueReference);
/*      */ 
/*      */     public abstract boolean isComputingReference();
/*      */   }
/*      */ 
/*      */   static abstract enum EntryFactory
/*      */   {
/*  352 */     STRONG, 
/*      */ 
/*  359 */     STRONG_EXPIRABLE, 
/*      */ 
/*  374 */     STRONG_EVICTABLE, 
/*      */ 
/*  389 */     STRONG_EXPIRABLE_EVICTABLE, 
/*      */ 
/*  406 */     WEAK, 
/*      */ 
/*  413 */     WEAK_EXPIRABLE, 
/*      */ 
/*  428 */     WEAK_EVICTABLE, 
/*      */ 
/*  443 */     WEAK_EXPIRABLE_EVICTABLE;
/*      */ 
/*      */     static final EntryFactory[][] factories;
/*      */ 
/*      */     static EntryFactory getFactory(MapMakerInternalMap.Strength keyStrength, boolean expireAfterWrite, boolean evictsBySize)
/*      */     {
/*  478 */       int flags = (expireAfterWrite ? 1 : 0) | (evictsBySize ? 2 : 0);
/*  479 */       return factories[keyStrength.ordinal()][flags];
/*      */     }
/*      */ 
/*      */     abstract <K, V> MapMakerInternalMap.ReferenceEntry<K, V> newEntry(MapMakerInternalMap.Segment<K, V> paramSegment, K paramK, int paramInt, MapMakerInternalMap.ReferenceEntry<K, V> paramReferenceEntry);
/*      */ 
/*      */     <K, V> MapMakerInternalMap.ReferenceEntry<K, V> copyEntry(MapMakerInternalMap.Segment<K, V> segment, MapMakerInternalMap.ReferenceEntry<K, V> original, MapMakerInternalMap.ReferenceEntry<K, V> newNext)
/*      */     {
/*  502 */       return newEntry(segment, original.getKey(), original.getHash(), newNext);
/*      */     }
/*      */ 
/*      */     <K, V> void copyExpirableEntry(MapMakerInternalMap.ReferenceEntry<K, V> original, MapMakerInternalMap.ReferenceEntry<K, V> newEntry)
/*      */     {
/*  509 */       newEntry.setExpirationTime(original.getExpirationTime());
/*      */ 
/*  511 */       MapMakerInternalMap.connectExpirables(original.getPreviousExpirable(), newEntry);
/*  512 */       MapMakerInternalMap.connectExpirables(newEntry, original.getNextExpirable());
/*      */ 
/*  514 */       MapMakerInternalMap.nullifyExpirable(original);
/*      */     }
/*      */ 
/*      */     <K, V> void copyEvictableEntry(MapMakerInternalMap.ReferenceEntry<K, V> original, MapMakerInternalMap.ReferenceEntry<K, V> newEntry)
/*      */     {
/*  521 */       MapMakerInternalMap.connectEvictables(original.getPreviousEvictable(), newEntry);
/*  522 */       MapMakerInternalMap.connectEvictables(newEntry, original.getNextEvictable());
/*      */ 
/*  524 */       MapMakerInternalMap.nullifyEvictable(original);
/*      */     }
/*      */ 
/*      */     static
/*      */     {
/*  470 */       factories = new EntryFactory[][] { { STRONG, STRONG_EXPIRABLE, STRONG_EVICTABLE, STRONG_EXPIRABLE_EVICTABLE }, new EntryFactory[0], { WEAK, WEAK_EXPIRABLE, WEAK_EVICTABLE, WEAK_EXPIRABLE_EVICTABLE } };
/*      */     }
/*      */   }
/*      */ 
/*      */   static abstract enum Strength
/*      */   {
/*  295 */     STRONG, 
/*      */ 
/*  308 */     SOFT, 
/*      */ 
/*  321 */     WEAK;
/*      */ 
/*      */     abstract <K, V> MapMakerInternalMap.ValueReference<K, V> referenceValue(MapMakerInternalMap.Segment<K, V> paramSegment, MapMakerInternalMap.ReferenceEntry<K, V> paramReferenceEntry, V paramV);
/*      */ 
/*      */     abstract Equivalence<Object> defaultEquivalence();
/*      */   }
/*      */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.MapMakerInternalMap
 * JD-Core Version:    0.6.0
 */