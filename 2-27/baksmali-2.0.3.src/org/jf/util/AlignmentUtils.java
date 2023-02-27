/*    */ package org.jf.util;
/*    */ 
/*    */ public abstract class AlignmentUtils
/*    */ {
/*    */   public static int alignOffset(int offset, int alignment)
/*    */   {
/* 33 */     int mask = alignment - 1;
/* 34 */     assert ((alignment >= 0) && ((mask & alignment) == 0));
/* 35 */     return offset + mask & (mask ^ 0xFFFFFFFF);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.util.AlignmentUtils
 * JD-Core Version:    0.6.0
 */