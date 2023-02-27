/*     */ package org.jf.dexlib2.writer.builder;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import org.jf.dexlib2.iface.reference.FieldReference;
/*     */ import org.jf.dexlib2.immutable.reference.ImmutableFieldReference;
/*     */ import org.jf.dexlib2.writer.FieldSection;
/*     */ 
/*     */ public class BuilderFieldPool
/*     */   implements FieldSection<BuilderStringReference, BuilderTypeReference, BuilderFieldReference, BuilderField>
/*     */ {
/*     */   private final BuilderContext context;
/*  47 */   private final ConcurrentMap<FieldReference, BuilderFieldReference> internedItems = Maps.newConcurrentMap();
/*     */ 
/*     */   BuilderFieldPool(BuilderContext context)
/*     */   {
/*  51 */     this.context = context;
/*     */   }
/*     */ 
/*     */   BuilderFieldReference internField(String definingClass, String name, String type) {
/*  55 */     ImmutableFieldReference fieldReference = new ImmutableFieldReference(definingClass, name, type);
/*  56 */     return internField(fieldReference);
/*     */   }
/*     */ 
/*     */   public BuilderFieldReference internField(FieldReference fieldReference) {
/*  60 */     BuilderFieldReference ret = (BuilderFieldReference)this.internedItems.get(fieldReference);
/*  61 */     if (ret != null) {
/*  62 */       return ret;
/*     */     }
/*     */ 
/*  65 */     BuilderFieldReference dexPoolFieldReference = new BuilderFieldReference(this.context.typePool.internType(fieldReference.getDefiningClass()), this.context.stringPool.internString(fieldReference.getName()), this.context.typePool.internType(fieldReference.getType()));
/*     */ 
/*  69 */     ret = (BuilderFieldReference)this.internedItems.putIfAbsent(dexPoolFieldReference, dexPoolFieldReference);
/*  70 */     return ret == null ? dexPoolFieldReference : ret;
/*     */   }
/*     */ 
/*     */   public BuilderTypeReference getDefiningClass(BuilderFieldReference key)
/*     */   {
/*  75 */     return key.definingClass;
/*     */   }
/*     */ 
/*     */   public BuilderTypeReference getFieldType(BuilderFieldReference key) {
/*  79 */     return key.fieldType;
/*     */   }
/*     */ 
/*     */   public BuilderStringReference getName(BuilderFieldReference key) {
/*  83 */     return key.name;
/*     */   }
/*     */ 
/*     */   public int getFieldIndex(BuilderField builderField) {
/*  87 */     return builderField.fieldReference.getIndex();
/*     */   }
/*     */ 
/*     */   public int getItemIndex(BuilderFieldReference key) {
/*  91 */     return key.index;
/*     */   }
/*     */ 
/*     */   public Collection<? extends Map.Entry<? extends BuilderFieldReference, Integer>> getItems() {
/*  95 */     return new BuilderMapEntryCollection(this.internedItems.values()) {
/*     */       protected int getValue(BuilderFieldReference key) {
/*  97 */         return key.index;
/*     */       }
/*     */ 
/*     */       protected int setValue(BuilderFieldReference key, int value) {
/* 101 */         int prev = key.index;
/* 102 */         key.index = value;
/* 103 */         return prev;
/*     */       }
/*     */     };
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderFieldPool
 * JD-Core Version:    0.6.0
 */