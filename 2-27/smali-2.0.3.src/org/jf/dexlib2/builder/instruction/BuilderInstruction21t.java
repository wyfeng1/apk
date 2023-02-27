/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderOffsetInstruction;
/*    */ import org.jf.dexlib2.builder.Label;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction21t;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class BuilderInstruction21t extends BuilderOffsetInstruction
/*    */   implements Instruction21t
/*    */ {
/* 44 */   public static final Format FORMAT = Format.Format21t;
/*    */   protected final int registerA;
/*    */ 
/*    */   public BuilderInstruction21t(Opcode opcode, int registerA, Label target)
/*    */   {
/* 51 */     super(opcode, target);
/* 52 */     this.registerA = Preconditions.checkByteRegister(registerA);
/*    */   }
/*    */   public int getRegisterA() {
/* 55 */     return this.registerA;
/*    */   }
/* 57 */   public Format getFormat() { return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction21t
 * JD-Core Version:    0.6.0
 */