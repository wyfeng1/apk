/*     */ package org.jf.dexlib2.writer.builder;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.FluentIterable;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Ordering;
/*     */ import java.io.IOException;
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.SortedSet;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import org.jf.dexlib2.builder.MutableMethodImplementation;
/*     */ import org.jf.dexlib2.iface.ExceptionHandler;
/*     */ import org.jf.dexlib2.iface.Field;
/*     */ import org.jf.dexlib2.iface.MethodImplementation;
/*     */ import org.jf.dexlib2.iface.TryBlock;
/*     */ import org.jf.dexlib2.iface.debug.DebugItem;
/*     */ import org.jf.dexlib2.iface.debug.EndLocal;
/*     */ import org.jf.dexlib2.iface.debug.LineNumber;
/*     */ import org.jf.dexlib2.iface.debug.RestartLocal;
/*     */ import org.jf.dexlib2.iface.debug.SetSourceFile;
/*     */ import org.jf.dexlib2.iface.debug.StartLocal;
/*     */ import org.jf.dexlib2.iface.instruction.Instruction;
/*     */ import org.jf.dexlib2.iface.reference.StringReference;
/*     */ import org.jf.dexlib2.iface.reference.TypeReference;
/*     */ import org.jf.dexlib2.iface.value.EncodedValue;
/*     */ import org.jf.dexlib2.util.EncodedValueUtils;
/*     */ import org.jf.dexlib2.writer.ClassSection;
/*     */ import org.jf.dexlib2.writer.DebugWriter;
/*     */ import org.jf.util.AbstractForwardSequentialList;
/*     */ import org.jf.util.CollectionUtils;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ public class BuilderClassPool
/*     */   implements ClassSection<BuilderStringReference, BuilderTypeReference, BuilderTypeList, BuilderClassDef, BuilderField, BuilderMethod, BuilderAnnotationSet, BuilderEncodedValues.BuilderEncodedValue>
/*     */ {
/*  65 */   private final ConcurrentMap<String, BuilderClassDef> internedItems = Maps.newConcurrentMap();
/*     */ 
/*  79 */   private ImmutableList<BuilderClassDef> sortedClasses = null;
/*     */ 
/* 133 */   private static final Predicate<Field> HAS_INITIALIZER = new Predicate()
/*     */   {
/*     */     public boolean apply(Field input) {
/* 136 */       EncodedValue encodedValue = input.getInitialValue();
/* 137 */       return (encodedValue != null) && (!EncodedValueUtils.isDefaultValue(encodedValue));
/*     */     }
/* 133 */   };
/*     */ 
/* 141 */   private static final Function<BuilderField, BuilderEncodedValues.BuilderEncodedValue> GET_INITIAL_VALUE = new Function()
/*     */   {
/*     */     public BuilderEncodedValues.BuilderEncodedValue apply(BuilderField input)
/*     */     {
/* 145 */       BuilderEncodedValues.BuilderEncodedValue initialValue = input.getInitialValue();
/* 146 */       if (initialValue == null) {
/* 147 */         return BuilderEncodedValues.defaultValueForType(input.getType());
/*     */       }
/* 149 */       return initialValue;
/*     */     }
/* 141 */   };
/*     */ 
/* 233 */   private static final Predicate<BuilderMethodParameter> HAS_PARAMETER_ANNOTATIONS = new Predicate()
/*     */   {
/*     */     public boolean apply(BuilderMethodParameter input)
/*     */     {
/* 237 */       return input.getAnnotations().size() > 0;
/*     */     }
/* 233 */   };
/*     */ 
/* 241 */   private static final Function<BuilderMethodParameter, BuilderAnnotationSet> PARAMETER_ANNOTATIONS = new Function()
/*     */   {
/*     */     public BuilderAnnotationSet apply(BuilderMethodParameter input)
/*     */     {
/* 245 */       return input.getAnnotations();
/*     */     }
/* 241 */   };
/*     */ 
/*     */   BuilderClassDef internClass(BuilderClassDef classDef)
/*     */   {
/*  72 */     BuilderClassDef prev = (BuilderClassDef)this.internedItems.put(classDef.getType(), classDef);
/*  73 */     if (prev != null) {
/*  74 */       throw new ExceptionWithContext("Class %s has already been interned", new Object[] { classDef.getType() });
/*     */     }
/*  76 */     return classDef;
/*     */   }
/*     */ 
/*     */   public Collection<? extends BuilderClassDef> getSortedClasses()
/*     */   {
/*  81 */     if (this.sortedClasses == null) {
/*  82 */       this.sortedClasses = Ordering.natural().immutableSortedCopy(this.internedItems.values());
/*     */     }
/*  84 */     return this.sortedClasses;
/*     */   }
/*     */ 
/*     */   public Map.Entry<? extends BuilderClassDef, Integer> getClassEntryByType(BuilderTypeReference type)
/*     */   {
/*  89 */     if (type == null) {
/*  90 */       return null;
/*     */     }
/*     */ 
/*  93 */     BuilderClassDef classDef = (BuilderClassDef)this.internedItems.get(type.getType());
/*  94 */     if (classDef == null) {
/*  95 */       return null;
/*     */     }
/*     */ 
/*  98 */     return new Map.Entry(classDef) {
/*     */       public BuilderClassDef getKey() {
/* 100 */         return this.val$classDef;
/*     */       }
/*     */ 
/*     */       public Integer getValue() {
/* 104 */         return Integer.valueOf(this.val$classDef.classDefIndex);
/*     */       }
/*     */ 
/*     */       public Integer setValue(Integer value) {
/* 108 */         return Integer.valueOf(this.val$classDef.classDefIndex = value.intValue());
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   public BuilderTypeReference getType(BuilderClassDef builderClassDef) {
/* 114 */     return builderClassDef.type;
/*     */   }
/*     */ 
/*     */   public int getAccessFlags(BuilderClassDef builderClassDef) {
/* 118 */     return builderClassDef.accessFlags;
/*     */   }
/*     */ 
/*     */   public BuilderTypeReference getSuperclass(BuilderClassDef builderClassDef) {
/* 122 */     return builderClassDef.superclass;
/*     */   }
/*     */ 
/*     */   public BuilderTypeList getSortedInterfaces(BuilderClassDef builderClassDef) {
/* 126 */     return builderClassDef.interfaces;
/*     */   }
/*     */ 
/*     */   public BuilderStringReference getSourceFile(BuilderClassDef builderClassDef) {
/* 130 */     return builderClassDef.sourceFile;
/*     */   }
/*     */ 
/*     */   public Collection<? extends BuilderEncodedValues.BuilderEncodedValue> getStaticInitializers(BuilderClassDef classDef)
/*     */   {
/* 155 */     SortedSet sortedStaticFields = classDef.getStaticFields();
/*     */ 
/* 157 */     int lastIndex = CollectionUtils.lastIndexOf(sortedStaticFields, HAS_INITIALIZER);
/* 158 */     if (lastIndex > -1)
/* 159 */       return new AbstractCollection(sortedStaticFields, lastIndex) {
/*     */         public Iterator<BuilderEncodedValues.BuilderEncodedValue> iterator() {
/* 161 */           return FluentIterable.from(this.val$sortedStaticFields).limit(this.val$lastIndex + 1).transform(BuilderClassPool.GET_INITIAL_VALUE).iterator();
/*     */         }
/*     */ 
/*     */         public int size()
/*     */         {
/* 167 */           return this.val$lastIndex + 1;
/*     */         }
/*     */       };
/* 171 */     return null;
/*     */   }
/*     */ 
/*     */   public Collection<? extends BuilderField> getSortedStaticFields(BuilderClassDef builderClassDef)
/*     */   {
/* 176 */     return builderClassDef.getStaticFields();
/*     */   }
/*     */ 
/*     */   public Collection<? extends BuilderField> getSortedInstanceFields(BuilderClassDef builderClassDef)
/*     */   {
/* 181 */     return builderClassDef.getInstanceFields();
/*     */   }
/*     */ 
/*     */   public Collection<? extends BuilderField> getSortedFields(BuilderClassDef builderClassDef)
/*     */   {
/* 186 */     return builderClassDef.getFields();
/*     */   }
/*     */ 
/*     */   public Collection<? extends BuilderMethod> getSortedDirectMethods(BuilderClassDef builderClassDef)
/*     */   {
/* 191 */     return builderClassDef.getDirectMethods();
/*     */   }
/*     */ 
/*     */   public Collection<? extends BuilderMethod> getSortedVirtualMethods(BuilderClassDef builderClassDef)
/*     */   {
/* 196 */     return builderClassDef.getVirtualMethods();
/*     */   }
/*     */ 
/*     */   public Collection<? extends BuilderMethod> getSortedMethods(BuilderClassDef builderClassDef)
/*     */   {
/* 201 */     return builderClassDef.getMethods();
/*     */   }
/*     */ 
/*     */   public int getFieldAccessFlags(BuilderField builderField) {
/* 205 */     return builderField.accessFlags;
/*     */   }
/*     */ 
/*     */   public int getMethodAccessFlags(BuilderMethod builderMethod) {
/* 209 */     return builderMethod.accessFlags;
/*     */   }
/*     */ 
/*     */   public BuilderAnnotationSet getClassAnnotations(BuilderClassDef builderClassDef) {
/* 213 */     if (builderClassDef.annotations.isEmpty()) {
/* 214 */       return null;
/*     */     }
/* 216 */     return builderClassDef.annotations;
/*     */   }
/*     */ 
/*     */   public BuilderAnnotationSet getFieldAnnotations(BuilderField builderField) {
/* 220 */     if (builderField.annotations.isEmpty()) {
/* 221 */       return null;
/*     */     }
/* 223 */     return builderField.annotations;
/*     */   }
/*     */ 
/*     */   public BuilderAnnotationSet getMethodAnnotations(BuilderMethod builderMethod) {
/* 227 */     if (builderMethod.annotations.isEmpty()) {
/* 228 */       return null;
/*     */     }
/* 230 */     return builderMethod.annotations;
/*     */   }
/*     */ 
/*     */   public List<? extends BuilderAnnotationSet> getParameterAnnotations(BuilderMethod method)
/*     */   {
/* 251 */     List parameters = method.getParameters();
/* 252 */     boolean hasParameterAnnotations = Iterables.any(parameters, HAS_PARAMETER_ANNOTATIONS);
/*     */ 
/* 254 */     if (hasParameterAnnotations)
/* 255 */       return new AbstractForwardSequentialList(parameters) {
/*     */         public Iterator<BuilderAnnotationSet> iterator() {
/* 257 */           return FluentIterable.from(this.val$parameters).transform(BuilderClassPool.PARAMETER_ANNOTATIONS).iterator();
/*     */         }
/*     */ 
/*     */         public int size()
/*     */         {
/* 262 */           return this.val$parameters.size();
/*     */         }
/*     */       };
/* 266 */     return null;
/*     */   }
/*     */ 
/*     */   public Iterable<? extends DebugItem> getDebugItems(BuilderMethod builderMethod)
/*     */   {
/* 271 */     MethodImplementation impl = builderMethod.getImplementation();
/* 272 */     if (impl == null) {
/* 273 */       return null;
/*     */     }
/* 275 */     return impl.getDebugItems();
/*     */   }
/*     */ 
/*     */   public Iterable<? extends BuilderStringReference> getParameterNames(BuilderMethod method)
/*     */   {
/* 280 */     return Iterables.transform(method.getParameters(), new Function() {
/*     */       public BuilderStringReference apply(BuilderMethodParameter input) {
/* 282 */         return input.name;
/*     */       } } );
/*     */   }
/*     */ 
/*     */   public int getRegisterCount(BuilderMethod builderMethod) {
/* 288 */     MethodImplementation impl = builderMethod.getImplementation();
/* 289 */     if (impl == null) {
/* 290 */       return 0;
/*     */     }
/* 292 */     return impl.getRegisterCount();
/*     */   }
/*     */ 
/*     */   public Iterable<? extends Instruction> getInstructions(BuilderMethod builderMethod)
/*     */   {
/* 297 */     MethodImplementation impl = builderMethod.getImplementation();
/* 298 */     if (impl == null) {
/* 299 */       return null;
/*     */     }
/* 301 */     return impl.getInstructions();
/*     */   }
/*     */ 
/*     */   public List<? extends TryBlock<? extends ExceptionHandler>> getTryBlocks(BuilderMethod builderMethod)
/*     */   {
/* 306 */     MethodImplementation impl = builderMethod.getImplementation();
/* 307 */     if (impl == null) {
/* 308 */       return ImmutableList.of();
/*     */     }
/* 310 */     return impl.getTryBlocks();
/*     */   }
/*     */ 
/*     */   public BuilderTypeReference getExceptionType(ExceptionHandler handler) {
/* 314 */     return checkTypeReference(handler.getExceptionTypeReference());
/*     */   }
/*     */ 
/*     */   public MutableMethodImplementation makeMutableMethodImplementation(BuilderMethod builderMethod)
/*     */   {
/* 319 */     MethodImplementation impl = builderMethod.getImplementation();
/* 320 */     if ((impl instanceof MutableMethodImplementation)) {
/* 321 */       return (MutableMethodImplementation)impl;
/*     */     }
/* 323 */     return new MutableMethodImplementation(impl);
/*     */   }
/*     */ 
/*     */   public void setEncodedArrayOffset(BuilderClassDef builderClassDef, int offset) {
/* 327 */     builderClassDef.encodedArrayOffset = offset;
/*     */   }
/*     */ 
/*     */   public int getEncodedArrayOffset(BuilderClassDef builderClassDef) {
/* 331 */     return builderClassDef.encodedArrayOffset;
/*     */   }
/*     */ 
/*     */   public void setAnnotationDirectoryOffset(BuilderClassDef builderClassDef, int offset) {
/* 335 */     builderClassDef.annotationDirectoryOffset = offset;
/*     */   }
/*     */ 
/*     */   public int getAnnotationDirectoryOffset(BuilderClassDef builderClassDef) {
/* 339 */     return builderClassDef.annotationDirectoryOffset;
/*     */   }
/*     */ 
/*     */   public void setAnnotationSetRefListOffset(BuilderMethod builderMethod, int offset) {
/* 343 */     builderMethod.annotationSetRefListOffset = offset;
/*     */   }
/*     */ 
/*     */   public int getAnnotationSetRefListOffset(BuilderMethod builderMethod) {
/* 347 */     return builderMethod.annotationSetRefListOffset;
/*     */   }
/*     */ 
/*     */   public void setCodeItemOffset(BuilderMethod builderMethod, int offset) {
/* 351 */     builderMethod.codeItemOffset = offset;
/*     */   }
/*     */ 
/*     */   public int getCodeItemOffset(BuilderMethod builderMethod) {
/* 355 */     return builderMethod.codeItemOffset;
/*     */   }
/*     */ 
/*     */   private BuilderStringReference checkStringReference(StringReference stringReference) {
/* 359 */     if (stringReference == null)
/* 360 */       return null;
/*     */     try
/*     */     {
/* 363 */       return (BuilderStringReference)stringReference; } catch (ClassCastException ex) {
/*     */     }
/* 365 */     throw new IllegalStateException("Only StringReference instances returned by DexBuilder.internStringReference or DexBuilder.internNullableStringReference may be used.");
/*     */   }
/*     */ 
/*     */   private BuilderTypeReference checkTypeReference(TypeReference typeReference)
/*     */   {
/* 371 */     if (typeReference == null)
/* 372 */       return null;
/*     */     try
/*     */     {
/* 375 */       return (BuilderTypeReference)typeReference; } catch (ClassCastException ex) {
/*     */     }
/* 377 */     throw new IllegalStateException("Only TypeReference instances returned by DexBuilder.internTypeReference or DexBuilder.internNullableTypeReference may be used.");
/*     */   }
/*     */ 
/*     */   public void writeDebugItem(DebugWriter<BuilderStringReference, BuilderTypeReference> writer, DebugItem debugItem)
/*     */     throws IOException
/*     */   {
/* 385 */     switch (debugItem.getDebugItemType()) {
/*     */     case 3:
/* 387 */       StartLocal startLocal = (StartLocal)debugItem;
/* 388 */       writer.writeStartLocal(startLocal.getCodeAddress(), startLocal.getRegister(), checkStringReference(startLocal.getNameReference()), checkTypeReference(startLocal.getTypeReference()), checkStringReference(startLocal.getSignatureReference()));
/*     */ 
/* 393 */       break;
/*     */     case 5:
/* 396 */       EndLocal endLocal = (EndLocal)debugItem;
/* 397 */       writer.writeEndLocal(endLocal.getCodeAddress(), endLocal.getRegister());
/* 398 */       break;
/*     */     case 6:
/* 401 */       RestartLocal restartLocal = (RestartLocal)debugItem;
/* 402 */       writer.writeRestartLocal(restartLocal.getCodeAddress(), restartLocal.getRegister());
/* 403 */       break;
/*     */     case 7:
/* 406 */       writer.writePrologueEnd(debugItem.getCodeAddress());
/* 407 */       break;
/*     */     case 8:
/* 410 */       writer.writeEpilogueBegin(debugItem.getCodeAddress());
/* 411 */       break;
/*     */     case 10:
/* 414 */       LineNumber lineNumber = (LineNumber)debugItem;
/* 415 */       writer.writeLineNumber(lineNumber.getCodeAddress(), lineNumber.getLineNumber());
/* 416 */       break;
/*     */     case 9:
/* 419 */       SetSourceFile setSourceFile = (SetSourceFile)debugItem;
/* 420 */       writer.writeSetSourceFile(setSourceFile.getCodeAddress(), checkStringReference(setSourceFile.getSourceFileReference()));
/*     */     case 4:
/*     */     default:
/* 424 */       throw new ExceptionWithContext("Unexpected debug item type: %d", new Object[] { Integer.valueOf(debugItem.getDebugItemType()) });
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getItemIndex(BuilderClassDef builderClassDef) {
/* 429 */     return builderClassDef.classDefIndex;
/*     */   }
/*     */ 
/*     */   public Collection<? extends Map.Entry<? extends BuilderClassDef, Integer>> getItems() {
/* 433 */     return new BuilderMapEntryCollection(this.internedItems.values()) {
/*     */       protected int getValue(BuilderClassDef key) {
/* 435 */         return key.classDefIndex;
/*     */       }
/*     */ 
/*     */       protected int setValue(BuilderClassDef key, int value) {
/* 439 */         int prev = key.classDefIndex;
/* 440 */         key.classDefIndex = value;
/* 441 */         return prev;
/*     */       }
/*     */     };
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderClassPool
 * JD-Core Version:    0.6.0
 */