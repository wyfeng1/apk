/*    */ package org.jf.dexlib2.immutable.value;
/*    */ 
/*    */ import org.jf.dexlib2.base.value.BaseBooleanEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.BooleanEncodedValue;
/*    */ 
/*    */ public class ImmutableBooleanEncodedValue extends BaseBooleanEncodedValue
/*    */   implements ImmutableEncodedValue
/*    */ {
/* 38 */   public static final ImmutableBooleanEncodedValue TRUE_VALUE = new ImmutableBooleanEncodedValue(true);
/* 39 */   public static final ImmutableBooleanEncodedValue FALSE_VALUE = new ImmutableBooleanEncodedValue(false);
/*    */   protected final boolean value;
/*    */ 
/*    */   private ImmutableBooleanEncodedValue(boolean value)
/*    */   {
/* 44 */     this.value = value;
/*    */   }
/*    */ 
/*    */   public static ImmutableBooleanEncodedValue forBoolean(boolean value) {
/* 48 */     return value ? TRUE_VALUE : FALSE_VALUE;
/*    */   }
/*    */ 
/*    */   public static ImmutableBooleanEncodedValue of(BooleanEncodedValue booleanEncodedValue) {
/* 52 */     return forBoolean(booleanEncodedValue.getValue());
/*    */   }
/*    */   public boolean getValue() {
/* 55 */     return this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.value.ImmutableBooleanEncodedValue
 * JD-Core Version:    0.6.0
 */