/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction11n;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction11n extends ImmutableInstruction
/*    */   implements Instruction11n
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format11n;
/*    */   protected final int registerA;
/*    */   protected final int literal;
/*    */ 
/*    */   public ImmutableInstruction11n(Opcode opcode, int registerA, int literal)
/*    */   {
/* 50 */     super(opcode);
/* 51 */     this.registerA = Preconditions.checkNibbleRegister(registerA);
/* 52 */     this.literal = Preconditions.checkNibbleLiteral(literal);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction11n of(Instruction11n instruction) {
/* 56 */     if ((instruction instanceof ImmutableInstruction11n)) {
/* 57 */       return (ImmutableInstruction11n)instruction;
/*    */     }
/* 59 */     return new ImmutableInstruction11n(instruction.getOpcode(), instruction.getRegisterA(), instruction.getNarrowLiteral());
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
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction11n
 * JD-Core Version:    0.6.0
 */