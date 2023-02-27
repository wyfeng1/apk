/*    */ package org.jf.dexlib2.writer.builder;
/*    */ 
/*    */ import org.jf.dexlib2.base.reference.BaseFieldReference;
/*    */ import org.jf.dexlib2.iface.Field;
/*    */ 
/*    */ public class BuilderField extends BaseFieldReference
/*    */   implements Field
/*    */ {
/*    */   final BuilderFieldReference fieldReference;
/*    */   final int accessFlags;
/*    */   final BuilderEncodedValues.BuilderEncodedValue initialValue;
/*    */   final BuilderAnnotationSet annotations;
/*    */ 
/*    */   BuilderField(BuilderFieldReference fieldReference, int accessFlags, BuilderEncodedValues.BuilderEncodedValue initialValue, BuilderAnnotationSet annotations)
/*    */   {
/* 51 */     this.fieldReference = fieldReference;
/* 52 */     this.accessFlags = accessFlags;
/* 53 */     this.initialValue = initialValue;
/* 54 */     this.annotations = annotations;
/*    */   }
/*    */ 
/*    */   public int getAccessFlags() {
/* 58 */     return this.accessFlags;
/*    */   }
/*    */ 
/*    */   public BuilderEncodedValues.BuilderEncodedValue getInitialValue() {
/* 62 */     return this.initialValue;
/*    */   }
/*    */ 
/*    */   public String getDefiningClass()
/*    */   {
/* 70 */     return this.fieldReference.definingClass.getType();
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 74 */     return this.fieldReference.name.getString();
/*    */   }
/*    */ 
/*    */   public String getType() {
/* 78 */     return this.fieldReference.fieldType.getType();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderField
 * JD-Core Version:    0.6.0
 */