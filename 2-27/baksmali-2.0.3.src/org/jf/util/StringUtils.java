/*     */ package org.jf.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ 
/*     */ public class StringUtils
/*     */ {
/*     */   public static void writeEscapedChar(Writer writer, char c)
/*     */     throws IOException
/*     */   {
/*  39 */     if ((c >= ' ') && (c < '')) {
/*  40 */       if ((c == '\'') || (c == '"') || (c == '\\')) {
/*  41 */         writer.write(92);
/*     */       }
/*  43 */       writer.write(c);
/*  44 */       return;
/*  45 */     }if (c <= '')
/*  46 */       switch (c) { case '\n':
/*  47 */         writer.write("\\n"); return;
/*     */       case '\r':
/*  48 */         writer.write("\\r"); return;
/*     */       case '\t':
/*  49 */         writer.write("\\t"); return;
/*     */       case '\013':
/*     */       case '\f':
/*     */       }
/*  53 */     writer.write("\\u");
/*  54 */     writer.write(Character.forDigit(c >> '\f', 16));
/*  55 */     writer.write(Character.forDigit(c >> '\b' & 0xF, 16));
/*  56 */     writer.write(Character.forDigit(c >> '\004' & 0xF, 16));
/*  57 */     writer.write(Character.forDigit(c & 0xF, 16));
/*     */   }
/*     */ 
/*     */   public static void writeEscapedString(Writer writer, String value) throws IOException {
/*  61 */     for (int i = 0; i < value.length(); i++) {
/*  62 */       char c = value.charAt(i);
/*     */ 
/*  64 */       if ((c >= ' ') && (c < '')) {
/*  65 */         if ((c == '\'') || (c == '"') || (c == '\\')) {
/*  66 */           writer.write(92);
/*     */         }
/*  68 */         writer.write(c);
/*     */       }
/*  70 */       else if (c <= '') {
/*  71 */         switch (c) { case '\n':
/*  72 */           writer.write("\\n"); break;
/*     */         case '\r':
/*  73 */           writer.write("\\r"); break;
/*     */         case '\t':
/*  74 */           writer.write("\\t"); break;
/*     */         case '\013':
/*     */         case '\f': }
/*     */       } else {
/*  78 */         writer.write("\\u");
/*  79 */         writer.write(Character.forDigit(c >> '\f', 16));
/*  80 */         writer.write(Character.forDigit(c >> '\b' & 0xF, 16));
/*  81 */         writer.write(Character.forDigit(c >> '\004' & 0xF, 16));
/*  82 */         writer.write(Character.forDigit(c & 0xF, 16));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String escapeString(String value) {
/*  87 */     int len = value.length();
/*  88 */     StringBuilder sb = new StringBuilder(len * 3 / 2);
/*     */ 
/*  90 */     for (int i = 0; i < len; i++) {
/*  91 */       char c = value.charAt(i);
/*     */ 
/*  93 */       if ((c >= ' ') && (c < '')) {
/*  94 */         if ((c == '\'') || (c == '"') || (c == '\\')) {
/*  95 */           sb.append('\\');
/*     */         }
/*  97 */         sb.append(c);
/*     */       }
/*  99 */       else if (c <= '') {
/* 100 */         switch (c) { case '\n':
/* 101 */           sb.append("\\n"); break;
/*     */         case '\r':
/* 102 */           sb.append("\\r"); break;
/*     */         case '\t':
/* 103 */           sb.append("\\t"); break;
/*     */         case '\013':
/*     */         case '\f': }
/*     */       } else {
/* 107 */         sb.append("\\u");
/* 108 */         sb.append(Character.forDigit(c >> '\f', 16));
/* 109 */         sb.append(Character.forDigit(c >> '\b' & 0xF, 16));
/* 110 */         sb.append(Character.forDigit(c >> '\004' & 0xF, 16));
/* 111 */         sb.append(Character.forDigit(c & 0xF, 16));
/*     */       }
/*     */     }
/* 114 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.util.StringUtils
 * JD-Core Version:    0.6.0
 */