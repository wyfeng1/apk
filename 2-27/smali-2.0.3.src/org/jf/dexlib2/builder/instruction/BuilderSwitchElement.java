/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.builder.BuilderSwitchPayload;
/*    */ import org.jf.dexlib2.builder.Label;
/*    */ import org.jf.dexlib2.builder.MethodLocation;
/*    */ import org.jf.dexlib2.iface.instruction.SwitchElement;
/*    */ 
/*    */ public class BuilderSwitchElement
/*    */   implements SwitchElement
/*    */ {
/*    */   BuilderSwitchPayload parent;
/*    */   private final int key;
/*    */   private final Label target;
/*    */ 
/*    */   public BuilderSwitchElement(BuilderSwitchPayload parent, int key, Label target)
/*    */   {
/* 17 */     this.parent = parent;
/* 18 */     this.key = key;
/* 19 */     this.target = target;
/*    */   }
/*    */ 
/*    */   public int getKey() {
/* 23 */     return this.key;
/*    */   }
/*    */ 
/*    */   public int getOffset() {
/* 27 */     return this.target.getCodeAddress() - this.parent.getReferrer().getCodeAddress();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderSwitchElement
 * JD-Core Version:    0.6.0
 */