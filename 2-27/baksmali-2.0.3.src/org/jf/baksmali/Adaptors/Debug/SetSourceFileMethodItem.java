/*    */ package org.jf.baksmali.Adaptors.Debug;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.dexlib2.iface.debug.SetSourceFile;
/*    */ import org.jf.util.IndentingWriter;
/*    */ import org.jf.util.StringUtils;
/*    */ 
/*    */ public class SetSourceFileMethodItem extends DebugMethodItem
/*    */ {
/*    */   private final String sourceFile;
/*    */ 
/*    */   public SetSourceFileMethodItem(int codeAddress, int sortOrder, SetSourceFile setSourceFile)
/*    */   {
/* 46 */     super(codeAddress, sortOrder);
/* 47 */     this.sourceFile = setSourceFile.getSourceFile();
/*    */   }
/*    */ 
/*    */   public boolean writeTo(IndentingWriter writer) throws IOException
/*    */   {
/* 52 */     writer.write(".source");
/*    */ 
/* 54 */     if (this.sourceFile != null) {
/* 55 */       writer.write(" \"");
/* 56 */       StringUtils.writeEscapedString(writer, this.sourceFile);
/* 57 */       writer.write(34);
/*    */     }
/* 59 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.Debug.SetSourceFileMethodItem
 * JD-Core Version:    0.6.0
 */