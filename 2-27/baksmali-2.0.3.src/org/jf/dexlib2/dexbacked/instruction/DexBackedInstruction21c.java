/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.reference.DexBackedReference;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction21c;
/*    */ import org.jf.dexlib2.iface.reference.Reference;
/*    */ 
/*    */ public class DexBackedInstruction21c extends DexBackedInstruction
/*    */   implements Instruction21c
/*    */ {
/*    */   public DexBackedInstruction21c(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
/*    */   {
/* 46 */     super(dexFile, opcode, instructionStart);
/*    */   }
/*    */   public int getRegisterA() {
/* 49 */     return this.dexFile.readUbyte(this.instructionStart + 1);
/*    */   }
/*    */ 
/*    */   public Reference getReference()
/*    */   {
/* 54 */     return DexBackedReference.makeReference(this.dexFile, this.opcode.referenceType, this.dexFile.readUshort(this.instructionStart + 2));
/*    */   }
/*    */ 
/*    */   public int getReferenceType()
/*    */   {
/* 59 */     return this.opcode.referenceType;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction21c
 * JD-Core Version:    0.6.0
 */