/*    */ package org.jf.dexlib2.analysis;
/*    */ 
/*    */ import org.jf.dexlib2.iface.reference.FieldReference;
/*    */ import org.jf.dexlib2.iface.reference.MethodReference;
/*    */ import org.jf.util.ExceptionWithContext;
/*    */ 
/*    */ public class PrimitiveProto
/*    */   implements TypeProto
/*    */ {
/*    */   protected final ClassPath classPath;
/*    */   protected final String type;
/*    */ 
/*    */   public PrimitiveProto(ClassPath classPath, String type)
/*    */   {
/* 46 */     this.classPath = classPath;
/* 47 */     this.type = type;
/*    */   }
/*    */   public String toString() {
/* 50 */     return this.type; } 
/* 51 */   public ClassPath getClassPath() { return this.classPath; } 
/* 52 */   public String getType() { return this.type; } 
/* 53 */   public boolean isInterface() { return false; } 
/*    */   public String getSuperclass() {
/* 55 */     return null;
/*    */   }
/* 57 */   public TypeProto getCommonSuperclass(TypeProto other) { throw new ExceptionWithContext("Cannot call getCommonSuperclass on PrimitiveProto", new Object[0]);
/*    */   }
/*    */ 
/*    */   public FieldReference getFieldByOffset(int fieldOffset)
/*    */   {
/* 63 */     return null;
/*    */   }
/*    */ 
/*    */   public MethodReference getMethodByVtableIndex(int vtableIndex)
/*    */   {
/* 69 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.analysis.PrimitiveProto
 * JD-Core Version:    0.6.0
 */