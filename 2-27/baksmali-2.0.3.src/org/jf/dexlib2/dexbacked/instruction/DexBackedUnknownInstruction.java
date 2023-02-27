/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.iface.instruction.formats.UnknownInstruction;
/*    */ 
/*    */ public class DexBackedUnknownInstruction extends DexBackedInstruction
/*    */   implements UnknownInstruction
/*    */ {
/*    */   public DexBackedUnknownInstruction(DexBackedDexFile dexFile, int instructionStart)
/*    */   {
/* 43 */     super(dexFile, Opcode.NOP, instructionStart);
/*    */   }
/*    */ 
/*    */   public int getOriginalOpcode() {
/* 47 */     int opcode = this.dexFile.readUbyte(this.instructionStart);
/* 48 */     if (opcode == 0) {
/* 49 */       opcode = this.dexFile.readUshort(this.instructionStart);
/*    */     }
/*    */ 
/* 52 */     return opcode;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedUnknownInstruction
 * JD-Core Version:    0.6.0
 */