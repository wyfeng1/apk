/*    */ package org.jf.dexlib2.immutable.util;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import org.jf.dexlib2.immutable.ImmutableMethodParameter;
/*    */ 
/*    */ public class ParamUtil
/*    */ {
/*    */   private static int findTypeEnd(String str, int index)
/*    */   {
/* 41 */     char c = str.charAt(index);
/* 42 */     switch (c) {
/*    */     case 'B':
/*    */     case 'C':
/*    */     case 'D':
/*    */     case 'F':
/*    */     case 'I':
/*    */     case 'J':
/*    */     case 'S':
/*    */     case 'Z':
/* 51 */       return index + 1;
/*    */     case 'L':
/* 53 */       while (str.charAt(index++) != ';');
/* 54 */       return index;
/*    */     case '[':
/* 56 */       while (str.charAt(index++) != '[');
/* 57 */       return findTypeEnd(str, index);
/*    */     case 'E':
/*    */     case 'G':
/*    */     case 'H':
/*    */     case 'K':
/*    */     case 'M':
/*    */     case 'N':
/*    */     case 'O':
/*    */     case 'P':
/*    */     case 'Q':
/*    */     case 'R':
/*    */     case 'T':
/*    */     case 'U':
/*    */     case 'V':
/*    */     case 'W':
/*    */     case 'X':
/* 59 */     case 'Y': } throw new IllegalArgumentException(String.format("Param string \"%s\" contains invalid type prefix: %s", new Object[] { str, Character.toString(c) }));
/*    */   }
/*    */ 
/*    */   public static Iterable<ImmutableMethodParameter> parseParamString(String params)
/*    */   {
/* 66 */     return new Iterable(params) {
/*    */       public Iterator<ImmutableMethodParameter> iterator() {
/* 68 */         return new Iterator()
/*    */         {
/* 70 */           private int index = 0;
/*    */ 
/*    */           public boolean hasNext() {
/* 73 */             return this.index < ParamUtil.1.this.val$params.length();
/*    */           }
/*    */ 
/*    */           public ImmutableMethodParameter next() {
/* 77 */             int end = ParamUtil.access$000(ParamUtil.1.this.val$params, this.index);
/* 78 */             String ret = ParamUtil.1.this.val$params.substring(this.index, end);
/* 79 */             this.index = end;
/* 80 */             return new ImmutableMethodParameter(ret, null, null);
/*    */           }
/*    */ 
/*    */           public void remove() {
/* 84 */             throw new UnsupportedOperationException();
/*    */           }
/*    */         };
/*    */       }
/*    */     };
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.util.ParamUtil
 * JD-Core Version:    0.6.0
 */