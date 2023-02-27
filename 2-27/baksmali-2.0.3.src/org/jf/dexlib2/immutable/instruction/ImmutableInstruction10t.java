/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction10t;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction10t extends ImmutableInstruction
/*    */   implements Instruction10t
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format10t;
/*    */   protected final int codeOffset;
/*    */ 
/*    */   public ImmutableInstruction10t(Opcode opcode, int codeOffset)
/*    */   {
/* 48 */     super(opcode);
/* 49 */     this.codeOffset = Preconditions.checkByteCodeOffset(codeOffset);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction10t of(Instruction10t instruction) {
/* 53 */     if ((instruction instanceof ImmutableInstruction10t)) {
/* 54 */       return (ImmutableInstruction10t)instruction;
/*    */     }
/* 56 */     return new ImmutableInstruction10t(instruction.getOpcode(), instruction.getCodeOffset());
/*    */   }
/*    */ 
/*    */   public int getCodeOffset()
/*    */   {
/* 61 */     return this.codeOffset; } 
/* 62 */   public Format getFormat() { return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction10t
 * JD-Core Version:    0.6.0
 */