/*    */ package org.jf.smali;
/*    */ 
/*    */ import org.antlr.runtime.IntStream;
/*    */ import org.antlr.runtime.RecognitionException;
/*    */ 
/*    */ public class OdexedInstructionException extends RecognitionException
/*    */ {
/*    */   private String odexedInstruction;
/*    */ 
/*    */   OdexedInstructionException(IntStream input, String odexedInstruction)
/*    */   {
/* 38 */     super(input);
/* 39 */     this.odexedInstruction = odexedInstruction;
/*    */   }
/*    */ 
/*    */   public String getMessage() {
/* 43 */     return this.odexedInstruction + " is an odexed instruction. You cannot reassemble a disassembled odex file " + "unless it has been deodexed.";
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.smali.OdexedInstructionException
 * JD-Core Version:    0.6.0
 */