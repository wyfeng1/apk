/*     */ package org.jf.dexlib2.writer.builder;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.jf.dexlib2.iface.Annotation;
/*     */ import org.jf.dexlib2.iface.MethodImplementation;
/*     */ import org.jf.dexlib2.iface.MethodParameter;
/*     */ import org.jf.dexlib2.iface.reference.FieldReference;
/*     */ import org.jf.dexlib2.iface.reference.MethodReference;
/*     */ import org.jf.dexlib2.iface.reference.Reference;
/*     */ import org.jf.dexlib2.iface.reference.StringReference;
/*     */ import org.jf.dexlib2.iface.reference.TypeReference;
/*     */ import org.jf.dexlib2.iface.value.BooleanEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.ByteEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.CharEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.DoubleEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.EncodedValue;
/*     */ import org.jf.dexlib2.iface.value.FloatEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.IntEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.LongEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.ShortEncodedValue;
/*     */ import org.jf.dexlib2.writer.DexWriter;
/*     */ import org.jf.dexlib2.writer.DexWriter.InternalEncodedValueWriter;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ public class DexBuilder extends DexWriter<BuilderStringReference, BuilderStringReference, BuilderTypeReference, BuilderTypeReference, BuilderProtoReference, BuilderFieldReference, BuilderMethodReference, BuilderClassDef, BuilderAnnotation, BuilderAnnotationSet, BuilderTypeList, BuilderField, BuilderMethod, BuilderEncodedValues.BuilderEncodedValue, BuilderAnnotationElement>
/*     */ {
/*     */   private final BuilderContext context;
/*     */ 
/*     */   public static DexBuilder makeDexBuilder(int api)
/*     */   {
/*  69 */     BuilderContext context = new BuilderContext();
/*  70 */     return new DexBuilder(api, context);
/*     */   }
/*     */ 
/*     */   private DexBuilder(int api, BuilderContext context) {
/*  74 */     super(api, context.stringPool, context.typePool, context.protoPool, context.fieldPool, context.methodPool, context.classPool, context.typeListPool, context.annotationPool, context.annotationSetPool);
/*     */ 
/*  77 */     this.context = context;
/*     */   }
/*     */ 
/*     */   public BuilderField internField(String definingClass, String name, String type, int accessFlags, EncodedValue initialValue, Set<? extends Annotation> annotations)
/*     */   {
/*  86 */     return new BuilderField(this.context.fieldPool.internField(definingClass, name, type), accessFlags, this.context.internNullableEncodedValue(initialValue), this.context.annotationSetPool.internAnnotationSet(annotations));
/*     */   }
/*     */ 
/*     */   public BuilderMethod internMethod(String definingClass, String name, List<? extends MethodParameter> parameters, String returnType, int accessFlags, Set<? extends Annotation> annotations, MethodImplementation methodImplementation)
/*     */   {
/*  99 */     if (parameters == null) {
/* 100 */       parameters = ImmutableList.of();
/*     */     }
/* 102 */     return new BuilderMethod(this.context.methodPool.internMethod(definingClass, name, parameters, returnType), internMethodParameters(parameters), accessFlags, this.context.annotationSetPool.internAnnotationSet(annotations), methodImplementation);
/*     */   }
/*     */ 
/*     */   public BuilderClassDef internClassDef(String type, int accessFlags, String superclass, List<String> interfaces, String sourceFile, Set<? extends Annotation> annotations, Iterable<? extends BuilderField> fields, Iterable<? extends BuilderMethod> methods)
/*     */   {
/* 117 */     if (interfaces == null) {
/* 118 */       interfaces = ImmutableList.of();
/*     */     } else {
/* 120 */       interfaces = Lists.newArrayList(interfaces);
/* 121 */       Collections.sort(interfaces);
/* 122 */       String prev = null;
/* 123 */       Iterator interfaceIterator = interfaces.iterator();
/* 124 */       while (interfaceIterator.hasNext()) {
/* 125 */         String iface = (String)interfaceIterator.next();
/* 126 */         if ((prev != null) && (iface.equals(prev))) {
/* 127 */           interfaceIterator.remove();
/*     */         }
/* 129 */         prev = iface;
/*     */       }
/*     */     }
/*     */ 
/* 133 */     return this.context.classPool.internClass(new BuilderClassDef(this.context.typePool.internType(type), accessFlags, this.context.typePool.internNullableType(superclass), this.context.typeListPool.internTypeList(interfaces), this.context.stringPool.internNullableString(sourceFile), this.context.annotationSetPool.internAnnotationSet(annotations), fields, methods));
/*     */   }
/*     */ 
/*     */   public BuilderStringReference internStringReference(String string)
/*     */   {
/* 144 */     return this.context.stringPool.internString(string);
/*     */   }
/*     */ 
/*     */   public BuilderStringReference internNullableStringReference(String string) {
/* 148 */     if (string != null) {
/* 149 */       return internStringReference(string);
/*     */     }
/* 151 */     return null;
/*     */   }
/*     */ 
/*     */   public BuilderTypeReference internTypeReference(String type) {
/* 155 */     return this.context.typePool.internType(type);
/*     */   }
/*     */ 
/*     */   public BuilderTypeReference internNullableTypeReference(String type) {
/* 159 */     if (type != null) {
/* 160 */       return internTypeReference(type);
/*     */     }
/* 162 */     return null;
/*     */   }
/*     */ 
/*     */   public BuilderFieldReference internFieldReference(FieldReference field) {
/* 166 */     return this.context.fieldPool.internField(field);
/*     */   }
/*     */ 
/*     */   public BuilderMethodReference internMethodReference(MethodReference method) {
/* 170 */     return this.context.methodPool.internMethod(method);
/*     */   }
/*     */ 
/*     */   public BuilderReference internReference(Reference reference) {
/* 174 */     if ((reference instanceof StringReference)) {
/* 175 */       return internStringReference(((StringReference)reference).getString());
/*     */     }
/* 177 */     if ((reference instanceof TypeReference)) {
/* 178 */       return internTypeReference(((TypeReference)reference).getType());
/*     */     }
/* 180 */     if ((reference instanceof MethodReference)) {
/* 181 */       return internMethodReference((MethodReference)reference);
/*     */     }
/* 183 */     if ((reference instanceof FieldReference)) {
/* 184 */       return internFieldReference((FieldReference)reference);
/*     */     }
/* 186 */     throw new IllegalArgumentException("Could not determine type of reference");
/*     */   }
/*     */ 
/*     */   private List<BuilderMethodParameter> internMethodParameters(List<? extends MethodParameter> methodParameters)
/*     */   {
/* 191 */     if (methodParameters == null) {
/* 192 */       return ImmutableList.of();
/*     */     }
/* 194 */     return ImmutableList.copyOf(Iterators.transform(methodParameters.iterator(), new Function()
/*     */     {
/*     */       public BuilderMethodParameter apply(MethodParameter input) {
/* 197 */         return DexBuilder.this.internMethodParameter(input);
/*     */       } } ));
/*     */   }
/*     */ 
/*     */   private BuilderMethodParameter internMethodParameter(MethodParameter methodParameter) {
/* 203 */     return new BuilderMethodParameter(this.context.typePool.internType(methodParameter.getType()), this.context.stringPool.internNullableString(methodParameter.getName()), this.context.annotationSetPool.internAnnotationSet(methodParameter.getAnnotations()));
/*     */   }
/*     */ 
/*     */   protected void writeEncodedValue(DexWriter<BuilderStringReference, BuilderStringReference, BuilderTypeReference, BuilderTypeReference, BuilderProtoReference, BuilderFieldReference, BuilderMethodReference, BuilderClassDef, BuilderAnnotation, BuilderAnnotationSet, BuilderTypeList, BuilderField, BuilderMethod, BuilderEncodedValues.BuilderEncodedValue, BuilderAnnotationElement>.InternalEncodedValueWriter writer, BuilderEncodedValues.BuilderEncodedValue encodedValue)
/*     */     throws IOException
/*     */   {
/* 211 */     switch (encodedValue.getValueType()) {
/*     */     case 29:
/* 213 */       BuilderEncodedValues.BuilderAnnotationEncodedValue annotationEncodedValue = (BuilderEncodedValues.BuilderAnnotationEncodedValue)encodedValue;
/* 214 */       writer.writeAnnotation(annotationEncodedValue.typeReference, annotationEncodedValue.elements);
/* 215 */       break;
/*     */     case 28:
/* 217 */       BuilderEncodedValues.BuilderArrayEncodedValue arrayEncodedValue = (BuilderEncodedValues.BuilderArrayEncodedValue)encodedValue;
/* 218 */       writer.writeArray(arrayEncodedValue.elements);
/* 219 */       break;
/*     */     case 31:
/* 221 */       writer.writeBoolean(((BooleanEncodedValue)encodedValue).getValue());
/* 222 */       break;
/*     */     case 0:
/* 224 */       writer.writeByte(((ByteEncodedValue)encodedValue).getValue());
/* 225 */       break;
/*     */     case 3:
/* 227 */       writer.writeChar(((CharEncodedValue)encodedValue).getValue());
/* 228 */       break;
/*     */     case 17:
/* 230 */       writer.writeDouble(((DoubleEncodedValue)encodedValue).getValue());
/* 231 */       break;
/*     */     case 27:
/* 233 */       writer.writeEnum(((BuilderEncodedValues.BuilderEnumEncodedValue)encodedValue).getValue());
/* 234 */       break;
/*     */     case 25:
/* 236 */       writer.writeField(((BuilderEncodedValues.BuilderFieldEncodedValue)encodedValue).fieldReference);
/* 237 */       break;
/*     */     case 16:
/* 239 */       writer.writeFloat(((FloatEncodedValue)encodedValue).getValue());
/* 240 */       break;
/*     */     case 4:
/* 242 */       writer.writeInt(((IntEncodedValue)encodedValue).getValue());
/* 243 */       break;
/*     */     case 6:
/* 245 */       writer.writeLong(((LongEncodedValue)encodedValue).getValue());
/* 246 */       break;
/*     */     case 26:
/* 248 */       writer.writeMethod(((BuilderEncodedValues.BuilderMethodEncodedValue)encodedValue).methodReference);
/* 249 */       break;
/*     */     case 30:
/* 251 */       writer.writeNull();
/* 252 */       break;
/*     */     case 2:
/* 254 */       writer.writeShort(((ShortEncodedValue)encodedValue).getValue());
/* 255 */       break;
/*     */     case 23:
/* 257 */       writer.writeString(((BuilderEncodedValues.BuilderStringEncodedValue)encodedValue).stringReference);
/* 258 */       break;
/*     */     case 24:
/* 260 */       writer.writeType(((BuilderEncodedValues.BuilderTypeEncodedValue)encodedValue).typeReference);
/* 261 */       break;
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
/*     */     case 22:
/*     */     default:
/* 263 */       throw new ExceptionWithContext("Unrecognized value type: %d", new Object[] { Integer.valueOf(encodedValue.getValueType()) });
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.DexBuilder
 * JD-Core Version:    0.6.0
 */