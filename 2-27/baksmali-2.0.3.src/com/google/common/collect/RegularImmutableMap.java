/*     */ package com.google.common.collect;
/*     */ 
/*     */ import com.google.common.base.Preconditions;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ final class RegularImmutableMap<K, V> extends ImmutableMap<K, V>
/*     */ {
/*     */   private final transient LinkedEntry<K, V>[] entries;
/*     */   private final transient LinkedEntry<K, V>[] table;
/*     */   private final transient int mask;
/*     */ 
/*     */   RegularImmutableMap(Map.Entry<?, ?>[] immutableEntries)
/*     */   {
/*  46 */     int size = immutableEntries.length;
/*  47 */     this.entries = createEntryArray(size);
/*     */ 
/*  49 */     int tableSize = Hashing.closedTableSize(size, 1.2D);
/*  50 */     this.table = createEntryArray(tableSize);
/*  51 */     this.mask = (tableSize - 1);
/*     */ 
/*  53 */     for (int entryIndex = 0; entryIndex < size; entryIndex++)
/*     */     {
/*  56 */       Map.Entry entry = immutableEntries[entryIndex];
/*  57 */       Object key = entry.getKey();
/*  58 */       int keyHashCode = key.hashCode();
/*  59 */       int tableIndex = Hashing.smear(keyHashCode) & this.mask;
/*  60 */       LinkedEntry existing = this.table[tableIndex];
/*     */ 
/*  62 */       LinkedEntry linkedEntry = newLinkedEntry(key, entry.getValue(), existing);
/*     */ 
/*  64 */       this.table[tableIndex] = linkedEntry;
/*  65 */       this.entries[entryIndex] = linkedEntry;
/*  66 */       while (existing != null) {
/*  67 */         Preconditions.checkArgument(!key.equals(existing.getKey()), "duplicate key: %s", new Object[] { key });
/*  68 */         existing = existing.next();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private LinkedEntry<K, V>[] createEntryArray(int size)
/*     */   {
/*  87 */     return new LinkedEntry[size];
/*     */   }
/*     */ 
/*     */   private static <K, V> LinkedEntry<K, V> newLinkedEntry(K key, V value, LinkedEntry<K, V> next)
/*     */   {
/*  92 */     return (LinkedEntry)(next == null ? new TerminalEntry(key, value) : new NonTerminalEntry(key, value, next));
/*     */   }
/*     */ 
/*     */   public V get(Object key)
/*     */   {
/* 137 */     if (key == null) {
/* 138 */       return null;
/*     */     }
/* 140 */     int index = Hashing.smear(key.hashCode()) & this.mask;
/* 141 */     LinkedEntry entry = this.table[index];
/* 142 */     while (entry != null)
/*     */     {
/* 144 */       Object candidateKey = entry.getKey();
/*     */ 
/* 152 */       if (key.equals(candidateKey))
/* 153 */         return entry.getValue();
/* 143 */       entry = entry.next();
/*     */     }
/*     */ 
/* 156 */     return null;
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 161 */     return this.entries.length;
/*     */   }
/*     */ 
/*     */   boolean isPartialView() {
/* 165 */     return false;
/*     */   }
/*     */ 
/*     */   ImmutableSet<Map.Entry<K, V>> createEntrySet()
/*     */   {
/* 170 */     return new EntrySet(null);
/*     */   }
/*     */   private class EntrySet extends ImmutableMapEntrySet<K, V> {
/*     */     private EntrySet() {
/*     */     }
/*     */     ImmutableMap<K, V> map() {
/* 176 */       return RegularImmutableMap.this;
/*     */     }
/*     */ 
/*     */     public UnmodifiableIterator<Map.Entry<K, V>> iterator()
/*     */     {
/* 181 */       return asList().iterator();
/*     */     }
/*     */ 
/*     */     ImmutableList<Map.Entry<K, V>> createAsList()
/*     */     {
/* 186 */       return new RegularImmutableAsList(this, RegularImmutableMap.this.entries);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static final class TerminalEntry<K, V> extends ImmutableEntry<K, V>
/*     */     implements RegularImmutableMap.LinkedEntry<K, V>
/*     */   {
/*     */     TerminalEntry(K key, V value)
/*     */     {
/* 128 */       super(value);
/*     */     }
/*     */ 
/*     */     public RegularImmutableMap.LinkedEntry<K, V> next() {
/* 132 */       return null;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static final class NonTerminalEntry<K, V> extends ImmutableEntry<K, V>
/*     */     implements RegularImmutableMap.LinkedEntry<K, V>
/*     */   {
/*     */     final RegularImmutableMap.LinkedEntry<K, V> next;
/*     */ 
/*     */     NonTerminalEntry(K key, V value, RegularImmutableMap.LinkedEntry<K, V> next)
/*     */     {
/* 110 */       super(value);
/* 111 */       this.next = next;
/*     */     }
/*     */ 
/*     */     public RegularImmutableMap.LinkedEntry<K, V> next() {
/* 115 */       return this.next;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static abstract interface LinkedEntry<K, V> extends Map.Entry<K, V>
/*     */   {
/*     */     public abstract LinkedEntry<K, V> next();
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     com.google.common.collect.RegularImmutableMap
 * JD-Core Version:    0.6.0
 */