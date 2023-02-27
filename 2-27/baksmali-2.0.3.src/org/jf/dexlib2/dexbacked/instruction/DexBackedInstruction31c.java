/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.reference.DexBackedReference;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction31c;
/*    */ import org.jf.dexlib2.iface.reference.Reference;
/*    */ 
/*    */ public class DexBackedInstruction31c extends DexBackedInstruction
/*    */   implements Instruction31c
/*    */ {
/*    */   public DexBackedInstruction31c(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
/*    */   {
/* 46 */     super(dexFile, opcode, instructionStart);
/*    */   }
/*    */   public int getRegisterA() {
/* 49 */     return this.dexFile.readUbyte(this.instructionStart + 1);
/*    */   }
/*    */ 
/*    */   public Reference getReference()
/*    */   {
/* 54 */     return DexBackedReference.makeReference(this.dexFile, this.opcode.referenceType, this.dexFile.readSmallUint(this.instructionStart + 2));
/*    */   }
/*    */ 
/*    */   public int getReferenceType()
/*    */   {
/* 60 */     return this.opcode.referenceType;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction31c
 * JD-Core Version:    0.6.0
 */