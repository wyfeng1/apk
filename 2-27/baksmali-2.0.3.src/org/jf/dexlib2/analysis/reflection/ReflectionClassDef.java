/*     */ package org.jf.dexlib2.analysis.reflection;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.Iterators;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import org.jf.dexlib2.analysis.reflection.util.ReflectionUtils;
/*     */ import org.jf.dexlib2.base.reference.BaseTypeReference;
/*     */ import org.jf.dexlib2.iface.Annotation;
/*     */ import org.jf.dexlib2.iface.ClassDef;
/*     */ 
/*     */ public class ReflectionClassDef extends BaseTypeReference
/*     */   implements ClassDef
/*     */ {
/*     */   private final Class cls;
/*     */ 
/*     */   public ReflectionClassDef(Class cls)
/*     */   {
/*  62 */     this.cls = cls;
/*     */   }
/*     */ 
/*     */   public int getAccessFlags()
/*     */   {
/*  67 */     return this.cls.getModifiers();
/*     */   }
/*     */ 
/*     */   public String getSuperclass() {
/*  71 */     if (Modifier.isInterface(this.cls.getModifiers())) {
/*  72 */       return "Ljava/lang/Object;";
/*     */     }
/*  74 */     Class superClass = this.cls.getSuperclass();
/*  75 */     if (superClass == null) {
/*  76 */       return null;
/*     */     }
/*  78 */     return ReflectionUtils.javaToDexName(superClass.getName());
/*     */   }
/*     */ 
/*     */   public Set<String> getInterfaces() {
/*  82 */     return new AbstractSet() {
/*     */       public Iterator<String> iterator() {
/*  84 */         return Iterators.transform(Iterators.forArray(ReflectionClassDef.this.cls.getInterfaces()), new Function() {
/*     */           public String apply(Class input) {
/*  86 */             if (input == null) {
/*  87 */               return null;
/*     */             }
/*  89 */             return ReflectionUtils.javaToDexName(input.getName());
/*     */           } } );
/*     */       }
/*     */ 
/*     */       public int size() {
/*  95 */         return ReflectionClassDef.this.cls.getInterfaces().length;
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   public String getSourceFile() {
/* 101 */     return null;
/*     */   }
/*     */ 
/*     */   public Set<? extends Annotation> getAnnotations() {
/* 105 */     return ImmutableSet.of();
/*     */   }
/*     */ 
/*     */   public Iterable<? extends org.jf.dexlib2.iface.Field> getStaticFields() {
/* 109 */     return new Iterable() {
/*     */       public Iterator<org.jf.dexlib2.iface.Field> iterator() {
/* 111 */         Iterator staticFields = Iterators.filter(Iterators.forArray(ReflectionClassDef.this.cls.getDeclaredFields()), new Predicate()
/*     */         {
/*     */           public boolean apply(java.lang.reflect.Field input)
/*     */           {
/* 115 */             return (input != null) && (Modifier.isStatic(input.getModifiers()));
/*     */           }
/*     */         });
/* 119 */         return Iterators.transform(staticFields, new Function()
/*     */         {
/*     */           public org.jf.dexlib2.iface.Field apply(java.lang.reflect.Field input) {
/* 122 */             return new ReflectionField(input);
/*     */           } } );
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public Iterable<? extends org.jf.dexlib2.iface.Field> getInstanceFields() {
/* 131 */     return new Iterable() {
/*     */       public Iterator<org.jf.dexlib2.iface.Field> iterator() {
/* 133 */         Iterator staticFields = Iterators.filter(Iterators.forArray(ReflectionClassDef.this.cls.getDeclaredFields()), new Predicate()
/*     */         {
/*     */           public boolean apply(java.lang.reflect.Field input)
/*     */           {
/* 137 */             return (input != null) && (!Modifier.isStatic(input.getModifiers()));
/*     */           }
/*     */         });
/* 141 */         return Iterators.transform(staticFields, new Function()
/*     */         {
/*     */           public org.jf.dexlib2.iface.Field apply(java.lang.reflect.Field input) {
/* 144 */             return new ReflectionField(input);
/*     */           }
/*     */         });
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public Iterable<? extends org.jf.dexlib2.iface.Method> getDirectMethods()
/*     */   {
/* 171 */     return new Iterable() {
/*     */       public Iterator<org.jf.dexlib2.iface.Method> iterator() {
/* 173 */         Iterator constructorIterator = Iterators.transform(Iterators.forArray(ReflectionClassDef.this.cls.getDeclaredConstructors()), new Function()
/*     */         {
/*     */           public org.jf.dexlib2.iface.Method apply(Constructor input)
/*     */           {
/* 177 */             return new ReflectionConstructor(input);
/*     */           }
/*     */         });
/* 181 */         Iterator directMethods = Iterators.filter(Iterators.forArray(ReflectionClassDef.this.cls.getDeclaredMethods()), new Predicate()
/*     */         {
/*     */           public boolean apply(java.lang.reflect.Method input)
/*     */           {
/* 185 */             return (input != null) && ((input.getModifiers() & 0xA) != 0);
/*     */           }
/*     */         });
/* 189 */         Iterator methodIterator = Iterators.transform(directMethods, new Function()
/*     */         {
/*     */           public org.jf.dexlib2.iface.Method apply(java.lang.reflect.Method input) {
/* 192 */             return new ReflectionMethod(input);
/*     */           }
/*     */         });
/* 195 */         return Iterators.concat(constructorIterator, methodIterator);
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   public Iterable<? extends org.jf.dexlib2.iface.Method> getVirtualMethods() {
/* 201 */     return new Iterable() {
/*     */       public Iterator<org.jf.dexlib2.iface.Method> iterator() {
/* 203 */         Iterator directMethods = Iterators.filter(Iterators.forArray(ReflectionClassDef.this.cls.getDeclaredMethods()), new Predicate()
/*     */         {
/*     */           public boolean apply(java.lang.reflect.Method input)
/*     */           {
/* 207 */             return (input != null) && ((input.getModifiers() & 0xA) == 0);
/*     */           }
/*     */         });
/* 211 */         return Iterators.transform(directMethods, new Function()
/*     */         {
/*     */           public org.jf.dexlib2.iface.Method apply(java.lang.reflect.Method input) {
/* 214 */             return new ReflectionMethod(input);
/*     */           } } );
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   public Set<? extends org.jf.dexlib2.iface.Method> getMethods() {
/* 222 */     return new AbstractSet() {
/*     */       public Iterator<org.jf.dexlib2.iface.Method> iterator() {
/* 224 */         Iterator constructorIterator = Iterators.transform(Iterators.forArray(ReflectionClassDef.this.cls.getDeclaredConstructors()), new Function()
/*     */         {
/*     */           public org.jf.dexlib2.iface.Method apply(Constructor input)
/*     */           {
/* 228 */             return new ReflectionConstructor(input);
/*     */           }
/*     */         });
/* 232 */         Iterator methodIterator = Iterators.transform(Iterators.forArray(ReflectionClassDef.this.cls.getDeclaredMethods()), new Function()
/*     */         {
/*     */           public org.jf.dexlib2.iface.Method apply(java.lang.reflect.Method input)
/*     */           {
/* 236 */             return new ReflectionMethod(input);
/*     */           }
/*     */         });
/* 239 */         return Iterators.concat(constructorIterator, methodIterator);
/*     */       }
/*     */ 
/*     */       public int size() {
/* 243 */         return ReflectionClassDef.this.cls.getDeclaredMethods().length + ReflectionClassDef.this.cls.getDeclaredConstructors().length;
/*     */       } } ;
/*     */   }
/*     */ 
/*     */   public String getType() {
/* 249 */     return ReflectionUtils.javaToDexName(this.cls.getName());
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.analysis.reflection.ReflectionClassDef
 * JD-Core Version:    0.6.0
 */