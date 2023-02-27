/*    */ package org.jf.dexlib2.immutable.debug;
/*    */ 
/*    */ import org.jf.dexlib2.iface.debug.EpilogueBegin;
/*    */ 
/*    */ public class ImmutableEpilogueBegin extends ImmutableDebugItem
/*    */   implements EpilogueBegin
/*    */ {
/*    */   public ImmutableEpilogueBegin(int codeAddress)
/*    */   {
/* 41 */     super(codeAddress);
/*    */   }
/*    */ 
/*    */   public static ImmutableEpilogueBegin of(EpilogueBegin epilogueBegin)
/*    */   {
/* 46 */     if ((epilogueBegin instanceof ImmutableEpilogueBegin)) {
/* 47 */       return (ImmutableEpilogueBegin)epilogueBegin;
/*    */     }
/* 49 */     return new ImmutableEpilogueBegin(epilogueBegin.getCodeAddress());
/*    */   }
/*    */   public int getDebugItemType() {
/* 52 */     return 8;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.debug.ImmutableEpilogueBegin
 * JD-Core Version:    0.6.0
 */