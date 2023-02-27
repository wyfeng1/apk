/*    */ package org.jf.dexlib2.writer.builder;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.jf.dexlib2.base.reference.BaseMethodReference;
/*    */ import org.jf.dexlib2.iface.Method;
/*    */ import org.jf.dexlib2.iface.MethodImplementation;
/*    */ 
/*    */ public class BuilderMethod extends BaseMethodReference
/*    */   implements Method
/*    */ {
/*    */   final BuilderMethodReference methodReference;
/*    */   final List<? extends BuilderMethodParameter> parameters;
/*    */   final int accessFlags;
/*    */   final BuilderAnnotationSet annotations;
/*    */   final MethodImplementation methodImplementation;
/* 50 */   int annotationSetRefListOffset = 0;
/* 51 */   int codeItemOffset = 0;
/*    */ 
/*    */   BuilderMethod(BuilderMethodReference methodReference, List<? extends BuilderMethodParameter> parameters, int accessFlags, BuilderAnnotationSet annotations, MethodImplementation methodImplementation)
/*    */   {
/* 58 */     this.methodReference = methodReference;
/* 59 */     this.parameters = parameters;
/* 60 */     this.accessFlags = accessFlags;
/* 61 */     this.annotations = annotations;
/* 62 */     this.methodImplementation = methodImplementation;
/*    */   }
/*    */   public String getDefiningClass() {
/* 65 */     return this.methodReference.definingClass.getType(); } 
/* 66 */   public String getName() { return this.methodReference.name.getString(); } 
/* 67 */   public BuilderTypeList getParameterTypes() { return this.methodReference.proto.parameterTypes; } 
/* 68 */   public String getReturnType() { return this.methodReference.proto.returnType.getType(); } 
/* 69 */   public List<? extends BuilderMethodParameter> getParameters() { return this.parameters; } 
/* 70 */   public int getAccessFlags() { return this.accessFlags; } 
/*    */   public MethodImplementation getImplementation() {
/* 72 */     return this.methodImplementation;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderMethod
 * JD-Core Version:    0.6.0
 */