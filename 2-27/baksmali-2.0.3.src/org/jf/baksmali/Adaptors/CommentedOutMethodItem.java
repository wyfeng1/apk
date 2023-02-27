/*    */ package org.jf.baksmali.Adaptors;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class CommentedOutMethodItem extends MethodItem
/*    */ {
/*    */   private final MethodItem commentedOutMethodItem;
/*    */ 
/*    */   public CommentedOutMethodItem(MethodItem commentedOutMethodItem)
/*    */   {
/* 39 */     super(commentedOutMethodItem.getCodeAddress());
/* 40 */     this.commentedOutMethodItem = commentedOutMethodItem;
/*    */   }
/*    */ 
/*    */   public double getSortOrder() {
/* 44 */     return this.commentedOutMethodItem.getSortOrder() + 0.001D;
/*    */   }
/*    */ 
/*    */   public boolean writeTo(IndentingWriter writer) throws IOException {
/* 48 */     writer.write(35);
/* 49 */     this.commentedOutMethodItem.writeTo(writer);
/* 50 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.CommentedOutMethodItem
 * JD-Core Version:    0.6.0
 */