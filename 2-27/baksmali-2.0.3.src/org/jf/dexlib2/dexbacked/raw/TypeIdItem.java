/*    */ package org.jf.dexlib2.dexbacked.raw;
/*    */ 
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
/*    */ import org.jf.dexlib2.util.AnnotatedBytes;
/*    */ 
/*    */ public class TypeIdItem
/*    */ {
/*    */   public static SectionAnnotator makeAnnotator(DexAnnotator annotator, MapItem mapItem)
/*    */   {
/* 46 */     return new SectionAnnotator(annotator, mapItem) {
/*    */       public String getItemName() {
/* 48 */         return "type_id_item";
/*    */       }
/*    */ 
/*    */       protected void annotateItem(AnnotatedBytes out, int itemIndex, String itemIdentity)
/*    */       {
/* 53 */         int stringIndex = this.dexFile.readSmallUint(out.getCursor());
/* 54 */         out.annotate(4, StringIdItem.getReferenceAnnotation(this.dexFile, stringIndex), new Object[0]); }  } ; } 
/*    */   public static String getReferenceAnnotation(DexBackedDexFile dexFile, int typeIndex) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: iload_1
/*    */     //   2: invokevirtual 16	org/jf/dexlib2/dexbacked/DexBackedDexFile:getType	(I)Ljava/lang/String;
/*    */     //   5: astore_2
/*    */     //   6: ldc 2
/*    */     //   8: iconst_2
/*    */     //   9: anewarray 6	java/lang/Object
/*    */     //   12: dup
/*    */     //   13: iconst_0
/*    */     //   14: iload_1
/*    */     //   15: invokestatic 14	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*    */     //   18: aastore
/*    */     //   19: dup
/*    */     //   20: iconst_1
/*    */     //   21: aload_2
/*    */     //   22: aastore
/*    */     //   23: invokestatic 15	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*    */     //   26: areturn
/*    */     //   27: astore_2
/*    */     //   28: aload_2
/*    */     //   29: getstatic 12	java/lang/System:err	Ljava/io/PrintStream;
/*    */     //   32: invokevirtual 13	java/lang/Exception:printStackTrace	(Ljava/io/PrintStream;)V
/*    */     //   35: ldc 1
/*    */     //   37: iconst_1
/*    */     //   38: anewarray 6	java/lang/Object
/*    */     //   41: dup
/*    */     //   42: iconst_0
/*    */     //   43: iload_1
/*    */     //   44: invokestatic 14	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*    */     //   47: aastore
/*    */     //   48: invokestatic 15	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*    */     //   51: areturn
/*    */     //
/*    */     // Exception table:
/*    */     //   from	to	target	type
/*    */     //   0	26	27	java/lang/Exception } 
/* 72 */   public static String getOptionalReferenceAnnotation(DexBackedDexFile dexFile, int typeIndex) { if (typeIndex == -1) {
/* 73 */       return "type_id_item[NO_INDEX]";
/*    */     }
/* 75 */     return getReferenceAnnotation(dexFile, typeIndex);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.TypeIdItem
 * JD-Core Version:    0.6.0
 */