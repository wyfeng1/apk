/*    */ package com.google.common.collect;
/*    */ 
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ final class ImmutableMapKeySet<K, V> extends ImmutableSet<K>
/*    */ {
/*    */   private final ImmutableMap<K, V> map;
/*    */ 
/*    */   ImmutableMapKeySet(ImmutableMap<K, V> map)
/*    */   {
/* 38 */     this.map = map;
/*    */   }
/*    */ 
/*    */   public int size()
/*    */   {
/* 43 */     return this.map.size();
/*    */   }
/*    */ 
/*    */   public UnmodifiableIterator<K> iterator()
/*    */   {
/* 48 */     return asList().iterator();
/*    */   }
/*    */ 
/*    */   public boolean contains(Object object)
/*    */   {
/* 53 */     return this.map.containsKey(object);
/*    */   }
/*    */ 
/*    */   ImmutableList<K> createAsList()
/*    */   {
/* 58 */     ImmutableList entryList = this.map.entrySet().asList();
/* 59 */     return new ImmutableAsList(entryList)
/*    */     {
/*    */       public K get(int index)
/*    */       {
/* 63 */         return ((Map.Entry)this.val$entryList.get(index)).getKey();
/*    */       }
/*    */ 
/*    */       ImmutableCollection<K> delegateCollection()
/*    */       {
/* 68 */         return ImmutableMapKeySet.this;
/*    */       }
/*    */     };
/*    */   }
/*    */ 
/*    */   boolean isPartialView()
/*    */   {
/* 76 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.ImmutableMapKeySet
 * JD-Core Version:    0.6.0
 */