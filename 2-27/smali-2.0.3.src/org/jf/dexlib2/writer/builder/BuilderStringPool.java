/*    */ package org.jf.dexlib2.writer.builder;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Collection;
/*    */ import java.util.Map.Entry;
/*    */ import java.util.concurrent.ConcurrentMap;
/*    */ import org.jf.dexlib2.writer.StringSection;
/*    */ 
/*    */ class BuilderStringPool
/*    */   implements StringSection<BuilderStringReference, BuilderStringReference>
/*    */ {
/* 45 */   private final ConcurrentMap<String, BuilderStringReference> internedItems = Maps.newConcurrentMap();
/*    */ 
/*    */   BuilderStringReference internString(String string) {
/* 48 */     BuilderStringReference ret = (BuilderStringReference)this.internedItems.get(string);
/* 49 */     if (ret != null) {
/* 50 */       return ret;
/*    */     }
/* 52 */     BuilderStringReference stringReference = new BuilderStringReference(string);
/* 53 */     ret = (BuilderStringReference)this.internedItems.putIfAbsent(string, stringReference);
/* 54 */     return ret == null ? stringReference : ret;
/*    */   }
/*    */ 
/*    */   BuilderStringReference internNullableString(String string) {
/* 58 */     if (string == null) {
/* 59 */       return null;
/*    */     }
/* 61 */     return internString(string);
/*    */   }
/*    */ 
/*    */   public int getNullableItemIndex(BuilderStringReference key) {
/* 65 */     return key == null ? -1 : key.index;
/*    */   }
/*    */ 
/*    */   public int getItemIndex(BuilderStringReference key) {
/* 69 */     return key.index;
/*    */   }
/*    */ 
/*    */   public boolean hasJumboIndexes() {
/* 73 */     return this.internedItems.size() > 65536;
/*    */   }
/*    */ 
/*    */   public Collection<? extends Map.Entry<? extends BuilderStringReference, Integer>> getItems() {
/* 77 */     return new BuilderMapEntryCollection(this.internedItems.values()) {
/*    */       protected int getValue(BuilderStringReference key) {
/* 79 */         return key.index;
/*    */       }
/*    */ 
/*    */       protected int setValue(BuilderStringReference key, int value) {
/* 83 */         int prev = key.index;
/* 84 */         key.index = value;
/* 85 */         return prev;
/*    */       }
/*    */     };
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderStringPool
 * JD-Core Version:    0.6.0
 */