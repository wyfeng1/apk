/*     */ package com.google.common.io;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.base.Throwables;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Deque;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public final class Closer
/*     */   implements Closeable
/*     */ {
/*  94 */   private static final Suppressor SUPPRESSOR = SuppressingSuppressor.isAvailable() ? SuppressingSuppressor.INSTANCE : LoggingSuppressor.INSTANCE;
/*     */   final Suppressor suppressor;
/* 108 */   private final Deque<Closeable> stack = new ArrayDeque(4);
/*     */   private Throwable thrown;
/*     */ 
/*     */   public static Closer create()
/*     */   {
/* 102 */     return new Closer(SUPPRESSOR);
/*     */   }
/*     */ 
/*     */   Closer(Suppressor suppressor)
/*     */   {
/* 112 */     this.suppressor = ((Suppressor)Preconditions.checkNotNull(suppressor));
/*     */   }
/*     */ 
/*     */   public <C extends Closeable> C register(C closeable)
/*     */   {
/* 123 */     this.stack.push(closeable);
/* 124 */     return closeable;
/*     */   }
/*     */ 
/*     */   public RuntimeException rethrow(Throwable e)
/*     */     throws IOException
/*     */   {
/* 141 */     this.thrown = e;
/* 142 */     Throwables.propagateIfPossible(e, IOException.class);
/* 143 */     throw Throwables.propagate(e);
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 200 */     Throwable throwable = this.thrown;
/*     */ 
/* 203 */     while (!this.stack.isEmpty()) {
/* 204 */       Closeable closeable = (Closeable)this.stack.pop();
/*     */       try {
/* 206 */         closeable.close();
/*     */       } catch (Throwable e) {
/* 208 */         if (throwable == null)
/* 209 */           throwable = e;
/*     */         else {
/* 211 */           this.suppressor.suppress(closeable, throwable, e);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 216 */     if ((this.thrown == null) && (throwable != null)) {
/* 217 */       Throwables.propagateIfPossible(throwable, IOException.class);
/* 218 */       throw new AssertionError(throwable);
/*     */     }
/*     */   }
/*     */ 
/*     */   static final class SuppressingSuppressor
/*     */     implements Closer.Suppressor
/*     */   {
/* 255 */     static final SuppressingSuppressor INSTANCE = new SuppressingSuppressor();
/*     */ 
/* 261 */     static final Method addSuppressed = getAddSuppressed();
/*     */ 
/*     */     static boolean isAvailable()
/*     */     {
/* 258 */       return addSuppressed != null;
/*     */     }
/*     */ 
/*     */     private static Method getAddSuppressed()
/*     */     {
/*     */       try
/*     */       {
/* 265 */         return Throwable.class.getMethod("addSuppressed", new Class[] { Throwable.class }); } catch (Throwable e) {
/*     */       }
/* 267 */       return null;
/*     */     }
/*     */ 
/*     */     public void suppress(Closeable closeable, Throwable thrown, Throwable suppressed)
/*     */     {
/* 274 */       if (thrown == suppressed)
/* 275 */         return;
/*     */       try
/*     */       {
/* 278 */         addSuppressed.invoke(thrown, new Object[] { suppressed });
/*     */       }
/*     */       catch (Throwable e) {
/* 281 */         Closer.LoggingSuppressor.INSTANCE.suppress(closeable, thrown, suppressed);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   static final class LoggingSuppressor
/*     */     implements Closer.Suppressor
/*     */   {
/* 239 */     static final LoggingSuppressor INSTANCE = new LoggingSuppressor();
/*     */ 
/*     */     public void suppress(Closeable closeable, Throwable thrown, Throwable suppressed)
/*     */     {
/* 244 */       Closeables.logger.log(Level.WARNING, "Suppressing exception thrown when closing " + closeable, suppressed);
/*     */     }
/*     */   }
/*     */ 
/*     */   static abstract interface Suppressor
/*     */   {
/*     */     public abstract void suppress(Closeable paramCloseable, Throwable paramThrowable1, Throwable paramThrowable2);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.io.Closer
 * JD-Core Version:    0.6.0
 */