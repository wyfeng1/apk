/*    */ package com.google.common.collect;
/*    */ 
/*    */ final class Hashing
/*    */ {
/* 48 */   static int MAX_TABLE_SIZE = 1073741824;
/*    */ 
/*    */   static int smear(int hashCode)
/*    */   {
/* 45 */     return 461845907 * Integer.rotateLeft(hashCode * -862048943, 15);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.Hashing
 * JD-Core Version:    0.6.0
 */