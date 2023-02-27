/*     */ package org.jf.dexlib2.util;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.TreeMap;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ import org.jf.util.Hex;
/*     */ import org.jf.util.TwoColumnOutput;
/*     */ 
/*     */ public class AnnotatedBytes
/*     */ {
/*  69 */   private TreeMap<Integer, AnnotationEndpoint> annotatations = Maps.newTreeMap();
/*     */   private int cursor;
/*     */   private int indentLevel;
/*     */   private int outputWidth;
/*  81 */   private int hexCols = 8;
/*     */ 
/*  83 */   private int startLimit = -1;
/*  84 */   private int endLimit = -1;
/*     */ 
/*     */   public AnnotatedBytes(int width) {
/*  87 */     this.outputWidth = width;
/*     */   }
/*     */ 
/*     */   public void moveTo(int offset)
/*     */   {
/*  96 */     this.cursor = offset;
/*     */   }
/*     */ 
/*     */   public void annotateTo(int offset, String msg, Object[] formatArgs)
/*     */   {
/* 109 */     annotate(offset - this.cursor, msg, formatArgs);
/*     */   }
/*     */ 
/*     */   public void annotate(int length, String msg, Object[] formatArgs)
/*     */   {
/* 123 */     if ((this.startLimit != -1) && (this.endLimit != -1) && ((this.cursor < this.startLimit) || (this.cursor >= this.endLimit)))
/* 124 */       throw new ExceptionWithContext("Annotating outside the parent bounds", new Object[0]);
/*     */     String formattedMsg;
/*     */     String formattedMsg;
/* 128 */     if ((formatArgs != null) && (formatArgs.length > 0))
/* 129 */       formattedMsg = String.format(msg, formatArgs);
/*     */     else {
/* 131 */       formattedMsg = msg;
/*     */     }
/* 133 */     int exclusiveEndOffset = this.cursor + length;
/*     */ 
/* 135 */     AnnotationEndpoint endPoint = null;
/*     */ 
/* 138 */     AnnotationEndpoint startPoint = (AnnotationEndpoint)this.annotatations.get(Integer.valueOf(this.cursor));
/* 139 */     if (startPoint == null)
/*     */     {
/* 141 */       Map.Entry previousEntry = this.annotatations.lowerEntry(Integer.valueOf(this.cursor));
/* 142 */       if (previousEntry != null) {
/* 143 */         AnnotationEndpoint previousAnnotations = (AnnotationEndpoint)previousEntry.getValue();
/* 144 */         AnnotationItem previousRangeAnnotation = previousAnnotations.rangeAnnotation;
/* 145 */         if (previousRangeAnnotation != null) {
/* 146 */           throw new ExceptionWithContext("Cannot add annotation %s, due to existing annotation %s", new Object[] { formatAnnotation(this.cursor, Integer.valueOf(this.cursor + length), formattedMsg), formatAnnotation(((Integer)previousEntry.getKey()).intValue(), previousRangeAnnotation.annotation) });
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/* 153 */     else if (length > 0) {
/* 154 */       AnnotationItem existingRangeAnnotation = startPoint.rangeAnnotation;
/* 155 */       if (existingRangeAnnotation != null) {
/* 156 */         throw new ExceptionWithContext("Cannot add annotation %s, due to existing annotation %s", new Object[] { formatAnnotation(this.cursor, Integer.valueOf(this.cursor + length), formattedMsg), formatAnnotation(this.cursor, existingRangeAnnotation.annotation) });
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 163 */     if (length > 0)
/*     */     {
/* 165 */       Map.Entry nextEntry = this.annotatations.higherEntry(Integer.valueOf(this.cursor));
/* 166 */       if (nextEntry != null) {
/* 167 */         int nextKey = ((Integer)nextEntry.getKey()).intValue();
/* 168 */         if (nextKey < exclusiveEndOffset)
/*     */         {
/* 171 */           AnnotationEndpoint nextEndpoint = (AnnotationEndpoint)nextEntry.getValue();
/* 172 */           AnnotationItem nextRangeAnnotation = nextEndpoint.rangeAnnotation;
/* 173 */           if (nextRangeAnnotation != null) {
/* 174 */             throw new ExceptionWithContext("Cannot add annotation %s, due to existing annotation %s", new Object[] { formatAnnotation(this.cursor, Integer.valueOf(this.cursor + length), formattedMsg), formatAnnotation(nextKey, nextRangeAnnotation.annotation) });
/*     */           }
/*     */ 
/* 179 */           if (nextEndpoint.pointAnnotations.size() > 0) {
/* 180 */             throw new ExceptionWithContext("Cannot add annotation %s, due to existing annotation %s", new Object[] { formatAnnotation(this.cursor, Integer.valueOf(this.cursor + length), formattedMsg), formatAnnotation(nextKey, Integer.valueOf(nextKey), ((AnnotationItem)nextEndpoint.pointAnnotations.get(0)).annotation) });
/*     */           }
/*     */ 
/* 187 */           throw new ExceptionWithContext("Cannot add annotation %s, due to existing annotation endpoint at %d", new Object[] { formatAnnotation(this.cursor, Integer.valueOf(this.cursor + length), formattedMsg), Integer.valueOf(nextKey) });
/*     */         }
/*     */ 
/* 193 */         if (nextKey == exclusiveEndOffset)
/*     */         {
/* 195 */           endPoint = (AnnotationEndpoint)nextEntry.getValue();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 204 */     if (startPoint == null) {
/* 205 */       startPoint = new AnnotationEndpoint(null);
/* 206 */       this.annotatations.put(Integer.valueOf(this.cursor), startPoint);
/*     */     }
/* 208 */     if (length == 0) {
/* 209 */       startPoint.pointAnnotations.add(new AnnotationItem(this.indentLevel, formattedMsg));
/*     */     } else {
/* 211 */       startPoint.rangeAnnotation = new AnnotationItem(this.indentLevel, formattedMsg);
/*     */ 
/* 214 */       if (endPoint == null) {
/* 215 */         endPoint = new AnnotationEndpoint(null);
/* 216 */         this.annotatations.put(Integer.valueOf(exclusiveEndOffset), endPoint);
/*     */       }
/*     */     }
/*     */ 
/* 220 */     this.cursor += length;
/*     */   }
/*     */ 
/*     */   private String formatAnnotation(int offset, String annotationMsg) {
/* 224 */     Integer endOffset = (Integer)this.annotatations.higherKey(Integer.valueOf(offset));
/* 225 */     return formatAnnotation(offset, endOffset, annotationMsg);
/*     */   }
/*     */ 
/*     */   private String formatAnnotation(int offset, Integer endOffset, String annotationMsg) {
/* 229 */     if (endOffset != null) {
/* 230 */       return String.format("[0x%x, 0x%x) \"%s\"", new Object[] { Integer.valueOf(offset), endOffset, annotationMsg });
/*     */     }
/* 232 */     return String.format("[0x%x, ) \"%s\"", new Object[] { Integer.valueOf(offset), annotationMsg });
/*     */   }
/*     */ 
/*     */   public void indent()
/*     */   {
/* 237 */     this.indentLevel += 1;
/*     */   }
/*     */ 
/*     */   public void deindent() {
/* 241 */     this.indentLevel -= 1;
/* 242 */     if (this.indentLevel < 0)
/* 243 */       this.indentLevel = 0;
/*     */   }
/*     */ 
/*     */   public int getCursor()
/*     */   {
/* 248 */     return this.cursor;
/*     */   }
/*     */ 
/*     */   public int getAnnotationWidth()
/*     */   {
/* 275 */     int leftWidth = 8 + this.hexCols * 2 + this.hexCols / 2;
/*     */ 
/* 277 */     return this.outputWidth - leftWidth;
/*     */   }
/*     */ 
/*     */   public void writeAnnotations(Writer out, byte[] data)
/*     */     throws IOException
/*     */   {
/* 286 */     int rightWidth = getAnnotationWidth();
/* 287 */     int leftWidth = this.outputWidth - rightWidth - 1;
/*     */ 
/* 289 */     String padding = Strings.repeat(" ", 1000);
/*     */ 
/* 291 */     TwoColumnOutput twoc = new TwoColumnOutput(out, leftWidth, rightWidth, "|");
/*     */ 
/* 293 */     Integer[] keys = new Integer[this.annotatations.size()];
/* 294 */     keys = (Integer[])this.annotatations.keySet().toArray(keys);
/*     */ 
/* 296 */     AnnotationEndpoint[] values = new AnnotationEndpoint[this.annotatations.size()];
/* 297 */     values = (AnnotationEndpoint[])this.annotatations.values().toArray(values);
/*     */ 
/* 299 */     for (int i = 0; i < keys.length - 1; i++) {
/* 300 */       int rangeStart = keys[i].intValue();
/* 301 */       int rangeEnd = keys[(i + 1)].intValue();
/*     */ 
/* 303 */       AnnotationEndpoint annotations = values[i];
/*     */ 
/* 305 */       for (AnnotationItem pointAnnotation : annotations.pointAnnotations) {
/* 306 */         String paddingSub = padding.substring(0, pointAnnotation.indentLevel * 2);
/* 307 */         twoc.write("", paddingSub + pointAnnotation.annotation);
/*     */       }
/*     */ 
/* 311 */       AnnotationItem rangeAnnotation = annotations.rangeAnnotation;
/*     */       String right;
/* 312 */       if (rangeAnnotation != null) {
/* 313 */         String right = padding.substring(0, rangeAnnotation.indentLevel * 2);
/* 314 */         right = right + rangeAnnotation.annotation;
/*     */       } else {
/* 316 */         right = "";
/*     */       }
/*     */ 
/* 319 */       String left = Hex.dump(data, rangeStart, rangeEnd - rangeStart, rangeStart, this.hexCols, 6);
/*     */ 
/* 321 */       twoc.write(left, right);
/*     */     }
/*     */ 
/* 324 */     int lastKey = keys[(keys.length - 1)].intValue();
/* 325 */     if (lastKey < data.length) {
/* 326 */       String left = Hex.dump(data, lastKey, data.length - lastKey, lastKey, this.hexCols, 6);
/* 327 */       twoc.write(left, "");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setLimit(int start, int end) {
/* 332 */     this.startLimit = start;
/* 333 */     this.endLimit = end;
/*     */   }
/*     */ 
/*     */   public void clearLimit() {
/* 337 */     this.startLimit = -1;
/* 338 */     this.endLimit = -1;
/*     */   }
/*     */ 
/*     */   private static class AnnotationItem
/*     */   {
/*     */     public final int indentLevel;
/*     */     public final String annotation;
/*     */ 
/*     */     public AnnotationItem(int indentLevel, String annotation)
/*     */     {
/* 265 */       this.indentLevel = indentLevel;
/* 266 */       this.annotation = annotation;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class AnnotationEndpoint
/*     */   {
/* 253 */     public final List<AnnotatedBytes.AnnotationItem> pointAnnotations = Lists.newArrayList();
/*     */ 
/* 256 */     public AnnotatedBytes.AnnotationItem rangeAnnotation = null;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.util.AnnotatedBytes
 * JD-Core Version:    0.6.0
 */