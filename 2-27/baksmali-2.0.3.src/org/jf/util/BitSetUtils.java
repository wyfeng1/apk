/*    */ package org.jf.util;
/*    */ 
/*    */ import java.util.BitSet;
/*    */ 
/*    */ public class BitSetUtils
/*    */ {
/*    */   public static BitSet bitSetOfIndexes(int[] indexes)
/*    */   {
/* 38 */     BitSet bitSet = new BitSet();
/* 39 */     for (int index : indexes) {
/* 40 */       bitSet.set(index);
/*    */     }
/* 42 */     return bitSet;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.util.BitSetUtils
 * JD-Core Version:    0.6.0
 */