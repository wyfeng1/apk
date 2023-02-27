/*     */ package org.jf.dexlib2.analysis.reflection;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.AbstractList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.jf.dexlib2.analysis.reflection.util.ReflectionUtils;
/*     */ import org.jf.dexlib2.base.BaseMethodParameter;
/*     */ import org.jf.dexlib2.base.reference.BaseMethodReference;
/*     */ import org.jf.dexlib2.iface.Annotation;
/*     */ import org.jf.dexlib2.iface.Method;
/*     */ import org.jf.dexlib2.iface.MethodImplementation;
/*     */ import org.jf.dexlib2.iface.MethodParameter;
/*     */ 
/*     */ public class ReflectionConstructor extends BaseMethodReference
/*     */   implements Method
/*     */ {
/*     */   private final Constructor constructor;
/*     */ 
/*     */   public ReflectionConstructor(Constructor constructor)
/*     */   {
/*  54 */     this.constructor = constructor;
/*     */   }
/*     */ 
/*     */   public List<? extends MethodParameter> getParameters() {
/*  58 */     Constructor method = this.constructor;
/*  59 */     return new AbstractList(method) {
/*  60 */       private final Class[] parameters = this.val$method.getParameterTypes();
/*     */ 
/*     */       public MethodParameter get(int index) {
/*  63 */         return new BaseMethodParameter(index) {
/*     */           public Set<? extends Annotation> getAnnotations() {
/*  65 */             return ImmutableSet.of();
/*     */           }
/*     */ 
/*     */           public String getName() {
/*  69 */             return null;
/*     */           }
/*     */ 
/*     */           public String getType() {
/*  73 */             return ReflectionUtils.javaToDexName(ReflectionConstructor.1.this.parameters[this.val$index].getName());
/*     */           } } ;
/*     */       }
/*     */ 
/*     */       public int size() {
/*  79 */         return this.parameters.length;
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   public int getAccessFlags() {
/*  85 */     return this.constructor.getModifiers();
/*     */   }
/*     */ 
/*     */   public Set<? extends Annotation> getAnnotations() {
/*  89 */     return ImmutableSet.of();
/*     */   }
/*     */ 
/*     */   public MethodImplementation getImplementation() {
/*  93 */     return null;
/*     */   }
/*     */ 
/*     */   public String getDefiningClass() {
/*  97 */     return ReflectionUtils.javaToDexName(this.constructor.getDeclaringClass().getName());
/*     */   }
/*     */ 
/*     */   public String getName() {
/* 101 */     return this.constructor.getName();
/*     */   }
/*     */ 
/*     */   public List<String> getParameterTypes() {
/* 105 */     return new AbstractList() {
/* 106 */       private final List<? extends MethodParameter> parameters = ReflectionConstructor.this.getParameters();
/*     */ 
/*     */       public String get(int index) {
/* 109 */         return ((MethodParameter)this.parameters.get(index)).getType();
/*     */       }
/*     */ 
/*     */       public int size() {
/* 113 */         return this.parameters.size();
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   public String getReturnType() {
/* 119 */     return "V";
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.analysis.reflection.ReflectionConstructor
 * JD-Core Version:    0.6.0
 */