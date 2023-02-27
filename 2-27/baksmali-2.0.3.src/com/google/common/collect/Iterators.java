/*      */ package com.google.common.collect;
/*      */ 
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.base.Objects;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.base.Predicate;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.ListIterator;
/*      */ import java.util.NoSuchElementException;
/*      */ 
/*      */ public final class Iterators
/*      */ {
/*   69 */   static final UnmodifiableListIterator<Object> EMPTY_LIST_ITERATOR = new UnmodifiableListIterator()
/*      */   {
/*      */     public boolean hasNext()
/*      */     {
/*   73 */       return false;
/*      */     }
/*      */ 
/*      */     public Object next() {
/*   77 */       throw new NoSuchElementException();
/*      */     }
/*      */ 
/*      */     public boolean hasPrevious() {
/*   81 */       return false;
/*      */     }
/*      */ 
/*      */     public Object previous() {
/*   85 */       throw new NoSuchElementException();
/*      */     }
/*      */ 
/*      */     public int nextIndex() {
/*   89 */       return 0;
/*      */     }
/*      */ 
/*      */     public int previousIndex() {
/*   93 */       return -1;
/*      */     }
/*   69 */   };
/*      */ 
/*  119 */   private static final Iterator<Object> EMPTY_MODIFIABLE_ITERATOR = new Iterator()
/*      */   {
/*      */     public boolean hasNext() {
/*  122 */       return false;
/*      */     }
/*      */ 
/*      */     public Object next() {
/*  126 */       throw new NoSuchElementException();
/*      */     }
/*      */ 
/*      */     public void remove() {
/*  130 */       throw new IllegalStateException();
/*      */     }
/*  119 */   };
/*      */ 
/*      */   public static <T> UnmodifiableIterator<T> emptyIterator()
/*      */   {
/*  104 */     return emptyListIterator();
/*      */   }
/*      */ 
/*      */   static <T> UnmodifiableListIterator<T> emptyListIterator()
/*      */   {
/*  116 */     return EMPTY_LIST_ITERATOR;
/*      */   }
/*      */ 
/*      */   public static <T> UnmodifiableIterator<T> unmodifiableIterator(Iterator<T> iterator)
/*      */   {
/*  149 */     Preconditions.checkNotNull(iterator);
/*  150 */     if ((iterator instanceof UnmodifiableIterator)) {
/*  151 */       return (UnmodifiableIterator)iterator;
/*      */     }
/*  153 */     return new UnmodifiableIterator(iterator)
/*      */     {
/*      */       public boolean hasNext() {
/*  156 */         return this.val$iterator.hasNext();
/*      */       }
/*      */ 
/*      */       public T next() {
/*  160 */         return this.val$iterator.next();
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static boolean contains(Iterator<?> iterator, Object element)
/*      */   {
/*  195 */     if (element == null) {
/*      */       do if (!iterator.hasNext())
/*      */           break; while (iterator.next() != null);
/*  198 */       return true;
/*      */     }
/*      */ 
/*  202 */     while (iterator.hasNext()) {
/*  203 */       if (element.equals(iterator.next())) {
/*  204 */         return true;
/*      */       }
/*      */     }
/*      */ 
/*  208 */     return false;
/*      */   }
/*      */ 
/*      */   public static boolean elementsEqual(Iterator<?> iterator1, Iterator<?> iterator2)
/*      */   {
/*  291 */     while (iterator1.hasNext()) {
/*  292 */       if (!iterator2.hasNext()) {
/*  293 */         return false;
/*      */       }
/*  295 */       Object o1 = iterator1.next();
/*  296 */       Object o2 = iterator2.next();
/*  297 */       if (!Objects.equal(o1, o2)) {
/*  298 */         return false;
/*      */       }
/*      */     }
/*  301 */     return !iterator2.hasNext();
/*      */   }
/*      */ 
/*      */   public static String toString(Iterator<?> iterator)
/*      */   {
/*  310 */     return ']';
/*      */   }
/*      */ 
/*      */   public static <T> T getOnlyElement(Iterator<T> iterator)
/*      */   {
/*  325 */     Object first = iterator.next();
/*  326 */     if (!iterator.hasNext()) {
/*  327 */       return first;
/*      */     }
/*      */ 
/*  330 */     StringBuilder sb = new StringBuilder();
/*  331 */     sb.append("expected one element but was: <" + first);
/*  332 */     for (int i = 0; (i < 4) && (iterator.hasNext()); i++) {
/*  333 */       sb.append(", " + iterator.next());
/*      */     }
/*  335 */     if (iterator.hasNext()) {
/*  336 */       sb.append(", ...");
/*      */     }
/*  338 */     sb.append('>');
/*      */ 
/*  340 */     throw new IllegalArgumentException(sb.toString());
/*      */   }
/*      */ 
/*      */   public static <T> boolean addAll(Collection<T> addTo, Iterator<? extends T> iterator)
/*      */   {
/*  381 */     Preconditions.checkNotNull(addTo);
/*  382 */     boolean wasModified = false;
/*  383 */     while (iterator.hasNext()) {
/*  384 */       wasModified |= addTo.add(iterator.next());
/*      */     }
/*  386 */     return wasModified;
/*      */   }
/*      */ 
/*      */   public static <T> Iterator<T> concat(Iterator<? extends T> a, Iterator<? extends T> b)
/*      */   {
/*  492 */     Preconditions.checkNotNull(a);
/*  493 */     Preconditions.checkNotNull(b);
/*  494 */     return concat(Arrays.asList(new Iterator[] { a, b }).iterator());
/*      */   }
/*      */ 
/*      */   public static <T> Iterator<T> concat(Iterator<? extends Iterator<? extends T>> inputs)
/*      */   {
/*  580 */     Preconditions.checkNotNull(inputs);
/*  581 */     return new Iterator(inputs) {
/*  582 */       Iterator<? extends T> current = Iterators.emptyIterator();
/*      */       Iterator<? extends T> removeFrom;
/*      */ 
/*      */       public boolean hasNext()
/*      */       {
/*      */         boolean currentHasNext;
/*  596 */         while ((!(currentHasNext = ((Iterator)Preconditions.checkNotNull(this.current)).hasNext())) && (this.val$inputs.hasNext())) {
/*  597 */           this.current = ((Iterator)this.val$inputs.next());
/*      */         }
/*  599 */         return currentHasNext;
/*      */       }
/*      */ 
/*      */       public T next() {
/*  603 */         if (!hasNext()) {
/*  604 */           throw new NoSuchElementException();
/*      */         }
/*  606 */         this.removeFrom = this.current;
/*  607 */         return this.current.next();
/*      */       }
/*      */ 
/*      */       public void remove() {
/*  611 */         Preconditions.checkState(this.removeFrom != null, "no calls to next() since last call to remove()");
/*      */ 
/*  613 */         this.removeFrom.remove();
/*  614 */         this.removeFrom = null;
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <T> UnmodifiableIterator<T> filter(Iterator<T> unfiltered, Predicate<? super T> predicate)
/*      */   {
/*  696 */     Preconditions.checkNotNull(unfiltered);
/*  697 */     Preconditions.checkNotNull(predicate);
/*  698 */     return new AbstractIterator(unfiltered, predicate) {
/*      */       protected T computeNext() {
/*  700 */         while (this.val$unfiltered.hasNext()) {
/*  701 */           Object element = this.val$unfiltered.next();
/*  702 */           if (this.val$predicate.apply(element)) {
/*  703 */             return element;
/*      */           }
/*      */         }
/*  706 */         return endOfData();
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <F, T> Iterator<T> transform(Iterator<F> fromIterator, Function<? super F, ? extends T> function)
/*      */   {
/*  856 */     Preconditions.checkNotNull(function);
/*  857 */     return new TransformedIterator(fromIterator, function)
/*      */     {
/*      */       T transform(F from) {
/*  860 */         return this.val$function.apply(from);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <T> T getNext(Iterator<? extends T> iterator, T defaultValue)
/*      */   {
/*  935 */     return iterator.hasNext() ? iterator.next() : defaultValue;
/*      */   }
/*      */ 
/*      */   public static <T> UnmodifiableIterator<T> forArray(T[] array)
/*      */   {
/* 1097 */     Preconditions.checkNotNull(array);
/* 1098 */     return new AbstractIndexedListIterator(array.length, array) {
/*      */       protected T get(int index) {
/* 1100 */         return this.val$array[index];
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   static <T> UnmodifiableListIterator<T> forArray(T[] array, int offset, int length, int index)
/*      */   {
/* 1114 */     Preconditions.checkArgument(length >= 0);
/* 1115 */     int end = offset + length;
/*      */ 
/* 1118 */     Preconditions.checkPositionIndexes(offset, end, array.length);
/*      */ 
/* 1125 */     return new AbstractIndexedListIterator(length, index, array, offset) {
/*      */       protected T get(int index) {
/* 1127 */         return this.val$array[(this.val$offset + index)];
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   public static <T> UnmodifiableIterator<T> singletonIterator(T value)
/*      */   {
/* 1140 */     return new UnmodifiableIterator(value) {
/*      */       boolean done;
/*      */ 
/* 1144 */       public boolean hasNext() { return !this.done; }
/*      */ 
/*      */       public T next()
/*      */       {
/* 1148 */         if (this.done) {
/* 1149 */           throw new NoSuchElementException();
/*      */         }
/* 1151 */         this.done = true;
/* 1152 */         return this.val$value;
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   static <T> ListIterator<T> cast(Iterator<T> iterator)
/*      */   {
/* 1395 */     return (ListIterator)iterator;
/*      */   }
/*      */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.Iterators
 * JD-Core Version:    0.6.0
 */