/*    */ package com.google.common.collect;
/*    */ 
/*    */ class RegularImmutableAsList<E> extends ImmutableAsList<E>
/*    */ {
/*    */   private final ImmutableCollection<E> delegate;
/*    */   private final ImmutableList<? extends E> delegateList;
/*    */ 
/*    */   RegularImmutableAsList(ImmutableCollection<E> delegate, ImmutableList<? extends E> delegateList)
/*    */   {
/* 34 */     this.delegate = delegate;
/* 35 */     this.delegateList = delegateList;
/*    */   }
/*    */ 
/*    */   RegularImmutableAsList(ImmutableCollection<E> delegate, Object[] array) {
/* 39 */     this(delegate, ImmutableList.asImmutableList(array));
/*    */   }
/*    */ 
/*    */   ImmutableCollection<E> delegateCollection()
/*    */   {
/* 44 */     return this.delegate;
/*    */   }
/*    */ 
/*    */   public UnmodifiableListIterator<E> listIterator(int index)
/*    */   {
/* 54 */     return this.delegateList.listIterator(index);
/*    */   }
/*    */ 
/*    */   public Object[] toArray()
/*    */   {
/* 59 */     return this.delegateList.toArray();
/*    */   }
/*    */ 
/*    */   public <T> T[] toArray(T[] other)
/*    */   {
/* 64 */     return this.delegateList.toArray(other);
/*    */   }
/*    */ 
/*    */   public int indexOf(Object object)
/*    */   {
/* 69 */     return this.delegateList.indexOf(object);
/*    */   }
/*    */ 
/*    */   public int lastIndexOf(Object object)
/*    */   {
/* 74 */     return this.delegateList.lastIndexOf(object);
/*    */   }
/*    */ 
/*    */   public boolean equals(Object obj)
/*    */   {
/* 79 */     return this.delegateList.equals(obj);
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 84 */     return this.delegateList.hashCode();
/*    */   }
/*    */ 
/*    */   public E get(int index)
/*    */   {
/* 89 */     return this.delegateList.get(index);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.RegularImmutableAsList
 * JD-Core Version:    0.6.0
 */