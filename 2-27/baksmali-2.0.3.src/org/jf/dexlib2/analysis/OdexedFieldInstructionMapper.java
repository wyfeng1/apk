/*     */ package org.jf.dexlib2.analysis;
/*     */ 
/*     */ import org.jf.dexlib2.Opcode;
/*     */ 
/*     */ public class OdexedFieldInstructionMapper
/*     */ {
/*  39 */   private static Opcode[][][][] opcodeMap = { { { { Opcode.IGET_QUICK, Opcode.IGET_QUICK, Opcode.IGET_QUICK, Opcode.IGET_QUICK, Opcode.IGET_QUICK, Opcode.IGET_WIDE_QUICK, Opcode.IGET_OBJECT_QUICK }, { Opcode.IGET_BOOLEAN, Opcode.IGET_BYTE, Opcode.IGET_SHORT, Opcode.IGET_CHAR, Opcode.IGET, Opcode.IGET_WIDE, Opcode.IGET_OBJECT } }, { { Opcode.IGET_VOLATILE, Opcode.IGET_VOLATILE, Opcode.IGET_VOLATILE, Opcode.IGET_VOLATILE, Opcode.IGET_VOLATILE, Opcode.IGET_WIDE_VOLATILE, Opcode.IGET_OBJECT_VOLATILE }, { Opcode.IGET_BOOLEAN, Opcode.IGET_BYTE, Opcode.IGET_SHORT, Opcode.IGET_CHAR, Opcode.IGET, Opcode.IGET_WIDE, Opcode.IGET_OBJECT } }, { { Opcode.SGET_VOLATILE, Opcode.SGET_VOLATILE, Opcode.SGET_VOLATILE, Opcode.SGET_VOLATILE, Opcode.SGET_VOLATILE, Opcode.SGET_WIDE_VOLATILE, Opcode.SGET_OBJECT_VOLATILE }, { Opcode.SGET_BOOLEAN, Opcode.SGET_BYTE, Opcode.SGET_SHORT, Opcode.SGET_CHAR, Opcode.SGET, Opcode.SGET_WIDE, Opcode.SGET_OBJECT } } }, { { { Opcode.IPUT_QUICK, Opcode.IPUT_QUICK, Opcode.IPUT_QUICK, Opcode.IPUT_QUICK, Opcode.IPUT_QUICK, Opcode.IPUT_WIDE_QUICK, Opcode.IPUT_OBJECT_QUICK }, { Opcode.IPUT_BOOLEAN, Opcode.IPUT_BYTE, Opcode.IPUT_SHORT, Opcode.IPUT_CHAR, Opcode.IPUT, Opcode.IPUT_WIDE, Opcode.IPUT_OBJECT } }, { { Opcode.IPUT_VOLATILE, Opcode.IPUT_VOLATILE, Opcode.IPUT_VOLATILE, Opcode.IPUT_VOLATILE, Opcode.IPUT_VOLATILE, Opcode.IPUT_WIDE_VOLATILE, Opcode.IPUT_OBJECT_VOLATILE }, { Opcode.IPUT_BOOLEAN, Opcode.IPUT_BYTE, Opcode.IPUT_SHORT, Opcode.IPUT_CHAR, Opcode.IPUT, Opcode.IPUT_WIDE, Opcode.IPUT_OBJECT } }, { { Opcode.SPUT_VOLATILE, Opcode.SPUT_VOLATILE, Opcode.SPUT_VOLATILE, Opcode.SPUT_VOLATILE, Opcode.SPUT_VOLATILE, Opcode.SPUT_WIDE_VOLATILE, Opcode.SPUT_OBJECT_VOLATILE }, { Opcode.SPUT_BOOLEAN, Opcode.SPUT_BYTE, Opcode.SPUT_SHORT, Opcode.SPUT_CHAR, Opcode.SPUT, Opcode.SPUT_WIDE, Opcode.SPUT_OBJECT } } } };
/*     */ 
/*     */   private static int getTypeIndex(char type)
/*     */   {
/* 187 */     switch (type) {
/*     */     case 'Z':
/* 189 */       return 0;
/*     */     case 'B':
/* 191 */       return 1;
/*     */     case 'S':
/* 193 */       return 2;
/*     */     case 'C':
/* 195 */       return 3;
/*     */     case 'F':
/*     */     case 'I':
/* 198 */       return 4;
/*     */     case 'D':
/*     */     case 'J':
/* 201 */       return 5;
/*     */     case 'L':
/*     */     case '[':
/* 204 */       return 6;
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
/* 207 */     case 'Y': } throw new RuntimeException(String.format("Unknown type %s: ", new Object[] { Character.valueOf(type) }));
/*     */   }
/*     */ 
/*     */   private static int getOpcodeSubtype(Opcode opcode) {
/* 211 */     if (opcode.isOdexedInstanceQuick())
/* 212 */       return 0;
/* 213 */     if (opcode.isOdexedInstanceVolatile())
/* 214 */       return 1;
/* 215 */     if (opcode.isOdexedStaticVolatile()) {
/* 216 */       return 2;
/*     */     }
/* 218 */     throw new RuntimeException(String.format("Not an odexed field access opcode: %s", new Object[] { opcode.name }));
/*     */   }
/*     */ 
/*     */   static Opcode getAndCheckDeodexedOpcodeForOdexedOpcode(String fieldType, Opcode odexedOpcode)
/*     */   {
/* 223 */     int opcodeType = odexedOpcode.setsRegister() ? 0 : 1;
/* 224 */     int opcodeSubType = getOpcodeSubtype(odexedOpcode);
/* 225 */     int typeIndex = getTypeIndex(fieldType.charAt(0));
/*     */ 
/* 229 */     Opcode correctOdexedOpcode = opcodeMap[opcodeType][opcodeSubType][0][typeIndex];
/* 230 */     Opcode deodexedOpcode = opcodeMap[opcodeType][opcodeSubType][1][typeIndex];
/*     */ 
/* 232 */     if (correctOdexedOpcode != odexedOpcode) {
/* 233 */       throw new AnalysisException(String.format("Incorrect field type \"%s\" for %s", new Object[] { fieldType, odexedOpcode.name }), new Object[0]);
/*     */     }
/*     */ 
/* 237 */     return deodexedOpcode;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.analysis.OdexedFieldInstructionMapper
 * JD-Core Version:    0.6.0
 */