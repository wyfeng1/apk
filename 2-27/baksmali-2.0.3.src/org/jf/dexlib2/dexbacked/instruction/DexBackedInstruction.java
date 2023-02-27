/*     */ package org.jf.dexlib2.dexbacked.instruction;
/*     */ 
/*     */ import org.jf.dexlib2.Format;
/*     */ import org.jf.dexlib2.Opcode;
/*     */ import org.jf.dexlib2.Opcodes;
/*     */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*     */ import org.jf.dexlib2.dexbacked.DexReader;
/*     */ import org.jf.dexlib2.iface.instruction.Instruction;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ public abstract class DexBackedInstruction
/*     */   implements Instruction
/*     */ {
/*     */   public final DexBackedDexFile dexFile;
/*     */   public final Opcode opcode;
/*     */   public final int instructionStart;
/*     */ 
/*     */   public DexBackedInstruction(DexBackedDexFile dexFile, Opcode opcode, int instructionStart)
/*     */   {
/*  51 */     this.dexFile = dexFile;
/*  52 */     this.opcode = opcode;
/*  53 */     this.instructionStart = instructionStart;
/*     */   }
/*     */   public Opcode getOpcode() {
/*  56 */     return this.opcode; } 
/*  57 */   public int getCodeUnits() { return this.opcode.format.size / 2; }
/*     */ 
/*     */   public static Instruction readFrom(DexReader reader)
/*     */   {
/*  61 */     int opcodeValue = reader.peekUbyte();
/*     */ 
/*  63 */     if (opcodeValue == 0) {
/*  64 */       opcodeValue = reader.peekUshort();
/*     */     }
/*     */ 
/*  67 */     Opcode opcode = ((DexBackedDexFile)reader.dexBuf).getOpcodes().getOpcodeByValue(opcodeValue);
/*     */ 
/*  69 */     Instruction instruction = buildInstruction((DexBackedDexFile)reader.dexBuf, opcode, reader.getOffset());
/*  70 */     reader.moveRelative(instruction.getCodeUnits() * 2);
/*  71 */     return instruction;
/*     */   }
/*     */ 
/*     */   private static DexBackedInstruction buildInstruction(DexBackedDexFile dexFile, Opcode opcode, int instructionStartOffset)
/*     */   {
/*  76 */     if (opcode == null) {
/*  77 */       return new DexBackedUnknownInstruction(dexFile, instructionStartOffset);
/*     */     }
/*  79 */     switch (1.$SwitchMap$org$jf$dexlib2$Format[opcode.format.ordinal()]) {
/*     */     case 1:
/*  81 */       return new DexBackedInstruction10t(dexFile, opcode, instructionStartOffset);
/*     */     case 2:
/*  83 */       return new DexBackedInstruction10x(dexFile, opcode, instructionStartOffset);
/*     */     case 3:
/*  85 */       return new DexBackedInstruction11n(dexFile, opcode, instructionStartOffset);
/*     */     case 4:
/*  87 */       return new DexBackedInstruction11x(dexFile, opcode, instructionStartOffset);
/*     */     case 5:
/*  89 */       return new DexBackedInstruction12x(dexFile, opcode, instructionStartOffset);
/*     */     case 6:
/*  91 */       return new DexBackedInstruction20bc(dexFile, opcode, instructionStartOffset);
/*     */     case 7:
/*  93 */       return new DexBackedInstruction20t(dexFile, opcode, instructionStartOffset);
/*     */     case 8:
/*  95 */       return new DexBackedInstruction21c(dexFile, opcode, instructionStartOffset);
/*     */     case 9:
/*  97 */       return new DexBackedInstruction21ih(dexFile, opcode, instructionStartOffset);
/*     */     case 10:
/*  99 */       return new DexBackedInstruction21lh(dexFile, opcode, instructionStartOffset);
/*     */     case 11:
/* 101 */       return new DexBackedInstruction21s(dexFile, opcode, instructionStartOffset);
/*     */     case 12:
/* 103 */       return new DexBackedInstruction21t(dexFile, opcode, instructionStartOffset);
/*     */     case 13:
/* 105 */       return new DexBackedInstruction22b(dexFile, opcode, instructionStartOffset);
/*     */     case 14:
/* 107 */       return new DexBackedInstruction22c(dexFile, opcode, instructionStartOffset);
/*     */     case 15:
/* 109 */       return new DexBackedInstruction22cs(dexFile, opcode, instructionStartOffset);
/*     */     case 16:
/* 111 */       return new DexBackedInstruction22s(dexFile, opcode, instructionStartOffset);
/*     */     case 17:
/* 113 */       return new DexBackedInstruction22t(dexFile, opcode, instructionStartOffset);
/*     */     case 18:
/* 115 */       return new DexBackedInstruction22x(dexFile, opcode, instructionStartOffset);
/*     */     case 19:
/* 117 */       return new DexBackedInstruction23x(dexFile, opcode, instructionStartOffset);
/*     */     case 20:
/* 119 */       return new DexBackedInstruction30t(dexFile, opcode, instructionStartOffset);
/*     */     case 21:
/* 121 */       return new DexBackedInstruction31c(dexFile, opcode, instructionStartOffset);
/*     */     case 22:
/* 123 */       return new DexBackedInstruction31i(dexFile, opcode, instructionStartOffset);
/*     */     case 23:
/* 125 */       return new DexBackedInstruction31t(dexFile, opcode, instructionStartOffset);
/*     */     case 24:
/* 127 */       return new DexBackedInstruction32x(dexFile, opcode, instructionStartOffset);
/*     */     case 25:
/* 129 */       return new DexBackedInstruction35c(dexFile, opcode, instructionStartOffset);
/*     */     case 26:
/* 131 */       return new DexBackedInstruction35ms(dexFile, opcode, instructionStartOffset);
/*     */     case 27:
/* 133 */       return new DexBackedInstruction35mi(dexFile, opcode, instructionStartOffset);
/*     */     case 28:
/* 135 */       return new DexBackedInstruction3rc(dexFile, opcode, instructionStartOffset);
/*     */     case 29:
/* 137 */       return new DexBackedInstruction3rmi(dexFile, opcode, instructionStartOffset);
/*     */     case 30:
/* 139 */       return new DexBackedInstruction3rms(dexFile, opcode, instructionStartOffset);
/*     */     case 31:
/* 141 */       return new DexBackedInstruction51l(dexFile, opcode, instructionStartOffset);
/*     */     case 32:
/* 143 */       return new DexBackedPackedSwitchPayload(dexFile, instructionStartOffset);
/*     */     case 33:
/* 145 */       return new DexBackedSparseSwitchPayload(dexFile, instructionStartOffset);
/*     */     case 34:
/* 147 */       return new DexBackedArrayPayload(dexFile, instructionStartOffset);
/*     */     }
/* 149 */     throw new ExceptionWithContext("Unexpected opcode format: %s", new Object[] { opcode.format.toString() });
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction
 * JD-Core Version:    0.6.0
 */