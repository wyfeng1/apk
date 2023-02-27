/*     */ package com.google.common.base;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
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
/*     */   public static Joiner on(char separator)
/*     */   {
/*  78 */     return new Joiner(String.valueOf(separator));
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
/*     */   public final String join(Iterable<?> parts)
/*     */   {
/* 230 */     return join(parts.iterator());
/*     */   }
/*     */ 
/*     */   public final String join(Iterator<?> parts)
/*     */   {
/* 240 */     return appendTo(new StringBuilder(), parts).toString();
/*     */   }
/*     */ 
/*     */   public final String join(Object[] parts)
/*     */   {
/* 248 */     return join(Arrays.asList(parts));
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
/*     */ 
/*     */     public StringBuilder appendTo(StringBuilder builder, Map<?, ?> map)
/*     */     {
/* 372 */       return appendTo(builder, map.entrySet());
/*     */     }
/*     */ 
/*     */     public <A extends Appendable> A appendTo(A appendable, Iterator<? extends Map.Entry<?, ?>> parts)
/*     */       throws IOException
/*     */     {
/* 423 */       Preconditions.checkNotNull(appendable);
/* 424 */       if (parts.hasNext()) {
/* 425 */         Map.Entry entry = (Map.Entry)parts.next();
/* 426 */         appendable.append(this.joiner.toString(entry.getKey()));
/* 427 */         appendable.append(this.keyValueSeparator);
/* 428 */         appendable.append(this.joiner.toString(entry.getValue()));
/* 429 */         while (parts.hasNext()) {
/* 430 */           appendable.append(this.joiner.separator);
/* 431 */           Map.Entry e = (Map.Entry)parts.next();
/* 432 */           appendable.append(this.joiner.toString(e.getKey()));
/* 433 */           appendable.append(this.keyValueSeparator);
/* 434 */           appendable.append(this.joiner.toString(e.getValue()));
/*     */         }
/*     */       }
/* 437 */       return appendable;
/*     */     }
/*     */ 
/*     */     public StringBuilder appendTo(StringBuilder builder, Iterable<? extends Map.Entry<?, ?>> entries)
/*     */     {
/* 467 */       return appendTo(builder, entries.iterator());
/*     */     }
/*     */ 
/*     */     public StringBuilder appendTo(StringBuilder builder, Iterator<? extends Map.Entry<?, ?>> entries)
/*     */     {
/*     */       try
/*     */       {
/* 480 */         appendTo(builder, entries);
/*     */       } catch (IOException impossible) {
/* 482 */         throw new AssertionError(impossible);
/*     */       }
/* 484 */       return builder;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.base.Joiner
 * JD-Core Version:    0.6.0
 */