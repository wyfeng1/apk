/*    */ package org.jf.util;
/*    */ 
/*    */ import com.google.common.base.Function;
/*    */ import com.google.common.base.Functions;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ 
/*    */ public class CharSequenceUtils
/*    */ {
/* 41 */   private static final Function<Object, String> TO_STRING = Functions.toStringFunction();
/*    */ 
/*    */   public static boolean listEquals(List<? extends CharSequence> list1, List<? extends CharSequence> list2)
/*    */   {
/* 48 */     return Lists.transform(list1, TO_STRING).equals(Lists.transform(list2, TO_STRING));
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.util.CharSequenceUtils
 * JD-Core Version:    0.6.0
 */