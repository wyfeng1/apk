/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction22t;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction22t extends ImmutableInstruction
/*    */   implements Instruction22t
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format22t;
/*    */   protected final int registerA;
/*    */   protected final int registerB;
/*    */   protected final int codeOffset;
/*    */ 
/*    */   public ImmutableInstruction22t(Opcode opcode, int registerA, int registerB, int codeOffset)
/*    */   {
/* 52 */     super(opcode);
/* 53 */     this.registerA = Preconditions.checkNibbleRegister(registerA);
/* 54 */     this.registerB = Preconditions.checkNibbleRegister(registerB);
/* 55 */     this.codeOffset = Preconditions.checkShortCodeOffset(codeOffset);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction22t of(Instruction22t instruction) {
/* 59 */     if ((instruction instanceof ImmutableInstruction22t)) {
/* 60 */       return (ImmutableInstruction22t)instruction;
/*    */     }
/* 62 */     return new ImmutableInstruction22t(instruction.getOpcode(), instruction.getRegisterA(), instruction.getRegisterB(), instruction.getCodeOffset());
/*    */   }
/*    */ 
/*    */   public int getRegisterA()
/*    */   {
/* 69 */     return this.registerA; } 
/* 70 */   public int getRegisterB() { return this.registerB; } 
/* 71 */   public int getCodeOffset() { return this.codeOffset; } 
/*    */   public Format getFormat() {
/* 73 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction22t
 * JD-Core Version:    0.6.0
 */