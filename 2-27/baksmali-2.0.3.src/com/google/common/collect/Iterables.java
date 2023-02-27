/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public final class Iterables
/*     */ {
/*     */   public static String toString(Iterable<?> iterable)
/*     */   {
/* 260 */     return Iterators.toString(iterable.iterator());
/*     */   }
/*     */ 
/*     */   public static <T> T getOnlyElement(Iterable<T> iterable)
/*     */   {
/* 271 */     return Iterators.getOnlyElement(iterable.iterator());
/*     */   }
/*     */ 
/*     */   static Object[] toArray(Iterable<?> iterable)
/*     */   {
/* 310 */     return toCollection(iterable).toArray();
/*     */   }
/*     */ 
/*     */   private static <E> Collection<E> toCollection(Iterable<E> iterable)
/*     */   {
/* 319 */     return (iterable instanceof Collection) ? (Collection)iterable : Lists.newArrayList(iterable.iterator());
/*     */   }
/*     */ 
/*     */   public static <T> boolean addAll(Collection<T> addTo, Iterable<? extends T> elementsToAdd)
/*     */   {
/* 332 */     if ((elementsToAdd instanceof Collection)) {
/* 333 */       Collection c = Collections2.cast(elementsToAdd);
/* 334 */       return addTo.addAll(c);
/*     */     }
/* 336 */     return Iterators.addAll(addTo, elementsToAdd.iterator());
/*     */   }
/*     */ 
/*     */   public static <T> Iterable<T> concat(Iterable<? extends T> a, Iterable<? extends T> b)
/*     */   {
/* 419 */     Preconditions.checkNotNull(a);
/* 420 */     Preconditions.checkNotNull(b);
/* 421 */     return concat(Arrays.asList(new Iterable[] { a, b }));
/*     */   }
/*     */ 
/*     */   public static <T> Iterable<T> concat(Iterable<? extends Iterable<? extends T>> inputs)
/*     */   {
/* 489 */     Preconditions.checkNotNull(inputs);
/* 490 */     return new FluentIterable(inputs)
/*     */     {
/*     */       public Iterator<T> iterator() {
/* 493 */         return Iterators.concat(Iterables.access$100(this.val$inputs));
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   private static <T> UnmodifiableIterator<Iterator<? extends T>> iterators(Iterable<? extends Iterable<? extends T>> iterables)
/*     */   {
/* 503 */     Iterator iterableIterator = iterables.iterator();
/*     */ 
/* 505 */     return new UnmodifiableIterator(iterableIterator)
/*     */     {
/*     */       public boolean hasNext() {
/* 508 */         return this.val$iterableIterator.hasNext();
/*     */       }
/*     */ 
/*     */       public Iterator<? extends T> next() {
/* 512 */         return ((Iterable)this.val$iterableIterator.next()).iterator();
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static <T> Iterable<T> filter(Iterable<T> unfiltered, Predicate<? super T> predicate)
/*     */   {
/* 584 */     Preconditions.checkNotNull(unfiltered);
/* 585 */     Preconditions.checkNotNull(predicate);
/* 586 */     return new FluentIterable(unfiltered, predicate)
/*     */     {
/*     */       public Iterator<T> iterator() {
/* 589 */         return Iterators.filter(this.val$unfiltered.iterator(), this.val$predicate);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static <F, T> Iterable<T> transform(Iterable<F> fromIterable, Function<? super F, ? extends T> function)
/*     */   {
/* 708 */     Preconditions.checkNotNull(fromIterable);
/* 709 */     Preconditions.checkNotNull(function);
/* 710 */     return new FluentIterable(fromIterable, function)
/*     */     {
/*     */       public Iterator<T> iterator() {
/* 713 */         return Iterators.transform(this.val$fromIterable.iterator(), this.val$function);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static <T> T getFirst(Iterable<? extends T> iterable, T defaultValue)
/*     */   {
/* 790 */     return Iterators.getNext(iterable.iterator(), defaultValue);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.Iterables
 * JD-Core Version:    0.6.0
 */