/*    */ package org.jf.dexlib2.base.reference;
/*    */ 
/*    */ import org.jf.dexlib2.iface.reference.TypeReference;
/*    */ 
/*    */ public abstract class BaseTypeReference
/*    */   implements TypeReference
/*    */ {
/*    */   public int hashCode()
/*    */   {
/* 41 */     return getType().hashCode();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 46 */     if (o != null) {
/* 47 */       if ((o instanceof TypeReference)) {
/* 48 */         return getType().equals(((TypeReference)o).getType());
/*    */       }
/* 50 */       if ((o instanceof CharSequence)) {
/* 51 */         return getType().equals(o.toString());
/*    */       }
/*    */     }
/* 54 */     return false;
/*    */   }
/*    */ 
/*    */   public int compareTo(CharSequence o)
/*    */   {
/* 59 */     return getType().compareTo(o.toString());
/*    */   }
/*    */   public int length() {
/* 62 */     return getType().length(); } 
/* 63 */   public char charAt(int index) { return getType().charAt(index); } 
/* 64 */   public CharSequence subSequence(int start, int end) { return getType().subSequence(start, end); } 
/* 65 */   public String toString() { return getType();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.reference.BaseTypeReference
 * JD-Core Version:    0.6.0
 */