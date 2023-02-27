/*    */ package com.google.common.cache;
/*    */ 
/*    */ import com.google.common.base.Objects;
/*    */ import com.google.common.base.Preconditions;
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ public final class RemovalNotification<K, V>
/*    */   implements Map.Entry<K, V>
/*    */ {
/*    */   private final K key;
/*    */   private final V value;
/*    */   private final RemovalCause cause;
/*    */ 
/*    */   RemovalNotification(K key, V value, RemovalCause cause)
/*    */   {
/* 48 */     this.key = key;
/* 49 */     this.value = value;
/* 50 */     this.cause = ((RemovalCause)Preconditions.checkNotNull(cause));
/*    */   }
/*    */ 
/*    */   public K getKey()
/*    */   {
/* 69 */     return this.key;
/*    */   }
/*    */ 
/*    */   public V getValue() {
/* 73 */     return this.value;
/*    */   }
/*    */ 
/*    */   public final V setValue(V value) {
/* 77 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object object) {
/* 81 */     if ((object instanceof Map.Entry)) {
/* 82 */       Map.Entry that = (Map.Entry)object;
/* 83 */       return (Objects.equal(getKey(), that.getKey())) && (Objects.equal(getValue(), that.getValue()));
/*    */     }
/*    */ 
/* 86 */     return false;
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 90 */     Object k = getKey();
/* 91 */     Object v = getValue();
/* 92 */     return (k == null ? 0 : k.hashCode()) ^ (v == null ? 0 : v.hashCode());
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 99 */     return getKey() + "=" + getValue();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.cache.RemovalNotification
 * JD-Core Version:    0.6.0
 */