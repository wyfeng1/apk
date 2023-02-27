/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction22c;
/*    */ import org.jf.dexlib2.iface.reference.Reference;
/*    */ import org.jf.dexlib2.immutable.reference.ImmutableReference;
/*    */ import org.jf.dexlib2.immutable.reference.ImmutableReferenceFactory;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction22c extends ImmutableInstruction
/*    */   implements Instruction22c
/*    */ {
/* 45 */   public static final Format FORMAT = Format.Format22c;
/*    */   protected final int registerA;
/*    */   protected final int registerB;
/*    */   protected final ImmutableReference reference;
/*    */ 
/*    */   public ImmutableInstruction22c(Opcode opcode, int registerA, int registerB, Reference reference)
/*    */   {
/* 55 */     super(opcode);
/* 56 */     this.registerA = Preconditions.checkNibbleRegister(registerA);
/* 57 */     this.registerB = Preconditions.checkNibbleRegister(registerB);
/* 58 */     this.reference = ImmutableReferenceFactory.of(opcode.referenceType, reference);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction22c of(Instruction22c instruction) {
/* 62 */     if ((instruction instanceof ImmutableInstruction22c)) {
/* 63 */       return (ImmutableInstruction22c)instruction;
/*    */     }
/* 65 */     return new ImmutableInstruction22c(instruction.getOpcode(), instruction.getRegisterA(), instruction.getRegisterB(), instruction.getReference());
/*    */   }
/*    */ 
/*    */   public int getRegisterA()
/*    */   {
/* 72 */     return this.registerA; } 
/* 73 */   public int getRegisterB() { return this.registerB; } 
/* 74 */   public ImmutableReference getReference() { return this.reference; } 
/* 75 */   public int getReferenceType() { return this.opcode.referenceType; } 
/*    */   public Format getFormat() {
/* 77 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction22c
 * JD-Core Version:    0.6.0
 */