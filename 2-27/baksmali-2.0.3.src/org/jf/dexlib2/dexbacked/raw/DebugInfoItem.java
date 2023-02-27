/*     */ package org.jf.dexlib2.dexbacked.raw;
/*     */ 
/*     */ import org.jf.dexlib2.dexbacked.DexReader;
/*     */ import org.jf.dexlib2.dexbacked.raw.util.DexAnnotator;
/*     */ import org.jf.dexlib2.util.AnnotatedBytes;
/*     */ 
/*     */ public class DebugInfoItem
/*     */ {
/*     */   public static SectionAnnotator makeAnnotator(DexAnnotator annotator, MapItem mapItem)
/*     */   {
/*  46 */     return new SectionAnnotator(annotator, mapItem) {
/*     */       public String getItemName() {
/*  48 */         return "debug_info_item";
/*     */       }
/*     */ 
/*     */       public void annotateItem(AnnotatedBytes out, int itemIndex, String itemIdentity)
/*     */       {
/*  53 */         DexReader reader = this.dexFile.readerAt(out.getCursor());
/*     */ 
/*  55 */         int lineStart = reader.readSmallUleb128();
/*  56 */         out.annotateTo(reader.getOffset(), "line_start = %d", new Object[] { Integer.valueOf(lineStart) });
/*     */ 
/*  58 */         int parametersSize = reader.readSmallUleb128();
/*  59 */         out.annotateTo(reader.getOffset(), "parameters_size = %d", new Object[] { Integer.valueOf(parametersSize) });
/*     */ 
/*  61 */         if (parametersSize > 0) {
/*  62 */           out.annotate(0, "parameters:", new Object[0]);
/*  63 */           out.indent();
/*  64 */           for (int i = 0; i < parametersSize; i++) {
/*  65 */             int paramaterIndex = reader.readSmallUleb128() - 1;
/*  66 */             out.annotateTo(reader.getOffset(), "%s", new Object[] { StringIdItem.getOptionalReferenceAnnotation(this.dexFile, paramaterIndex, true) });
/*     */           }
/*     */ 
/*  69 */           out.deindent();
/*     */         }
/*     */ 
/*  72 */         out.annotate(0, "debug opcodes:", new Object[0]);
/*  73 */         out.indent();
/*     */ 
/*  75 */         int codeAddress = 0;
/*  76 */         int lineNumber = lineStart;
/*     */         while (true)
/*     */         {
/*  79 */           int opcode = reader.readUbyte();
/*  80 */           switch (opcode) {
/*     */           case 0:
/*  82 */             out.annotateTo(reader.getOffset(), "DBG_END_SEQUENCE", new Object[0]);
/*  83 */             break;
/*     */           case 1:
/*  86 */             out.annotateTo(reader.getOffset(), "DBG_ADVANCE_PC", new Object[0]);
/*  87 */             out.indent();
/*  88 */             int addressDiff = reader.readSmallUleb128();
/*  89 */             codeAddress += addressDiff;
/*  90 */             out.annotateTo(reader.getOffset(), "addr_diff = +0x%x: 0x%x", new Object[] { Integer.valueOf(addressDiff), Integer.valueOf(codeAddress) });
/*     */ 
/*  92 */             out.deindent();
/*  93 */             break;
/*     */           case 2:
/*  96 */             out.annotateTo(reader.getOffset(), "DBG_ADVANCE_LINE", new Object[0]);
/*  97 */             out.indent();
/*  98 */             int lineDiff = reader.readSleb128();
/*  99 */             lineNumber += lineDiff;
/* 100 */             out.annotateTo(reader.getOffset(), "line_diff = +%d: %d", new Object[] { Integer.valueOf(Math.abs(lineDiff)), Integer.valueOf(lineNumber) });
/* 101 */             out.deindent();
/* 102 */             break;
/*     */           case 3:
/* 105 */             out.annotateTo(reader.getOffset(), "DBG_START_LOCAL", new Object[0]);
/* 106 */             out.indent();
/* 107 */             int registerNum = reader.readSmallUleb128();
/* 108 */             out.annotateTo(reader.getOffset(), "register_num = v%d", new Object[] { Integer.valueOf(registerNum) });
/* 109 */             int nameIndex = reader.readSmallUleb128() - 1;
/* 110 */             out.annotateTo(reader.getOffset(), "name_idx = %s", new Object[] { StringIdItem.getOptionalReferenceAnnotation(this.dexFile, nameIndex, true) });
/*     */ 
/* 112 */             int typeIndex = reader.readSmallUleb128() - 1;
/* 113 */             out.annotateTo(reader.getOffset(), "type_idx = %s", new Object[] { TypeIdItem.getOptionalReferenceAnnotation(this.dexFile, typeIndex) });
/*     */ 
/* 115 */             out.deindent();
/* 116 */             break;
/*     */           case 4:
/* 119 */             out.annotateTo(reader.getOffset(), "DBG_START_LOCAL_EXTENDED", new Object[0]);
/* 120 */             out.indent();
/* 121 */             int registerNum = reader.readSmallUleb128();
/* 122 */             out.annotateTo(reader.getOffset(), "register_num = v%d", new Object[] { Integer.valueOf(registerNum) });
/* 123 */             int nameIndex = reader.readSmallUleb128() - 1;
/* 124 */             out.annotateTo(reader.getOffset(), "name_idx = %s", new Object[] { StringIdItem.getOptionalReferenceAnnotation(this.dexFile, nameIndex, true) });
/*     */ 
/* 126 */             int typeIndex = reader.readSmallUleb128() - 1;
/* 127 */             out.annotateTo(reader.getOffset(), "type_idx = %s", new Object[] { TypeIdItem.getOptionalReferenceAnnotation(this.dexFile, typeIndex) });
/*     */ 
/* 129 */             int sigIndex = reader.readSmallUleb128() - 1;
/* 130 */             out.annotateTo(reader.getOffset(), "sig_idx = %s", new Object[] { StringIdItem.getOptionalReferenceAnnotation(this.dexFile, sigIndex, true) });
/*     */ 
/* 132 */             out.deindent();
/* 133 */             break;
/*     */           case 5:
/* 136 */             out.annotateTo(reader.getOffset(), "DBG_END_LOCAL", new Object[0]);
/* 137 */             out.indent();
/* 138 */             int registerNum = reader.readSmallUleb128();
/* 139 */             out.annotateTo(reader.getOffset(), "register_num = v%d", new Object[] { Integer.valueOf(registerNum) });
/* 140 */             out.deindent();
/* 141 */             break;
/*     */           case 6:
/* 144 */             out.annotateTo(reader.getOffset(), "DBG_RESTART_LOCAL", new Object[0]);
/* 145 */             out.indent();
/* 146 */             int registerNum = reader.readSmallUleb128();
/* 147 */             out.annotateTo(reader.getOffset(), "register_num = v%d", new Object[] { Integer.valueOf(registerNum) });
/* 148 */             out.deindent();
/* 149 */             break;
/*     */           case 7:
/* 152 */             out.annotateTo(reader.getOffset(), "DBG_SET_PROLOGUE_END", new Object[0]);
/* 153 */             break;
/*     */           case 8:
/* 156 */             out.annotateTo(reader.getOffset(), "DBG_SET_EPILOGUE_BEGIN", new Object[0]);
/* 157 */             break;
/*     */           case 9:
/* 160 */             out.annotateTo(reader.getOffset(), "DBG_SET_FILE", new Object[0]);
/* 161 */             out.indent();
/* 162 */             int nameIdx = reader.readSmallUleb128() - 1;
/* 163 */             out.annotateTo(reader.getOffset(), "name_idx = %s", new Object[] { StringIdItem.getOptionalReferenceAnnotation(this.dexFile, nameIdx) });
/*     */ 
/* 165 */             out.deindent();
/* 166 */             break;
/*     */           default:
/* 169 */             int adjusted = opcode - 10;
/* 170 */             int addressDiff = adjusted / 15;
/* 171 */             int lineDiff = adjusted % 15 - 4;
/* 172 */             codeAddress += addressDiff;
/* 173 */             lineNumber += lineDiff;
/* 174 */             out.annotateTo(reader.getOffset(), "address_diff = +0x%x:0x%x, line_diff = +%d:%d, ", new Object[] { Integer.valueOf(addressDiff), Integer.valueOf(codeAddress), Integer.valueOf(lineDiff), Integer.valueOf(lineNumber) });
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 179 */         out.deindent();
/*     */       }
/*     */     };
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.raw.DebugInfoItem
 * JD-Core Version:    0.6.0
 */