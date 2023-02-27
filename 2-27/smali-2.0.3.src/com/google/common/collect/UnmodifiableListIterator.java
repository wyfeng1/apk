/*    */ package com.google.common.collect;
/*    */ 
/*    */ import java.util.ListIterator;
/*    */ 
/*    */ public abstract class UnmodifiableListIterator<E> extends UnmodifiableIterator<E>
/*    */   implements ListIterator<E>
/*    */ {
/*    */   @Deprecated
/*    */   public final void add(E e)
/*    */   {
/* 43 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   @Deprecated
/*    */   public final void set(E e)
/*    */   {
/* 53 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.UnmodifiableListIterator
 * JD-Core Version:    0.6.0
 */