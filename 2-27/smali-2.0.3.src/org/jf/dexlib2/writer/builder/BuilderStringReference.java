/*    */ package org.jf.dexlib2.writer.builder;
/*    */ 
/*    */ import org.jf.dexlib2.base.reference.BaseStringReference;
/*    */ 
/*    */ public class BuilderStringReference extends BaseStringReference
/*    */   implements BuilderReference
/*    */ {
/*    */   final String string;
/* 41 */   int index = -1;
/*    */ 
/*    */   BuilderStringReference(String string) {
/* 44 */     this.string = string;
/*    */   }
/*    */ 
/*    */   public String getString() {
/* 48 */     return this.string;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderStringReference
 * JD-Core Version:    0.6.0
 */