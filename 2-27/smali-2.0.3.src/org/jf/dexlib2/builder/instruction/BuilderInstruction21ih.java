/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderInstruction;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction21ih;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class BuilderInstruction21ih extends BuilderInstruction
/*    */   implements Instruction21ih
/*    */ {
/* 43 */   public static final Format FORMAT = Format.Format21ih;
/*    */   protected final int registerA;
/*    */   protected final int literal;
/*    */ 
/*    */   public BuilderInstruction21ih(Opcode opcode, int registerA, int literal)
/*    */   {
/* 51 */     super(opcode);
/* 52 */     this.registerA = Preconditions.checkByteRegister(registerA);
/* 53 */     this.literal = Preconditions.checkIntegerHatLiteral(literal);
/*    */   }
/*    */   public int getRegisterA() {
/* 56 */     return this.registerA; } 
/* 57 */   public int getNarrowLiteral() { return this.literal; } 
/* 58 */   public long getWideLiteral() { return this.literal; } 
/* 59 */   public short getHatLiteral() { return (short)(this.literal >>> 16); } 
/*    */   public Format getFormat() {
/* 61 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction21ih
 * JD-Core Version:    0.6.0
 */