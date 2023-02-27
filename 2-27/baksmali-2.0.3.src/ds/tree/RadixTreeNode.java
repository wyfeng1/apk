/*     */ package ds.tree;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ class RadixTreeNode<T>
/*     */ {
/*     */   private String key;
/*     */   private List<RadixTreeNode<T>> childern;
/*     */   private boolean real;
/*     */   private T value;
/*     */ 
/*     */   public RadixTreeNode()
/*     */   {
/*  51 */     this.key = "";
/*  52 */     this.childern = new ArrayList();
/*  53 */     this.real = false;
/*     */   }
/*     */ 
/*     */   public T getValue() {
/*  57 */     return this.value;
/*     */   }
/*     */ 
/*     */   public void setValue(T data) {
/*  61 */     this.value = data;
/*     */   }
/*     */ 
/*     */   public String getKey() {
/*  65 */     return this.key;
/*     */   }
/*     */ 
/*     */   public void setKey(String value) {
/*  69 */     this.key = value;
/*     */   }
/*     */ 
/*     */   public boolean isReal() {
/*  73 */     return this.real;
/*     */   }
/*     */ 
/*     */   public void setReal(boolean datanode) {
/*  77 */     this.real = datanode;
/*     */   }
/*     */ 
/*     */   public List<RadixTreeNode<T>> getChildern() {
/*  81 */     return this.childern;
/*     */   }
/*     */ 
/*     */   public void setChildern(List<RadixTreeNode<T>> childern) {
/*  85 */     this.childern = childern;
/*     */   }
/*     */ 
/*     */   public int getNumberOfMatchingCharacters(String key) {
/*  89 */     int numberOfMatchingCharacters = 0;
/*  90 */     while ((numberOfMatchingCharacters < key.length()) && (numberOfMatchingCharacters < getKey().length()) && 
/*  91 */       (key.charAt(numberOfMatchingCharacters) == getKey().charAt(numberOfMatchingCharacters)))
/*     */     {
/*  94 */       numberOfMatchingCharacters++;
/*     */     }
/*  96 */     return numberOfMatchingCharacters;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 101 */     return this.key;
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     ds.tree.RadixTreeNode
 * JD-Core Version:    0.6.0
 */