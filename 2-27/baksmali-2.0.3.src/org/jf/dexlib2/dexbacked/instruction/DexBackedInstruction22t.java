/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction22t;
/*    */ import org.jf.util.NibbleUtils;
/*    */ 
/*    */ public class DexBackedInstruction22t extends DexBackedInstruction
/*    */   implements Instruction22t
/*    */ {
/*    */   public DexBackedInstruction22t(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
/*    */   {
/* 45 */     super(dexFile, opcode, instructionStart);
/*    */   }
/*    */ 
/*    */   public int getRegisterA()
/*    */   {
/* 50 */     return NibbleUtils.extractLowUnsignedNibble(this.dexFile.readByte(this.instructionStart + 1));
/*    */   }
/*    */ 
/*    */   public int getRegisterB()
/*    */   {
/* 55 */     return NibbleUtils.extractHighUnsignedNibble(this.dexFile.readByte(this.instructionStart + 1));
/*    */   }
/*    */   public int getCodeOffset() {
/* 58 */     return this.dexFile.readShort(this.instructionStart + 2);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction22t
 * JD-Core Version:    0.6.0
 */