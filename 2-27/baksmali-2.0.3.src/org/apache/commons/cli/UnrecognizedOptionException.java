/*    */ package org.apache.commons.cli;
/*    */ 
/*    */ public class UnrecognizedOptionException extends ParseException
/*    */ {
/*    */   private String option;
/*    */ 
/*    */   public UnrecognizedOptionException(String message)
/*    */   {
/* 40 */     super(message);
/*    */   }
/*    */ 
/*    */   public UnrecognizedOptionException(String message, String option)
/*    */   {
/* 53 */     this(message);
/* 54 */     this.option = option;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.apache.commons.cli.UnrecognizedOptionException
 * JD-Core Version:    0.6.0
 */