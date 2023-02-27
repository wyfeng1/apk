/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction10x;
/*    */ 
/*    */ public class ImmutableInstruction10x extends ImmutableInstruction
/*    */   implements Instruction10x
/*    */ {
/* 41 */   public static final Format FORMAT = Format.Format10x;
/*    */ 
/*    */   public ImmutableInstruction10x(Opcode opcode) {
/* 44 */     super(opcode);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction10x of(Instruction10x instruction) {
/* 48 */     if ((instruction instanceof ImmutableInstruction10x)) {
/* 49 */       return (ImmutableInstruction10x)instruction;
/*    */     }
/* 51 */     return new ImmutableInstruction10x(instruction.getOpcode());
/*    */   }
/*    */   public Format getFormat() {
/* 54 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction10x
 * JD-Core Version:    0.6.0
 */