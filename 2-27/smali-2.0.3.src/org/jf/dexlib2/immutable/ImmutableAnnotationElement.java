/*    */ package org.jf.dexlib2.immutable;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import org.jf.dexlib2.base.BaseAnnotationElement;
/*    */ import org.jf.dexlib2.iface.AnnotationElement;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ import org.jf.dexlib2.immutable.value.ImmutableEncodedValue;
/*    */ import org.jf.dexlib2.immutable.value.ImmutableEncodedValueFactory;
/*    */ import org.jf.util.ImmutableConverter;
/*    */ 
/*    */ public class ImmutableAnnotationElement extends BaseAnnotationElement
/*    */ {
/*    */   protected final String name;
/*    */   protected final ImmutableEncodedValue value;
/* 79 */   private static final ImmutableConverter<ImmutableAnnotationElement, AnnotationElement> CONVERTER = new ImmutableConverter()
/*    */   {
/*    */     protected boolean isImmutable(AnnotationElement item)
/*    */     {
/* 83 */       return item instanceof ImmutableAnnotationElement;
/*    */     }
/*    */ 
/*    */     protected ImmutableAnnotationElement makeImmutable(AnnotationElement item)
/*    */     {
/* 89 */       return ImmutableAnnotationElement.of(item);
/*    */     }
/* 79 */   };
/*    */ 
/*    */   public ImmutableAnnotationElement(String name, EncodedValue value)
/*    */   {
/* 51 */     this.name = name;
/* 52 */     this.value = ImmutableEncodedValueFactory.of(value);
/*    */   }
/*    */ 
/*    */   public static ImmutableAnnotationElement of(AnnotationElement annotationElement)
/*    */   {
/* 62 */     if ((annotationElement instanceof ImmutableAnnotationElement)) {
/* 63 */       return (ImmutableAnnotationElement)annotationElement;
/*    */     }
/* 65 */     return new ImmutableAnnotationElement(annotationElement.getName(), annotationElement.getValue());
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 70 */     return this.name; } 
/* 71 */   public EncodedValue getValue() { return this.value;
/*    */   }
/*    */ 
/*    */   public static ImmutableSet<ImmutableAnnotationElement> immutableSetOf(Iterable<? extends AnnotationElement> list)
/*    */   {
/* 76 */     return CONVERTER.toSet(list);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.ImmutableAnnotationElement
 * JD-Core Version:    0.6.0
 */