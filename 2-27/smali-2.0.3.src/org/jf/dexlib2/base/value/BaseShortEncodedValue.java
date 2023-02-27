/*    */ package org.jf.dexlib2.base.value;
/*    */ 
/*    */ import com.google.common.primitives.Ints;
/*    */ import com.google.common.primitives.Shorts;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ import org.jf.dexlib2.iface.value.ShortEncodedValue;
/*    */ 
/*    */ public abstract class BaseShortEncodedValue
/*    */   implements ShortEncodedValue
/*    */ {
/*    */   public int hashCode()
/*    */   {
/* 46 */     return getValue();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 51 */     if ((o instanceof ShortEncodedValue)) {
/* 52 */       return getValue() == ((ShortEncodedValue)o).getValue();
/*    */     }
/* 54 */     return false;
/*    */   }
/*    */ 
/*    */   public int compareTo(EncodedValue o)
/*    */   {
/* 59 */     int res = Ints.compare(getValueType(), o.getValueType());
/* 60 */     if (res != 0) return res;
/* 61 */     return Shorts.compare(getValue(), ((ShortEncodedValue)o).getValue());
/*    */   }
/*    */   public int getValueType() {
/* 64 */     return 2;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.value.BaseShortEncodedValue
 * JD-Core Version:    0.6.0
 */