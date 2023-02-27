/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.EnumSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class ImmutableSet<E> extends ImmutableCollection<E>
/*     */   implements Set<E>
/*     */ {
/* 236 */   private static final int CUTOFF = (int)Math.floor(751619276.79999995D);
/*     */ 
/*     */   public static <E> ImmutableSet<E> of()
/*     */   {
/*  84 */     return EmptyImmutableSet.INSTANCE;
/*     */   }
/*     */ 
/*     */   public static <E> ImmutableSet<E> of(E element)
/*     */   {
/*  94 */     return new SingletonImmutableSet(element);
/*     */   }
/*     */ 
/*     */   private static <E> ImmutableSet<E> construct(int n, Object[] elements)
/*     */   {
/* 179 */     switch (n) {
/*     */     case 0:
/* 181 */       return of();
/*     */     case 1:
/* 184 */       Object elem = elements[0];
/* 185 */       return of(elem);
/*     */     }
/*     */ 
/* 189 */     int tableSize = chooseTableSize(n);
/* 190 */     Object[] table = new Object[tableSize];
/* 191 */     int mask = tableSize - 1;
/* 192 */     int hashCode = 0;
/* 193 */     int uniques = 0;
/* 194 */     for (int i = 0; i < n; i++) {
/* 195 */       Object element = ObjectArrays.checkElementNotNull(elements[i], i);
/* 196 */       int hash = element.hashCode();
/* 197 */       for (int j = Hashing.smear(hash); ; j++) {
/* 198 */         int index = j & mask;
/* 199 */         Object value = table[index];
/* 200 */         if (value == null)
/*     */         {
/* 202 */           elements[(uniques++)] = element;
/* 203 */           table[index] = element;
/* 204 */           hashCode += hash;
/* 205 */           break;
/* 206 */         }if (value.equals(element)) {
/*     */           break;
/*     */         }
/*     */       }
/*     */     }
/* 211 */     Arrays.fill(elements, uniques, n, null);
/* 212 */     if (uniques == 1)
/*     */     {
/* 215 */       Object element = elements[0];
/* 216 */       return new SingletonImmutableSet(element, hashCode);
/* 217 */     }if (tableSize != chooseTableSize(uniques))
/*     */     {
/* 220 */       return construct(uniques, elements);
/*     */     }
/* 222 */     Object[] uniqueElements = uniques < elements.length ? ObjectArrays.arraysCopyOf(elements, uniques) : elements;
/*     */ 
/* 225 */     return new RegularImmutableSet(uniqueElements, hashCode, table, mask);
/*     */   }
/*     */ 
/*     */   static int chooseTableSize(int setSize)
/*     */   {
/* 249 */     if (setSize < CUTOFF)
/*     */     {
/* 251 */       int tableSize = Integer.highestOneBit(setSize - 1) << 1;
/* 252 */       while (tableSize * 0.7D < setSize) {
/* 253 */         tableSize <<= 1;
/*     */       }
/* 255 */       return tableSize;
/*     */     }
/*     */ 
/* 259 */     Preconditions.checkArgument(setSize < 1073741824, "collection too large");
/* 260 */     return 1073741824;
/*     */   }
/*     */ 
/*     */   public static <E> ImmutableSet<E> copyOf(Iterator<? extends E> elements)
/*     */   {
/* 316 */     if (!elements.hasNext()) {
/* 317 */       return of();
/*     */     }
/* 319 */     Object first = elements.next();
/* 320 */     if (!elements.hasNext()) {
/* 321 */       return of(first);
/*     */     }
/* 323 */     return new Builder().add(first).addAll(elements).build();
/*     */   }
/*     */ 
/*     */   public static <E> ImmutableSet<E> copyOf(Collection<? extends E> elements)
/*     */   {
/* 362 */     if (((elements instanceof ImmutableSet)) && (!(elements instanceof ImmutableSortedSet)))
/*     */     {
/* 365 */       ImmutableSet set = (ImmutableSet)elements;
/* 366 */       if (!set.isPartialView())
/* 367 */         return set;
/*     */     }
/* 369 */     else if ((elements instanceof EnumSet)) {
/* 370 */       EnumSet enumSet = EnumSet.copyOf((EnumSet)elements);
/*     */ 
/* 373 */       ImmutableSet result = ImmutableEnumSet.asImmutable(enumSet);
/* 374 */       return result;
/*     */     }
/* 376 */     return copyFromCollection(elements);
/*     */   }
/*     */ 
/*     */   private static <E> ImmutableSet<E> copyFromCollection(Collection<? extends E> collection)
/*     */   {
/* 381 */     Object[] elements = collection.toArray();
/* 382 */     switch (elements.length) {
/*     */     case 0:
/* 384 */       return of();
/*     */     case 1:
/* 387 */       Object onlyElement = elements[0];
/* 388 */       return of(onlyElement);
/*     */     }
/*     */ 
/* 392 */     return construct(elements.length, elements);
/*     */   }
/*     */ 
/*     */   boolean isHashCodeFast()
/*     */   {
/* 400 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object) {
/* 404 */     if (object == this) {
/* 405 */       return true;
/*     */     }
/* 407 */     if (((object instanceof ImmutableSet)) && (isHashCodeFast()) && (((ImmutableSet)object).isHashCodeFast()) && (hashCode() != object.hashCode()))
/*     */     {
/* 411 */       return false;
/*     */     }
/* 413 */     return Sets.equalsImpl(this, object);
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/* 417 */     return Sets.hashCodeImpl(this);
/*     */   }
/*     */ 
/*     */   public abstract UnmodifiableIterator<E> iterator();
/*     */ 
/*     */   public static class Builder<E> extends ImmutableCollection.Builder<E>
/*     */   {
/*     */     Object[] contents;
/*     */     int size;
/*     */ 
/*     */     public Builder()
/*     */     {
/* 535 */       this(4);
/*     */     }
/*     */ 
/*     */     Builder(int capacity) {
/* 539 */       Preconditions.checkArgument(capacity >= 0, "capacity must be >= 0 but was %s", new Object[] { Integer.valueOf(capacity) });
/* 540 */       this.contents = new Object[capacity];
/* 541 */       this.size = 0;
/*     */     }
/*     */ 
/*     */     Builder<E> ensureCapacity(int minCapacity)
/*     */     {
/* 549 */       if (this.contents.length < minCapacity) {
/* 550 */         this.contents = ObjectArrays.arraysCopyOf(this.contents, expandedCapacity(this.contents.length, minCapacity));
/*     */       }
/*     */ 
/* 553 */       return this;
/*     */     }
/*     */ 
/*     */     public Builder<E> add(E element)
/*     */     {
/* 566 */       ensureCapacity(this.size + 1);
/* 567 */       this.contents[(this.size++)] = Preconditions.checkNotNull(element);
/* 568 */       return this;
/*     */     }
/*     */ 
/*     */     public Builder<E> addAll(Iterator<? extends E> elements)
/*     */     {
/* 618 */       super.addAll(elements);
/* 619 */       return this;
/*     */     }
/*     */ 
/*     */     public ImmutableSet<E> build()
/*     */     {
/* 627 */       ImmutableSet result = ImmutableSet.access$000(this.size, this.contents);
/*     */ 
/* 630 */       this.size = result.size();
/* 631 */       return result;
/*     */     }
/*     */   }
/*     */ 
/*     */   static abstract class ArrayImmutableSet<E> extends ImmutableSet<E>
/*     */   {
/*     */     final transient Object[] elements;
/*     */ 
/*     */     ArrayImmutableSet(Object[] elements)
/*     */     {
/* 429 */       this.elements = elements;
/*     */     }
/*     */ 
/*     */     public int size()
/*     */     {
/* 434 */       return this.elements.length;
/*     */     }
/*     */ 
/*     */     public boolean isEmpty() {
/* 438 */       return false;
/*     */     }
/*     */ 
/*     */     public UnmodifiableIterator<E> iterator() {
/* 442 */       return asList().iterator();
/*     */     }
/*     */ 
/*     */     public Object[] toArray() {
/* 446 */       return asList().toArray();
/*     */     }
/*     */ 
/*     */     public <T> T[] toArray(T[] array) {
/* 450 */       return asList().toArray(array);
/*     */     }
/*     */ 
/*     */     public boolean containsAll(Collection<?> targets) {
/* 454 */       if (targets == this) {
/* 455 */         return true;
/*     */       }
/* 457 */       if (!(targets instanceof ArrayImmutableSet)) {
/* 458 */         return super.containsAll(targets);
/*     */       }
/* 460 */       if (targets.size() > size()) {
/* 461 */         return false;
/*     */       }
/* 463 */       for (Object target : ((ArrayImmutableSet)targets).elements) {
/* 464 */         if (!contains(target)) {
/* 465 */           return false;
/*     */         }
/*     */       }
/* 468 */       return true;
/*     */     }
/*     */ 
/*     */     boolean isPartialView() {
/* 472 */       return false;
/*     */     }
/*     */ 
/*     */     ImmutableList<E> createAsList() {
/* 476 */       return new RegularImmutableAsList(this, this.elements);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.ImmutableSet
 * JD-Core Version:    0.6.0
 */