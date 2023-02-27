/*     */ package org.jf.dexlib2.writer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import org.jf.dexlib2.iface.reference.FieldReference;
/*     */ import org.jf.dexlib2.iface.reference.MethodReference;
/*     */ 
/*     */ public abstract class EncodedValueWriter<StringKey, TypeKey, FieldRefKey extends FieldReference, MethodRefKey extends MethodReference, AnnotationElement, EncodedValue>
/*     */ {
/*     */   private final DexDataWriter writer;
/*     */   private final StringSection<StringKey, ?> stringSection;
/*     */   private final TypeSection<?, TypeKey, ?> typeSection;
/*     */   private final FieldSection<?, ?, FieldRefKey, ?> fieldSection;
/*     */   private final MethodSection<?, ?, ?, MethodRefKey, ?> methodSection;
/*     */   private final AnnotationSection<StringKey, TypeKey, ?, AnnotationElement, EncodedValue> annotationSection;
/*     */ 
/*     */   public EncodedValueWriter(DexDataWriter writer, StringSection<StringKey, ?> stringSection, TypeSection<?, TypeKey, ?> typeSection, FieldSection<?, ?, FieldRefKey, ?> fieldSection, MethodSection<?, ?, ?, MethodRefKey, ?> methodSection, AnnotationSection<StringKey, TypeKey, ?, AnnotationElement, EncodedValue> annotationSection)
/*     */   {
/*  58 */     this.writer = writer;
/*  59 */     this.stringSection = stringSection;
/*  60 */     this.typeSection = typeSection;
/*  61 */     this.fieldSection = fieldSection;
/*  62 */     this.methodSection = methodSection;
/*  63 */     this.annotationSection = annotationSection;
/*     */   }
/*     */ 
/*     */   protected abstract void writeEncodedValue(EncodedValue paramEncodedValue) throws IOException;
/*     */ 
/*     */   public void writeAnnotation(TypeKey annotationType, Collection<? extends AnnotationElement> elements) throws IOException {
/*  70 */     this.writer.writeEncodedValueHeader(29, 0);
/*  71 */     this.writer.writeUleb128(this.typeSection.getItemIndex(annotationType));
/*  72 */     this.writer.writeUleb128(elements.size());
/*  73 */     for (Iterator i$ = elements.iterator(); i$.hasNext(); ) { Object element = i$.next();
/*  74 */       this.writer.writeUleb128(this.stringSection.getItemIndex(this.annotationSection.getElementName(element)));
/*  75 */       writeEncodedValue(this.annotationSection.getElementValue(element)); }
/*     */   }
/*     */ 
/*     */   public void writeArray(Collection<? extends EncodedValue> elements) throws IOException
/*     */   {
/*  80 */     this.writer.writeEncodedValueHeader(28, 0);
/*  81 */     this.writer.writeUleb128(elements.size());
/*  82 */     for (Iterator i$ = elements.iterator(); i$.hasNext(); ) { Object element = i$.next();
/*  83 */       writeEncodedValue(element); }
/*     */   }
/*     */ 
/*     */   public void writeBoolean(boolean value) throws IOException
/*     */   {
/*  88 */     this.writer.writeEncodedValueHeader(31, value ? 1 : 0);
/*     */   }
/*     */ 
/*     */   public void writeByte(byte value) throws IOException {
/*  92 */     this.writer.writeEncodedInt(0, value);
/*     */   }
/*     */ 
/*     */   public void writeChar(char value) throws IOException {
/*  96 */     this.writer.writeEncodedUint(3, value);
/*     */   }
/*     */ 
/*     */   public void writeDouble(double value) throws IOException {
/* 100 */     this.writer.writeEncodedDouble(17, value);
/*     */   }
/*     */ 
/*     */   public void writeEnum(FieldRefKey value) throws IOException {
/* 104 */     this.writer.writeEncodedUint(27, this.fieldSection.getItemIndex(value));
/*     */   }
/*     */ 
/*     */   public void writeField(FieldRefKey value) throws IOException {
/* 108 */     this.writer.writeEncodedUint(25, this.fieldSection.getItemIndex(value));
/*     */   }
/*     */ 
/*     */   public void writeFloat(float value) throws IOException {
/* 112 */     this.writer.writeEncodedFloat(16, value);
/*     */   }
/*     */ 
/*     */   public void writeInt(int value) throws IOException {
/* 116 */     this.writer.writeEncodedInt(4, value);
/*     */   }
/*     */ 
/*     */   public void writeLong(long value) throws IOException {
/* 120 */     this.writer.writeEncodedLong(6, value);
/*     */   }
/*     */ 
/*     */   public void writeMethod(MethodRefKey value) throws IOException {
/* 124 */     this.writer.writeEncodedUint(26, this.methodSection.getItemIndex(value));
/*     */   }
/*     */ 
/*     */   public void writeNull() throws IOException {
/* 128 */     this.writer.write(30);
/*     */   }
/*     */ 
/*     */   public void writeShort(int value) throws IOException {
/* 132 */     this.writer.writeEncodedInt(2, value);
/*     */   }
/*     */ 
/*     */   public void writeString(StringKey value) throws IOException {
/* 136 */     this.writer.writeEncodedUint(23, this.stringSection.getItemIndex(value));
/*     */   }
/*     */ 
/*     */   public void writeType(TypeKey value) throws IOException {
/* 140 */     this.writer.writeEncodedUint(24, this.typeSection.getItemIndex(value));
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.EncodedValueWriter
 * JD-Core Version:    0.6.0
 */