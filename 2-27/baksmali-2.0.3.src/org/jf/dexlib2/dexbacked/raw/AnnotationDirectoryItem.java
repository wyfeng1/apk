/*     */ package org.jf.dexlib2.dexbacked.raw;
/*     */ 
/*     */ import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
/*     */ import org.jf.dexlib2.util.AnnotatedBytes;
/*     */ 
/*     */ public class AnnotationDirectoryItem
/*     */ {
/*     */   public static SectionAnnotator makeAnnotator(DexAnnotator annotator, MapItem mapItem)
/*     */   {
/*  48 */     return new SectionAnnotator(annotator, mapItem) {
/*     */       public String getItemName() {
/*  50 */         return "annotation_directory_item";
/*     */       }
/*     */ 
/*     */       public int getItemAlignment() {
/*  54 */         return 4;
/*     */       }
/*     */ 
/*     */       protected void annotateItem(AnnotatedBytes out, int itemIndex, String itemIdentity)
/*     */       {
/*  59 */         int classAnnotationsOffset = this.dexFile.readSmallUint(out.getCursor());
/*  60 */         out.annotate(4, "class_annotations_off = %s", new Object[] { AnnotationSetItem.getReferenceAnnotation(this.dexFile, classAnnotationsOffset) });
/*     */ 
/*  63 */         int fieldsSize = this.dexFile.readSmallUint(out.getCursor());
/*  64 */         out.annotate(4, "fields_size = %d", new Object[] { Integer.valueOf(fieldsSize) });
/*     */ 
/*  66 */         int annotatedMethodsSize = this.dexFile.readSmallUint(out.getCursor());
/*  67 */         out.annotate(4, "annotated_methods_size = %d", new Object[] { Integer.valueOf(annotatedMethodsSize) });
/*     */ 
/*  69 */         int annotatedParameterSize = this.dexFile.readSmallUint(out.getCursor());
/*  70 */         out.annotate(4, "annotated_parameters_size = %d", new Object[] { Integer.valueOf(annotatedParameterSize) });
/*     */ 
/*  72 */         if (fieldsSize > 0) {
/*  73 */           out.annotate(0, "field_annotations:", new Object[0]);
/*  74 */           out.indent();
/*  75 */           for (int i = 0; i < fieldsSize; i++) {
/*  76 */             out.annotate(0, "field_annotation[%d]", new Object[] { Integer.valueOf(i) });
/*  77 */             out.indent();
/*  78 */             int fieldIndex = this.dexFile.readSmallUint(out.getCursor());
/*  79 */             out.annotate(4, "%s", new Object[] { FieldIdItem.getReferenceAnnotation(this.dexFile, fieldIndex) });
/*  80 */             int annotationOffset = this.dexFile.readSmallUint(out.getCursor());
/*  81 */             out.annotate(4, "%s", new Object[] { AnnotationSetItem.getReferenceAnnotation(this.dexFile, annotationOffset) });
/*  82 */             out.deindent();
/*     */           }
/*  84 */           out.deindent();
/*     */         }
/*     */ 
/*  87 */         if (annotatedMethodsSize > 0) {
/*  88 */           out.annotate(0, "method_annotations:", new Object[0]);
/*  89 */           out.indent();
/*  90 */           for (int i = 0; i < annotatedMethodsSize; i++) {
/*  91 */             out.annotate(0, "method_annotation[%d]", new Object[] { Integer.valueOf(i) });
/*  92 */             out.indent();
/*  93 */             int methodIndex = this.dexFile.readSmallUint(out.getCursor());
/*  94 */             out.annotate(4, "%s", new Object[] { MethodIdItem.getReferenceAnnotation(this.dexFile, methodIndex) });
/*  95 */             int annotationOffset = this.dexFile.readSmallUint(out.getCursor());
/*  96 */             out.annotate(4, "%s", new Object[] { AnnotationSetItem.getReferenceAnnotation(this.dexFile, annotationOffset) });
/*  97 */             out.deindent();
/*     */           }
/*  99 */           out.deindent();
/*     */         }
/*     */ 
/* 102 */         if (annotatedParameterSize > 0) {
/* 103 */           out.annotate(0, "parameter_annotations:", new Object[0]);
/* 104 */           out.indent();
/* 105 */           for (int i = 0; i < annotatedParameterSize; i++) {
/* 106 */             out.annotate(0, "parameter_annotation[%d]", new Object[] { Integer.valueOf(i) });
/* 107 */             out.indent();
/* 108 */             int methodIndex = this.dexFile.readSmallUint(out.getCursor());
/* 109 */             out.annotate(4, "%s", new Object[] { MethodIdItem.getReferenceAnnotation(this.dexFile, methodIndex) });
/* 110 */             int annotationOffset = this.dexFile.readSmallUint(out.getCursor());
/* 111 */             out.annotate(4, "%s", new Object[] { AnnotationSetRefList.getReferenceAnnotation(this.dexFile, annotationOffset) });
/* 112 */             out.deindent();
/*     */           }
/* 114 */           out.deindent();
/*     */         }
/*     */       }
/*     */     };
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.AnnotationDirectoryItem
 * JD-Core Version:    0.6.0
 */