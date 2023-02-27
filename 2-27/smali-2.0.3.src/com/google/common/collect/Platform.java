/*    */ package com.google.common.collect;
/*    */ 
/*    */ import java.lang.reflect.Array;
/*    */ 
/*    */ class Platform
/*    */ {
/*    */   static <T> T[] newArray(T[] reference, int length)
/*    */   {
/* 54 */     Class type = reference.getClass().getComponentType();
/*    */ 
/* 59 */     Object[] result = (Object[])(Object[])Array.newInstance(type, length);
/* 60 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.Platform
 * JD-Core Version:    0.6.0
 */