/*     */ package org.jf.dexlib2.analysis;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ public class RegisterType
/*     */ {
/*     */   public final byte category;
/*     */   public final TypeProto type;
/*     */   public static final String[] CATEGORY_NAMES;
/*     */   protected static byte[][] mergeTable;
/*     */   public static final RegisterType UNKNOWN_TYPE;
/*     */   public static final RegisterType UNINIT_TYPE;
/*     */   public static final RegisterType NULL_TYPE;
/*     */   public static final RegisterType ONE_TYPE;
/*     */   public static final RegisterType BOOLEAN_TYPE;
/*     */   public static final RegisterType BYTE_TYPE;
/*     */   public static final RegisterType POS_BYTE_TYPE;
/*     */   public static final RegisterType SHORT_TYPE;
/*     */   public static final RegisterType POS_SHORT_TYPE;
/*     */   public static final RegisterType CHAR_TYPE;
/*     */   public static final RegisterType INTEGER_TYPE;
/*     */   public static final RegisterType FLOAT_TYPE;
/*     */   public static final RegisterType LONG_LO_TYPE;
/*     */   public static final RegisterType LONG_HI_TYPE;
/*     */   public static final RegisterType DOUBLE_LO_TYPE;
/*     */   public static final RegisterType DOUBLE_HI_TYPE;
/*     */   public static final RegisterType CONFLICTED_TYPE;
/*     */ 
/*     */   private RegisterType(byte category, TypeProto type)
/*     */   {
/*  47 */     assert (((category == 18) || (category == 16) || (category == 17)) && ((type != null) || ((category != 18) && (category != 16) && (category != 17) && (type == null))));
/*     */ 
/*  50 */     this.category = category;
/*  51 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  56 */     return "(" + CATEGORY_NAMES[this.category] + (this.type == null ? "" : new StringBuilder().append(",").append(this.type).toString()) + ")";
/*     */   }
/*     */ 
/*     */   public void writeTo(Writer writer) throws IOException {
/*  60 */     writer.write(40);
/*  61 */     writer.write(CATEGORY_NAMES[this.category]);
/*  62 */     if (this.type != null) {
/*  63 */       writer.write(44);
/*  64 */       writer.write(this.type.getType());
/*     */     }
/*  66 */     writer.write(41);
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/*  71 */     if (this == o) return true;
/*  72 */     if ((o == null) || (getClass() != o.getClass())) return false;
/*     */ 
/*  74 */     RegisterType that = (RegisterType)o;
/*     */ 
/*  76 */     if (this.category != that.category) {
/*  77 */       return false;
/*     */     }
/*     */ 
/*  82 */     if ((this.category == 16) || (this.category == 17)) {
/*  83 */       return false;
/*     */     }
/*  85 */     return that.type == null ? true : this.type != null ? this.type.equals(that.type) : false;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  90 */     int result = this.category;
/*  91 */     result = 31 * result + (this.type != null ? this.type.hashCode() : 0);
/*  92 */     return result;
/*     */   }
/*     */ 
/*     */   public static RegisterType getWideRegisterType(CharSequence type, boolean firstRegister)
/*     */   {
/* 197 */     switch (type.charAt(0)) {
/*     */     case 'J':
/* 199 */       if (firstRegister) {
/* 200 */         return getRegisterType(12, null);
/*     */       }
/* 202 */       return getRegisterType(13, null);
/*     */     case 'D':
/* 205 */       if (firstRegister) {
/* 206 */         return getRegisterType(14, null);
/*     */       }
/* 208 */       return getRegisterType(15, null);
/*     */     }
/*     */ 
/* 211 */     throw new ExceptionWithContext("Cannot use this method for narrow register type: %s", new Object[] { type });
/*     */   }
/*     */ 
/*     */   public static RegisterType getRegisterType(ClassPath classPath, CharSequence type)
/*     */   {
/* 217 */     switch (type.charAt(0)) {
/*     */     case 'Z':
/* 219 */       return BOOLEAN_TYPE;
/*     */     case 'B':
/* 221 */       return BYTE_TYPE;
/*     */     case 'S':
/* 223 */       return SHORT_TYPE;
/*     */     case 'C':
/* 225 */       return CHAR_TYPE;
/*     */     case 'I':
/* 227 */       return INTEGER_TYPE;
/*     */     case 'F':
/* 229 */       return FLOAT_TYPE;
/*     */     case 'J':
/* 231 */       return LONG_LO_TYPE;
/*     */     case 'D':
/* 233 */       return DOUBLE_LO_TYPE;
/*     */     case 'L':
/*     */     case '[':
/* 236 */       return getRegisterType(18, classPath.getClass(type));
/*     */     case 'E':
/*     */     case 'G':
/*     */     case 'H':
/*     */     case 'K':
/*     */     case 'M':
/*     */     case 'N':
/*     */     case 'O':
/*     */     case 'P':
/*     */     case 'Q':
/*     */     case 'R':
/*     */     case 'T':
/*     */     case 'U':
/*     */     case 'V':
/*     */     case 'W':
/*     */     case 'X':
/* 238 */     case 'Y': } throw new ExceptionWithContext("Invalid type: " + type, new Object[0]);
/*     */   }
/*     */ 
/*     */   public static RegisterType getRegisterTypeForLiteral(int literalValue)
/*     */   {
/* 244 */     if (literalValue < -32768) {
/* 245 */       return INTEGER_TYPE;
/*     */     }
/* 247 */     if (literalValue < -128) {
/* 248 */       return SHORT_TYPE;
/*     */     }
/* 250 */     if (literalValue < 0) {
/* 251 */       return BYTE_TYPE;
/*     */     }
/* 253 */     if (literalValue == 0) {
/* 254 */       return NULL_TYPE;
/*     */     }
/* 256 */     if (literalValue == 1) {
/* 257 */       return ONE_TYPE;
/*     */     }
/* 259 */     if (literalValue < 128) {
/* 260 */       return POS_BYTE_TYPE;
/*     */     }
/* 262 */     if (literalValue < 32768) {
/* 263 */       return POS_SHORT_TYPE;
/*     */     }
/* 265 */     if (literalValue < 65536) {
/* 266 */       return CHAR_TYPE;
/*     */     }
/* 268 */     return INTEGER_TYPE;
/*     */   }
/*     */ 
/*     */   public RegisterType merge(RegisterType other)
/*     */   {
/* 273 */     if (other.equals(this)) {
/* 274 */       return this;
/*     */     }
/*     */ 
/* 277 */     byte mergedCategory = mergeTable[this.category][other.category];
/*     */ 
/* 279 */     TypeProto mergedType = null;
/* 280 */     if (mergedCategory == 18) {
/* 281 */       TypeProto type = this.type;
/* 282 */       if (type != null) {
/* 283 */         if (other.type != null)
/* 284 */           mergedType = type.getCommonSuperclass(other.type);
/*     */         else
/* 286 */           mergedType = type;
/*     */       }
/*     */       else
/* 289 */         mergedType = other.type;
/*     */     }
/* 291 */     else if ((mergedCategory == 16) || (mergedCategory == 17)) {
/* 292 */       if (this.category == 0) {
/* 293 */         return other;
/*     */       }
/* 295 */       assert (other.category == 0);
/* 296 */       return this;
/*     */     }
/*     */ 
/* 299 */     if (mergedType != null) {
/* 300 */       if (mergedType.equals(this.type)) {
/* 301 */         return this;
/*     */       }
/* 303 */       if (mergedType.equals(other.type)) {
/* 304 */         return other;
/*     */       }
/*     */     }
/* 307 */     return getRegisterType(mergedCategory, mergedType);
/*     */   }
/*     */ 
/*     */   public static RegisterType getRegisterType(byte category, TypeProto typeProto)
/*     */   {
/* 312 */     switch (category) {
/*     */     case 0:
/* 314 */       return UNKNOWN_TYPE;
/*     */     case 1:
/* 316 */       return UNINIT_TYPE;
/*     */     case 2:
/* 318 */       return NULL_TYPE;
/*     */     case 3:
/* 320 */       return ONE_TYPE;
/*     */     case 4:
/* 322 */       return BOOLEAN_TYPE;
/*     */     case 5:
/* 324 */       return BYTE_TYPE;
/*     */     case 6:
/* 326 */       return POS_BYTE_TYPE;
/*     */     case 7:
/* 328 */       return SHORT_TYPE;
/*     */     case 8:
/* 330 */       return POS_SHORT_TYPE;
/*     */     case 9:
/* 332 */       return CHAR_TYPE;
/*     */     case 10:
/* 334 */       return INTEGER_TYPE;
/*     */     case 11:
/* 336 */       return FLOAT_TYPE;
/*     */     case 12:
/* 338 */       return LONG_LO_TYPE;
/*     */     case 13:
/* 340 */       return LONG_HI_TYPE;
/*     */     case 14:
/* 342 */       return DOUBLE_LO_TYPE;
/*     */     case 15:
/* 344 */       return DOUBLE_HI_TYPE;
/*     */     case 19:
/* 346 */       return CONFLICTED_TYPE;
/*     */     case 16:
/*     */     case 17:
/* 349 */     case 18: } return new RegisterType(category, typeProto);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 125 */     CATEGORY_NAMES = new String[] { "Unknown", "Uninit", "Null", "One", "Boolean", "Byte", "PosByte", "Short", "PosShort", "Char", "Integer", "Float", "LongLo", "LongHi", "DoubleLo", "DoubleHi", "UninitRef", "UninitThis", "Reference", "Conflicted" };
/*     */ 
/* 151 */     mergeTable = new byte[][] { { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19 }, { 1, 1, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19 }, { 2, 19, 2, 4, 4, 5, 6, 7, 8, 9, 10, 11, 19, 19, 19, 19, 19, 19, 18, 19 }, { 3, 19, 4, 3, 4, 5, 6, 7, 8, 9, 10, 11, 19, 19, 19, 19, 19, 19, 19, 19 }, { 4, 19, 4, 4, 4, 5, 6, 7, 8, 9, 10, 11, 19, 19, 19, 19, 19, 19, 19, 19 }, { 5, 19, 5, 5, 5, 5, 5, 7, 7, 10, 10, 11, 19, 19, 19, 19, 19, 19, 19, 19 }, { 6, 19, 6, 6, 6, 5, 6, 7, 8, 9, 10, 11, 19, 19, 19, 19, 19, 19, 19, 19 }, { 7, 19, 7, 7, 7, 7, 7, 7, 7, 10, 10, 11, 19, 19, 19, 19, 19, 19, 19, 19 }, { 8, 19, 8, 8, 8, 7, 8, 7, 8, 9, 10, 11, 19, 19, 19, 19, 19, 19, 19, 19 }, { 9, 19, 9, 9, 9, 10, 9, 10, 9, 9, 10, 11, 19, 19, 19, 19, 19, 19, 19, 19 }, { 10, 19, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 19, 19, 19, 19, 19, 19, 19, 19 }, { 11, 19, 11, 11, 11, 11, 11, 11, 11, 11, 10, 11, 19, 19, 19, 19, 19, 19, 19, 19 }, { 12, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 12, 19, 12, 19, 19, 19, 19, 19 }, { 13, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 13, 19, 13, 19, 19, 19, 19 }, { 14, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 12, 19, 14, 19, 19, 19, 19, 19 }, { 15, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 13, 19, 15, 19, 19, 19, 19 }, { 16, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19 }, { 17, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 17, 19, 19 }, { 18, 19, 18, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 18, 19 }, { 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19, 19 } };
/*     */ 
/* 177 */     UNKNOWN_TYPE = new RegisterType(0, null);
/* 178 */     UNINIT_TYPE = new RegisterType(1, null);
/* 179 */     NULL_TYPE = new RegisterType(2, null);
/* 180 */     ONE_TYPE = new RegisterType(3, null);
/* 181 */     BOOLEAN_TYPE = new RegisterType(4, null);
/* 182 */     BYTE_TYPE = new RegisterType(5, null);
/* 183 */     POS_BYTE_TYPE = new RegisterType(6, null);
/* 184 */     SHORT_TYPE = new RegisterType(7, null);
/* 185 */     POS_SHORT_TYPE = new RegisterType(8, null);
/* 186 */     CHAR_TYPE = new RegisterType(9, null);
/* 187 */     INTEGER_TYPE = new RegisterType(10, null);
/* 188 */     FLOAT_TYPE = new RegisterType(11, null);
/* 189 */     LONG_LO_TYPE = new RegisterType(12, null);
/* 190 */     LONG_HI_TYPE = new RegisterType(13, null);
/* 191 */     DOUBLE_LO_TYPE = new RegisterType(14, null);
/* 192 */     DOUBLE_HI_TYPE = new RegisterType(15, null);
/* 193 */     CONFLICTED_TYPE = new RegisterType(19, null);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.analysis.RegisterType
 * JD-Core Version:    0.6.0
 */