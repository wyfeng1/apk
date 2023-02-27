/*     */ package org.jf.util;
/*     */ 
/*     */ public class SparseIntArray
/*     */ {
/*     */   private int[] mKeys;
/*     */   private int[] mValues;
/*     */   private int mSize;
/*     */ 
/*     */   public SparseIntArray()
/*     */   {
/*  44 */     this(10);
/*     */   }
/*     */ 
/*     */   public SparseIntArray(int initialCapacity)
/*     */   {
/*  53 */     this.mKeys = new int[initialCapacity];
/*  54 */     this.mValues = new int[initialCapacity];
/*  55 */     this.mSize = 0;
/*     */   }
/*     */ 
/*     */   public int get(int key, int valueIfKeyNotFound)
/*     */   {
/*  71 */     int i = binarySearch(this.mKeys, 0, this.mSize, key);
/*     */ 
/*  73 */     if (i < 0) {
/*  74 */       return valueIfKeyNotFound;
/*     */     }
/*  76 */     return this.mValues[i];
/*     */   }
/*     */ 
/*     */   public void put(int key, int value)
/*     */   {
/* 124 */     int i = binarySearch(this.mKeys, 0, this.mSize, key);
/*     */ 
/* 126 */     if (i >= 0) {
/* 127 */       this.mValues[i] = value;
/*     */     } else {
/* 129 */       i ^= -1;
/*     */ 
/* 131 */       if (this.mSize >= this.mKeys.length) {
/* 132 */         int n = Math.max(this.mSize + 1, this.mKeys.length * 2);
/*     */ 
/* 134 */         int[] nkeys = new int[n];
/* 135 */         int[] nvalues = new int[n];
/*     */ 
/* 138 */         System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
/* 139 */         System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
/*     */ 
/* 141 */         this.mKeys = nkeys;
/* 142 */         this.mValues = nvalues;
/*     */       }
/*     */ 
/* 145 */       if (this.mSize - i != 0)
/*     */       {
/* 147 */         System.arraycopy(this.mKeys, i, this.mKeys, i + 1, this.mSize - i);
/* 148 */         System.arraycopy(this.mValues, i, this.mValues, i + 1, this.mSize - i);
/*     */       }
/*     */ 
/* 151 */       this.mKeys[i] = key;
/* 152 */       this.mValues[i] = value;
/* 153 */       this.mSize += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void append(int key, int value)
/*     */   {
/* 220 */     if ((this.mSize != 0) && (key <= this.mKeys[(this.mSize - 1)])) {
/* 221 */       put(key, value);
/* 222 */       return;
/*     */     }
/*     */ 
/* 225 */     int pos = this.mSize;
/* 226 */     if (pos >= this.mKeys.length) {
/* 227 */       int n = Math.max(pos + 1, this.mKeys.length * 2);
/*     */ 
/* 229 */       int[] nkeys = new int[n];
/* 230 */       int[] nvalues = new int[n];
/*     */ 
/* 233 */       System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
/* 234 */       System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
/*     */ 
/* 236 */       this.mKeys = nkeys;
/* 237 */       this.mValues = nvalues;
/*     */     }
/*     */ 
/* 240 */     this.mKeys[pos] = key;
/* 241 */     this.mValues[pos] = value;
/* 242 */     this.mSize = (pos + 1);
/*     */   }
/*     */ 
/*     */   private static int binarySearch(int[] a, int start, int len, int key) {
/* 246 */     int high = start + len; int low = start - 1;
/*     */ 
/* 248 */     while (high - low > 1) {
/* 249 */       int guess = (high + low) / 2;
/*     */ 
/* 251 */       if (a[guess] < key) {
/* 252 */         low = guess; continue;
/*     */       }
/* 254 */       high = guess;
/*     */     }
/*     */ 
/* 257 */     if (high == start + len)
/* 258 */       return start + len ^ 0xFFFFFFFF;
/* 259 */     if (a[high] == key) {
/* 260 */       return high;
/*     */     }
/* 262 */     return high ^ 0xFFFFFFFF;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.util.SparseIntArray
 * JD-Core Version:    0.6.0
 */