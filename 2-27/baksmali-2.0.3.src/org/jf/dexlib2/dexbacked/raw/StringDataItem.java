/*    */ package org.jf.dexlib2.dexbacked.raw;
/*    */ 
/*    */ import org.jf.dexlib2.dexbacked.DexReader;
/*    */ import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
/*    */ import org.jf.dexlib2.util.AnnotatedBytes;
/*    */ import org.jf.util.StringUtils;
/*    */ 
/*    */ public class StringDataItem
/*    */ {
/*    */   public static SectionAnnotator makeAnnotator(DexAnnotator annotator, MapItem mapItem)
/*    */   {
/* 45 */     return new SectionAnnotator(annotator, mapItem) {
/*    */       public String getItemName() {
/* 47 */         return "string_data_item";
/*    */       }
/*    */ 
/*    */       protected void annotateItem(AnnotatedBytes out, int itemIndex, String itemIdentity)
/*    */       {
/* 52 */         DexReader reader = this.dexFile.readerAt(out.getCursor());
/* 53 */         int utf16Length = reader.readSmallUleb128();
/* 54 */         out.annotateTo(reader.getOffset(), "utf16_size = %d", new Object[] { Integer.valueOf(utf16Length) });
/*    */ 
/* 56 */         String value = reader.readString(utf16Length);
/* 57 */         out.annotateTo(reader.getOffset() + 1, "data = \"%s\"", new Object[] { StringUtils.escapeString(value) });
/*    */       }
/*    */     };
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.StringDataItem
 * JD-Core Version:    0.6.0
 */