/*    */ package org.jf.baksmali.Adaptors;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.baksmali.baksmaliOptions;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class RegisterFormatter
/*    */ {
/*    */   public final baksmaliOptions options;
/*    */   public final int registerCount;
/*    */   public final int parameterRegisterCount;
/*    */ 
/*    */   public RegisterFormatter(baksmaliOptions options, int registerCount, int parameterRegisterCount)
/*    */   {
/* 46 */     this.options = options;
/* 47 */     this.registerCount = registerCount;
/* 48 */     this.parameterRegisterCount = parameterRegisterCount;
/*    */   }
/*    */ 
/*    */   public void writeRegisterRange(IndentingWriter writer, int startRegister, int lastRegister)
/*    */     throws IOException
/*    */   {
/* 61 */     if (!this.options.noParameterRegisters) {
/* 62 */       assert (startRegister <= lastRegister);
/*    */ 
/* 64 */       if (startRegister >= this.registerCount - this.parameterRegisterCount) {
/* 65 */         writer.write("{p");
/* 66 */         writer.printSignedIntAsDec(startRegister - (this.registerCount - this.parameterRegisterCount));
/* 67 */         writer.write(" .. p");
/* 68 */         writer.printSignedIntAsDec(lastRegister - (this.registerCount - this.parameterRegisterCount));
/* 69 */         writer.write(125);
/* 70 */         return;
/*    */       }
/*    */     }
/* 73 */     writer.write("{v");
/* 74 */     writer.printSignedIntAsDec(startRegister);
/* 75 */     writer.write(" .. v");
/* 76 */     writer.printSignedIntAsDec(lastRegister);
/* 77 */     writer.write(125);
/*    */   }
/*    */ 
/*    */   public void writeTo(IndentingWriter writer, int register)
/*    */     throws IOException
/*    */   {
/* 89 */     if ((!this.options.noParameterRegisters) && 
/* 90 */       (register >= this.registerCount - this.parameterRegisterCount)) {
/* 91 */       writer.write(112);
/* 92 */       writer.printSignedIntAsDec(register - (this.registerCount - this.parameterRegisterCount));
/* 93 */       return;
/*    */     }
/*    */ 
/* 96 */     writer.write(118);
/* 97 */     writer.printSignedIntAsDec(register);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.RegisterFormatter
 * JD-Core Version:    0.6.0
 */