/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction32x;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction32x extends ImmutableInstruction
/*    */   implements Instruction32x
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format32x;
/*    */   protected final int registerA;
/*    */   protected final int registerB;
/*    */ 
/*    */   public ImmutableInstruction32x(Opcode opcode, int registerA, int registerB)
/*    */   {
/* 50 */     super(opcode);
/* 51 */     this.registerA = Preconditions.checkShortRegister(registerA);
/* 52 */     this.registerB = Preconditions.checkShortRegister(registerB);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction32x of(Instruction32x instruction) {
/* 56 */     if ((instruction instanceof ImmutableInstruction32x)) {
/* 57 */       return (ImmutableInstruction32x)instruction;
/*    */     }
/* 59 */     return new ImmutableInstruction32x(instruction.getOpcode(), instruction.getRegisterA(), instruction.getRegisterB());
/*    */   }
/*    */ 
/*    */   public int getRegisterA()
/*    */   {
/* 65 */     return this.registerA; } 
/* 66 */   public int getRegisterB() { return this.registerB; } 
/*    */   public Format getFormat() {
/* 68 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction32x
 * JD-Core Version:    0.6.0
 */