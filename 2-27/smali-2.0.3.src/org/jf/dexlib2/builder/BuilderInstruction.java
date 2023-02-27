/*    */ package org.jf.dexlib2.builder;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.Instruction;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public abstract class BuilderInstruction
/*    */   implements Instruction
/*    */ {
/*    */   protected final Opcode opcode;
/*    */   MethodLocation location;
/*    */ 
/*    */   protected BuilderInstruction(Opcode opcode)
/*    */   {
/* 48 */     Preconditions.checkFormat(opcode, getFormat());
/* 49 */     this.opcode = opcode;
/*    */   }
/*    */ 
/*    */   public Opcode getOpcode() {
/* 53 */     return this.opcode;
/*    */   }
/*    */   public abstract Format getFormat();
/*    */ 
/*    */   public int getCodeUnits() {
/* 59 */     return getFormat().size / 2;
/*    */   }
/*    */ 
/*    */   public MethodLocation getLocation()
/*    */   {
/* 64 */     if (this.location == null) {
/* 65 */       throw new IllegalStateException("Cannot get the location of an instruction that hasn't been added to a method.");
/*    */     }
/*    */ 
/* 68 */     return this.location;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.BuilderInstruction
 * JD-Core Version:    0.6.0
 */