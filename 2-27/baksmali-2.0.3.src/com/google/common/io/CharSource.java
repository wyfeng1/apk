/*     */ package com.google.common.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ 
/*     */ public abstract class CharSource
/*     */ {
/*     */   public abstract Reader openStream()
/*     */     throws IOException;
/*     */ 
/*     */   public String read()
/*     */     throws IOException
/*     */   {
/* 134 */     Closer closer = Closer.create();
/*     */     try {
/* 136 */       Reader reader = (Reader)closer.register(openStream());
/* 137 */       String str = CharStreams.toString(reader);
/*     */       return str;
/*     */     }
/*     */     catch (Throwable e)
/*     */     {
/* 139 */       throw closer.rethrow(e);
/*     */     } finally {
/* 141 */       closer.close(); } throw localObject;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.io.CharSource
 * JD-Core Version:    0.6.0
 */