/*     */ package com.google.common.io;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.IOException;
/*     */ import java.nio.CharBuffer;
/*     */ 
/*     */ public final class CharStreams
/*     */ {
/*     */   public static long copy(Readable from, Appendable to)
/*     */     throws IOException
/*     */   {
/* 240 */     Preconditions.checkNotNull(from);
/* 241 */     Preconditions.checkNotNull(to);
/* 242 */     CharBuffer buf = CharBuffer.allocate(2048);
/* 243 */     long total = 0L;
/* 244 */     while (from.read(buf) != -1) {
/* 245 */       buf.flip();
/* 246 */       to.append(buf);
/* 247 */       total += buf.remaining();
/* 248 */       buf.clear();
/*     */     }
/* 250 */     return total;
/*     */   }
/*     */ 
/*     */   public static String toString(Readable r)
/*     */     throws IOException
/*     */   {
/* 262 */     return toStringBuilder(r).toString();
/*     */   }
/*     */ 
/*     */   private static StringBuilder toStringBuilder(Readable r)
/*     */     throws IOException
/*     */   {
/* 287 */     StringBuilder sb = new StringBuilder();
/* 288 */     copy(r, sb);
/* 289 */     return sb;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.io.CharStreams
 * JD-Core Version:    0.6.0
 */