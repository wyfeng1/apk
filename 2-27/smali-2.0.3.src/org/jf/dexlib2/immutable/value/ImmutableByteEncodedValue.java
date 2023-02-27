/*    */ package org.jf.dexlib2.immutable.value;
/*    */ 
/*    */ import org.jf.dexlib2.base.value.BaseByteEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.ByteEncodedValue;
/*    */ 
/*    */ public class ImmutableByteEncodedValue extends BaseByteEncodedValue
/*    */   implements ImmutableEncodedValue
/*    */ {
/*    */   protected final byte value;
/*    */ 
/*    */   public ImmutableByteEncodedValue(byte value)
/*    */   {
/* 41 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public static ImmutableByteEncodedValue of(ByteEncodedValue byteEncodedValue) {
/* 45 */     if ((byteEncodedValue instanceof ImmutableByteEncodedValue)) {
/* 46 */       return (ImmutableByteEncodedValue)byteEncodedValue;
/*    */     }
/* 48 */     return new ImmutableByteEncodedValue(byteEncodedValue.getValue());
/*    */   }
/*    */   public byte getValue() {
/* 51 */     return this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.value.ImmutableByteEncodedValue
 * JD-Core Version:    0.6.0
 */