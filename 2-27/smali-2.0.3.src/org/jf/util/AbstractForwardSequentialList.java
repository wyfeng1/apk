/*     */ package org.jf.util;
/*     */ 
/*     */ import java.util.AbstractSequentialList;
/*     */ import java.util.Iterator;
/*     */ import java.util.ListIterator;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ public abstract class AbstractForwardSequentialList<T> extends AbstractSequentialList<T>
/*     */ {
/*     */   private Iterator<T> iterator(int index)
/*     */   {
/*  44 */     if (index < 0) {
/*  45 */       throw new NoSuchElementException();
/*     */     }
/*     */ 
/*  48 */     Iterator it = iterator();
/*  49 */     for (int i = 0; i < index; i++) {
/*  50 */       it.next();
/*     */     }
/*  52 */     return it;
/*     */   }
/*     */   public abstract Iterator<T> iterator();
/*     */ 
/*     */   public ListIterator<T> listIterator(int initialIndex) {
/*     */     Iterator initialIterator;
/*     */     try {
/*  61 */       initialIterator = iterator(initialIndex);
/*     */     } catch (NoSuchElementException ex) {
/*  63 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */ 
/*  66 */     return new AbstractListIterator(initialIndex, initialIterator) {
/*  67 */       private int index = this.val$initialIndex - 1;
/*  68 */       private Iterator<T> forwardIterator = this.val$initialIterator;
/*     */ 
/*     */       private Iterator<T> getForwardIterator()
/*     */       {
/*  72 */         if (this.forwardIterator == null) {
/*     */           try {
/*  74 */             this.forwardIterator = AbstractForwardSequentialList.this.iterator(this.index + 1);
/*     */           } catch (IndexOutOfBoundsException ex) {
/*  76 */             throw new NoSuchElementException();
/*     */           }
/*     */         }
/*  79 */         return this.forwardIterator;
/*     */       }
/*     */ 
/*     */       public boolean hasNext() {
/*  83 */         return getForwardIterator().hasNext();
/*     */       }
/*     */ 
/*     */       public boolean hasPrevious() {
/*  87 */         return this.index >= 0;
/*     */       }
/*     */ 
/*     */       public T next() {
/*  91 */         Object ret = getForwardIterator().next();
/*  92 */         this.index += 1;
/*  93 */         return ret;
/*     */       }
/*     */ 
/*     */       public int nextIndex() {
/*  97 */         return this.index + 1;
/*     */       }
/*     */ 
/*     */       public T previous() {
/* 101 */         this.forwardIterator = null;
/*     */         try {
/* 103 */           return AbstractForwardSequentialList.this.iterator(this.index--).next(); } catch (IndexOutOfBoundsException ex) {
/*     */         }
/* 105 */         throw new NoSuchElementException();
/*     */       }
/*     */ 
/*     */       public int previousIndex()
/*     */       {
/* 110 */         return this.index;
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   public ListIterator<T> listIterator() {
/* 116 */     return listIterator(0);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.util.AbstractForwardSequentialList
 * JD-Core Version:    0.6.0
 */