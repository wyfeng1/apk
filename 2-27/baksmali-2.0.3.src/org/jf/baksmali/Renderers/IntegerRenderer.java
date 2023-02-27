/*    */ package org.jf.baksmali.Renderers;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class IntegerRenderer
/*    */ {
/*    */   public static void writeTo(IndentingWriter writer, int val)
/*    */     throws IOException
/*    */   {
/* 37 */     if (val < 0) {
/* 38 */       writer.write("-0x");
/* 39 */       writer.printUnsignedLongAsHex(-val);
/*    */     } else {
/* 41 */       writer.write("0x");
/* 42 */       writer.printUnsignedLongAsHex(val);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Renderers.IntegerRenderer
 * JD-Core Version:    0.6.0
 */