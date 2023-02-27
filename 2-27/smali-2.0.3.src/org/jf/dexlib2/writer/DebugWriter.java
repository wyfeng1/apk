/*     */ package org.jf.dexlib2.writer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ public class DebugWriter<StringKey extends CharSequence, TypeKey extends CharSequence>
/*     */ {
/*     */   private final StringSection<StringKey, ?> stringSection;
/*     */   private final TypeSection<StringKey, TypeKey, ?> typeSection;
/*     */   private final DexDataWriter writer;
/*     */   private int currentAddress;
/*     */   private int currentLine;
/*     */ 
/*     */   DebugWriter(StringSection<StringKey, ?> stringSection, TypeSection<StringKey, TypeKey, ?> typeSection, DexDataWriter writer)
/*     */   {
/*  51 */     this.stringSection = stringSection;
/*  52 */     this.typeSection = typeSection;
/*  53 */     this.writer = writer;
/*     */   }
/*     */ 
/*     */   void reset(int startLine) {
/*  57 */     this.currentAddress = 0;
/*  58 */     this.currentLine = startLine;
/*     */   }
/*     */ 
/*     */   public void writeStartLocal(int codeAddress, int register, StringKey name, TypeKey type, StringKey signature)
/*     */     throws IOException
/*     */   {
/*  65 */     int nameIndex = this.stringSection.getNullableItemIndex(name);
/*  66 */     int typeIndex = this.typeSection.getNullableItemIndex(type);
/*  67 */     int signatureIndex = this.stringSection.getNullableItemIndex(signature);
/*     */ 
/*  69 */     writeAdvancePC(codeAddress);
/*  70 */     if (signatureIndex == -1) {
/*  71 */       this.writer.write(3);
/*  72 */       this.writer.writeUleb128(register);
/*  73 */       this.writer.writeUleb128(nameIndex + 1);
/*  74 */       this.writer.writeUleb128(typeIndex + 1);
/*     */     } else {
/*  76 */       this.writer.write(4);
/*  77 */       this.writer.writeUleb128(register);
/*  78 */       this.writer.writeUleb128(nameIndex + 1);
/*  79 */       this.writer.writeUleb128(typeIndex + 1);
/*  80 */       this.writer.writeUleb128(signatureIndex + 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeEndLocal(int codeAddress, int register) throws IOException {
/*  85 */     writeAdvancePC(codeAddress);
/*  86 */     this.writer.write(5);
/*  87 */     this.writer.writeUleb128(register);
/*     */   }
/*     */ 
/*     */   public void writeRestartLocal(int codeAddress, int register) throws IOException {
/*  91 */     writeAdvancePC(codeAddress);
/*  92 */     this.writer.write(6);
/*  93 */     this.writer.writeUleb128(register);
/*     */   }
/*     */ 
/*     */   public void writePrologueEnd(int codeAddress) throws IOException {
/*  97 */     writeAdvancePC(codeAddress);
/*  98 */     this.writer.write(7);
/*     */   }
/*     */ 
/*     */   public void writeEpilogueBegin(int codeAddress) throws IOException {
/* 102 */     writeAdvancePC(codeAddress);
/* 103 */     this.writer.write(8);
/*     */   }
/*     */ 
/*     */   public void writeLineNumber(int codeAddress, int lineNumber) throws IOException {
/* 107 */     int lineDelta = lineNumber - this.currentLine;
/* 108 */     int addressDelta = codeAddress - this.currentAddress;
/*     */ 
/* 110 */     if (addressDelta < 0) {
/* 111 */       throw new ExceptionWithContext("debug info items must have non-decreasing code addresses", new Object[0]);
/*     */     }
/* 113 */     if ((lineDelta < -4) || (lineDelta > 10)) {
/* 114 */       writeAdvanceLine(lineNumber);
/* 115 */       lineDelta = 0;
/*     */     }
/* 117 */     if (((lineDelta < 2) && (addressDelta > 16)) || ((lineDelta > 1) && (addressDelta > 15))) {
/* 118 */       writeAdvancePC(codeAddress);
/* 119 */       addressDelta = 0;
/*     */     }
/*     */ 
/* 124 */     writeSpecialOpcode(lineDelta, addressDelta);
/*     */   }
/*     */ 
/*     */   public void writeSetSourceFile(int codeAddress, StringKey sourceFile) throws IOException {
/* 128 */     writeAdvancePC(codeAddress);
/* 129 */     this.writer.write(9);
/* 130 */     this.writer.writeUleb128(this.stringSection.getNullableItemIndex(sourceFile) + 1);
/*     */   }
/*     */ 
/*     */   private void writeAdvancePC(int address) throws IOException {
/* 134 */     int addressDelta = address - this.currentAddress;
/*     */ 
/* 136 */     if (addressDelta > 0) {
/* 137 */       this.writer.write(1);
/* 138 */       this.writer.writeUleb128(addressDelta);
/* 139 */       this.currentAddress = address;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeAdvanceLine(int line)
/*     */     throws IOException
/*     */   {
/* 146 */     int lineDelta = line - this.currentLine;
/* 147 */     if (lineDelta != 0) {
/* 148 */       this.writer.write(2);
/* 149 */       this.writer.writeSleb128(lineDelta);
/* 150 */       this.currentLine = line;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeSpecialOpcode(int lineDelta, int addressDelta)
/*     */     throws IOException
/*     */   {
/* 159 */     this.writer.write((byte)(10 + addressDelta * 15 + (lineDelta - -4)));
/* 160 */     this.currentLine += lineDelta;
/* 161 */     this.currentAddress += addressDelta;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.DebugWriter
 * JD-Core Version:    0.6.0
 */