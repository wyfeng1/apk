/*    */ package org.jf.dexlib2.immutable.value;
/*    */ 
/*    */ import org.jf.dexlib2.base.value.BaseFieldEncodedValue;
/*    */ import org.jf.dexlib2.iface.reference.FieldReference;
/*    */ import org.jf.dexlib2.iface.value.FieldEncodedValue;
/*    */ 
/*    */ public class ImmutableFieldEncodedValue extends BaseFieldEncodedValue
/*    */   implements ImmutableEncodedValue
/*    */ {
/*    */   protected final FieldReference value;
/*    */ 
/*    */   public ImmutableFieldEncodedValue(FieldReference value)
/*    */   {
/* 44 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public static ImmutableFieldEncodedValue of(FieldEncodedValue fieldEncodedValue) {
/* 48 */     if ((fieldEncodedValue instanceof ImmutableFieldEncodedValue)) {
/* 49 */       return (ImmutableFieldEncodedValue)fieldEncodedValue;
/*    */     }
/* 51 */     return new ImmutableFieldEncodedValue(fieldEncodedValue.getValue());
/*    */   }
/*    */   public FieldReference getValue() {
/* 54 */     return this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.value.ImmutableFieldEncodedValue
 * JD-Core Version:    0.6.0
 */