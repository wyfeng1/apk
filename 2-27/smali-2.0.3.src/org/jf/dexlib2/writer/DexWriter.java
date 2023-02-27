/*      */ package org.jf.dexlib2.writer;
/*      */ 
/*      */ import com.google.common.collect.Iterables;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Ordering;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.zip.Adler32;
/*      */ import org.jf.dexlib2.AccessFlags;
/*      */ import org.jf.dexlib2.Opcode;
/*      */ import org.jf.dexlib2.base.BaseAnnotation;
/*      */ import org.jf.dexlib2.builder.MutableMethodImplementation;
/*      */ import org.jf.dexlib2.builder.instruction.BuilderInstruction31c;
/*      */ import org.jf.dexlib2.iface.Annotation;
/*      */ import org.jf.dexlib2.iface.ExceptionHandler;
/*      */ import org.jf.dexlib2.iface.TryBlock;
/*      */ import org.jf.dexlib2.iface.debug.DebugItem;
/*      */ import org.jf.dexlib2.iface.debug.LineNumber;
/*      */ import org.jf.dexlib2.iface.instruction.Instruction;
/*      */ import org.jf.dexlib2.iface.instruction.OneRegisterInstruction;
/*      */ import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
/*      */ import org.jf.dexlib2.iface.instruction.formats.ArrayPayload;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction10t;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction10x;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction11n;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction11x;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction12x;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction20bc;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction20t;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction21c;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction21ih;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction21lh;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction21s;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction21t;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction22b;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction22c;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction22s;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction22t;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction22x;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction23x;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction30t;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction31c;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction31i;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction31t;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction32x;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction35c;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction3rc;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction51l;
/*      */ import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload;
/*      */ import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload;
/*      */ import org.jf.dexlib2.iface.reference.FieldReference;
/*      */ import org.jf.dexlib2.iface.reference.MethodReference;
/*      */ import org.jf.dexlib2.iface.reference.StringReference;
/*      */ import org.jf.dexlib2.iface.reference.TypeReference;
/*      */ import org.jf.dexlib2.util.InstructionUtil;
/*      */ import org.jf.dexlib2.util.MethodUtil;
/*      */ import org.jf.dexlib2.writer.io.DeferredOutputStream;
/*      */ import org.jf.dexlib2.writer.io.DeferredOutputStreamFactory;
/*      */ import org.jf.dexlib2.writer.io.DexDataStore;
/*      */ import org.jf.dexlib2.writer.io.MemoryDeferredOutputStream;
/*      */ import org.jf.dexlib2.writer.util.TryListBuilder;
/*      */ import org.jf.util.CollectionUtils;
/*      */ import org.jf.util.ExceptionWithContext;
/*      */ 
/*      */ public abstract class DexWriter<StringKey extends CharSequence, StringRef extends StringReference, TypeKey extends CharSequence, TypeRef extends TypeReference, ProtoKey extends Comparable<ProtoKey>, FieldRefKey extends FieldReference, MethodRefKey extends MethodReference, ClassKey extends Comparable<? super ClassKey>, AnnotationKey extends Annotation, AnnotationSetKey, TypeListKey, FieldKey, MethodKey, EncodedValue, AnnotationElement>
/*      */ {
/*      */   protected final int api;
/*   90 */   protected int stringIndexSectionOffset = 0;
/*   91 */   protected int typeSectionOffset = 0;
/*   92 */   protected int protoSectionOffset = 0;
/*   93 */   protected int fieldSectionOffset = 0;
/*   94 */   protected int methodSectionOffset = 0;
/*   95 */   protected int classIndexSectionOffset = 0;
/*      */ 
/*   97 */   protected int stringDataSectionOffset = 0;
/*   98 */   protected int classDataSectionOffset = 0;
/*   99 */   protected int typeListSectionOffset = 0;
/*  100 */   protected int encodedArraySectionOffset = 0;
/*  101 */   protected int annotationSectionOffset = 0;
/*  102 */   protected int annotationSetSectionOffset = 0;
/*  103 */   protected int annotationSetRefSectionOffset = 0;
/*  104 */   protected int annotationDirectorySectionOffset = 0;
/*  105 */   protected int debugSectionOffset = 0;
/*  106 */   protected int codeSectionOffset = 0;
/*  107 */   protected int mapSectionOffset = 0;
/*      */ 
/*  109 */   protected int numEncodedArrayItems = 0;
/*  110 */   protected int numAnnotationSetRefItems = 0;
/*  111 */   protected int numAnnotationDirectoryItems = 0;
/*  112 */   protected int numDebugInfoItems = 0;
/*  113 */   protected int numCodeItemItems = 0;
/*  114 */   protected int numClassDataItems = 0;
/*      */   protected final StringSection<StringKey, StringRef> stringSection;
/*      */   protected final TypeSection<StringKey, TypeKey, TypeRef> typeSection;
/*      */   protected final ProtoSection<StringKey, TypeKey, ProtoKey, TypeListKey> protoSection;
/*      */   protected final FieldSection<StringKey, TypeKey, FieldRefKey, FieldKey> fieldSection;
/*      */   protected final MethodSection<StringKey, TypeKey, ProtoKey, MethodRefKey, MethodKey> methodSection;
/*      */   protected final ClassSection<StringKey, TypeKey, TypeListKey, ClassKey, FieldKey, MethodKey, AnnotationSetKey, EncodedValue> classSection;
/*      */   protected final TypeListSection<TypeKey, TypeListKey> typeListSection;
/*      */   protected final AnnotationSection<StringKey, TypeKey, AnnotationKey, AnnotationElement, EncodedValue> annotationSection;
/*      */   protected final AnnotationSetSection<AnnotationKey, AnnotationSetKey> annotationSetSection;
/*  155 */   private static Comparator<Map.Entry> toStringKeyComparator = new Comparator()
/*      */   {
/*      */     public int compare(Map.Entry o1, Map.Entry o2) {
/*  158 */       return o1.getKey().toString().compareTo(o2.getKey().toString());
/*      */     }
/*  155 */   };
/*      */ 
/*      */   protected DexWriter(int api, StringSection<StringKey, StringRef> stringSection, TypeSection<StringKey, TypeKey, TypeRef> typeSection, ProtoSection<StringKey, TypeKey, ProtoKey, TypeListKey> protoSection, FieldSection<StringKey, TypeKey, FieldRefKey, FieldKey> fieldSection, MethodSection<StringKey, TypeKey, ProtoKey, MethodRefKey, MethodKey> methodSection, ClassSection<StringKey, TypeKey, TypeListKey, ClassKey, FieldKey, MethodKey, AnnotationSetKey, EncodedValue> classSection, TypeListSection<TypeKey, TypeListKey> typeListSection, AnnotationSection<StringKey, TypeKey, AnnotationKey, AnnotationElement, EncodedValue> annotationSection, AnnotationSetSection<AnnotationKey, AnnotationSetKey> annotationSetSection)
/*      */   {
/*  140 */     this.api = api;
/*  141 */     this.stringSection = stringSection;
/*  142 */     this.typeSection = typeSection;
/*  143 */     this.protoSection = protoSection;
/*  144 */     this.fieldSection = fieldSection;
/*  145 */     this.methodSection = methodSection;
/*  146 */     this.classSection = classSection;
/*  147 */     this.typeListSection = typeListSection;
/*  148 */     this.annotationSection = annotationSection;
/*  149 */     this.annotationSetSection = annotationSetSection;
/*      */   }
/*      */ 
/*      */   protected abstract void writeEncodedValue(DexWriter<StringKey, StringRef, TypeKey, TypeRef, ProtoKey, FieldRefKey, MethodRefKey, ClassKey, AnnotationKey, AnnotationSetKey, TypeListKey, FieldKey, MethodKey, EncodedValue, AnnotationElement>.InternalEncodedValueWriter paramDexWriter, EncodedValue paramEncodedValue)
/*      */     throws IOException;
/*      */ 
/*      */   private static <T extends Comparable<? super T>> Comparator<Map.Entry<? extends T, ?>> comparableKeyComparator()
/*      */   {
/*  163 */     return new Comparator() {
/*      */       public int compare(Map.Entry<? extends T, ?> o1, Map.Entry<? extends T, ?> o2) {
/*  165 */         return ((Comparable)o1.getKey()).compareTo(o2.getKey());
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   private int getDataSectionOffset()
/*      */   {
/*  182 */     return 112 + this.stringSection.getItems().size() * 4 + this.typeSection.getItems().size() * 4 + this.protoSection.getItems().size() * 12 + this.fieldSection.getItems().size() * 8 + this.methodSection.getItems().size() * 8 + this.classSection.getItems().size() * 32;
/*      */   }
/*      */ 
/*      */   public void writeTo(DexDataStore dest)
/*      */     throws IOException
/*      */   {
/*  192 */     writeTo(dest, MemoryDeferredOutputStream.getFactory());
/*      */   }
/*      */ 
/*      */   public void writeTo(DexDataStore dest, DeferredOutputStreamFactory tempFactory) throws IOException
/*      */   {
/*      */     try {
/*  198 */       int dataSectionOffset = getDataSectionOffset();
/*  199 */       DexDataWriter headerWriter = outputAt(dest, 0);
/*  200 */       DexDataWriter indexWriter = outputAt(dest, 112);
/*  201 */       DexDataWriter offsetWriter = outputAt(dest, dataSectionOffset);
/*      */       try {
/*  203 */         writeStrings(indexWriter, offsetWriter);
/*  204 */         writeTypes(indexWriter);
/*  205 */         writeTypeLists(offsetWriter);
/*  206 */         writeProtos(indexWriter);
/*  207 */         writeFields(indexWriter);
/*  208 */         writeMethods(indexWriter);
/*  209 */         writeEncodedArrays(offsetWriter);
/*  210 */         writeAnnotations(offsetWriter);
/*  211 */         writeAnnotationSets(offsetWriter);
/*  212 */         writeAnnotationSetRefs(offsetWriter);
/*  213 */         writeAnnotationDirectories(offsetWriter);
/*  214 */         writeDebugAndCodeItems(offsetWriter, tempFactory.makeDeferredOutputStream());
/*  215 */         writeClasses(indexWriter, offsetWriter);
/*  216 */         writeMapItem(offsetWriter);
/*  217 */         writeHeader(headerWriter, dataSectionOffset, offsetWriter.getPosition());
/*      */       } finally {
/*  219 */         headerWriter.close();
/*  220 */         indexWriter.close();
/*  221 */         offsetWriter.close();
/*      */       }
/*  223 */       updateSignature(dest);
/*  224 */       updateChecksum(dest);
/*      */     } finally {
/*  226 */       dest.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void updateSignature(DexDataStore dataStore) throws IOException {
/*      */     MessageDigest md;
/*      */     try {
/*  233 */       md = MessageDigest.getInstance("SHA-1");
/*      */     } catch (NoSuchAlgorithmException ex) {
/*  235 */       throw new RuntimeException(ex);
/*      */     }
/*      */ 
/*  238 */     byte[] buffer = new byte[4096];
/*  239 */     InputStream input = dataStore.readAt(32);
/*  240 */     int bytesRead = input.read(buffer);
/*  241 */     while (bytesRead >= 0) {
/*  242 */       md.update(buffer, 0, bytesRead);
/*  243 */       bytesRead = input.read(buffer);
/*      */     }
/*      */ 
/*  246 */     byte[] signature = md.digest();
/*  247 */     if (signature.length != 20) {
/*  248 */       throw new RuntimeException("unexpected digest write: " + signature.length + " bytes");
/*      */     }
/*      */ 
/*  252 */     OutputStream output = dataStore.outputAt(12);
/*  253 */     output.write(signature);
/*  254 */     output.close();
/*      */   }
/*      */ 
/*      */   private void updateChecksum(DexDataStore dataStore) throws IOException {
/*  258 */     Adler32 a32 = new Adler32();
/*      */ 
/*  260 */     byte[] buffer = new byte[4096];
/*  261 */     InputStream input = dataStore.readAt(12);
/*  262 */     int bytesRead = input.read(buffer);
/*  263 */     while (bytesRead >= 0) {
/*  264 */       a32.update(buffer, 0, bytesRead);
/*  265 */       bytesRead = input.read(buffer);
/*      */     }
/*      */ 
/*  269 */     OutputStream output = dataStore.outputAt(8);
/*  270 */     DexDataWriter.writeInt(output, (int)a32.getValue());
/*  271 */     output.close();
/*      */   }
/*      */ 
/*      */   private static DexDataWriter outputAt(DexDataStore dataStore, int filePosition) throws IOException {
/*  275 */     return new DexDataWriter(dataStore.outputAt(filePosition), filePosition);
/*      */   }
/*      */ 
/*      */   private void writeStrings(DexDataWriter indexWriter, DexDataWriter offsetWriter) throws IOException {
/*  279 */     this.stringIndexSectionOffset = indexWriter.getPosition();
/*  280 */     this.stringDataSectionOffset = offsetWriter.getPosition();
/*  281 */     int index = 0;
/*  282 */     List stringEntries = Lists.newArrayList(this.stringSection.getItems());
/*  283 */     Collections.sort(stringEntries, toStringKeyComparator);
/*      */ 
/*  285 */     for (Map.Entry entry : stringEntries) {
/*  286 */       entry.setValue(Integer.valueOf(index++));
/*  287 */       indexWriter.writeInt(offsetWriter.getPosition());
/*  288 */       String stringValue = ((CharSequence)entry.getKey()).toString();
/*  289 */       offsetWriter.writeUleb128(stringValue.length());
/*  290 */       offsetWriter.writeString(stringValue);
/*  291 */       offsetWriter.write(0);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void writeTypes(DexDataWriter writer) throws IOException {
/*  296 */     this.typeSectionOffset = writer.getPosition();
/*  297 */     int index = 0;
/*      */ 
/*  299 */     List typeEntries = Lists.newArrayList(this.typeSection.getItems());
/*  300 */     Collections.sort(typeEntries, toStringKeyComparator);
/*      */ 
/*  302 */     for (Map.Entry entry : typeEntries) {
/*  303 */       entry.setValue(Integer.valueOf(index++));
/*  304 */       writer.writeInt(this.stringSection.getItemIndex(this.typeSection.getString(entry.getKey())));
/*      */     }
/*      */   }
/*      */ 
/*      */   private void writeProtos(DexDataWriter writer) throws IOException {
/*  309 */     this.protoSectionOffset = writer.getPosition();
/*  310 */     int index = 0;
/*      */ 
/*  312 */     List protoEntries = Lists.newArrayList(this.protoSection.getItems());
/*  313 */     Collections.sort(protoEntries, comparableKeyComparator());
/*      */ 
/*  315 */     for (Map.Entry entry : protoEntries) {
/*  316 */       entry.setValue(Integer.valueOf(index++));
/*  317 */       Comparable key = (Comparable)entry.getKey();
/*  318 */       writer.writeInt(this.stringSection.getItemIndex(this.protoSection.getShorty(key)));
/*  319 */       writer.writeInt(this.typeSection.getItemIndex(this.protoSection.getReturnType(key)));
/*  320 */       writer.writeInt(this.typeListSection.getNullableItemOffset(this.protoSection.getParameters(key)));
/*      */     }
/*      */   }
/*      */ 
/*      */   private void writeFields(DexDataWriter writer) throws IOException {
/*  325 */     this.fieldSectionOffset = writer.getPosition();
/*  326 */     int index = 0;
/*      */ 
/*  328 */     List fieldEntries = Lists.newArrayList(this.fieldSection.getItems());
/*  329 */     Collections.sort(fieldEntries, comparableKeyComparator());
/*      */ 
/*  331 */     for (Map.Entry entry : fieldEntries) {
/*  332 */       entry.setValue(Integer.valueOf(index++));
/*  333 */       FieldReference key = (FieldReference)entry.getKey();
/*  334 */       writer.writeUshort(this.typeSection.getItemIndex(this.fieldSection.getDefiningClass(key)));
/*  335 */       writer.writeUshort(this.typeSection.getItemIndex(this.fieldSection.getFieldType(key)));
/*  336 */       writer.writeInt(this.stringSection.getItemIndex(this.fieldSection.getName(key)));
/*      */     }
/*      */   }
/*      */ 
/*      */   private void writeMethods(DexDataWriter writer) throws IOException {
/*  341 */     this.methodSectionOffset = writer.getPosition();
/*  342 */     int index = 0;
/*      */ 
/*  344 */     List methodEntries = Lists.newArrayList(this.methodSection.getItems());
/*  345 */     Collections.sort(methodEntries, comparableKeyComparator());
/*      */ 
/*  347 */     for (Map.Entry entry : methodEntries) {
/*  348 */       entry.setValue(Integer.valueOf(index++));
/*  349 */       MethodReference key = (MethodReference)entry.getKey();
/*  350 */       writer.writeUshort(this.typeSection.getItemIndex(this.methodSection.getDefiningClass(key)));
/*  351 */       writer.writeUshort(this.protoSection.getItemIndex(this.methodSection.getPrototype(key)));
/*  352 */       writer.writeInt(this.stringSection.getItemIndex(this.methodSection.getName(key)));
/*      */     }
/*      */   }
/*      */ 
/*      */   private void writeClasses(DexDataWriter indexWriter, DexDataWriter offsetWriter) throws IOException {
/*  357 */     this.classIndexSectionOffset = indexWriter.getPosition();
/*  358 */     this.classDataSectionOffset = offsetWriter.getPosition();
/*      */ 
/*  360 */     List classEntries = Lists.newArrayList(this.classSection.getItems());
/*  361 */     Collections.sort(classEntries, comparableKeyComparator());
/*      */ 
/*  363 */     int index = 0;
/*  364 */     for (Map.Entry key : classEntries)
/*  365 */       index = writeClass(indexWriter, offsetWriter, index, key);
/*      */   }
/*      */ 
/*      */   private int writeClass(DexDataWriter indexWriter, DexDataWriter offsetWriter, int nextIndex, Map.Entry<? extends ClassKey, Integer> entry)
/*      */     throws IOException
/*      */   {
/*  379 */     if (entry == null)
/*      */     {
/*  381 */       return nextIndex;
/*      */     }
/*      */ 
/*  384 */     if (((Integer)entry.getValue()).intValue() != -1)
/*      */     {
/*  386 */       return nextIndex;
/*      */     }
/*      */ 
/*  389 */     Comparable key = (Comparable)entry.getKey();
/*      */ 
/*  392 */     entry.setValue(Integer.valueOf(0));
/*      */ 
/*  395 */     Map.Entry superEntry = this.classSection.getClassEntryByType(this.classSection.getSuperclass(key));
/*      */ 
/*  397 */     nextIndex = writeClass(indexWriter, offsetWriter, nextIndex, superEntry);
/*      */ 
/*  400 */     for (CharSequence interfaceTypeKey : this.typeListSection.getTypes(this.classSection.getSortedInterfaces(key))) {
/*  401 */       Map.Entry interfaceEntry = this.classSection.getClassEntryByType(interfaceTypeKey);
/*  402 */       nextIndex = writeClass(indexWriter, offsetWriter, nextIndex, interfaceEntry);
/*      */     }
/*      */ 
/*  406 */     entry.setValue(Integer.valueOf(nextIndex++));
/*      */ 
/*  410 */     indexWriter.writeInt(this.typeSection.getItemIndex(this.classSection.getType(key)));
/*  411 */     indexWriter.writeInt(this.classSection.getAccessFlags(key));
/*  412 */     indexWriter.writeInt(this.typeSection.getNullableItemIndex(this.classSection.getSuperclass(key)));
/*  413 */     indexWriter.writeInt(this.typeListSection.getNullableItemOffset(this.classSection.getSortedInterfaces(key)));
/*  414 */     indexWriter.writeInt(this.stringSection.getNullableItemIndex(this.classSection.getSourceFile(key)));
/*  415 */     indexWriter.writeInt(this.classSection.getAnnotationDirectoryOffset(key));
/*      */ 
/*  417 */     Collection staticFields = this.classSection.getSortedStaticFields(key);
/*  418 */     Collection instanceFields = this.classSection.getSortedInstanceFields(key);
/*  419 */     Collection directMethods = this.classSection.getSortedDirectMethods(key);
/*  420 */     Collection virtualMethods = this.classSection.getSortedVirtualMethods(key);
/*  421 */     boolean classHasData = (staticFields.size() > 0) || (instanceFields.size() > 0) || (directMethods.size() > 0) || (virtualMethods.size() > 0);
/*      */ 
/*  426 */     if (classHasData)
/*  427 */       indexWriter.writeInt(offsetWriter.getPosition());
/*      */     else {
/*  429 */       indexWriter.writeInt(0);
/*      */     }
/*      */ 
/*  432 */     indexWriter.writeInt(this.classSection.getEncodedArrayOffset(key));
/*      */ 
/*  435 */     if (classHasData) {
/*  436 */       this.numClassDataItems += 1;
/*      */ 
/*  438 */       offsetWriter.writeUleb128(staticFields.size());
/*  439 */       offsetWriter.writeUleb128(instanceFields.size());
/*  440 */       offsetWriter.writeUleb128(directMethods.size());
/*  441 */       offsetWriter.writeUleb128(virtualMethods.size());
/*      */ 
/*  443 */       writeEncodedFields(offsetWriter, staticFields);
/*  444 */       writeEncodedFields(offsetWriter, instanceFields);
/*  445 */       writeEncodedMethods(offsetWriter, directMethods);
/*  446 */       writeEncodedMethods(offsetWriter, virtualMethods);
/*      */     }
/*      */ 
/*  449 */     return nextIndex;
/*      */   }
/*      */ 
/*      */   private void writeEncodedFields(DexDataWriter writer, Collection<? extends FieldKey> fields) throws IOException
/*      */   {
/*  454 */     int prevIndex = 0;
/*  455 */     for (Iterator i$ = fields.iterator(); i$.hasNext(); ) { Object key = i$.next();
/*  456 */       int index = this.fieldSection.getFieldIndex(key);
/*  457 */       writer.writeUleb128(index - prevIndex);
/*  458 */       writer.writeUleb128(this.classSection.getFieldAccessFlags(key));
/*  459 */       prevIndex = index; }
/*      */   }
/*      */ 
/*      */   private void writeEncodedMethods(DexDataWriter writer, Collection<? extends MethodKey> methods)
/*      */     throws IOException
/*      */   {
/*  465 */     int prevIndex = 0;
/*  466 */     for (Iterator i$ = methods.iterator(); i$.hasNext(); ) { Object key = i$.next();
/*  467 */       int index = this.methodSection.getMethodIndex(key);
/*  468 */       writer.writeUleb128(index - prevIndex);
/*  469 */       writer.writeUleb128(this.classSection.getMethodAccessFlags(key));
/*  470 */       writer.writeUleb128(this.classSection.getCodeItemOffset(key));
/*  471 */       prevIndex = index; }
/*      */   }
/*      */ 
/*      */   private void writeTypeLists(DexDataWriter writer) throws IOException
/*      */   {
/*  476 */     writer.align();
/*  477 */     this.typeListSectionOffset = writer.getPosition();
/*  478 */     for (Map.Entry entry : this.typeListSection.getItems()) {
/*  479 */       writer.align();
/*  480 */       entry.setValue(Integer.valueOf(writer.getPosition()));
/*      */ 
/*  482 */       Collection types = this.typeListSection.getTypes(entry.getKey());
/*  483 */       writer.writeInt(types.size());
/*  484 */       for (CharSequence typeKey : types)
/*  485 */         writer.writeUshort(this.typeSection.getItemIndex(typeKey));
/*      */     }
/*      */   }
/*      */ 
/*      */   private void writeEncodedArrays(DexDataWriter writer)
/*      */     throws IOException
/*      */   {
/*  513 */     InternalEncodedValueWriter encodedValueWriter = new InternalEncodedValueWriter(writer, null);
/*  514 */     this.encodedArraySectionOffset = writer.getPosition();
/*      */ 
/*  516 */     HashMap internedItems = Maps.newHashMap();
/*  517 */     EncodedArrayKey key = new EncodedArrayKey();
/*      */ 
/*  519 */     for (Comparable classKey : this.classSection.getSortedClasses()) {
/*  520 */       Collection elements = this.classSection.getStaticInitializers(classKey);
/*  521 */       if ((elements != null) && (elements.size() > 0)) {
/*  522 */         key.elements = elements;
/*  523 */         Integer prev = (Integer)internedItems.get(key);
/*  524 */         if (prev != null) {
/*  525 */           this.classSection.setEncodedArrayOffset(classKey, prev.intValue());
/*      */         } else {
/*  527 */           int offset = writer.getPosition();
/*  528 */           internedItems.put(key, Integer.valueOf(offset));
/*  529 */           this.classSection.setEncodedArrayOffset(classKey, offset);
/*  530 */           key = new EncodedArrayKey();
/*      */ 
/*  532 */           this.numEncodedArrayItems += 1;
/*      */ 
/*  534 */           writer.writeUleb128(elements.size());
/*  535 */           for (i$ = elements.iterator(); i$.hasNext(); ) { Object value = i$.next();
/*  536 */             writeEncodedValue(encodedValueWriter, value); } 
/*      */         }
/*      */       }
/*      */     }
/*      */     Iterator i$;
/*      */   }
/*      */ 
/*      */   private void writeAnnotations(DexDataWriter writer) throws IOException {
/*  544 */     InternalEncodedValueWriter encodedValueWriter = new InternalEncodedValueWriter(writer, null);
/*      */ 
/*  546 */     this.annotationSectionOffset = writer.getPosition();
/*  547 */     for (Map.Entry entry : this.annotationSection.getItems()) {
/*  548 */       entry.setValue(Integer.valueOf(writer.getPosition()));
/*      */ 
/*  550 */       Annotation key = (Annotation)entry.getKey();
/*      */ 
/*  552 */       writer.writeUbyte(this.annotationSection.getVisibility(key));
/*  553 */       writer.writeUleb128(this.typeSection.getItemIndex(this.annotationSection.getType(key)));
/*      */ 
/*  555 */       Collection elements = this.annotationSection.getElements(key);
/*  556 */       writer.writeUleb128(elements.size());
/*      */ 
/*  558 */       for (i$ = elements.iterator(); i$.hasNext(); ) { Object element = i$.next();
/*  559 */         writer.writeUleb128(this.stringSection.getItemIndex(this.annotationSection.getElementName(element)));
/*  560 */         writeEncodedValue(encodedValueWriter, this.annotationSection.getElementValue(element)); }
/*      */     }
/*      */     Iterator i$;
/*      */   }
/*      */ 
/*      */   private void writeAnnotationSets(DexDataWriter writer) throws IOException {
/*  567 */     writer.align();
/*  568 */     this.annotationSetSectionOffset = writer.getPosition();
/*  569 */     for (Map.Entry entry : this.annotationSetSection.getItems()) {
/*  570 */       Collection annotations = Ordering.from(BaseAnnotation.BY_TYPE).immutableSortedCopy(this.annotationSetSection.getAnnotations(entry.getKey()));
/*      */ 
/*  573 */       writer.align();
/*  574 */       entry.setValue(Integer.valueOf(writer.getPosition()));
/*  575 */       writer.writeInt(annotations.size());
/*  576 */       for (Annotation annotationKey : annotations)
/*  577 */         writer.writeInt(this.annotationSection.getItemOffset(annotationKey));
/*      */     }
/*      */   }
/*      */ 
/*      */   private void writeAnnotationSetRefs(DexDataWriter writer) throws IOException
/*      */   {
/*  583 */     writer.align();
/*  584 */     this.annotationSetRefSectionOffset = writer.getPosition();
/*  585 */     HashMap internedItems = Maps.newHashMap();
/*      */ 
/*  587 */     for (Comparable classKey : this.classSection.getSortedClasses())
/*  588 */       for (i$ = this.classSection.getSortedMethods(classKey).iterator(); i$.hasNext(); ) { Object methodKey = i$.next();
/*  589 */         List parameterAnnotations = this.classSection.getParameterAnnotations(methodKey);
/*  590 */         if (parameterAnnotations != null) {
/*  591 */           Integer prev = (Integer)internedItems.get(parameterAnnotations);
/*  592 */           if (prev != null) {
/*  593 */             this.classSection.setAnnotationSetRefListOffset(methodKey, prev.intValue());
/*      */           } else {
/*  595 */             writer.align();
/*  596 */             int position = writer.getPosition();
/*  597 */             this.classSection.setAnnotationSetRefListOffset(methodKey, position);
/*  598 */             internedItems.put(parameterAnnotations, Integer.valueOf(position));
/*      */ 
/*  600 */             this.numAnnotationSetRefItems += 1;
/*      */ 
/*  602 */             writer.writeInt(parameterAnnotations.size());
/*  603 */             for (i$ = parameterAnnotations.iterator(); i$.hasNext(); ) { Object annotationSetKey = i$.next();
/*  604 */               if (this.annotationSetSection.getAnnotations(annotationSetKey).size() > 0)
/*  605 */                 writer.writeInt(this.annotationSetSection.getItemOffset(annotationSetKey));
/*      */               else
/*  607 */                 writer.writeInt(0); }
/*      */           }
/*      */         } }
/*      */     Iterator i$;
/*      */     Iterator i$;
/*      */   }
/*      */ 
/*      */   private void writeAnnotationDirectories(DexDataWriter writer) throws IOException {
/*  617 */     writer.align();
/*  618 */     this.annotationDirectorySectionOffset = writer.getPosition();
/*  619 */     HashMap internedItems = Maps.newHashMap();
/*      */ 
/*  621 */     ByteBuffer tempBuffer = ByteBuffer.allocate(65536);
/*  622 */     tempBuffer.order(ByteOrder.LITTLE_ENDIAN);
/*      */ 
/*  624 */     for (Comparable key : this.classSection.getSortedClasses())
/*      */     {
/*  628 */       Collection fields = this.classSection.getSortedFields(key);
/*  629 */       Collection methods = this.classSection.getSortedMethods(key);
/*      */ 
/*  632 */       int maxSize = fields.size() * 8 + methods.size() * 16;
/*  633 */       if (maxSize > tempBuffer.capacity()) {
/*  634 */         tempBuffer = ByteBuffer.allocate(maxSize);
/*  635 */         tempBuffer.order(ByteOrder.LITTLE_ENDIAN);
/*      */       }
/*      */ 
/*  638 */       tempBuffer.clear();
/*      */ 
/*  640 */       int fieldAnnotations = 0;
/*  641 */       int methodAnnotations = 0;
/*  642 */       int parameterAnnotations = 0;
/*      */ 
/*  644 */       for (Iterator i$ = fields.iterator(); i$.hasNext(); ) { Object field = i$.next();
/*  645 */         Object fieldAnnotationsKey = this.classSection.getFieldAnnotations(field);
/*  646 */         if (fieldAnnotationsKey != null) {
/*  647 */           fieldAnnotations++;
/*  648 */           tempBuffer.putInt(this.fieldSection.getFieldIndex(field));
/*  649 */           tempBuffer.putInt(this.annotationSetSection.getItemOffset(fieldAnnotationsKey));
/*      */         }
/*      */       }
/*      */ 
/*  653 */       for (Iterator i$ = methods.iterator(); i$.hasNext(); ) { Object method = i$.next();
/*  654 */         Object methodAnnotationsKey = this.classSection.getMethodAnnotations(method);
/*  655 */         if (methodAnnotationsKey != null) {
/*  656 */           methodAnnotations++;
/*  657 */           tempBuffer.putInt(this.methodSection.getMethodIndex(method));
/*  658 */           tempBuffer.putInt(this.annotationSetSection.getItemOffset(methodAnnotationsKey));
/*      */         }
/*      */       }
/*      */ 
/*  662 */       for (Iterator i$ = methods.iterator(); i$.hasNext(); ) { Object method = i$.next();
/*  663 */         int offset = this.classSection.getAnnotationSetRefListOffset(method);
/*  664 */         if (offset != 0) {
/*  665 */           parameterAnnotations++;
/*  666 */           tempBuffer.putInt(this.methodSection.getMethodIndex(method));
/*  667 */           tempBuffer.putInt(offset);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  673 */       Object classAnnotationKey = this.classSection.getClassAnnotations(key);
/*  674 */       if ((fieldAnnotations == 0) && (methodAnnotations == 0) && (parameterAnnotations == 0)) {
/*  675 */         if (classAnnotationKey == null)
/*      */           continue;
/*  677 */         Integer directoryOffset = (Integer)internedItems.get(classAnnotationKey);
/*  678 */         if (directoryOffset != null) {
/*  679 */           this.classSection.setAnnotationDirectoryOffset(key, directoryOffset.intValue());
/*  680 */           continue;
/*      */         }
/*  682 */         internedItems.put(classAnnotationKey, Integer.valueOf(writer.getPosition()));
/*      */       }
/*      */ 
/*  690 */       this.numAnnotationDirectoryItems += 1;
/*  691 */       this.classSection.setAnnotationDirectoryOffset(key, writer.getPosition());
/*      */ 
/*  693 */       writer.writeInt(this.annotationSetSection.getNullableItemOffset(classAnnotationKey));
/*  694 */       writer.writeInt(fieldAnnotations);
/*  695 */       writer.writeInt(methodAnnotations);
/*  696 */       writer.writeInt(parameterAnnotations);
/*  697 */       writer.write(tempBuffer.array(), 0, tempBuffer.position());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void writeDebugAndCodeItems(DexDataWriter offsetWriter, DeferredOutputStream temp)
/*      */     throws IOException
/*      */   {
/*  713 */     ByteArrayOutputStream ehBuf = new ByteArrayOutputStream();
/*  714 */     this.debugSectionOffset = offsetWriter.getPosition();
/*  715 */     DebugWriter debugWriter = new DebugWriter(this.stringSection, this.typeSection, offsetWriter);
/*      */ 
/*  718 */     DexDataWriter codeWriter = new DexDataWriter(temp, 0);
/*      */ 
/*  720 */     List codeOffsets = Lists.newArrayList();
/*      */ 
/*  722 */     for (Comparable classKey : this.classSection.getSortedClasses()) {
/*  723 */       Collection directMethods = this.classSection.getSortedDirectMethods(classKey);
/*  724 */       Collection virtualMethods = this.classSection.getSortedVirtualMethods(classKey);
/*      */ 
/*  726 */       Iterable methods = Iterables.concat(directMethods, virtualMethods);
/*      */ 
/*  728 */       for (i$ = methods.iterator(); i$.hasNext(); ) { Object methodKey = i$.next();
/*  729 */         List tryBlocks = this.classSection.getTryBlocks(methodKey);
/*      */ 
/*  731 */         Iterable instructions = this.classSection.getInstructions(methodKey);
/*  732 */         Iterable debugItems = this.classSection.getDebugItems(methodKey);
/*      */ 
/*  734 */         if ((instructions != null) && (this.stringSection.hasJumboIndexes())) {
/*  735 */           boolean needsFix = false;
/*  736 */           for (Instruction instruction : instructions) {
/*  737 */             if ((instruction.getOpcode() == Opcode.CONST_STRING) && 
/*  738 */               (this.stringSection.getItemIndex((StringReference)((ReferenceInstruction)instruction).getReference()) >= 65536))
/*      */             {
/*  740 */               needsFix = true;
/*  741 */               break;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  746 */           if (needsFix) {
/*  747 */             MutableMethodImplementation mutableMethodImplementation = this.classSection.makeMutableMethodImplementation(methodKey);
/*      */ 
/*  749 */             fixInstructions(mutableMethodImplementation);
/*      */ 
/*  751 */             instructions = mutableMethodImplementation.getInstructions();
/*  752 */             tryBlocks = mutableMethodImplementation.getTryBlocks();
/*  753 */             debugItems = mutableMethodImplementation.getDebugItems();
/*      */           }
/*      */         }
/*      */ 
/*  757 */         int debugItemOffset = writeDebugItem(offsetWriter, debugWriter, this.classSection.getParameterNames(methodKey), debugItems);
/*      */ 
/*  759 */         int codeItemOffset = writeCodeItem(codeWriter, ehBuf, methodKey, tryBlocks, instructions, debugItemOffset);
/*      */ 
/*  761 */         if (codeItemOffset != -1)
/*  762 */           codeOffsets.add(new CodeItemOffset(methodKey, codeItemOffset, null));
/*      */       }
/*      */     }
/*      */     Iterator i$;
/*  767 */     offsetWriter.align();
/*  768 */     this.codeSectionOffset = offsetWriter.getPosition();
/*      */ 
/*  770 */     codeWriter.close();
/*  771 */     temp.writeTo(offsetWriter);
/*  772 */     temp.close();
/*      */ 
/*  774 */     for (CodeItemOffset codeOffset : codeOffsets)
/*  775 */       this.classSection.setCodeItemOffset(codeOffset.method, this.codeSectionOffset + codeOffset.codeOffset);
/*      */   }
/*      */ 
/*      */   private void fixInstructions(MutableMethodImplementation methodImplementation)
/*      */   {
/*  780 */     List instructions = methodImplementation.getInstructions();
/*      */ 
/*  782 */     for (int i = 0; i < instructions.size(); i++) {
/*  783 */       Instruction instruction = (Instruction)instructions.get(i);
/*      */ 
/*  785 */       if ((instruction.getOpcode() != Opcode.CONST_STRING) || 
/*  786 */         (this.stringSection.getItemIndex((StringReference)((ReferenceInstruction)instruction).getReference()) < 65536))
/*      */         continue;
/*  788 */       methodImplementation.replaceInstruction(i, new BuilderInstruction31c(Opcode.CONST_STRING_JUMBO, ((OneRegisterInstruction)instruction).getRegisterA(), ((ReferenceInstruction)instruction).getReference()));
/*      */     }
/*      */   }
/*      */ 
/*      */   private int writeDebugItem(DexDataWriter writer, DebugWriter<StringKey, TypeKey> debugWriter, Iterable<? extends StringKey> parameterNames, Iterable<? extends DebugItem> debugItems)
/*      */     throws IOException
/*      */   {
/*  800 */     int parameterCount = 0;
/*  801 */     int lastNamedParameterIndex = -1;
/*      */     int index;
/*  802 */     if (parameterNames != null) {
/*  803 */       parameterCount = Iterables.size(parameterNames);
/*  804 */       index = 0;
/*  805 */       for (CharSequence parameterName : parameterNames) {
/*  806 */         if (parameterName != null) {
/*  807 */           lastNamedParameterIndex = index;
/*      */         }
/*  809 */         index++;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  814 */     if ((lastNamedParameterIndex == -1) && ((debugItems == null) || (Iterables.isEmpty(debugItems)))) {
/*  815 */       return 0;
/*      */     }
/*      */ 
/*  818 */     this.numDebugInfoItems += 1;
/*      */ 
/*  820 */     int debugItemOffset = writer.getPosition();
/*  821 */     int startingLineNumber = 0;
/*      */ 
/*  823 */     if (debugItems != null) {
/*  824 */       for (DebugItem debugItem : debugItems) {
/*  825 */         if ((debugItem instanceof LineNumber)) {
/*  826 */           startingLineNumber = ((LineNumber)debugItem).getLineNumber();
/*  827 */           break;
/*      */         }
/*      */       }
/*      */     }
/*  831 */     writer.writeUleb128(startingLineNumber);
/*      */ 
/*  833 */     writer.writeUleb128(parameterCount);
/*      */     int index;
/*  834 */     if (parameterNames != null) {
/*  835 */       index = 0;
/*  836 */       for (CharSequence parameterName : parameterNames) {
/*  837 */         if (index == parameterCount) {
/*      */           break;
/*      */         }
/*  840 */         index++;
/*  841 */         writer.writeUleb128(this.stringSection.getNullableItemIndex(parameterName) + 1);
/*      */       }
/*      */     }
/*      */ 
/*  845 */     if (debugItems != null) {
/*  846 */       debugWriter.reset(startingLineNumber);
/*      */ 
/*  848 */       for (DebugItem debugItem : debugItems) {
/*  849 */         this.classSection.writeDebugItem(debugWriter, debugItem);
/*      */       }
/*      */     }
/*      */ 
/*  853 */     writer.write(0);
/*      */ 
/*  855 */     return debugItemOffset;
/*      */   }
/*      */ 
/*      */   private int writeCodeItem(DexDataWriter writer, ByteArrayOutputStream ehBuf, MethodKey methodKey, List<? extends TryBlock<? extends ExceptionHandler>> tryBlocks, Iterable<? extends Instruction> instructions, int debugItemOffset)
/*      */     throws IOException
/*      */   {
/*  864 */     if ((instructions == null) && (debugItemOffset == 0)) {
/*  865 */       return -1;
/*      */     }
/*      */ 
/*  868 */     this.numCodeItemItems += 1;
/*      */ 
/*  870 */     writer.align();
/*      */ 
/*  872 */     int codeItemOffset = writer.getPosition();
/*      */ 
/*  874 */     writer.writeUshort(this.classSection.getRegisterCount(methodKey));
/*      */ 
/*  876 */     boolean isStatic = AccessFlags.STATIC.isSet(this.classSection.getMethodAccessFlags(methodKey));
/*  877 */     Collection parameters = this.typeListSection.getTypes(this.protoSection.getParameters(this.methodSection.getPrototype(methodKey)));
/*      */ 
/*  880 */     writer.writeUshort(MethodUtil.getParameterRegisterCount(parameters, isStatic));
/*      */ 
/*  882 */     if (instructions != null) {
/*  883 */       tryBlocks = TryListBuilder.massageTryBlocks(tryBlocks);
/*      */ 
/*  885 */       int outParamCount = 0;
/*  886 */       int codeUnitCount = 0;
/*  887 */       for (Instruction instruction : instructions) {
/*  888 */         codeUnitCount += instruction.getCodeUnits();
/*  889 */         if (instruction.getOpcode().referenceType == 3) {
/*  890 */           ReferenceInstruction refInsn = (ReferenceInstruction)instruction;
/*  891 */           MethodReference methodRef = (MethodReference)refInsn.getReference();
/*  892 */           int paramCount = MethodUtil.getParameterRegisterCount(methodRef, InstructionUtil.isInvokeStatic(instruction.getOpcode()));
/*  893 */           if (paramCount > outParamCount) {
/*  894 */             outParamCount = paramCount;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*  899 */       writer.writeUshort(outParamCount);
/*  900 */       writer.writeUshort(tryBlocks.size());
/*  901 */       writer.writeInt(debugItemOffset);
/*      */ 
/*  903 */       InstructionWriter instructionWriter = InstructionWriter.makeInstructionWriter(writer, this.stringSection, this.typeSection, this.fieldSection, this.methodSection);
/*      */ 
/*  907 */       writer.writeInt(codeUnitCount);
/*  908 */       for (Instruction instruction : instructions) {
/*  909 */         switch (3.$SwitchMap$org$jf$dexlib2$Format[instruction.getOpcode().format.ordinal()]) {
/*      */         case 1:
/*  911 */           instructionWriter.write((Instruction10t)instruction);
/*  912 */           break;
/*      */         case 2:
/*  914 */           instructionWriter.write((Instruction10x)instruction);
/*  915 */           break;
/*      */         case 3:
/*  917 */           instructionWriter.write((Instruction11n)instruction);
/*  918 */           break;
/*      */         case 4:
/*  920 */           instructionWriter.write((Instruction11x)instruction);
/*  921 */           break;
/*      */         case 5:
/*  923 */           instructionWriter.write((Instruction12x)instruction);
/*  924 */           break;
/*      */         case 6:
/*  926 */           instructionWriter.write((Instruction20bc)instruction);
/*  927 */           break;
/*      */         case 7:
/*  929 */           instructionWriter.write((Instruction20t)instruction);
/*  930 */           break;
/*      */         case 8:
/*  932 */           instructionWriter.write((Instruction21c)instruction);
/*  933 */           break;
/*      */         case 9:
/*  935 */           instructionWriter.write((Instruction21ih)instruction);
/*  936 */           break;
/*      */         case 10:
/*  938 */           instructionWriter.write((Instruction21lh)instruction);
/*  939 */           break;
/*      */         case 11:
/*  941 */           instructionWriter.write((Instruction21s)instruction);
/*  942 */           break;
/*      */         case 12:
/*  944 */           instructionWriter.write((Instruction21t)instruction);
/*  945 */           break;
/*      */         case 13:
/*  947 */           instructionWriter.write((Instruction22b)instruction);
/*  948 */           break;
/*      */         case 14:
/*  950 */           instructionWriter.write((Instruction22c)instruction);
/*  951 */           break;
/*      */         case 15:
/*  953 */           instructionWriter.write((Instruction22s)instruction);
/*  954 */           break;
/*      */         case 16:
/*  956 */           instructionWriter.write((Instruction22t)instruction);
/*  957 */           break;
/*      */         case 17:
/*  959 */           instructionWriter.write((Instruction22x)instruction);
/*  960 */           break;
/*      */         case 18:
/*  962 */           instructionWriter.write((Instruction23x)instruction);
/*  963 */           break;
/*      */         case 19:
/*  965 */           instructionWriter.write((Instruction30t)instruction);
/*  966 */           break;
/*      */         case 20:
/*  968 */           instructionWriter.write((Instruction31c)instruction);
/*  969 */           break;
/*      */         case 21:
/*  971 */           instructionWriter.write((Instruction31i)instruction);
/*  972 */           break;
/*      */         case 22:
/*  974 */           instructionWriter.write((Instruction31t)instruction);
/*  975 */           break;
/*      */         case 23:
/*  977 */           instructionWriter.write((Instruction32x)instruction);
/*  978 */           break;
/*      */         case 24:
/*  980 */           instructionWriter.write((Instruction35c)instruction);
/*  981 */           break;
/*      */         case 25:
/*  983 */           instructionWriter.write((Instruction3rc)instruction);
/*  984 */           break;
/*      */         case 26:
/*  986 */           instructionWriter.write((Instruction51l)instruction);
/*  987 */           break;
/*      */         case 27:
/*  989 */           instructionWriter.write((ArrayPayload)instruction);
/*  990 */           break;
/*      */         case 28:
/*  992 */           instructionWriter.write((PackedSwitchPayload)instruction);
/*  993 */           break;
/*      */         case 29:
/*  995 */           instructionWriter.write((SparseSwitchPayload)instruction);
/*  996 */           break;
/*      */         default:
/*  998 */           throw new ExceptionWithContext("Unsupported instruction format: %s", new Object[] { instruction.getOpcode().format });
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1003 */       if (tryBlocks.size() > 0) {
/* 1004 */         writer.align();
/*      */ 
/* 1007 */         Map exceptionHandlerOffsetMap = Maps.newHashMap();
/* 1008 */         for (TryBlock tryBlock : tryBlocks) {
/* 1009 */           exceptionHandlerOffsetMap.put(tryBlock.getExceptionHandlers(), Integer.valueOf(0));
/*      */         }
/* 1011 */         DexDataWriter.writeUleb128(ehBuf, exceptionHandlerOffsetMap.size());
/*      */ 
/* 1013 */         for (TryBlock tryBlock : tryBlocks) {
/* 1014 */           int startAddress = tryBlock.getStartCodeAddress();
/* 1015 */           int endAddress = startAddress + tryBlock.getCodeUnitCount();
/*      */ 
/* 1017 */           int tbCodeUnitCount = endAddress - startAddress;
/*      */ 
/* 1019 */           writer.writeInt(startAddress);
/* 1020 */           writer.writeUshort(tbCodeUnitCount);
/*      */ 
/* 1022 */           if (tryBlock.getExceptionHandlers().size() == 0) {
/* 1023 */             throw new ExceptionWithContext("No exception handlers for the try block!", new Object[0]);
/*      */           }
/*      */ 
/* 1026 */           Integer offset = (Integer)exceptionHandlerOffsetMap.get(tryBlock.getExceptionHandlers());
/* 1027 */           if (offset.intValue() != 0)
/*      */           {
/* 1029 */             writer.writeUshort(offset.intValue());
/*      */           }
/*      */           else {
/* 1032 */             offset = Integer.valueOf(ehBuf.size());
/* 1033 */             writer.writeUshort(offset.intValue());
/* 1034 */             exceptionHandlerOffsetMap.put(tryBlock.getExceptionHandlers(), offset);
/*      */ 
/* 1037 */             int ehSize = tryBlock.getExceptionHandlers().size();
/* 1038 */             ExceptionHandler ehLast = (ExceptionHandler)tryBlock.getExceptionHandlers().get(ehSize - 1);
/* 1039 */             if (ehLast.getExceptionType() == null) {
/* 1040 */               ehSize = ehSize * -1 + 1;
/*      */             }
/*      */ 
/* 1044 */             DexDataWriter.writeSleb128(ehBuf, ehSize);
/* 1045 */             for (ExceptionHandler eh : tryBlock.getExceptionHandlers()) {
/* 1046 */               CharSequence exceptionTypeKey = this.classSection.getExceptionType(eh);
/*      */ 
/* 1048 */               int codeAddress = eh.getHandlerCodeAddress();
/*      */ 
/* 1050 */               if (exceptionTypeKey != null)
/*      */               {
/* 1052 */                 DexDataWriter.writeUleb128(ehBuf, this.typeSection.getItemIndex(exceptionTypeKey));
/* 1053 */                 DexDataWriter.writeUleb128(ehBuf, codeAddress);
/*      */               }
/*      */               else {
/* 1056 */                 DexDataWriter.writeUleb128(ehBuf, codeAddress);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/* 1062 */         if (ehBuf.size() > 0) {
/* 1063 */           ehBuf.writeTo(writer);
/* 1064 */           ehBuf.reset();
/*      */         }
/*      */       }
/*      */     }
/*      */     else {
/* 1069 */       writer.writeUshort(0);
/* 1070 */       writer.writeUshort(0);
/* 1071 */       writer.writeInt(debugItemOffset);
/* 1072 */       writer.writeInt(0);
/*      */     }
/*      */ 
/* 1075 */     return codeItemOffset;
/*      */   }
/*      */ 
/*      */   private int calcNumItems() {
/* 1079 */     int numItems = 0;
/*      */ 
/* 1082 */     numItems++;
/*      */ 
/* 1084 */     if (this.stringSection.getItems().size() > 0) {
/* 1085 */       numItems += 2;
/*      */     }
/* 1087 */     if (this.typeSection.getItems().size() > 0) {
/* 1088 */       numItems++;
/*      */     }
/* 1090 */     if (this.protoSection.getItems().size() > 0) {
/* 1091 */       numItems++;
/*      */     }
/* 1093 */     if (this.fieldSection.getItems().size() > 0) {
/* 1094 */       numItems++;
/*      */     }
/* 1096 */     if (this.methodSection.getItems().size() > 0) {
/* 1097 */       numItems++;
/*      */     }
/* 1099 */     if (this.typeListSection.getItems().size() > 0) {
/* 1100 */       numItems++;
/*      */     }
/* 1102 */     if (this.numEncodedArrayItems > 0) {
/* 1103 */       numItems++;
/*      */     }
/* 1105 */     if (this.annotationSection.getItems().size() > 0) {
/* 1106 */       numItems++;
/*      */     }
/* 1108 */     if (this.annotationSetSection.getItems().size() > 0) {
/* 1109 */       numItems++;
/*      */     }
/* 1111 */     if (this.numAnnotationSetRefItems > 0) {
/* 1112 */       numItems++;
/*      */     }
/* 1114 */     if (this.numAnnotationDirectoryItems > 0) {
/* 1115 */       numItems++;
/*      */     }
/* 1117 */     if (this.numDebugInfoItems > 0) {
/* 1118 */       numItems++;
/*      */     }
/* 1120 */     if (this.numCodeItemItems > 0) {
/* 1121 */       numItems++;
/*      */     }
/* 1123 */     if (this.classSection.getItems().size() > 0) {
/* 1124 */       numItems++;
/*      */     }
/* 1126 */     if (this.numClassDataItems > 0) {
/* 1127 */       numItems++;
/*      */     }
/*      */ 
/* 1130 */     numItems++;
/*      */ 
/* 1132 */     return numItems;
/*      */   }
/*      */ 
/*      */   private void writeMapItem(DexDataWriter writer) throws IOException {
/* 1136 */     writer.align();
/* 1137 */     this.mapSectionOffset = writer.getPosition();
/* 1138 */     int numItems = calcNumItems();
/*      */ 
/* 1140 */     writer.writeInt(numItems);
/*      */ 
/* 1143 */     writeMapItem(writer, 0, 1, 0);
/* 1144 */     writeMapItem(writer, 1, this.stringSection.getItems().size(), this.stringIndexSectionOffset);
/* 1145 */     writeMapItem(writer, 2, this.typeSection.getItems().size(), this.typeSectionOffset);
/* 1146 */     writeMapItem(writer, 3, this.protoSection.getItems().size(), this.protoSectionOffset);
/* 1147 */     writeMapItem(writer, 4, this.fieldSection.getItems().size(), this.fieldSectionOffset);
/* 1148 */     writeMapItem(writer, 5, this.methodSection.getItems().size(), this.methodSectionOffset);
/* 1149 */     writeMapItem(writer, 6, this.classSection.getItems().size(), this.classIndexSectionOffset);
/*      */ 
/* 1152 */     writeMapItem(writer, 8194, this.stringSection.getItems().size(), this.stringDataSectionOffset);
/* 1153 */     writeMapItem(writer, 4097, this.typeListSection.getItems().size(), this.typeListSectionOffset);
/* 1154 */     writeMapItem(writer, 8197, this.numEncodedArrayItems, this.encodedArraySectionOffset);
/* 1155 */     writeMapItem(writer, 8196, this.annotationSection.getItems().size(), this.annotationSectionOffset);
/* 1156 */     writeMapItem(writer, 4099, this.annotationSetSection.getItems().size(), this.annotationSetSectionOffset);
/*      */ 
/* 1158 */     writeMapItem(writer, 4098, this.numAnnotationSetRefItems, this.annotationSetRefSectionOffset);
/* 1159 */     writeMapItem(writer, 8198, this.numAnnotationDirectoryItems, this.annotationDirectorySectionOffset);
/*      */ 
/* 1161 */     writeMapItem(writer, 8195, this.numDebugInfoItems, this.debugSectionOffset);
/* 1162 */     writeMapItem(writer, 8193, this.numCodeItemItems, this.codeSectionOffset);
/* 1163 */     writeMapItem(writer, 8192, this.numClassDataItems, this.classDataSectionOffset);
/* 1164 */     writeMapItem(writer, 4096, 1, this.mapSectionOffset);
/*      */   }
/*      */ 
/*      */   private void writeMapItem(DexDataWriter writer, int type, int size, int offset) throws IOException {
/* 1168 */     if (size > 0) {
/* 1169 */       writer.writeUshort(type);
/* 1170 */       writer.writeUshort(0);
/* 1171 */       writer.writeInt(size);
/* 1172 */       writer.writeInt(offset);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void writeHeader(DexDataWriter writer, int dataOffset, int fileSize) throws IOException
/*      */   {
/* 1178 */     writer.write(org.jf.dexlib2.dexbacked.raw.HeaderItem.MAGIC_VALUES[0]);
/*      */ 
/* 1181 */     writer.writeInt(0);
/*      */ 
/* 1184 */     writer.write(new byte[20]);
/*      */ 
/* 1186 */     writer.writeInt(fileSize);
/* 1187 */     writer.writeInt(112);
/* 1188 */     writer.writeInt(305419896);
/*      */ 
/* 1191 */     writer.writeInt(0);
/* 1192 */     writer.writeInt(0);
/*      */ 
/* 1195 */     writer.writeInt(this.mapSectionOffset);
/*      */ 
/* 1199 */     writeSectionInfo(writer, this.stringSection.getItems().size(), this.stringIndexSectionOffset);
/* 1200 */     writeSectionInfo(writer, this.typeSection.getItems().size(), this.typeSectionOffset);
/* 1201 */     writeSectionInfo(writer, this.protoSection.getItems().size(), this.protoSectionOffset);
/* 1202 */     writeSectionInfo(writer, this.fieldSection.getItems().size(), this.fieldSectionOffset);
/* 1203 */     writeSectionInfo(writer, this.methodSection.getItems().size(), this.methodSectionOffset);
/* 1204 */     writeSectionInfo(writer, this.classSection.getItems().size(), this.classIndexSectionOffset);
/*      */ 
/* 1207 */     writer.writeInt(fileSize - dataOffset);
/* 1208 */     writer.writeInt(dataOffset);
/*      */   }
/*      */ 
/*      */   private void writeSectionInfo(DexDataWriter writer, int numItems, int offset) throws IOException {
/* 1212 */     writer.writeInt(numItems);
/* 1213 */     if (numItems > 0)
/* 1214 */       writer.writeInt(offset);
/*      */     else
/* 1216 */       writer.writeInt(0);
/*      */   }
/*      */ 
/*      */   private static class CodeItemOffset<MethodKey>
/*      */   {
/*      */     MethodKey method;
/*      */     int codeOffset;
/*      */ 
/*      */     private CodeItemOffset(MethodKey method, int codeOffset)
/*      */     {
/*  706 */       this.codeOffset = codeOffset;
/*  707 */       this.method = method;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class EncodedArrayKey<EncodedValue>
/*      */   {
/*      */     Collection<? extends EncodedValue> elements;
/*      */ 
/*      */     public int hashCode()
/*      */     {
/*  497 */       return CollectionUtils.listHashCode(this.elements);
/*      */     }
/*      */ 
/*      */     public boolean equals(Object o) {
/*  501 */       if ((o instanceof EncodedArrayKey)) {
/*  502 */         EncodedArrayKey other = (EncodedArrayKey)o;
/*  503 */         if (this.elements.size() != other.elements.size()) {
/*  504 */           return false;
/*      */         }
/*  506 */         return Iterables.elementsEqual(this.elements, other.elements);
/*      */       }
/*  508 */       return false;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected class InternalEncodedValueWriter extends EncodedValueWriter<StringKey, TypeKey, FieldRefKey, MethodRefKey, AnnotationElement, EncodedValue>
/*      */   {
/*      */     private InternalEncodedValueWriter(DexDataWriter writer)
/*      */     {
/*  173 */       super(DexWriter.this.stringSection, DexWriter.this.typeSection, DexWriter.this.fieldSection, DexWriter.this.methodSection, DexWriter.this.annotationSection);
/*      */     }
/*      */ 
/*      */     protected void writeEncodedValue(EncodedValue encodedValue) throws IOException {
/*  177 */       DexWriter.this.writeEncodedValue(this, encodedValue);
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.DexWriter
 * JD-Core Version:    0.6.0
 */