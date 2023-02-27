/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction30t;
/*    */ 
/*    */ public class ImmutableInstruction30t extends ImmutableInstruction
/*    */   implements Instruction30t
/*    */ {
/* 41 */   public static final Format FORMAT = Format.Format30t;
/*    */   protected final int codeOffset;
/*    */ 
/*    */   public ImmutableInstruction30t(Opcode opcode, int codeOffset)
/*    */   {
/* 47 */     super(opcode);
/* 48 */     this.codeOffset = codeOffset;
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction30t of(Instruction30t instruction) {
/* 52 */     if ((instruction instanceof ImmutableInstruction30t)) {
/* 53 */       return (ImmutableInstruction30t)instruction;
/*    */     }
/* 55 */     return new ImmutableInstruction30t(instruction.getOpcode(), instruction.getCodeOffset());
/*    */   }
/*    */ 
/*    */   public int getCodeOffset()
/*    */   {
/* 60 */     return this.codeOffset; } 
/* 61 */   public Format getFormat() { return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction30t
 * JD-Core Version:    0.6.0
 */