/*     */ package org.jf.dexlib2.writer.builder;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Iterators;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.jf.dexlib2.iface.AnnotationElement;
/*     */ import org.jf.dexlib2.iface.value.AnnotationEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.ArrayEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.BooleanEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.ByteEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.CharEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.DoubleEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.EncodedValue;
/*     */ import org.jf.dexlib2.iface.value.EnumEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.FieldEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.FloatEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.IntEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.LongEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.MethodEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.ShortEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.StringEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.TypeEncodedValue;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ class BuilderContext
/*     */ {
/*     */   final BuilderStringPool stringPool;
/*     */   final BuilderTypePool typePool;
/*     */   final BuilderFieldPool fieldPool;
/*     */   final BuilderMethodPool methodPool;
/*     */   final BuilderProtoPool protoPool;
/*     */   final BuilderClassPool classPool;
/*     */   final BuilderTypeListPool typeListPool;
/*     */   final BuilderAnnotationPool annotationPool;
/*     */   final BuilderAnnotationSetPool annotationSetPool;
/*     */ 
/*     */   BuilderContext()
/*     */   {
/*  63 */     this.stringPool = new BuilderStringPool();
/*  64 */     this.typePool = new BuilderTypePool(this);
/*  65 */     this.fieldPool = new BuilderFieldPool(this);
/*  66 */     this.methodPool = new BuilderMethodPool(this);
/*  67 */     this.protoPool = new BuilderProtoPool(this);
/*  68 */     this.classPool = new BuilderClassPool();
/*     */ 
/*  70 */     this.typeListPool = new BuilderTypeListPool(this);
/*  71 */     this.annotationPool = new BuilderAnnotationPool(this);
/*  72 */     this.annotationSetPool = new BuilderAnnotationSetPool(this);
/*     */   }
/*     */ 
/*     */   Set<? extends BuilderAnnotationElement> internAnnotationElements(Set<? extends AnnotationElement> elements)
/*     */   {
/*  77 */     return ImmutableSet.copyOf(Iterators.transform(elements.iterator(), new Function()
/*     */     {
/*     */       public BuilderAnnotationElement apply(AnnotationElement input)
/*     */       {
/*  82 */         return BuilderContext.this.internAnnotationElement(input);
/*     */       } } ));
/*     */   }
/*     */ 
/*     */   private BuilderAnnotationElement internAnnotationElement(AnnotationElement annotationElement) {
/*  88 */     return new BuilderAnnotationElement(this.stringPool.internString(annotationElement.getName()), internEncodedValue(annotationElement.getValue()));
/*     */   }
/*     */ 
/*     */   BuilderEncodedValues.BuilderEncodedValue internNullableEncodedValue(EncodedValue encodedValue)
/*     */   {
/*  93 */     if (encodedValue == null) {
/*  94 */       return null;
/*     */     }
/*  96 */     return internEncodedValue(encodedValue);
/*     */   }
/*     */ 
/*     */   private BuilderEncodedValues.BuilderEncodedValue internEncodedValue(EncodedValue encodedValue) {
/* 100 */     switch (encodedValue.getValueType()) {
/*     */     case 29:
/* 102 */       return internAnnotationEncodedValue((AnnotationEncodedValue)encodedValue);
/*     */     case 28:
/* 104 */       return internArrayEncodedValue((ArrayEncodedValue)encodedValue);
/*     */     case 31:
/* 106 */       boolean value = ((BooleanEncodedValue)encodedValue).getValue();
/* 107 */       return value ? BuilderEncodedValues.BuilderBooleanEncodedValue.TRUE_VALUE : BuilderEncodedValues.BuilderBooleanEncodedValue.FALSE_VALUE;
/*     */     case 0:
/* 109 */       return new BuilderEncodedValues.BuilderByteEncodedValue(((ByteEncodedValue)encodedValue).getValue());
/*     */     case 3:
/* 111 */       return new BuilderEncodedValues.BuilderCharEncodedValue(((CharEncodedValue)encodedValue).getValue());
/*     */     case 17:
/* 113 */       return new BuilderEncodedValues.BuilderDoubleEncodedValue(((DoubleEncodedValue)encodedValue).getValue());
/*     */     case 27:
/* 115 */       return internEnumEncodedValue((EnumEncodedValue)encodedValue);
/*     */     case 25:
/* 117 */       return internFieldEncodedValue((FieldEncodedValue)encodedValue);
/*     */     case 16:
/* 119 */       return new BuilderEncodedValues.BuilderFloatEncodedValue(((FloatEncodedValue)encodedValue).getValue());
/*     */     case 4:
/* 121 */       return new BuilderEncodedValues.BuilderIntEncodedValue(((IntEncodedValue)encodedValue).getValue());
/*     */     case 6:
/* 123 */       return new BuilderEncodedValues.BuilderLongEncodedValue(((LongEncodedValue)encodedValue).getValue());
/*     */     case 26:
/* 125 */       return internMethodEncodedValue((MethodEncodedValue)encodedValue);
/*     */     case 30:
/* 127 */       return BuilderEncodedValues.BuilderNullEncodedValue.INSTANCE;
/*     */     case 2:
/* 129 */       return new BuilderEncodedValues.BuilderShortEncodedValue(((ShortEncodedValue)encodedValue).getValue());
/*     */     case 23:
/* 131 */       return internStringEncodedValue((StringEncodedValue)encodedValue);
/*     */     case 24:
/* 133 */       return internTypeEncodedValue((TypeEncodedValue)encodedValue);
/*     */     case 1:
/*     */     case 5:
/*     */     case 7:
/*     */     case 8:
/*     */     case 9:
/*     */     case 10:
/*     */     case 11:
/*     */     case 12:
/*     */     case 13:
/*     */     case 14:
/*     */     case 15:
/*     */     case 18:
/*     */     case 19:
/*     */     case 20:
/*     */     case 21:
/* 135 */     case 22: } throw new ExceptionWithContext("Unexpected encoded value type: %d", new Object[] { Integer.valueOf(encodedValue.getValueType()) });
/*     */   }
/*     */ 
/*     */   private BuilderEncodedValues.BuilderAnnotationEncodedValue internAnnotationEncodedValue(AnnotationEncodedValue value)
/*     */   {
/* 140 */     return new BuilderEncodedValues.BuilderAnnotationEncodedValue(this.typePool.internType(value.getType()), internAnnotationElements(value.getElements()));
/*     */   }
/*     */ 
/*     */   private BuilderEncodedValues.BuilderArrayEncodedValue internArrayEncodedValue(ArrayEncodedValue value)
/*     */   {
/* 146 */     return new BuilderEncodedValues.BuilderArrayEncodedValue(ImmutableList.copyOf(Iterators.transform(value.getValue().iterator(), new Function()
/*     */     {
/*     */       public BuilderEncodedValues.BuilderEncodedValue apply(EncodedValue input)
/*     */       {
/* 151 */         return BuilderContext.this.internEncodedValue(input);
/*     */       } } )));
/*     */   }
/*     */ 
/*     */   private BuilderEncodedValues.BuilderEnumEncodedValue internEnumEncodedValue(EnumEncodedValue value) {
/* 157 */     return new BuilderEncodedValues.BuilderEnumEncodedValue(this.fieldPool.internField(value.getValue()));
/*     */   }
/*     */ 
/*     */   private BuilderEncodedValues.BuilderFieldEncodedValue internFieldEncodedValue(FieldEncodedValue value) {
/* 161 */     return new BuilderEncodedValues.BuilderFieldEncodedValue(this.fieldPool.internField(value.getValue()));
/*     */   }
/*     */ 
/*     */   private BuilderEncodedValues.BuilderMethodEncodedValue internMethodEncodedValue(MethodEncodedValue value) {
/* 165 */     return new BuilderEncodedValues.BuilderMethodEncodedValue(this.methodPool.internMethod(value.getValue()));
/*     */   }
/*     */ 
/*     */   private BuilderEncodedValues.BuilderStringEncodedValue internStringEncodedValue(StringEncodedValue string) {
/* 169 */     return new BuilderEncodedValues.BuilderStringEncodedValue(this.stringPool.internString(string.getValue()));
/*     */   }
/*     */ 
/*     */   private BuilderEncodedValues.BuilderTypeEncodedValue internTypeEncodedValue(TypeEncodedValue type) {
/* 173 */     return new BuilderEncodedValues.BuilderTypeEncodedValue(this.typePool.internType(type.getValue()));
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderContext
 * JD-Core Version:    0.6.0
 */