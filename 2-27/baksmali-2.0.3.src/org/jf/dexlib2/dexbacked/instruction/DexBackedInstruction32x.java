/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction32x;
/*    */ 
/*    */ public class DexBackedInstruction32x extends DexBackedInstruction
/*    */   implements Instruction32x
/*    */ {
/*    */   public DexBackedInstruction32x(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
/*    */   {
/* 44 */     super(dexFile, opcode, instructionStart);
/*    */   }
/*    */   public int getRegisterA() {
/* 47 */     return this.dexFile.readUshort(this.instructionStart + 2); } 
/* 48 */   public int getRegisterB() { return this.dexFile.readUshort(this.instructionStart + 4);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction32x
 * JD-Core Version:    0.6.0
 */