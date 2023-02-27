/*     */ package org.jf.baksmali.Adaptors;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.jf.baksmali.Adaptors.Debug.DebugMethodItem;
/*     */ import org.jf.baksmali.Adaptors.Format.InstructionMethodItemFactory;
/*     */ import org.jf.baksmali.baksmaliOptions;
/*     */ import org.jf.dexlib2.AccessFlags;
/*     */ import org.jf.dexlib2.Format;
/*     */ import org.jf.dexlib2.Opcode;
/*     */ import org.jf.dexlib2.analysis.AnalysisException;
/*     */ import org.jf.dexlib2.analysis.AnalyzedInstruction;
/*     */ import org.jf.dexlib2.analysis.MethodAnalyzer;
/*     */ import org.jf.dexlib2.dexbacked.DexBackedDexFile.InvalidItemIndex;
/*     */ import org.jf.dexlib2.iface.ExceptionHandler;
/*     */ import org.jf.dexlib2.iface.Method;
/*     */ import org.jf.dexlib2.iface.MethodImplementation;
/*     */ import org.jf.dexlib2.iface.MethodParameter;
/*     */ import org.jf.dexlib2.iface.TryBlock;
/*     */ import org.jf.dexlib2.iface.debug.DebugItem;
/*     */ import org.jf.dexlib2.iface.instruction.Instruction;
/*     */ import org.jf.dexlib2.iface.instruction.OffsetInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
/*     */ import org.jf.dexlib2.iface.reference.MethodReference;
/*     */ import org.jf.dexlib2.util.InstructionOffsetMap;
/*     */ import org.jf.dexlib2.util.InstructionOffsetMap.InvalidInstructionOffset;
/*     */ import org.jf.dexlib2.util.ReferenceUtil;
/*     */ import org.jf.dexlib2.util.SyntheticAccessorResolver;
/*     */ import org.jf.dexlib2.util.SyntheticAccessorResolver.AccessedMember;
/*     */ import org.jf.dexlib2.util.TypeUtils;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ import org.jf.util.IndentingWriter;
/*     */ import org.jf.util.SparseIntArray;
/*     */ 
/*     */ public class MethodDefinition
/*     */ {
/*     */   public final ClassDefinition classDef;
/*     */   public final Method method;
/*     */   public final MethodImplementation methodImpl;
/*     */   public final ImmutableList<Instruction> instructions;
/*     */   public final ImmutableList<MethodParameter> methodParameters;
/*     */   public RegisterFormatter registerFormatter;
/*  70 */   private final LabelCache labelCache = new LabelCache();
/*     */   private final SparseIntArray packedSwitchMap;
/*     */   private final SparseIntArray sparseSwitchMap;
/*     */   private final InstructionOffsetMap instructionOffsetMap;
/*     */ 
/*     */   public MethodDefinition(ClassDefinition classDef, Method method, MethodImplementation methodImpl)
/*     */   {
/*  78 */     this.classDef = classDef;
/*  79 */     this.method = method;
/*  80 */     this.methodImpl = methodImpl;
/*     */     try
/*     */     {
/*  85 */       this.instructions = ImmutableList.copyOf(methodImpl.getInstructions());
/*  86 */       this.methodParameters = ImmutableList.copyOf(method.getParameters());
/*     */ 
/*  88 */       this.packedSwitchMap = new SparseIntArray(0);
/*  89 */       this.sparseSwitchMap = new SparseIntArray(0);
/*  90 */       this.instructionOffsetMap = new InstructionOffsetMap(this.instructions);
/*     */ 
/*  92 */       for (int i = 0; i < this.instructions.size(); i++) {
/*  93 */         Instruction instruction = (Instruction)this.instructions.get(i);
/*     */ 
/*  95 */         Opcode opcode = instruction.getOpcode();
/*  96 */         if (opcode == Opcode.PACKED_SWITCH) {
/*  97 */           boolean valid = true;
/*  98 */           int codeOffset = this.instructionOffsetMap.getInstructionCodeOffset(i);
/*  99 */           int targetOffset = codeOffset + ((OffsetInstruction)instruction).getCodeOffset();
/*     */           try {
/* 101 */             targetOffset = findSwitchPayload(targetOffset, Opcode.PACKED_SWITCH_PAYLOAD);
/*     */           } catch (InvalidSwitchPayload ex) {
/* 103 */             valid = false;
/*     */           }
/* 105 */           if (valid)
/* 106 */             this.packedSwitchMap.append(targetOffset, codeOffset);
/*     */         }
/* 108 */         else if (opcode == Opcode.SPARSE_SWITCH) {
/* 109 */           boolean valid = true;
/* 110 */           int codeOffset = this.instructionOffsetMap.getInstructionCodeOffset(i);
/* 111 */           int targetOffset = codeOffset + ((OffsetInstruction)instruction).getCodeOffset();
/*     */           try {
/* 113 */             targetOffset = findSwitchPayload(targetOffset, Opcode.SPARSE_SWITCH_PAYLOAD);
/*     */           } catch (InvalidSwitchPayload ex) {
/* 115 */             valid = false;
/*     */           }
/*     */ 
/* 119 */           if (valid)
/* 120 */             this.sparseSwitchMap.append(targetOffset, codeOffset);
/*     */         }
/*     */       }
/*     */     } catch (Exception ex) {
/*     */       String methodString;
/*     */       try {
/* 127 */         methodString = ReferenceUtil.getMethodDescriptor(method);
/*     */       } catch (Exception ex2) {
/* 129 */         throw ExceptionWithContext.withContext(ex, "Error while processing method", new Object[0]);
/*     */       }
/* 131 */       throw ExceptionWithContext.withContext(ex, "Error while processing method %s", new Object[] { methodString });
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void writeEmptyMethodTo(IndentingWriter writer, Method method, baksmaliOptions options) throws IOException
/*     */   {
/* 137 */     writer.write(".method ");
/* 138 */     writeAccessFlags(writer, method.getAccessFlags());
/* 139 */     writer.write(method.getName());
/* 140 */     writer.write("(");
/* 141 */     ImmutableList methodParameters = ImmutableList.copyOf(method.getParameters());
/* 142 */     for (MethodParameter parameter : methodParameters) {
/* 143 */       writer.write(parameter.getType());
/*     */     }
/* 145 */     writer.write(")");
/* 146 */     writer.write(method.getReturnType());
/* 147 */     writer.write(10);
/*     */ 
/* 149 */     writer.indent(4);
/* 150 */     writeParameters(writer, method, methodParameters, options);
/* 151 */     AnnotationFormatter.writeTo(writer, method.getAnnotations());
/* 152 */     writer.deindent(4);
/* 153 */     writer.write(".end method\n");
/*     */   }
/*     */ 
/*     */   public void writeTo(IndentingWriter writer) throws IOException {
/* 157 */     int parameterRegisterCount = 0;
/* 158 */     if (!AccessFlags.STATIC.isSet(this.method.getAccessFlags())) {
/* 159 */       parameterRegisterCount++;
/*     */     }
/*     */ 
/* 162 */     writer.write(".method ");
/* 163 */     writeAccessFlags(writer, this.method.getAccessFlags());
/* 164 */     writer.write(this.method.getName());
/* 165 */     writer.write("(");
/* 166 */     for (MethodParameter parameter : this.methodParameters) {
/* 167 */       String type = parameter.getType();
/* 168 */       writer.write(type);
/* 169 */       parameterRegisterCount++;
/* 170 */       if (TypeUtils.isWideType(type)) {
/* 171 */         parameterRegisterCount++;
/*     */       }
/*     */     }
/* 174 */     writer.write(")");
/* 175 */     writer.write(this.method.getReturnType());
/* 176 */     writer.write(10);
/*     */ 
/* 178 */     writer.indent(4);
/* 179 */     if (this.classDef.options.useLocalsDirective) {
/* 180 */       writer.write(".locals ");
/* 181 */       writer.printSignedIntAsDec(this.methodImpl.getRegisterCount() - parameterRegisterCount);
/*     */     } else {
/* 183 */       writer.write(".registers ");
/* 184 */       writer.printSignedIntAsDec(this.methodImpl.getRegisterCount());
/*     */     }
/* 186 */     writer.write(10);
/* 187 */     writeParameters(writer, this.method, this.methodParameters, this.classDef.options);
/*     */ 
/* 189 */     if (this.registerFormatter == null) {
/* 190 */       this.registerFormatter = new RegisterFormatter(this.classDef.options, this.methodImpl.getRegisterCount(), parameterRegisterCount);
/*     */     }
/*     */ 
/* 194 */     AnnotationFormatter.writeTo(writer, this.method.getAnnotations());
/*     */ 
/* 196 */     writer.write(10);
/*     */ 
/* 198 */     List methodItems = getMethodItems();
/* 199 */     for (MethodItem methodItem : methodItems) {
/* 200 */       if (methodItem.writeTo(writer)) {
/* 201 */         writer.write(10);
/*     */       }
/*     */     }
/* 204 */     writer.deindent(4);
/* 205 */     writer.write(".end method\n");
/*     */   }
/*     */   public int findSwitchPayload(int targetOffset, Opcode type) {
/*     */     int targetIndex;
/*     */     try {
/* 211 */       targetIndex = this.instructionOffsetMap.getInstructionIndexAtCodeOffset(targetOffset);
/*     */     } catch (InstructionOffsetMap.InvalidInstructionOffset ex) {
/* 213 */       throw new InvalidSwitchPayload(targetOffset);
/*     */     }
/*     */ 
/* 219 */     Instruction instruction = (Instruction)this.instructions.get(targetIndex);
/* 220 */     if (instruction.getOpcode() != type)
/*     */     {
/* 222 */       if (instruction.getOpcode() == Opcode.NOP) {
/* 223 */         targetIndex++;
/* 224 */         if (targetIndex < this.instructions.size()) {
/* 225 */           instruction = (Instruction)this.instructions.get(targetIndex);
/* 226 */           if (instruction.getOpcode() == type) {
/* 227 */             return this.instructionOffsetMap.getInstructionCodeOffset(targetIndex);
/*     */           }
/*     */         }
/*     */       }
/* 231 */       throw new InvalidSwitchPayload(targetOffset);
/*     */     }
/* 233 */     return targetOffset;
/*     */   }
/*     */ 
/*     */   private static void writeAccessFlags(IndentingWriter writer, int accessFlags)
/*     */     throws IOException
/*     */   {
/* 239 */     for (AccessFlags accessFlag : AccessFlags.getAccessFlagsForMethod(accessFlags)) {
/* 240 */       writer.write(accessFlag.toString());
/* 241 */       writer.write(32);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void writeParameters(IndentingWriter writer, Method method, List<? extends MethodParameter> parameters, baksmaliOptions options)
/*     */     throws IOException
/*     */   {
/* 248 */     boolean isStatic = AccessFlags.STATIC.isSet(method.getAccessFlags());
/* 249 */     int registerNumber = isStatic ? 0 : 1;
/* 250 */     for (MethodParameter parameter : parameters) {
/* 251 */       String parameterType = parameter.getType();
/* 252 */       String parameterName = parameter.getName();
/* 253 */       Collection annotations = parameter.getAnnotations();
/* 254 */       if ((parameterName != null) || (annotations.size() != 0)) {
/* 255 */         writer.write(".param p");
/* 256 */         writer.printSignedIntAsDec(registerNumber);
/*     */ 
/* 258 */         if ((parameterName != null) && (options.outputDebugInfo)) {
/* 259 */           writer.write(", ");
/* 260 */           ReferenceFormatter.writeStringReference(writer, parameterName);
/*     */         }
/* 262 */         writer.write("    # ");
/* 263 */         writer.write(parameterType);
/* 264 */         writer.write("\n");
/* 265 */         if (annotations.size() > 0) {
/* 266 */           writer.indent(4);
/* 267 */           AnnotationFormatter.writeTo(writer, annotations);
/* 268 */           writer.deindent(4);
/* 269 */           writer.write(".end param\n");
/*     */         }
/*     */       }
/*     */ 
/* 273 */       registerNumber++;
/* 274 */       if (TypeUtils.isWideType(parameterType))
/* 275 */         registerNumber++;
/*     */     }
/*     */   }
/*     */ 
/*     */   public LabelCache getLabelCache()
/*     */   {
/* 281 */     return this.labelCache;
/*     */   }
/*     */ 
/*     */   public int getPackedSwitchBaseAddress(int packedSwitchPayloadCodeOffset) {
/* 285 */     return this.packedSwitchMap.get(packedSwitchPayloadCodeOffset, -1);
/*     */   }
/*     */ 
/*     */   public int getSparseSwitchBaseAddress(int sparseSwitchPayloadCodeOffset) {
/* 289 */     return this.sparseSwitchMap.get(sparseSwitchPayloadCodeOffset, -1);
/*     */   }
/*     */ 
/*     */   private List<MethodItem> getMethodItems() {
/* 293 */     ArrayList methodItems = new ArrayList();
/*     */ 
/* 295 */     if ((this.classDef.options.registerInfo != 0) || ((this.classDef.options.deodex) && (needsAnalyzed())))
/* 296 */       addAnalyzedInstructionMethodItems(methodItems);
/*     */     else {
/* 298 */       addInstructionMethodItems(methodItems);
/*     */     }
/*     */ 
/* 301 */     addTries(methodItems);
/* 302 */     if (this.classDef.options.outputDebugInfo) {
/* 303 */       addDebugInfo(methodItems);
/*     */     }
/*     */ 
/* 306 */     if (this.classDef.options.useSequentialLabels) {
/* 307 */       setLabelSequentialNumbers();
/*     */     }
/*     */ 
/* 310 */     for (LabelMethodItem labelMethodItem : this.labelCache.getLabels()) {
/* 311 */       methodItems.add(labelMethodItem);
/*     */     }
/*     */ 
/* 314 */     Collections.sort(methodItems);
/*     */ 
/* 316 */     return methodItems;
/*     */   }
/*     */ 
/*     */   private boolean needsAnalyzed() {
/* 320 */     for (Instruction instruction : this.methodImpl.getInstructions()) {
/* 321 */       if (instruction.getOpcode().odexOnly()) {
/* 322 */         return true;
/*     */       }
/*     */     }
/* 325 */     return false;
/*     */   }
/*     */ 
/*     */   private void addInstructionMethodItems(List<MethodItem> methodItems) {
/* 329 */     int currentCodeAddress = 0;
/* 330 */     for (int i = 0; i < this.instructions.size(); i++) {
/* 331 */       Instruction instruction = (Instruction)this.instructions.get(i);
/*     */ 
/* 333 */       MethodItem methodItem = InstructionMethodItemFactory.makeInstructionFormatMethodItem(this, currentCodeAddress, instruction);
/*     */ 
/* 336 */       methodItems.add(methodItem);
/*     */ 
/* 338 */       if (i != this.instructions.size() - 1) {
/* 339 */         methodItems.add(new BlankMethodItem(currentCodeAddress));
/*     */       }
/*     */ 
/* 342 */       if (this.classDef.options.addCodeOffsets) {
/* 343 */         methodItems.add(new MethodItem(currentCodeAddress)
/*     */         {
/*     */           public double getSortOrder()
/*     */           {
/* 347 */             return -1000.0D;
/*     */           }
/*     */ 
/*     */           public boolean writeTo(IndentingWriter writer) throws IOException
/*     */           {
/* 352 */             writer.write("#@");
/* 353 */             writer.printUnsignedLongAsHex(this.codeAddress & 0xFFFFFFFF);
/* 354 */             return true;
/*     */           }
/*     */         });
/*     */       }
/* 359 */       if ((!this.classDef.options.noAccessorComments) && ((instruction instanceof ReferenceInstruction))) {
/* 360 */         Opcode opcode = instruction.getOpcode();
/*     */ 
/* 362 */         if (opcode.referenceType == 3) {
/* 363 */           MethodReference methodReference = null;
/*     */           try {
/* 365 */             methodReference = (MethodReference)((ReferenceInstruction)instruction).getReference();
/*     */           }
/*     */           catch (DexBackedDexFile.InvalidItemIndex localInvalidItemIndex)
/*     */           {
/*     */           }
/*     */ 
/* 371 */           if ((methodReference != null) && (SyntheticAccessorResolver.looksLikeSyntheticAccessor(methodReference.getName())))
/*     */           {
/* 373 */             SyntheticAccessorResolver.AccessedMember accessedMember = this.classDef.options.syntheticAccessorResolver.getAccessedMember(methodReference);
/*     */ 
/* 375 */             if (accessedMember != null) {
/* 376 */               methodItems.add(new SyntheticAccessCommentMethodItem(accessedMember, currentCodeAddress));
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 382 */       currentCodeAddress += instruction.getCodeUnits();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addAnalyzedInstructionMethodItems(List<MethodItem> methodItems) {
/* 387 */     MethodAnalyzer methodAnalyzer = new MethodAnalyzer(this.classDef.options.classPath, this.method, this.classDef.options.inlineResolver);
/*     */ 
/* 390 */     AnalysisException analysisException = methodAnalyzer.getAnalysisException();
/* 391 */     if (analysisException != null)
/*     */     {
/* 393 */       methodItems.add(new CommentMethodItem(String.format("AnalysisException: %s", new Object[] { analysisException.getMessage() }), analysisException.codeAddress, -2147483648.0D));
/*     */ 
/* 396 */       analysisException.printStackTrace(System.err);
/*     */     }
/*     */ 
/* 399 */     List instructions = methodAnalyzer.getAnalyzedInstructions();
/*     */ 
/* 401 */     int currentCodeAddress = 0;
/* 402 */     for (int i = 0; i < instructions.size(); i++) {
/* 403 */       AnalyzedInstruction instruction = (AnalyzedInstruction)instructions.get(i);
/*     */ 
/* 405 */       MethodItem methodItem = InstructionMethodItemFactory.makeInstructionFormatMethodItem(this, currentCodeAddress, instruction.getInstruction());
/*     */ 
/* 408 */       methodItems.add(methodItem);
/*     */ 
/* 410 */       if (instruction.getInstruction().getOpcode().format == Format.UnresolvedOdexInstruction) {
/* 411 */         methodItems.add(new CommentedOutMethodItem(InstructionMethodItemFactory.makeInstructionFormatMethodItem(this, currentCodeAddress, instruction.getOriginalInstruction())));
/*     */       }
/*     */ 
/* 416 */       if (i != instructions.size() - 1) {
/* 417 */         methodItems.add(new BlankMethodItem(currentCodeAddress));
/*     */       }
/*     */ 
/* 420 */       if (this.classDef.options.addCodeOffsets) {
/* 421 */         methodItems.add(new MethodItem(currentCodeAddress)
/*     */         {
/*     */           public double getSortOrder()
/*     */           {
/* 425 */             return -1000.0D;
/*     */           }
/*     */ 
/*     */           public boolean writeTo(IndentingWriter writer) throws IOException
/*     */           {
/* 430 */             writer.write("#@");
/* 431 */             writer.printUnsignedLongAsHex(this.codeAddress & 0xFFFFFFFF);
/* 432 */             return true;
/*     */           }
/*     */         });
/*     */       }
/* 437 */       if ((this.classDef.options.registerInfo != 0) && (!instruction.getInstruction().getOpcode().format.isPayloadFormat))
/*     */       {
/* 439 */         methodItems.add(new PreInstructionRegisterInfoMethodItem(this.classDef.options.registerInfo, methodAnalyzer, this.registerFormatter, instruction, currentCodeAddress));
/*     */ 
/* 443 */         methodItems.add(new PostInstructionRegisterInfoMethodItem(this.registerFormatter, instruction, currentCodeAddress));
/*     */       }
/*     */ 
/* 447 */       currentCodeAddress += instruction.getInstruction().getCodeUnits();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void addTries(List<MethodItem> methodItems) {
/* 452 */     List tryBlocks = this.methodImpl.getTryBlocks();
/* 453 */     if (tryBlocks.size() == 0) {
/* 454 */       return;
/*     */     }
/*     */ 
/* 457 */     int lastInstructionAddress = this.instructionOffsetMap.getInstructionCodeOffset(this.instructions.size() - 1);
/* 458 */     int codeSize = lastInstructionAddress + ((Instruction)this.instructions.get(this.instructions.size() - 1)).getCodeUnits();
/*     */ 
/* 460 */     for (TryBlock tryBlock : tryBlocks) {
/* 461 */       startAddress = tryBlock.getStartCodeAddress();
/* 462 */       endAddress = startAddress + tryBlock.getCodeUnitCount();
/*     */ 
/* 464 */       if (startAddress >= codeSize) {
/* 465 */         throw new RuntimeException(String.format("Try start offset %d is past the end of the code block.", new Object[] { Integer.valueOf(startAddress) }));
/*     */       }
/*     */ 
/* 469 */       if (endAddress > codeSize) {
/* 470 */         throw new RuntimeException(String.format("Try end offset %d is past the end of the code block.", new Object[] { Integer.valueOf(endAddress) }));
/*     */       }
/*     */ 
/* 481 */       int lastCoveredIndex = this.instructionOffsetMap.getInstructionIndexAtCodeOffset(endAddress - 1, false);
/* 482 */       lastCoveredAddress = this.instructionOffsetMap.getInstructionCodeOffset(lastCoveredIndex);
/*     */ 
/* 484 */       for (ExceptionHandler handler : tryBlock.getExceptionHandlers()) {
/* 485 */         int handlerAddress = handler.getHandlerCodeAddress();
/* 486 */         if (handlerAddress >= codeSize) {
/* 487 */           throw new ExceptionWithContext("Exception handler offset %d is past the end of the code block.", new Object[] { Integer.valueOf(handlerAddress) });
/*     */         }
/*     */ 
/* 492 */         CatchMethodItem catchMethodItem = new CatchMethodItem(this.classDef.options, this.labelCache, lastCoveredAddress, handler.getExceptionType(), startAddress, endAddress, handlerAddress);
/*     */ 
/* 494 */         methodItems.add(catchMethodItem); }  } int startAddress;
/*     */     int endAddress;
/*     */     int lastCoveredAddress;
/*     */   }
/* 500 */   private void addDebugInfo(List<MethodItem> methodItems) { for (DebugItem debugItem : this.methodImpl.getDebugItems())
/* 501 */       methodItems.add(DebugMethodItem.build(this.registerFormatter, debugItem));
/*     */   }
/*     */ 
/*     */   private void setLabelSequentialNumbers()
/*     */   {
/* 506 */     HashMap nextLabelSequenceByType = new HashMap();
/* 507 */     ArrayList sortedLabels = new ArrayList(this.labelCache.getLabels());
/*     */ 
/* 510 */     Collections.sort(sortedLabels);
/*     */ 
/* 512 */     for (LabelMethodItem labelMethodItem : sortedLabels) {
/* 513 */       Integer labelSequence = (Integer)nextLabelSequenceByType.get(labelMethodItem.getLabelPrefix());
/* 514 */       if (labelSequence == null) {
/* 515 */         labelSequence = Integer.valueOf(0);
/*     */       }
/* 517 */       labelMethodItem.setLabelSequence(labelSequence.intValue());
/* 518 */       nextLabelSequenceByType.put(labelMethodItem.getLabelPrefix(), Integer.valueOf(labelSequence.intValue() + 1));
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class InvalidSwitchPayload extends ExceptionWithContext
/*     */   {
/*     */     private final int payloadOffset;
/*     */ 
/*     */     public InvalidSwitchPayload(int payloadOffset)
/*     */     {
/* 547 */       super(new Object[] { Integer.valueOf(payloadOffset) });
/* 548 */       this.payloadOffset = payloadOffset;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class LabelCache
/*     */   {
/* 523 */     protected HashMap<LabelMethodItem, LabelMethodItem> labels = new HashMap();
/*     */ 
/*     */     public LabelMethodItem internLabel(LabelMethodItem labelMethodItem)
/*     */     {
/* 529 */       LabelMethodItem internedLabelMethodItem = (LabelMethodItem)this.labels.get(labelMethodItem);
/* 530 */       if (internedLabelMethodItem != null) {
/* 531 */         return internedLabelMethodItem;
/*     */       }
/* 533 */       this.labels.put(labelMethodItem, labelMethodItem);
/* 534 */       return labelMethodItem;
/*     */     }
/*     */ 
/*     */     public Collection<LabelMethodItem> getLabels()
/*     */     {
/* 539 */       return this.labels.values();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.MethodDefinition
 * JD-Core Version:    0.6.0
 */