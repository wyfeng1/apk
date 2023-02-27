/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction31i;
/*    */ 
/*    */ public class DexBackedInstruction31i extends DexBackedInstruction
/*    */   implements Instruction31i
/*    */ {
/*    */   public DexBackedInstruction31i(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
/*    */   {
/* 44 */     super(dexFile, opcode, instructionStart);
/*    */   }
/*    */   public int getRegisterA() {
/* 47 */     return this.dexFile.readUbyte(this.instructionStart + 1); } 
/* 48 */   public int getNarrowLiteral() { return this.dexFile.readInt(this.instructionStart + 2); } 
/* 49 */   public long getWideLiteral() { return getNarrowLiteral();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction31i
 * JD-Core Version:    0.6.0
 */