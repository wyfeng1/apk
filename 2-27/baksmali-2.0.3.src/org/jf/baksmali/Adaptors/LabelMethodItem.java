/*     */ package org.jf.baksmali.Adaptors;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.jf.baksmali.baksmaliOptions;
/*     */ import org.jf.util.IndentingWriter;
/*     */ 
/*     */ public class LabelMethodItem extends MethodItem
/*     */ {
/*     */   private final baksmaliOptions options;
/*     */   private final String labelPrefix;
/*     */   private int labelSequence;
/*     */ 
/*     */   public LabelMethodItem(baksmaliOptions options, int codeAddress, String labelPrefix)
/*     */   {
/*  43 */     super(codeAddress);
/*  44 */     this.options = options;
/*  45 */     this.labelPrefix = labelPrefix;
/*     */   }
/*     */ 
/*     */   public double getSortOrder() {
/*  49 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   public int compareTo(MethodItem methodItem) {
/*  53 */     int result = super.compareTo(methodItem);
/*     */ 
/*  55 */     if ((result == 0) && 
/*  56 */       ((methodItem instanceof LabelMethodItem))) {
/*  57 */       result = this.labelPrefix.compareTo(((LabelMethodItem)methodItem).labelPrefix);
/*     */     }
/*     */ 
/*  60 */     return result;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  65 */     return getCodeAddress();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o) {
/*  69 */     if (!(o instanceof LabelMethodItem)) {
/*  70 */       return false;
/*     */     }
/*  72 */     return compareTo((MethodItem)o) == 0;
/*     */   }
/*     */ 
/*     */   public boolean writeTo(IndentingWriter writer) throws IOException
/*     */   {
/*  77 */     writer.write(58);
/*  78 */     writer.write(this.labelPrefix);
/*  79 */     if (this.options.useSequentialLabels)
/*  80 */       writer.printUnsignedLongAsHex(this.labelSequence);
/*     */     else {
/*  82 */       writer.printUnsignedLongAsHex(getLabelAddress());
/*     */     }
/*  84 */     return true;
/*     */   }
/*     */ 
/*     */   public String getLabelPrefix() {
/*  88 */     return this.labelPrefix;
/*     */   }
/*     */ 
/*     */   public int getLabelAddress() {
/*  92 */     return getCodeAddress();
/*     */   }
/*     */ 
/*     */   public void setLabelSequence(int labelSequence)
/*     */   {
/* 100 */     this.labelSequence = labelSequence;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.LabelMethodItem
 * JD-Core Version:    0.6.0
 */