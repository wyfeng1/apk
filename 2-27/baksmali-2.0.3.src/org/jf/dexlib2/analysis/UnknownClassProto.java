/*    */ package org.jf.dexlib2.analysis;
/*    */ 
/*    */ import org.jf.dexlib2.iface.reference.FieldReference;
/*    */ import org.jf.dexlib2.iface.reference.MethodReference;
/*    */ 
/*    */ public class UnknownClassProto
/*    */   implements TypeProto
/*    */ {
/*    */   protected final ClassPath classPath;
/*    */ 
/*    */   public UnknownClassProto(ClassPath classPath)
/*    */   {
/* 44 */     this.classPath = classPath;
/*    */   }
/*    */   public String toString() {
/* 47 */     return "Ujava/lang/Object;"; } 
/* 48 */   public ClassPath getClassPath() { return this.classPath; } 
/* 49 */   public String getSuperclass() { return null; } 
/* 50 */   public boolean isInterface() { return false; }
/*    */ 
/*    */   public TypeProto getCommonSuperclass(TypeProto other)
/*    */   {
/* 54 */     if (other.getType().equals("Ljava/lang/Object;")) {
/* 55 */       return other;
/*    */     }
/* 57 */     if ((other instanceof ArrayProto))
/*    */     {
/* 60 */       return this.classPath.getClass("Ljava/lang/Object;");
/*    */     }
/* 62 */     return this;
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 67 */     return "Ujava/lang/Object;";
/*    */   }
/*    */ 
/*    */   public FieldReference getFieldByOffset(int fieldOffset)
/*    */   {
/* 73 */     return this.classPath.getClass("Ljava/lang/Object;").getFieldByOffset(fieldOffset);
/*    */   }
/*    */ 
/*    */   public MethodReference getMethodByVtableIndex(int vtableIndex)
/*    */   {
/* 79 */     return this.classPath.getClass("Ljava/lang/Object;").getMethodByVtableIndex(vtableIndex);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.analysis.UnknownClassProto
 * JD-Core Version:    0.6.0
 */