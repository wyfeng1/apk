/*    */ package org.antlr.runtime;
/*    */ 
/*    */ import org.antlr.runtime.tree.TreeNodeStream;
/*    */ 
/*    */ public class MismatchedTreeNodeException extends RecognitionException
/*    */ {
/*    */   public int expecting;
/*    */ 
/*    */   public MismatchedTreeNodeException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public MismatchedTreeNodeException(int expecting, TreeNodeStream input)
/*    */   {
/* 41 */     super(input);
/* 42 */     this.expecting = expecting;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 47 */     return "MismatchedTreeNodeException(" + getUnexpectedType() + "!=" + this.expecting + ")";
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.MismatchedTreeNodeException
 * JD-Core Version:    0.6.0
 */