/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.base.Joiner;
/*      */ import com.google.common.base.Joiner.MapJoiner;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import java.util.TreeMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ 
/*      */ public final class Maps
/*      */ {
/* 3394 */   static final Joiner.MapJoiner STANDARD_JOINER = Collections2.STANDARD_JOINER.withKeyValueSeparator("=");
/*      */ 
/*      */   public static <K, V> HashMap<K, V> newHashMap()
/*      */   {
/*  145 */     return new HashMap();
/*      */   }
/*      */ 
/*      */   public static <K, V> LinkedHashMap<K, V> newLinkedHashMap()
/*      */   {
/*  211 */     return new LinkedHashMap();
/*      */   }
/*      */ 
/*      */   public static <K, V> ConcurrentMap<K, V> newConcurrentMap()
/*      */   {
/*  246 */     return new MapMaker().makeMap();
/*      */   }
/*      */ 
/*      */   public static <K extends Comparable, V> TreeMap<K, V> newTreeMap()
/*      */   {
/*  259 */     return new TreeMap();
/*      */   }
/*      */ 
/*      */   public static <K, V> Map.Entry<K, V> immutableEntry(K key, V value)
/*      */   {
/* 1209 */     return new ImmutableEntry(key, value);
/*      */   }
/*      */ 
/*      */   static boolean equalsImpl(Map<?, ?> map, Object object)
/*      */   {
/* 3486 */     if (map == object) {
/* 3487 */       return true;
/*      */     }
/* 3489 */     if ((object instanceof Map)) {
/* 3490 */       Map o = (Map)object;
/* 3491 */       return map.entrySet().equals(o.entrySet());
/*      */     }
/* 3493 */     return false;
/*      */   }
/*      */ 
/*      */   static String toStringImpl(Map<?, ?> map)
/*      */   {
/* 3500 */     StringBuilder sb = Collections2.newStringBuilderForCollection(map.size()).append('{');
/*      */ 
/* 3502 */     STANDARD_JOINER.appendTo(sb, map);
/* 3503 */     return '}';
/*      */   }
/*      */ 
/*      */   static boolean containsValueImpl(Map<?, ?> map, Object value)
/*      */   {
/* 3527 */     return Iterators.contains(valueIterator(map.entrySet().iterator()), value);
/*      */   }
/*      */ 
/*      */   static <K, V> Iterator<V> valueIterator(Iterator<Map.Entry<K, V>> entryIterator)
/*      */   {
/* 3685 */     return new TransformedIterator(entryIterator)
/*      */     {
/*      */       V transform(Map.Entry<K, V> entry) {
/* 3688 */         return entry.getValue();
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   static <K, V> UnmodifiableIterator<V> valueIterator(UnmodifiableIterator<Map.Entry<K, V>> entryIterator) {
/* 3695 */     return new UnmodifiableIterator(entryIterator)
/*      */     {
/*      */       public boolean hasNext() {
/* 3698 */         return this.val$entryIterator.hasNext();
/*      */       }
/*      */ 
/*      */       public V next()
/*      */       {
/* 3703 */         return ((Map.Entry)this.val$entryIterator.next()).getValue();
/*      */       }
/*      */     };
/*      */   }
/*      */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.Maps
 * JD-Core Version:    0.6.0
 */