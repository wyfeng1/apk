/*    */ package org.jf.dexlib2.base.value;
/*    */ 
/*    */ import com.google.common.primitives.Ints;
/*    */ import org.jf.dexlib2.iface.value.ArrayEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ import org.jf.util.CollectionUtils;
/*    */ 
/*    */ public abstract class BaseArrayEncodedValue
/*    */   implements ArrayEncodedValue
/*    */ {
/*    */   public int hashCode()
/*    */   {
/* 46 */     return getValue().hashCode();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 51 */     if ((o instanceof ArrayEncodedValue)) {
/* 52 */       return getValue().equals(((ArrayEncodedValue)o).getValue());
/*    */     }
/* 54 */     return false;
/*    */   }
/*    */ 
/*    */   public int compareTo(EncodedValue o) {
/* 58 */     int res = Ints.compare(getValueType(), o.getValueType());
/* 59 */     if (res != 0) return res;
/* 60 */     return CollectionUtils.compareAsList(getValue(), ((ArrayEncodedValue)o).getValue());
/*    */   }
/*    */   public int getValueType() {
/* 63 */     return 28;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.value.BaseArrayEncodedValue
 * JD-Core Version:    0.6.0
 */