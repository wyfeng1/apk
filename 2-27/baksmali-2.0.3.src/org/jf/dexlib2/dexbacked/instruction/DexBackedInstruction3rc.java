/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.reference.DexBackedReference;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction3rc;
/*    */ import org.jf.dexlib2.iface.reference.Reference;
/*    */ 
/*    */ public class DexBackedInstruction3rc extends DexBackedInstruction
/*    */   implements Instruction3rc
/*    */ {
/*    */   public DexBackedInstruction3rc(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
/*    */   {
/* 46 */     super(dexFile, opcode, instructionStart);
/*    */   }
/*    */ 
/*    */   public int getRegisterCount() {
/* 50 */     return this.dexFile.readUbyte(this.instructionStart + 1);
/*    */   }
/*    */ 
/*    */   public int getStartRegister()
/*    */   {
/* 55 */     return this.dexFile.readUshort(this.instructionStart + 4);
/*    */   }
/*    */ 
/*    */   public Reference getReference()
/*    */   {
/* 61 */     return DexBackedReference.makeReference(this.dexFile, this.opcode.referenceType, this.dexFile.readUshort(this.instructionStart + 2));
/*    */   }
/*    */ 
/*    */   public int getReferenceType()
/*    */   {
/* 67 */     return this.opcode.referenceType;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction3rc
 * JD-Core Version:    0.6.0
 */