/*    */ package org.jf.baksmali.Adaptors;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public abstract class MethodItem
/*    */   implements Comparable<MethodItem>
/*    */ {
/*    */   protected final int codeAddress;
/*    */ 
/*    */   protected MethodItem(int codeAddress)
/*    */   {
/* 39 */     this.codeAddress = codeAddress;
/*    */   }
/*    */ 
/*    */   public int getCodeAddress() {
/* 43 */     return this.codeAddress;
/*    */   }
/*    */ 
/*    */   public abstract double getSortOrder();
/*    */ 
/*    */   public int compareTo(MethodItem methodItem) {
/* 50 */     int result = Integer.valueOf(this.codeAddress).compareTo(Integer.valueOf(methodItem.codeAddress));
/*    */ 
/* 52 */     if (result == 0) {
/* 53 */       return Double.valueOf(getSortOrder()).compareTo(Double.valueOf(methodItem.getSortOrder()));
/*    */     }
/* 55 */     return result;
/*    */   }
/*    */ 
/*    */   public abstract boolean writeTo(IndentingWriter paramIndentingWriter)
/*    */     throws IOException;
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.MethodItem
 * JD-Core Version:    0.6.0
 */