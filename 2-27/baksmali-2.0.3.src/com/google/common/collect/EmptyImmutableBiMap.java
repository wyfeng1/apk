/*    */ package com.google.common.collect;
/*    */ 
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ final class EmptyImmutableBiMap extends ImmutableBiMap<Object, Object>
/*    */ {
/* 31 */   static final EmptyImmutableBiMap INSTANCE = new EmptyImmutableBiMap();
/*    */ 
/*    */   public ImmutableBiMap<Object, Object> inverse()
/*    */   {
/* 36 */     return this;
/*    */   }
/*    */ 
/*    */   public int size()
/*    */   {
/* 41 */     return 0;
/*    */   }
/*    */ 
/*    */   public boolean isEmpty()
/*    */   {
/* 46 */     return true;
/*    */   }
/*    */ 
/*    */   public Object get(Object key)
/*    */   {
/* 51 */     return null;
/*    */   }
/*    */ 
/*    */   public ImmutableSet<Map.Entry<Object, Object>> entrySet()
/*    */   {
/* 56 */     return ImmutableSet.of();
/*    */   }
/*    */ 
/*    */   ImmutableSet<Map.Entry<Object, Object>> createEntrySet()
/*    */   {
/* 61 */     throw new AssertionError("should never be called");
/*    */   }
/*    */ 
/*    */   public ImmutableSet<Object> keySet()
/*    */   {
/* 66 */     return ImmutableSet.of();
/*    */   }
/*    */ 
/*    */   boolean isPartialView()
/*    */   {
/* 71 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.EmptyImmutableBiMap
 * JD-Core Version:    0.6.0
 */