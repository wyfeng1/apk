/*    */ package com.google.common.collect;
/*    */ 
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ final class SingletonImmutableBiMap<K, V> extends ImmutableBiMap<K, V>
/*    */ {
/*    */   final transient K singleKey;
/*    */   final transient V singleValue;
/*    */   transient ImmutableBiMap<V, K> inverse;
/*    */ 
/*    */   SingletonImmutableBiMap(K singleKey, V singleValue)
/*    */   {
/* 37 */     this.singleKey = singleKey;
/* 38 */     this.singleValue = singleValue;
/*    */   }
/*    */ 
/*    */   private SingletonImmutableBiMap(K singleKey, V singleValue, ImmutableBiMap<V, K> inverse)
/*    */   {
/* 43 */     this.singleKey = singleKey;
/* 44 */     this.singleValue = singleValue;
/* 45 */     this.inverse = inverse;
/*    */   }
/*    */ 
/*    */   SingletonImmutableBiMap(Map.Entry<K, V> entry) {
/* 49 */     this(entry.getKey(), entry.getValue());
/*    */   }
/*    */ 
/*    */   public V get(Object key) {
/* 53 */     return this.singleKey.equals(key) ? this.singleValue : null;
/*    */   }
/*    */ 
/*    */   public int size()
/*    */   {
/* 58 */     return 1;
/*    */   }
/*    */ 
/*    */   public boolean containsKey(Object key) {
/* 62 */     return this.singleKey.equals(key);
/*    */   }
/*    */ 
/*    */   public boolean containsValue(Object value) {
/* 66 */     return this.singleValue.equals(value);
/*    */   }
/*    */ 
/*    */   boolean isPartialView() {
/* 70 */     return false;
/*    */   }
/*    */ 
/*    */   ImmutableSet<Map.Entry<K, V>> createEntrySet()
/*    */   {
/* 75 */     return ImmutableSet.of(Maps.immutableEntry(this.singleKey, this.singleValue));
/*    */   }
/*    */ 
/*    */   ImmutableSet<K> createKeySet()
/*    */   {
/* 80 */     return ImmutableSet.of(this.singleKey);
/*    */   }
/*    */ 
/*    */   public ImmutableBiMap<V, K> inverse()
/*    */   {
/* 88 */     ImmutableBiMap result = this.inverse;
/* 89 */     if (result == null) {
/* 90 */       return this.inverse = new SingletonImmutableBiMap(this.singleValue, this.singleKey, this);
/*    */     }
/*    */ 
/* 93 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.SingletonImmutableBiMap
 * JD-Core Version:    0.6.0
 */