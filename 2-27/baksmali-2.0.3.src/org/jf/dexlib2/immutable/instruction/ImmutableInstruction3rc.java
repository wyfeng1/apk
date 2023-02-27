/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction3rc;
/*    */ import org.jf.dexlib2.iface.reference.Reference;
/*    */ import org.jf.dexlib2.immutable.reference.ImmutableReference;
/*    */ import org.jf.dexlib2.immutable.reference.ImmutableReferenceFactory;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction3rc extends ImmutableInstruction
/*    */   implements Instruction3rc
/*    */ {
/* 45 */   public static final Format FORMAT = Format.Format3rc;
/*    */   protected final int startRegister;
/*    */   protected final int registerCount;
/*    */   protected final ImmutableReference reference;
/*    */ 
/*    */   public ImmutableInstruction3rc(Opcode opcode, int startRegister, int registerCount, Reference reference)
/*    */   {
/* 56 */     super(opcode);
/* 57 */     this.startRegister = Preconditions.checkShortRegister(startRegister);
/* 58 */     this.registerCount = Preconditions.checkRegisterRangeCount(registerCount);
/* 59 */     this.reference = ImmutableReferenceFactory.of(opcode.referenceType, reference);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction3rc of(Instruction3rc instruction) {
/* 63 */     if ((instruction instanceof ImmutableInstruction3rc)) {
/* 64 */       return (ImmutableInstruction3rc)instruction;
/*    */     }
/* 66 */     return new ImmutableInstruction3rc(instruction.getOpcode(), instruction.getStartRegister(), instruction.getRegisterCount(), instruction.getReference());
/*    */   }
/*    */ 
/*    */   public int getStartRegister()
/*    */   {
/* 73 */     return this.startRegister; } 
/* 74 */   public int getRegisterCount() { return this.registerCount; } 
/* 75 */   public ImmutableReference getReference() { return this.reference; } 
/* 76 */   public int getReferenceType() { return this.opcode.referenceType; } 
/*    */   public Format getFormat() {
/* 78 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction3rc
 * JD-Core Version:    0.6.0
 */