/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction22b;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction22b extends ImmutableInstruction
/*    */   implements Instruction22b
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format22b;
/*    */   protected final int registerA;
/*    */   protected final int registerB;
/*    */   protected final int literal;
/*    */ 
/*    */   public ImmutableInstruction22b(Opcode opcode, int registerA, int registerB, int literal)
/*    */   {
/* 52 */     super(opcode);
/* 53 */     this.registerA = Preconditions.checkByteRegister(registerA);
/* 54 */     this.registerB = Preconditions.checkByteRegister(registerB);
/* 55 */     this.literal = Preconditions.checkByteLiteral(literal);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction22b of(Instruction22b instruction) {
/* 59 */     if ((instruction instanceof ImmutableInstruction22b)) {
/* 60 */       return (ImmutableInstruction22b)instruction;
/*    */     }
/* 62 */     return new ImmutableInstruction22b(instruction.getOpcode(), instruction.getRegisterA(), instruction.getRegisterB(), instruction.getNarrowLiteral());
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
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction22b
 * JD-Core Version:    0.6.0
 */