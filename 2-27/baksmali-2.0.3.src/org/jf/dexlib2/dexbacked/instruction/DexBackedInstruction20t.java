/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction20t;
/*    */ 
/*    */ public class DexBackedInstruction20t extends DexBackedInstruction
/*    */   implements Instruction20t
/*    */ {
/*    */   public DexBackedInstruction20t(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
/*    */   {
/* 44 */     super(dexFile, opcode, instructionStart);
/*    */   }
/*    */   public int getCodeOffset() {
/* 47 */     return this.dexFile.readShort(this.instructionStart + 2);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction20t
 * JD-Core Version:    0.6.0
 */