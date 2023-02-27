/*    */ package org.jf.dexlib2.analysis;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.Instruction;
/*    */ 
/*    */ public class UnresolvedOdexInstruction
/*    */   implements Instruction
/*    */ {
/*    */   public final Instruction originalInstruction;
/*    */   public final int objectRegisterNum;
/*    */ 
/*    */   public UnresolvedOdexInstruction(Instruction originalInstruction, int objectRegisterNumber)
/*    */   {
/* 48 */     this.originalInstruction = originalInstruction;
/* 49 */     this.objectRegisterNum = objectRegisterNumber;
/*    */   }
/*    */ 
/*    */   public Opcode getOpcode() {
/* 53 */     return this.originalInstruction.getOpcode();
/*    */   }
/*    */ 
/*    */   public int getCodeUnits() {
/* 57 */     return this.originalInstruction.getCodeUnits();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.analysis.UnresolvedOdexInstruction
 * JD-Core Version:    0.6.0
 */