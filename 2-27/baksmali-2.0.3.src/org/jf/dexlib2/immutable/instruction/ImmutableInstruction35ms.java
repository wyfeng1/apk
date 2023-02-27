/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction35ms;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class ImmutableInstruction35ms extends ImmutableInstruction
/*    */   implements Instruction35ms
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format35ms;
/*    */   protected final int registerCount;
/*    */   protected final int registerC;
/*    */   protected final int registerD;
/*    */   protected final int registerE;
/*    */   protected final int registerF;
/*    */   protected final int registerG;
/*    */   protected final int vtableIndex;
/*    */ 
/*    */   public ImmutableInstruction35ms(Opcode opcode, int registerCount, int registerC, int registerD, int registerE, int registerF, int registerG, int vtableIndex)
/*    */   {
/* 60 */     super(opcode);
/* 61 */     this.registerCount = Preconditions.check35cRegisterCount(registerCount);
/* 62 */     this.registerC = (registerCount > 0 ? Preconditions.checkNibbleRegister(registerC) : 0);
/* 63 */     this.registerD = (registerCount > 1 ? Preconditions.checkNibbleRegister(registerD) : 0);
/* 64 */     this.registerE = (registerCount > 2 ? Preconditions.checkNibbleRegister(registerE) : 0);
/* 65 */     this.registerF = (registerCount > 3 ? Preconditions.checkNibbleRegister(registerF) : 0);
/* 66 */     this.registerG = (registerCount > 4 ? Preconditions.checkNibbleRegister(registerG) : 0);
/* 67 */     this.vtableIndex = Preconditions.checkVtableIndex(vtableIndex);
/*    */   }
/*    */ 
/*    */   public static ImmutableInstruction35ms of(Instruction35ms instruction) {
/* 71 */     if ((instruction instanceof ImmutableInstruction35ms)) {
/* 72 */       return (ImmutableInstruction35ms)instruction;
/*    */     }
/* 74 */     return new ImmutableInstruction35ms(instruction.getOpcode(), instruction.getRegisterCount(), instruction.getRegisterC(), instruction.getRegisterD(), instruction.getRegisterE(), instruction.getRegisterF(), instruction.getRegisterG(), instruction.getVtableIndex());
/*    */   }
/*    */ 
/*    */   public int getRegisterCount()
/*    */   {
/* 85 */     return this.registerCount; } 
/* 86 */   public int getRegisterC() { return this.registerC; } 
/* 87 */   public int getRegisterD() { return this.registerD; } 
/* 88 */   public int getRegisterE() { return this.registerE; } 
/* 89 */   public int getRegisterF() { return this.registerF; } 
/* 90 */   public int getRegisterG() { return this.registerG; } 
/* 91 */   public int getVtableIndex() { return this.vtableIndex; } 
/*    */   public Format getFormat() {
/* 93 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction35ms
 * JD-Core Version:    0.6.0
 */