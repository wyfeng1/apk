/*    */ package org.jf.dexlib2.base.value;
/*    */ 
/*    */ import com.google.common.primitives.Ints;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ import org.jf.dexlib2.iface.value.NullEncodedValue;
/*    */ 
/*    */ public abstract class BaseNullEncodedValue
/*    */   implements NullEncodedValue
/*    */ {
/*    */   public int hashCode()
/*    */   {
/* 45 */     return 0;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 50 */     return o instanceof NullEncodedValue;
/*    */   }
/*    */ 
/*    */   public int compareTo(EncodedValue o)
/*    */   {
/* 55 */     return Ints.compare(getValueType(), o.getValueType());
/*    */   }
/*    */   public int getValueType() {
/* 58 */     return 30;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.value.BaseNullEncodedValue
 * JD-Core Version:    0.6.0
 */