/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction31t;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction31t extends ImmutableInstruction
/*    */   implements Instruction31t
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format31t;
/*    */   protected final int registerA;
/*    */   protected final int codeOffset;
/*    */ 
/*    */   public ImmutableInstruction31t(Opcode opcode, int registerA, int codeOffset)
/*    */   {
/* 50 */     super(opcode);
/* 51 */     this.registerA = Preconditions.checkByteRegister(registerA);
/* 52 */     this.codeOffset = codeOffset;
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction31t of(Instruction31t instruction) {
/* 56 */     if ((instruction instanceof ImmutableInstruction31t)) {
/* 57 */       return (ImmutableInstruction31t)instruction;
/*    */     }
/* 59 */     return new ImmutableInstruction31t(instruction.getOpcode(), instruction.getRegisterA(), instruction.getCodeOffset());
/*    */   }
/*    */ 
/*    */   public int getRegisterA()
/*    */   {
/* 65 */     return this.registerA; } 
/* 66 */   public int getCodeOffset() { return this.codeOffset; } 
/*    */   public Format getFormat() {
/* 68 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction31t
 * JD-Core Version:    0.6.0
 */