/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction3rmi;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction3rmi extends ImmutableInstruction
/*    */   implements Instruction3rmi
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format3rmi;
/*    */   protected final int startRegister;
/*    */   protected final int registerCount;
/*    */   protected final int inlineIndex;
/*    */ 
/*    */   public ImmutableInstruction3rmi(Opcode opcode, int startRegister, int registerCount, int inlineIndex)
/*    */   {
/* 52 */     super(opcode);
/* 53 */     this.startRegister = Preconditions.checkShortRegister(startRegister);
/* 54 */     this.registerCount = Preconditions.checkRegisterRangeCount(registerCount);
/* 55 */     this.inlineIndex = Preconditions.checkInlineIndex(inlineIndex);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction3rmi of(Instruction3rmi instruction) {
/* 59 */     if ((instruction instanceof ImmutableInstruction3rmi)) {
/* 60 */       return (ImmutableInstruction3rmi)instruction;
/*    */     }
/* 62 */     return new ImmutableInstruction3rmi(instruction.getOpcode(), instruction.getStartRegister(), instruction.getRegisterCount(), instruction.getInlineIndex());
/*    */   }
/*    */ 
/*    */   public int getStartRegister()
/*    */   {
/* 69 */     return this.startRegister; } 
/* 70 */   public int getRegisterCount() { return this.registerCount; } 
/* 71 */   public int getInlineIndex() { return this.inlineIndex; } 
/*    */   public Format getFormat() {
/* 73 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction3rmi
 * JD-Core Version:    0.6.0
 */