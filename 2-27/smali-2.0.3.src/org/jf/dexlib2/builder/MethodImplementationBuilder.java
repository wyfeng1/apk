/*     */ package org.jf.dexlib2.builder;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Set;
/*     */ import org.jf.dexlib2.iface.MethodImplementation;
/*     */ import org.jf.dexlib2.iface.reference.StringReference;
/*     */ import org.jf.dexlib2.iface.reference.TypeReference;
/*     */ 
/*     */ public class MethodImplementationBuilder
/*     */ {
/*  44 */   private final HashMap<String, Label> labels = new HashMap();
/*     */   private final MutableMethodImplementation impl;
/*     */   private MethodLocation currentLocation;
/*     */ 
/*     */   public MethodImplementationBuilder(int registerCount)
/*     */   {
/*  52 */     this.impl = new MutableMethodImplementation(registerCount);
/*  53 */     this.currentLocation = ((MethodLocation)this.impl.instructionList.get(0));
/*     */   }
/*     */ 
/*     */   public MethodImplementation getMethodImplementation() {
/*  57 */     return this.impl;
/*     */   }
/*     */ 
/*     */   public Label addLabel(String name)
/*     */   {
/*  70 */     Label label = (Label)this.labels.get(name);
/*     */ 
/*  72 */     if (label != null) {
/*  73 */       if (label.isPlaced()) {
/*  74 */         throw new IllegalArgumentException("There is already a label with that name.");
/*     */       }
/*  76 */       this.currentLocation.getLabels().add(label);
/*     */     }
/*     */     else {
/*  79 */       label = this.currentLocation.addNewLabel();
/*  80 */       this.labels.put(name, label);
/*     */     }
/*     */ 
/*  83 */     return label;
/*     */   }
/*     */ 
/*     */   public Label getLabel(String name)
/*     */   {
/*  98 */     Label label = (Label)this.labels.get(name);
/*  99 */     if (label == null) {
/* 100 */       label = new Label();
/* 101 */       this.labels.put(name, label);
/*     */     }
/* 103 */     return label;
/*     */   }
/*     */ 
/*     */   public void addCatch(TypeReference type, Label from, Label to, Label handler)
/*     */   {
/* 108 */     this.impl.addCatch(type, from, to, handler);
/*     */   }
/*     */ 
/*     */   public void addCatch(Label from, Label to, Label handler)
/*     */   {
/* 117 */     this.impl.addCatch(from, to, handler);
/*     */   }
/*     */ 
/*     */   public void addLineNumber(int lineNumber) {
/* 121 */     this.currentLocation.addLineNumber(lineNumber);
/*     */   }
/*     */ 
/*     */   public void addStartLocal(int registerNumber, StringReference name, TypeReference type, StringReference signature)
/*     */   {
/* 126 */     this.currentLocation.addStartLocal(registerNumber, name, type, signature);
/*     */   }
/*     */ 
/*     */   public void addEndLocal(int registerNumber) {
/* 130 */     this.currentLocation.addEndLocal(registerNumber);
/*     */   }
/*     */ 
/*     */   public void addRestartLocal(int registerNumber) {
/* 134 */     this.currentLocation.addRestartLocal(registerNumber);
/*     */   }
/*     */ 
/*     */   public void addPrologue() {
/* 138 */     this.currentLocation.addPrologue();
/*     */   }
/*     */ 
/*     */   public void addEpilogue() {
/* 142 */     this.currentLocation.addEpilogue();
/*     */   }
/*     */ 
/*     */   public void addSetSourceFile(StringReference sourceFile) {
/* 146 */     this.currentLocation.addSetSourceFile(sourceFile);
/*     */   }
/*     */ 
/*     */   public void addInstruction(BuilderInstruction instruction) {
/* 150 */     this.impl.addInstruction(instruction);
/* 151 */     this.currentLocation = ((MethodLocation)this.impl.instructionList.get(this.impl.instructionList.size() - 1));
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.MethodImplementationBuilder
 * JD-Core Version:    0.6.0
 */