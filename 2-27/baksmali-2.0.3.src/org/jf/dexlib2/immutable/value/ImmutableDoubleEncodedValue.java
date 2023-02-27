/*    */ package org.jf.dexlib2.immutable.value;
/*    */ 
/*    */ import org.jf.dexlib2.base.value.BaseDoubleEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.DoubleEncodedValue;
/*    */ 
/*    */ public class ImmutableDoubleEncodedValue extends BaseDoubleEncodedValue
/*    */   implements ImmutableEncodedValue
/*    */ {
/*    */   protected final double value;
/*    */ 
/*    */   public ImmutableDoubleEncodedValue(double value)
/*    */   {
/* 41 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public static ImmutableDoubleEncodedValue of(DoubleEncodedValue doubleEncodedValue) {
/* 45 */     if ((doubleEncodedValue instanceof ImmutableDoubleEncodedValue)) {
/* 46 */       return (ImmutableDoubleEncodedValue)doubleEncodedValue;
/*    */     }
/* 48 */     return new ImmutableDoubleEncodedValue(doubleEncodedValue.getValue());
/*    */   }
/*    */   public double getValue() {
/* 51 */     return this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.value.ImmutableDoubleEncodedValue
 * JD-Core Version:    0.6.0
 */