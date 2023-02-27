/*    */ package org.jf.dexlib2.util;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import java.util.Collection;
/*    */ import org.jf.dexlib2.AccessFlags;
/*    */ import org.jf.dexlib2.iface.Method;
/*    */ import org.jf.dexlib2.iface.reference.MethodReference;
/*    */ 
/*    */ public final class MethodUtil
/*    */ {
/* 44 */   private static int directMask = AccessFlags.STATIC.getValue() | AccessFlags.PRIVATE.getValue() | AccessFlags.CONSTRUCTOR.getValue();
/*    */ 
/* 47 */   public static Predicate<Method> METHOD_IS_DIRECT = new Predicate() {
/*    */     public boolean apply(Method input) {
/* 49 */       return (input != null) && (MethodUtil.isDirect(input));
/*    */     }
/* 47 */   };
/*    */ 
/* 53 */   public static Predicate<Method> METHOD_IS_VIRTUAL = new Predicate() {
/*    */     public boolean apply(Method input) {
/* 55 */       return (input != null) && (!MethodUtil.isDirect(input));
/*    */     }
/* 53 */   };
/*    */ 
/*    */   public static boolean isDirect(Method method)
/*    */   {
/* 60 */     return (method.getAccessFlags() & directMask) != 0;
/*    */   }
/*    */ 
/*    */   public static boolean isStatic(Method method) {
/* 64 */     return AccessFlags.STATIC.isSet(method.getAccessFlags());
/*    */   }
/*    */ 
/*    */   public static boolean isConstructor(MethodReference methodReference) {
/* 68 */     return methodReference.getName().equals("<init>");
/*    */   }
/*    */ 
/*    */   public static int getParameterRegisterCount(Method method) {
/* 72 */     return getParameterRegisterCount(method, isStatic(method));
/*    */   }
/*    */ 
/*    */   public static int getParameterRegisterCount(MethodReference methodRef, boolean isStatic) {
/* 76 */     return getParameterRegisterCount(methodRef.getParameterTypes(), isStatic);
/*    */   }
/*    */ 
/*    */   public static int getParameterRegisterCount(Collection<? extends CharSequence> parameterTypes, boolean isStatic)
/*    */   {
/* 81 */     int regCount = 0;
/* 82 */     for (CharSequence paramType : parameterTypes) {
/* 83 */       int firstChar = paramType.charAt(0);
/* 84 */       if ((firstChar == 74) || (firstChar == 68))
/* 85 */         regCount += 2;
/*    */       else {
/* 87 */         regCount++;
/*    */       }
/*    */     }
/* 90 */     if (!isStatic) {
/* 91 */       regCount++;
/*    */     }
/* 93 */     return regCount;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.util.MethodUtil
 * JD-Core Version:    0.6.0
 */