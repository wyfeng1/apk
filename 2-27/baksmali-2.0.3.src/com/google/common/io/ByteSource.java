/*     */ package com.google.common.io;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.nio.charset.Charset;
/*     */ 
/*     */ public abstract class ByteSource
/*     */ {
/* 159 */   private static final byte[] countBuffer = new byte[4096];
/*     */ 
/*     */   public CharSource asCharSource(Charset charset)
/*     */   {
/*  63 */     return new AsCharSource(charset, null);
/*     */   }
/*     */ 
/*     */   public abstract InputStream openStream()
/*     */     throws IOException;
/*     */ 
/*     */   private final class AsCharSource extends CharSource
/*     */   {
/*     */     private final Charset charset;
/*     */ 
/*     */     private AsCharSource(Charset charset)
/*     */     {
/* 282 */       this.charset = ((Charset)Preconditions.checkNotNull(charset));
/*     */     }
/*     */ 
/*     */     public Reader openStream() throws IOException
/*     */     {
/* 287 */       return new InputStreamReader(ByteSource.this.openStream(), this.charset);
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 292 */       return ByteSource.this.toString() + ".asCharSource(" + this.charset + ")";
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.io.ByteSource
 * JD-Core Version:    0.6.0
 */