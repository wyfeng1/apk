/*    */ package org.jf.dexlib2.immutable.reference;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import org.jf.dexlib2.base.reference.BaseMethodReference;
/*    */ import org.jf.dexlib2.iface.reference.MethodReference;
/*    */ import org.jf.dexlib2.immutable.util.CharSequenceConverter;
/*    */ 
/*    */ public class ImmutableMethodReference extends BaseMethodReference
/*    */   implements ImmutableReference
/*    */ {
/*    */   protected final String definingClass;
/*    */   protected final String name;
/*    */   protected final ImmutableList<String> parameters;
/*    */   protected final String returnType;
/*    */ 
/*    */   public ImmutableMethodReference(String definingClass, String name, Iterable<? extends CharSequence> parameters, String returnType)
/*    */   {
/* 53 */     this.definingClass = definingClass;
/* 54 */     this.name = name;
/* 55 */     this.parameters = CharSequenceConverter.immutableStringList(parameters);
/* 56 */     this.returnType = returnType;
/*    */   }
/*    */ 
/*    */   public static ImmutableMethodReference of(MethodReference methodReference)
/*    */   {
/* 71 */     if ((methodReference instanceof ImmutableMethodReference)) {
/* 72 */       return (ImmutableMethodReference)methodReference;
/*    */     }
/* 74 */     return new ImmutableMethodReference(methodReference.getDefiningClass(), methodReference.getName(), methodReference.getParameterTypes(), methodReference.getReturnType());
/*    */   }
/*    */ 
/*    */   public String getDefiningClass()
/*    */   {
/* 81 */     return this.definingClass; } 
/* 82 */   public String getName() { return this.name; } 
/* 83 */   public ImmutableList<String> getParameterTypes() { return this.parameters; } 
/* 84 */   public String getReturnType() { return this.returnType;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.reference.ImmutableMethodReference
 * JD-Core Version:    0.6.0
 */