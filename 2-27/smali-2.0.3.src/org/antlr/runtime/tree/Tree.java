/*    */ package org.antlr.runtime.tree;
/*    */ 
/*    */ import org.antlr.runtime.Token;
/*    */ 
/*    */ public abstract interface Tree
/*    */ {
/* 45 */   public static final Tree INVALID_NODE = new CommonTree(Token.INVALID_TOKEN);
/*    */ 
/*    */   public abstract Tree getChild(int paramInt);
/*    */ 
/*    */   public abstract int getChildCount();
/*    */ 
/*    */   public abstract Tree getParent();
/*    */ 
/*    */   public abstract void setParent(Tree paramTree);
/*    */ 
/*    */   public abstract int getChildIndex();
/*    */ 
/*    */   public abstract void setChildIndex(int paramInt);
/*    */ 
/*    */   public abstract void addChild(Tree paramTree);
/*    */ 
/*    */   public abstract boolean isNil();
/*    */ 
/*    */   public abstract void setTokenStartIndex(int paramInt);
/*    */ 
/*    */   public abstract void setTokenStopIndex(int paramInt);
/*    */ 
/*    */   public abstract Tree dupNode();
/*    */ 
/*    */   public abstract int getType();
/*    */ 
/*    */   public abstract String getText();
/*    */ 
/*    */   public abstract int getLine();
/*    */ 
/*    */   public abstract int getCharPositionInLine();
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.tree.Tree
 * JD-Core Version:    0.6.0
 */