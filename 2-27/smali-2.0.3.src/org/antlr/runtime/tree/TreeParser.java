/*     */ package org.antlr.runtime.tree;
/*     */ 
/*     */ import java.util.regex.Pattern;
/*     */ import org.antlr.runtime.BaseRecognizer;
/*     */ import org.antlr.runtime.BitSet;
/*     */ import org.antlr.runtime.CommonToken;
/*     */ import org.antlr.runtime.IntStream;
/*     */ import org.antlr.runtime.MismatchedTreeNodeException;
/*     */ import org.antlr.runtime.RecognitionException;
/*     */ import org.antlr.runtime.RecognizerSharedState;
/*     */ 
/*     */ public class TreeParser extends BaseRecognizer
/*     */ {
/*  44 */   static String dotdot = ".*[^.]\\.\\.[^.].*";
/*  45 */   static String doubleEtc = ".*\\.\\.\\.\\s+\\.\\.\\..*";
/*  46 */   static Pattern dotdotPattern = Pattern.compile(dotdot);
/*  47 */   static Pattern doubleEtcPattern = Pattern.compile(doubleEtc);
/*     */   protected TreeNodeStream input;
/*     */ 
/*     */   public TreeParser(TreeNodeStream input, RecognizerSharedState state)
/*     */   {
/*  57 */     super(state);
/*  58 */     setTreeNodeStream(input);
/*     */   }
/*     */ 
/*     */   public void setTreeNodeStream(TreeNodeStream input)
/*     */   {
/*  71 */     this.input = input;
/*     */   }
/*     */ 
/*     */   public String getSourceName()
/*     */   {
/*  80 */     return this.input.getSourceName();
/*     */   }
/*     */ 
/*     */   protected Object getCurrentInputSymbol(IntStream input)
/*     */   {
/*  85 */     return ((TreeNodeStream)input).LT(1);
/*     */   }
/*     */ 
/*     */   protected Object getMissingSymbol(IntStream input, RecognitionException e, int expectedTokenType, BitSet follow)
/*     */   {
/*  94 */     String tokenText = "<missing " + getTokenNames()[expectedTokenType] + ">";
/*     */ 
/*  96 */     TreeAdaptor adaptor = ((TreeNodeStream)e.input).getTreeAdaptor();
/*  97 */     return adaptor.create(new CommonToken(expectedTokenType, tokenText));
/*     */   }
/*     */ 
/*     */   protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow)
/*     */     throws RecognitionException
/*     */   {
/* 141 */     throw new MismatchedTreeNodeException(ttype, (TreeNodeStream)input);
/*     */   }
/*     */ 
/*     */   public String getErrorHeader(RecognitionException e)
/*     */   {
/* 150 */     return getGrammarFileName() + ": node from " + (e.approximateLineInfo ? "after " : "") + "line " + e.line + ":" + e.charPositionInLine;
/*     */   }
/*     */ 
/*     */   public String getErrorMessage(RecognitionException e, String[] tokenNames)
/*     */   {
/* 159 */     if ((this instanceof TreeParser)) {
/* 160 */       TreeAdaptor adaptor = ((TreeNodeStream)e.input).getTreeAdaptor();
/* 161 */       e.token = adaptor.getToken(e.node);
/* 162 */       if (e.token == null) {
/* 163 */         e.token = new CommonToken(adaptor.getType(e.node), adaptor.getText(e.node));
/*     */       }
/*     */     }
/*     */ 
/* 167 */     return super.getErrorMessage(e, tokenNames);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.tree.TreeParser
 * JD-Core Version:    0.6.0
 */