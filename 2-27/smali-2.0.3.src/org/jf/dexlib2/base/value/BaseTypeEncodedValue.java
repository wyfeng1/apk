/*    */ package org.jf.dexlib2.base.value;
/*    */ 
/*    */ import com.google.common.primitives.Ints;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ import org.jf.dexlib2.iface.value.TypeEncodedValue;
/*    */ 
/*    */ public abstract class BaseTypeEncodedValue
/*    */   implements TypeEncodedValue
/*    */ {
/*    */   public int hashCode()
/*    */   {
/* 45 */     return getValue().hashCode();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 50 */     if ((o instanceof TypeEncodedValue)) {
/* 51 */       return getValue().equals(((TypeEncodedValue)o).getValue());
/*    */     }
/* 53 */     return false;
/*    */   }
/*    */ 
/*    */   public int compareTo(EncodedValue o)
/*    */   {
/* 58 */     int res = Ints.compare(getValueType(), o.getValueType());
/* 59 */     if (res != 0) return res;
/* 60 */     return getValue().compareTo(((TypeEncodedValue)o).getValue());
/*    */   }
/*    */   public int getValueType() {
/* 63 */     return 24;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.value.BaseTypeEncodedValue
 * JD-Core Version:    0.6.0
 */