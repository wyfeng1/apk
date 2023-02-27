/*     */ package org.antlr.runtime;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ public class BufferedTokenStream
/*     */   implements TokenStream
/*     */ {
/*     */   protected TokenSource tokenSource;
/*  57 */   protected List<Token> tokens = new ArrayList(100);
/*     */   protected int lastMarker;
/*  67 */   protected int p = -1;
/*     */ 
/*  69 */   protected int range = -1;
/*     */ 
/*     */   public BufferedTokenStream() {
/*     */   }
/*     */   public BufferedTokenStream(TokenSource tokenSource) {
/*  74 */     this.tokenSource = tokenSource;
/*     */   }
/*     */ 
/*     */   public int index()
/*     */   {
/*  81 */     return this.p;
/*     */   }
/*     */ 
/*     */   public int mark()
/*     */   {
/*  88 */     if (this.p == -1) setup();
/*  89 */     this.lastMarker = index();
/*  90 */     return this.lastMarker;
/*     */   }
/*     */ 
/*     */   public void rewind(int marker)
/*     */   {
/* 100 */     seek(marker);
/*     */   }
/*     */ 
/*     */   public void rewind()
/*     */   {
/* 105 */     seek(this.lastMarker);
/*     */   }
/*     */ 
/*     */   public void seek(int index)
/*     */   {
/* 114 */     this.p = index;
/*     */   }
/*     */   public int size() {
/* 117 */     return this.tokens.size();
/*     */   }
/*     */ 
/*     */   public void consume()
/*     */   {
/* 128 */     if (this.p == -1) setup();
/* 129 */     this.p += 1;
/* 130 */     sync(this.p);
/*     */   }
/*     */ 
/*     */   protected void sync(int i)
/*     */   {
/* 135 */     int n = i - this.tokens.size() + 1;
/*     */ 
/* 137 */     if (n > 0) fetch(n);
/*     */   }
/*     */ 
/*     */   protected void fetch(int n)
/*     */   {
/* 142 */     for (int i = 1; i <= n; i++) {
/* 143 */       Token t = this.tokenSource.nextToken();
/* 144 */       t.setTokenIndex(this.tokens.size());
/*     */ 
/* 146 */       this.tokens.add(t);
/* 147 */       if (t.getType() == -1)
/*     */         break;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Token get(int i) {
/* 153 */     if ((i < 0) || (i >= this.tokens.size())) {
/* 154 */       throw new NoSuchElementException("token index " + i + " out of range 0.." + (this.tokens.size() - 1));
/*     */     }
/* 156 */     return (Token)this.tokens.get(i);
/*     */   }
/*     */ 
/*     */   public int LA(int i)
/*     */   {
/* 174 */     return LT(i).getType();
/*     */   }
/*     */   protected Token LB(int k) {
/* 177 */     if (this.p - k < 0) return null;
/* 178 */     return (Token)this.tokens.get(this.p - k);
/*     */   }
/*     */ 
/*     */   public Token LT(int k)
/*     */   {
/* 183 */     if (this.p == -1) setup();
/* 184 */     if (k == 0) return null;
/* 185 */     if (k < 0) return LB(-k);
/*     */ 
/* 187 */     int i = this.p + k - 1;
/* 188 */     sync(i);
/* 189 */     if (i >= this.tokens.size())
/*     */     {
/* 191 */       return (Token)this.tokens.get(this.tokens.size() - 1);
/*     */     }
/* 193 */     if (i > this.range) this.range = i;
/* 194 */     return (Token)this.tokens.get(i);
/*     */   }
/*     */   protected void setup() {
/* 197 */     sync(0); this.p = 0;
/*     */   }
/*     */ 
/*     */   public List<? extends Token> getTokens()
/*     */   {
/* 206 */     return this.tokens;
/*     */   }
/*     */ 
/*     */   public String getSourceName()
/*     */   {
/* 245 */     return this.tokenSource.getSourceName();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 250 */     if (this.p == -1) setup();
/* 251 */     fill();
/* 252 */     return toString(0, this.tokens.size() - 1);
/*     */   }
/*     */ 
/*     */   public String toString(int start, int stop)
/*     */   {
/* 257 */     if ((start < 0) || (stop < 0)) return null;
/* 258 */     if (this.p == -1) setup();
/* 259 */     if (stop >= this.tokens.size()) stop = this.tokens.size() - 1;
/* 260 */     StringBuilder buf = new StringBuilder();
/* 261 */     for (int i = start; i <= stop; i++) {
/* 262 */       Token t = (Token)this.tokens.get(i);
/* 263 */       if (t.getType() == -1) break;
/* 264 */       buf.append(t.getText());
/*     */     }
/* 266 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String toString(Token start, Token stop)
/*     */   {
/* 271 */     if ((start != null) && (stop != null)) {
/* 272 */       return toString(start.getTokenIndex(), stop.getTokenIndex());
/*     */     }
/* 274 */     return null;
/*     */   }
/*     */ 
/*     */   public void fill()
/*     */   {
/* 279 */     if (this.p == -1) setup();
/* 280 */     if (((Token)this.tokens.get(this.p)).getType() == -1) return;
/*     */ 
/* 282 */     int i = this.p + 1;
/* 283 */     sync(i);
/* 284 */     while (((Token)this.tokens.get(i)).getType() != -1) {
/* 285 */       i++;
/* 286 */       sync(i);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.BufferedTokenStream
 * JD-Core Version:    0.6.0
 */