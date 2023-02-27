/*    */ package org.jf.dexlib2.base;
/*    */ 
/*    */ import org.jf.dexlib2.iface.ExceptionHandler;
/*    */ import org.jf.dexlib2.iface.TryBlock;
/*    */ 
/*    */ public abstract class BaseTryBlock<EH extends ExceptionHandler>
/*    */   implements TryBlock<EH>
/*    */ {
/*    */   public boolean equals(Object o)
/*    */   {
/* 39 */     if ((o instanceof TryBlock)) {
/* 40 */       TryBlock other = (TryBlock)o;
/* 41 */       return (getStartCodeAddress() == other.getStartCodeAddress()) && (getCodeUnitCount() == other.getCodeUnitCount()) && (getExceptionHandlers().equals(other.getExceptionHandlers()));
/*    */     }
/*    */ 
/* 45 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.BaseTryBlock
 * JD-Core Version:    0.6.0
 */