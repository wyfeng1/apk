/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction22b;
/*    */ 
/*    */ public class DexBackedInstruction22b extends DexBackedInstruction
/*    */   implements Instruction22b
/*    */ {
/*    */   public DexBackedInstruction22b(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
/*    */   {
/* 44 */     super(dexFile, opcode, instructionStart);
/*    */   }
/*    */   public int getRegisterA() {
/* 47 */     return this.dexFile.readUbyte(this.instructionStart + 1); } 
/* 48 */   public int getRegisterB() { return this.dexFile.readUbyte(this.instructionStart + 2); } 
/* 49 */   public int getNarrowLiteral() { return this.dexFile.readByte(this.instructionStart + 3); } 
/* 50 */   public long getWideLiteral() { return getNarrowLiteral();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction22b
 * JD-Core Version:    0.6.0
 */