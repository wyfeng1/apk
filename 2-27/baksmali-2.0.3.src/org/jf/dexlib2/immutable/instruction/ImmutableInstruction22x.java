/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction22x;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction22x extends ImmutableInstruction
/*    */   implements Instruction22x
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format22x;
/*    */   protected final int registerA;
/*    */   protected final int registerB;
/*    */ 
/*    */   public ImmutableInstruction22x(Opcode opcode, int registerA, int registerB)
/*    */   {
/* 50 */     super(opcode);
/* 51 */     this.registerA = Preconditions.checkByteRegister(registerA);
/* 52 */     this.registerB = Preconditions.checkShortRegister(registerB);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction22x of(Instruction22x instruction) {
/* 56 */     if ((instruction instanceof ImmutableInstruction22x)) {
/* 57 */       return (ImmutableInstruction22x)instruction;
/*    */     }
/* 59 */     return new ImmutableInstruction22x(instruction.getOpcode(), instruction.getRegisterA(), instruction.getRegisterB());
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
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction22x
 * JD-Core Version:    0.6.0
 */