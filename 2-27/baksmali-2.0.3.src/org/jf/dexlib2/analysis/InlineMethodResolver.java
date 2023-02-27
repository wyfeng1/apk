/*     */ package org.jf.dexlib2.analysis;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import org.jf.dexlib2.iface.Method;
/*     */ import org.jf.dexlib2.iface.instruction.InlineIndexInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.VariableRegisterInstruction;
/*     */ import org.jf.dexlib2.immutable.ImmutableMethod;
/*     */ import org.jf.dexlib2.immutable.util.ParamUtil;
/*     */ 
/*     */ public abstract class InlineMethodResolver
/*     */ {
/*     */   public static InlineMethodResolver createInlineMethodResolver(int odexVersion)
/*     */   {
/*  54 */     if (odexVersion == 35)
/*  55 */       return new InlineMethodResolver_version35();
/*  56 */     if (odexVersion == 36) {
/*  57 */       return new InlineMethodResolver_version36();
/*     */     }
/*  59 */     throw new RuntimeException(String.format("odex version %d is not supported yet", new Object[] { Integer.valueOf(odexVersion) }));
/*     */   }
/*     */ 
/*     */   private static Method inlineMethod(int accessFlags, String cls, String name, String params, String returnType)
/*     */   {
/*  69 */     ImmutableList paramList = ImmutableList.copyOf(ParamUtil.parseParamString(params));
/*  70 */     return new ImmutableMethod(cls, name, paramList, returnType, accessFlags, null, null);
/*     */   }
/*     */ 
/*     */   public abstract Method resolveExecuteInline(AnalyzedInstruction paramAnalyzedInstruction);
/*     */ 
/*     */   private static class InlineMethodResolver_version36 extends InlineMethodResolver
/*     */   {
/*     */     private final Method[] inlineMethods;
/*     */     private final Method indexOfIMethod;
/*     */     private final Method indexOfIIMethod;
/*     */     private final Method fastIndexOfMethod;
/*     */     private final Method isEmptyMethod;
/*     */ 
/*     */     public InlineMethodResolver_version36()
/*     */     {
/* 124 */       this.indexOfIMethod = InlineMethodResolver.access$000(1, "Ljava/lang/String;", "indexOf", "I", "I");
/* 125 */       this.indexOfIIMethod = InlineMethodResolver.access$000(1, "Ljava/lang/String;", "indexOf", "II", "I");
/*     */ 
/* 128 */       this.fastIndexOfMethod = InlineMethodResolver.access$000(2, "Ljava/lang/String;", "fastIndexOf", "II", "I");
/* 129 */       this.isEmptyMethod = InlineMethodResolver.access$000(1, "Ljava/lang/String;", "isEmpty", "", "Z");
/*     */ 
/* 131 */       this.inlineMethods = new Method[] { InlineMethodResolver.access$000(8, "Lorg/apache/harmony/dalvik/NativeTestTarget;", "emptyInlineMethod", "", "V"), InlineMethodResolver.access$000(1, "Ljava/lang/String;", "charAt", "I", "C"), InlineMethodResolver.access$000(1, "Ljava/lang/String;", "compareTo", "Ljava/lang/String;", "I"), InlineMethodResolver.access$000(1, "Ljava/lang/String;", "equals", "Ljava/lang/Object;", "Z"), null, null, InlineMethodResolver.access$000(1, "Ljava/lang/String;", "length", "", "I"), InlineMethodResolver.access$000(8, "Ljava/lang/Math;", "abs", "I", "I"), InlineMethodResolver.access$000(8, "Ljava/lang/Math;", "abs", "J", "J"), InlineMethodResolver.access$000(8, "Ljava/lang/Math;", "abs", "F", "F"), InlineMethodResolver.access$000(8, "Ljava/lang/Math;", "abs", "D", "D"), InlineMethodResolver.access$000(8, "Ljava/lang/Math;", "min", "II", "I"), InlineMethodResolver.access$000(8, "Ljava/lang/Math;", "max", "II", "I"), InlineMethodResolver.access$000(8, "Ljava/lang/Math;", "sqrt", "D", "D"), InlineMethodResolver.access$000(8, "Ljava/lang/Math;", "cos", "D", "D"), InlineMethodResolver.access$000(8, "Ljava/lang/Math;", "sin", "D", "D"), InlineMethodResolver.access$000(8, "Ljava/lang/Float;", "floatToIntBits", "F", "I"), InlineMethodResolver.access$000(8, "Ljava/lang/Float;", "floatToRawIntBits", "F", "I"), InlineMethodResolver.access$000(8, "Ljava/lang/Float;", "intBitsToFloat", "I", "F"), InlineMethodResolver.access$000(8, "Ljava/lang/Double;", "doubleToLongBits", "D", "J"), InlineMethodResolver.access$000(8, "Ljava/lang/Double;", "doubleToRawLongBits", "D", "J"), InlineMethodResolver.access$000(8, "Ljava/lang/Double;", "longBitsToDouble", "J", "D"), InlineMethodResolver.access$000(8, "Ljava/lang/StrictMath;", "abs", "I", "I"), InlineMethodResolver.access$000(8, "Ljava/lang/StrictMath;", "abs", "J", "J"), InlineMethodResolver.access$000(8, "Ljava/lang/StrictMath;", "abs", "F", "F"), InlineMethodResolver.access$000(8, "Ljava/lang/StrictMath;", "abs", "D", "D"), InlineMethodResolver.access$000(8, "Ljava/lang/StrictMath;", "min", "II", "I"), InlineMethodResolver.access$000(8, "Ljava/lang/StrictMath;", "max", "II", "I"), InlineMethodResolver.access$000(8, "Ljava/lang/StrictMath;", "sqrt", "D", "D") };
/*     */     }
/*     */ 
/*     */     public Method resolveExecuteInline(AnalyzedInstruction analyzedInstruction)
/*     */     {
/* 171 */       InlineIndexInstruction instruction = (InlineIndexInstruction)analyzedInstruction.instruction;
/* 172 */       int inlineIndex = instruction.getInlineIndex();
/*     */ 
/* 174 */       if ((inlineIndex < 0) || (inlineIndex >= this.inlineMethods.length)) {
/* 175 */         throw new RuntimeException("Invalid method index: " + inlineIndex);
/*     */       }
/*     */ 
/* 178 */       if (inlineIndex == 4) {
/* 179 */         int parameterCount = ((VariableRegisterInstruction)instruction).getRegisterCount();
/* 180 */         if (parameterCount == 2)
/* 181 */           return this.indexOfIMethod;
/* 182 */         if (parameterCount == 3) {
/* 183 */           return this.fastIndexOfMethod;
/*     */         }
/* 185 */         throw new RuntimeException("Could not determine the correct inline method to use");
/*     */       }
/* 187 */       if (inlineIndex == 5) {
/* 188 */         int parameterCount = ((VariableRegisterInstruction)instruction).getRegisterCount();
/* 189 */         if (parameterCount == 3)
/* 190 */           return this.indexOfIIMethod;
/* 191 */         if (parameterCount == 1) {
/* 192 */           return this.isEmptyMethod;
/*     */         }
/* 194 */         throw new RuntimeException("Could not determine the correct inline method to use");
/*     */       }
/*     */ 
/* 198 */       return this.inlineMethods[inlineIndex];
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class InlineMethodResolver_version35 extends InlineMethodResolver
/*     */   {
/*     */     private final Method[] inlineMethods;
/*     */ 
/*     */     public InlineMethodResolver_version35()
/*     */     {
/*  80 */       this.inlineMethods = new Method[] { InlineMethodResolver.access$000(8, "Lorg/apache/harmony/dalvik/NativeTestTarget;", "emptyInlineMethod", "", "V"), InlineMethodResolver.access$000(1, "Ljava/lang/String;", "charAt", "I", "C"), InlineMethodResolver.access$000(1, "Ljava/lang/String;", "compareTo", "Ljava/lang/String;", "I"), InlineMethodResolver.access$000(1, "Ljava/lang/String;", "equals", "Ljava/lang/Object;", "Z"), InlineMethodResolver.access$000(1, "Ljava/lang/String;", "length", "", "I"), InlineMethodResolver.access$000(8, "Ljava/lang/Math;", "abs", "I", "I"), InlineMethodResolver.access$000(8, "Ljava/lang/Math;", "abs", "J", "J"), InlineMethodResolver.access$000(8, "Ljava/lang/Math;", "abs", "F", "F"), InlineMethodResolver.access$000(8, "Ljava/lang/Math;", "abs", "D", "D"), InlineMethodResolver.access$000(8, "Ljava/lang/Math;", "min", "II", "I"), InlineMethodResolver.access$000(8, "Ljava/lang/Math;", "max", "II", "I"), InlineMethodResolver.access$000(8, "Ljava/lang/Math;", "sqrt", "D", "D"), InlineMethodResolver.access$000(8, "Ljava/lang/Math;", "cos", "D", "D"), InlineMethodResolver.access$000(8, "Ljava/lang/Math;", "sin", "D", "D") };
/*     */     }
/*     */ 
/*     */     public Method resolveExecuteInline(AnalyzedInstruction analyzedInstruction)
/*     */     {
/* 101 */       InlineIndexInstruction instruction = (InlineIndexInstruction)analyzedInstruction.instruction;
/* 102 */       int inlineIndex = instruction.getInlineIndex();
/*     */ 
/* 104 */       if ((inlineIndex < 0) || (inlineIndex >= this.inlineMethods.length)) {
/* 105 */         throw new RuntimeException("Invalid inline index: " + inlineIndex);
/*     */       }
/* 107 */       return this.inlineMethods[inlineIndex];
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.analysis.InlineMethodResolver
 * JD-Core Version:    0.6.0
 */