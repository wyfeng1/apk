/*     */ package org.jf.dexlib2.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import org.jf.dexlib2.iface.reference.FieldReference;
/*     */ import org.jf.dexlib2.iface.reference.MethodReference;
/*     */ import org.jf.dexlib2.iface.reference.Reference;
/*     */ import org.jf.dexlib2.iface.reference.StringReference;
/*     */ import org.jf.dexlib2.iface.reference.TypeReference;
/*     */ import org.jf.util.StringUtils;
/*     */ 
/*     */ public final class ReferenceUtil
/*     */ {
/*     */   public static String getShortMethodDescriptor(MethodReference methodReference)
/*     */   {
/*  43 */     StringBuilder sb = new StringBuilder();
/*  44 */     sb.append(methodReference.getName());
/*  45 */     sb.append('(');
/*  46 */     for (CharSequence paramType : methodReference.getParameterTypes()) {
/*  47 */       sb.append(paramType);
/*     */     }
/*  49 */     sb.append(')');
/*  50 */     sb.append(methodReference.getReturnType());
/*  51 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String getMethodDescriptor(MethodReference methodReference) {
/*  55 */     StringBuilder sb = new StringBuilder();
/*  56 */     sb.append(methodReference.getDefiningClass());
/*  57 */     sb.append("->");
/*  58 */     sb.append(methodReference.getName());
/*  59 */     sb.append('(');
/*  60 */     for (CharSequence paramType : methodReference.getParameterTypes()) {
/*  61 */       sb.append(paramType);
/*     */     }
/*  63 */     sb.append(')');
/*  64 */     sb.append(methodReference.getReturnType());
/*  65 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static void writeMethodDescriptor(Writer writer, MethodReference methodReference) throws IOException {
/*  69 */     writer.write(methodReference.getDefiningClass());
/*  70 */     writer.write("->");
/*  71 */     writer.write(methodReference.getName());
/*  72 */     writer.write(40);
/*  73 */     for (CharSequence paramType : methodReference.getParameterTypes()) {
/*  74 */       writer.write(paramType.toString());
/*     */     }
/*  76 */     writer.write(41);
/*  77 */     writer.write(methodReference.getReturnType());
/*     */   }
/*     */ 
/*     */   public static String getFieldDescriptor(FieldReference fieldReference) {
/*  81 */     StringBuilder sb = new StringBuilder();
/*  82 */     sb.append(fieldReference.getDefiningClass());
/*  83 */     sb.append("->");
/*  84 */     sb.append(fieldReference.getName());
/*  85 */     sb.append(':');
/*  86 */     sb.append(fieldReference.getType());
/*  87 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static String getShortFieldDescriptor(FieldReference fieldReference) {
/*  91 */     StringBuilder sb = new StringBuilder();
/*  92 */     sb.append(fieldReference.getName());
/*  93 */     sb.append(':');
/*  94 */     sb.append(fieldReference.getType());
/*  95 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public static void writeFieldDescriptor(Writer writer, FieldReference fieldReference) throws IOException {
/*  99 */     writer.write(fieldReference.getDefiningClass());
/* 100 */     writer.write("->");
/* 101 */     writer.write(fieldReference.getName());
/* 102 */     writer.write(58);
/* 103 */     writer.write(fieldReference.getType());
/*     */   }
/*     */ 
/*     */   public static String getReferenceString(Reference reference)
/*     */   {
/* 108 */     if ((reference instanceof StringReference)) {
/* 109 */       return String.format("\"%s\"", new Object[] { StringUtils.escapeString(((StringReference)reference).getString()) });
/*     */     }
/* 111 */     if ((reference instanceof TypeReference)) {
/* 112 */       return ((TypeReference)reference).getType();
/*     */     }
/* 114 */     if ((reference instanceof FieldReference)) {
/* 115 */       return getFieldDescriptor((FieldReference)reference);
/*     */     }
/* 117 */     if ((reference instanceof MethodReference)) {
/* 118 */       return getMethodDescriptor((MethodReference)reference);
/*     */     }
/* 120 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.util.ReferenceUtil
 * JD-Core Version:    0.6.0
 */