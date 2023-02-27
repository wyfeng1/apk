/*     */ package org.jf.dexlib2.immutable;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.ImmutableSortedSet;
/*     */ import com.google.common.collect.Ordering;
/*     */ import java.util.Set;
/*     */ import org.jf.dexlib2.base.reference.BaseMethodReference;
/*     */ import org.jf.dexlib2.iface.Annotation;
/*     */ import org.jf.dexlib2.iface.Method;
/*     */ import org.jf.dexlib2.iface.MethodImplementation;
/*     */ import org.jf.dexlib2.iface.MethodParameter;
/*     */ import org.jf.util.ImmutableConverter;
/*     */ import org.jf.util.ImmutableUtils;
/*     */ 
/*     */ public class ImmutableMethod extends BaseMethodReference
/*     */   implements Method
/*     */ {
/*     */   protected final String definingClass;
/*     */   protected final String name;
/*     */   protected final ImmutableList<? extends ImmutableMethodParameter> parameters;
/*     */   protected final String returnType;
/*     */   protected final int accessFlags;
/*     */   protected final ImmutableSet<? extends ImmutableAnnotation> annotations;
/*     */   protected final ImmutableMethodImplementation methodImplementation;
/* 119 */   private static final ImmutableConverter<ImmutableMethod, Method> CONVERTER = new ImmutableConverter()
/*     */   {
/*     */     protected boolean isImmutable(Method item)
/*     */     {
/* 123 */       return item instanceof ImmutableMethod;
/*     */     }
/*     */ 
/*     */     protected ImmutableMethod makeImmutable(Method item)
/*     */     {
/* 129 */       return ImmutableMethod.of(item);
/*     */     }
/* 119 */   };
/*     */ 
/*     */   public ImmutableMethod(String definingClass, String name, Iterable<? extends MethodParameter> parameters, String returnType, int accessFlags, Set<? extends Annotation> annotations, MethodImplementation methodImplementation)
/*     */   {
/*  66 */     this.definingClass = definingClass;
/*  67 */     this.name = name;
/*  68 */     this.parameters = ImmutableMethodParameter.immutableListOf(parameters);
/*  69 */     this.returnType = returnType;
/*  70 */     this.accessFlags = accessFlags;
/*  71 */     this.annotations = ImmutableAnnotation.immutableSetOf(annotations);
/*  72 */     this.methodImplementation = ImmutableMethodImplementation.of(methodImplementation);
/*     */   }
/*     */ 
/*     */   public ImmutableMethod(String definingClass, String name, ImmutableList<? extends ImmutableMethodParameter> parameters, String returnType, int accessFlags, ImmutableSet<? extends ImmutableAnnotation> annotations, ImmutableMethodImplementation methodImplementation)
/*     */   {
/*  82 */     this.definingClass = definingClass;
/*  83 */     this.name = name;
/*  84 */     this.parameters = ImmutableUtils.nullToEmptyList(parameters);
/*  85 */     this.returnType = returnType;
/*  86 */     this.accessFlags = accessFlags;
/*  87 */     this.annotations = ImmutableUtils.nullToEmptySet(annotations);
/*  88 */     this.methodImplementation = methodImplementation;
/*     */   }
/*     */ 
/*     */   public static ImmutableMethod of(Method method) {
/*  92 */     if ((method instanceof ImmutableMethod)) {
/*  93 */       return (ImmutableMethod)method;
/*     */     }
/*  95 */     return new ImmutableMethod(method.getDefiningClass(), method.getName(), method.getParameters(), method.getReturnType(), method.getAccessFlags(), method.getAnnotations(), method.getImplementation());
/*     */   }
/*     */ 
/*     */   public String getDefiningClass()
/*     */   {
/* 105 */     return this.definingClass; } 
/* 106 */   public String getName() { return this.name; } 
/* 107 */   public ImmutableList<? extends CharSequence> getParameterTypes() { return this.parameters; } 
/* 108 */   public ImmutableList<? extends ImmutableMethodParameter> getParameters() { return this.parameters; } 
/* 109 */   public String getReturnType() { return this.returnType; } 
/* 110 */   public int getAccessFlags() { return this.accessFlags; } 
/* 111 */   public ImmutableSet<? extends ImmutableAnnotation> getAnnotations() { return this.annotations; } 
/* 112 */   public ImmutableMethodImplementation getImplementation() { return this.methodImplementation; }
/*     */ 
/*     */   public static ImmutableSortedSet<ImmutableMethod> immutableSetOf(Iterable<? extends Method> list)
/*     */   {
/* 116 */     return CONVERTER.toSortedSet(Ordering.natural(), list);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.ImmutableMethod
 * JD-Core Version:    0.6.0
 */