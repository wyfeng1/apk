/*    */ package org.jf.dexlib2.dexbacked.raw;
/*    */ 
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
/*    */ import org.jf.dexlib2.util.AnnotatedBytes;
/*    */ 
/*    */ public class AnnotationSetRefList
/*    */ {
/*    */   public static SectionAnnotator makeAnnotator(DexAnnotator annotator, MapItem mapItem)
/*    */   {
/* 47 */     return new SectionAnnotator(annotator, mapItem) {
/*    */       public String getItemName() {
/* 49 */         return "annotation_set_ref_list";
/*    */       }
/*    */ 
/*    */       protected void annotateItem(AnnotatedBytes out, int itemIndex, String itemIdentity)
/*    */       {
/* 54 */         int size = this.dexFile.readSmallUint(out.getCursor());
/* 55 */         out.annotate(4, "size = %d", new Object[] { Integer.valueOf(size) });
/*    */ 
/* 57 */         for (int i = 0; i < size; i++) {
/* 58 */           int annotationSetOffset = this.dexFile.readSmallUint(out.getCursor());
/* 59 */           out.annotate(4, "annotation_set_item[0x%x]", new Object[] { Integer.valueOf(annotationSetOffset) });
/*    */         }
/*    */       }
/*    */ 
/*    */       public int getItemAlignment() {
/* 64 */         return 4;
/*    */       }
/*    */     };
/*    */   }
/*    */ 
/*    */   public static String getReferenceAnnotation(DexBackedDexFile dexFile, int annotationSetRefListOffset) {
/* 71 */     if (annotationSetRefListOffset == 0) {
/* 72 */       return "annotation_set_ref_list[NO_OFFSET]";
/*    */     }
/* 74 */     return String.format("annotation_set_ref_list[0x%x]", new Object[] { Integer.valueOf(annotationSetRefListOffset) });
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.AnnotationSetRefList
 * JD-Core Version:    0.6.0
 */