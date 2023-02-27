/*    */ package org.apache.commons.cli;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MissingOptionException extends ParseException
/*    */ {
/*    */   private List missingOptions;
/*    */ 
/*    */   public MissingOptionException(String message)
/*    */   {
/* 42 */     super(message);
/*    */   }
/*    */ 
/*    */   public MissingOptionException(List missingOptions)
/*    */   {
/* 54 */     this(createMessage(missingOptions));
/* 55 */     this.missingOptions = missingOptions;
/*    */   }
/*    */ 
/*    */   private static String createMessage(List missingOptions)
/*    */   {
/* 77 */     StringBuffer buff = new StringBuffer("Missing required option");
/* 78 */     buff.append(missingOptions.size() == 1 ? "" : "s");
/* 79 */     buff.append(": ");
/*    */ 
/* 81 */     Iterator it = missingOptions.iterator();
/* 82 */     while (it.hasNext())
/*    */     {
/* 84 */       buff.append(it.next());
/* 85 */       if (!it.hasNext())
/*    */         continue;
/* 87 */       buff.append(", ");
/*    */     }
/*    */ 
/* 91 */     return buff.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.apache.commons.cli.MissingOptionException
 * JD-Core Version:    0.6.0
 */