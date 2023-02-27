/*     */ package org.jf.dexlib2.dexbacked;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Iterators;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.jf.dexlib2.base.reference.BaseMethodReference;
/*     */ import org.jf.dexlib2.dexbacked.util.AnnotationsDirectory;
/*     */ import org.jf.dexlib2.dexbacked.util.AnnotationsDirectory.AnnotationIterator;
/*     */ import org.jf.dexlib2.dexbacked.util.FixedSizeList;
/*     */ import org.jf.dexlib2.dexbacked.util.ParameterIterator;
/*     */ import org.jf.dexlib2.iface.Annotation;
/*     */ import org.jf.dexlib2.iface.Method;
/*     */ import org.jf.dexlib2.iface.MethodParameter;
/*     */ import org.jf.util.AbstractForwardSequentialList;
/*     */ 
/*     */ public class DexBackedMethod extends BaseMethodReference
/*     */   implements Method
/*     */ {
/*     */   public final DexBackedDexFile dexFile;
/*     */   public final DexBackedClassDef classDef;
/*     */   public final int accessFlags;
/*     */   private final int codeOffset;
/*     */   private final int parameterAnnotationSetListOffset;
/*     */   private final int methodAnnotationSetOffset;
/*     */   public final int methodIndex;
/*     */   private int methodIdItemOffset;
/*     */   private int protoIdItemOffset;
/*  68 */   private int parametersOffset = -1;
/*     */ 
/*     */   public DexBackedMethod(DexReader reader, DexBackedClassDef classDef, int previousMethodIndex, AnnotationsDirectory.AnnotationIterator methodAnnotationIterator, AnnotationsDirectory.AnnotationIterator paramaterAnnotationIterator)
/*     */   {
/*  92 */     this.dexFile = ((DexBackedDexFile)reader.dexBuf);
/*  93 */     this.classDef = classDef;
/*     */ 
/*  97 */     int methodIndexDiff = reader.readLargeUleb128();
/*  98 */     this.methodIndex = (methodIndexDiff + previousMethodIndex);
/*  99 */     this.accessFlags = reader.readSmallUleb128();
/* 100 */     this.codeOffset = reader.readSmallUleb128();
/*     */ 
/* 102 */     this.methodAnnotationSetOffset = methodAnnotationIterator.seekTo(this.methodIndex);
/* 103 */     this.parameterAnnotationSetListOffset = paramaterAnnotationIterator.seekTo(this.methodIndex);
/*     */   }
/*     */ 
/*     */   public String getDefiningClass() {
/* 107 */     return this.classDef.getType(); } 
/* 108 */   public int getAccessFlags() { return this.accessFlags;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/* 113 */     return this.dexFile.getString(this.dexFile.readSmallUint(getMethodIdItemOffset() + 4));
/*     */   }
/*     */ 
/*     */   public String getReturnType()
/*     */   {
/* 119 */     return this.dexFile.getType(this.dexFile.readSmallUint(getProtoIdItemOffset() + 4));
/*     */   }
/*     */ 
/*     */   public List<? extends MethodParameter> getParameters()
/*     */   {
/* 125 */     int parametersOffset = getParametersOffset();
/* 126 */     if (parametersOffset > 0) {
/* 127 */       List parameterTypes = getParameterTypes();
/*     */ 
/* 129 */       return new AbstractForwardSequentialList(parameterTypes) {
/*     */         public Iterator<MethodParameter> iterator() {
/* 131 */           return new ParameterIterator(this.val$parameterTypes, DexBackedMethod.this.getParameterAnnotations(), DexBackedMethod.this.getParameterNames());
/*     */         }
/*     */ 
/*     */         public int size()
/*     */         {
/* 137 */           return this.val$parameterTypes.size();
/*     */         } } ;
/*     */     }
/* 141 */     return ImmutableList.of();
/*     */   }
/*     */ 
/*     */   public List<? extends Set<? extends DexBackedAnnotation>> getParameterAnnotations()
/*     */   {
/* 146 */     return AnnotationsDirectory.getParameterAnnotations(this.dexFile, this.parameterAnnotationSetListOffset);
/*     */   }
/*     */ 
/*     */   public Iterator<String> getParameterNames()
/*     */   {
/* 151 */     DexBackedMethodImplementation methodImpl = getImplementation();
/* 152 */     if (methodImpl != null) {
/* 153 */       return methodImpl.getParameterNames(null);
/*     */     }
/* 155 */     return Iterators.emptyIterator();
/*     */   }
/*     */ 
/*     */   public List<String> getParameterTypes()
/*     */   {
/* 161 */     int parametersOffset = getParametersOffset();
/* 162 */     if (parametersOffset > 0) {
/* 163 */       int parameterCount = this.dexFile.readSmallUint(parametersOffset + 0);
/* 164 */       int paramListStart = parametersOffset + 4;
/* 165 */       return new FixedSizeList(paramListStart, parameterCount)
/*     */       {
/*     */         public String readItem(int index)
/*     */         {
/* 169 */           return DexBackedMethod.this.dexFile.getType(DexBackedMethod.this.dexFile.readUshort(this.val$paramListStart + 2 * index));
/*     */         }
/* 171 */         public int size() { return this.val$parameterCount; } } ;
/*     */     }
/* 174 */     return ImmutableList.of();
/*     */   }
/*     */ 
/*     */   public Set<? extends Annotation> getAnnotations()
/*     */   {
/* 180 */     return AnnotationsDirectory.getAnnotations(this.dexFile, this.methodAnnotationSetOffset);
/*     */   }
/*     */ 
/*     */   public DexBackedMethodImplementation getImplementation()
/*     */   {
/* 186 */     if (this.codeOffset > 0) {
/* 187 */       return new DexBackedMethodImplementation(this.dexFile, this, this.codeOffset);
/*     */     }
/* 189 */     return null;
/*     */   }
/*     */ 
/*     */   private int getMethodIdItemOffset() {
/* 193 */     if (this.methodIdItemOffset == 0) {
/* 194 */       this.methodIdItemOffset = this.dexFile.getMethodIdItemOffset(this.methodIndex);
/*     */     }
/* 196 */     return this.methodIdItemOffset;
/*     */   }
/*     */ 
/*     */   private int getProtoIdItemOffset() {
/* 200 */     if (this.protoIdItemOffset == 0) {
/* 201 */       int protoIndex = this.dexFile.readUshort(getMethodIdItemOffset() + 2);
/* 202 */       this.protoIdItemOffset = this.dexFile.getProtoIdItemOffset(protoIndex);
/*     */     }
/* 204 */     return this.protoIdItemOffset;
/*     */   }
/*     */ 
/*     */   private int getParametersOffset() {
/* 208 */     if (this.parametersOffset == -1) {
/* 209 */       this.parametersOffset = this.dexFile.readSmallUint(getProtoIdItemOffset() + 8);
/*     */     }
/* 211 */     return this.parametersOffset;
/*     */   }
/*     */ 
/*     */   public static void skipMethods(DexReader reader, int count)
/*     */   {
/* 221 */     for (int i = 0; i < count; i++) {
/* 222 */       reader.skipUleb128();
/* 223 */       reader.skipUleb128();
/* 224 */       reader.skipUleb128();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.DexBackedMethod
 * JD-Core Version:    0.6.0
 */