/*     */ package org.jf.dexlib2.immutable;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.util.Set;
/*     */ import org.jf.dexlib2.base.BaseMethodParameter;
/*     */ import org.jf.dexlib2.iface.Annotation;
/*     */ import org.jf.dexlib2.iface.MethodParameter;
/*     */ import org.jf.util.ImmutableConverter;
/*     */ import org.jf.util.ImmutableUtils;
/*     */ 
/*     */ public class ImmutableMethodParameter extends BaseMethodParameter
/*     */ {
/*     */   protected final String type;
/*     */   protected final ImmutableSet<? extends ImmutableAnnotation> annotations;
/*     */   protected final String name;
/*  90 */   private static final ImmutableConverter<ImmutableMethodParameter, MethodParameter> CONVERTER = new ImmutableConverter()
/*     */   {
/*     */     protected boolean isImmutable(MethodParameter item)
/*     */     {
/*  94 */       return item instanceof ImmutableMethodParameter;
/*     */     }
/*     */ 
/*     */     protected ImmutableMethodParameter makeImmutable(MethodParameter item)
/*     */     {
/* 100 */       return ImmutableMethodParameter.of(item);
/*     */     }
/*  90 */   };
/*     */ 
/*     */   public ImmutableMethodParameter(String type, Set<? extends Annotation> annotations, String name)
/*     */   {
/*  54 */     this.type = type;
/*  55 */     this.annotations = ImmutableAnnotation.immutableSetOf(annotations);
/*  56 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public ImmutableMethodParameter(String type, ImmutableSet<? extends ImmutableAnnotation> annotations, String name)
/*     */   {
/*  62 */     this.type = type;
/*  63 */     this.annotations = ImmutableUtils.nullToEmptySet(annotations);
/*  64 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public static ImmutableMethodParameter of(MethodParameter methodParameter) {
/*  68 */     if ((methodParameter instanceof ImmutableMethodParameter)) {
/*  69 */       return (ImmutableMethodParameter)methodParameter;
/*     */     }
/*  71 */     return new ImmutableMethodParameter(methodParameter.getType(), methodParameter.getAnnotations(), methodParameter.getName());
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/*  77 */     return this.type; } 
/*  78 */   public Set<? extends Annotation> getAnnotations() { return this.annotations; } 
/*  79 */   public String getName() { return this.name; }
/*     */ 
/*     */   public String getSignature() {
/*  82 */     return null;
/*     */   }
/*     */ 
/*     */   public static ImmutableList<ImmutableMethodParameter> immutableListOf(Iterable<? extends MethodParameter> list)
/*     */   {
/*  87 */     return CONVERTER.toList(list);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.ImmutableMethodParameter
 * JD-Core Version:    0.6.0
 */