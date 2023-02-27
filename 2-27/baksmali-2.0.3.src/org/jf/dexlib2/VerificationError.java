/*     */ package org.jf.dexlib2;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class VerificationError
/*     */ {
/*  51 */   private static final HashMap<String, Integer> verificationErrorNames = Maps.newHashMap();
/*     */ 
/*     */   public static String getVerificationErrorName(int verificationError)
/*     */   {
/*  67 */     switch (verificationError) {
/*     */     case 1:
/*  69 */       return "generic-error";
/*     */     case 2:
/*  71 */       return "no-such-class";
/*     */     case 3:
/*  73 */       return "no-such-field";
/*     */     case 4:
/*  75 */       return "no-such-method";
/*     */     case 5:
/*  77 */       return "illegal-class-access";
/*     */     case 6:
/*  79 */       return "illegal-field-access";
/*     */     case 7:
/*  81 */       return "illegal-method-access";
/*     */     case 8:
/*  83 */       return "class-change-error";
/*     */     case 9:
/*  85 */       return "instantiation-error";
/*     */     }
/*  87 */     return null;
/*     */   }
/*     */ 
/*     */   public static boolean isValidVerificationError(int verificationError)
/*     */   {
/* 100 */     return (verificationError > 0) && (verificationError < 10);
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  54 */     verificationErrorNames.put("generic-error", Integer.valueOf(1));
/*  55 */     verificationErrorNames.put("no-such-class", Integer.valueOf(2));
/*  56 */     verificationErrorNames.put("no-such-field", Integer.valueOf(3));
/*  57 */     verificationErrorNames.put("no-such-method", Integer.valueOf(4));
/*  58 */     verificationErrorNames.put("illegal-class-access", Integer.valueOf(5));
/*  59 */     verificationErrorNames.put("illegal-field-access", Integer.valueOf(6));
/*  60 */     verificationErrorNames.put("illegal-method-access", Integer.valueOf(7));
/*  61 */     verificationErrorNames.put("class-change-error", Integer.valueOf(8));
/*  62 */     verificationErrorNames.put("instantiation-error", Integer.valueOf(9));
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.VerificationError
 * JD-Core Version:    0.6.0
 */