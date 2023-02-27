/*     */ package org.jf.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class PathUtil
/*     */ {
/*     */   public static File getRelativeFile(File baseFile, File fileToRelativize)
/*     */     throws IOException
/*     */   {
/*  40 */     if (baseFile.isFile()) {
/*  41 */       baseFile = baseFile.getParentFile();
/*     */     }
/*     */ 
/*  44 */     return new File(getRelativeFileInternal(baseFile.getCanonicalFile(), fileToRelativize.getCanonicalFile()));
/*     */   }
/*     */ 
/*     */   static String getRelativeFileInternal(File canonicalBaseFile, File canonicalFileToRelativize)
/*     */   {
/*  58 */     ArrayList basePath = getPathComponents(canonicalBaseFile);
/*  59 */     ArrayList pathToRelativize = getPathComponents(canonicalFileToRelativize);
/*     */ 
/*  63 */     if (!((String)basePath.get(0)).equals(pathToRelativize.get(0))) {
/*  64 */       return canonicalFileToRelativize.getPath();
/*     */     }
/*     */ 
/*  68 */     StringBuilder sb = new StringBuilder();
/*     */ 
/*  70 */     for (int commonDirs = 1; (commonDirs < basePath.size()) && (commonDirs < pathToRelativize.size()) && 
/*  71 */       (((String)basePath.get(commonDirs)).equals(pathToRelativize.get(commonDirs))); commonDirs++);
/*  76 */     boolean first = true;
/*  77 */     for (int i = commonDirs; i < basePath.size(); i++) {
/*  78 */       if (!first)
/*  79 */         sb.append(File.separatorChar);
/*     */       else {
/*  81 */         first = false;
/*     */       }
/*     */ 
/*  84 */       sb.append("..");
/*     */     }
/*     */ 
/*  87 */     first = true;
/*  88 */     for (int i = commonDirs; i < pathToRelativize.size(); i++) {
/*  89 */       if (first) {
/*  90 */         if (sb.length() != 0) {
/*  91 */           sb.append(File.separatorChar);
/*     */         }
/*  93 */         first = false;
/*     */       } else {
/*  95 */         sb.append(File.separatorChar);
/*     */       }
/*     */ 
/*  98 */       sb.append((String)pathToRelativize.get(i));
/*     */     }
/*     */ 
/* 101 */     if (sb.length() == 0) {
/* 102 */       return ".";
/*     */     }
/*     */ 
/* 105 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   private static ArrayList<String> getPathComponents(File file) {
/* 109 */     ArrayList path = new ArrayList();
/*     */ 
/* 111 */     while (file != null) {
/* 112 */       File parentFile = file.getParentFile();
/*     */ 
/* 114 */       if (parentFile == null)
/* 115 */         path.add(0, file.getPath());
/*     */       else {
/* 117 */         path.add(0, file.getName());
/*     */       }
/*     */ 
/* 120 */       file = parentFile;
/*     */     }
/*     */ 
/* 123 */     return path;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.util.PathUtil
 * JD-Core Version:    0.6.0
 */