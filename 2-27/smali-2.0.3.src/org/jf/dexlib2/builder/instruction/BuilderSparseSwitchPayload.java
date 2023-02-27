/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import com.google.common.base.Function;
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.List;
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderSwitchPayload;
/*    */ import org.jf.dexlib2.builder.SwitchLabelElement;
/*    */ import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload;
/*    */ 
/*    */ public class BuilderSparseSwitchPayload extends BuilderSwitchPayload
/*    */   implements SparseSwitchPayload
/*    */ {
/* 48 */   public static final Opcode OPCODE = Opcode.SPARSE_SWITCH_PAYLOAD;
/*    */   protected final List<BuilderSwitchElement> switchElements;
/*    */ 
/*    */   public BuilderSparseSwitchPayload(List<? extends SwitchLabelElement> switchElements)
/*    */   {
/* 53 */     super(OPCODE);
/* 54 */     if (switchElements == null)
/* 55 */       this.switchElements = ImmutableList.of();
/*    */     else
/* 57 */       this.switchElements = Lists.transform(switchElements, new Function() {
/*    */         public BuilderSwitchElement apply(SwitchLabelElement element) {
/* 59 */           assert (element != null);
/* 60 */           return new BuilderSwitchElement(BuilderSparseSwitchPayload.this, element.key, element.target);
/*    */         } } );
/*    */   }
/*    */ 
/*    */   public List<BuilderSwitchElement> getSwitchElements() {
/* 66 */     return this.switchElements;
/*    */   }
/* 68 */   public int getCodeUnits() { return 2 + this.switchElements.size() * 4; } 
/* 69 */   public Format getFormat() { return OPCODE.format;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderSparseSwitchPayload
 * JD-Core Version:    0.6.0
 */