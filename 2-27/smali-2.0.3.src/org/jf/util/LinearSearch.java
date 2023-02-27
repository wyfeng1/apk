/*    */ package org.jf.util;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class LinearSearch
/*    */ {
/*    */   public static <T> int linearSearch(List<? extends T> list, Comparator<T> comparator, T key, int initialGuess)
/*    */   {
/* 49 */     int guess = initialGuess;
/* 50 */     if (guess >= list.size()) {
/* 51 */       guess = list.size() - 1;
/*    */     }
/* 53 */     int comparison = comparator.compare(list.get(guess), key);
/* 54 */     if (comparison == 0) {
/* 55 */       return guess;
/*    */     }
/* 57 */     if (comparison < 0) {
/* 58 */       guess++;
/* 59 */       while (guess < list.size()) {
/* 60 */         comparison = comparator.compare(list.get(guess), key);
/* 61 */         if (comparison == 0) {
/* 62 */           return guess;
/*    */         }
/* 64 */         if (comparison > 0) {
/* 65 */           return -(guess + 1);
/*    */         }
/* 67 */         guess++;
/*    */       }
/* 69 */       return -(list.size() + 1);
/*    */     }
/* 71 */     guess--;
/* 72 */     while (guess >= 0) {
/* 73 */       comparison = comparator.compare(list.get(guess), key);
/* 74 */       if (comparison == 0) {
/* 75 */         return guess;
/*    */       }
/* 77 */       if (comparison < 0) {
/* 78 */         return -(guess + 2);
/*    */       }
/* 80 */       guess--;
/*    */     }
/* 82 */     return -1;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.util.LinearSearch
 * JD-Core Version:    0.6.0
 */