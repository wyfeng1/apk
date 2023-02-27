/*    */ package org.jf.dexlib2.writer.io;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import java.io.RandomAccessFile;
/*    */ import org.jf.util.RandomAccessFileInputStream;
/*    */ import org.jf.util.RandomAccessFileOutputStream;
/*    */ 
/*    */ public class FileDataStore
/*    */   implements DexDataStore
/*    */ {
/*    */   private final RandomAccessFile raf;
/*    */ 
/*    */   public FileDataStore(File file)
/*    */     throws FileNotFoundException, IOException
/*    */   {
/* 13 */     this.raf = new RandomAccessFile(file, "rw");
/* 14 */     this.raf.setLength(0L);
/*    */   }
/*    */ 
/*    */   public OutputStream outputAt(int offset) {
/* 18 */     return new RandomAccessFileOutputStream(this.raf, offset);
/*    */   }
/*    */ 
/*    */   public InputStream readAt(int offset) {
/* 22 */     return new RandomAccessFileInputStream(this.raf, offset);
/*    */   }
/*    */ 
/*    */   public void close() throws IOException {
/* 26 */     this.raf.close();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.io.FileDataStore
 * JD-Core Version:    0.6.0
 */