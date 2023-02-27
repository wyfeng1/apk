/*    */ package org.jf.dexlib2.analysis;
/*    */ 
/*    */ import org.jf.util.ExceptionWithContext;
/*    */ 
/*    */ public class AnalysisException extends ExceptionWithContext
/*    */ {
/*    */   public int codeAddress;
/*    */ 
/*    */   public AnalysisException(String message, Object[] formatArgs)
/*    */   {
/* 48 */     super(message, formatArgs);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.analysis.AnalysisException
 * JD-Core Version:    0.6.0
 */