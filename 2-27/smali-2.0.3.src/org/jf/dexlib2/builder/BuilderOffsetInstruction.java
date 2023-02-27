/*    */ package org.jf.dexlib2.builder;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.OffsetInstruction;
/*    */ 
/*    */ public abstract class BuilderOffsetInstruction extends BuilderInstruction
/*    */   implements OffsetInstruction
/*    */ {
/*    */   protected final Label target;
/*    */ 
/*    */   public BuilderOffsetInstruction(Opcode opcode, Label target)
/*    */   {
/* 45 */     super(opcode);
/* 46 */     this.target = target;
/*    */   }
/*    */ 
/*    */   public int getCodeOffset() {
/* 50 */     int codeOffset = internalGetCodeOffset();
/* 51 */     if (((getCodeUnits() == 1) && ((codeOffset < -128) || (codeOffset > 127))) || ((getCodeUnits() == 2) && ((codeOffset < -32768) || (codeOffset > 32767))))
/*    */     {
/* 53 */       throw new IllegalStateException("Target is out of range");
/*    */     }
/* 55 */     return codeOffset;
/*    */   }
/*    */ 
/*    */   int internalGetCodeOffset()
/*    */   {
/* 60 */     return this.target.getCodeAddress() - getLocation().getCodeAddress();
/*    */   }
/*    */ 
/*    */   public Label getTarget()
/*    */   {
/* 65 */     return this.target;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.BuilderOffsetInstruction
 * JD-Core Version:    0.6.0
 */