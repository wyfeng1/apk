/*     */ package com.google.common.collect;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.EnumSet;
/*     */ 
/*     */ final class ImmutableEnumSet<E extends Enum<E>> extends ImmutableSet<E>
/*     */ {
/*     */   private final transient EnumSet<E> delegate;
/*     */   private transient int hashCode;
/*     */ 
/*     */   static <E extends Enum<E>> ImmutableSet<E> asImmutable(EnumSet<E> set)
/*     */   {
/*  35 */     switch (set.size()) {
/*     */     case 0:
/*  37 */       return ImmutableSet.of();
/*     */     case 1:
/*  39 */       return ImmutableSet.of(Iterables.getOnlyElement(set));
/*     */     }
/*  41 */     return new ImmutableEnumSet(set);
/*     */   }
/*     */ 
/*     */   private ImmutableEnumSet(EnumSet<E> delegate)
/*     */   {
/*  56 */     this.delegate = delegate;
/*     */   }
/*     */ 
/*     */   boolean isPartialView() {
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */   public UnmodifiableIterator<E> iterator() {
/*  64 */     return Iterators.unmodifiableIterator(this.delegate.iterator());
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/*  69 */     return this.delegate.size();
/*     */   }
/*     */ 
/*     */   public boolean contains(Object object) {
/*  73 */     return this.delegate.contains(object);
/*     */   }
/*     */ 
/*     */   public boolean containsAll(Collection<?> collection) {
/*  77 */     return this.delegate.containsAll(collection);
/*     */   }
/*     */ 
/*     */   public boolean isEmpty() {
/*  81 */     return this.delegate.isEmpty();
/*     */   }
/*     */ 
/*     */   public Object[] toArray() {
/*  85 */     return this.delegate.toArray();
/*     */   }
/*     */ 
/*     */   public <T> T[] toArray(T[] array) {
/*  89 */     return this.delegate.toArray(array);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object) {
/*  93 */     return (object == this) || (this.delegate.equals(object));
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  99 */     int result = this.hashCode;
/* 100 */     return result == 0 ? (this.hashCode = this.delegate.hashCode()) : result;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 104 */     return this.delegate.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.ImmutableEnumSet
 * JD-Core Version:    0.6.0
 */