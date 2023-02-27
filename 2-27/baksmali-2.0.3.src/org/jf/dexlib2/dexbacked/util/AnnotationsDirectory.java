/*     */ package org.jf.dexlib2.dexbacked.util;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.jf.dexlib2.dexbacked.DexBackedAnnotation;
/*     */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*     */ 
/*     */ public abstract class AnnotationsDirectory
/*     */ {
/*  44 */   public static final AnnotationsDirectory EMPTY = new AnnotationsDirectory() {
/*     */     public Set<? extends DexBackedAnnotation> getClassAnnotations() {
/*  46 */       return ImmutableSet.of(); } 
/*  47 */     public AnnotationsDirectory.AnnotationIterator getFieldAnnotationIterator() { return AnnotationsDirectory.AnnotationIterator.EMPTY; } 
/*  48 */     public AnnotationsDirectory.AnnotationIterator getMethodAnnotationIterator() { return AnnotationsDirectory.AnnotationIterator.EMPTY; } 
/*  49 */     public AnnotationsDirectory.AnnotationIterator getParameterAnnotationIterator() { return AnnotationsDirectory.AnnotationIterator.EMPTY;
/*     */     }
/*  44 */   };
/*     */ 
/*     */   public abstract Set<? extends DexBackedAnnotation> getClassAnnotations();
/*     */ 
/*     */   public abstract AnnotationIterator getFieldAnnotationIterator();
/*     */ 
/*     */   public abstract AnnotationIterator getMethodAnnotationIterator();
/*     */ 
/*     */   public abstract AnnotationIterator getParameterAnnotationIterator();
/*     */ 
/*     */   public static AnnotationsDirectory newOrEmpty(DexBackedDexFile dexFile, int directoryAnnotationsOffset)
/*     */   {
/*  61 */     if (directoryAnnotationsOffset == 0) {
/*  62 */       return EMPTY;
/*     */     }
/*  64 */     return new AnnotationsDirectoryImpl(dexFile, directoryAnnotationsOffset);
/*     */   }
/*     */ 
/*     */   public static Set<? extends DexBackedAnnotation> getAnnotations(DexBackedDexFile dexFile, int annotationSetOffset)
/*     */   {
/* 101 */     if (annotationSetOffset != 0) {
/* 102 */       int size = dexFile.readSmallUint(annotationSetOffset);
/* 103 */       return new FixedSizeSet(dexFile, annotationSetOffset, size)
/*     */       {
/*     */         public DexBackedAnnotation readItem(int index)
/*     */         {
/* 107 */           int annotationOffset = this.val$dexFile.readSmallUint(this.val$annotationSetOffset + 4 + 4 * index);
/* 108 */           return new DexBackedAnnotation(this.val$dexFile, annotationOffset);
/*     */         }
/*     */         public int size() {
/* 111 */           return this.val$size;
/*     */         } } ;
/*     */     }
/* 115 */     return ImmutableSet.of();
/*     */   }
/*     */ 
/*     */   public static List<Set<? extends DexBackedAnnotation>> getParameterAnnotations(DexBackedDexFile dexFile, int annotationSetListOffset)
/*     */   {
/* 121 */     if (annotationSetListOffset > 0) {
/* 122 */       int size = dexFile.readSmallUint(annotationSetListOffset);
/*     */ 
/* 124 */       return new FixedSizeList(dexFile, annotationSetListOffset, size)
/*     */       {
/*     */         public Set<? extends DexBackedAnnotation> readItem(int index)
/*     */         {
/* 128 */           int annotationSetOffset = this.val$dexFile.readSmallUint(this.val$annotationSetListOffset + 4 + index * 4);
/* 129 */           return AnnotationsDirectory.getAnnotations(this.val$dexFile, annotationSetOffset);
/*     */         }
/*     */         public int size() {
/* 132 */           return this.val$size;
/*     */         } } ;
/*     */     }
/* 135 */     return ImmutableList.of();
/*     */   }
/*     */ 
/*     */   private static class AnnotationsDirectoryImpl extends AnnotationsDirectory
/*     */   {
/*     */     public final DexBackedDexFile dexFile;
/*     */     private final int directoryOffset;
/*     */ 
/*     */     public AnnotationsDirectoryImpl(DexBackedDexFile dexFile, int directoryOffset)
/*     */     {
/* 154 */       this.dexFile = dexFile;
/* 155 */       this.directoryOffset = directoryOffset;
/*     */     }
/*     */ 
/*     */     public int getFieldAnnotationCount() {
/* 159 */       return this.dexFile.readSmallUint(this.directoryOffset + 4);
/*     */     }
/*     */ 
/*     */     public int getMethodAnnotationCount() {
/* 163 */       return this.dexFile.readSmallUint(this.directoryOffset + 8);
/*     */     }
/*     */ 
/*     */     public int getParameterAnnotationCount() {
/* 167 */       return this.dexFile.readSmallUint(this.directoryOffset + 12);
/*     */     }
/*     */ 
/*     */     public Set<? extends DexBackedAnnotation> getClassAnnotations()
/*     */     {
/* 172 */       return getAnnotations(this.dexFile, this.dexFile.readSmallUint(this.directoryOffset));
/*     */     }
/*     */ 
/*     */     public AnnotationsDirectory.AnnotationIterator getFieldAnnotationIterator()
/*     */     {
/* 177 */       int fieldAnnotationCount = getFieldAnnotationCount();
/* 178 */       if (fieldAnnotationCount == 0) {
/* 179 */         return AnnotationsDirectory.AnnotationIterator.EMPTY;
/*     */       }
/* 181 */       return new AnnotationIteratorImpl(this.directoryOffset + 16, fieldAnnotationCount);
/*     */     }
/*     */ 
/*     */     public AnnotationsDirectory.AnnotationIterator getMethodAnnotationIterator()
/*     */     {
/* 186 */       int methodCount = getMethodAnnotationCount();
/* 187 */       if (methodCount == 0) {
/* 188 */         return AnnotationsDirectory.AnnotationIterator.EMPTY;
/*     */       }
/* 190 */       int fieldCount = getFieldAnnotationCount();
/* 191 */       int methodAnnotationsOffset = this.directoryOffset + 16 + fieldCount * 8;
/*     */ 
/* 193 */       return new AnnotationIteratorImpl(methodAnnotationsOffset, methodCount);
/*     */     }
/*     */ 
/*     */     public AnnotationsDirectory.AnnotationIterator getParameterAnnotationIterator()
/*     */     {
/* 198 */       int parameterAnnotationCount = getParameterAnnotationCount();
/* 199 */       if (parameterAnnotationCount == 0) {
/* 200 */         return AnnotationsDirectory.AnnotationIterator.EMPTY;
/*     */       }
/* 202 */       int fieldCount = getFieldAnnotationCount();
/* 203 */       int methodCount = getMethodAnnotationCount();
/* 204 */       int parameterAnnotationsOffset = this.directoryOffset + 16 + fieldCount * 8 + methodCount * 8;
/*     */ 
/* 207 */       return new AnnotationIteratorImpl(parameterAnnotationsOffset, parameterAnnotationCount); } 
/*     */     private class AnnotationIteratorImpl implements AnnotationsDirectory.AnnotationIterator { private final int startOffset;
/*     */       private final int size;
/*     */       private int currentIndex;
/*     */       private int currentItemIndex;
/*     */ 
/* 217 */       public AnnotationIteratorImpl(int startOffset, int size) { this.startOffset = startOffset;
/* 218 */         this.size = size;
/* 219 */         this.currentItemIndex = AnnotationsDirectory.AnnotationsDirectoryImpl.this.dexFile.readSmallUint(startOffset);
/* 220 */         this.currentIndex = 0; }
/*     */ 
/*     */       public int seekTo(int itemIndex)
/*     */       {
/* 224 */         while ((this.currentItemIndex < itemIndex) && (this.currentIndex + 1 < this.size)) {
/* 225 */           this.currentIndex += 1;
/* 226 */           this.currentItemIndex = AnnotationsDirectory.AnnotationsDirectoryImpl.this.dexFile.readSmallUint(this.startOffset + this.currentIndex * 8);
/*     */         }
/*     */ 
/* 229 */         if (this.currentItemIndex == itemIndex) {
/* 230 */           return AnnotationsDirectory.AnnotationsDirectoryImpl.this.dexFile.readSmallUint(this.startOffset + this.currentIndex * 8 + 4);
/*     */         }
/* 232 */         return 0;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static abstract interface AnnotationIterator
/*     */   {
/*  75 */     public static final AnnotationIterator EMPTY = new AnnotationIterator() {
/*  76 */       public int seekTo(int key) { return 0;
/*     */       }
/*  75 */     };
/*     */ 
/*     */     public abstract int seekTo(int paramInt);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.util.AnnotationsDirectory
 * JD-Core Version:    0.6.0
 */