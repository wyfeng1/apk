/*    */ package org.jf.dexlib2.writer.io;
/*    */ 
/*    */ import com.google.common.collect.Lists;
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ import java.util.List;
/*    */ 
/*    */ public class MemoryDeferredOutputStream extends DeferredOutputStream
/*    */ {
/* 16 */   private final List<byte[]> buffers = Lists.newArrayList();
/*    */   private byte[] currentBuffer;
/*    */   private int currentPosition;
/*    */ 
/*    */   public MemoryDeferredOutputStream()
/*    */   {
/* 21 */     this(16384);
/*    */   }
/*    */ 
/*    */   public MemoryDeferredOutputStream(int bufferSize) {
/* 25 */     this.currentBuffer = new byte[bufferSize];
/*    */   }
/*    */ 
/*    */   public void writeTo(OutputStream output) throws IOException {
/* 29 */     for (byte[] buffer : this.buffers) {
/* 30 */       output.write(buffer);
/*    */     }
/* 32 */     if (this.currentPosition > 0) {
/* 33 */       output.write(this.currentBuffer, 0, this.currentPosition);
/*    */     }
/* 35 */     this.buffers.clear();
/* 36 */     this.currentPosition = 0;
/*    */   }
/*    */ 
/*    */   public void write(int i) throws IOException {
/* 40 */     if (remaining() == 0) {
/* 41 */       this.buffers.add(this.currentBuffer);
/* 42 */       this.currentBuffer = new byte[this.currentBuffer.length];
/* 43 */       this.currentPosition = 0;
/*    */     }
/* 45 */     this.currentBuffer[(this.currentPosition++)] = (byte)i;
/*    */   }
/*    */ 
/*    */   public void write(byte[] bytes) throws IOException {
/* 49 */     write(bytes, 0, bytes.length);
/*    */   }
/*    */ 
/*    */   public void write(byte[] bytes, int offset, int length) throws IOException {
/* 53 */     int remaining = remaining();
/* 54 */     int written = 0;
/* 55 */     while (length - written > 0) {
/* 56 */       int toWrite = Math.min(remaining, length - written);
/* 57 */       System.arraycopy(bytes, offset + written, this.currentBuffer, this.currentPosition, toWrite);
/* 58 */       written += toWrite;
/* 59 */       this.currentPosition += toWrite;
/*    */ 
/* 61 */       remaining = remaining();
/* 62 */       if (remaining == 0) {
/* 63 */         this.buffers.add(this.currentBuffer);
/* 64 */         this.currentBuffer = new byte[this.currentBuffer.length];
/* 65 */         this.currentPosition = 0;
/* 66 */         remaining = this.currentBuffer.length;
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   private int remaining() {
/* 72 */     return this.currentBuffer.length - this.currentPosition;
/*    */   }
/*    */ 
/*    */   public static DeferredOutputStreamFactory getFactory()
/*    */   {
/* 77 */     return getFactory(16384);
/*    */   }
/*    */ 
/*    */   public static DeferredOutputStreamFactory getFactory(int bufferSize)
/*    */   {
/* 82 */     return new DeferredOutputStreamFactory(bufferSize) {
/*    */       public DeferredOutputStream makeDeferredOutputStream() {
/* 84 */         return new MemoryDeferredOutputStream(this.val$bufferSize);
/*    */       }
/*    */     };
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.io.MemoryDeferredOutputStream
 * JD-Core Version:    0.6.0
 */