/*     */ package org.jf.dexlib2.dexbacked.raw.util;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Ordering;
/*     */ import com.google.common.primitives.Ints;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jf.dexlib2.dexbacked.raw.AnnotationDirectoryItem;
/*     */ import org.jf.dexlib2.dexbacked.raw.AnnotationItem;
/*     */ import org.jf.dexlib2.dexbacked.raw.AnnotationSetItem;
/*     */ import org.jf.dexlib2.dexbacked.raw.AnnotationSetRefList;
/*     */ import org.jf.dexlib2.dexbacked.raw.ClassDataItem;
/*     */ import org.jf.dexlib2.dexbacked.raw.ClassDefItem;
/*     */ import org.jf.dexlib2.dexbacked.raw.CodeItem;
/*     */ import org.jf.dexlib2.dexbacked.raw.DebugInfoItem;
/*     */ import org.jf.dexlib2.dexbacked.raw.EncodedArrayItem;
/*     */ import org.jf.dexlib2.dexbacked.raw.FieldIdItem;
/*     */ import org.jf.dexlib2.dexbacked.raw.HeaderItem;
/*     */ import org.jf.dexlib2.dexbacked.raw.MapItem;
/*     */ import org.jf.dexlib2.dexbacked.raw.MethodIdItem;
/*     */ import org.jf.dexlib2.dexbacked.raw.ProtoIdItem;
/*     */ import org.jf.dexlib2.dexbacked.raw.RawDexFile;
/*     */ import org.jf.dexlib2.dexbacked.raw.SectionAnnotator;
/*     */ import org.jf.dexlib2.dexbacked.raw.StringDataItem;
/*     */ import org.jf.dexlib2.dexbacked.raw.StringIdItem;
/*     */ import org.jf.dexlib2.dexbacked.raw.TypeIdItem;
/*     */ import org.jf.dexlib2.dexbacked.raw.TypeListItem;
/*     */ import org.jf.dexlib2.util.AnnotatedBytes;
/*     */ 
/*     */ public class DexAnnotator extends AnnotatedBytes
/*     */ {
/*     */   public final RawDexFile dexFile;
/*  51 */   private final Map<Integer, SectionAnnotator> annotators = Maps.newHashMap();
/*  52 */   private static final Map<Integer, Integer> sectionAnnotationOrder = Maps.newHashMap();
/*     */ 
/*     */   public DexAnnotator(RawDexFile dexFile, int width)
/*     */   {
/*  86 */     super(width);
/*     */ 
/*  88 */     this.dexFile = dexFile;
/*     */ 
/*  90 */     for (MapItem mapItem : dexFile.getMapItems())
/*  91 */       switch (mapItem.getType()) {
/*     */       case 0:
/*  93 */         this.annotators.put(Integer.valueOf(mapItem.getType()), HeaderItem.makeAnnotator(this, mapItem));
/*  94 */         break;
/*     */       case 1:
/*  96 */         this.annotators.put(Integer.valueOf(mapItem.getType()), StringIdItem.makeAnnotator(this, mapItem));
/*  97 */         break;
/*     */       case 2:
/*  99 */         this.annotators.put(Integer.valueOf(mapItem.getType()), TypeIdItem.makeAnnotator(this, mapItem));
/* 100 */         break;
/*     */       case 3:
/* 102 */         this.annotators.put(Integer.valueOf(mapItem.getType()), ProtoIdItem.makeAnnotator(this, mapItem));
/* 103 */         break;
/*     */       case 4:
/* 105 */         this.annotators.put(Integer.valueOf(mapItem.getType()), FieldIdItem.makeAnnotator(this, mapItem));
/* 106 */         break;
/*     */       case 5:
/* 108 */         this.annotators.put(Integer.valueOf(mapItem.getType()), MethodIdItem.makeAnnotator(this, mapItem));
/* 109 */         break;
/*     */       case 6:
/* 111 */         this.annotators.put(Integer.valueOf(mapItem.getType()), ClassDefItem.makeAnnotator(this, mapItem));
/* 112 */         break;
/*     */       case 4096:
/* 114 */         this.annotators.put(Integer.valueOf(mapItem.getType()), MapItem.makeAnnotator(this, mapItem));
/* 115 */         break;
/*     */       case 4097:
/* 117 */         this.annotators.put(Integer.valueOf(mapItem.getType()), TypeListItem.makeAnnotator(this, mapItem));
/* 118 */         break;
/*     */       case 4098:
/* 120 */         this.annotators.put(Integer.valueOf(mapItem.getType()), AnnotationSetRefList.makeAnnotator(this, mapItem));
/* 121 */         break;
/*     */       case 4099:
/* 123 */         this.annotators.put(Integer.valueOf(mapItem.getType()), AnnotationSetItem.makeAnnotator(this, mapItem));
/* 124 */         break;
/*     */       case 8192:
/* 126 */         this.annotators.put(Integer.valueOf(mapItem.getType()), ClassDataItem.makeAnnotator(this, mapItem));
/* 127 */         break;
/*     */       case 8193:
/* 129 */         this.annotators.put(Integer.valueOf(mapItem.getType()), CodeItem.makeAnnotator(this, mapItem));
/* 130 */         break;
/*     */       case 8194:
/* 132 */         this.annotators.put(Integer.valueOf(mapItem.getType()), StringDataItem.makeAnnotator(this, mapItem));
/* 133 */         break;
/*     */       case 8195:
/* 135 */         this.annotators.put(Integer.valueOf(mapItem.getType()), DebugInfoItem.makeAnnotator(this, mapItem));
/* 136 */         break;
/*     */       case 8196:
/* 138 */         this.annotators.put(Integer.valueOf(mapItem.getType()), AnnotationItem.makeAnnotator(this, mapItem));
/* 139 */         break;
/*     */       case 8197:
/* 141 */         this.annotators.put(Integer.valueOf(mapItem.getType()), EncodedArrayItem.makeAnnotator(this, mapItem));
/* 142 */         break;
/*     */       case 8198:
/* 144 */         this.annotators.put(Integer.valueOf(mapItem.getType()), AnnotationDirectoryItem.makeAnnotator(this, mapItem));
/* 145 */         break;
/*     */       default:
/* 147 */         throw new RuntimeException(String.format("Unrecognized item type: 0x%x", new Object[] { Integer.valueOf(mapItem.getType()) }));
/*     */       }
/*     */   }
/*     */ 
/*     */   public void writeAnnotations(Writer out) throws IOException
/*     */   {
/* 153 */     List mapItems = this.dexFile.getMapItems();
/*     */ 
/* 155 */     Ordering ordering = Ordering.from(new Comparator() {
/*     */       public int compare(MapItem o1, MapItem o2) {
/* 157 */         return Ints.compare(((Integer)DexAnnotator.sectionAnnotationOrder.get(Integer.valueOf(o1.getType()))).intValue(), ((Integer)DexAnnotator.sectionAnnotationOrder.get(Integer.valueOf(o2.getType()))).intValue());
/*     */       }
/*     */     });
/* 161 */     mapItems = ordering.immutableSortedCopy(mapItems);
/*     */     try
/*     */     {
/* 164 */       for (MapItem mapItem : mapItems) {
/* 165 */         SectionAnnotator annotator = (SectionAnnotator)this.annotators.get(Integer.valueOf(mapItem.getType()));
/* 166 */         annotator.annotateSection(this);
/*     */       }
/*     */     } finally {
/* 169 */       this.dexFile.writeAnnotations(out, this);
/*     */     }
/*     */   }
/*     */ 
/*     */   public SectionAnnotator getAnnotator(int itemType)
/*     */   {
/* 175 */     return (SectionAnnotator)this.annotators.get(Integer.valueOf(itemType));
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  55 */     int[] sectionOrder = { 4096, 0, 1, 2, 3, 4, 5, 6, 8192, 8193, 8195, 4097, 4098, 4099, 8194, 8196, 8197, 8198 };
/*     */ 
/*  80 */     for (int i = 0; i < sectionOrder.length; i++)
/*  81 */       sectionAnnotationOrder.put(Integer.valueOf(sectionOrder[i]), Integer.valueOf(i));
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.util.DexAnnotator
 * JD-Core Version:    0.6.0
 */