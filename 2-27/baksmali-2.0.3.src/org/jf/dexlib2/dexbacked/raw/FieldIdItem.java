/*    */ package org.jf.dexlib2.dexbacked.raw;
/*    */ 
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
/*    */ import org.jf.dexlib2.util.AnnotatedBytes;
/*    */ 
/*    */ public class FieldIdItem
/*    */ {
/*    */   public static SectionAnnotator makeAnnotator(DexAnnotator annotator, MapItem mapItem)
/*    */   {
/* 50 */     return new SectionAnnotator(annotator, mapItem) {
/*    */       public String getItemName() {
/* 52 */         return "field_id_item";
/*    */       }
/*    */ 
/*    */       protected void annotateItem(AnnotatedBytes out, int itemIndex, String itemIdentity)
/*    */       {
/* 57 */         int classIndex = this.dexFile.readUshort(out.getCursor());
/* 58 */         out.annotate(2, "class_idx = %s", new Object[] { TypeIdItem.getReferenceAnnotation(this.dexFile, classIndex) });
/*    */ 
/* 60 */         int typeIndex = this.dexFile.readUshort(out.getCursor());
/* 61 */         out.annotate(2, "return_type_idx = %s", new Object[] { TypeIdItem.getReferenceAnnotation(this.dexFile, typeIndex) });
/*    */ 
/* 63 */         int nameIndex = this.dexFile.readSmallUint(out.getCursor());
/* 64 */         out.annotate(4, "name_idx = %s", new Object[] { StringIdItem.getReferenceAnnotation(this.dexFile, nameIndex) });
/*    */       }
/*    */     };
/*    */   }
/*    */ 
/*    */   public static String asString(DexBackedDexFile dexFile, int fieldIndex) {
/* 71 */     int fieldOffset = dexFile.getFieldIdItemOffset(fieldIndex);
/* 72 */     int classIndex = dexFile.readUshort(fieldOffset + 0);
/* 73 */     String classType = dexFile.getType(classIndex);
/*    */ 
/* 75 */     int typeIndex = dexFile.readUshort(fieldOffset + 2);
/* 76 */     String fieldType = dexFile.getType(typeIndex);
/*    */ 
/* 78 */     int nameIndex = dexFile.readSmallUint(fieldOffset + 4);
/* 79 */     String fieldName = dexFile.getString(nameIndex);
/*    */ 
/* 81 */     return String.format("%s->%s:%s", new Object[] { classType, fieldName, fieldType });
/*    */   }
/*    */ 
/*    */   public static String getReferenceAnnotation(DexBackedDexFile dexFile, int fieldIndex)
/*    */   {
/*    */     // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: iload_1
/*    */     //   2: invokestatic 21	org/jf/dexlib2/dexbacked/raw/FieldIdItem:asString	(Lorg/jf/dexlib2/dexbacked/DexBackedDexFile;I)Ljava/lang/String;
/*    */     //   5: astore_2
/*    */     //   6: ldc 3
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
/*    */     //   35: ldc 2
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
/*    */     //   0	26	27	java/lang/Exception
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.FieldIdItem
 * JD-Core Version:    0.6.0
 */