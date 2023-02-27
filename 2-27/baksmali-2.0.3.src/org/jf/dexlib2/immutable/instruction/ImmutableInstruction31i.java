/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction31i;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction31i extends ImmutableInstruction
/*    */   implements Instruction31i
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format31i;
/*    */   protected final int registerA;
/*    */   protected final int literal;
/*    */ 
/*    */   public ImmutableInstruction31i(Opcode opcode, int registerA, int literal)
/*    */   {
/* 50 */     super(opcode);
/* 51 */     this.registerA = Preconditions.checkByteRegister(registerA);
/* 52 */     this.literal = literal;
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction31i of(Instruction31i instruction) {
/* 56 */     if ((instruction instanceof ImmutableInstruction31i)) {
/* 57 */       return (ImmutableInstruction31i)instruction;
/*    */     }
/* 59 */     return new ImmutableInstruction31i(instruction.getOpcode(), instruction.getRegisterA(), instruction.getNarrowLiteral());
/*    */   }
/*    */ 
/*    */   public int getRegisterA()
/*    */   {
/* 65 */     return this.registerA; } 
/* 66 */   public int getNarrowLiteral() { return this.literal; } 
/* 67 */   public long getWideLiteral() { return this.literal; } 
/*    */   public Format getFormat() {
/* 69 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction31i
 * JD-Core Version:    0.6.0
 */