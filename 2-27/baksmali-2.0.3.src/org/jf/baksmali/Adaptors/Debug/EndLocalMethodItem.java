/*    */ package org.jf.baksmali.Adaptors.Debug;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.baksmali.Adaptors.RegisterFormatter;
/*    */ import org.jf.dexlib2.iface.debug.EndLocal;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class EndLocalMethodItem extends DebugMethodItem
/*    */ {
/*    */   private final EndLocal endLocal;
/*    */   private final RegisterFormatter registerFormatter;
/*    */ 
/*    */   public EndLocalMethodItem(int codeAddress, int sortOrder, RegisterFormatter registerFormatter, EndLocal endLocal)
/*    */   {
/* 47 */     super(codeAddress, sortOrder);
/* 48 */     this.endLocal = endLocal;
/* 49 */     this.registerFormatter = registerFormatter;
/*    */   }
/*    */ 
/*    */   public boolean writeTo(IndentingWriter writer) throws IOException
/*    */   {
/* 54 */     writer.write(".end local ");
/* 55 */     this.registerFormatter.writeTo(writer, this.endLocal.getRegister());
/*    */ 
/* 57 */     String name = this.endLocal.getName();
/* 58 */     String type = this.endLocal.getType();
/* 59 */     String signature = this.endLocal.getSignature();
/* 60 */     if ((name != null) || (type != null) || (signature != null)) {
/* 61 */       writer.write("    # ");
/* 62 */       LocalFormatter.writeLocal(writer, name, type, signature);
/*    */     }
/* 64 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.Debug.EndLocalMethodItem
 * JD-Core Version:    0.6.0
 */