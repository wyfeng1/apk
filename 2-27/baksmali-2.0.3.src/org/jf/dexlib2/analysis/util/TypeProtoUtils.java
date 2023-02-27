/*    */ package org.jf.dexlib2.analysis.util;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.NoSuchElementException;
/*    */ import org.jf.dexlib2.analysis.ClassPath;
/*    */ import org.jf.dexlib2.analysis.TypeProto;
/*    */ import org.jf.dexlib2.analysis.UnresolvedClassException;
/*    */ 
/*    */ public class TypeProtoUtils
/*    */ {
/*    */   public static Iterable<TypeProto> getSuperclassChain(TypeProto typeProto)
/*    */   {
/* 56 */     return new Iterable(typeProto)
/*    */     {
/*    */       public Iterator<TypeProto> iterator() {
/* 59 */         return new Iterator() {
/* 60 */           private TypeProto type = TypeProtoUtils.getSuperclassAsTypeProto(TypeProtoUtils.1.this.val$typeProto);
/*    */ 
/*    */           public boolean hasNext() {
/* 63 */             return this.type != null;
/*    */           }
/*    */ 
/*    */           public TypeProto next() {
/* 67 */             TypeProto type = this.type;
/* 68 */             if (type == null) {
/* 69 */               throw new NoSuchElementException();
/*    */             }
/*    */ 
/* 72 */             this.type = TypeProtoUtils.getSuperclassAsTypeProto(type);
/* 73 */             return type;
/*    */           }
/*    */ 
/*    */           public void remove() {
/* 77 */             throw new UnsupportedOperationException();
/*    */           } } ;
/*    */       }
/*    */     };
/*    */   }
/*    */ 
/*    */   public static TypeProto getSuperclassAsTypeProto(TypeProto type) {
/*    */     try {
/* 87 */       String next = type.getSuperclass();
/* 88 */       if (next != null) {
/* 89 */         return type.getClassPath().getClass(next);
/*    */       }
/* 91 */       return null;
/*    */     } catch (UnresolvedClassException ex) {
/*    */     }
/* 94 */     return type.getClassPath().getUnknownClass();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.analysis.util.TypeProtoUtils
 * JD-Core Version:    0.6.0
 */