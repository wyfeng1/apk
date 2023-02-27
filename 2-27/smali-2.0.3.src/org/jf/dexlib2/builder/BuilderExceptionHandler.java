/*    */ package org.jf.dexlib2.builder;
/*    */ 
/*    */ import org.jf.dexlib2.base.BaseExceptionHandler;
/*    */ import org.jf.dexlib2.iface.reference.TypeReference;
/*    */ 
/*    */ public abstract class BuilderExceptionHandler extends BaseExceptionHandler
/*    */ {
/*    */   protected final Label handler;
/*    */ 
/*    */   private BuilderExceptionHandler(Label handler)
/*    */   {
/* 44 */     this.handler = handler;
/*    */   }
/*    */ 
/*    */   static BuilderExceptionHandler newExceptionHandler(TypeReference exceptionType, Label handler)
/*    */   {
/* 54 */     if (exceptionType == null) {
/* 55 */       return newExceptionHandler(handler);
/*    */     }
/* 57 */     return new BuilderExceptionHandler(handler, exceptionType) {
/*    */       public String getExceptionType() {
/* 59 */         return this.val$exceptionType.getType();
/*    */       }
/*    */ 
/*    */       public int getHandlerCodeAddress() {
/* 63 */         return this.handler.getCodeAddress();
/*    */       }
/*    */ 
/*    */       public TypeReference getExceptionTypeReference() {
/* 67 */         return this.val$exceptionType;
/*    */       } } ;
/*    */   }
/*    */ 
/*    */   static BuilderExceptionHandler newExceptionHandler(Label handler) {
/* 73 */     return new BuilderExceptionHandler(handler) {
/*    */       public String getExceptionType() {
/* 75 */         return null;
/*    */       }
/*    */ 
/*    */       public int getHandlerCodeAddress() {
/* 79 */         return this.handler.getCodeAddress();
/*    */       }
/*    */     };
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.BuilderExceptionHandler
 * JD-Core Version:    0.6.0
 */