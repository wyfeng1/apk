/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction10t;
/*    */ 
/*    */ public class DexBackedInstruction10t extends DexBackedInstruction
/*    */   implements Instruction10t
/*    */ {
/*    */   public DexBackedInstruction10t(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
/*    */   {
/* 44 */     super(dexFile, opcode, instructionStart);
/*    */   }
/*    */   public int getCodeOffset() {
/* 47 */     return this.dexFile.readByte(this.instructionStart + 1);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction10t
 * JD-Core Version:    0.6.0
 */