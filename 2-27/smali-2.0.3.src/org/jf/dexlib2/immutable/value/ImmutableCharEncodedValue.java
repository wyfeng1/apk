/*    */ package org.jf.dexlib2.immutable.value;
/*    */ 
/*    */ import org.jf.dexlib2.base.value.BaseCharEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.CharEncodedValue;
/*    */ 
/*    */ public class ImmutableCharEncodedValue extends BaseCharEncodedValue
/*    */   implements ImmutableEncodedValue
/*    */ {
/*    */   protected final char value;
/*    */ 
/*    */   public ImmutableCharEncodedValue(char value)
/*    */   {
/* 41 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public static ImmutableCharEncodedValue of(CharEncodedValue charEncodedValue) {
/* 45 */     if ((charEncodedValue instanceof ImmutableCharEncodedValue)) {
/* 46 */       return (ImmutableCharEncodedValue)charEncodedValue;
/*    */     }
/* 48 */     return new ImmutableCharEncodedValue(charEncodedValue.getValue());
/*    */   }
/*    */   public char getValue() {
/* 51 */     return this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.value.ImmutableCharEncodedValue
 * JD-Core Version:    0.6.0
 */