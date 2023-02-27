/*     */ package org.jf.dexlib2.builder;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.jf.dexlib2.builder.debug.BuilderEndLocal;
/*     */ import org.jf.dexlib2.builder.debug.BuilderEpilogueBegin;
/*     */ import org.jf.dexlib2.builder.debug.BuilderLineNumber;
/*     */ import org.jf.dexlib2.builder.debug.BuilderPrologueEnd;
/*     */ import org.jf.dexlib2.builder.debug.BuilderRestartLocal;
/*     */ import org.jf.dexlib2.builder.debug.BuilderSetSourceFile;
/*     */ import org.jf.dexlib2.builder.debug.BuilderStartLocal;
/*     */ import org.jf.dexlib2.iface.instruction.Instruction;
/*     */ import org.jf.dexlib2.iface.reference.StringReference;
/*     */ import org.jf.dexlib2.iface.reference.TypeReference;
/*     */ 
/*     */ public class MethodLocation
/*     */ {
/*     */   BuilderInstruction instruction;
/*     */   int codeAddress;
/*     */   int index;
/*  53 */   private List<Label> labels = null;
/*     */ 
/*  55 */   private List<BuilderDebugItem> debugItems = null;
/*     */ 
/*     */   MethodLocation(BuilderInstruction instruction, int codeAddress, int index)
/*     */   {
/*  59 */     this.instruction = instruction;
/*  60 */     this.codeAddress = codeAddress;
/*  61 */     this.index = index;
/*     */   }
/*     */ 
/*     */   public Instruction getInstruction()
/*     */   {
/*  66 */     return this.instruction;
/*     */   }
/*     */ 
/*     */   public int getCodeAddress() {
/*  70 */     return this.codeAddress;
/*     */   }
/*     */ 
/*     */   private List<Label> getLabels(boolean mutable)
/*     */   {
/*  79 */     if (this.labels == null) {
/*  80 */       if (mutable) {
/*  81 */         this.labels = new ArrayList(1);
/*  82 */         return this.labels;
/*     */       }
/*  84 */       return ImmutableList.of();
/*     */     }
/*  86 */     return this.labels;
/*     */   }
/*     */ 
/*     */   private List<BuilderDebugItem> getDebugItems(boolean mutable)
/*     */   {
/*  91 */     if (this.debugItems == null) {
/*  92 */       if (mutable) {
/*  93 */         this.debugItems = new ArrayList(1);
/*  94 */         return this.debugItems;
/*     */       }
/*  96 */       return ImmutableList.of();
/*     */     }
/*  98 */     return this.debugItems;
/*     */   }
/*     */ 
/*     */   void mergeInto(MethodLocation other) {
/* 102 */     if ((this.labels != null) || (other.labels != null)) {
/* 103 */       List otherLabels = other.getLabels(true);
/* 104 */       for (Label label : getLabels(false)) {
/* 105 */         label.location = other;
/* 106 */         otherLabels.add(label);
/*     */       }
/* 108 */       this.labels = null;
/*     */     }
/*     */ 
/* 111 */     if ((this.debugItems != null) || (other.labels != null))
/*     */     {
/* 114 */       List debugItems = getDebugItems(true);
/* 115 */       for (BuilderDebugItem debugItem : debugItems) {
/* 116 */         debugItem.location = other;
/*     */       }
/* 118 */       debugItems.addAll(other.getDebugItems(false));
/* 119 */       other.debugItems = debugItems;
/* 120 */       this.debugItems = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Set<Label> getLabels()
/*     */   {
/* 126 */     return new AbstractSet()
/*     */     {
/*     */       public Iterator<Label> iterator() {
/* 129 */         Iterator it = MethodLocation.this.getLabels(false).iterator();
/*     */ 
/* 131 */         return new Iterator(it) {
/* 132 */           private Label currentLabel = null;
/*     */ 
/*     */           public boolean hasNext() {
/* 135 */             return this.val$it.hasNext();
/*     */           }
/*     */ 
/*     */           public Label next() {
/* 139 */             this.currentLabel = ((Label)this.val$it.next());
/* 140 */             return this.currentLabel;
/*     */           }
/*     */ 
/*     */           public void remove() {
/* 144 */             if (this.currentLabel != null) {
/* 145 */               this.currentLabel.location = null;
/*     */             }
/* 147 */             this.val$it.remove();
/*     */           } } ;
/*     */       }
/*     */ 
/*     */       public int size() {
/* 153 */         return MethodLocation.this.getLabels(false).size();
/*     */       }
/*     */ 
/*     */       public boolean add(Label label) {
/* 157 */         if (label.isPlaced()) {
/* 158 */           throw new IllegalArgumentException("Cannot add a label that is already placed. You must remove it from its current location first.");
/*     */         }
/*     */ 
/* 161 */         label.location = MethodLocation.this;
/* 162 */         MethodLocation.this.getLabels(true).add(label);
/* 163 */         return true;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public Label addNewLabel() {
/* 170 */     Label label = new Label(this);
/* 171 */     getLabels(true).add(label);
/* 172 */     return label;
/*     */   }
/*     */ 
/*     */   public Set<BuilderDebugItem> getDebugItems()
/*     */   {
/* 177 */     return new AbstractSet()
/*     */     {
/*     */       public Iterator<BuilderDebugItem> iterator() {
/* 180 */         Iterator it = MethodLocation.this.getDebugItems(false).iterator();
/*     */ 
/* 182 */         return new Iterator(it) {
/* 183 */           private BuilderDebugItem currentDebugItem = null;
/*     */ 
/*     */           public boolean hasNext() {
/* 186 */             return this.val$it.hasNext();
/*     */           }
/*     */ 
/*     */           public BuilderDebugItem next() {
/* 190 */             this.currentDebugItem = ((BuilderDebugItem)this.val$it.next());
/* 191 */             return this.currentDebugItem;
/*     */           }
/*     */ 
/*     */           public void remove() {
/* 195 */             if (this.currentDebugItem != null) {
/* 196 */               this.currentDebugItem.location = null;
/*     */             }
/* 198 */             this.val$it.remove();
/*     */           } } ;
/*     */       }
/*     */ 
/*     */       public int size() {
/* 204 */         return MethodLocation.this.getDebugItems(false).size();
/*     */       }
/*     */ 
/*     */       public boolean add(BuilderDebugItem debugItem) {
/* 208 */         if (debugItem.location != null) {
/* 209 */           throw new IllegalArgumentException("Cannot add a debug item that has already been added to a method. You must remove it from its current location first.");
/*     */         }
/*     */ 
/* 212 */         debugItem.location = MethodLocation.this;
/* 213 */         MethodLocation.this.getDebugItems(true).add(debugItem);
/* 214 */         return true;
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   public void addLineNumber(int lineNumber) {
/* 220 */     getDebugItems().add(new BuilderLineNumber(lineNumber));
/*     */   }
/*     */ 
/*     */   public void addStartLocal(int registerNumber, StringReference name, TypeReference type, StringReference signature)
/*     */   {
/* 225 */     getDebugItems().add(new BuilderStartLocal(registerNumber, name, type, signature));
/*     */   }
/*     */ 
/*     */   public void addEndLocal(int registerNumber) {
/* 229 */     getDebugItems().add(new BuilderEndLocal(registerNumber));
/*     */   }
/*     */ 
/*     */   public void addRestartLocal(int registerNumber) {
/* 233 */     getDebugItems().add(new BuilderRestartLocal(registerNumber));
/*     */   }
/*     */ 
/*     */   public void addPrologue() {
/* 237 */     getDebugItems().add(new BuilderPrologueEnd());
/*     */   }
/*     */ 
/*     */   public void addEpilogue() {
/* 241 */     getDebugItems().add(new BuilderEpilogueBegin());
/*     */   }
/*     */ 
/*     */   public void addSetSourceFile(StringReference sourceFile) {
/* 245 */     getDebugItems().add(new BuilderSetSourceFile(sourceFile));
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.MethodLocation
 * JD-Core Version:    0.6.0
 */