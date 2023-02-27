/*     */ package com.google.common.collect;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public final class ObjectArrays
/*     */ {
/*  35 */   static final Object[] EMPTY_ARRAY = new Object[0];
/*     */ 
/*     */   public static <T> T[] newArray(T[] reference, int length)
/*     */   {
/*  59 */     return Platform.newArray(reference, length);
/*     */   }
/*     */ 
/*     */   static <T> T[] arraysCopyOf(T[] original, int newLength)
/*     */   {
/* 110 */     Object[] copy = newArray(original, newLength);
/* 111 */     System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
/*     */ 
/* 113 */     return copy;
/*     */   }
/*     */ 
/*     */   static <T> T[] toArrayImpl(Collection<?> c, T[] array)
/*     */   {
/* 141 */     int size = c.size();
/* 142 */     if (array.length < size) {
/* 143 */       array = newArray(array, size);
/*     */     }
/* 145 */     fillArray(c, array);
/* 146 */     if (array.length > size) {
/* 147 */       array[size] = null;
/*     */     }
/* 149 */     return array;
/*     */   }
/*     */ 
/*     */   static Object[] toArrayImpl(Collection<?> c)
/*     */   {
/* 167 */     return fillArray(c, new Object[c.size()]);
/*     */   }
/*     */ 
/*     */   private static Object[] fillArray(Iterable<?> elements, Object[] array) {
/* 171 */     int i = 0;
/* 172 */     for (Iterator i$ = elements.iterator(); i$.hasNext(); ) { Object element = i$.next();
/* 173 */       array[(i++)] = element;
/*     */     }
/* 175 */     return array;
/*     */   }
/*     */ 
/*     */   static Object checkElementNotNull(Object element, int index)
/*     */   {
/* 190 */     if (element == null) {
/* 191 */       throw new NullPointerException("at index " + index);
/*     */     }
/* 193 */     return element;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.ObjectArrays
 * JD-Core Version:    0.6.0
 */