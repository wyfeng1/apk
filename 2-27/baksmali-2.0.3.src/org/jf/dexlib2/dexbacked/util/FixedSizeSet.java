/*    */ package org.jf.dexlib2.dexbacked.util;
/*    */ 
/*    */ import java.util.AbstractSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ public abstract class FixedSizeSet<T> extends AbstractSet<T>
/*    */ {
/*    */   public Iterator<T> iterator()
/*    */   {
/* 46 */     return new Iterator() {
/* 47 */       int index = 0;
/*    */ 
/* 49 */       public boolean hasNext() { return this.index < FixedSizeSet.this.size(); } 
/* 50 */       public void remove() { throw new UnsupportedOperationException(); }
/*    */ 
/*    */       public T next() {
/* 53 */         if (!hasNext()) {
/* 54 */           throw new NoSuchElementException();
/*    */         }
/* 56 */         return FixedSizeSet.this.readItem(this.index++);
/*    */       }
/*    */     };
/*    */   }
/*    */ 
/*    */   public abstract T readItem(int paramInt);
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.util.FixedSizeSet
 * JD-Core Version:    0.6.0
 */