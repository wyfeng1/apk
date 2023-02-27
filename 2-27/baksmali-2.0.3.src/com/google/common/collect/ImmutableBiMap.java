/*     */ package com.google.common.collect;
/*     */ 
/*     */ public abstract class ImmutableBiMap<K, V> extends ImmutableMap<K, V>
/*     */   implements BiMap<K, V>
/*     */ {
/*     */   public static <K, V> ImmutableBiMap<K, V> of()
/*     */   {
/*  53 */     return EmptyImmutableBiMap.INSTANCE;
/*     */   }
/*     */ 
/*     */   public abstract ImmutableBiMap<V, K> inverse();
/*     */ 
/*     */   public ImmutableSet<V> values()
/*     */   {
/* 249 */     return inverse().keySet();
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.ImmutableBiMap
 * JD-Core Version:    0.6.0
 */