/*     */ package org.jf.dexlib2.analysis;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import org.jf.dexlib2.iface.reference.FieldReference;
/*     */ import org.jf.dexlib2.iface.reference.MethodReference;
/*     */ import org.jf.dexlib2.immutable.reference.ImmutableFieldReference;
/*     */ import org.jf.dexlib2.util.TypeUtils;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ public class ArrayProto
/*     */   implements TypeProto
/*     */ {
/*     */   protected final ClassPath classPath;
/*     */   protected final int dimensions;
/*     */   protected final String elementType;
/* 144 */   private static final String BRACKETS = Strings.repeat("[", 256);
/*     */ 
/*     */   public ArrayProto(ClassPath classPath, String type)
/*     */   {
/*  50 */     this.classPath = classPath;
/*  51 */     int i = 0;
/*  52 */     while (type.charAt(i) == '[') {
/*  53 */       i++;
/*  54 */       if (i == type.length()) {
/*  55 */         throw new ExceptionWithContext("Invalid array type: %s", new Object[] { type });
/*     */       }
/*     */     }
/*     */ 
/*  59 */     if (i == 0) {
/*  60 */       throw new ExceptionWithContext("Invalid array type: %s", new Object[] { type });
/*     */     }
/*     */ 
/*  63 */     this.dimensions = i;
/*  64 */     this.elementType = type.substring(i);
/*     */   }
/*     */   public String toString() {
/*  67 */     return getType(); } 
/*  68 */   public ClassPath getClassPath() { return this.classPath; } 
/*  69 */   public String getType() { return makeArrayType(this.elementType, this.dimensions); } 
/*     */   public boolean isInterface() {
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */   public String getElementType()
/*     */   {
/*  76 */     return this.elementType;
/*     */   }
/*     */ 
/*     */   public String getImmediateElementType()
/*     */   {
/*  83 */     if (this.dimensions > 1) {
/*  84 */       return makeArrayType(this.elementType, this.dimensions - 1);
/*     */     }
/*  86 */     return this.elementType;
/*     */   }
/*     */ 
/*     */   public boolean implementsInterface(String iface) {
/*  90 */     return (iface.equals("Ljava/lang/Cloneable;")) || (iface.equals("Ljava/io/Serializable;"));
/*     */   }
/*     */ 
/*     */   public String getSuperclass()
/*     */   {
/*  95 */     return "Ljava/lang/Object;";
/*     */   }
/*     */ 
/*     */   public TypeProto getCommonSuperclass(TypeProto other)
/*     */   {
/* 100 */     if ((other instanceof ArrayProto)) {
/* 101 */       if ((TypeUtils.isPrimitiveType(getElementType())) || (TypeUtils.isPrimitiveType(((ArrayProto)other).getElementType())))
/*     */       {
/* 103 */         if ((this.dimensions == ((ArrayProto)other).dimensions) && (getElementType().equals(((ArrayProto)other).getElementType())))
/*     */         {
/* 105 */           return this;
/*     */         }
/* 107 */         return this.classPath.getClass("Ljava/lang/Object;");
/*     */       }
/*     */ 
/* 110 */       if (this.dimensions == ((ArrayProto)other).dimensions) {
/* 111 */         TypeProto thisClass = this.classPath.getClass(this.elementType);
/* 112 */         TypeProto otherClass = this.classPath.getClass(((ArrayProto)other).elementType);
/* 113 */         TypeProto mergedClass = thisClass.getCommonSuperclass(otherClass);
/* 114 */         if (thisClass == mergedClass) {
/* 115 */           return this;
/*     */         }
/* 117 */         if (otherClass == mergedClass) {
/* 118 */           return other;
/*     */         }
/* 120 */         return this.classPath.getClass(makeArrayType(mergedClass.getType(), this.dimensions));
/*     */       }
/*     */ 
/* 123 */       int dimensions = Math.min(this.dimensions, ((ArrayProto)other).dimensions);
/* 124 */       return this.classPath.getClass(makeArrayType("Ljava/lang/Object;", dimensions));
/*     */     }
/*     */ 
/* 127 */     if ((other instanceof ClassProto)) {
/*     */       try {
/* 129 */         if ((other.isInterface()) && 
/* 130 */           (implementsInterface(other.getType()))) {
/* 131 */           return other;
/*     */         }
/*     */       }
/*     */       catch (UnresolvedClassException localUnresolvedClassException)
/*     */       {
/*     */       }
/* 137 */       return this.classPath.getClass("Ljava/lang/Object;");
/*     */     }
/*     */ 
/* 141 */     return other.getCommonSuperclass(this);
/*     */   }
/*     */ 
/*     */   private static String makeArrayType(String elementType, int dimensions)
/*     */   {
/* 148 */     return BRACKETS.substring(0, dimensions) + elementType;
/*     */   }
/*     */ 
/*     */   public FieldReference getFieldByOffset(int fieldOffset)
/*     */   {
/* 155 */     if (fieldOffset == 8) {
/* 156 */       return new ImmutableFieldReference(getType(), "length", "int");
/*     */     }
/* 158 */     return null;
/*     */   }
/*     */ 
/*     */   public MethodReference getMethodByVtableIndex(int vtableIndex)
/*     */   {
/* 164 */     return this.classPath.getClass("Ljava/lang/Object;").getMethodByVtableIndex(vtableIndex);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.analysis.ArrayProto
 * JD-Core Version:    0.6.0
 */