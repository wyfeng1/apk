/*    */ package org.jf.dexlib2.immutable.value;
/*    */ 
/*    */ import org.jf.dexlib2.base.value.BaseMethodEncodedValue;
/*    */ import org.jf.dexlib2.iface.reference.MethodReference;
/*    */ import org.jf.dexlib2.iface.value.MethodEncodedValue;
/*    */ 
/*    */ public class ImmutableMethodEncodedValue extends BaseMethodEncodedValue
/*    */   implements ImmutableEncodedValue
/*    */ {
/*    */   protected final MethodReference value;
/*    */ 
/*    */   public ImmutableMethodEncodedValue(MethodReference value)
/*    */   {
/* 44 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public static ImmutableMethodEncodedValue of(MethodEncodedValue methodEncodedValue) {
/* 48 */     if ((methodEncodedValue instanceof ImmutableMethodEncodedValue)) {
/* 49 */       return (ImmutableMethodEncodedValue)methodEncodedValue;
/*    */     }
/* 51 */     return new ImmutableMethodEncodedValue(methodEncodedValue.getValue());
/*    */   }
/*    */   public MethodReference getValue() {
/* 54 */     return this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.value.ImmutableMethodEncodedValue
 * JD-Core Version:    0.6.0
 */