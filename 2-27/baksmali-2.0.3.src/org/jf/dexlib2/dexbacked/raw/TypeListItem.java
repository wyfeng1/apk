/*    */ package org.jf.dexlib2.dexbacked.raw;
/*    */ 
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
/*    */ import org.jf.dexlib2.util.AnnotatedBytes;
/*    */ 
/*    */ public class TypeListItem
/*    */ {
/*    */   public static SectionAnnotator makeAnnotator(DexAnnotator annotator, MapItem mapItem)
/*    */   {
/* 47 */     return new SectionAnnotator(annotator, mapItem) {
/*    */       public String getItemName() {
/* 49 */         return "type_list";
/*    */       }
/*    */ 
/*    */       protected void annotateItem(AnnotatedBytes out, int itemIndex, String itemIdentity)
/*    */       {
/* 54 */         int size = this.dexFile.readSmallUint(out.getCursor());
/* 55 */         out.annotate(4, "size: %d", new Object[] { Integer.valueOf(size) });
/*    */ 
/* 57 */         for (int i = 0; i < size; i++) {
/* 58 */           int typeIndex = this.dexFile.readUshort(out.getCursor());
/* 59 */           out.annotate(2, TypeIdItem.getReferenceAnnotation(this.dexFile, typeIndex), new Object[0]);
/*    */         }
/*    */       }
/*    */ 
/*    */       public int getItemAlignment() {
/* 64 */         return 4; }  } ; } 
/*    */   public static String getReferenceAnnotation(DexBackedDexFile dexFile, int typeListOffset) { // Byte code:
/*    */     //   0: iload_1
/*    */     //   1: ifne +6 -> 7
/*    */     //   4: ldc 4
/*    */     //   6: areturn
/*    */     //   7: aload_0
/*    */     //   8: iload_1
/*    */     //   9: invokestatic 24	org/jf/dexlib2/dexbacked/raw/TypeListItem:asString	(Lorg/jf/dexlib2/dexbacked/DexBackedDexFile;I)Ljava/lang/String;
/*    */     //   12: astore_2
/*    */     //   13: ldc 3
/*    */     //   15: iconst_2
/*    */     //   16: anewarray 7	java/lang/Object
/*    */     //   19: dup
/*    */     //   20: iconst_0
/*    */     //   21: iload_1
/*    */     //   22: invokestatic 16	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*    */     //   25: aastore
/*    */     //   26: dup
/*    */     //   27: iconst_1
/*    */     //   28: aload_2
/*    */     //   29: aastore
/*    */     //   30: invokestatic 17	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*    */     //   33: areturn
/*    */     //   34: astore_2
/*    */     //   35: aload_2
/*    */     //   36: getstatic 14	java/lang/System:err	Ljava/io/PrintStream;
/*    */     //   39: invokevirtual 15	java/lang/Exception:printStackTrace	(Ljava/io/PrintStream;)V
/*    */     //   42: ldc 2
/*    */     //   44: iconst_1
/*    */     //   45: anewarray 7	java/lang/Object
/*    */     //   48: dup
/*    */     //   49: iconst_0
/*    */     //   50: iload_1
/*    */     //   51: invokestatic 16	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*    */     //   54: aastore
/*    */     //   55: invokestatic 17	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*    */     //   58: areturn
/*    */     //
/*    */     // Exception table:
/*    */     //   from	to	target	type
/*    */     //   7	33	34	java/lang/Exception } 
/* 86 */   public static String asString(DexBackedDexFile dexFile, int typeListOffset) { if (typeListOffset == 0) {
/* 87 */       return "";
/*    */     }
/*    */ 
/* 90 */     StringBuilder sb = new StringBuilder();
/*    */ 
/* 92 */     int size = dexFile.readSmallUint(typeListOffset);
/* 93 */     for (int i = 0; i < size; i++) {
/* 94 */       int typeIndex = dexFile.readUshort(typeListOffset + 4 + i * 2);
/* 95 */       String type = dexFile.getType(typeIndex);
/* 96 */       sb.append(type);
/*    */     }
/* 98 */     return sb.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.TypeListItem
 * JD-Core Version:    0.6.0
 */