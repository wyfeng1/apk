/*    */ package org.jf.dexlib2.builder;
/*    */ 
/*    */ public class Label
/*    */ {
/*    */   MethodLocation location;
/*    */ 
/*    */   Label()
/*    */   {
/*    */   }
/*    */ 
/*    */   Label(MethodLocation location)
/*    */   {
/* 44 */     this.location = location;
/*    */   }
/*    */ 
/*    */   public int getCodeAddress() {
/* 48 */     return getLocation().getCodeAddress();
/*    */   }
/*    */ 
/*    */   public MethodLocation getLocation()
/*    */   {
/* 53 */     if (this.location == null) {
/* 54 */       throw new IllegalStateException("Cannot get the location of a label that hasn't been placed yet.");
/*    */     }
/* 56 */     return this.location;
/*    */   }
/*    */ 
/*    */   public boolean isPlaced() {
/* 60 */     return this.location != null;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.Label
 * JD-Core Version:    0.6.0
 */