/*    */ package org.jf.dexlib2.base.reference;
/*    */ 
/*    */ import org.jf.dexlib2.iface.reference.StringReference;
/*    */ 
/*    */ public abstract class BaseStringReference
/*    */   implements StringReference
/*    */ {
/*    */   public int hashCode()
/*    */   {
/* 42 */     return getString().hashCode();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 47 */     if ((o != null) && ((o instanceof StringReference))) {
/* 48 */       return getString().equals(((StringReference)o).getString());
/*    */     }
/* 50 */     return false;
/*    */   }
/*    */ 
/*    */   public int compareTo(CharSequence o)
/*    */   {
/* 55 */     return getString().compareTo(o.toString());
/*    */   }
/*    */   public int length() {
/* 58 */     return getString().length(); } 
/* 59 */   public char charAt(int index) { return getString().charAt(index); } 
/* 60 */   public CharSequence subSequence(int start, int end) { return getString().subSequence(start, end); } 
/* 61 */   public String toString() { return getString();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.reference.BaseStringReference
 * JD-Core Version:    0.6.0
 */