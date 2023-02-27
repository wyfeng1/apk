/*    */ package org.antlr.runtime;
/*    */ 
/*    */ public abstract interface Token
/*    */ {
/* 43 */   public static final Token INVALID_TOKEN = new CommonToken(0);
/*    */ 
/* 48 */   public static final Token SKIP_TOKEN = new CommonToken(0);
/*    */ 
/*    */   public abstract String getText();
/*    */ 
/*    */   public abstract void setText(String paramString);
/*    */ 
/*    */   public abstract int getType();
/*    */ 
/*    */   public abstract void setType(int paramInt);
/*    */ 
/*    */   public abstract int getLine();
/*    */ 
/*    */   public abstract int getCharPositionInLine();
/*    */ 
/*    */   public abstract int getChannel();
/*    */ 
/*    */   public abstract int getTokenIndex();
/*    */ 
/*    */   public abstract void setTokenIndex(int paramInt);
/*    */ 
/*    */   public abstract CharStream getInputStream();
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.Token
 * JD-Core Version:    0.6.0
 */