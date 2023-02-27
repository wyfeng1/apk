/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.base.Ascii;
/*     */ import com.google.common.base.Equivalence;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.base.Objects.ToStringHelper;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Ticker;
/*     */ import java.io.Serializable;
/*     */ import java.util.AbstractMap;
/*     */ import java.util.Collections;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ 
/*     */ public final class MapMaker extends GenericMapMaker<Object, Object>
/*     */ {
/*     */   boolean useCustomMap;
/* 119 */   int initialCapacity = -1;
/* 120 */   int concurrencyLevel = -1;
/* 121 */   int maximumSize = -1;
/*     */   MapMakerInternalMap.Strength keyStrength;
/*     */   MapMakerInternalMap.Strength valueStrength;
/* 126 */   long expireAfterWriteNanos = -1L;
/* 127 */   long expireAfterAccessNanos = -1L;
/*     */   RemovalCause nullRemovalCause;
/*     */   Equivalence<Object> keyEquivalence;
/*     */   Ticker ticker;
/*     */ 
/*     */   Equivalence<Object> getKeyEquivalence()
/*     */   {
/* 158 */     return (Equivalence)Objects.firstNonNull(this.keyEquivalence, getKeyStrength().defaultEquivalence());
/*     */   }
/*     */ 
/*     */   int getInitialCapacity()
/*     */   {
/* 181 */     return this.initialCapacity == -1 ? 16 : this.initialCapacity;
/*     */   }
/*     */ 
/*     */   int getConcurrencyLevel()
/*     */   {
/* 251 */     return this.concurrencyLevel == -1 ? 4 : this.concurrencyLevel;
/*     */   }
/*     */ 
/*     */   MapMakerInternalMap.Strength getKeyStrength()
/*     */   {
/* 283 */     return (MapMakerInternalMap.Strength)Objects.firstNonNull(this.keyStrength, MapMakerInternalMap.Strength.STRONG);
/*     */   }
/*     */ 
/*     */   MapMakerInternalMap.Strength getValueStrength()
/*     */   {
/* 346 */     return (MapMakerInternalMap.Strength)Objects.firstNonNull(this.valueStrength, MapMakerInternalMap.Strength.STRONG);
/*     */   }
/*     */ 
/*     */   long getExpireAfterWriteNanos()
/*     */   {
/* 396 */     return this.expireAfterWriteNanos == -1L ? 0L : this.expireAfterWriteNanos;
/*     */   }
/*     */ 
/*     */   long getExpireAfterAccessNanos()
/*     */   {
/* 439 */     return this.expireAfterAccessNanos == -1L ? 0L : this.expireAfterAccessNanos;
/*     */   }
/*     */ 
/*     */   Ticker getTicker()
/*     */   {
/* 444 */     return (Ticker)Objects.firstNonNull(this.ticker, Ticker.systemTicker());
/*     */   }
/*     */ 
/*     */   public <K, V> ConcurrentMap<K, V> makeMap()
/*     */   {
/* 503 */     if (!this.useCustomMap) {
/* 504 */       return new ConcurrentHashMap(getInitialCapacity(), 0.75F, getConcurrencyLevel());
/*     */     }
/* 506 */     return (ConcurrentMap)(this.nullRemovalCause == null ? new MapMakerInternalMap(this) : new NullConcurrentMap(this));
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 594 */     Objects.ToStringHelper s = Objects.toStringHelper(this);
/* 595 */     if (this.initialCapacity != -1) {
/* 596 */       s.add("initialCapacity", this.initialCapacity);
/*     */     }
/* 598 */     if (this.concurrencyLevel != -1) {
/* 599 */       s.add("concurrencyLevel", this.concurrencyLevel);
/*     */     }
/* 601 */     if (this.maximumSize != -1) {
/* 602 */       s.add("maximumSize", this.maximumSize);
/*     */     }
/* 604 */     if (this.expireAfterWriteNanos != -1L) {
/* 605 */       s.add("expireAfterWrite", this.expireAfterWriteNanos + "ns");
/*     */     }
/* 607 */     if (this.expireAfterAccessNanos != -1L) {
/* 608 */       s.add("expireAfterAccess", this.expireAfterAccessNanos + "ns");
/*     */     }
/* 610 */     if (this.keyStrength != null) {
/* 611 */       s.add("keyStrength", Ascii.toLowerCase(this.keyStrength.toString()));
/*     */     }
/* 613 */     if (this.valueStrength != null) {
/* 614 */       s.add("valueStrength", Ascii.toLowerCase(this.valueStrength.toString()));
/*     */     }
/* 616 */     if (this.keyEquivalence != null) {
/* 617 */       s.addValue("keyEquivalence");
/*     */     }
/* 619 */     if (this.removalListener != null) {
/* 620 */       s.addValue("removalListener");
/*     */     }
/* 622 */     return s.toString();
/*     */   }
/*     */ 
/*     */   static class NullConcurrentMap<K, V> extends AbstractMap<K, V>
/*     */     implements Serializable, ConcurrentMap<K, V>
/*     */   {
/*     */     private final MapMaker.RemovalListener<K, V> removalListener;
/*     */     private final MapMaker.RemovalCause removalCause;
/*     */ 
/*     */     NullConcurrentMap(MapMaker mapMaker)
/*     */     {
/* 757 */       this.removalListener = mapMaker.getRemovalListener();
/* 758 */       this.removalCause = mapMaker.nullRemovalCause;
/*     */     }
/*     */ 
/*     */     public boolean containsKey(Object key)
/*     */     {
/* 765 */       return false;
/*     */     }
/*     */ 
/*     */     public boolean containsValue(Object value)
/*     */     {
/* 770 */       return false;
/*     */     }
/*     */ 
/*     */     public V get(Object key)
/*     */     {
/* 775 */       return null;
/*     */     }
/*     */ 
/*     */     void notifyRemoval(K key, V value) {
/* 779 */       MapMaker.RemovalNotification notification = new MapMaker.RemovalNotification(key, value, this.removalCause);
/*     */ 
/* 781 */       this.removalListener.onRemoval(notification);
/*     */     }
/*     */ 
/*     */     public V put(K key, V value)
/*     */     {
/* 786 */       Preconditions.checkNotNull(key);
/* 787 */       Preconditions.checkNotNull(value);
/* 788 */       notifyRemoval(key, value);
/* 789 */       return null;
/*     */     }
/*     */ 
/*     */     public V putIfAbsent(K key, V value)
/*     */     {
/* 794 */       return put(key, value);
/*     */     }
/*     */ 
/*     */     public V remove(Object key)
/*     */     {
/* 799 */       return null;
/*     */     }
/*     */ 
/*     */     public boolean remove(Object key, Object value)
/*     */     {
/* 804 */       return false;
/*     */     }
/*     */ 
/*     */     public V replace(K key, V value)
/*     */     {
/* 809 */       Preconditions.checkNotNull(key);
/* 810 */       Preconditions.checkNotNull(value);
/* 811 */       return null;
/*     */     }
/*     */ 
/*     */     public boolean replace(K key, V oldValue, V newValue)
/*     */     {
/* 816 */       Preconditions.checkNotNull(key);
/* 817 */       Preconditions.checkNotNull(newValue);
/* 818 */       return false;
/*     */     }
/*     */ 
/*     */     public Set<Map.Entry<K, V>> entrySet()
/*     */     {
/* 823 */       return Collections.emptySet();
/*     */     }
/*     */   }
/*     */ 
/*     */   static abstract enum RemovalCause
/*     */   {
/* 688 */     EXPLICIT, 
/*     */ 
/* 701 */     REPLACED, 
/*     */ 
/* 712 */     COLLECTED, 
/*     */ 
/* 723 */     EXPIRED, 
/*     */ 
/* 734 */     SIZE;
/*     */   }
/*     */ 
/*     */   static final class RemovalNotification<K, V> extends ImmutableEntry<K, V>
/*     */   {
/*     */     private final MapMaker.RemovalCause cause;
/*     */ 
/*     */     RemovalNotification(K key, V value, MapMaker.RemovalCause cause)
/*     */     {
/* 660 */       super(value);
/* 661 */       this.cause = cause;
/*     */     }
/*     */   }
/*     */ 
/*     */   static abstract interface RemovalListener<K, V>
/*     */   {
/*     */     public abstract void onRemoval(MapMaker.RemovalNotification<K, V> paramRemovalNotification);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.MapMaker
 * JD-Core Version:    0.6.0
 */