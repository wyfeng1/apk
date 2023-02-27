/*     */ package org.apache.commons.cli;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class CommandLine
/*     */   implements Serializable
/*     */ {
/*  48 */   private List args = new LinkedList();
/*     */ 
/*  51 */   private List options = new ArrayList();
/*     */ 
/*     */   public boolean hasOption(String opt)
/*     */   {
/*  69 */     return this.options.contains(resolveOption(opt));
/*     */   }
/*     */ 
/*     */   public String getOptionValue(String opt)
/*     */   {
/* 145 */     String[] values = getOptionValues(opt);
/*     */ 
/* 147 */     return values == null ? null : values[0];
/*     */   }
/*     */ 
/*     */   public String[] getOptionValues(String opt)
/*     */   {
/* 171 */     List values = new ArrayList();
/*     */ 
/* 173 */     for (Iterator it = this.options.iterator(); it.hasNext(); )
/*     */     {
/* 175 */       Option option = (Option)it.next();
/* 176 */       if ((opt.equals(option.getOpt())) || (opt.equals(option.getLongOpt())))
/*     */       {
/* 178 */         values.addAll(option.getValuesList());
/*     */       }
/*     */     }
/*     */ 
/* 182 */     return values.isEmpty() ? null : (String[])(String[])values.toArray(new String[values.size()]);
/*     */   }
/*     */ 
/*     */   private Option resolveOption(String opt)
/*     */   {
/* 193 */     opt = Util.stripLeadingHyphens(opt);
/* 194 */     for (Iterator it = this.options.iterator(); it.hasNext(); )
/*     */     {
/* 196 */       Option option = (Option)it.next();
/* 197 */       if (opt.equals(option.getOpt()))
/*     */       {
/* 199 */         return option;
/*     */       }
/*     */ 
/* 202 */       if (opt.equals(option.getLongOpt()))
/*     */       {
/* 204 */         return option;
/*     */       }
/*     */     }
/*     */ 
/* 208 */     return null;
/*     */   }
/*     */ 
/*     */   public String[] getOptionValues(char opt)
/*     */   {
/* 220 */     return getOptionValues(String.valueOf(opt));
/*     */   }
/*     */ 
/*     */   public String[] getArgs()
/*     */   {
/* 300 */     String[] answer = new String[this.args.size()];
/*     */ 
/* 302 */     this.args.toArray(answer);
/*     */ 
/* 304 */     return answer;
/*     */   }
/*     */ 
/*     */   void addArg(String arg)
/*     */   {
/* 346 */     this.args.add(arg);
/*     */   }
/*     */ 
/*     */   void addOption(Option opt)
/*     */   {
/* 356 */     this.options.add(opt);
/*     */   }
/*     */ 
/*     */   public Option[] getOptions()
/*     */   {
/* 377 */     Collection processed = this.options;
/*     */ 
/* 380 */     Option[] optionsArray = new Option[processed.size()];
/*     */ 
/* 383 */     return (Option[])(Option[])processed.toArray(optionsArray);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.apache.commons.cli.CommandLine
 * JD-Core Version:    0.6.0
 */