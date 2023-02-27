/*    */ package org.jf.baksmali.Adaptors;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Collection;
/*    */ import org.jf.baksmali.Adaptors.EncodedValue.AnnotationEncodedValueAdaptor;
/*    */ import org.jf.dexlib2.AnnotationVisibility;
/*    */ import org.jf.dexlib2.iface.Annotation;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class AnnotationFormatter
/*    */ {
/*    */   public static void writeTo(IndentingWriter writer, Collection<? extends Annotation> annotations)
/*    */     throws IOException
/*    */   {
/* 43 */     boolean first = true;
/* 44 */     for (Annotation annotation : annotations) {
/* 45 */       if (!first) {
/* 46 */         writer.write(10);
/*    */       }
/* 48 */       first = false;
/*    */ 
/* 50 */       writeTo(writer, annotation);
/*    */     }
/*    */   }
/*    */ 
/*    */   public static void writeTo(IndentingWriter writer, Annotation annotation) throws IOException {
/* 55 */     writer.write(".annotation ");
/* 56 */     writer.write(AnnotationVisibility.getVisibility(annotation.getVisibility()));
/* 57 */     writer.write(32);
/* 58 */     writer.write(annotation.getType());
/* 59 */     writer.write(10);
/*    */ 
/* 61 */     AnnotationEncodedValueAdaptor.writeElementsTo(writer, annotation.getElements());
/*    */ 
/* 63 */     writer.write(".end annotation\n");
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.AnnotationFormatter
 * JD-Core Version:    0.6.0
 */