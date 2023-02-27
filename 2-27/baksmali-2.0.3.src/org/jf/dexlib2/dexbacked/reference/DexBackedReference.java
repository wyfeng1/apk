/*    */ package org.jf.dexlib2.dexbacked.reference;
/*    */ 
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.iface.reference.Reference;
/*    */ import org.jf.util.ExceptionWithContext;
/*    */ 
/*    */ public abstract class DexBackedReference
/*    */ {
/*    */   public static Reference makeReference(DexBackedDexFile dexFile, int referenceType, int referenceIndex)
/*    */   {
/* 43 */     switch (referenceType) {
/*    */     case 0:
/* 45 */       return new DexBackedStringReference(dexFile, referenceIndex);
/*    */     case 1:
/* 47 */       return new DexBackedTypeReference(dexFile, referenceIndex);
/*    */     case 3:
/* 49 */       return new DexBackedMethodReference(dexFile, referenceIndex);
/*    */     case 2:
/* 51 */       return new DexBackedFieldReference(dexFile, referenceIndex);
/*    */     }
/* 53 */     throw new ExceptionWithContext("Invalid reference type: %d", new Object[] { Integer.valueOf(referenceType) });
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.reference.DexBackedReference
 * JD-Core Version:    0.6.0
 */