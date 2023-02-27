/*    */ package com.google.common.collect;
/*    */ 
/*    */ import com.google.common.base.Objects;
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ abstract class AbstractMapEntry<K, V>
/*    */   implements Map.Entry<K, V>
/*    */ {
/*    */   public abstract K getKey();
/*    */ 
/*    */   public abstract V getValue();
/*    */ 
/*    */   public V setValue(V value)
/*    */   {
/* 43 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object object) {
/* 47 */     if ((object instanceof Map.Entry)) {
/* 48 */       Map.Entry that = (Map.Entry)object;
/* 49 */       return (Objects.equal(getKey(), that.getKey())) && (Objects.equal(getValue(), that.getValue()));
/*    */     }
/*    */ 
/* 52 */     return false;
/*    */   }
/*    */ 
/*    */   public int hashCode() {
/* 56 */     Object k = getKey();
/* 57 */     Object v = getValue();
/* 58 */     return (k == null ? 0 : k.hashCode()) ^ (v == null ? 0 : v.hashCode());
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 65 */     return getKey() + "=" + getValue();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.AbstractMapEntry
 * JD-Core Version:    0.6.0
 */