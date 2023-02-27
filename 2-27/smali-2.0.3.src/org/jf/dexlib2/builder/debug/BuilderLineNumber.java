/*    */ package org.jf.dexlib2.builder.debug;
/*    */ 
/*    */ import org.jf.dexlib2.builder.BuilderDebugItem;
/*    */ import org.jf.dexlib2.iface.debug.LineNumber;
/*    */ 
/*    */ public class BuilderLineNumber extends BuilderDebugItem
/*    */   implements LineNumber
/*    */ {
/*    */   private final int lineNumber;
/*    */ 
/*    */   public BuilderLineNumber(int lineNumber)
/*    */   {
/* 42 */     this.lineNumber = lineNumber;
/*    */   }
/*    */   public int getLineNumber() {
/* 45 */     return this.lineNumber;
/*    */   }
/* 47 */   public int getDebugItemType() { return 10;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.debug.BuilderLineNumber
 * JD-Core Version:    0.6.0
 */