/*    */ package org.jf.dexlib2.base.value;
/*    */ 
/*    */ import com.google.common.primitives.Ints;
/*    */ import org.jf.dexlib2.iface.reference.MethodReference;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ import org.jf.dexlib2.iface.value.MethodEncodedValue;
/*    */ 
/*    */ public abstract class BaseMethodEncodedValue
/*    */   implements MethodEncodedValue
/*    */ {
/*    */   public int hashCode()
/*    */   {
/* 45 */     return getValue().hashCode();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 50 */     if ((o instanceof MethodEncodedValue)) {
/* 51 */       return getValue().equals(((MethodEncodedValue)o).getValue());
/*    */     }
/* 53 */     return false;
/*    */   }
/*    */ 
/*    */   public int compareTo(EncodedValue o)
/*    */   {
/* 58 */     int res = Ints.compare(getValueType(), o.getValueType());
/* 59 */     if (res != 0) return res;
/* 60 */     return getValue().compareTo(((MethodEncodedValue)o).getValue());
/*    */   }
/*    */   public int getValueType() {
/* 63 */     return 26;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.value.BaseMethodEncodedValue
 * JD-Core Version:    0.6.0
 */