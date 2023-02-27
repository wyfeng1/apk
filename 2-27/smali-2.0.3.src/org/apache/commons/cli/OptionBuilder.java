/*     */ package org.apache.commons.cli;
/*     */ 
/*     */ public final class OptionBuilder
/*     */ {
/*     */   private static String longopt;
/*     */   private static String description;
/*     */   private static String argName;
/*     */   private static boolean required;
/*  46 */   private static int numberOfArgs = -1;
/*     */   private static Object type;
/*     */   private static boolean optionalArg;
/*     */   private static char valuesep;
/*  58 */   private static OptionBuilder instance = new OptionBuilder();
/*     */ 
/*     */   private static void reset()
/*     */   {
/*  73 */     description = null;
/*  74 */     argName = "arg";
/*  75 */     longopt = null;
/*  76 */     type = null;
/*  77 */     required = false;
/*  78 */     numberOfArgs = -1;
/*     */ 
/*  82 */     optionalArg = false;
/*  83 */     valuesep = '\000';
/*     */   }
/*     */ 
/*     */   public static OptionBuilder withLongOpt(String newLongopt)
/*     */   {
/*  94 */     longopt = newLongopt;
/*     */ 
/*  96 */     return instance;
/*     */   }
/*     */ 
/*     */   public static OptionBuilder hasArg()
/*     */   {
/* 106 */     numberOfArgs = 1;
/*     */ 
/* 108 */     return instance;
/*     */   }
/*     */ 
/*     */   public static OptionBuilder withArgName(String name)
/*     */   {
/* 133 */     argName = name;
/*     */ 
/* 135 */     return instance;
/*     */   }
/*     */ 
/*     */   public static OptionBuilder withDescription(String newDescription)
/*     */   {
/* 300 */     description = newDescription;
/*     */ 
/* 302 */     return instance;
/*     */   }
/*     */ 
/*     */   public static Option create(String opt)
/*     */     throws IllegalArgumentException
/*     */   {
/* 348 */     Option option = null;
/*     */     try
/*     */     {
/* 351 */       option = new Option(opt, description);
/*     */ 
/* 354 */       option.setLongOpt(longopt);
/* 355 */       option.setRequired(required);
/* 356 */       option.setOptionalArg(optionalArg);
/* 357 */       option.setArgs(numberOfArgs);
/* 358 */       option.setType(type);
/* 359 */       option.setValueSeparator(valuesep);
/* 360 */       option.setArgName(argName);
/*     */     }
/*     */     finally {
/* 363 */       reset();
/*     */     }
/*     */ 
/* 367 */     return option;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.apache.commons.cli.OptionBuilder
 * JD-Core Version:    0.6.0
 */