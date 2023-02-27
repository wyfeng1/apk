/*    */ package org.jf.dexlib2.immutable.reference;
/*    */ 
/*    */ import org.jf.dexlib2.base.reference.BaseTypeReference;
/*    */ import org.jf.dexlib2.iface.reference.TypeReference;
/*    */ import org.jf.util.ImmutableConverter;
/*    */ 
/*    */ public class ImmutableTypeReference extends BaseTypeReference
/*    */   implements ImmutableReference
/*    */ {
/*    */   protected final String type;
/* 65 */   private static final ImmutableConverter<ImmutableTypeReference, TypeReference> CONVERTER = new ImmutableConverter()
/*    */   {
/*    */     protected boolean isImmutable(TypeReference item)
/*    */     {
/* 69 */       return item instanceof ImmutableTypeReference;
/*    */     }
/*    */ 
/*    */     protected ImmutableTypeReference makeImmutable(TypeReference item)
/*    */     {
/* 75 */       return ImmutableTypeReference.of(item);
/*    */     }
/* 65 */   };
/*    */ 
/*    */   public ImmutableTypeReference(String type)
/*    */   {
/* 47 */     this.type = type;
/*    */   }
/*    */ 
/*    */   public static ImmutableTypeReference of(TypeReference typeReference)
/*    */   {
/* 52 */     if ((typeReference instanceof ImmutableTypeReference)) {
/* 53 */       return (ImmutableTypeReference)typeReference;
/*    */     }
/* 55 */     return new ImmutableTypeReference(typeReference.getType());
/*    */   }
/*    */   public String getType() {
/* 58 */     return this.type;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.reference.ImmutableTypeReference
 * JD-Core Version:    0.6.0
 */