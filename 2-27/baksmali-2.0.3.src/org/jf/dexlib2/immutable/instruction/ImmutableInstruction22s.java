/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction22s;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction22s extends ImmutableInstruction
/*    */   implements Instruction22s
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format22s;
/*    */   protected final int registerA;
/*    */   protected final int registerB;
/*    */   protected final int literal;
/*    */ 
/*    */   public ImmutableInstruction22s(Opcode opcode, int registerA, int registerB, int literal)
/*    */   {
/* 52 */     super(opcode);
/* 53 */     this.registerA = Preconditions.checkNibbleRegister(registerA);
/* 54 */     this.registerB = Preconditions.checkNibbleRegister(registerB);
/* 55 */     this.literal = Preconditions.checkShortLiteral(literal);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction22s of(Instruction22s instruction) {
/* 59 */     if ((instruction instanceof ImmutableInstruction22s)) {
/* 60 */       return (ImmutableInstruction22s)instruction;
/*    */     }
/* 62 */     return new ImmutableInstruction22s(instruction.getOpcode(), instruction.getRegisterA(), instruction.getRegisterB(), instruction.getNarrowLiteral());
/*    */   }
/*    */ 
/*    */   public int getRegisterA()
/*    */   {
/* 69 */     return this.registerA; } 
/* 70 */   public int getRegisterB() { return this.registerB; } 
/* 71 */   public int getNarrowLiteral() { return this.literal; } 
/* 72 */   public long getWideLiteral() { return this.literal; } 
/*    */   public Format getFormat() {
/* 74 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction22s
 * JD-Core Version:    0.6.0
 */