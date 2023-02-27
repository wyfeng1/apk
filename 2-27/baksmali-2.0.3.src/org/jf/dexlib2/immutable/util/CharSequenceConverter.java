/*    */ package org.jf.dexlib2.immutable.util;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import org.jf.util.ImmutableConverter;
/*    */ 
/*    */ public final class CharSequenceConverter
/*    */ {
/* 49 */   private static final ImmutableConverter<String, CharSequence> CONVERTER = new ImmutableConverter()
/*    */   {
/*    */     protected boolean isImmutable(CharSequence item)
/*    */     {
/* 53 */       return item instanceof String;
/*    */     }
/*    */ 
/*    */     protected String makeImmutable(CharSequence item)
/*    */     {
/* 59 */       return item.toString();
/*    */     }
/* 49 */   };
/*    */ 
/*    */   public static ImmutableList<String> immutableStringList(Iterable<? extends CharSequence> iterable)
/*    */   {
/* 46 */     return CONVERTER.toList(iterable);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.util.CharSequenceConverter
 * JD-Core Version:    0.6.0
 */