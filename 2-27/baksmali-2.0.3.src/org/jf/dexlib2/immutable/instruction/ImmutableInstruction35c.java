/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction35c;
/*    */ import org.jf.dexlib2.iface.reference.Reference;
/*    */ import org.jf.dexlib2.immutable.reference.ImmutableReference;
/*    */ import org.jf.dexlib2.immutable.reference.ImmutableReferenceFactory;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction35c extends ImmutableInstruction
/*    */   implements Instruction35c
/*    */ {
/* 45 */   public static final Format FORMAT = Format.Format35c;
/*    */   protected final int registerCount;
/*    */   protected final int registerC;
/*    */   protected final int registerD;
/*    */   protected final int registerE;
/*    */   protected final int registerF;
/*    */   protected final int registerG;
/*    */   protected final ImmutableReference reference;
/*    */ 
/*    */   public ImmutableInstruction35c(Opcode opcode, int registerCount, int registerC, int registerD, int registerE, int registerF, int registerG, Reference reference)
/*    */   {
/* 63 */     super(opcode);
/* 64 */     this.registerCount = Preconditions.check35cRegisterCount(registerCount);
/* 65 */     this.registerC = (registerCount > 0 ? Preconditions.checkNibbleRegister(registerC) : 0);
/* 66 */     this.registerD = (registerCount > 1 ? Preconditions.checkNibbleRegister(registerD) : 0);
/* 67 */     this.registerE = (registerCount > 2 ? Preconditions.checkNibbleRegister(registerE) : 0);
/* 68 */     this.registerF = (registerCount > 3 ? Preconditions.checkNibbleRegister(registerF) : 0);
/* 69 */     this.registerG = (registerCount > 4 ? Preconditions.checkNibbleRegister(registerG) : 0);
/* 70 */     this.reference = ImmutableReferenceFactory.of(opcode.referenceType, reference);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction35c of(Instruction35c instruction) {
/* 74 */     if ((instruction instanceof ImmutableInstruction35c)) {
/* 75 */       return (ImmutableInstruction35c)instruction;
/*    */     }
/* 77 */     return new ImmutableInstruction35c(instruction.getOpcode(), instruction.getRegisterCount(), instruction.getRegisterC(), instruction.getRegisterD(), instruction.getRegisterE(), instruction.getRegisterF(), instruction.getRegisterG(), instruction.getReference());
/*    */   }
/*    */ 
/*    */   public int getRegisterCount()
/*    */   {
/* 88 */     return this.registerCount; } 
/* 89 */   public int getRegisterC() { return this.registerC; } 
/* 90 */   public int getRegisterD() { return this.registerD; } 
/* 91 */   public int getRegisterE() { return this.registerE; } 
/* 92 */   public int getRegisterF() { return this.registerF; } 
/* 93 */   public int getRegisterG() { return this.registerG; } 
/* 94 */   public ImmutableReference getReference() { return this.reference; } 
/* 95 */   public int getReferenceType() { return this.opcode.referenceType; } 
/*    */   public Format getFormat() {
/* 97 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction35c
 * JD-Core Version:    0.6.0
 */