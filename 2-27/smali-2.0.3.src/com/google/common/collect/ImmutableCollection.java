/*     */ package com.google.common.collect;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public abstract class ImmutableCollection<E>
/*     */   implements Serializable, Collection<E>
/*     */ {
/*  45 */   static final ImmutableCollection<Object> EMPTY_IMMUTABLE_COLLECTION = new EmptyImmutableCollection(null);
/*     */   private transient ImmutableList<E> asList;
/*     */ 
/*     */   public abstract UnmodifiableIterator<E> iterator();
/*     */ 
/*     */   public Object[] toArray()
/*     */   {
/*  58 */     return ObjectArrays.toArrayImpl(this);
/*     */   }
/*     */ 
/*     */   public <T> T[] toArray(T[] other)
/*     */   {
/*  63 */     return ObjectArrays.toArrayImpl(this, other);
/*     */   }
/*     */ 
/*     */   public boolean contains(Object object)
/*     */   {
/*  68 */     return (object != null) && (Iterators.contains(iterator(), object));
/*     */   }
/*     */ 
/*     */   public boolean containsAll(Collection<?> targets)
/*     */   {
/*  73 */     return Collections2.containsAllImpl(this, targets);
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/*  78 */     return size() == 0;
/*     */   }
/*     */ 
/*     */   public String toString() {
/*  82 */     return Collections2.toStringImpl(this);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public final boolean add(E e)
/*     */   {
/*  94 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public final boolean remove(Object object)
/*     */   {
/* 106 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public final boolean addAll(Collection<? extends E> newElements)
/*     */   {
/* 118 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public final boolean removeAll(Collection<?> oldElements)
/*     */   {
/* 130 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public final boolean retainAll(Collection<?> elementsToKeep)
/*     */   {
/* 142 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public final void clear()
/*     */   {
/* 154 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   public ImmutableList<E> asList()
/*     */   {
/* 169 */     ImmutableList list = this.asList;
/* 170 */     return list == null ? (this.asList = createAsList()) : list;
/*     */   }
/*     */ 
/*     */   ImmutableList<E> createAsList() {
/* 174 */     switch (size()) {
/*     */     case 0:
/* 176 */       return ImmutableList.of();
/*     */     case 1:
/* 178 */       return ImmutableList.of(iterator().next());
/*     */     }
/* 180 */     return new RegularImmutableAsList(this, toArray());
/*     */   }
/*     */ 
/*     */   abstract boolean isPartialView();
/*     */ 
/*     */   public static abstract class Builder<E>
/*     */   {
/*     */     static int expandedCapacity(int oldCapacity, int minCapacity)
/*     */     {
/* 292 */       if (minCapacity < 0) {
/* 293 */         throw new AssertionError("cannot store more than MAX_VALUE elements");
/*     */       }
/*     */ 
/* 296 */       int newCapacity = oldCapacity + (oldCapacity >> 1) + 1;
/* 297 */       if (newCapacity < minCapacity) {
/* 298 */         newCapacity = Integer.highestOneBit(minCapacity - 1) << 1;
/*     */       }
/* 300 */       if (newCapacity < 0) {
/* 301 */         newCapacity = 2147483647;
/*     */       }
/*     */ 
/* 304 */       return newCapacity;
/*     */     }
/*     */ 
/*     */     public abstract Builder<E> add(E paramE);
/*     */ 
/*     */     public Builder<E> addAll(Iterator<? extends E> elements)
/*     */     {
/* 373 */       while (elements.hasNext()) {
/* 374 */         add(elements.next());
/*     */       }
/* 376 */       return this;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class EmptyImmutableCollection extends ImmutableCollection<Object>
/*     */   {
/* 205 */     private static final Object[] EMPTY_ARRAY = new Object[0];
/*     */ 
/*     */     public int size()
/*     */     {
/* 190 */       return 0;
/*     */     }
/*     */ 
/*     */     public boolean isEmpty() {
/* 194 */       return true;
/*     */     }
/*     */ 
/*     */     public boolean contains(Object object) {
/* 198 */       return false;
/*     */     }
/*     */ 
/*     */     public UnmodifiableIterator<Object> iterator() {
/* 202 */       return Iterators.EMPTY_LIST_ITERATOR;
/*     */     }
/*     */ 
/*     */     public Object[] toArray()
/*     */     {
/* 208 */       return EMPTY_ARRAY;
/*     */     }
/*     */ 
/*     */     public <T> T[] toArray(T[] array) {
/* 212 */       if (array.length > 0) {
/* 213 */         array[0] = null;
/*     */       }
/* 215 */       return array;
/*     */     }
/*     */ 
/*     */     ImmutableList<Object> createAsList() {
/* 219 */       return ImmutableList.of();
/*     */     }
/*     */ 
/*     */     boolean isPartialView() {
/* 223 */       return false;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.ImmutableCollection
 * JD-Core Version:    0.6.0
 */