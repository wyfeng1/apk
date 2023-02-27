/*    */ package org.jf.baksmali.Adaptors;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class CommentMethodItem extends MethodItem
/*    */ {
/*    */   private final String comment;
/*    */   private final double sortOrder;
/*    */ 
/*    */   public CommentMethodItem(String comment, int codeAddress, double sortOrder)
/*    */   {
/* 41 */     super(codeAddress);
/* 42 */     this.comment = comment;
/* 43 */     this.sortOrder = sortOrder;
/*    */   }
/*    */ 
/*    */   public double getSortOrder() {
/* 47 */     return this.sortOrder;
/*    */   }
/*    */ 
/*    */   public boolean writeTo(IndentingWriter writer) throws IOException {
/* 51 */     writer.write(35);
/* 52 */     writer.write(this.comment);
/* 53 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.CommentMethodItem
 * JD-Core Version:    0.6.0
 */