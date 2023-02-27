/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ 
/*     */ public abstract class GenericMapMaker<K0, V0>
/*     */ {
/*     */   MapMaker.RemovalListener<K0, V0> removalListener;
/*     */ 
/*     */   <K extends K0, V extends V0> MapMaker.RemovalListener<K, V> getRemovalListener()
/*     */   {
/* 116 */     return (MapMaker.RemovalListener)Objects.firstNonNull(this.removalListener, NullListener.INSTANCE);
/*     */   }
/*     */ 
/*     */   static enum NullListener
/*     */     implements MapMaker.RemovalListener<Object, Object>
/*     */   {
/*  45 */     INSTANCE;
/*     */ 
/*     */     public void onRemoval(MapMaker.RemovalNotification<Object, Object> notification)
/*     */     {
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.GenericMapMaker
 * JD-Core Version:    0.6.0
 */