/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ 
/*     */ final class RegularImmutableSortedSet<E> extends ImmutableSortedSet<E>
/*     */ {
/*     */   private final transient ImmutableList<E> elements;
/*     */ 
/*     */   RegularImmutableSortedSet(ImmutableList<E> elements, Comparator<? super E> comparator)
/*     */   {
/*  54 */     super(comparator);
/*  55 */     this.elements = elements;
/*  56 */     Preconditions.checkArgument(!elements.isEmpty());
/*     */   }
/*     */ 
/*     */   public UnmodifiableIterator<E> iterator() {
/*  60 */     return this.elements.iterator();
/*     */   }
/*     */ 
/*     */   public UnmodifiableIterator<E> descendingIterator()
/*     */   {
/*  65 */     return this.elements.reverse().iterator();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty() {
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/*  74 */     return this.elements.size();
/*     */   }
/*     */ 
/*     */   public boolean contains(Object o) {
/*     */     try {
/*  79 */       return (o != null) && (unsafeBinarySearch(o) >= 0); } catch (ClassCastException e) {
/*     */     }
/*  81 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean containsAll(Collection<?> targets)
/*     */   {
/*  90 */     if ((!SortedIterables.hasSameComparator(comparator(), targets)) || (targets.size() <= 1))
/*     */     {
/*  92 */       return super.containsAll(targets);
/*     */     }
/*     */ 
/*  99 */     Iterator thisIterator = iterator();
/* 100 */     Iterator thatIterator = targets.iterator();
/* 101 */     Object target = thatIterator.next();
/*     */     try
/*     */     {
/* 105 */       while (thisIterator.hasNext())
/*     */       {
/* 107 */         int cmp = unsafeCompare(thisIterator.next(), target);
/*     */ 
/* 109 */         if (cmp == 0)
/*     */         {
/* 111 */           if (!thatIterator.hasNext())
/*     */           {
/* 113 */             return true;
/*     */           }
/*     */ 
/* 116 */           target = thatIterator.next();
/*     */         }
/* 118 */         else if (cmp > 0) {
/* 119 */           return false;
/*     */         }
/*     */       }
/*     */     } catch (NullPointerException e) {
/* 123 */       return false;
/*     */     } catch (ClassCastException e) {
/* 125 */       return false;
/*     */     }
/*     */ 
/* 128 */     return false;
/*     */   }
/*     */ 
/*     */   private int unsafeBinarySearch(Object key) throws ClassCastException {
/* 132 */     return Collections.binarySearch(this.elements, key, unsafeComparator());
/*     */   }
/*     */ 
/*     */   boolean isPartialView() {
/* 136 */     return this.elements.isPartialView();
/*     */   }
/*     */ 
/*     */   public Object[] toArray() {
/* 140 */     return this.elements.toArray();
/*     */   }
/*     */ 
/*     */   public <T> T[] toArray(T[] array) {
/* 144 */     return this.elements.toArray(array);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object) {
/* 148 */     if (object == this) {
/* 149 */       return true;
/*     */     }
/* 151 */     if (!(object instanceof Set)) {
/* 152 */       return false;
/*     */     }
/*     */ 
/* 155 */     Set that = (Set)object;
/* 156 */     if (size() != that.size()) {
/* 157 */       return false;
/*     */     }
/*     */ 
/* 160 */     if (SortedIterables.hasSameComparator(this.comparator, that)) {
/* 161 */       Iterator otherIterator = that.iterator();
/*     */       try {
/* 163 */         Iterator iterator = iterator();
/* 164 */         while (iterator.hasNext()) {
/* 165 */           Object element = iterator.next();
/* 166 */           Object otherElement = otherIterator.next();
/* 167 */           if ((otherElement == null) || (unsafeCompare(element, otherElement) != 0))
/*     */           {
/* 169 */             return false;
/*     */           }
/*     */         }
/* 172 */         return true;
/*     */       } catch (ClassCastException e) {
/* 174 */         return false;
/*     */       } catch (NoSuchElementException e) {
/* 176 */         return false;
/*     */       }
/*     */     }
/* 179 */     return containsAll(that);
/*     */   }
/*     */ 
/*     */   public E first()
/*     */   {
/* 184 */     return this.elements.get(0);
/*     */   }
/*     */ 
/*     */   public E last()
/*     */   {
/* 189 */     return this.elements.get(size() - 1);
/*     */   }
/*     */ 
/*     */   public E lower(E element)
/*     */   {
/* 194 */     int index = headIndex(element, false) - 1;
/* 195 */     return index == -1 ? null : this.elements.get(index);
/*     */   }
/*     */ 
/*     */   public E floor(E element)
/*     */   {
/* 200 */     int index = headIndex(element, true) - 1;
/* 201 */     return index == -1 ? null : this.elements.get(index);
/*     */   }
/*     */ 
/*     */   public E ceiling(E element)
/*     */   {
/* 206 */     int index = tailIndex(element, true);
/* 207 */     return index == size() ? null : this.elements.get(index);
/*     */   }
/*     */ 
/*     */   public E higher(E element)
/*     */   {
/* 212 */     int index = tailIndex(element, false);
/* 213 */     return index == size() ? null : this.elements.get(index);
/*     */   }
/*     */ 
/*     */   ImmutableSortedSet<E> headSetImpl(E toElement, boolean inclusive)
/*     */   {
/* 218 */     return getSubSet(0, headIndex(toElement, inclusive));
/*     */   }
/*     */ 
/*     */   int headIndex(E toElement, boolean inclusive) {
/* 222 */     return SortedLists.binarySearch(this.elements, Preconditions.checkNotNull(toElement), comparator(), inclusive ? SortedLists.KeyPresentBehavior.FIRST_AFTER : SortedLists.KeyPresentBehavior.FIRST_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
/*     */   }
/*     */ 
/*     */   ImmutableSortedSet<E> subSetImpl(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive)
/*     */   {
/* 230 */     return tailSetImpl(fromElement, fromInclusive).headSetImpl(toElement, toInclusive);
/*     */   }
/*     */ 
/*     */   ImmutableSortedSet<E> tailSetImpl(E fromElement, boolean inclusive)
/*     */   {
/* 236 */     return getSubSet(tailIndex(fromElement, inclusive), size());
/*     */   }
/*     */ 
/*     */   int tailIndex(E fromElement, boolean inclusive) {
/* 240 */     return SortedLists.binarySearch(this.elements, Preconditions.checkNotNull(fromElement), comparator(), inclusive ? SortedLists.KeyPresentBehavior.FIRST_PRESENT : SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
/*     */   }
/*     */ 
/*     */   Comparator<Object> unsafeComparator()
/*     */   {
/* 252 */     return this.comparator;
/*     */   }
/*     */ 
/*     */   ImmutableSortedSet<E> getSubSet(int newFromIndex, int newToIndex) {
/* 256 */     if ((newFromIndex == 0) && (newToIndex == size()))
/* 257 */       return this;
/* 258 */     if (newFromIndex < newToIndex) {
/* 259 */       return new RegularImmutableSortedSet(this.elements.subList(newFromIndex, newToIndex), this.comparator);
/*     */     }
/*     */ 
/* 262 */     return emptySet(this.comparator);
/*     */   }
/*     */ 
/*     */   int indexOf(Object target)
/*     */   {
/* 267 */     if (target == null)
/* 268 */       return -1;
/*     */     int position;
/*     */     try {
/* 272 */       position = SortedLists.binarySearch(this.elements, target, unsafeComparator(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.INVERTED_INSERTION_INDEX);
/*     */     }
/*     */     catch (ClassCastException e) {
/* 275 */       return -1;
/*     */     }
/* 277 */     return position >= 0 ? position : -1;
/*     */   }
/*     */ 
/*     */   ImmutableList<E> createAsList() {
/* 281 */     return new ImmutableSortedAsList(this, this.elements);
/*     */   }
/*     */ 
/*     */   ImmutableSortedSet<E> createDescendingSet()
/*     */   {
/* 286 */     return new RegularImmutableSortedSet(this.elements.reverse(), Ordering.from(this.comparator).reverse());
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.RegularImmutableSortedSet
 * JD-Core Version:    0.6.0
 */