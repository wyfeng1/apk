/*    */ package org.jf.baksmali.Renderers;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class DoubleRenderer
/*    */ {
/*    */   public static void writeTo(IndentingWriter writer, double val)
/*    */     throws IOException
/*    */   {
/* 37 */     writer.write(Double.toString(val));
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Renderers.DoubleRenderer
 * JD-Core Version:    0.6.0
 */