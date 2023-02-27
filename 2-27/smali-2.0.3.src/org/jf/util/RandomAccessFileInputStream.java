/*    */ package org.jf.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.RandomAccessFile;
/*    */ 
/*    */ public class RandomAccessFileInputStream extends InputStream
/*    */ {
/*    */   private int filePosition;
/*    */   private final RandomAccessFile raf;
/*    */ 
/*    */   public RandomAccessFileInputStream(RandomAccessFile raf, int filePosition)
/*    */   {
/* 44 */     this.filePosition = filePosition;
/* 45 */     this.raf = raf;
/*    */   }
/*    */ 
/*    */   public int read() throws IOException {
/* 49 */     this.raf.seek(this.filePosition);
/* 50 */     this.filePosition += 1;
/* 51 */     return this.raf.read();
/*    */   }
/*    */ 
/*    */   public int read(byte[] bytes) throws IOException {
/* 55 */     this.raf.seek(this.filePosition);
/* 56 */     int bytesRead = this.raf.read(bytes);
/* 57 */     this.filePosition += bytesRead;
/* 58 */     return bytesRead;
/*    */   }
/*    */ 
/*    */   public int read(byte[] bytes, int offset, int length) throws IOException {
/* 62 */     this.raf.seek(this.filePosition);
/* 63 */     int bytesRead = this.raf.read(bytes, offset, length);
/* 64 */     this.filePosition += bytesRead;
/* 65 */     return bytesRead;
/*    */   }
/*    */ 
/*    */   public long skip(long l) throws IOException {
/* 69 */     int skipBytes = Math.min((int)l, available());
/* 70 */     this.filePosition += skipBytes;
/* 71 */     return skipBytes;
/*    */   }
/*    */ 
/*    */   public int available() throws IOException {
/* 75 */     return (int)this.raf.length() - this.filePosition;
/*    */   }
/*    */ 
/*    */   public boolean markSupported() {
/* 79 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.util.RandomAccessFileInputStream
 * JD-Core Version:    0.6.0
 */