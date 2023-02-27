/*    */ package org.jf.dexlib2.builder;
/*    */ 
/*    */ import org.jf.dexlib2.iface.debug.DebugItem;
/*    */ 
/*    */ public abstract class BuilderDebugItem
/*    */   implements DebugItem
/*    */ {
/*    */   MethodLocation location;
/*    */ 
/*    */   public int getCodeAddress()
/*    */   {
/* 45 */     if (this.location == null) {
/* 46 */       throw new IllegalStateException("Cannot get the address of a BuilderDebugItem that isn't associated with a method.");
/*    */     }
/*    */ 
/* 49 */     return this.location.getCodeAddress();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.BuilderDebugItem
 * JD-Core Version:    0.6.0
 */