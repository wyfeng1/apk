/*    */ package org.jf.dexlib2.base.value;
/*    */ 
/*    */ import com.google.common.primitives.Ints;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ import org.jf.dexlib2.iface.value.IntEncodedValue;
/*    */ 
/*    */ public abstract class BaseIntEncodedValue
/*    */   implements IntEncodedValue
/*    */ {
/*    */   public int hashCode()
/*    */   {
/* 45 */     return getValue();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 50 */     if ((o instanceof IntEncodedValue)) {
/* 51 */       return getValue() == ((IntEncodedValue)o).getValue();
/*    */     }
/* 53 */     return false;
/*    */   }
/*    */ 
/*    */   public int compareTo(EncodedValue o)
/*    */   {
/* 58 */     int res = Ints.compare(getValueType(), o.getValueType());
/* 59 */     if (res != 0) return res;
/* 60 */     return Ints.compare(getValue(), ((IntEncodedValue)o).getValue());
/*    */   }
/*    */   public int getValueType() {
/* 63 */     return 4;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.value.BaseIntEncodedValue
 * JD-Core Version:    0.6.0
 */