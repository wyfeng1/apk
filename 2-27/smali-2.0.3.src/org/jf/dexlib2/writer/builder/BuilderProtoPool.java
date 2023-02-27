/*     */ package org.jf.dexlib2.writer.builder;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import org.jf.dexlib2.iface.reference.MethodReference;
/*     */ import org.jf.dexlib2.util.MethodUtil;
/*     */ import org.jf.dexlib2.writer.ProtoSection;
/*     */ import org.jf.util.CharSequenceUtils;
/*     */ 
/*     */ class BuilderProtoPool
/*     */   implements ProtoSection<BuilderStringReference, BuilderTypeReference, BuilderProtoReference, BuilderTypeList>
/*     */ {
/*     */   private final BuilderContext context;
/*  50 */   private final ConcurrentMap<ProtoKey, BuilderProtoReference> internedItems = Maps.newConcurrentMap();
/*     */ 
/*     */   BuilderProtoPool(BuilderContext context)
/*     */   {
/*  54 */     this.context = context;
/*     */   }
/*     */ 
/*     */   public BuilderProtoReference internProto(List<? extends CharSequence> parameters, String returnType)
/*     */   {
/*  59 */     ProtoKey key = new Key(parameters, returnType);
/*  60 */     BuilderProtoReference ret = (BuilderProtoReference)this.internedItems.get(key);
/*  61 */     if (ret != null) {
/*  62 */       return ret;
/*     */     }
/*     */ 
/*  65 */     BuilderProtoReference protoReference = new BuilderProtoReference(this.context.stringPool.internString(MethodUtil.getShorty(parameters, returnType)), this.context.typeListPool.internTypeList(parameters), this.context.typePool.internType(returnType));
/*     */ 
/*  69 */     ret = (BuilderProtoReference)this.internedItems.putIfAbsent(protoReference, protoReference);
/*  70 */     return ret == null ? protoReference : ret;
/*     */   }
/*     */ 
/*     */   public BuilderProtoReference internProto(MethodReference methodReference) {
/*  74 */     return internProto(methodReference.getParameterTypes(), methodReference.getReturnType());
/*     */   }
/*     */ 
/*     */   public BuilderStringReference getShorty(BuilderProtoReference key) {
/*  78 */     return key.shorty;
/*     */   }
/*     */ 
/*     */   public BuilderTypeReference getReturnType(BuilderProtoReference key) {
/*  82 */     return key.returnType;
/*     */   }
/*     */ 
/*     */   public BuilderTypeList getParameters(BuilderProtoReference key) {
/*  86 */     return key.parameterTypes;
/*     */   }
/*     */ 
/*     */   public int getItemIndex(BuilderProtoReference key) {
/*  90 */     return key.index;
/*     */   }
/*     */ 
/*     */   public Collection<? extends Map.Entry<? extends BuilderProtoReference, Integer>> getItems() {
/*  94 */     return new BuilderMapEntryCollection(this.internedItems.values()) {
/*     */       protected int getValue(BuilderProtoReference key) {
/*  96 */         return key.index;
/*     */       }
/*     */ 
/*     */       protected int setValue(BuilderProtoReference key, int value) {
/* 100 */         int prev = key.index;
/* 101 */         key.index = value;
/* 102 */         return prev;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   private static class Key
/*     */     implements BuilderProtoPool.ProtoKey
/*     */   {
/*     */     private final List<? extends CharSequence> parameters;
/*     */     private final String returnType;
/*     */ 
/*     */     public Key(List<? extends CharSequence> parameters, String returnType)
/*     */     {
/* 119 */       this.parameters = parameters;
/* 120 */       this.returnType = returnType;
/*     */     }
/*     */ 
/*     */     public List<? extends CharSequence> getParameterTypes() {
/* 124 */       return this.parameters;
/*     */     }
/*     */ 
/*     */     public String getReturnType() {
/* 128 */       return this.returnType;
/*     */     }
/*     */ 
/*     */     public int hashCode() {
/* 132 */       int hashCode = this.returnType.hashCode();
/* 133 */       return hashCode * 31 + this.parameters.hashCode();
/*     */     }
/*     */ 
/*     */     public boolean equals(Object o) {
/* 137 */       if ((o != null) && ((o instanceof BuilderProtoPool.ProtoKey))) {
/* 138 */         BuilderProtoPool.ProtoKey other = (BuilderProtoPool.ProtoKey)o;
/* 139 */         return (getReturnType().equals(other.getReturnType())) && (CharSequenceUtils.listEquals(getParameterTypes(), other.getParameterTypes()));
/*     */       }
/*     */ 
/* 142 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */   static abstract interface ProtoKey
/*     */   {
/*     */     public abstract List<? extends CharSequence> getParameterTypes();
/*     */ 
/*     */     public abstract String getReturnType();
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderProtoPool
 * JD-Core Version:    0.6.0
 */