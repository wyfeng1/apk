/*    */ package org.jf.baksmali.Renderers;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class FloatRenderer
/*    */ {
/*    */   public static void writeTo(IndentingWriter writer, float val)
/*    */     throws IOException
/*    */   {
/* 37 */     writer.write(Float.toString(val));
/* 38 */     writer.write(102);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Renderers.FloatRenderer
 * JD-Core Version:    0.6.0
 */