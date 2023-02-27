/*    */ package org.jf.dexlib2.immutable.value;
/*    */ 
/*    */ import org.jf.dexlib2.base.value.BaseIntEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.IntEncodedValue;
/*    */ 
/*    */ public class ImmutableIntEncodedValue extends BaseIntEncodedValue
/*    */   implements ImmutableEncodedValue
/*    */ {
/*    */   protected final int value;
/*    */ 
/*    */   public ImmutableIntEncodedValue(int value)
/*    */   {
/* 41 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public static ImmutableIntEncodedValue of(IntEncodedValue intEncodedValue) {
/* 45 */     if ((intEncodedValue instanceof ImmutableIntEncodedValue)) {
/* 46 */       return (ImmutableIntEncodedValue)intEncodedValue;
/*    */     }
/* 48 */     return new ImmutableIntEncodedValue(intEncodedValue.getValue());
/*    */   }
/*    */   public int getValue() {
/* 51 */     return this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.value.ImmutableIntEncodedValue
 * JD-Core Version:    0.6.0
 */