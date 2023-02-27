/*    */ package org.jf.dexlib2.immutable;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import org.jf.dexlib2.base.BaseExceptionHandler;
/*    */ import org.jf.dexlib2.iface.ExceptionHandler;
/*    */ import org.jf.util.ImmutableConverter;
/*    */ 
/*    */ public class ImmutableExceptionHandler extends BaseExceptionHandler
/*    */   implements ExceptionHandler
/*    */ {
/*    */   protected final String exceptionType;
/*    */   protected final int handlerCodeAddress;
/* 70 */   private static final ImmutableConverter<ImmutableExceptionHandler, ExceptionHandler> CONVERTER = new ImmutableConverter()
/*    */   {
/*    */     protected boolean isImmutable(ExceptionHandler item)
/*    */     {
/* 74 */       return item instanceof ImmutableExceptionHandler;
/*    */     }
/*    */ 
/*    */     protected ImmutableExceptionHandler makeImmutable(ExceptionHandler item)
/*    */     {
/* 80 */       return ImmutableExceptionHandler.of(item);
/*    */     }
/* 70 */   };
/*    */ 
/*    */   public ImmutableExceptionHandler(String exceptionType, int handlerCodeAddress)
/*    */   {
/* 48 */     this.exceptionType = exceptionType;
/* 49 */     this.handlerCodeAddress = handlerCodeAddress;
/*    */   }
/*    */ 
/*    */   public static ImmutableExceptionHandler of(ExceptionHandler exceptionHandler) {
/* 53 */     if ((exceptionHandler instanceof ImmutableExceptionHandler)) {
/* 54 */       return (ImmutableExceptionHandler)exceptionHandler;
/*    */     }
/* 56 */     return new ImmutableExceptionHandler(exceptionHandler.getExceptionType(), exceptionHandler.getHandlerCodeAddress());
/*    */   }
/*    */ 
/*    */   public String getExceptionType()
/*    */   {
/* 61 */     return this.exceptionType; } 
/* 62 */   public int getHandlerCodeAddress() { return this.handlerCodeAddress;
/*    */   }
/*    */ 
/*    */   public static ImmutableList<ImmutableExceptionHandler> immutableListOf(Iterable<? extends ExceptionHandler> list)
/*    */   {
/* 67 */     return CONVERTER.toList(list);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.ImmutableExceptionHandler
 * JD-Core Version:    0.6.0
 */