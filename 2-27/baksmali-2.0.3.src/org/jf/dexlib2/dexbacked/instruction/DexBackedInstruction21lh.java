/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction21lh;
/*    */ 
/*    */ public class DexBackedInstruction21lh extends DexBackedInstruction
/*    */   implements Instruction21lh
/*    */ {
/*    */   public DexBackedInstruction21lh(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
/*    */   {
/* 44 */     super(dexFile, opcode, instructionStart);
/*    */   }
/*    */   public int getRegisterA() {
/* 47 */     return this.dexFile.readUbyte(this.instructionStart + 1); } 
/* 48 */   public long getWideLiteral() { return getHatLiteral() << 48; } 
/* 49 */   public short getHatLiteral() { return (short)this.dexFile.readShort(this.instructionStart + 2);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction21lh
 * JD-Core Version:    0.6.0
 */