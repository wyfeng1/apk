/*    */ package org.jf.dexlib2.writer.builder;
/*    */ 
/*    */ import java.util.Set;
/*    */ import org.jf.dexlib2.base.BaseAnnotation;
/*    */ 
/*    */ class BuilderAnnotation extends BaseAnnotation
/*    */ {
/*    */   int visibility;
/*    */   final BuilderTypeReference type;
/*    */   final Set<? extends BuilderAnnotationElement> elements;
/* 44 */   int offset = 0;
/*    */ 
/*    */   public BuilderAnnotation(int visibility, BuilderTypeReference type, Set<? extends BuilderAnnotationElement> elements)
/*    */   {
/* 48 */     this.visibility = visibility;
/* 49 */     this.type = type;
/* 50 */     this.elements = elements;
/*    */   }
/*    */ 
/*    */   public int getVisibility() {
/* 54 */     return this.visibility;
/*    */   }
/*    */ 
/*    */   public String getType() {
/* 58 */     return this.type.getType();
/*    */   }
/*    */ 
/*    */   public Set<? extends BuilderAnnotationElement> getElements() {
/* 62 */     return this.elements;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderAnnotation
 * JD-Core Version:    0.6.0
 */