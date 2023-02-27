/*    */ package org.antlr.runtime.tree;
/*    */ 
/*    */ import org.antlr.runtime.Token;
/*    */ 
/*    */ public class RewriteRuleTokenStream extends RewriteRuleElementStream
/*    */ {
/*    */   public RewriteRuleTokenStream(TreeAdaptor adaptor, String elementDescription)
/*    */   {
/* 37 */     super(adaptor, elementDescription);
/*    */   }
/*    */ 
/*    */   public RewriteRuleTokenStream(TreeAdaptor adaptor, String elementDescription, Object oneElement)
/*    */   {
/* 45 */     super(adaptor, elementDescription, oneElement);
/*    */   }
/*    */ 
/*    */   public Object nextNode()
/*    */   {
/* 58 */     Token t = (Token)_next();
/* 59 */     return this.adaptor.create(t);
/*    */   }
/*    */ 
/*    */   protected Object toTree(Object el)
/*    */   {
/* 71 */     return el;
/*    */   }
/*    */ 
/*    */   protected Object dup(Object el)
/*    */   {
/* 76 */     throw new UnsupportedOperationException("dup can't be called for a token stream.");
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.tree.RewriteRuleTokenStream
 * JD-Core Version:    0.6.0
 */