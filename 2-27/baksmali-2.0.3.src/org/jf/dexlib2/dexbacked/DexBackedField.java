/*     */ package org.jf.dexlib2.dexbacked;
/*     */ 
/*     */ import java.util.Set;
/*     */ import org.jf.dexlib2.base.reference.BaseFieldReference;
/*     */ import org.jf.dexlib2.dexbacked.util.AnnotationsDirectory;
/*     */ import org.jf.dexlib2.dexbacked.util.AnnotationsDirectory.AnnotationIterator;
/*     */ import org.jf.dexlib2.dexbacked.util.StaticInitialValueIterator;
/*     */ import org.jf.dexlib2.iface.ClassDef;
/*     */ import org.jf.dexlib2.iface.Field;
/*     */ import org.jf.dexlib2.iface.value.EncodedValue;
/*     */ 
/*     */ public class DexBackedField extends BaseFieldReference
/*     */   implements Field
/*     */ {
/*     */   public final DexBackedDexFile dexFile;
/*     */   public final ClassDef classDef;
/*     */   public final int accessFlags;
/*     */   public final EncodedValue initialValue;
/*     */   public final int annotationSetOffset;
/*     */   public final int fieldIndex;
/*     */   private int fieldIdItemOffset;
/*     */ 
/*     */   public DexBackedField(DexReader reader, DexBackedClassDef classDef, int previousFieldIndex, StaticInitialValueIterator staticInitialValueIterator, AnnotationsDirectory.AnnotationIterator annotationIterator)
/*     */   {
/*  63 */     this.dexFile = ((DexBackedDexFile)reader.dexBuf);
/*  64 */     this.classDef = classDef;
/*     */ 
/*  68 */     int fieldIndexDiff = reader.readLargeUleb128();
/*  69 */     this.fieldIndex = (fieldIndexDiff + previousFieldIndex);
/*  70 */     this.accessFlags = reader.readSmallUleb128();
/*     */ 
/*  72 */     this.annotationSetOffset = annotationIterator.seekTo(this.fieldIndex);
/*  73 */     this.initialValue = staticInitialValueIterator.getNextOrNull();
/*     */   }
/*     */ 
/*     */   public DexBackedField(DexReader reader, DexBackedClassDef classDef, int previousFieldIndex, AnnotationsDirectory.AnnotationIterator annotationIterator)
/*     */   {
/*  80 */     this.dexFile = ((DexBackedDexFile)reader.dexBuf);
/*  81 */     this.classDef = classDef;
/*     */ 
/*  85 */     int fieldIndexDiff = reader.readLargeUleb128();
/*  86 */     this.fieldIndex = (fieldIndexDiff + previousFieldIndex);
/*  87 */     this.accessFlags = reader.readSmallUleb128();
/*     */ 
/*  89 */     this.annotationSetOffset = annotationIterator.seekTo(this.fieldIndex);
/*  90 */     this.initialValue = null;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  96 */     return this.dexFile.getString(this.dexFile.readSmallUint(getFieldIdItemOffset() + 4));
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 102 */     return this.dexFile.getType(this.dexFile.readUshort(getFieldIdItemOffset() + 2));
/*     */   }
/*     */   public String getDefiningClass() {
/* 105 */     return this.classDef.getType(); } 
/* 106 */   public int getAccessFlags() { return this.accessFlags; } 
/* 107 */   public EncodedValue getInitialValue() { return this.initialValue;
/*     */   }
/*     */ 
/*     */   public Set<? extends DexBackedAnnotation> getAnnotations()
/*     */   {
/* 112 */     return AnnotationsDirectory.getAnnotations(this.dexFile, this.annotationSetOffset);
/*     */   }
/*     */ 
/*     */   public static void skipFields(DexReader reader, int count)
/*     */   {
/* 122 */     for (int i = 0; i < count; i++) {
/* 123 */       reader.skipUleb128();
/* 124 */       reader.skipUleb128();
/*     */     }
/*     */   }
/*     */ 
/*     */   private int getFieldIdItemOffset() {
/* 129 */     if (this.fieldIdItemOffset == 0) {
/* 130 */       this.fieldIdItemOffset = this.dexFile.getFieldIdItemOffset(this.fieldIndex);
/*     */     }
/* 132 */     return this.fieldIdItemOffset;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.DexBackedField
 * JD-Core Version:    0.6.0
 */