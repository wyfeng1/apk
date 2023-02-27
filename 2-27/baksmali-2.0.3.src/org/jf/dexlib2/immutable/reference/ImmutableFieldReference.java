/*    */ package org.jf.dexlib2.immutable.reference;
/*    */ 
/*    */ import org.jf.dexlib2.base.reference.BaseFieldReference;
/*    */ import org.jf.dexlib2.iface.reference.FieldReference;
/*    */ 
/*    */ public class ImmutableFieldReference extends BaseFieldReference
/*    */   implements ImmutableReference
/*    */ {
/*    */   protected final String definingClass;
/*    */   protected final String name;
/*    */   protected final String type;
/*    */ 
/*    */   public ImmutableFieldReference(String definingClass, String name, String type)
/*    */   {
/* 47 */     this.definingClass = definingClass;
/* 48 */     this.name = name;
/* 49 */     this.type = type;
/*    */   }
/*    */ 
/*    */   public static ImmutableFieldReference of(FieldReference fieldReference)
/*    */   {
/* 54 */     if ((fieldReference instanceof ImmutableFieldReference)) {
/* 55 */       return (ImmutableFieldReference)fieldReference;
/*    */     }
/* 57 */     return new ImmutableFieldReference(fieldReference.getDefiningClass(), fieldReference.getName(), fieldReference.getType());
/*    */   }
/*    */ 
/*    */   public String getDefiningClass()
/*    */   {
/* 63 */     return this.definingClass; } 
/* 64 */   public String getName() { return this.name; } 
/* 65 */   public String getType() { return this.type;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.reference.ImmutableFieldReference
 * JD-Core Version:    0.6.0
 */