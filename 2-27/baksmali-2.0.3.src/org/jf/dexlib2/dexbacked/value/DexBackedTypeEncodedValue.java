/*    */ package org.jf.dexlib2.dexbacked.value;
/*    */ 
/*    */ import org.jf.dexlib2.base.value.BaseTypeEncodedValue;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.DexReader;
/*    */ 
/*    */ public class DexBackedTypeEncodedValue extends BaseTypeEncodedValue
/*    */ {
/*    */   public final DexBackedDexFile dexFile;
/*    */   private final int typeIndex;
/*    */ 
/*    */   public DexBackedTypeEncodedValue(DexReader reader, int valueArg)
/*    */   {
/* 45 */     this.dexFile = ((DexBackedDexFile)reader.dexBuf);
/* 46 */     this.typeIndex = reader.readSizedSmallUint(valueArg + 1);
/*    */   }
/*    */ 
/*    */   public String getValue() {
/* 50 */     return this.dexFile.getType(this.typeIndex);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.value.DexBackedTypeEncodedValue
 * JD-Core Version:    0.6.0
 */