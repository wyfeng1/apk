/*    */ package org.apache.commons.cli;
/*    */ 
/*    */ public class MissingArgumentException extends ParseException
/*    */ {
/*    */   private Option option;
/*    */ 
/*    */   public MissingArgumentException(String message)
/*    */   {
/* 40 */     super(message);
/*    */   }
/*    */ 
/*    */   public MissingArgumentException(Option option)
/*    */   {
/* 52 */     this("Missing argument for option: " + option.getKey());
/* 53 */     this.option = option;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.apache.commons.cli.MissingArgumentException
 * JD-Core Version:    0.6.0
 */