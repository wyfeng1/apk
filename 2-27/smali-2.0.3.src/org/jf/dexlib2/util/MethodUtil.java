/*     */ package org.jf.dexlib2.util;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Collection;
/*     */ import org.jf.dexlib2.AccessFlags;
/*     */ import org.jf.dexlib2.iface.Method;
/*     */ import org.jf.dexlib2.iface.reference.MethodReference;
/*     */ 
/*     */ public final class MethodUtil
/*     */ {
/*  44 */   private static int directMask = AccessFlags.STATIC.getValue() | AccessFlags.PRIVATE.getValue() | AccessFlags.CONSTRUCTOR.getValue();
/*     */ 
/*  47 */   public static Predicate<Method> METHOD_IS_DIRECT = new Predicate() {
/*     */     public boolean apply(Method input) {
/*  49 */       return (input != null) && (MethodUtil.isDirect(input));
/*     */     }
/*  47 */   };
/*     */ 
/*  53 */   public static Predicate<Method> METHOD_IS_VIRTUAL = new Predicate() {
/*     */     public boolean apply(Method input) {
/*  55 */       return (input != null) && (!MethodUtil.isDirect(input));
/*     */     }
/*  53 */   };
/*     */ 
/*     */   public static boolean isDirect(Method method)
/*     */   {
/*  60 */     return (method.getAccessFlags() & directMask) != 0;
/*     */   }
/*     */ 
/*     */   public static int getParameterRegisterCount(MethodReference methodRef, boolean isStatic)
/*     */   {
/*  76 */     return getParameterRegisterCount(methodRef.getParameterTypes(), isStatic);
/*     */   }
/*     */ 
/*     */   public static int getParameterRegisterCount(Collection<? extends CharSequence> parameterTypes, boolean isStatic)
/*     */   {
/*  81 */     int regCount = 0;
/*  82 */     for (CharSequence paramType : parameterTypes) {
/*  83 */       int firstChar = paramType.charAt(0);
/*  84 */       if ((firstChar == 74) || (firstChar == 68))
/*  85 */         regCount += 2;
/*     */       else {
/*  87 */         regCount++;
/*     */       }
/*     */     }
/*  90 */     if (!isStatic) {
/*  91 */       regCount++;
/*     */     }
/*  93 */     return regCount;
/*     */   }
/*     */ 
/*     */   private static char getShortyType(CharSequence type) {
/*  97 */     if (type.length() > 1) {
/*  98 */       return 'L';
/*     */     }
/* 100 */     return type.charAt(0);
/*     */   }
/*     */ 
/*     */   public static String getShorty(Collection<? extends CharSequence> params, String returnType) {
/* 104 */     StringBuilder sb = new StringBuilder(params.size() + 1);
/* 105 */     sb.append(getShortyType(returnType));
/* 106 */     for (CharSequence typeRef : params) {
/* 107 */       sb.append(getShortyType(typeRef));
/*     */     }
/* 109 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.util.MethodUtil
 * JD-Core Version:    0.6.0
 */