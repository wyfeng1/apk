/*     */ package org.jf.dexlib2.analysis;
/*     */ 
/*     */ import java.util.BitSet;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedList;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeSet;
/*     */ import org.jf.dexlib2.Opcode;
/*     */ import org.jf.dexlib2.iface.instruction.FiveRegisterInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.Instruction;
/*     */ import org.jf.dexlib2.iface.instruction.OneRegisterInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.RegisterRangeInstruction;
/*     */ import org.jf.dexlib2.iface.reference.MethodReference;
/*     */ import org.jf.dexlib2.iface.reference.Reference;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ public class AnalyzedInstruction
/*     */   implements Comparable<AnalyzedInstruction>
/*     */ {
/*     */   protected Instruction instruction;
/*     */   protected final int instructionIndex;
/*  56 */   protected final TreeSet<AnalyzedInstruction> predecessors = new TreeSet();
/*     */ 
/*  61 */   protected final LinkedList<AnalyzedInstruction> successors = new LinkedList();
/*     */   protected final RegisterType[] preRegisterMap;
/*     */   protected final RegisterType[] postRegisterMap;
/*     */   protected final Instruction originalInstruction;
/*     */ 
/*     */   public AnalyzedInstruction(Instruction instruction, int instructionIndex, int registerCount)
/*     */   {
/*  80 */     this.instruction = instruction;
/*  81 */     this.originalInstruction = instruction;
/*  82 */     this.instructionIndex = instructionIndex;
/*  83 */     this.postRegisterMap = new RegisterType[registerCount];
/*  84 */     this.preRegisterMap = new RegisterType[registerCount];
/*  85 */     RegisterType unknown = RegisterType.getRegisterType(0, null);
/*  86 */     for (int i = 0; i < registerCount; i++) {
/*  87 */       this.preRegisterMap[i] = unknown;
/*  88 */       this.postRegisterMap[i] = unknown;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getInstructionIndex() {
/*  93 */     return this.instructionIndex;
/*     */   }
/*     */ 
/*     */   public int getPredecessorCount() {
/*  97 */     return this.predecessors.size();
/*     */   }
/*     */ 
/*     */   public SortedSet<AnalyzedInstruction> getPredecessors() {
/* 101 */     return Collections.unmodifiableSortedSet(this.predecessors);
/*     */   }
/*     */ 
/*     */   protected boolean addPredecessor(AnalyzedInstruction predecessor) {
/* 105 */     return this.predecessors.add(predecessor);
/*     */   }
/*     */ 
/*     */   protected void addSuccessor(AnalyzedInstruction successor) {
/* 109 */     this.successors.add(successor);
/*     */   }
/*     */ 
/*     */   protected void setDeodexedInstruction(Instruction instruction) {
/* 113 */     assert (this.originalInstruction.getOpcode().odexOnly());
/* 114 */     this.instruction = instruction;
/*     */   }
/*     */ 
/*     */   protected void restoreOdexedInstruction() {
/* 118 */     assert (this.originalInstruction.getOpcode().odexOnly());
/* 119 */     this.instruction = this.originalInstruction;
/*     */   }
/*     */ 
/*     */   public Instruction getInstruction()
/*     */   {
/* 131 */     return this.instruction;
/*     */   }
/*     */ 
/*     */   public Instruction getOriginalInstruction() {
/* 135 */     return this.originalInstruction;
/*     */   }
/*     */ 
/*     */   public boolean isBeginningInstruction()
/*     */   {
/* 153 */     if (this.predecessors.size() == 0) {
/* 154 */       return false;
/*     */     }
/*     */ 
/* 158 */     return ((AnalyzedInstruction)this.predecessors.first()).instructionIndex == -1;
/*     */   }
/*     */ 
/*     */   protected boolean mergeRegister(int registerNumber, RegisterType registerType, BitSet verifiedInstructions)
/*     */   {
/* 173 */     assert ((registerNumber >= 0) && (registerNumber < this.postRegisterMap.length));
/* 174 */     assert (registerType != null);
/*     */ 
/* 176 */     RegisterType oldRegisterType = this.preRegisterMap[registerNumber];
/* 177 */     RegisterType mergedRegisterType = oldRegisterType.merge(registerType);
/*     */ 
/* 179 */     if (mergedRegisterType.equals(oldRegisterType)) {
/* 180 */       return false;
/*     */     }
/*     */ 
/* 183 */     this.preRegisterMap[registerNumber] = mergedRegisterType;
/* 184 */     verifiedInstructions.clear(this.instructionIndex);
/*     */ 
/* 186 */     if (!setsRegister(registerNumber)) {
/* 187 */       this.postRegisterMap[registerNumber] = mergedRegisterType;
/* 188 */       return true;
/*     */     }
/*     */ 
/* 191 */     return false;
/*     */   }
/*     */ 
/*     */   protected boolean setPostRegisterType(int registerNumber, RegisterType registerType)
/*     */   {
/* 217 */     assert ((registerNumber >= 0) && (registerNumber < this.postRegisterMap.length));
/* 218 */     assert (registerType != null);
/*     */ 
/* 220 */     RegisterType oldRegisterType = this.postRegisterMap[registerNumber];
/* 221 */     if (oldRegisterType.equals(registerType)) {
/* 222 */       return false;
/*     */     }
/*     */ 
/* 225 */     this.postRegisterMap[registerNumber] = registerType;
/* 226 */     return true;
/*     */   }
/*     */ 
/*     */   protected boolean isInvokeInit()
/*     */   {
/* 231 */     if ((this.instruction == null) || (!this.instruction.getOpcode().canInitializeReference())) {
/* 232 */       return false;
/*     */     }
/*     */ 
/* 235 */     ReferenceInstruction instruction = (ReferenceInstruction)this.instruction;
/*     */ 
/* 237 */     Reference reference = instruction.getReference();
/* 238 */     if ((reference instanceof MethodReference)) {
/* 239 */       return ((MethodReference)reference).getName().equals("<init>");
/*     */     }
/*     */ 
/* 242 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean setsRegister() {
/* 246 */     return this.instruction.getOpcode().setsRegister();
/*     */   }
/*     */ 
/*     */   public boolean setsWideRegister() {
/* 250 */     return this.instruction.getOpcode().setsWideRegister();
/*     */   }
/*     */ 
/*     */   public boolean setsRegister(int registerNumber)
/*     */   {
/* 259 */     if (isInvokeInit())
/*     */     {
/*     */       int destinationRegister;
/*     */       int destinationRegister;
/* 261 */       if ((this.instruction instanceof FiveRegisterInstruction)) {
/* 262 */         destinationRegister = ((FiveRegisterInstruction)this.instruction).getRegisterC();
/*     */       } else {
/* 264 */         assert ((this.instruction instanceof RegisterRangeInstruction));
/* 265 */         RegisterRangeInstruction rangeInstruction = (RegisterRangeInstruction)this.instruction;
/* 266 */         assert (rangeInstruction.getRegisterCount() > 0);
/* 267 */         destinationRegister = rangeInstruction.getStartRegister();
/*     */       }
/*     */ 
/* 270 */       if (registerNumber == destinationRegister) {
/* 271 */         return true;
/*     */       }
/* 273 */       RegisterType preInstructionDestRegisterType = getPreInstructionRegisterType(registerNumber);
/* 274 */       if ((preInstructionDestRegisterType.category != 16) && (preInstructionDestRegisterType.category != 17))
/*     */       {
/* 277 */         return false;
/*     */       }
/*     */ 
/* 281 */       return getPreInstructionRegisterType(registerNumber).equals(preInstructionDestRegisterType);
/*     */     }
/*     */ 
/* 286 */     if (!setsRegister()) {
/* 287 */       return false;
/*     */     }
/* 289 */     int destinationRegister = getDestinationRegister();
/*     */ 
/* 291 */     if (registerNumber == destinationRegister) {
/* 292 */       return true;
/*     */     }
/*     */ 
/* 295 */     return (setsWideRegister()) && (registerNumber == destinationRegister + 1);
/*     */   }
/*     */ 
/*     */   public int getDestinationRegister()
/*     */   {
/* 301 */     if (!this.instruction.getOpcode().setsRegister()) {
/* 302 */       throw new ExceptionWithContext("Cannot call getDestinationRegister() for an instruction that doesn't store a value", new Object[0]);
/*     */     }
/*     */ 
/* 305 */     return ((OneRegisterInstruction)this.instruction).getRegisterA();
/*     */   }
/*     */ 
/*     */   public int getRegisterCount() {
/* 309 */     return this.postRegisterMap.length;
/*     */   }
/*     */ 
/*     */   public RegisterType getPostInstructionRegisterType(int registerNumber)
/*     */   {
/* 314 */     return this.postRegisterMap[registerNumber];
/*     */   }
/*     */ 
/*     */   public RegisterType getPreInstructionRegisterType(int registerNumber)
/*     */   {
/* 319 */     return this.preRegisterMap[registerNumber];
/*     */   }
/*     */ 
/*     */   public int compareTo(AnalyzedInstruction analyzedInstruction) {
/* 323 */     if (this.instructionIndex < analyzedInstruction.instructionIndex)
/* 324 */       return -1;
/* 325 */     if (this.instructionIndex == analyzedInstruction.instructionIndex) {
/* 326 */       return 0;
/*     */     }
/* 328 */     return 1;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.analysis.AnalyzedInstruction
 * JD-Core Version:    0.6.0
 */