/*    */ package org.jf.dexlib2.immutable.debug;
/*    */ 
/*    */ import org.jf.dexlib2.iface.debug.PrologueEnd;
/*    */ 
/*    */ public class ImmutablePrologueEnd extends ImmutableDebugItem
/*    */   implements PrologueEnd
/*    */ {
/*    */   public ImmutablePrologueEnd(int codeAddress)
/*    */   {
/* 41 */     super(codeAddress);
/*    */   }
/*    */ 
/*    */   public static ImmutablePrologueEnd of(PrologueEnd prologueEnd)
/*    */   {
/* 46 */     if ((prologueEnd instanceof ImmutablePrologueEnd)) {
/* 47 */       return (ImmutablePrologueEnd)prologueEnd;
/*    */     }
/* 49 */     return new ImmutablePrologueEnd(prologueEnd.getCodeAddress());
/*    */   }
/*    */   public int getDebugItemType() {
/* 52 */     return 7;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.debug.ImmutablePrologueEnd
 * JD-Core Version:    0.6.0
 */