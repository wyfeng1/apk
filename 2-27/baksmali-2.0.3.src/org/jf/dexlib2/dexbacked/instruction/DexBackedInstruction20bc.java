/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.ReferenceType;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.reference.DexBackedReference;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction20bc;
/*    */ import org.jf.dexlib2.iface.reference.Reference;
/*    */ 
/*    */ public class DexBackedInstruction20bc extends DexBackedInstruction
/*    */   implements Instruction20bc
/*    */ {
/*    */   public DexBackedInstruction20bc(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
/*    */   {
/* 47 */     super(dexFile, opcode, instructionStart);
/*    */   }
/*    */   public int getVerificationError() {
/* 50 */     return this.dexFile.readUbyte(this.instructionStart + 1) & 0x3F;
/*    */   }
/*    */ 
/*    */   public Reference getReference()
/*    */   {
/* 55 */     int referenceType = getReferenceType();
/* 56 */     return DexBackedReference.makeReference(this.dexFile, referenceType, this.dexFile.readUshort(this.instructionStart + 2));
/*    */   }
/*    */ 
/*    */   public int getReferenceType() {
/* 60 */     int referenceType = (this.dexFile.readUbyte(this.instructionStart + 1) >>> 6) + 1;
/* 61 */     ReferenceType.validateReferenceType(referenceType);
/* 62 */     return referenceType;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction20bc
 * JD-Core Version:    0.6.0
 */