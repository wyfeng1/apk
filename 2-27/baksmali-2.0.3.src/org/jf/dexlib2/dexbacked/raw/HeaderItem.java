/*     */ package org.jf.dexlib2.dexbacked.raw;
/*     */ 
/*     */ import org.jf.dexlib2.dexbacked.BaseDexBuffer;
/*     */ import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
/*     */ import org.jf.dexlib2.util.AnnotatedBytes;
/*     */ import org.jf.util.StringUtils;
/*     */ 
/*     */ public class HeaderItem
/*     */ {
/*  45 */   public static final byte[][] MAGIC_VALUES = { { 100, 101, 120, 10, 48, 51, 53, 0 }, { 100, 101, 120, 10, 48, 51, 54, 0 } };
/*     */   private RawDexFile dexFile;
/*     */ 
/*     */   public HeaderItem(RawDexFile dexFile)
/*     */   {
/*  90 */     this.dexFile = dexFile;
/*     */   }
/*     */ 
/*     */   public int getMapOffset()
/*     */   {
/* 102 */     return this.dexFile.readSmallUint(52);
/*     */   }
/*     */ 
/*     */   public static SectionAnnotator makeAnnotator(DexAnnotator annotator, MapItem mapItem)
/*     */   {
/* 159 */     return new SectionAnnotator(annotator, mapItem) {
/*     */       public String getItemName() {
/* 161 */         return "header_item";
/*     */       }
/*     */ 
/*     */       protected void annotateItem(AnnotatedBytes out, int itemIndex, String itemIdentity)
/*     */       {
/* 166 */         int startOffset = out.getCursor();
/*     */ 
/* 169 */         StringBuilder magicBuilder = new StringBuilder();
/* 170 */         for (int i = 0; i < 8; i++) {
/* 171 */           magicBuilder.append((char)this.dexFile.readUbyte(startOffset + i));
/*     */         }
/*     */ 
/* 174 */         out.annotate(8, "magic: %s", new Object[] { StringUtils.escapeString(magicBuilder.toString()) });
/* 175 */         out.annotate(4, "checksum", new Object[0]);
/* 176 */         out.annotate(20, "signature", new Object[0]);
/* 177 */         out.annotate(4, "file_size: %d", new Object[] { Integer.valueOf(this.dexFile.readInt(out.getCursor())) });
/*     */ 
/* 179 */         int headerSize = this.dexFile.readInt(out.getCursor());
/* 180 */         out.annotate(4, "header_size: %d", new Object[] { Integer.valueOf(headerSize) });
/*     */ 
/* 182 */         int endianTag = this.dexFile.readInt(out.getCursor());
/* 183 */         out.annotate(4, "endian_tag: 0x%x (%s)", new Object[] { Integer.valueOf(endianTag), HeaderItem.access$000(endianTag) });
/*     */ 
/* 185 */         out.annotate(4, "link_size: %d", new Object[] { Integer.valueOf(this.dexFile.readInt(out.getCursor())) });
/* 186 */         out.annotate(4, "link_offset: 0x%x", new Object[] { Integer.valueOf(this.dexFile.readInt(out.getCursor())) });
/*     */ 
/* 188 */         out.annotate(4, "map_off: 0x%x", new Object[] { Integer.valueOf(this.dexFile.readInt(out.getCursor())) });
/*     */ 
/* 190 */         out.annotate(4, "string_ids_size: %d", new Object[] { Integer.valueOf(this.dexFile.readInt(out.getCursor())) });
/* 191 */         out.annotate(4, "string_ids_off: 0x%x", new Object[] { Integer.valueOf(this.dexFile.readInt(out.getCursor())) });
/*     */ 
/* 193 */         out.annotate(4, "type_ids_size: %d", new Object[] { Integer.valueOf(this.dexFile.readInt(out.getCursor())) });
/* 194 */         out.annotate(4, "type_ids_off: 0x%x", new Object[] { Integer.valueOf(this.dexFile.readInt(out.getCursor())) });
/*     */ 
/* 196 */         out.annotate(4, "proto_ids_size: %d", new Object[] { Integer.valueOf(this.dexFile.readInt(out.getCursor())) });
/* 197 */         out.annotate(4, "proto_ids_off: 0x%x", new Object[] { Integer.valueOf(this.dexFile.readInt(out.getCursor())) });
/*     */ 
/* 199 */         out.annotate(4, "field_ids_size: %d", new Object[] { Integer.valueOf(this.dexFile.readInt(out.getCursor())) });
/* 200 */         out.annotate(4, "field_ids_off: 0x%x", new Object[] { Integer.valueOf(this.dexFile.readInt(out.getCursor())) });
/*     */ 
/* 202 */         out.annotate(4, "method_ids_size: %d", new Object[] { Integer.valueOf(this.dexFile.readInt(out.getCursor())) });
/* 203 */         out.annotate(4, "method_ids_off: 0x%x", new Object[] { Integer.valueOf(this.dexFile.readInt(out.getCursor())) });
/*     */ 
/* 205 */         out.annotate(4, "class_defs_size: %d", new Object[] { Integer.valueOf(this.dexFile.readInt(out.getCursor())) });
/* 206 */         out.annotate(4, "class_defs_off: 0x%x", new Object[] { Integer.valueOf(this.dexFile.readInt(out.getCursor())) });
/*     */ 
/* 208 */         out.annotate(4, "data_size: %d", new Object[] { Integer.valueOf(this.dexFile.readInt(out.getCursor())) });
/* 209 */         out.annotate(4, "data_off: 0x%x", new Object[] { Integer.valueOf(this.dexFile.readInt(out.getCursor())) });
/*     */ 
/* 211 */         if (headerSize > 112)
/* 212 */           out.annotateTo(headerSize, "header padding", new Object[0]);
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   private static String getEndianText(int endianTag) {
/* 219 */     if (endianTag == 305419896) {
/* 220 */       return "Little Endian";
/*     */     }
/* 222 */     if (endianTag == 2018915346) {
/* 223 */       return "Big Endian";
/*     */     }
/* 225 */     return "Invalid";
/*     */   }
/*     */ 
/*     */   private static int getVersion(byte[] buf, int offset) {
/* 229 */     if (buf.length - offset < 8) {
/* 230 */       return 0;
/*     */     }
/*     */ 
/* 233 */     boolean matches = true;
/* 234 */     for (int i = 0; i < MAGIC_VALUES.length; i++) {
/* 235 */       byte[] expected = MAGIC_VALUES[i];
/* 236 */       matches = true;
/* 237 */       for (int j = 0; j < 8; j++) {
/* 238 */         if (buf[(offset + j)] != expected[j]) {
/* 239 */           matches = false;
/* 240 */           break;
/*     */         }
/*     */       }
/* 243 */       if (matches) {
/* 244 */         return i == 0 ? 35 : 36;
/*     */       }
/*     */     }
/* 247 */     return 0;
/*     */   }
/*     */ 
/*     */   public static boolean verifyMagic(byte[] buf, int offset)
/*     */   {
/* 252 */     return getVersion(buf, offset) != 0;
/*     */   }
/*     */ 
/*     */   public static int getEndian(byte[] buf, int offset)
/*     */   {
/* 257 */     BaseDexBuffer bdb = new BaseDexBuffer(buf);
/* 258 */     return bdb.readInt(offset + 40);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.HeaderItem
 * JD-Core Version:    0.6.0
 */