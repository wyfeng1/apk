/*     */ package org.jf.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ 
/*     */ public class IndentingWriter extends Writer
/*     */ {
/*     */   protected final Writer writer;
/*  36 */   protected final char[] buffer = new char[24];
/*  37 */   protected int indentLevel = 0;
/*  38 */   private boolean beginningOfLine = true;
/*  39 */   private static final String newLine = System.getProperty("line.separator");
/*     */ 
/*     */   public IndentingWriter(Writer writer) {
/*  42 */     this.writer = writer;
/*     */   }
/*     */ 
/*     */   protected void writeIndent() throws IOException {
/*  46 */     for (int i = 0; i < this.indentLevel; i++)
/*  47 */       this.writer.write(32);
/*     */   }
/*     */ 
/*     */   public void write(int chr)
/*     */     throws IOException
/*     */   {
/*  53 */     if (chr == 10) {
/*  54 */       this.writer.write(newLine);
/*  55 */       this.beginningOfLine = true;
/*     */     } else {
/*  57 */       if (this.beginningOfLine) {
/*  58 */         writeIndent();
/*     */       }
/*  60 */       this.beginningOfLine = false;
/*  61 */       this.writer.write(chr);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeLine(char[] chars, int start, int len)
/*     */     throws IOException
/*     */   {
/*  69 */     if ((this.beginningOfLine) && (len > 0)) {
/*  70 */       writeIndent();
/*  71 */       this.beginningOfLine = false;
/*     */     }
/*  73 */     this.writer.write(chars, start, len);
/*     */   }
/*     */ 
/*     */   private void writeLine(String str, int start, int len)
/*     */     throws IOException
/*     */   {
/*  81 */     if ((this.beginningOfLine) && (len > 0)) {
/*  82 */       writeIndent();
/*  83 */       this.beginningOfLine = false;
/*     */     }
/*  85 */     this.writer.write(str, start, len);
/*     */   }
/*     */ 
/*     */   public void write(char[] chars) throws IOException
/*     */   {
/*  90 */     write(chars, 0, chars.length);
/*     */   }
/*     */ 
/*     */   public void write(char[] chars, int start, int len) throws IOException
/*     */   {
/*  95 */     int end = start + len;
/*  96 */     int pos = start;
/*  97 */     while (pos < end) {
/*  98 */       if (chars[pos] == '\n') {
/*  99 */         writeLine(chars, start, pos - start);
/*     */ 
/* 101 */         this.writer.write(newLine);
/* 102 */         this.beginningOfLine = true;
/* 103 */         pos++;
/* 104 */         start = pos; continue;
/*     */       }
/* 106 */       pos++;
/*     */     }
/*     */ 
/* 109 */     writeLine(chars, start, pos - start);
/*     */   }
/*     */ 
/*     */   public void write(String s) throws IOException
/*     */   {
/* 114 */     write(s, 0, s.length());
/*     */   }
/*     */ 
/*     */   public void write(String str, int start, int len) throws IOException
/*     */   {
/* 119 */     int end = start + len;
/* 120 */     int pos = start;
/* 121 */     while (pos < end) {
/* 122 */       pos = str.indexOf('\n', start);
/* 123 */       if (pos == -1) {
/* 124 */         writeLine(str, start, end - start);
/* 125 */         return;
/*     */       }
/* 127 */       writeLine(str, start, pos - start);
/* 128 */       this.writer.write(newLine);
/* 129 */       this.beginningOfLine = true;
/* 130 */       start = pos + 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Writer append(CharSequence charSequence)
/*     */     throws IOException
/*     */   {
/* 137 */     write(charSequence.toString());
/* 138 */     return this;
/*     */   }
/*     */ 
/*     */   public Writer append(CharSequence charSequence, int start, int len) throws IOException
/*     */   {
/* 143 */     write(charSequence.subSequence(start, len).toString());
/* 144 */     return this;
/*     */   }
/*     */ 
/*     */   public Writer append(char c) throws IOException
/*     */   {
/* 149 */     write(c);
/* 150 */     return this;
/*     */   }
/*     */ 
/*     */   public void flush() throws IOException
/*     */   {
/* 155 */     this.writer.flush();
/*     */   }
/*     */ 
/*     */   public void close() throws IOException
/*     */   {
/* 160 */     this.writer.close();
/*     */   }
/*     */ 
/*     */   public void indent(int indentAmount) {
/* 164 */     this.indentLevel += indentAmount;
/* 165 */     if (this.indentLevel < 0)
/* 166 */       this.indentLevel = 0;
/*     */   }
/*     */ 
/*     */   public void deindent(int indentAmount)
/*     */   {
/* 171 */     this.indentLevel -= indentAmount;
/* 172 */     if (this.indentLevel < 0)
/* 173 */       this.indentLevel = 0;
/*     */   }
/*     */ 
/*     */   public void printUnsignedLongAsHex(long value) throws IOException
/*     */   {
/* 178 */     int bufferIndex = 23;
/*     */     do {
/* 180 */       int digit = (int)(value & 0xF);
/* 181 */       if (digit < 10)
/* 182 */         this.buffer[(bufferIndex--)] = (char)(digit + 48);
/*     */       else {
/* 184 */         this.buffer[(bufferIndex--)] = (char)(digit - 10 + 97);
/*     */       }
/*     */ 
/* 187 */       value >>>= 4;
/* 188 */     }while (value != 0L);
/*     */ 
/* 190 */     bufferIndex++;
/*     */ 
/* 192 */     writeLine(this.buffer, bufferIndex, 24 - bufferIndex);
/*     */   }
/*     */ 
/*     */   public void printSignedLongAsDec(long value) throws IOException {
/* 196 */     int bufferIndex = 23;
/*     */ 
/* 198 */     if (value < 0L) {
/* 199 */       value *= -1L;
/* 200 */       write(45);
/*     */     }
/*     */     do
/*     */     {
/* 204 */       long digit = value % 10L;
/* 205 */       this.buffer[(bufferIndex--)] = (char)(int)(digit + 48L);
/*     */ 
/* 207 */       value /= 10L;
/* 208 */     }while (value != 0L);
/*     */ 
/* 210 */     bufferIndex++;
/*     */ 
/* 212 */     writeLine(this.buffer, bufferIndex, 24 - bufferIndex);
/*     */   }
/*     */ 
/*     */   public void printSignedIntAsDec(int value) throws IOException {
/* 216 */     int bufferIndex = 15;
/*     */ 
/* 218 */     if (value < 0) {
/* 219 */       value *= -1;
/* 220 */       write(45);
/*     */     }
/*     */     do
/*     */     {
/* 224 */       int digit = value % 10;
/* 225 */       this.buffer[(bufferIndex--)] = (char)(digit + 48);
/*     */ 
/* 227 */       value /= 10;
/* 228 */     }while (value != 0);
/*     */ 
/* 230 */     bufferIndex++;
/*     */ 
/* 232 */     writeLine(this.buffer, bufferIndex, 16 - bufferIndex);
/*     */   }
/*     */ 
/*     */   public void printUnsignedIntAsDec(int value) throws IOException {
/* 236 */     int bufferIndex = 15;
/*     */ 
/* 238 */     if (value < 0)
/* 239 */       printSignedLongAsDec(value & 0xFFFFFFFF);
/*     */     else
/* 241 */       printSignedIntAsDec(value);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.util.IndentingWriter
 * JD-Core Version:    0.6.0
 */