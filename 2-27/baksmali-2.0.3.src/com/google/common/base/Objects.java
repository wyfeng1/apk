/*     */ package com.google.common.base;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public final class Objects
/*     */ {
/*     */   public static boolean equal(Object a, Object b)
/*     */   {
/*  55 */     return (a == b) || ((a != null) && (a.equals(b)));
/*     */   }
/*     */ 
/*     */   public static int hashCode(Object[] objects)
/*     */   {
/*  74 */     return Arrays.hashCode(objects);
/*     */   }
/*     */ 
/*     */   public static ToStringHelper toStringHelper(Object self)
/*     */   {
/* 118 */     return new ToStringHelper(simpleName(self.getClass()), null);
/*     */   }
/*     */ 
/*     */   private static String simpleName(Class<?> clazz)
/*     */   {
/* 152 */     String name = clazz.getName();
/*     */ 
/* 156 */     name = name.replaceAll("\\$[0-9]+", "\\$");
/*     */ 
/* 159 */     int start = name.lastIndexOf('$');
/*     */ 
/* 163 */     if (start == -1) {
/* 164 */       start = name.lastIndexOf('.');
/*     */     }
/* 166 */     return name.substring(start + 1);
/*     */   }
/*     */ 
/*     */   public static <T> T firstNonNull(T first, T second)
/*     */   {
/* 186 */     return first != null ? first : Preconditions.checkNotNull(second);
/*     */   }
/*     */ 
/*     */   public static final class ToStringHelper
/*     */   {
/*     */     private final String className;
/* 197 */     private ValueHolder holderHead = new ValueHolder(null);
/* 198 */     private ValueHolder holderTail = this.holderHead;
/* 199 */     private boolean omitNullValues = false;
/*     */ 
/*     */     private ToStringHelper(String className)
/*     */     {
/* 205 */       this.className = ((String)Preconditions.checkNotNull(className));
/*     */     }
/*     */ 
/*     */     public ToStringHelper add(String name, Object value)
/*     */     {
/* 227 */       return addHolder(name, value);
/*     */     }
/*     */ 
/*     */     public ToStringHelper add(String name, int value)
/*     */     {
/* 277 */       return addHolder(name, String.valueOf(value));
/*     */     }
/*     */ 
/*     */     public ToStringHelper add(String name, long value)
/*     */     {
/* 287 */       return addHolder(name, String.valueOf(value));
/*     */     }
/*     */ 
/*     */     public ToStringHelper addValue(Object value)
/*     */     {
/* 297 */       return addHolder(value);
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 384 */       boolean omitNullValuesSnapshot = this.omitNullValues;
/* 385 */       String nextSeparator = "";
/* 386 */       StringBuilder builder = new StringBuilder(32).append(this.className).append('{');
/*     */ 
/* 388 */       for (ValueHolder valueHolder = this.holderHead.next; valueHolder != null; )
/*     */       {
/* 390 */         if ((!omitNullValuesSnapshot) || (valueHolder.value != null)) {
/* 391 */           builder.append(nextSeparator);
/* 392 */           nextSeparator = ", ";
/*     */ 
/* 394 */           if (valueHolder.name != null) {
/* 395 */             builder.append(valueHolder.name).append('=');
/*     */           }
/* 397 */           builder.append(valueHolder.value);
/*     */         }
/* 389 */         valueHolder = valueHolder.next;
/*     */       }
/*     */ 
/* 400 */       return '}';
/*     */     }
/*     */ 
/*     */     private ValueHolder addHolder() {
/* 404 */       ValueHolder valueHolder = new ValueHolder(null);
/* 405 */       this.holderTail = (this.holderTail.next = valueHolder);
/* 406 */       return valueHolder;
/*     */     }
/*     */ 
/*     */     private ToStringHelper addHolder(Object value) {
/* 410 */       ValueHolder valueHolder = addHolder();
/* 411 */       valueHolder.value = value;
/* 412 */       return this;
/*     */     }
/*     */ 
/*     */     private ToStringHelper addHolder(String name, Object value) {
/* 416 */       ValueHolder valueHolder = addHolder();
/* 417 */       valueHolder.value = value;
/* 418 */       valueHolder.name = ((String)Preconditions.checkNotNull(name));
/* 419 */       return this;
/*     */     }
/*     */ 
/*     */     private static final class ValueHolder
/*     */     {
/*     */       String name;
/*     */       Object value;
/*     */       ValueHolder next;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.base.Objects
 * JD-Core Version:    0.6.0
 */