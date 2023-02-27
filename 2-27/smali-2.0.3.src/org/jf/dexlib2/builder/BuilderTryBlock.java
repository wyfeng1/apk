/*    */ package org.jf.dexlib2.builder;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.jf.dexlib2.base.BaseTryBlock;
/*    */ import org.jf.dexlib2.iface.reference.TypeReference;
/*    */ 
/*    */ public class BuilderTryBlock extends BaseTryBlock<BuilderExceptionHandler>
/*    */ {
/*    */   public final BuilderExceptionHandler exceptionHandler;
/*    */   public final Label start;
/*    */   public final Label end;
/*    */ 
/*    */   public BuilderTryBlock(Label start, Label end, TypeReference exceptionType, Label handler)
/*    */   {
/* 59 */     this.start = start;
/* 60 */     this.end = end;
/* 61 */     this.exceptionHandler = BuilderExceptionHandler.newExceptionHandler(exceptionType, handler);
/*    */   }
/*    */ 
/*    */   public BuilderTryBlock(Label start, Label end, Label handler) {
/* 65 */     this.start = start;
/* 66 */     this.end = end;
/* 67 */     this.exceptionHandler = BuilderExceptionHandler.newExceptionHandler(handler);
/*    */   }
/*    */ 
/*    */   public int getStartCodeAddress() {
/* 71 */     return this.start.getCodeAddress();
/*    */   }
/*    */ 
/*    */   public int getCodeUnitCount() {
/* 75 */     return this.end.getCodeAddress() - this.start.getCodeAddress();
/*    */   }
/*    */ 
/*    */   public List<? extends BuilderExceptionHandler> getExceptionHandlers() {
/* 79 */     return ImmutableList.of(this.exceptionHandler);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.BuilderTryBlock
 * JD-Core Version:    0.6.0
 */