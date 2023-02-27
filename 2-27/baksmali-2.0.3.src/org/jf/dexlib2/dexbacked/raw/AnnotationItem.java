/*    */ package org.jf.dexlib2.dexbacked.raw;
/*    */ 
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.DexReader;
/*    */ import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
/*    */ import org.jf.dexlib2.util.AnnotatedBytes;
/*    */ 
/*    */ public class AnnotationItem
/*    */ {
/*    */   public static SectionAnnotator makeAnnotator(DexAnnotator annotator, MapItem mapItem)
/*    */   {
/* 48 */     return new SectionAnnotator(annotator, mapItem) {
/*    */       public String getItemName() {
/* 50 */         return "annotation_item";
/*    */       }
/*    */ 
/*    */       protected void annotateItem(AnnotatedBytes out, int itemIndex, String itemIdentity)
/*    */       {
/* 55 */         int visibility = this.dexFile.readUbyte(out.getCursor());
/* 56 */         out.annotate(1, "visibility = %d: %s", new Object[] { Integer.valueOf(visibility), AnnotationItem.access$000(visibility) });
/*    */ 
/* 58 */         DexReader reader = this.dexFile.readerAt(out.getCursor());
/*    */ 
/* 60 */         EncodedValue.annotateEncodedAnnotation(out, reader);
/*    */       } } ;
/*    */   }
/*    */ 
/*    */   private static String getAnnotationVisibility(int visibility) {
/* 66 */     switch (visibility) {
/*    */     case 0:
/* 68 */       return "build";
/*    */     case 1:
/* 70 */       return "runtime";
/*    */     case 2:
/* 72 */       return "system";
/*    */     }
/* 74 */     return "invalid visibility";
/*    */   }
/*    */ 
/*    */   public static String getReferenceAnnotation(DexBackedDexFile dexFile, int annotationItemOffset)
/*    */   {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: iload_1
/*    */     //   2: invokevirtual 21	org/jf/dexlib2/dexbacked/DexBackedDexFile:readerAt	(I)Lorg/jf/dexlib2/dexbacked/DexReader;
/*    */     //   5: astore_2
/*    */     //   6: aload_2
/*    */     //   7: invokevirtual 23	org/jf/dexlib2/dexbacked/DexReader:readUbyte	()I
/*    */     //   10: pop
/*    */     //   11: aload_2
/*    */     //   12: invokevirtual 22	org/jf/dexlib2/dexbacked/DexReader:readSmallUleb128	()I
/*    */     //   15: istore_3
/*    */     //   16: aload_0
/*    */     //   17: iload_3
/*    */     //   18: invokevirtual 20	org/jf/dexlib2/dexbacked/DexBackedDexFile:getType	(I)Ljava/lang/String;
/*    */     //   21: astore 4
/*    */     //   23: ldc 2
/*    */     //   25: iconst_2
/*    */     //   26: anewarray 9	java/lang/Object
/*    */     //   29: dup
/*    */     //   30: iconst_0
/*    */     //   31: iload_1
/*    */     //   32: invokestatic 18	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*    */     //   35: aastore
/*    */     //   36: dup
/*    */     //   37: iconst_1
/*    */     //   38: aload 4
/*    */     //   40: aastore
/*    */     //   41: invokestatic 19	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*    */     //   44: areturn
/*    */     //   45: astore_2
/*    */     //   46: aload_2
/*    */     //   47: getstatic 16	java/lang/System:err	Ljava/io/PrintStream;
/*    */     //   50: invokevirtual 17	java/lang/Exception:printStackTrace	(Ljava/io/PrintStream;)V
/*    */     //   53: ldc 1
/*    */     //   55: iconst_1
/*    */     //   56: anewarray 9	java/lang/Object
/*    */     //   59: dup
/*    */     //   60: iconst_0
/*    */     //   61: iload_1
/*    */     //   62: invokestatic 18	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*    */     //   65: aastore
/*    */     //   66: invokestatic 19	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*    */     //   69: areturn
/*    */     //
/*    */     // Exception table:
/*    */     //   from	to	target	type
/*    */     //   0	44	45	java/lang/Exception
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.AnnotationItem
 * JD-Core Version:    0.6.0
 */