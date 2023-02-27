/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderInstruction;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction32x;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class BuilderInstruction32x extends BuilderInstruction
/*    */   implements Instruction32x
/*    */ {
/* 43 */   public static final Format FORMAT = Format.Format32x;
/*    */   protected final int registerA;
/*    */   protected final int registerB;
/*    */ 
/*    */   public BuilderInstruction32x(Opcode opcode, int registerA, int registerB)
/*    */   {
/* 51 */     super(opcode);
/* 52 */     this.registerA = Preconditions.checkShortRegister(registerA);
/* 53 */     this.registerB = Preconditions.checkShortRegister(registerB);
/*    */   }
/*    */   public int getRegisterA() {
/* 56 */     return this.registerA; } 
/* 57 */   public int getRegisterB() { return this.registerB; } 
/*    */   public Format getFormat() {
/* 59 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction32x
 * JD-Core Version:    0.6.0
 */