/*    */ package org.jf.dexlib2.writer.builder;
/*    */ 
/*    */ import java.util.AbstractCollection;
/*    */ import java.util.Collection;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map.Entry;
/*    */ 
/*    */ public abstract class BuilderMapEntryCollection<Key> extends AbstractCollection<Map.Entry<Key, Integer>>
/*    */ {
/*    */   private final Collection<Key> keys;
/*    */ 
/*    */   public BuilderMapEntryCollection(Collection<Key> keys)
/*    */   {
/* 44 */     this.keys = keys;
/*    */   }
/*    */ 
/*    */   public Iterator<Map.Entry<Key, Integer>> iterator()
/*    */   {
/* 64 */     Iterator iter = this.keys.iterator();
/*    */ 
/* 66 */     return new Iterator(iter) {
/*    */       public boolean hasNext() {
/* 68 */         return this.val$iter.hasNext();
/*    */       }
/*    */ 
/*    */       public Map.Entry<Key, Integer> next() {
/* 72 */         BuilderMapEntryCollection.MapEntry entry = new BuilderMapEntryCollection.MapEntry(BuilderMapEntryCollection.this, null);
/* 73 */         BuilderMapEntryCollection.MapEntry.access$102(entry, this.val$iter.next());
/* 74 */         return entry;
/*    */       }
/*    */ 
/*    */       public void remove() {
/* 78 */         throw new UnsupportedOperationException();
/*    */       } } ;
/*    */   }
/*    */ 
/*    */   public int size() {
/* 84 */     return this.keys.size();
/*    */   }
/*    */ 
/*    */   protected abstract int getValue(Key paramKey);
/*    */ 
/*    */   protected abstract int setValue(Key paramKey, int paramInt);
/*    */ 
/*    */   private class MapEntry
/*    */     implements Map.Entry<Key, Integer>
/*    */   {
/*    */     private Key key;
/*    */ 
/*    */     private MapEntry()
/*    */     {
/*    */     }
/*    */ 
/*    */     public Key getKey()
/*    */     {
/* 51 */       return this.key;
/*    */     }
/*    */ 
/*    */     public Integer getValue() {
/* 55 */       return Integer.valueOf(BuilderMapEntryCollection.this.getValue(this.key));
/*    */     }
/*    */ 
/*    */     public Integer setValue(Integer value) {
/* 59 */       return Integer.valueOf(BuilderMapEntryCollection.this.setValue(this.key, value.intValue()));
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderMapEntryCollection
 * JD-Core Version:    0.6.0
 */