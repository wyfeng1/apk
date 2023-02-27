/*     */ package org.jf.dexlib2.writer.builder;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import org.jf.dexlib2.base.reference.BaseMethodReference;
/*     */ import org.jf.dexlib2.iface.reference.MethodReference;
/*     */ import org.jf.dexlib2.writer.MethodSection;
/*     */ 
/*     */ class BuilderMethodPool
/*     */   implements MethodSection<BuilderStringReference, BuilderTypeReference, BuilderProtoReference, BuilderMethodReference, BuilderMethod>
/*     */ {
/*     */   private final BuilderContext context;
/*  48 */   private final ConcurrentMap<MethodReference, BuilderMethodReference> internedItems = Maps.newConcurrentMap();
/*     */ 
/*     */   BuilderMethodPool(BuilderContext context)
/*     */   {
/*  52 */     this.context = context;
/*     */   }
/*     */ 
/*     */   public BuilderMethodReference internMethod(MethodReference methodReference) {
/*  56 */     BuilderMethodReference ret = (BuilderMethodReference)this.internedItems.get(methodReference);
/*  57 */     if (ret != null) {
/*  58 */       return ret;
/*     */     }
/*     */ 
/*  61 */     BuilderMethodReference dexPoolMethodReference = new BuilderMethodReference(this.context.typePool.internType(methodReference.getDefiningClass()), this.context.stringPool.internString(methodReference.getName()), this.context.protoPool.internProto(methodReference));
/*     */ 
/*  65 */     ret = (BuilderMethodReference)this.internedItems.putIfAbsent(dexPoolMethodReference, dexPoolMethodReference);
/*  66 */     return ret == null ? dexPoolMethodReference : ret;
/*     */   }
/*     */ 
/*     */   public BuilderMethodReference internMethod(String definingClass, String name, List<? extends CharSequence> parameters, String returnType)
/*     */   {
/*  72 */     return internMethod(new MethodKey(definingClass, name, parameters, returnType));
/*     */   }
/*     */ 
/*     */   public BuilderTypeReference getDefiningClass(BuilderMethodReference key)
/*     */   {
/*  77 */     return key.definingClass;
/*     */   }
/*     */ 
/*     */   public BuilderProtoReference getPrototype(BuilderMethodReference key)
/*     */   {
/*  82 */     return key.proto;
/*     */   }
/*     */ 
/*     */   public BuilderProtoReference getPrototype(BuilderMethod builderMethod) {
/*  86 */     return builderMethod.methodReference.proto;
/*     */   }
/*     */ 
/*     */   public BuilderStringReference getName(BuilderMethodReference key) {
/*  90 */     return key.name;
/*     */   }
/*     */ 
/*     */   public int getMethodIndex(BuilderMethod builderMethod) {
/*  94 */     return builderMethod.methodReference.index;
/*     */   }
/*     */ 
/*     */   public int getItemIndex(BuilderMethodReference key) {
/*  98 */     return key.index;
/*     */   }
/*     */ 
/*     */   public Collection<? extends Map.Entry<? extends BuilderMethodReference, Integer>> getItems() {
/* 102 */     return new BuilderMapEntryCollection(this.internedItems.values()) {
/*     */       protected int getValue(BuilderMethodReference key) {
/* 104 */         return key.index;
/*     */       }
/*     */ 
/*     */       protected int setValue(BuilderMethodReference key, int value) {
/* 108 */         int prev = key.index;
/* 109 */         key.index = value;
/* 110 */         return prev;
/*     */       } } ;
/*     */   }
/*     */   private static class MethodKey extends BaseMethodReference implements MethodReference {
/*     */     private final String definingClass;
/*     */     private final String name;
/*     */     private final List<? extends CharSequence> parameterTypes;
/*     */     private final String returnType;
/*     */ 
/* 123 */     public MethodKey(String definingClass, String name, List<? extends CharSequence> parameterTypes, String returnType) { this.definingClass = definingClass;
/* 124 */       this.name = name;
/* 125 */       this.parameterTypes = parameterTypes;
/* 126 */       this.returnType = returnType; }
/*     */ 
/*     */     public String getDefiningClass()
/*     */     {
/* 130 */       return this.definingClass;
/*     */     }
/*     */ 
/*     */     public String getName() {
/* 134 */       return this.name;
/*     */     }
/*     */ 
/*     */     public List<? extends CharSequence> getParameterTypes() {
/* 138 */       return this.parameterTypes;
/*     */     }
/*     */ 
/*     */     public String getReturnType() {
/* 142 */       return this.returnType;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderMethodPool
 * JD-Core Version:    0.6.0
 */