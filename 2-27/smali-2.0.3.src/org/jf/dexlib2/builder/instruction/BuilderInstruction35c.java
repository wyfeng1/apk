/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderInstruction;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction35c;
/*    */ import org.jf.dexlib2.iface.reference.Reference;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class BuilderInstruction35c extends BuilderInstruction
/*    */   implements Instruction35c
/*    */ {
/* 44 */   public static final Format FORMAT = Format.Format35c;
/*    */   protected final int registerCount;
/*    */   protected final int registerC;
/*    */   protected final int registerD;
/*    */   protected final int registerE;
/*    */   protected final int registerF;
/*    */   protected final int registerG;
/*    */   protected final Reference reference;
/*    */ 
/*    */   public BuilderInstruction35c(Opcode opcode, int registerCount, int registerC, int registerD, int registerE, int registerF, int registerG, Reference reference)
/*    */   {
/* 62 */     super(opcode);
/* 63 */     this.registerCount = Preconditions.check35cRegisterCount(registerCount);
/* 64 */     this.registerC = (registerCount > 0 ? Preconditions.checkNibbleRegister(registerC) : 0);
/* 65 */     this.registerD = (registerCount > 1 ? Preconditions.checkNibbleRegister(registerD) : 0);
/* 66 */     this.registerE = (registerCount > 2 ? Preconditions.checkNibbleRegister(registerE) : 0);
/* 67 */     this.registerF = (registerCount > 3 ? Preconditions.checkNibbleRegister(registerF) : 0);
/* 68 */     this.registerG = (registerCount > 4 ? Preconditions.checkNibbleRegister(registerG) : 0);
/* 69 */     this.reference = reference;
/*    */   }
/*    */   public int getRegisterCount() {
/* 72 */     return this.registerCount; } 
/* 73 */   public int getRegisterC() { return this.registerC; } 
/* 74 */   public int getRegisterD() { return this.registerD; } 
/* 75 */   public int getRegisterE() { return this.registerE; } 
/* 76 */   public int getRegisterF() { return this.registerF; } 
/* 77 */   public int getRegisterG() { return this.registerG; } 
/* 78 */   public Reference getReference() { return this.reference; }
/*    */ 
/*    */   public Format getFormat() {
/* 81 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction35c
 * JD-Core Version:    0.6.0
 */