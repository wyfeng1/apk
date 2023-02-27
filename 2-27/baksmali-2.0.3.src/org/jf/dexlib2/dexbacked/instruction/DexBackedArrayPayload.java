/*     */ package org.jf.dexlib2.dexbacked.instruction;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.jf.dexlib2.Opcode;
/*     */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*     */ import org.jf.dexlib2.iface.instruction.formats.ArrayPayload;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ public class DexBackedArrayPayload extends DexBackedInstruction
/*     */   implements ArrayPayload
/*     */ {
/*  44 */   public static final Opcode OPCODE = Opcode.ARRAY_PAYLOAD;
/*     */   public final int elementWidth;
/*     */   public final int elementCount;
/*     */ 
/*     */   public DexBackedArrayPayload(DexBackedDexFile dexFile, int instructionStart)
/*     */   {
/*  55 */     super(dexFile, OPCODE, instructionStart);
/*     */ 
/*  57 */     this.elementWidth = dexFile.readUshort(instructionStart + 2);
/*  58 */     this.elementCount = dexFile.readSmallUint(instructionStart + 4);
/*     */   }
/*     */   public int getElementWidth() {
/*  61 */     return this.elementWidth;
/*     */   }
/*     */ 
/*     */   public List<Number> getArrayElements()
/*     */   {
/*  66 */     int elementsStart = this.instructionStart + 8;
/*     */ 
/*  72 */     switch (this.elementWidth) {
/*     */     case 1:
/*  74 */       return new DexBackedArrayPayload.1ReturnedList(elementsStart)
/*     */       {
/*     */         public Number readItem(int index)
/*     */         {
/*  78 */           return Integer.valueOf(DexBackedArrayPayload.this.dexFile.readByte(this.val$elementsStart + index));
/*     */         } } ;
/*     */     case 2:
/*  82 */       return new DexBackedArrayPayload.1ReturnedList(elementsStart)
/*     */       {
/*     */         public Number readItem(int index)
/*     */         {
/*  86 */           return Integer.valueOf(DexBackedArrayPayload.this.dexFile.readShort(this.val$elementsStart + index * 2));
/*     */         } } ;
/*     */     case 4:
/*  90 */       return new DexBackedArrayPayload.1ReturnedList(elementsStart)
/*     */       {
/*     */         public Number readItem(int index)
/*     */         {
/*  94 */           return Integer.valueOf(DexBackedArrayPayload.this.dexFile.readInt(this.val$elementsStart + index * 4));
/*     */         } } ;
/*     */     case 8:
/*  98 */       return new DexBackedArrayPayload.1ReturnedList(elementsStart)
/*     */       {
/*     */         public Number readItem(int index)
/*     */         {
/* 102 */           return Long.valueOf(DexBackedArrayPayload.this.dexFile.readLong(this.val$elementsStart + index * 8)); }  } ;
/*     */     case 3:
/*     */     case 5:
/*     */     case 6:
/* 106 */     case 7: } throw new ExceptionWithContext("Invalid element width: %d", new Object[] { Integer.valueOf(this.elementWidth) });
/*     */   }
/*     */ 
/*     */   public int getCodeUnits()
/*     */   {
/* 112 */     return 4 + (this.elementWidth * this.elementCount + 1) / 2;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedArrayPayload
 * JD-Core Version:    0.6.0
 */