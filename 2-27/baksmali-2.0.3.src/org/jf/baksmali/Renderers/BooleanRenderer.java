/*    */ package org.jf.baksmali.Renderers;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class BooleanRenderer
/*    */ {
/*    */   public static void writeTo(IndentingWriter writer, boolean val)
/*    */     throws IOException
/*    */   {
/* 37 */     if (val)
/* 38 */       writer.write("true");
/*    */     else
/* 40 */       writer.write("false");
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Renderers.BooleanRenderer
 * JD-Core Version:    0.6.0
 */