/*    */ package org.jf.dexlib2.base.value;
/*    */ 
/*    */ import com.google.common.primitives.Ints;
/*    */ import org.jf.dexlib2.iface.value.AnnotationEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ import org.jf.util.CollectionUtils;
/*    */ 
/*    */ public abstract class BaseAnnotationEncodedValue
/*    */   implements AnnotationEncodedValue
/*    */ {
/*    */   public int hashCode()
/*    */   {
/* 46 */     int hashCode = getType().hashCode();
/* 47 */     return hashCode * 31 + getElements().hashCode();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 52 */     if ((o instanceof AnnotationEncodedValue)) {
/* 53 */       AnnotationEncodedValue other = (AnnotationEncodedValue)o;
/* 54 */       return (getType().equals(other.getType())) && (getElements().equals(other.getElements()));
/*    */     }
/*    */ 
/* 57 */     return false;
/*    */   }
/*    */ 
/*    */   public int compareTo(EncodedValue o)
/*    */   {
/* 62 */     int res = Ints.compare(getValueType(), o.getValueType());
/* 63 */     if (res != 0) return res;
/* 64 */     AnnotationEncodedValue other = (AnnotationEncodedValue)o;
/* 65 */     res = getType().compareTo(other.getType());
/* 66 */     if (res != 0) return res;
/* 67 */     return CollectionUtils.compareAsSet(getElements(), other.getElements());
/*    */   }
/*    */ 
/*    */   public int getValueType() {
/* 71 */     return 29;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.base.value.BaseAnnotationEncodedValue
 * JD-Core Version:    0.6.0
 */