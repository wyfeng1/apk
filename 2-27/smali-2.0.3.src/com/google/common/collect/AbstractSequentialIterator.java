/*    */ package com.google.common.collect;
/*    */ 
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ public abstract class AbstractSequentialIterator<T> extends UnmodifiableIterator<T>
/*    */ {
/*    */   private T nextOrNull;
/*    */ 
/*    */   protected AbstractSequentialIterator(T firstOrNull)
/*    */   {
/* 53 */     this.nextOrNull = firstOrNull;
/*    */   }
/*    */ 
/*    */   protected abstract T computeNext(T paramT);
/*    */ 
/*    */   public final boolean hasNext()
/*    */   {
/* 66 */     return this.nextOrNull != null;
/*    */   }
/*    */ 
/*    */   public final T next()
/*    */   {
/* 71 */     if (!hasNext())
/* 72 */       throw new NoSuchElementException();
/*    */     try
/*    */     {
/* 75 */       Object localObject1 = this.nextOrNull;
/*    */       return localObject1; } finally { this.nextOrNull = computeNext(this.nextOrNull); } throw localObject2;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.AbstractSequentialIterator
 * JD-Core Version:    0.6.0
 */