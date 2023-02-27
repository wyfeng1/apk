/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction23x;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction23x extends ImmutableInstruction
/*    */   implements Instruction23x
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format23x;
/*    */   protected final int registerA;
/*    */   protected final int registerB;
/*    */   protected final int registerC;
/*    */ 
/*    */   public ImmutableInstruction23x(Opcode opcode, int registerA, int registerB, int registerC)
/*    */   {
/* 52 */     super(opcode);
/* 53 */     this.registerA = Preconditions.checkByteRegister(registerA);
/* 54 */     this.registerB = Preconditions.checkByteRegister(registerB);
/* 55 */     this.registerC = Preconditions.checkByteRegister(registerC);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction23x of(Instruction23x instruction) {
/* 59 */     if ((instruction instanceof ImmutableInstruction23x)) {
/* 60 */       return (ImmutableInstruction23x)instruction;
/*    */     }
/* 62 */     return new ImmutableInstruction23x(instruction.getOpcode(), instruction.getRegisterA(), instruction.getRegisterB(), instruction.getRegisterC());
/*    */   }
/*    */ 
/*    */   public int getRegisterA()
/*    */   {
/* 69 */     return this.registerA; } 
/* 70 */   public int getRegisterB() { return this.registerB; } 
/* 71 */   public int getRegisterC() { return this.registerC; } 
/*    */   public Format getFormat() {
/* 73 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction23x
 * JD-Core Version:    0.6.0
 */