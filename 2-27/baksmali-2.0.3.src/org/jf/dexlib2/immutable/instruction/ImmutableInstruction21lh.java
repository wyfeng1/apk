/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction21lh;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction21lh extends ImmutableInstruction
/*    */   implements Instruction21lh
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format21lh;
/*    */   protected final int registerA;
/*    */   protected final long literal;
/*    */ 
/*    */   public ImmutableInstruction21lh(Opcode opcode, int registerA, long literal)
/*    */   {
/* 50 */     super(opcode);
/* 51 */     this.registerA = Preconditions.checkByteRegister(registerA);
/* 52 */     this.literal = Preconditions.checkLongHatLiteral(literal);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction21lh of(Instruction21lh instruction) {
/* 56 */     if ((instruction instanceof ImmutableInstruction21lh)) {
/* 57 */       return (ImmutableInstruction21lh)instruction;
/*    */     }
/* 59 */     return new ImmutableInstruction21lh(instruction.getOpcode(), instruction.getRegisterA(), instruction.getWideLiteral());
/*    */   }
/*    */ 
/*    */   public int getRegisterA()
/*    */   {
/* 65 */     return this.registerA; } 
/* 66 */   public long getWideLiteral() { return this.literal; }
/*    */ 
/*    */   public Format getFormat() {
/* 69 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction21lh
 * JD-Core Version:    0.6.0
 */