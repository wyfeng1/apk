/*    */ package org.jf.dexlib2.base;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ import org.jf.dexlib2.iface.AnnotationElement;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ 
/*    */ public abstract class BaseAnnotationElement
/*    */   implements AnnotationElement
/*    */ {
/* 63 */   public static final Comparator<AnnotationElement> BY_NAME = new Comparator()
/*    */   {
/*    */     public int compare(AnnotationElement element1, AnnotationElement element2) {
/* 66 */       return element1.getName().compareTo(element2.getName());
/*    */     }
/* 63 */   };
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 42 */     int hashCode = getName().hashCode();
/* 43 */     return hashCode * 31 + getValue().hashCode();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 48 */     if ((o != null) && ((o instanceof AnnotationElement))) {
/* 49 */       AnnotationElement other = (AnnotationElement)o;
/* 50 */       return (getName().equals(other.getName())) && (getValue().equals(other.getValue()));
/*    */     }
/*    */ 
/* 53 */     return false;
/*    */   }
/*    */ 
/*    */   public int compareTo(AnnotationElement o)
/*    */   {
/* 58 */     int res = getName().compareTo(o.getName());
/* 59 */     if (res != 0) return res;
/* 60 */     return getValue().compareTo(o.getValue());
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.BaseAnnotationElement
 * JD-Core Version:    0.6.0
 */