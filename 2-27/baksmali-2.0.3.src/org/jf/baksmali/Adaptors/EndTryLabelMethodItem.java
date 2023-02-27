/*    */ package org.jf.baksmali.Adaptors;
/*    */ 
/*    */ import org.jf.baksmali.baksmaliOptions;
/*    */ 
/*    */ public class EndTryLabelMethodItem extends LabelMethodItem
/*    */ {
/*    */   private int endTryAddress;
/*    */ 
/*    */   public EndTryLabelMethodItem(baksmaliOptions options, int codeAddress, int endTryAddress)
/*    */   {
/* 39 */     super(options, codeAddress, "try_end_");
/* 40 */     this.endTryAddress = endTryAddress;
/*    */   }
/*    */ 
/*    */   public double getSortOrder()
/*    */   {
/* 45 */     return 101.0D;
/*    */   }
/*    */ 
/*    */   public int getLabelAddress() {
/* 49 */     return this.endTryAddress;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.EndTryLabelMethodItem
 * JD-Core Version:    0.6.0
 */