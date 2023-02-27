/*    */ package org.jf.util;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ 
/*    */ public class ImmutableUtils
/*    */ {
/*    */   public static <T> ImmutableList<T> nullToEmptyList(ImmutableList<T> list)
/*    */   {
/* 43 */     if (list == null) {
/* 44 */       return ImmutableList.of();
/*    */     }
/* 46 */     return list;
/*    */   }
/*    */ 
/*    */   public static <T> ImmutableSet<T> nullToEmptySet(ImmutableSet<T> set) {
/* 50 */     if (set == null) {
/* 51 */       return ImmutableSet.of();
/*    */     }
/* 53 */     return set;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.util.ImmutableUtils
 * JD-Core Version:    0.6.0
 */