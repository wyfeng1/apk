/*    */ package org.jf.dexlib2.base.value;
/*    */ 
/*    */ import com.google.common.primitives.Chars;
/*    */ import com.google.common.primitives.Ints;
/*    */ import org.jf.dexlib2.iface.value.CharEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ 
/*    */ public abstract class BaseCharEncodedValue
/*    */   implements CharEncodedValue
/*    */ {
/*    */   public int hashCode()
/*    */   {
/* 46 */     return getValue();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 51 */     if ((o instanceof CharEncodedValue)) {
/* 52 */       return getValue() == ((CharEncodedValue)o).getValue();
/*    */     }
/* 54 */     return false;
/*    */   }
/*    */ 
/*    */   public int compareTo(EncodedValue o)
/*    */   {
/* 59 */     int res = Ints.compare(getValueType(), o.getValueType());
/* 60 */     if (res != 0) return res;
/* 61 */     return Chars.compare(getValue(), ((CharEncodedValue)o).getValue());
/*    */   }
/*    */   public int getValueType() {
/* 64 */     return 3;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.value.BaseCharEncodedValue
 * JD-Core Version:    0.6.0
 */