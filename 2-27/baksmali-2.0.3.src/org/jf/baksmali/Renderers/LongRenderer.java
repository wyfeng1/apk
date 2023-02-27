/*    */ package org.jf.baksmali.Renderers;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class LongRenderer
/*    */ {
/*    */   public static void writeTo(IndentingWriter writer, long val)
/*    */     throws IOException
/*    */   {
/* 37 */     if (val < 0L) {
/* 38 */       writer.write("-0x");
/* 39 */       writer.printUnsignedLongAsHex(-val);
/* 40 */       writer.write(76);
/*    */     } else {
/* 42 */       writer.write("0x");
/* 43 */       writer.printUnsignedLongAsHex(val);
/* 44 */       writer.write(76);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void writeSignedIntOrLongTo(IndentingWriter writer, long val) throws IOException {
/* 49 */     if (val < 0L) {
/* 50 */       writer.write("-0x");
/* 51 */       writer.printUnsignedLongAsHex(-val);
/* 52 */       if (val < -2147483648L)
/* 53 */         writer.write(76);
/*    */     }
/*    */     else {
/* 56 */       writer.write("0x");
/* 57 */       writer.printUnsignedLongAsHex(val);
/* 58 */       if (val > 2147483647L)
/* 59 */         writer.write(76);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Renderers.LongRenderer
 * JD-Core Version:    0.6.0
 */