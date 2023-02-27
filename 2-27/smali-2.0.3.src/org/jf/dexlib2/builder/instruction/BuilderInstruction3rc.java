/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderInstruction;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction3rc;
/*    */ import org.jf.dexlib2.iface.reference.Reference;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class BuilderInstruction3rc extends BuilderInstruction
/*    */   implements Instruction3rc
/*    */ {
/* 44 */   public static final Format FORMAT = Format.Format3rc;
/*    */   protected final int startRegister;
/*    */   protected final int registerCount;
/*    */   protected final Reference reference;
/*    */ 
/*    */   public BuilderInstruction3rc(Opcode opcode, int startRegister, int registerCount, Reference reference)
/*    */   {
/* 55 */     super(opcode);
/* 56 */     this.startRegister = Preconditions.checkShortRegister(startRegister);
/* 57 */     this.registerCount = Preconditions.checkRegisterRangeCount(registerCount);
/* 58 */     this.reference = reference;
/*    */   }
/*    */   public int getStartRegister() {
/* 61 */     return this.startRegister; } 
/* 62 */   public int getRegisterCount() { return this.registerCount; } 
/* 63 */   public Reference getReference() { return this.reference; }
/*    */ 
/*    */   public Format getFormat() {
/* 66 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction3rc
 * JD-Core Version:    0.6.0
 */