/*    */ package com.google.common.collect;
/*    */ 
/*    */ abstract class ImmutableAsList<E> extends ImmutableList<E>
/*    */ {
/*    */   abstract ImmutableCollection<E> delegateCollection();
/*    */ 
/*    */   public boolean contains(Object target)
/*    */   {
/* 41 */     return delegateCollection().contains(target);
/*    */   }
/*    */ 
/*    */   public int size()
/*    */   {
/* 46 */     return delegateCollection().size();
/*    */   }
/*    */ 
/*    */   public boolean isEmpty()
/*    */   {
/* 51 */     return delegateCollection().isEmpty();
/*    */   }
/*    */ 
/*    */   boolean isPartialView()
/*    */   {
/* 56 */     return delegateCollection().isPartialView();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.ImmutableAsList
 * JD-Core Version:    0.6.0
 */