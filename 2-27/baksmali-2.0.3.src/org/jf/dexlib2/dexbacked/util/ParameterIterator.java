/*    */ package org.jf.dexlib2.dexbacked.util;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import org.jf.dexlib2.base.BaseMethodParameter;
/*    */ import org.jf.dexlib2.iface.Annotation;
/*    */ import org.jf.dexlib2.iface.MethodParameter;
/*    */ 
/*    */ public class ParameterIterator
/*    */   implements Iterator<MethodParameter>
/*    */ {
/*    */   private final Iterator<? extends CharSequence> parameterTypes;
/*    */   private final Iterator<? extends Set<? extends Annotation>> parameterAnnotations;
/*    */   private final Iterator<String> parameterNames;
/*    */ 
/*    */   public ParameterIterator(List<? extends CharSequence> parameterTypes, List<? extends Set<? extends Annotation>> parameterAnnotations, Iterator<String> parameterNames)
/*    */   {
/* 53 */     this.parameterTypes = parameterTypes.iterator();
/* 54 */     this.parameterAnnotations = parameterAnnotations.iterator();
/* 55 */     this.parameterNames = parameterNames;
/*    */   }
/*    */ 
/*    */   public boolean hasNext() {
/* 59 */     return this.parameterTypes.hasNext();
/*    */   }
/*    */ 
/*    */   public MethodParameter next() {
/* 63 */     String type = ((CharSequence)this.parameterTypes.next()).toString();
/*    */     Set annotations;
/*    */     Set annotations;
/* 67 */     if (this.parameterAnnotations.hasNext())
/* 68 */       annotations = (Set)this.parameterAnnotations.next();
/*    */     else
/* 70 */       annotations = ImmutableSet.of();
/*    */     String name;
/*    */     String name;
/* 73 */     if (this.parameterNames.hasNext())
/* 74 */       name = (String)this.parameterNames.next();
/*    */     else {
/* 76 */       name = null;
/*    */     }
/*    */ 
/* 79 */     return new BaseMethodParameter(annotations, name, type) {
/* 80 */       public Set<? extends Annotation> getAnnotations() { return this.val$annotations; } 
/* 81 */       public String getName() { return this.val$name; } 
/* 82 */       public String getType() { return this.val$type; } } ;
/*    */   }
/*    */ 
/*    */   public void remove() {
/* 87 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.util.ParameterIterator
 * JD-Core Version:    0.6.0
 */