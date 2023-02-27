/*    */ package org.jf.dexlib2;
/*    */ 
/*    */ import org.jf.dexlib2.iface.reference.FieldReference;
/*    */ import org.jf.dexlib2.iface.reference.MethodReference;
/*    */ import org.jf.dexlib2.iface.reference.Reference;
/*    */ import org.jf.dexlib2.iface.reference.StringReference;
/*    */ import org.jf.dexlib2.iface.reference.TypeReference;
/*    */ import org.jf.util.ExceptionWithContext;
/*    */ 
/*    */ public final class ReferenceType
/*    */ {
/*    */   public static String toString(int referenceType)
/*    */   {
/* 45 */     switch (referenceType) {
/*    */     case 0:
/* 47 */       return "string";
/*    */     case 1:
/* 49 */       return "type";
/*    */     case 2:
/* 51 */       return "field";
/*    */     case 3:
/* 53 */       return "method";
/*    */     }
/* 55 */     throw new InvalidReferenceTypeException(referenceType);
/*    */   }
/*    */ 
/*    */   public static int getReferenceType(Reference reference)
/*    */   {
/* 60 */     if ((reference instanceof StringReference))
/* 61 */       return 0;
/* 62 */     if ((reference instanceof TypeReference))
/* 63 */       return 1;
/* 64 */     if ((reference instanceof FieldReference))
/* 65 */       return 2;
/* 66 */     if ((reference instanceof MethodReference)) {
/* 67 */       return 3;
/*    */     }
/* 69 */     throw new IllegalStateException("Invalid reference");
/*    */   }
/*    */ 
/*    */   public static void validateReferenceType(int referenceType)
/*    */   {
/* 79 */     if ((referenceType < 0) || (referenceType > 3))
/* 80 */       throw new InvalidReferenceTypeException(referenceType);
/*    */   }
/*    */ 
/*    */   public static class InvalidReferenceTypeException extends ExceptionWithContext {
/*    */     private final int referenceType;
/*    */ 
/*    */     public InvalidReferenceTypeException(int referenceType) {
/* 88 */       super(new Object[] { Integer.valueOf(referenceType) });
/* 89 */       this.referenceType = referenceType;
/*    */     }
/*    */ 
/*    */     public int getReferenceType()
/*    */     {
/* 98 */       return this.referenceType;
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.ReferenceType
 * JD-Core Version:    0.6.0
 */