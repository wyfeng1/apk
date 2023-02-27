/*    */ package org.jf.dexlib2.dexbacked;
/*    */ 
/*    */ import java.util.Set;
/*    */ import org.jf.dexlib2.base.BaseAnnotation;
/*    */ import org.jf.dexlib2.dexbacked.util.VariableSizeSet;
/*    */ 
/*    */ public class DexBackedAnnotation extends BaseAnnotation
/*    */ {
/*    */   public final DexBackedDexFile dexFile;
/*    */   public final int visibility;
/*    */   public final int typeIndex;
/*    */   private final int elementsOffset;
/*    */ 
/*    */   public DexBackedAnnotation(DexBackedDexFile dexFile, int annotationOffset)
/*    */   {
/* 49 */     this.dexFile = dexFile;
/*    */ 
/* 51 */     DexReader reader = dexFile.readerAt(annotationOffset);
/* 52 */     this.visibility = reader.readUbyte();
/* 53 */     this.typeIndex = reader.readSmallUleb128();
/* 54 */     this.elementsOffset = reader.getOffset();
/*    */   }
/*    */   public int getVisibility() {
/* 57 */     return this.visibility; } 
/* 58 */   public String getType() { return this.dexFile.getType(this.typeIndex);
/*    */   }
/*    */ 
/*    */   public Set<? extends DexBackedAnnotationElement> getElements()
/*    */   {
/* 63 */     DexReader reader = this.dexFile.readerAt(this.elementsOffset);
/* 64 */     int size = reader.readSmallUleb128();
/*    */ 
/* 66 */     return new VariableSizeSet(this.dexFile, reader.getOffset(), size)
/*    */     {
/*    */       protected DexBackedAnnotationElement readNextItem(DexReader reader, int index)
/*    */       {
/* 70 */         return new DexBackedAnnotationElement(reader);
/*    */       }
/*    */     };
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.DexBackedAnnotation
 * JD-Core Version:    0.6.0
 */