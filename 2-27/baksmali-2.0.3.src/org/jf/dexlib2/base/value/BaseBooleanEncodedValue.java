/*    */ package org.jf.dexlib2.base.value;
/*    */ 
/*    */ import com.google.common.primitives.Booleans;
/*    */ import com.google.common.primitives.Ints;
/*    */ import org.jf.dexlib2.iface.value.BooleanEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ 
/*    */ public abstract class BaseBooleanEncodedValue
/*    */   implements BooleanEncodedValue
/*    */ {
/*    */   public int hashCode()
/*    */   {
/* 46 */     return getValue() ? 1 : 0;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 51 */     if ((o instanceof BooleanEncodedValue)) {
/* 52 */       return getValue() == ((BooleanEncodedValue)o).getValue();
/*    */     }
/* 54 */     return false;
/*    */   }
/*    */ 
/*    */   public int compareTo(EncodedValue o)
/*    */   {
/* 59 */     int res = Ints.compare(getValueType(), o.getValueType());
/* 60 */     if (res != 0) return res;
/* 61 */     return Booleans.compare(getValue(), ((BooleanEncodedValue)o).getValue());
/*    */   }
/*    */   public int getValueType() {
/* 64 */     return 31;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.value.BaseBooleanEncodedValue
 * JD-Core Version:    0.6.0
 */