/*    */ package org.jf.dexlib2.dexbacked.value;
/*    */ 
/*    */ import org.jf.dexlib2.base.value.BaseStringEncodedValue;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.DexReader;
/*    */ 
/*    */ public class DexBackedStringEncodedValue extends BaseStringEncodedValue
/*    */ {
/*    */   public final DexBackedDexFile dexFile;
/*    */   private final int stringIndex;
/*    */ 
/*    */   public DexBackedStringEncodedValue(DexReader reader, int valueArg)
/*    */   {
/* 45 */     this.dexFile = ((DexBackedDexFile)reader.dexBuf);
/* 46 */     this.stringIndex = reader.readSizedSmallUint(valueArg + 1);
/*    */   }
/*    */ 
/*    */   public String getValue() {
/* 50 */     return this.dexFile.getString(this.stringIndex);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.value.DexBackedStringEncodedValue
 * JD-Core Version:    0.6.0
 */