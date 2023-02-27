/*    */ package org.apache.commons.cli;
/*    */ 
/*    */ public class AlreadySelectedException extends ParseException
/*    */ {
/*    */   private OptionGroup group;
/*    */   private Option option;
/*    */ 
/*    */   public AlreadySelectedException(String message)
/*    */   {
/* 43 */     super(message);
/*    */   }
/*    */ 
/*    */   public AlreadySelectedException(OptionGroup group, Option option)
/*    */   {
/* 56 */     this("The option '" + option.getKey() + "' was specified but an option from this group " + "has already been selected: '" + group.getSelected() + "'");
/*    */ 
/* 58 */     this.group = group;
/* 59 */     this.option = option;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.apache.commons.cli.AlreadySelectedException
 * JD-Core Version:    0.6.0
 */