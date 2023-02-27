/*     */ package org.jf.dexlib2.immutable.debug;
/*     */ 
/*     */ import org.jf.dexlib2.iface.debug.StartLocal;
/*     */ 
/*     */ public class ImmutableStartLocal extends ImmutableDebugItem
/*     */   implements StartLocal
/*     */ {
/*     */   protected final int register;
/*     */   protected final String name;
/*     */   protected final String type;
/*     */   protected final String signature;
/*     */ 
/*     */   public ImmutableStartLocal(int codeAddress, int register, String name, String type, String signature)
/*     */   {
/*  55 */     super(codeAddress);
/*  56 */     this.register = register;
/*  57 */     this.name = name;
/*  58 */     this.type = type;
/*  59 */     this.signature = signature;
/*     */   }
/*     */ 
/*     */   public static ImmutableStartLocal of(StartLocal startLocal)
/*     */   {
/*  64 */     if ((startLocal instanceof ImmutableStartLocal)) {
/*  65 */       return (ImmutableStartLocal)startLocal;
/*     */     }
/*  67 */     return new ImmutableStartLocal(startLocal.getCodeAddress(), startLocal.getRegister(), startLocal.getName(), startLocal.getType(), startLocal.getSignature());
/*     */   }
/*     */ 
/*     */   public int getRegister()
/*     */   {
/*  75 */     return this.register;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 101 */     return this.name; } 
/* 102 */   public String getType() { return this.type; } 
/* 103 */   public String getSignature() { return this.signature; } 
/*     */   public int getDebugItemType() {
/* 105 */     return 3;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.debug.ImmutableStartLocal
 * JD-Core Version:    0.6.0
 */