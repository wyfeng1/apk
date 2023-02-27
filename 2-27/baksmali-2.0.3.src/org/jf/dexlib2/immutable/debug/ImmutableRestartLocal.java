/*    */ package org.jf.dexlib2.immutable.debug;
/*    */ 
/*    */ import org.jf.dexlib2.iface.debug.RestartLocal;
/*    */ 
/*    */ public class ImmutableRestartLocal extends ImmutableDebugItem
/*    */   implements RestartLocal
/*    */ {
/*    */   protected final int register;
/*    */   protected final String name;
/*    */   protected final String type;
/*    */   protected final String signature;
/*    */ 
/*    */   public ImmutableRestartLocal(int codeAddress, int register, String name, String type, String signature)
/*    */   {
/* 60 */     super(codeAddress);
/* 61 */     this.register = register;
/* 62 */     this.name = name;
/* 63 */     this.type = type;
/* 64 */     this.signature = signature;
/*    */   }
/*    */ 
/*    */   public static ImmutableRestartLocal of(RestartLocal restartLocal)
/*    */   {
/* 69 */     if ((restartLocal instanceof ImmutableRestartLocal)) {
/* 70 */       return (ImmutableRestartLocal)restartLocal;
/*    */     }
/* 72 */     return new ImmutableRestartLocal(restartLocal.getCodeAddress(), restartLocal.getRegister(), restartLocal.getType(), restartLocal.getName(), restartLocal.getSignature());
/*    */   }
/*    */ 
/*    */   public int getRegister()
/*    */   {
/* 80 */     return this.register; } 
/* 81 */   public String getName() { return this.name; } 
/* 82 */   public String getType() { return this.type; } 
/* 83 */   public String getSignature() { return this.signature; } 
/*    */   public int getDebugItemType() {
/* 85 */     return 6;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.debug.ImmutableRestartLocal
 * JD-Core Version:    0.6.0
 */