/*    */ package org.jf.dexlib2.immutable.value;
/*    */ 
/*    */ import org.jf.dexlib2.base.value.BaseTypeEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.TypeEncodedValue;
/*    */ 
/*    */ public class ImmutableTypeEncodedValue extends BaseTypeEncodedValue
/*    */   implements ImmutableEncodedValue
/*    */ {
/*    */   protected final String value;
/*    */ 
/*    */   public ImmutableTypeEncodedValue(String value)
/*    */   {
/* 43 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public static ImmutableTypeEncodedValue of(TypeEncodedValue typeEncodedValue) {
/* 47 */     if ((typeEncodedValue instanceof ImmutableTypeEncodedValue)) {
/* 48 */       return (ImmutableTypeEncodedValue)typeEncodedValue;
/*    */     }
/* 50 */     return new ImmutableTypeEncodedValue(typeEncodedValue.getValue());
/*    */   }
/*    */   public String getValue() {
/* 53 */     return this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.value.ImmutableTypeEncodedValue
 * JD-Core Version:    0.6.0
 */