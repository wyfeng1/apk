/*     */ package org.jf.dexlib2.dexbacked.raw;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import org.jf.dexlib2.AccessFlags;
/*     */ import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
/*     */ import org.jf.dexlib2.util.AnnotatedBytes;
/*     */ 
/*     */ public class ClassDefItem
/*     */ {
/*     */   public static SectionAnnotator makeAnnotator(DexAnnotator annotator, MapItem mapItem)
/*     */   {
/*  57 */     return new SectionAnnotator(annotator, mapItem) {
/*  58 */       private SectionAnnotator classDataAnnotator = null;
/*     */ 
/*     */       public void annotateSection(AnnotatedBytes out) {
/*  61 */         this.classDataAnnotator = this.annotator.getAnnotator(8192);
/*  62 */         super.annotateSection(out);
/*     */       }
/*     */ 
/*     */       public String getItemName() {
/*  66 */         return "class_def_item";
/*     */       }
/*     */ 
/*     */       protected void annotateItem(AnnotatedBytes out, int itemIndex, String itemIdentity)
/*     */       {
/*  71 */         int classIndex = this.dexFile.readSmallUint(out.getCursor());
/*  72 */         out.annotate(4, "class_idx = %s", new Object[] { TypeIdItem.getReferenceAnnotation(this.dexFile, classIndex) });
/*     */ 
/*  74 */         int accessFlags = this.dexFile.readInt(out.getCursor());
/*  75 */         out.annotate(4, "access_flags = 0x%x: %s", new Object[] { Integer.valueOf(accessFlags), Joiner.on('|').join(AccessFlags.getAccessFlagsForClass(accessFlags)) });
/*     */ 
/*  78 */         int superclassIndex = this.dexFile.readOptionalUint(out.getCursor());
/*  79 */         out.annotate(4, "superclass_idx = %s", new Object[] { TypeIdItem.getOptionalReferenceAnnotation(this.dexFile, superclassIndex) });
/*     */ 
/*  82 */         int interfacesOffset = this.dexFile.readSmallUint(out.getCursor());
/*  83 */         out.annotate(4, "interfaces_off = %s", new Object[] { TypeListItem.getReferenceAnnotation(this.dexFile, interfacesOffset) });
/*     */ 
/*  85 */         int sourceFileIdx = this.dexFile.readOptionalUint(out.getCursor());
/*  86 */         out.annotate(4, "source_file_idx = %s", new Object[] { StringIdItem.getOptionalReferenceAnnotation(this.dexFile, sourceFileIdx) });
/*     */ 
/*  89 */         int annotationsOffset = this.dexFile.readSmallUint(out.getCursor());
/*  90 */         if (annotationsOffset == 0)
/*  91 */           out.annotate(4, "annotations_off = annotations_directory_item[NO_OFFSET]", new Object[0]);
/*     */         else {
/*  93 */           out.annotate(4, "annotations_off = annotations_directory_item[0x%x]", new Object[] { Integer.valueOf(annotationsOffset) });
/*     */         }
/*     */ 
/*  96 */         int classDataOffset = this.dexFile.readSmallUint(out.getCursor());
/*  97 */         if (classDataOffset == 0) {
/*  98 */           out.annotate(4, "class_data_off = class_data_item[NO_OFFSET]", new Object[0]);
/*     */         } else {
/* 100 */           out.annotate(4, "class_data_off = class_data_item[0x%x]", new Object[] { Integer.valueOf(classDataOffset) });
/* 101 */           addClassDataIdentity(classDataOffset, this.dexFile.getType(classIndex));
/*     */         }
/*     */ 
/* 104 */         int staticValuesOffset = this.dexFile.readSmallUint(out.getCursor());
/* 105 */         if (staticValuesOffset == 0)
/* 106 */           out.annotate(4, "static_values_off = encoded_array_item[NO_OFFSET]", new Object[0]);
/*     */         else
/* 108 */           out.annotate(4, "static_values_off = encoded_array_item[0x%x]", new Object[] { Integer.valueOf(staticValuesOffset) });
/*     */       }
/*     */ 
/*     */       private void addClassDataIdentity(int classDataOffset, String classType)
/*     */       {
/* 113 */         if (this.classDataAnnotator != null)
/* 114 */           this.classDataAnnotator.setItemIdentity(classDataOffset, classType);
/*     */       }
/*     */     };
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.ClassDefItem
 * JD-Core Version:    0.6.0
 */