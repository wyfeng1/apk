/*    */ package org.jf.dexlib2.base.value;
/*    */ 
/*    */ import com.google.common.primitives.Ints;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ import org.jf.dexlib2.iface.value.FloatEncodedValue;
/*    */ 
/*    */ public abstract class BaseFloatEncodedValue
/*    */   implements FloatEncodedValue
/*    */ {
/*    */   public int hashCode()
/*    */   {
/* 45 */     return Float.floatToRawIntBits(getValue());
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 50 */     if ((o != null) && ((o instanceof FloatEncodedValue))) {
/* 51 */       return Float.floatToRawIntBits(getValue()) == Float.floatToRawIntBits(((FloatEncodedValue)o).getValue());
/*    */     }
/* 53 */     return false;
/*    */   }
/*    */ 
/*    */   public int compareTo(EncodedValue o)
/*    */   {
/* 58 */     int res = Ints.compare(getValueType(), o.getValueType());
/* 59 */     if (res != 0) return res;
/* 60 */     return Float.compare(getValue(), ((FloatEncodedValue)o).getValue());
/*    */   }
/*    */   public int getValueType() {
/* 63 */     return 16;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.value.BaseFloatEncodedValue
 * JD-Core Version:    0.6.0
 */