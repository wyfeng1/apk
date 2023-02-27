/*     */ package org.jf.dexlib2.writer.builder;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.jf.dexlib2.base.value.BaseAnnotationEncodedValue;
/*     */ import org.jf.dexlib2.base.value.BaseArrayEncodedValue;
/*     */ import org.jf.dexlib2.base.value.BaseBooleanEncodedValue;
/*     */ import org.jf.dexlib2.base.value.BaseEnumEncodedValue;
/*     */ import org.jf.dexlib2.base.value.BaseFieldEncodedValue;
/*     */ import org.jf.dexlib2.base.value.BaseMethodEncodedValue;
/*     */ import org.jf.dexlib2.base.value.BaseNullEncodedValue;
/*     */ import org.jf.dexlib2.base.value.BaseStringEncodedValue;
/*     */ import org.jf.dexlib2.base.value.BaseTypeEncodedValue;
/*     */ import org.jf.dexlib2.iface.value.EncodedValue;
/*     */ import org.jf.dexlib2.immutable.value.ImmutableByteEncodedValue;
/*     */ import org.jf.dexlib2.immutable.value.ImmutableCharEncodedValue;
/*     */ import org.jf.dexlib2.immutable.value.ImmutableDoubleEncodedValue;
/*     */ import org.jf.dexlib2.immutable.value.ImmutableFloatEncodedValue;
/*     */ import org.jf.dexlib2.immutable.value.ImmutableIntEncodedValue;
/*     */ import org.jf.dexlib2.immutable.value.ImmutableLongEncodedValue;
/*     */ import org.jf.dexlib2.immutable.value.ImmutableShortEncodedValue;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ public abstract class BuilderEncodedValues
/*     */ {
/*     */   public static BuilderEncodedValue defaultValueForType(String type)
/*     */   {
/*  81 */     switch (type.charAt(0)) {
/*     */     case 'Z':
/*  83 */       return BuilderBooleanEncodedValue.FALSE_VALUE;
/*     */     case 'B':
/*  85 */       return new BuilderByteEncodedValue(0);
/*     */     case 'S':
/*  87 */       return new BuilderShortEncodedValue(0);
/*     */     case 'C':
/*  89 */       return new BuilderCharEncodedValue('\000');
/*     */     case 'I':
/*  91 */       return new BuilderIntEncodedValue(0);
/*     */     case 'J':
/*  93 */       return new BuilderLongEncodedValue(0L);
/*     */     case 'F':
/*  95 */       return new BuilderFloatEncodedValue(0.0F);
/*     */     case 'D':
/*  97 */       return new BuilderDoubleEncodedValue(0.0D);
/*     */     case 'L':
/*     */     case '[':
/* 100 */       return BuilderNullEncodedValue.INSTANCE;
/*     */     case 'E':
/*     */     case 'G':
/*     */     case 'H':
/*     */     case 'K':
/*     */     case 'M':
/*     */     case 'N':
/*     */     case 'O':
/*     */     case 'P':
/*     */     case 'Q':
/*     */     case 'R':
/*     */     case 'T':
/*     */     case 'U':
/*     */     case 'V':
/*     */     case 'W':
/*     */     case 'X':
/* 102 */     case 'Y': } throw new ExceptionWithContext("Unrecognized type: %s", new Object[] { type });
/*     */   }
/*     */ 
/*     */   public static class BuilderTypeEncodedValue extends BaseTypeEncodedValue
/*     */     implements BuilderEncodedValues.BuilderEncodedValue
/*     */   {
/*     */     final BuilderTypeReference typeReference;
/*     */ 
/*     */     BuilderTypeEncodedValue(BuilderTypeReference typeReference)
/*     */     {
/* 235 */       this.typeReference = typeReference;
/*     */     }
/*     */ 
/*     */     public String getValue() {
/* 239 */       return this.typeReference.getType();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class BuilderStringEncodedValue extends BaseStringEncodedValue
/*     */     implements BuilderEncodedValues.BuilderEncodedValue
/*     */   {
/*     */     final BuilderStringReference stringReference;
/*     */ 
/*     */     BuilderStringEncodedValue(BuilderStringReference stringReference)
/*     */     {
/* 222 */       this.stringReference = stringReference;
/*     */     }
/*     */ 
/*     */     public String getValue() {
/* 226 */       return this.stringReference.getString();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class BuilderShortEncodedValue extends ImmutableShortEncodedValue
/*     */     implements BuilderEncodedValues.BuilderEncodedValue
/*     */   {
/*     */     public BuilderShortEncodedValue(short value)
/*     */     {
/* 213 */       super();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class BuilderNullEncodedValue extends BaseNullEncodedValue
/*     */     implements BuilderEncodedValues.BuilderEncodedValue
/*     */   {
/* 205 */     public static final BuilderNullEncodedValue INSTANCE = new BuilderNullEncodedValue();
/*     */   }
/*     */ 
/*     */   public static class BuilderMethodEncodedValue extends BaseMethodEncodedValue
/*     */     implements BuilderEncodedValues.BuilderEncodedValue
/*     */   {
/*     */     final BuilderMethodReference methodReference;
/*     */ 
/*     */     BuilderMethodEncodedValue(BuilderMethodReference methodReference)
/*     */     {
/* 195 */       this.methodReference = methodReference;
/*     */     }
/*     */ 
/*     */     public BuilderMethodReference getValue() {
/* 199 */       return this.methodReference;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class BuilderLongEncodedValue extends ImmutableLongEncodedValue
/*     */     implements BuilderEncodedValues.BuilderEncodedValue
/*     */   {
/*     */     public BuilderLongEncodedValue(long value)
/*     */     {
/* 186 */       super();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class BuilderIntEncodedValue extends ImmutableIntEncodedValue
/*     */     implements BuilderEncodedValues.BuilderEncodedValue
/*     */   {
/*     */     public BuilderIntEncodedValue(int value)
/*     */     {
/* 179 */       super();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class BuilderFloatEncodedValue extends ImmutableFloatEncodedValue
/*     */     implements BuilderEncodedValues.BuilderEncodedValue
/*     */   {
/*     */     public BuilderFloatEncodedValue(float value)
/*     */     {
/* 172 */       super();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class BuilderFieldEncodedValue extends BaseFieldEncodedValue
/*     */     implements BuilderEncodedValues.BuilderEncodedValue
/*     */   {
/*     */     final BuilderFieldReference fieldReference;
/*     */ 
/*     */     BuilderFieldEncodedValue(BuilderFieldReference fieldReference)
/*     */     {
/* 161 */       this.fieldReference = fieldReference;
/*     */     }
/*     */ 
/*     */     public BuilderFieldReference getValue() {
/* 165 */       return this.fieldReference;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class BuilderEnumEncodedValue extends BaseEnumEncodedValue
/*     */     implements BuilderEncodedValues.BuilderEncodedValue
/*     */   {
/*     */     final BuilderFieldReference enumReference;
/*     */ 
/*     */     BuilderEnumEncodedValue(BuilderFieldReference enumReference)
/*     */     {
/* 148 */       this.enumReference = enumReference;
/*     */     }
/*     */ 
/*     */     public BuilderFieldReference getValue() {
/* 152 */       return this.enumReference;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class BuilderDoubleEncodedValue extends ImmutableDoubleEncodedValue
/*     */     implements BuilderEncodedValues.BuilderEncodedValue
/*     */   {
/*     */     public BuilderDoubleEncodedValue(double value)
/*     */     {
/* 139 */       super();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class BuilderCharEncodedValue extends ImmutableCharEncodedValue
/*     */     implements BuilderEncodedValues.BuilderEncodedValue
/*     */   {
/*     */     public BuilderCharEncodedValue(char value)
/*     */     {
/* 132 */       super();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class BuilderByteEncodedValue extends ImmutableByteEncodedValue
/*     */     implements BuilderEncodedValues.BuilderEncodedValue
/*     */   {
/*     */     public BuilderByteEncodedValue(byte value)
/*     */     {
/* 125 */       super();
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class BuilderBooleanEncodedValue extends BaseBooleanEncodedValue
/*     */     implements BuilderEncodedValues.BuilderEncodedValue
/*     */   {
/* 108 */     public static final BuilderBooleanEncodedValue TRUE_VALUE = new BuilderBooleanEncodedValue(true);
/* 109 */     public static final BuilderBooleanEncodedValue FALSE_VALUE = new BuilderBooleanEncodedValue(false);
/*     */     private final boolean value;
/*     */ 
/*     */     private BuilderBooleanEncodedValue(boolean value)
/*     */     {
/* 114 */       this.value = value;
/*     */     }
/*     */ 
/*     */     public boolean getValue() {
/* 118 */       return this.value;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class BuilderArrayEncodedValue extends BaseArrayEncodedValue
/*     */     implements BuilderEncodedValues.BuilderEncodedValue
/*     */   {
/*     */     final List<? extends BuilderEncodedValues.BuilderEncodedValue> elements;
/*     */ 
/*     */     BuilderArrayEncodedValue(List<? extends BuilderEncodedValues.BuilderEncodedValue> elements)
/*     */     {
/*  71 */       this.elements = elements;
/*     */     }
/*     */ 
/*     */     public List<? extends EncodedValue> getValue() {
/*  75 */       return this.elements;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class BuilderAnnotationEncodedValue extends BaseAnnotationEncodedValue
/*     */     implements BuilderEncodedValues.BuilderEncodedValue
/*     */   {
/*     */     final BuilderTypeReference typeReference;
/*     */     final Set<? extends BuilderAnnotationElement> elements;
/*     */ 
/*     */     BuilderAnnotationEncodedValue(BuilderTypeReference typeReference, Set<? extends BuilderAnnotationElement> elements)
/*     */     {
/*  54 */       this.typeReference = typeReference;
/*  55 */       this.elements = elements;
/*     */     }
/*     */ 
/*     */     public String getType() {
/*  59 */       return this.typeReference.getType();
/*     */     }
/*     */ 
/*     */     public Set<? extends BuilderAnnotationElement> getElements() {
/*  63 */       return this.elements;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static abstract interface BuilderEncodedValue extends EncodedValue
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderEncodedValues
 * JD-Core Version:    0.6.0
 */