/*     */ package org.antlr.runtime;
/*     */ 
/*     */ import java.util.List;
/*     */ 
/*     */ public class CommonTokenStream extends BufferedTokenStream
/*     */ {
/*  50 */   protected int channel = 0;
/*     */ 
/*     */   public CommonTokenStream() {
/*     */   }
/*     */   public CommonTokenStream(TokenSource tokenSource) {
/*  55 */     super(tokenSource);
/*     */   }
/*     */ 
/*     */   public void consume()
/*     */   {
/*  66 */     if (this.p == -1) setup();
/*  67 */     this.p += 1;
/*  68 */     sync(this.p);
/*  69 */     while (((Token)this.tokens.get(this.p)).getChannel() != this.channel) {
/*  70 */       this.p += 1;
/*  71 */       sync(this.p);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Token LB(int k)
/*     */   {
/*  77 */     if ((k == 0) || (this.p - k < 0)) return null;
/*     */ 
/*  79 */     int i = this.p;
/*  80 */     int n = 1;
/*     */ 
/*  82 */     while (n <= k)
/*     */     {
/*  84 */       i = skipOffTokenChannelsReverse(i - 1);
/*  85 */       n++;
/*     */     }
/*  87 */     if (i < 0) return null;
/*  88 */     return (Token)this.tokens.get(i);
/*     */   }
/*     */ 
/*     */   public Token LT(int k)
/*     */   {
/*  94 */     if (this.p == -1) setup();
/*  95 */     if (k == 0) return null;
/*  96 */     if (k < 0) return LB(-k);
/*  97 */     int i = this.p;
/*  98 */     int n = 1;
/*     */ 
/* 100 */     while (n < k)
/*     */     {
/* 102 */       i = skipOffTokenChannels(i + 1);
/* 103 */       n++;
/*     */     }
/* 105 */     if (i > this.range) this.range = i;
/* 106 */     return (Token)this.tokens.get(i);
/*     */   }
/*     */ 
/*     */   protected int skipOffTokenChannels(int i)
/*     */   {
/* 113 */     sync(i);
/* 114 */     while (((Token)this.tokens.get(i)).getChannel() != this.channel) {
/* 115 */       i++;
/* 116 */       sync(i);
/*     */     }
/* 118 */     return i;
/*     */   }
/*     */ 
/*     */   protected int skipOffTokenChannelsReverse(int i) {
/* 122 */     while ((i >= 0) && (((Token)this.tokens.get(i)).getChannel() != this.channel)) {
/* 123 */       i--;
/*     */     }
/* 125 */     return i;
/*     */   }
/*     */ 
/*     */   protected void setup()
/*     */   {
/* 136 */     this.p = 0;
/* 137 */     sync(0);
/* 138 */     int i = 0;
/* 139 */     while (((Token)this.tokens.get(i)).getChannel() != this.channel) {
/* 140 */       i++;
/* 141 */       sync(i);
/*     */     }
/* 143 */     this.p = i;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.CommonTokenStream
 * JD-Core Version:    0.6.0
 */