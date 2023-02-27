/*    */ package org.jf.dexlib2.builder.debug;
/*    */ 
/*    */ import org.jf.dexlib2.builder.BuilderDebugItem;
/*    */ import org.jf.dexlib2.iface.debug.RestartLocal;
/*    */ 
/*    */ public class BuilderRestartLocal extends BuilderDebugItem
/*    */   implements RestartLocal
/*    */ {
/*    */   private final int register;
/*    */ 
/*    */   public BuilderRestartLocal(int register)
/*    */   {
/* 44 */     this.register = register;
/*    */   }
/*    */   public int getRegister() {
/* 47 */     return this.register;
/*    */   }
/*    */ 
/*    */   public int getDebugItemType()
/*    */   {
/* 52 */     return 6;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.debug.BuilderRestartLocal
 * JD-Core Version:    0.6.0
 */