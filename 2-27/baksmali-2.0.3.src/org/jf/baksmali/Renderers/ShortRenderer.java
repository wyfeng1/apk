/*    */ package org.jf.baksmali.Renderers;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class ShortRenderer
/*    */ {
/*    */   public static void writeTo(IndentingWriter writer, short val)
/*    */     throws IOException
/*    */   {
/* 37 */     if (val < 0) {
/* 38 */       writer.write("-0x");
/* 39 */       writer.printUnsignedLongAsHex(-val);
/* 40 */       writer.write(115);
/*    */     } else {
/* 42 */       writer.write("0x");
/* 43 */       writer.printUnsignedLongAsHex(val);
/* 44 */       writer.write(115);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Renderers.ShortRenderer
 * JD-Core Version:    0.6.0
 */