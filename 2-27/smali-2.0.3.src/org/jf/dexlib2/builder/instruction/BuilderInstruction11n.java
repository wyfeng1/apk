/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderInstruction;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction11n;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class BuilderInstruction11n extends BuilderInstruction
/*    */   implements Instruction11n
/*    */ {
/* 43 */   public static final Format FORMAT = Format.Format11n;
/*    */   protected final int registerA;
/*    */   protected final int literal;
/*    */ 
/*    */   public BuilderInstruction11n(Opcode opcode, int registerA, int literal)
/*    */   {
/* 51 */     super(opcode);
/* 52 */     this.registerA = Preconditions.checkNibbleRegister(registerA);
/* 53 */     this.literal = Preconditions.checkNibbleLiteral(literal);
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
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction11n
 * JD-Core Version:    0.6.0
 */