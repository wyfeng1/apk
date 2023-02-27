/*    */ package org.jf.dexlib2.immutable.value;
/*    */ 
/*    */ import org.jf.dexlib2.base.value.BaseFloatEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.FloatEncodedValue;
/*    */ 
/*    */ public class ImmutableFloatEncodedValue extends BaseFloatEncodedValue
/*    */   implements ImmutableEncodedValue
/*    */ {
/*    */   protected final float value;
/*    */ 
/*    */   public ImmutableFloatEncodedValue(float value)
/*    */   {
/* 41 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public static ImmutableFloatEncodedValue of(FloatEncodedValue floatEncodedValue) {
/* 45 */     if ((floatEncodedValue instanceof ImmutableFloatEncodedValue)) {
/* 46 */       return (ImmutableFloatEncodedValue)floatEncodedValue;
/*    */     }
/* 48 */     return new ImmutableFloatEncodedValue(floatEncodedValue.getValue());
/*    */   }
/*    */   public float getValue() {
/* 51 */     return this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.value.ImmutableFloatEncodedValue
 * JD-Core Version:    0.6.0
 */