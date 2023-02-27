/*      */ package com.google.common.collect;
/*      */ 
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.Set;
/*      */ 
/*      */ public final class Sets
/*      */ {
/*      */   public static <E> HashSet<E> newHashSet()
/*      */   {
/*  165 */     return new HashSet();
/*      */   }
/*      */ 
/*      */   static int hashCodeImpl(Set<?> s)
/*      */   {
/* 1398 */     int hashCode = 0;
/* 1399 */     for (Iterator i$ = s.iterator(); i$.hasNext(); ) { Object o = i$.next();
/* 1400 */       hashCode += (o != null ? o.hashCode() : 0);
/*      */ 
/* 1402 */       hashCode = hashCode ^ 0xFFFFFFFF ^ 0xFFFFFFFF;
/*      */     }
/*      */ 
/* 1405 */     return hashCode;
/*      */   }
/*      */ 
/*      */   static boolean equalsImpl(Set<?> s, Object object)
/*      */   {
/* 1412 */     if (s == object) {
/* 1413 */       return true;
/*      */     }
/* 1415 */     if ((object instanceof Set)) {
/* 1416 */       Set o = (Set)object;
/*      */       try
/*      */       {
/* 1419 */         return (s.size() == o.size()) && (s.containsAll(o));
/*      */       } catch (NullPointerException ignored) {
/* 1421 */         return false;
/*      */       } catch (ClassCastException ignored) {
/* 1423 */         return false;
/*      */       }
/*      */     }
/* 1426 */     return false;
/*      */   }
/*      */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.Sets
 * JD-Core Version:    0.6.0
 */