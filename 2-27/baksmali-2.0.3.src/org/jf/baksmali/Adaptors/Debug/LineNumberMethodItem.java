/*    */ package org.jf.baksmali.Adaptors.Debug;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.dexlib2.iface.debug.LineNumber;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class LineNumberMethodItem extends DebugMethodItem
/*    */ {
/*    */   private final int lineNumber;
/*    */ 
/*    */   public LineNumberMethodItem(int codeAddress, int sortOrder, LineNumber lineNumber)
/*    */   {
/* 44 */     super(codeAddress, sortOrder);
/* 45 */     this.lineNumber = lineNumber.getLineNumber();
/*    */   }
/*    */ 
/*    */   public boolean writeTo(IndentingWriter writer) throws IOException
/*    */   {
/* 50 */     writer.write(".line ");
/* 51 */     writer.printUnsignedIntAsDec(this.lineNumber);
/* 52 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.Debug.LineNumberMethodItem
 * JD-Core Version:    0.6.0
 */