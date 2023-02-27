/*     */ package org.jf.dexlib2.writer.builder;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import org.jf.dexlib2.writer.TypeListSection;
/*     */ 
/*     */ class BuilderTypeListPool
/*     */   implements TypeListSection<BuilderTypeReference, BuilderTypeList>
/*     */ {
/*     */   private final BuilderContext context;
/*  50 */   private final ConcurrentMap<List<? extends CharSequence>, BuilderTypeList> internedItems = Maps.newConcurrentMap();
/*     */ 
/*     */   BuilderTypeListPool(BuilderContext context)
/*     */   {
/*  54 */     this.context = context;
/*     */   }
/*     */ 
/*     */   public BuilderTypeList internTypeList(List<? extends CharSequence> types) {
/*  58 */     if ((types == null) || (types.size() == 0)) {
/*  59 */       return BuilderTypeList.EMPTY;
/*     */     }
/*     */ 
/*  62 */     BuilderTypeList ret = (BuilderTypeList)this.internedItems.get(types);
/*  63 */     if (ret != null) {
/*  64 */       return ret;
/*     */     }
/*     */ 
/*  67 */     BuilderTypeList typeList = new BuilderTypeList(ImmutableList.copyOf(Iterables.transform(types, new Function()
/*     */     {
/*     */       public BuilderTypeReference apply(CharSequence input) {
/*  70 */         return BuilderTypeListPool.this.context.typePool.internType(input.toString());
/*     */       }
/*     */     })));
/*  74 */     ret = (BuilderTypeList)this.internedItems.putIfAbsent(typeList, typeList);
/*  75 */     return ret == null ? typeList : ret;
/*     */   }
/*     */ 
/*     */   public int getNullableItemOffset(BuilderTypeList key) {
/*  79 */     return (key == null) || (key.size() == 0) ? 0 : key.offset;
/*     */   }
/*     */ 
/*     */   public Collection<? extends BuilderTypeReference> getTypes(BuilderTypeList key)
/*     */   {
/*  84 */     return key == null ? BuilderTypeList.EMPTY : key.types;
/*     */   }
/*     */ 
/*     */   public int getItemOffset(BuilderTypeList key) {
/*  88 */     return key.offset;
/*     */   }
/*     */ 
/*     */   public Collection<? extends Map.Entry<? extends BuilderTypeList, Integer>> getItems() {
/*  92 */     return new BuilderMapEntryCollection(this.internedItems.values()) {
/*     */       protected int getValue(BuilderTypeList key) {
/*  94 */         return key.offset;
/*     */       }
/*     */ 
/*     */       protected int setValue(BuilderTypeList key, int value) {
/*  98 */         int prev = key.offset;
/*  99 */         key.offset = value;
/* 100 */         return prev;
/*     */       }
/*     */     };
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderTypeListPool
 * JD-Core Version:    0.6.0
 */