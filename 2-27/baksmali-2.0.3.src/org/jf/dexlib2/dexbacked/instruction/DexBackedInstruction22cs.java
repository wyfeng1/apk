/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction22cs;
/*    */ import org.jf.util.NibbleUtils;
/*    */ 
/*    */ public class DexBackedInstruction22cs extends DexBackedInstruction
/*    */   implements Instruction22cs
/*    */ {
/*    */   public DexBackedInstruction22cs(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
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
/*    */ 
/*    */   public int getFieldOffset()
/*    */   {
/* 60 */     return this.dexFile.readUshort(this.instructionStart + 2);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction22cs
 * JD-Core Version:    0.6.0
 */