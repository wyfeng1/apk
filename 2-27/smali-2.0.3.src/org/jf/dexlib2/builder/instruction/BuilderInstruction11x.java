/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderInstruction;
/*    */ import org.jf.dexlib2.iface.instruction.formats.Instruction11x;
/*    */ import org.jf.dexlib2.util.Preconditions;
/*    */ 
/*    */ public class BuilderInstruction11x extends BuilderInstruction
/*    */   implements Instruction11x
/*    */ {
/* 43 */   public static final Format FORMAT = Format.Format11x;
/*    */   protected final int registerA;
/*    */ 
/*    */   public BuilderInstruction11x(Opcode opcode, int registerA)
/*    */   {
/* 49 */     super(opcode);
/* 50 */     this.registerA = Preconditions.checkByteRegister(registerA);
/*    */   }
/*    */   public int getRegisterA() {
/* 53 */     return this.registerA;
/*    */   }
/* 55 */   public Format getFormat() { return FORMAT;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderInstruction11x
 * JD-Core Version:    0.6.0
 */