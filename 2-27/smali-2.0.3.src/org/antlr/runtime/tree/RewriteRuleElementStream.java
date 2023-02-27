/*     */ package org.antlr.runtime.tree;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class RewriteRuleElementStream
/*     */ {
/*  50 */   protected int cursor = 0;
/*     */   protected Object singleElement;
/*     */   protected List<Object> elements;
/*  68 */   protected boolean dirty = false;
/*     */   protected String elementDescription;
/*     */   protected TreeAdaptor adaptor;
/*     */ 
/*     */   public RewriteRuleElementStream(TreeAdaptor adaptor, String elementDescription)
/*     */   {
/*  78 */     this.elementDescription = elementDescription;
/*  79 */     this.adaptor = adaptor;
/*     */   }
/*     */ 
/*     */   public RewriteRuleElementStream(TreeAdaptor adaptor, String elementDescription, Object oneElement)
/*     */   {
/*  88 */     this(adaptor, elementDescription);
/*  89 */     add(oneElement);
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 108 */     this.cursor = 0;
/* 109 */     this.dirty = true;
/*     */   }
/*     */ 
/*     */   public void add(Object el)
/*     */   {
/* 114 */     if (el == null) {
/* 115 */       return;
/*     */     }
/* 117 */     if (this.elements != null) {
/* 118 */       this.elements.add(el);
/* 119 */       return;
/*     */     }
/* 121 */     if (this.singleElement == null) {
/* 122 */       this.singleElement = el;
/* 123 */       return;
/*     */     }
/*     */ 
/* 126 */     this.elements = new ArrayList(5);
/* 127 */     this.elements.add(this.singleElement);
/* 128 */     this.singleElement = null;
/* 129 */     this.elements.add(el);
/*     */   }
/*     */ 
/*     */   public Object nextTree()
/*     */   {
/* 138 */     int n = size();
/* 139 */     if ((this.dirty) || ((this.cursor >= n) && (n == 1)))
/*     */     {
/* 141 */       Object el = _next();
/* 142 */       return dup(el);
/*     */     }
/*     */ 
/* 145 */     Object el = _next();
/* 146 */     return el;
/*     */   }
/*     */ 
/*     */   protected Object _next()
/*     */   {
/* 156 */     int n = size();
/* 157 */     if (n == 0) {
/* 158 */       throw new RewriteEmptyStreamException(this.elementDescription);
/*     */     }
/* 160 */     if (this.cursor >= n) {
/* 161 */       if (n == 1) {
/* 162 */         return toTree(this.singleElement);
/*     */       }
/*     */ 
/* 165 */       throw new RewriteCardinalityException(this.elementDescription);
/*     */     }
/*     */ 
/* 168 */     if (this.singleElement != null) {
/* 169 */       this.cursor += 1;
/* 170 */       return toTree(this.singleElement);
/*     */     }
/*     */ 
/* 173 */     Object o = toTree(this.elements.get(this.cursor));
/* 174 */     this.cursor += 1;
/* 175 */     return o;
/*     */   }
/*     */ 
/*     */   protected abstract Object dup(Object paramObject);
/*     */ 
/*     */   protected Object toTree(Object el)
/*     */   {
/* 189 */     return el;
/*     */   }
/*     */ 
/*     */   public boolean hasNext() {
/* 193 */     return ((this.singleElement != null) && (this.cursor < 1)) || ((this.elements != null) && (this.cursor < this.elements.size()));
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 198 */     int n = 0;
/* 199 */     if (this.singleElement != null) {
/* 200 */       n = 1;
/*     */     }
/* 202 */     if (this.elements != null) {
/* 203 */       return this.elements.size();
/*     */     }
/* 205 */     return n;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.tree.RewriteRuleElementStream
 * JD-Core Version:    0.6.0
 */