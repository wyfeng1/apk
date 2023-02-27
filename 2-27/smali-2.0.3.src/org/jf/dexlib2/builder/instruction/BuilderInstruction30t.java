/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderOffsetInstruction;
/*    */ import org.jf.dexlib2.builder.Label;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction30t;
/*    */ 
/*    */ public class BuilderInstruction30t extends BuilderOffsetInstruction
/*    */   implements Instruction30t
/*    */ {
/* 43 */   public static final Format FORMAT = Format.Format30t;
/*    */ 
/*    */   public BuilderInstruction30t(Opcode opcode, Label target)
/*    */   {
/* 47 */     super(opcode, target);
/*    */   }
/*    */   public Format getFormat() {
/* 50 */     return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction30t
 * JD-Core Version:    0.6.0
 */