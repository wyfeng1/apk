/*      */ package org.jf.smali;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Reader;
/*      */ import org.antlr.runtime.CommonToken;
/*      */ import org.antlr.runtime.Token;
/*      */ import org.antlr.runtime.TokenSource;
/*      */ import org.jf.util.PathUtil;
/*      */ 
/*      */ public class smaliFlexLexer
/*      */   implements TokenSource, LexerErrorInterface
/*      */ {
/*   36 */   private static final int[] ZZ_LEXSTATE = { 0, 0, 1, 1, 2, 2 };
/*      */ 
/*   59 */   private static final char[] ZZ_CMAP = zzUnpackCMap("");
/*      */ 
/*   64 */   private static final int[] ZZ_ACTION = zzUnpackAction();
/*      */ 
/*  141 */   private static final int[] ZZ_ROWMAP = zzUnpackRowMap();
/*      */ 
/*  311 */   private static final int[] ZZ_TRANS = zzUnpackTrans();
/*      */ 
/* 2724 */   private static final String[] ZZ_ERROR_MSG = { "Unkown internal scanner error", "Error: could not match input", "Error: pushback value was too large" };
/*      */ 
/* 2733 */   private static final int[] ZZ_ATTRIBUTE = zzUnpackAttribute();
/*      */   private Reader zzReader;
/*      */   private int zzState;
/* 2787 */   private int zzLexicalState = 0;
/*      */ 
/* 2791 */   private char[] zzBuffer = new char[16384];
/*      */   private int zzMarkedPos;
/*      */   private int zzCurrentPos;
/*      */   private int zzStartRead;
/*      */   private int zzEndRead;
/*      */   private int yyline;
/*      */   private int yychar;
/*      */   private int yycolumn;
/* 2821 */   private boolean zzAtBOL = true;
/*      */   private boolean zzAtEOF;
/* 2830 */   private StringBuffer sb = new StringBuffer();
/* 2831 */   private String stringOrCharError = null;
/*      */   private int stringStartLine;
/*      */   private int stringStartCol;
/*      */   private int stringStartChar;
/* 2836 */   private int lexerErrors = 0;
/*      */   private File sourceFile;
/*      */   private boolean suppressErrors;
/*      */ 
/*      */   private static int[] zzUnpackAction()
/*      */   {
/*  119 */     int[] result = new int[1153];
/*  120 */     int offset = 0;
/*  121 */     offset = zzUnpackAction("", offset, result);
/*  122 */     return result;
/*      */   }
/*      */ 
/*      */   private static int zzUnpackAction(String packed, int offset, int[] result) {
/*  126 */     int i = 0;
/*  127 */     int j = offset;
/*  128 */     int l = packed.length();
/*  129 */     while (i < l) {
/*  130 */       int count = packed.charAt(i++);
/*  131 */       int value = packed.charAt(i++);
/*      */       do { result[(j++)] = value; count--; } while (count > 0);
/*      */     }
/*  134 */     return j;
/*      */   }
/*      */ 
/*      */   private static int[] zzUnpackRowMap()
/*      */   {
/*  291 */     int[] result = new int[1153];
/*  292 */     int offset = 0;
/*  293 */     offset = zzUnpackRowMap("", offset, result);
/*  294 */     return result;
/*      */   }
/*      */ 
/*      */   private static int zzUnpackRowMap(String packed, int offset, int[] result) {
/*  298 */     int i = 0;
/*  299 */     int j = offset;
/*  300 */     int l = packed.length();
/*  301 */     while (i < l) {
/*  302 */       int high = packed.charAt(i++) << '\020';
/*  303 */       result[(j++)] = (high | packed.charAt(i++));
/*      */     }
/*  305 */     return j;
/*      */   }
/*      */ 
/*      */   private static int[] zzUnpackTrans()
/*      */   {
/* 2698 */     int[] result = new int[81198];
/* 2699 */     int offset = 0;
/* 2700 */     offset = zzUnpackTrans("", offset, result);
/* 2701 */     return result;
/*      */   }
/*      */ 
/*      */   private static int zzUnpackTrans(String packed, int offset, int[] result) {
/* 2705 */     int i = 0;
/* 2706 */     int j = offset;
/* 2707 */     int l = packed.length();
/* 2708 */     while (i < l) {
/* 2709 */       int count = packed.charAt(i++);
/* 2710 */       int value = packed.charAt(i++);
/* 2711 */       value--;
/*      */       do { result[(j++)] = value; count--; } while (count > 0);
/*      */     }
/* 2714 */     return j;
/*      */   }
/*      */ 
/*      */   private static int[] zzUnpackAttribute()
/*      */   {
/* 2762 */     int[] result = new int[1153];
/* 2763 */     int offset = 0;
/* 2764 */     offset = zzUnpackAttribute("", offset, result);
/* 2765 */     return result;
/*      */   }
/*      */ 
/*      */   private static int zzUnpackAttribute(String packed, int offset, int[] result) {
/* 2769 */     int i = 0;
/* 2770 */     int j = offset;
/* 2771 */     int l = packed.length();
/* 2772 */     while (i < l) {
/* 2773 */       int count = packed.charAt(i++);
/* 2774 */       int value = packed.charAt(i++);
/*      */       do { result[(j++)] = value; count--; } while (count > 0);
/*      */     }
/* 2777 */     return j;
/*      */   }
/*      */ 
/*      */   public Token nextToken()
/*      */   {
/*      */     try
/*      */     {
/* 2844 */       Token token = yylex();
/* 2845 */       if ((token instanceof InvalidToken)) {
/* 2846 */         InvalidToken invalidToken = (InvalidToken)token;
/* 2847 */         if (!this.suppressErrors) {
/* 2848 */           System.err.println(getErrorHeader(invalidToken) + " Error for input '" + invalidToken.getText() + "': " + invalidToken.getMessage());
/*      */         }
/*      */ 
/* 2851 */         this.lexerErrors += 1;
/*      */       }
/* 2853 */       return token;
/*      */     }
/*      */     catch (IOException e) {
/* 2856 */       System.err.println("shouldn't happen: " + e.getMessage());
/* 2857 */     }return newToken(-1);
/*      */   }
/*      */ 
/*      */   public int getLine()
/*      */   {
/* 2870 */     return this.yyline + 1;
/*      */   }
/*      */ 
/*      */   public int getColumn() {
/* 2874 */     return this.yycolumn;
/*      */   }
/*      */ 
/*      */   public void setSourceFile(File sourceFile)
/*      */   {
/* 2882 */     this.sourceFile = sourceFile;
/*      */   }
/*      */ 
/*      */   public String getSourceName() {
/* 2886 */     if (this.sourceFile == null)
/* 2887 */       return "";
/*      */     try
/*      */     {
/* 2890 */       return PathUtil.getRelativeFile(new File("."), this.sourceFile).getPath(); } catch (IOException ex) {
/*      */     }
/* 2892 */     return this.sourceFile.getAbsolutePath();
/*      */   }
/*      */ 
/*      */   public int getNumberOfSyntaxErrors()
/*      */   {
/* 2897 */     return this.lexerErrors;
/*      */   }
/*      */ 
/*      */   private Token newToken(int type, String text, boolean hidden) {
/* 2901 */     CommonToken token = new CommonToken(type, text);
/* 2902 */     if (hidden) {
/* 2903 */       token.setChannel(99);
/*      */     }
/*      */ 
/* 2906 */     token.setStartIndex(this.yychar);
/* 2907 */     token.setStopIndex(this.yychar + yylength() - 1);
/* 2908 */     token.setLine(getLine());
/* 2909 */     token.setCharPositionInLine(getColumn());
/* 2910 */     return token;
/*      */   }
/*      */ 
/*      */   private Token newToken(int type, boolean hidden)
/*      */   {
/* 2918 */     return newToken(type, yytext(), hidden);
/*      */   }
/*      */ 
/*      */   private Token newToken(int type) {
/* 2922 */     return newToken(type, yytext(), false);
/*      */   }
/*      */ 
/*      */   private Token invalidToken(String message, String text) {
/* 2926 */     InvalidToken token = new InvalidToken(message, text);
/*      */ 
/* 2928 */     token.setStartIndex(this.yychar);
/* 2929 */     token.setStopIndex(this.yychar + yylength() - 1);
/* 2930 */     token.setLine(getLine());
/* 2931 */     token.setCharPositionInLine(getColumn());
/*      */ 
/* 2933 */     return token;
/*      */   }
/*      */ 
/*      */   private Token invalidToken(String message) {
/* 2937 */     return invalidToken(message, yytext());
/*      */   }
/*      */ 
/*      */   private void beginStringOrChar(int state) {
/* 2941 */     yybegin(state);
/* 2942 */     this.sb.setLength(0);
/* 2943 */     this.stringStartLine = getLine();
/* 2944 */     this.stringStartCol = getColumn();
/* 2945 */     this.stringStartChar = this.yychar;
/* 2946 */     this.stringOrCharError = null;
/*      */   }
/*      */ 
/*      */   private Token endStringOrChar(int type) {
/* 2950 */     yybegin(0);
/*      */ 
/* 2952 */     if (this.stringOrCharError != null) {
/* 2953 */       return invalidStringOrChar(this.stringOrCharError);
/*      */     }
/*      */ 
/* 2956 */     CommonToken token = new CommonToken(type, this.sb.toString());
/* 2957 */     token.setStartIndex(this.stringStartChar);
/* 2958 */     token.setStopIndex(this.yychar + yylength() - 1);
/* 2959 */     token.setLine(this.stringStartLine);
/* 2960 */     token.setCharPositionInLine(this.stringStartCol);
/* 2961 */     return token;
/*      */   }
/*      */ 
/*      */   private void setStringOrCharError(String message) {
/* 2965 */     if (this.stringOrCharError == null)
/* 2966 */       this.stringOrCharError = message;
/*      */   }
/*      */ 
/*      */   private Token invalidStringOrChar(String message)
/*      */   {
/* 2971 */     yybegin(0);
/*      */ 
/* 2973 */     InvalidToken token = new InvalidToken(message, this.sb.toString());
/* 2974 */     token.setStartIndex(this.stringStartChar);
/* 2975 */     token.setStopIndex(this.yychar + yylength() - 1);
/* 2976 */     token.setLine(this.stringStartLine);
/* 2977 */     token.setCharPositionInLine(this.stringStartCol);
/* 2978 */     return token;
/*      */   }
/*      */ 
/*      */   public String getErrorHeader(InvalidToken token) {
/* 2982 */     return getSourceName() + "[" + token.getLine() + "," + token.getCharPositionInLine() + "]";
/*      */   }
/*      */ 
/*      */   public smaliFlexLexer(Reader in)
/*      */   {
/* 2993 */     this.zzReader = in;
/*      */   }
/*      */ 
/*      */   private static char[] zzUnpackCMap(String packed)
/*      */   {
/* 3013 */     char[] map = new char[65536];
/* 3014 */     int i = 0;
/* 3015 */     int j = 0;
/* 3016 */     while (i < 208) {
/* 3017 */       int count = packed.charAt(i++);
/* 3018 */       char value = packed.charAt(i++);
/*      */       do { map[(j++)] = value; count--; } while (count > 0);
/*      */     }
/* 3021 */     return map;
/*      */   }
/*      */ 
/*      */   private boolean zzRefill()
/*      */     throws IOException
/*      */   {
/* 3035 */     if (this.zzStartRead > 0) {
/* 3036 */       System.arraycopy(this.zzBuffer, this.zzStartRead, this.zzBuffer, 0, this.zzEndRead - this.zzStartRead);
/*      */ 
/* 3041 */       this.zzEndRead -= this.zzStartRead;
/* 3042 */       this.zzCurrentPos -= this.zzStartRead;
/* 3043 */       this.zzMarkedPos -= this.zzStartRead;
/* 3044 */       this.zzStartRead = 0;
/*      */     }
/*      */ 
/* 3048 */     if (this.zzCurrentPos >= this.zzBuffer.length)
/*      */     {
/* 3050 */       char[] newBuffer = new char[this.zzCurrentPos * 2];
/* 3051 */       System.arraycopy(this.zzBuffer, 0, newBuffer, 0, this.zzBuffer.length);
/* 3052 */       this.zzBuffer = newBuffer;
/*      */     }
/*      */ 
/* 3056 */     int numRead = this.zzReader.read(this.zzBuffer, this.zzEndRead, this.zzBuffer.length - this.zzEndRead);
/*      */ 
/* 3059 */     if (numRead > 0) {
/* 3060 */       this.zzEndRead += numRead;
/* 3061 */       return false;
/*      */     }
/*      */ 
/* 3064 */     if (numRead == 0) {
/* 3065 */       int c = this.zzReader.read();
/* 3066 */       if (c == -1) {
/* 3067 */         return true;
/*      */       }
/* 3069 */       this.zzBuffer[(this.zzEndRead++)] = (char)c;
/* 3070 */       return false;
/*      */     }
/*      */ 
/* 3075 */     return true;
/*      */   }
/*      */ 
/*      */   public final void yybegin(int newState)
/*      */   {
/* 3127 */     this.zzLexicalState = newState;
/*      */   }
/*      */ 
/*      */   public final String yytext()
/*      */   {
/* 3135 */     return new String(this.zzBuffer, this.zzStartRead, this.zzMarkedPos - this.zzStartRead);
/*      */   }
/*      */ 
/*      */   public final int yylength()
/*      */   {
/* 3159 */     return this.zzMarkedPos - this.zzStartRead;
/*      */   }
/*      */ 
/*      */   private void zzScanError(int errorCode)
/*      */   {
/*      */     String message;
/*      */     try
/*      */     {
/* 3180 */       message = ZZ_ERROR_MSG[errorCode];
/*      */     }
/*      */     catch (ArrayIndexOutOfBoundsException e) {
/* 3183 */       message = ZZ_ERROR_MSG[0];
/*      */     }
/*      */ 
/* 3186 */     throw new Error(message);
/*      */   }
/*      */ 
/*      */   public Token yylex()
/*      */     throws IOException
/*      */   {
/* 3220 */     int zzEndReadL = this.zzEndRead;
/* 3221 */     char[] zzBufferL = this.zzBuffer;
/* 3222 */     char[] zzCMapL = ZZ_CMAP;
/*      */ 
/* 3224 */     int[] zzTransL = ZZ_TRANS;
/* 3225 */     int[] zzRowMapL = ZZ_ROWMAP;
/* 3226 */     int[] zzAttrL = ZZ_ATTRIBUTE;
/*      */     while (true)
/*      */     {
/* 3229 */       int zzMarkedPosL = this.zzMarkedPos;
/*      */ 
/* 3231 */       this.yychar += zzMarkedPosL - this.zzStartRead;
/*      */ 
/* 3233 */       boolean zzR = false;
/* 3234 */       for (int zzCurrentPosL = this.zzStartRead; zzCurrentPosL < zzMarkedPosL; )
/*      */       {
/* 3236 */         switch (zzBufferL[zzCurrentPosL]) {
/*      */         case '\013':
/*      */         case '\f':
/*      */         case '':
/*      */         case ' ':
/*      */         case ' ':
/* 3242 */           this.yyline += 1;
/* 3243 */           this.yycolumn = 0;
/* 3244 */           zzR = false;
/* 3245 */           break;
/*      */         case '\r':
/* 3247 */           this.yyline += 1;
/* 3248 */           this.yycolumn = 0;
/* 3249 */           zzR = true;
/* 3250 */           break;
/*      */         case '\n':
/* 3252 */           if (zzR) {
/* 3253 */             zzR = false;
/*      */           } else {
/* 3255 */             this.yyline += 1;
/* 3256 */             this.yycolumn = 0;
/*      */           }
/* 3258 */           break;
/*      */         default:
/* 3260 */           zzR = false;
/* 3261 */           this.yycolumn += 1;
/*      */         }
/* 3235 */         zzCurrentPosL++;
/*      */       }
/*      */ 
/* 3265 */       if (zzR)
/*      */       {
/*      */         boolean zzPeek;
/*      */         boolean zzPeek;
/* 3268 */         if (zzMarkedPosL < zzEndReadL) {
/* 3269 */           zzPeek = zzBufferL[zzMarkedPosL] == '\n';
/*      */         }
/*      */         else
/*      */         {
/*      */           boolean zzPeek;
/* 3270 */           if (this.zzAtEOF) {
/* 3271 */             zzPeek = false;
/*      */           } else {
/* 3273 */             boolean eof = zzRefill();
/* 3274 */             zzEndReadL = this.zzEndRead;
/* 3275 */             zzMarkedPosL = this.zzMarkedPos;
/* 3276 */             zzBufferL = this.zzBuffer;
/*      */             boolean zzPeek;
/* 3277 */             if (eof)
/* 3278 */               zzPeek = false;
/*      */             else
/* 3280 */               zzPeek = zzBufferL[zzMarkedPosL] == '\n'; 
/*      */           }
/*      */         }
/* 3282 */         if (zzPeek) this.yyline -= 1;
/*      */       }
/* 3284 */       int zzAction = -1;
/*      */ 
/* 3286 */       zzCurrentPosL = this.zzCurrentPos = this.zzStartRead = zzMarkedPosL;
/*      */ 
/* 3288 */       this.zzState = ZZ_LEXSTATE[this.zzLexicalState];
/*      */       int zzInput;
/*      */       while (true)
/*      */       {
/*      */         int zzInput;
/* 3294 */         if (zzCurrentPosL < zzEndReadL) {
/* 3295 */           zzInput = zzBufferL[(zzCurrentPosL++)]; } else {
/* 3296 */           if (this.zzAtEOF) {
/* 3297 */             int zzInput = -1;
/* 3298 */             break;
/*      */           }
/*      */ 
/* 3302 */           this.zzCurrentPos = zzCurrentPosL;
/* 3303 */           this.zzMarkedPos = zzMarkedPosL;
/* 3304 */           boolean eof = zzRefill();
/*      */ 
/* 3306 */           zzCurrentPosL = this.zzCurrentPos;
/* 3307 */           zzMarkedPosL = this.zzMarkedPos;
/* 3308 */           zzBufferL = this.zzBuffer;
/* 3309 */           zzEndReadL = this.zzEndRead;
/* 3310 */           if (eof) {
/* 3311 */             int zzInput = -1;
/* 3312 */             break;
/*      */           }
/*      */ 
/* 3315 */           zzInput = zzBufferL[(zzCurrentPosL++)];
/*      */         }
/*      */ 
/* 3318 */         int zzNext = zzTransL[(zzRowMapL[this.zzState] + zzCMapL[zzInput])];
/* 3319 */         if (zzNext == -1) break;
/* 3320 */         this.zzState = zzNext;
/*      */ 
/* 3322 */         int zzAttributes = zzAttrL[this.zzState];
/* 3323 */         if ((zzAttributes & 0x1) == 1) {
/* 3324 */           zzAction = this.zzState;
/* 3325 */           zzMarkedPosL = zzCurrentPosL;
/* 3326 */           if ((zzAttributes & 0x8) == 8)
/*      */           {
/*      */             break;
/*      */           }
/*      */         }
/*      */       }
/*      */ 
/* 3333 */       this.zzMarkedPos = zzMarkedPosL;
/*      */ 
/* 3335 */       switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
/*      */       case 102:
/* 3337 */         return newToken(75);
/*      */       case 134:
/* 3339 */         break;
/*      */       case 96:
/* 3341 */         return newToken(213);
/*      */       case 135:
/* 3343 */         break;
/*      */       case 85:
/* 3345 */         return newToken(200);
/*      */       case 136:
/* 3347 */         break;
/*      */       case 39:
/* 3349 */         this.sb.append('\r');
/*      */       case 137:
/* 3351 */         break;
/*      */       case 97:
/* 3353 */         return newToken(56);
/*      */       case 138:
/* 3355 */         break;
/*      */       case 111:
/* 3357 */         return newToken(68);
/*      */       case 139:
/* 3359 */         break;
/*      */       case 100:
/* 3361 */         return newToken(40);
/*      */       case 140:
/* 3363 */         break;
/*      */       case 119:
/* 3365 */         return newToken(93);
/*      */       case 141:
/* 3367 */         break;
/*      */       case 43:
/* 3369 */         this.sb.append('\'');
/*      */       case 142:
/* 3371 */         break;
/*      */       case 8:
/* 3373 */         beginStringOrChar(4); this.sb.append('\'');
/*      */       case 143:
/* 3375 */         break;
/*      */       case 47:
/* 3377 */         return newToken(196);
/*      */       case 144:
/* 3379 */         break;
/*      */       case 41:
/* 3381 */         this.sb.append('\t');
/*      */       case 145:
/* 3383 */         break;
/*      */       case 57:
/* 3385 */         return newToken(21);
/*      */       case 146:
/* 3387 */         break;
/*      */       case 121:
/* 3389 */         return newToken(95);
/*      */       case 147:
/* 3391 */         break;
/*      */       case 94:
/* 3393 */         return newToken(101);
/*      */       case 148:
/* 3395 */         break;
/*      */       case 59:
/* 3397 */         return newToken(6);
/*      */       case 149:
/* 3399 */         break;
/*      */       case 72:
/* 3401 */         this.sb.append((char)Integer.parseInt(yytext().substring(2, 6), 16));
/*      */       case 150:
/* 3403 */         break;
/*      */       case 34:
/* 3405 */         return newToken(201);
/*      */       case 151:
/* 3407 */         break;
/*      */       case 77:
/* 3409 */         return newToken(90);
/*      */       case 152:
/* 3411 */         break;
/*      */       case 118:
/* 3413 */         return newToken(36);
/*      */       case 153:
/* 3415 */         break;
/*      */       case 48:
/* 3417 */         return newToken(26);
/*      */       case 154:
/* 3419 */         break;
/*      */       case 2:
/* 3421 */         return newToken(198);
/*      */       case 155:
/* 3423 */         break;
/*      */       case 11:
/* 3425 */         return newToken(47);
/*      */       case 156:
/* 3427 */         break;
/*      */       case 26:
/* 3429 */         return newToken(187);
/*      */       case 157:
/* 3431 */         break;
/*      */       case 87:
/* 3433 */         return newToken(78);
/*      */       case 158:
/* 3435 */         break;
/*      */       case 1:
/* 3437 */         return invalidToken("Invalid text");
/*      */       case 159:
/* 3439 */         break;
/*      */       case 79:
/* 3441 */         return newToken(85);
/*      */       case 160:
/* 3443 */         break;
/*      */       case 110:
/* 3445 */         return newToken(89);
/*      */       case 161:
/* 3447 */         break;
/*      */       case 25:
/* 3449 */         return newToken(34);
/*      */       case 162:
/* 3451 */         break;
/*      */       case 90:
/* 3453 */         return newToken(38);
/*      */       case 163:
/* 3455 */         break;
/*      */       case 17:
/* 3457 */         return newToken(29);
/*      */       case 164:
/* 3459 */         break;
/*      */       case 27:
/* 3461 */         return newToken(204);
/*      */       case 165:
/* 3463 */         break;
/*      */       case 71:
/* 3465 */         return newToken(195);
/*      */       case 166:
/* 3467 */         break;
/*      */       case 12:
/* 3469 */         return newToken(30);
/*      */       case 167:
/* 3471 */         break;
/*      */       case 70:
/* 3473 */         return newToken(210);
/*      */       case 168:
/* 3475 */         break;
/*      */       case 84:
/* 3477 */         return newToken(23);
/*      */       case 169:
/* 3479 */         break;
/*      */       case 68:
/* 3481 */         return newToken(24);
/*      */       case 170:
/* 3483 */         break;
/*      */       case 29:
/* 3485 */         return newToken(190);
/*      */       case 171:
/* 3487 */         break;
/*      */       case 115:
/* 3489 */         return newToken(203);
/*      */       case 172:
/* 3491 */         break;
/*      */       case 35:
/* 3493 */         this.sb.append(yytext());
/* 3494 */         setStringOrCharError("Invalid escape sequence " + yytext());
/*      */       case 173:
/* 3496 */         break;
/*      */       case 132:
/* 3498 */         return newToken(97);
/*      */       case 174:
/* 3500 */         break;
/*      */       case 101:
/* 3502 */         return newToken(83);
/*      */       case 175:
/* 3504 */         break;
/*      */       case 38:
/* 3506 */         this.sb.append(yytext());
/* 3507 */         setStringOrCharError("Invalid \\u sequence. \\u must be followed by 4 hex digits");
/*      */       case 176:
/* 3509 */         break;
/*      */       case 89:
/* 3511 */         return newToken(57);
/*      */       case 177:
/* 3513 */         break;
/*      */       case 19:
/* 3515 */         this.sb.append('"'); return endStringOrChar(208);
/*      */       case 178:
/* 3517 */         break;
/*      */       case 129:
/* 3519 */         return newToken(99);
/*      */       case 179:
/* 3521 */         break;
/*      */       case 52:
/* 3523 */         return newToken(84);
/*      */       case 180:
/* 3525 */         break;
/*      */       case 83:
/* 3527 */         return newToken(50);
/*      */       case 181:
/* 3529 */         break;
/*      */       case 31:
/* 3531 */         return newToken(33);
/*      */       case 182:
/* 3533 */         break;
/*      */       case 9:
/* 3535 */         return newToken(183, true);
/*      */       case 183:
/* 3537 */         break;
/*      */       case 78:
/* 3539 */         return newToken(66);
/*      */       case 184:
/* 3541 */         break;
/*      */       case 28:
/* 3543 */         return newToken(35);
/*      */       case 185:
/* 3545 */         break;
/*      */       case 82:
/* 3547 */         return newToken(211);
/*      */       case 186:
/* 3549 */         break;
/*      */       case 65:
/* 3551 */         return newToken(74);
/*      */       case 187:
/* 3553 */         break;
/*      */       case 6:
/* 3555 */         return newToken(214, true);
/*      */       case 188:
/* 3557 */         break;
/*      */       case 13:
/* 3559 */         return newToken(31);
/*      */       case 189:
/* 3561 */         break;
/*      */       case 67:
/* 3563 */         return newToken(27);
/*      */       case 190:
/* 3565 */         break;
/*      */       case 104:
/* 3567 */         return newToken(69);
/*      */       case 191:
/* 3569 */         break;
/*      */       case 60:
/* 3571 */         return newToken(82);
/*      */       case 192:
/* 3573 */         break;
/*      */       case 80:
/* 3575 */         return newToken(73);
/*      */       case 193:
/* 3577 */         break;
/*      */       case 62:
/* 3579 */         return newToken(45);
/*      */       case 194:
/* 3581 */         break;
/*      */       case 44:
/* 3583 */         this.sb.append('\\');
/*      */       case 195:
/* 3585 */         break;
/*      */       case 49:
/* 3587 */         return newToken(59);
/*      */       case 196:
/* 3589 */         break;
/*      */       case 16:
/* 3591 */         return newToken(193);
/*      */       case 197:
/* 3593 */         break;
/*      */       case 74:
/* 3595 */         return newToken(206);
/*      */       case 198:
/* 3597 */         break;
/*      */       case 106:
/* 3599 */         return newToken(80);
/*      */       case 199:
/* 3601 */         break;
/*      */       case 64:
/* 3603 */         return newToken(62);
/*      */       case 200:
/* 3605 */         break;
/*      */       case 127:
/* 3607 */         return newToken(92);
/*      */       case 201:
/* 3609 */         break;
/*      */       case 5:
/* 3611 */         return newToken(199);
/*      */       case 202:
/* 3613 */         break;
/*      */       case 107:
/* 3615 */         return newToken(63);
/*      */       case 203:
/* 3617 */         break;
/*      */       case 51:
/* 3619 */         return newToken(76);
/*      */       case 204:
/* 3621 */         break;
/*      */       case 42:
/* 3623 */         this.sb.append('"');
/*      */       case 205:
/* 3625 */         break;
/*      */       case 10:
/* 3627 */         return newToken(212);
/*      */       case 206:
/* 3629 */         break;
/*      */       case 108:
/* 3631 */         return newToken(77);
/*      */       case 207:
/* 3633 */         break;
/*      */       case 123:
/* 3635 */         return newToken(44);
/*      */       case 208:
/* 3637 */         break;
/*      */       case 66:
/* 3639 */         return newToken(49);
/*      */       case 209:
/* 3641 */         break;
/*      */       case 4:
/* 3643 */         return invalidToken("Invalid directive");
/*      */       case 210:
/* 3645 */         break;
/*      */       case 109:
/* 3647 */         return newToken(87);
/*      */       case 211:
/* 3649 */         break;
/*      */       case 112:
/* 3651 */         return newToken(209);
/*      */       case 212:
/* 3653 */         break;
/*      */       case 58:
/* 3655 */         return newToken(58);
/*      */       case 213:
/* 3657 */         break;
/*      */       case 91:
/* 3659 */         return newToken(39);
/*      */       case 214:
/* 3661 */         break;
/*      */       case 36:
/* 3663 */         this.sb.append('\b');
/*      */       case 215:
/* 3665 */         break;
/*      */       case 45:
/* 3667 */         this.sb.append(yytext());
/* 3668 */         setStringOrCharError("Invalid \\u sequence. \\u must be followed by exactly 4 hex digits");
/*      */       case 216:
/* 3670 */         break;
/*      */       case 61:
/* 3672 */         return newToken(184);
/*      */       case 217:
/* 3674 */         break;
/*      */       case 75:
/* 3676 */         return newToken(189);
/*      */       case 218:
/* 3678 */         break;
/*      */       case 131:
/* 3680 */         return newToken(100);
/*      */       case 219:
/* 3682 */         break;
/*      */       case 113:
/* 3684 */         return newToken(207);
/*      */       case 220:
/* 3686 */         break;
/*      */       case 93:
/* 3688 */         return newToken(202);
/*      */       case 221:
/* 3690 */         break;
/*      */       case 30:
/* 3692 */         return newToken(9);
/*      */       case 222:
/* 3694 */         break;
/*      */       case 21:
/* 3696 */         this.sb.append('\'');
/* 3697 */         if (this.sb.length() == 2)
/* 3698 */           return invalidStringOrChar("Empty character literal");
/* 3699 */         if (this.sb.length() > 3) {
/* 3700 */           return invalidStringOrChar("Character literal with multiple chars");
/*      */         }
/*      */ 
/* 3703 */         return endStringOrChar(25);
/*      */       case 223:
/* 3705 */         break;
/*      */       case 92:
/* 3707 */         return newToken(42);
/*      */       case 224:
/* 3709 */         break;
/*      */       case 95:
/* 3711 */         return newToken(70);
/*      */       case 225:
/* 3713 */         break;
/*      */       case 37:
/* 3715 */         this.sb.append('\f');
/*      */       case 226:
/* 3717 */         break;
/*      */       case 124:
/* 3719 */         return newToken(43);
/*      */       case 227:
/* 3721 */         break;
/*      */       case 98:
/* 3723 */         return newToken(7);
/*      */       case 228:
/* 3725 */         break;
/*      */       case 130:
/* 3727 */         return newToken(98);
/*      */       case 229:
/* 3729 */         break;
/*      */       case 88:
/* 3731 */         return newToken(79);
/*      */       case 230:
/* 3733 */         break;
/*      */       case 24:
/* 3735 */         return newToken(22);
/*      */       case 231:
/* 3737 */         break;
/*      */       case 56:
/* 3739 */         return newToken(191);
/*      */       case 232:
/* 3741 */         break;
/*      */       case 105:
/* 3743 */         return newToken(71);
/*      */       case 233:
/* 3745 */         break;
/*      */       case 126:
/* 3747 */         return newToken(86);
/*      */       case 234:
/* 3749 */         break;
/*      */       case 33:
/* 3751 */         return newToken(8);
/*      */       case 235:
/* 3753 */         break;
/*      */       case 55:
/* 3755 */         return newToken(64);
/*      */       case 236:
/* 3757 */         break;
/*      */       case 116:
/* 3759 */         return newToken(94);
/*      */       case 237:
/* 3761 */         break;
/*      */       case 76:
/* 3763 */         return newToken(61);
/*      */       case 238:
/* 3765 */         break;
/*      */       case 128:
/* 3767 */         return newToken(60);
/*      */       case 239:
/* 3769 */         break;
/*      */       case 86:
/* 3771 */         return newToken(46);
/*      */       case 240:
/* 3773 */         break;
/*      */       case 114:
/* 3775 */         return newToken(194);
/*      */       case 241:
/* 3777 */         break;
/*      */       case 20:
/* 3779 */         return invalidStringOrChar("Unterminated string literal");
/*      */       case 242:
/* 3781 */         break;
/*      */       case 32:
/* 3783 */         return newToken(197);
/*      */       case 243:
/* 3785 */         break;
/*      */       case 120:
/* 3787 */         return newToken(72);
/*      */       case 244:
/* 3789 */         break;
/*      */       case 46:
/* 3791 */         return newToken(51);
/*      */       case 245:
/* 3793 */         break;
/*      */       case 103:
/* 3795 */         return newToken(91);
/*      */       case 246:
/* 3797 */         break;
/*      */       case 18:
/* 3799 */         this.sb.append(yytext());
/*      */       case 247:
/* 3801 */         break;
/*      */       case 73:
/* 3803 */         return newToken(185);
/*      */       case 248:
/* 3805 */         break;
/*      */       case 81:
/* 3807 */         return newToken(81);
/*      */       case 249:
/* 3809 */         break;
/*      */       case 22:
/* 3811 */         return invalidStringOrChar("Unterminated character literal");
/*      */       case 250:
/* 3813 */         break;
/*      */       case 117:
/* 3815 */         return newToken(37);
/*      */       case 251:
/* 3817 */         break;
/*      */       case 99:
/* 3819 */         return newToken(5);
/*      */       case 252:
/* 3821 */         break;
/*      */       case 15:
/* 3823 */         return newToken(28);
/*      */       case 253:
/* 3825 */         break;
/*      */       case 125:
/* 3827 */         return newToken(41);
/*      */       case 254:
/* 3829 */         break;
/*      */       case 69:
/* 3831 */         return newToken(186);
/*      */       case 255:
/* 3833 */         break;
/*      */       case 133:
/* 3835 */         return newToken(65);
/*      */       case 256:
/* 3837 */         break;
/*      */       case 122:
/* 3839 */         return newToken(96);
/*      */       case 257:
/* 3841 */         break;
/*      */       case 40:
/* 3843 */         this.sb.append('\n');
/*      */       case 258:
/* 3845 */         break;
/*      */       case 54:
/* 3847 */         return newToken(4);
/*      */       case 259:
/* 3849 */         break;
/*      */       case 50:
/* 3851 */         return newToken(188);
/*      */       case 260:
/* 3853 */         break;
/*      */       case 3:
/* 3855 */         return newToken(205);
/*      */       case 261:
/* 3857 */         break;
/*      */       case 7:
/* 3859 */         beginStringOrChar(2); this.sb.append('"');
/*      */       case 262:
/* 3861 */         break;
/*      */       case 14:
/* 3863 */         return newToken(192);
/*      */       case 263:
/* 3865 */         break;
/*      */       case 23:
/* 3867 */         return newToken(52);
/*      */       case 264:
/* 3869 */         break;
/*      */       case 53:
/* 3871 */         return newToken(67);
/*      */       case 265:
/* 3873 */         break;
/*      */       case 63:
/* 3875 */         return newToken(88);
/*      */       case 266:
/* 3877 */         break;
/*      */       default:
/* 3879 */         if ((zzInput == -1) && (this.zzStartRead == this.zzCurrentPos))
/* 3880 */           this.zzAtEOF = true;
/* 3881 */         switch (this.zzLexicalState) {
/*      */         case 2:
/* 3883 */           return invalidStringOrChar("Unterminated string literal");
/*      */         case 1154:
/* 3885 */           break;
/*      */         case 0:
/* 3887 */           return newToken(-1);
/*      */         case 1155:
/* 3889 */           break;
/*      */         case 4:
/* 3891 */           return invalidStringOrChar("Unterminated character literal");
/*      */         case 1156:
/* 3893 */           break;
/*      */         default:
/* 3895 */           return null;
/*      */ 
/* 3899 */           zzScanError(1);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.smali.smaliFlexLexer
 * JD-Core Version:    0.6.0
 */