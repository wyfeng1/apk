/*    */ package org.jf.baksmali.Adaptors.Format;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.baksmali.Adaptors.LabelMethodItem;
/*    */ import org.jf.baksmali.Adaptors.MethodDefinition;
/*    */ import org.jf.baksmali.Adaptors.MethodDefinition.LabelCache;
/*    */ import org.jf.baksmali.baksmaliOptions;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.OffsetInstruction;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class OffsetInstructionFormatMethodItem extends InstructionMethodItem<OffsetInstruction>
/*    */ {
/*    */   protected LabelMethodItem label;
/*    */ 
/*    */   public OffsetInstructionFormatMethodItem(baksmaliOptions options, MethodDefinition methodDef, int codeAddress, OffsetInstruction instruction)
/*    */   {
/* 46 */     super(methodDef, codeAddress, instruction);
/*    */ 
/* 48 */     this.label = new LabelMethodItem(options, codeAddress + instruction.getCodeOffset(), getLabelPrefix());
/* 49 */     this.label = methodDef.getLabelCache().internLabel(this.label);
/*    */   }
/*    */ 
/*    */   protected void writeTargetLabel(IndentingWriter writer) throws IOException
/*    */   {
/* 54 */     this.label.writeTo(writer);
/*    */   }
/*    */ 
/*    */   private String getLabelPrefix()
/*    */   {
/* 62 */     Opcode opcode = ((OffsetInstruction)this.instruction).getOpcode();
/* 63 */     switch (1.$SwitchMap$org$jf$dexlib2$Format[opcode.format.ordinal()]) {
/*    */     case 1:
/*    */     case 2:
/*    */     case 3:
/* 67 */       return "goto_";
/*    */     case 4:
/*    */     case 5:
/* 70 */       return "cond_";
/*    */     case 6:
/* 72 */       if (opcode == Opcode.FILL_ARRAY_DATA) {
/* 73 */         return "array_";
/*    */       }
/* 75 */       if (opcode == Opcode.PACKED_SWITCH) {
/* 76 */         return "pswitch_data_";
/*    */       }
/*    */ 
/* 79 */       return "sswitch_data_";
/*    */     }
/*    */ 
/* 82 */     if (!$assertionsDisabled) throw new AssertionError();
/* 83 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.Format.OffsetInstructionFormatMethodItem
 * JD-Core Version:    0.6.0
 */