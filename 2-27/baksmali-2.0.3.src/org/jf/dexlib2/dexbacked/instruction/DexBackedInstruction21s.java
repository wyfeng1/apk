/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction21s;
/*    */ 
/*    */ public class DexBackedInstruction21s extends DexBackedInstruction
/*    */   implements Instruction21s
/*    */ {
/*    */   public DexBackedInstruction21s(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
/*    */   {
/* 44 */     super(dexFile, opcode, instructionStart);
/*    */   }
/*    */   public int getRegisterA() {
/* 47 */     return this.dexFile.readUbyte(this.instructionStart + 1); } 
/* 48 */   public int getNarrowLiteral() { return this.dexFile.readShort(this.instructionStart + 2); } 
/* 49 */   public long getWideLiteral() { return getNarrowLiteral();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction21s
 * JD-Core Version:    0.6.0
 */