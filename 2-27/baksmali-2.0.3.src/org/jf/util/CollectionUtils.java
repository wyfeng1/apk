/*     */ package org.jf.util;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSortedSet;
/*     */ import com.google.common.collect.Ordering;
/*     */ import com.google.common.primitives.Ints;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.SortedSet;
/*     */ 
/*     */ public class CollectionUtils
/*     */ {
/*     */   public static <T extends Comparable<? super T>> int compareAsList(Collection<? extends T> list1, Collection<? extends T> list2)
/*     */   {
/*  65 */     int res = Ints.compare(list1.size(), list2.size());
/*  66 */     if (res != 0) return res;
/*  67 */     Iterator elements2 = list2.iterator();
/*  68 */     for (Comparable element1 : list1) {
/*  69 */       res = element1.compareTo(elements2.next());
/*  70 */       if (res != 0) return res;
/*     */     }
/*  72 */     return 0;
/*     */   }
/*     */ 
/*     */   public static <T> int compareAsIterable(Comparator<? super T> comparator, Iterable<? extends T> it1, Iterable<? extends T> it2)
/*     */   {
/*  78 */     Iterator elements2 = it2.iterator();
/*  79 */     for (Iterator i$ = it1.iterator(); i$.hasNext(); ) { Object element1 = i$.next();
/*     */       Object element2;
/*     */       try { element2 = elements2.next();
/*     */       } catch (NoSuchElementException ex) {
/*  84 */         return 1;
/*     */       }
/*  86 */       int res = comparator.compare(element1, element2);
/*  87 */       if (res != 0) return res;
/*     */     }
/*  89 */     if (elements2.hasNext()) {
/*  90 */       return -1;
/*     */     }
/*  92 */     return 0;
/*     */   }
/*     */ 
/*     */   public static <T> boolean isNaturalSortedSet(Iterable<? extends T> it)
/*     */   {
/* 139 */     if ((it instanceof SortedSet)) {
/* 140 */       SortedSet sortedSet = (SortedSet)it;
/* 141 */       Comparator comparator = sortedSet.comparator();
/* 142 */       return (comparator == null) || (comparator.equals(Ordering.natural()));
/*     */     }
/* 144 */     return false;
/*     */   }
/*     */ 
/*     */   private static <T> SortedSet<? extends T> toNaturalSortedSet(Collection<? extends T> collection)
/*     */   {
/* 162 */     if (isNaturalSortedSet(collection)) {
/* 163 */       return (SortedSet)collection;
/*     */     }
/* 165 */     return ImmutableSortedSet.copyOf(collection);
/*     */   }
/*     */ 
/*     */   public static <T extends Comparable<T>> int compareAsSet(Collection<? extends T> set1, Collection<? extends T> set2)
/*     */   {
/* 194 */     int res = Ints.compare(set1.size(), set2.size());
/* 195 */     if (res != 0) return res;
/*     */ 
/* 197 */     SortedSet sortedSet1 = toNaturalSortedSet(set1);
/* 198 */     SortedSet sortedSet2 = toNaturalSortedSet(set2);
/*     */ 
/* 200 */     Iterator elements2 = set2.iterator();
/* 201 */     for (Comparable element1 : set1) {
/* 202 */       res = element1.compareTo(elements2.next());
/* 203 */       if (res != 0) return res;
/*     */     }
/* 205 */     return 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\?????????\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.util.CollectionUtils
 * JD-Core Version:    0.6.0
 */