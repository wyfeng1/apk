/*     */ package org.jf.dexlib2.writer.builder;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableSortedSet;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Ordering;
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.SortedSet;
/*     */ import org.jf.dexlib2.base.reference.BaseTypeReference;
/*     */ import org.jf.dexlib2.iface.ClassDef;
/*     */ import org.jf.dexlib2.util.FieldUtil;
/*     */ import org.jf.dexlib2.util.MethodUtil;
/*     */ 
/*     */ public class BuilderClassDef extends BaseTypeReference
/*     */   implements ClassDef
/*     */ {
/*     */   final BuilderTypeReference type;
/*     */   final int accessFlags;
/*     */   final BuilderTypeReference superclass;
/*     */   final BuilderTypeList interfaces;
/*     */   final BuilderStringReference sourceFile;
/*     */   final BuilderAnnotationSet annotations;
/*     */   final SortedSet<BuilderField> staticFields;
/*     */   final SortedSet<BuilderField> instanceFields;
/*     */   final SortedSet<BuilderMethod> directMethods;
/*     */   final SortedSet<BuilderMethod> virtualMethods;
/*  58 */   int classDefIndex = -1;
/*  59 */   int encodedArrayOffset = 0;
/*  60 */   int annotationDirectoryOffset = 0;
/*     */ 
/*     */   BuilderClassDef(BuilderTypeReference type, int accessFlags, BuilderTypeReference superclass, BuilderTypeList interfaces, BuilderStringReference sourceFile, BuilderAnnotationSet annotations, Iterable<? extends BuilderField> fields, Iterable<? extends BuilderMethod> methods)
/*     */   {
/*  70 */     if (fields == null) {
/*  71 */       fields = ImmutableList.of();
/*     */     }
/*  73 */     if (methods == null) {
/*  74 */       methods = ImmutableList.of();
/*     */     }
/*     */ 
/*  77 */     this.type = type;
/*  78 */     this.accessFlags = accessFlags;
/*  79 */     this.superclass = superclass;
/*  80 */     this.interfaces = interfaces;
/*  81 */     this.sourceFile = sourceFile;
/*  82 */     this.annotations = annotations;
/*  83 */     this.staticFields = ImmutableSortedSet.copyOf(Iterables.filter(fields, FieldUtil.FIELD_IS_STATIC));
/*  84 */     this.instanceFields = ImmutableSortedSet.copyOf(Iterables.filter(fields, FieldUtil.FIELD_IS_INSTANCE));
/*  85 */     this.directMethods = ImmutableSortedSet.copyOf(Iterables.filter(methods, MethodUtil.METHOD_IS_DIRECT));
/*  86 */     this.virtualMethods = ImmutableSortedSet.copyOf(Iterables.filter(methods, MethodUtil.METHOD_IS_VIRTUAL));
/*     */   }
/*     */   public String getType() {
/*  89 */     return this.type.getType();
/*     */   }
/*     */ 
/*     */   public SortedSet<BuilderField> getStaticFields()
/*     */   {
/*  94 */     return this.staticFields; } 
/*  95 */   public SortedSet<BuilderField> getInstanceFields() { return this.instanceFields; } 
/*  96 */   public SortedSet<BuilderMethod> getDirectMethods() { return this.directMethods; } 
/*  97 */   public SortedSet<BuilderMethod> getVirtualMethods() { return this.virtualMethods;
/*     */   }
/*     */ 
/*     */   public Collection<BuilderField> getFields()
/*     */   {
/* 113 */     return new AbstractCollection() {
/*     */       public Iterator<BuilderField> iterator() {
/* 115 */         return Iterators.mergeSorted(ImmutableList.of(BuilderClassDef.this.staticFields.iterator(), BuilderClassDef.this.instanceFields.iterator()), Ordering.natural());
/*     */       }
/*     */ 
/*     */       public int size()
/*     */       {
/* 121 */         return BuilderClassDef.this.staticFields.size() + BuilderClassDef.this.instanceFields.size();
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   public Collection<BuilderMethod> getMethods() {
/* 127 */     return new AbstractCollection() {
/*     */       public Iterator<BuilderMethod> iterator() {
/* 129 */         return Iterators.mergeSorted(ImmutableList.of(BuilderClassDef.this.directMethods.iterator(), BuilderClassDef.this.virtualMethods.iterator()), Ordering.natural());
/*     */       }
/*     */ 
/*     */       public int size()
/*     */       {
/* 135 */         return BuilderClassDef.this.directMethods.size() + BuilderClassDef.this.virtualMethods.size();
/*     */       }
/*     */     };
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderClassDef
 * JD-Core Version:    0.6.0
 */