/*    */ package org.jf.baksmali.Adaptors.EncodedValue;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Collection;
/*    */ import org.jf.dexlib2.iface.value.ArrayEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class ArrayEncodedValueAdaptor
/*    */ {
/*    */   public static void writeTo(IndentingWriter writer, ArrayEncodedValue arrayEncodedValue)
/*    */     throws IOException
/*    */   {
/* 40 */     writer.write(123);
/* 41 */     Collection values = arrayEncodedValue.getValue();
/* 42 */     if (values.size() == 0) {
/* 43 */       writer.write(125);
/* 44 */       return;
/*    */     }
/*    */ 
/* 47 */     writer.write(10);
/* 48 */     writer.indent(4);
/* 49 */     boolean first = true;
/* 50 */     for (EncodedValue encodedValue : values) {
/* 51 */       if (!first) {
/* 52 */         writer.write(",\n");
/*    */       }
/* 54 */       first = false;
/*    */ 
/* 56 */       EncodedValueAdaptor.writeTo(writer, encodedValue);
/*    */     }
/* 58 */     writer.deindent(4);
/* 59 */     writer.write("\n}");
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.EncodedValue.ArrayEncodedValueAdaptor
 * JD-Core Version:    0.6.0
 */