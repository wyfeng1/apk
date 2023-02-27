/*     */ package org.jf.dexlib2;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.HashMap;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ public class VerificationError
/*     */ {
/*  51 */   private static final HashMap<String, Integer> verificationErrorNames = Maps.newHashMap();
/*     */ 
/*     */   public static int getVerificationError(String verificationError)
/*     */   {
/*  92 */     Integer ret = (Integer)verificationErrorNames.get(verificationError);
/*  93 */     if (ret == null) {
/*  94 */       throw new ExceptionWithContext("Invalid verification error: %s", new Object[] { verificationError });
/*     */     }
/*  96 */     return ret.intValue();
/*     */   }
/*     */ 
/*     */   public static boolean isValidVerificationError(int verificationError) {
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

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.VerificationError
 * JD-Core Version:    0.6.0
 */