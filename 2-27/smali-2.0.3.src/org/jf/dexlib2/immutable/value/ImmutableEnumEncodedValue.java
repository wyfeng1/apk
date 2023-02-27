/*    */ package org.jf.dexlib2.immutable.value;
/*    */ 
/*    */ import org.jf.dexlib2.base.value.BaseEnumEncodedValue;
/*    */ import org.jf.dexlib2.iface.reference.FieldReference;
/*    */ import org.jf.dexlib2.iface.value.EnumEncodedValue;
/*    */ 
/*    */ public class ImmutableEnumEncodedValue extends BaseEnumEncodedValue
/*    */   implements ImmutableEncodedValue
/*    */ {
/*    */   protected final FieldReference value;
/*    */ 
/*    */   public ImmutableEnumEncodedValue(FieldReference value)
/*    */   {
/* 44 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public static ImmutableEnumEncodedValue of(EnumEncodedValue enumEncodedValue) {
/* 48 */     if ((enumEncodedValue instanceof ImmutableEnumEncodedValue)) {
/* 49 */       return (ImmutableEnumEncodedValue)enumEncodedValue;
/*    */     }
/* 51 */     return new ImmutableEnumEncodedValue(enumEncodedValue.getValue());
/*    */   }
/*    */   public FieldReference getValue() {
/* 54 */     return this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.value.ImmutableEnumEncodedValue
 * JD-Core Version:    0.6.0
 */