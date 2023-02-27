/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction22cs;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction22cs extends ImmutableInstruction
/*    */   implements Instruction22cs
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format22cs;
/*    */   protected final int registerA;
/*    */   protected final int registerB;
/*    */   protected final int fieldOffset;
/*    */ 
/*    */   public ImmutableInstruction22cs(Opcode opcode, int registerA, int registerB, int fieldOffset)
/*    */   {
/* 52 */     super(opcode);
/* 53 */     this.registerA = Preconditions.checkNibbleRegister(registerA);
/* 54 */     this.registerB = Preconditions.checkNibbleRegister(registerB);
/* 55 */     this.fieldOffset = Preconditions.checkFieldOffset(fieldOffset);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction22cs of(Instruction22cs instruction) {
/* 59 */     if ((instruction instanceof ImmutableInstruction22cs)) {
/* 60 */       return (ImmutableInstruction22cs)instruction;
/*    */     }
/* 62 */     return new ImmutableInstruction22cs(instruction.getOpcode(), instruction.getRegisterA(), instruction.getRegisterB(), instruction.getFieldOffset());
/*    */   }
/*    */ 
/*    */   public int getRegisterA()
/*    */   {
/* 69 */     return this.registerA; } 
/* 70 */   public int getRegisterB() { return this.registerB; } 
/* 71 */   public int getFieldOffset() { return this.fieldOffset; } 
/*    */   public Format getFormat() {
/* 73 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction22cs
 * JD-Core Version:    0.6.0
 */