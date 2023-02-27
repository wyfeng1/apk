/*     */ package com.google.common.base;
/*     */ 
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ public final class Stopwatch
/*     */ {
/*     */   private final Ticker ticker;
/*     */   private boolean isRunning;
/*     */   private long elapsedNanos;
/*     */   private long startTick;
/*     */ 
/*     */   public Stopwatch()
/*     */   {
/*  84 */     this(Ticker.systemTicker());
/*     */   }
/*     */ 
/*     */   public Stopwatch(Ticker ticker)
/*     */   {
/*  92 */     this.ticker = ((Ticker)Preconditions.checkNotNull(ticker, "ticker"));
/*     */   }
/*     */ 
/*     */   public Stopwatch start()
/*     */   {
/* 111 */     Preconditions.checkState(!this.isRunning, "This stopwatch is already running; it cannot be started more than once.");
/*     */ 
/* 113 */     this.isRunning = true;
/* 114 */     this.startTick = this.ticker.read();
/* 115 */     return this;
/*     */   }
/*     */ 
/*     */   private long elapsedNanos()
/*     */   {
/* 147 */     return this.isRunning ? this.ticker.read() - this.startTick + this.elapsedNanos : this.elapsedNanos;
/*     */   }
/*     */ 
/*     */   public long elapsed(TimeUnit desiredUnit)
/*     */   {
/* 161 */     return desiredUnit.convert(elapsedNanos(), TimeUnit.NANOSECONDS);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 198 */     return toString(4);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public String toString(int significantDigits)
/*     */   {
/* 213 */     long nanos = elapsedNanos();
/*     */ 
/* 215 */     TimeUnit unit = chooseUnit(nanos);
/* 216 */     double value = nanos / TimeUnit.NANOSECONDS.convert(1L, unit);
/*     */ 
/* 219 */     return String.format("%." + significantDigits + "g %s", new Object[] { Double.valueOf(value), abbreviate(unit) });
/*     */   }
/*     */ 
/*     */   private static TimeUnit chooseUnit(long nanos)
/*     */   {
/* 224 */     if (TimeUnit.SECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
/* 225 */       return TimeUnit.SECONDS;
/*     */     }
/* 227 */     if (TimeUnit.MILLISECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
/* 228 */       return TimeUnit.MILLISECONDS;
/*     */     }
/* 230 */     if (TimeUnit.MICROSECONDS.convert(nanos, TimeUnit.NANOSECONDS) > 0L) {
/* 231 */       return TimeUnit.MICROSECONDS;
/*     */     }
/* 233 */     return TimeUnit.NANOSECONDS;
/*     */   }
/*     */ 
/*     */   private static String abbreviate(TimeUnit unit) {
/* 237 */     switch (1.$SwitchMap$java$util$concurrent$TimeUnit[unit.ordinal()]) {
/*     */     case 1:
/* 239 */       return "ns";
/*     */     case 2:
/* 241 */       return "μs";
/*     */     case 3:
/* 243 */       return "ms";
/*     */     case 4:
/* 245 */       return "s";
/*     */     }
/* 247 */     throw new AssertionError();
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.base.Stopwatch
 * JD-Core Version:    0.6.0
 */