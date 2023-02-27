/*    */ package org.jf.dexlib2.analysis.reflection;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Set;
/*    */ import org.jf.dexlib2.analysis.reflection.util.ReflectionUtils;
/*    */ import org.jf.dexlib2.base.reference.BaseFieldReference;
/*    */ import org.jf.dexlib2.iface.Annotation;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ 
/*    */ public class ReflectionField extends BaseFieldReference
/*    */   implements org.jf.dexlib2.iface.Field
/*    */ {
/*    */   private final java.lang.reflect.Field field;
/*    */ 
/*    */   public ReflectionField(java.lang.reflect.Field field)
/*    */   {
/* 49 */     this.field = field;
/*    */   }
/*    */ 
/*    */   public int getAccessFlags() {
/* 53 */     return this.field.getModifiers();
/*    */   }
/*    */ 
/*    */   public EncodedValue getInitialValue() {
/* 57 */     return null;
/*    */   }
/*    */ 
/*    */   public Set<? extends Annotation> getAnnotations() {
/* 61 */     return ImmutableSet.of();
/*    */   }
/*    */ 
/*    */   public String getDefiningClass() {
/* 65 */     return ReflectionUtils.javaToDexName(this.field.getDeclaringClass().getName());
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 69 */     return this.field.getName();
/*    */   }
/*    */ 
/*    */   public String getType() {
/* 73 */     return ReflectionUtils.javaToDexName(this.field.getType().getName());
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.analysis.reflection.ReflectionField
 * JD-Core Version:    0.6.0
 */