/*     */ package org.jf.util;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ public class ExceptionWithContext extends RuntimeException
/*     */ {
/*     */   private StringBuffer context;
/*     */ 
/*     */   public static ExceptionWithContext withContext(Throwable ex, String str, Object[] formatArgs)
/*     */   {
/*     */     ExceptionWithContext ewc;
/*     */     ExceptionWithContext ewc;
/*  51 */     if ((ex instanceof ExceptionWithContext))
/*  52 */       ewc = (ExceptionWithContext)ex;
/*     */     else {
/*  54 */       ewc = new ExceptionWithContext(ex);
/*     */     }
/*     */ 
/*  57 */     ewc.addContext(String.format(str, formatArgs));
/*  58 */     return ewc;
/*     */   }
/*     */ 
/*     */   public ExceptionWithContext(String message, Object[] formatArgs)
/*     */   {
/*  67 */     this(null, message, formatArgs);
/*     */   }
/*     */ 
/*     */   public ExceptionWithContext(Throwable cause)
/*     */   {
/*  76 */     this(cause, null, new Object[0]);
/*     */   }
/*     */ 
/*     */   public ExceptionWithContext(Throwable cause, String message, Object[] formatArgs)
/*     */   {
/*  86 */     super(cause != null ? cause.getMessage() : message != null ? formatMessage(message, formatArgs) : null, cause);
/*     */ 
/*  90 */     if ((cause instanceof ExceptionWithContext)) {
/*  91 */       String ctx = ((ExceptionWithContext)cause).context.toString();
/*  92 */       this.context = new StringBuffer(ctx.length() + 200);
/*  93 */       this.context.append(ctx);
/*     */     } else {
/*  95 */       this.context = new StringBuffer(200);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static String formatMessage(String message, Object[] formatArgs) {
/* 100 */     if (message == null) {
/* 101 */       return null;
/*     */     }
/* 103 */     return String.format(message, formatArgs);
/*     */   }
/*     */ 
/*     */   public void printStackTrace(PrintStream out)
/*     */   {
/* 109 */     super.printStackTrace(out);
/* 110 */     out.println(this.context);
/*     */   }
/*     */ 
/*     */   public void printStackTrace(PrintWriter out)
/*     */   {
/* 116 */     super.printStackTrace(out);
/* 117 */     out.println(this.context);
/*     */   }
/*     */ 
/*     */   public void addContext(String str)
/*     */   {
/* 126 */     if (str == null) {
/* 127 */       throw new NullPointerException("str == null");
/*     */     }
/*     */ 
/* 130 */     this.context.append(str);
/* 131 */     if (!str.endsWith("\n"))
/* 132 */       this.context.append('\n');
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.util.ExceptionWithContext
 * JD-Core Version:    0.6.0
 */