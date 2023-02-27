/*    */ package org.jf.dexlib2.base.value;
/*    */ 
/*    */ import com.google.common.primitives.Ints;
/*    */ import com.google.common.primitives.Longs;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ import org.jf.dexlib2.iface.value.LongEncodedValue;
/*    */ 
/*    */ public abstract class BaseLongEncodedValue
/*    */   implements LongEncodedValue
/*    */ {
/*    */   public int hashCode()
/*    */   {
/* 46 */     long value = getValue();
/* 47 */     int hashCode = (int)value;
/* 48 */     return hashCode * 31 + (int)(value >>> 32);
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 53 */     if ((o instanceof LongEncodedValue)) {
/* 54 */       return getValue() == ((LongEncodedValue)o).getValue();
/*    */     }
/* 56 */     return false;
/*    */   }
/*    */ 
/*    */   public int compareTo(EncodedValue o)
/*    */   {
/* 61 */     int res = Ints.compare(getValueType(), o.getValueType());
/* 62 */     if (res != 0) return res;
/* 63 */     return Longs.compare(getValue(), ((LongEncodedValue)o).getValue());
/*    */   }
/*    */   public int getValueType() {
/* 66 */     return 6;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.value.BaseLongEncodedValue
 * JD-Core Version:    0.6.0
 */