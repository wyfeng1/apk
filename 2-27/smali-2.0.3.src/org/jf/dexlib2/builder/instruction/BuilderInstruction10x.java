/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderInstruction;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction10x;
/*    */ 
/*    */ public class BuilderInstruction10x extends BuilderInstruction
/*    */   implements Instruction10x
/*    */ {
/* 42 */   public static final Format FORMAT = Format.Format10x;
/*    */ 
/*    */   public BuilderInstruction10x(Opcode opcode) {
/* 45 */     super(opcode);
/*    */   }
/*    */   public Format getFormat() {
/* 48 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction10x
 * JD-Core Version:    0.6.0
 */