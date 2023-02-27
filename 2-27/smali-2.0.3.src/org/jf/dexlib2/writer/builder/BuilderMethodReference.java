/*    */ package org.jf.dexlib2.writer.builder;
/*    */ 
/*    */ import org.jf.dexlib2.base.reference.BaseMethodReference;
/*    */ 
/*    */ public class BuilderMethodReference extends BaseMethodReference
/*    */   implements BuilderReference
/*    */ {
/*    */   final BuilderTypeReference definingClass;
/*    */   final BuilderStringReference name;
/*    */   final BuilderProtoReference proto;
/* 43 */   int index = -1;
/*    */ 
/*    */   BuilderMethodReference(BuilderTypeReference definingClass, BuilderStringReference name, BuilderProtoReference proto)
/*    */   {
/* 48 */     this.definingClass = definingClass;
/* 49 */     this.name = name;
/* 50 */     this.proto = proto;
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
/*    */   public BuilderTypeList getParameterTypes() {
/* 62 */     return this.proto.parameterTypes;
/*    */   }
/*    */ 
/*    */   public String getReturnType() {
/* 66 */     return this.proto.returnType.getType();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderMethodReference
 * JD-Core Version:    0.6.0
 */