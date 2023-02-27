/*    */ package org.jf.dexlib2.immutable.value;
/*    */ 
/*    */ import org.jf.dexlib2.base.value.BaseStringEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.StringEncodedValue;
/*    */ 
/*    */ public class ImmutableStringEncodedValue extends BaseStringEncodedValue
/*    */   implements ImmutableEncodedValue
/*    */ {
/*    */   protected final String value;
/*    */ 
/*    */   public ImmutableStringEncodedValue(String value)
/*    */   {
/* 43 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public static ImmutableStringEncodedValue of(StringEncodedValue stringEncodedValue) {
/* 47 */     if ((stringEncodedValue instanceof ImmutableStringEncodedValue)) {
/* 48 */       return (ImmutableStringEncodedValue)stringEncodedValue;
/*    */     }
/* 50 */     return new ImmutableStringEncodedValue(stringEncodedValue.getValue());
/*    */   }
/*    */   public String getValue() {
/* 53 */     return this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.value.ImmutableStringEncodedValue
 * JD-Core Version:    0.6.0
 */