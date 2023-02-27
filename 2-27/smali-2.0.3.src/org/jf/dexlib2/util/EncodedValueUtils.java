/*    */ package org.jf.dexlib2.util;
/*    */ 
/*    */ import org.jf.dexlib2.iface.value.BooleanEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.ByteEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.CharEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.DoubleEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ import org.jf.dexlib2.iface.value.FloatEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.IntEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.LongEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.ShortEncodedValue;
/*    */ 
/*    */ public final class EncodedValueUtils
/*    */ {
/*    */   public static boolean isDefaultValue(EncodedValue encodedValue)
/*    */   {
/* 39 */     switch (encodedValue.getValueType()) {
/*    */     case 31:
/* 41 */       return !((BooleanEncodedValue)encodedValue).getValue();
/*    */     case 0:
/* 43 */       return ((ByteEncodedValue)encodedValue).getValue() == 0;
/*    */     case 3:
/* 45 */       return ((CharEncodedValue)encodedValue).getValue() == 0;
/*    */     case 17:
/* 47 */       return ((DoubleEncodedValue)encodedValue).getValue() == 0.0D;
/*    */     case 16:
/* 49 */       return ((FloatEncodedValue)encodedValue).getValue() == 0.0F;
/*    */     case 4:
/* 51 */       return ((IntEncodedValue)encodedValue).getValue() == 0;
/*    */     case 6:
/* 53 */       return ((LongEncodedValue)encodedValue).getValue() == 0L;
/*    */     case 30:
/* 55 */       return true;
/*    */     case 2:
/* 57 */       return ((ShortEncodedValue)encodedValue).getValue() == 0;
/*    */     case 1:
/*    */     case 5:
/*    */     case 7:
/*    */     case 8:
/*    */     case 9:
/*    */     case 10:
/*    */     case 11:
/*    */     case 12:
/*    */     case 13:
/*    */     case 14:
/*    */     case 15:
/*    */     case 18:
/*    */     case 19:
/*    */     case 20:
/*    */     case 21:
/*    */     case 22:
/*    */     case 23:
/*    */     case 24:
/*    */     case 25:
/*    */     case 26:
/*    */     case 27:
/*    */     case 28:
/* 59 */     case 29: } return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.util.EncodedValueUtils
 * JD-Core Version:    0.6.0
 */