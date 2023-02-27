/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderInstruction;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction31i;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class BuilderInstruction31i extends BuilderInstruction
/*    */   implements Instruction31i
/*    */ {
/* 43 */   public static final Format FORMAT = Format.Format31i;
/*    */   protected final int registerA;
/*    */   protected final int literal;
/*    */ 
/*    */   public BuilderInstruction31i(Opcode opcode, int registerA, int literal)
/*    */   {
/* 51 */     super(opcode);
/* 52 */     this.registerA = Preconditions.checkByteRegister(registerA);
/* 53 */     this.literal = literal;
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
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction31i
 * JD-Core Version:    0.6.0
 */