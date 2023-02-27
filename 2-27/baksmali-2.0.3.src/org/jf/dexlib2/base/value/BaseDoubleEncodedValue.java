/*    */ package org.jf.dexlib2.base.value;
/*    */ 
/*    */ import com.google.common.primitives.Ints;
/*    */ import org.jf.dexlib2.iface.value.DoubleEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ 
/*    */ public abstract class BaseDoubleEncodedValue
/*    */   implements DoubleEncodedValue
/*    */ {
/*    */   public int hashCode()
/*    */   {
/* 45 */     long v = Double.doubleToRawLongBits(getValue());
/* 46 */     return (int)(v ^ v >>> 32);
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 51 */     if ((o instanceof DoubleEncodedValue)) {
/* 52 */       return Double.doubleToRawLongBits(getValue()) == Double.doubleToRawLongBits(((DoubleEncodedValue)o).getValue());
/*    */     }
/*    */ 
/* 55 */     return false;
/*    */   }
/*    */ 
/*    */   public int compareTo(EncodedValue o)
/*    */   {
/* 60 */     int res = Ints.compare(getValueType(), o.getValueType());
/* 61 */     if (res != 0) return res;
/* 62 */     return Double.compare(getValue(), ((DoubleEncodedValue)o).getValue());
/*    */   }
/*    */   public int getValueType() {
/* 65 */     return 17;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.value.BaseDoubleEncodedValue
 * JD-Core Version:    0.6.0
 */