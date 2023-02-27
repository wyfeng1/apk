/*    */ package org.jf.dexlib2.dexbacked.value;
/*    */ 
/*    */ import org.jf.dexlib2.base.value.BaseEnumEncodedValue;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.DexReader;
/*    */ import org.jf.dexlib2.dexbacked.reference.DexBackedFieldReference;
/*    */ import org.jf.dexlib2.iface.reference.FieldReference;
/*    */ 
/*    */ public class DexBackedEnumEncodedValue extends BaseEnumEncodedValue
/*    */ {
/*    */   public final DexBackedDexFile dexFile;
/*    */   private final int fieldIndex;
/*    */ 
/*    */   public DexBackedEnumEncodedValue(DexReader reader, int valueArg)
/*    */   {
/* 47 */     this.dexFile = ((DexBackedDexFile)reader.dexBuf);
/* 48 */     this.fieldIndex = reader.readSizedSmallUint(valueArg + 1);
/*    */   }
/*    */ 
/*    */   public FieldReference getValue() {
/* 52 */     return new DexBackedFieldReference(this.dexFile, this.fieldIndex);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.value.DexBackedEnumEncodedValue
 * JD-Core Version:    0.6.0
 */