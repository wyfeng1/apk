/*    */ package com.google.common.collect;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.Set;
/*    */ 
/*    */ final class EmptyImmutableSet extends ImmutableSet<Object>
/*    */ {
/* 33 */   static final EmptyImmutableSet INSTANCE = new EmptyImmutableSet();
/*    */ 
/*    */   public int size()
/*    */   {
/* 39 */     return 0;
/*    */   }
/*    */ 
/*    */   public boolean isEmpty() {
/* 43 */     return true;
/*    */   }
/*    */ 
/*    */   public boolean contains(Object target) {
/* 47 */     return false;
/*    */   }
/*    */ 
/*    */   public boolean containsAll(Collection<?> targets) {
/* 51 */     return targets.isEmpty();
/*    */   }
/*    */ 
/*    */   public UnmodifiableIterator<Object> iterator() {
/* 55 */     return Iterators.emptyIterator();
/*    */   }
/*    */ 
/*    */   boolean isPartialView() {
/* 59 */     return false;
/*    */   }
/*    */ 
/*    */   public Object[] toArray() {
/* 63 */     return ObjectArrays.EMPTY_ARRAY;
/*    */   }
/*    */ 
/*    */   public <T> T[] toArray(T[] a) {
/* 67 */     return asList().toArray(a);
/*    */   }
/*    */ 
/*    */   public ImmutableList<Object> asList()
/*    */   {
/* 72 */     return ImmutableList.of();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object object) {
/* 76 */     if ((object instanceof Set)) {
/* 77 */       Set that = (Set)object;
/* 78 */       return that.isEmpty();
/*    */     }
/* 80 */     return false;
/*    */   }
/*    */ 
/*    */   public final int hashCode() {
/* 84 */     return 0;
/*    */   }
/*    */ 
/*    */   boolean isHashCodeFast() {
/* 88 */     return true;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 92 */     return "[]";
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.EmptyImmutableSet
 * JD-Core Version:    0.6.0
 */