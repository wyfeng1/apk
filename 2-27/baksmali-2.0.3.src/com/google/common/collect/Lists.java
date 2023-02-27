/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.base.Objects;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.primitives.Ints;
/*      */ import java.io.Serializable;
/*      */ import java.util.AbstractList;
/*      */ import java.util.AbstractSequentialList;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.RandomAccess;
/*      */ 
/*      */ public final class Lists
/*      */ {
/*      */   public static <E> ArrayList<E> newArrayList()
/*      */   {
/*   80 */     return new ArrayList();
/*      */   }
/*      */ 
/*      */   public static <E> ArrayList<E> newArrayList(E[] elements)
/*      */   {
/*   96 */     Preconditions.checkNotNull(elements);
/*      */ 
/*   98 */     int capacity = computeArrayListCapacity(elements.length);
/*   99 */     ArrayList list = new ArrayList(capacity);
/*  100 */     Collections.addAll(list, elements);
/*  101 */     return list;
/*      */   }
/*      */ 
/*      */   static int computeArrayListCapacity(int arraySize) {
/*  105 */     Preconditions.checkArgument(arraySize >= 0);
/*      */ 
/*  108 */     return Ints.saturatedCast(5L + arraySize + arraySize / 10);
/*      */   }
/*      */ 
/*      */   public static <E> ArrayList<E> newArrayList(Iterable<? extends E> elements)
/*      */   {
/*  123 */     Preconditions.checkNotNull(elements);
/*      */ 
/*  125 */     return (elements instanceof Collection) ? new ArrayList(Collections2.cast(elements)) : newArrayList(elements.iterator());
/*      */   }
/*      */ 
/*      */   public static <E> ArrayList<E> newArrayList(Iterator<? extends E> elements)
/*      */   {
/*  142 */     Preconditions.checkNotNull(elements);
/*  143 */     ArrayList list = newArrayList();
/*  144 */     while (elements.hasNext()) {
/*  145 */       list.add(elements.next());
/*      */     }
/*  147 */     return list;
/*      */   }
/*      */ 
/*      */   public static <E> ArrayList<E> newArrayListWithCapacity(int initialArraySize)
/*      */   {
/*  173 */     Preconditions.checkArgument(initialArraySize >= 0);
/*  174 */     return new ArrayList(initialArraySize);
/*      */   }
/*      */ 
/*      */   public static <E> LinkedList<E> newLinkedList()
/*      */   {
/*  210 */     return new LinkedList();
/*      */   }
/*      */ 
/*      */   public static <F, T> List<T> transform(List<F> fromList, Function<? super F, ? extends T> function)
/*      */   {
/*  510 */     return (fromList instanceof RandomAccess) ? new TransformingRandomAccessList(fromList, function) : new TransformingSequentialList(fromList, function);
/*      */   }
/*      */ 
/*      */   public static <T> List<T> reverse(List<T> list)
/*      */   {
/*  844 */     if ((list instanceof ReverseList))
/*  845 */       return ((ReverseList)list).getForwardList();
/*  846 */     if ((list instanceof RandomAccess)) {
/*  847 */       return new RandomAccessReverseList(list);
/*      */     }
/*  849 */     return new ReverseList(list);
/*      */   }
/*      */ 
/*      */   static int hashCodeImpl(List<?> list)
/*      */   {
/* 1007 */     int hashCode = 1;
/* 1008 */     for (Iterator i$ = list.iterator(); i$.hasNext(); ) { Object o = i$.next();
/* 1009 */       hashCode = 31 * hashCode + (o == null ? 0 : o.hashCode());
/*      */ 
/* 1011 */       hashCode = hashCode ^ 0xFFFFFFFF ^ 0xFFFFFFFF;
/*      */     }
/*      */ 
/* 1014 */     return hashCode;
/*      */   }
/*      */ 
/*      */   static boolean equalsImpl(List<?> list, Object object)
/*      */   {
/* 1021 */     if (object == Preconditions.checkNotNull(list)) {
/* 1022 */       return true;
/*      */     }
/* 1024 */     if (!(object instanceof List)) {
/* 1025 */       return false;
/*      */     }
/*      */ 
/* 1028 */     List o = (List)object;
/*      */ 
/* 1030 */     return (list.size() == o.size()) && (Iterators.elementsEqual(list.iterator(), o.iterator()));
/*      */   }
/*      */ 
/*      */   static int indexOfImpl(List<?> list, Object element)
/*      */   {
/* 1052 */     ListIterator listIterator = list.listIterator();
/* 1053 */     while (listIterator.hasNext()) {
/* 1054 */       if (Objects.equal(element, listIterator.next())) {
/* 1055 */         return listIterator.previousIndex();
/*      */       }
/*      */     }
/* 1058 */     return -1;
/*      */   }
/*      */ 
/*      */   static int lastIndexOfImpl(List<?> list, Object element)
/*      */   {
/* 1065 */     ListIterator listIterator = list.listIterator(list.size());
/* 1066 */     while (listIterator.hasPrevious()) {
/* 1067 */       if (Objects.equal(element, listIterator.previous())) {
/* 1068 */         return listIterator.nextIndex();
/*      */       }
/*      */     }
/* 1071 */     return -1;
/*      */   }
/*      */ 
/*      */   private static class RandomAccessReverseList<T> extends Lists.ReverseList<T>
/*      */     implements RandomAccess
/*      */   {
/*      */     RandomAccessReverseList(List<T> forwardList)
/*      */     {
/*  999 */       super();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ReverseList<T> extends AbstractList<T>
/*      */   {
/*      */     private final List<T> forwardList;
/*      */ 
/*      */     ReverseList(List<T> forwardList)
/*      */     {
/*  857 */       this.forwardList = ((List)Preconditions.checkNotNull(forwardList));
/*      */     }
/*      */ 
/*      */     List<T> getForwardList() {
/*  861 */       return this.forwardList;
/*      */     }
/*      */ 
/*      */     private int reverseIndex(int index) {
/*  865 */       int size = size();
/*  866 */       Preconditions.checkElementIndex(index, size);
/*  867 */       return size - 1 - index;
/*      */     }
/*      */ 
/*      */     private int reversePosition(int index) {
/*  871 */       int size = size();
/*  872 */       Preconditions.checkPositionIndex(index, size);
/*  873 */       return size - index;
/*      */     }
/*      */ 
/*      */     public void add(int index, T element) {
/*  877 */       this.forwardList.add(reversePosition(index), element);
/*      */     }
/*      */ 
/*      */     public void clear() {
/*  881 */       this.forwardList.clear();
/*      */     }
/*      */ 
/*      */     public T remove(int index) {
/*  885 */       return this.forwardList.remove(reverseIndex(index));
/*      */     }
/*      */ 
/*      */     protected void removeRange(int fromIndex, int toIndex) {
/*  889 */       subList(fromIndex, toIndex).clear();
/*      */     }
/*      */ 
/*      */     public T set(int index, T element) {
/*  893 */       return this.forwardList.set(reverseIndex(index), element);
/*      */     }
/*      */ 
/*      */     public T get(int index) {
/*  897 */       return this.forwardList.get(reverseIndex(index));
/*      */     }
/*      */ 
/*      */     public boolean isEmpty() {
/*  901 */       return this.forwardList.isEmpty();
/*      */     }
/*      */ 
/*      */     public int size() {
/*  905 */       return this.forwardList.size();
/*      */     }
/*      */ 
/*      */     public boolean contains(Object o) {
/*  909 */       return this.forwardList.contains(o);
/*      */     }
/*      */ 
/*      */     public boolean containsAll(Collection<?> c) {
/*  913 */       return this.forwardList.containsAll(c);
/*      */     }
/*      */ 
/*      */     public List<T> subList(int fromIndex, int toIndex) {
/*  917 */       Preconditions.checkPositionIndexes(fromIndex, toIndex, size());
/*  918 */       return Lists.reverse(this.forwardList.subList(reversePosition(toIndex), reversePosition(fromIndex)));
/*      */     }
/*      */ 
/*      */     public int indexOf(Object o)
/*      */     {
/*  923 */       int index = this.forwardList.lastIndexOf(o);
/*  924 */       return index >= 0 ? reverseIndex(index) : -1;
/*      */     }
/*      */ 
/*      */     public int lastIndexOf(Object o) {
/*  928 */       int index = this.forwardList.indexOf(o);
/*  929 */       return index >= 0 ? reverseIndex(index) : -1;
/*      */     }
/*      */ 
/*      */     public Iterator<T> iterator() {
/*  933 */       return listIterator();
/*      */     }
/*      */ 
/*      */     public ListIterator<T> listIterator(int index) {
/*  937 */       int start = reversePosition(index);
/*  938 */       ListIterator forwardIterator = this.forwardList.listIterator(start);
/*  939 */       return new ListIterator(forwardIterator) {
/*      */         boolean canRemove;
/*      */         boolean canSet;
/*      */ 
/*  945 */         public void add(T e) { this.val$forwardIterator.add(e);
/*  946 */           this.val$forwardIterator.previous();
/*  947 */           this.canSet = (this.canRemove = 0); }
/*      */ 
/*      */         public boolean hasNext()
/*      */         {
/*  951 */           return this.val$forwardIterator.hasPrevious();
/*      */         }
/*      */ 
/*      */         public boolean hasPrevious() {
/*  955 */           return this.val$forwardIterator.hasNext();
/*      */         }
/*      */ 
/*      */         public T next() {
/*  959 */           if (!hasNext()) {
/*  960 */             throw new NoSuchElementException();
/*      */           }
/*  962 */           this.canSet = (this.canRemove = 1);
/*  963 */           return this.val$forwardIterator.previous();
/*      */         }
/*      */ 
/*      */         public int nextIndex() {
/*  967 */           return Lists.ReverseList.this.reversePosition(this.val$forwardIterator.nextIndex());
/*      */         }
/*      */ 
/*      */         public T previous() {
/*  971 */           if (!hasPrevious()) {
/*  972 */             throw new NoSuchElementException();
/*      */           }
/*  974 */           this.canSet = (this.canRemove = 1);
/*  975 */           return this.val$forwardIterator.next();
/*      */         }
/*      */ 
/*      */         public int previousIndex() {
/*  979 */           return nextIndex() - 1;
/*      */         }
/*      */ 
/*      */         public void remove() {
/*  983 */           Preconditions.checkState(this.canRemove);
/*  984 */           this.val$forwardIterator.remove();
/*  985 */           this.canRemove = (this.canSet = 0);
/*      */         }
/*      */ 
/*      */         public void set(T e) {
/*  989 */           Preconditions.checkState(this.canSet);
/*  990 */           this.val$forwardIterator.set(e);
/*      */         }
/*      */       };
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class TransformingRandomAccessList<F, T> extends AbstractList<T>
/*      */     implements Serializable, RandomAccess
/*      */   {
/*      */     final List<F> fromList;
/*      */     final Function<? super F, ? extends T> function;
/*      */ 
/*      */     TransformingRandomAccessList(List<F> fromList, Function<? super F, ? extends T> function)
/*      */     {
/*  568 */       this.fromList = ((List)Preconditions.checkNotNull(fromList));
/*  569 */       this.function = ((Function)Preconditions.checkNotNull(function));
/*      */     }
/*      */     public void clear() {
/*  572 */       this.fromList.clear();
/*      */     }
/*      */     public T get(int index) {
/*  575 */       return this.function.apply(this.fromList.get(index));
/*      */     }
/*      */     public boolean isEmpty() {
/*  578 */       return this.fromList.isEmpty();
/*      */     }
/*      */     public T remove(int index) {
/*  581 */       return this.function.apply(this.fromList.remove(index));
/*      */     }
/*      */     public int size() {
/*  584 */       return this.fromList.size();
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class TransformingSequentialList<F, T> extends AbstractSequentialList<T>
/*      */     implements Serializable
/*      */   {
/*      */     final List<F> fromList;
/*      */     final Function<? super F, ? extends T> function;
/*      */ 
/*      */     TransformingSequentialList(List<F> fromList, Function<? super F, ? extends T> function)
/*      */     {
/*  527 */       this.fromList = ((List)Preconditions.checkNotNull(fromList));
/*  528 */       this.function = ((Function)Preconditions.checkNotNull(function));
/*      */     }
/*      */ 
/*      */     public void clear()
/*      */     {
/*  536 */       this.fromList.clear();
/*      */     }
/*      */     public int size() {
/*  539 */       return this.fromList.size();
/*      */     }
/*      */     public ListIterator<T> listIterator(int index) {
/*  542 */       return new TransformedListIterator(this.fromList.listIterator(index))
/*      */       {
/*      */         T transform(F from) {
/*  545 */           return Lists.TransformingSequentialList.this.function.apply(from);
/*      */         }
/*      */       };
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.Lists
 * JD-Core Version:    0.6.0
 */