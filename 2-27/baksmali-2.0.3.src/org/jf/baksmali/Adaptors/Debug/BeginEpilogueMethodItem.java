/*    */ package org.jf.baksmali.Adaptors.Debug;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class BeginEpilogueMethodItem extends DebugMethodItem
/*    */ {
/*    */   public BeginEpilogueMethodItem(int codeAddress, int sortOrder)
/*    */   {
/* 40 */     super(codeAddress, sortOrder);
/*    */   }
/*    */ 
/*    */   public boolean writeTo(IndentingWriter writer) throws IOException
/*    */   {
/* 45 */     writer.write(".prologue");
/* 46 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.Debug.BeginEpilogueMethodItem
 * JD-Core Version:    0.6.0
 */