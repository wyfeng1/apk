/*     */ package org.jf.dexlib2.immutable;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.ImmutableSortedSet;
/*     */ import com.google.common.collect.Ordering;
/*     */ import java.util.Collection;
/*     */ import org.jf.dexlib2.base.reference.BaseFieldReference;
/*     */ import org.jf.dexlib2.iface.Annotation;
/*     */ import org.jf.dexlib2.iface.Field;
/*     */ import org.jf.dexlib2.iface.value.EncodedValue;
/*     */ import org.jf.dexlib2.immutable.value.ImmutableEncodedValue;
/*     */ import org.jf.dexlib2.immutable.value.ImmutableEncodedValueFactory;
/*     */ import org.jf.util.ImmutableConverter;
/*     */ 
/*     */ public class ImmutableField extends BaseFieldReference
/*     */   implements Field
/*     */ {
/*     */   protected final String definingClass;
/*     */   protected final String name;
/*     */   protected final String type;
/*     */   protected final int accessFlags;
/*     */   protected final ImmutableEncodedValue initialValue;
/*     */   protected final ImmutableSet<? extends ImmutableAnnotation> annotations;
/* 111 */   private static final ImmutableConverter<ImmutableField, Field> CONVERTER = new ImmutableConverter()
/*     */   {
/*     */     protected boolean isImmutable(Field item)
/*     */     {
/* 115 */       return item instanceof ImmutableField;
/*     */     }
/*     */ 
/*     */     protected ImmutableField makeImmutable(Field item)
/*     */     {
/* 121 */       return ImmutableField.of(item);
/*     */     }
/* 111 */   };
/*     */ 
/*     */   public ImmutableField(String definingClass, String name, String type, int accessFlags, EncodedValue initialValue, Collection<? extends Annotation> annotations)
/*     */   {
/*  64 */     this.definingClass = definingClass;
/*  65 */     this.name = name;
/*  66 */     this.type = type;
/*  67 */     this.accessFlags = accessFlags;
/*  68 */     this.initialValue = ImmutableEncodedValueFactory.ofNullable(initialValue);
/*  69 */     this.annotations = ImmutableAnnotation.immutableSetOf(annotations);
/*     */   }
/*     */ 
/*     */   public static ImmutableField of(Field field)
/*     */   {
/*  87 */     if ((field instanceof ImmutableField)) {
/*  88 */       return (ImmutableField)field;
/*     */     }
/*  90 */     return new ImmutableField(field.getDefiningClass(), field.getName(), field.getType(), field.getAccessFlags(), field.getInitialValue(), field.getAnnotations());
/*     */   }
/*     */ 
/*     */   public String getDefiningClass()
/*     */   {
/*  99 */     return this.definingClass; } 
/* 100 */   public String getName() { return this.name; } 
/* 101 */   public String getType() { return this.type; } 
/* 102 */   public int getAccessFlags() { return this.accessFlags; } 
/* 103 */   public EncodedValue getInitialValue() { return this.initialValue; } 
/* 104 */   public ImmutableSet<? extends ImmutableAnnotation> getAnnotations() { return this.annotations; }
/*     */ 
/*     */   public static ImmutableSortedSet<ImmutableField> immutableSetOf(Iterable<? extends Field> list)
/*     */   {
/* 108 */     return CONVERTER.toSortedSet(Ordering.natural(), list);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.ImmutableField
 * JD-Core Version:    0.6.0
 */