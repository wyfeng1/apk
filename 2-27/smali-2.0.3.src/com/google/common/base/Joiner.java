/*     */ package com.google.common.base;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class Joiner
/*     */ {
/*     */   private final String separator;
/*     */ 
/*     */   public static Joiner on(String separator)
/*     */   {
/*  71 */     return new Joiner(separator);
/*     */   }
/*     */ 
/*     */   private Joiner(String separator)
/*     */   {
/*  84 */     this.separator = ((String)Preconditions.checkNotNull(separator));
/*     */   }
/*     */ 
/*     */   private Joiner(Joiner prototype) {
/*  88 */     this.separator = prototype.separator;
/*     */   }
/*     */ 
/*     */   public <A extends Appendable> A appendTo(A appendable, Iterator<?> parts)
/*     */     throws IOException
/*     */   {
/* 122 */     Preconditions.checkNotNull(appendable);
/* 123 */     if (parts.hasNext()) {
/* 124 */       appendable.append(toString(parts.next()));
/* 125 */       while (parts.hasNext()) {
/* 126 */         appendable.append(this.separator);
/* 127 */         appendable.append(toString(parts.next()));
/*     */       }
/*     */     }
/* 130 */     return appendable;
/*     */   }
/*     */ 
/*     */   public final StringBuilder appendTo(StringBuilder builder, Iterable<?> parts)
/*     */   {
/* 172 */     return appendTo(builder, parts.iterator());
/*     */   }
/*     */ 
/*     */   public final StringBuilder appendTo(StringBuilder builder, Iterator<?> parts)
/*     */   {
/*     */     try
/*     */     {
/* 184 */       appendTo(builder, parts);
/*     */     } catch (IOException impossible) {
/* 186 */       throw new AssertionError(impossible);
/*     */     }
/* 188 */     return builder;
/*     */   }
/*     */ 
/*     */   public Joiner useForNull(String nullText)
/*     */   {
/* 265 */     Preconditions.checkNotNull(nullText);
/* 266 */     return new Joiner(this, nullText) {
/*     */       CharSequence toString(Object part) {
/* 268 */         return part == null ? this.val$nullText : Joiner.this.toString(part);
/*     */       }
/*     */ 
/*     */       public Joiner useForNull(String nullText) {
/* 272 */         Preconditions.checkNotNull(nullText);
/* 273 */         throw new UnsupportedOperationException("already specified useForNull");
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public MapJoiner withKeyValueSeparator(String keyValueSeparator)
/*     */   {
/* 328 */     return new MapJoiner(this, keyValueSeparator, null);
/*     */   }
/*     */ 
/*     */   CharSequence toString(Object part)
/*     */   {
/* 538 */     Preconditions.checkNotNull(part);
/* 539 */     return (part instanceof CharSequence) ? (CharSequence)part : part.toString();
/*     */   }
/*     */ 
/*     */   public static final class MapJoiner
/*     */   {
/*     */     private final Joiner joiner;
/*     */     private final String keyValueSeparator;
/*     */ 
/*     */     private MapJoiner(Joiner joiner, String keyValueSeparator)
/*     */     {
/* 354 */       this.joiner = joiner;
/* 355 */       this.keyValueSeparator = ((String)Preconditions.checkNotNull(keyValueSeparator));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     com.google.common.base.Joiner
 * JD-Core Version:    0.6.0
 */