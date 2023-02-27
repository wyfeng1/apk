/*    */ package org.jf.baksmali.Adaptors.Debug;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.baksmali.Adaptors.RegisterFormatter;
/*    */ import org.jf.dexlib2.iface.debug.RestartLocal;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class RestartLocalMethodItem extends DebugMethodItem
/*    */ {
/*    */   private final RestartLocal restartLocal;
/*    */   private final RegisterFormatter registerFormatter;
/*    */ 
/*    */   public RestartLocalMethodItem(int codeAddress, int sortOrder, RegisterFormatter registerFormatter, RestartLocal restartLocal)
/*    */   {
/* 47 */     super(codeAddress, sortOrder);
/* 48 */     this.restartLocal = restartLocal;
/* 49 */     this.registerFormatter = registerFormatter;
/*    */   }
/*    */ 
/*    */   public boolean writeTo(IndentingWriter writer) throws IOException
/*    */   {
/* 54 */     writer.write(".restart local ");
/* 55 */     this.registerFormatter.writeTo(writer, this.restartLocal.getRegister());
/*    */ 
/* 57 */     String name = this.restartLocal.getName();
/* 58 */     String type = this.restartLocal.getType();
/* 59 */     String signature = this.restartLocal.getSignature();
/* 60 */     if ((name != null) || (type != null) || (signature != null)) {
/* 61 */       writer.write("    # ");
/* 62 */       LocalFormatter.writeLocal(writer, name, type, signature);
/*    */     }
/* 64 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.Debug.RestartLocalMethodItem
 * JD-Core Version:    0.6.0
 */