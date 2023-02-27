/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderOffsetInstruction;
/*    */ import org.jf.dexlib2.builder.Label;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction20t;
/*    */ 
/*    */ public class BuilderInstruction20t extends BuilderOffsetInstruction
/*    */   implements Instruction20t
/*    */ {
/* 43 */   public static final Format FORMAT = Format.Format20t;
/*    */ 
/*    */   public BuilderInstruction20t(Opcode opcode, Label target)
/*    */   {
/* 47 */     super(opcode, target);
/*    */   }
/*    */   public Format getFormat() {
/* 50 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction20t
 * JD-Core Version:    0.6.0
 */