/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderInstruction;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction21s;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class BuilderInstruction21s extends BuilderInstruction
/*    */   implements Instruction21s
/*    */ {
/* 43 */   public static final Format FORMAT = Format.Format21s;
/*    */   protected final int registerA;
/*    */   protected final int literal;
/*    */ 
/*    */   public BuilderInstruction21s(Opcode opcode, int registerA, int literal)
/*    */   {
/* 51 */     super(opcode);
/* 52 */     this.registerA = Preconditions.checkByteRegister(registerA);
/* 53 */     this.literal = Preconditions.checkShortLiteral(literal);
/*    */   }
/*    */   public int getRegisterA() {
/* 56 */     return this.registerA; } 
/* 57 */   public int getNarrowLiteral() { return this.literal; } 
/* 58 */   public long getWideLiteral() { return this.literal; } 
/*    */   public Format getFormat() {
/* 60 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction21s
 * JD-Core Version:    0.6.0
 */