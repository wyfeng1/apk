/*     */ package org.jf.dexlib2.dexbacked.util;
/*     */ 
/*     */ import com.google.common.collect.Iterators;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import org.jf.dexlib2.AccessFlags;
/*     */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*     */ import org.jf.dexlib2.dexbacked.DexBackedMethod;
/*     */ import org.jf.dexlib2.dexbacked.DexBackedMethodImplementation;
/*     */ import org.jf.dexlib2.dexbacked.DexReader;
/*     */ import org.jf.dexlib2.iface.debug.DebugItem;
/*     */ import org.jf.dexlib2.iface.debug.EndLocal;
/*     */ import org.jf.dexlib2.iface.debug.LocalInfo;
/*     */ import org.jf.dexlib2.immutable.debug.ImmutableEndLocal;
/*     */ import org.jf.dexlib2.immutable.debug.ImmutableEpilogueBegin;
/*     */ import org.jf.dexlib2.immutable.debug.ImmutableLineNumber;
/*     */ import org.jf.dexlib2.immutable.debug.ImmutablePrologueEnd;
/*     */ import org.jf.dexlib2.immutable.debug.ImmutableRestartLocal;
/*     */ import org.jf.dexlib2.immutable.debug.ImmutableSetSourceFile;
/*     */ import org.jf.dexlib2.immutable.debug.ImmutableStartLocal;
/*     */ 
/*     */ public abstract class DebugInfo
/*     */   implements Iterable<DebugItem>
/*     */ {
/*     */   public abstract Iterator<String> getParameterNames(DexReader paramDexReader);
/*     */ 
/*     */   public static DebugInfo newOrEmpty(DexBackedDexFile dexFile, int debugInfoOffset, DexBackedMethodImplementation methodImpl)
/*     */   {
/*  64 */     if (debugInfoOffset == 0) {
/*  65 */       return EmptyDebugInfo.INSTANCE;
/*     */     }
/*  67 */     return new DebugInfoImpl(dexFile, debugInfoOffset, methodImpl);
/*     */   }
/*     */ 
/*     */   private static class DebugInfoImpl extends DebugInfo
/*     */   {
/*     */     public final DexBackedDexFile dexFile;
/*     */     private final int debugInfoOffset;
/*     */     private final DexBackedMethodImplementation methodImpl;
/*  92 */     private static final LocalInfo EMPTY_LOCAL_INFO = new LocalInfo() {
/*  93 */       public String getName() { return null; } 
/*  94 */       public String getType() { return null; } 
/*  95 */       public String getSignature() { return null;
/*     */       }
/*  92 */     };
/*     */ 
/*     */     public DebugInfoImpl(DexBackedDexFile dexFile, int debugInfoOffset, DexBackedMethodImplementation methodImpl)
/*     */     {
/*  87 */       this.dexFile = dexFile;
/*  88 */       this.debugInfoOffset = debugInfoOffset;
/*  89 */       this.methodImpl = methodImpl;
/*     */     }
/*     */ 
/*     */     public Iterator<DebugItem> iterator()
/*     */     {
/* 101 */       DexReader reader = this.dexFile.readerAt(this.debugInfoOffset);
/* 102 */       int lineNumberStart = reader.readBigUleb128();
/* 103 */       int registerCount = this.methodImpl.getRegisterCount();
/*     */ 
/* 106 */       LocalInfo[] locals = new LocalInfo[registerCount];
/* 107 */       Arrays.fill(locals, EMPTY_LOCAL_INFO);
/*     */ 
/* 109 */       DexBackedMethod method = this.methodImpl.method;
/*     */ 
/* 114 */       Iterator parameterIterator = new ParameterIterator(method.getParameterTypes(), method.getParameterAnnotations(), getParameterNames(reader));
/*     */ 
/* 121 */       int parameterIndex = 0;
/* 122 */       if (!AccessFlags.STATIC.isSet(this.methodImpl.method.getAccessFlags()))
/*     */       {
/* 124 */         locals[(parameterIndex++)] = new LocalInfo() {
/* 125 */           public String getName() { return "this"; } 
/* 126 */           public String getType() { return DebugInfo.DebugInfoImpl.this.methodImpl.method.getDefiningClass(); } 
/* 127 */           public String getSignature() { return null; } } ;
/*     */       }
/* 130 */       while (parameterIterator.hasNext()) {
/* 131 */         locals[(parameterIndex++)] = ((LocalInfo)parameterIterator.next());
/*     */       }
/*     */ 
/* 134 */       if (parameterIndex < registerCount)
/*     */       {
/* 136 */         int localIndex = registerCount - 1;
/*     */         while (true) { parameterIndex--; if (parameterIndex <= -1) break;
/* 138 */           LocalInfo currentLocal = locals[parameterIndex];
/* 139 */           String type = currentLocal.getType();
/* 140 */           if ((type != null) && ((type.equals("J")) || (type.equals("D")))) {
/* 141 */             localIndex--;
/* 142 */             if (localIndex == parameterIndex)
/*     */             {
/*     */               break;
/*     */             }
/*     */           }
/* 147 */           locals[localIndex] = currentLocal;
/* 148 */           locals[parameterIndex] = EMPTY_LOCAL_INFO;
/* 149 */           localIndex--;
/*     */         }
/*     */       }
/*     */ 
/* 153 */       return new VariableSizeLookaheadIterator(this.dexFile, reader.getOffset(), lineNumberStart, locals) {
/* 154 */         private int codeAddress = 0;
/* 155 */         private int lineNumber = this.val$lineNumberStart;
/*     */ 
/*     */         protected DebugItem readNextItem(DexReader reader) {
/*     */           int next;
/*     */           while (true) { next = reader.readUbyte();
/* 161 */             switch (next) {
/*     */             case 0:
/* 163 */               return null;
/*     */             case 1:
/* 166 */               int addressDiff = reader.readSmallUleb128();
/* 167 */               this.codeAddress += addressDiff;
/* 168 */               break;
/*     */             case 2:
/* 171 */               int lineDiff = reader.readSleb128();
/* 172 */               this.lineNumber += lineDiff;
/*     */             case 3:
/*     */             case 4:
/*     */             case 5:
/*     */             case 6:
/*     */             case 7:
/*     */             case 8:
/* 176 */             case 9: }  } int register = reader.readSmallUleb128();
/* 177 */           String name = DebugInfo.DebugInfoImpl.this.dexFile.getOptionalString(reader.readSmallUleb128() - 1);
/* 178 */           String type = DebugInfo.DebugInfoImpl.this.dexFile.getOptionalType(reader.readSmallUleb128() - 1);
/* 179 */           ImmutableStartLocal startLocal = new ImmutableStartLocal(this.codeAddress, register, name, type, null);
/*     */ 
/* 181 */           this.val$locals[register] = startLocal;
/* 182 */           return startLocal;
/*     */ 
/* 185 */           int register = reader.readSmallUleb128();
/* 186 */           String name = DebugInfo.DebugInfoImpl.this.dexFile.getOptionalString(reader.readSmallUleb128() - 1);
/* 187 */           String type = DebugInfo.DebugInfoImpl.this.dexFile.getOptionalType(reader.readSmallUleb128() - 1);
/* 188 */           String signature = DebugInfo.DebugInfoImpl.this.dexFile.getOptionalString(reader.readSmallUleb128() - 1);
/* 189 */           ImmutableStartLocal startLocal = new ImmutableStartLocal(this.codeAddress, register, name, type, signature);
/*     */ 
/* 191 */           this.val$locals[register] = startLocal;
/* 192 */           return startLocal;
/*     */ 
/* 195 */           int register = reader.readSmallUleb128();
/* 196 */           LocalInfo localInfo = this.val$locals[register];
/* 197 */           boolean replaceLocalInTable = true;
/* 198 */           if ((localInfo instanceof EndLocal)) {
/* 199 */             localInfo = DebugInfo.DebugInfoImpl.EMPTY_LOCAL_INFO;
/*     */ 
/* 203 */             replaceLocalInTable = false;
/*     */           }
/* 205 */           ImmutableEndLocal endLocal = new ImmutableEndLocal(this.codeAddress, register, localInfo.getName(), localInfo.getType(), localInfo.getSignature());
/*     */ 
/* 208 */           if (replaceLocalInTable) {
/* 209 */             this.val$locals[register] = endLocal;
/*     */           }
/* 211 */           return endLocal;
/*     */ 
/* 214 */           int register = reader.readSmallUleb128();
/* 215 */           LocalInfo localInfo = this.val$locals[register];
/* 216 */           ImmutableRestartLocal restartLocal = new ImmutableRestartLocal(this.codeAddress, register, localInfo.getName(), localInfo.getType(), localInfo.getSignature());
/*     */ 
/* 219 */           this.val$locals[register] = restartLocal;
/* 220 */           return restartLocal;
/*     */ 
/* 223 */           return new ImmutablePrologueEnd(this.codeAddress);
/*     */ 
/* 226 */           return new ImmutableEpilogueBegin(this.codeAddress);
/*     */ 
/* 229 */           String sourceFile = DebugInfo.DebugInfoImpl.this.dexFile.getOptionalString(reader.readSmallUleb128() - 1);
/* 230 */           return new ImmutableSetSourceFile(this.codeAddress, sourceFile);
/*     */ 
/* 233 */           int adjusted = next - 10;
/* 234 */           this.codeAddress += adjusted / 15;
/* 235 */           this.lineNumber += adjusted % 15 - 4;
/* 236 */           return new ImmutableLineNumber(this.codeAddress, this.lineNumber);
/*     */         }
/*     */       };
/*     */     }
/*     */ 
/*     */     public VariableSizeIterator<String> getParameterNames(DexReader reader)
/*     */     {
/* 247 */       if (reader == null) {
/* 248 */         reader = this.dexFile.readerAt(this.debugInfoOffset);
/* 249 */         reader.skipUleb128();
/*     */       }
/*     */ 
/* 252 */       int parameterNameCount = reader.readSmallUleb128();
/* 253 */       return new VariableSizeIterator(reader, parameterNameCount) {
/*     */         protected String readNextItem(DexReader reader, int index) {
/* 255 */           return DebugInfo.DebugInfoImpl.this.dexFile.getOptionalString(reader.readSmallUleb128() - 1);
/*     */         }
/*     */       };
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class EmptyDebugInfo extends DebugInfo
/*     */   {
/*  71 */     public static final EmptyDebugInfo INSTANCE = new EmptyDebugInfo();
/*     */ 
/*  73 */     public Iterator<DebugItem> iterator() { return Iterators.emptyIterator(); } 
/*     */     public Iterator<String> getParameterNames(DexReader reader) {
/*  75 */       return Iterators.emptyIterator();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.util.DebugInfo
 * JD-Core Version:    0.6.0
 */