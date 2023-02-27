/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public abstract class ImmutableMap<K, V>
/*     */   implements Serializable, Map<K, V>
/*     */ {
/*     */   private transient ImmutableSet<Map.Entry<K, V>> entrySet;
/*     */   private transient ImmutableSet<K> keySet;
/*     */   private transient ImmutableCollection<V> values;
/*     */ 
/*     */   public static <K, V> ImmutableMap<K, V> of()
/*     */   {
/*  70 */     return ImmutableBiMap.of();
/*     */   }
/*     */ 
/*     */   public static <K, V> Builder<K, V> builder()
/*     */   {
/* 132 */     return new Builder();
/*     */   }
/*     */ 
/*     */   static <K, V> Map.Entry<K, V> entryOf(K key, V value)
/*     */   {
/* 143 */     Preconditions.checkNotNull(key, "null key in entry: null=%s", new Object[] { value });
/* 144 */     Preconditions.checkNotNull(value, "null value in entry: %s=null", new Object[] { key });
/* 145 */     return Maps.immutableEntry(key, value);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public final V put(K k, V v)
/*     */   {
/* 318 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public final V remove(Object o)
/*     */   {
/* 330 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public final void putAll(Map<? extends K, ? extends V> map)
/*     */   {
/* 342 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public final void clear()
/*     */   {
/* 354 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 359 */     return size() == 0;
/*     */   }
/*     */ 
/*     */   public boolean containsKey(Object key)
/*     */   {
/* 364 */     return get(key) != null;
/*     */   }
/*     */ 
/*     */   public boolean containsValue(Object value)
/*     */   {
/* 369 */     return (value != null) && (Maps.containsValueImpl(this, value));
/*     */   }
/*     */ 
/*     */   public abstract V get(Object paramObject);
/*     */ 
/*     */   public ImmutableSet<Map.Entry<K, V>> entrySet()
/*     */   {
/* 384 */     ImmutableSet result = this.entrySet;
/* 385 */     return result == null ? (this.entrySet = createEntrySet()) : result;
/*     */   }
/*     */ 
/*     */   abstract ImmutableSet<Map.Entry<K, V>> createEntrySet();
/*     */ 
/*     */   public ImmutableSet<K> keySet()
/*     */   {
/* 398 */     ImmutableSet result = this.keySet;
/* 399 */     return result == null ? (this.keySet = createKeySet()) : result;
/*     */   }
/*     */ 
/*     */   ImmutableSet<K> createKeySet() {
/* 403 */     return new ImmutableMapKeySet(this);
/*     */   }
/*     */ 
/*     */   public ImmutableCollection<V> values()
/*     */   {
/* 414 */     ImmutableCollection result = this.values;
/* 415 */     return result == null ? (this.values = new ImmutableMapValues(this)) : result;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 492 */     return Maps.equalsImpl(this, object);
/*     */   }
/*     */ 
/*     */   abstract boolean isPartialView();
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 500 */     return entrySet().hashCode();
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 504 */     return Maps.toStringImpl(this);
/*     */   }
/*     */ 
/*     */   public static class Builder<K, V>
/*     */   {
/* 169 */     final ArrayList<Map.Entry<K, V>> entries = Lists.newArrayList();
/*     */ 
/*     */     public Builder<K, V> put(K key, V value)
/*     */     {
/* 182 */       this.entries.add(ImmutableMap.entryOf(key, value));
/* 183 */       return this;
/*     */     }
/*     */ 
/*     */     public ImmutableMap<K, V> build()
/*     */     {
/* 235 */       return fromEntryList(this.entries);
/*     */     }
/*     */ 
/*     */     private static <K, V> ImmutableMap<K, V> fromEntryList(List<Map.Entry<K, V>> entries)
/*     */     {
/* 240 */       int size = entries.size();
/* 241 */       switch (size) {
/*     */       case 0:
/* 243 */         return ImmutableMap.of();
/*     */       case 1:
/* 245 */         return new SingletonImmutableBiMap((Map.Entry)Iterables.getOnlyElement(entries));
/*     */       }
/* 247 */       Map.Entry[] entryArray = (Map.Entry[])entries.toArray(new Map.Entry[entries.size()]);
/*     */ 
/* 249 */       return new RegularImmutableMap(entryArray);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.ImmutableMap
 * JD-Core Version:    0.6.0
 */