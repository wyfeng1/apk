/*    */ package org.jf.dexlib2.immutable.value;
/*    */ 
/*    */ import org.jf.dexlib2.base.value.BaseShortEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.ShortEncodedValue;
/*    */ 
/*    */ public class ImmutableShortEncodedValue extends BaseShortEncodedValue
/*    */   implements ImmutableEncodedValue
/*    */ {
/*    */   protected final short value;
/*    */ 
/*    */   public ImmutableShortEncodedValue(short value)
/*    */   {
/* 41 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public static ImmutableShortEncodedValue of(ShortEncodedValue shortEncodedValue) {
/* 45 */     if ((shortEncodedValue instanceof ImmutableShortEncodedValue)) {
/* 46 */       return (ImmutableShortEncodedValue)shortEncodedValue;
/*    */     }
/* 48 */     return new ImmutableShortEncodedValue(shortEncodedValue.getValue());
/*    */   }
/*    */   public short getValue() {
/* 51 */     return this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.value.ImmutableShortEncodedValue
 * JD-Core Version:    0.6.0
 */