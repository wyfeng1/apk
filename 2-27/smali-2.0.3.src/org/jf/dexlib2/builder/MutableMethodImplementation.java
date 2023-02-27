/*     */ package org.jf.dexlib2.builder;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.AbstractList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.jf.dexlib2.Format;
/*     */ import org.jf.dexlib2.Opcode;
/*     */ import org.jf.dexlib2.builder.debug.BuilderEndLocal;
/*     */ import org.jf.dexlib2.builder.debug.BuilderEpilogueBegin;
/*     */ import org.jf.dexlib2.builder.debug.BuilderLineNumber;
/*     */ import org.jf.dexlib2.builder.debug.BuilderPrologueEnd;
/*     */ import org.jf.dexlib2.builder.debug.BuilderRestartLocal;
/*     */ import org.jf.dexlib2.builder.debug.BuilderSetSourceFile;
/*     */ import org.jf.dexlib2.builder.debug.BuilderStartLocal;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderArrayPayload;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction10t;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction10x;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction11n;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction11x;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction12x;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction20bc;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction20t;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction21c;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction21ih;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction21lh;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction21s;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction21t;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction22b;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction22c;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction22s;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction22t;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction22x;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction23x;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction30t;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction31c;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction31i;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction31t;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction32x;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction35c;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction3rc;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderInstruction51l;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderPackedSwitchPayload;
/*     */ import org.jf.dexlib2.builder.instruction.BuilderSparseSwitchPayload;
/*     */ import org.jf.dexlib2.iface.ExceptionHandler;
/*     */ import org.jf.dexlib2.iface.MethodImplementation;
/*     */ import org.jf.dexlib2.iface.TryBlock;
/*     */ import org.jf.dexlib2.iface.debug.DebugItem;
/*     */ import org.jf.dexlib2.iface.debug.EndLocal;
/*     */ import org.jf.dexlib2.iface.debug.LineNumber;
/*     */ import org.jf.dexlib2.iface.debug.RestartLocal;
/*     */ import org.jf.dexlib2.iface.debug.SetSourceFile;
/*     */ import org.jf.dexlib2.iface.debug.StartLocal;
/*     */ import org.jf.dexlib2.iface.instruction.Instruction;
/*     */ import org.jf.dexlib2.iface.instruction.SwitchElement;
/*     */ import org.jf.dexlib2.iface.instruction.formats.ArrayPayload;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction10t;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction10x;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction11n;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction11x;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction12x;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction20bc;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction20t;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction21c;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction21ih;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction21lh;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction21s;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction21t;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction22b;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction22c;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction22s;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction22t;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction22x;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction23x;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction30t;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction31c;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction31i;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction31t;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction32x;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction35c;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction3rc;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction51l;
/*     */ import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload;
/*     */ import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload;
/*     */ import org.jf.dexlib2.iface.reference.TypeReference;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ public class MutableMethodImplementation
/*     */   implements MethodImplementation
/*     */ {
/*     */   private final int registerCount;
/*  58 */   final ArrayList<MethodLocation> instructionList = Lists.newArrayList(new MethodLocation[] { new MethodLocation(null, 0, 0) });
/*  59 */   private final ArrayList<BuilderTryBlock> tryBlocks = Lists.newArrayList();
/*  60 */   private boolean fixInstructions = true;
/*     */ 
/*     */   public MutableMethodImplementation(MethodImplementation methodImplementation) {
/*  63 */     this.registerCount = methodImplementation.getRegisterCount();
/*     */ 
/*  65 */     int codeAddress = 0;
/*  66 */     int index = 0;
/*     */ 
/*  68 */     for (Instruction instruction : methodImplementation.getInstructions()) {
/*  69 */       codeAddress += instruction.getCodeUnits();
/*  70 */       index++;
/*     */ 
/*  72 */       this.instructionList.add(new MethodLocation(null, codeAddress, index));
/*     */     }
/*     */ 
/*  75 */     int[] codeAddressToIndex = new int[codeAddress + 1];
/*  76 */     Arrays.fill(codeAddressToIndex, -1);
/*     */ 
/*  78 */     for (int i = 0; i < this.instructionList.size(); i++) {
/*  79 */       codeAddressToIndex[((MethodLocation)this.instructionList.get(i)).codeAddress] = i;
/*     */     }
/*     */ 
/*  82 */     List switchPayloadTasks = Lists.newArrayList();
/*  83 */     index = 0;
/*  84 */     for (Instruction instruction : methodImplementation.getInstructions()) {
/*  85 */       MethodLocation location = (MethodLocation)this.instructionList.get(index);
/*  86 */       Opcode opcode = instruction.getOpcode();
/*  87 */       if ((opcode == Opcode.PACKED_SWITCH_PAYLOAD) || (opcode == Opcode.SPARSE_SWITCH_PAYLOAD))
/*  88 */         switchPayloadTasks.add(new Task(location, codeAddressToIndex, instruction) {
/*     */           public void perform() {
/*  90 */             MutableMethodImplementation.this.convertAndSetInstruction(this.val$location, this.val$codeAddressToIndex, this.val$instruction);
/*     */           }
/*     */         });
/*  94 */       else convertAndSetInstruction(location, codeAddressToIndex, instruction);
/*     */ 
/*  96 */       index++;
/*     */     }
/*     */ 
/* 101 */     for (Task switchPayloadTask : switchPayloadTasks) {
/* 102 */       switchPayloadTask.perform();
/*     */     }
/*     */ 
/* 105 */     for (DebugItem debugItem : methodImplementation.getDebugItems()) {
/* 106 */       int debugCodeAddress = debugItem.getCodeAddress();
/* 107 */       int locationIndex = mapCodeAddressToIndex(codeAddressToIndex, debugCodeAddress);
/* 108 */       MethodLocation debugLocation = (MethodLocation)this.instructionList.get(locationIndex);
/* 109 */       BuilderDebugItem builderDebugItem = convertDebugItem(debugItem);
/* 110 */       debugLocation.getDebugItems().add(builderDebugItem);
/* 111 */       builderDebugItem.location = debugLocation;
/*     */     }
/*     */ 
/* 114 */     for (TryBlock tryBlock : methodImplementation.getTryBlocks()) {
/* 115 */       startLabel = newLabel(codeAddressToIndex, tryBlock.getStartCodeAddress());
/* 116 */       endLabel = newLabel(codeAddressToIndex, tryBlock.getStartCodeAddress() + tryBlock.getCodeUnitCount());
/*     */ 
/* 118 */       for (ExceptionHandler exceptionHandler : tryBlock.getExceptionHandlers())
/* 119 */         this.tryBlocks.add(new BuilderTryBlock(startLabel, endLabel, exceptionHandler.getExceptionTypeReference(), newLabel(codeAddressToIndex, exceptionHandler.getHandlerCodeAddress())));
/*     */     }
/*     */     Label startLabel;
/*     */     Label endLabel;
/*     */   }
/*     */ 
/*     */   public MutableMethodImplementation(int registerCount)
/*     */   {
/* 131 */     this.registerCount = registerCount;
/*     */   }
/*     */ 
/*     */   public int getRegisterCount() {
/* 135 */     return this.registerCount;
/*     */   }
/*     */ 
/*     */   public List<BuilderInstruction> getInstructions()
/*     */   {
/* 140 */     if (this.fixInstructions) {
/* 141 */       fixInstructions();
/*     */     }
/*     */ 
/* 144 */     return new AbstractList() {
/*     */       public BuilderInstruction get(int i) {
/* 146 */         if (i >= size()) {
/* 147 */           throw new IndexOutOfBoundsException();
/*     */         }
/* 149 */         if (MutableMethodImplementation.this.fixInstructions) {
/* 150 */           MutableMethodImplementation.this.fixInstructions();
/*     */         }
/* 152 */         return ((MethodLocation)MutableMethodImplementation.this.instructionList.get(i)).instruction;
/*     */       }
/*     */ 
/*     */       public int size() {
/* 156 */         if (MutableMethodImplementation.this.fixInstructions) {
/* 157 */           MutableMethodImplementation.this.fixInstructions();
/*     */         }
/*     */ 
/* 160 */         return MutableMethodImplementation.this.instructionList.size() - 1;
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   public List<BuilderTryBlock> getTryBlocks() {
/* 166 */     if (this.fixInstructions) {
/* 167 */       fixInstructions();
/*     */     }
/* 169 */     return Collections.unmodifiableList(this.tryBlocks);
/*     */   }
/*     */ 
/*     */   public Iterable<? extends DebugItem> getDebugItems() {
/* 173 */     if (this.fixInstructions) {
/* 174 */       fixInstructions();
/*     */     }
/* 176 */     return Iterables.concat(Iterables.transform(this.instructionList, new Function()
/*     */     {
/*     */       public Iterable<? extends DebugItem> apply(MethodLocation input) {
/* 179 */         assert (input != null);
/* 180 */         if (MutableMethodImplementation.this.fixInstructions) {
/* 181 */           throw new IllegalStateException("This iterator was invalidated by a change to this MutableMethodImplementation.");
/*     */         }
/*     */ 
/* 184 */         return input.getDebugItems();
/*     */       }
/*     */     }));
/*     */   }
/*     */ 
/*     */   public void addCatch(TypeReference type, Label from, Label to, Label handler) {
/* 191 */     this.tryBlocks.add(new BuilderTryBlock(from, to, type, handler));
/*     */   }
/*     */ 
/*     */   public void addCatch(Label from, Label to, Label handler)
/*     */   {
/* 200 */     this.tryBlocks.add(new BuilderTryBlock(from, to, handler));
/*     */   }
/*     */ 
/*     */   public void addInstruction(int index, BuilderInstruction instruction)
/*     */   {
/* 207 */     if (index >= this.instructionList.size()) {
/* 208 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */ 
/* 211 */     if (index == this.instructionList.size() - 1) {
/* 212 */       addInstruction(instruction);
/* 213 */       return;
/*     */     }
/* 215 */     int codeAddress = ((MethodLocation)this.instructionList.get(index)).getCodeAddress();
/*     */ 
/* 217 */     this.instructionList.add(index, new MethodLocation(instruction, codeAddress, index));
/* 218 */     codeAddress += instruction.getCodeUnits();
/*     */ 
/* 220 */     for (int i = index + 1; i < this.instructionList.size(); i++) {
/* 221 */       MethodLocation location = (MethodLocation)this.instructionList.get(i);
/* 222 */       location.index += 1;
/* 223 */       location.codeAddress = codeAddress;
/* 224 */       if (location.instruction != null) {
/* 225 */         codeAddress += location.instruction.getCodeUnits();
/*     */       }
/*     */       else {
/* 228 */         assert (i == this.instructionList.size() - 1);
/*     */       }
/*     */     }
/*     */ 
/* 232 */     this.fixInstructions = true;
/*     */   }
/*     */ 
/*     */   public void addInstruction(BuilderInstruction instruction) {
/* 236 */     MethodLocation last = (MethodLocation)this.instructionList.get(this.instructionList.size() - 1);
/* 237 */     last.instruction = instruction;
/* 238 */     instruction.location = last;
/*     */ 
/* 240 */     int nextCodeAddress = last.codeAddress + instruction.getCodeUnits();
/* 241 */     this.instructionList.add(new MethodLocation(null, nextCodeAddress, this.instructionList.size()));
/*     */ 
/* 243 */     this.fixInstructions = true;
/*     */   }
/*     */ 
/*     */   public void replaceInstruction(int index, BuilderInstruction replacementInstruction) {
/* 247 */     if (index >= this.instructionList.size() - 1) {
/* 248 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */ 
/* 251 */     MethodLocation replaceLocation = (MethodLocation)this.instructionList.get(index);
/* 252 */     replacementInstruction.location = replaceLocation;
/* 253 */     BuilderInstruction old = replaceLocation.instruction;
/* 254 */     assert (old != null);
/* 255 */     old.location = null;
/* 256 */     replaceLocation.instruction = replacementInstruction;
/*     */ 
/* 259 */     int codeAddress = replaceLocation.codeAddress + replaceLocation.instruction.getCodeUnits();
/* 260 */     for (int i = index + 1; i < this.instructionList.size(); i++) {
/* 261 */       MethodLocation location = (MethodLocation)this.instructionList.get(i);
/* 262 */       location.codeAddress = codeAddress;
/*     */ 
/* 264 */       Instruction instruction = location.getInstruction();
/* 265 */       if (instruction != null)
/* 266 */         codeAddress += instruction.getCodeUnits();
/*     */       else {
/* 268 */         assert (i == this.instructionList.size() - 1);
/*     */       }
/*     */     }
/*     */ 
/* 272 */     this.fixInstructions = true;
/*     */   }
/*     */ 
/*     */   public void removeInstruction(int index) {
/* 276 */     if (index >= this.instructionList.size() - 1) {
/* 277 */       throw new IndexOutOfBoundsException();
/*     */     }
/*     */ 
/* 280 */     MethodLocation toRemove = (MethodLocation)this.instructionList.get(index);
/* 281 */     toRemove.instruction = null;
/* 282 */     MethodLocation next = (MethodLocation)this.instructionList.get(index + 1);
/* 283 */     toRemove.mergeInto(next);
/*     */ 
/* 285 */     this.instructionList.remove(index);
/* 286 */     int codeAddress = toRemove.codeAddress;
/* 287 */     for (int i = index; i < this.instructionList.size(); i++) {
/* 288 */       MethodLocation location = (MethodLocation)this.instructionList.get(i);
/* 289 */       location.index = i;
/* 290 */       location.codeAddress = codeAddress;
/*     */ 
/* 292 */       Instruction instruction = location.getInstruction();
/* 293 */       if (instruction != null)
/* 294 */         codeAddress += instruction.getCodeUnits();
/*     */       else {
/* 296 */         assert (i == this.instructionList.size() - 1);
/*     */       }
/*     */     }
/*     */ 
/* 300 */     this.fixInstructions = true;
/*     */   }
/*     */ 
/*     */   private BuilderInstruction getFirstNonNop(int startIndex)
/*     */   {
/* 345 */     for (int i = startIndex; i < this.instructionList.size() - 1; i++) {
/* 346 */       BuilderInstruction instruction = ((MethodLocation)this.instructionList.get(i)).instruction;
/* 347 */       assert (instruction != null);
/* 348 */       if (instruction.getOpcode() != Opcode.NOP) {
/* 349 */         return instruction;
/*     */       }
/*     */     }
/* 352 */     return null;
/*     */   }
/*     */ 
/*     */   private void fixInstructions() {
/* 356 */     HashSet payloadLocations = Sets.newHashSet();
/*     */ 
/* 358 */     for (MethodLocation location : this.instructionList) {
/* 359 */       BuilderInstruction instruction = location.instruction;
/* 360 */       if (instruction != null) {
/* 361 */         switch (instruction.getOpcode()) {
/*     */         case SPARSE_SWITCH:
/*     */         case PACKED_SWITCH:
/* 364 */           MethodLocation targetLocation = ((BuilderOffsetInstruction)instruction).getTarget().getLocation();
/*     */ 
/* 366 */           BuilderInstruction targetInstruction = targetLocation.instruction;
/* 367 */           if (targetInstruction == null) {
/* 368 */             throw new IllegalStateException(String.format("Switch instruction at address/index 0x%x/%d points to the end of the method.", new Object[] { Integer.valueOf(location.codeAddress), Integer.valueOf(location.index) }));
/*     */           }
/*     */ 
/* 372 */           if (targetInstruction.getOpcode() == Opcode.NOP) {
/* 373 */             targetInstruction = getFirstNonNop(targetLocation.index + 1);
/*     */           }
/* 375 */           if ((targetInstruction == null) || (!(targetInstruction instanceof BuilderSwitchPayload))) {
/* 376 */             throw new IllegalStateException(String.format("Switch instruction at address/index 0x%x/%d does not refer to a payload instruction.", new Object[] { Integer.valueOf(location.codeAddress), Integer.valueOf(location.index) }));
/*     */           }
/*     */ 
/* 380 */           if (((instruction.opcode == Opcode.PACKED_SWITCH) && (targetInstruction.getOpcode() != Opcode.PACKED_SWITCH_PAYLOAD)) || ((instruction.opcode == Opcode.SPARSE_SWITCH) && (targetInstruction.getOpcode() != Opcode.SPARSE_SWITCH_PAYLOAD)))
/*     */           {
/* 384 */             throw new IllegalStateException(String.format("Switch instruction at address/index 0x%x/%d refers to the wrong type of payload instruction.", new Object[] { Integer.valueOf(location.codeAddress), Integer.valueOf(location.index) }));
/*     */           }
/*     */ 
/* 389 */           if (!payloadLocations.add(targetLocation)) {
/* 390 */             throw new IllegalStateException("Multiple switch instructions refer to the same payload. This is not currently supported. Please file a bug :)");
/*     */           }
/*     */ 
/* 394 */           ((BuilderSwitchPayload)targetInstruction).referrer = location;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/*     */     boolean madeChanges;
/*     */     do
/*     */     {
/* 403 */       madeChanges = false;
/*     */ 
/* 405 */       for (int index = 0; index < this.instructionList.size(); index++) {
/* 406 */         MethodLocation location = (MethodLocation)this.instructionList.get(index);
/* 407 */         BuilderInstruction instruction = location.instruction;
/* 408 */         if (instruction != null) {
/* 409 */           switch (4.$SwitchMap$org$jf$dexlib2$Opcode[instruction.getOpcode().ordinal()]) {
/*     */           case 3:
/* 411 */             int offset = ((BuilderOffsetInstruction)instruction).internalGetCodeOffset();
/* 412 */             if ((offset >= -128) && (offset <= 127))
/*     */               continue;
/*     */             BuilderOffsetInstruction replacement;
/*     */             BuilderOffsetInstruction replacement;
/* 414 */             if ((offset < -32768) || (offset > 32767)) {
/* 415 */               replacement = new BuilderInstruction30t(Opcode.GOTO_32, ((BuilderOffsetInstruction)instruction).getTarget());
/*     */             }
/*     */             else {
/* 418 */               replacement = new BuilderInstruction20t(Opcode.GOTO_16, ((BuilderOffsetInstruction)instruction).getTarget());
/*     */             }
/*     */ 
/* 421 */             replaceInstruction(location.index, replacement);
/* 422 */             madeChanges = true;
/* 423 */             break;
/*     */           case 4:
/* 427 */             int offset = ((BuilderOffsetInstruction)instruction).internalGetCodeOffset();
/* 428 */             if ((offset < -32768) || (offset > 32767)) {
/* 429 */               BuilderOffsetInstruction replacement = new BuilderInstruction30t(Opcode.GOTO_32, ((BuilderOffsetInstruction)instruction).getTarget());
/*     */ 
/* 431 */               replaceInstruction(location.index, replacement);
/* 432 */               madeChanges = true;
/* 433 */             }break;
/*     */           case 5:
/*     */           case 6:
/* 438 */             if (((BuilderSwitchPayload)instruction).referrer != null)
/*     */               break;
/* 440 */             removeInstruction(index);
/* 441 */             index--;
/* 442 */             madeChanges = true;
/* 443 */             break;
/*     */           case 7:
/* 447 */             if ((location.codeAddress & 0x1) != 0) {
/* 448 */               int previousIndex = location.index - 1;
/* 449 */               MethodLocation previousLocation = (MethodLocation)this.instructionList.get(previousIndex);
/* 450 */               Instruction previousInstruction = previousLocation.instruction;
/* 451 */               assert (previousInstruction != null);
/* 452 */               if (previousInstruction.getOpcode() == Opcode.NOP) {
/* 453 */                 removeInstruction(previousIndex);
/* 454 */                 index--;
/*     */               } else {
/* 456 */                 addInstruction(location.index, new BuilderInstruction10x(Opcode.NOP));
/* 457 */                 index++;
/*     */               }
/* 459 */               madeChanges = true;
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 466 */     while (madeChanges);
/*     */ 
/* 468 */     this.fixInstructions = false;
/*     */   }
/*     */   private int mapCodeAddressToIndex(int[] codeAddressToIndex, int codeAddress) {
/*     */     int index;
/*     */     while (true) {
/* 474 */       index = codeAddressToIndex[codeAddress];
/* 475 */       if (index >= 0) break;
/* 476 */       codeAddress--;
/*     */     }
/* 478 */     return index;
/*     */   }
/*     */ 
/*     */   private Label newLabel(int[] codeAddressToIndex, int codeAddress)
/*     */   {
/* 485 */     MethodLocation referent = (MethodLocation)this.instructionList.get(mapCodeAddressToIndex(codeAddressToIndex, codeAddress));
/* 486 */     return referent.addNewLabel();
/*     */   }
/*     */ 
/*     */   public Label newSwitchPayloadReferenceLabel(MethodLocation switchLocation, int[] codeAddressToIndex, int codeAddress)
/*     */   {
/* 496 */     MethodLocation referent = (MethodLocation)this.instructionList.get(mapCodeAddressToIndex(codeAddressToIndex, codeAddress));
/* 497 */     SwitchPayloadReferenceLabel label = new SwitchPayloadReferenceLabel(null);
/* 498 */     label.switchLocation = switchLocation;
/* 499 */     referent.getLabels().add(label);
/* 500 */     return label;
/*     */   }
/*     */ 
/*     */   private void setInstruction(MethodLocation location, BuilderInstruction instruction) {
/* 504 */     location.instruction = instruction;
/* 505 */     instruction.location = location;
/*     */   }
/*     */ 
/*     */   private void convertAndSetInstruction(MethodLocation location, int[] codeAddressToIndex, Instruction instruction)
/*     */   {
/* 510 */     switch (4.$SwitchMap$org$jf$dexlib2$Format[instruction.getOpcode().format.ordinal()]) {
/*     */     case 1:
/* 512 */       setInstruction(location, newBuilderInstruction10t(location.codeAddress, codeAddressToIndex, (Instruction10t)instruction));
/*     */ 
/* 514 */       return;
/*     */     case 2:
/* 516 */       setInstruction(location, newBuilderInstruction10x((Instruction10x)instruction));
/* 517 */       return;
/*     */     case 3:
/* 519 */       setInstruction(location, newBuilderInstruction11n((Instruction11n)instruction));
/* 520 */       return;
/*     */     case 4:
/* 522 */       setInstruction(location, newBuilderInstruction11x((Instruction11x)instruction));
/* 523 */       return;
/*     */     case 5:
/* 525 */       setInstruction(location, newBuilderInstruction12x((Instruction12x)instruction));
/* 526 */       return;
/*     */     case 6:
/* 528 */       setInstruction(location, newBuilderInstruction20bc((Instruction20bc)instruction));
/* 529 */       return;
/*     */     case 7:
/* 531 */       setInstruction(location, newBuilderInstruction20t(location.codeAddress, codeAddressToIndex, (Instruction20t)instruction));
/*     */ 
/* 533 */       return;
/*     */     case 8:
/* 535 */       setInstruction(location, newBuilderInstruction21c((Instruction21c)instruction));
/* 536 */       return;
/*     */     case 9:
/* 538 */       setInstruction(location, newBuilderInstruction21ih((Instruction21ih)instruction));
/* 539 */       return;
/*     */     case 10:
/* 541 */       setInstruction(location, newBuilderInstruction21lh((Instruction21lh)instruction));
/* 542 */       return;
/*     */     case 11:
/* 544 */       setInstruction(location, newBuilderInstruction21s((Instruction21s)instruction));
/* 545 */       return;
/*     */     case 12:
/* 547 */       setInstruction(location, newBuilderInstruction21t(location.codeAddress, codeAddressToIndex, (Instruction21t)instruction));
/*     */ 
/* 549 */       return;
/*     */     case 13:
/* 551 */       setInstruction(location, newBuilderInstruction22b((Instruction22b)instruction));
/* 552 */       return;
/*     */     case 14:
/* 554 */       setInstruction(location, newBuilderInstruction22c((Instruction22c)instruction));
/* 555 */       return;
/*     */     case 15:
/* 557 */       setInstruction(location, newBuilderInstruction22s((Instruction22s)instruction));
/* 558 */       return;
/*     */     case 16:
/* 560 */       setInstruction(location, newBuilderInstruction22t(location.codeAddress, codeAddressToIndex, (Instruction22t)instruction));
/*     */ 
/* 562 */       return;
/*     */     case 17:
/* 564 */       setInstruction(location, newBuilderInstruction22x((Instruction22x)instruction));
/* 565 */       return;
/*     */     case 18:
/* 567 */       setInstruction(location, newBuilderInstruction23x((Instruction23x)instruction));
/* 568 */       return;
/*     */     case 19:
/* 570 */       setInstruction(location, newBuilderInstruction30t(location.codeAddress, codeAddressToIndex, (Instruction30t)instruction));
/*     */ 
/* 572 */       return;
/*     */     case 20:
/* 574 */       setInstruction(location, newBuilderInstruction31c((Instruction31c)instruction));
/* 575 */       return;
/*     */     case 21:
/* 577 */       setInstruction(location, newBuilderInstruction31i((Instruction31i)instruction));
/* 578 */       return;
/*     */     case 22:
/* 580 */       setInstruction(location, newBuilderInstruction31t(location, codeAddressToIndex, (Instruction31t)instruction));
/*     */ 
/* 582 */       return;
/*     */     case 23:
/* 584 */       setInstruction(location, newBuilderInstruction32x((Instruction32x)instruction));
/* 585 */       return;
/*     */     case 24:
/* 587 */       setInstruction(location, newBuilderInstruction35c((Instruction35c)instruction));
/* 588 */       return;
/*     */     case 25:
/* 590 */       setInstruction(location, newBuilderInstruction3rc((Instruction3rc)instruction));
/* 591 */       return;
/*     */     case 26:
/* 593 */       setInstruction(location, newBuilderInstruction51l((Instruction51l)instruction));
/* 594 */       return;
/*     */     case 27:
/* 596 */       setInstruction(location, newBuilderPackedSwitchPayload(location, codeAddressToIndex, (PackedSwitchPayload)instruction));
/*     */ 
/* 598 */       return;
/*     */     case 28:
/* 600 */       setInstruction(location, newBuilderSparseSwitchPayload(location, codeAddressToIndex, (SparseSwitchPayload)instruction));
/*     */ 
/* 602 */       return;
/*     */     case 29:
/* 604 */       setInstruction(location, newBuilderArrayPayload((ArrayPayload)instruction));
/* 605 */       return;
/*     */     }
/* 607 */     throw new ExceptionWithContext("Instruction format %s not supported", new Object[] { instruction.getOpcode().format });
/*     */   }
/*     */ 
/*     */   private BuilderInstruction10t newBuilderInstruction10t(int codeAddress, int[] codeAddressToIndex, Instruction10t instruction)
/*     */   {
/* 614 */     return new BuilderInstruction10t(instruction.getOpcode(), newLabel(codeAddressToIndex, codeAddress + instruction.getCodeOffset()));
/*     */   }
/*     */ 
/*     */   private BuilderInstruction10x newBuilderInstruction10x(Instruction10x instruction)
/*     */   {
/* 621 */     return new BuilderInstruction10x(instruction.getOpcode());
/*     */   }
/*     */ 
/*     */   private BuilderInstruction11n newBuilderInstruction11n(Instruction11n instruction)
/*     */   {
/* 627 */     return new BuilderInstruction11n(instruction.getOpcode(), instruction.getRegisterA(), instruction.getNarrowLiteral());
/*     */   }
/*     */ 
/*     */   private BuilderInstruction11x newBuilderInstruction11x(Instruction11x instruction)
/*     */   {
/* 635 */     return new BuilderInstruction11x(instruction.getOpcode(), instruction.getRegisterA());
/*     */   }
/*     */ 
/*     */   private BuilderInstruction12x newBuilderInstruction12x(Instruction12x instruction)
/*     */   {
/* 642 */     return new BuilderInstruction12x(instruction.getOpcode(), instruction.getRegisterA(), instruction.getRegisterB());
/*     */   }
/*     */ 
/*     */   private BuilderInstruction20bc newBuilderInstruction20bc(Instruction20bc instruction)
/*     */   {
/* 650 */     return new BuilderInstruction20bc(instruction.getOpcode(), instruction.getVerificationError(), instruction.getReference());
/*     */   }
/*     */ 
/*     */   private BuilderInstruction20t newBuilderInstruction20t(int codeAddress, int[] codeAddressToIndex, Instruction20t instruction)
/*     */   {
/* 659 */     return new BuilderInstruction20t(instruction.getOpcode(), newLabel(codeAddressToIndex, codeAddress + instruction.getCodeOffset()));
/*     */   }
/*     */ 
/*     */   private BuilderInstruction21c newBuilderInstruction21c(Instruction21c instruction)
/*     */   {
/* 666 */     return new BuilderInstruction21c(instruction.getOpcode(), instruction.getRegisterA(), instruction.getReference());
/*     */   }
/*     */ 
/*     */   private BuilderInstruction21ih newBuilderInstruction21ih(Instruction21ih instruction)
/*     */   {
/* 674 */     return new BuilderInstruction21ih(instruction.getOpcode(), instruction.getRegisterA(), instruction.getNarrowLiteral());
/*     */   }
/*     */ 
/*     */   private BuilderInstruction21lh newBuilderInstruction21lh(Instruction21lh instruction)
/*     */   {
/* 682 */     return new BuilderInstruction21lh(instruction.getOpcode(), instruction.getRegisterA(), instruction.getWideLiteral());
/*     */   }
/*     */ 
/*     */   private BuilderInstruction21s newBuilderInstruction21s(Instruction21s instruction)
/*     */   {
/* 690 */     return new BuilderInstruction21s(instruction.getOpcode(), instruction.getRegisterA(), instruction.getNarrowLiteral());
/*     */   }
/*     */ 
/*     */   private BuilderInstruction21t newBuilderInstruction21t(int codeAddress, int[] codeAddressToIndex, Instruction21t instruction)
/*     */   {
/* 699 */     return new BuilderInstruction21t(instruction.getOpcode(), instruction.getRegisterA(), newLabel(codeAddressToIndex, codeAddress + instruction.getCodeOffset()));
/*     */   }
/*     */ 
/*     */   private BuilderInstruction22b newBuilderInstruction22b(Instruction22b instruction)
/*     */   {
/* 707 */     return new BuilderInstruction22b(instruction.getOpcode(), instruction.getRegisterA(), instruction.getRegisterB(), instruction.getNarrowLiteral());
/*     */   }
/*     */ 
/*     */   private BuilderInstruction22c newBuilderInstruction22c(Instruction22c instruction)
/*     */   {
/* 716 */     return new BuilderInstruction22c(instruction.getOpcode(), instruction.getRegisterA(), instruction.getRegisterB(), instruction.getReference());
/*     */   }
/*     */ 
/*     */   private BuilderInstruction22s newBuilderInstruction22s(Instruction22s instruction)
/*     */   {
/* 725 */     return new BuilderInstruction22s(instruction.getOpcode(), instruction.getRegisterA(), instruction.getRegisterB(), instruction.getNarrowLiteral());
/*     */   }
/*     */ 
/*     */   private BuilderInstruction22t newBuilderInstruction22t(int codeAddress, int[] codeAddressToIndex, Instruction22t instruction)
/*     */   {
/* 735 */     return new BuilderInstruction22t(instruction.getOpcode(), instruction.getRegisterA(), instruction.getRegisterB(), newLabel(codeAddressToIndex, codeAddress + instruction.getCodeOffset()));
/*     */   }
/*     */ 
/*     */   private BuilderInstruction22x newBuilderInstruction22x(Instruction22x instruction)
/*     */   {
/* 744 */     return new BuilderInstruction22x(instruction.getOpcode(), instruction.getRegisterA(), instruction.getRegisterB());
/*     */   }
/*     */ 
/*     */   private BuilderInstruction23x newBuilderInstruction23x(Instruction23x instruction)
/*     */   {
/* 752 */     return new BuilderInstruction23x(instruction.getOpcode(), instruction.getRegisterA(), instruction.getRegisterB(), instruction.getRegisterC());
/*     */   }
/*     */ 
/*     */   private BuilderInstruction30t newBuilderInstruction30t(int codeAddress, int[] codeAddressToIndex, Instruction30t instruction)
/*     */   {
/* 762 */     return new BuilderInstruction30t(instruction.getOpcode(), newLabel(codeAddressToIndex, codeAddress + instruction.getCodeOffset()));
/*     */   }
/*     */ 
/*     */   private BuilderInstruction31c newBuilderInstruction31c(Instruction31c instruction)
/*     */   {
/* 769 */     return new BuilderInstruction31c(instruction.getOpcode(), instruction.getRegisterA(), instruction.getReference());
/*     */   }
/*     */ 
/*     */   private BuilderInstruction31i newBuilderInstruction31i(Instruction31i instruction)
/*     */   {
/* 777 */     return new BuilderInstruction31i(instruction.getOpcode(), instruction.getRegisterA(), instruction.getNarrowLiteral());
/*     */   }
/*     */ 
/*     */   private BuilderInstruction31t newBuilderInstruction31t(MethodLocation location, int[] codeAddressToIndex, Instruction31t instruction)
/*     */   {
/* 786 */     int codeAddress = location.getCodeAddress();
/*     */     Label newLabel;
/*     */     Label newLabel;
/* 788 */     if (instruction.getOpcode() != Opcode.FILL_ARRAY_DATA)
/*     */     {
/* 790 */       newLabel = newSwitchPayloadReferenceLabel(location, codeAddressToIndex, codeAddress + instruction.getCodeOffset());
/*     */     }
/* 792 */     else newLabel = newLabel(codeAddressToIndex, codeAddress + instruction.getCodeOffset());
/*     */ 
/* 794 */     return new BuilderInstruction31t(instruction.getOpcode(), instruction.getRegisterA(), newLabel);
/*     */   }
/*     */ 
/*     */   private BuilderInstruction32x newBuilderInstruction32x(Instruction32x instruction)
/*     */   {
/* 802 */     return new BuilderInstruction32x(instruction.getOpcode(), instruction.getRegisterA(), instruction.getRegisterB());
/*     */   }
/*     */ 
/*     */   private BuilderInstruction35c newBuilderInstruction35c(Instruction35c instruction)
/*     */   {
/* 810 */     return new BuilderInstruction35c(instruction.getOpcode(), instruction.getRegisterCount(), instruction.getRegisterC(), instruction.getRegisterD(), instruction.getRegisterE(), instruction.getRegisterF(), instruction.getRegisterG(), instruction.getReference());
/*     */   }
/*     */ 
/*     */   private BuilderInstruction3rc newBuilderInstruction3rc(Instruction3rc instruction)
/*     */   {
/* 823 */     return new BuilderInstruction3rc(instruction.getOpcode(), instruction.getStartRegister(), instruction.getRegisterCount(), instruction.getReference());
/*     */   }
/*     */ 
/*     */   private BuilderInstruction51l newBuilderInstruction51l(Instruction51l instruction)
/*     */   {
/* 832 */     return new BuilderInstruction51l(instruction.getOpcode(), instruction.getRegisterA(), instruction.getWideLiteral());
/*     */   }
/*     */ 
/*     */   private MethodLocation findSwitchForPayload(MethodLocation payloadLocation)
/*     */   {
/* 840 */     MethodLocation location = payloadLocation;
/* 841 */     MethodLocation switchLocation = null;
/*     */     do {
/* 843 */       for (Label label : location.getLabels()) {
/* 844 */         if ((label instanceof SwitchPayloadReferenceLabel)) {
/* 845 */           if (switchLocation != null) {
/* 846 */             throw new IllegalStateException("Multiple switch instructions refer to the same payload. This is not currently supported. Please file a bug :)");
/*     */           }
/*     */ 
/* 849 */           switchLocation = ((SwitchPayloadReferenceLabel)label).switchLocation;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 858 */       if (location.index == 0) {
/* 859 */         return switchLocation;
/*     */       }
/* 861 */       location = (MethodLocation)this.instructionList.get(location.index - 1);
/* 862 */     }while ((location.instruction != null) && (location.instruction.getOpcode() == Opcode.NOP));
/* 863 */     return switchLocation;
/*     */   }
/*     */ 
/*     */   private BuilderPackedSwitchPayload newBuilderPackedSwitchPayload(MethodLocation location, int[] codeAddressToIndex, PackedSwitchPayload instruction)
/*     */   {
/* 872 */     List switchElements = instruction.getSwitchElements();
/* 873 */     if (switchElements.size() == 0) {
/* 874 */       return new BuilderPackedSwitchPayload(0, null);
/*     */     }
/*     */ 
/* 877 */     MethodLocation switchLocation = findSwitchForPayload(location);
/*     */     int baseAddress;
/*     */     int baseAddress;
/* 879 */     if (switchLocation == null)
/* 880 */       baseAddress = 0;
/*     */     else {
/* 882 */       baseAddress = switchLocation.codeAddress;
/*     */     }
/*     */ 
/* 885 */     List labels = Lists.newArrayList();
/* 886 */     for (SwitchElement element : switchElements) {
/* 887 */       labels.add(newLabel(codeAddressToIndex, element.getOffset() + baseAddress));
/*     */     }
/*     */ 
/* 890 */     return new BuilderPackedSwitchPayload(((SwitchElement)switchElements.get(0)).getKey(), labels);
/*     */   }
/*     */ 
/*     */   private BuilderSparseSwitchPayload newBuilderSparseSwitchPayload(MethodLocation location, int[] codeAddressToIndex, SparseSwitchPayload instruction)
/*     */   {
/* 897 */     List switchElements = instruction.getSwitchElements();
/* 898 */     if (switchElements.size() == 0) {
/* 899 */       return new BuilderSparseSwitchPayload(null);
/*     */     }
/*     */ 
/* 902 */     MethodLocation switchLocation = findSwitchForPayload(location);
/*     */     int baseAddress;
/*     */     int baseAddress;
/* 904 */     if (switchLocation == null)
/* 905 */       baseAddress = 0;
/*     */     else {
/* 907 */       baseAddress = switchLocation.codeAddress;
/*     */     }
/*     */ 
/* 910 */     List labelElements = Lists.newArrayList();
/* 911 */     for (SwitchElement element : switchElements) {
/* 912 */       labelElements.add(new SwitchLabelElement(element.getKey(), newLabel(codeAddressToIndex, element.getOffset() + baseAddress)));
/*     */     }
/*     */ 
/* 916 */     return new BuilderSparseSwitchPayload(labelElements);
/*     */   }
/*     */ 
/*     */   private BuilderArrayPayload newBuilderArrayPayload(ArrayPayload instruction)
/*     */   {
/* 921 */     return new BuilderArrayPayload(instruction.getElementWidth(), instruction.getArrayElements());
/*     */   }
/*     */ 
/*     */   private BuilderDebugItem convertDebugItem(DebugItem debugItem)
/*     */   {
/* 926 */     switch (debugItem.getDebugItemType()) {
/*     */     case 3:
/* 928 */       StartLocal startLocal = (StartLocal)debugItem;
/* 929 */       return new BuilderStartLocal(startLocal.getRegister(), startLocal.getNameReference(), startLocal.getTypeReference(), startLocal.getSignatureReference());
/*     */     case 5:
/* 933 */       EndLocal endLocal = (EndLocal)debugItem;
/* 934 */       return new BuilderEndLocal(endLocal.getRegister());
/*     */     case 6:
/* 937 */       RestartLocal restartLocal = (RestartLocal)debugItem;
/* 938 */       return new BuilderRestartLocal(restartLocal.getRegister());
/*     */     case 7:
/* 941 */       return new BuilderPrologueEnd();
/*     */     case 8:
/* 943 */       return new BuilderEpilogueBegin();
/*     */     case 10:
/* 945 */       LineNumber lineNumber = (LineNumber)debugItem;
/* 946 */       return new BuilderLineNumber(lineNumber.getLineNumber());
/*     */     case 9:
/* 949 */       SetSourceFile setSourceFile = (SetSourceFile)debugItem;
/* 950 */       return new BuilderSetSourceFile(setSourceFile.getSourceFileReference());
/*     */     case 4:
/*     */     }
/* 953 */     throw new ExceptionWithContext("Invalid debug item type: " + debugItem.getDebugItemType(), new Object[0]);
/*     */   }
/*     */ 
/*     */   private static class SwitchPayloadReferenceLabel extends Label
/*     */   {
/*     */     public MethodLocation switchLocation;
/*     */   }
/*     */ 
/*     */   private static abstract interface Task
/*     */   {
/*     */     public abstract void perform();
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.MutableMethodImplementation
 * JD-Core Version:    0.6.0
 */