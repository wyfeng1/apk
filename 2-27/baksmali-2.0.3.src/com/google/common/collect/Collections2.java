/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public final class Collections2
/*     */ {
/* 376 */   static final Joiner STANDARD_JOINER = Joiner.on(", ").useForNull("null");
/*     */ 
/*     */   static boolean containsAllImpl(Collection<?> self, Collection<?> c)
/*     */   {
/* 337 */     Preconditions.checkNotNull(self);
/* 338 */     for (Iterator i$ = c.iterator(); i$.hasNext(); ) { Object o = i$.next();
/* 339 */       if (!self.contains(o)) {
/* 340 */         return false;
/*     */       }
/*     */     }
/* 343 */     return true;
/*     */   }
/*     */ 
/*     */   static String toStringImpl(Collection<?> collection)
/*     */   {
/* 350 */     StringBuilder sb = newStringBuilderForCollection(collection.size()).append('[');
/*     */ 
/* 352 */     STANDARD_JOINER.appendTo(sb, Iterables.transform(collection, new Function(collection)
/*     */     {
/*     */       public Object apply(Object input) {
/* 355 */         return input == this.val$collection ? "(this Collection)" : input;
/*     */       }
/*     */     }));
/* 358 */     return ']';
/*     */   }
/*     */ 
/*     */   static StringBuilder newStringBuilderForCollection(int size)
/*     */   {
/* 365 */     Preconditions.checkArgument(size >= 0, "size must be non-negative");
/* 366 */     return new StringBuilder((int)Math.min(size * 8L, 1073741824L));
/*     */   }
/*     */ 
/*     */   static <T> Collection<T> cast(Iterable<T> iterable)
/*     */   {
/* 373 */     return (Collection)iterable;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.Collections2
 * JD-Core Version:    0.6.0
 */