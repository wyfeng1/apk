/*     */ package org.antlr.runtime.tree;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.antlr.runtime.Token;
/*     */ import org.antlr.runtime.TokenStream;
/*     */ import org.antlr.runtime.misc.LookaheadStream;
/*     */ 
/*     */ public class CommonTreeNodeStream extends LookaheadStream<Object>
/*     */   implements PositionTrackingStream<Object>, TreeNodeStream
/*     */ {
/*     */   protected Object root;
/*     */   protected TokenStream tokens;
/*     */   TreeAdaptor adaptor;
/*     */   protected TreeIterator it;
/*  56 */   protected boolean hasNilRoot = false;
/*     */ 
/*  59 */   protected int level = 0;
/*     */   protected Object previousLocationElement;
/*     */ 
/*     */   public CommonTreeNodeStream(Object tree)
/*     */   {
/*  73 */     this(new CommonTreeAdaptor(), tree);
/*     */   }
/*     */ 
/*     */   public CommonTreeNodeStream(TreeAdaptor adaptor, Object tree) {
/*  77 */     this.root = tree;
/*  78 */     this.adaptor = adaptor;
/*  79 */     this.it = new TreeIterator(adaptor, this.root);
/*     */   }
/*     */ 
/*     */   public Object nextElement()
/*     */   {
/*  97 */     Object t = this.it.next();
/*     */ 
/*  99 */     if (t == this.it.up) {
/* 100 */       this.level -= 1;
/* 101 */       if ((this.level == 0) && (this.hasNilRoot)) return this.it.next();
/*     */     }
/* 103 */     else if (t == this.it.down) { this.level += 1; }
/* 104 */     if ((this.level == 0) && (this.adaptor.isNil(t))) {
/* 105 */       this.hasNilRoot = true;
/* 106 */       t = this.it.next();
/* 107 */       this.level += 1;
/* 108 */       t = this.it.next();
/*     */     }
/* 110 */     return t;
/*     */   }
/*     */ 
/*     */   public Object remove()
/*     */   {
/* 115 */     Object result = super.remove();
/* 116 */     if ((this.p == 0) && (hasPositionInformation(this.prevElement))) {
/* 117 */       this.previousLocationElement = this.prevElement;
/*     */     }
/*     */ 
/* 120 */     return result;
/*     */   }
/*     */ 
/*     */   public boolean isEOF(Object o) {
/* 124 */     return this.adaptor.getType(o) == -1;
/*     */   }
/*     */ 
/*     */   public String getSourceName()
/*     */   {
/* 133 */     return getTokenStream().getSourceName();
/*     */   }
/*     */   public TokenStream getTokenStream() {
/* 136 */     return this.tokens;
/*     */   }
/* 138 */   public void setTokenStream(TokenStream tokens) { this.tokens = tokens; }
/*     */ 
/*     */   public TreeAdaptor getTreeAdaptor() {
/* 141 */     return this.adaptor;
/*     */   }
/*     */ 
/*     */   public int LA(int i)
/*     */   {
/* 151 */     return this.adaptor.getType(LT(i));
/*     */   }
/*     */ 
/*     */   public Object getKnownPositionElement(boolean allowApproximateLocation)
/*     */   {
/* 182 */     Object node = this.data.get(this.p);
/* 183 */     if (hasPositionInformation(node)) {
/* 184 */       return node;
/*     */     }
/*     */ 
/* 187 */     if (!allowApproximateLocation) {
/* 188 */       return null;
/*     */     }
/*     */ 
/* 191 */     for (int index = this.p - 1; index >= 0; index--) {
/* 192 */       node = this.data.get(index);
/* 193 */       if (hasPositionInformation(node)) {
/* 194 */         return node;
/*     */       }
/*     */     }
/*     */ 
/* 198 */     return this.previousLocationElement;
/*     */   }
/*     */ 
/*     */   public boolean hasPositionInformation(Object node)
/*     */   {
/* 203 */     Token token = this.adaptor.getToken(node);
/* 204 */     if (token == null) {
/* 205 */       return false;
/*     */     }
/*     */ 
/* 209 */     return token.getLine() > 0;
/*     */   }
/*     */ 
/*     */   public String toString(Object start, Object stop)
/*     */   {
/* 228 */     return "n/a";
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.tree.CommonTreeNodeStream
 * JD-Core Version:    0.6.0
 */