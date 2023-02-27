/*    */ package org.jf.dexlib2.base;
/*    */ 
/*    */ import org.jf.dexlib2.base.reference.BaseTypeReference;
/*    */ import org.jf.dexlib2.iface.Annotation;
/*    */ import org.jf.dexlib2.iface.AnnotationElement;
/*    */ import org.jf.dexlib2.iface.MethodParameter;
/*    */ import org.jf.dexlib2.iface.value.ArrayEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ import org.jf.dexlib2.iface.value.StringEncodedValue;
/*    */ 
/*    */ public abstract class BaseMethodParameter extends BaseTypeReference
/*    */   implements MethodParameter
/*    */ {
/*    */   public String getSignature()
/*    */   {
/* 49 */     Annotation signatureAnnotation = null;
/* 50 */     for (Annotation annotation : getAnnotations()) {
/* 51 */       if (annotation.getType().equals("Ldalvik/annotation/Signature;")) {
/* 52 */         signatureAnnotation = annotation;
/* 53 */         break;
/*    */       }
/*    */     }
/* 56 */     if (signatureAnnotation == null) {
/* 57 */       return null;
/*    */     }
/*    */ 
/* 60 */     ArrayEncodedValue signatureValues = null;
/* 61 */     for (AnnotationElement annotationElement : signatureAnnotation.getElements()) {
/* 62 */       if (annotationElement.getName().equals("value")) {
/* 63 */         EncodedValue encodedValue = annotationElement.getValue();
/* 64 */         if (encodedValue.getValueType() != 28) {
/* 65 */           return null;
/*    */         }
/* 67 */         signatureValues = (ArrayEncodedValue)encodedValue;
/* 68 */         break;
/*    */       }
/*    */     }
/* 71 */     if (signatureValues == null) {
/* 72 */       return null;
/*    */     }
/*    */ 
/* 75 */     StringBuilder sb = new StringBuilder();
/* 76 */     for (EncodedValue signatureValue : signatureValues.getValue()) {
/* 77 */       if (signatureValue.getValueType() != 23) {
/* 78 */         return null;
/*    */       }
/* 80 */       sb.append(((StringEncodedValue)signatureValue).getValue());
/*    */     }
/* 82 */     return sb.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.BaseMethodParameter
 * JD-Core Version:    0.6.0
 */