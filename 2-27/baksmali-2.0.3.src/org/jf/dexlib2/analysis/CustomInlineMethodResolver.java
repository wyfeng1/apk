/*     */ package org.jf.dexlib2.analysis;
/*     */ 
/*     */ import com.google.common.io.Files;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.jf.dexlib2.iface.ClassDef;
/*     */ import org.jf.dexlib2.iface.Method;
/*     */ import org.jf.dexlib2.iface.instruction.InlineIndexInstruction;
/*     */ import org.jf.dexlib2.immutable.ImmutableMethod;
/*     */ import org.jf.dexlib2.immutable.reference.ImmutableMethodReference;
/*     */ import org.jf.dexlib2.immutable.util.ParamUtil;
/*     */ 
/*     */ public class CustomInlineMethodResolver extends InlineMethodResolver
/*     */ {
/*     */   private final ClassPath classPath;
/*     */   private final Method[] inlineMethods;
/*     */   private static final Pattern longMethodPattern;
/*     */ 
/*     */   public CustomInlineMethodResolver(ClassPath classPath, String inlineTable)
/*     */   {
/*  56 */     this.classPath = classPath;
/*     */ 
/*  58 */     StringReader reader = new StringReader(inlineTable);
/*  59 */     List lines = new ArrayList();
/*     */ 
/*  61 */     BufferedReader br = new BufferedReader(reader);
/*     */     try
/*     */     {
/*  64 */       String line = br.readLine();
/*     */ 
/*  66 */       while (line != null) {
/*  67 */         if (line.length() > 0) {
/*  68 */           lines.add(line);
/*     */         }
/*     */ 
/*  71 */         line = br.readLine();
/*     */       }
/*     */     } catch (IOException ex) {
/*  74 */       throw new RuntimeException("Error while parsing inline table", ex);
/*     */     }
/*     */ 
/*  77 */     this.inlineMethods = new Method[lines.size()];
/*     */ 
/*  79 */     for (int i = 0; i < this.inlineMethods.length; i++)
/*  80 */       this.inlineMethods[i] = parseAndResolveInlineMethod((String)lines.get(i));
/*     */   }
/*     */ 
/*     */   public CustomInlineMethodResolver(ClassPath classPath, File inlineTable) throws IOException
/*     */   {
/*  85 */     this(classPath, Files.toString(inlineTable, Charset.forName("UTF-8")));
/*     */   }
/*     */ 
/*     */   public Method resolveExecuteInline(AnalyzedInstruction analyzedInstruction)
/*     */   {
/*  91 */     InlineIndexInstruction instruction = (InlineIndexInstruction)analyzedInstruction.instruction;
/*  92 */     int methodIndex = instruction.getInlineIndex();
/*     */ 
/*  94 */     if ((methodIndex < 0) || (methodIndex >= this.inlineMethods.length)) {
/*  95 */       throw new RuntimeException("Invalid method index: " + methodIndex);
/*     */     }
/*  97 */     return this.inlineMethods[methodIndex];
/*     */   }
/*     */ 
/*     */   private Method parseAndResolveInlineMethod(String inlineMethod)
/*     */   {
/* 104 */     Matcher m = longMethodPattern.matcher(inlineMethod);
/* 105 */     if (!m.matches()) {
/* 106 */       if (!$assertionsDisabled) throw new AssertionError();
/* 107 */       throw new RuntimeException("Invalid method descriptor: " + inlineMethod);
/*     */     }
/*     */ 
/* 110 */     String className = m.group(1);
/* 111 */     String methodName = m.group(2);
/* 112 */     Iterable methodParams = ParamUtil.parseParamString(m.group(3));
/* 113 */     String methodRet = m.group(4);
/* 114 */     ImmutableMethodReference methodRef = new ImmutableMethodReference(className, methodName, methodParams, methodRet);
/*     */ 
/* 117 */     int accessFlags = 0;
/*     */ 
/* 119 */     boolean resolved = false;
/* 120 */     TypeProto typeProto = this.classPath.getClass(className);
/* 121 */     if ((typeProto instanceof ClassProto)) {
/* 122 */       ClassDef classDef = ((ClassProto)typeProto).getClassDef();
/* 123 */       for (Method method : classDef.getMethods()) {
/* 124 */         if (method.equals(methodRef)) {
/* 125 */           resolved = true;
/* 126 */           accessFlags = method.getAccessFlags();
/* 127 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 132 */     if (!resolved) {
/* 133 */       throw new RuntimeException("Cannot resolve inline method: " + inlineMethod);
/*     */     }
/*     */ 
/* 136 */     return new ImmutableMethod(className, methodName, methodParams, methodRet, accessFlags, null, null);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 100 */     longMethodPattern = Pattern.compile("(L[^;]+;)->([^(]+)\\(([^)]*)\\)(.+)");
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.analysis.CustomInlineMethodResolver
 * JD-Core Version:    0.6.0
 */