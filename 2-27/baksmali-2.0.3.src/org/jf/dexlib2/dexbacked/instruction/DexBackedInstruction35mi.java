/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction35mi;
/*    */ import org.jf.util.NibbleUtils;
/*    */ 
/*    */ public class DexBackedInstruction35mi extends DexBackedInstruction
/*    */   implements Instruction35mi
/*    */ {
/*    */   public DexBackedInstruction35mi(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
/*    */   {
/* 45 */     super(dexFile, opcode, instructionStart);
/*    */   }
/*    */ 
/*    */   public int getRegisterCount() {
/* 49 */     return NibbleUtils.extractHighUnsignedNibble(this.dexFile.readUbyte(this.instructionStart + 1));
/*    */   }
/*    */ 
/*    */   public int getRegisterC()
/*    */   {
/* 54 */     return NibbleUtils.extractLowUnsignedNibble(this.dexFile.readUbyte(this.instructionStart + 4));
/*    */   }
/*    */ 
/*    */   public int getRegisterD()
/*    */   {
/* 59 */     return NibbleUtils.extractHighUnsignedNibble(this.dexFile.readUbyte(this.instructionStart + 4));
/*    */   }
/*    */ 
/*    */   public int getRegisterE()
/*    */   {
/* 64 */     return NibbleUtils.extractLowUnsignedNibble(this.dexFile.readUbyte(this.instructionStart + 5));
/*    */   }
/*    */ 
/*    */   public int getRegisterF()
/*    */   {
/* 69 */     return NibbleUtils.extractHighUnsignedNibble(this.dexFile.readUbyte(this.instructionStart + 5));
/*    */   }
/*    */ 
/*    */   public int getRegisterG()
/*    */   {
/* 74 */     return NibbleUtils.extractLowUnsignedNibble(this.dexFile.readUbyte(this.instructionStart + 1));
/*    */   }
/*    */ 
/*    */   public int getInlineIndex()
/*    */   {
/* 79 */     return this.dexFile.readUshort(this.instructionStart + 2);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction35mi
 * JD-Core Version:    0.6.0
 */