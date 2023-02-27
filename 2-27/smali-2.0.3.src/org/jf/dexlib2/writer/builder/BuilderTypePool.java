/*    */ package org.jf.dexlib2.writer.builder;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Collection;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.concurrent.ConcurrentMap;
/*    */ import org.jf.dexlib2.writer.TypeSection;
/*    */ 
/*    */ class BuilderTypePool
/*    */   implements TypeSection<BuilderStringReference, BuilderTypeReference, BuilderTypeReference>
/*    */ {
/*    */   private final BuilderContext context;
/* 46 */   private final ConcurrentMap<String, BuilderTypeReference> internedItems = Maps.newConcurrentMap();
/*    */ 
/*    */   BuilderTypePool(BuilderContext context) {
/* 49 */     this.context = context;
/*    */   }
/*    */ 
/*    */   public BuilderTypeReference internType(String type) {
/* 53 */     BuilderTypeReference ret = (BuilderTypeReference)this.internedItems.get(type);
/* 54 */     if (ret != null) {
/* 55 */       return ret;
/*    */     }
/* 57 */     BuilderStringReference stringRef = this.context.stringPool.internString(type);
/* 58 */     BuilderTypeReference typeReference = new BuilderTypeReference(stringRef);
/* 59 */     ret = (BuilderTypeReference)this.internedItems.putIfAbsent(type, typeReference);
/* 60 */     return ret == null ? typeReference : ret;
/*    */   }
/*    */ 
/*    */   public BuilderTypeReference internNullableType(String type) {
/* 64 */     if (type == null) {
/* 65 */       return null;
/*    */     }
/* 67 */     return internType(type);
/*    */   }
/*    */ 
/*    */   public BuilderStringReference getString(BuilderTypeReference key) {
/* 71 */     return key.stringReference;
/*    */   }
/*    */ 
/*    */   public int getNullableItemIndex(BuilderTypeReference key) {
/* 75 */     return key == null ? -1 : key.index;
/*    */   }
/*    */ 
/*    */   public int getItemIndex(BuilderTypeReference key) {
/* 79 */     return key.getIndex();
/*    */   }
/*    */ 
/*    */   public Collection<? extends Map.Entry<? extends BuilderTypeReference, Integer>> getItems() {
/* 83 */     return new BuilderMapEntryCollection(this.internedItems.values()) {
/*    */       protected int getValue(BuilderTypeReference key) {
/* 85 */         return key.index;
/*    */       }
/*    */ 
/*    */       protected int setValue(BuilderTypeReference key, int value) {
/* 89 */         int prev = key.index;
/* 90 */         key.index = value;
/* 91 */         return prev;
/*    */       }
/*    */     };
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderTypePool
 * JD-Core Version:    0.6.0
 */