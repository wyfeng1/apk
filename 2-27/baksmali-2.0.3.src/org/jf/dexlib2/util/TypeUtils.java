/*    */ package org.jf.dexlib2.util;
/*    */ 
/*    */ import org.jf.dexlib2.iface.reference.TypeReference;
/*    */ 
/*    */ public final class TypeUtils
/*    */ {
/*    */   public static boolean isWideType(String type)
/*    */   {
/* 40 */     char c = type.charAt(0);
/* 41 */     return (c == 'J') || (c == 'D');
/*    */   }
/*    */ 
/*    */   public static boolean isWideType(TypeReference type) {
/* 45 */     return isWideType(type.getType());
/*    */   }
/*    */ 
/*    */   public static boolean isPrimitiveType(String type) {
/* 49 */     return type.length() == 1;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.util.TypeUtils
 * JD-Core Version:    0.6.0
 */