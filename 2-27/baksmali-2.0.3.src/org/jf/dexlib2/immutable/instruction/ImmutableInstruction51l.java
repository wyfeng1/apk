/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction51l;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction51l extends ImmutableInstruction
/*    */   implements Instruction51l
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format51l;
/*    */   protected final int registerA;
/*    */   protected final long literal;
/*    */ 
/*    */   public ImmutableInstruction51l(Opcode opcode, int registerA, long literal)
/*    */   {
/* 50 */     super(opcode);
/* 51 */     this.registerA = Preconditions.checkByteRegister(registerA);
/* 52 */     this.literal = literal;
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction51l of(Instruction51l instruction) {
/* 56 */     if ((instruction instanceof ImmutableInstruction51l)) {
/* 57 */       return (ImmutableInstruction51l)instruction;
/*    */     }
/* 59 */     return new ImmutableInstruction51l(instruction.getOpcode(), instruction.getRegisterA(), instruction.getWideLiteral());
/*    */   }
/*    */ 
/*    */   public int getRegisterA()
/*    */   {
/* 65 */     return this.registerA; } 
/* 66 */   public long getWideLiteral() { return this.literal; } 
/*    */   public Format getFormat() {
/* 68 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction51l
 * JD-Core Version:    0.6.0
 */