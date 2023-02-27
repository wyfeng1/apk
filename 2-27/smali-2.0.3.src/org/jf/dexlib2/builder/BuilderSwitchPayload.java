/*    */ package org.jf.dexlib2.builder;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.SwitchPayload;
/*    */ 
/*    */ public abstract class BuilderSwitchPayload extends BuilderInstruction
/*    */   implements SwitchPayload
/*    */ {
/*    */   MethodLocation referrer;
/*    */ 
/*    */   protected BuilderSwitchPayload(Opcode opcode)
/*    */   {
/* 47 */     super(opcode);
/*    */   }
/*    */ 
/*    */   public MethodLocation getReferrer()
/*    */   {
/* 52 */     if (this.referrer == null) {
/* 53 */       throw new IllegalStateException("The referrer has not been set yet");
/*    */     }
/* 55 */     return this.referrer;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.BuilderSwitchPayload
 * JD-Core Version:    0.6.0
 */