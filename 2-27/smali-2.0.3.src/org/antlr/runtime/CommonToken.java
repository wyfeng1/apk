/*     */ package org.antlr.runtime;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ 
/*     */ public class CommonToken
/*     */   implements Serializable, Token
/*     */ {
/*     */   protected int type;
/*     */   protected int line;
/*  35 */   protected int charPositionInLine = -1;
/*  36 */   protected int channel = 0;
/*     */   protected transient CharStream input;
/*     */   protected String text;
/*  46 */   protected int index = -1;
/*     */   protected int start;
/*     */   protected int stop;
/*     */ 
/*     */   public CommonToken(int type)
/*     */   {
/*  55 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public CommonToken(int type, String text)
/*     */   {
/*  67 */     this.type = type;
/*  68 */     this.channel = 0;
/*  69 */     this.text = text;
/*     */   }
/*     */ 
/*     */   public CommonToken(Token oldToken) {
/*  73 */     this.text = oldToken.getText();
/*  74 */     this.type = oldToken.getType();
/*  75 */     this.line = oldToken.getLine();
/*  76 */     this.index = oldToken.getTokenIndex();
/*  77 */     this.charPositionInLine = oldToken.getCharPositionInLine();
/*  78 */     this.channel = oldToken.getChannel();
/*  79 */     this.input = oldToken.getInputStream();
/*  80 */     if ((oldToken instanceof CommonToken)) {
/*  81 */       this.start = ((CommonToken)oldToken).start;
/*  82 */       this.stop = ((CommonToken)oldToken).stop;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getType()
/*     */   {
/*  88 */     return this.type;
/*     */   }
/*     */ 
/*     */   public void setLine(int line)
/*     */   {
/*  93 */     this.line = line;
/*     */   }
/*     */ 
/*     */   public String getText()
/*     */   {
/*  98 */     if (this.text != null) {
/*  99 */       return this.text;
/*     */     }
/* 101 */     if (this.input == null) {
/* 102 */       return null;
/*     */     }
/* 104 */     int n = this.input.size();
/* 105 */     if ((this.start < n) && (this.stop < n)) {
/* 106 */       return this.input.substring(this.start, this.stop);
/*     */     }
/*     */ 
/* 109 */     return "<EOF>";
/*     */   }
/*     */ 
/*     */   public void setText(String text)
/*     */   {
/* 120 */     this.text = text;
/*     */   }
/*     */ 
/*     */   public int getLine()
/*     */   {
/* 125 */     return this.line;
/*     */   }
/*     */ 
/*     */   public int getCharPositionInLine()
/*     */   {
/* 130 */     return this.charPositionInLine;
/*     */   }
/*     */ 
/*     */   public void setCharPositionInLine(int charPositionInLine)
/*     */   {
/* 135 */     this.charPositionInLine = charPositionInLine;
/*     */   }
/*     */ 
/*     */   public int getChannel()
/*     */   {
/* 140 */     return this.channel;
/*     */   }
/*     */ 
/*     */   public void setChannel(int channel)
/*     */   {
/* 145 */     this.channel = channel;
/*     */   }
/*     */ 
/*     */   public void setType(int type)
/*     */   {
/* 150 */     this.type = type;
/*     */   }
/*     */ 
/*     */   public int getStartIndex() {
/* 154 */     return this.start;
/*     */   }
/*     */ 
/*     */   public void setStartIndex(int start) {
/* 158 */     this.start = start;
/*     */   }
/*     */ 
/*     */   public int getStopIndex() {
/* 162 */     return this.stop;
/*     */   }
/*     */ 
/*     */   public void setStopIndex(int stop) {
/* 166 */     this.stop = stop;
/*     */   }
/*     */ 
/*     */   public int getTokenIndex()
/*     */   {
/* 171 */     return this.index;
/*     */   }
/*     */ 
/*     */   public void setTokenIndex(int index)
/*     */   {
/* 176 */     this.index = index;
/*     */   }
/*     */ 
/*     */   public CharStream getInputStream()
/*     */   {
/* 181 */     return this.input;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 191 */     String channelStr = "";
/* 192 */     if (this.channel > 0) {
/* 193 */       channelStr = ",channel=" + this.channel;
/*     */     }
/* 195 */     String txt = getText();
/* 196 */     if (txt != null) {
/* 197 */       txt = txt.replaceAll("\n", "\\\\n");
/* 198 */       txt = txt.replaceAll("\r", "\\\\r");
/* 199 */       txt = txt.replaceAll("\t", "\\\\t");
/*     */     }
/*     */     else {
/* 202 */       txt = "<no text>";
/*     */     }
/* 204 */     return "[@" + getTokenIndex() + "," + this.start + ":" + this.stop + "='" + txt + "',<" + this.type + ">" + channelStr + "," + this.line + ":" + getCharPositionInLine() + "]";
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.CommonToken
 * JD-Core Version:    0.6.0
 */