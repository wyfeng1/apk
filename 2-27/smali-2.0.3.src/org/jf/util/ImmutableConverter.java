/*     */ package org.jf.util;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public abstract class ImmutableConverter<ImmutableItem, Item>
/*     */ {
/*     */   protected abstract boolean isImmutable(Item paramItem);
/*     */ 
/*     */   protected abstract ImmutableItem makeImmutable(Item paramItem);
/*     */ 
/*     */   public ImmutableList<ImmutableItem> toList(Iterable<? extends Item> iterable)
/*     */   {
/*  50 */     if (iterable == null) {
/*  51 */       return ImmutableList.of();
/*     */     }
/*     */ 
/*  54 */     boolean needsCopy = false;
/*     */     Iterator i$;
/*  55 */     if ((iterable instanceof ImmutableList))
/*  56 */       for (i$ = iterable.iterator(); i$.hasNext(); ) { Object element = i$.next();
/*  57 */         if (!isImmutable(element)) {
/*  58 */           needsCopy = true;
/*  59 */           break;
/*     */         }
/*     */       }
/*     */     else {
/*  63 */       needsCopy = true;
/*     */     }
/*     */ 
/*  66 */     if (!needsCopy) {
/*  67 */       return (ImmutableList)iterable;
/*     */     }
/*     */ 
/*  70 */     Iterator iter = iterable.iterator();
/*     */ 
/*  72 */     return ImmutableList.copyOf(new Iterator(iter) {
/*  73 */       public boolean hasNext() { return this.val$iter.hasNext(); } 
/*  74 */       public ImmutableItem next() { return ImmutableConverter.this.makeImmutable(this.val$iter.next()); } 
/*  75 */       public void remove() { this.val$iter.remove(); }
/*     */     });
/*     */   }
/*     */ 
/*     */   public ImmutableSet<ImmutableItem> toSet(Iterable<? extends Item> iterable) {
/*  81 */     if (iterable == null) {
/*  82 */       return ImmutableSet.of();
/*     */     }
/*     */ 
/*  85 */     boolean needsCopy = false;
/*     */     Iterator i$;
/*  86 */     if ((iterable instanceof ImmutableSet))
/*  87 */       for (i$ = iterable.iterator(); i$.hasNext(); ) { Object element = i$.next();
/*  88 */         if (!isImmutable(element)) {
/*  89 */           needsCopy = true;
/*  90 */           break;
/*     */         }
/*     */       }
/*     */     else {
/*  94 */       needsCopy = true;
/*     */     }
/*     */ 
/*  97 */     if (!needsCopy) {
/*  98 */       return (ImmutableSet)iterable;
/*     */     }
/*     */ 
/* 101 */     Iterator iter = iterable.iterator();
/*     */ 
/* 103 */     return ImmutableSet.copyOf(new Iterator(iter) {
/* 104 */       public boolean hasNext() { return this.val$iter.hasNext(); } 
/* 105 */       public ImmutableItem next() { return ImmutableConverter.this.makeImmutable(this.val$iter.next()); } 
/* 106 */       public void remove() { this.val$iter.remove();
/*     */       }
/*     */     });
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.util.ImmutableConverter
 * JD-Core Version:    0.6.0
 */