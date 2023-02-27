/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.SwitchElement;
/*    */ import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload;
/*    */ 
/*    */ public class ImmutablePackedSwitchPayload extends ImmutableInstruction
/*    */   implements PackedSwitchPayload
/*    */ {
/* 46 */   public static final Opcode OPCODE = Opcode.PACKED_SWITCH_PAYLOAD;
/*    */   protected final ImmutableList<? extends ImmutableSwitchElement> switchElements;
/*    */ 
/*    */   public ImmutablePackedSwitchPayload(List<? extends SwitchElement> switchElements)
/*    */   {
/* 51 */     super(OPCODE);
/*    */ 
/* 53 */     this.switchElements = ImmutableSwitchElement.immutableListOf(switchElements);
/*    */   }
/*    */ 
/*    */   public static ImmutablePackedSwitchPayload of(PackedSwitchPayload instruction)
/*    */   {
/* 64 */     if ((instruction instanceof ImmutablePackedSwitchPayload)) {
/* 65 */       return (ImmutablePackedSwitchPayload)instruction;
/*    */     }
/* 67 */     return new ImmutablePackedSwitchPayload(instruction.getSwitchElements());
/*    */   }
/*    */ 
/*    */   public List<? extends SwitchElement> getSwitchElements() {
/* 71 */     return this.switchElements;
/*    */   }
/* 73 */   public int getCodeUnits() { return 4 + this.switchElements.size() * 2; } 
/* 74 */   public Format getFormat() { return OPCODE.format;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutablePackedSwitchPayload
 * JD-Core Version:    0.6.0
 */