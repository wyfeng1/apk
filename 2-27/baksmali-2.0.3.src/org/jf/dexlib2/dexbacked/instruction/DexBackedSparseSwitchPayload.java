/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.util.FixedSizeList;
/*    */ import org.jf.dexlib2.iface.instruction.SwitchElement;
/*    */ import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload;
/*    */ 
/*    */ public class DexBackedSparseSwitchPayload extends DexBackedInstruction
/*    */   implements SparseSwitchPayload
/*    */ {
/*    */   public final int elementCount;
/*    */ 
/*    */   public DexBackedSparseSwitchPayload(DexBackedDexFile dexFile, int instructionStart)
/*    */   {
/* 51 */     super(dexFile, Opcode.SPARSE_SWITCH_PAYLOAD, instructionStart);
/*    */ 
/* 53 */     this.elementCount = dexFile.readUshort(instructionStart + 2);
/*    */   }
/*    */ 
/*    */   public List<? extends SwitchElement> getSwitchElements()
/*    */   {
/* 59 */     return new FixedSizeList()
/*    */     {
/*    */       public SwitchElement readItem(int index)
/*    */       {
/* 63 */         return new SwitchElement(index)
/*    */         {
/*    */           public int getKey() {
/* 66 */             return DexBackedSparseSwitchPayload.this.dexFile.readInt(DexBackedSparseSwitchPayload.this.instructionStart + 4 + this.val$index * 4);
/*    */           }
/*    */ 
/*    */           public int getOffset()
/*    */           {
/* 71 */             return DexBackedSparseSwitchPayload.this.dexFile.readInt(DexBackedSparseSwitchPayload.this.instructionStart + 4 + DexBackedSparseSwitchPayload.this.elementCount * 4 + this.val$index * 4);
/*    */           } } ;
/*    */       }
/*    */ 
/*    */       public int size() {
/* 76 */         return DexBackedSparseSwitchPayload.this.elementCount;
/*    */       } } ;
/*    */   }
/*    */   public int getCodeUnits() {
/* 80 */     return 2 + this.elementCount * 4;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedSparseSwitchPayload
 * JD-Core Version:    0.6.0
 */