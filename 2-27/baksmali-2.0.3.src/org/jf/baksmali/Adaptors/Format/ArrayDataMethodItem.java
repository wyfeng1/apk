/*    */ package org.jf.baksmali.Adaptors.Format;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.jf.baksmali.Adaptors.MethodDefinition;
/*    */ import org.jf.baksmali.Renderers.LongRenderer;
/*    */ import org.jf.dexlib2.iface.instruction.formats.ArrayPayload;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class ArrayDataMethodItem extends InstructionMethodItem<ArrayPayload>
/*    */ {
/*    */   public ArrayDataMethodItem(MethodDefinition methodDef, int codeAddress, ArrayPayload instruction)
/*    */   {
/* 41 */     super(methodDef, codeAddress, instruction);
/*    */   }
/*    */ 
/*    */   public boolean writeTo(IndentingWriter writer) throws IOException {
/* 45 */     int elementWidth = ((ArrayPayload)this.instruction).getElementWidth();
/*    */ 
/* 47 */     writer.write(".array-data ");
/* 48 */     writer.printSignedIntAsDec(((ArrayPayload)this.instruction).getElementWidth());
/* 49 */     writer.write(10);
/*    */ 
/* 51 */     writer.indent(4);
/*    */ 
/* 53 */     List elements = ((ArrayPayload)this.instruction).getArrayElements();
/*    */ 
/* 55 */     String suffix = "";
/* 56 */     switch (elementWidth) {
/*    */     case 1:
/* 58 */       suffix = "t";
/* 59 */       break;
/*    */     case 2:
/* 61 */       suffix = "s";
/*    */     }
/*    */ 
/* 65 */     for (Number number : elements) {
/* 66 */       LongRenderer.writeSignedIntOrLongTo(writer, number.longValue());
/* 67 */       writer.write(suffix);
/* 68 */       if (elementWidth == 4)
/* 69 */         writeResourceId(writer, number.intValue());
/* 70 */       writer.write("\n");
/*    */     }
/* 72 */     writer.deindent(4);
/* 73 */     writer.write(".end array-data");
/* 74 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.Format.ArrayDataMethodItem
 * JD-Core Version:    0.6.0
 */