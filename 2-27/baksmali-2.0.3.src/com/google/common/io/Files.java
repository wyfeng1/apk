/*     */ package com.google.common.io;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.Charset;
/*     */ 
/*     */ public final class Files
/*     */ {
/*     */   public static ByteSource asByteSource(File file)
/*     */   {
/* 111 */     return new FileByteSource(file, null);
/*     */   }
/*     */ 
/*     */   public static CharSource asCharSource(File file, Charset charset)
/*     */   {
/* 239 */     return asByteSource(file).asCharSource(charset);
/*     */   }
/*     */ 
/*     */   public static String toString(File file, Charset charset)
/*     */     throws IOException
/*     */   {
/* 369 */     return asCharSource(file, charset).read();
/*     */   }
/*     */ 
/*     */   private static final class FileByteSource extends ByteSource
/*     */   {
/*     */     private final File file;
/*     */ 
/*     */     private FileByteSource(File file)
/*     */     {
/* 119 */       this.file = ((File)Preconditions.checkNotNull(file));
/*     */     }
/*     */ 
/*     */     public FileInputStream openStream() throws IOException
/*     */     {
/* 124 */       return new FileInputStream(this.file);
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 194 */       return "Files.asByteSource(" + this.file + ")";
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.io.Files
 * JD-Core Version:    0.6.0
 */