/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.reference.DexBackedReference;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction22c;
/*    */ import org.jf.dexlib2.iface.reference.Reference;
/*    */ import org.jf.util.NibbleUtils;
/*    */ 
/*    */ public class DexBackedInstruction22c extends DexBackedInstruction
/*    */   implements Instruction22c
/*    */ {
/*    */   public DexBackedInstruction22c(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
/*    */   {
/* 47 */     super(dexFile, opcode, instructionStart);
/*    */   }
/*    */ 
/*    */   public int getRegisterA()
/*    */   {
/* 52 */     return NibbleUtils.extractLowUnsignedNibble(this.dexFile.readByte(this.instructionStart + 1));
/*    */   }
/*    */ 
/*    */   public int getRegisterB()
/*    */   {
/* 57 */     return NibbleUtils.extractHighUnsignedNibble(this.dexFile.readByte(this.instructionStart + 1));
/*    */   }
/*    */ 
/*    */   public Reference getReference()
/*    */   {
/* 63 */     return DexBackedReference.makeReference(this.dexFile, this.opcode.referenceType, this.dexFile.readUshort(this.instructionStart + 2));
/*    */   }
/*    */ 
/*    */   public int getReferenceType()
/*    */   {
/* 68 */     return this.opcode.referenceType;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction22c
 * JD-Core Version:    0.6.0
 */