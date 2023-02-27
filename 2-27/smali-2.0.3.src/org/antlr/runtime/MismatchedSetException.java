/*    */ package org.antlr.runtime;
/*    */ 
/*    */ public class MismatchedSetException extends RecognitionException
/*    */ {
/*    */   public BitSet expecting;
/*    */ 
/*    */   public MismatchedSetException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public MismatchedSetException(BitSet expecting, IntStream input)
/*    */   {
/* 37 */     super(input);
/* 38 */     this.expecting = expecting;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 43 */     return "MismatchedSetException(" + getUnexpectedType() + "!=" + this.expecting + ")";
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.MismatchedSetException
 * JD-Core Version:    0.6.0
 */