/*    */ package org.jf.dexlib2.util;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import org.jf.dexlib2.AccessFlags;
/*    */ import org.jf.dexlib2.iface.Field;
/*    */ 
/*    */ public final class FieldUtil
/*    */ {
/* 42 */   public static Predicate<Field> FIELD_IS_STATIC = new Predicate() {
/*    */     public boolean apply(Field input) {
/* 44 */       return (input != null) && (FieldUtil.isStatic(input));
/*    */     }
/* 42 */   };
/*    */ 
/* 48 */   public static Predicate<Field> FIELD_IS_INSTANCE = new Predicate() {
/*    */     public boolean apply(Field input) {
/* 50 */       return (input != null) && (!FieldUtil.isStatic(input));
/*    */     }
/* 48 */   };
/*    */ 
/*    */   public static boolean isStatic(Field field)
/*    */   {
/* 55 */     return AccessFlags.STATIC.isSet(field.getAccessFlags());
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.util.FieldUtil
 * JD-Core Version:    0.6.0
 */