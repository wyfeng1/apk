/*    */ package org.jf.dexlib2.dexbacked.instruction;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.util.FixedSizeList;
/*    */ import org.jf.dexlib2.iface.instruction.SwitchElement;
/*    */ import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload;
/*    */ 
/*    */ public class DexBackedPackedSwitchPayload extends DexBackedInstruction
/*    */   implements PackedSwitchPayload
/*    */ {
/*    */   public final int elementCount;
/*    */ 
/*    */   public DexBackedPackedSwitchPayload(DexBackedDexFile dexFile, int instructionStart)
/*    */   {
/* 52 */     super(dexFile, Opcode.PACKED_SWITCH_PAYLOAD, instructionStart);
/*    */ 
/* 54 */     this.elementCount = dexFile.readUshort(instructionStart + 2);
/*    */   }
/*    */ 
/*    */   public List<? extends SwitchElement> getSwitchElements()
/*    */   {
/* 60 */     int firstKey = this.dexFile.readInt(this.instructionStart + 4);
/* 61 */     return new FixedSizeList(firstKey)
/*    */     {
/*    */       public SwitchElement readItem(int index)
/*    */       {
/* 65 */         return new SwitchElement(index)
/*    */         {
/*    */           public int getKey() {
/* 68 */             return DexBackedPackedSwitchPayload.1.this.val$firstKey + this.val$index;
/*    */           }
/*    */ 
/*    */           public int getOffset()
/*    */           {
/* 73 */             return DexBackedPackedSwitchPayload.this.dexFile.readInt(DexBackedPackedSwitchPayload.this.instructionStart + 8 + this.val$index * 4);
/*    */           } } ;
/*    */       }
/*    */ 
/*    */       public int size() {
/* 78 */         return DexBackedPackedSwitchPayload.this.elementCount;
/*    */       } } ;
/*    */   }
/*    */   public int getCodeUnits() {
/* 82 */     return 4 + this.elementCount * 2;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.instruction.DexBackedPackedSwitchPayload
 * JD-Core Version:    0.6.0
 */