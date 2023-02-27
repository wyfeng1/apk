/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderInstruction;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction21lh;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class BuilderInstruction21lh extends BuilderInstruction
/*    */   implements Instruction21lh
/*    */ {
/* 43 */   public static final Format FORMAT = Format.Format21lh;
/*    */   protected final int registerA;
/*    */   protected final long literal;
/*    */ 
/*    */   public BuilderInstruction21lh(Opcode opcode, int registerA, long literal)
/*    */   {
/* 51 */     super(opcode);
/* 52 */     this.registerA = Preconditions.checkByteRegister(registerA);
/* 53 */     this.literal = Preconditions.checkLongHatLiteral(literal);
/*    */   }
/*    */   public int getRegisterA() {
/* 56 */     return this.registerA; } 
/* 57 */   public long getWideLiteral() { return this.literal; } 
/* 58 */   public short getHatLiteral() { return (short)(int)(this.literal >>> 48); } 
/*    */   public Format getFormat() {
/* 60 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction21lh
 * JD-Core Version:    0.6.0
 */