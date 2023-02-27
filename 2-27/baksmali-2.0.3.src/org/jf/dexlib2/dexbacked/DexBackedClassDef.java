/*     */ package org.jf.dexlib2.dexbacked;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Iterables;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import org.jf.dexlib2.base.reference.BaseTypeReference;
/*     */ import org.jf.dexlib2.dexbacked.util.AnnotationsDirectory;
/*     */ import org.jf.dexlib2.dexbacked.util.AnnotationsDirectory.AnnotationIterator;
/*     */ import org.jf.dexlib2.dexbacked.util.FixedSizeSet;
/*     */ import org.jf.dexlib2.dexbacked.util.StaticInitialValueIterator;
/*     */ import org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator;
/*     */ import org.jf.dexlib2.iface.ClassDef;
/*     */ import org.jf.dexlib2.iface.reference.FieldReference;
/*     */ import org.jf.dexlib2.iface.reference.MethodReference;
/*     */ import org.jf.dexlib2.immutable.reference.ImmutableFieldReference;
/*     */ import org.jf.dexlib2.immutable.reference.ImmutableMethodReference;
/*     */ 
/*     */ public class DexBackedClassDef extends BaseTypeReference
/*     */   implements ClassDef
/*     */ {
/*     */   public final DexBackedDexFile dexFile;
/*     */   private final int classDefOffset;
/*     */   private final int staticFieldsOffset;
/*  58 */   private int instanceFieldsOffset = 0;
/*  59 */   private int directMethodsOffset = 0;
/*  60 */   private int virtualMethodsOffset = 0;
/*     */   private final int staticFieldCount;
/*     */   private final int instanceFieldCount;
/*     */   private final int directMethodCount;
/*     */   private final int virtualMethodCount;
/*     */   private AnnotationsDirectory annotationsDirectory;
/*     */ 
/*     */   public DexBackedClassDef(DexBackedDexFile dexFile, int classDefOffset)
/*     */   {
/*  71 */     this.dexFile = dexFile;
/*  72 */     this.classDefOffset = classDefOffset;
/*     */ 
/*  74 */     int classDataOffset = dexFile.readSmallUint(classDefOffset + 24);
/*  75 */     if (classDataOffset == 0) {
/*  76 */       this.staticFieldsOffset = -1;
/*  77 */       this.staticFieldCount = 0;
/*  78 */       this.instanceFieldCount = 0;
/*  79 */       this.directMethodCount = 0;
/*  80 */       this.virtualMethodCount = 0;
/*     */     } else {
/*  82 */       DexReader reader = dexFile.readerAt(classDataOffset);
/*  83 */       this.staticFieldCount = reader.readSmallUleb128();
/*  84 */       this.instanceFieldCount = reader.readSmallUleb128();
/*  85 */       this.directMethodCount = reader.readSmallUleb128();
/*  86 */       this.virtualMethodCount = reader.readSmallUleb128();
/*  87 */       this.staticFieldsOffset = reader.getOffset();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  95 */     return this.dexFile.getType(this.dexFile.readSmallUint(this.classDefOffset + 0));
/*     */   }
/*     */ 
/*     */   public String getSuperclass()
/*     */   {
/* 101 */     return this.dexFile.getOptionalType(this.dexFile.readOptionalUint(this.classDefOffset + 8));
/*     */   }
/*     */ 
/*     */   public int getAccessFlags()
/*     */   {
/* 106 */     return this.dexFile.readSmallUint(this.classDefOffset + 4);
/*     */   }
/*     */ 
/*     */   public String getSourceFile()
/*     */   {
/* 112 */     return this.dexFile.getOptionalString(this.dexFile.readOptionalUint(this.classDefOffset + 16));
/*     */   }
/*     */ 
/*     */   public Set<String> getInterfaces()
/*     */   {
/* 118 */     int interfacesOffset = this.dexFile.readSmallUint(this.classDefOffset + 12);
/* 119 */     if (interfacesOffset > 0) {
/* 120 */       int size = this.dexFile.readSmallUint(interfacesOffset);
/* 121 */       return new FixedSizeSet(interfacesOffset, size)
/*     */       {
/*     */         public String readItem(int index)
/*     */         {
/* 125 */           return DexBackedClassDef.this.dexFile.getType(DexBackedClassDef.this.dexFile.readUshort(this.val$interfacesOffset + 4 + 2 * index));
/*     */         }
/*     */         public int size() {
/* 128 */           return this.val$size;
/*     */         } } ;
/*     */     }
/* 131 */     return ImmutableSet.of();
/*     */   }
/*     */ 
/*     */   public Set<? extends DexBackedAnnotation> getAnnotations()
/*     */   {
/* 137 */     return getAnnotationsDirectory().getClassAnnotations();
/*     */   }
/*     */ 
/*     */   public Iterable<? extends DexBackedField> getStaticFields()
/*     */   {
/* 143 */     return getStaticFields(true);
/*     */   }
/*     */ 
/*     */   public Iterable<? extends DexBackedField> getStaticFields(boolean skipDuplicates)
/*     */   {
/* 148 */     if (this.staticFieldCount > 0) {
/* 149 */       DexReader reader = this.dexFile.readerAt(this.staticFieldsOffset);
/*     */ 
/* 151 */       AnnotationsDirectory annotationsDirectory = getAnnotationsDirectory();
/* 152 */       int staticInitialValuesOffset = this.dexFile.readSmallUint(this.classDefOffset + 28);
/*     */ 
/* 154 */       int fieldsStartOffset = reader.getOffset();
/*     */ 
/* 156 */       return new Iterable(annotationsDirectory, staticInitialValuesOffset, fieldsStartOffset, skipDuplicates)
/*     */       {
/*     */         public Iterator<DexBackedField> iterator()
/*     */         {
/* 160 */           AnnotationsDirectory.AnnotationIterator annotationIterator = this.val$annotationsDirectory.getFieldAnnotationIterator();
/*     */ 
/* 162 */           StaticInitialValueIterator staticInitialValueIterator = StaticInitialValueIterator.newOrEmpty(DexBackedClassDef.this.dexFile, this.val$staticInitialValuesOffset);
/*     */ 
/* 165 */           return new VariableSizeLookaheadIterator(DexBackedClassDef.this.dexFile, this.val$fieldsStartOffset, staticInitialValueIterator, annotationIterator) { private int count;
/*     */             private FieldReference previousField;
/*     */             private int previousIndex;
/*     */ 
/*     */             protected DexBackedField readNextItem(DexReader reader) { DexBackedField item;
/*     */               FieldReference currentField;
/*     */               FieldReference nextField;
/*     */               do { if (++this.count > DexBackedClassDef.this.staticFieldCount) {
/* 175 */                   DexBackedClassDef.access$102(DexBackedClassDef.this, reader.getOffset());
/* 176 */                   return null;
/*     */                 }
/*     */ 
/* 179 */                 item = new DexBackedField(reader, DexBackedClassDef.this, this.previousIndex, this.val$staticInitialValueIterator, this.val$annotationIterator);
/*     */ 
/* 181 */                 currentField = this.previousField;
/* 182 */                 nextField = ImmutableFieldReference.of(item);
/*     */ 
/* 184 */                 this.previousField = nextField;
/* 185 */                 this.previousIndex = item.fieldIndex;
/*     */               }
/* 187 */               while ((DexBackedClassDef.2.this.val$skipDuplicates) && (currentField != null) && (currentField.equals(nextField)));
/*     */ 
/* 191 */               return item; }
/*     */           };
/*     */         }
/*     */       };
/*     */     }
/* 198 */     this.instanceFieldsOffset = this.staticFieldsOffset;
/* 199 */     return ImmutableSet.of();
/*     */   }
/*     */ 
/*     */   public Iterable<? extends DexBackedField> getInstanceFields()
/*     */   {
/* 206 */     return getInstanceFields(true);
/*     */   }
/*     */ 
/*     */   public Iterable<? extends DexBackedField> getInstanceFields(boolean skipDuplicates)
/*     */   {
/* 211 */     if (this.instanceFieldCount > 0) {
/* 212 */       DexReader reader = this.dexFile.readerAt(getInstanceFieldsOffset());
/*     */ 
/* 214 */       AnnotationsDirectory annotationsDirectory = getAnnotationsDirectory();
/* 215 */       int fieldsStartOffset = reader.getOffset();
/*     */ 
/* 217 */       return new Iterable(annotationsDirectory, fieldsStartOffset, skipDuplicates)
/*     */       {
/*     */         public Iterator<DexBackedField> iterator()
/*     */         {
/* 221 */           AnnotationsDirectory.AnnotationIterator annotationIterator = this.val$annotationsDirectory.getFieldAnnotationIterator();
/*     */ 
/* 224 */           return new VariableSizeLookaheadIterator(DexBackedClassDef.this.dexFile, this.val$fieldsStartOffset, annotationIterator) { private int count;
/*     */             private FieldReference previousField;
/*     */             private int previousIndex;
/*     */ 
/*     */             protected DexBackedField readNextItem(DexReader reader) { DexBackedField item;
/*     */               FieldReference currentField;
/*     */               FieldReference nextField;
/*     */               do { if (++this.count > DexBackedClassDef.this.instanceFieldCount) {
/* 234 */                   DexBackedClassDef.access$302(DexBackedClassDef.this, reader.getOffset());
/* 235 */                   return null;
/*     */                 }
/*     */ 
/* 238 */                 item = new DexBackedField(reader, DexBackedClassDef.this, this.previousIndex, this.val$annotationIterator);
/*     */ 
/* 240 */                 currentField = this.previousField;
/* 241 */                 nextField = ImmutableFieldReference.of(item);
/*     */ 
/* 243 */                 this.previousField = nextField;
/* 244 */                 this.previousIndex = item.fieldIndex;
/*     */               }
/* 246 */               while ((DexBackedClassDef.3.this.val$skipDuplicates) && (currentField != null) && (currentField.equals(nextField)));
/*     */ 
/* 250 */               return item; }
/*     */           };
/*     */         }
/*     */       };
/*     */     }
/* 257 */     if (this.instanceFieldsOffset > 0) {
/* 258 */       this.directMethodsOffset = this.instanceFieldsOffset;
/*     */     }
/* 260 */     return ImmutableSet.of();
/*     */   }
/*     */ 
/*     */   public Iterable<? extends DexBackedMethod> getDirectMethods()
/*     */   {
/* 273 */     return getDirectMethods(true);
/*     */   }
/*     */ 
/*     */   public Iterable<? extends DexBackedMethod> getDirectMethods(boolean skipDuplicates)
/*     */   {
/* 278 */     if (this.directMethodCount > 0) {
/* 279 */       DexReader reader = this.dexFile.readerAt(getDirectMethodsOffset());
/*     */ 
/* 281 */       AnnotationsDirectory annotationsDirectory = getAnnotationsDirectory();
/* 282 */       int methodsStartOffset = reader.getOffset();
/*     */ 
/* 284 */       return new Iterable(annotationsDirectory, methodsStartOffset, skipDuplicates)
/*     */       {
/*     */         public Iterator<DexBackedMethod> iterator()
/*     */         {
/* 288 */           AnnotationsDirectory.AnnotationIterator methodAnnotationIterator = this.val$annotationsDirectory.getMethodAnnotationIterator();
/*     */ 
/* 290 */           AnnotationsDirectory.AnnotationIterator parameterAnnotationIterator = this.val$annotationsDirectory.getParameterAnnotationIterator();
/*     */ 
/* 293 */           return new VariableSizeLookaheadIterator(DexBackedClassDef.this.dexFile, this.val$methodsStartOffset, methodAnnotationIterator, parameterAnnotationIterator) { private int count;
/*     */             private MethodReference previousMethod;
/*     */             private int previousIndex;
/*     */ 
/*     */             protected DexBackedMethod readNextItem(DexReader reader) { DexBackedMethod item;
/*     */               MethodReference currentMethod;
/*     */               MethodReference nextMethod;
/*     */               do { if (++this.count > DexBackedClassDef.this.directMethodCount) {
/* 303 */                   DexBackedClassDef.access$502(DexBackedClassDef.this, reader.getOffset());
/* 304 */                   return null;
/*     */                 }
/*     */ 
/* 307 */                 item = new DexBackedMethod(reader, DexBackedClassDef.this, this.previousIndex, this.val$methodAnnotationIterator, this.val$parameterAnnotationIterator);
/*     */ 
/* 309 */                 currentMethod = this.previousMethod;
/* 310 */                 nextMethod = ImmutableMethodReference.of(item);
/*     */ 
/* 312 */                 this.previousMethod = nextMethod;
/* 313 */                 this.previousIndex = item.methodIndex;
/*     */               }
/* 315 */               while ((DexBackedClassDef.4.this.val$skipDuplicates) && (currentMethod != null) && (currentMethod.equals(nextMethod)));
/*     */ 
/* 319 */               return item; }
/*     */           };
/*     */         }
/*     */       };
/*     */     }
/* 326 */     if (this.directMethodsOffset > 0) {
/* 327 */       this.virtualMethodsOffset = this.directMethodsOffset;
/*     */     }
/* 329 */     return ImmutableSet.of();
/*     */   }
/*     */ 
/*     */   public Iterable<? extends DexBackedMethod> getVirtualMethods(boolean skipDuplicates)
/*     */   {
/* 335 */     if (this.virtualMethodCount > 0) {
/* 336 */       DexReader reader = this.dexFile.readerAt(getVirtualMethodsOffset());
/*     */ 
/* 338 */       AnnotationsDirectory annotationsDirectory = getAnnotationsDirectory();
/* 339 */       int methodsStartOffset = reader.getOffset();
/*     */ 
/* 341 */       return new Iterable(annotationsDirectory, methodsStartOffset, skipDuplicates) {
/* 342 */         final AnnotationsDirectory.AnnotationIterator methodAnnotationIterator = this.val$annotationsDirectory.getMethodAnnotationIterator();
/*     */ 
/* 344 */         final AnnotationsDirectory.AnnotationIterator parameterAnnotationIterator = this.val$annotationsDirectory.getParameterAnnotationIterator();
/*     */ 
/*     */         public Iterator<DexBackedMethod> iterator()
/*     */         {
/* 350 */           return new VariableSizeLookaheadIterator(DexBackedClassDef.this.dexFile, this.val$methodsStartOffset) { private int count;
/*     */             private MethodReference previousMethod;
/*     */             private int previousIndex;
/*     */ 
/*     */             protected DexBackedMethod readNextItem(DexReader reader) { DexBackedMethod item;
/*     */               MethodReference currentMethod;
/*     */               MethodReference nextMethod;
/*     */               do { if (++this.count > DexBackedClassDef.this.virtualMethodCount) {
/* 360 */                   return null;
/*     */                 }
/*     */ 
/* 363 */                 item = new DexBackedMethod(reader, DexBackedClassDef.this, this.previousIndex, DexBackedClassDef.5.this.methodAnnotationIterator, DexBackedClassDef.5.this.parameterAnnotationIterator);
/*     */ 
/* 365 */                 currentMethod = this.previousMethod;
/* 366 */                 nextMethod = ImmutableMethodReference.of(item);
/*     */ 
/* 368 */                 this.previousMethod = nextMethod;
/* 369 */                 this.previousIndex = item.methodIndex;
/*     */               }
/* 371 */               while ((DexBackedClassDef.5.this.val$skipDuplicates) && (currentMethod != null) && (currentMethod.equals(nextMethod)));
/*     */ 
/* 374 */               return item; }
/*     */           };
/*     */         }
/*     */       };
/*     */     }
/* 381 */     return ImmutableSet.of();
/*     */   }
/*     */ 
/*     */   public Iterable<? extends DexBackedMethod> getVirtualMethods()
/*     */   {
/* 388 */     return getVirtualMethods(true);
/*     */   }
/*     */ 
/*     */   public Iterable<? extends DexBackedMethod> getMethods()
/*     */   {
/* 394 */     return Iterables.concat(getDirectMethods(), getVirtualMethods());
/*     */   }
/*     */ 
/*     */   private AnnotationsDirectory getAnnotationsDirectory() {
/* 398 */     if (this.annotationsDirectory == null) {
/* 399 */       int annotationsDirectoryOffset = this.dexFile.readSmallUint(this.classDefOffset + 20);
/* 400 */       this.annotationsDirectory = AnnotationsDirectory.newOrEmpty(this.dexFile, annotationsDirectoryOffset);
/*     */     }
/* 402 */     return this.annotationsDirectory;
/*     */   }
/*     */ 
/*     */   private int getInstanceFieldsOffset() {
/* 406 */     if (this.instanceFieldsOffset > 0) {
/* 407 */       return this.instanceFieldsOffset;
/*     */     }
/* 409 */     DexReader reader = new DexReader(this.dexFile, this.staticFieldsOffset);
/* 410 */     DexBackedField.skipFields(reader, this.staticFieldCount);
/* 411 */     this.instanceFieldsOffset = reader.getOffset();
/* 412 */     return this.instanceFieldsOffset;
/*     */   }
/*     */ 
/*     */   private int getDirectMethodsOffset() {
/* 416 */     if (this.directMethodsOffset > 0) {
/* 417 */       return this.directMethodsOffset;
/*     */     }
/* 419 */     DexReader reader = this.dexFile.readerAt(getInstanceFieldsOffset());
/* 420 */     DexBackedField.skipFields(reader, this.instanceFieldCount);
/* 421 */     this.directMethodsOffset = reader.getOffset();
/* 422 */     return this.directMethodsOffset;
/*     */   }
/*     */ 
/*     */   private int getVirtualMethodsOffset() {
/* 426 */     if (this.virtualMethodsOffset > 0) {
/* 427 */       return this.virtualMethodsOffset;
/*     */     }
/* 429 */     DexReader reader = this.dexFile.readerAt(getDirectMethodsOffset());
/* 430 */     DexBackedMethod.skipMethods(reader, this.directMethodCount);
/* 431 */     this.virtualMethodsOffset = reader.getOffset();
/* 432 */     return this.virtualMethodsOffset;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.DexBackedClassDef
 * JD-Core Version:    0.6.0
 */