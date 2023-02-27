/*     */ package org.jf.dexlib2.dexbacked.raw;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*     */ import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
/*     */ import org.jf.dexlib2.util.AnnotatedBytes;
/*     */ import org.jf.util.StringUtils;
/*     */ 
/*     */ public class StringIdItem
/*     */ {
/*     */   public static SectionAnnotator makeAnnotator(DexAnnotator annotator, MapItem mapItem)
/*     */   {
/*  47 */     return new SectionAnnotator(annotator, mapItem) {
/*     */       public String getItemName() {
/*  49 */         return "string_id_item";
/*     */       }
/*     */ 
/*     */       public void annotateItem(AnnotatedBytes out, int itemIndex, String itemIdentity)
/*     */       {
/*  54 */         int stringDataOffset = this.dexFile.readSmallUint(out.getCursor());
/*     */         try {
/*  56 */           String stringValue = this.dexFile.getString(itemIndex);
/*  57 */           out.annotate(4, "string_data_item[0x%x]: \"%s\"", new Object[] { Integer.valueOf(stringDataOffset), StringUtils.escapeString(stringValue) });
/*     */ 
/*  59 */           return;
/*     */         } catch (Exception ex) {
/*  61 */           System.err.print("Error while resolving string value at index: ");
/*  62 */           System.err.print(itemIndex);
/*  63 */           ex.printStackTrace(System.err);
/*     */ 
/*  66 */           out.annotate(4, "string_id_item[0x%x]", new Object[] { Integer.valueOf(stringDataOffset) });
/*     */         }
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   public static String getReferenceAnnotation(DexBackedDexFile dexFile, int stringIndex) {
/*  73 */     return getReferenceAnnotation(dexFile, stringIndex, false); } 
/*     */   public static String getReferenceAnnotation(DexBackedDexFile dexFile, int stringIndex, boolean quote) { // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: iload_1
/*     */     //   2: invokevirtual 18	org/jf/dexlib2/dexbacked/DexBackedDexFile:getString	(I)Ljava/lang/String;
/*     */     //   5: astore_3
/*     */     //   6: iload_2
/*     */     //   7: ifeq +20 -> 27
/*     */     //   10: ldc 1
/*     */     //   12: iconst_1
/*     */     //   13: anewarray 7	java/lang/Object
/*     */     //   16: dup
/*     */     //   17: iconst_0
/*     */     //   18: aload_3
/*     */     //   19: invokestatic 22	org/jf/util/StringUtils:escapeString	(Ljava/lang/String;)Ljava/lang/String;
/*     */     //   22: aastore
/*     */     //   23: invokestatic 17	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   26: astore_3
/*     */     //   27: ldc 3
/*     */     //   29: iconst_2
/*     */     //   30: anewarray 7	java/lang/Object
/*     */     //   33: dup
/*     */     //   34: iconst_0
/*     */     //   35: iload_1
/*     */     //   36: invokestatic 16	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*     */     //   39: aastore
/*     */     //   40: dup
/*     */     //   41: iconst_1
/*     */     //   42: aload_3
/*     */     //   43: aastore
/*     */     //   44: invokestatic 17	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   47: areturn
/*     */     //   48: astore_3
/*     */     //   49: aload_3
/*     */     //   50: getstatic 14	java/lang/System:err	Ljava/io/PrintStream;
/*     */     //   53: invokevirtual 15	java/lang/Exception:printStackTrace	(Ljava/io/PrintStream;)V
/*     */     //   56: ldc 2
/*     */     //   58: iconst_1
/*     */     //   59: anewarray 7	java/lang/Object
/*     */     //   62: dup
/*     */     //   63: iconst_0
/*     */     //   64: iload_1
/*     */     //   65: invokestatic 16	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*     */     //   68: aastore
/*     */     //   69: invokestatic 17	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   72: areturn
/*     */     //
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   0	47	48	java/lang/Exception } 
/*  93 */   public static String getOptionalReferenceAnnotation(DexBackedDexFile dexFile, int stringIndex) { return getOptionalReferenceAnnotation(dexFile, stringIndex, false);
/*     */   }
/*     */ 
/*     */   public static String getOptionalReferenceAnnotation(DexBackedDexFile dexFile, int stringIndex, boolean quote)
/*     */   {
/*  99 */     if (stringIndex == -1) {
/* 100 */       return "string_id_item[NO_INDEX]";
/*     */     }
/* 102 */     return getReferenceAnnotation(dexFile, stringIndex, quote);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.StringIdItem
 * JD-Core Version:    0.6.0
 */