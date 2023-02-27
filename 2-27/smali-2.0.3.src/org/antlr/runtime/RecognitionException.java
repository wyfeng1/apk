/*     */ package org.antlr.runtime;
/*     */ 
/*     */ import org.antlr.runtime.tree.CommonTree;
/*     */ import org.antlr.runtime.tree.PositionTrackingStream;
/*     */ import org.antlr.runtime.tree.Tree;
/*     */ import org.antlr.runtime.tree.TreeAdaptor;
/*     */ import org.antlr.runtime.tree.TreeNodeStream;
/*     */ 
/*     */ public class RecognitionException extends Exception
/*     */ {
/*     */   public transient IntStream input;
/*     */   public int index;
/*     */   public Token token;
/*     */   public Object node;
/*     */   public int c;
/*     */   public int line;
/*     */   public int charPositionInLine;
/*     */   public boolean approximateLineInfo;
/*     */ 
/*     */   public RecognitionException()
/*     */   {
/*     */   }
/*     */ 
/*     */   public RecognitionException(IntStream input)
/*     */   {
/* 103 */     this.input = input;
/* 104 */     this.index = input.index();
/* 105 */     if ((input instanceof TokenStream)) {
/* 106 */       this.token = ((TokenStream)input).LT(1);
/* 107 */       this.line = this.token.getLine();
/* 108 */       this.charPositionInLine = this.token.getCharPositionInLine();
/*     */     }
/* 110 */     if ((input instanceof TreeNodeStream)) {
/* 111 */       extractInformationFromTreeNodeStream(input);
/*     */     }
/* 113 */     else if ((input instanceof CharStream)) {
/* 114 */       this.c = input.LA(1);
/* 115 */       this.line = ((CharStream)input).getLine();
/* 116 */       this.charPositionInLine = ((CharStream)input).getCharPositionInLine();
/*     */     }
/*     */     else {
/* 119 */       this.c = input.LA(1);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void extractInformationFromTreeNodeStream(IntStream input) {
/* 124 */     TreeNodeStream nodes = (TreeNodeStream)input;
/*     */ 
/* 126 */     this.node = nodes.LT(1);
/*     */ 
/* 128 */     Object positionNode = null;
/* 129 */     if ((nodes instanceof PositionTrackingStream)) {
/* 130 */       positionNode = ((PositionTrackingStream)nodes).getKnownPositionElement(false);
/* 131 */       if (positionNode == null) {
/* 132 */         positionNode = ((PositionTrackingStream)nodes).getKnownPositionElement(true);
/* 133 */         this.approximateLineInfo = (positionNode != null);
/*     */       }
/*     */     }
/*     */ 
/* 137 */     TreeAdaptor adaptor = nodes.getTreeAdaptor();
/* 138 */     Token payload = adaptor.getToken(positionNode != null ? positionNode : this.node);
/* 139 */     if (payload != null) {
/* 140 */       this.token = payload;
/* 141 */       if (payload.getLine() <= 0)
/*     */       {
/* 143 */         int i = -1;
/* 144 */         Object priorNode = nodes.LT(i);
/* 145 */         while (priorNode != null) {
/* 146 */           Token priorPayload = adaptor.getToken(priorNode);
/* 147 */           if ((priorPayload != null) && (priorPayload.getLine() > 0))
/*     */           {
/* 149 */             this.line = priorPayload.getLine();
/* 150 */             this.charPositionInLine = priorPayload.getCharPositionInLine();
/* 151 */             this.approximateLineInfo = true;
/* 152 */             break;
/*     */           }
/*     */ 
/* 155 */           i--;
/*     */           try {
/* 157 */             priorNode = nodes.LT(i);
/*     */           } catch (UnsupportedOperationException ex) {
/* 159 */             priorNode = null;
/*     */           }
/*     */         }
/*     */       }
/*     */       else {
/* 164 */         this.line = payload.getLine();
/* 165 */         this.charPositionInLine = payload.getCharPositionInLine();
/*     */       }
/*     */     }
/* 168 */     else if ((this.node instanceof Tree)) {
/* 169 */       this.line = ((Tree)this.node).getLine();
/* 170 */       this.charPositionInLine = ((Tree)this.node).getCharPositionInLine();
/* 171 */       if ((this.node instanceof CommonTree))
/* 172 */         this.token = ((CommonTree)this.node).token;
/*     */     }
/*     */     else
/*     */     {
/* 176 */       int type = adaptor.getType(this.node);
/* 177 */       String text = adaptor.getText(this.node);
/* 178 */       this.token = new CommonToken(type, text);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getUnexpectedType()
/*     */   {
/* 184 */     if ((this.input instanceof TokenStream)) {
/* 185 */       return this.token.getType();
/*     */     }
/* 187 */     if ((this.input instanceof TreeNodeStream)) {
/* 188 */       TreeNodeStream nodes = (TreeNodeStream)this.input;
/* 189 */       TreeAdaptor adaptor = nodes.getTreeAdaptor();
/* 190 */       return adaptor.getType(this.node);
/*     */     }
/*     */ 
/* 193 */     return this.c;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.RecognitionException
 * JD-Core Version:    0.6.0
 */