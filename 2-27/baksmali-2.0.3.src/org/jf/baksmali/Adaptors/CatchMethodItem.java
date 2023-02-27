/*    */ package org.jf.baksmali.Adaptors;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.baksmali.baksmaliOptions;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class CatchMethodItem extends MethodItem
/*    */ {
/*    */   private final String exceptionType;
/*    */   private final LabelMethodItem tryStartLabel;
/*    */   private final LabelMethodItem tryEndLabel;
/*    */   private final LabelMethodItem handlerLabel;
/*    */ 
/*    */   public CatchMethodItem(baksmaliOptions options, MethodDefinition.LabelCache labelCache, int codeAddress, String exceptionType, int startAddress, int endAddress, int handlerAddress)
/*    */   {
/* 48 */     super(codeAddress);
/* 49 */     this.exceptionType = exceptionType;
/*    */ 
/* 51 */     this.tryStartLabel = labelCache.internLabel(new LabelMethodItem(options, startAddress, "try_start_"));
/*    */ 
/* 55 */     this.tryEndLabel = labelCache.internLabel(new EndTryLabelMethodItem(options, codeAddress, endAddress));
/*    */ 
/* 57 */     if (exceptionType == null)
/* 58 */       this.handlerLabel = labelCache.internLabel(new LabelMethodItem(options, handlerAddress, "catchall_"));
/*    */     else
/* 60 */       this.handlerLabel = labelCache.internLabel(new LabelMethodItem(options, handlerAddress, "catch_"));
/*    */   }
/*    */ 
/*    */   public double getSortOrder()
/*    */   {
/* 78 */     return 102.0D;
/*    */   }
/*    */ 
/*    */   public boolean writeTo(IndentingWriter writer) throws IOException
/*    */   {
/* 83 */     if (this.exceptionType == null) {
/* 84 */       writer.write(".catchall");
/*    */     } else {
/* 86 */       writer.write(".catch ");
/* 87 */       writer.write(this.exceptionType);
/*    */     }
/* 89 */     writer.write(" {");
/* 90 */     this.tryStartLabel.writeTo(writer);
/* 91 */     writer.write(" .. ");
/* 92 */     this.tryEndLabel.writeTo(writer);
/* 93 */     writer.write("} ");
/* 94 */     this.handlerLabel.writeTo(writer);
/* 95 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.CatchMethodItem
 * JD-Core Version:    0.6.0
 */