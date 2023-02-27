/*    */ package org.jf.dexlib2.immutable.value;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Collection;
/*    */ import org.jf.dexlib2.base.value.BaseAnnotationEncodedValue;
/*    */ import org.jf.dexlib2.iface.AnnotationElement;
/*    */ import org.jf.dexlib2.iface.value.AnnotationEncodedValue;
/*    */ import org.jf.dexlib2.immutable.ImmutableAnnotationElement;
/*    */ 
/*    */ public class ImmutableAnnotationEncodedValue extends BaseAnnotationEncodedValue
/*    */   implements ImmutableEncodedValue
/*    */ {
/*    */   protected final String type;
/*    */   protected final ImmutableSet<? extends ImmutableAnnotationElement> elements;
/*    */ 
/*    */   public ImmutableAnnotationEncodedValue(String type, Collection<? extends AnnotationElement> elements)
/*    */   {
/* 51 */     this.type = type;
/* 52 */     this.elements = ImmutableAnnotationElement.immutableSetOf(elements);
/*    */   }
/*    */ 
/*    */   public static ImmutableAnnotationEncodedValue of(AnnotationEncodedValue annotationEncodedValue)
/*    */   {
/* 62 */     if ((annotationEncodedValue instanceof ImmutableAnnotationEncodedValue)) {
/* 63 */       return (ImmutableAnnotationEncodedValue)annotationEncodedValue;
/*    */     }
/* 65 */     return new ImmutableAnnotationEncodedValue(annotationEncodedValue.getType(), annotationEncodedValue.getElements());
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 70 */     return this.type; } 
/* 71 */   public ImmutableSet<? extends ImmutableAnnotationElement> getElements() { return this.elements;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.value.ImmutableAnnotationEncodedValue
 * JD-Core Version:    0.6.0
 */