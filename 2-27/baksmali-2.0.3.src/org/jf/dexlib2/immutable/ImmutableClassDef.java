/*     */ package org.jf.dexlib2.immutable;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import com.google.common.collect.ImmutableSortedSet;
/*     */ import com.google.common.collect.Iterators;
/*     */ import java.util.AbstractCollection;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import org.jf.dexlib2.base.reference.BaseTypeReference;
/*     */ import org.jf.dexlib2.iface.Annotation;
/*     */ import org.jf.dexlib2.iface.ClassDef;
/*     */ import org.jf.dexlib2.iface.Field;
/*     */ import org.jf.dexlib2.iface.Method;
/*     */ import org.jf.util.ImmutableConverter;
/*     */ 
/*     */ public class ImmutableClassDef extends BaseTypeReference
/*     */   implements ClassDef
/*     */ {
/*     */   protected final String type;
/*     */   protected final int accessFlags;
/*     */   protected final String superclass;
/*     */   protected final ImmutableSet<String> interfaces;
/*     */   protected final String sourceFile;
/*     */   protected final ImmutableSet<? extends ImmutableAnnotation> annotations;
/*     */   protected final ImmutableSortedSet<? extends ImmutableField> staticFields;
/*     */   protected final ImmutableSortedSet<? extends ImmutableField> instanceFields;
/*     */   protected final ImmutableSortedSet<? extends ImmutableMethod> directMethods;
/*     */   protected final ImmutableSortedSet<? extends ImmutableMethod> virtualMethods;
/* 199 */   private static final ImmutableConverter<ImmutableClassDef, ClassDef> CONVERTER = new ImmutableConverter()
/*     */   {
/*     */     protected boolean isImmutable(ClassDef item)
/*     */     {
/* 203 */       return item instanceof ImmutableClassDef;
/*     */     }
/*     */ 
/*     */     protected ImmutableClassDef makeImmutable(ClassDef item)
/*     */     {
/* 209 */       return ImmutableClassDef.of(item);
/*     */     }
/* 199 */   };
/*     */ 
/*     */   public ImmutableClassDef(String type, int accessFlags, String superclass, Collection<String> interfaces, String sourceFile, Collection<? extends Annotation> annotations, Iterable<? extends Field> staticFields, Iterable<? extends Field> instanceFields, Iterable<? extends Method> directMethods, Iterable<? extends Method> virtualMethods)
/*     */   {
/* 100 */     this.type = type;
/* 101 */     this.accessFlags = accessFlags;
/* 102 */     this.superclass = superclass;
/* 103 */     this.interfaces = (interfaces == null ? ImmutableSet.of() : ImmutableSet.copyOf(interfaces));
/* 104 */     this.sourceFile = sourceFile;
/* 105 */     this.annotations = ImmutableAnnotation.immutableSetOf(annotations);
/* 106 */     this.staticFields = ImmutableField.immutableSetOf(staticFields);
/* 107 */     this.instanceFields = ImmutableField.immutableSetOf(instanceFields);
/* 108 */     this.directMethods = ImmutableMethod.immutableSetOf(directMethods);
/* 109 */     this.virtualMethods = ImmutableMethod.immutableSetOf(virtualMethods);
/*     */   }
/*     */ 
/*     */   public static ImmutableClassDef of(ClassDef classDef)
/*     */   {
/* 135 */     if ((classDef instanceof ImmutableClassDef)) {
/* 136 */       return (ImmutableClassDef)classDef;
/*     */     }
/* 138 */     return new ImmutableClassDef(classDef.getType(), classDef.getAccessFlags(), classDef.getSuperclass(), classDef.getInterfaces(), classDef.getSourceFile(), classDef.getAnnotations(), classDef.getStaticFields(), classDef.getInstanceFields(), classDef.getDirectMethods(), classDef.getVirtualMethods());
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 151 */     return this.type; } 
/* 152 */   public int getAccessFlags() { return this.accessFlags; } 
/* 153 */   public String getSuperclass() { return this.superclass; } 
/* 154 */   public ImmutableSet<String> getInterfaces() { return this.interfaces; } 
/* 155 */   public String getSourceFile() { return this.sourceFile; } 
/* 156 */   public ImmutableSet<? extends ImmutableAnnotation> getAnnotations() { return this.annotations; } 
/* 157 */   public ImmutableSet<? extends ImmutableField> getStaticFields() { return this.staticFields; } 
/* 158 */   public ImmutableSet<? extends ImmutableField> getInstanceFields() { return this.instanceFields; } 
/* 159 */   public ImmutableSet<? extends ImmutableMethod> getDirectMethods() { return this.directMethods; } 
/* 160 */   public ImmutableSet<? extends ImmutableMethod> getVirtualMethods() { return this.virtualMethods;
/*     */   }
/*     */ 
/*     */   public Collection<? extends ImmutableMethod> getMethods()
/*     */   {
/* 181 */     return new AbstractCollection()
/*     */     {
/*     */       public Iterator<ImmutableMethod> iterator()
/*     */       {
/* 185 */         return Iterators.concat(ImmutableClassDef.this.directMethods.iterator(), ImmutableClassDef.this.virtualMethods.iterator());
/*     */       }
/*     */ 
/*     */       public int size() {
/* 189 */         return ImmutableClassDef.this.directMethods.size() + ImmutableClassDef.this.virtualMethods.size();
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public static ImmutableSet<ImmutableClassDef> immutableSetOf(Iterable<? extends ClassDef> iterable) {
/* 196 */     return CONVERTER.toSet(iterable);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.ImmutableClassDef
 * JD-Core Version:    0.6.0
 */