/*    */ package org.jf.dexlib2.dexbacked.reference;
/*    */ 
/*    */ import org.jf.dexlib2.base.reference.BaseTypeReference;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ 
/*    */ public class DexBackedTypeReference extends BaseTypeReference
/*    */ {
/*    */   public final DexBackedDexFile dexFile;
/*    */   public final int typeIndex;
/*    */ 
/*    */   public DexBackedTypeReference(DexBackedDexFile dexFile, int typeIndex)
/*    */   {
/* 45 */     this.dexFile = dexFile;
/* 46 */     this.typeIndex = typeIndex;
/*    */   }
/*    */ 
/*    */   public String getType() {
/* 50 */     return this.dexFile.getType(this.typeIndex);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.reference.DexBackedTypeReference
 * JD-Core Version:    0.6.0
 */