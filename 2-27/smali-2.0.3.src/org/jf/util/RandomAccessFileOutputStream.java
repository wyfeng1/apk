/*    */ package org.jf.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.io.RandomAccessFile;
/*    */ 
/*    */ public class RandomAccessFileOutputStream extends OutputStream
/*    */ {
/*    */   private int filePosition;
/*    */   private final RandomAccessFile raf;
/*    */ 
/*    */   public RandomAccessFileOutputStream(RandomAccessFile raf, int startFilePosition)
/*    */   {
/* 44 */     this.filePosition = startFilePosition;
/* 45 */     this.raf = raf;
/*    */   }
/*    */ 
/*    */   public void write(int b) throws IOException {
/* 49 */     this.raf.seek(this.filePosition);
/* 50 */     this.filePosition += 1;
/* 51 */     this.raf.write(b);
/*    */   }
/*    */ 
/*    */   public void write(byte[] b) throws IOException {
/* 55 */     this.raf.seek(this.filePosition);
/* 56 */     this.filePosition += b.length;
/* 57 */     this.raf.write(b);
/*    */   }
/*    */ 
/*    */   public void write(byte[] b, int off, int len) throws IOException {
/* 61 */     this.raf.seek(this.filePosition);
/* 62 */     this.filePosition += len;
/* 63 */     this.raf.write(b, off, len);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.util.RandomAccessFileOutputStream
 * JD-Core Version:    0.6.0
 */