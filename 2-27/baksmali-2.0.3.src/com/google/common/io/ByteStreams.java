/*     */ package com.google.common.io;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public final class ByteStreams
/*     */ {
/* 577 */   private static final OutputStream NULL_OUTPUT_STREAM = new OutputStream()
/*     */   {
/*     */     public void write(int b)
/*     */     {
/*     */     }
/*     */ 
/*     */     public void write(byte[] b) {
/* 584 */       Preconditions.checkNotNull(b);
/*     */     }
/*     */ 
/*     */     public void write(byte[] b, int off, int len) {
/* 588 */       Preconditions.checkNotNull(b);
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 593 */       return "ByteStreams.nullOutputStream()";
/*     */     }
/* 577 */   };
/*     */ 
/*     */   public static long copy(InputStream from, OutputStream to)
/*     */     throws IOException
/*     */   {
/* 202 */     Preconditions.checkNotNull(from);
/* 203 */     Preconditions.checkNotNull(to);
/* 204 */     byte[] buf = new byte[4096];
/* 205 */     long total = 0L;
/*     */     while (true) {
/* 207 */       int r = from.read(buf);
/* 208 */       if (r == -1) {
/*     */         break;
/*     */       }
/* 211 */       to.write(buf, 0, r);
/* 212 */       total += r;
/*     */     }
/* 214 */     return total;
/*     */   }
/*     */ 
/*     */   public static byte[] toByteArray(InputStream in)
/*     */     throws IOException
/*     */   {
/* 251 */     ByteArrayOutputStream out = new ByteArrayOutputStream();
/* 252 */     copy(in, out);
/* 253 */     return out.toByteArray();
/*     */   }
/*     */ 
/*     */   public static void readFully(InputStream in, byte[] b)
/*     */     throws IOException
/*     */   {
/* 714 */     readFully(in, b, 0, b.length);
/*     */   }
/*     */ 
/*     */   public static void readFully(InputStream in, byte[] b, int off, int len)
/*     */     throws IOException
/*     */   {
/* 733 */     int read = read(in, b, off, len);
/* 734 */     if (read != len)
/* 735 */       throw new EOFException("reached end of stream after reading " + read + " bytes; " + len + " bytes expected");
/*     */   }
/*     */ 
/*     */   public static void skipFully(InputStream in, long n)
/*     */     throws IOException
/*     */   {
/* 753 */     long toSkip = n;
/* 754 */     while (n > 0L) {
/* 755 */       long amt = in.skip(n);
/* 756 */       if (amt == 0L)
/*     */       {
/* 758 */         if (in.read() == -1) {
/* 759 */           long skipped = toSkip - n;
/* 760 */           throw new EOFException("reached end of stream after skipping " + skipped + " bytes; " + toSkip + " bytes expected");
/*     */         }
/*     */ 
/* 763 */         n -= 1L;
/*     */       } else {
/* 765 */         n -= amt;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static int read(InputStream in, byte[] b, int off, int len)
/*     */     throws IOException
/*     */   {
/* 892 */     Preconditions.checkNotNull(in);
/* 893 */     Preconditions.checkNotNull(b);
/* 894 */     if (len < 0) {
/* 895 */       throw new IndexOutOfBoundsException("len is negative");
/*     */     }
/* 897 */     int total = 0;
/* 898 */     while (total < len) {
/* 899 */       int result = in.read(b, off + total, len - total);
/* 900 */       if (result == -1) {
/*     */         break;
/*     */       }
/* 903 */       total += result;
/*     */     }
/* 905 */     return total;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.io.ByteStreams
 * JD-Core Version:    0.6.0
 */