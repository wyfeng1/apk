/*     */ package com.google.common.base;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public final class Suppliers
/*     */ {
/*     */   public static <T> Supplier<T> memoize(Supplier<T> delegate)
/*     */   {
/* 103 */     return (delegate instanceof MemoizingSupplier) ? delegate : new MemoizingSupplier((Supplier)Preconditions.checkNotNull(delegate));
/*     */   }
/*     */ 
/*     */   public static <T> Supplier<T> ofInstance(T instance)
/*     */   {
/* 219 */     return new SupplierOfInstance(instance);
/*     */   }
/*     */ 
/*     */   private static class SupplierOfInstance<T> implements Supplier<T>, Serializable {
/*     */     final T instance;
/*     */ 
/*     */     SupplierOfInstance(T instance) {
/* 227 */       this.instance = instance;
/*     */     }
/*     */ 
/*     */     public T get() {
/* 231 */       return this.instance;
/*     */     }
/*     */ 
/*     */     public boolean equals(Object obj) {
/* 235 */       if ((obj instanceof SupplierOfInstance)) {
/* 236 */         SupplierOfInstance that = (SupplierOfInstance)obj;
/* 237 */         return Objects.equal(this.instance, that.instance);
/*     */       }
/* 239 */       return false;
/*     */     }
/*     */ 
/*     */     public int hashCode() {
/* 243 */       return Objects.hashCode(new Object[] { this.instance });
/*     */     }
/*     */ 
/*     */     public String toString() {
/* 247 */       return "Suppliers.ofInstance(" + this.instance + ")";
/*     */     }
/*     */   }
/*     */ 
/*     */   static class MemoizingSupplier<T>
/*     */     implements Supplier<T>, Serializable
/*     */   {
/*     */     final Supplier<T> delegate;
/*     */     volatile transient boolean initialized;
/*     */     transient T value;
/*     */ 
/*     */     MemoizingSupplier(Supplier<T> delegate)
/*     */     {
/* 117 */       this.delegate = delegate;
/*     */     }
/*     */ 
/*     */     public T get()
/*     */     {
/* 122 */       if (!this.initialized) {
/* 123 */         synchronized (this) {
/* 124 */           if (!this.initialized) {
/* 125 */             Object t = this.delegate.get();
/* 126 */             this.value = t;
/* 127 */             this.initialized = true;
/* 128 */             return t;
/*     */           }
/*     */         }
/*     */       }
/* 132 */       return this.value;
/*     */     }
/*     */ 
/*     */     public String toString() {
/* 136 */       return "Suppliers.memoize(" + this.delegate + ")";
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.base.Suppliers
 * JD-Core Version:    0.6.0
 */