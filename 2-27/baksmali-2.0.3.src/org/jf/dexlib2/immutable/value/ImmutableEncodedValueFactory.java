/*     */ package org.jf.dexlib2.immutable.value;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import com.google.common.collect.ImmutableList;
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
/*     */ import org.jf.util.ImmutableConverter;
/*     */ 
/*     */ public class ImmutableEncodedValueFactory
/*     */ {
/* 127 */   private static final ImmutableConverter<ImmutableEncodedValue, EncodedValue> CONVERTER = new ImmutableConverter()
/*     */   {
/*     */     protected boolean isImmutable(EncodedValue item)
/*     */     {
/* 131 */       return item instanceof ImmutableEncodedValue;
/*     */     }
/*     */ 
/*     */     protected ImmutableEncodedValue makeImmutable(EncodedValue item)
/*     */     {
/* 137 */       return ImmutableEncodedValueFactory.of(item);
/*     */     }
/* 127 */   };
/*     */ 
/*     */   public static ImmutableEncodedValue of(EncodedValue encodedValue)
/*     */   {
/*  47 */     switch (encodedValue.getValueType()) {
/*     */     case 0:
/*  49 */       return ImmutableByteEncodedValue.of((ByteEncodedValue)encodedValue);
/*     */     case 2:
/*  51 */       return ImmutableShortEncodedValue.of((ShortEncodedValue)encodedValue);
/*     */     case 3:
/*  53 */       return ImmutableCharEncodedValue.of((CharEncodedValue)encodedValue);
/*     */     case 4:
/*  55 */       return ImmutableIntEncodedValue.of((IntEncodedValue)encodedValue);
/*     */     case 6:
/*  57 */       return ImmutableLongEncodedValue.of((LongEncodedValue)encodedValue);
/*     */     case 16:
/*  59 */       return ImmutableFloatEncodedValue.of((FloatEncodedValue)encodedValue);
/*     */     case 17:
/*  61 */       return ImmutableDoubleEncodedValue.of((DoubleEncodedValue)encodedValue);
/*     */     case 23:
/*  63 */       return ImmutableStringEncodedValue.of((StringEncodedValue)encodedValue);
/*     */     case 24:
/*  65 */       return ImmutableTypeEncodedValue.of((TypeEncodedValue)encodedValue);
/*     */     case 25:
/*  67 */       return ImmutableFieldEncodedValue.of((FieldEncodedValue)encodedValue);
/*     */     case 26:
/*  69 */       return ImmutableMethodEncodedValue.of((MethodEncodedValue)encodedValue);
/*     */     case 27:
/*  71 */       return ImmutableEnumEncodedValue.of((EnumEncodedValue)encodedValue);
/*     */     case 28:
/*  73 */       return ImmutableArrayEncodedValue.of((ArrayEncodedValue)encodedValue);
/*     */     case 29:
/*  75 */       return ImmutableAnnotationEncodedValue.of((AnnotationEncodedValue)encodedValue);
/*     */     case 30:
/*  77 */       return ImmutableNullEncodedValue.INSTANCE;
/*     */     case 31:
/*  79 */       return ImmutableBooleanEncodedValue.of((BooleanEncodedValue)encodedValue);
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
/*  81 */     case 22: } Preconditions.checkArgument(false);
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */   public static ImmutableEncodedValue ofNullable(EncodedValue encodedValue)
/*     */   {
/* 115 */     if (encodedValue == null) {
/* 116 */       return null;
/*     */     }
/* 118 */     return of(encodedValue);
/*     */   }
/*     */ 
/*     */   public static ImmutableList<ImmutableEncodedValue> immutableListOf(Iterable<? extends EncodedValue> list)
/*     */   {
/* 124 */     return CONVERTER.toList(list);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.value.ImmutableEncodedValueFactory
 * JD-Core Version:    0.6.0
 */