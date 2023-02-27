/*    */ package org.jf.dexlib2.immutable.debug;
/*    */ 
/*    */ import org.jf.dexlib2.iface.debug.SetSourceFile;
/*    */ 
/*    */ public class ImmutableSetSourceFile extends ImmutableDebugItem
/*    */   implements SetSourceFile
/*    */ {
/*    */   protected final String sourceFile;
/*    */ 
/*    */   public ImmutableSetSourceFile(int codeAddress, String sourceFile)
/*    */   {
/* 47 */     super(codeAddress);
/* 48 */     this.sourceFile = sourceFile;
/*    */   }
/*    */ 
/*    */   public static ImmutableSetSourceFile of(SetSourceFile setSourceFile)
/*    */   {
/* 53 */     if ((setSourceFile instanceof ImmutableSetSourceFile)) {
/* 54 */       return (ImmutableSetSourceFile)setSourceFile;
/*    */     }
/* 56 */     return new ImmutableSetSourceFile(setSourceFile.getCodeAddress(), setSourceFile.getSourceFile());
/*    */   }
/*    */ 
/*    */   public String getSourceFile()
/*    */   {
/* 61 */     return this.sourceFile;
/*    */   }
/*    */ 
/*    */   public int getDebugItemType()
/*    */   {
/* 72 */     return 9;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.debug.ImmutableSetSourceFile
 * JD-Core Version:    0.6.0
 */