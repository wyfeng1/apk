/*     */ package org.jf.dexlib2.dexbacked;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.jf.dexlib2.dexbacked.instruction.DexBackedInstruction;
/*     */ import org.jf.dexlib2.dexbacked.util.DebugInfo;
/*     */ import org.jf.dexlib2.dexbacked.util.FixedSizeList;
/*     */ import org.jf.dexlib2.dexbacked.util.VariableSizeLookaheadIterator;
/*     */ import org.jf.dexlib2.iface.MethodImplementation;
/*     */ import org.jf.dexlib2.iface.debug.DebugItem;
/*     */ import org.jf.dexlib2.iface.instruction.Instruction;
/*     */ import org.jf.util.AlignmentUtils;
/*     */ 
/*     */ public class DexBackedMethodImplementation
/*     */   implements MethodImplementation
/*     */ {
/*     */   public final DexBackedDexFile dexFile;
/*     */   public final DexBackedMethod method;
/*     */   private final int codeOffset;
/*     */ 
/*     */   public DexBackedMethodImplementation(DexBackedDexFile dexFile, DexBackedMethod method, int codeOffset)
/*     */   {
/*  58 */     this.dexFile = dexFile;
/*  59 */     this.method = method;
/*  60 */     this.codeOffset = codeOffset;
/*     */   }
/*     */   public int getRegisterCount() {
/*  63 */     return this.dexFile.readUshort(this.codeOffset);
/*     */   }
/*     */ 
/*     */   public Iterable<? extends Instruction> getInstructions() {
/*  67 */     int instructionsSize = this.dexFile.readSmallUint(this.codeOffset + 12);
/*     */ 
/*  69 */     int instructionsStartOffset = this.codeOffset + 16;
/*  70 */     int endOffset = instructionsStartOffset + instructionsSize * 2;
/*  71 */     return new Iterable(instructionsStartOffset, endOffset)
/*     */     {
/*     */       public Iterator<Instruction> iterator() {
/*  74 */         return new VariableSizeLookaheadIterator(DexBackedMethodImplementation.this.dexFile, this.val$instructionsStartOffset)
/*     */         {
/*     */           protected Instruction readNextItem(DexReader reader) {
/*  77 */             if (reader.getOffset() >= DexBackedMethodImplementation.1.this.val$endOffset) {
/*  78 */               return null;
/*     */             }
/*  80 */             return DexBackedInstruction.readFrom(reader);
/*     */           }
/*     */         };
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public List<? extends DexBackedTryBlock> getTryBlocks() {
/*  90 */     int triesSize = this.dexFile.readUshort(this.codeOffset + 6);
/*  91 */     if (triesSize > 0) {
/*  92 */       int instructionsSize = this.dexFile.readSmallUint(this.codeOffset + 12);
/*  93 */       int triesStartOffset = AlignmentUtils.alignOffset(this.codeOffset + 16 + instructionsSize * 2, 4);
/*     */ 
/*  95 */       int handlersStartOffset = triesStartOffset + triesSize * 8;
/*     */ 
/*  97 */       return new FixedSizeList(triesStartOffset, handlersStartOffset, triesSize)
/*     */       {
/*     */         public DexBackedTryBlock readItem(int index)
/*     */         {
/* 101 */           return new DexBackedTryBlock(DexBackedMethodImplementation.this.dexFile, this.val$triesStartOffset + index * 8, this.val$handlersStartOffset);
/*     */         }
/*     */ 
/*     */         public int size()
/*     */         {
/* 108 */           return this.val$triesSize;
/*     */         } } ;
/*     */     }
/* 112 */     return ImmutableList.of();
/*     */   }
/*     */ 
/*     */   private DebugInfo getDebugInfo()
/*     */   {
/* 117 */     return DebugInfo.newOrEmpty(this.dexFile, this.dexFile.readSmallUint(this.codeOffset + 8), this);
/*     */   }
/*     */ 
/*     */   public Iterable<? extends DebugItem> getDebugItems()
/*     */   {
/* 122 */     return getDebugInfo();
/*     */   }
/*     */ 
/*     */   public Iterator<String> getParameterNames(DexReader dexReader)
/*     */   {
/* 127 */     return getDebugInfo().getParameterNames(dexReader);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.DexBackedMethodImplementation
 * JD-Core Version:    0.6.0
 */