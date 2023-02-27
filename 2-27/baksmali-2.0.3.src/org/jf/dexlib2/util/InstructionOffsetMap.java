/*    */ package org.jf.dexlib2.util;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import org.jf.dexlib2.iface.instruction.Instruction;
/*    */ import org.jf.util.ExceptionWithContext;
/*    */ 
/*    */ public class InstructionOffsetMap
/*    */ {
/*    */   private final int[] instructionCodeOffsets;
/*    */ 
/*    */   public InstructionOffsetMap(List<? extends Instruction> instructions)
/*    */   {
/* 45 */     this.instructionCodeOffsets = new int[instructions.size()];
/*    */ 
/* 47 */     int codeOffset = 0;
/* 48 */     for (int i = 0; i < instructions.size(); i++) {
/* 49 */       this.instructionCodeOffsets[i] = codeOffset;
/* 50 */       codeOffset += ((Instruction)instructions.get(i)).getCodeUnits();
/*    */     }
/*    */   }
/*    */ 
/*    */   public int getInstructionIndexAtCodeOffset(int codeOffset) {
/* 55 */     return getInstructionIndexAtCodeOffset(codeOffset, true);
/*    */   }
/*    */ 
/*    */   public int getInstructionIndexAtCodeOffset(int codeOffset, boolean exact) {
/* 59 */     int index = Arrays.binarySearch(this.instructionCodeOffsets, codeOffset);
/* 60 */     if (index < 0) {
/* 61 */       if (exact) {
/* 62 */         throw new InvalidInstructionOffset(codeOffset);
/*    */       }
/*    */ 
/* 67 */       return (index ^ 0xFFFFFFFF) - 1;
/*    */     }
/*    */ 
/* 70 */     return index;
/*    */   }
/*    */ 
/*    */   public int getInstructionCodeOffset(int index) {
/* 74 */     if ((index < 0) || (index >= this.instructionCodeOffsets.length)) {
/* 75 */       throw new InvalidInstructionIndex(index);
/*    */     }
/* 77 */     return this.instructionCodeOffsets[index];
/*    */   }
/*    */ 
/*    */   public static class InvalidInstructionIndex extends ExceptionWithContext
/*    */   {
/*    */     private final int instructionIndex;
/*    */ 
/*    */     public InvalidInstructionIndex(int instructionIndex)
/*    */     {
/* 97 */       super(new Object[] { Integer.valueOf(instructionIndex) });
/* 98 */       this.instructionIndex = instructionIndex;
/*    */     }
/*    */   }
/*    */ 
/*    */   public static class InvalidInstructionOffset extends ExceptionWithContext
/*    */   {
/*    */     private final int instructionOffset;
/*    */ 
/*    */     public InvalidInstructionOffset(int instructionOffset)
/*    */     {
/* 84 */       super(new Object[] { Integer.valueOf(instructionOffset) });
/* 85 */       this.instructionOffset = instructionOffset;
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.util.InstructionOffsetMap
 * JD-Core Version:    0.6.0
 */