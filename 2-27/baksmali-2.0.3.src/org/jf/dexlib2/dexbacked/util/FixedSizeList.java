/*    */ package org.jf.dexlib2.dexbacked.util;
/*    */ 
/*    */ import java.util.AbstractList;
/*    */ 
/*    */ public abstract class FixedSizeList<T> extends AbstractList<T>
/*    */ {
/*    */   public T get(int index)
/*    */   {
/* 44 */     if ((index < 0) || (index >= size())) {
/* 45 */       throw new IndexOutOfBoundsException();
/*    */     }
/* 47 */     return readItem(index);
/*    */   }
/*    */ 
/*    */   public abstract T readItem(int paramInt);
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.util.FixedSizeList
 * JD-Core Version:    0.6.0
 */