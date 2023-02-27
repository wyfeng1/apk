/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.reference.DexBackedReference;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction35c;
/*    */ import org.jf.dexlib2.iface.reference.Reference;
/*    */ import org.jf.util.NibbleUtils;
/*    */ 
/*    */ public class DexBackedInstruction35c extends DexBackedInstruction
/*    */   implements Instruction35c
/*    */ {
/*    */   public DexBackedInstruction35c(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
/*    */   {
/* 47 */     super(dexFile, opcode, instructionStart);
/*    */   }
/*    */ 
/*    */   public int getRegisterCount() {
/* 51 */     return NibbleUtils.extractHighUnsignedNibble(this.dexFile.readUbyte(this.instructionStart + 1));
/*    */   }
/*    */ 
/*    */   public int getRegisterC()
/*    */   {
/* 56 */     return NibbleUtils.extractLowUnsignedNibble(this.dexFile.readUbyte(this.instructionStart + 4));
/*    */   }
/*    */ 
/*    */   public int getRegisterD()
/*    */   {
/* 61 */     return NibbleUtils.extractHighUnsignedNibble(this.dexFile.readUbyte(this.instructionStart + 4));
/*    */   }
/*    */ 
/*    */   public int getRegisterE()
/*    */   {
/* 66 */     return NibbleUtils.extractLowUnsignedNibble(this.dexFile.readUbyte(this.instructionStart + 5));
/*    */   }
/*    */ 
/*    */   public int getRegisterF()
/*    */   {
/* 71 */     return NibbleUtils.extractHighUnsignedNibble(this.dexFile.readUbyte(this.instructionStart + 5));
/*    */   }
/*    */ 
/*    */   public int getRegisterG()
/*    */   {
/* 76 */     return NibbleUtils.extractLowUnsignedNibble(this.dexFile.readUbyte(this.instructionStart + 1));
/*    */   }
/*    */ 
/*    */   public Reference getReference()
/*    */   {
/* 82 */     return DexBackedReference.makeReference(this.dexFile, this.opcode.referenceType, this.dexFile.readUshort(this.instructionStart + 2));
/*    */   }
/*    */ 
/*    */   public int getReferenceType()
/*    */   {
/* 88 */     return this.opcode.referenceType;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction35c
 * JD-Core Version:    0.6.0
 */