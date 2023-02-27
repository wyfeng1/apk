/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction12x;
/*    */ import org.jf.util.NibbleUtils;
/*    */ 
/*    */ public class DexBackedInstruction12x extends DexBackedInstruction
/*    */   implements Instruction12x
/*    */ {
/*    */   public DexBackedInstruction12x(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
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
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction12x
 * JD-Core Version:    0.6.0
 */