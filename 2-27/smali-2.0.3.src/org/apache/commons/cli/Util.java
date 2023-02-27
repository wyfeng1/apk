/*    */ package org.apache.commons.cli;
/*    */ 
/*    */ class Util
/*    */ {
/*    */   static String stripLeadingHyphens(String str)
/*    */   {
/* 38 */     if (str == null)
/*    */     {
/* 40 */       return null;
/*    */     }
/* 42 */     if (str.startsWith("--"))
/*    */     {
/* 44 */       return str.substring(2, str.length());
/*    */     }
/* 46 */     if (str.startsWith("-"))
/*    */     {
/* 48 */       return str.substring(1, str.length());
/*    */     }
/*    */ 
/* 51 */     return str;
/*    */   }
/*    */ 
/*    */   static String stripLeadingAndTrailingQuotes(String str)
/*    */   {
/* 65 */     if (str.startsWith("\""))
/*    */     {
/* 67 */       str = str.substring(1, str.length());
/*    */     }
/* 69 */     if (str.endsWith("\""))
/*    */     {
/* 71 */       str = str.substring(0, str.length() - 1);
/*    */     }
/* 73 */     return str;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.apache.commons.cli.Util
 * JD-Core Version:    0.6.0
 */