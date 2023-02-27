/*    */ package com.google.common.collect;
/*    */ 
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ abstract class ImmutableMapEntrySet<K, V> extends ImmutableSet<Map.Entry<K, V>>
/*    */ {
/*    */   abstract ImmutableMap<K, V> map();
/*    */ 
/*    */   public int size()
/*    */   {
/* 41 */     return map().size();
/*    */   }
/*    */ 
/*    */   public boolean contains(Object object)
/*    */   {
/* 46 */     if ((object instanceof Map.Entry)) {
/* 47 */       Map.Entry entry = (Map.Entry)object;
/* 48 */       Object value = map().get(entry.getKey());
/* 49 */       return (value != null) && (value.equals(entry.getValue()));
/*    */     }
/* 51 */     return false;
/*    */   }
/*    */ 
/*    */   boolean isPartialView()
/*    */   {
/* 56 */     return map().isPartialView();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.ImmutableMapEntrySet
 * JD-Core Version:    0.6.0
 */