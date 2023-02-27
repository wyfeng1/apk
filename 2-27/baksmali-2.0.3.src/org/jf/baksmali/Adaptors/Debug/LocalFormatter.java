/*    */ package org.jf.baksmali.Adaptors.Debug;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.baksmali.Adaptors.ReferenceFormatter;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class LocalFormatter
/*    */ {
/*    */   public static void writeLocal(IndentingWriter writer, String name, String type, String signature)
/*    */     throws IOException
/*    */   {
/* 57 */     if (name != null)
/* 58 */       ReferenceFormatter.writeStringReference(writer, name);
/*    */     else {
/* 60 */       writer.write("null");
/*    */     }
/* 62 */     writer.write(58);
/* 63 */     if (type != null)
/* 64 */       writer.write(type);
/*    */     else {
/* 66 */       writer.write("V");
/*    */     }
/* 68 */     if (signature != null) {
/* 69 */       writer.write(", ");
/* 70 */       ReferenceFormatter.writeStringReference(writer, signature);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.Debug.LocalFormatter
 * JD-Core Version:    0.6.0
 */