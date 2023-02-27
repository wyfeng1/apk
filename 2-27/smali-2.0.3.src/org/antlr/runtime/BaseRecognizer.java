/*     */ package org.antlr.runtime;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public abstract class BaseRecognizer
/*     */ {
/*     */   protected RecognizerSharedState state;
/*     */ 
/*     */   public BaseRecognizer(RecognizerSharedState state)
/*     */   {
/*  64 */     if (state == null) {
/*  65 */       state = new RecognizerSharedState();
/*     */     }
/*  67 */     this.state = state;
/*     */   }
/*     */ 
/*     */   public Object match(IntStream input, int ttype, BitSet follow)
/*     */     throws RecognitionException
/*     */   {
/* 104 */     Object matchedSymbol = getCurrentInputSymbol(input);
/* 105 */     if (input.LA(1) == ttype) {
/* 106 */       input.consume();
/* 107 */       this.state.errorRecovery = false;
/* 108 */       this.state.failed = false;
/* 109 */       return matchedSymbol;
/*     */     }
/* 111 */     if (this.state.backtracking > 0) {
/* 112 */       this.state.failed = true;
/* 113 */       return matchedSymbol;
/*     */     }
/* 115 */     matchedSymbol = recoverFromMismatchedToken(input, ttype, follow);
/* 116 */     return matchedSymbol;
/*     */   }
/*     */ 
/*     */   public boolean mismatchIsUnwantedToken(IntStream input, int ttype)
/*     */   {
/* 127 */     return input.LA(2) == ttype;
/*     */   }
/*     */ 
/*     */   public boolean mismatchIsMissingToken(IntStream input, BitSet follow) {
/* 131 */     if (follow == null)
/*     */     {
/* 134 */       return false;
/*     */     }
/*     */ 
/* 137 */     if (follow.member(1)) {
/* 138 */       BitSet viableTokensFollowingThisRule = computeContextSensitiveRuleFOLLOW();
/* 139 */       follow = follow.or(viableTokensFollowingThisRule);
/* 140 */       if (this.state._fsp >= 0) {
/* 141 */         follow.remove(1);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 156 */     return (follow.member(input.LA(1))) || (follow.member(1));
/*     */   }
/*     */ 
/*     */   public void reportError(RecognitionException e)
/*     */   {
/* 179 */     if (this.state.errorRecovery)
/*     */     {
/* 181 */       return;
/*     */     }
/* 183 */     this.state.syntaxErrors += 1;
/* 184 */     this.state.errorRecovery = true;
/*     */ 
/* 186 */     displayRecognitionError(getTokenNames(), e);
/*     */   }
/*     */ 
/*     */   public void displayRecognitionError(String[] tokenNames, RecognitionException e)
/*     */   {
/* 192 */     String hdr = getErrorHeader(e);
/* 193 */     String msg = getErrorMessage(e, tokenNames);
/* 194 */     emitErrorMessage(hdr + " " + msg);
/*     */   }
/*     */ 
/*     */   public String getErrorMessage(RecognitionException e, String[] tokenNames)
/*     */   {
/* 220 */     String msg = e.getMessage();
/* 221 */     if ((e instanceof UnwantedTokenException)) {
/* 222 */       UnwantedTokenException ute = (UnwantedTokenException)e;
/*     */       String tokenName;
/*     */       String tokenName;
/* 224 */       if (ute.expecting == -1) {
/* 225 */         tokenName = "EOF";
/*     */       }
/*     */       else {
/* 228 */         tokenName = tokenNames[ute.expecting];
/*     */       }
/* 230 */       msg = "extraneous input " + getTokenErrorDisplay(ute.getUnexpectedToken()) + " expecting " + tokenName;
/*     */     }
/* 233 */     else if ((e instanceof MissingTokenException)) {
/* 234 */       MissingTokenException mte = (MissingTokenException)e;
/*     */       String tokenName;
/*     */       String tokenName;
/* 236 */       if (mte.expecting == -1) {
/* 237 */         tokenName = "EOF";
/*     */       }
/*     */       else {
/* 240 */         tokenName = tokenNames[mte.expecting];
/*     */       }
/* 242 */       msg = "missing " + tokenName + " at " + getTokenErrorDisplay(e.token);
/*     */     }
/* 244 */     else if ((e instanceof MismatchedTokenException)) {
/* 245 */       MismatchedTokenException mte = (MismatchedTokenException)e;
/*     */       String tokenName;
/*     */       String tokenName;
/* 247 */       if (mte.expecting == -1) {
/* 248 */         tokenName = "EOF";
/*     */       }
/*     */       else {
/* 251 */         tokenName = tokenNames[mte.expecting];
/*     */       }
/* 253 */       msg = "mismatched input " + getTokenErrorDisplay(e.token) + " expecting " + tokenName;
/*     */     }
/* 256 */     else if ((e instanceof MismatchedTreeNodeException)) {
/* 257 */       MismatchedTreeNodeException mtne = (MismatchedTreeNodeException)e;
/*     */       String tokenName;
/*     */       String tokenName;
/* 259 */       if (mtne.expecting == -1) {
/* 260 */         tokenName = "EOF";
/*     */       }
/*     */       else {
/* 263 */         tokenName = tokenNames[mtne.expecting];
/*     */       }
/* 265 */       msg = "mismatched tree node: " + mtne.node + " expecting " + tokenName;
/*     */     }
/* 268 */     else if ((e instanceof NoViableAltException))
/*     */     {
/* 273 */       msg = "no viable alternative at input " + getTokenErrorDisplay(e.token);
/*     */     }
/* 275 */     else if ((e instanceof EarlyExitException))
/*     */     {
/* 278 */       msg = "required (...)+ loop did not match anything at input " + getTokenErrorDisplay(e.token);
/*     */     }
/* 281 */     else if ((e instanceof MismatchedSetException)) {
/* 282 */       MismatchedSetException mse = (MismatchedSetException)e;
/* 283 */       msg = "mismatched input " + getTokenErrorDisplay(e.token) + " expecting set " + mse.expecting;
/*     */     }
/* 286 */     else if ((e instanceof MismatchedNotSetException)) {
/* 287 */       MismatchedNotSetException mse = (MismatchedNotSetException)e;
/* 288 */       msg = "mismatched input " + getTokenErrorDisplay(e.token) + " expecting set " + mse.expecting;
/*     */     }
/* 291 */     else if ((e instanceof FailedPredicateException)) {
/* 292 */       FailedPredicateException fpe = (FailedPredicateException)e;
/* 293 */       msg = "rule " + fpe.ruleName + " failed predicate: {" + fpe.predicateText + "}?";
/*     */     }
/*     */ 
/* 296 */     return msg;
/*     */   }
/*     */ 
/*     */   public int getNumberOfSyntaxErrors()
/*     */   {
/* 307 */     return this.state.syntaxErrors;
/*     */   }
/*     */ 
/*     */   public String getErrorHeader(RecognitionException e)
/*     */   {
/* 312 */     if (getSourceName() != null) {
/* 313 */       return getSourceName() + " line " + e.line + ":" + e.charPositionInLine;
/*     */     }
/* 315 */     return "line " + e.line + ":" + e.charPositionInLine;
/*     */   }
/*     */ 
/*     */   public String getTokenErrorDisplay(Token t)
/*     */   {
/* 327 */     String s = t.getText();
/* 328 */     if (s == null) {
/* 329 */       if (t.getType() == -1) {
/* 330 */         s = "<EOF>";
/*     */       }
/*     */       else {
/* 333 */         s = "<" + t.getType() + ">";
/*     */       }
/*     */     }
/* 336 */     s = s.replaceAll("\n", "\\\\n");
/* 337 */     s = s.replaceAll("\r", "\\\\r");
/* 338 */     s = s.replaceAll("\t", "\\\\t");
/* 339 */     return "'" + s + "'";
/*     */   }
/*     */ 
/*     */   public void emitErrorMessage(String msg)
/*     */   {
/* 344 */     System.err.println(msg);
/*     */   }
/*     */ 
/*     */   public void recover(IntStream input, RecognitionException re)
/*     */   {
/* 354 */     if (this.state.lastErrorIndex == input.index())
/*     */     {
/* 359 */       input.consume();
/*     */     }
/* 361 */     this.state.lastErrorIndex = input.index();
/* 362 */     BitSet followSet = computeErrorRecoverySet();
/* 363 */     beginResync();
/* 364 */     consumeUntil(input, followSet);
/* 365 */     endResync();
/*     */   }
/*     */ 
/*     */   public void beginResync()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void endResync()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected BitSet computeErrorRecoverySet()
/*     */   {
/* 469 */     return combineFollows(false);
/*     */   }
/*     */ 
/*     */   protected BitSet computeContextSensitiveRuleFOLLOW()
/*     */   {
/* 525 */     return combineFollows(true);
/*     */   }
/*     */ 
/*     */   protected BitSet combineFollows(boolean exact)
/*     */   {
/* 533 */     int top = this.state._fsp;
/* 534 */     BitSet followSet = new BitSet();
/* 535 */     for (int i = top; i >= 0; i--) {
/* 536 */       BitSet localFollowSet = this.state.following[i];
/*     */ 
/* 541 */       followSet.orInPlace(localFollowSet);
/* 542 */       if (!exact)
/*     */         continue;
/* 544 */       if (!localFollowSet.member(1)) {
/*     */         break;
/*     */       }
/* 547 */       if (i > 0) {
/* 548 */         followSet.remove(1);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 556 */     return followSet;
/*     */   }
/*     */ 
/*     */   protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow)
/*     */     throws RecognitionException
/*     */   {
/* 591 */     RecognitionException e = null;
/*     */ 
/* 593 */     if (mismatchIsUnwantedToken(input, ttype)) {
/* 594 */       e = new UnwantedTokenException(ttype, input);
/*     */ 
/* 600 */       beginResync();
/* 601 */       input.consume();
/* 602 */       endResync();
/* 603 */       reportError(e);
/*     */ 
/* 605 */       Object matchedSymbol = getCurrentInputSymbol(input);
/* 606 */       input.consume();
/* 607 */       return matchedSymbol;
/*     */     }
/*     */ 
/* 610 */     if (mismatchIsMissingToken(input, follow)) {
/* 611 */       Object inserted = getMissingSymbol(input, e, ttype, follow);
/* 612 */       e = new MissingTokenException(ttype, input, inserted);
/* 613 */       reportError(e);
/* 614 */       return inserted;
/*     */     }
/*     */ 
/* 617 */     e = new MismatchedTokenException(ttype, input);
/* 618 */     throw e;
/*     */   }
/*     */ 
/*     */   protected Object getCurrentInputSymbol(IntStream input)
/*     */   {
/* 646 */     return null;
/*     */   }
/*     */ 
/*     */   protected Object getMissingSymbol(IntStream input, RecognitionException e, int expectedTokenType, BitSet follow)
/*     */   {
/* 672 */     return null;
/*     */   }
/*     */ 
/*     */   public void consumeUntil(IntStream input, BitSet set)
/*     */   {
/* 687 */     int ttype = input.LA(1);
/* 688 */     while ((ttype != -1) && (!set.member(ttype)))
/*     */     {
/* 690 */       input.consume();
/* 691 */       ttype = input.LA(1);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void pushFollow(BitSet fset)
/*     */   {
/* 697 */     if (this.state._fsp + 1 >= this.state.following.length) {
/* 698 */       BitSet[] f = new BitSet[this.state.following.length * 2];
/* 699 */       System.arraycopy(this.state.following, 0, f, 0, this.state.following.length);
/* 700 */       this.state.following = f;
/*     */     }
/* 702 */     this.state.following[(++this.state._fsp)] = fset;
/*     */   }
/*     */ 
/*     */   public static List<String> getRuleInvocationStack(Throwable e, String recognizerClassName)
/*     */   {
/* 728 */     List rules = new ArrayList();
/* 729 */     StackTraceElement[] stack = e.getStackTrace();
/*     */ 
/* 731 */     for (int i = stack.length - 1; i >= 0; i--) {
/* 732 */       StackTraceElement t = stack[i];
/* 733 */       if (t.getClassName().startsWith("org.antlr.runtime.")) {
/*     */         continue;
/*     */       }
/* 736 */       if (t.getMethodName().equals("nextToken")) {
/*     */         continue;
/*     */       }
/* 739 */       if (!t.getClassName().equals(recognizerClassName)) {
/*     */         continue;
/*     */       }
/* 742 */       rules.add(t.getMethodName());
/*     */     }
/* 744 */     return rules;
/*     */   }
/*     */ 
/*     */   public String[] getTokenNames()
/*     */   {
/* 759 */     return null;
/*     */   }
/*     */ 
/*     */   public String getGrammarFileName()
/*     */   {
/* 766 */     return null;
/*     */   }
/*     */ 
/*     */   public abstract String getSourceName();
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.BaseRecognizer
 * JD-Core Version:    0.6.0
 */