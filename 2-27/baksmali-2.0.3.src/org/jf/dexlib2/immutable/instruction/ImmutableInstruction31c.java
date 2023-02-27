/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction31c;
/*    */ import org.jf.dexlib2.iface.reference.Reference;
/*    */ import org.jf.dexlib2.immutable.reference.ImmutableReference;
/*    */ import org.jf.dexlib2.immutable.reference.ImmutableReferenceFactory;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction31c extends ImmutableInstruction
/*    */   implements Instruction31c
/*    */ {
/* 45 */   public static final Format FORMAT = Format.Format31c;
/*    */   protected final int registerA;
/*    */   protected final ImmutableReference reference;
/*    */ 
/*    */   public ImmutableInstruction31c(Opcode opcode, int registerA, Reference reference)
/*    */   {
/* 53 */     super(opcode);
/* 54 */     this.registerA = Preconditions.checkByteRegister(registerA);
/* 55 */     this.reference = ImmutableReferenceFactory.of(opcode.referenceType, reference);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction31c of(Instruction31c instruction) {
/* 59 */     if ((instruction instanceof ImmutableInstruction31c)) {
/* 60 */       return (ImmutableInstruction31c)instruction;
/*    */     }
/* 62 */     return new ImmutableInstruction31c(instruction.getOpcode(), instruction.getRegisterA(), instruction.getReference());
/*    */   }
/*    */ 
/*    */   public int getRegisterA()
/*    */   {
/* 68 */     return this.registerA; } 
/* 69 */   public ImmutableReference getReference() { return this.reference; } 
/* 70 */   public int getReferenceType() { return this.opcode.referenceType; } 
/*    */   public Format getFormat() {
/* 72 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction31c
 * JD-Core Version:    0.6.0
 */