/*    */ package org.jf.dexlib2.writer.builder;
/*    */ 
/*    */ import org.jf.dexlib2.base.BaseAnnotationElement;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ 
/*    */ public class BuilderAnnotationElement extends BaseAnnotationElement
/*    */ {
/*    */   final BuilderStringReference name;
/*    */   final BuilderEncodedValues.BuilderEncodedValue value;
/*    */ 
/*    */   public BuilderAnnotationElement(BuilderStringReference name, BuilderEncodedValues.BuilderEncodedValue value)
/*    */   {
/* 45 */     this.name = name;
/* 46 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 50 */     return this.name.getString();
/*    */   }
/*    */ 
/*    */   public EncodedValue getValue() {
/* 54 */     return this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderAnnotationElement
 * JD-Core Version:    0.6.0
 */