/*     */ package org.jf.baksmali.Adaptors.Format;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jf.baksmali.Adaptors.ClassDefinition;
/*     */ import org.jf.baksmali.Adaptors.LabelMethodItem;
/*     */ import org.jf.baksmali.Adaptors.MethodDefinition;
/*     */ import org.jf.baksmali.Adaptors.MethodDefinition.LabelCache;
/*     */ import org.jf.baksmali.Renderers.IntegerRenderer;
/*     */ import org.jf.dexlib2.iface.instruction.SwitchElement;
/*     */ import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload;
/*     */ import org.jf.util.IndentingWriter;
/*     */ 
/*     */ public class SparseSwitchMethodItem extends InstructionMethodItem<SparseSwitchPayload>
/*     */ {
/*     */   private final List<SparseSwitchTarget> targets;
/*     */ 
/*     */   public SparseSwitchMethodItem(MethodDefinition methodDef, int codeAddress, SparseSwitchPayload instruction)
/*     */   {
/*  46 */     super(methodDef, codeAddress, instruction);
/*     */ 
/*  48 */     int baseCodeAddress = methodDef.getSparseSwitchBaseAddress(codeAddress);
/*     */ 
/*  50 */     this.targets = new ArrayList();
/*  51 */     if (baseCodeAddress >= 0) {
/*  52 */       for (SwitchElement switchElement : instruction.getSwitchElements()) {
/*  53 */         LabelMethodItem label = methodDef.getLabelCache().internLabel(new LabelMethodItem(methodDef.classDef.options, baseCodeAddress + switchElement.getOffset(), "sswitch_"));
/*     */ 
/*  56 */         this.targets.add(new SparseSwitchLabelTarget(switchElement.getKey(), label));
/*     */       }
/*     */     }
/*     */     else
/*  60 */       for (SwitchElement switchElement : instruction.getSwitchElements())
/*  61 */         this.targets.add(new SparseSwitchOffsetTarget(switchElement.getKey(), switchElement.getOffset()));
/*     */   }
/*     */ 
/*     */   public boolean writeTo(IndentingWriter writer)
/*     */     throws IOException
/*     */   {
/*  68 */     writer.write(".sparse-switch\n");
/*  69 */     writer.indent(4);
/*  70 */     for (SparseSwitchTarget target : this.targets) {
/*  71 */       IntegerRenderer.writeTo(writer, target.getKey());
/*  72 */       writer.write(" -> ");
/*  73 */       target.writeTargetTo(writer);
/*  74 */       writeResourceId(writer, target.getKey());
/*  75 */       writer.write(10);
/*     */     }
/*  77 */     writer.deindent(4);
/*  78 */     writer.write(".end sparse-switch");
/*  79 */     return true;
/*     */   }
/*     */ 
/*     */   private static class SparseSwitchOffsetTarget extends SparseSwitchMethodItem.SparseSwitchTarget
/*     */   {
/*     */     private final int target;
/*     */ 
/*     */     public SparseSwitchOffsetTarget(int key, int target)
/*     */     {
/* 106 */       super();
/* 107 */       this.target = target;
/*     */     }
/*     */ 
/*     */     public void writeTargetTo(IndentingWriter writer) throws IOException {
/* 111 */       if (this.target >= 0) {
/* 112 */         writer.write(43);
/*     */       }
/* 114 */       writer.printSignedIntAsDec(this.target);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class SparseSwitchLabelTarget extends SparseSwitchMethodItem.SparseSwitchTarget
/*     */   {
/*     */     private final LabelMethodItem target;
/*     */ 
/*     */     public SparseSwitchLabelTarget(int key, LabelMethodItem target)
/*     */     {
/*  94 */       super();
/*  95 */       this.target = target;
/*     */     }
/*     */ 
/*     */     public void writeTargetTo(IndentingWriter writer) throws IOException {
/*  99 */       this.target.writeTo(writer);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static abstract class SparseSwitchTarget
/*     */   {
/*     */     private final int key;
/*     */ 
/*     */     public SparseSwitchTarget(int key)
/*     */     {
/*  85 */       this.key = key;
/*     */     }
/*  87 */     public int getKey() { return this.key;
/*     */     }
/*     */ 
/*     */     public abstract void writeTargetTo(IndentingWriter paramIndentingWriter)
/*     */       throws IOException;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.Format.SparseSwitchMethodItem
 * JD-Core Version:    0.6.0
 */