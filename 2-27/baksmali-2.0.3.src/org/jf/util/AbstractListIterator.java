/*    */ package org.jf.util;
/*    */ 
/*    */ import java.util.ListIterator;
/*    */ 
/*    */ public abstract class AbstractListIterator<T>
/*    */   implements ListIterator<T>
/*    */ {
/*    */   public boolean hasNext()
/*    */   {
/* 39 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   public T next()
/*    */   {
/* 44 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   public boolean hasPrevious()
/*    */   {
/* 49 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   public T previous()
/*    */   {
/* 54 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   public int nextIndex()
/*    */   {
/* 59 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   public int previousIndex()
/*    */   {
/* 64 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   public void remove()
/*    */   {
/* 69 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   public void set(T t)
/*    */   {
/* 74 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   public void add(T t)
/*    */   {
/* 79 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.util.AbstractListIterator
 * JD-Core Version:    0.6.0
 */