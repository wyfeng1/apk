/*    */ package org.jf.baksmali.Adaptors.Debug;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.baksmali.Adaptors.RegisterFormatter;
/*    */ import org.jf.dexlib2.iface.debug.StartLocal;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class StartLocalMethodItem extends DebugMethodItem
/*    */ {
/*    */   private final StartLocal startLocal;
/*    */   private final RegisterFormatter registerFormatter;
/*    */ 
/*    */   public StartLocalMethodItem(int codeAddress, int sortOrder, RegisterFormatter registerFormatter, StartLocal startLocal)
/*    */   {
/* 47 */     super(codeAddress, sortOrder);
/* 48 */     this.startLocal = startLocal;
/* 49 */     this.registerFormatter = registerFormatter;
/*    */   }
/*    */ 
/*    */   public boolean writeTo(IndentingWriter writer) throws IOException
/*    */   {
/* 54 */     writer.write(".local ");
/* 55 */     this.registerFormatter.writeTo(writer, this.startLocal.getRegister());
/*    */ 
/* 57 */     String name = this.startLocal.getName();
/* 58 */     String type = this.startLocal.getType();
/* 59 */     String signature = this.startLocal.getSignature();
/*    */ 
/* 61 */     if ((name != null) || (type != null) || (signature != null)) {
/* 62 */       writer.write(", ");
/* 63 */       LocalFormatter.writeLocal(writer, name, type, signature);
/*    */     }
/* 65 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.Debug.StartLocalMethodItem
 * JD-Core Version:    0.6.0
 */