/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderInstruction;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction23x;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class BuilderInstruction23x extends BuilderInstruction
/*    */   implements Instruction23x
/*    */ {
/* 43 */   public static final Format FORMAT = Format.Format23x;
/*    */   protected final int registerA;
/*    */   protected final int registerB;
/*    */   protected final int registerC;
/*    */ 
/*    */   public BuilderInstruction23x(Opcode opcode, int registerA, int registerB, int registerC)
/*    */   {
/* 53 */     super(opcode);
/* 54 */     this.registerA = Preconditions.checkByteRegister(registerA);
/* 55 */     this.registerB = Preconditions.checkByteRegister(registerB);
/* 56 */     this.registerC = Preconditions.checkByteRegister(registerC);
/*    */   }
/*    */   public int getRegisterA() {
/* 59 */     return this.registerA; } 
/* 60 */   public int getRegisterB() { return this.registerB; } 
/* 61 */   public int getRegisterC() { return this.registerC; } 
/*    */   public Format getFormat() {
/* 63 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction23x
 * JD-Core Version:    0.6.0
 */