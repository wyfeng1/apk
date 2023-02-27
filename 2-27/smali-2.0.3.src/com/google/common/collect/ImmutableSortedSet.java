/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.NavigableSet;
/*     */ 
/*     */ public abstract class ImmutableSortedSet<E> extends ImmutableSortedSetFauxverideShim<E>
/*     */   implements SortedIterable<E>, NavigableSet<E>
/*     */ {
/*  98 */   private static final Comparator<Comparable> NATURAL_ORDER = Ordering.natural();
/*     */ 
/* 101 */   private static final ImmutableSortedSet<Comparable> NATURAL_EMPTY_SET = new EmptyImmutableSortedSet(NATURAL_ORDER);
/*     */   final transient Comparator<? super E> comparator;
/*     */   transient ImmutableSortedSet<E> descendingSet;
/*     */ 
/*     */   private static <E> ImmutableSortedSet<E> emptySet()
/*     */   {
/* 106 */     return NATURAL_EMPTY_SET;
/*     */   }
/*     */ 
/*     */   static <E> ImmutableSortedSet<E> emptySet(Comparator<? super E> comparator)
/*     */   {
/* 111 */     if (NATURAL_ORDER.equals(comparator)) {
/* 112 */       return emptySet();
/*     */     }
/* 114 */     return new EmptyImmutableSortedSet(comparator);
/*     */   }
/*     */ 
/*     */   public static <E> ImmutableSortedSet<E> copyOf(Iterable<? extends E> elements)
/*     */   {
/* 250 */     Ordering naturalOrder = Ordering.natural();
/* 251 */     return copyOf(naturalOrder, elements);
/*     */   }
/*     */ 
/*     */   public static <E> ImmutableSortedSet<E> copyOf(Collection<? extends E> elements)
/*     */   {
/* 288 */     Ordering naturalOrder = Ordering.natural();
/* 289 */     return copyOf(naturalOrder, elements);
/*     */   }
/*     */ 
/*     */   public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Iterable<? extends E> elements)
/*     */   {
/* 341 */     Preconditions.checkNotNull(comparator);
/* 342 */     boolean hasSameComparator = SortedIterables.hasSameComparator(comparator, elements);
/*     */ 
/* 345 */     if ((hasSameComparator) && ((elements instanceof ImmutableSortedSet)))
/*     */     {
/* 347 */       ImmutableSortedSet original = (ImmutableSortedSet)elements;
/* 348 */       if (!original.isPartialView()) {
/* 349 */         return original;
/*     */       }
/*     */     }
/*     */ 
/* 353 */     Object[] array = (Object[])Iterables.toArray(elements);
/* 354 */     return construct(comparator, array.length, array);
/*     */   }
/*     */ 
/*     */   public static <E> ImmutableSortedSet<E> copyOf(Comparator<? super E> comparator, Collection<? extends E> elements)
/*     */   {
/* 377 */     return copyOf(comparator, elements);
/*     */   }
/*     */ 
/*     */   static <E> int sortAndUnique(Comparator<? super E> comparator, int n, E[] contents)
/*     */   {
/* 419 */     if (n == 0) {
/* 420 */       return 0;
/*     */     }
/* 422 */     for (int i = 0; i < n; i++) {
/* 423 */       ObjectArrays.checkElementNotNull(contents[i], i);
/*     */     }
/* 425 */     Arrays.sort(contents, 0, n, comparator);
/* 426 */     int uniques = 1;
/* 427 */     for (int i = 1; i < n; i++) {
/* 428 */       Object cur = contents[i];
/* 429 */       Object prev = contents[(uniques - 1)];
/* 430 */       if (comparator.compare(cur, prev) != 0) {
/* 431 */         contents[(uniques++)] = cur;
/*     */       }
/*     */     }
/* 434 */     Arrays.fill(contents, uniques, n, null);
/* 435 */     return uniques;
/*     */   }
/*     */ 
/*     */   static <E> ImmutableSortedSet<E> construct(Comparator<? super E> comparator, int n, E[] contents)
/*     */   {
/* 452 */     int uniques = sortAndUnique(comparator, n, contents);
/* 453 */     if (uniques == 0)
/* 454 */       return emptySet(comparator);
/* 455 */     if (uniques < contents.length) {
/* 456 */       contents = ObjectArrays.arraysCopyOf(contents, uniques);
/*     */     }
/* 458 */     return new RegularImmutableSortedSet(ImmutableList.asImmutableList(contents), comparator);
/*     */   }
/*     */ 
/*     */   int unsafeCompare(Object a, Object b)
/*     */   {
/* 589 */     return unsafeCompare(this.comparator, a, b);
/*     */   }
/*     */ 
/*     */   static int unsafeCompare(Comparator<?> comparator, Object a, Object b)
/*     */   {
/* 598 */     Comparator unsafeComparator = comparator;
/* 599 */     return unsafeComparator.compare(a, b);
/*     */   }
/*     */ 
/*     */   ImmutableSortedSet(Comparator<? super E> comparator)
/*     */   {
/* 605 */     this.comparator = comparator;
/*     */   }
/*     */ 
/*     */   public Comparator<? super E> comparator()
/*     */   {
/* 617 */     return this.comparator;
/*     */   }
/*     */ 
/*     */   public abstract UnmodifiableIterator<E> iterator();
/*     */ 
/*     */   public ImmutableSortedSet<E> headSet(E toElement)
/*     */   {
/* 636 */     return headSet(toElement, false);
/*     */   }
/*     */ 
/*     */   public ImmutableSortedSet<E> headSet(E toElement, boolean inclusive)
/*     */   {
/* 645 */     return headSetImpl(Preconditions.checkNotNull(toElement), inclusive);
/*     */   }
/*     */ 
/*     */   public ImmutableSortedSet<E> subSet(E fromElement, E toElement)
/*     */   {
/* 663 */     return subSet(fromElement, true, toElement, false);
/*     */   }
/*     */ 
/*     */   public ImmutableSortedSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive)
/*     */   {
/* 673 */     Preconditions.checkNotNull(fromElement);
/* 674 */     Preconditions.checkNotNull(toElement);
/* 675 */     Preconditions.checkArgument(this.comparator.compare(fromElement, toElement) <= 0);
/* 676 */     return subSetImpl(fromElement, fromInclusive, toElement, toInclusive);
/*     */   }
/*     */ 
/*     */   public ImmutableSortedSet<E> tailSet(E fromElement)
/*     */   {
/* 692 */     return tailSet(fromElement, true);
/*     */   }
/*     */ 
/*     */   public ImmutableSortedSet<E> tailSet(E fromElement, boolean inclusive)
/*     */   {
/* 701 */     return tailSetImpl(Preconditions.checkNotNull(fromElement), inclusive);
/*     */   }
/*     */ 
/*     */   abstract ImmutableSortedSet<E> headSetImpl(E paramE, boolean paramBoolean);
/*     */ 
/*     */   abstract ImmutableSortedSet<E> subSetImpl(E paramE1, boolean paramBoolean1, E paramE2, boolean paramBoolean2);
/*     */ 
/*     */   abstract ImmutableSortedSet<E> tailSetImpl(E paramE, boolean paramBoolean);
/*     */ 
/*     */   public E lower(E e)
/*     */   {
/* 721 */     return Iterators.getNext(headSet(e, false).descendingIterator(), null);
/*     */   }
/*     */ 
/*     */   public E floor(E e)
/*     */   {
/* 730 */     return Iterators.getNext(headSet(e, true).descendingIterator(), null);
/*     */   }
/*     */ 
/*     */   public E ceiling(E e)
/*     */   {
/* 739 */     return Iterables.getFirst(tailSet(e, true), null);
/*     */   }
/*     */ 
/*     */   public E higher(E e)
/*     */   {
/* 748 */     return Iterables.getFirst(tailSet(e, false), null);
/*     */   }
/*     */ 
/*     */   public E first()
/*     */   {
/* 753 */     return iterator().next();
/*     */   }
/*     */ 
/*     */   public E last()
/*     */   {
/* 758 */     return descendingIterator().next();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public final E pollFirst()
/*     */   {
/* 772 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public final E pollLast()
/*     */   {
/* 786 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public ImmutableSortedSet<E> descendingSet()
/*     */   {
/* 799 */     ImmutableSortedSet result = this.descendingSet;
/* 800 */     if (result == null) {
/* 801 */       result = this.descendingSet = createDescendingSet();
/* 802 */       result.descendingSet = this;
/*     */     }
/* 804 */     return result;
/*     */   }
/*     */ 
/*     */   ImmutableSortedSet<E> createDescendingSet()
/*     */   {
/* 809 */     return new DescendingImmutableSortedSet(this);
/*     */   }
/*     */ 
/*     */   public abstract UnmodifiableIterator<E> descendingIterator();
/*     */ 
/*     */   abstract int indexOf(Object paramObject);
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.ImmutableSortedSet
 * JD-Core Version:    0.6.0
 */