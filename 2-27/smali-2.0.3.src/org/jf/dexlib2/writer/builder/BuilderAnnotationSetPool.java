/*     */ package org.jf.dexlib2.writer.builder;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import org.jf.dexlib2.iface.Annotation;
/*     */ import org.jf.dexlib2.writer.AnnotationSetSection;
/*     */ 
/*     */ class BuilderAnnotationSetPool
/*     */   implements AnnotationSetSection<BuilderAnnotation, BuilderAnnotationSet>
/*     */ {
/*     */   private final BuilderContext context;
/*  51 */   private final ConcurrentMap<Set<? extends Annotation>, BuilderAnnotationSet> internedItems = Maps.newConcurrentMap();
/*     */ 
/*     */   BuilderAnnotationSetPool(BuilderContext context)
/*     */   {
/*  55 */     this.context = context;
/*     */   }
/*     */ 
/*     */   public BuilderAnnotationSet internAnnotationSet(Set<? extends Annotation> annotations) {
/*  59 */     if (annotations == null) {
/*  60 */       return BuilderAnnotationSet.EMPTY;
/*     */     }
/*     */ 
/*  63 */     BuilderAnnotationSet ret = (BuilderAnnotationSet)this.internedItems.get(annotations);
/*  64 */     if (ret != null) {
/*  65 */       return ret;
/*     */     }
/*     */ 
/*  68 */     BuilderAnnotationSet annotationSet = new BuilderAnnotationSet(ImmutableSet.copyOf(Iterators.transform(annotations.iterator(), new Function()
/*     */     {
/*     */       public BuilderAnnotation apply(Annotation input)
/*     */       {
/*  72 */         return BuilderAnnotationSetPool.this.context.annotationPool.internAnnotation(input);
/*     */       }
/*     */     })));
/*  76 */     ret = (BuilderAnnotationSet)this.internedItems.putIfAbsent(annotationSet, annotationSet);
/*  77 */     return ret == null ? annotationSet : ret;
/*     */   }
/*     */ 
/*     */   public Collection<? extends BuilderAnnotation> getAnnotations(BuilderAnnotationSet key)
/*     */   {
/*  82 */     return key.annotations;
/*     */   }
/*     */ 
/*     */   public int getNullableItemOffset(BuilderAnnotationSet key) {
/*  86 */     return key == null ? 0 : key.offset;
/*     */   }
/*     */ 
/*     */   public int getItemOffset(BuilderAnnotationSet key) {
/*  90 */     return key.offset;
/*     */   }
/*     */ 
/*     */   public Collection<? extends Map.Entry<? extends BuilderAnnotationSet, Integer>> getItems() {
/*  94 */     return new BuilderMapEntryCollection(this.internedItems.values()) {
/*     */       protected int getValue(BuilderAnnotationSet key) {
/*  96 */         return key.offset;
/*     */       }
/*     */ 
/*     */       protected int setValue(BuilderAnnotationSet key, int value) {
/* 100 */         int prev = key.offset;
/* 101 */         key.offset = value;
/* 102 */         return prev;
/*     */       }
/*     */     };
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderAnnotationSetPool
 * JD-Core Version:    0.6.0
 */