/*    */ package org.jf.dexlib2.analysis.reflection.util;
/*    */ 
/*    */ public class ReflectionUtils
/*    */ {
/*    */   public static String javaToDexName(String javaName)
/*    */   {
/* 36 */     javaName = javaName.replace('.', '/');
/* 37 */     if ((javaName.length() > 1) && (javaName.charAt(javaName.length() - 1) != ';')) {
/* 38 */       javaName = 'L' + javaName + ';';
/*    */     }
/* 40 */     return javaName;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.analysis.reflection.util.ReflectionUtils
 * JD-Core Version:    0.6.0
 */