/*     */ package org.jf.baksmali.Adaptors;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.BitSet;
/*     */ import org.jf.baksmali.baksmaliOptions;
/*     */ import org.jf.dexlib2.analysis.AnalyzedInstruction;
/*     */ import org.jf.dexlib2.analysis.RegisterType;
/*     */ import org.jf.util.IndentingWriter;
/*     */ 
/*     */ public class PostInstructionRegisterInfoMethodItem extends MethodItem
/*     */ {
/*     */   private final RegisterFormatter registerFormatter;
/*     */   private final AnalyzedInstruction analyzedInstruction;
/*     */ 
/*     */   public PostInstructionRegisterInfoMethodItem(RegisterFormatter registerFormatter, AnalyzedInstruction analyzedInstruction, int codeAddress)
/*     */   {
/*  47 */     super(codeAddress);
/*  48 */     this.registerFormatter = registerFormatter;
/*  49 */     this.analyzedInstruction = analyzedInstruction;
/*     */   }
/*     */ 
/*     */   public double getSortOrder()
/*     */   {
/*  54 */     return 100.09999999999999D;
/*     */   }
/*     */ 
/*     */   public boolean writeTo(IndentingWriter writer) throws IOException
/*     */   {
/*  59 */     int registerInfo = this.registerFormatter.options.registerInfo;
/*  60 */     int registerCount = this.analyzedInstruction.getRegisterCount();
/*  61 */     BitSet registers = new BitSet(registerCount);
/*     */ 
/*  63 */     if ((registerInfo & 0x1) != 0) {
/*  64 */       registers.set(0, registerCount);
/*     */     }
/*  66 */     else if ((registerInfo & 0x4) != 0)
/*  67 */       registers.set(0, registerCount);
/*  68 */     else if ((registerInfo & 0x10) != 0) {
/*  69 */       addDestRegs(registers, registerCount);
/*     */     }
/*     */ 
/*  73 */     return writeRegisterInfo(writer, registers);
/*     */   }
/*     */ 
/*     */   private void addDestRegs(BitSet printPostRegister, int registerCount) {
/*  77 */     for (int registerNum = 0; registerNum < registerCount; registerNum++) {
/*  78 */       if (this.analyzedInstruction.getPreInstructionRegisterType(registerNum).equals(this.analyzedInstruction.getPostInstructionRegisterType(registerNum)))
/*     */         continue;
/*  80 */       printPostRegister.set(registerNum);
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean writeRegisterInfo(IndentingWriter writer, BitSet registers) throws IOException
/*     */   {
/*  86 */     int registerNum = registers.nextSetBit(0);
/*  87 */     if (registerNum < 0) {
/*  88 */       return false;
/*     */     }
/*     */ 
/*  91 */     writer.write(35);
/*  92 */     for (; registerNum >= 0; registerNum = registers.nextSetBit(registerNum + 1)) {
/*  93 */       RegisterType registerType = this.analyzedInstruction.getPostInstructionRegisterType(registerNum);
/*     */ 
/*  95 */       this.registerFormatter.writeTo(writer, registerNum);
/*  96 */       writer.write(61);
/*  97 */       registerType.writeTo(writer);
/*  98 */       writer.write(59);
/*     */     }
/* 100 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.PostInstructionRegisterInfoMethodItem
 * JD-Core Version:    0.6.0
 */