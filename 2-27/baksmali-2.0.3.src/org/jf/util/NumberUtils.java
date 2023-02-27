/*     */ package org.jf.util;
/*     */ 
/*     */ import java.text.DecimalFormat;
/*     */ 
/*     */ public class NumberUtils
/*     */ {
/*  37 */   private static final int canonicalFloatNaN = Float.floatToRawIntBits((0.0F / 0.0F));
/*  38 */   private static final int maxFloat = Float.floatToRawIntBits(3.4028235E+38F);
/*  39 */   private static final int piFloat = Float.floatToRawIntBits(3.141593F);
/*  40 */   private static final int eFloat = Float.floatToRawIntBits(2.718282F);
/*     */ 
/*  42 */   private static final long canonicalDoubleNaN = Double.doubleToRawLongBits((0.0D / 0.0D));
/*  43 */   private static final long maxDouble = Double.doubleToLongBits(1.7976931348623157E+308D);
/*  44 */   private static final long piDouble = Double.doubleToLongBits(3.141592653589793D);
/*  45 */   private static final long eDouble = Double.doubleToLongBits(2.718281828459045D);
/*     */ 
/*  47 */   private static final DecimalFormat format = new DecimalFormat("0.####################E0");
/*     */ 
/*     */   public static boolean isLikelyFloat(int value)
/*     */   {
/*  52 */     if ((value == canonicalFloatNaN) || (value == maxFloat) || (value == piFloat) || (value == eFloat))
/*     */     {
/*  56 */       return true;
/*     */     }
/*     */ 
/*  60 */     if ((value == 2147483647) || (value == -2147483648)) {
/*  61 */       return false;
/*     */     }
/*     */ 
/*  66 */     int packageId = value >> 24;
/*  67 */     int resourceType = value >> 16 & 0xFF;
/*  68 */     int resourceId = value & 0xFFFF;
/*  69 */     if (((packageId == 127) || (packageId == 1)) && (resourceType < 31) && (resourceId < 4095)) {
/*  70 */       return false;
/*     */     }
/*     */ 
/*  74 */     float floatValue = Float.intBitsToFloat(value);
/*  75 */     if (Float.isNaN(floatValue)) {
/*  76 */       return false;
/*     */     }
/*     */ 
/*  81 */     String asInt = format.format(value);
/*  82 */     String asFloat = format.format(floatValue);
/*     */ 
/*  85 */     int decimalPoint = asFloat.indexOf('.');
/*  86 */     int exponent = asFloat.indexOf("E");
/*  87 */     int zeros = asFloat.indexOf("000");
/*  88 */     if ((zeros > decimalPoint) && (zeros < exponent)) {
/*  89 */       asFloat = asFloat.substring(0, zeros) + asFloat.substring(exponent);
/*     */     } else {
/*  91 */       int nines = asFloat.indexOf("999");
/*  92 */       if ((nines > decimalPoint) && (nines < exponent)) {
/*  93 */         asFloat = asFloat.substring(0, nines) + asFloat.substring(exponent);
/*     */       }
/*     */     }
/*     */ 
/*  97 */     return asFloat.length() < asInt.length();
/*     */   }
/*     */ 
/*     */   public static boolean isLikelyDouble(long value)
/*     */   {
/* 103 */     if ((value == canonicalDoubleNaN) || (value == maxDouble) || (value == piDouble) || (value == eDouble))
/*     */     {
/* 107 */       return true;
/*     */     }
/*     */ 
/* 111 */     if ((value == 9223372036854775807L) || (value == -9223372036854775808L)) {
/* 112 */       return false;
/*     */     }
/*     */ 
/* 116 */     double doubleValue = Double.longBitsToDouble(value);
/* 117 */     if (Double.isNaN(doubleValue)) {
/* 118 */       return false;
/*     */     }
/*     */ 
/* 123 */     String asLong = format.format(value);
/* 124 */     String asDouble = format.format(doubleValue);
/*     */ 
/* 127 */     int decimalPoint = asDouble.indexOf('.');
/* 128 */     int exponent = asDouble.indexOf("E");
/* 129 */     int zeros = asDouble.indexOf("000");
/* 130 */     if ((zeros > decimalPoint) && (zeros < exponent)) {
/* 131 */       asDouble = asDouble.substring(0, zeros) + asDouble.substring(exponent);
/*     */     } else {
/* 133 */       int nines = asDouble.indexOf("999");
/* 134 */       if ((nines > decimalPoint) && (nines < exponent)) {
/* 135 */         asDouble = asDouble.substring(0, nines) + asDouble.substring(exponent);
/*     */       }
/*     */     }
/*     */ 
/* 139 */     return asDouble.length() < asLong.length();
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.util.NumberUtils
 * JD-Core Version:    0.6.0
 */