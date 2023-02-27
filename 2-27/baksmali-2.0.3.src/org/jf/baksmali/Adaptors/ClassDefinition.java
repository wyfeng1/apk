/*     */ package org.jf.baksmali.Adaptors;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.jf.baksmali.baksmaliOptions;
/*     */ import org.jf.dexlib2.AccessFlags;
/*     */ import org.jf.dexlib2.dexbacked.DexBackedClassDef;
/*     */ import org.jf.dexlib2.dexbacked.DexBackedDexFile.InvalidItemIndex;
/*     */ import org.jf.dexlib2.iface.ClassDef;
/*     */ import org.jf.dexlib2.iface.Field;
/*     */ import org.jf.dexlib2.iface.Method;
/*     */ import org.jf.dexlib2.iface.MethodImplementation;
/*     */ import org.jf.dexlib2.iface.instruction.Instruction;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction21c;
/*     */ import org.jf.dexlib2.iface.reference.FieldReference;
/*     */ import org.jf.dexlib2.util.ReferenceUtil;
/*     */ import org.jf.util.IndentingWriter;
/*     */ import org.jf.util.StringUtils;
/*     */ 
/*     */ public class ClassDefinition
/*     */ {
/*     */   public final baksmaliOptions options;
/*     */   public final ClassDef classDef;
/*     */   private final HashSet<String> fieldsSetInStaticConstructor;
/*     */ 
/*     */   public ClassDefinition(baksmaliOptions options, ClassDef classDef)
/*     */   {
/*  56 */     this.options = options;
/*  57 */     this.classDef = classDef;
/*  58 */     this.fieldsSetInStaticConstructor = findFieldsSetInStaticConstructor();
/*     */   }
/*     */ 
/*     */   private HashSet<String> findFieldsSetInStaticConstructor()
/*     */   {
/*  67 */     HashSet fieldsSetInStaticConstructor = new HashSet();
/*     */ 
/*  69 */     for (Method method : this.classDef.getDirectMethods()) {
/*  70 */       if (method.getName().equals("<clinit>")) {
/*  71 */         MethodImplementation impl = method.getImplementation();
/*  72 */         if (impl != null) {
/*  73 */           for (Instruction instruction : impl.getInstructions()) {
/*  74 */             switch (1.$SwitchMap$org$jf$dexlib2$Opcode[instruction.getOpcode().ordinal()]) {
/*     */             case 1:
/*     */             case 2:
/*     */             case 3:
/*     */             case 4:
/*     */             case 5:
/*     */             case 6:
/*     */             case 7:
/*  82 */               Instruction21c ins = (Instruction21c)instruction;
/*  83 */               FieldReference fieldRef = null;
/*     */               try {
/*  85 */                 fieldRef = (FieldReference)ins.getReference();
/*     */               }
/*     */               catch (DexBackedDexFile.InvalidItemIndex localInvalidItemIndex)
/*     */               {
/*     */               }
/*  90 */               if ((fieldRef != null) && (fieldRef.getDefiningClass().equals(this.classDef.getType())))
/*     */               {
/*  92 */                 fieldsSetInStaticConstructor.add(ReferenceUtil.getShortFieldDescriptor(fieldRef));
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 101 */     return fieldsSetInStaticConstructor;
/*     */   }
/*     */ 
/*     */   public void writeTo(IndentingWriter writer) throws IOException {
/* 105 */     writeClass(writer);
/* 106 */     writeSuper(writer);
/* 107 */     writeSourceFile(writer);
/* 108 */     writeInterfaces(writer);
/* 109 */     writeAnnotations(writer);
/* 110 */     Set staticFields = writeStaticFields(writer);
/* 111 */     writeInstanceFields(writer, staticFields);
/* 112 */     Set directMethods = writeDirectMethods(writer);
/* 113 */     writeVirtualMethods(writer, directMethods);
/*     */   }
/*     */ 
/*     */   private void writeClass(IndentingWriter writer) throws IOException {
/* 117 */     writer.write(".class ");
/* 118 */     writeAccessFlags(writer);
/* 119 */     writer.write(this.classDef.getType());
/* 120 */     writer.write(10);
/*     */   }
/*     */ 
/*     */   private void writeAccessFlags(IndentingWriter writer) throws IOException {
/* 124 */     for (AccessFlags accessFlag : AccessFlags.getAccessFlagsForClass(this.classDef.getAccessFlags())) {
/* 125 */       writer.write(accessFlag.toString());
/* 126 */       writer.write(32);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeSuper(IndentingWriter writer) throws IOException {
/* 131 */     String superClass = this.classDef.getSuperclass();
/* 132 */     if (superClass != null) {
/* 133 */       writer.write(".super ");
/* 134 */       writer.write(superClass);
/* 135 */       writer.write(10);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeSourceFile(IndentingWriter writer) throws IOException {
/* 140 */     String sourceFile = this.classDef.getSourceFile();
/* 141 */     if (sourceFile != null) {
/* 142 */       writer.write(".source \"");
/* 143 */       StringUtils.writeEscapedString(writer, sourceFile);
/* 144 */       writer.write("\"\n");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeInterfaces(IndentingWriter writer) throws IOException {
/* 149 */     List interfaces = Lists.newArrayList(this.classDef.getInterfaces());
/* 150 */     Collections.sort(interfaces);
/*     */ 
/* 152 */     if (interfaces.size() != 0) {
/* 153 */       writer.write(10);
/* 154 */       writer.write("# interfaces\n");
/* 155 */       for (String interfaceName : interfaces) {
/* 156 */         writer.write(".implements ");
/* 157 */         writer.write(interfaceName);
/* 158 */         writer.write(10);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void writeAnnotations(IndentingWriter writer) throws IOException {
/* 164 */     Collection classAnnotations = this.classDef.getAnnotations();
/* 165 */     if (classAnnotations.size() != 0) {
/* 166 */       writer.write("\n\n");
/* 167 */       writer.write("# annotations\n");
/* 168 */       AnnotationFormatter.writeTo(writer, classAnnotations);
/*     */     }
/*     */   }
/*     */ 
/*     */   private Set<String> writeStaticFields(IndentingWriter writer) throws IOException {
/* 173 */     boolean wroteHeader = false;
/* 174 */     Set writtenFields = new HashSet();
/*     */     Iterable staticFields;
/*     */     Iterable staticFields;
/* 177 */     if ((this.classDef instanceof DexBackedClassDef))
/* 178 */       staticFields = ((DexBackedClassDef)this.classDef).getStaticFields(false);
/*     */     else {
/* 180 */       staticFields = this.classDef.getStaticFields();
/*     */     }
/*     */ 
/* 183 */     for (Field field : staticFields) {
/* 184 */       if (!wroteHeader) {
/* 185 */         writer.write("\n\n");
/* 186 */         writer.write("# static fields");
/* 187 */         wroteHeader = true;
/*     */       }
/* 189 */       writer.write(10);
/*     */ 
/* 192 */       IndentingWriter fieldWriter = writer;
/* 193 */       String fieldString = ReferenceUtil.getShortFieldDescriptor(field);
/*     */       boolean setInStaticConstructor;
/*     */       boolean setInStaticConstructor;
/* 194 */       if (!writtenFields.add(fieldString)) {
/* 195 */         writer.write("# duplicate field ignored\n");
/* 196 */         fieldWriter = new CommentingIndentingWriter(writer);
/* 197 */         System.err.println(String.format("Ignoring duplicate field: %s->%s", new Object[] { this.classDef.getType(), fieldString }));
/* 198 */         setInStaticConstructor = false;
/*     */       } else {
/* 200 */         setInStaticConstructor = this.fieldsSetInStaticConstructor.contains(fieldString);
/*     */       }
/* 202 */       FieldDefinition.writeTo(fieldWriter, field, setInStaticConstructor);
/*     */     }
/* 204 */     return writtenFields;
/*     */   }
/*     */ 
/*     */   private void writeInstanceFields(IndentingWriter writer, Set<String> staticFields) throws IOException {
/* 208 */     boolean wroteHeader = false;
/* 209 */     Set writtenFields = new HashSet();
/*     */     Iterable instanceFields;
/*     */     Iterable instanceFields;
/* 212 */     if ((this.classDef instanceof DexBackedClassDef))
/* 213 */       instanceFields = ((DexBackedClassDef)this.classDef).getInstanceFields(false);
/*     */     else {
/* 215 */       instanceFields = this.classDef.getInstanceFields();
/*     */     }
/*     */ 
/* 218 */     for (Field field : instanceFields) {
/* 219 */       if (!wroteHeader) {
/* 220 */         writer.write("\n\n");
/* 221 */         writer.write("# instance fields");
/* 222 */         wroteHeader = true;
/*     */       }
/* 224 */       writer.write(10);
/*     */ 
/* 226 */       IndentingWriter fieldWriter = writer;
/* 227 */       String fieldString = ReferenceUtil.getShortFieldDescriptor(field);
/* 228 */       if (!writtenFields.add(fieldString)) {
/* 229 */         writer.write("# duplicate field ignored\n");
/* 230 */         fieldWriter = new CommentingIndentingWriter(writer);
/* 231 */         System.err.println(String.format("Ignoring duplicate field: %s->%s", new Object[] { this.classDef.getType(), fieldString }));
/* 232 */       } else if (staticFields.contains(fieldString)) {
/* 233 */         System.err.println(String.format("Duplicate static+instance field found: %s->%s", new Object[] { this.classDef.getType(), fieldString }));
/*     */ 
/* 235 */         System.err.println("You will need to rename one of these fields, including all references.");
/*     */ 
/* 237 */         writer.write("# There is both a static and instance field with this signature.\n# You will need to rename one of these fields, including all references.\n");
/*     */       }
/*     */ 
/* 240 */       FieldDefinition.writeTo(fieldWriter, field, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   private Set<String> writeDirectMethods(IndentingWriter writer) throws IOException {
/* 245 */     boolean wroteHeader = false;
/* 246 */     Set writtenMethods = new HashSet();
/*     */     Iterable directMethods;
/*     */     Iterable directMethods;
/* 249 */     if ((this.classDef instanceof DexBackedClassDef))
/* 250 */       directMethods = ((DexBackedClassDef)this.classDef).getDirectMethods(false);
/*     */     else {
/* 252 */       directMethods = this.classDef.getDirectMethods();
/*     */     }
/*     */ 
/* 255 */     for (Method method : directMethods) {
/* 256 */       if (!wroteHeader) {
/* 257 */         writer.write("\n\n");
/* 258 */         writer.write("# direct methods");
/* 259 */         wroteHeader = true;
/*     */       }
/* 261 */       writer.write(10);
/*     */ 
/* 264 */       String methodString = ReferenceUtil.getShortMethodDescriptor(method);
/*     */ 
/* 266 */       IndentingWriter methodWriter = writer;
/* 267 */       if (!writtenMethods.add(methodString)) {
/* 268 */         writer.write("# duplicate method ignored\n");
/* 269 */         methodWriter = new CommentingIndentingWriter(writer);
/*     */       }
/*     */ 
/* 272 */       MethodImplementation methodImpl = method.getImplementation();
/* 273 */       if (methodImpl == null) {
/* 274 */         MethodDefinition.writeEmptyMethodTo(methodWriter, method, this.options);
/*     */       } else {
/* 276 */         MethodDefinition methodDefinition = new MethodDefinition(this, method, methodImpl);
/* 277 */         methodDefinition.writeTo(methodWriter);
/*     */       }
/*     */     }
/* 280 */     return writtenMethods;
/*     */   }
/*     */ 
/*     */   private void writeVirtualMethods(IndentingWriter writer, Set<String> directMethods) throws IOException {
/* 284 */     boolean wroteHeader = false;
/* 285 */     Set writtenMethods = new HashSet();
/*     */     Iterable virtualMethods;
/*     */     Iterable virtualMethods;
/* 288 */     if ((this.classDef instanceof DexBackedClassDef))
/* 289 */       virtualMethods = ((DexBackedClassDef)this.classDef).getVirtualMethods(false);
/*     */     else {
/* 291 */       virtualMethods = this.classDef.getVirtualMethods();
/*     */     }
/*     */ 
/* 294 */     for (Method method : virtualMethods) {
/* 295 */       if (!wroteHeader) {
/* 296 */         writer.write("\n\n");
/* 297 */         writer.write("# virtual methods");
/* 298 */         wroteHeader = true;
/*     */       }
/* 300 */       writer.write(10);
/*     */ 
/* 303 */       String methodString = ReferenceUtil.getShortMethodDescriptor(method);
/*     */ 
/* 305 */       IndentingWriter methodWriter = writer;
/* 306 */       if (!writtenMethods.add(methodString)) {
/* 307 */         writer.write("# duplicate method ignored\n");
/* 308 */         methodWriter = new CommentingIndentingWriter(writer);
/* 309 */       } else if (directMethods.contains(methodString)) {
/* 310 */         writer.write("# There is both a direct and virtual method with this signature.\n# You will need to rename one of these methods, including all references.\n");
/*     */ 
/* 312 */         System.err.println(String.format("Duplicate direct+virtual method found: %s->%s", new Object[] { this.classDef.getType(), methodString }));
/*     */ 
/* 314 */         System.err.println("You will need to rename one of these methods, including all references.");
/*     */       }
/*     */ 
/* 317 */       MethodImplementation methodImpl = method.getImplementation();
/* 318 */       if (methodImpl == null) {
/* 319 */         MethodDefinition.writeEmptyMethodTo(methodWriter, method, this.options);
/*     */       } else {
/* 321 */         MethodDefinition methodDefinition = new MethodDefinition(this, method, methodImpl);
/* 322 */         methodDefinition.writeTo(methodWriter);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.baksmali.Adaptors.ClassDefinition
 * JD-Core Version:    0.6.0
 */