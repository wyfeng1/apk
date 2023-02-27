/*    */ package org.jf.baksmali.Adaptors;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.Collection;
/*    */ import org.jf.baksmali.Adaptors.EncodedValue.EncodedValueAdaptor;
/*    */ import org.jf.dexlib2.AccessFlags;
/*    */ import org.jf.dexlib2.iface.Field;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ import org.jf.dexlib2.util.EncodedValueUtils;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class FieldDefinition
/*    */ {
/*    */   public static void writeTo(IndentingWriter writer, Field field, boolean setInStaticConstructor)
/*    */     throws IOException
/*    */   {
/* 44 */     EncodedValue initialValue = field.getInitialValue();
/* 45 */     int accessFlags = field.getAccessFlags();
/*    */ 
/* 47 */     if ((setInStaticConstructor) && (AccessFlags.STATIC.isSet(accessFlags)) && (AccessFlags.FINAL.isSet(accessFlags)) && (initialValue != null))
/*    */     {
/* 51 */       if (!EncodedValueUtils.isDefaultValue(initialValue)) {
/* 52 */         writer.write("# The value of this static final field might be set in the static constructor\n");
/*    */       }
/*    */       else
/*    */       {
/* 56 */         initialValue = null;
/*    */       }
/*    */     }
/*    */ 
/* 60 */     writer.write(".field ");
/* 61 */     writeAccessFlags(writer, field.getAccessFlags());
/* 62 */     writer.write(field.getName());
/* 63 */     writer.write(58);
/* 64 */     writer.write(field.getType());
/* 65 */     if (initialValue != null) {
/* 66 */       writer.write(" = ");
/* 67 */       EncodedValueAdaptor.writeTo(writer, initialValue);
/*    */     }
/*    */ 
/* 70 */     writer.write(10);
/*    */ 
/* 72 */     Collection annotations = field.getAnnotations();
/* 73 */     if (annotations.size() > 0) {
/* 74 */       writer.indent(4);
/* 75 */       AnnotationFormatter.writeTo(writer, annotations);
/* 76 */       writer.deindent(4);
/* 77 */       writer.write(".end field\n");
/*    */     }
/*    */   }
/*    */ 
/*    */   private static void writeAccessFlags(IndentingWriter writer, int accessFlags) throws IOException {
/* 82 */     for (AccessFlags accessFlag : AccessFlags.getAccessFlagsForField(accessFlags)) {
/* 83 */       writer.write(accessFlag.toString());
/* 84 */       writer.write(32);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.FieldDefinition
 * JD-Core Version:    0.6.0
 */