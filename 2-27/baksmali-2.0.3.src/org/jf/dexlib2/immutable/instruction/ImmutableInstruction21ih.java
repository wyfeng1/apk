/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction21ih;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction21ih extends ImmutableInstruction
/*    */   implements Instruction21ih
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format21ih;
/*    */   protected final int registerA;
/*    */   protected final int literal;
/*    */ 
/*    */   public ImmutableInstruction21ih(Opcode opcode, int registerA, int literal)
/*    */   {
/* 50 */     super(opcode);
/* 51 */     this.registerA = Preconditions.checkByteRegister(registerA);
/* 52 */     this.literal = Preconditions.checkIntegerHatLiteral(literal);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction21ih of(Instruction21ih instruction) {
/* 56 */     if ((instruction instanceof ImmutableInstruction21ih)) {
/* 57 */       return (ImmutableInstruction21ih)instruction;
/*    */     }
/* 59 */     return new ImmutableInstruction21ih(instruction.getOpcode(), instruction.getRegisterA(), instruction.getNarrowLiteral());
/*    */   }
/*    */ 
/*    */   public int getRegisterA()
/*    */   {
/* 65 */     return this.registerA; } 
/* 66 */   public int getNarrowLiteral() { return this.literal; } 
/* 67 */   public long getWideLiteral() { return this.literal; }
/*    */ 
/*    */   public Format getFormat() {
/* 70 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction21ih
 * JD-Core Version:    0.6.0
 */