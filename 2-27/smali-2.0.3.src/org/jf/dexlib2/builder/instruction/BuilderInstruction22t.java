/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderOffsetInstruction;
/*    */ import org.jf.dexlib2.builder.Label;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction22t;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class BuilderInstruction22t extends BuilderOffsetInstruction
/*    */   implements Instruction22t
/*    */ {
/* 44 */   public static final Format FORMAT = Format.Format22t;
/*    */   protected final int registerA;
/*    */   protected final int registerB;
/*    */ 
/*    */   public BuilderInstruction22t(Opcode opcode, int registerA, int registerB, Label target)
/*    */   {
/* 53 */     super(opcode, target);
/* 54 */     this.registerA = Preconditions.checkNibbleRegister(registerA);
/* 55 */     this.registerB = Preconditions.checkNibbleRegister(registerB);
/*    */   }
/*    */   public int getRegisterA() {
/* 58 */     return this.registerA; } 
/* 59 */   public int getRegisterB() { return this.registerB; } 
/*    */   public Format getFormat() {
/* 61 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction22t
 * JD-Core Version:    0.6.0
 */