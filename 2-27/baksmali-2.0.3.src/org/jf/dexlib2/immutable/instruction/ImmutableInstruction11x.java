/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction11x;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction11x extends ImmutableInstruction
/*    */   implements Instruction11x
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format11x;
/*    */   protected final int registerA;
/*    */ 
/*    */   public ImmutableInstruction11x(Opcode opcode, int registerA)
/*    */   {
/* 48 */     super(opcode);
/* 49 */     this.registerA = Preconditions.checkByteRegister(registerA);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction11x of(Instruction11x instruction) {
/* 53 */     if ((instruction instanceof ImmutableInstruction11x)) {
/* 54 */       return (ImmutableInstruction11x)instruction;
/*    */     }
/* 56 */     return new ImmutableInstruction11x(instruction.getOpcode(), instruction.getRegisterA());
/*    */   }
/*    */ 
/*    */   public int getRegisterA()
/*    */   {
/* 61 */     return this.registerA;
/*    */   }
/* 63 */   public Format getFormat() { return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction11x
 * JD-Core Version:    0.6.0
 */