/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderInstruction;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction22b;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class BuilderInstruction22b extends BuilderInstruction
/*    */   implements Instruction22b
/*    */ {
/* 43 */   public static final Format FORMAT = Format.Format22b;
/*    */   protected final int registerA;
/*    */   protected final int registerB;
/*    */   protected final int literal;
/*    */ 
/*    */   public BuilderInstruction22b(Opcode opcode, int registerA, int registerB, int literal)
/*    */   {
/* 53 */     super(opcode);
/* 54 */     this.registerA = Preconditions.checkByteRegister(registerA);
/* 55 */     this.registerB = Preconditions.checkByteRegister(registerB);
/* 56 */     this.literal = Preconditions.checkByteLiteral(literal);
/*    */   }
/*    */   public int getRegisterA() {
/* 59 */     return this.registerA; } 
/* 60 */   public int getRegisterB() { return this.registerB; } 
/* 61 */   public int getNarrowLiteral() { return this.literal; } 
/* 62 */   public long getWideLiteral() { return this.literal; } 
/*    */   public Format getFormat() {
/* 64 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction22b
 * JD-Core Version:    0.6.0
 */