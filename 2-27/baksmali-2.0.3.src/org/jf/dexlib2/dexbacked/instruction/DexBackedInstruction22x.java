/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction22x;
/*    */ 
/*    */ public class DexBackedInstruction22x extends DexBackedInstruction
/*    */   implements Instruction22x
/*    */ {
/*    */   public DexBackedInstruction22x(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
/*    */   {
/* 44 */     super(dexFile, opcode, instructionStart);
/*    */   }
/*    */   public int getRegisterA() {
/* 47 */     return this.dexFile.readUbyte(this.instructionStart + 1); } 
/* 48 */   public int getRegisterB() { return this.dexFile.readUshort(this.instructionStart + 2);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction22x
 * JD-Core Version:    0.6.0
 */