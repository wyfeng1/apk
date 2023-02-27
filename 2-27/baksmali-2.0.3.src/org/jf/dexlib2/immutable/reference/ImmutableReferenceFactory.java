/*    */ package org.jf.dexlib2.immutable.reference;
/*    */ 
/*    */ import org.jf.dexlib2.iface.reference.FieldReference;
/*    */ import org.jf.dexlib2.iface.reference.MethodReference;
/*    */ import org.jf.dexlib2.iface.reference.Reference;
/*    */ import org.jf.dexlib2.iface.reference.StringReference;
/*    */ import org.jf.dexlib2.iface.reference.TypeReference;
/*    */ import org.jf.util.ExceptionWithContext;
/*    */ 
/*    */ public class ImmutableReferenceFactory
/*    */ {
/*    */   public static ImmutableReference of(int referenceType, Reference reference)
/*    */   {
/* 60 */     switch (referenceType) {
/*    */     case 0:
/* 62 */       return ImmutableStringReference.of((StringReference)reference);
/*    */     case 1:
/* 64 */       return ImmutableTypeReference.of((TypeReference)reference);
/*    */     case 2:
/* 66 */       return ImmutableFieldReference.of((FieldReference)reference);
/*    */     case 3:
/* 68 */       return ImmutableMethodReference.of((MethodReference)reference);
/*    */     }
/* 70 */     throw new ExceptionWithContext("Invalid reference type: %d", new Object[] { Integer.valueOf(referenceType) });
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.reference.ImmutableReferenceFactory
 * JD-Core Version:    0.6.0
 */