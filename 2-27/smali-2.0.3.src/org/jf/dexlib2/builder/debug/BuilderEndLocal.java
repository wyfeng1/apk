/*    */ package org.jf.dexlib2.builder.debug;
/*    */ 
/*    */ import org.jf.dexlib2.builder.BuilderDebugItem;
/*    */ import org.jf.dexlib2.iface.debug.EndLocal;
/*    */ 
/*    */ public class BuilderEndLocal extends BuilderDebugItem
/*    */   implements EndLocal
/*    */ {
/*    */   private final int register;
/*    */ 
/*    */   public BuilderEndLocal(int register)
/*    */   {
/* 44 */     this.register = register;
/*    */   }
/*    */   public int getRegister() {
/* 47 */     return this.register;
/*    */   }
/*    */ 
/*    */   public int getDebugItemType()
/*    */   {
/* 52 */     return 5;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.debug.BuilderEndLocal
 * JD-Core Version:    0.6.0
 */