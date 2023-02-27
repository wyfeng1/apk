/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction21s;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction21s extends ImmutableInstruction
/*    */   implements Instruction21s
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format21s;
/*    */   protected final int registerA;
/*    */   protected final int literal;
/*    */ 
/*    */   public ImmutableInstruction21s(Opcode opcode, int registerA, int literal)
/*    */   {
/* 50 */     super(opcode);
/* 51 */     this.registerA = Preconditions.checkByteRegister(registerA);
/* 52 */     this.literal = Preconditions.checkShortLiteral(literal);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction21s of(Instruction21s instruction) {
/* 56 */     if ((instruction instanceof ImmutableInstruction21s)) {
/* 57 */       return (ImmutableInstruction21s)instruction;
/*    */     }
/* 59 */     return new ImmutableInstruction21s(instruction.getOpcode(), instruction.getRegisterA(), instruction.getNarrowLiteral());
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
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction21s
 * JD-Core Version:    0.6.0
 */