/*    */ package com.google.common.collect;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ final class ImmutableSortedAsList<E> extends RegularImmutableAsList<E>
/*    */   implements SortedIterable<E>
/*    */ {
/*    */   ImmutableSortedAsList(ImmutableSortedSet<E> backingSet, ImmutableList<E> backingList)
/*    */   {
/* 36 */     super(backingSet, backingList);
/*    */   }
/*    */ 
/*    */   ImmutableSortedSet<E> delegateCollection()
/*    */   {
/* 41 */     return (ImmutableSortedSet)super.delegateCollection();
/*    */   }
/*    */ 
/*    */   public Comparator<? super E> comparator() {
/* 45 */     return delegateCollection().comparator();
/*    */   }
/*    */ 
/*    */   public int indexOf(Object target)
/*    */   {
/* 53 */     int index = delegateCollection().indexOf(target);
/*    */ 
/* 60 */     return (index >= 0) && (get(index).equals(target)) ? index : -1;
/*    */   }
/*    */ 
/*    */   public int lastIndexOf(Object target)
/*    */   {
/* 65 */     return indexOf(target);
/*    */   }
/*    */ 
/*    */   public boolean contains(Object target)
/*    */   {
/* 71 */     return indexOf(target) >= 0;
/*    */   }
/*    */ 
/*    */   ImmutableList<E> subListUnchecked(int fromIndex, int toIndex)
/*    */   {
/* 82 */     return new RegularImmutableSortedSet(super.subListUnchecked(fromIndex, toIndex), comparator()).asList();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.ImmutableSortedAsList
 * JD-Core Version:    0.6.0
 */