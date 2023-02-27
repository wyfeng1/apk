/*    */ package org.jf.dexlib2.base.reference;
/*    */ 
/*    */ import com.google.common.collect.Ordering;
/*    */ import org.jf.dexlib2.iface.reference.MethodReference;
/*    */ import org.jf.util.CharSequenceUtils;
/*    */ import org.jf.util.CollectionUtils;
/*    */ 
/*    */ public abstract class BaseMethodReference
/*    */   implements MethodReference
/*    */ {
/*    */   public int hashCode()
/*    */   {
/* 45 */     int hashCode = getDefiningClass().hashCode();
/* 46 */     hashCode = hashCode * 31 + getName().hashCode();
/* 47 */     hashCode = hashCode * 31 + getReturnType().hashCode();
/* 48 */     return hashCode * 31 + getParameterTypes().hashCode();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 53 */     if ((o != null) && ((o instanceof MethodReference))) {
/* 54 */       MethodReference other = (MethodReference)o;
/* 55 */       return (getDefiningClass().equals(other.getDefiningClass())) && (getName().equals(other.getName())) && (getReturnType().equals(other.getReturnType())) && (CharSequenceUtils.listEquals(getParameterTypes(), other.getParameterTypes()));
/*    */     }
/*    */ 
/* 60 */     return false;
/*    */   }
/*    */ 
/*    */   public int compareTo(MethodReference o)
/*    */   {
/* 65 */     int res = getDefiningClass().compareTo(o.getDefiningClass());
/* 66 */     if (res != 0) return res;
/* 67 */     res = getName().compareTo(o.getName());
/* 68 */     if (res != 0) return res;
/* 69 */     res = getReturnType().compareTo(o.getReturnType());
/* 70 */     if (res != 0) return res;
/* 71 */     return CollectionUtils.compareAsIterable(Ordering.usingToString(), getParameterTypes(), o.getParameterTypes());
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.reference.BaseMethodReference
 * JD-Core Version:    0.6.0
 */