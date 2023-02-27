/*     */ package org.jf.dexlib2.dexbacked.raw;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import org.jf.dexlib2.AccessFlags;
/*     */ import org.jf.dexlib2.dexbacked.DexReader;
/*     */ import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
/*     */ import org.jf.dexlib2.util.AnnotatedBytes;
/*     */ 
/*     */ public class ClassDataItem
/*     */ {
/*     */   public static SectionAnnotator makeAnnotator(DexAnnotator annotator, MapItem mapItem)
/*     */   {
/*  46 */     return new SectionAnnotator(annotator, mapItem) {
/*  47 */       private SectionAnnotator codeItemAnnotator = null;
/*     */ 
/*     */       public void annotateSection(AnnotatedBytes out) {
/*  50 */         this.codeItemAnnotator = this.annotator.getAnnotator(8193);
/*  51 */         super.annotateSection(out);
/*     */       }
/*     */ 
/*     */       public String getItemName()
/*     */       {
/*  56 */         return "class_data_item";
/*     */       }
/*     */ 
/*     */       protected void annotateItem(AnnotatedBytes out, int itemIndex, String itemIdentity)
/*     */       {
/*  61 */         DexReader reader = this.dexFile.readerAt(out.getCursor());
/*     */ 
/*  63 */         int staticFieldsSize = reader.readSmallUleb128();
/*  64 */         out.annotateTo(reader.getOffset(), "static_fields_size = %d", new Object[] { Integer.valueOf(staticFieldsSize) });
/*     */ 
/*  66 */         int instanceFieldsSize = reader.readSmallUleb128();
/*  67 */         out.annotateTo(reader.getOffset(), "instance_fields_size = %d", new Object[] { Integer.valueOf(instanceFieldsSize) });
/*     */ 
/*  69 */         int directMethodsSize = reader.readSmallUleb128();
/*  70 */         out.annotateTo(reader.getOffset(), "direct_methods_size = %d", new Object[] { Integer.valueOf(directMethodsSize) });
/*     */ 
/*  72 */         int virtualMethodsSize = reader.readSmallUleb128();
/*  73 */         out.annotateTo(reader.getOffset(), "virtual_methods_size = %d", new Object[] { Integer.valueOf(virtualMethodsSize) });
/*     */ 
/*  75 */         int previousIndex = 0;
/*  76 */         if (staticFieldsSize > 0) {
/*  77 */           out.annotate(0, "static_fields:", new Object[0]);
/*  78 */           out.indent();
/*  79 */           for (int i = 0; i < staticFieldsSize; i++) {
/*  80 */             out.annotate(0, "static_field[%d]", new Object[] { Integer.valueOf(i) });
/*  81 */             out.indent();
/*  82 */             previousIndex = annotateEncodedField(out, this.dexFile, reader, previousIndex);
/*  83 */             out.deindent();
/*     */           }
/*  85 */           out.deindent();
/*     */         }
/*     */ 
/*  88 */         if (instanceFieldsSize > 0) {
/*  89 */           out.annotate(0, "instance_fields:", new Object[0]);
/*  90 */           out.indent();
/*  91 */           previousIndex = 0;
/*  92 */           for (int i = 0; i < instanceFieldsSize; i++) {
/*  93 */             out.annotate(0, "instance_field[%d]", new Object[] { Integer.valueOf(i) });
/*  94 */             out.indent();
/*  95 */             previousIndex = annotateEncodedField(out, this.dexFile, reader, previousIndex);
/*  96 */             out.deindent();
/*     */           }
/*  98 */           out.deindent();
/*     */         }
/*     */ 
/* 101 */         if (directMethodsSize > 0) {
/* 102 */           out.annotate(0, "direct_methods:", new Object[0]);
/* 103 */           out.indent();
/* 104 */           previousIndex = 0;
/* 105 */           for (int i = 0; i < directMethodsSize; i++) {
/* 106 */             out.annotate(0, "direct_method[%d]", new Object[] { Integer.valueOf(i) });
/* 107 */             out.indent();
/* 108 */             previousIndex = annotateEncodedMethod(out, this.dexFile, reader, previousIndex);
/* 109 */             out.deindent();
/*     */           }
/* 111 */           out.deindent();
/*     */         }
/*     */ 
/* 114 */         if (virtualMethodsSize > 0) {
/* 115 */           out.annotate(0, "virtual_methods:", new Object[0]);
/* 116 */           out.indent();
/* 117 */           previousIndex = 0;
/* 118 */           for (int i = 0; i < virtualMethodsSize; i++) {
/* 119 */             out.annotate(0, "virtual_method[%d]", new Object[] { Integer.valueOf(i) });
/* 120 */             out.indent();
/* 121 */             previousIndex = annotateEncodedMethod(out, this.dexFile, reader, previousIndex);
/* 122 */             out.deindent();
/*     */           }
/* 124 */           out.deindent();
/*     */         }
/*     */       }
/*     */ 
/*     */       private int annotateEncodedField(AnnotatedBytes out, RawDexFile dexFile, DexReader reader, int previousIndex)
/*     */       {
/* 132 */         int indexDelta = reader.readLargeUleb128();
/* 133 */         int fieldIndex = previousIndex + indexDelta;
/* 134 */         out.annotateTo(reader.getOffset(), "field_idx_diff = %d: %s", new Object[] { Integer.valueOf(indexDelta), FieldIdItem.getReferenceAnnotation(dexFile, fieldIndex) });
/*     */ 
/* 137 */         int accessFlags = reader.readSmallUleb128();
/* 138 */         out.annotateTo(reader.getOffset(), "access_flags = 0x%x: %s", new Object[] { Integer.valueOf(accessFlags), Joiner.on('|').join(AccessFlags.getAccessFlagsForField(accessFlags)) });
/*     */ 
/* 141 */         return fieldIndex;
/*     */       }
/*     */ 
/*     */       private int annotateEncodedMethod(AnnotatedBytes out, RawDexFile dexFile, DexReader reader, int previousIndex)
/*     */       {
/* 148 */         int indexDelta = reader.readLargeUleb128();
/* 149 */         int methodIndex = previousIndex + indexDelta;
/* 150 */         out.annotateTo(reader.getOffset(), "method_idx_diff = %d: %s", new Object[] { Integer.valueOf(indexDelta), MethodIdItem.getReferenceAnnotation(dexFile, methodIndex) });
/*     */ 
/* 153 */         int accessFlags = reader.readSmallUleb128();
/* 154 */         out.annotateTo(reader.getOffset(), "access_flags = 0x%x: %s", new Object[] { Integer.valueOf(accessFlags), Joiner.on('|').join(AccessFlags.getAccessFlagsForMethod(accessFlags)) });
/*     */ 
/* 157 */         int codeOffset = reader.readSmallUleb128();
/* 158 */         if (codeOffset == 0) {
/* 159 */           out.annotateTo(reader.getOffset(), "code_off = code_item[NO_OFFSET]", new Object[0]);
/*     */         } else {
/* 161 */           out.annotateTo(reader.getOffset(), "code_off = code_item[0x%x]", new Object[] { Integer.valueOf(codeOffset) });
/* 162 */           addCodeItemIdentity(codeOffset, MethodIdItem.asString(dexFile, methodIndex));
/*     */         }
/*     */ 
/* 165 */         return methodIndex;
/*     */       }
/*     */ 
/*     */       private void addCodeItemIdentity(int codeItemOffset, String methodString) {
/* 169 */         if (this.codeItemAnnotator != null)
/* 170 */           this.codeItemAnnotator.setItemIdentity(codeItemOffset, methodString);
/*     */       }
/*     */     };
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.ClassDataItem
 * JD-Core Version:    0.6.0
 */