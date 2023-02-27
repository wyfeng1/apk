/*    */ package com.google.common.collect;
/*    */ 
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ final class ImmutableMapValues<K, V> extends ImmutableCollection<V>
/*    */ {
/*    */   private final ImmutableMap<K, V> map;
/*    */ 
/*    */   ImmutableMapValues(ImmutableMap<K, V> map)
/*    */   {
/* 36 */     this.map = map;
/*    */   }
/*    */ 
/*    */   public int size()
/*    */   {
/* 41 */     return this.map.size();
/*    */   }
/*    */ 
/*    */   public UnmodifiableIterator<V> iterator()
/*    */   {
/* 46 */     return Maps.valueIterator(this.map.entrySet().iterator());
/*    */   }
/*    */ 
/*    */   public boolean contains(Object object)
/*    */   {
/* 51 */     return this.map.containsValue(object);
/*    */   }
/*    */ 
/*    */   boolean isPartialView()
/*    */   {
/* 56 */     return true;
/*    */   }
/*    */ 
/*    */   ImmutableList<V> createAsList()
/*    */   {
/* 61 */     ImmutableList entryList = this.map.entrySet().asList();
/* 62 */     return new ImmutableAsList(entryList)
/*    */     {
/*    */       public V get(int index) {
/* 65 */         return ((Map.Entry)this.val$entryList.get(index)).getValue();
/*    */       }
/*    */ 
/*    */       ImmutableCollection<V> delegateCollection()
/*    */       {
/* 70 */         return ImmutableMapValues.this;
/*    */       }
/*    */     };
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.ImmutableMapValues
 * JD-Core Version:    0.6.0
 */