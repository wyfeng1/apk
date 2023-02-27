/*    */ package org.jf.dexlib2;
/*    */ 
/*    */ import org.jf.util.ExceptionWithContext;
/*    */ 
/*    */ public final class AnnotationVisibility
/*    */ {
/* 41 */   private static String[] NAMES = { "build", "runtime", "system" };
/*    */ 
/*    */   public static int getVisibility(String visibility)
/*    */   {
/* 51 */     visibility = visibility.toLowerCase();
/* 52 */     if (visibility.equals("build")) {
/* 53 */       return 0;
/*    */     }
/* 55 */     if (visibility.equals("runtime")) {
/* 56 */       return 1;
/*    */     }
/* 58 */     if (visibility.equals("system")) {
/* 59 */       return 2;
/*    */     }
/* 61 */     throw new ExceptionWithContext("Invalid annotation visibility: %s", new Object[] { visibility });
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.AnnotationVisibility
 * JD-Core Version:    0.6.0
 */