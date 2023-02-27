/*    */ package org.jf.dexlib2.immutable.debug;
/*    */ 
/*    */ import org.jf.dexlib2.iface.debug.LineNumber;
/*    */ 
/*    */ public class ImmutableLineNumber extends ImmutableDebugItem
/*    */   implements LineNumber
/*    */ {
/*    */   protected final int lineNumber;
/*    */ 
/*    */   public ImmutableLineNumber(int codeAddress, int lineNumber)
/*    */   {
/* 44 */     super(codeAddress);
/* 45 */     this.lineNumber = lineNumber;
/*    */   }
/*    */ 
/*    */   public static ImmutableLineNumber of(LineNumber lineNumber)
/*    */   {
/* 50 */     if ((lineNumber instanceof ImmutableLineNumber)) {
/* 51 */       return (ImmutableLineNumber)lineNumber;
/*    */     }
/* 53 */     return new ImmutableLineNumber(lineNumber.getCodeAddress(), lineNumber.getLineNumber());
/*    */   }
/*    */ 
/*    */   public int getLineNumber()
/*    */   {
/* 58 */     return this.lineNumber;
/*    */   }
/* 60 */   public int getDebugItemType() { return 10;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.debug.ImmutableLineNumber
 * JD-Core Version:    0.6.0
 */