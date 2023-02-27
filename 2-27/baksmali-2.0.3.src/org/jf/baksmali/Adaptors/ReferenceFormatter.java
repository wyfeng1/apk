/*    */ package org.jf.baksmali.Adaptors;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.dexlib2.iface.reference.FieldReference;
/*    */ import org.jf.dexlib2.iface.reference.MethodReference;
/*    */ import org.jf.dexlib2.iface.reference.Reference;
/*    */ import org.jf.dexlib2.iface.reference.StringReference;
/*    */ import org.jf.dexlib2.iface.reference.TypeReference;
/*    */ import org.jf.dexlib2.util.ReferenceUtil;
/*    */ import org.jf.util.IndentingWriter;
/*    */ import org.jf.util.StringUtils;
/*    */ 
/*    */ public class ReferenceFormatter
/*    */ {
/*    */   public static void writeStringReference(IndentingWriter writer, String item)
/*    */     throws IOException
/*    */   {
/* 41 */     writer.write(34);
/* 42 */     StringUtils.writeEscapedString(writer, item);
/* 43 */     writer.write(34);
/*    */   }
/*    */ 
/*    */   public static void writeReference(IndentingWriter writer, int referenceType, Reference reference) throws IOException
/*    */   {
/* 48 */     switch (referenceType) {
/*    */     case 0:
/* 50 */       writeStringReference(writer, ((StringReference)reference).getString());
/* 51 */       return;
/*    */     case 1:
/* 53 */       writer.write(((TypeReference)reference).getType());
/* 54 */       return;
/*    */     case 3:
/* 56 */       ReferenceUtil.writeMethodDescriptor(writer, (MethodReference)reference);
/* 57 */       return;
/*    */     case 2:
/* 59 */       ReferenceUtil.writeFieldDescriptor(writer, (FieldReference)reference);
/* 60 */       return;
/*    */     }
/* 62 */     throw new IllegalStateException("Unknown reference type");
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.ReferenceFormatter
 * JD-Core Version:    0.6.0
 */