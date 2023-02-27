/*    */ package org.jf.dexlib2.dexbacked.raw;
/*    */ 
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
/*    */ import org.jf.dexlib2.util.AnnotatedBytes;
/*    */ 
/*    */ public class ProtoIdItem
/*    */ {
/*    */   public static SectionAnnotator makeAnnotator(DexAnnotator annotator, MapItem mapItem)
/*    */   {
/* 50 */     return new SectionAnnotator(annotator, mapItem) {
/*    */       public String getItemName() {
/* 52 */         return "proto_id_item";
/*    */       }
/*    */ 
/*    */       protected void annotateItem(AnnotatedBytes out, int itemIndex, String itemIdentity)
/*    */       {
/* 57 */         int shortyIndex = this.dexFile.readSmallUint(out.getCursor());
/* 58 */         out.annotate(4, "shorty_idx = %s", new Object[] { StringIdItem.getReferenceAnnotation(this.dexFile, shortyIndex) });
/*    */ 
/* 60 */         int returnTypeIndex = this.dexFile.readSmallUint(out.getCursor());
/* 61 */         out.annotate(4, "return_type_idx = %s", new Object[] { TypeIdItem.getReferenceAnnotation(this.dexFile, returnTypeIndex) });
/*    */ 
/* 63 */         int parametersOffset = this.dexFile.readSmallUint(out.getCursor());
/* 64 */         out.annotate(4, "parameters_off = %s", new Object[] { TypeListItem.getReferenceAnnotation(this.dexFile, parametersOffset) }); }  } ; } 
/*    */   public static String getReferenceAnnotation(DexBackedDexFile dexFile, int protoIndex) { // Byte code:
/*    */     //   0: aload_0
/*    */     //   1: iload_1
/*    */     //   2: invokestatic 25	org/jf/dexlib2/dexbacked/raw/ProtoIdItem:asString	(Lorg/jf/dexlib2/dexbacked/DexBackedDexFile;I)Ljava/lang/String;
/*    */     //   5: astore_2
/*    */     //   6: ldc 4
/*    */     //   8: iconst_2
/*    */     //   9: anewarray 7	java/lang/Object
/*    */     //   12: dup
/*    */     //   13: iconst_0
/*    */     //   14: iload_1
/*    */     //   15: invokestatic 17	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*    */     //   18: aastore
/*    */     //   19: dup
/*    */     //   20: iconst_1
/*    */     //   21: aload_2
/*    */     //   22: aastore
/*    */     //   23: invokestatic 18	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*    */     //   26: areturn
/*    */     //   27: astore_2
/*    */     //   28: aload_2
/*    */     //   29: getstatic 15	java/lang/System:err	Ljava/io/PrintStream;
/*    */     //   32: invokevirtual 16	java/lang/Exception:printStackTrace	(Ljava/io/PrintStream;)V
/*    */     //   35: ldc 3
/*    */     //   37: iconst_1
/*    */     //   38: anewarray 7	java/lang/Object
/*    */     //   41: dup
/*    */     //   42: iconst_0
/*    */     //   43: iload_1
/*    */     //   44: invokestatic 17	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
/*    */     //   47: aastore
/*    */     //   48: invokestatic 18	java/lang/String:format	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
/*    */     //   51: areturn
/*    */     //
/*    */     // Exception table:
/*    */     //   from	to	target	type
/*    */     //   0	26	27	java/lang/Exception } 
/* 82 */   public static String asString(DexBackedDexFile dexFile, int protoIndex) { int offset = dexFile.getProtoIdItemOffset(protoIndex);
/*    */ 
/* 84 */     StringBuilder sb = new StringBuilder();
/* 85 */     sb.append("(");
/*    */ 
/* 87 */     int parametersOffset = dexFile.readSmallUint(offset + 8);
/* 88 */     sb.append(TypeListItem.asString(dexFile, parametersOffset));
/* 89 */     sb.append(")");
/*    */ 
/* 91 */     int returnTypeIndex = dexFile.readSmallUint(offset + 4);
/* 92 */     String returnType = dexFile.getType(returnTypeIndex);
/* 93 */     sb.append(returnType);
/*    */ 
/* 95 */     return sb.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.ProtoIdItem
 * JD-Core Version:    0.6.0
 */