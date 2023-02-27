/*    */ package org.antlr.runtime;
/*    */ 
/*    */ public class MissingTokenException extends MismatchedTokenException
/*    */ {
/*    */   public Object inserted;
/*    */ 
/*    */   public MissingTokenException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public MissingTokenException(int expecting, IntStream input, Object inserted)
/*    */   {
/* 39 */     super(expecting, input);
/* 40 */     this.inserted = inserted;
/*    */   }
/*    */ 
/*    */   public int getMissingType() {
/* 44 */     return this.expecting;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 49 */     if ((this.inserted != null) && (this.token != null)) {
/* 50 */       return "MissingTokenException(inserted " + this.inserted + " at " + this.token.getText() + ")";
/*    */     }
/* 52 */     if (this.token != null) {
/* 53 */       return "MissingTokenException(at " + this.token.getText() + ")";
/*    */     }
/* 55 */     return "MissingTokenException";
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.MissingTokenException
 * JD-Core Version:    0.6.0
 */