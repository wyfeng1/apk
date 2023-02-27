/*    */ package org.jf.baksmali.Renderers;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.util.IndentingWriter;
/*    */ import org.jf.util.StringUtils;
/*    */ 
/*    */ public class CharRenderer
/*    */ {
/*    */   public static void writeTo(IndentingWriter writer, char val)
/*    */     throws IOException
/*    */   {
/* 38 */     writer.write(39);
/* 39 */     StringUtils.writeEscapedChar(writer, val);
/* 40 */     writer.write(39);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Renderers.CharRenderer
 * JD-Core Version:    0.6.0
 */