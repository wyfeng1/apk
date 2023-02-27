/*    */ package org.jf.dexlib2.immutable;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Collection;
/*    */ import org.jf.dexlib2.base.BaseAnnotation;
/*    */ import org.jf.dexlib2.iface.Annotation;
/*    */ import org.jf.dexlib2.iface.AnnotationElement;
/*    */ import org.jf.util.ImmutableConverter;
/*    */ 
/*    */ public class ImmutableAnnotation extends BaseAnnotation
/*    */ {
/*    */   protected final int visibility;
/*    */   protected final String type;
/*    */   protected final ImmutableSet<? extends ImmutableAnnotationElement> elements;
/* 85 */   private static final ImmutableConverter<ImmutableAnnotation, Annotation> CONVERTER = new ImmutableConverter()
/*    */   {
/*    */     protected boolean isImmutable(Annotation item)
/*    */     {
/* 89 */       return item instanceof ImmutableAnnotation;
/*    */     }
/*    */ 
/*    */     protected ImmutableAnnotation makeImmutable(Annotation item)
/*    */     {
/* 95 */       return ImmutableAnnotation.of(item);
/*    */     }
/* 85 */   };
/*    */ 
/*    */   public ImmutableAnnotation(int visibility, String type, Collection<? extends AnnotationElement> elements)
/*    */   {
/* 53 */     this.visibility = visibility;
/* 54 */     this.type = type;
/* 55 */     this.elements = ImmutableAnnotationElement.immutableSetOf(elements);
/*    */   }
/*    */ 
/*    */   public static ImmutableAnnotation of(Annotation annotation)
/*    */   {
/* 67 */     if ((annotation instanceof ImmutableAnnotation)) {
/* 68 */       return (ImmutableAnnotation)annotation;
/*    */     }
/* 70 */     return new ImmutableAnnotation(annotation.getVisibility(), annotation.getType(), annotation.getElements());
/*    */   }
/*    */ 
/*    */   public int getVisibility()
/*    */   {
/* 76 */     return this.visibility; } 
/* 77 */   public String getType() { return this.type; } 
/* 78 */   public ImmutableSet<? extends ImmutableAnnotationElement> getElements() { return this.elements; }
/*    */ 
/*    */   public static ImmutableSet<ImmutableAnnotation> immutableSetOf(Iterable<? extends Annotation> list)
/*    */   {
/* 82 */     return CONVERTER.toSet(list);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.ImmutableAnnotation
 * JD-Core Version:    0.6.0
 */