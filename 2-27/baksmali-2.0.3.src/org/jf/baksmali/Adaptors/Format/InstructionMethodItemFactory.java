/*    */ package org.jf.baksmali.Adaptors.Format;
/*    */ 
/*    */ import org.jf.baksmali.Adaptors.ClassDefinition;
/*    */ import org.jf.baksmali.Adaptors.MethodDefinition;
/*    */ import org.jf.dexlib2.analysis.UnresolvedOdexInstruction;
/*    */ import org.jf.dexlib2.iface.instruction.Instruction;
/*    */ import org.jf.dexlib2.iface.instruction.OffsetInstruction;
/*    */ import org.jf.dexlib2.iface.instruction.formats.ArrayPayload;
/*    */ import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload;
/*    */ import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload;
/*    */ 
/*    */ public class InstructionMethodItemFactory
/*    */ {
/*    */   public static InstructionMethodItem makeInstructionFormatMethodItem(MethodDefinition methodDef, int codeAddress, Instruction instruction)
/*    */   {
/* 46 */     if ((instruction instanceof OffsetInstruction)) {
/* 47 */       return new OffsetInstructionFormatMethodItem(methodDef.classDef.options, methodDef, codeAddress, (OffsetInstruction)instruction);
/*    */     }
/*    */ 
/* 51 */     if ((instruction instanceof UnresolvedOdexInstruction)) {
/* 52 */       return new UnresolvedOdexInstructionMethodItem(methodDef, codeAddress, (UnresolvedOdexInstruction)instruction);
/*    */     }
/*    */ 
/* 56 */     switch (1.$SwitchMap$org$jf$dexlib2$Format[instruction.getOpcode().format.ordinal()]) {
/*    */     case 1:
/* 58 */       return new ArrayDataMethodItem(methodDef, codeAddress, (ArrayPayload)instruction);
/*    */     case 2:
/* 60 */       return new PackedSwitchMethodItem(methodDef, codeAddress, (PackedSwitchPayload)instruction);
/*    */     case 3:
/* 62 */       return new SparseSwitchMethodItem(methodDef, codeAddress, (SparseSwitchPayload)instruction);
/*    */     }
/* 64 */     return new InstructionMethodItem(methodDef, codeAddress, instruction);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.Format.InstructionMethodItemFactory
 * JD-Core Version:    0.6.0
 */