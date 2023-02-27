/*    */ package org.antlr.runtime.tree;
/*    */ 
/*    */ public class RewriteRuleSubtreeStream extends RewriteRuleElementStream
/*    */ {
/*    */   public RewriteRuleSubtreeStream(TreeAdaptor adaptor, String elementDescription)
/*    */   {
/* 35 */     super(adaptor, elementDescription);
/*    */   }
/*    */ 
/*    */   public RewriteRuleSubtreeStream(TreeAdaptor adaptor, String elementDescription, Object oneElement)
/*    */   {
/* 43 */     super(adaptor, elementDescription, oneElement);
/*    */   }
/*    */ 
/*    */   protected Object dup(Object el)
/*    */   {
/* 87 */     return this.adaptor.dupTree(el);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.tree.RewriteRuleSubtreeStream
 * JD-Core Version:    0.6.0
 */