/*     */ package com.google.common.cache;
/*     */ 
/*     */ import com.google.common.base.Ascii;
/*     */ import com.google.common.base.Equivalence;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Objects.ToStringHelper;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Supplier;
/*     */ import com.google.common.base.Suppliers;
/*     */ import com.google.common.base.Ticker;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public final class CacheBuilder<K, V>
/*     */ {
/* 159 */   static final Supplier<? extends AbstractCache.StatsCounter> NULL_STATS_COUNTER = Suppliers.ofInstance(new AbstractCache.StatsCounter()
/*     */   {
/*     */     public void recordHits(int count)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void recordMisses(int count)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void recordLoadSuccess(long loadTime)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void recordLoadException(long loadTime)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void recordEviction()
/*     */     {
/*     */     }
/*     */   });
/*     */ 
/* 181 */   static final CacheStats EMPTY_STATS = new CacheStats(0L, 0L, 0L, 0L, 0L, 0L);
/*     */ 
/* 183 */   static final Supplier<AbstractCache.StatsCounter> CACHE_STATS_COUNTER = new Supplier()
/*     */   {
/*     */     public AbstractCache.StatsCounter get()
/*     */     {
/* 187 */       return new AbstractCache.SimpleStatsCounter();
/*     */     }
/* 183 */   };
/*     */ 
/* 207 */   static final Ticker NULL_TICKER = new Ticker()
/*     */   {
/*     */     public long read() {
/* 210 */       return 0L;
/*     */     }
/* 207 */   };
/*     */ 
/* 214 */   private static final Logger logger = Logger.getLogger(CacheBuilder.class.getName());
/*     */ 
/* 218 */   boolean strictParsing = true;
/*     */ 
/* 220 */   int initialCapacity = -1;
/* 221 */   int concurrencyLevel = -1;
/* 222 */   long maximumSize = -1L;
/* 223 */   long maximumWeight = -1L;
/*     */   Weigher<? super K, ? super V> weigher;
/*     */   LocalCache.Strength keyStrength;
/*     */   LocalCache.Strength valueStrength;
/* 229 */   long expireAfterWriteNanos = -1L;
/* 230 */   long expireAfterAccessNanos = -1L;
/* 231 */   long refreshNanos = -1L;
/*     */   Equivalence<Object> keyEquivalence;
/*     */   Equivalence<Object> valueEquivalence;
/*     */   RemovalListener<? super K, ? super V> removalListener;
/*     */   Ticker ticker;
/* 239 */   Supplier<? extends AbstractCache.StatsCounter> statsCounterSupplier = NULL_STATS_COUNTER;
/*     */ 
/*     */   public static CacheBuilder<Object, Object> newBuilder()
/*     */   {
/* 249 */     return new CacheBuilder();
/*     */   }
/*     */ 
/*     */   Equivalence<Object> getKeyEquivalence()
/*     */   {
/* 300 */     return (Equivalence)Objects.firstNonNull(this.keyEquivalence, getKeyStrength().defaultEquivalence());
/*     */   }
/*     */ 
/*     */   Equivalence<Object> getValueEquivalence()
/*     */   {
/* 319 */     return (Equivalence)Objects.firstNonNull(this.valueEquivalence, getValueStrength().defaultEquivalence());
/*     */   }
/*     */ 
/*     */   int getInitialCapacity()
/*     */   {
/* 341 */     return this.initialCapacity == -1 ? 16 : this.initialCapacity;
/*     */   }
/*     */ 
/*     */   int getConcurrencyLevel()
/*     */   {
/* 383 */     return this.concurrencyLevel == -1 ? 4 : this.concurrencyLevel;
/*     */   }
/*     */ 
/*     */   long getMaximumWeight()
/*     */   {
/* 492 */     if ((this.expireAfterWriteNanos == 0L) || (this.expireAfterAccessNanos == 0L)) {
/* 493 */       return 0L;
/*     */     }
/* 495 */     return this.weigher == null ? this.maximumSize : this.maximumWeight;
/*     */   }
/*     */ 
/*     */   <K1 extends K, V1 extends V> Weigher<K1, V1> getWeigher()
/*     */   {
/* 501 */     return (Weigher)Objects.firstNonNull(this.weigher, OneWeigher.INSTANCE);
/*     */   }
/*     */ 
/*     */   LocalCache.Strength getKeyStrength()
/*     */   {
/* 529 */     return (LocalCache.Strength)Objects.firstNonNull(this.keyStrength, LocalCache.Strength.STRONG);
/*     */   }
/*     */ 
/*     */   LocalCache.Strength getValueStrength()
/*     */   {
/* 584 */     return (LocalCache.Strength)Objects.firstNonNull(this.valueStrength, LocalCache.Strength.STRONG);
/*     */   }
/*     */ 
/*     */   long getExpireAfterWriteNanos()
/*     */   {
/* 615 */     return this.expireAfterWriteNanos == -1L ? 0L : this.expireAfterWriteNanos;
/*     */   }
/*     */ 
/*     */   long getExpireAfterAccessNanos()
/*     */   {
/* 649 */     return this.expireAfterAccessNanos == -1L ? 0L : this.expireAfterAccessNanos;
/*     */   }
/*     */ 
/*     */   long getRefreshNanos()
/*     */   {
/* 689 */     return this.refreshNanos == -1L ? 0L : this.refreshNanos;
/*     */   }
/*     */ 
/*     */   Ticker getTicker(boolean recordsTime)
/*     */   {
/* 708 */     if (this.ticker != null) {
/* 709 */       return this.ticker;
/*     */     }
/* 711 */     return recordsTime ? Ticker.systemTicker() : NULL_TICKER;
/*     */   }
/*     */ 
/*     */   <K1 extends K, V1 extends V> RemovalListener<K1, V1> getRemovalListener()
/*     */   {
/* 750 */     return (RemovalListener)Objects.firstNonNull(this.removalListener, NullListener.INSTANCE);
/*     */   }
/*     */ 
/*     */   Supplier<? extends AbstractCache.StatsCounter> getStatsCounterSupplier()
/*     */   {
/* 767 */     return this.statsCounterSupplier;
/*     */   }
/*     */ 
/*     */   public <K1 extends K, V1 extends V> LoadingCache<K1, V1> build(CacheLoader<? super K1, V1> loader)
/*     */   {
/* 784 */     checkWeightWithWeigher();
/* 785 */     return new LocalCache.LocalLoadingCache(this, loader);
/*     */   }
/*     */ 
/*     */   private void checkWeightWithWeigher()
/*     */   {
/* 811 */     if (this.weigher == null) {
/* 812 */       Preconditions.checkState(this.maximumWeight == -1L, "maximumWeight requires weigher");
/*     */     }
/* 814 */     else if (this.strictParsing) {
/* 815 */       Preconditions.checkState(this.maximumWeight != -1L, "weigher requires maximumWeight");
/*     */     }
/* 817 */     else if (this.maximumWeight == -1L)
/* 818 */       logger.log(Level.WARNING, "ignoring weigher specified without maximumWeight");
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 830 */     Objects.ToStringHelper s = Objects.toStringHelper(this);
/* 831 */     if (this.initialCapacity != -1) {
/* 832 */       s.add("initialCapacity", this.initialCapacity);
/*     */     }
/* 834 */     if (this.concurrencyLevel != -1) {
/* 835 */       s.add("concurrencyLevel", this.concurrencyLevel);
/*     */     }
/* 837 */     if (this.maximumSize != -1L) {
/* 838 */       s.add("maximumSize", this.maximumSize);
/*     */     }
/* 840 */     if (this.maximumWeight != -1L) {
/* 841 */       s.add("maximumWeight", this.maximumWeight);
/*     */     }
/* 843 */     if (this.expireAfterWriteNanos != -1L) {
/* 844 */       s.add("expireAfterWrite", this.expireAfterWriteNanos + "ns");
/*     */     }
/* 846 */     if (this.expireAfterAccessNanos != -1L) {
/* 847 */       s.add("expireAfterAccess", this.expireAfterAccessNanos + "ns");
/*     */     }
/* 849 */     if (this.keyStrength != null) {
/* 850 */       s.add("keyStrength", Ascii.toLowerCase(this.keyStrength.toString()));
/*     */     }
/* 852 */     if (this.valueStrength != null) {
/* 853 */       s.add("valueStrength", Ascii.toLowerCase(this.valueStrength.toString()));
/*     */     }
/* 855 */     if (this.keyEquivalence != null) {
/* 856 */       s.addValue("keyEquivalence");
/*     */     }
/* 858 */     if (this.valueEquivalence != null) {
/* 859 */       s.addValue("valueEquivalence");
/*     */     }
/* 861 */     if (this.removalListener != null) {
/* 862 */       s.addValue("removalListener");
/*     */     }
/* 864 */     return s.toString();
/*     */   }
/*     */ 
/*     */   static enum OneWeigher
/*     */     implements Weigher<Object, Object>
/*     */   {
/* 199 */     INSTANCE;
/*     */ 
/*     */     public int weigh(Object key, Object value)
/*     */     {
/* 203 */       return 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   static enum NullListener
/*     */     implements RemovalListener<Object, Object>
/*     */   {
/* 192 */     INSTANCE;
/*     */ 
/*     */     public void onRemoval(RemovalNotification<Object, Object> notification)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.cache.CacheBuilder
 * JD-Core Version:    0.6.0
 */