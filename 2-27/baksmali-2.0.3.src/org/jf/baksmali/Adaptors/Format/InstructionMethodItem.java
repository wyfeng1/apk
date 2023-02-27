/*     */ package org.jf.baksmali.Adaptors.Format;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import org.jf.baksmali.Adaptors.ClassDefinition;
/*     */ import org.jf.baksmali.Adaptors.MethodDefinition;
/*     */ import org.jf.baksmali.Adaptors.MethodDefinition.InvalidSwitchPayload;
/*     */ import org.jf.baksmali.Adaptors.MethodItem;
/*     */ import org.jf.baksmali.Adaptors.RegisterFormatter;
/*     */ import org.jf.baksmali.Renderers.LongRenderer;
/*     */ import org.jf.baksmali.baksmaliOptions;
/*     */ import org.jf.dexlib2.Format;
/*     */ import org.jf.dexlib2.Opcode;
/*     */ import org.jf.dexlib2.ReferenceType;
/*     */ import org.jf.dexlib2.ReferenceType.InvalidReferenceTypeException;
/*     */ import org.jf.dexlib2.VerificationError;
/*     */ import org.jf.dexlib2.dexbacked.DexBackedDexFile.InvalidItemIndex;
/*     */ import org.jf.dexlib2.iface.instruction.FieldOffsetInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.FiveRegisterInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.InlineIndexInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.Instruction;
/*     */ import org.jf.dexlib2.iface.instruction.NarrowLiteralInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.OneRegisterInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.RegisterRangeInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.ThreeRegisterInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.TwoRegisterInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.VtableIndexInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.WideLiteralInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction20bc;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction31t;
/*     */ import org.jf.dexlib2.iface.instruction.formats.UnknownInstruction;
/*     */ import org.jf.dexlib2.iface.reference.Reference;
/*     */ import org.jf.dexlib2.util.ReferenceUtil;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ import org.jf.util.IndentingWriter;
/*     */ 
/*     */ public class InstructionMethodItem<T extends Instruction> extends MethodItem
/*     */ {
/*     */   protected final MethodDefinition methodDef;
/*     */   protected final T instruction;
/*     */ 
/*     */   public InstructionMethodItem(MethodDefinition methodDef, int codeAddress, T instruction)
/*     */   {
/*  58 */     super(codeAddress);
/*  59 */     this.methodDef = methodDef;
/*  60 */     this.instruction = instruction;
/*     */   }
/*     */ 
/*     */   public double getSortOrder()
/*     */   {
/*  65 */     return 100.0D;
/*     */   }
/*     */ 
/*     */   private boolean isAllowedOdex(Opcode opcode) {
/*  69 */     baksmaliOptions options = this.methodDef.classDef.options;
/*  70 */     if (options.allowOdex) {
/*  71 */       return true;
/*     */     }
/*     */ 
/*  74 */     if (this.methodDef.classDef.options.apiLevel >= 14) {
/*  75 */       return false;
/*     */     }
/*     */ 
/*  78 */     return (opcode.isOdexedInstanceVolatile()) || (opcode.isOdexedStaticVolatile()) || (opcode == Opcode.THROW_VERIFICATION_ERROR);
/*     */   }
/*     */ 
/*     */   public boolean writeTo(IndentingWriter writer)
/*     */     throws IOException
/*     */   {
/*  84 */     Opcode opcode = this.instruction.getOpcode();
/*  85 */     String verificationErrorName = null;
/*  86 */     String referenceString = null;
/*     */ 
/*  88 */     boolean commentOutInstruction = false;
/*     */ 
/*  90 */     if ((this.instruction instanceof Instruction20bc)) {
/*  91 */       int verificationError = ((Instruction20bc)this.instruction).getVerificationError();
/*  92 */       verificationErrorName = VerificationError.getVerificationErrorName(verificationError);
/*  93 */       if (verificationErrorName == null) {
/*  94 */         writer.write("#was invalid verification error type: ");
/*  95 */         writer.printSignedIntAsDec(verificationError);
/*  96 */         writer.write("\n");
/*  97 */         verificationErrorName = "generic-error";
/*     */       }
/*     */     }
/*     */ 
/* 101 */     if ((this.instruction instanceof ReferenceInstruction)) {
/* 102 */       ReferenceInstruction referenceInstruction = (ReferenceInstruction)this.instruction;
/*     */       try {
/* 104 */         Reference reference = referenceInstruction.getReference();
/* 105 */         referenceString = ReferenceUtil.getReferenceString(reference);
/* 106 */         if ((!$assertionsDisabled) && (referenceString == null)) throw new AssertionError(); 
/*     */       }
/*     */       catch (DexBackedDexFile.InvalidItemIndex ex) {
/* 108 */         writer.write("#");
/* 109 */         writer.write(ex.getMessage());
/* 110 */         writer.write("\n");
/* 111 */         commentOutInstruction = true;
/*     */ 
/* 113 */         referenceString = String.format("%s@%d", new Object[] { ReferenceType.toString(referenceInstruction.getReferenceType()), Integer.valueOf(ex.getInvalidIndex()) });
/*     */       }
/*     */       catch (ReferenceType.InvalidReferenceTypeException ex)
/*     */       {
/* 117 */         writer.write("#invalid reference type: ");
/* 118 */         writer.printSignedIntAsDec(ex.getReferenceType());
/* 119 */         commentOutInstruction = true;
/*     */ 
/* 121 */         referenceString = "invalid_reference";
/*     */       }
/*     */     }
/*     */ 
/* 125 */     if ((this.instruction instanceof Instruction31t))
/*     */     {
/*     */       Opcode payloadOpcode;
/* 127 */       switch (1.$SwitchMap$org$jf$dexlib2$Opcode[this.instruction.getOpcode().ordinal()]) {
/*     */       case 1:
/* 129 */         payloadOpcode = Opcode.PACKED_SWITCH_PAYLOAD;
/* 130 */         break;
/*     */       case 2:
/* 132 */         payloadOpcode = Opcode.SPARSE_SWITCH_PAYLOAD;
/* 133 */         break;
/*     */       case 3:
/* 135 */         payloadOpcode = Opcode.ARRAY_PAYLOAD;
/* 136 */         break;
/*     */       default:
/* 138 */         throw new ExceptionWithContext("Invalid 31t opcode: %s", new Object[] { this.instruction.getOpcode() });
/*     */       }
/*     */       try
/*     */       {
/* 142 */         this.methodDef.findSwitchPayload(this.codeAddress + ((Instruction31t)this.instruction).getCodeOffset(), payloadOpcode);
/*     */       }
/*     */       catch (MethodDefinition.InvalidSwitchPayload ex) {
/* 145 */         writer.write("#invalid payload reference");
/* 146 */         commentOutInstruction = true;
/*     */       }
/*     */     }
/*     */ 
/* 150 */     if ((opcode.odexOnly()) && 
/* 151 */       (!isAllowedOdex(opcode))) {
/* 152 */       writer.write("#disallowed odex opcode\n");
/* 153 */       commentOutInstruction = true;
/*     */     }
/*     */ 
/* 157 */     if (commentOutInstruction) {
/* 158 */       writer.write("#");
/*     */     }
/*     */ 
/* 161 */     switch (1.$SwitchMap$org$jf$dexlib2$Format[this.instruction.getOpcode().format.ordinal()]) {
/*     */     case 1:
/* 163 */       writeOpcode(writer);
/* 164 */       writer.write(32);
/* 165 */       writeTargetLabel(writer);
/* 166 */       break;
/*     */     case 2:
/* 168 */       if ((this.instruction instanceof UnknownInstruction)) {
/* 169 */         writer.write("#unknown opcode: 0x");
/* 170 */         writer.printUnsignedLongAsHex(((UnknownInstruction)this.instruction).getOriginalOpcode());
/* 171 */         writer.write(10);
/*     */       }
/* 173 */       writeOpcode(writer);
/* 174 */       break;
/*     */     case 3:
/* 176 */       writeOpcode(writer);
/* 177 */       writer.write(32);
/* 178 */       writeFirstRegister(writer);
/* 179 */       writer.write(", ");
/* 180 */       writeLiteral(writer);
/* 181 */       break;
/*     */     case 4:
/* 183 */       writeOpcode(writer);
/* 184 */       writer.write(32);
/* 185 */       writeFirstRegister(writer);
/* 186 */       break;
/*     */     case 5:
/* 188 */       writeOpcode(writer);
/* 189 */       writer.write(32);
/* 190 */       writeFirstRegister(writer);
/* 191 */       writer.write(", ");
/* 192 */       writeSecondRegister(writer);
/* 193 */       break;
/*     */     case 6:
/* 195 */       writeOpcode(writer);
/* 196 */       writer.write(32);
/* 197 */       writer.write(verificationErrorName);
/* 198 */       writer.write(", ");
/* 199 */       writer.write(referenceString);
/* 200 */       break;
/*     */     case 7:
/*     */     case 8:
/* 203 */       writeOpcode(writer);
/* 204 */       writer.write(32);
/* 205 */       writeTargetLabel(writer);
/* 206 */       break;
/*     */     case 9:
/*     */     case 10:
/* 209 */       writeOpcode(writer);
/* 210 */       writer.write(32);
/* 211 */       writeFirstRegister(writer);
/* 212 */       writer.write(", ");
/* 213 */       writer.write(referenceString);
/* 214 */       break;
/*     */     case 11:
/*     */     case 12:
/*     */     case 13:
/*     */     case 14:
/*     */     case 15:
/* 220 */       writeOpcode(writer);
/* 221 */       writer.write(32);
/* 222 */       writeFirstRegister(writer);
/* 223 */       writer.write(", ");
/* 224 */       writeLiteral(writer);
/* 225 */       if (this.instruction.getOpcode().setsWideRegister()) break;
/* 226 */       writeResourceId(writer); break;
/*     */     case 16:
/*     */     case 17:
/* 230 */       writeOpcode(writer);
/* 231 */       writer.write(32);
/* 232 */       writeFirstRegister(writer);
/* 233 */       writer.write(", ");
/* 234 */       writeTargetLabel(writer);
/* 235 */       break;
/*     */     case 18:
/*     */     case 19:
/* 238 */       writeOpcode(writer);
/* 239 */       writer.write(32);
/* 240 */       writeFirstRegister(writer);
/* 241 */       writer.write(", ");
/* 242 */       writeSecondRegister(writer);
/* 243 */       writer.write(", ");
/* 244 */       writeLiteral(writer);
/* 245 */       break;
/*     */     case 20:
/* 247 */       writeOpcode(writer);
/* 248 */       writer.write(32);
/* 249 */       writeFirstRegister(writer);
/* 250 */       writer.write(", ");
/* 251 */       writeSecondRegister(writer);
/* 252 */       writer.write(", ");
/* 253 */       writer.write(referenceString);
/* 254 */       break;
/*     */     case 21:
/* 256 */       writeOpcode(writer);
/* 257 */       writer.write(32);
/* 258 */       writeFirstRegister(writer);
/* 259 */       writer.write(", ");
/* 260 */       writeSecondRegister(writer);
/* 261 */       writer.write(", ");
/* 262 */       writeFieldOffset(writer);
/* 263 */       break;
/*     */     case 22:
/* 265 */       writeOpcode(writer);
/* 266 */       writer.write(32);
/* 267 */       writeFirstRegister(writer);
/* 268 */       writer.write(", ");
/* 269 */       writeSecondRegister(writer);
/* 270 */       writer.write(", ");
/* 271 */       writeTargetLabel(writer);
/* 272 */       break;
/*     */     case 23:
/*     */     case 24:
/* 275 */       writeOpcode(writer);
/* 276 */       writer.write(32);
/* 277 */       writeFirstRegister(writer);
/* 278 */       writer.write(", ");
/* 279 */       writeSecondRegister(writer);
/* 280 */       break;
/*     */     case 25:
/* 282 */       writeOpcode(writer);
/* 283 */       writer.write(32);
/* 284 */       writeFirstRegister(writer);
/* 285 */       writer.write(", ");
/* 286 */       writeSecondRegister(writer);
/* 287 */       writer.write(", ");
/* 288 */       writeThirdRegister(writer);
/* 289 */       break;
/*     */     case 26:
/* 291 */       writeOpcode(writer);
/* 292 */       writer.write(32);
/* 293 */       writeInvokeRegisters(writer);
/* 294 */       writer.write(", ");
/* 295 */       writer.write(referenceString);
/* 296 */       break;
/*     */     case 27:
/* 298 */       writeOpcode(writer);
/* 299 */       writer.write(32);
/* 300 */       writeInvokeRegisters(writer);
/* 301 */       writer.write(", ");
/* 302 */       writeInlineIndex(writer);
/* 303 */       break;
/*     */     case 28:
/* 305 */       writeOpcode(writer);
/* 306 */       writer.write(32);
/* 307 */       writeInvokeRegisters(writer);
/* 308 */       writer.write(", ");
/* 309 */       writeVtableIndex(writer);
/* 310 */       break;
/*     */     case 29:
/* 312 */       writeOpcode(writer);
/* 313 */       writer.write(32);
/* 314 */       writeInvokeRangeRegisters(writer);
/* 315 */       writer.write(", ");
/* 316 */       writer.write(referenceString);
/* 317 */       break;
/*     */     case 30:
/* 319 */       writeOpcode(writer);
/* 320 */       writer.write(32);
/* 321 */       writeInvokeRangeRegisters(writer);
/* 322 */       writer.write(", ");
/* 323 */       writeInlineIndex(writer);
/* 324 */       break;
/*     */     case 31:
/* 326 */       writeOpcode(writer);
/* 327 */       writer.write(32);
/* 328 */       writeInvokeRangeRegisters(writer);
/* 329 */       writer.write(", ");
/* 330 */       writeVtableIndex(writer);
/* 331 */       break;
/*     */     default:
/* 333 */       if (!$assertionsDisabled) throw new AssertionError();
/* 334 */       return false;
/*     */     }
/*     */ 
/* 337 */     if (commentOutInstruction) {
/* 338 */       writer.write("\nnop");
/*     */     }
/*     */ 
/* 341 */     return true;
/*     */   }
/*     */ 
/*     */   protected void writeOpcode(IndentingWriter writer) throws IOException {
/* 345 */     writer.write(this.instruction.getOpcode().name);
/*     */   }
/*     */ 
/*     */   protected void writeTargetLabel(IndentingWriter writer)
/*     */     throws IOException
/*     */   {
/* 351 */     throw new RuntimeException();
/*     */   }
/*     */ 
/*     */   protected void writeRegister(IndentingWriter writer, int registerNumber) throws IOException {
/* 355 */     this.methodDef.registerFormatter.writeTo(writer, registerNumber);
/*     */   }
/*     */ 
/*     */   protected void writeFirstRegister(IndentingWriter writer) throws IOException {
/* 359 */     writeRegister(writer, ((OneRegisterInstruction)this.instruction).getRegisterA());
/*     */   }
/*     */ 
/*     */   protected void writeSecondRegister(IndentingWriter writer) throws IOException {
/* 363 */     writeRegister(writer, ((TwoRegisterInstruction)this.instruction).getRegisterB());
/*     */   }
/*     */ 
/*     */   protected void writeThirdRegister(IndentingWriter writer) throws IOException {
/* 367 */     writeRegister(writer, ((ThreeRegisterInstruction)this.instruction).getRegisterC());
/*     */   }
/*     */ 
/*     */   protected void writeInvokeRegisters(IndentingWriter writer) throws IOException {
/* 371 */     FiveRegisterInstruction instruction = (FiveRegisterInstruction)this.instruction;
/* 372 */     int regCount = instruction.getRegisterCount();
/*     */ 
/* 374 */     writer.write(123);
/* 375 */     switch (regCount) {
/*     */     case 1:
/* 377 */       writeRegister(writer, instruction.getRegisterC());
/* 378 */       break;
/*     */     case 2:
/* 380 */       writeRegister(writer, instruction.getRegisterC());
/* 381 */       writer.write(", ");
/* 382 */       writeRegister(writer, instruction.getRegisterD());
/* 383 */       break;
/*     */     case 3:
/* 385 */       writeRegister(writer, instruction.getRegisterC());
/* 386 */       writer.write(", ");
/* 387 */       writeRegister(writer, instruction.getRegisterD());
/* 388 */       writer.write(", ");
/* 389 */       writeRegister(writer, instruction.getRegisterE());
/* 390 */       break;
/*     */     case 4:
/* 392 */       writeRegister(writer, instruction.getRegisterC());
/* 393 */       writer.write(", ");
/* 394 */       writeRegister(writer, instruction.getRegisterD());
/* 395 */       writer.write(", ");
/* 396 */       writeRegister(writer, instruction.getRegisterE());
/* 397 */       writer.write(", ");
/* 398 */       writeRegister(writer, instruction.getRegisterF());
/* 399 */       break;
/*     */     case 5:
/* 401 */       writeRegister(writer, instruction.getRegisterC());
/* 402 */       writer.write(", ");
/* 403 */       writeRegister(writer, instruction.getRegisterD());
/* 404 */       writer.write(", ");
/* 405 */       writeRegister(writer, instruction.getRegisterE());
/* 406 */       writer.write(", ");
/* 407 */       writeRegister(writer, instruction.getRegisterF());
/* 408 */       writer.write(", ");
/* 409 */       writeRegister(writer, instruction.getRegisterG());
/*     */     }
/*     */ 
/* 412 */     writer.write(125);
/*     */   }
/*     */ 
/*     */   protected void writeInvokeRangeRegisters(IndentingWriter writer) throws IOException {
/* 416 */     RegisterRangeInstruction instruction = (RegisterRangeInstruction)this.instruction;
/*     */ 
/* 418 */     int regCount = instruction.getRegisterCount();
/* 419 */     if (regCount == 0) {
/* 420 */       writer.write("{}");
/*     */     } else {
/* 422 */       int startRegister = instruction.getStartRegister();
/* 423 */       this.methodDef.registerFormatter.writeRegisterRange(writer, startRegister, startRegister + regCount - 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void writeLiteral(IndentingWriter writer) throws IOException {
/* 428 */     LongRenderer.writeSignedIntOrLongTo(writer, ((WideLiteralInstruction)this.instruction).getWideLiteral());
/*     */   }
/*     */ 
/*     */   protected void writeResourceId(IndentingWriter writer) throws IOException {
/* 432 */     writeResourceId(writer, ((NarrowLiteralInstruction)this.instruction).getNarrowLiteral());
/*     */   }
/*     */ 
/*     */   protected void writeResourceId(IndentingWriter writer, int val) throws IOException {
/* 436 */     Map resourceIds = this.methodDef.classDef.options.resourceIds;
/* 437 */     String resource = (String)resourceIds.get(Integer.valueOf(val));
/* 438 */     if (resource != null) {
/* 439 */       writer.write("    # ");
/* 440 */       writer.write(resource);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void writeFieldOffset(IndentingWriter writer) throws IOException {
/* 445 */     writer.write("field@0x");
/* 446 */     writer.printUnsignedLongAsHex(((FieldOffsetInstruction)this.instruction).getFieldOffset());
/*     */   }
/*     */ 
/*     */   protected void writeInlineIndex(IndentingWriter writer) throws IOException {
/* 450 */     writer.write("inline@");
/* 451 */     writer.printSignedIntAsDec(((InlineIndexInstruction)this.instruction).getInlineIndex());
/*     */   }
/*     */ 
/*     */   protected void writeVtableIndex(IndentingWriter writer) throws IOException {
/* 455 */     writer.write("vtable@");
/* 456 */     writer.printSignedIntAsDec(((VtableIndexInstruction)this.instruction).getVtableIndex());
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.Format.InstructionMethodItem
 * JD-Core Version:    0.6.0
 */