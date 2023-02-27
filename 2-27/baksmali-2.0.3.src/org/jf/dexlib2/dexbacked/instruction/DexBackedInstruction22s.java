/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction22s;
/*    */ import org.jf.util.NibbleUtils;
/*    */ 
/*    */ public class DexBackedInstruction22s extends DexBackedInstruction
/*    */   implements Instruction22s
/*    */ {
/*    */   public DexBackedInstruction22s(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
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
/*    */   public int getNarrowLiteral() {
/* 58 */     return this.dexFile.readShort(this.instructionStart + 2); } 
/* 59 */   public long getWideLiteral() { return getNarrowLiteral();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction22s
 * JD-Core Version:    0.6.0
 */