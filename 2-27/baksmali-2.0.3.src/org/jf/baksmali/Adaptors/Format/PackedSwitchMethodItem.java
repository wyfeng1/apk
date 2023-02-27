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
/*     */ import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload;
/*     */ import org.jf.util.IndentingWriter;
/*     */ 
/*     */ public class PackedSwitchMethodItem extends InstructionMethodItem<PackedSwitchPayload>
/*     */ {
/*     */   private final List<PackedSwitchTarget> targets;
/*     */   private final int firstKey;
/*     */ 
/*     */   public PackedSwitchMethodItem(MethodDefinition methodDef, int codeAddress, PackedSwitchPayload instruction)
/*     */   {
/*  47 */     super(methodDef, codeAddress, instruction);
/*     */ 
/*  49 */     int baseCodeAddress = methodDef.getPackedSwitchBaseAddress(codeAddress);
/*     */ 
/*  51 */     this.targets = new ArrayList();
/*     */ 
/*  53 */     boolean first = true;
/*     */ 
/*  55 */     int firstKey = 0;
/*  56 */     if (baseCodeAddress >= 0)
/*  57 */       for (SwitchElement switchElement : instruction.getSwitchElements()) {
/*  58 */         if (first) {
/*  59 */           firstKey = switchElement.getKey();
/*  60 */           first = false;
/*     */         }
/*  62 */         LabelMethodItem label = methodDef.getLabelCache().internLabel(new LabelMethodItem(methodDef.classDef.options, baseCodeAddress + switchElement.getOffset(), "pswitch_"));
/*     */ 
/*  65 */         this.targets.add(new PackedSwitchLabelTarget(label));
/*     */       }
/*     */     else {
/*  68 */       for (SwitchElement switchElement : instruction.getSwitchElements()) {
/*  69 */         if (first) {
/*  70 */           firstKey = switchElement.getKey();
/*  71 */           first = false;
/*     */         }
/*  73 */         this.targets.add(new PackedSwitchOffsetTarget(switchElement.getOffset()));
/*     */       }
/*     */     }
/*  76 */     this.firstKey = firstKey;
/*     */   }
/*     */ 
/*     */   public boolean writeTo(IndentingWriter writer) throws IOException
/*     */   {
/*  81 */     writer.write(".packed-switch ");
/*  82 */     IntegerRenderer.writeTo(writer, this.firstKey);
/*  83 */     writer.indent(4);
/*  84 */     writer.write(10);
/*  85 */     int key = this.firstKey;
/*  86 */     for (PackedSwitchTarget target : this.targets) {
/*  87 */       target.writeTargetTo(writer);
/*  88 */       writeResourceId(writer, key);
/*  89 */       writer.write(10);
/*  90 */       key++;
/*     */     }
/*  92 */     writer.deindent(4);
/*  93 */     writer.write(".end packed-switch");
/*  94 */     return true;
/*     */   }
/*     */ 
/*     */   private static class PackedSwitchOffsetTarget extends PackedSwitchMethodItem.PackedSwitchTarget
/*     */   {
/*     */     private final int target;
/*     */ 
/*     */     public PackedSwitchOffsetTarget(int target)
/*     */     {
/* 113 */       super();
/* 114 */       this.target = target;
/*     */     }
/*     */     public void writeTargetTo(IndentingWriter writer) throws IOException {
/* 117 */       if (this.target >= 0) {
/* 118 */         writer.write(43);
/*     */       }
/* 120 */       writer.printSignedIntAsDec(this.target);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class PackedSwitchLabelTarget extends PackedSwitchMethodItem.PackedSwitchTarget
/*     */   {
/*     */     private final LabelMethodItem target;
/*     */ 
/*     */     public PackedSwitchLabelTarget(LabelMethodItem target)
/*     */     {
/* 103 */       super();
/* 104 */       this.target = target;
/*     */     }
/*     */     public void writeTargetTo(IndentingWriter writer) throws IOException {
/* 107 */       this.target.writeTo(writer);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static abstract class PackedSwitchTarget
/*     */   {
/*     */     public abstract void writeTargetTo(IndentingWriter paramIndentingWriter)
/*     */       throws IOException;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.Format.PackedSwitchMethodItem
 * JD-Core Version:    0.6.0
 */