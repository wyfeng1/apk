/*     */ package org.jf.dexlib2.writer.builder;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import org.jf.dexlib2.iface.Annotation;
/*     */ import org.jf.dexlib2.writer.AnnotationSection;
/*     */ 
/*     */ class BuilderAnnotationPool
/*     */   implements AnnotationSection<BuilderStringReference, BuilderTypeReference, BuilderAnnotation, BuilderAnnotationElement, BuilderEncodedValues.BuilderEncodedValue>
/*     */ {
/*     */   private final BuilderContext context;
/*  47 */   private final ConcurrentMap<Annotation, BuilderAnnotation> internedItems = Maps.newConcurrentMap();
/*     */ 
/*     */   BuilderAnnotationPool(BuilderContext context)
/*     */   {
/*  51 */     this.context = context;
/*     */   }
/*     */ 
/*     */   public BuilderAnnotation internAnnotation(Annotation annotation) {
/*  55 */     BuilderAnnotation ret = (BuilderAnnotation)this.internedItems.get(annotation);
/*  56 */     if (ret != null) {
/*  57 */       return ret;
/*     */     }
/*     */ 
/*  60 */     BuilderAnnotation dexBuilderAnnotation = new BuilderAnnotation(annotation.getVisibility(), this.context.typePool.internType(annotation.getType()), this.context.internAnnotationElements(annotation.getElements()));
/*     */ 
/*  64 */     ret = (BuilderAnnotation)this.internedItems.putIfAbsent(dexBuilderAnnotation, dexBuilderAnnotation);
/*  65 */     return ret == null ? dexBuilderAnnotation : ret;
/*     */   }
/*     */ 
/*     */   public int getVisibility(BuilderAnnotation key) {
/*  69 */     return key.visibility;
/*     */   }
/*     */ 
/*     */   public BuilderTypeReference getType(BuilderAnnotation key) {
/*  73 */     return key.type;
/*     */   }
/*     */ 
/*     */   public Collection<? extends BuilderAnnotationElement> getElements(BuilderAnnotation key)
/*     */   {
/*  78 */     return key.elements;
/*     */   }
/*     */ 
/*     */   public BuilderStringReference getElementName(BuilderAnnotationElement element)
/*     */   {
/*  83 */     return element.name;
/*     */   }
/*     */ 
/*     */   public BuilderEncodedValues.BuilderEncodedValue getElementValue(BuilderAnnotationElement element)
/*     */   {
/*  88 */     return element.value;
/*     */   }
/*     */ 
/*     */   public int getItemOffset(BuilderAnnotation key) {
/*  92 */     return key.offset;
/*     */   }
/*     */ 
/*     */   public Collection<? extends Map.Entry<? extends BuilderAnnotation, Integer>> getItems() {
/*  96 */     return new BuilderMapEntryCollection(this.internedItems.values()) {
/*     */       protected int getValue(BuilderAnnotation key) {
/*  98 */         return key.offset;
/*     */       }
/*     */ 
/*     */       protected int setValue(BuilderAnnotation key, int value) {
/* 102 */         int prev = key.offset;
/* 103 */         key.offset = value;
/* 104 */         return prev;
/*     */       }
/*     */     };
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderAnnotationPool
 * JD-Core Version:    0.6.0
 */