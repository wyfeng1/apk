/*     */ package org.jf.dexlib2.dexbacked.raw;
/*     */ 
/*     */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*     */ import org.jf.dexlib2.dexbacked.DexReader;
/*     */ import org.jf.dexlib2.util.AnnotatedBytes;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ public class EncodedValue
/*     */ {
/*     */   public static void annotateEncodedValue(AnnotatedBytes out, DexReader reader)
/*     */   {
/*  42 */     int valueArgType = reader.readUbyte();
/*     */ 
/*  44 */     int valueArg = valueArgType >>> 5;
/*  45 */     int valueType = valueArgType & 0x1F;
/*     */     int intValue;
/*     */     int fieldIndex;
/*  47 */     switch (valueType) {
/*     */     case 0:
/*  49 */       out.annotate(1, "valueArg = %d, valueType = 0x%x: byte", new Object[] { Integer.valueOf(valueArg), Integer.valueOf(valueType) });
/*  50 */       intValue = reader.readByte();
/*  51 */       out.annotate(1, "value = 0x%x", new Object[] { Integer.valueOf(intValue) });
/*  52 */       break;
/*     */     case 2:
/*  54 */       out.annotate(1, "valueArg = %d, valueType = 0x%x: short", new Object[] { Integer.valueOf(valueArg), Integer.valueOf(valueType) });
/*  55 */       intValue = reader.readSizedInt(valueArg + 1);
/*  56 */       out.annotate(valueArg + 1, "value = 0x%x", new Object[] { Integer.valueOf(intValue) });
/*  57 */       break;
/*     */     case 3:
/*  59 */       out.annotate(1, "valueArg = %d, valueType = 0x%x: char", new Object[] { Integer.valueOf(valueArg), Integer.valueOf(valueType) });
/*  60 */       intValue = reader.readSizedSmallUint(valueArg + 1);
/*  61 */       out.annotate(valueArg + 1, "value = 0x%x", new Object[] { Integer.valueOf(intValue) });
/*  62 */       break;
/*     */     case 4:
/*  64 */       out.annotate(1, "valueArg = %d, valueType = 0x%x: int", new Object[] { Integer.valueOf(valueArg), Integer.valueOf(valueType) });
/*  65 */       intValue = reader.readSizedInt(valueArg + 1);
/*  66 */       out.annotate(valueArg + 1, "value = 0x%x", new Object[] { Integer.valueOf(intValue) });
/*  67 */       break;
/*     */     case 6:
/*  69 */       out.annotate(1, "valueArg = %d, valueType = 0x%x: long", new Object[] { Integer.valueOf(valueArg), Integer.valueOf(valueType) });
/*  70 */       long longValue = reader.readSizedLong(valueArg + 1);
/*  71 */       out.annotate(valueArg + 1, "value = 0x%x", new Object[] { Long.valueOf(longValue) });
/*  72 */       break;
/*     */     case 16:
/*  74 */       out.annotate(1, "valueArg = %d, valueType = 0x%x: float", new Object[] { Integer.valueOf(valueArg), Integer.valueOf(valueType) });
/*  75 */       float floatValue = Float.intBitsToFloat(reader.readSizedRightExtendedInt(valueArg + 1));
/*  76 */       out.annotate(valueArg + 1, "value = %f", new Object[] { Float.valueOf(floatValue) });
/*  77 */       break;
/*     */     case 17:
/*  79 */       out.annotate(1, "valueArg = %d, valueType = 0x%x: double", new Object[] { Integer.valueOf(valueArg), Integer.valueOf(valueType) });
/*  80 */       double doubleValue = Double.longBitsToDouble(reader.readSizedRightExtendedLong(valueArg + 1));
/*  81 */       out.annotate(valueArg + 1, "value = %f", new Object[] { Double.valueOf(doubleValue) });
/*  82 */       break;
/*     */     case 23:
/*  84 */       out.annotate(1, "valueArg = %d, valueType = 0x%x: string", new Object[] { Integer.valueOf(valueArg), Integer.valueOf(valueType) });
/*  85 */       int stringIndex = reader.readSizedSmallUint(valueArg + 1);
/*  86 */       out.annotate(valueArg + 1, "value = %s", new Object[] { StringIdItem.getReferenceAnnotation((DexBackedDexFile)reader.dexBuf, stringIndex, true) });
/*     */ 
/*  88 */       break;
/*     */     case 24:
/*  90 */       out.annotate(1, "valueArg = %d, valueType = 0x%x: type", new Object[] { Integer.valueOf(valueArg), Integer.valueOf(valueType) });
/*  91 */       int typeIndex = reader.readSizedSmallUint(valueArg + 1);
/*  92 */       out.annotate(valueArg + 1, "value = %s", new Object[] { TypeIdItem.getReferenceAnnotation((DexBackedDexFile)reader.dexBuf, typeIndex) });
/*  93 */       break;
/*     */     case 25:
/*  95 */       out.annotate(1, "valueArg = %d, valueType = 0x%x: field", new Object[] { Integer.valueOf(valueArg), Integer.valueOf(valueType) });
/*  96 */       fieldIndex = reader.readSizedSmallUint(valueArg + 1);
/*  97 */       out.annotate(valueArg + 1, "value = %s", new Object[] { FieldIdItem.getReferenceAnnotation((DexBackedDexFile)reader.dexBuf, fieldIndex) });
/*  98 */       break;
/*     */     case 26:
/* 100 */       out.annotate(1, "valueArg = %d, valueType = 0x%x: method", new Object[] { Integer.valueOf(valueArg), Integer.valueOf(valueType) });
/* 101 */       int methodIndex = reader.readSizedSmallUint(valueArg + 1);
/* 102 */       out.annotate(valueArg + 1, "value = %s", new Object[] { MethodIdItem.getReferenceAnnotation((DexBackedDexFile)reader.dexBuf, methodIndex) });
/* 103 */       break;
/*     */     case 27:
/* 105 */       out.annotate(1, "valueArg = %d, valueType = 0x%x: enum", new Object[] { Integer.valueOf(valueArg), Integer.valueOf(valueType) });
/* 106 */       fieldIndex = reader.readSizedSmallUint(valueArg + 1);
/* 107 */       out.annotate(valueArg + 1, "value = %s", new Object[] { FieldIdItem.getReferenceAnnotation((DexBackedDexFile)reader.dexBuf, fieldIndex) });
/* 108 */       break;
/*     */     case 28:
/* 110 */       out.annotate(1, "valueArg = %d, valueType = 0x%x: array", new Object[] { Integer.valueOf(valueArg), Integer.valueOf(valueType) });
/* 111 */       annotateEncodedArray(out, reader);
/* 112 */       break;
/*     */     case 29:
/* 114 */       out.annotate(1, "valueArg = %d, valueType = 0x%x: annotation", new Object[] { Integer.valueOf(valueArg), Integer.valueOf(valueType) });
/* 115 */       annotateEncodedAnnotation(out, reader);
/* 116 */       break;
/*     */     case 30:
/* 118 */       out.annotate(1, "valueArg = %d, valueType = 0x%x: null", new Object[] { Integer.valueOf(valueArg), Integer.valueOf(valueType) });
/* 119 */       break;
/*     */     case 31:
/* 121 */       out.annotate(1, "valueArg = %d, valueType = 0x%x: boolean, value=%s", new Object[] { Integer.valueOf(valueArg), Integer.valueOf(valueType), Boolean.valueOf(valueArg == 1 ? 1 : false) });
/* 122 */       break;
/*     */     case 1:
/*     */     case 5:
/*     */     case 7:
/*     */     case 8:
/*     */     case 9:
/*     */     case 10:
/*     */     case 11:
/*     */     case 12:
/*     */     case 13:
/*     */     case 14:
/*     */     case 15:
/*     */     case 18:
/*     */     case 19:
/*     */     case 20:
/*     */     case 21:
/*     */     case 22:
/*     */     default:
/* 124 */       throw new ExceptionWithContext("Invalid encoded value type 0x%x at offset 0x%x", new Object[] { Integer.valueOf(valueType), Integer.valueOf(out.getCursor()) });
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void annotateEncodedAnnotation(AnnotatedBytes out, DexReader reader)
/*     */   {
/* 130 */     assert (out.getCursor() == reader.getOffset());
/*     */ 
/* 132 */     int typeIndex = reader.readSmallUleb128();
/* 133 */     out.annotateTo(reader.getOffset(), TypeIdItem.getReferenceAnnotation((DexBackedDexFile)reader.dexBuf, typeIndex), new Object[0]);
/*     */ 
/* 135 */     int size = reader.readSmallUleb128();
/* 136 */     out.annotateTo(reader.getOffset(), "size: %d", new Object[] { Integer.valueOf(size) });
/*     */ 
/* 138 */     for (int i = 0; i < size; i++) {
/* 139 */       out.annotate(0, "element[%d]", new Object[] { Integer.valueOf(i) });
/* 140 */       out.indent();
/*     */ 
/* 142 */       int nameIndex = reader.readSmallUleb128();
/* 143 */       out.annotateTo(reader.getOffset(), "name = %s", new Object[] { StringIdItem.getReferenceAnnotation((DexBackedDexFile)reader.dexBuf, nameIndex) });
/*     */ 
/* 146 */       annotateEncodedValue(out, reader);
/*     */ 
/* 148 */       out.deindent();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void annotateEncodedArray(AnnotatedBytes out, DexReader reader) {
/* 153 */     assert (out.getCursor() == reader.getOffset());
/*     */ 
/* 155 */     int size = reader.readSmallUleb128();
/* 156 */     out.annotateTo(reader.getOffset(), "size: %d", new Object[] { Integer.valueOf(size) });
/*     */ 
/* 158 */     for (int i = 0; i < size; i++) {
/* 159 */       out.annotate(0, "element[%d]", new Object[] { Integer.valueOf(i) });
/* 160 */       out.indent();
/*     */ 
/* 162 */       annotateEncodedValue(out, reader);
/*     */ 
/* 164 */       out.deindent();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.EncodedValue
 * JD-Core Version:    0.6.0
 */