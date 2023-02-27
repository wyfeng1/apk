/*    */ package org.jf.dexlib2.writer.builder;
/*    */ 
/*    */ import org.jf.dexlib2.base.reference.BaseFieldReference;
/*    */ 
/*    */ public class BuilderFieldReference extends BaseFieldReference
/*    */   implements BuilderReference
/*    */ {
/*    */   final BuilderTypeReference definingClass;
/*    */   final BuilderStringReference name;
/*    */   final BuilderTypeReference fieldType;
/* 43 */   int index = -1;
/*    */ 
/*    */   BuilderFieldReference(BuilderTypeReference definingClass, BuilderStringReference name, BuilderTypeReference fieldType)
/*    */   {
/* 48 */     this.definingClass = definingClass;
/* 49 */     this.name = name;
/* 50 */     this.fieldType = fieldType;
/*    */   }
/*    */ 
/*    */   public String getDefiningClass() {
/* 54 */     return this.definingClass.getType();
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 58 */     return this.name.getString();
/*    */   }
/*    */ 
/*    */   public String getType() {
/* 62 */     return this.fieldType.getType();
/*    */   }
/*    */ 
/*    */   public int getIndex() {
/* 66 */     return this.index;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderFieldReference
 * JD-Core Version:    0.6.0
 */