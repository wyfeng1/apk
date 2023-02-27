/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderSwitchPayload;
/*    */ import org.jf.dexlib2.builder.Label;
/*    */ import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload;
/*    */ 
/*    */ public class BuilderPackedSwitchPayload extends BuilderSwitchPayload
/*    */   implements PackedSwitchPayload
/*    */ {
/* 47 */   public static final Opcode OPCODE = Opcode.PACKED_SWITCH_PAYLOAD;
/*    */   protected final List<BuilderSwitchElement> switchElements;
/*    */ 
/*    */   public BuilderPackedSwitchPayload(int startKey, List<? extends Label> switchElements)
/*    */   {
/* 53 */     super(OPCODE);
/*    */     int key;
/* 54 */     if (switchElements == null) {
/* 55 */       this.switchElements = ImmutableList.of();
/*    */     } else {
/* 57 */       this.switchElements = Lists.newArrayList();
/* 58 */       key = startKey;
/* 59 */       for (Label target : switchElements)
/* 60 */         this.switchElements.add(new BuilderSwitchElement(this, key++, target));
/*    */     }
/*    */   }
/*    */ 
/*    */   public List<BuilderSwitchElement> getSwitchElements() {
/* 65 */     return this.switchElements;
/*    */   }
/* 67 */   public int getCodeUnits() { return 4 + this.switchElements.size() * 2; } 
/* 68 */   public Format getFormat() { return OPCODE.format;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderPackedSwitchPayload
 * JD-Core Version:    0.6.0
 */