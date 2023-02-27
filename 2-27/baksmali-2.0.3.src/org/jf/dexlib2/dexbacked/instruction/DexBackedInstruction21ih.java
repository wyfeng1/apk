/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction21ih;
/*    */ 
/*    */ public class DexBackedInstruction21ih extends DexBackedInstruction
/*    */   implements Instruction21ih
/*    */ {
/*    */   public DexBackedInstruction21ih(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
/*    */   {
/* 44 */     super(dexFile, opcode, instructionStart);
/*    */   }
/*    */   public int getRegisterA() {
/* 47 */     return this.dexFile.readUbyte(this.instructionStart + 1); } 
/* 48 */   public int getNarrowLiteral() { return getHatLiteral() << 16; } 
/* 49 */   public long getWideLiteral() { return getNarrowLiteral(); } 
/* 50 */   public short getHatLiteral() { return (short)this.dexFile.readShort(this.instructionStart + 2);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction21ih
 * JD-Core Version:    0.6.0
 */