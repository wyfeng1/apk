/*     */ package org.jf.dexlib2.analysis;
/*     */ 
/*     */ import com.google.common.base.Predicates;
/*     */ import com.google.common.base.Supplier;
/*     */ import com.google.common.base.Suppliers;
/*     */ import com.google.common.collect.FluentIterable;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import org.jf.dexlib2.AccessFlags;
/*     */ import org.jf.dexlib2.analysis.util.TypeProtoUtils;
/*     */ import org.jf.dexlib2.iface.ClassDef;
/*     */ import org.jf.dexlib2.iface.Field;
/*     */ import org.jf.dexlib2.iface.Method;
/*     */ import org.jf.dexlib2.iface.reference.FieldReference;
/*     */ import org.jf.dexlib2.iface.reference.MethodReference;
/*     */ import org.jf.dexlib2.immutable.ImmutableMethod;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ import org.jf.util.SparseArray;
/*     */ 
/*     */ public class ClassProto
/*     */   implements TypeProto
/*     */ {
/*     */   protected final ClassPath classPath;
/*     */   protected final String type;
/*  67 */   protected boolean vtableFullyResolved = true;
/*  68 */   protected boolean interfacesFullyResolved = true;
/*     */ 
/*  88 */   private final Supplier<ClassDef> classDefSupplier = Suppliers.memoize(new Supplier() {
/*     */     public ClassDef get() {
/*  90 */       return ClassProto.this.classPath.getClassDef(ClassProto.this.type);
/*     */     }
/*     */   });
/*     */ 
/* 125 */   private final Supplier<LinkedHashMap<String, ClassDef>> interfacesSupplier = Suppliers.memoize(new Supplier()
/*     */   {
/*     */     public LinkedHashMap<String, ClassDef> get()
/*     */     {
/* 129 */       LinkedHashMap interfaces = Maps.newLinkedHashMap();
/*     */       try
/*     */       {
/* 132 */         for (String interfaceType : ClassProto.this.getClassDef().getInterfaces())
/* 133 */           if (!interfaces.containsKey(interfaceType))
/*     */           {
/*     */             try {
/* 136 */               ClassDef interfaceDef = ClassProto.this.classPath.getClassDef(interfaceType);
/* 137 */               interfaces.put(interfaceType, interfaceDef);
/*     */             } catch (UnresolvedClassException ex) {
/* 139 */               interfaces.put(interfaceType, null);
/* 140 */               ClassProto.this.interfacesFullyResolved = false;
/*     */             }
/*     */ 
/* 143 */             ClassProto interfaceProto = (ClassProto)ClassProto.this.classPath.getClass(interfaceType);
/* 144 */             for (String superInterface : interfaceProto.getInterfaces().keySet()) {
/* 145 */               if (!interfaces.containsKey(superInterface)) {
/* 146 */                 interfaces.put(superInterface, interfaceProto.getInterfaces().get(superInterface));
/*     */               }
/*     */             }
/* 149 */             if (!interfaceProto.interfacesFullyResolved)
/* 150 */               ClassProto.this.interfacesFullyResolved = false;
/*     */           }
/*     */       }
/*     */       catch (UnresolvedClassException ex)
/*     */       {
/* 155 */         ClassProto.this.interfacesFullyResolved = false;
/*     */       }
/*     */ 
/* 161 */       if ((ClassProto.this.isInterface()) && (!interfaces.containsKey(ClassProto.this.getType()))) {
/* 162 */         interfaces.put(ClassProto.this.getType(), null);
/*     */       }
/*     */       try
/*     */       {
/* 166 */         String superclass = ClassProto.this.getSuperclass();
/* 167 */         if (superclass != null) {
/* 168 */           ClassProto superclassProto = (ClassProto)ClassProto.this.classPath.getClass(superclass);
/* 169 */           for (String superclassInterface : superclassProto.getInterfaces().keySet()) {
/* 170 */             if (!interfaces.containsKey(superclassInterface)) {
/* 171 */               interfaces.put(superclassInterface, null);
/*     */             }
/*     */           }
/* 174 */           if (!superclassProto.interfacesFullyResolved)
/* 175 */             ClassProto.this.interfacesFullyResolved = false;
/*     */         }
/*     */       }
/*     */       catch (UnresolvedClassException ex) {
/* 179 */         ClassProto.this.interfacesFullyResolved = false;
/*     */       }
/*     */ 
/* 182 */       return interfaces;
/*     */     }
/*     */   });
/*     */ 
/* 362 */   private final Supplier<SparseArray<FieldReference>> instanceFieldsSupplier = Suppliers.memoize(new Supplier()
/*     */   {
/*     */     public SparseArray<FieldReference> get()
/*     */     {
/* 369 */       byte REFERENCE = 0;
/* 370 */       byte WIDE = 1;
/* 371 */       byte OTHER = 2;
/*     */ 
/* 373 */       ArrayList fields = getSortedInstanceFields(ClassProto.this.getClassDef());
/* 374 */       int fieldCount = fields.size();
/*     */ 
/* 376 */       byte[] fieldTypes = new byte[fields.size()];
/* 377 */       for (int i = 0; i < fieldCount; i++) {
/* 378 */         fieldTypes[i] = getFieldType((FieldReference)fields.get(i));
/*     */       }
/*     */ 
/* 383 */       int back = fields.size() - 1;
/*     */ 
/* 385 */       for (int front = 0; front < fieldCount; front++) {
/* 386 */         if (fieldTypes[front] != 0) {
/* 387 */           while (back > front) {
/* 388 */             if (fieldTypes[back] == 0) {
/* 389 */               swap(fieldTypes, fields, front, back--);
/* 390 */               break;
/*     */             }
/* 392 */             back--;
/*     */           }
/*     */         }
/*     */ 
/* 396 */         if (fieldTypes[front] != 0)
/*     */         {
/*     */           break;
/*     */         }
/*     */       }
/* 401 */       int startFieldOffset = 8;
/* 402 */       String superclassType = ClassProto.this.getSuperclass();
/* 403 */       ClassProto superclass = null;
/* 404 */       if (superclassType != null) {
/* 405 */         superclass = (ClassProto)ClassProto.this.classPath.getClass(superclassType);
/* 406 */         if (superclass != null)
/* 407 */           startFieldOffset = superclass.getNextFieldOffset();
/*     */       }
/*     */       int fieldIndexMod;
/*     */       int fieldIndexMod;
/* 412 */       if (startFieldOffset % 8 == 0)
/* 413 */         fieldIndexMod = 0;
/*     */       else {
/* 415 */         fieldIndexMod = 1;
/*     */       }
/*     */ 
/* 422 */       if ((front < fieldCount) && (front % 2 != fieldIndexMod)) {
/* 423 */         if (fieldTypes[front] == 1)
/*     */         {
/* 425 */           back = fieldCount - 1;
/* 426 */           while (back > front) {
/* 427 */             if (fieldTypes[back] == 2) {
/* 428 */               swap(fieldTypes, fields, front++, back);
/* 429 */               break;
/*     */             }
/* 431 */             back--;
/*     */           }
/*     */         }
/*     */ 
/* 435 */         front++;
/*     */       }
/*     */ 
/* 440 */       back = fieldCount - 1;
/* 441 */       for (; front < fieldCount; front++) {
/* 442 */         if (fieldTypes[front] != 1) {
/* 443 */           while (back > front) {
/* 444 */             if (fieldTypes[back] == 1) {
/* 445 */               swap(fieldTypes, fields, front, back--);
/* 446 */               break;
/*     */             }
/* 448 */             back--;
/*     */           }
/*     */         }
/*     */ 
/* 452 */         if (fieldTypes[front] != 1)
/*     */           break;
/*     */       }
/*     */       SparseArray superFields;
/*     */       SparseArray superFields;
/* 458 */       if (superclass != null)
/* 459 */         superFields = superclass.getInstanceFields();
/*     */       else {
/* 461 */         superFields = new SparseArray();
/*     */       }
/* 463 */       int superFieldCount = superFields.size();
/*     */ 
/* 466 */       int totalFieldCount = superFieldCount + fieldCount;
/* 467 */       SparseArray instanceFields = new SparseArray(totalFieldCount);
/*     */       int fieldOffset;
/* 471 */       if ((superclass != null) && (superFieldCount > 0)) {
/* 472 */         for (int i = 0; i < superFieldCount; i++) {
/* 473 */           instanceFields.append(superFields.keyAt(i), superFields.valueAt(i));
/*     */         }
/*     */ 
/* 476 */         int fieldOffset = instanceFields.keyAt(superFieldCount - 1);
/*     */ 
/* 478 */         FieldReference lastSuperField = (FieldReference)superFields.valueAt(superFieldCount - 1);
/* 479 */         char fieldType = lastSuperField.getType().charAt(0);
/* 480 */         if ((fieldType == 'J') || (fieldType == 'D'))
/* 481 */           fieldOffset += 8;
/*     */         else
/* 483 */           fieldOffset += 4;
/*     */       }
/*     */       else
/*     */       {
/* 487 */         fieldOffset = 8;
/*     */       }
/*     */ 
/* 490 */       boolean gotDouble = false;
/* 491 */       for (int i = 0; i < fieldCount; i++) {
/* 492 */         FieldReference field = (FieldReference)fields.get(i);
/*     */ 
/* 495 */         if ((fieldTypes[i] == 1) && (!gotDouble) && 
/* 496 */           (!gotDouble)) {
/* 497 */           if (fieldOffset % 8 != 0) {
/* 498 */             assert (fieldOffset % 8 == 4);
/* 499 */             fieldOffset += 4;
/*     */           }
/* 501 */           gotDouble = true;
/*     */         }
/*     */ 
/* 505 */         instanceFields.append(fieldOffset, field);
/* 506 */         if (fieldTypes[i] == 1)
/* 507 */           fieldOffset += 8;
/*     */         else {
/* 509 */           fieldOffset += 4;
/*     */         }
/*     */       }
/*     */ 
/* 513 */       return instanceFields;
/*     */     }
/*     */ 
/*     */     private ArrayList<Field> getSortedInstanceFields(ClassDef classDef)
/*     */     {
/* 518 */       ArrayList fields = Lists.newArrayList(classDef.getInstanceFields());
/* 519 */       Collections.sort(fields);
/* 520 */       return fields;
/*     */     }
/*     */ 
/*     */     private byte getFieldType(FieldReference field) {
/* 524 */       switch (field.getType().charAt(0)) {
/*     */       case 'L':
/*     */       case '[':
/* 527 */         return 0;
/*     */       case 'D':
/*     */       case 'J':
/* 530 */         return 1;
/*     */       }
/* 532 */       return 2;
/*     */     }
/*     */ 
/*     */     private void swap(byte[] fieldTypes, List<Field> fields, int position1, int position2)
/*     */     {
/* 537 */       byte tempType = fieldTypes[position1];
/* 538 */       fieldTypes[position1] = fieldTypes[position2];
/* 539 */       fieldTypes[position2] = tempType;
/*     */ 
/* 541 */       Field tempField = (Field)fields.set(position1, fields.get(position2));
/* 542 */       fields.set(position2, tempField);
/*     */     }
/*     */   });
/*     */ 
/* 570 */   private final Supplier<List<Method>> vtableSupplier = Suppliers.memoize(new Supplier() {
/*     */     public List<Method> get() {
/* 572 */       List vtable = Lists.newArrayList();
/*     */       String superclassType;
/*     */       try {
/* 577 */         superclassType = ClassProto.this.getSuperclass();
/*     */       } catch (UnresolvedClassException ex) {
/* 579 */         vtable.addAll(((ClassProto)ClassProto.this.classPath.getClass("Ljava/lang/Object;")).getVtable());
/* 580 */         ClassProto.this.vtableFullyResolved = false;
/* 581 */         return vtable;
/*     */       }
/*     */ 
/* 584 */       if (superclassType != null) {
/* 585 */         ClassProto superclass = (ClassProto)ClassProto.this.classPath.getClass(superclassType);
/* 586 */         vtable.addAll(superclass.getVtable());
/*     */ 
/* 590 */         if (!superclass.vtableFullyResolved) {
/* 591 */           ClassProto.this.vtableFullyResolved = false;
/* 592 */           return vtable;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 598 */       if (!ClassProto.this.isInterface()) {
/* 599 */         addToVtable(ClassProto.this.getClassDef().getVirtualMethods(), vtable, true);
/*     */ 
/* 603 */         for (ClassDef interfaceDef : ClassProto.this.getDirectInterfaces()) {
/* 604 */           List interfaceMethods = Lists.newArrayList();
/* 605 */           for (Method interfaceMethod : interfaceDef.getVirtualMethods()) {
/* 606 */             ImmutableMethod method = new ImmutableMethod(ClassProto.this.type, interfaceMethod.getName(), interfaceMethod.getParameters(), interfaceMethod.getReturnType(), interfaceMethod.getAccessFlags(), interfaceMethod.getAnnotations(), interfaceMethod.getImplementation());
/*     */ 
/* 614 */             interfaceMethods.add(method);
/*     */           }
/* 616 */           addToVtable(interfaceMethods, vtable, false);
/*     */         }
/*     */       }
/* 619 */       return vtable;
/*     */     }
/*     */ 
/*     */     private void addToVtable(Iterable<? extends Method> localMethods, List<Method> vtable, boolean replaceExisting)
/*     */     {
/* 624 */       List methods = Lists.newArrayList(localMethods);
/* 625 */       Collections.sort(methods);
/*     */ 
/* 627 */       for (Method virtualMethod : methods) {
/* 628 */         for (int i = 0; ; i++) { if (i >= vtable.size()) break label128; Method superMethod = (Method)vtable.get(i);
/* 630 */           if ((!methodSignaturesMatch(superMethod, virtualMethod)) || (
/* 631 */             (ClassProto.this.classPath.getApi() >= 17) && (!canAccess(superMethod)))) continue;
/* 632 */           if (!replaceExisting) break;
/* 633 */           vtable.set(i, virtualMethod); break;
/*     */         }
/*     */ 
/* 640 */         vtable.add(virtualMethod);
/*     */       }
/* 642 */       label128:
/*     */     }
/*     */     private boolean methodSignaturesMatch(Method a, Method b) {
/* 645 */       return (a.getName().equals(b.getName())) && (a.getReturnType().equals(b.getReturnType())) && (a.getParameters().equals(b.getParameters()));
/*     */     }
/*     */ 
/*     */     private boolean canAccess(Method virtualMethod)
/*     */     {
/* 651 */       if (!methodIsPackagePrivate(virtualMethod.getAccessFlags())) {
/* 652 */         return true;
/*     */       }
/*     */ 
/* 655 */       String otherPackage = getPackage(virtualMethod.getDefiningClass());
/* 656 */       String ourPackage = getPackage(ClassProto.this.getClassDef().getType());
/* 657 */       return otherPackage.equals(ourPackage);
/*     */     }
/*     */ 
/*     */     private String getPackage(String classType)
/*     */     {
/* 662 */       int lastSlash = classType.lastIndexOf('/');
/* 663 */       if (lastSlash < 0) {
/* 664 */         return "";
/*     */       }
/* 666 */       return classType.substring(1, lastSlash);
/*     */     }
/*     */ 
/*     */     private boolean methodIsPackagePrivate(int accessFlags) {
/* 670 */       return (accessFlags & (AccessFlags.PRIVATE.getValue() | AccessFlags.PROTECTED.getValue() | AccessFlags.PUBLIC.getValue())) == 0;
/*     */     }
/*     */   });
/*     */ 
/*     */   public ClassProto(ClassPath classPath, String type)
/*     */   {
/*  71 */     if (type.charAt(0) != 'L') {
/*  72 */       throw new ExceptionWithContext("Cannot construct ClassProto for non reference type: %s", new Object[] { type });
/*     */     }
/*  74 */     this.classPath = classPath;
/*  75 */     this.type = type;
/*     */   }
/*     */   public String toString() {
/*  78 */     return this.type; } 
/*  79 */   public ClassPath getClassPath() { return this.classPath; } 
/*  80 */   public String getType() { return this.type; }
/*     */ 
/*     */   public ClassDef getClassDef()
/*     */   {
/*  84 */     return (ClassDef)this.classDefSupplier.get();
/*     */   }
/*     */ 
/*     */   public boolean isInterface()
/*     */   {
/* 102 */     ClassDef classDef = getClassDef();
/* 103 */     return (classDef.getAccessFlags() & AccessFlags.INTERFACE.getValue()) != 0;
/*     */   }
/*     */ 
/*     */   protected LinkedHashMap<String, ClassDef> getInterfaces()
/*     */   {
/* 122 */     return (LinkedHashMap)this.interfacesSupplier.get();
/*     */   }
/*     */ 
/*     */   protected Iterable<ClassDef> getDirectInterfaces()
/*     */   {
/* 196 */     Iterable directInterfaces = FluentIterable.from(getInterfaces().values()).filter(Predicates.notNull());
/*     */ 
/* 199 */     if (!this.interfacesFullyResolved) {
/* 200 */       throw new UnresolvedClassException("Interfaces for class %s not fully resolved", new Object[] { getType() });
/*     */     }
/*     */ 
/* 203 */     return directInterfaces;
/*     */   }
/*     */ 
/*     */   public boolean implementsInterface(String iface)
/*     */   {
/* 219 */     if (getInterfaces().containsKey(iface)) {
/* 220 */       return true;
/*     */     }
/* 222 */     if (!this.interfacesFullyResolved) {
/* 223 */       throw new UnresolvedClassException("Interfaces for class %s not fully resolved", new Object[] { getType() });
/*     */     }
/* 225 */     return false;
/*     */   }
/*     */ 
/*     */   public String getSuperclass()
/*     */   {
/* 230 */     return getClassDef().getSuperclass();
/*     */   }
/*     */ 
/*     */   private boolean checkInterface(ClassProto other)
/*     */   {
/* 249 */     boolean isResolved = true;
/* 250 */     boolean isInterface = true;
/*     */     try {
/* 252 */       isInterface = isInterface();
/*     */     } catch (UnresolvedClassException ex) {
/* 254 */       isResolved = false;
/*     */     }
/*     */ 
/* 258 */     if (isInterface) {
/*     */       try {
/* 260 */         if (other.implementsInterface(getType())) {
/* 261 */           return true;
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (UnresolvedClassException ex)
/*     */       {
/* 271 */         if (isResolved) {
/* 272 */           throw ex;
/*     */         }
/*     */       }
/*     */     }
/* 276 */     return false;
/*     */   }
/*     */ 
/*     */   public TypeProto getCommonSuperclass(TypeProto other)
/*     */   {
/* 282 */     if (!(other instanceof ClassProto)) {
/* 283 */       return other.getCommonSuperclass(this);
/*     */     }
/*     */ 
/* 286 */     if ((this == other) || (getType().equals(other.getType()))) {
/* 287 */       return this;
/*     */     }
/*     */ 
/* 290 */     if (getType().equals("Ljava/lang/Object;")) {
/* 291 */       return this;
/*     */     }
/*     */ 
/* 294 */     if (other.getType().equals("Ljava/lang/Object;")) {
/* 295 */       return other;
/*     */     }
/*     */ 
/* 298 */     boolean gotException = false;
/*     */     try {
/* 300 */       if (checkInterface((ClassProto)other))
/* 301 */         return this;
/*     */     }
/*     */     catch (UnresolvedClassException ex) {
/* 304 */       gotException = true;
/*     */     }
/*     */     try
/*     */     {
/* 308 */       if (((ClassProto)other).checkInterface(this))
/* 309 */         return other;
/*     */     }
/*     */     catch (UnresolvedClassException ex) {
/* 312 */       gotException = true;
/*     */     }
/* 314 */     if (gotException) {
/* 315 */       return this.classPath.getUnknownClass();
/*     */     }
/*     */ 
/* 318 */     List thisChain = Lists.newArrayList(new TypeProto[] { this });
/* 319 */     Iterables.addAll(thisChain, TypeProtoUtils.getSuperclassChain(this));
/*     */ 
/* 321 */     List otherChain = Lists.newArrayList(new TypeProto[] { other });
/* 322 */     Iterables.addAll(otherChain, TypeProtoUtils.getSuperclassChain(other));
/*     */ 
/* 325 */     thisChain = Lists.reverse(thisChain);
/* 326 */     otherChain = Lists.reverse(otherChain);
/*     */ 
/* 328 */     for (int i = Math.min(thisChain.size(), otherChain.size()) - 1; i >= 0; i--) {
/* 329 */       TypeProto typeProto = (TypeProto)thisChain.get(i);
/* 330 */       if (typeProto.getType().equals(((TypeProto)otherChain.get(i)).getType())) {
/* 331 */         return typeProto;
/*     */       }
/*     */     }
/*     */ 
/* 335 */     return this.classPath.getUnknownClass();
/*     */   }
/*     */ 
/*     */   public FieldReference getFieldByOffset(int fieldOffset)
/*     */   {
/* 341 */     if (getInstanceFields().size() == 0) {
/* 342 */       return null;
/*     */     }
/* 344 */     return (FieldReference)getInstanceFields().get(fieldOffset);
/*     */   }
/*     */ 
/*     */   public MethodReference getMethodByVtableIndex(int vtableIndex)
/*     */   {
/* 350 */     List vtable = getVtable();
/* 351 */     if ((vtableIndex < 0) || (vtableIndex >= vtable.size())) {
/* 352 */       return null;
/*     */     }
/*     */ 
/* 355 */     return (MethodReference)vtable.get(vtableIndex);
/*     */   }
/*     */ 
/*     */   SparseArray<FieldReference> getInstanceFields() {
/* 359 */     return (SparseArray)this.instanceFieldsSupplier.get();
/*     */   }
/*     */ 
/*     */   private int getNextFieldOffset()
/*     */   {
/* 547 */     SparseArray instanceFields = getInstanceFields();
/* 548 */     if (instanceFields.size() == 0) {
/* 549 */       return 8;
/*     */     }
/*     */ 
/* 552 */     int lastItemIndex = instanceFields.size() - 1;
/* 553 */     int fieldOffset = instanceFields.keyAt(lastItemIndex);
/* 554 */     FieldReference lastField = (FieldReference)instanceFields.valueAt(lastItemIndex);
/*     */ 
/* 556 */     switch (lastField.getType().charAt(0)) {
/*     */     case 'D':
/*     */     case 'J':
/* 559 */       return fieldOffset + 8;
/*     */     }
/* 561 */     return fieldOffset + 4;
/*     */   }
/*     */ 
/*     */   List<Method> getVtable()
/*     */   {
/* 566 */     return (List)this.vtableSupplier.get();
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.analysis.ClassProto
 * JD-Core Version:    0.6.0
 */