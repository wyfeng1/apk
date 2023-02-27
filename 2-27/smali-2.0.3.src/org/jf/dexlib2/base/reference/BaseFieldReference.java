/*    */ package org.jf.dexlib2.base.reference;
/*    */ 
/*    */ import org.jf.dexlib2.iface.reference.FieldReference;
/*    */ 
/*    */ public abstract class BaseFieldReference
/*    */   implements FieldReference
/*    */ {
/*    */   public int hashCode()
/*    */   {
/* 42 */     int hashCode = getDefiningClass().hashCode();
/* 43 */     hashCode = hashCode * 31 + getName().hashCode();
/* 44 */     return hashCode * 31 + getType().hashCode();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 49 */     if ((o instanceof FieldReference)) {
/* 50 */       FieldReference other = (FieldReference)o;
/* 51 */       return (getDefiningClass().equals(other.getDefiningClass())) && (getName().equals(other.getName())) && (getType().equals(other.getType()));
/*    */     }
/*    */ 
/* 56 */     return false;
/*    */   }
/*    */ 
/*    */   public int compareTo(FieldReference o)
/*    */   {
/* 61 */     int res = getDefiningClass().compareTo(o.getDefiningClass());
/* 62 */     if (res != 0) return res;
/* 63 */     res = getName().compareTo(o.getName());
/* 64 */     if (res != 0) return res;
/* 65 */     return getType().compareTo(o.getType());
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.reference.BaseFieldReference
 * JD-Core Version:    0.6.0
 */