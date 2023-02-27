/*    */ package org.jf.dexlib2.dexbacked.value;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.jf.dexlib2.base.value.BaseArrayEncodedValue;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.DexReader;
/*    */ import org.jf.dexlib2.dexbacked.util.VariableSizeList;
/*    */ import org.jf.dexlib2.iface.value.ArrayEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ 
/*    */ public class DexBackedArrayEncodedValue extends BaseArrayEncodedValue
/*    */   implements ArrayEncodedValue
/*    */ {
/*    */   public final DexBackedDexFile dexFile;
/*    */   private final int elementCount;
/*    */   private final int encodedArrayOffset;
/*    */ 
/*    */   public DexBackedArrayEncodedValue(DexReader reader)
/*    */   {
/* 50 */     this.dexFile = ((DexBackedDexFile)reader.dexBuf);
/* 51 */     this.elementCount = reader.readSmallUleb128();
/* 52 */     this.encodedArrayOffset = reader.getOffset();
/* 53 */     skipElementsFrom(reader, this.elementCount);
/*    */   }
/*    */ 
/*    */   public static void skipFrom(DexReader reader) {
/* 57 */     int elementCount = reader.readSmallUleb128();
/* 58 */     skipElementsFrom(reader, elementCount);
/*    */   }
/*    */ 
/*    */   private static void skipElementsFrom(DexReader reader, int elementCount) {
/* 62 */     for (int i = 0; i < elementCount; i++)
/* 63 */       DexBackedEncodedValue.skipFrom(reader);
/*    */   }
/*    */ 
/*    */   public List<? extends EncodedValue> getValue()
/*    */   {
/* 70 */     return new VariableSizeList(this.dexFile, this.encodedArrayOffset, this.elementCount)
/*    */     {
/*    */       protected EncodedValue readNextItem(DexReader dexReader, int index)
/*    */       {
/* 74 */         return DexBackedEncodedValue.readFrom(dexReader);
/*    */       }
/*    */     };
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.value.DexBackedArrayEncodedValue
 * JD-Core Version:    0.6.0
 */