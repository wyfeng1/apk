/*    */ package org.jf.baksmali.Adaptors.EncodedValue;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Collection;
/*    */ import org.jf.dexlib2.iface.AnnotationElement;
/*    */ import org.jf.dexlib2.iface.value.AnnotationEncodedValue;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public abstract class AnnotationEncodedValueAdaptor
/*    */ {
/*    */   public static void writeTo(IndentingWriter writer, AnnotationEncodedValue annotationEncodedValue)
/*    */     throws IOException
/*    */   {
/* 42 */     writer.write(".subannotation ");
/* 43 */     writer.write(annotationEncodedValue.getType());
/* 44 */     writer.write(10);
/*    */ 
/* 46 */     writeElementsTo(writer, annotationEncodedValue.getElements());
/* 47 */     writer.write(".end subannotation");
/*    */   }
/*    */ 
/*    */   public static void writeElementsTo(IndentingWriter writer, Collection<? extends AnnotationElement> annotationElements) throws IOException
/*    */   {
/* 52 */     writer.indent(4);
/* 53 */     for (AnnotationElement annotationElement : annotationElements) {
/* 54 */       writer.write(annotationElement.getName());
/* 55 */       writer.write(" = ");
/* 56 */       EncodedValueAdaptor.writeTo(writer, annotationElement.getValue());
/* 57 */       writer.write(10);
/*    */     }
/* 59 */     writer.deindent(4);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.EncodedValue.AnnotationEncodedValueAdaptor
 * JD-Core Version:    0.6.0
 */