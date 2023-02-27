/*     */ package org.jf.dexlib2.dexbacked;
/*     */ 
/*     */ import com.google.common.io.ByteStreams;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.List;
/*     */ import org.jf.dexlib2.Opcodes;
/*     */ import org.jf.dexlib2.dexbacked.raw.OdexHeaderItem;
/*     */ import org.jf.dexlib2.dexbacked.util.VariableSizeList;
/*     */ 
/*     */ public class DexBackedOdexFile extends DexBackedDexFile
/*     */ {
/*     */   private final byte[] odexBuf;
/*     */ 
/*     */   public DexBackedOdexFile(Opcodes opcodes, byte[] odexBuf, byte[] dexBuf)
/*     */   {
/*  55 */     super(opcodes, dexBuf);
/*     */ 
/*  57 */     this.odexBuf = odexBuf;
/*     */   }
/*     */ 
/*     */   public boolean isOdexFile() {
/*  61 */     return true;
/*     */   }
/*     */ 
/*     */   public List<String> getDependencies() {
/*  65 */     int dexOffset = OdexHeaderItem.getDexOffset(this.odexBuf);
/*  66 */     int dependencyOffset = OdexHeaderItem.getDependenciesOffset(this.odexBuf) - dexOffset;
/*     */ 
/*  68 */     BaseDexBuffer buf = new BaseDexBuffer(this.buf);
/*  69 */     int dependencyCount = buf.readInt(dependencyOffset + 12);
/*     */ 
/*  71 */     return new VariableSizeList(this, dependencyOffset + 16, dependencyCount) {
/*     */       protected String readNextItem(DexReader reader, int index) {
/*  73 */         int length = reader.readInt();
/*  74 */         int offset = reader.getOffset();
/*  75 */         reader.moveRelative(length + 20);
/*     */         try {
/*  77 */           return new String(DexBackedOdexFile.this.buf, offset, length - 1, "US-ASCII"); } catch (UnsupportedEncodingException ex) {
/*     */         }
/*  79 */         throw new RuntimeException(ex);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static DexBackedOdexFile fromInputStream(Opcodes opcodes, InputStream is) throws IOException
/*     */   {
/*  87 */     if (!is.markSupported()) {
/*  88 */       throw new IllegalArgumentException("InputStream must support mark");
/*     */     }
/*  90 */     is.mark(8);
/*  91 */     byte[] partialHeader = new byte[8];
/*     */     try {
/*  93 */       ByteStreams.readFully(is, partialHeader);
/*     */     } catch (EOFException ex) {
/*  95 */       throw new DexBackedDexFile.NotADexFile("File is too short");
/*     */     } finally {
/*  97 */       is.reset();
/*     */     }
/*     */ 
/* 100 */     verifyMagic(partialHeader);
/*     */ 
/* 102 */     is.reset();
/* 103 */     byte[] odexBuf = new byte[40];
/* 104 */     ByteStreams.readFully(is, odexBuf);
/* 105 */     int dexOffset = OdexHeaderItem.getDexOffset(odexBuf);
/* 106 */     if (dexOffset > 40) {
/* 107 */       ByteStreams.skipFully(is, dexOffset - 40);
/*     */     }
/*     */ 
/* 110 */     byte[] dexBuf = ByteStreams.toByteArray(is);
/*     */ 
/* 112 */     return new DexBackedOdexFile(opcodes, odexBuf, dexBuf);
/*     */   }
/*     */ 
/*     */   private static void verifyMagic(byte[] buf) {
/* 116 */     if (!OdexHeaderItem.verifyMagic(buf)) {
/* 117 */       StringBuilder sb = new StringBuilder("Invalid magic value:");
/* 118 */       for (int i = 0; i < 8; i++) {
/* 119 */         sb.append(String.format(" %02x", new Object[] { Byte.valueOf(buf[i]) }));
/*     */       }
/* 121 */       throw new NotAnOdexFile(sb.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getOdexVersion() {
/* 126 */     return OdexHeaderItem.getVersion(this.odexBuf);
/*     */   }
/*     */ 
/*     */   public static class NotAnOdexFile extends RuntimeException
/*     */   {
/*     */     public NotAnOdexFile()
/*     */     {
/*     */     }
/*     */ 
/*     */     public NotAnOdexFile(String message)
/*     */     {
/* 138 */       super();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.DexBackedOdexFile
 * JD-Core Version:    0.6.0
 */