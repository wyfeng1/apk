/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction3rms;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction3rms extends ImmutableInstruction
/*    */   implements Instruction3rms
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format3rms;
/*    */   protected final int startRegister;
/*    */   protected final int registerCount;
/*    */   protected final int vtableIndex;
/*    */ 
/*    */   public ImmutableInstruction3rms(Opcode opcode, int startRegister, int registerCount, int vtableIndex)
/*    */   {
/* 52 */     super(opcode);
/* 53 */     this.startRegister = Preconditions.checkShortRegister(startRegister);
/* 54 */     this.registerCount = Preconditions.checkRegisterRangeCount(registerCount);
/* 55 */     this.vtableIndex = Preconditions.checkVtableIndex(vtableIndex);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction3rms of(Instruction3rms instruction) {
/* 59 */     if ((instruction instanceof ImmutableInstruction3rms)) {
/* 60 */       return (ImmutableInstruction3rms)instruction;
/*    */     }
/* 62 */     return new ImmutableInstruction3rms(instruction.getOpcode(), instruction.getStartRegister(), instruction.getRegisterCount(), instruction.getVtableIndex());
/*    */   }
/*    */ 
/*    */   public int getStartRegister()
/*    */   {
/* 69 */     return this.startRegister; } 
/* 70 */   public int getRegisterCount() { return this.registerCount; } 
/* 71 */   public int getVtableIndex() { return this.vtableIndex; } 
/*    */   public Format getFormat() {
/* 73 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction3rms
 * JD-Core Version:    0.6.0
 */