/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.SwitchElement;
/*    */ import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload;
/*    */ 
/*    */ public class ImmutableSparseSwitchPayload extends ImmutableInstruction
/*    */   implements SparseSwitchPayload
/*    */ {
/* 46 */   public static final Opcode OPCODE = Opcode.SPARSE_SWITCH_PAYLOAD;
/*    */   protected final ImmutableList<? extends ImmutableSwitchElement> switchElements;
/*    */ 
/*    */   public ImmutableSparseSwitchPayload(List<? extends SwitchElement> switchElements)
/*    */   {
/* 51 */     super(OPCODE);
/* 52 */     this.switchElements = ImmutableSwitchElement.immutableListOf(switchElements);
/*    */   }
/*    */ 
/*    */   public static ImmutableSparseSwitchPayload of(SparseSwitchPayload instruction)
/*    */   {
/* 63 */     if ((instruction instanceof ImmutableSparseSwitchPayload)) {
/* 64 */       return (ImmutableSparseSwitchPayload)instruction;
/*    */     }
/* 66 */     return new ImmutableSparseSwitchPayload(instruction.getSwitchElements());
/*    */   }
/*    */ 
/*    */   public List<? extends SwitchElement> getSwitchElements() {
/* 70 */     return this.switchElements;
/*    */   }
/* 72 */   public int getCodeUnits() { return 2 + this.switchElements.size() * 4; } 
/* 73 */   public Format getFormat() { return OPCODE.format;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableSparseSwitchPayload
 * JD-Core Version:    0.6.0
 */