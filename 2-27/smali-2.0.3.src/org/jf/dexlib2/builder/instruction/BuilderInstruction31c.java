/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderInstruction;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction31c;
/*    */ import org.jf.dexlib2.iface.reference.Reference;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class BuilderInstruction31c extends BuilderInstruction
/*    */   implements Instruction31c
/*    */ {
/* 44 */   public static final Format FORMAT = Format.Format31c;
/*    */   protected final int registerA;
/*    */   protected final Reference reference;
/*    */ 
/*    */   public BuilderInstruction31c(Opcode opcode, int registerA, Reference reference)
/*    */   {
/* 52 */     super(opcode);
/* 53 */     this.registerA = Preconditions.checkByteRegister(registerA);
/* 54 */     this.reference = reference;
/*    */   }
/*    */   public int getRegisterA() {
/* 57 */     return this.registerA; } 
/* 58 */   public Reference getReference() { return this.reference; }
/*    */ 
/*    */   public Format getFormat() {
/* 61 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction31c
 * JD-Core Version:    0.6.0
 */