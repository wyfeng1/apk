/*     */ package org.jf.util;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ public class SparseArray<E>
/*     */ {
/*  44 */   private static final Object DELETED = new Object();
/*  45 */   private boolean mGarbage = false;
/*     */   private int[] mKeys;
/*     */   private Object[] mValues;
/*     */   private int mSize;
/*     */ 
/*     */   public SparseArray()
/*     */   {
/*  51 */     this(10);
/*     */   }
/*     */ 
/*     */   public SparseArray(int initialCapacity)
/*     */   {
/*  60 */     this.mKeys = new int[initialCapacity];
/*  61 */     this.mValues = new Object[initialCapacity];
/*  62 */     this.mSize = 0;
/*     */   }
/*     */ 
/*     */   public E get(int key)
/*     */   {
/*  70 */     return get(key, null);
/*     */   }
/*     */ 
/*     */   public E get(int key, E valueIfKeyNotFound)
/*     */   {
/*  78 */     int i = binarySearch(this.mKeys, 0, this.mSize, key);
/*     */ 
/*  80 */     if ((i < 0) || (this.mValues[i] == DELETED)) {
/*  81 */       return valueIfKeyNotFound;
/*     */     }
/*  83 */     return this.mValues[i];
/*     */   }
/*     */ 
/*     */   private void gc()
/*     */   {
/* 111 */     int n = this.mSize;
/* 112 */     int o = 0;
/* 113 */     int[] keys = this.mKeys;
/* 114 */     Object[] values = this.mValues;
/*     */ 
/* 116 */     for (int i = 0; i < n; i++) {
/* 117 */       Object val = values[i];
/*     */ 
/* 119 */       if (val != DELETED) {
/* 120 */         if (i != o) {
/* 121 */           keys[o] = keys[i];
/* 122 */           values[o] = val;
/*     */         }
/*     */ 
/* 125 */         o++;
/*     */       }
/*     */     }
/*     */ 
/* 129 */     this.mGarbage = false;
/* 130 */     this.mSize = o;
/*     */   }
/*     */ 
/*     */   public void put(int key, E value)
/*     */   {
/* 141 */     int i = binarySearch(this.mKeys, 0, this.mSize, key);
/*     */ 
/* 143 */     if (i >= 0) {
/* 144 */       this.mValues[i] = value;
/*     */     } else {
/* 146 */       i ^= -1;
/*     */ 
/* 148 */       if ((i < this.mSize) && (this.mValues[i] == DELETED)) {
/* 149 */         this.mKeys[i] = key;
/* 150 */         this.mValues[i] = value;
/* 151 */         return;
/*     */       }
/*     */ 
/* 154 */       if ((this.mGarbage) && (this.mSize >= this.mKeys.length)) {
/* 155 */         gc();
/*     */ 
/* 158 */         i = binarySearch(this.mKeys, 0, this.mSize, key) ^ 0xFFFFFFFF;
/*     */       }
/*     */ 
/* 161 */       if (this.mSize >= this.mKeys.length) {
/* 162 */         int n = Math.max(this.mSize + 1, this.mKeys.length * 2);
/*     */ 
/* 164 */         int[] nkeys = new int[n];
/* 165 */         Object[] nvalues = new Object[n];
/*     */ 
/* 168 */         System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
/* 169 */         System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
/*     */ 
/* 171 */         this.mKeys = nkeys;
/* 172 */         this.mValues = nvalues;
/*     */       }
/*     */ 
/* 175 */       if (this.mSize - i != 0)
/*     */       {
/* 177 */         System.arraycopy(this.mKeys, i, this.mKeys, i + 1, this.mSize - i);
/* 178 */         System.arraycopy(this.mValues, i, this.mValues, i + 1, this.mSize - i);
/*     */       }
/*     */ 
/* 181 */       this.mKeys[i] = key;
/* 182 */       this.mValues[i] = value;
/* 183 */       this.mSize += 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 192 */     if (this.mGarbage) {
/* 193 */       gc();
/*     */     }
/*     */ 
/* 196 */     return this.mSize;
/*     */   }
/*     */ 
/*     */   public int keyAt(int index)
/*     */   {
/* 205 */     if (this.mGarbage) {
/* 206 */       gc();
/*     */     }
/*     */ 
/* 209 */     return this.mKeys[index];
/*     */   }
/*     */ 
/*     */   public E valueAt(int index)
/*     */   {
/* 218 */     if (this.mGarbage) {
/* 219 */       gc();
/*     */     }
/*     */ 
/* 222 */     return this.mValues[index];
/*     */   }
/*     */ 
/*     */   public int indexOfKey(int key)
/*     */   {
/* 244 */     if (this.mGarbage) {
/* 245 */       gc();
/*     */     }
/*     */ 
/* 248 */     return binarySearch(this.mKeys, 0, this.mSize, key);
/*     */   }
/*     */ 
/*     */   public void append(int key, E value)
/*     */   {
/* 291 */     if ((this.mSize != 0) && (key <= this.mKeys[(this.mSize - 1)])) {
/* 292 */       put(key, value);
/* 293 */       return;
/*     */     }
/*     */ 
/* 296 */     if ((this.mGarbage) && (this.mSize >= this.mKeys.length)) {
/* 297 */       gc();
/*     */     }
/*     */ 
/* 300 */     int pos = this.mSize;
/* 301 */     if (pos >= this.mKeys.length) {
/* 302 */       int n = Math.max(pos + 1, this.mKeys.length * 2);
/*     */ 
/* 304 */       int[] nkeys = new int[n];
/* 305 */       Object[] nvalues = new Object[n];
/*     */ 
/* 308 */       System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
/* 309 */       System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
/*     */ 
/* 311 */       this.mKeys = nkeys;
/* 312 */       this.mValues = nvalues;
/*     */     }
/*     */ 
/* 315 */     this.mKeys[pos] = key;
/* 316 */     this.mValues[pos] = value;
/* 317 */     this.mSize = (pos + 1);
/*     */   }
/*     */ 
/*     */   public void ensureCapacity(int capacity)
/*     */   {
/* 326 */     if ((this.mGarbage) && (this.mSize >= this.mKeys.length)) {
/* 327 */       gc();
/*     */     }
/*     */ 
/* 330 */     if (this.mKeys.length < capacity) {
/* 331 */       int[] nkeys = new int[capacity];
/* 332 */       Object[] nvalues = new Object[capacity];
/*     */ 
/* 334 */       System.arraycopy(this.mKeys, 0, nkeys, 0, this.mKeys.length);
/* 335 */       System.arraycopy(this.mValues, 0, nvalues, 0, this.mValues.length);
/*     */ 
/* 337 */       this.mKeys = nkeys;
/* 338 */       this.mValues = nvalues;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static int binarySearch(int[] a, int start, int len, int key) {
/* 343 */     int high = start + len; int low = start - 1;
/*     */ 
/* 345 */     while (high - low > 1) {
/* 346 */       int guess = (high + low) / 2;
/*     */ 
/* 348 */       if (a[guess] < key) {
/* 349 */         low = guess; continue;
/*     */       }
/* 351 */       high = guess;
/*     */     }
/*     */ 
/* 354 */     if (high == start + len)
/* 355 */       return start + len ^ 0xFFFFFFFF;
/* 356 */     if (a[high] == key) {
/* 357 */       return high;
/*     */     }
/* 359 */     return high ^ 0xFFFFFFFF;
/*     */   }
/*     */ 
/*     */   public List<E> getValues()
/*     */   {
/* 367 */     return Collections.unmodifiableList(Arrays.asList((Object[])this.mValues));
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.util.SparseArray
 * JD-Core Version:    0.6.0
 */