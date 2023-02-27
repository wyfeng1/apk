/*     */ package org.jf.util;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class ConsoleUtil
/*     */ {
/*     */   public static int getConsoleWidth()
/*     */   {
/*  41 */     if (System.getProperty("os.name").toLowerCase().contains("windows"))
/*     */       try {
/*  43 */         return attemptMode();
/*     */       }
/*     */       catch (Exception localException1) {
/*     */       }
/*     */     else try {
/*  48 */         return attemptStty();
/*     */       }
/*     */       catch (Exception localException2)
/*     */       {
/*     */       }
/*  53 */     return 80;
/*     */   }
/*     */ 
/*     */   private static int attemptStty() {
/*  57 */     String output = attemptCommand(new String[] { "sh", "-c", "stty size < /dev/tty" });
/*  58 */     if (output == null) {
/*  59 */       return 80;
/*     */     }
/*     */ 
/*  62 */     String[] vals = output.split(" ");
/*  63 */     if (vals.length < 2) {
/*  64 */       return 80;
/*     */     }
/*  66 */     return Integer.parseInt(vals[1]);
/*     */   }
/*     */ 
/*     */   private static int attemptMode() {
/*  70 */     String output = attemptCommand(new String[] { "mode", "con" });
/*  71 */     if (output == null) {
/*  72 */       return 80;
/*     */     }
/*     */ 
/*  75 */     Pattern pattern = Pattern.compile("Columns:[ \t]*(\\d+)");
/*  76 */     Matcher m = pattern.matcher(output);
/*  77 */     if (!m.find()) {
/*  78 */       return 80;
/*     */     }
/*     */ 
/*  81 */     return Integer.parseInt(m.group(1));
/*     */   }
/*     */ 
/*     */   private static String attemptCommand(String[] command) {
/*  85 */     StringBuffer buffer = null;
/*     */     try
/*     */     {
/*  89 */       Process p = Runtime.getRuntime().exec(command);
/*  90 */       BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
/*     */       String line;
/*  94 */       while ((line = reader.readLine()) != null) {
/*  95 */         if (buffer == null) {
/*  96 */           buffer = new StringBuffer();
/*     */         }
/*     */ 
/*  99 */         buffer.append(line);
/*     */       }
/*     */ 
/* 102 */       if (buffer != null) {
/* 103 */         return buffer.toString();
/*     */       }
/* 105 */       return null; } catch (Exception ex) {
/*     */     }
/* 107 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.util.ConsoleUtil
 * JD-Core Version:    0.6.0
 */