/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderInstruction;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction22x;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class BuilderInstruction22x extends BuilderInstruction
/*    */   implements Instruction22x
/*    */ {
/* 43 */   public static final Format FORMAT = Format.Format22x;
/*    */   protected final int registerA;
/*    */   protected final int registerB;
/*    */ 
/*    */   public BuilderInstruction22x(Opcode opcode, int registerA, int registerB)
/*    */   {
/* 51 */     super(opcode);
/* 52 */     this.registerA = Preconditions.checkByteRegister(registerA);
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
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction22x
 * JD-Core Version:    0.6.0
 */