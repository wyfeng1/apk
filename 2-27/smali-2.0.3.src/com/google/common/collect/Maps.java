/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.base.Joiner;
/*      */ import com.google.common.base.Joiner.MapJoiner;
/*      */ import java.util.HashMap;
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
/*      */   public static <K, V> ConcurrentMap<K, V> newConcurrentMap()
/*      */   {
/*  246 */     return new MapMaker().makeMap();
/*      */   }
/*      */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.Maps
 * JD-Core Version:    0.6.0
 */