/*    */ package org.jf.smali;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.primitives.Ints;
/*    */ import java.util.Comparator;
/*    */ import java.util.Set;
/*    */ import org.jf.dexlib2.base.BaseMethodParameter;
/*    */ import org.jf.dexlib2.iface.Annotation;
/*    */ 
/*    */ public class SmaliMethodParameter extends BaseMethodParameter
/*    */   implements WithRegister
/*    */ {
/*    */   public final int register;
/*    */   public final String type;
/*    */   public Set<? extends Annotation> annotations;
/*    */   public String name;
/* 62 */   public static final Comparator<WithRegister> COMPARATOR = new Comparator() {
/*    */     public int compare(WithRegister o1, WithRegister o2) {
/* 64 */       return Ints.compare(o1.getRegister(), o2.getRegister());
/*    */     }
/* 62 */   };
/*    */ 
/*    */   public SmaliMethodParameter(int register, String type)
/*    */   {
/* 51 */     this.register = register;
/* 52 */     this.type = type;
/* 53 */     this.annotations = ImmutableSet.of();
/*    */   }
/*    */   public int getRegister() {
/* 56 */     return this.register; } 
/* 57 */   public String getType() { return this.type; } 
/* 58 */   public Set<? extends Annotation> getAnnotations() { return this.annotations; } 
/* 59 */   public String getName() { return this.name;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.smali.SmaliMethodParameter
 * JD-Core Version:    0.6.0
 */