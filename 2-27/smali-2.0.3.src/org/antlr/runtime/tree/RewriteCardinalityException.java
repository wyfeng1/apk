/*    */ package org.antlr.runtime.tree;
/*    */ 
/*    */ public class RewriteCardinalityException extends RuntimeException
/*    */ {
/*    */   public String elementDescription;
/*    */ 
/*    */   public RewriteCardinalityException(String elementDescription)
/*    */   {
/* 38 */     this.elementDescription = elementDescription;
/*    */   }
/*    */ 
/*    */   public String getMessage()
/*    */   {
/* 43 */     if (this.elementDescription != null) {
/* 44 */       return this.elementDescription;
/*    */     }
/* 46 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.tree.RewriteCardinalityException
 * JD-Core Version:    0.6.0
 */