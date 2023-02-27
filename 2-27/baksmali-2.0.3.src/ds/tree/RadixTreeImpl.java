/*     */ package ds.tree;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Formattable;
/*     */ import java.util.Formatter;
/*     */ import java.util.List;
/*     */ 
/*     */ public class RadixTreeImpl<T>
/*     */   implements RadixTree<T>, Formattable
/*     */ {
/*     */   protected RadixTreeNode<T> root;
/*     */   protected long size;
/*     */ 
/*     */   public RadixTreeImpl()
/*     */   {
/*  53 */     this.root = new RadixTreeNode();
/*  54 */     this.root.setKey("");
/*  55 */     this.size = 0L;
/*     */   }
/*     */ 
/*     */   public T find(String key) {
/*  59 */     Visitor visitor = new VisitorImpl()
/*     */     {
/*     */       public void visit(String key, RadixTreeNode<T> parent, RadixTreeNode<T> node)
/*     */       {
/*  63 */         if (node.isReal())
/*  64 */           this.result = node.getValue();
/*     */       }
/*     */     };
/*  68 */     visit(key, visitor);
/*     */ 
/*  70 */     return visitor.getResult();
/*     */   }
/*     */ 
/*     */   public boolean replace(String key, T value) {
/*  74 */     Visitor visitor = new VisitorImpl(value) {
/*     */       public void visit(String key, RadixTreeNode<T> parent, RadixTreeNode<T> node) {
/*  76 */         if (node.isReal()) {
/*  77 */           node.setValue(this.val$value);
/*  78 */           this.result = this.val$value;
/*     */         } else {
/*  80 */           this.result = null;
/*     */         }
/*     */       }
/*     */     };
/*  85 */     visit(key, visitor);
/*     */ 
/*  87 */     return visitor.getResult() != null;
/*     */   }
/*     */ 
/*     */   public void insert(String key, T value)
/*     */     throws DuplicateKeyException
/*     */   {
/*     */     try
/*     */     {
/* 159 */       insert(key, this.root, value);
/*     */     }
/*     */     catch (DuplicateKeyException e) {
/* 162 */       throw new DuplicateKeyException("Duplicate key: '" + key + "'");
/*     */     }
/* 164 */     this.size += 1L;
/*     */   }
/*     */ 
/*     */   private void insert(String key, RadixTreeNode<T> node, T value)
/*     */     throws DuplicateKeyException
/*     */   {
/* 178 */     int numberOfMatchingCharacters = node.getNumberOfMatchingCharacters(key);
/*     */ 
/* 182 */     if ((node.getKey().equals("") == true) || (numberOfMatchingCharacters == 0) || ((numberOfMatchingCharacters < key.length()) && (numberOfMatchingCharacters >= node.getKey().length()))) {
/* 183 */       boolean flag = false;
/* 184 */       String newText = key.substring(numberOfMatchingCharacters, key.length());
/* 185 */       for (RadixTreeNode child : node.getChildern()) {
/* 186 */         if (child.getKey().startsWith(newText.charAt(0) + "")) {
/* 187 */           flag = true;
/* 188 */           insert(newText, child, value);
/* 189 */           break;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 194 */       if (!flag) {
/* 195 */         RadixTreeNode n = new RadixTreeNode();
/* 196 */         n.setKey(newText);
/* 197 */         n.setReal(true);
/* 198 */         n.setValue(value);
/*     */ 
/* 200 */         node.getChildern().add(n);
/*     */       }
/*     */ 
/*     */     }
/* 204 */     else if ((numberOfMatchingCharacters == key.length()) && (numberOfMatchingCharacters == node.getKey().length())) {
/* 205 */       if (node.isReal() == true) {
/* 206 */         throw new DuplicateKeyException("Duplicate key");
/*     */       }
/*     */ 
/* 209 */       node.setReal(true);
/* 210 */       node.setValue(value);
/*     */     }
/* 214 */     else if ((numberOfMatchingCharacters > 0) && (numberOfMatchingCharacters < node.getKey().length())) {
/* 215 */       RadixTreeNode n1 = new RadixTreeNode();
/* 216 */       n1.setKey(node.getKey().substring(numberOfMatchingCharacters, node.getKey().length()));
/* 217 */       n1.setReal(node.isReal());
/* 218 */       n1.setValue(node.getValue());
/* 219 */       n1.setChildern(node.getChildern());
/*     */ 
/* 221 */       node.setKey(key.substring(0, numberOfMatchingCharacters));
/* 222 */       node.setReal(false);
/* 223 */       node.setChildern(new ArrayList());
/* 224 */       node.getChildern().add(n1);
/*     */ 
/* 226 */       if (numberOfMatchingCharacters < key.length()) {
/* 227 */         RadixTreeNode n2 = new RadixTreeNode();
/* 228 */         n2.setKey(key.substring(numberOfMatchingCharacters, key.length()));
/* 229 */         n2.setReal(true);
/* 230 */         n2.setValue(value);
/*     */ 
/* 232 */         node.getChildern().add(n2);
/*     */       } else {
/* 234 */         node.setValue(value);
/* 235 */         node.setReal(true);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 240 */       RadixTreeNode n = new RadixTreeNode();
/* 241 */       n.setKey(node.getKey().substring(numberOfMatchingCharacters, node.getKey().length()));
/* 242 */       n.setChildern(node.getChildern());
/* 243 */       n.setReal(node.isReal());
/* 244 */       n.setValue(node.getValue());
/*     */ 
/* 246 */       node.setKey(key);
/* 247 */       node.setReal(true);
/* 248 */       node.setValue(value);
/*     */ 
/* 250 */       node.getChildern().add(n);
/*     */     }
/*     */   }
/*     */ 
/*     */   public <R> void visit(String key, Visitor<T, R> visitor)
/*     */   {
/* 328 */     if (this.root != null)
/* 329 */       visit(key, visitor, null, this.root);
/*     */   }
/*     */ 
/*     */   private <R> void visit(String prefix, Visitor<T, R> visitor, RadixTreeNode<T> parent, RadixTreeNode<T> node)
/*     */   {
/* 348 */     int numberOfMatchingCharacters = node.getNumberOfMatchingCharacters(prefix);
/*     */     String newText;
/* 351 */     if ((numberOfMatchingCharacters == prefix.length()) && (numberOfMatchingCharacters == node.getKey().length())) {
/* 352 */       visitor.visit(prefix, parent, node);
/* 353 */     } else if ((node.getKey().equals("") == true) || ((numberOfMatchingCharacters < prefix.length()) && (numberOfMatchingCharacters >= node.getKey().length())))
/*     */     {
/* 357 */       newText = prefix.substring(numberOfMatchingCharacters, prefix.length());
/* 358 */       for (RadixTreeNode child : node.getChildern())
/*     */       {
/* 360 */         if (child.getKey().startsWith(newText.charAt(0) + "")) {
/* 361 */           visit(newText, visitor, node, child);
/* 362 */           break;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public long getSize() {
/* 369 */     return this.size;
/*     */   }
/*     */ 
/*     */   private void formatNodeTo(Formatter f, int level, RadixTreeNode<T> node)
/*     */   {
/* 392 */     for (int i = 0; i < level; i++) {
/* 393 */       f.format(" ", new Object[0]);
/*     */     }
/* 395 */     f.format("|", new Object[0]);
/* 396 */     for (int i = 0; i < level; i++) {
/* 397 */       f.format("-", new Object[0]);
/*     */     }
/*     */ 
/* 400 */     if (node.isReal() == true)
/* 401 */       f.format("%s[%s]*%n", new Object[] { node.getKey(), node.getValue() });
/*     */     else {
/* 403 */       f.format("%s%n", new Object[] { node.getKey() });
/*     */     }
/* 405 */     for (RadixTreeNode child : node.getChildern())
/* 406 */       formatNodeTo(f, level + 1, child);
/*     */   }
/*     */ 
/*     */   public void formatTo(Formatter formatter, int flags, int width, int precision)
/*     */   {
/* 418 */     formatNodeTo(formatter, 0, this.root);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     ds.tree.RadixTreeImpl
 * JD-Core Version:    0.6.0
 */