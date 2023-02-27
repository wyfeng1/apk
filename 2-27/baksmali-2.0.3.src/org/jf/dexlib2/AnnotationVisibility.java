/*    */ package org.jf.dexlib2;
/*    */ 
/*    */ import org.jf.util.ExceptionWithContext;
/*    */ 
/*    */ public final class AnnotationVisibility
/*    */ {
/* 41 */   private static String[] NAMES = { "build", "runtime", "system" };
/*    */ 
/*    */   public static String getVisibility(int visibility) {
/* 44 */     if ((visibility < 0) || (visibility >= NAMES.length)) {
/* 45 */       throw new ExceptionWithContext("Invalid annotation visibility %d", new Object[] { Integer.valueOf(visibility) });
/*    */     }
/* 47 */     return NAMES[visibility];
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.AnnotationVisibility
 * JD-Core Version:    0.6.0
 */