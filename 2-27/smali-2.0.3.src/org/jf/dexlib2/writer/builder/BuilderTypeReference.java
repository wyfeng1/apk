/*    */ package org.jf.dexlib2.writer.builder;
/*    */ 
/*    */ import org.jf.dexlib2.base.reference.BaseTypeReference;
/*    */ 
/*    */ public class BuilderTypeReference extends BaseTypeReference
/*    */   implements BuilderReference
/*    */ {
/*    */   final BuilderStringReference stringReference;
/* 41 */   int index = -1;
/*    */ 
/*    */   BuilderTypeReference(BuilderStringReference stringReference) {
/* 44 */     this.stringReference = stringReference;
/*    */   }
/*    */ 
/*    */   public String getType() {
/* 48 */     return this.stringReference.getString();
/*    */   }
/*    */ 
/*    */   public int getIndex() {
/* 52 */     return this.index;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderTypeReference
 * JD-Core Version:    0.6.0
 */