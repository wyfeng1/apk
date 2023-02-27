/*     */ package org.jf.dexlib2.dexbacked;
/*     */ 
/*     */ import com.google.common.io.ByteStreams;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Set;
/*     */ import org.jf.dexlib2.Opcodes;
/*     */ import org.jf.dexlib2.dexbacked.raw.HeaderItem;
/*     */ import org.jf.dexlib2.dexbacked.util.FixedSizeSet;
/*     */ import org.jf.dexlib2.iface.DexFile;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ public class DexBackedDexFile extends BaseDexBuffer
/*     */   implements DexFile
/*     */ {
/*     */   private final Opcodes opcodes;
/*     */   private final int stringCount;
/*     */   private final int stringStartOffset;
/*     */   private final int typeCount;
/*     */   private final int typeStartOffset;
/*     */   private final int protoCount;
/*     */   private final int protoStartOffset;
/*     */   private final int fieldCount;
/*     */   private final int fieldStartOffset;
/*     */   private final int methodCount;
/*     */   private final int methodStartOffset;
/*     */   private final int classCount;
/*     */   private final int classStartOffset;
/*     */ 
/*     */   private DexBackedDexFile(Opcodes opcodes, byte[] buf, int offset, boolean verifyMagic)
/*     */   {
/*  65 */     super(buf);
/*     */ 
/*  67 */     this.opcodes = opcodes;
/*     */ 
/*  69 */     if (verifyMagic) {
/*  70 */       verifyMagicAndByteOrder(buf, offset);
/*     */     }
/*     */ 
/*  73 */     this.stringCount = readSmallUint(56);
/*  74 */     this.stringStartOffset = readSmallUint(60);
/*  75 */     this.typeCount = readSmallUint(64);
/*  76 */     this.typeStartOffset = readSmallUint(68);
/*  77 */     this.protoCount = readSmallUint(72);
/*  78 */     this.protoStartOffset = readSmallUint(76);
/*  79 */     this.fieldCount = readSmallUint(80);
/*  80 */     this.fieldStartOffset = readSmallUint(84);
/*  81 */     this.methodCount = readSmallUint(88);
/*  82 */     this.methodStartOffset = readSmallUint(92);
/*  83 */     this.classCount = readSmallUint(96);
/*  84 */     this.classStartOffset = readSmallUint(100);
/*     */   }
/*     */ 
/*     */   public DexBackedDexFile(Opcodes opcodes, BaseDexBuffer buf) {
/*  88 */     this(opcodes, buf.buf);
/*     */   }
/*     */ 
/*     */   public DexBackedDexFile(Opcodes opcodes, byte[] buf)
/*     */   {
/*  96 */     this(opcodes, buf, 0, true);
/*     */   }
/*     */ 
/*     */   public static DexBackedDexFile fromInputStream(Opcodes opcodes, InputStream is) throws IOException
/*     */   {
/* 101 */     if (!is.markSupported()) {
/* 102 */       throw new IllegalArgumentException("InputStream must support mark");
/*     */     }
/* 104 */     is.mark(44);
/* 105 */     byte[] partialHeader = new byte[44];
/*     */     try {
/* 107 */       ByteStreams.readFully(is, partialHeader);
/*     */     } catch (EOFException ex) {
/* 109 */       throw new NotADexFile("File is too short");
/*     */     } finally {
/* 111 */       is.reset();
/*     */     }
/*     */ 
/* 114 */     verifyMagicAndByteOrder(partialHeader, 0);
/*     */ 
/* 116 */     byte[] buf = ByteStreams.toByteArray(is);
/* 117 */     return new DexBackedDexFile(opcodes, buf, 0, false);
/*     */   }
/*     */ 
/*     */   public Opcodes getOpcodes() {
/* 121 */     return this.opcodes;
/*     */   }
/*     */ 
/*     */   public boolean isOdexFile() {
/* 125 */     return false;
/*     */   }
/*     */ 
/*     */   public Set<? extends DexBackedClassDef> getClasses()
/*     */   {
/* 131 */     return new FixedSizeSet()
/*     */     {
/*     */       public DexBackedClassDef readItem(int index)
/*     */       {
/* 135 */         return new DexBackedClassDef(DexBackedDexFile.this, DexBackedDexFile.this.getClassDefItemOffset(index));
/*     */       }
/*     */ 
/*     */       public int size()
/*     */       {
/* 140 */         return DexBackedDexFile.this.classCount;
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   private static void verifyMagicAndByteOrder(byte[] buf, int offset) {
/* 146 */     if (!HeaderItem.verifyMagic(buf, offset)) {
/* 147 */       StringBuilder sb = new StringBuilder("Invalid magic value:");
/* 148 */       for (int i = 0; i < 8; i++) {
/* 149 */         sb.append(String.format(" %02x", new Object[] { Byte.valueOf(buf[i]) }));
/*     */       }
/* 151 */       throw new NotADexFile(sb.toString());
/*     */     }
/*     */ 
/* 154 */     int endian = HeaderItem.getEndian(buf, offset);
/* 155 */     if (endian == 2018915346) {
/* 156 */       throw new ExceptionWithContext("Big endian dex files are not currently supported", new Object[0]);
/*     */     }
/*     */ 
/* 159 */     if (endian != 305419896)
/* 160 */       throw new ExceptionWithContext("Invalid endian tag: 0x%x", new Object[] { Integer.valueOf(endian) });
/*     */   }
/*     */ 
/*     */   public int getStringIdItemOffset(int stringIndex)
/*     */   {
/* 165 */     if ((stringIndex < 0) || (stringIndex >= this.stringCount)) {
/* 166 */       throw new InvalidItemIndex(stringIndex, "String index out of bounds: %d", new Object[] { Integer.valueOf(stringIndex) });
/*     */     }
/* 168 */     return this.stringStartOffset + stringIndex * 4;
/*     */   }
/*     */ 
/*     */   public int getTypeIdItemOffset(int typeIndex) {
/* 172 */     if ((typeIndex < 0) || (typeIndex >= this.typeCount)) {
/* 173 */       throw new InvalidItemIndex(typeIndex, "Type index out of bounds: %d", new Object[] { Integer.valueOf(typeIndex) });
/*     */     }
/* 175 */     return this.typeStartOffset + typeIndex * 4;
/*     */   }
/*     */ 
/*     */   public int getFieldIdItemOffset(int fieldIndex) {
/* 179 */     if ((fieldIndex < 0) || (fieldIndex >= this.fieldCount)) {
/* 180 */       throw new InvalidItemIndex(fieldIndex, "Field index out of bounds: %d", new Object[] { Integer.valueOf(fieldIndex) });
/*     */     }
/* 182 */     return this.fieldStartOffset + fieldIndex * 8;
/*     */   }
/*     */ 
/*     */   public int getMethodIdItemOffset(int methodIndex) {
/* 186 */     if ((methodIndex < 0) || (methodIndex >= this.methodCount)) {
/* 187 */       throw new InvalidItemIndex(methodIndex, "Method index out of bounds: %d", new Object[] { Integer.valueOf(methodIndex) });
/*     */     }
/* 189 */     return this.methodStartOffset + methodIndex * 8;
/*     */   }
/*     */ 
/*     */   public int getProtoIdItemOffset(int protoIndex) {
/* 193 */     if ((protoIndex < 0) || (protoIndex >= this.protoCount)) {
/* 194 */       throw new InvalidItemIndex(protoIndex, "Proto index out of bounds: %d", new Object[] { Integer.valueOf(protoIndex) });
/*     */     }
/* 196 */     return this.protoStartOffset + protoIndex * 12;
/*     */   }
/*     */ 
/*     */   public int getClassDefItemOffset(int classIndex) {
/* 200 */     if ((classIndex < 0) || (classIndex >= this.classCount)) {
/* 201 */       throw new InvalidItemIndex(classIndex, "Class index out of bounds: %d", new Object[] { Integer.valueOf(classIndex) });
/*     */     }
/* 203 */     return this.classStartOffset + classIndex * 32;
/*     */   }
/*     */ 
/*     */   public String getString(int stringIndex)
/*     */   {
/* 212 */     int stringOffset = getStringIdItemOffset(stringIndex);
/* 213 */     int stringDataOffset = readSmallUint(stringOffset);
/* 214 */     DexReader reader = readerAt(stringDataOffset);
/* 215 */     int utf16Length = reader.readSmallUleb128();
/* 216 */     return reader.readString(utf16Length);
/*     */   }
/*     */ 
/*     */   public String getOptionalString(int stringIndex)
/*     */   {
/* 221 */     if (stringIndex == -1) {
/* 222 */       return null;
/*     */     }
/* 224 */     return getString(stringIndex);
/*     */   }
/*     */ 
/*     */   public String getType(int typeIndex)
/*     */   {
/* 229 */     int typeOffset = getTypeIdItemOffset(typeIndex);
/* 230 */     int stringIndex = readSmallUint(typeOffset);
/* 231 */     return getString(stringIndex);
/*     */   }
/*     */ 
/*     */   public String getOptionalType(int typeIndex)
/*     */   {
/* 236 */     if (typeIndex == -1) {
/* 237 */       return null;
/*     */     }
/* 239 */     return getType(typeIndex);
/*     */   }
/*     */ 
/*     */   public DexReader readerAt(int offset)
/*     */   {
/* 245 */     return new DexReader(this, offset);
/*     */   }
/*     */ 
/*     */   public static class InvalidItemIndex extends ExceptionWithContext
/*     */   {
/*     */     private final int itemIndex;
/*     */ 
/*     */     public InvalidItemIndex(int itemIndex, String message, Object[] formatArgs)
/*     */     {
/* 274 */       super(formatArgs);
/* 275 */       this.itemIndex = itemIndex;
/*     */     }
/*     */ 
/*     */     public int getInvalidIndex() {
/* 279 */       return this.itemIndex;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class NotADexFile extends RuntimeException
/*     */   {
/*     */     public NotADexFile()
/*     */     {
/*     */     }
/*     */ 
/*     */     public NotADexFile(String message)
/*     */     {
/* 257 */       super();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.DexBackedDexFile
 * JD-Core Version:    0.6.0
 */