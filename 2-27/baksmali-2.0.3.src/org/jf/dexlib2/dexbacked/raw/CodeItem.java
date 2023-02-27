/*     */ package org.jf.dexlib2.dexbacked.raw;
/*     */ 
/*     */ import com.google.common.base.Joiner;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import org.jf.dexlib2.Opcode;
/*     */ import org.jf.dexlib2.VerificationError;
/*     */ import org.jf.dexlib2.dexbacked.DexReader;
/*     */ import org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction;
/*     */ import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
/*     */ import org.jf.dexlib2.iface.instruction.FieldOffsetInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.InlineIndexInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.Instruction;
/*     */ import org.jf.dexlib2.iface.instruction.NarrowLiteralInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.OffsetInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.OneRegisterInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.SwitchElement;
/*     */ import org.jf.dexlib2.iface.instruction.ThreeRegisterInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.TwoRegisterInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.VerificationErrorInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.VtableIndexInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.WideLiteralInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.formats.ArrayPayload;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction35c;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction3rc;
/*     */ import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload;
/*     */ import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload;
/*     */ import org.jf.dexlib2.util.AnnotatedBytes;
/*     */ import org.jf.dexlib2.util.ReferenceUtil;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ import org.jf.util.NumberUtils;
/*     */ 
/*     */ public class CodeItem
/*     */ {
/*     */   public static SectionAnnotator makeAnnotator(DexAnnotator annotator, MapItem mapItem)
/*     */   {
/*  70 */     return new SectionAnnotator(annotator, mapItem) {
/*  71 */       private SectionAnnotator debugInfoAnnotator = null;
/*     */ 
/*     */       public void annotateSection(AnnotatedBytes out) {
/*  74 */         this.debugInfoAnnotator = this.annotator.getAnnotator(8195);
/*  75 */         super.annotateSection(out);
/*     */       }
/*     */ 
/*     */       public String getItemName() {
/*  79 */         return "code_item";
/*     */       }
/*     */ 
/*     */       public int getItemAlignment() {
/*  83 */         return 4;
/*     */       }
/*     */ 
/*     */       public void annotateItem(AnnotatedBytes out, int itemIndex, String itemIdentity)
/*     */       {
/*     */         try {
/*  89 */           DexReader reader = this.dexFile.readerAt(out.getCursor());
/*     */ 
/*  91 */           int registers = reader.readUshort();
/*  92 */           out.annotate(2, "registers_size = %d", new Object[] { Integer.valueOf(registers) });
/*     */ 
/*  94 */           int inSize = reader.readUshort();
/*  95 */           out.annotate(2, "ins_size = %d", new Object[] { Integer.valueOf(inSize) });
/*     */ 
/*  97 */           int outSize = reader.readUshort();
/*  98 */           out.annotate(2, "outs_size = %d", new Object[] { Integer.valueOf(outSize) });
/*     */ 
/* 100 */           int triesCount = reader.readUshort();
/* 101 */           out.annotate(2, "tries_size = %d", new Object[] { Integer.valueOf(triesCount) });
/*     */ 
/* 103 */           int debugInfoOffset = reader.readSmallUint();
/* 104 */           out.annotate(4, "debug_info_off = 0x%x", new Object[] { Integer.valueOf(debugInfoOffset) });
/*     */ 
/* 106 */           if (debugInfoOffset != 0) {
/* 107 */             addDebugInfoIdentity(debugInfoOffset, itemIdentity);
/*     */           }
/*     */ 
/* 110 */           int instructionSize = reader.readSmallUint();
/* 111 */           out.annotate(4, "insns_size = 0x%x", new Object[] { Integer.valueOf(instructionSize) });
/*     */ 
/* 113 */           out.annotate(0, "instructions:", new Object[0]);
/* 114 */           out.indent();
/*     */ 
/* 116 */           out.setLimit(out.getCursor(), out.getCursor() + instructionSize * 2);
/*     */ 
/* 118 */           int end = reader.getOffset() + instructionSize * 2;
/*     */           try {
/* 120 */             while (reader.getOffset() < end) {
/* 121 */               Instruction instruction = DexBackedInstruction.readFrom(reader);
/*     */ 
/* 124 */               if (reader.getOffset() > end) {
/* 125 */                 out.annotateTo(end, "truncated instruction", new Object[0]);
/* 126 */                 reader.setOffset(end);
/*     */               } else {
/* 128 */                 switch (CodeItem.2.$SwitchMap$org$jf$dexlib2$Format[instruction.getOpcode().format.ordinal()]) {
/*     */                 case 1:
/* 130 */                   annotateInstruction10x(out, instruction);
/* 131 */                   break;
/*     */                 case 2:
/* 133 */                   annotateInstruction35c(out, (Instruction35c)instruction);
/* 134 */                   break;
/*     */                 case 3:
/* 136 */                   annotateInstruction3rc(out, (Instruction3rc)instruction);
/* 137 */                   break;
/*     */                 case 4:
/* 139 */                   annotateArrayPayload(out, (ArrayPayload)instruction);
/* 140 */                   break;
/*     */                 case 5:
/* 142 */                   annotatePackedSwitchPayload(out, (PackedSwitchPayload)instruction);
/* 143 */                   break;
/*     */                 case 6:
/* 145 */                   annotateSparseSwitchPayload(out, (SparseSwitchPayload)instruction);
/* 146 */                   break;
/*     */                 default:
/* 148 */                   annotateDefaultInstruction(out, instruction);
/*     */                 }
/*     */ 
/*     */               }
/*     */ 
/* 153 */               assert (reader.getOffset() == out.getCursor());
/*     */             }
/*     */           } catch (ExceptionWithContext ex) {
/* 156 */             ex.printStackTrace(System.err);
/* 157 */             out.annotate(0, "annotation error: %s", new Object[] { ex.getMessage() });
/* 158 */             out.moveTo(end);
/* 159 */             reader.setOffset(end);
/*     */           } finally {
/* 161 */             out.clearLimit();
/* 162 */             out.deindent();
/*     */           }
/*     */ 
/* 165 */           if (triesCount > 0) {
/* 166 */             if (reader.getOffset() % 4 != 0) {
/* 167 */               reader.readUshort();
/* 168 */               out.annotate(2, "padding", new Object[0]);
/*     */             }
/*     */ 
/* 171 */             out.annotate(0, "try_items:", new Object[0]);
/* 172 */             out.indent();
/*     */             try {
/* 174 */               for (int i = 0; i < triesCount; i++) {
/* 175 */                 out.annotate(0, "try_item[%d]:", new Object[] { Integer.valueOf(i) });
/* 176 */                 out.indent();
/*     */                 try {
/* 178 */                   int startAddr = reader.readSmallUint();
/* 179 */                   out.annotate(4, "start_addr = 0x%x", new Object[] { Integer.valueOf(startAddr) });
/*     */ 
/* 181 */                   int instructionCount = reader.readUshort();
/* 182 */                   out.annotate(2, "insn_count = 0x%x", new Object[] { Integer.valueOf(instructionCount) });
/*     */ 
/* 184 */                   int handlerOffset = reader.readUshort();
/* 185 */                   out.annotate(2, "handler_off = 0x%x", new Object[] { Integer.valueOf(handlerOffset) });
/*     */                 } finally {
/* 187 */                   out.deindent();
/*     */                 }
/*     */               }
/*     */             } finally {
/* 191 */               out.deindent();
/*     */             }
/*     */ 
/* 194 */             int handlerListCount = reader.readSmallUleb128();
/* 195 */             out.annotate(0, "encoded_catch_handler_list:", new Object[0]);
/* 196 */             out.annotateTo(reader.getOffset(), "size = %d", new Object[] { Integer.valueOf(handlerListCount) });
/* 197 */             out.indent();
/*     */             try {
/* 199 */               for (int i = 0; i < handlerListCount; i++) {
/* 200 */                 out.annotate(0, "encoded_catch_handler[%d]", new Object[] { Integer.valueOf(i) });
/* 201 */                 out.indent();
/*     */                 try {
/* 203 */                   int handlerCount = reader.readSleb128();
/* 204 */                   out.annotateTo(reader.getOffset(), "size = %d", new Object[] { Integer.valueOf(handlerCount) });
/* 205 */                   boolean hasCatchAll = handlerCount <= 0;
/* 206 */                   handlerCount = Math.abs(handlerCount);
/* 207 */                   if (handlerCount != 0) {
/* 208 */                     out.annotate(0, "handlers:", new Object[0]);
/* 209 */                     out.indent();
/*     */                     try {
/* 211 */                       for (int j = 0; j < handlerCount; j++) {
/* 212 */                         out.annotate(0, "encoded_type_addr_pair[%d]", new Object[] { Integer.valueOf(i) });
/* 213 */                         out.indent();
/*     */                         try {
/* 215 */                           int typeIndex = reader.readSmallUleb128();
/* 216 */                           out.annotateTo(reader.getOffset(), TypeIdItem.getReferenceAnnotation(this.dexFile, typeIndex), new Object[0]);
/*     */ 
/* 218 */                           int handlerAddress = reader.readSmallUleb128();
/* 219 */                           out.annotateTo(reader.getOffset(), "addr = 0x%x", new Object[] { Integer.valueOf(handlerAddress) });
/*     */                         } finally {
/* 221 */                           out.deindent();
/*     */                         }
/*     */                       }
/*     */                     } finally {
/* 225 */                       out.deindent();
/*     */                     }
/*     */                   }
/* 228 */                   if (hasCatchAll) {
/* 229 */                     int catchAllAddress = reader.readSmallUleb128();
/* 230 */                     out.annotateTo(reader.getOffset(), "catch_all_addr = 0x%x", new Object[] { Integer.valueOf(catchAllAddress) });
/*     */                   }
/*     */                 } finally {
/* 233 */                   out.deindent();
/*     */                 }
/*     */               }
/*     */             } finally {
/* 237 */               out.deindent();
/*     */             }
/*     */           }
/*     */         } catch (ExceptionWithContext ex) {
/* 241 */           out.annotate(0, "annotation error: %s", new Object[] { ex.getMessage() });
/*     */         }
/*     */       }
/*     */ 
/*     */       private String formatRegister(int registerNum) {
/* 246 */         return String.format("v%d", new Object[] { Integer.valueOf(registerNum) });
/*     */       }
/*     */ 
/*     */       private void annotateInstruction10x(AnnotatedBytes out, Instruction instruction) {
/* 250 */         out.annotate(2, instruction.getOpcode().name, new Object[0]);
/*     */       }
/*     */ 
/*     */       private void annotateInstruction35c(AnnotatedBytes out, Instruction35c instruction) {
/* 254 */         List args = Lists.newArrayList();
/*     */ 
/* 256 */         int registerCount = instruction.getRegisterCount();
/* 257 */         if (registerCount == 1) {
/* 258 */           args.add(formatRegister(instruction.getRegisterC()));
/* 259 */         } else if (registerCount == 2) {
/* 260 */           args.add(formatRegister(instruction.getRegisterC()));
/* 261 */           args.add(formatRegister(instruction.getRegisterD()));
/* 262 */         } else if (registerCount == 3) {
/* 263 */           args.add(formatRegister(instruction.getRegisterC()));
/* 264 */           args.add(formatRegister(instruction.getRegisterD()));
/* 265 */           args.add(formatRegister(instruction.getRegisterE()));
/* 266 */         } else if (registerCount == 4) {
/* 267 */           args.add(formatRegister(instruction.getRegisterC()));
/* 268 */           args.add(formatRegister(instruction.getRegisterD()));
/* 269 */           args.add(formatRegister(instruction.getRegisterE()));
/* 270 */           args.add(formatRegister(instruction.getRegisterF()));
/* 271 */         } else if (registerCount == 5) {
/* 272 */           args.add(formatRegister(instruction.getRegisterC()));
/* 273 */           args.add(formatRegister(instruction.getRegisterD()));
/* 274 */           args.add(formatRegister(instruction.getRegisterE()));
/* 275 */           args.add(formatRegister(instruction.getRegisterF()));
/* 276 */           args.add(formatRegister(instruction.getRegisterG()));
/*     */         }
/*     */ 
/* 279 */         String reference = ReferenceUtil.getReferenceString(instruction.getReference());
/*     */ 
/* 281 */         out.annotate(6, String.format("%s {%s}, %s", new Object[] { instruction.getOpcode().name, Joiner.on(", ").join(args), reference }), new Object[0]);
/*     */       }
/*     */ 
/*     */       private void annotateInstruction3rc(AnnotatedBytes out, Instruction3rc instruction)
/*     */       {
/* 286 */         int startRegister = instruction.getStartRegister();
/* 287 */         int endRegister = startRegister + instruction.getRegisterCount() - 1;
/* 288 */         String reference = ReferenceUtil.getReferenceString(instruction.getReference());
/* 289 */         out.annotate(6, String.format("%s {%s .. %s}, %s", new Object[] { instruction.getOpcode().name, formatRegister(startRegister), formatRegister(endRegister), reference }), new Object[0]);
/*     */       }
/*     */ 
/*     */       private void annotateDefaultInstruction(AnnotatedBytes out, Instruction instruction)
/*     */       {
/* 295 */         List args = Lists.newArrayList();
/*     */ 
/* 297 */         if ((instruction instanceof OneRegisterInstruction)) {
/* 298 */           args.add(formatRegister(((OneRegisterInstruction)instruction).getRegisterA()));
/* 299 */           if ((instruction instanceof TwoRegisterInstruction)) {
/* 300 */             args.add(formatRegister(((TwoRegisterInstruction)instruction).getRegisterB()));
/* 301 */             if ((instruction instanceof ThreeRegisterInstruction))
/* 302 */               args.add(formatRegister(((ThreeRegisterInstruction)instruction).getRegisterC()));
/*     */           }
/*     */         }
/* 305 */         else if ((instruction instanceof VerificationErrorInstruction)) {
/* 306 */           String verificationError = VerificationError.getVerificationErrorName(((VerificationErrorInstruction)instruction).getVerificationError());
/*     */ 
/* 308 */           if (verificationError != null)
/* 309 */             args.add(verificationError);
/*     */           else {
/* 311 */             args.add("invalid verification error type");
/*     */           }
/*     */         }
/*     */ 
/* 315 */         if ((instruction instanceof ReferenceInstruction)) {
/* 316 */           args.add(ReferenceUtil.getReferenceString(((ReferenceInstruction)instruction).getReference()));
/* 317 */         } else if ((instruction instanceof OffsetInstruction)) {
/* 318 */           int offset = ((OffsetInstruction)instruction).getCodeOffset();
/* 319 */           String sign = offset >= 0 ? "+" : "-";
/* 320 */           args.add(String.format("%s0x%x", new Object[] { sign, Integer.valueOf(Math.abs(offset)) }));
/* 321 */         } else if ((instruction instanceof NarrowLiteralInstruction)) {
/* 322 */           int value = ((NarrowLiteralInstruction)instruction).getNarrowLiteral();
/* 323 */           if (NumberUtils.isLikelyFloat(value))
/* 324 */             args.add(String.format("%d # %f", new Object[] { Integer.valueOf(value), Float.valueOf(Float.intBitsToFloat(value)) }));
/*     */           else
/* 326 */             args.add(String.format("%d", new Object[] { Integer.valueOf(value) }));
/*     */         }
/* 328 */         else if ((instruction instanceof WideLiteralInstruction)) {
/* 329 */           long value = ((WideLiteralInstruction)instruction).getWideLiteral();
/* 330 */           if (NumberUtils.isLikelyDouble(value))
/* 331 */             args.add(String.format("%d # %f", new Object[] { Long.valueOf(value), Double.valueOf(Double.longBitsToDouble(value)) }));
/*     */           else
/* 333 */             args.add(String.format("%d", new Object[] { Long.valueOf(value) }));
/*     */         }
/* 335 */         else if ((instruction instanceof FieldOffsetInstruction)) {
/* 336 */           int fieldOffset = ((FieldOffsetInstruction)instruction).getFieldOffset();
/* 337 */           args.add(String.format("field@0x%x", new Object[] { Integer.valueOf(fieldOffset) }));
/* 338 */         } else if ((instruction instanceof VtableIndexInstruction)) {
/* 339 */           int vtableIndex = ((VtableIndexInstruction)instruction).getVtableIndex();
/* 340 */           args.add(String.format("vtable@%d", new Object[] { Integer.valueOf(vtableIndex) }));
/* 341 */         } else if ((instruction instanceof InlineIndexInstruction)) {
/* 342 */           int inlineIndex = ((InlineIndexInstruction)instruction).getInlineIndex();
/* 343 */           args.add(String.format("inline@%d", new Object[] { Integer.valueOf(inlineIndex) }));
/*     */         }
/*     */ 
/* 346 */         out.annotate(instruction.getCodeUnits() * 2, "%s %s", new Object[] { instruction.getOpcode().name, Joiner.on(", ").join(args) });
/*     */       }
/*     */ 
/*     */       private void annotateArrayPayload(AnnotatedBytes out, ArrayPayload instruction)
/*     */       {
/* 351 */         List elements = instruction.getArrayElements();
/* 352 */         int elementWidth = instruction.getElementWidth();
/*     */ 
/* 354 */         out.annotate(2, instruction.getOpcode().name, new Object[0]);
/* 355 */         out.indent();
/* 356 */         out.annotate(2, "element_width = %d", new Object[] { Integer.valueOf(elementWidth) });
/* 357 */         out.annotate(4, "size = %d", new Object[] { Integer.valueOf(elements.size()) });
/* 358 */         out.annotate(0, "elements:", new Object[0]);
/* 359 */         out.indent();
/* 360 */         for (int i = 0; i < elements.size(); i++) {
/* 361 */           if (elementWidth == 8) {
/* 362 */             long value = ((Number)elements.get(i)).longValue();
/* 363 */             if (NumberUtils.isLikelyDouble(value))
/* 364 */               out.annotate(elementWidth, "element[%d] = %d # %f", new Object[] { Integer.valueOf(i), Long.valueOf(value), Double.valueOf(Double.longBitsToDouble(value)) });
/*     */             else
/* 366 */               out.annotate(elementWidth, "element[%d] = %d", new Object[] { Integer.valueOf(i), Long.valueOf(value) });
/*     */           }
/*     */           else {
/* 369 */             int value = ((Number)elements.get(i)).intValue();
/* 370 */             if (NumberUtils.isLikelyFloat(value))
/* 371 */               out.annotate(elementWidth, "element[%d] = %d # %f", new Object[] { Integer.valueOf(i), Integer.valueOf(value), Float.valueOf(Float.intBitsToFloat(value)) });
/*     */             else {
/* 373 */               out.annotate(elementWidth, "element[%d] = %d", new Object[] { Integer.valueOf(i), Integer.valueOf(value) });
/*     */             }
/*     */           }
/*     */         }
/* 377 */         if (out.getCursor() % 2 != 0) {
/* 378 */           out.annotate(1, "padding", new Object[0]);
/*     */         }
/* 380 */         out.deindent();
/* 381 */         out.deindent();
/*     */       }
/*     */ 
/*     */       private void annotatePackedSwitchPayload(AnnotatedBytes out, PackedSwitchPayload instruction)
/*     */       {
/* 386 */         List elements = instruction.getSwitchElements();
/*     */ 
/* 388 */         out.annotate(2, instruction.getOpcode().name, new Object[0]);
/* 389 */         out.indent();
/*     */ 
/* 391 */         out.annotate(2, "size = %d", new Object[] { Integer.valueOf(elements.size()) });
/* 392 */         if (elements.size() == 0) {
/* 393 */           out.annotate(4, "first_key", new Object[0]);
/*     */         } else {
/* 395 */           out.annotate(4, "first_key = %d", new Object[] { Integer.valueOf(((SwitchElement)elements.get(0)).getKey()) });
/* 396 */           out.annotate(0, "targets:", new Object[0]);
/* 397 */           out.indent();
/* 398 */           for (int i = 0; i < elements.size(); i++) {
/* 399 */             out.annotate(4, "target[%d] = %d", new Object[] { Integer.valueOf(i), Integer.valueOf(((SwitchElement)elements.get(i)).getOffset()) });
/*     */           }
/* 401 */           out.deindent();
/*     */         }
/* 403 */         out.deindent();
/*     */       }
/*     */ 
/*     */       private void annotateSparseSwitchPayload(AnnotatedBytes out, SparseSwitchPayload instruction)
/*     */       {
/* 408 */         List elements = instruction.getSwitchElements();
/*     */ 
/* 410 */         out.annotate(2, instruction.getOpcode().name, new Object[0]);
/* 411 */         out.indent();
/* 412 */         out.annotate(2, "size = %d", new Object[] { Integer.valueOf(elements.size()) });
/* 413 */         if (elements.size() > 0) {
/* 414 */           out.annotate(0, "keys:", new Object[0]);
/* 415 */           out.indent();
/* 416 */           for (int i = 0; i < elements.size(); i++) {
/* 417 */             out.annotate(4, "key[%d] = %d", new Object[] { Integer.valueOf(i), Integer.valueOf(((SwitchElement)elements.get(i)).getKey()) });
/*     */           }
/* 419 */           out.deindent();
/* 420 */           out.annotate(0, "targets:", new Object[0]);
/* 421 */           out.indent();
/* 422 */           for (int i = 0; i < elements.size(); i++) {
/* 423 */             out.annotate(4, "target[%d] = %d", new Object[] { Integer.valueOf(i), Integer.valueOf(((SwitchElement)elements.get(i)).getOffset()) });
/*     */           }
/* 425 */           out.deindent();
/*     */         }
/* 427 */         out.deindent();
/*     */       }
/*     */ 
/*     */       private void addDebugInfoIdentity(int debugInfoOffset, String methodString) {
/* 431 */         if (this.debugInfoAnnotator != null)
/* 432 */           this.debugInfoAnnotator.setItemIdentity(debugInfoOffset, methodString);
/*     */       }
/*     */     };
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.CodeItem
 * JD-Core Version:    0.6.0
 */