/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction12x;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction12x extends ImmutableInstruction
/*    */   implements Instruction12x
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format12x;
/*    */   protected final int registerA;
/*    */   protected final int registerB;
/*    */ 
/*    */   public ImmutableInstruction12x(Opcode opcode, int registerA, int registerB)
/*    */   {
/* 50 */     super(opcode);
/* 51 */     this.registerA = Preconditions.checkNibbleRegister(registerA);
/* 52 */     this.registerB = Preconditions.checkNibbleRegister(registerB);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction12x of(Instruction12x instruction) {
/* 56 */     if ((instruction instanceof ImmutableInstruction12x)) {
/* 57 */       return (ImmutableInstruction12x)instruction;
/*    */     }
/* 59 */     return new ImmutableInstruction12x(instruction.getOpcode(), instruction.getRegisterA(), instruction.getRegisterB());
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
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction12x
 * JD-Core Version:    0.6.0
 */