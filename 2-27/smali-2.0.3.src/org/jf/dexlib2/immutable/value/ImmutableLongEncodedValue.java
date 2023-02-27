/*    */ package org.jf.dexlib2.immutable.value;
/*    */ 
/*    */ import org.jf.dexlib2.base.value.BaseLongEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.LongEncodedValue;
/*    */ 
/*    */ public class ImmutableLongEncodedValue extends BaseLongEncodedValue
/*    */   implements ImmutableEncodedValue
/*    */ {
/*    */   protected final long value;
/*    */ 
/*    */   public ImmutableLongEncodedValue(long value)
/*    */   {
/* 41 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public static ImmutableLongEncodedValue of(LongEncodedValue longEncodedValue) {
/* 45 */     if ((longEncodedValue instanceof ImmutableLongEncodedValue)) {
/* 46 */       return (ImmutableLongEncodedValue)longEncodedValue;
/*    */     }
/* 48 */     return new ImmutableLongEncodedValue(longEncodedValue.getValue());
/*    */   }
/*    */   public long getValue() {
/* 51 */     return this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.value.ImmutableLongEncodedValue
 * JD-Core Version:    0.6.0
 */