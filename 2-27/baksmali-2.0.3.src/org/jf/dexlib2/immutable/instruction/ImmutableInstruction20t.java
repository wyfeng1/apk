/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction20t;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction20t extends ImmutableInstruction
/*    */   implements Instruction20t
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format20t;
/*    */   protected final int codeOffset;
/*    */ 
/*    */   public ImmutableInstruction20t(Opcode opcode, int codeOffset)
/*    */   {
/* 48 */     super(opcode);
/* 49 */     this.codeOffset = Preconditions.checkShortCodeOffset(codeOffset);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction20t of(Instruction20t instruction) {
/* 53 */     if ((instruction instanceof ImmutableInstruction20t)) {
/* 54 */       return (ImmutableInstruction20t)instruction;
/*    */     }
/* 56 */     return new ImmutableInstruction20t(instruction.getOpcode(), instruction.getCodeOffset());
/*    */   }
/*    */ 
/*    */   public int getCodeOffset()
/*    */   {
/* 61 */     return this.codeOffset; } 
/* 62 */   public Format getFormat() { return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction20t
 * JD-Core Version:    0.6.0
 */