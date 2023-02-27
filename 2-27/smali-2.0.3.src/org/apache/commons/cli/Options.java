/*     */ package org.apache.commons.cli;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class Options
/*     */   implements Serializable
/*     */ {
/*  51 */   private Map shortOpts = new HashMap();
/*     */ 
/*  54 */   private Map longOpts = new HashMap();
/*     */ 
/*  57 */   private List requiredOpts = new ArrayList();
/*     */ 
/*  60 */   private Map optionGroups = new HashMap();
/*     */ 
/*     */   public Options addOption(Option opt)
/*     */   {
/* 144 */     String key = opt.getKey();
/*     */ 
/* 147 */     if (opt.hasLongOpt())
/*     */     {
/* 149 */       this.longOpts.put(opt.getLongOpt(), opt);
/*     */     }
/*     */ 
/* 153 */     if (opt.isRequired())
/*     */     {
/* 155 */       if (this.requiredOpts.contains(key))
/*     */       {
/* 157 */         this.requiredOpts.remove(this.requiredOpts.indexOf(key));
/*     */       }
/* 159 */       this.requiredOpts.add(key);
/*     */     }
/*     */ 
/* 162 */     this.shortOpts.put(key, opt);
/*     */ 
/* 164 */     return this;
/*     */   }
/*     */ 
/*     */   public Collection getOptions()
/*     */   {
/* 174 */     return Collections.unmodifiableCollection(helpOptions());
/*     */   }
/*     */ 
/*     */   List helpOptions()
/*     */   {
/* 184 */     return new ArrayList(this.shortOpts.values());
/*     */   }
/*     */ 
/*     */   public List getRequiredOptions()
/*     */   {
/* 194 */     return this.requiredOpts;
/*     */   }
/*     */ 
/*     */   public Option getOption(String opt)
/*     */   {
/* 206 */     opt = Util.stripLeadingHyphens(opt);
/*     */ 
/* 208 */     if (this.shortOpts.containsKey(opt))
/*     */     {
/* 210 */       return (Option)this.shortOpts.get(opt);
/*     */     }
/*     */ 
/* 213 */     return (Option)this.longOpts.get(opt);
/*     */   }
/*     */ 
/*     */   public boolean hasOption(String opt)
/*     */   {
/* 225 */     opt = Util.stripLeadingHyphens(opt);
/*     */ 
/* 227 */     return (this.shortOpts.containsKey(opt)) || (this.longOpts.containsKey(opt));
/*     */   }
/*     */ 
/*     */   public OptionGroup getOptionGroup(Option opt)
/*     */   {
/* 239 */     return (OptionGroup)this.optionGroups.get(opt.getKey());
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 249 */     StringBuffer buf = new StringBuffer();
/*     */ 
/* 251 */     buf.append("[ Options: [ short ");
/* 252 */     buf.append(this.shortOpts.toString());
/* 253 */     buf.append(" ] [ long ");
/* 254 */     buf.append(this.longOpts);
/* 255 */     buf.append(" ]");
/*     */ 
/* 257 */     return buf.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.apache.commons.cli.Options
 * JD-Core Version:    0.6.0
 */