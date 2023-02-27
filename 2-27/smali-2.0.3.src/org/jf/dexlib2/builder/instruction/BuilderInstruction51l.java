/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderInstruction;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction51l;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class BuilderInstruction51l extends BuilderInstruction
/*    */   implements Instruction51l
/*    */ {
/* 43 */   public static final Format FORMAT = Format.Format51l;
/*    */   protected final int registerA;
/*    */   protected final long literal;
/*    */ 
/*    */   public BuilderInstruction51l(Opcode opcode, int registerA, long literal)
/*    */   {
/* 51 */     super(opcode);
/* 52 */     this.registerA = Preconditions.checkByteRegister(registerA);
/* 53 */     this.literal = literal;
/*    */   }
/*    */   public int getRegisterA() {
/* 56 */     return this.registerA; } 
/* 57 */   public long getWideLiteral() { return this.literal; } 
/*    */   public Format getFormat() {
/* 59 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction51l
 * JD-Core Version:    0.6.0
 */