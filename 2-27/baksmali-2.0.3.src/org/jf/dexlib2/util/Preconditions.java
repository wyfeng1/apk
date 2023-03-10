/*     */ package org.jf.dexlib2.util;
/*     */ 
/*     */ import org.jf.dexlib2.Format;
/*     */ import org.jf.dexlib2.Opcode;
/*     */ import org.jf.dexlib2.VerificationError;
/*     */ 
/*     */ public class Preconditions
/*     */ {
/*     */   public static void checkFormat(Opcode opcode, Format expectedFormat)
/*     */   {
/*  42 */     if (opcode.format != expectedFormat)
/*  43 */       throw new IllegalArgumentException(String.format("Invalid opcode %s for %s", new Object[] { opcode.name, expectedFormat.name() }));
/*     */   }
/*     */ 
/*     */   public static int checkNibbleRegister(int register)
/*     */   {
/*  49 */     if ((register & 0xFFFFFFF0) != 0) {
/*  50 */       throw new IllegalArgumentException(String.format("Invalid register: v%d. Must be between v0 and v15, inclusive.", new Object[] { Integer.valueOf(register) }));
/*     */     }
/*     */ 
/*  53 */     return register;
/*     */   }
/*     */ 
/*     */   public static int checkByteRegister(int register) {
/*  57 */     if ((register & 0xFFFFFF00) != 0) {
/*  58 */       throw new IllegalArgumentException(String.format("Invalid register: v%d. Must be between v0 and v255, inclusive.", new Object[] { Integer.valueOf(register) }));
/*     */     }
/*     */ 
/*  61 */     return register;
/*     */   }
/*     */ 
/*     */   public static int checkShortRegister(int register) {
/*  65 */     if ((register & 0xFFFF0000) != 0) {
/*  66 */       throw new IllegalArgumentException(String.format("Invalid register: v%d. Must be between v0 and v65535, inclusive.", new Object[] { Integer.valueOf(register) }));
/*     */     }
/*     */ 
/*  69 */     return register;
/*     */   }
/*     */ 
/*     */   public static int checkNibbleLiteral(int literal) {
/*  73 */     if ((literal < -8) || (literal > 7)) {
/*  74 */       throw new IllegalArgumentException(String.format("Invalid literal value: %d. Must be between -8 and 7, inclusive.", new Object[] { Integer.valueOf(literal) }));
/*     */     }
/*     */ 
/*  77 */     return literal;
/*     */   }
/*     */ 
/*     */   public static int checkByteLiteral(int literal) {
/*  81 */     if ((literal < -128) || (literal > 127)) {
/*  82 */       throw new IllegalArgumentException(String.format("Invalid literal value: %d. Must be between -128 and 127, inclusive.", new Object[] { Integer.valueOf(literal) }));
/*     */     }
/*     */ 
/*  85 */     return literal;
/*     */   }
/*     */ 
/*     */   public static int checkShortLiteral(int literal) {
/*  89 */     if ((literal < -32768) || (literal > 32767)) {
/*  90 */       throw new IllegalArgumentException(String.format("Invalid literal value: %d. Must be between -32768 and 32767, inclusive.", new Object[] { Integer.valueOf(literal) }));
/*     */     }
/*     */ 
/*  93 */     return literal;
/*     */   }
/*     */ 
/*     */   public static int checkIntegerHatLiteral(int literal) {
/*  97 */     if ((literal & 0xFFFF) != 0) {
/*  98 */       throw new IllegalArgumentException(String.format("Invalid literal value: %d. Low 16 bits must be zeroed out.", new Object[] { Integer.valueOf(literal) }));
/*     */     }
/*     */ 
/* 101 */     return literal;
/*     */   }
/*     */ 
/*     */   public static long checkLongHatLiteral(long literal) {
/* 105 */     if ((literal & 0xFFFFFFFF) != 0L) {
/* 106 */       throw new IllegalArgumentException(String.format("Invalid literal value: %d. Low 48 bits must be zeroed out.", new Object[] { Long.valueOf(literal) }));
/*     */     }
/*     */ 
/* 109 */     return literal;
/*     */   }
/*     */ 
/*     */   public static int checkByteCodeOffset(int offset) {
/* 113 */     if ((offset < -128) || (offset > 127)) {
/* 114 */       throw new IllegalArgumentException(String.format("Invalid code offset: %d. Must be between -128 and 127, inclusive.", new Object[] { Integer.valueOf(offset) }));
/*     */     }
/*     */ 
/* 117 */     return offset;
/*     */   }
/*     */ 
/*     */   public static int checkShortCodeOffset(int offset) {
/* 121 */     if ((offset < -32768) || (offset > 32767)) {
/* 122 */       throw new IllegalArgumentException(String.format("Invalid code offset: %d. Must be between -32768 and 32767, inclusive.", new Object[] { Integer.valueOf(offset) }));
/*     */     }
/*     */ 
/* 125 */     return offset;
/*     */   }
/*     */ 
/*     */   public static int check35cRegisterCount(int registerCount) {
/* 129 */     if ((registerCount < 0) || (registerCount > 5)) {
/* 130 */       throw new IllegalArgumentException(String.format("Invalid register count: %d. Must be between 0 and 5, inclusive.", new Object[] { Integer.valueOf(registerCount) }));
/*     */     }
/*     */ 
/* 133 */     return registerCount;
/*     */   }
/*     */ 
/*     */   public static int checkRegisterRangeCount(int registerCount) {
/* 137 */     if ((registerCount & 0xFFFFFF00) != 0) {
/* 138 */       throw new IllegalArgumentException(String.format("Invalid register count: %d. Must be between 0 and 255, inclusive.", new Object[] { Integer.valueOf(registerCount) }));
/*     */     }
/*     */ 
/* 141 */     return registerCount;
/*     */   }
/*     */ 
/*     */   public static void checkValueArg(int valueArg, int maxValue) {
/* 145 */     if (valueArg > maxValue) {
/* 146 */       if (maxValue == 0) {
/* 147 */         throw new IllegalArgumentException(String.format("Invalid value_arg value %d for an encoded_value. Expecting 0", new Object[] { Integer.valueOf(valueArg) }));
/*     */       }
/*     */ 
/* 151 */       throw new IllegalArgumentException(String.format("Invalid value_arg value %d for an encoded_value. Expecting 0..%d, inclusive", new Object[] { Integer.valueOf(valueArg), Integer.valueOf(maxValue) }));
/*     */     }
/*     */   }
/*     */ 
/*     */   public static int checkFieldOffset(int fieldOffset)
/*     */   {
/* 158 */     if ((fieldOffset < 0) || (fieldOffset > 65535)) {
/* 159 */       throw new IllegalArgumentException(String.format("Invalid field offset: 0x%x. Must be between 0x0000 and 0xFFFF inclusive", new Object[] { Integer.valueOf(fieldOffset) }));
/*     */     }
/*     */ 
/* 163 */     return fieldOffset;
/*     */   }
/*     */ 
/*     */   public static int checkVtableIndex(int vtableIndex) {
/* 167 */     if ((vtableIndex < 0) || (vtableIndex > 65535)) {
/* 168 */       throw new IllegalArgumentException(String.format("Invalid vtable index: %d. Must be between 0 and 65535, inclusive", new Object[] { Integer.valueOf(vtableIndex) }));
/*     */     }
/*     */ 
/* 171 */     return vtableIndex;
/*     */   }
/*     */ 
/*     */   public static int checkInlineIndex(int inlineIndex) {
/* 175 */     if ((inlineIndex < 0) || (inlineIndex > 65535)) {
/* 176 */       throw new IllegalArgumentException(String.format("Invalid inline index: %d. Must be between 0 and 65535, inclusive", new Object[] { Integer.valueOf(inlineIndex) }));
/*     */     }
/*     */ 
/* 179 */     return inlineIndex;
/*     */   }
/*     */ 
/*     */   public static int checkVerificationError(int verificationError) {
/* 183 */     if (!VerificationError.isValidVerificationError(verificationError)) {
/* 184 */       throw new IllegalArgumentException(String.format("Invalid verification error value: %d. Must be between 1 and 9, inclusive", new Object[] { Integer.valueOf(verificationError) }));
/*     */     }
/*     */ 
/* 188 */     return verificationError;
/*     */   }
/*     */ }

/* Location:           C:\Users\?????????\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.util.Preconditions
 * JD-Core Version:    0.6.0
 */