/*    */ package org.jf.dexlib2.immutable.reference;
/*    */ 
/*    */ import org.jf.dexlib2.base.reference.BaseStringReference;
/*    */ import org.jf.dexlib2.iface.reference.StringReference;
/*    */ 
/*    */ public class ImmutableStringReference extends BaseStringReference
/*    */   implements ImmutableReference
/*    */ {
/*    */   protected final String str;
/*    */ 
/*    */   public ImmutableStringReference(String str)
/*    */   {
/* 43 */     this.str = str;
/*    */   }
/*    */ 
/*    */   public static ImmutableStringReference of(StringReference stringReference)
/*    */   {
/* 48 */     if ((stringReference instanceof ImmutableStringReference)) {
/* 49 */       return (ImmutableStringReference)stringReference;
/*    */     }
/* 51 */     return new ImmutableStringReference(stringReference.getString());
/*    */   }
/*    */   public String getString() {
/* 54 */     return this.str;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.reference.ImmutableStringReference
 * JD-Core Version:    0.6.0
 */