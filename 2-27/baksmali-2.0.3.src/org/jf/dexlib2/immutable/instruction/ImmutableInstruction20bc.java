/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.ReferenceType;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction20bc;
/*    */ import org.jf.dexlib2.iface.reference.Reference;
/*    */ import org.jf.dexlib2.immutable.reference.ImmutableReference;
/*    */ import org.jf.dexlib2.immutable.reference.ImmutableReferenceFactory;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction20bc extends ImmutableInstruction
/*    */   implements Instruction20bc
/*    */ {
/* 46 */   public static final Format FORMAT = Format.Format20bc;
/*    */   protected final int verificationError;
/*    */   protected final ImmutableReference reference;
/*    */ 
/*    */   public ImmutableInstruction20bc(Opcode opcode, int verificationError, Reference reference)
/*    */   {
/* 54 */     super(opcode);
/* 55 */     this.verificationError = Preconditions.checkVerificationError(verificationError);
/* 56 */     this.reference = ImmutableReferenceFactory.of(opcode.referenceType, reference);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction20bc of(Instruction20bc instruction) {
/* 60 */     if ((instruction instanceof ImmutableInstruction20bc)) {
/* 61 */       return (ImmutableInstruction20bc)instruction;
/*    */     }
/* 63 */     return new ImmutableInstruction20bc(instruction.getOpcode(), instruction.getVerificationError(), instruction.getReference());
/*    */   }
/*    */ 
/*    */   public int getVerificationError()
/*    */   {
/* 69 */     return this.verificationError; } 
/* 70 */   public ImmutableReference getReference() { return this.reference; } 
/* 71 */   public int getReferenceType() { return ReferenceType.getReferenceType(this.reference); } 
/*    */   public Format getFormat() {
/* 73 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction20bc
 * JD-Core Version:    0.6.0
 */