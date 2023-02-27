/*     */ package org.jf.dexlib2.base;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.primitives.Ints;
/*     */ import java.util.Comparator;
/*     */ import org.jf.dexlib2.iface.ExceptionHandler;
/*     */ 
/*     */ public abstract class BaseExceptionHandler
/*     */   implements ExceptionHandler
/*     */ {
/*  96 */   public static final Comparator<ExceptionHandler> BY_EXCEPTION = new Comparator() {
/*     */     public int compare(ExceptionHandler o1, ExceptionHandler o2) {
/*  98 */       String exceptionType1 = o1.getExceptionType();
/*  99 */       if (exceptionType1 == null) {
/* 100 */         if (o2.getExceptionType() != null) {
/* 101 */           return 1;
/*     */         }
/* 103 */         return 0;
/*     */       }
/* 105 */       String exceptionType2 = o2.getExceptionType();
/* 106 */       if (exceptionType2 == null) {
/* 107 */         return -1;
/*     */       }
/* 109 */       return exceptionType1.compareTo(o2.getExceptionType());
/*     */     }
/*  96 */   };
/*     */ 
/*     */   public int hashCode()
/*     */   {
/*  60 */     String exceptionType = getExceptionType();
/*  61 */     int hashCode = exceptionType == null ? 0 : exceptionType.hashCode();
/*  62 */     return hashCode * 31 + getHandlerCodeAddress();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/*  67 */     if ((o instanceof ExceptionHandler)) {
/*  68 */       ExceptionHandler other = (ExceptionHandler)o;
/*  69 */       return (Objects.equal(getExceptionType(), other.getExceptionType())) && (getHandlerCodeAddress() == other.getHandlerCodeAddress());
/*     */     }
/*     */ 
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */   public int compareTo(ExceptionHandler o)
/*     */   {
/*  78 */     String exceptionType = getExceptionType();
/*  79 */     if (exceptionType == null) {
/*  80 */       if (o.getExceptionType() != null)
/*  81 */         return 1;
/*     */     }
/*     */     else {
/*  84 */       String otherExceptionType = o.getExceptionType();
/*  85 */       if (otherExceptionType == null) {
/*  86 */         return -1;
/*     */       }
/*  88 */       int res = exceptionType.compareTo(o.getExceptionType());
/*  89 */       if (res != 0) return res;
/*     */     }
/*  91 */     return Ints.compare(getHandlerCodeAddress(), o.getHandlerCodeAddress());
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.BaseExceptionHandler
 * JD-Core Version:    0.6.0
 */