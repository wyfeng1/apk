/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction3rms;
/*    */ 
/*    */ public class DexBackedInstruction3rms extends DexBackedInstruction
/*    */   implements Instruction3rms
/*    */ {
/*    */   public DexBackedInstruction3rms(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
/*    */   {
/* 44 */     super(dexFile, opcode, instructionStart);
/*    */   }
/*    */ 
/*    */   public int getRegisterCount() {
/* 48 */     return this.dexFile.readUbyte(this.instructionStart + 1);
/*    */   }
/*    */ 
/*    */   public int getStartRegister()
/*    */   {
/* 53 */     return this.dexFile.readUshort(this.instructionStart + 4);
/*    */   }
/*    */ 
/*    */   public int getVtableIndex()
/*    */   {
/* 58 */     return this.dexFile.readUshort(this.instructionStart + 2);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction3rms
 * JD-Core Version:    0.6.0
 */