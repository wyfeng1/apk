/*     */ package org.jf.dexlib2;
/*     */ 
/*     */ import com.google.common.io.ByteStreams;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*     */ import org.jf.dexlib2.dexbacked.DexBackedDexFile.NotADexFile;
/*     */ import org.jf.dexlib2.dexbacked.DexBackedOdexFile;
/*     */ import org.jf.dexlib2.dexbacked.DexBackedOdexFile.NotAnOdexFile;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ public final class DexFileFactory
/*     */ {
/*     */   public static DexBackedDexFile loadDexFile(File dexFile, int api)
/*     */     throws IOException
/*     */   {
/*  54 */     return loadDexFile(dexFile, new Opcodes(api)); } 
/*     */   public static DexBackedDexFile loadDexFile(File dexFile, Opcodes opcodes) throws IOException { // Byte code:
/*     */     //   0: aconst_null
/*     */     //   1: astore_2
/*     */     //   2: iconst_0
/*     */     //   3: istore_3
/*     */     //   4: new 16	java/util/zip/ZipFile
/*     */     //   7: dup
/*     */     //   8: aload_0
/*     */     //   9: invokespecial 35	java/util/zip/ZipFile:<init>	(Ljava/io/File;)V
/*     */     //   12: astore_2
/*     */     //   13: iconst_1
/*     */     //   14: istore_3
/*     */     //   15: aload_2
/*     */     //   16: ldc 4
/*     */     //   18: invokevirtual 37	java/util/zip/ZipFile:getEntry	(Ljava/lang/String;)Ljava/util/zip/ZipEntry;
/*     */     //   21: astore 4
/*     */     //   23: aload 4
/*     */     //   25: ifnonnull +24 -> 49
/*     */     //   28: new 18	org/jf/dexlib2/DexFileFactory$NoClassesDexException
/*     */     //   31: dup
/*     */     //   32: ldc 5
/*     */     //   34: iconst_1
/*     */     //   35: anewarray 13	java/lang/Object
/*     */     //   38: dup
/*     */     //   39: iconst_0
/*     */     //   40: aload_0
/*     */     //   41: invokevirtual 31	java/io/File:getName	()Ljava/lang/String;
/*     */     //   44: aastore
/*     */     //   45: invokespecial 40	org/jf/dexlib2/DexFileFactory$NoClassesDexException:<init>	(Ljava/lang/String;[Ljava/lang/Object;)V
/*     */     //   48: athrow
/*     */     //   49: aload 4
/*     */     //   51: invokevirtual 34	java/util/zip/ZipEntry:getSize	()J
/*     */     //   54: lstore 5
/*     */     //   56: lload 5
/*     */     //   58: ldc2_w 25
/*     */     //   61: lcmp
/*     */     //   62: ifge +24 -> 86
/*     */     //   65: new 24	org/jf/util/ExceptionWithContext
/*     */     //   68: dup
/*     */     //   69: ldc 3
/*     */     //   71: iconst_1
/*     */     //   72: anewarray 13	java/lang/Object
/*     */     //   75: dup
/*     */     //   76: iconst_0
/*     */     //   77: aload_0
/*     */     //   78: invokevirtual 31	java/io/File:getName	()Ljava/lang/String;
/*     */     //   81: aastore
/*     */     //   82: invokespecial 45	org/jf/util/ExceptionWithContext:<init>	(Ljava/lang/String;[Ljava/lang/Object;)V
/*     */     //   85: athrow
/*     */     //   86: lload 5
/*     */     //   88: ldc2_w 27
/*     */     //   91: lcmp
/*     */     //   92: ifle +24 -> 116
/*     */     //   95: new 24	org/jf/util/ExceptionWithContext
/*     */     //   98: dup
/*     */     //   99: ldc 2
/*     */     //   101: iconst_1
/*     */     //   102: anewarray 13	java/lang/Object
/*     */     //   105: dup
/*     */     //   106: iconst_0
/*     */     //   107: aload_0
/*     */     //   108: invokevirtual 31	java/io/File:getName	()Ljava/lang/String;
/*     */     //   111: aastore
/*     */     //   112: invokespecial 45	org/jf/util/ExceptionWithContext:<init>	(Ljava/lang/String;[Ljava/lang/Object;)V
/*     */     //   115: athrow
/*     */     //   116: lload 5
/*     */     //   118: l2i
/*     */     //   119: newarray byte
/*     */     //   121: astore 7
/*     */     //   123: aload_2
/*     */     //   124: aload 4
/*     */     //   126: invokevirtual 38	java/util/zip/ZipFile:getInputStream	(Ljava/util/zip/ZipEntry;)Ljava/io/InputStream;
/*     */     //   129: aload 7
/*     */     //   131: invokestatic 29	com/google/common/io/ByteStreams:readFully	(Ljava/io/InputStream;[B)V
/*     */     //   134: new 20	org/jf/dexlib2/dexbacked/DexBackedDexFile
/*     */     //   137: dup
/*     */     //   138: aload_1
/*     */     //   139: aload 7
/*     */     //   141: invokespecial 42	org/jf/dexlib2/dexbacked/DexBackedDexFile:<init>	(Lorg/jf/dexlib2/Opcodes;[B)V
/*     */     //   144: astore 8
/*     */     //   146: aload_2
/*     */     //   147: ifnull +12 -> 159
/*     */     //   150: aload_2
/*     */     //   151: invokevirtual 36	java/util/zip/ZipFile:close	()V
/*     */     //   154: goto +5 -> 159
/*     */     //   157: astore 9
/*     */     //   159: aload 8
/*     */     //   161: areturn
/*     */     //   162: astore 4
/*     */     //   164: iload_3
/*     */     //   165: ifeq +6 -> 171
/*     */     //   168: aload 4
/*     */     //   170: athrow
/*     */     //   171: aload_2
/*     */     //   172: ifnull +33 -> 205
/*     */     //   175: aload_2
/*     */     //   176: invokevirtual 36	java/util/zip/ZipFile:close	()V
/*     */     //   179: goto +26 -> 205
/*     */     //   182: astore 4
/*     */     //   184: goto +21 -> 205
/*     */     //   187: astore 10
/*     */     //   189: aload_2
/*     */     //   190: ifnull +12 -> 202
/*     */     //   193: aload_2
/*     */     //   194: invokevirtual 36	java/util/zip/ZipFile:close	()V
/*     */     //   197: goto +5 -> 202
/*     */     //   200: astore 11
/*     */     //   202: aload 10
/*     */     //   204: athrow
/*     */     //   205: new 8	java/io/BufferedInputStream
/*     */     //   208: dup
/*     */     //   209: new 10	java/io/FileInputStream
/*     */     //   212: dup
/*     */     //   213: aload_0
/*     */     //   214: invokespecial 33	java/io/FileInputStream:<init>	(Ljava/io/File;)V
/*     */     //   217: invokespecial 30	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
/*     */     //   220: astore 4
/*     */     //   222: aload_1
/*     */     //   223: aload 4
/*     */     //   225: invokestatic 43	org/jf/dexlib2/dexbacked/DexBackedDexFile:fromInputStream	(Lorg/jf/dexlib2/Opcodes;Ljava/io/InputStream;)Lorg/jf/dexlib2/dexbacked/DexBackedDexFile;
/*     */     //   228: areturn
/*     */     //   229: astore 5
/*     */     //   231: aload_1
/*     */     //   232: aload 4
/*     */     //   234: invokestatic 44	org/jf/dexlib2/dexbacked/DexBackedOdexFile:fromInputStream	(Lorg/jf/dexlib2/Opcodes;Ljava/io/InputStream;)Lorg/jf/dexlib2/dexbacked/DexBackedOdexFile;
/*     */     //   237: areturn
/*     */     //   238: astore 5
/*     */     //   240: new 24	org/jf/util/ExceptionWithContext
/*     */     //   243: dup
/*     */     //   244: ldc 1
/*     */     //   246: iconst_1
/*     */     //   247: anewarray 13	java/lang/Object
/*     */     //   250: dup
/*     */     //   251: iconst_0
/*     */     //   252: aload_0
/*     */     //   253: invokevirtual 32	java/io/File:getPath	()Ljava/lang/String;
/*     */     //   256: aastore
/*     */     //   257: invokespecial 45	org/jf/util/ExceptionWithContext:<init>	(Ljava/lang/String;[Ljava/lang/Object;)V
/*     */     //   260: athrow
/*     */     //
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   150	154	157	java/io/IOException
/*     */     //   4	146	162	java/io/IOException
/*     */     //   175	179	182	java/io/IOException
/*     */     //   4	146	187	finally
/*     */     //   162	171	187	finally
/*     */     //   187	189	187	finally
/*     */     //   193	197	200	java/io/IOException
/*     */     //   222	228	229	org/jf/dexlib2/dexbacked/DexBackedDexFile$NotADexFile
/*     */     //   231	237	238	org/jf/dexlib2/dexbacked/DexBackedOdexFile$NotAnOdexFile } 
/* 130 */   public static class NoClassesDexException extends ExceptionWithContext { public NoClassesDexException(String message, Object[] formatArgs) { super(formatArgs);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.DexFileFactory
 * JD-Core Version:    0.6.0
 */