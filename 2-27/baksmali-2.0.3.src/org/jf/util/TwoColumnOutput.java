/*     */ package org.jf.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ 
/*     */ public final class TwoColumnOutput
/*     */ {
/*     */   private final Writer out;
/*     */   private final int leftWidth;
/*     */   private final int rightWidth;
/*     */   private final String spacer;
/*  94 */   private String[] leftLines = null;
/*  95 */   private String[] rightLines = null;
/*     */ 
/*     */   public TwoColumnOutput(Writer out, int leftWidth, int rightWidth, String spacer)
/*     */   {
/*  67 */     if (leftWidth < 1) {
/*  68 */       throw new IllegalArgumentException("leftWidth < 1");
/*     */     }
/*     */ 
/*  71 */     if (rightWidth < 1) {
/*  72 */       throw new IllegalArgumentException("rightWidth < 1");
/*     */     }
/*     */ 
/*  75 */     this.out = out;
/*  76 */     this.leftWidth = leftWidth;
/*  77 */     this.rightWidth = rightWidth;
/*  78 */     this.spacer = spacer;
/*     */   }
/*     */ 
/*     */   public void write(String left, String right)
/*     */     throws IOException
/*     */   {
/*  97 */     this.leftLines = StringWrapper.wrapString(left, this.leftWidth, this.leftLines);
/*  98 */     this.rightLines = StringWrapper.wrapString(right, this.rightWidth, this.rightLines);
/*  99 */     int leftCount = this.leftLines.length;
/* 100 */     int rightCount = this.rightLines.length;
/*     */ 
/* 102 */     for (int i = 0; (i < leftCount) || (i < rightCount); i++) {
/* 103 */       String leftLine = null;
/* 104 */       String rightLine = null;
/*     */ 
/* 106 */       if (i < leftCount) {
/* 107 */         leftLine = this.leftLines[i];
/* 108 */         if (leftLine == null) {
/* 109 */           leftCount = i;
/*     */         }
/*     */       }
/*     */ 
/* 113 */       if (i < rightCount) {
/* 114 */         rightLine = this.rightLines[i];
/* 115 */         if (rightLine == null) {
/* 116 */           rightCount = i;
/*     */         }
/*     */       }
/*     */ 
/* 120 */       if ((leftLine != null) || (rightLine != null)) {
/* 121 */         int written = 0;
/* 122 */         if (leftLine != null) {
/* 123 */           this.out.write(leftLine);
/* 124 */           written = leftLine.length();
/*     */         }
/*     */ 
/* 127 */         int remaining = this.leftWidth - written;
/* 128 */         if (remaining > 0) {
/* 129 */           writeSpaces(this.out, remaining);
/*     */         }
/*     */ 
/* 132 */         this.out.write(this.spacer);
/*     */ 
/* 134 */         if (rightLine != null) {
/* 135 */           this.out.write(rightLine);
/*     */         }
/*     */ 
/* 138 */         this.out.write(10);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void writeSpaces(Writer out, int amt)
/*     */     throws IOException
/*     */   {
/* 150 */     while (amt > 0) {
/* 151 */       out.write(32);
/* 152 */       amt--;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.util.TwoColumnOutput
 * JD-Core Version:    0.6.0
 */