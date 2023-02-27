/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderInstruction;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction20bc;
/*    */ import org.jf.dexlib2.iface.reference.Reference;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class BuilderInstruction20bc extends BuilderInstruction
/*    */   implements Instruction20bc
/*    */ {
/* 45 */   public static final Format FORMAT = Format.Format20bc;
/*    */   protected final int verificationError;
/*    */   protected final Reference reference;
/*    */ 
/*    */   public BuilderInstruction20bc(Opcode opcode, int verificationError, Reference reference)
/*    */   {
/* 53 */     super(opcode);
/* 54 */     this.verificationError = Preconditions.checkVerificationError(verificationError);
/* 55 */     this.reference = reference;
/*    */   }
/*    */   public int getVerificationError() {
/* 58 */     return this.verificationError; } 
/* 59 */   public Reference getReference() { return this.reference; }
/*    */ 
/*    */   public Format getFormat() {
/* 62 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction20bc
 * JD-Core Version:    0.6.0
 */