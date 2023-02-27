/*    */ package org.apache.commons.cli;
/*    */ 
/*    */ class OptionValidator
/*    */ {
/*    */   static void validateOption(String opt)
/*    */     throws IllegalArgumentException
/*    */   {
/* 48 */     if (opt == null)
/*    */     {
/* 50 */       return;
/*    */     }
/*    */ 
/* 54 */     if (opt.length() == 1)
/*    */     {
/* 56 */       char ch = opt.charAt(0);
/*    */ 
/* 58 */       if (!isValidOpt(ch))
/*    */       {
/* 60 */         throw new IllegalArgumentException("illegal option value '" + ch + "'");
/*    */       }
/*    */ 
/*    */     }
/*    */     else
/*    */     {
/* 67 */       char[] chars = opt.toCharArray();
/*    */ 
/* 69 */       for (int i = 0; i < chars.length; i++)
/*    */       {
/* 71 */         if (isValidChar(chars[i]))
/*    */           continue;
/* 73 */         throw new IllegalArgumentException("opt contains illegal character value '" + chars[i] + "'");
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   private static boolean isValidOpt(char c)
/*    */   {
/* 88 */     return (isValidChar(c)) || (c == ' ') || (c == '?') || (c == '@');
/*    */   }
/*    */ 
/*    */   private static boolean isValidChar(char c)
/*    */   {
/* 99 */     return Character.isJavaIdentifierPart(c);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.apache.commons.cli.OptionValidator
 * JD-Core Version:    0.6.0
 */