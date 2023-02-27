/*     */ package org.jf.dexlib2.dexbacked.raw;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
/*     */ import org.jf.dexlib2.util.AnnotatedBytes;
/*     */ import org.jf.util.AlignmentUtils;
/*     */ 
/*     */ public abstract class SectionAnnotator
/*     */ {
/*     */   public final DexAnnotator annotator;
/*     */   public final RawDexFile dexFile;
/*     */   public final int itemType;
/*     */   public final int sectionOffset;
/*     */   public final int itemCount;
/*  50 */   private Map<Integer, String> itemIdentities = Maps.newHashMap();
/*     */ 
/*     */   public SectionAnnotator(DexAnnotator annotator, MapItem mapItem) {
/*  53 */     this.annotator = annotator;
/*  54 */     this.dexFile = annotator.dexFile;
/*  55 */     this.itemType = mapItem.getType();
/*     */ 
/*  57 */     this.sectionOffset = mapItem.getOffset();
/*  58 */     this.itemCount = mapItem.getItemCount();
/*     */   }
/*     */ 
/*     */   public abstract String getItemName();
/*     */ 
/*     */   protected abstract void annotateItem(AnnotatedBytes paramAnnotatedBytes, int paramInt, String paramString);
/*     */ 
/*     */   public void annotateSection(AnnotatedBytes out)
/*     */   {
/*  70 */     out.moveTo(this.sectionOffset);
/*  71 */     annotateSectionInner(out, this.itemCount);
/*     */   }
/*     */ 
/*     */   protected void annotateSectionInner(AnnotatedBytes out, int itemCount) {
/*  75 */     String itemName = getItemName();
/*  76 */     int itemAlignment = getItemAlignment();
/*  77 */     if (itemCount > 0) {
/*  78 */       out.annotate(0, "", new Object[0]);
/*  79 */       out.annotate(0, "-----------------------------", new Object[0]);
/*  80 */       out.annotate(0, "%s section", new Object[] { itemName });
/*  81 */       out.annotate(0, "-----------------------------", new Object[0]);
/*  82 */       out.annotate(0, "", new Object[0]);
/*     */ 
/*  84 */       for (int i = 0; i < itemCount; i++) {
/*  85 */         out.moveTo(AlignmentUtils.alignOffset(out.getCursor(), itemAlignment));
/*     */ 
/*  87 */         String itemIdentity = getItemIdentity(out.getCursor());
/*  88 */         if (itemIdentity != null)
/*  89 */           out.annotate(0, "[%d] %s: %s", new Object[] { Integer.valueOf(i), itemName, itemIdentity });
/*     */         else {
/*  91 */           out.annotate(0, "[%d] %s", new Object[] { Integer.valueOf(i), itemName });
/*     */         }
/*  93 */         out.indent();
/*  94 */         annotateItem(out, i, itemIdentity);
/*  95 */         out.deindent();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private String getItemIdentity(int itemOffset) {
/* 101 */     return (String)this.itemIdentities.get(Integer.valueOf(itemOffset));
/*     */   }
/*     */ 
/*     */   public void setItemIdentity(int itemOffset, String identity) {
/* 105 */     this.itemIdentities.put(Integer.valueOf(itemOffset), identity);
/*     */   }
/*     */ 
/*     */   public int getItemAlignment() {
/* 109 */     return 1;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.SectionAnnotator
 * JD-Core Version:    0.6.0
 */