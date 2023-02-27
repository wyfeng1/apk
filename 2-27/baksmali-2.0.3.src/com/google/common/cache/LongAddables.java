/*    */ package com.google.common.cache;
/*    */ 
/*    */ import com.google.common.base.Supplier;
/*    */ import java.util.concurrent.atomic.AtomicLong;
/*    */ 
/*    */ final class LongAddables
/*    */ {
/*    */   private static final Supplier<LongAddable> SUPPLIER;
/*    */ 
/*    */   public static LongAddable create()
/*    */   {
/* 56 */     return (LongAddable)SUPPLIER.get();
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/*    */     Supplier supplier;
/*    */     try
/*    */     {
/* 37 */       new LongAdder();
/* 38 */       supplier = new Supplier()
/*    */       {
/*    */         public LongAddable get() {
/* 41 */           return new LongAdder();
/*    */         } } ;
/*    */     } catch (Throwable t) {
/* 45 */       supplier = new Supplier()
/*    */       {
/*    */         public LongAddable get() {
/* 48 */           return new LongAddables.PureJavaLongAddable(null);
/*    */         } } ;
/*    */     }
/* 52 */     SUPPLIER = supplier;
/*    */   }
/*    */ 
/*    */   private static final class PureJavaLongAddable extends AtomicLong
/*    */     implements LongAddable
/*    */   {
/*    */     public void increment()
/*    */     {
/* 62 */       getAndIncrement();
/*    */     }
/*    */ 
/*    */     public void add(long x)
/*    */     {
/* 67 */       getAndAdd(x);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.cache.LongAddables
 * JD-Core Version:    0.6.0
 */