/*    */ package org.jf.dexlib2.dexbacked.reference;
/*    */ 
/*    */ import org.jf.dexlib2.base.reference.BaseFieldReference;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ 
/*    */ public class DexBackedFieldReference extends BaseFieldReference
/*    */ {
/*    */   public final DexBackedDexFile dexFile;
/*    */   public final int fieldIdItemOffset;
/*    */ 
/*    */   public DexBackedFieldReference(DexBackedDexFile dexFile, int fieldIndex)
/*    */   {
/* 45 */     this.dexFile = dexFile;
/* 46 */     this.fieldIdItemOffset = dexFile.getFieldIdItemOffset(fieldIndex);
/*    */   }
/*    */ 
/*    */   public String getDefiningClass()
/*    */   {
/* 52 */     return this.dexFile.getType(this.dexFile.readUshort(this.fieldIdItemOffset + 0));
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 58 */     return this.dexFile.getString(this.dexFile.readSmallUint(this.fieldIdItemOffset + 4));
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 64 */     return this.dexFile.getType(this.dexFile.readUshort(this.fieldIdItemOffset + 2));
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.reference.DexBackedFieldReference
 * JD-Core Version:    0.6.0
 */