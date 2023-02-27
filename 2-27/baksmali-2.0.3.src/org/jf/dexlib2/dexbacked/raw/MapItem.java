/*    */ package org.jf.dexlib2.dexbacked.raw;
/*    */ 
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
/*    */ import org.jf.dexlib2.util.AnnotatedBytes;
/*    */ 
/*    */ public class MapItem
/*    */ {
/*    */   private final DexBackedDexFile dexFile;
/*    */   private final int offset;
/*    */ 
/*    */   public MapItem(DexBackedDexFile dexFile, int offset)
/*    */   {
/* 52 */     this.dexFile = dexFile;
/* 53 */     this.offset = offset;
/*    */   }
/*    */ 
/*    */   public int getType() {
/* 57 */     return this.dexFile.readUshort(this.offset + 0);
/*    */   }
/*    */ 
/*    */   public int getItemCount()
/*    */   {
/* 66 */     return this.dexFile.readSmallUint(this.offset + 4);
/*    */   }
/*    */ 
/*    */   public int getOffset() {
/* 70 */     return this.dexFile.readSmallUint(this.offset + 8);
/*    */   }
/*    */ 
/*    */   public static SectionAnnotator makeAnnotator(DexAnnotator annotator, MapItem mapItem)
/*    */   {
/* 75 */     return new SectionAnnotator(annotator, mapItem) {
/*    */       public String getItemName() {
/* 77 */         return "map_item";
/*    */       }
/*    */ 
/*    */       protected void annotateItem(AnnotatedBytes out, int itemIndex, String itemIdentity)
/*    */       {
/* 82 */         int itemType = this.dexFile.readUshort(out.getCursor());
/* 83 */         out.annotate(2, "type = 0x%x: %s", new Object[] { Integer.valueOf(itemType), ItemType.getItemTypeName(itemType) });
/*    */ 
/* 85 */         out.annotate(2, "unused", new Object[0]);
/*    */ 
/* 87 */         int size = this.dexFile.readSmallUint(out.getCursor());
/* 88 */         out.annotate(4, "size = %d", new Object[] { Integer.valueOf(size) });
/*    */ 
/* 90 */         int offset = this.dexFile.readSmallUint(out.getCursor());
/* 91 */         out.annotate(4, "offset = 0x%x", new Object[] { Integer.valueOf(offset) });
/*    */       }
/*    */ 
/*    */       public void annotateSection(AnnotatedBytes out) {
/* 95 */         out.moveTo(this.sectionOffset);
/* 96 */         int mapItemCount = this.dexFile.readSmallUint(out.getCursor());
/* 97 */         out.annotate(4, "size = %d", new Object[] { Integer.valueOf(mapItemCount) });
/*    */ 
/* 99 */         super.annotateSectionInner(out, mapItemCount);
/*    */       }
/*    */     };
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.MapItem
 * JD-Core Version:    0.6.0
 */