/*     */ package org.jf.baksmali.Adaptors;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.BitSet;
/*     */ import org.jf.dexlib2.analysis.AnalyzedInstruction;
/*     */ import org.jf.dexlib2.analysis.MethodAnalyzer;
/*     */ import org.jf.dexlib2.analysis.RegisterType;
/*     */ import org.jf.dexlib2.iface.instruction.FiveRegisterInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.OneRegisterInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.RegisterRangeInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.ThreeRegisterInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.TwoRegisterInstruction;
/*     */ import org.jf.util.IndentingWriter;
/*     */ 
/*     */ public class PreInstructionRegisterInfoMethodItem extends MethodItem
/*     */ {
/*     */   private final int registerInfo;
/*     */   private final MethodAnalyzer methodAnalyzer;
/*     */   private final RegisterFormatter registerFormatter;
/*     */   private final AnalyzedInstruction analyzedInstruction;
/*     */ 
/*     */   public PreInstructionRegisterInfoMethodItem(int registerInfo, MethodAnalyzer methodAnalyzer, RegisterFormatter registerFormatter, AnalyzedInstruction analyzedInstruction, int codeAddress)
/*     */   {
/*  53 */     super(codeAddress);
/*  54 */     this.registerInfo = registerInfo;
/*  55 */     this.methodAnalyzer = methodAnalyzer;
/*  56 */     this.registerFormatter = registerFormatter;
/*  57 */     this.analyzedInstruction = analyzedInstruction;
/*     */   }
/*     */ 
/*     */   public double getSortOrder()
/*     */   {
/*  62 */     return 99.900000000000006D;
/*     */   }
/*     */ 
/*     */   public boolean writeTo(IndentingWriter writer) throws IOException
/*     */   {
/*  67 */     int registerCount = this.analyzedInstruction.getRegisterCount();
/*  68 */     BitSet registers = new BitSet(registerCount);
/*  69 */     BitSet mergeRegisters = null;
/*     */ 
/*  71 */     if ((this.registerInfo & 0x1) != 0) {
/*  72 */       registers.set(0, registerCount);
/*     */     }
/*  74 */     else if ((this.registerInfo & 0x2) != 0) {
/*  75 */       registers.set(0, registerCount);
/*     */     } else {
/*  77 */       if ((this.registerInfo & 0x8) != 0) {
/*  78 */         addArgsRegs(registers);
/*     */       }
/*  80 */       if ((this.registerInfo & 0x20) != 0) {
/*  81 */         if (this.analyzedInstruction.isBeginningInstruction()) {
/*  82 */           addParamRegs(registers, registerCount);
/*     */         }
/*  84 */         mergeRegisters = new BitSet(registerCount);
/*  85 */         addMergeRegs(mergeRegisters, registerCount);
/*  86 */       } else if (((this.registerInfo & 0x40) != 0) && (this.analyzedInstruction.isBeginningInstruction()))
/*     */       {
/*  88 */         addParamRegs(registers, registerCount);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  93 */     if ((this.registerInfo & 0x40) != 0) {
/*  94 */       if (mergeRegisters == null) {
/*  95 */         mergeRegisters = new BitSet(registerCount);
/*  96 */         addMergeRegs(mergeRegisters, registerCount);
/*     */       }
/*  98 */       registers.or(mergeRegisters);
/*  99 */     } else if (mergeRegisters != null) {
/* 100 */       registers.or(mergeRegisters);
/* 101 */       mergeRegisters = null;
/*     */     }
/*     */ 
/* 104 */     return writeRegisterInfo(writer, registers, mergeRegisters);
/*     */   }
/*     */ 
/*     */   private void addArgsRegs(BitSet registers) {
/* 108 */     if ((this.analyzedInstruction.getInstruction() instanceof RegisterRangeInstruction)) {
/* 109 */       RegisterRangeInstruction instruction = (RegisterRangeInstruction)this.analyzedInstruction.getInstruction();
/*     */ 
/* 111 */       registers.set(instruction.getStartRegister(), instruction.getStartRegister() + instruction.getRegisterCount());
/*     */     }
/* 113 */     else if ((this.analyzedInstruction.getInstruction() instanceof FiveRegisterInstruction)) {
/* 114 */       FiveRegisterInstruction instruction = (FiveRegisterInstruction)this.analyzedInstruction.getInstruction();
/* 115 */       int regCount = instruction.getRegisterCount();
/* 116 */       switch (regCount) {
/*     */       case 5:
/* 118 */         registers.set(instruction.getRegisterG());
/*     */       case 4:
/* 121 */         registers.set(instruction.getRegisterF());
/*     */       case 3:
/* 124 */         registers.set(instruction.getRegisterE());
/*     */       case 2:
/* 127 */         registers.set(instruction.getRegisterD());
/*     */       case 1:
/* 130 */         registers.set(instruction.getRegisterC());
/*     */       }
/* 132 */     } else if ((this.analyzedInstruction.getInstruction() instanceof ThreeRegisterInstruction)) {
/* 133 */       ThreeRegisterInstruction instruction = (ThreeRegisterInstruction)this.analyzedInstruction.getInstruction();
/* 134 */       registers.set(instruction.getRegisterA());
/* 135 */       registers.set(instruction.getRegisterB());
/* 136 */       registers.set(instruction.getRegisterC());
/* 137 */     } else if ((this.analyzedInstruction.getInstruction() instanceof TwoRegisterInstruction)) {
/* 138 */       TwoRegisterInstruction instruction = (TwoRegisterInstruction)this.analyzedInstruction.getInstruction();
/* 139 */       registers.set(instruction.getRegisterA());
/* 140 */       registers.set(instruction.getRegisterB());
/* 141 */     } else if ((this.analyzedInstruction.getInstruction() instanceof OneRegisterInstruction)) {
/* 142 */       OneRegisterInstruction instruction = (OneRegisterInstruction)this.analyzedInstruction.getInstruction();
/* 143 */       registers.set(instruction.getRegisterA());
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addMergeRegs(BitSet registers, int registerCount) {
/* 148 */     if (this.analyzedInstruction.getPredecessorCount() <= 1)
/*     */     {
/* 152 */       return;
/*     */     }
/*     */     RegisterType mergedRegisterType;
/* 155 */     for (int registerNum = 0; registerNum < registerCount; registerNum++) {
/* 156 */       mergedRegisterType = this.analyzedInstruction.getPreInstructionRegisterType(registerNum);
/*     */ 
/* 158 */       for (AnalyzedInstruction predecessor : this.analyzedInstruction.getPredecessors()) {
/* 159 */         RegisterType predecessorRegisterType = predecessor.getPostInstructionRegisterType(registerNum);
/* 160 */         if ((predecessorRegisterType.category != 0) && (!predecessorRegisterType.equals(mergedRegisterType)))
/*     */         {
/* 162 */           registers.set(registerNum);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addParamRegs(BitSet registers, int registerCount) {
/* 169 */     int parameterRegisterCount = this.methodAnalyzer.getParamRegisterCount();
/* 170 */     registers.set(registerCount - parameterRegisterCount, registerCount);
/*     */   }
/*     */ 
/*     */   private void writeFullMerge(IndentingWriter writer, int registerNum) throws IOException {
/* 174 */     this.registerFormatter.writeTo(writer, registerNum);
/* 175 */     writer.write(61);
/* 176 */     this.analyzedInstruction.getPreInstructionRegisterType(registerNum).writeTo(writer);
/* 177 */     writer.write(":merge{");
/*     */ 
/* 179 */     boolean first = true;
/*     */ 
/* 181 */     for (AnalyzedInstruction predecessor : this.analyzedInstruction.getPredecessors()) {
/* 182 */       RegisterType predecessorRegisterType = predecessor.getPostInstructionRegisterType(registerNum);
/*     */ 
/* 184 */       if (!first) {
/* 185 */         writer.write(44);
/*     */       }
/*     */ 
/* 188 */       if (predecessor.getInstructionIndex() == -1)
/*     */       {
/* 190 */         writer.write("Start:");
/*     */       } else {
/* 192 */         writer.write("0x");
/* 193 */         writer.printUnsignedLongAsHex(this.methodAnalyzer.getInstructionAddress(predecessor));
/* 194 */         writer.write(58);
/*     */       }
/* 196 */       predecessorRegisterType.writeTo(writer);
/*     */ 
/* 198 */       first = false;
/*     */     }
/* 200 */     writer.write(125);
/*     */   }
/*     */ 
/*     */   private boolean writeRegisterInfo(IndentingWriter writer, BitSet registers, BitSet fullMergeRegisters) throws IOException
/*     */   {
/* 205 */     boolean firstRegister = true;
/* 206 */     boolean previousWasFullMerge = false;
/* 207 */     int registerNum = registers.nextSetBit(0);
/* 208 */     if (registerNum < 0) {
/* 209 */       return false;
/*     */     }
/*     */ 
/* 212 */     writer.write(35);
/* 213 */     for (; registerNum >= 0; registerNum = registers.nextSetBit(registerNum + 1)) {
/* 214 */       boolean fullMerge = (fullMergeRegisters != null) && (fullMergeRegisters.get(registerNum));
/* 215 */       if (fullMerge) {
/* 216 */         if (!firstRegister) {
/* 217 */           writer.write(10);
/* 218 */           writer.write(35);
/*     */         }
/* 220 */         writeFullMerge(writer, registerNum);
/* 221 */         previousWasFullMerge = true;
/*     */       } else {
/* 223 */         if (previousWasFullMerge) {
/* 224 */           writer.write(10);
/* 225 */           writer.write(35);
/* 226 */           previousWasFullMerge = false;
/*     */         }
/*     */ 
/* 229 */         RegisterType registerType = this.analyzedInstruction.getPreInstructionRegisterType(registerNum);
/*     */ 
/* 231 */         this.registerFormatter.writeTo(writer, registerNum);
/* 232 */         writer.write(61);
/*     */ 
/* 234 */         registerType.writeTo(writer);
/* 235 */         writer.write(59);
/*     */       }
/*     */ 
/* 238 */       firstRegister = false;
/*     */     }
/* 240 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.PreInstructionRegisterInfoMethodItem
 * JD-Core Version:    0.6.0
 */