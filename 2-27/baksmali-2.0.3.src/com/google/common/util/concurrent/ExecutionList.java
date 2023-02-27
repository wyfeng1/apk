/*     */ package com.google.common.util.concurrent;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ 
/*     */ public final class ExecutionList
/*     */ {
/*  49 */   static final Logger log = Logger.getLogger(ExecutionList.class.getName());
/*     */ 
/*  53 */   private final Queue<RunnableExecutorPair> runnables = Lists.newLinkedList();
/*     */ 
/*  57 */   private boolean executed = false;
/*     */ 
/*     */   public void add(Runnable runnable, Executor executor)
/*     */   {
/*  85 */     Preconditions.checkNotNull(runnable, "Runnable was null.");
/*  86 */     Preconditions.checkNotNull(executor, "Executor was null.");
/*     */ 
/*  88 */     boolean executeImmediate = false;
/*     */ 
/*  93 */     synchronized (this.runnables) {
/*  94 */       if (!this.executed)
/*  95 */         this.runnables.add(new RunnableExecutorPair(runnable, executor));
/*     */       else {
/*  97 */         executeImmediate = true;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 105 */     if (executeImmediate)
/* 106 */       new RunnableExecutorPair(runnable, executor).execute();
/*     */   }
/*     */ 
/*     */   public void execute()
/*     */   {
/* 125 */     synchronized (this.runnables) {
/* 126 */       if (this.executed) {
/* 127 */         return;
/*     */       }
/* 129 */       this.executed = true;
/*     */     }
/*     */ 
/* 134 */     while (!this.runnables.isEmpty())
/* 135 */       ((RunnableExecutorPair)this.runnables.poll()).execute(); 
/*     */   }
/*     */ 
/*     */   private static class RunnableExecutorPair {
/*     */     final Runnable runnable;
/*     */     final Executor executor;
/*     */ 
/*     */     RunnableExecutorPair(Runnable runnable, Executor executor) {
/* 144 */       this.runnable = runnable;
/* 145 */       this.executor = executor;
/*     */     }
/*     */ 
/*     */     void execute() {
/*     */       try {
/* 150 */         this.executor.execute(this.runnable);
/*     */       }
/*     */       catch (RuntimeException e)
/*     */       {
/* 155 */         ExecutionList.log.log(Level.SEVERE, "RuntimeException while executing runnable " + this.runnable + " with executor " + this.executor, e);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.util.concurrent.ExecutionList
 * JD-Core Version:    0.6.0
 */