/*    */ package org.jf.baksmali.Adaptors.Debug;
/*    */ 
/*    */ import org.jf.baksmali.Adaptors.MethodItem;
/*    */ import org.jf.baksmali.Adaptors.RegisterFormatter;
/*    */ import org.jf.dexlib2.iface.debug.DebugItem;
/*    */ import org.jf.dexlib2.iface.debug.EndLocal;
/*    */ import org.jf.dexlib2.iface.debug.LineNumber;
/*    */ import org.jf.dexlib2.iface.debug.RestartLocal;
/*    */ import org.jf.dexlib2.iface.debug.SetSourceFile;
/*    */ import org.jf.dexlib2.iface.debug.StartLocal;
/*    */ import org.jf.util.ExceptionWithContext;
/*    */ 
/*    */ public abstract class DebugMethodItem extends MethodItem
/*    */ {
/*    */   private final int sortOrder;
/*    */ 
/*    */   protected DebugMethodItem(int codeAddress, int sortOrder)
/*    */   {
/* 44 */     super(codeAddress);
/* 45 */     this.sortOrder = sortOrder;
/*    */   }
/*    */   public double getSortOrder() {
/* 48 */     return this.sortOrder;
/*    */   }
/*    */   public static DebugMethodItem build(RegisterFormatter registerFormatter, DebugItem debugItem) {
/* 51 */     int codeAddress = debugItem.getCodeAddress();
/* 52 */     switch (debugItem.getDebugItemType()) {
/*    */     case 3:
/* 54 */       return new StartLocalMethodItem(codeAddress, -1, registerFormatter, (StartLocal)debugItem);
/*    */     case 5:
/* 56 */       return new EndLocalMethodItem(codeAddress, -1, registerFormatter, (EndLocal)debugItem);
/*    */     case 6:
/* 58 */       return new RestartLocalMethodItem(codeAddress, -1, registerFormatter, (RestartLocal)debugItem);
/*    */     case 8:
/* 60 */       return new BeginEpilogueMethodItem(codeAddress, -4);
/*    */     case 7:
/* 62 */       return new EndPrologueMethodItem(codeAddress, -4);
/*    */     case 9:
/* 64 */       return new SetSourceFileMethodItem(codeAddress, -3, (SetSourceFile)debugItem);
/*    */     case 10:
/* 66 */       return new LineNumberMethodItem(codeAddress, -2, (LineNumber)debugItem);
/*    */     case 4:
/* 68 */     }throw new ExceptionWithContext("Invalid debug item type: %d", new Object[] { Integer.valueOf(debugItem.getDebugItemType()) });
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.Debug.DebugMethodItem
 * JD-Core Version:    0.6.0
 */