/*    */ package org.jf.dexlib2.base.value;
/*    */ 
/*    */ import com.google.common.primitives.Ints;
/*    */ import org.jf.dexlib2.iface.value.ByteEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ 
/*    */ public abstract class BaseByteEncodedValue
/*    */   implements ByteEncodedValue
/*    */ {
/*    */   public int hashCode()
/*    */   {
/* 45 */     return getValue();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 50 */     if ((o instanceof ByteEncodedValue)) {
/* 51 */       return getValue() == ((ByteEncodedValue)o).getValue();
/*    */     }
/* 53 */     return false;
/*    */   }
/*    */ 
/*    */   public int compareTo(EncodedValue o)
/*    */   {
/* 58 */     int res = Ints.compare(getValueType(), o.getValueType());
/* 59 */     if (res != 0) return res;
/* 60 */     return Ints.compare(getValue(), ((ByteEncodedValue)o).getValue());
/*    */   }
/*    */   public int getValueType() {
/* 63 */     return 0;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.value.BaseByteEncodedValue
 * JD-Core Version:    0.6.0
 */