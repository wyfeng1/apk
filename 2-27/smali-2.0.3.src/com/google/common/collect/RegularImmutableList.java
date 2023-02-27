/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ class RegularImmutableList<E> extends ImmutableList<E>
/*     */ {
/*     */   private final transient int offset;
/*     */   private final transient int size;
/*     */   private final transient Object[] array;
/*     */ 
/*     */   RegularImmutableList(Object[] array, int offset, int size)
/*     */   {
/*  39 */     this.offset = offset;
/*  40 */     this.size = size;
/*  41 */     this.array = array;
/*     */   }
/*     */ 
/*     */   RegularImmutableList(Object[] array) {
/*  45 */     this(array, 0, array.length);
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/*  50 */     return this.size;
/*     */   }
/*     */ 
/*     */   public boolean isEmpty() {
/*  54 */     return false;
/*     */   }
/*     */ 
/*     */   boolean isPartialView() {
/*  58 */     return (this.offset != 0) || (this.size != this.array.length);
/*     */   }
/*     */ 
/*     */   public Object[] toArray() {
/*  62 */     Object[] newArray = new Object[size()];
/*  63 */     System.arraycopy(this.array, this.offset, newArray, 0, this.size);
/*  64 */     return newArray;
/*     */   }
/*     */ 
/*     */   public <T> T[] toArray(T[] other) {
/*  68 */     if (other.length < this.size)
/*  69 */       other = ObjectArrays.newArray(other, this.size);
/*  70 */     else if (other.length > this.size) {
/*  71 */       other[this.size] = null;
/*     */     }
/*  73 */     System.arraycopy(this.array, this.offset, other, 0, this.size);
/*  74 */     return other;
/*     */   }
/*     */ 
/*     */   public E get(int index)
/*     */   {
/*  81 */     Preconditions.checkElementIndex(index, this.size);
/*  82 */     return this.array[(index + this.offset)];
/*     */   }
/*     */ 
/*     */   ImmutableList<E> subListUnchecked(int fromIndex, int toIndex)
/*     */   {
/*  87 */     return new RegularImmutableList(this.array, this.offset + fromIndex, toIndex - fromIndex);
/*     */   }
/*     */ 
/*     */   public UnmodifiableListIterator<E> listIterator(int index)
/*     */   {
/*  96 */     return Iterators.forArray(this.array, this.offset, this.size, index);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object object)
/*     */   {
/* 101 */     if (object == this) {
/* 102 */       return true;
/*     */     }
/* 104 */     if (!(object instanceof List)) {
/* 105 */       return false;
/*     */     }
/*     */ 
/* 108 */     List that = (List)object;
/* 109 */     if (size() != that.size()) {
/* 110 */       return false;
/*     */     }
/*     */ 
/* 113 */     int index = this.offset;
/*     */     Iterator i$;
/* 114 */     if ((object instanceof RegularImmutableList)) {
/* 115 */       RegularImmutableList other = (RegularImmutableList)object;
/* 116 */       for (int i = other.offset; i < other.offset + other.size; i++)
/* 117 */         if (!this.array[(index++)].equals(other.array[i]))
/* 118 */           return false;
/*     */     }
/*     */     else
/*     */     {
/* 122 */       for (i$ = that.iterator(); i$.hasNext(); ) { Object element = i$.next();
/* 123 */         if (!this.array[(index++)].equals(element)) {
/* 124 */           return false;
/*     */         }
/*     */       }
/*     */     }
/* 128 */     return true;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 132 */     StringBuilder sb = Collections2.newStringBuilderForCollection(size()).append('[').append(this.array[this.offset]);
/*     */ 
/* 134 */     for (int i = this.offset + 1; i < this.offset + this.size; i++) {
/* 135 */       sb.append(", ").append(this.array[i]);
/*     */     }
/* 137 */     return ']';
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.RegularImmutableList
 * JD-Core Version:    0.6.0
 */