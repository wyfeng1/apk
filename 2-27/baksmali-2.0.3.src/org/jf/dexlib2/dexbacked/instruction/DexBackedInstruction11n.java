/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction11n;
/*    */ import org.jf.util.NibbleUtils;
/*    */ 
/*    */ public class DexBackedInstruction11n extends DexBackedInstruction
/*    */   implements Instruction11n
/*    */ {
/*    */   public DexBackedInstruction11n(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
/*    */   {
/* 45 */     super(dexFile, opcode, instructionStart);
/*    */   }
/*    */ 
/*    */   public int getRegisterA()
/*    */   {
/* 50 */     return NibbleUtils.extractLowUnsignedNibble(this.dexFile.readByte(this.instructionStart + 1));
/*    */   }
/*    */ 
/*    */   public int getNarrowLiteral()
/*    */   {
/* 55 */     return NibbleUtils.extractHighSignedNibble(this.dexFile.readByte(this.instructionStart + 1));
/*    */   }
/*    */   public long getWideLiteral() {
/* 58 */     return getNarrowLiteral();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction11n
 * JD-Core Version:    0.6.0
 */