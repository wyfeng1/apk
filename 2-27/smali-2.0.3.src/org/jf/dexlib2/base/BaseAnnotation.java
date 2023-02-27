/*    */ package org.jf.dexlib2.base;
/*    */ 
/*    */ import com.google.common.primitives.Ints;
/*    */ import java.util.Comparator;
/*    */ import org.jf.dexlib2.iface.Annotation;
/*    */ import org.jf.util.CollectionUtils;
/*    */ 
/*    */ public abstract class BaseAnnotation
/*    */   implements Annotation
/*    */ {
/* 68 */   public static final Comparator<? super Annotation> BY_TYPE = new Comparator()
/*    */   {
/*    */     public int compare(Annotation annotation1, Annotation annotation2) {
/* 71 */       return annotation1.getType().compareTo(annotation2.getType());
/*    */     }
/* 68 */   };
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 43 */     int hashCode = getVisibility();
/* 44 */     hashCode = hashCode * 31 + getType().hashCode();
/* 45 */     return hashCode * 31 + getElements().hashCode();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 50 */     if ((o instanceof Annotation)) {
/* 51 */       Annotation other = (Annotation)o;
/* 52 */       return (getVisibility() == other.getVisibility()) && (getType().equals(other.getType())) && (getElements().equals(other.getElements()));
/*    */     }
/*    */ 
/* 56 */     return false;
/*    */   }
/*    */ 
/*    */   public int compareTo(Annotation o)
/*    */   {
/* 61 */     int res = Ints.compare(getVisibility(), o.getVisibility());
/* 62 */     if (res != 0) return res;
/* 63 */     res = getType().compareTo(o.getType());
/* 64 */     if (res != 0) return res;
/* 65 */     return CollectionUtils.compareAsSet(getElements(), o.getElements());
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.BaseAnnotation
 * JD-Core Version:    0.6.0
 */