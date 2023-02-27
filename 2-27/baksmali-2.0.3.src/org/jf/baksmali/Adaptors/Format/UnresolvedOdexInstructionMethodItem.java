/*    */ package org.jf.baksmali.Adaptors.Format;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.jf.baksmali.Adaptors.MethodDefinition;
/*    */ import org.jf.dexlib2.analysis.UnresolvedOdexInstruction;
/*    */ import org.jf.util.IndentingWriter;
/*    */ 
/*    */ public class UnresolvedOdexInstructionMethodItem extends InstructionMethodItem<UnresolvedOdexInstruction>
/*    */ {
/*    */   public UnresolvedOdexInstructionMethodItem(MethodDefinition methodDef, int codeAddress, UnresolvedOdexInstruction instruction)
/*    */   {
/* 41 */     super(methodDef, codeAddress, instruction);
/*    */   }
/*    */ 
/*    */   public boolean writeTo(IndentingWriter writer) throws IOException {
/* 45 */     writeThrowTo(writer);
/* 46 */     return true;
/*    */   }
/*    */ 
/*    */   private void writeThrowTo(IndentingWriter writer) throws IOException {
/* 50 */     writer.write("#Replaced unresolvable odex instruction with a throw\n");
/* 51 */     writer.write("throw ");
/* 52 */     writeRegister(writer, ((UnresolvedOdexInstruction)this.instruction).objectRegisterNum);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.Format.UnresolvedOdexInstructionMethodItem
 * JD-Core Version:    0.6.0
 */