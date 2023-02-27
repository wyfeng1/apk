/*      */ package org.jf.dexlib2.analysis;
/*      */ 
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import java.util.BitSet;
/*      */ import java.util.List;
/*      */ import org.jf.dexlib2.AccessFlags;
/*      */ import org.jf.dexlib2.Format;
/*      */ import org.jf.dexlib2.Opcode;
/*      */ import org.jf.dexlib2.iface.ClassDef;
/*      */ import org.jf.dexlib2.iface.ExceptionHandler;
/*      */ import org.jf.dexlib2.iface.Method;
/*      */ import org.jf.dexlib2.iface.MethodImplementation;
/*      */ import org.jf.dexlib2.iface.MethodParameter;
/*      */ import org.jf.dexlib2.iface.TryBlock;
/*      */ import org.jf.dexlib2.iface.instruction.FiveRegisterInstruction;
/*      */ import org.jf.dexlib2.iface.instruction.Instruction;
/*      */ import org.jf.dexlib2.iface.instruction.NarrowLiteralInstruction;
/*      */ import org.jf.dexlib2.iface.instruction.OffsetInstruction;
/*      */ import org.jf.dexlib2.iface.instruction.OneRegisterInstruction;
/*      */ import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
/*      */ import org.jf.dexlib2.iface.instruction.RegisterRangeInstruction;
/*      */ import org.jf.dexlib2.iface.instruction.SwitchElement;
/*      */ import org.jf.dexlib2.iface.instruction.SwitchPayload;
/*      */ import org.jf.dexlib2.iface.instruction.ThreeRegisterInstruction;
/*      */ import org.jf.dexlib2.iface.instruction.TwoRegisterInstruction;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction10x;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction22c;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction22cs;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction35c;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction35mi;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction35ms;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction3rc;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction3rmi;
/*      */ import org.jf.dexlib2.iface.instruction.formats.Instruction3rms;
/*      */ import org.jf.dexlib2.iface.reference.FieldReference;
/*      */ import org.jf.dexlib2.iface.reference.MethodReference;
/*      */ import org.jf.dexlib2.iface.reference.Reference;
/*      */ import org.jf.dexlib2.iface.reference.TypeReference;
/*      */ import org.jf.dexlib2.immutable.instruction.ImmutableInstruction10x;
/*      */ import org.jf.dexlib2.immutable.instruction.ImmutableInstruction21c;
/*      */ import org.jf.dexlib2.immutable.instruction.ImmutableInstruction22c;
/*      */ import org.jf.dexlib2.immutable.instruction.ImmutableInstruction35c;
/*      */ import org.jf.dexlib2.immutable.instruction.ImmutableInstruction3rc;
/*      */ import org.jf.dexlib2.immutable.reference.ImmutableFieldReference;
/*      */ import org.jf.dexlib2.immutable.reference.ImmutableMethodReference;
/*      */ import org.jf.dexlib2.util.MethodUtil;
/*      */ import org.jf.dexlib2.util.ReferenceUtil;
/*      */ import org.jf.dexlib2.util.TypeUtils;
/*      */ import org.jf.util.BitSetUtils;
/*      */ import org.jf.util.ExceptionWithContext;
/*      */ import org.jf.util.SparseArray;
/*      */ 
/*      */ public class MethodAnalyzer
/*      */ {
/*      */   private final Method method;
/*      */   private final MethodImplementation methodImpl;
/*      */   private final int paramRegisterCount;
/*      */   private final ClassPath classPath;
/*      */   private final InlineMethodResolver inlineResolver;
/*   81 */   private final SparseArray<AnalyzedInstruction> analyzedInstructions = new SparseArray(0);
/*      */   private final BitSet analyzedState;
/*   87 */   private AnalysisException analysisException = null;
/*      */   private final AnalyzedInstruction startOfMethod;
/*      */   private static final BitSet Primitive32BitCategories;
/*      */   private static final BitSet WideLowCategories;
/*      */   private static final BitSet WideHighCategories;
/*      */   private static final BitSet ReferenceOrUninitCategories;
/*      */   private static final BitSet BooleanCategories;
/*      */ 
/*      */   public MethodAnalyzer(ClassPath classPath, Method method, InlineMethodResolver inlineResolver)
/*      */   {
/*   97 */     this.classPath = classPath;
/*   98 */     this.inlineResolver = inlineResolver;
/*      */ 
/*  100 */     this.method = method;
/*      */ 
/*  102 */     MethodImplementation methodImpl = method.getImplementation();
/*  103 */     if (methodImpl == null) {
/*  104 */       throw new IllegalArgumentException("The method has no implementation");
/*      */     }
/*      */ 
/*  107 */     this.methodImpl = methodImpl;
/*      */ 
/*  111 */     this.startOfMethod = new AnalyzedInstruction(null, -1, methodImpl.getRegisterCount()) {
/*      */       public boolean setsRegister() {
/*  113 */         return false;
/*      */       }
/*      */ 
/*      */       public boolean setsWideRegister()
/*      */       {
/*  118 */         return false;
/*      */       }
/*      */ 
/*      */       public boolean setsRegister(int registerNumber)
/*      */       {
/*  123 */         return false;
/*      */       }
/*      */ 
/*      */       public int getDestinationRegister()
/*      */       {
/*  128 */         if (!$assertionsDisabled) throw new AssertionError();
/*  129 */         return -1;
/*      */       }
/*      */     };
/*  133 */     buildInstructionList();
/*      */ 
/*  135 */     this.analyzedState = new BitSet(this.analyzedInstructions.size());
/*  136 */     this.paramRegisterCount = MethodUtil.getParameterRegisterCount(method);
/*  137 */     analyze();
/*      */   }
/*      */ 
/*      */   private void analyze() {
/*  141 */     Method method = this.method;
/*  142 */     MethodImplementation methodImpl = this.methodImpl;
/*      */ 
/*  144 */     int totalRegisters = methodImpl.getRegisterCount();
/*  145 */     int parameterRegisters = this.paramRegisterCount;
/*      */ 
/*  147 */     int nonParameterRegisters = totalRegisters - parameterRegisters;
/*      */ 
/*  151 */     if (!MethodUtil.isStatic(method)) {
/*  152 */       int thisRegister = totalRegisters - parameterRegisters;
/*      */ 
/*  155 */       if (MethodUtil.isConstructor(method)) {
/*  156 */         setPostRegisterTypeAndPropagateChanges(this.startOfMethod, thisRegister, RegisterType.getRegisterType(17, this.classPath.getClass(method.getDefiningClass())));
/*      */       }
/*      */       else
/*      */       {
/*  160 */         setPostRegisterTypeAndPropagateChanges(this.startOfMethod, thisRegister, RegisterType.getRegisterType(18, this.classPath.getClass(method.getDefiningClass())));
/*      */       }
/*      */ 
/*  165 */       propagateParameterTypes(totalRegisters - parameterRegisters + 1);
/*      */     } else {
/*  167 */       propagateParameterTypes(totalRegisters - parameterRegisters);
/*      */     }
/*      */ 
/*  170 */     RegisterType uninit = RegisterType.getRegisterType(1, null);
/*  171 */     for (int i = 0; i < nonParameterRegisters; i++) {
/*  172 */       setPostRegisterTypeAndPropagateChanges(this.startOfMethod, i, uninit);
/*      */     }
/*      */ 
/*  175 */     BitSet instructionsToAnalyze = new BitSet(this.analyzedInstructions.size());
/*      */ 
/*  178 */     for (AnalyzedInstruction successor : this.startOfMethod.successors) {
/*  179 */       instructionsToAnalyze.set(successor.instructionIndex);
/*      */     }
/*      */ 
/*  182 */     BitSet undeodexedInstructions = new BitSet(this.analyzedInstructions.size());
/*      */     while (true)
/*      */     {
/*  185 */       boolean didSomething = false;
/*      */ 
/*  187 */       while (!instructionsToAnalyze.isEmpty()) {
/*  188 */         for (int i = instructionsToAnalyze.nextSetBit(0); i >= 0; i = instructionsToAnalyze.nextSetBit(i + 1)) {
/*  189 */           instructionsToAnalyze.clear(i);
/*  190 */           if (this.analyzedState.get(i)) {
/*      */             continue;
/*      */           }
/*  193 */           AnalyzedInstruction instructionToAnalyze = (AnalyzedInstruction)this.analyzedInstructions.valueAt(i);
/*      */           try {
/*  195 */             if (instructionToAnalyze.originalInstruction.getOpcode().odexOnly())
/*      */             {
/*  199 */               instructionToAnalyze.restoreOdexedInstruction();
/*      */             }
/*      */ 
/*  202 */             if (!analyzeInstruction(instructionToAnalyze)) {
/*  203 */               undeodexedInstructions.set(i);
/*  204 */               continue;
/*      */             }
/*  206 */             didSomething = true;
/*  207 */             undeodexedInstructions.clear(i);
/*      */           }
/*      */           catch (AnalysisException ex) {
/*  210 */             this.analysisException = ex;
/*  211 */             int codeAddress = getInstructionAddress(instructionToAnalyze);
/*  212 */             ex.codeAddress = codeAddress;
/*  213 */             ex.addContext(String.format("opcode: %s", new Object[] { instructionToAnalyze.instruction.getOpcode().name }));
/*  214 */             ex.addContext(String.format("code address: %d", new Object[] { Integer.valueOf(codeAddress) }));
/*  215 */             ex.addContext(String.format("method: %s", new Object[] { ReferenceUtil.getReferenceString(method) }));
/*  216 */             break;
/*      */           }
/*      */ 
/*  219 */           this.analyzedState.set(instructionToAnalyze.getInstructionIndex());
/*      */ 
/*  221 */           for (AnalyzedInstruction successor : instructionToAnalyze.successors) {
/*  222 */             instructionsToAnalyze.set(successor.getInstructionIndex());
/*      */           }
/*      */         }
/*  225 */         if (this.analysisException != null) {
/*  226 */           break;
/*      */         }
/*      */       }
/*      */ 
/*  230 */       if (!didSomething)
/*      */       {
/*      */         break;
/*      */       }
/*  234 */       if (!undeodexedInstructions.isEmpty()) {
/*  235 */         for (int i = undeodexedInstructions.nextSetBit(0); i >= 0; i = undeodexedInstructions.nextSetBit(i + 1)) {
/*  236 */           instructionsToAnalyze.set(i);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  245 */     for (int i = 0; i < this.analyzedInstructions.size(); i++) {
/*  246 */       AnalyzedInstruction analyzedInstruction = (AnalyzedInstruction)this.analyzedInstructions.valueAt(i);
/*      */ 
/*  248 */       Instruction instruction = analyzedInstruction.getInstruction();
/*      */ 
/*  250 */       if (!instruction.getOpcode().odexOnly())
/*      */         continue;
/*      */       int objectRegisterNumber;
/*  252 */       switch (3.$SwitchMap$org$jf$dexlib2$Format[instruction.getOpcode().format.ordinal()]) {
/*      */       case 1:
/*  254 */         analyzeReturnVoidBarrier(analyzedInstruction, false);
/*  255 */         break;
/*      */       case 2:
/*      */       case 3:
/*  258 */         analyzePutGetVolatile(analyzedInstruction, false);
/*  259 */         break;
/*      */       case 4:
/*  261 */         analyzeInvokeDirectEmpty(analyzedInstruction, false);
/*  262 */         break;
/*      */       case 5:
/*  264 */         analyzeInvokeObjectInitRange(analyzedInstruction, false);
/*  265 */         break;
/*      */       case 6:
/*  267 */         objectRegisterNumber = ((Instruction22cs)instruction).getRegisterB();
/*  268 */         break;
/*      */       case 7:
/*      */       case 8:
/*  271 */         objectRegisterNumber = ((FiveRegisterInstruction)instruction).getRegisterC();
/*  272 */         break;
/*      */       case 9:
/*      */       case 10:
/*  275 */         objectRegisterNumber = ((RegisterRangeInstruction)instruction).getStartRegister();
/*  276 */         break;
/*      */       default:
/*  278 */         break;
/*      */       }
/*      */ 
/*  281 */       analyzedInstruction.setDeodexedInstruction(new UnresolvedOdexInstruction(instruction, objectRegisterNumber));
/*      */     }
/*      */   }
/*      */ 
/*      */   private void propagateParameterTypes(int parameterStartRegister)
/*      */   {
/*  288 */     int i = 0;
/*  289 */     for (MethodParameter parameter : this.method.getParameters())
/*  290 */       if (TypeUtils.isWideType(parameter)) {
/*  291 */         setPostRegisterTypeAndPropagateChanges(this.startOfMethod, parameterStartRegister + i++, RegisterType.getWideRegisterType(parameter, true));
/*      */ 
/*  293 */         setPostRegisterTypeAndPropagateChanges(this.startOfMethod, parameterStartRegister + i++, RegisterType.getWideRegisterType(parameter, false));
/*      */       }
/*      */       else {
/*  296 */         setPostRegisterTypeAndPropagateChanges(this.startOfMethod, parameterStartRegister + i++, RegisterType.getRegisterType(this.classPath, parameter));
/*      */       }
/*      */   }
/*      */ 
/*      */   public List<AnalyzedInstruction> getAnalyzedInstructions()
/*      */   {
/*  303 */     return this.analyzedInstructions.getValues();
/*      */   }
/*      */ 
/*      */   public AnalysisException getAnalysisException()
/*      */   {
/*  319 */     return this.analysisException;
/*      */   }
/*      */ 
/*      */   public int getParamRegisterCount() {
/*  323 */     return this.paramRegisterCount;
/*      */   }
/*      */ 
/*      */   public int getInstructionAddress(AnalyzedInstruction instruction) {
/*  327 */     return this.analyzedInstructions.keyAt(instruction.instructionIndex);
/*      */   }
/*      */ 
/*      */   private void setDestinationRegisterTypeAndPropagateChanges(AnalyzedInstruction analyzedInstruction, RegisterType registerType)
/*      */   {
/*  332 */     setPostRegisterTypeAndPropagateChanges(analyzedInstruction, analyzedInstruction.getDestinationRegister(), registerType);
/*      */   }
/*      */ 
/*      */   private void setPostRegisterTypeAndPropagateChanges(AnalyzedInstruction analyzedInstruction, int registerNumber, RegisterType registerType)
/*      */   {
/*  339 */     BitSet changedInstructions = new BitSet(this.analyzedInstructions.size());
/*      */ 
/*  341 */     if (!analyzedInstruction.setPostRegisterType(registerNumber, registerType)) {
/*  342 */       return;
/*      */     }
/*      */ 
/*  345 */     propagateRegisterToSuccessors(analyzedInstruction, registerNumber, changedInstructions);
/*      */ 
/*  353 */     while (!changedInstructions.isEmpty()) {
/*  354 */       int instructionIndex = changedInstructions.nextSetBit(0);
/*  355 */       while (instructionIndex >= 0)
/*      */       {
/*  358 */         changedInstructions.clear(instructionIndex);
/*      */ 
/*  360 */         propagateRegisterToSuccessors((AnalyzedInstruction)this.analyzedInstructions.valueAt(instructionIndex), registerNumber, changedInstructions);
/*      */ 
/*  356 */         instructionIndex = changedInstructions.nextSetBit(instructionIndex + 1);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  365 */     if (registerType.category == 12) {
/*  366 */       checkWidePair(registerNumber, analyzedInstruction);
/*  367 */       setPostRegisterTypeAndPropagateChanges(analyzedInstruction, registerNumber + 1, RegisterType.LONG_HI_TYPE);
/*  368 */     } else if (registerType.category == 14) {
/*  369 */       checkWidePair(registerNumber, analyzedInstruction);
/*  370 */       setPostRegisterTypeAndPropagateChanges(analyzedInstruction, registerNumber + 1, RegisterType.DOUBLE_HI_TYPE);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void propagateRegisterToSuccessors(AnalyzedInstruction instruction, int registerNumber, BitSet changedInstructions)
/*      */   {
/*  376 */     RegisterType postRegisterType = instruction.getPostInstructionRegisterType(registerNumber);
/*  377 */     for (AnalyzedInstruction successor : instruction.successors)
/*  378 */       if (successor.mergeRegister(registerNumber, postRegisterType, this.analyzedState))
/*  379 */         changedInstructions.set(successor.instructionIndex);
/*      */   }
/*      */ 
/*      */   private void buildInstructionList()
/*      */   {
/*  385 */     int registerCount = this.methodImpl.getRegisterCount();
/*      */ 
/*  387 */     ImmutableList instructions = ImmutableList.copyOf(this.methodImpl.getInstructions());
/*      */ 
/*  389 */     this.analyzedInstructions.ensureCapacity(instructions.size());
/*      */ 
/*  392 */     int currentCodeAddress = 0;
/*  393 */     for (int i = 0; i < instructions.size(); i++) {
/*  394 */       Instruction instruction = (Instruction)instructions.get(i);
/*  395 */       this.analyzedInstructions.append(currentCodeAddress, new AnalyzedInstruction(instruction, i, registerCount));
/*  396 */       assert (this.analyzedInstructions.indexOfKey(currentCodeAddress) == i);
/*  397 */       currentCodeAddress += instruction.getCodeUnits();
/*      */     }
/*      */ 
/*  403 */     List tries = this.methodImpl.getTryBlocks();
/*  404 */     int triesIndex = 0;
/*  405 */     TryBlock currentTry = null;
/*  406 */     AnalyzedInstruction[] currentExceptionHandlers = null;
/*  407 */     AnalyzedInstruction[][] exceptionHandlers = new AnalyzedInstruction[instructions.size()][];
/*      */ 
/*  409 */     if (tries != null) {
/*  410 */       for (int i = 0; i < this.analyzedInstructions.size(); i++) {
/*  411 */         AnalyzedInstruction instruction = (AnalyzedInstruction)this.analyzedInstructions.valueAt(i);
/*  412 */         Opcode instructionOpcode = instruction.instruction.getOpcode();
/*  413 */         currentCodeAddress = getInstructionAddress(instruction);
/*      */ 
/*  416 */         if ((currentTry != null) && 
/*  417 */           (currentTry.getStartCodeAddress() + currentTry.getCodeUnitCount() <= currentCodeAddress)) {
/*  418 */           currentTry = null;
/*  419 */           triesIndex++;
/*      */         }
/*      */ 
/*  424 */         if ((currentTry == null) && (triesIndex < tries.size())) {
/*  425 */           TryBlock tryBlock = (TryBlock)tries.get(triesIndex);
/*  426 */           if (tryBlock.getStartCodeAddress() <= currentCodeAddress) {
/*  427 */             assert (tryBlock.getStartCodeAddress() + tryBlock.getCodeUnitCount() > currentCodeAddress);
/*      */ 
/*  429 */             currentTry = tryBlock;
/*      */ 
/*  431 */             currentExceptionHandlers = buildExceptionHandlerArray(tryBlock);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  437 */         if ((currentTry != null) && (instructionOpcode.canThrow())) {
/*  438 */           exceptionHandlers[i] = currentExceptionHandlers;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  446 */     assert (this.analyzedInstructions.size() > 0);
/*  447 */     BitSet instructionsToProcess = new BitSet(instructions.size());
/*      */ 
/*  449 */     addPredecessorSuccessor(this.startOfMethod, (AnalyzedInstruction)this.analyzedInstructions.valueAt(0), exceptionHandlers, instructionsToProcess);
/*  450 */     while (!instructionsToProcess.isEmpty()) {
/*  451 */       int currentInstructionIndex = instructionsToProcess.nextSetBit(0);
/*  452 */       instructionsToProcess.clear(currentInstructionIndex);
/*      */ 
/*  454 */       AnalyzedInstruction instruction = (AnalyzedInstruction)this.analyzedInstructions.valueAt(currentInstructionIndex);
/*  455 */       Opcode instructionOpcode = instruction.instruction.getOpcode();
/*  456 */       int instructionCodeAddress = getInstructionAddress(instruction);
/*      */ 
/*  458 */       if (instruction.instruction.getOpcode().canContinue()) {
/*  459 */         if (currentInstructionIndex == this.analyzedInstructions.size() - 1) {
/*  460 */           throw new AnalysisException("Execution can continue past the last instruction", new Object[0]);
/*      */         }
/*      */ 
/*  463 */         AnalyzedInstruction nextInstruction = (AnalyzedInstruction)this.analyzedInstructions.valueAt(currentInstructionIndex + 1);
/*  464 */         addPredecessorSuccessor(instruction, nextInstruction, exceptionHandlers, instructionsToProcess);
/*      */       }
/*      */ 
/*  467 */       if ((instruction.instruction instanceof OffsetInstruction)) {
/*  468 */         OffsetInstruction offsetInstruction = (OffsetInstruction)instruction.instruction;
/*      */ 
/*  470 */         if ((instructionOpcode == Opcode.PACKED_SWITCH) || (instructionOpcode == Opcode.SPARSE_SWITCH)) {
/*  471 */           SwitchPayload switchPayload = (SwitchPayload)((AnalyzedInstruction)this.analyzedInstructions.get(instructionCodeAddress + offsetInstruction.getCodeOffset())).instruction;
/*      */ 
/*  473 */           for (SwitchElement switchElement : switchPayload.getSwitchElements()) {
/*  474 */             AnalyzedInstruction targetInstruction = (AnalyzedInstruction)this.analyzedInstructions.get(instructionCodeAddress + switchElement.getOffset());
/*      */ 
/*  477 */             addPredecessorSuccessor(instruction, targetInstruction, exceptionHandlers, instructionsToProcess);
/*      */           }
/*      */         }
/*  480 */         else if (instructionOpcode != Opcode.FILL_ARRAY_DATA) {
/*  481 */           int targetAddressOffset = offsetInstruction.getCodeOffset();
/*  482 */           AnalyzedInstruction targetInstruction = (AnalyzedInstruction)this.analyzedInstructions.get(instructionCodeAddress + targetAddressOffset);
/*      */ 
/*  484 */           addPredecessorSuccessor(instruction, targetInstruction, exceptionHandlers, instructionsToProcess);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addPredecessorSuccessor(AnalyzedInstruction predecessor, AnalyzedInstruction successor, AnalyzedInstruction[][] exceptionHandlers, BitSet instructionsToProcess)
/*      */   {
/*  494 */     addPredecessorSuccessor(predecessor, successor, exceptionHandlers, instructionsToProcess, false);
/*      */   }
/*      */ 
/*      */   private void addPredecessorSuccessor(AnalyzedInstruction predecessor, AnalyzedInstruction successor, AnalyzedInstruction[][] exceptionHandlers, BitSet instructionsToProcess, boolean allowMoveException)
/*      */   {
/*  502 */     if ((!allowMoveException) && (successor.instruction.getOpcode() == Opcode.MOVE_EXCEPTION)) {
/*  503 */       throw new AnalysisException("Execution can pass from the " + predecessor.instruction.getOpcode().name + " instruction at code address 0x" + Integer.toHexString(getInstructionAddress(predecessor)) + " to the move-exception instruction at address 0x" + Integer.toHexString(getInstructionAddress(successor)), new Object[0]);
/*      */     }
/*      */ 
/*  509 */     if (!successor.addPredecessor(predecessor)) {
/*  510 */       return;
/*      */     }
/*      */ 
/*  513 */     predecessor.addSuccessor(successor);
/*  514 */     instructionsToProcess.set(successor.getInstructionIndex());
/*      */ 
/*  522 */     AnalyzedInstruction[] exceptionHandlersForSuccessor = exceptionHandlers[successor.instructionIndex];
/*  523 */     if (exceptionHandlersForSuccessor != null)
/*      */     {
/*  526 */       assert (successor.instruction.getOpcode().canThrow());
/*      */ 
/*  528 */       for (AnalyzedInstruction exceptionHandler : exceptionHandlersForSuccessor)
/*  529 */         addPredecessorSuccessor(predecessor, exceptionHandler, exceptionHandlers, instructionsToProcess, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   private AnalyzedInstruction[] buildExceptionHandlerArray(TryBlock<? extends ExceptionHandler> tryBlock)
/*      */   {
/*  536 */     List exceptionHandlers = tryBlock.getExceptionHandlers();
/*      */ 
/*  538 */     AnalyzedInstruction[] handlerInstructions = new AnalyzedInstruction[exceptionHandlers.size()];
/*  539 */     for (int i = 0; i < exceptionHandlers.size(); i++) {
/*  540 */       handlerInstructions[i] = ((AnalyzedInstruction)this.analyzedInstructions.get(((ExceptionHandler)exceptionHandlers.get(i)).getHandlerCodeAddress()));
/*      */     }
/*      */ 
/*  543 */     return handlerInstructions;
/*      */   }
/*      */ 
/*      */   private boolean analyzeInstruction(AnalyzedInstruction analyzedInstruction)
/*      */   {
/*  551 */     Instruction instruction = analyzedInstruction.instruction;
/*      */ 
/*  553 */     switch (3.$SwitchMap$org$jf$dexlib2$Opcode[instruction.getOpcode().ordinal()]) {
/*      */     case 1:
/*  555 */       return true;
/*      */     case 2:
/*      */     case 3:
/*      */     case 4:
/*      */     case 5:
/*      */     case 6:
/*      */     case 7:
/*      */     case 8:
/*      */     case 9:
/*      */     case 10:
/*  565 */       analyzeMove(analyzedInstruction);
/*  566 */       return true;
/*      */     case 11:
/*      */     case 12:
/*      */     case 13:
/*  570 */       analyzeMoveResult(analyzedInstruction);
/*  571 */       return true;
/*      */     case 14:
/*  573 */       analyzeMoveException(analyzedInstruction);
/*  574 */       return true;
/*      */     case 15:
/*      */     case 16:
/*      */     case 17:
/*      */     case 18:
/*  579 */       return true;
/*      */     case 19:
/*  581 */       analyzeReturnVoidBarrier(analyzedInstruction);
/*  582 */       return true;
/*      */     case 20:
/*      */     case 21:
/*      */     case 22:
/*      */     case 23:
/*  587 */       analyzeConst(analyzedInstruction);
/*  588 */       return true;
/*      */     case 24:
/*      */     case 25:
/*      */     case 26:
/*      */     case 27:
/*  593 */       analyzeWideConst(analyzedInstruction);
/*  594 */       return true;
/*      */     case 28:
/*      */     case 29:
/*  597 */       analyzeConstString(analyzedInstruction);
/*  598 */       return true;
/*      */     case 30:
/*  600 */       analyzeConstClass(analyzedInstruction);
/*  601 */       return true;
/*      */     case 31:
/*      */     case 32:
/*  604 */       return true;
/*      */     case 33:
/*  606 */       analyzeCheckCast(analyzedInstruction);
/*  607 */       return true;
/*      */     case 34:
/*  609 */       analyzeInstanceOf(analyzedInstruction);
/*  610 */       return true;
/*      */     case 35:
/*  612 */       analyzeArrayLength(analyzedInstruction);
/*  613 */       return true;
/*      */     case 36:
/*  615 */       analyzeNewInstance(analyzedInstruction);
/*  616 */       return true;
/*      */     case 37:
/*  618 */       analyzeNewArray(analyzedInstruction);
/*  619 */       return true;
/*      */     case 38:
/*      */     case 39:
/*  622 */       return true;
/*      */     case 40:
/*  624 */       return true;
/*      */     case 41:
/*      */     case 42:
/*      */     case 43:
/*      */     case 44:
/*  629 */       return true;
/*      */     case 45:
/*      */     case 46:
/*  632 */       return true;
/*      */     case 47:
/*      */     case 48:
/*      */     case 49:
/*      */     case 50:
/*      */     case 51:
/*  638 */       analyzeFloatWideCmp(analyzedInstruction);
/*  639 */       return true;
/*      */     case 52:
/*      */     case 53:
/*      */     case 54:
/*      */     case 55:
/*      */     case 56:
/*      */     case 57:
/*      */     case 58:
/*      */     case 59:
/*      */     case 60:
/*      */     case 61:
/*      */     case 62:
/*      */     case 63:
/*  652 */       return true;
/*      */     case 64:
/*  654 */       analyze32BitPrimitiveAget(analyzedInstruction, RegisterType.INTEGER_TYPE);
/*  655 */       return true;
/*      */     case 65:
/*  657 */       analyze32BitPrimitiveAget(analyzedInstruction, RegisterType.BOOLEAN_TYPE);
/*  658 */       return true;
/*      */     case 66:
/*  660 */       analyze32BitPrimitiveAget(analyzedInstruction, RegisterType.BYTE_TYPE);
/*  661 */       return true;
/*      */     case 67:
/*  663 */       analyze32BitPrimitiveAget(analyzedInstruction, RegisterType.CHAR_TYPE);
/*  664 */       return true;
/*      */     case 68:
/*  666 */       analyze32BitPrimitiveAget(analyzedInstruction, RegisterType.SHORT_TYPE);
/*  667 */       return true;
/*      */     case 69:
/*  669 */       analyzeAgetWide(analyzedInstruction);
/*  670 */       return true;
/*      */     case 70:
/*  672 */       analyzeAgetObject(analyzedInstruction);
/*  673 */       return true;
/*      */     case 71:
/*      */     case 72:
/*      */     case 73:
/*      */     case 74:
/*      */     case 75:
/*      */     case 76:
/*      */     case 77:
/*  681 */       return true;
/*      */     case 78:
/*  683 */       analyze32BitPrimitiveIgetSget(analyzedInstruction, RegisterType.INTEGER_TYPE);
/*  684 */       return true;
/*      */     case 79:
/*  686 */       analyze32BitPrimitiveIgetSget(analyzedInstruction, RegisterType.BOOLEAN_TYPE);
/*  687 */       return true;
/*      */     case 80:
/*  689 */       analyze32BitPrimitiveIgetSget(analyzedInstruction, RegisterType.BYTE_TYPE);
/*  690 */       return true;
/*      */     case 81:
/*  692 */       analyze32BitPrimitiveIgetSget(analyzedInstruction, RegisterType.CHAR_TYPE);
/*  693 */       return true;
/*      */     case 82:
/*  695 */       analyze32BitPrimitiveIgetSget(analyzedInstruction, RegisterType.SHORT_TYPE);
/*  696 */       return true;
/*      */     case 83:
/*      */     case 84:
/*  699 */       analyzeIgetSgetWideObject(analyzedInstruction);
/*  700 */       return true;
/*      */     case 85:
/*      */     case 86:
/*      */     case 87:
/*      */     case 88:
/*      */     case 89:
/*      */     case 90:
/*      */     case 91:
/*  708 */       return true;
/*      */     case 92:
/*  710 */       analyze32BitPrimitiveIgetSget(analyzedInstruction, RegisterType.INTEGER_TYPE);
/*  711 */       return true;
/*      */     case 93:
/*  713 */       analyze32BitPrimitiveIgetSget(analyzedInstruction, RegisterType.BOOLEAN_TYPE);
/*  714 */       return true;
/*      */     case 94:
/*  716 */       analyze32BitPrimitiveIgetSget(analyzedInstruction, RegisterType.BYTE_TYPE);
/*  717 */       return true;
/*      */     case 95:
/*  719 */       analyze32BitPrimitiveIgetSget(analyzedInstruction, RegisterType.CHAR_TYPE);
/*  720 */       return true;
/*      */     case 96:
/*  722 */       analyze32BitPrimitiveIgetSget(analyzedInstruction, RegisterType.SHORT_TYPE);
/*  723 */       return true;
/*      */     case 97:
/*      */     case 98:
/*  726 */       analyzeIgetSgetWideObject(analyzedInstruction);
/*  727 */       return true;
/*      */     case 99:
/*      */     case 100:
/*      */     case 101:
/*      */     case 102:
/*      */     case 103:
/*      */     case 104:
/*      */     case 105:
/*  735 */       return true;
/*      */     case 106:
/*      */     case 107:
/*  738 */       return true;
/*      */     case 108:
/*  740 */       analyzeInvokeDirect(analyzedInstruction);
/*  741 */       return true;
/*      */     case 109:
/*      */     case 110:
/*      */     case 111:
/*      */     case 112:
/*  746 */       return true;
/*      */     case 113:
/*  748 */       analyzeInvokeDirectRange(analyzedInstruction);
/*  749 */       return true;
/*      */     case 114:
/*      */     case 115:
/*  752 */       return true;
/*      */     case 116:
/*      */     case 117:
/*  755 */       analyzeUnaryOp(analyzedInstruction, RegisterType.INTEGER_TYPE);
/*  756 */       return true;
/*      */     case 118:
/*      */     case 119:
/*  759 */       analyzeUnaryOp(analyzedInstruction, RegisterType.LONG_LO_TYPE);
/*  760 */       return true;
/*      */     case 120:
/*  762 */       analyzeUnaryOp(analyzedInstruction, RegisterType.FLOAT_TYPE);
/*  763 */       return true;
/*      */     case 121:
/*  765 */       analyzeUnaryOp(analyzedInstruction, RegisterType.DOUBLE_LO_TYPE);
/*  766 */       return true;
/*      */     case 122:
/*  768 */       analyzeUnaryOp(analyzedInstruction, RegisterType.LONG_LO_TYPE);
/*  769 */       return true;
/*      */     case 123:
/*  771 */       analyzeUnaryOp(analyzedInstruction, RegisterType.FLOAT_TYPE);
/*  772 */       return true;
/*      */     case 124:
/*  774 */       analyzeUnaryOp(analyzedInstruction, RegisterType.DOUBLE_LO_TYPE);
/*  775 */       return true;
/*      */     case 125:
/*      */     case 126:
/*  778 */       analyzeUnaryOp(analyzedInstruction, RegisterType.INTEGER_TYPE);
/*  779 */       return true;
/*      */     case 127:
/*      */     case 128:
/*  782 */       analyzeUnaryOp(analyzedInstruction, RegisterType.FLOAT_TYPE);
/*  783 */       return true;
/*      */     case 129:
/*  785 */       analyzeUnaryOp(analyzedInstruction, RegisterType.DOUBLE_LO_TYPE);
/*  786 */       return true;
/*      */     case 130:
/*  788 */       analyzeUnaryOp(analyzedInstruction, RegisterType.INTEGER_TYPE);
/*  789 */       return true;
/*      */     case 131:
/*  791 */       analyzeUnaryOp(analyzedInstruction, RegisterType.LONG_LO_TYPE);
/*  792 */       return true;
/*      */     case 132:
/*  794 */       analyzeUnaryOp(analyzedInstruction, RegisterType.DOUBLE_LO_TYPE);
/*  795 */       return true;
/*      */     case 133:
/*  797 */       analyzeUnaryOp(analyzedInstruction, RegisterType.LONG_LO_TYPE);
/*  798 */       return true;
/*      */     case 134:
/*  800 */       analyzeUnaryOp(analyzedInstruction, RegisterType.BYTE_TYPE);
/*  801 */       return true;
/*      */     case 135:
/*  803 */       analyzeUnaryOp(analyzedInstruction, RegisterType.CHAR_TYPE);
/*  804 */       return true;
/*      */     case 136:
/*  806 */       analyzeUnaryOp(analyzedInstruction, RegisterType.SHORT_TYPE);
/*  807 */       return true;
/*      */     case 137:
/*      */     case 138:
/*      */     case 139:
/*      */     case 140:
/*      */     case 141:
/*      */     case 142:
/*      */     case 143:
/*      */     case 144:
/*  816 */       analyzeBinaryOp(analyzedInstruction, RegisterType.INTEGER_TYPE, false);
/*  817 */       return true;
/*      */     case 145:
/*      */     case 146:
/*      */     case 147:
/*  821 */       analyzeBinaryOp(analyzedInstruction, RegisterType.INTEGER_TYPE, true);
/*  822 */       return true;
/*      */     case 148:
/*      */     case 149:
/*      */     case 150:
/*      */     case 151:
/*      */     case 152:
/*      */     case 153:
/*      */     case 154:
/*      */     case 155:
/*      */     case 156:
/*      */     case 157:
/*      */     case 158:
/*  834 */       analyzeBinaryOp(analyzedInstruction, RegisterType.LONG_LO_TYPE, false);
/*  835 */       return true;
/*      */     case 159:
/*      */     case 160:
/*      */     case 161:
/*      */     case 162:
/*      */     case 163:
/*  841 */       analyzeBinaryOp(analyzedInstruction, RegisterType.FLOAT_TYPE, false);
/*  842 */       return true;
/*      */     case 164:
/*      */     case 165:
/*      */     case 166:
/*      */     case 167:
/*      */     case 168:
/*  848 */       analyzeBinaryOp(analyzedInstruction, RegisterType.DOUBLE_LO_TYPE, false);
/*  849 */       return true;
/*      */     case 169:
/*      */     case 170:
/*      */     case 171:
/*      */     case 172:
/*      */     case 173:
/*      */     case 174:
/*      */     case 175:
/*      */     case 176:
/*  858 */       analyzeBinary2AddrOp(analyzedInstruction, RegisterType.INTEGER_TYPE, false);
/*  859 */       return true;
/*      */     case 177:
/*      */     case 178:
/*      */     case 179:
/*  863 */       analyzeBinary2AddrOp(analyzedInstruction, RegisterType.INTEGER_TYPE, true);
/*  864 */       return true;
/*      */     case 180:
/*      */     case 181:
/*      */     case 182:
/*      */     case 183:
/*      */     case 184:
/*      */     case 185:
/*      */     case 186:
/*      */     case 187:
/*      */     case 188:
/*      */     case 189:
/*      */     case 190:
/*  876 */       analyzeBinary2AddrOp(analyzedInstruction, RegisterType.LONG_LO_TYPE, false);
/*  877 */       return true;
/*      */     case 191:
/*      */     case 192:
/*      */     case 193:
/*      */     case 194:
/*      */     case 195:
/*  883 */       analyzeBinary2AddrOp(analyzedInstruction, RegisterType.FLOAT_TYPE, false);
/*  884 */       return true;
/*      */     case 196:
/*      */     case 197:
/*      */     case 198:
/*      */     case 199:
/*      */     case 200:
/*  890 */       analyzeBinary2AddrOp(analyzedInstruction, RegisterType.DOUBLE_LO_TYPE, false);
/*  891 */       return true;
/*      */     case 201:
/*      */     case 202:
/*      */     case 203:
/*      */     case 204:
/*      */     case 205:
/*  897 */       analyzeLiteralBinaryOp(analyzedInstruction, RegisterType.INTEGER_TYPE, false);
/*  898 */       return true;
/*      */     case 206:
/*      */     case 207:
/*      */     case 208:
/*  902 */       analyzeLiteralBinaryOp(analyzedInstruction, RegisterType.INTEGER_TYPE, true);
/*  903 */       return true;
/*      */     case 209:
/*      */     case 210:
/*      */     case 211:
/*      */     case 212:
/*      */     case 213:
/*      */     case 214:
/*  910 */       analyzeLiteralBinaryOp(analyzedInstruction, RegisterType.INTEGER_TYPE, false);
/*  911 */       return true;
/*      */     case 215:
/*      */     case 216:
/*      */     case 217:
/*  915 */       analyzeLiteralBinaryOp(analyzedInstruction, RegisterType.INTEGER_TYPE, true);
/*  916 */       return true;
/*      */     case 218:
/*  918 */       analyzeLiteralBinaryOp(analyzedInstruction, getDestTypeForLiteralShiftRight(analyzedInstruction, true), false);
/*      */ 
/*  920 */       return true;
/*      */     case 219:
/*  922 */       analyzeLiteralBinaryOp(analyzedInstruction, getDestTypeForLiteralShiftRight(analyzedInstruction, false), false);
/*      */ 
/*  924 */       return true;
/*      */     case 220:
/*      */     case 221:
/*      */     case 222:
/*      */     case 223:
/*      */     case 224:
/*      */     case 225:
/*      */     case 226:
/*      */     case 227:
/*      */     case 228:
/*  936 */       analyzePutGetVolatile(analyzedInstruction);
/*  937 */       return true;
/*      */     case 229:
/*  939 */       return true;
/*      */     case 230:
/*  941 */       analyzeExecuteInline(analyzedInstruction);
/*  942 */       return true;
/*      */     case 231:
/*  944 */       analyzeExecuteInlineRange(analyzedInstruction);
/*  945 */       return true;
/*      */     case 232:
/*  947 */       analyzeInvokeDirectEmpty(analyzedInstruction);
/*  948 */       return true;
/*      */     case 233:
/*  950 */       analyzeInvokeObjectInitRange(analyzedInstruction);
/*  951 */       return true;
/*      */     case 234:
/*      */     case 235:
/*      */     case 236:
/*      */     case 237:
/*      */     case 238:
/*      */     case 239:
/*  958 */       return analyzeIputIgetQuick(analyzedInstruction);
/*      */     case 240:
/*  960 */       return analyzeInvokeVirtualQuick(analyzedInstruction, false, false);
/*      */     case 241:
/*  962 */       return analyzeInvokeVirtualQuick(analyzedInstruction, true, false);
/*      */     case 242:
/*  964 */       return analyzeInvokeVirtualQuick(analyzedInstruction, false, true);
/*      */     case 243:
/*  966 */       return analyzeInvokeVirtualQuick(analyzedInstruction, true, true);
/*      */     case 244:
/*      */     case 245:
/*      */     case 246:
/*  970 */       analyzePutGetVolatile(analyzedInstruction);
/*  971 */       return true;
/*      */     }
/*  973 */     if (!$assertionsDisabled) throw new AssertionError();
/*  974 */     return true;
/*      */   }
/*      */ 
/*      */   private void analyzeMove(AnalyzedInstruction analyzedInstruction)
/*      */   {
/* 1010 */     TwoRegisterInstruction instruction = (TwoRegisterInstruction)analyzedInstruction.instruction;
/*      */ 
/* 1012 */     RegisterType sourceRegisterType = analyzedInstruction.getPreInstructionRegisterType(instruction.getRegisterB());
/* 1013 */     setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, sourceRegisterType);
/*      */   }
/*      */ 
/*      */   private void analyzeMoveResult(AnalyzedInstruction analyzedInstruction) {
/* 1017 */     AnalyzedInstruction previousInstruction = (AnalyzedInstruction)this.analyzedInstructions.valueAt(analyzedInstruction.instructionIndex - 1);
/* 1018 */     if (!previousInstruction.instruction.getOpcode().setsResult()) {
/* 1019 */       throw new AnalysisException(analyzedInstruction.instruction.getOpcode().name + " must occur after an " + "invoke-*/fill-new-array instruction", new Object[0]);
/*      */     }
/*      */ 
/* 1024 */     ReferenceInstruction invokeInstruction = (ReferenceInstruction)previousInstruction.instruction;
/* 1025 */     Reference reference = invokeInstruction.getReference();
/*      */     RegisterType resultRegisterType;
/*      */     RegisterType resultRegisterType;
/* 1027 */     if ((reference instanceof MethodReference))
/* 1028 */       resultRegisterType = RegisterType.getRegisterType(this.classPath, ((MethodReference)reference).getReturnType());
/*      */     else {
/* 1030 */       resultRegisterType = RegisterType.getRegisterType(this.classPath, (TypeReference)reference);
/*      */     }
/*      */ 
/* 1033 */     setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, resultRegisterType);
/*      */   }
/*      */ 
/*      */   private void analyzeMoveException(AnalyzedInstruction analyzedInstruction) {
/* 1037 */     int instructionAddress = getInstructionAddress(analyzedInstruction);
/*      */ 
/* 1039 */     RegisterType exceptionType = RegisterType.UNKNOWN_TYPE;
/*      */ 
/* 1041 */     for (TryBlock tryBlock : this.methodImpl.getTryBlocks()) {
/* 1042 */       for (ExceptionHandler handler : tryBlock.getExceptionHandlers())
/*      */       {
/* 1044 */         if (handler.getHandlerCodeAddress() == instructionAddress) {
/* 1045 */           String type = handler.getExceptionType();
/* 1046 */           if (type == null) {
/* 1047 */             exceptionType = RegisterType.getRegisterType(18, this.classPath.getClass("Ljava/lang/Throwable;"));
/*      */           }
/*      */           else {
/* 1050 */             exceptionType = RegisterType.getRegisterType(18, this.classPath.getClass(type)).merge(exceptionType);
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1057 */     if (exceptionType.category == 0) {
/* 1058 */       throw new AnalysisException("move-exception must be the first instruction in an exception handler block", new Object[0]);
/*      */     }
/*      */ 
/* 1061 */     setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, exceptionType);
/*      */   }
/*      */ 
/*      */   private void analyzeReturnVoidBarrier(AnalyzedInstruction analyzedInstruction) {
/* 1065 */     analyzeReturnVoidBarrier(analyzedInstruction, true);
/*      */   }
/*      */ 
/*      */   private void analyzeReturnVoidBarrier(AnalyzedInstruction analyzedInstruction, boolean analyzeResult) {
/* 1069 */     Instruction10x deodexedInstruction = new ImmutableInstruction10x(Opcode.RETURN_VOID);
/*      */ 
/* 1071 */     analyzedInstruction.setDeodexedInstruction(deodexedInstruction);
/*      */ 
/* 1073 */     if (analyzeResult)
/* 1074 */       analyzeInstruction(analyzedInstruction);
/*      */   }
/*      */ 
/*      */   private void analyzeConst(AnalyzedInstruction analyzedInstruction)
/*      */   {
/* 1079 */     NarrowLiteralInstruction instruction = (NarrowLiteralInstruction)analyzedInstruction.instruction;
/*      */ 
/* 1083 */     setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterTypeForLiteral(instruction.getNarrowLiteral()));
/*      */   }
/*      */ 
/*      */   private void analyzeWideConst(AnalyzedInstruction analyzedInstruction)
/*      */   {
/* 1088 */     setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.LONG_LO_TYPE);
/*      */   }
/*      */ 
/*      */   private void analyzeConstString(AnalyzedInstruction analyzedInstruction) {
/* 1092 */     TypeProto stringClass = this.classPath.getClass("Ljava/lang/String;");
/* 1093 */     RegisterType stringType = RegisterType.getRegisterType(18, stringClass);
/* 1094 */     setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, stringType);
/*      */   }
/*      */ 
/*      */   private void analyzeConstClass(AnalyzedInstruction analyzedInstruction) {
/* 1098 */     TypeProto classClass = this.classPath.getClass("Ljava/lang/Class;");
/* 1099 */     RegisterType classType = RegisterType.getRegisterType(18, classClass);
/* 1100 */     setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, classType);
/*      */   }
/*      */ 
/*      */   private void analyzeCheckCast(AnalyzedInstruction analyzedInstruction) {
/* 1104 */     ReferenceInstruction instruction = (ReferenceInstruction)analyzedInstruction.instruction;
/* 1105 */     TypeReference reference = (TypeReference)instruction.getReference();
/* 1106 */     RegisterType castRegisterType = RegisterType.getRegisterType(this.classPath, reference);
/* 1107 */     setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, castRegisterType);
/*      */   }
/*      */ 
/*      */   private void analyzeInstanceOf(AnalyzedInstruction analyzedInstruction) {
/* 1111 */     setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.BOOLEAN_TYPE);
/*      */   }
/*      */ 
/*      */   private void analyzeArrayLength(AnalyzedInstruction analyzedInstruction) {
/* 1115 */     setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.INTEGER_TYPE);
/*      */   }
/*      */ 
/*      */   private void analyzeNewInstance(AnalyzedInstruction analyzedInstruction) {
/* 1119 */     ReferenceInstruction instruction = (ReferenceInstruction)analyzedInstruction.instruction;
/*      */ 
/* 1121 */     int register = ((OneRegisterInstruction)analyzedInstruction.instruction).getRegisterA();
/* 1122 */     RegisterType destRegisterType = analyzedInstruction.getPostInstructionRegisterType(register);
/* 1123 */     if (destRegisterType.category != 0)
/*      */     {
/* 1127 */       assert (destRegisterType.category == 16);
/* 1128 */       return;
/*      */     }
/*      */ 
/* 1131 */     TypeReference typeReference = (TypeReference)instruction.getReference();
/*      */ 
/* 1133 */     RegisterType classType = RegisterType.getRegisterType(this.classPath, typeReference);
/*      */ 
/* 1135 */     setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(16, classType.type));
/*      */   }
/*      */ 
/*      */   private void analyzeNewArray(AnalyzedInstruction analyzedInstruction)
/*      */   {
/* 1140 */     ReferenceInstruction instruction = (ReferenceInstruction)analyzedInstruction.instruction;
/*      */ 
/* 1142 */     TypeReference type = (TypeReference)instruction.getReference();
/* 1143 */     if (type.getType().charAt(0) != '[') {
/* 1144 */       throw new AnalysisException("new-array used with non-array type", new Object[0]);
/*      */     }
/*      */ 
/* 1147 */     RegisterType arrayType = RegisterType.getRegisterType(this.classPath, type);
/*      */ 
/* 1149 */     setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, arrayType);
/*      */   }
/*      */ 
/*      */   private void analyzeFloatWideCmp(AnalyzedInstruction analyzedInstruction) {
/* 1153 */     setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.BYTE_TYPE);
/*      */   }
/*      */ 
/*      */   private void analyze32BitPrimitiveAget(AnalyzedInstruction analyzedInstruction, RegisterType registerType)
/*      */   {
/* 1158 */     setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, registerType);
/*      */   }
/*      */ 
/*      */   private void analyzeAgetWide(AnalyzedInstruction analyzedInstruction) {
/* 1162 */     ThreeRegisterInstruction instruction = (ThreeRegisterInstruction)analyzedInstruction.instruction;
/*      */ 
/* 1164 */     RegisterType arrayRegisterType = analyzedInstruction.getPreInstructionRegisterType(instruction.getRegisterB());
/* 1165 */     if (arrayRegisterType.category != 2) {
/* 1166 */       if ((arrayRegisterType.category != 18) || (!(arrayRegisterType.type instanceof ArrayProto)))
/*      */       {
/* 1168 */         throw new AnalysisException("aget-wide used with non-array register: %s", new Object[] { arrayRegisterType.toString() });
/*      */       }
/* 1170 */       ArrayProto arrayProto = (ArrayProto)arrayRegisterType.type;
/*      */ 
/* 1172 */       if (arrayProto.dimensions != 1) {
/* 1173 */         throw new AnalysisException("aget-wide used with multi-dimensional array: %s", new Object[] { arrayRegisterType.toString() });
/*      */       }
/*      */ 
/* 1177 */       char arrayBaseType = arrayProto.getElementType().charAt(0);
/* 1178 */       if (arrayBaseType == 'J')
/* 1179 */         setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.LONG_LO_TYPE);
/* 1180 */       else if (arrayBaseType == 'D')
/* 1181 */         setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.DOUBLE_LO_TYPE);
/*      */       else {
/* 1183 */         throw new AnalysisException("aget-wide used with narrow array: %s", new Object[] { arrayRegisterType });
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1188 */       setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.LONG_LO_TYPE);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void analyzeAgetObject(AnalyzedInstruction analyzedInstruction) {
/* 1193 */     ThreeRegisterInstruction instruction = (ThreeRegisterInstruction)analyzedInstruction.instruction;
/*      */ 
/* 1195 */     RegisterType arrayRegisterType = analyzedInstruction.getPreInstructionRegisterType(instruction.getRegisterB());
/* 1196 */     if (arrayRegisterType.category != 2) {
/* 1197 */       if ((arrayRegisterType.category != 18) || (!(arrayRegisterType.type instanceof ArrayProto)))
/*      */       {
/* 1199 */         throw new AnalysisException("aget-object used with non-array register: %s", new Object[] { arrayRegisterType.toString() });
/*      */       }
/*      */ 
/* 1203 */       ArrayProto arrayProto = (ArrayProto)arrayRegisterType.type;
/*      */ 
/* 1205 */       String elementType = arrayProto.getImmediateElementType();
/*      */ 
/* 1207 */       setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.getRegisterType(18, this.classPath.getClass(elementType)));
/*      */     }
/*      */     else
/*      */     {
/* 1212 */       setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, RegisterType.NULL_TYPE);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void analyze32BitPrimitiveIgetSget(AnalyzedInstruction analyzedInstruction, RegisterType registerType)
/*      */   {
/* 1218 */     setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, registerType);
/*      */   }
/*      */ 
/*      */   private void analyzeIgetSgetWideObject(AnalyzedInstruction analyzedInstruction) {
/* 1222 */     ReferenceInstruction referenceInstruction = (ReferenceInstruction)analyzedInstruction.instruction;
/*      */ 
/* 1224 */     FieldReference fieldReference = (FieldReference)referenceInstruction.getReference();
/*      */ 
/* 1226 */     RegisterType fieldType = RegisterType.getRegisterType(this.classPath, fieldReference.getType());
/* 1227 */     setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, fieldType);
/*      */   }
/*      */ 
/*      */   private void analyzeInvokeDirect(AnalyzedInstruction analyzedInstruction) {
/* 1231 */     FiveRegisterInstruction instruction = (FiveRegisterInstruction)analyzedInstruction.instruction;
/* 1232 */     analyzeInvokeDirectCommon(analyzedInstruction, instruction.getRegisterC());
/*      */   }
/*      */ 
/*      */   private void analyzeInvokeDirectRange(AnalyzedInstruction analyzedInstruction) {
/* 1236 */     RegisterRangeInstruction instruction = (RegisterRangeInstruction)analyzedInstruction.instruction;
/* 1237 */     analyzeInvokeDirectCommon(analyzedInstruction, instruction.getStartRegister());
/*      */   }
/*      */ 
/*      */   private void analyzeInvokeDirectCommon(AnalyzedInstruction analyzedInstruction, int objectRegister)
/*      */   {
/* 1245 */     ReferenceInstruction instruction = (ReferenceInstruction)analyzedInstruction.instruction;
/*      */ 
/* 1247 */     MethodReference methodReference = (MethodReference)instruction.getReference();
/*      */ 
/* 1249 */     if (!methodReference.getName().equals("<init>")) {
/* 1250 */       return;
/*      */     }
/*      */ 
/* 1253 */     RegisterType objectRegisterType = analyzedInstruction.getPreInstructionRegisterType(objectRegister);
/*      */ 
/* 1255 */     if ((objectRegisterType.category != 16) && (objectRegisterType.category != 17))
/*      */     {
/* 1257 */       return;
/*      */     }
/*      */ 
/* 1260 */     setPostRegisterTypeAndPropagateChanges(analyzedInstruction, objectRegister, RegisterType.getRegisterType(18, objectRegisterType.type));
/*      */ 
/* 1263 */     for (int i = 0; i < analyzedInstruction.postRegisterMap.length; i++) {
/* 1264 */       RegisterType postInstructionRegisterType = analyzedInstruction.postRegisterMap[i];
/* 1265 */       if (postInstructionRegisterType.category == 0) {
/* 1266 */         RegisterType preInstructionRegisterType = analyzedInstruction.getPreInstructionRegisterType(i);
/*      */ 
/* 1269 */         if ((preInstructionRegisterType.category != 16) && (preInstructionRegisterType.category != 17))
/*      */           continue;
/*      */         RegisterType registerType;
/*      */         RegisterType registerType;
/* 1272 */         if (preInstructionRegisterType.equals(objectRegisterType))
/* 1273 */           registerType = analyzedInstruction.postRegisterMap[objectRegister];
/*      */         else {
/* 1275 */           registerType = preInstructionRegisterType;
/*      */         }
/*      */ 
/* 1278 */         setPostRegisterTypeAndPropagateChanges(analyzedInstruction, i, registerType);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void analyzeUnaryOp(AnalyzedInstruction analyzedInstruction, RegisterType destRegisterType)
/*      */   {
/* 1286 */     setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, destRegisterType);
/*      */   }
/*      */ 
/*      */   private void analyzeBinaryOp(AnalyzedInstruction analyzedInstruction, RegisterType destRegisterType, boolean checkForBoolean)
/*      */   {
/* 1291 */     if (checkForBoolean) {
/* 1292 */       ThreeRegisterInstruction instruction = (ThreeRegisterInstruction)analyzedInstruction.instruction;
/*      */ 
/* 1294 */       RegisterType source1RegisterType = analyzedInstruction.getPreInstructionRegisterType(instruction.getRegisterB());
/*      */ 
/* 1296 */       RegisterType source2RegisterType = analyzedInstruction.getPreInstructionRegisterType(instruction.getRegisterC());
/*      */ 
/* 1299 */       if ((BooleanCategories.get(source1RegisterType.category)) && (BooleanCategories.get(source2RegisterType.category)))
/*      */       {
/* 1301 */         destRegisterType = RegisterType.BOOLEAN_TYPE;
/*      */       }
/*      */     }
/*      */ 
/* 1305 */     setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, destRegisterType);
/*      */   }
/*      */ 
/*      */   private void analyzeBinary2AddrOp(AnalyzedInstruction analyzedInstruction, RegisterType destRegisterType, boolean checkForBoolean)
/*      */   {
/* 1310 */     if (checkForBoolean) {
/* 1311 */       TwoRegisterInstruction instruction = (TwoRegisterInstruction)analyzedInstruction.instruction;
/*      */ 
/* 1313 */       RegisterType source1RegisterType = analyzedInstruction.getPreInstructionRegisterType(instruction.getRegisterA());
/*      */ 
/* 1315 */       RegisterType source2RegisterType = analyzedInstruction.getPreInstructionRegisterType(instruction.getRegisterB());
/*      */ 
/* 1318 */       if ((BooleanCategories.get(source1RegisterType.category)) && (BooleanCategories.get(source2RegisterType.category)))
/*      */       {
/* 1320 */         destRegisterType = RegisterType.BOOLEAN_TYPE;
/*      */       }
/*      */     }
/*      */ 
/* 1324 */     setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, destRegisterType);
/*      */   }
/*      */ 
/*      */   private void analyzeLiteralBinaryOp(AnalyzedInstruction analyzedInstruction, RegisterType destRegisterType, boolean checkForBoolean)
/*      */   {
/* 1329 */     if (checkForBoolean) {
/* 1330 */       TwoRegisterInstruction instruction = (TwoRegisterInstruction)analyzedInstruction.instruction;
/*      */ 
/* 1332 */       RegisterType sourceRegisterType = analyzedInstruction.getPreInstructionRegisterType(instruction.getRegisterB());
/*      */ 
/* 1335 */       if (BooleanCategories.get(sourceRegisterType.category)) {
/* 1336 */         int literal = ((NarrowLiteralInstruction)analyzedInstruction.instruction).getNarrowLiteral();
/* 1337 */         if ((literal == 0) || (literal == 1)) {
/* 1338 */           destRegisterType = RegisterType.BOOLEAN_TYPE;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1343 */     setDestinationRegisterTypeAndPropagateChanges(analyzedInstruction, destRegisterType);
/*      */   }
/*      */ 
/*      */   private RegisterType getDestTypeForLiteralShiftRight(AnalyzedInstruction analyzedInstruction, boolean signedShift) {
/* 1347 */     TwoRegisterInstruction instruction = (TwoRegisterInstruction)analyzedInstruction.instruction;
/*      */ 
/* 1349 */     RegisterType sourceRegisterType = getAndCheckSourceRegister(analyzedInstruction, instruction.getRegisterB(), Primitive32BitCategories);
/*      */ 
/* 1351 */     long literalShift = ((NarrowLiteralInstruction)analyzedInstruction.instruction).getNarrowLiteral();
/*      */ 
/* 1353 */     if (literalShift == 0L)
/* 1354 */       return sourceRegisterType;
/*      */     RegisterType destRegisterType;
/*      */     RegisterType destRegisterType;
/* 1358 */     if (!signedShift)
/* 1359 */       destRegisterType = RegisterType.INTEGER_TYPE;
/*      */     else {
/* 1361 */       destRegisterType = sourceRegisterType;
/*      */     }
/*      */ 
/* 1364 */     literalShift &= 31L;
/*      */ 
/* 1366 */     switch (sourceRegisterType.category) {
/*      */     case 10:
/*      */     case 11:
/* 1369 */       if (!signedShift) {
/* 1370 */         if (literalShift > 24L) {
/* 1371 */           return RegisterType.POS_BYTE_TYPE;
/*      */         }
/* 1373 */         if (literalShift < 16L) break;
/* 1374 */         return RegisterType.CHAR_TYPE;
/*      */       }
/*      */       else {
/* 1377 */         if (literalShift >= 24L) {
/* 1378 */           return RegisterType.BYTE_TYPE;
/*      */         }
/* 1380 */         if (literalShift < 16L) break;
/* 1381 */         return RegisterType.SHORT_TYPE;
/*      */       }
/*      */ 
/*      */     case 7:
/* 1386 */       if ((!signedShift) || (literalShift < 8L)) break;
/* 1387 */       return RegisterType.BYTE_TYPE;
/*      */     case 8:
/* 1391 */       if (literalShift < 8L) break;
/* 1392 */       return RegisterType.POS_BYTE_TYPE;
/*      */     case 9:
/* 1396 */       if (literalShift <= 8L) break;
/* 1397 */       return RegisterType.POS_BYTE_TYPE;
/*      */     case 5:
/* 1401 */       break;
/*      */     case 6:
/* 1403 */       return RegisterType.POS_BYTE_TYPE;
/*      */     case 2:
/*      */     case 3:
/*      */     case 4:
/* 1407 */       return RegisterType.NULL_TYPE;
/*      */     default:
/* 1409 */       if ($assertionsDisabled) break; throw new AssertionError();
/*      */     }
/*      */ 
/* 1412 */     return destRegisterType;
/*      */   }
/*      */ 
/*      */   private void analyzeExecuteInline(AnalyzedInstruction analyzedInstruction)
/*      */   {
/* 1417 */     if (this.inlineResolver == null) {
/* 1418 */       throw new AnalysisException("Cannot analyze an odexed instruction unless we are deodexing", new Object[0]);
/*      */     }
/*      */ 
/* 1421 */     Instruction35mi instruction = (Instruction35mi)analyzedInstruction.instruction;
/* 1422 */     Method resolvedMethod = this.inlineResolver.resolveExecuteInline(analyzedInstruction);
/*      */ 
/* 1425 */     int acccessFlags = resolvedMethod.getAccessFlags();
/*      */     Opcode deodexedOpcode;
/*      */     Opcode deodexedOpcode;
/* 1426 */     if (AccessFlags.STATIC.isSet(acccessFlags)) {
/* 1427 */       deodexedOpcode = Opcode.INVOKE_STATIC;
/*      */     }
/*      */     else
/*      */     {
/*      */       Opcode deodexedOpcode;
/* 1428 */       if (AccessFlags.PRIVATE.isSet(acccessFlags))
/* 1429 */         deodexedOpcode = Opcode.INVOKE_DIRECT;
/*      */       else {
/* 1431 */         deodexedOpcode = Opcode.INVOKE_VIRTUAL;
/*      */       }
/*      */     }
/* 1434 */     Instruction35c deodexedInstruction = new ImmutableInstruction35c(deodexedOpcode, instruction.getRegisterCount(), instruction.getRegisterC(), instruction.getRegisterD(), instruction.getRegisterE(), instruction.getRegisterF(), instruction.getRegisterG(), resolvedMethod);
/*      */ 
/* 1438 */     analyzedInstruction.setDeodexedInstruction(deodexedInstruction);
/* 1439 */     analyzeInstruction(analyzedInstruction);
/*      */   }
/*      */ 
/*      */   private void analyzeExecuteInlineRange(AnalyzedInstruction analyzedInstruction) {
/* 1443 */     if (this.inlineResolver == null) {
/* 1444 */       throw new AnalysisException("Cannot analyze an odexed instruction unless we are deodexing", new Object[0]);
/*      */     }
/*      */ 
/* 1447 */     Instruction3rmi instruction = (Instruction3rmi)analyzedInstruction.instruction;
/* 1448 */     Method resolvedMethod = this.inlineResolver.resolveExecuteInline(analyzedInstruction);
/*      */ 
/* 1451 */     int acccessFlags = resolvedMethod.getAccessFlags();
/*      */     Opcode deodexedOpcode;
/*      */     Opcode deodexedOpcode;
/* 1452 */     if (AccessFlags.STATIC.isSet(acccessFlags)) {
/* 1453 */       deodexedOpcode = Opcode.INVOKE_STATIC_RANGE;
/*      */     }
/*      */     else
/*      */     {
/*      */       Opcode deodexedOpcode;
/* 1454 */       if (AccessFlags.PRIVATE.isSet(acccessFlags))
/* 1455 */         deodexedOpcode = Opcode.INVOKE_DIRECT_RANGE;
/*      */       else {
/* 1457 */         deodexedOpcode = Opcode.INVOKE_VIRTUAL_RANGE;
/*      */       }
/*      */     }
/* 1460 */     Instruction3rc deodexedInstruction = new ImmutableInstruction3rc(deodexedOpcode, instruction.getStartRegister(), instruction.getRegisterCount(), resolvedMethod);
/*      */ 
/* 1463 */     analyzedInstruction.setDeodexedInstruction(deodexedInstruction);
/* 1464 */     analyzeInstruction(analyzedInstruction);
/*      */   }
/*      */ 
/*      */   private void analyzeInvokeDirectEmpty(AnalyzedInstruction analyzedInstruction) {
/* 1468 */     analyzeInvokeDirectEmpty(analyzedInstruction, true);
/*      */   }
/*      */ 
/*      */   private void analyzeInvokeDirectEmpty(AnalyzedInstruction analyzedInstruction, boolean analyzeResult) {
/* 1472 */     Instruction35c instruction = (Instruction35c)analyzedInstruction.instruction;
/*      */ 
/* 1474 */     Instruction35c deodexedInstruction = new ImmutableInstruction35c(Opcode.INVOKE_DIRECT, instruction.getRegisterCount(), instruction.getRegisterC(), instruction.getRegisterD(), instruction.getRegisterE(), instruction.getRegisterF(), instruction.getRegisterG(), instruction.getReference());
/*      */ 
/* 1479 */     analyzedInstruction.setDeodexedInstruction(deodexedInstruction);
/*      */ 
/* 1481 */     if (analyzeResult)
/* 1482 */       analyzeInstruction(analyzedInstruction);
/*      */   }
/*      */ 
/*      */   private void analyzeInvokeObjectInitRange(AnalyzedInstruction analyzedInstruction)
/*      */   {
/* 1487 */     analyzeInvokeObjectInitRange(analyzedInstruction, true);
/*      */   }
/*      */ 
/*      */   private void analyzeInvokeObjectInitRange(AnalyzedInstruction analyzedInstruction, boolean analyzeResult) {
/* 1491 */     Instruction3rc instruction = (Instruction3rc)analyzedInstruction.instruction;
/*      */ 
/* 1495 */     int startRegister = instruction.getStartRegister();
/* 1496 */     int registerCount = instruction.getRegisterCount();
/*      */     Instruction deodexedInstruction;
/*      */     Instruction deodexedInstruction;
/* 1497 */     if ((registerCount == 1) && (startRegister < 16)) {
/* 1498 */       deodexedInstruction = new ImmutableInstruction35c(Opcode.INVOKE_DIRECT, registerCount, startRegister, 0, 0, 0, 0, instruction.getReference());
/*      */     }
/*      */     else {
/* 1501 */       deodexedInstruction = new ImmutableInstruction3rc(Opcode.INVOKE_DIRECT_RANGE, startRegister, registerCount, instruction.getReference());
/*      */     }
/*      */ 
/* 1505 */     analyzedInstruction.setDeodexedInstruction(deodexedInstruction);
/*      */ 
/* 1507 */     if (analyzeResult)
/* 1508 */       analyzeInstruction(analyzedInstruction);
/*      */   }
/*      */ 
/*      */   private boolean analyzeIputIgetQuick(AnalyzedInstruction analyzedInstruction)
/*      */   {
/* 1513 */     Instruction22cs instruction = (Instruction22cs)analyzedInstruction.instruction;
/*      */ 
/* 1515 */     int fieldOffset = instruction.getFieldOffset();
/* 1516 */     RegisterType objectRegisterType = getAndCheckSourceRegister(analyzedInstruction, instruction.getRegisterB(), ReferenceOrUninitCategories);
/*      */ 
/* 1519 */     if (objectRegisterType.category == 2) {
/* 1520 */       return false;
/*      */     }
/*      */ 
/* 1523 */     TypeProto objectRegisterTypeProto = objectRegisterType.type;
/* 1524 */     assert (objectRegisterTypeProto != null);
/*      */ 
/* 1526 */     TypeProto classTypeProto = this.classPath.getClass(objectRegisterTypeProto.getType());
/* 1527 */     FieldReference resolvedField = classTypeProto.getFieldByOffset(fieldOffset);
/*      */ 
/* 1529 */     if (resolvedField == null) {
/* 1530 */       throw new AnalysisException("Could not resolve the field in class %s at offset %d", new Object[] { objectRegisterType.type.getType(), Integer.valueOf(fieldOffset) });
/*      */     }
/*      */ 
/* 1534 */     ClassDef thisClass = this.classPath.getClassDef(this.method.getDefiningClass());
/*      */ 
/* 1536 */     if (!canAccessClass(thisClass, this.classPath.getClassDef(resolvedField.getDefiningClass())))
/*      */     {
/* 1540 */       ClassDef fieldClass = this.classPath.getClassDef(objectRegisterTypeProto.getType());
/* 1541 */       while (!canAccessClass(thisClass, fieldClass)) {
/* 1542 */         String superclass = fieldClass.getSuperclass();
/* 1543 */         if (superclass == null) {
/* 1544 */           throw new ExceptionWithContext("Couldn't find accessible class while resolving field %s", new Object[] { ReferenceUtil.getShortFieldDescriptor(resolvedField) });
/*      */         }
/*      */ 
/* 1548 */         fieldClass = this.classPath.getClassDef(superclass);
/*      */       }
/*      */ 
/* 1553 */       resolvedField = this.classPath.getClass(fieldClass.getType()).getFieldByOffset(fieldOffset);
/* 1554 */       if (resolvedField == null) {
/* 1555 */         throw new ExceptionWithContext("Couldn't find accessible class while resolving field %s", new Object[] { ReferenceUtil.getShortFieldDescriptor(resolvedField) });
/*      */       }
/*      */ 
/* 1558 */       resolvedField = new ImmutableFieldReference(fieldClass.getType(), resolvedField.getName(), resolvedField.getType());
/*      */     }
/*      */ 
/* 1562 */     String fieldType = resolvedField.getType();
/*      */ 
/* 1564 */     Opcode opcode = OdexedFieldInstructionMapper.getAndCheckDeodexedOpcodeForOdexedOpcode(fieldType, instruction.getOpcode());
/*      */ 
/* 1567 */     Instruction22c deodexedInstruction = new ImmutableInstruction22c(opcode, (byte)instruction.getRegisterA(), (byte)instruction.getRegisterB(), resolvedField);
/*      */ 
/* 1569 */     analyzedInstruction.setDeodexedInstruction(deodexedInstruction);
/*      */ 
/* 1571 */     analyzeInstruction(analyzedInstruction);
/*      */ 
/* 1573 */     return true;
/*      */   }
/*      */ 
/*      */   private boolean analyzeInvokeVirtualQuick(AnalyzedInstruction analyzedInstruction, boolean isSuper, boolean isRange)
/*      */   {
/*      */     int objectRegister;
/*      */     int methodIndex;
/*      */     int objectRegister;
/* 1581 */     if (isRange) {
/* 1582 */       Instruction3rms instruction = (Instruction3rms)analyzedInstruction.instruction;
/* 1583 */       int methodIndex = instruction.getVtableIndex();
/* 1584 */       objectRegister = instruction.getStartRegister();
/*      */     } else {
/* 1586 */       Instruction35ms instruction = (Instruction35ms)analyzedInstruction.instruction;
/* 1587 */       methodIndex = instruction.getVtableIndex();
/* 1588 */       objectRegister = instruction.getRegisterC();
/*      */     }
/*      */ 
/* 1591 */     RegisterType objectRegisterType = getAndCheckSourceRegister(analyzedInstruction, objectRegister, ReferenceOrUninitCategories);
/*      */ 
/* 1593 */     TypeProto objectRegisterTypeProto = objectRegisterType.type;
/*      */ 
/* 1595 */     if (objectRegisterType.category == 2) {
/* 1596 */       return false;
/*      */     }
/*      */ 
/* 1599 */     assert (objectRegisterTypeProto != null);
/*      */     MethodReference resolvedMethod;
/*      */     MethodReference resolvedMethod;
/* 1602 */     if (isSuper)
/*      */     {
/* 1604 */       TypeProto typeProto = this.classPath.getClass(this.method.getDefiningClass());
/*      */ 
/* 1607 */       String superclassType = typeProto.getSuperclass();
/*      */       TypeProto superType;
/*      */       TypeProto superType;
/* 1608 */       if (superclassType != null) {
/* 1609 */         superType = this.classPath.getClass(superclassType);
/*      */       }
/*      */       else {
/* 1612 */         superType = typeProto;
/*      */       }
/*      */ 
/* 1615 */       resolvedMethod = superType.getMethodByVtableIndex(methodIndex);
/*      */     } else {
/* 1617 */       resolvedMethod = objectRegisterTypeProto.getMethodByVtableIndex(methodIndex);
/*      */     }
/*      */ 
/* 1620 */     if (resolvedMethod == null) {
/* 1621 */       throw new AnalysisException("Could not resolve the method in class %s at index %d", new Object[] { objectRegisterType.type.getType(), Integer.valueOf(methodIndex) });
/*      */     }
/*      */ 
/* 1626 */     ClassDef thisClass = this.classPath.getClassDef(this.method.getDefiningClass());
/*      */ 
/* 1628 */     if ((!isSuper) && (!canAccessClass(thisClass, this.classPath.getClassDef(resolvedMethod.getDefiningClass()))))
/*      */     {
/* 1632 */       ClassDef methodClass = this.classPath.getClassDef(objectRegisterTypeProto.getType());
/* 1633 */       while (!canAccessClass(thisClass, methodClass)) {
/* 1634 */         String superclass = methodClass.getSuperclass();
/* 1635 */         if (superclass == null) {
/* 1636 */           throw new ExceptionWithContext("Couldn't find accessible class while resolving method %s", new Object[] { ReferenceUtil.getShortMethodDescriptor(resolvedMethod) });
/*      */         }
/*      */ 
/* 1640 */         methodClass = this.classPath.getClassDef(superclass);
/*      */       }
/*      */ 
/* 1645 */       resolvedMethod = this.classPath.getClass(methodClass.getType()).getMethodByVtableIndex(methodIndex);
/* 1646 */       if (resolvedMethod == null) {
/* 1647 */         throw new ExceptionWithContext("Couldn't find accessible class while resolving method %s", new Object[] { ReferenceUtil.getShortMethodDescriptor(resolvedMethod) });
/*      */       }
/*      */ 
/* 1650 */       resolvedMethod = new ImmutableMethodReference(methodClass.getType(), resolvedMethod.getName(), resolvedMethod.getParameterTypes(), resolvedMethod.getReturnType());
/*      */     }
/*      */     Instruction deodexedInstruction;
/*      */     Instruction deodexedInstruction;
/* 1655 */     if (isRange) {
/* 1656 */       Instruction3rms instruction = (Instruction3rms)analyzedInstruction.instruction;
/*      */       Opcode opcode;
/*      */       Opcode opcode;
/* 1658 */       if (isSuper)
/* 1659 */         opcode = Opcode.INVOKE_SUPER_RANGE;
/*      */       else {
/* 1661 */         opcode = Opcode.INVOKE_VIRTUAL_RANGE;
/*      */       }
/*      */ 
/* 1664 */       deodexedInstruction = new ImmutableInstruction3rc(opcode, instruction.getStartRegister(), instruction.getRegisterCount(), resolvedMethod);
/*      */     }
/*      */     else {
/* 1667 */       Instruction35ms instruction = (Instruction35ms)analyzedInstruction.instruction;
/*      */       Opcode opcode;
/*      */       Opcode opcode;
/* 1669 */       if (isSuper)
/* 1670 */         opcode = Opcode.INVOKE_SUPER;
/*      */       else {
/* 1672 */         opcode = Opcode.INVOKE_VIRTUAL;
/*      */       }
/*      */ 
/* 1675 */       deodexedInstruction = new ImmutableInstruction35c(opcode, instruction.getRegisterCount(), instruction.getRegisterC(), instruction.getRegisterD(), instruction.getRegisterE(), instruction.getRegisterF(), instruction.getRegisterG(), resolvedMethod);
/*      */     }
/*      */ 
/* 1680 */     analyzedInstruction.setDeodexedInstruction(deodexedInstruction);
/* 1681 */     analyzeInstruction(analyzedInstruction);
/*      */ 
/* 1683 */     return true;
/*      */   }
/*      */ 
/*      */   private boolean canAccessClass(ClassDef accessorClassDef, ClassDef accesseeClassDef) {
/* 1687 */     if (AccessFlags.PUBLIC.isSet(accesseeClassDef.getAccessFlags())) {
/* 1688 */       return true;
/*      */     }
/*      */ 
/* 1693 */     return getPackage(accesseeClassDef.getType()).equals(getPackage(accessorClassDef.getType()));
/*      */   }
/*      */ 
/*      */   private static String getPackage(String className) {
/* 1697 */     int lastSlash = className.lastIndexOf('/');
/* 1698 */     if (lastSlash < 0) {
/* 1699 */       return "";
/*      */     }
/* 1701 */     return className.substring(1, lastSlash);
/*      */   }
/*      */ 
/*      */   private boolean analyzePutGetVolatile(AnalyzedInstruction analyzedInstruction) {
/* 1705 */     return analyzePutGetVolatile(analyzedInstruction, true);
/*      */   }
/*      */ 
/*      */   private boolean analyzePutGetVolatile(AnalyzedInstruction analyzedInstruction, boolean analyzeResult) {
/* 1709 */     FieldReference field = (FieldReference)((ReferenceInstruction)analyzedInstruction.instruction).getReference();
/* 1710 */     String fieldType = field.getType();
/*      */ 
/* 1712 */     Opcode originalOpcode = analyzedInstruction.instruction.getOpcode();
/*      */ 
/* 1714 */     Opcode opcode = OdexedFieldInstructionMapper.getAndCheckDeodexedOpcodeForOdexedOpcode(fieldType, originalOpcode);
/*      */     Instruction deodexedInstruction;
/*      */     Instruction deodexedInstruction;
/* 1719 */     if (originalOpcode.isOdexedStaticVolatile()) {
/* 1720 */       OneRegisterInstruction instruction = (OneRegisterInstruction)analyzedInstruction.instruction;
/* 1721 */       deodexedInstruction = new ImmutableInstruction21c(opcode, instruction.getRegisterA(), field);
/*      */     } else {
/* 1723 */       TwoRegisterInstruction instruction = (TwoRegisterInstruction)analyzedInstruction.instruction;
/*      */ 
/* 1725 */       deodexedInstruction = new ImmutableInstruction22c(opcode, instruction.getRegisterA(), instruction.getRegisterB(), field);
/*      */     }
/*      */ 
/* 1729 */     analyzedInstruction.setDeodexedInstruction(deodexedInstruction);
/*      */ 
/* 1731 */     if (analyzeResult) {
/* 1732 */       analyzeInstruction(analyzedInstruction);
/*      */     }
/* 1734 */     return true;
/*      */   }
/*      */ 
/*      */   private static RegisterType getAndCheckSourceRegister(AnalyzedInstruction analyzedInstruction, int registerNumber, BitSet validCategories)
/*      */   {
/* 1740 */     assert ((registerNumber >= 0) && (registerNumber < analyzedInstruction.postRegisterMap.length));
/*      */ 
/* 1742 */     RegisterType registerType = analyzedInstruction.getPreInstructionRegisterType(registerNumber);
/*      */ 
/* 1744 */     checkRegister(registerType, registerNumber, validCategories);
/*      */ 
/* 1746 */     if (validCategories == WideLowCategories) {
/* 1747 */       checkRegister(registerType, registerNumber, WideLowCategories);
/* 1748 */       checkWidePair(registerNumber, analyzedInstruction);
/*      */ 
/* 1750 */       RegisterType secondRegisterType = analyzedInstruction.getPreInstructionRegisterType(registerNumber + 1);
/* 1751 */       checkRegister(secondRegisterType, registerNumber + 1, WideHighCategories);
/*      */     }
/*      */ 
/* 1754 */     return registerType;
/*      */   }
/*      */ 
/*      */   private static void checkRegister(RegisterType registerType, int registerNumber, BitSet validCategories) {
/* 1758 */     if (!validCategories.get(registerType.category))
/* 1759 */       throw new AnalysisException(String.format("Invalid register type %s for register v%d.", new Object[] { registerType.toString(), Integer.valueOf(registerNumber) }), new Object[0]);
/*      */   }
/*      */ 
/*      */   private static void checkWidePair(int registerNumber, AnalyzedInstruction analyzedInstruction)
/*      */   {
/* 1765 */     if (registerNumber + 1 >= analyzedInstruction.postRegisterMap.length)
/* 1766 */       throw new AnalysisException(String.format("v%d cannot be used as the first register in a wide registerpair because it is the last register.", new Object[] { Integer.valueOf(registerNumber) }), new Object[0]);
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*  978 */     Primitive32BitCategories = BitSetUtils.bitSetOfIndexes(new int[] { 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 });
/*      */ 
/*  990 */     WideLowCategories = BitSetUtils.bitSetOfIndexes(new int[] { 12, 14 });
/*      */ 
/*  994 */     WideHighCategories = BitSetUtils.bitSetOfIndexes(new int[] { 13, 15 });
/*      */ 
/*  998 */     ReferenceOrUninitCategories = BitSetUtils.bitSetOfIndexes(new int[] { 2, 16, 17, 18 });
/*      */ 
/* 1004 */     BooleanCategories = BitSetUtils.bitSetOfIndexes(new int[] { 2, 3, 4 });
/*      */   }
/*      */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.analysis.MethodAnalyzer
 * JD-Core Version:    0.6.0
 */