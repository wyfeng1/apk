/*     */ package org.antlr.runtime;
/*     */ 
/*     */ public class BitSet
/*     */   implements Cloneable
/*     */ {
/*     */   protected long[] bits;
/*     */ 
/*     */   public BitSet()
/*     */   {
/*  52 */     this(64);
/*     */   }
/*     */ 
/*     */   public BitSet(long[] bits_)
/*     */   {
/*  57 */     this.bits = bits_;
/*     */   }
/*     */ 
/*     */   public BitSet(int nbits)
/*     */   {
/*  73 */     this.bits = new long[(nbits - 1 >> 6) + 1];
/*     */   }
/*     */ 
/*     */   public BitSet or(BitSet a)
/*     */   {
/* 108 */     if (a == null) {
/* 109 */       return this;
/*     */     }
/* 111 */     BitSet s = (BitSet)clone();
/* 112 */     s.orInPlace(a);
/* 113 */     return s;
/*     */   }
/*     */ 
/*     */   public void orInPlace(BitSet a)
/*     */   {
/* 137 */     if (a == null) {
/* 138 */       return;
/*     */     }
/*     */ 
/* 141 */     if (a.bits.length > this.bits.length) {
/* 142 */       setSize(a.bits.length);
/*     */     }
/* 144 */     int min = Math.min(this.bits.length, a.bits.length);
/* 145 */     for (int i = min - 1; i >= 0; i--)
/* 146 */       this.bits[i] |= a.bits[i];
/*     */   }
/*     */ 
/*     */   private void setSize(int nwords)
/*     */   {
/* 155 */     long[] newbits = new long[nwords];
/* 156 */     int n = Math.min(nwords, this.bits.length);
/* 157 */     System.arraycopy(this.bits, 0, newbits, 0, n);
/* 158 */     this.bits = newbits;
/*     */   }
/*     */ 
/*     */   private static final long bitMask(int bitNumber) {
/* 162 */     int bitPosition = bitNumber & 0x3F;
/* 163 */     return 1L << bitPosition;
/*     */   }
/*     */ 
/*     */   public Object clone() {
/*     */     BitSet s;
/*     */     try {
/* 170 */       s = (BitSet)super.clone();
/* 171 */       s.bits = new long[this.bits.length];
/* 172 */       System.arraycopy(this.bits, 0, s.bits, 0, this.bits.length);
/*     */     }
/*     */     catch (CloneNotSupportedException e) {
/* 175 */       throw new InternalError();
/*     */     }
/* 177 */     return s;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object other)
/*     */   {
/* 197 */     if ((other == null) || (!(other instanceof BitSet))) {
/* 198 */       return false;
/*     */     }
/*     */ 
/* 201 */     BitSet otherSet = (BitSet)other;
/*     */ 
/* 203 */     int n = Math.min(this.bits.length, otherSet.bits.length);
/*     */ 
/* 206 */     for (int i = 0; i < n; i++) {
/* 207 */       if (this.bits[i] != otherSet.bits[i]) {
/* 208 */         return false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 214 */     if (this.bits.length > n) {
/* 215 */       for (int i = n + 1; i < this.bits.length; i++) {
/* 216 */         if (this.bits[i] != 0L) {
/* 217 */           return false;
/*     */         }
/*     */       }
/*     */     }
/* 221 */     else if (otherSet.bits.length > n) {
/* 222 */       for (int i = n + 1; i < otherSet.bits.length; i++) {
/* 223 */         if (otherSet.bits[i] != 0L) {
/* 224 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 229 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean member(int el) {
/* 233 */     if (el < 0) {
/* 234 */       return false;
/*     */     }
/* 236 */     int n = wordNumber(el);
/* 237 */     if (n >= this.bits.length) return false;
/* 238 */     return (this.bits[n] & bitMask(el)) != 0L;
/*     */   }
/*     */ 
/*     */   public void remove(int el)
/*     */   {
/* 243 */     int n = wordNumber(el);
/* 244 */     if (n < this.bits.length)
/* 245 */       this.bits[n] &= (bitMask(el) ^ 0xFFFFFFFF);
/*     */   }
/*     */ 
/*     */   private static final int wordNumber(int bit)
/*     */   {
/* 295 */     return bit >> 6;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 300 */     return toString(null);
/*     */   }
/*     */ 
/*     */   public String toString(String[] tokenNames) {
/* 304 */     StringBuilder buf = new StringBuilder();
/* 305 */     String separator = ",";
/* 306 */     boolean havePrintedAnElement = false;
/* 307 */     buf.append('{');
/*     */ 
/* 309 */     for (int i = 0; i < this.bits.length << 6; i++) {
/* 310 */       if (member(i)) {
/* 311 */         if ((i > 0) && (havePrintedAnElement)) {
/* 312 */           buf.append(separator);
/*     */         }
/* 314 */         if (tokenNames != null) {
/* 315 */           buf.append(tokenNames[i]);
/*     */         }
/*     */         else {
/* 318 */           buf.append(i);
/*     */         }
/* 320 */         havePrintedAnElement = true;
/*     */       }
/*     */     }
/* 323 */     buf.append('}');
/* 324 */     return buf.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.BitSet
 * JD-Core Version:    0.6.0
 */