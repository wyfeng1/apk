/*    */ package com.google.common.collect;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public abstract class UnmodifiableIterator<E>
/*    */   implements Iterator<E>
/*    */ {
/*    */   @Deprecated
/*    */   public final void remove()
/*    */   {
/* 43 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.UnmodifiableIterator
 * JD-Core Version:    0.6.0
 */