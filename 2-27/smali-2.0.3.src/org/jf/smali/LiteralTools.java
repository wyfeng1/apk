/*     */ package org.jf.smali;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class LiteralTools
/*     */ {
/* 310 */   private static Pattern specialFloatRegex = Pattern.compile("((-)?infinityf)|(nanf)", 2);
/*     */ 
/* 328 */   private static Pattern specialDoubleRegex = Pattern.compile("((-)?infinityd?)|(nand?)", 2);
/*     */ 
/*     */   public static byte parseByte(String byteLiteral)
/*     */     throws NumberFormatException
/*     */   {
/*  38 */     if (byteLiteral == null) {
/*  39 */       throw new NumberFormatException("string is null");
/*     */     }
/*  41 */     if (byteLiteral.length() == 0)
/*  42 */       throw new NumberFormatException("string is blank");
/*     */     char[] byteChars;
/*     */     char[] byteChars;
/*  46 */     if (byteLiteral.toUpperCase().endsWith("T"))
/*  47 */       byteChars = byteLiteral.substring(0, byteLiteral.length() - 1).toCharArray();
/*     */     else {
/*  49 */       byteChars = byteLiteral.toCharArray();
/*     */     }
/*     */ 
/*  52 */     int position = 0;
/*  53 */     int radix = 10;
/*  54 */     boolean negative = false;
/*  55 */     if (byteChars[position] == '-') {
/*  56 */       position++;
/*  57 */       negative = true;
/*     */     }
/*     */ 
/*  60 */     if (byteChars[position] == '0') {
/*  61 */       position++;
/*  62 */       if (position == byteChars.length)
/*  63 */         return 0;
/*  64 */       if ((byteChars[position] == 'x') || (byteChars[position] == 'X')) {
/*  65 */         radix = 16;
/*  66 */         position++;
/*  67 */       } else if (Character.digit(byteChars[position], 8) >= 0) {
/*  68 */         radix = 8;
/*     */       }
/*     */     }
/*     */ 
/*  72 */     byte result = 0;
/*     */ 
/*  75 */     byte maxValue = (byte)(127 / (radix / 2));
/*     */ 
/*  77 */     while (position < byteChars.length) {
/*  78 */       int digit = Character.digit(byteChars[position], radix);
/*  79 */       if (digit < 0) {
/*  80 */         throw new NumberFormatException("The string contains invalid an digit - '" + byteChars[position] + "'");
/*     */       }
/*  82 */       byte shiftedResult = (byte)(result * radix);
/*  83 */       if (result > maxValue) {
/*  84 */         throw new NumberFormatException(byteLiteral + " cannot fit into a byte");
/*     */       }
/*  86 */       if ((shiftedResult < 0) && (shiftedResult >= -digit)) {
/*  87 */         throw new NumberFormatException(byteLiteral + " cannot fit into a byte");
/*     */       }
/*  89 */       result = (byte)(shiftedResult + digit);
/*  90 */       position++;
/*     */     }
/*     */ 
/*  93 */     if (negative)
/*     */     {
/*  95 */       if (result == -128)
/*  96 */         return result;
/*  97 */       if (result < 0) {
/*  98 */         throw new NumberFormatException(byteLiteral + " cannot fit into a byte");
/*     */       }
/* 100 */       return (byte)(result * -1);
/*     */     }
/* 102 */     return result;
/*     */   }
/*     */ 
/*     */   public static short parseShort(String shortLiteral)
/*     */     throws NumberFormatException
/*     */   {
/* 108 */     if (shortLiteral == null) {
/* 109 */       throw new NumberFormatException("string is null");
/*     */     }
/* 111 */     if (shortLiteral.length() == 0)
/* 112 */       throw new NumberFormatException("string is blank");
/*     */     char[] shortChars;
/*     */     char[] shortChars;
/* 116 */     if (shortLiteral.toUpperCase().endsWith("S"))
/* 117 */       shortChars = shortLiteral.substring(0, shortLiteral.length() - 1).toCharArray();
/*     */     else {
/* 119 */       shortChars = shortLiteral.toCharArray();
/*     */     }
/*     */ 
/* 122 */     int position = 0;
/* 123 */     int radix = 10;
/* 124 */     boolean negative = false;
/* 125 */     if (shortChars[position] == '-') {
/* 126 */       position++;
/* 127 */       negative = true;
/*     */     }
/*     */ 
/* 130 */     if (shortChars[position] == '0') {
/* 131 */       position++;
/* 132 */       if (position == shortChars.length)
/* 133 */         return 0;
/* 134 */       if ((shortChars[position] == 'x') || (shortChars[position] == 'X')) {
/* 135 */         radix = 16;
/* 136 */         position++;
/* 137 */       } else if (Character.digit(shortChars[position], 8) >= 0) {
/* 138 */         radix = 8;
/*     */       }
/*     */     }
/*     */ 
/* 142 */     short result = 0;
/*     */ 
/* 145 */     short maxValue = (short)(32767 / (radix / 2));
/*     */ 
/* 147 */     while (position < shortChars.length) {
/* 148 */       int digit = Character.digit(shortChars[position], radix);
/* 149 */       if (digit < 0) {
/* 150 */         throw new NumberFormatException("The string contains invalid an digit - '" + shortChars[position] + "'");
/*     */       }
/* 152 */       short shiftedResult = (short)(result * radix);
/* 153 */       if (result > maxValue) {
/* 154 */         throw new NumberFormatException(shortLiteral + " cannot fit into a short");
/*     */       }
/* 156 */       if ((shiftedResult < 0) && (shiftedResult >= -digit)) {
/* 157 */         throw new NumberFormatException(shortLiteral + " cannot fit into a short");
/*     */       }
/* 159 */       result = (short)(shiftedResult + digit);
/* 160 */       position++;
/*     */     }
/*     */ 
/* 163 */     if (negative)
/*     */     {
/* 165 */       if (result == -32768)
/* 166 */         return result;
/* 167 */       if (result < 0) {
/* 168 */         throw new NumberFormatException(shortLiteral + " cannot fit into a short");
/*     */       }
/* 170 */       return (short)(result * -1);
/*     */     }
/* 172 */     return result;
/*     */   }
/*     */ 
/*     */   public static int parseInt(String intLiteral)
/*     */     throws NumberFormatException
/*     */   {
/* 178 */     if (intLiteral == null) {
/* 179 */       throw new NumberFormatException("string is null");
/*     */     }
/* 181 */     if (intLiteral.length() == 0) {
/* 182 */       throw new NumberFormatException("string is blank");
/*     */     }
/*     */ 
/* 185 */     char[] intChars = intLiteral.toCharArray();
/* 186 */     int position = 0;
/* 187 */     int radix = 10;
/* 188 */     boolean negative = false;
/* 189 */     if (intChars[position] == '-') {
/* 190 */       position++;
/* 191 */       negative = true;
/*     */     }
/*     */ 
/* 194 */     if (intChars[position] == '0') {
/* 195 */       position++;
/* 196 */       if (position == intChars.length)
/* 197 */         return 0;
/* 198 */       if ((intChars[position] == 'x') || (intChars[position] == 'X')) {
/* 199 */         radix = 16;
/* 200 */         position++;
/* 201 */       } else if (Character.digit(intChars[position], 8) >= 0) {
/* 202 */         radix = 8;
/*     */       }
/*     */     }
/*     */ 
/* 206 */     int result = 0;
/*     */ 
/* 209 */     int maxValue = 2147483647 / (radix / 2);
/*     */ 
/* 211 */     while (position < intChars.length) {
/* 212 */       int digit = Character.digit(intChars[position], radix);
/* 213 */       if (digit < 0) {
/* 214 */         throw new NumberFormatException("The string contains an invalid digit - '" + intChars[position] + "'");
/*     */       }
/* 216 */       int shiftedResult = result * radix;
/* 217 */       if (result > maxValue) {
/* 218 */         throw new NumberFormatException(intLiteral + " cannot fit into an int");
/*     */       }
/* 220 */       if ((shiftedResult < 0) && (shiftedResult >= -digit)) {
/* 221 */         throw new NumberFormatException(intLiteral + " cannot fit into an int");
/*     */       }
/* 223 */       result = shiftedResult + digit;
/* 224 */       position++;
/*     */     }
/*     */ 
/* 227 */     if (negative)
/*     */     {
/* 229 */       if (result == -2147483648)
/* 230 */         return result;
/* 231 */       if (result < 0) {
/* 232 */         throw new NumberFormatException(intLiteral + " cannot fit into an int");
/*     */       }
/* 234 */       return result * -1;
/*     */     }
/* 236 */     return result;
/*     */   }
/*     */ 
/*     */   public static long parseLong(String longLiteral)
/*     */     throws NumberFormatException
/*     */   {
/* 242 */     if (longLiteral == null) {
/* 243 */       throw new NumberFormatException("string is null");
/*     */     }
/* 245 */     if (longLiteral.length() == 0)
/* 246 */       throw new NumberFormatException("string is blank");
/*     */     char[] longChars;
/*     */     char[] longChars;
/* 250 */     if (longLiteral.toUpperCase().endsWith("L"))
/* 251 */       longChars = longLiteral.substring(0, longLiteral.length() - 1).toCharArray();
/*     */     else {
/* 253 */       longChars = longLiteral.toCharArray();
/*     */     }
/*     */ 
/* 256 */     int position = 0;
/* 257 */     int radix = 10;
/* 258 */     boolean negative = false;
/* 259 */     if (longChars[position] == '-') {
/* 260 */       position++;
/* 261 */       negative = true;
/*     */     }
/*     */ 
/* 264 */     if (longChars[position] == '0') {
/* 265 */       position++;
/* 266 */       if (position == longChars.length)
/* 267 */         return 0L;
/* 268 */       if ((longChars[position] == 'x') || (longChars[position] == 'X')) {
/* 269 */         radix = 16;
/* 270 */         position++;
/* 271 */       } else if (Character.digit(longChars[position], 8) >= 0) {
/* 272 */         radix = 8;
/*     */       }
/*     */     }
/*     */ 
/* 276 */     long result = 0L;
/*     */ 
/* 279 */     long maxValue = 9223372036854775807L / (radix / 2);
/*     */ 
/* 281 */     while (position < longChars.length) {
/* 282 */       int digit = Character.digit(longChars[position], radix);
/* 283 */       if (digit < 0) {
/* 284 */         throw new NumberFormatException("The string contains an invalid digit - '" + longChars[position] + "'");
/*     */       }
/* 286 */       long shiftedResult = result * radix;
/* 287 */       if (result > maxValue) {
/* 288 */         throw new NumberFormatException(longLiteral + " cannot fit into a long");
/*     */       }
/* 290 */       if ((shiftedResult < 0L) && (shiftedResult >= -digit)) {
/* 291 */         throw new NumberFormatException(longLiteral + " cannot fit into a long");
/*     */       }
/* 293 */       result = shiftedResult + digit;
/* 294 */       position++;
/*     */     }
/*     */ 
/* 297 */     if (negative)
/*     */     {
/* 299 */       if (result == -9223372036854775808L)
/* 300 */         return result;
/* 301 */       if (result < 0L) {
/* 302 */         throw new NumberFormatException(longLiteral + " cannot fit into a long");
/*     */       }
/* 304 */       return result * -1L;
/*     */     }
/* 306 */     return result;
/*     */   }
/*     */ 
/*     */   public static float parseFloat(String floatString)
/*     */   {
/* 312 */     Matcher m = specialFloatRegex.matcher(floatString);
/* 313 */     if (m.matches())
/*     */     {
/* 315 */       if (m.start(1) != -1) {
/* 316 */         if (m.start(2) != -1) {
/* 317 */           return (1.0F / -1.0F);
/*     */         }
/* 319 */         return (1.0F / 1.0F);
/*     */       }
/*     */ 
/* 322 */       return (0.0F / 0.0F);
/*     */     }
/*     */ 
/* 325 */     return Float.parseFloat(floatString);
/*     */   }
/*     */ 
/*     */   public static double parseDouble(String doubleString)
/*     */   {
/* 330 */     Matcher m = specialDoubleRegex.matcher(doubleString);
/* 331 */     if (m.matches())
/*     */     {
/* 333 */       if (m.start(1) != -1) {
/* 334 */         if (m.start(2) != -1) {
/* 335 */           return (-1.0D / 0.0D);
/*     */         }
/* 337 */         return (1.0D / 0.0D);
/*     */       }
/*     */ 
/* 340 */       return (0.0D / 0.0D);
/*     */     }
/*     */ 
/* 343 */     return Double.parseDouble(doubleString);
/*     */   }
/*     */ 
/*     */   public static void checkInt(long value)
/*     */   {
/* 395 */     if ((value > -1L) || (value < -2147483648L))
/* 396 */       throw new NumberFormatException(Long.toString(value) + " cannot fit into an int");
/*     */   }
/*     */ 
/*     */   public static void checkShort(long value)
/*     */   {
/* 401 */     if (((value > 65535L ? 1 : 0) | (value < -32768L ? 1 : 0)) != 0)
/* 402 */       throw new NumberFormatException(Long.toString(value) + " cannot fit into a short");
/*     */   }
/*     */ 
/*     */   public static void checkByte(long value)
/*     */   {
/* 407 */     if (((value > 255L ? 1 : 0) | (value < -128L ? 1 : 0)) != 0)
/* 408 */       throw new NumberFormatException(Long.toString(value) + " cannot fit into a byte");
/*     */   }
/*     */ 
/*     */   public static void checkNibble(long value)
/*     */   {
/* 413 */     if (((value > 15L ? 1 : 0) | (value < -8L ? 1 : 0)) != 0)
/* 414 */       throw new NumberFormatException(Long.toString(value) + " cannot fit into a nibble");
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.smali.LiteralTools
 * JD-Core Version:    0.6.0
 */