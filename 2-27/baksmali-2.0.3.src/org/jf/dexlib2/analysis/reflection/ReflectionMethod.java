/*     */ package org.jf.dexlib2.analysis.reflection;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.util.AbstractList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.jf.dexlib2.analysis.reflection.util.ReflectionUtils;
/*     */ import org.jf.dexlib2.base.BaseMethodParameter;
/*     */ import org.jf.dexlib2.base.reference.BaseMethodReference;
/*     */ import org.jf.dexlib2.iface.Annotation;
/*     */ import org.jf.dexlib2.iface.MethodImplementation;
/*     */ import org.jf.dexlib2.iface.MethodParameter;
/*     */ 
/*     */ public class ReflectionMethod extends BaseMethodReference
/*     */   implements org.jf.dexlib2.iface.Method
/*     */ {
/*     */   private final java.lang.reflect.Method method;
/*     */ 
/*     */   public ReflectionMethod(java.lang.reflect.Method method)
/*     */   {
/*  53 */     this.method = method;
/*     */   }
/*     */ 
/*     */   public List<? extends MethodParameter> getParameters() {
/*  57 */     java.lang.reflect.Method method = this.method;
/*  58 */     return new AbstractList(method) {
/*  59 */       private final Class[] parameters = this.val$method.getParameterTypes();
/*     */ 
/*     */       public MethodParameter get(int index) {
/*  62 */         return new BaseMethodParameter(index) {
/*     */           public Set<? extends Annotation> getAnnotations() {
/*  64 */             return ImmutableSet.of();
/*     */           }
/*     */ 
/*     */           public String getName() {
/*  68 */             return null;
/*     */           }
/*     */ 
/*     */           public String getType() {
/*  72 */             return ReflectionUtils.javaToDexName(ReflectionMethod.1.this.parameters[this.val$index].getName());
/*     */           } } ;
/*     */       }
/*     */ 
/*     */       public int size() {
/*  78 */         return this.parameters.length;
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   public int getAccessFlags() {
/*  84 */     return this.method.getModifiers();
/*     */   }
/*     */ 
/*     */   public Set<? extends Annotation> getAnnotations() {
/*  88 */     return ImmutableSet.of();
/*     */   }
/*     */ 
/*     */   public MethodImplementation getImplementation() {
/*  92 */     return null;
/*     */   }
/*     */ 
/*     */   public String getDefiningClass() {
/*  96 */     return ReflectionUtils.javaToDexName(this.method.getDeclaringClass().getName());
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 100 */     return this.method.getName();
/*     */   }
/*     */ 
/*     */   public List<String> getParameterTypes() {
/* 104 */     return new AbstractList() {
/* 105 */       private final List<? extends MethodParameter> parameters = ReflectionMethod.this.getParameters();
/*     */ 
/*     */       public String get(int index) {
/* 108 */         return ((MethodParameter)this.parameters.get(index)).getType();
/*     */       }
/*     */ 
/*     */       public int size() {
/* 112 */         return this.parameters.size();
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   public String getReturnType() {
/* 118 */     return ReflectionUtils.javaToDexName(this.method.getReturnType().getName());
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.analysis.reflection.ReflectionMethod
 * JD-Core Version:    0.6.0
 */