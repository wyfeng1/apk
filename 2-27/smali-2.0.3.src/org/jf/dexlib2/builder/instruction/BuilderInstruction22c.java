/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderInstruction;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction22c;
/*    */ import org.jf.dexlib2.iface.reference.Reference;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class BuilderInstruction22c extends BuilderInstruction
/*    */   implements Instruction22c
/*    */ {
/* 44 */   public static final Format FORMAT = Format.Format22c;
/*    */   protected final int registerA;
/*    */   protected final int registerB;
/*    */   protected final Reference reference;
/*    */ 
/*    */   public BuilderInstruction22c(Opcode opcode, int registerA, int registerB, Reference reference)
/*    */   {
/* 54 */     super(opcode);
/* 55 */     this.registerA = Preconditions.checkNibbleRegister(registerA);
/* 56 */     this.registerB = Preconditions.checkNibbleRegister(registerB);
/* 57 */     this.reference = reference;
/*    */   }
/*    */   public int getRegisterA() {
/* 60 */     return this.registerA; } 
/* 61 */   public int getRegisterB() { return this.registerB; } 
/* 62 */   public Reference getReference() { return this.reference; }
/*    */ 
/*    */   public Format getFormat() {
/* 65 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction22c
 * JD-Core Version:    0.6.0
 */