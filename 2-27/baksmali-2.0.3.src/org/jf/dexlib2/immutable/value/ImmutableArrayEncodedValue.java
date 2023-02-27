/*    */ package org.jf.dexlib2.immutable.value;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.Collection;
/*    */ import org.jf.dexlib2.base.value.BaseArrayEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.ArrayEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ 
/*    */ public class ImmutableArrayEncodedValue extends BaseArrayEncodedValue
/*    */   implements ImmutableEncodedValue
/*    */ {
/*    */   protected final ImmutableList<? extends ImmutableEncodedValue> value;
/*    */ 
/*    */   public ImmutableArrayEncodedValue(Collection<? extends EncodedValue> value)
/*    */   {
/* 46 */     this.value = ImmutableEncodedValueFactory.immutableListOf(value);
/*    */   }
/*    */ 
/*    */   public static ImmutableArrayEncodedValue of(ArrayEncodedValue arrayEncodedValue)
/*    */   {
/* 54 */     if ((arrayEncodedValue instanceof ImmutableArrayEncodedValue)) {
/* 55 */       return (ImmutableArrayEncodedValue)arrayEncodedValue;
/*    */     }
/* 57 */     return new ImmutableArrayEncodedValue(arrayEncodedValue.getValue());
/*    */   }
/*    */   public ImmutableList<? extends ImmutableEncodedValue> getValue() {
/* 60 */     return this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.value.ImmutableArrayEncodedValue
 * JD-Core Version:    0.6.0
 */