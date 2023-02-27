/*    */ package org.jf.baksmali.Adaptors;
/*    */ 
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class BlankMethodItem extends MethodItem
/*    */ {
/*    */   public BlankMethodItem(int codeAddress)
/*    */   {
/* 36 */     super(codeAddress);
/*    */   }
/*    */ 
/*    */   public double getSortOrder() {
/* 40 */     return 2147483647.0D;
/*    */   }
/*    */ 
/*    */   public boolean writeTo(IndentingWriter writer)
/*    */   {
/* 46 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.BlankMethodItem
 * JD-Core Version:    0.6.0
 */