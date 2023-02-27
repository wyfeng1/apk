/*    */ package org.jf.baksmali.Adaptors.EncodedValue;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.baksmali.Adaptors.ReferenceFormatter;
/*    */ import org.jf.baksmali.Renderers.BooleanRenderer;
/*    */ import org.jf.baksmali.Renderers.ByteRenderer;
/*    */ import org.jf.baksmali.Renderers.CharRenderer;
/*    */ import org.jf.baksmali.Renderers.DoubleRenderer;
/*    */ import org.jf.baksmali.Renderers.FloatRenderer;
/*    */ import org.jf.baksmali.Renderers.IntegerRenderer;
/*    */ import org.jf.baksmali.Renderers.LongRenderer;
/*    */ import org.jf.baksmali.Renderers.ShortRenderer;
/*    */ import org.jf.dexlib2.iface.value.AnnotationEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.ArrayEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.BooleanEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.ByteEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.CharEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.DoubleEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ import org.jf.dexlib2.iface.value.EnumEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.FieldEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.FloatEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.IntEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.LongEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.MethodEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.ShortEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.StringEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.TypeEncodedValue;
/*    */ import org.jf.dexlib2.util.ReferenceUtil;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public abstract class EncodedValueAdaptor
/*    */ {
/*    */   public static void writeTo(IndentingWriter writer, EncodedValue encodedValue)
/*    */     throws IOException
/*    */   {
/* 42 */     switch (encodedValue.getValueType()) {
/*    */     case 29:
/* 44 */       AnnotationEncodedValueAdaptor.writeTo(writer, (AnnotationEncodedValue)encodedValue);
/* 45 */       return;
/*    */     case 28:
/* 47 */       ArrayEncodedValueAdaptor.writeTo(writer, (ArrayEncodedValue)encodedValue);
/* 48 */       return;
/*    */     case 31:
/* 50 */       BooleanRenderer.writeTo(writer, ((BooleanEncodedValue)encodedValue).getValue());
/* 51 */       return;
/*    */     case 0:
/* 53 */       ByteRenderer.writeTo(writer, ((ByteEncodedValue)encodedValue).getValue());
/* 54 */       return;
/*    */     case 3:
/* 56 */       CharRenderer.writeTo(writer, ((CharEncodedValue)encodedValue).getValue());
/* 57 */       return;
/*    */     case 17:
/* 59 */       DoubleRenderer.writeTo(writer, ((DoubleEncodedValue)encodedValue).getValue());
/* 60 */       return;
/*    */     case 27:
/* 62 */       writer.write(".enum ");
/* 63 */       ReferenceUtil.writeFieldDescriptor(writer, ((EnumEncodedValue)encodedValue).getValue());
/* 64 */       return;
/*    */     case 25:
/* 66 */       ReferenceUtil.writeFieldDescriptor(writer, ((FieldEncodedValue)encodedValue).getValue());
/* 67 */       return;
/*    */     case 16:
/* 69 */       FloatRenderer.writeTo(writer, ((FloatEncodedValue)encodedValue).getValue());
/* 70 */       return;
/*    */     case 4:
/* 72 */       IntegerRenderer.writeTo(writer, ((IntEncodedValue)encodedValue).getValue());
/* 73 */       return;
/*    */     case 6:
/* 75 */       LongRenderer.writeTo(writer, ((LongEncodedValue)encodedValue).getValue());
/* 76 */       return;
/*    */     case 26:
/* 78 */       ReferenceUtil.writeMethodDescriptor(writer, ((MethodEncodedValue)encodedValue).getValue());
/* 79 */       return;
/*    */     case 30:
/* 81 */       writer.write("null");
/* 82 */       return;
/*    */     case 2:
/* 84 */       ShortRenderer.writeTo(writer, ((ShortEncodedValue)encodedValue).getValue());
/* 85 */       return;
/*    */     case 23:
/* 87 */       ReferenceFormatter.writeStringReference(writer, ((StringEncodedValue)encodedValue).getValue());
/* 88 */       return;
/*    */     case 24:
/* 90 */       writer.write(((TypeEncodedValue)encodedValue).getValue());
/*    */     case 1:
/*    */     case 5:
/*    */     case 7:
/*    */     case 8:
/*    */     case 9:
/*    */     case 10:
/*    */     case 11:
/*    */     case 12:
/*    */     case 13:
/*    */     case 14:
/*    */     case 15:
/*    */     case 18:
/*    */     case 19:
/*    */     case 20:
/*    */     case 21:
/*    */     case 22:
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.EncodedValue.EncodedValueAdaptor
 * JD-Core Version:    0.6.0
 */