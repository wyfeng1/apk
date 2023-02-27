/*    */ package org.jf.dexlib2.dexbacked.raw;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
/*    */ import java.util.List;
/*    */ import org.jf.dexlib2.Opcodes;
/*    */ import org.jf.dexlib2.dexbacked.BaseDexBuffer;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.util.FixedSizeList;
/*    */ import org.jf.dexlib2.util.AnnotatedBytes;
/*    */ 
/*    */ public class RawDexFile extends DexBackedDexFile
/*    */ {
/*    */   public final HeaderItem headerItem;
/*    */ 
/*    */   public RawDexFile(Opcodes opcodes, BaseDexBuffer buf)
/*    */   {
/* 51 */     super(opcodes, buf);
/* 52 */     this.headerItem = new HeaderItem(this);
/*    */   }
/*    */ 
/*    */   public int getMapOffset()
/*    */   {
/* 66 */     return this.headerItem.getMapOffset();
/*    */   }
/*    */ 
/*    */   public List<MapItem> getMapItems()
/*    */   {
/* 80 */     int mapOffset = getMapOffset();
/* 81 */     int mapSize = readSmallUint(mapOffset);
/*    */ 
/* 83 */     return new FixedSizeList(mapOffset, mapSize)
/*    */     {
/*    */       public MapItem readItem(int index) {
/* 86 */         int mapItemOffset = this.val$mapOffset + 4 + index * 12;
/* 87 */         return new MapItem(RawDexFile.this, mapItemOffset);
/*    */       }
/*    */ 
/*    */       public int size() {
/* 91 */         return this.val$mapSize;
/*    */       } } ;
/*    */   }
/*    */ 
/*    */   public void writeAnnotations(Writer out, AnnotatedBytes annotatedBytes) throws IOException {
/* 97 */     annotatedBytes.writeAnnotations(out, getBuf());
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.RawDexFile
 * JD-Core Version:    0.6.0
 */