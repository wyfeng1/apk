/*    */ package org.jf.dexlib2.dexbacked.value;
/*    */ 
/*    */ import java.util.Set;
/*    */ import org.jf.dexlib2.base.value.BaseAnnotationEncodedValue;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedAnnotationElement;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.DexReader;
/*    */ import org.jf.dexlib2.dexbacked.util.VariableSizeSet;
/*    */ import org.jf.dexlib2.iface.value.AnnotationEncodedValue;
/*    */ 
/*    */ public class DexBackedAnnotationEncodedValue extends BaseAnnotationEncodedValue
/*    */   implements AnnotationEncodedValue
/*    */ {
/*    */   public final DexBackedDexFile dexFile;
/*    */   public final String type;
/*    */   private final int elementCount;
/*    */   private final int elementsOffset;
/*    */ 
/*    */   public DexBackedAnnotationEncodedValue(DexReader reader)
/*    */   {
/* 51 */     this.dexFile = ((DexBackedDexFile)reader.dexBuf);
/* 52 */     this.type = this.dexFile.getType(reader.readSmallUleb128());
/* 53 */     this.elementCount = reader.readSmallUleb128();
/* 54 */     this.elementsOffset = reader.getOffset();
/* 55 */     skipElements(reader, this.elementCount);
/*    */   }
/*    */ 
/*    */   public static void skipFrom(DexReader reader) {
/* 59 */     reader.skipUleb128();
/* 60 */     int elementCount = reader.readSmallUleb128();
/* 61 */     skipElements(reader, elementCount);
/*    */   }
/*    */ 
/*    */   private static void skipElements(DexReader reader, int elementCount) {
/* 65 */     for (int i = 0; i < elementCount; i++) {
/* 66 */       reader.skipUleb128();
/* 67 */       DexBackedEncodedValue.skipFrom(reader);
/*    */     }
/*    */   }
/*    */ 
/*    */   public String getType() {
/* 71 */     return this.type;
/*    */   }
/*    */ 
/*    */   public Set<? extends DexBackedAnnotationElement> getElements()
/*    */   {
/* 76 */     return new VariableSizeSet(this.dexFile, this.elementsOffset, this.elementCount)
/*    */     {
/*    */       protected DexBackedAnnotationElement readNextItem(DexReader dexReader, int index)
/*    */       {
/* 80 */         return new DexBackedAnnotationElement(dexReader);
/*    */       }
/*    */     };
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.value.DexBackedAnnotationEncodedValue
 * JD-Core Version:    0.6.0
 */