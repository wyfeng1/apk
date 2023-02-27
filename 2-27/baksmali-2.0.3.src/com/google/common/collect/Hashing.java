/*    */ package com.google.common.collect;
/*    */ 
/*    */ final class Hashing
/*    */ {
/* 48 */   static int MAX_TABLE_SIZE = 1073741824;
/*    */ 
/*    */   static int smear(int hashCode)
/*    */   {
/* 45 */     return 461845907 * Integer.rotateLeft(hashCode * -862048943, 15);
/*    */   }
/*    */ 
/*    */   static int closedTableSize(int expectedEntries, double loadFactor)
/*    */   {
/* 53 */     expectedEntries = Math.max(expectedEntries, 2);
/* 54 */     int tableSize = Integer.highestOneBit(expectedEntries);
/*    */ 
/* 56 */     if (expectedEntries / tableSize > loadFactor) {
/* 57 */       tableSize <<= 1;
/* 58 */       return tableSize > 0 ? tableSize : MAX_TABLE_SIZE;
/*    */     }
/* 60 */     return tableSize;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.Hashing
 * JD-Core Version:    0.6.0
 */