/*    */ package com.google.common.collect;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ class ImmutableEntry<K, V> extends AbstractMapEntry<K, V>
/*    */   implements Serializable
/*    */ {
/*    */   private final K key;
/*    */   private final V value;
/*    */ 
/*    */   ImmutableEntry(K key, V value)
/*    */   {
/* 35 */     this.key = key;
/* 36 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public K getKey() {
/* 40 */     return this.key;
/*    */   }
/*    */ 
/*    */   public V getValue() {
/* 44 */     return this.value;
/*    */   }
/*    */ 
/*    */   public final V setValue(V value) {
/* 48 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.ImmutableEntry
 * JD-Core Version:    0.6.0
 */