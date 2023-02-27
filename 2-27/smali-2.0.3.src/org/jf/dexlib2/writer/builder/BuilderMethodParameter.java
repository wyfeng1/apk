/*    */ package org.jf.dexlib2.writer.builder;
/*    */ 
/*    */ import org.jf.dexlib2.base.BaseMethodParameter;
/*    */ 
/*    */ public class BuilderMethodParameter extends BaseMethodParameter
/*    */ {
/*    */   final BuilderTypeReference type;
/*    */   final BuilderStringReference name;
/*    */   final BuilderAnnotationSet annotations;
/*    */ 
/*    */   public BuilderMethodParameter(BuilderTypeReference type, BuilderStringReference name, BuilderAnnotationSet annotations)
/*    */   {
/* 47 */     this.type = type;
/* 48 */     this.name = name;
/* 49 */     this.annotations = annotations;
/*    */   }
/*    */ 
/*    */   public String getType() {
/* 53 */     return this.type.getType();
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 57 */     return this.name == null ? null : this.name.getString();
/*    */   }
/*    */ 
/*    */   public BuilderAnnotationSet getAnnotations() {
/* 61 */     return this.annotations;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderMethodParameter
 * JD-Core Version:    0.6.0
 */