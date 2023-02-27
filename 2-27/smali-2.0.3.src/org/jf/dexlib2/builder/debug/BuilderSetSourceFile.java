/*    */ package org.jf.dexlib2.builder.debug;
/*    */ 
/*    */ import org.jf.dexlib2.builder.BuilderDebugItem;
/*    */ import org.jf.dexlib2.iface.debug.SetSourceFile;
/*    */ import org.jf.dexlib2.iface.reference.StringReference;
/*    */ 
/*    */ public class BuilderSetSourceFile extends BuilderDebugItem
/*    */   implements SetSourceFile
/*    */ {
/*    */   private final StringReference sourceFile;
/*    */ 
/*    */   public BuilderSetSourceFile(StringReference sourceFile)
/*    */   {
/* 46 */     this.sourceFile = sourceFile;
/*    */   }
/*    */   public int getDebugItemType() {
/* 49 */     return 9;
/*    */   }
/*    */ 
/*    */   public StringReference getSourceFileReference()
/*    */   {
/* 56 */     return this.sourceFile;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.debug.BuilderSetSourceFile
 * JD-Core Version:    0.6.0
 */