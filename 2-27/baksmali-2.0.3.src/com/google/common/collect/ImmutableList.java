/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.RandomAccess;
/*     */ 
/*     */ public abstract class ImmutableList<E> extends ImmutableCollection<E>
/*     */   implements List<E>, RandomAccess
/*     */ {
/*     */   public static <E> ImmutableList<E> of()
/*     */   {
/*  74 */     return EmptyImmutableList.INSTANCE;
/*     */   }
/*     */ 
/*     */   public static <E> ImmutableList<E> of(E element)
/*     */   {
/*  86 */     return new SingletonImmutableList(element);
/*     */   }
/*     */ 
/*     */   public static <E> ImmutableList<E> copyOf(Iterable<? extends E> elements)
/*     */   {
/* 222 */     Preconditions.checkNotNull(elements);
/* 223 */     return (elements instanceof Collection) ? copyOf(Collections2.cast(elements)) : copyOf(elements.iterator());
/*     */   }
/*     */ 
/*     */   public static <E> ImmutableList<E> copyOf(Collection<? extends E> elements)
/*     */   {
/* 248 */     if ((elements instanceof ImmutableCollection))
/*     */     {
/* 250 */       ImmutableList list = ((ImmutableCollection)elements).asList();
/* 251 */       return list.isPartialView() ? copyFromCollection(list) : list;
/*     */     }
/* 253 */     return copyFromCollection(elements);
/*     */   }
/*     */ 
/*     */   public static <E> ImmutableList<E> copyOf(Iterator<? extends E> elements)
/*     */   {
/* 263 */     if (!elements.hasNext()) {
/* 264 */       return of();
/*     */     }
/* 266 */     Object first = elements.next();
/* 267 */     if (!elements.hasNext()) {
/* 268 */       return of(first);
/*     */     }
/* 270 */     return new Builder().add(first).addAll(elements).build();
/*     */   }
/*     */ 
/*     */   static <E> ImmutableList<E> asImmutableList(Object[] elements)
/*     */   {
/* 300 */     switch (elements.length) {
/*     */     case 0:
/* 302 */       return of();
/*     */     case 1:
/* 305 */       ImmutableList list = new SingletonImmutableList(elements[0]);
/* 306 */       return list;
/*     */     }
/* 308 */     return construct(elements);
/*     */   }
/*     */ 
/*     */   private static <E> ImmutableList<E> copyFromCollection(Collection<? extends E> collection)
/*     */   {
/* 314 */     return asImmutableList(collection.toArray());
/*     */   }
/*     */ 
/*     */   private static <E> ImmutableList<E> construct(Object[] elements)
/*     */   {
/* 319 */     for (int i = 0; i < elements.length; i++) {
/* 320 */       ObjectArrays.checkElementNotNull(elements[i], i);
/*     */     }
/* 322 */     return new RegularImmutableList(elements);
/*     */   }
/*     */ 
/*     */   public UnmodifiableIterator<E> iterator()
/*     */   {
/* 330 */     return listIterator();
/*     */   }
/*     */ 
/*     */   public UnmodifiableListIterator<E> listIterator() {
/* 334 */     return listIterator(0);
/*     */   }
/*     */ 
/*     */   public UnmodifiableListIterator<E> listIterator(int index) {
/* 338 */     return new AbstractIndexedListIterator(size(), index)
/*     */     {
/*     */       protected E get(int index) {
/* 341 */         return ImmutableList.this.get(index);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public int indexOf(Object object) {
/* 348 */     return object == null ? -1 : Lists.indexOfImpl(this, object);
/*     */   }
/*     */ 
/*     */   public int lastIndexOf(Object object)
/*     */   {
/* 353 */     return object == null ? -1 : Lists.lastIndexOfImpl(this, object);
/*     */   }
/*     */ 
/*     */   public boolean contains(Object object)
/*     */   {
/* 358 */     return indexOf(object) >= 0;
/*     */   }
/*     */ 
/*     */   public ImmutableList<E> subList(int fromIndex, int toIndex)
/*     */   {
/* 371 */     Preconditions.checkPositionIndexes(fromIndex, toIndex, size());
/* 372 */     int length = toIndex - fromIndex;
/* 373 */     switch (length) {
/*     */     case 0:
/* 375 */       return of();
/*     */     case 1:
/* 377 */       return of(get(fromIndex));
/*     */     }
/* 379 */     return subListUnchecked(fromIndex, toIndex);
/*     */   }
/*     */ 
/*     */   ImmutableList<E> subListUnchecked(int fromIndex, int toIndex)
/*     */   {
/* 389 */     return new SubList(fromIndex, toIndex - fromIndex);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public final boolean addAll(int index, Collection<? extends E> newElements)
/*     */   {
/* 433 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public final E set(int index, E element)
/*     */   {
/* 445 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public final void add(int index, E element)
/*     */   {
/* 457 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public final E remove(int index)
/*     */   {
/* 469 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public ImmutableList<E> asList()
/*     */   {
/* 478 */     return this;
/*     */   }
/*     */ 
/*     */   public ImmutableList<E> reverse()
/*     */   {
/* 490 */     return new ReverseImmutableList(this);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 588 */     return Lists.equalsImpl(this, obj);
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/* 592 */     return Lists.hashCodeImpl(this);
/*     */   }
/*     */ 
/*     */   public static final class Builder<E> extends ImmutableCollection.Builder<E>
/*     */   {
/*     */     private Object[] contents;
/*     */     private int size;
/*     */ 
/*     */     public Builder()
/*     */     {
/* 652 */       this(4);
/*     */     }
/*     */ 
/*     */     Builder(int capacity)
/*     */     {
/* 657 */       this.contents = new Object[capacity];
/* 658 */       this.size = 0;
/*     */     }
/*     */ 
/*     */     Builder<E> ensureCapacity(int minCapacity)
/*     */     {
/* 666 */       if (this.contents.length < minCapacity) {
/* 667 */         this.contents = ObjectArrays.arraysCopyOf(this.contents, expandedCapacity(this.contents.length, minCapacity));
/*     */       }
/*     */ 
/* 670 */       return this;
/*     */     }
/*     */ 
/*     */     public Builder<E> add(E element)
/*     */     {
/* 681 */       Preconditions.checkNotNull(element);
/* 682 */       ensureCapacity(this.size + 1);
/* 683 */       this.contents[(this.size++)] = element;
/* 684 */       return this;
/*     */     }
/*     */ 
/*     */     public Builder<E> addAll(Iterator<? extends E> elements)
/*     */     {
/* 731 */       super.addAll(elements);
/* 732 */       return this;
/*     */     }
/*     */ 
/*     */     public ImmutableList<E> build()
/*     */     {
/* 740 */       switch (this.size) {
/*     */       case 0:
/* 742 */         return ImmutableList.of();
/*     */       case 1:
/* 745 */         Object singleElement = this.contents[0];
/* 746 */         return ImmutableList.of(singleElement);
/*     */       }
/* 748 */       if (this.size == this.contents.length)
/*     */       {
/* 750 */         return new RegularImmutableList(this.contents);
/*     */       }
/* 752 */       return new RegularImmutableList(ObjectArrays.arraysCopyOf(this.contents, this.size));
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class ReverseImmutableList<E> extends ImmutableList<E>
/*     */   {
/*     */     private final transient ImmutableList<E> forwardList;
/*     */     private final transient int size;
/*     */ 
/*     */     ReverseImmutableList(ImmutableList<E> backingList)
/*     */     {
/* 498 */       this.forwardList = backingList;
/* 499 */       this.size = backingList.size();
/*     */     }
/*     */ 
/*     */     private int reverseIndex(int index) {
/* 503 */       return this.size - 1 - index;
/*     */     }
/*     */ 
/*     */     private int reversePosition(int index) {
/* 507 */       return this.size - index;
/*     */     }
/*     */ 
/*     */     public ImmutableList<E> reverse() {
/* 511 */       return this.forwardList;
/*     */     }
/*     */ 
/*     */     public boolean contains(Object object) {
/* 515 */       return this.forwardList.contains(object);
/*     */     }
/*     */ 
/*     */     public boolean containsAll(Collection<?> targets) {
/* 519 */       return this.forwardList.containsAll(targets);
/*     */     }
/*     */ 
/*     */     public int indexOf(Object object) {
/* 523 */       int index = this.forwardList.lastIndexOf(object);
/* 524 */       return index >= 0 ? reverseIndex(index) : -1;
/*     */     }
/*     */ 
/*     */     public int lastIndexOf(Object object) {
/* 528 */       int index = this.forwardList.indexOf(object);
/* 529 */       return index >= 0 ? reverseIndex(index) : -1;
/*     */     }
/*     */ 
/*     */     public ImmutableList<E> subList(int fromIndex, int toIndex) {
/* 533 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, this.size);
/* 534 */       return this.forwardList.subList(reversePosition(toIndex), reversePosition(fromIndex)).reverse();
/*     */     }
/*     */ 
/*     */     public E get(int index)
/*     */     {
/* 539 */       Preconditions.checkElementIndex(index, this.size);
/* 540 */       return this.forwardList.get(reverseIndex(index));
/*     */     }
/*     */ 
/*     */     public UnmodifiableListIterator<E> listIterator(int index) {
/* 544 */       Preconditions.checkPositionIndex(index, this.size);
/* 545 */       UnmodifiableListIterator forward = this.forwardList.listIterator(reversePosition(index));
/*     */ 
/* 547 */       return new UnmodifiableListIterator(forward) {
/*     */         public boolean hasNext() {
/* 549 */           return this.val$forward.hasPrevious();
/*     */         }
/*     */ 
/*     */         public boolean hasPrevious() {
/* 553 */           return this.val$forward.hasNext();
/*     */         }
/*     */ 
/*     */         public E next() {
/* 557 */           return this.val$forward.previous();
/*     */         }
/*     */ 
/*     */         public int nextIndex() {
/* 561 */           return ImmutableList.ReverseImmutableList.this.reverseIndex(this.val$forward.previousIndex());
/*     */         }
/*     */ 
/*     */         public E previous() {
/* 565 */           return this.val$forward.next();
/*     */         }
/*     */ 
/*     */         public int previousIndex() {
/* 569 */           return ImmutableList.ReverseImmutableList.this.reverseIndex(this.val$forward.nextIndex());
/*     */         } } ;
/*     */     }
/*     */ 
/*     */     public int size() {
/* 575 */       return this.size;
/*     */     }
/*     */ 
/*     */     public boolean isEmpty() {
/* 579 */       return this.forwardList.isEmpty();
/*     */     }
/*     */ 
/*     */     boolean isPartialView() {
/* 583 */       return this.forwardList.isPartialView();
/*     */     }
/*     */   }
/*     */ 
/*     */   class SubList extends ImmutableList<E>
/*     */   {
/*     */     final transient int offset;
/*     */     final transient int length;
/*     */ 
/*     */     SubList(int offset, int length)
/*     */     {
/* 397 */       this.offset = offset;
/* 398 */       this.length = length;
/*     */     }
/*     */ 
/*     */     public int size()
/*     */     {
/* 403 */       return this.length;
/*     */     }
/*     */ 
/*     */     public E get(int index)
/*     */     {
/* 408 */       Preconditions.checkElementIndex(index, this.length);
/* 409 */       return ImmutableList.this.get(index + this.offset);
/*     */     }
/*     */ 
/*     */     public ImmutableList<E> subList(int fromIndex, int toIndex)
/*     */     {
/* 414 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, this.length);
/* 415 */       return ImmutableList.this.subList(fromIndex + this.offset, toIndex + this.offset);
/*     */     }
/*     */ 
/*     */     boolean isPartialView()
/*     */     {
/* 420 */       return true;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.ImmutableList
 * JD-Core Version:    0.6.0
 */