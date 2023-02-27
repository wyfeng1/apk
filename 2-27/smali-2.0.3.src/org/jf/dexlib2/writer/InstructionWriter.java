/*     */ package org.jf.dexlib2.writer;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.jf.dexlib2.Opcode;
/*     */ import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
/*     */ import org.jf.dexlib2.iface.instruction.SwitchElement;
/*     */ import org.jf.dexlib2.iface.instruction.formats.ArrayPayload;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction10t;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction10x;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction11n;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction11x;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction12x;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction20bc;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction20t;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction21c;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction21ih;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction21lh;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction21s;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction21t;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction22b;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction22c;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction22s;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction22t;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction22x;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction23x;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction30t;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction31c;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction31i;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction31t;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction32x;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction35c;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction3rc;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction51l;
/*     */ import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload;
/*     */ import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload;
/*     */ import org.jf.dexlib2.iface.reference.FieldReference;
/*     */ import org.jf.dexlib2.iface.reference.MethodReference;
/*     */ import org.jf.dexlib2.iface.reference.StringReference;
/*     */ import org.jf.dexlib2.iface.reference.TypeReference;
/*     */ import org.jf.util.ExceptionWithContext;
/*     */ 
/*     */ public class InstructionWriter<StringRef extends StringReference, TypeRef extends TypeReference, FieldRefKey extends FieldReference, MethodRefKey extends MethodReference>
/*     */ {
/*     */   private final DexDataWriter writer;
/*     */   private final StringSection<?, StringRef> stringSection;
/*     */   private final TypeSection<?, ?, TypeRef> typeSection;
/*     */   private final FieldSection<?, ?, FieldRefKey, ?> fieldSection;
/*     */   private final MethodSection<?, ?, ?, MethodRefKey, ?> methodSection;
/*     */ 
/*     */   static <StringRef extends StringReference, TypeRef extends TypeReference, FieldRefKey extends FieldReference, MethodRefKey extends MethodReference> InstructionWriter<StringRef, TypeRef, FieldRefKey, MethodRefKey> makeInstructionWriter(DexDataWriter writer, StringSection<?, StringRef> stringSection, TypeSection<?, ?, TypeRef> typeSection, FieldSection<?, ?, FieldRefKey, ?> fieldSection, MethodSection<?, ?, ?, MethodRefKey, ?> methodSection)
/*     */   {
/*  64 */     return new InstructionWriter(writer, stringSection, typeSection, fieldSection, methodSection);
/*     */   }
/*     */ 
/*     */   InstructionWriter(DexDataWriter writer, StringSection<?, StringRef> stringSection, TypeSection<?, ?, TypeRef> typeSection, FieldSection<?, ?, FieldRefKey, ?> fieldSection, MethodSection<?, ?, ?, MethodRefKey, ?> methodSection)
/*     */   {
/*  73 */     this.writer = writer;
/*  74 */     this.stringSection = stringSection;
/*  75 */     this.typeSection = typeSection;
/*  76 */     this.fieldSection = fieldSection;
/*  77 */     this.methodSection = methodSection;
/*     */   }
/*     */ 
/*     */   public void write(Instruction10t instruction) {
/*     */     try {
/*  82 */       this.writer.write(instruction.getOpcode().value);
/*  83 */       this.writer.write(instruction.getCodeOffset());
/*     */     } catch (IOException ex) {
/*  85 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction10x instruction) {
/*     */     try {
/*  91 */       this.writer.write(instruction.getOpcode().value);
/*  92 */       this.writer.write(0);
/*     */     } catch (IOException ex) {
/*  94 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction11n instruction) {
/*     */     try {
/* 100 */       this.writer.write(instruction.getOpcode().value);
/* 101 */       this.writer.write(packNibbles(instruction.getRegisterA(), instruction.getNarrowLiteral()));
/*     */     } catch (IOException ex) {
/* 103 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction11x instruction) {
/*     */     try {
/* 109 */       this.writer.write(instruction.getOpcode().value);
/* 110 */       this.writer.write(instruction.getRegisterA());
/*     */     } catch (IOException ex) {
/* 112 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction12x instruction) {
/*     */     try {
/* 118 */       this.writer.write(instruction.getOpcode().value);
/* 119 */       this.writer.write(packNibbles(instruction.getRegisterA(), instruction.getRegisterB()));
/*     */     } catch (IOException ex) {
/* 121 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction20bc instruction) {
/*     */     try {
/* 127 */       this.writer.write(instruction.getOpcode().value);
/* 128 */       this.writer.write(instruction.getVerificationError());
/* 129 */       this.writer.writeUshort(getReferenceIndex(instruction));
/*     */     } catch (IOException ex) {
/* 131 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction20t instruction) {
/*     */     try {
/* 137 */       this.writer.write(instruction.getOpcode().value);
/* 138 */       this.writer.write(0);
/* 139 */       this.writer.writeShort(instruction.getCodeOffset());
/*     */     } catch (IOException ex) {
/* 141 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction21c instruction) {
/*     */     try {
/* 147 */       this.writer.write(instruction.getOpcode().value);
/* 148 */       this.writer.write(instruction.getRegisterA());
/* 149 */       this.writer.writeUshort(getReferenceIndex(instruction));
/*     */     } catch (IOException ex) {
/* 151 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction21ih instruction) {
/*     */     try {
/* 157 */       this.writer.write(instruction.getOpcode().value);
/* 158 */       this.writer.write(instruction.getRegisterA());
/* 159 */       this.writer.writeShort(instruction.getHatLiteral());
/*     */     } catch (IOException ex) {
/* 161 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction21lh instruction) {
/*     */     try {
/* 167 */       this.writer.write(instruction.getOpcode().value);
/* 168 */       this.writer.write(instruction.getRegisterA());
/* 169 */       this.writer.writeShort(instruction.getHatLiteral());
/*     */     } catch (IOException ex) {
/* 171 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction21s instruction) {
/*     */     try {
/* 177 */       this.writer.write(instruction.getOpcode().value);
/* 178 */       this.writer.write(instruction.getRegisterA());
/* 179 */       this.writer.writeShort(instruction.getNarrowLiteral());
/*     */     } catch (IOException ex) {
/* 181 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction21t instruction) {
/*     */     try {
/* 187 */       this.writer.write(instruction.getOpcode().value);
/* 188 */       this.writer.write(instruction.getRegisterA());
/* 189 */       this.writer.writeShort(instruction.getCodeOffset());
/*     */     } catch (IOException ex) {
/* 191 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction22b instruction) {
/*     */     try {
/* 197 */       this.writer.write(instruction.getOpcode().value);
/* 198 */       this.writer.write(instruction.getRegisterA());
/* 199 */       this.writer.write(instruction.getRegisterB());
/* 200 */       this.writer.write(instruction.getNarrowLiteral());
/*     */     } catch (IOException ex) {
/* 202 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction22c instruction) {
/*     */     try {
/* 208 */       this.writer.write(instruction.getOpcode().value);
/* 209 */       this.writer.write(packNibbles(instruction.getRegisterA(), instruction.getRegisterB()));
/* 210 */       this.writer.writeUshort(getReferenceIndex(instruction));
/*     */     } catch (IOException ex) {
/* 212 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction22s instruction) {
/*     */     try {
/* 218 */       this.writer.write(instruction.getOpcode().value);
/* 219 */       this.writer.write(packNibbles(instruction.getRegisterA(), instruction.getRegisterB()));
/* 220 */       this.writer.writeShort(instruction.getNarrowLiteral());
/*     */     } catch (IOException ex) {
/* 222 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction22t instruction) {
/*     */     try {
/* 228 */       this.writer.write(instruction.getOpcode().value);
/* 229 */       this.writer.write(packNibbles(instruction.getRegisterA(), instruction.getRegisterB()));
/* 230 */       this.writer.writeShort(instruction.getCodeOffset());
/*     */     } catch (IOException ex) {
/* 232 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction22x instruction) {
/*     */     try {
/* 238 */       this.writer.write(instruction.getOpcode().value);
/* 239 */       this.writer.write(instruction.getRegisterA());
/* 240 */       this.writer.writeUshort(instruction.getRegisterB());
/*     */     } catch (IOException ex) {
/* 242 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction23x instruction) {
/*     */     try {
/* 248 */       this.writer.write(instruction.getOpcode().value);
/* 249 */       this.writer.write(instruction.getRegisterA());
/* 250 */       this.writer.write(instruction.getRegisterB());
/* 251 */       this.writer.write(instruction.getRegisterC());
/*     */     } catch (IOException ex) {
/* 253 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction30t instruction) {
/*     */     try {
/* 259 */       this.writer.write(instruction.getOpcode().value);
/* 260 */       this.writer.write(0);
/* 261 */       this.writer.writeInt(instruction.getCodeOffset());
/*     */     } catch (IOException ex) {
/* 263 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction31c instruction) {
/*     */     try {
/* 269 */       this.writer.write(instruction.getOpcode().value);
/* 270 */       this.writer.write(instruction.getRegisterA());
/* 271 */       this.writer.writeInt(getReferenceIndex(instruction));
/*     */     } catch (IOException ex) {
/* 273 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction31i instruction) {
/*     */     try {
/* 279 */       this.writer.write(instruction.getOpcode().value);
/* 280 */       this.writer.write(instruction.getRegisterA());
/* 281 */       this.writer.writeInt(instruction.getNarrowLiteral());
/*     */     } catch (IOException ex) {
/* 283 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction31t instruction) {
/*     */     try {
/* 289 */       this.writer.write(instruction.getOpcode().value);
/* 290 */       this.writer.write(instruction.getRegisterA());
/* 291 */       this.writer.writeInt(instruction.getCodeOffset());
/*     */     } catch (IOException ex) {
/* 293 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction32x instruction) {
/*     */     try {
/* 299 */       this.writer.write(instruction.getOpcode().value);
/* 300 */       this.writer.write(0);
/* 301 */       this.writer.writeUshort(instruction.getRegisterA());
/* 302 */       this.writer.writeUshort(instruction.getRegisterB());
/*     */     } catch (IOException ex) {
/* 304 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction35c instruction) {
/*     */     try {
/* 310 */       this.writer.write(instruction.getOpcode().value);
/* 311 */       this.writer.write(packNibbles(instruction.getRegisterG(), instruction.getRegisterCount()));
/* 312 */       this.writer.writeUshort(getReferenceIndex(instruction));
/* 313 */       this.writer.write(packNibbles(instruction.getRegisterC(), instruction.getRegisterD()));
/* 314 */       this.writer.write(packNibbles(instruction.getRegisterE(), instruction.getRegisterF()));
/*     */     } catch (IOException ex) {
/* 316 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction3rc instruction) {
/*     */     try {
/* 322 */       this.writer.write(instruction.getOpcode().value);
/* 323 */       this.writer.write(instruction.getRegisterCount());
/* 324 */       this.writer.writeUshort(getReferenceIndex(instruction));
/* 325 */       this.writer.writeUshort(instruction.getStartRegister());
/*     */     } catch (IOException ex) {
/* 327 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(Instruction51l instruction) {
/*     */     try {
/* 333 */       this.writer.write(instruction.getOpcode().value);
/* 334 */       this.writer.write(instruction.getRegisterA());
/* 335 */       this.writer.writeLong(instruction.getWideLiteral());
/*     */     } catch (IOException ex) {
/* 337 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(ArrayPayload instruction) {
/*     */     try {
/* 343 */       this.writer.writeUshort(instruction.getOpcode().value);
/* 344 */       this.writer.writeUshort(instruction.getElementWidth());
/* 345 */       List elements = instruction.getArrayElements();
/* 346 */       this.writer.writeInt(elements.size());
/* 347 */       switch (instruction.getElementWidth()) {
/*     */       case 1:
/* 349 */         for (Number element : elements) {
/* 350 */           this.writer.write(element.byteValue());
/*     */         }
/* 352 */         break;
/*     */       case 2:
/* 354 */         for (Number element : elements) {
/* 355 */           this.writer.writeShort(element.shortValue());
/*     */         }
/* 357 */         break;
/*     */       case 4:
/* 359 */         for (Number element : elements) {
/* 360 */           this.writer.writeInt(element.intValue());
/*     */         }
/* 362 */         break;
/*     */       case 8:
/* 364 */         for (Number element : elements)
/* 365 */           this.writer.writeLong(element.longValue()); case 3:
/*     */       case 5:
/*     */       case 6:
/*     */       case 7:
/* 369 */       }if ((this.writer.getPosition() & 0x1) != 0)
/* 370 */         this.writer.write(0);
/*     */     }
/*     */     catch (IOException ex) {
/* 373 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(SparseSwitchPayload instruction) {
/*     */     try {
/* 379 */       this.writer.writeUbyte(0);
/* 380 */       this.writer.writeUbyte(instruction.getOpcode().value >> 8);
/* 381 */       List elements = instruction.getSwitchElements();
/* 382 */       this.writer.writeUshort(elements.size());
/* 383 */       for (SwitchElement element : elements) {
/* 384 */         this.writer.writeInt(element.getKey());
/*     */       }
/* 386 */       for (SwitchElement element : elements)
/* 387 */         this.writer.writeInt(element.getOffset());
/*     */     }
/*     */     catch (IOException ex) {
/* 390 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void write(PackedSwitchPayload instruction) {
/*     */     try {
/* 396 */       this.writer.writeUbyte(0);
/* 397 */       this.writer.writeUbyte(instruction.getOpcode().value >> 8);
/* 398 */       List elements = instruction.getSwitchElements();
/* 399 */       this.writer.writeUshort(elements.size());
/* 400 */       if (elements.size() == 0) {
/* 401 */         this.writer.writeInt(0);
/*     */       } else {
/* 403 */         this.writer.writeInt(((SwitchElement)elements.get(0)).getKey());
/* 404 */         for (SwitchElement element : elements)
/* 405 */           this.writer.writeInt(element.getOffset());
/*     */       }
/*     */     }
/*     */     catch (IOException ex) {
/* 409 */       throw new RuntimeException(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static int packNibbles(int a, int b) {
/* 414 */     return b << 4 | a;
/*     */   }
/*     */ 
/*     */   private int getReferenceIndex(ReferenceInstruction referenceInstruction) {
/* 418 */     switch (referenceInstruction.getOpcode().referenceType) {
/*     */     case 2:
/* 420 */       return this.fieldSection.getItemIndex((FieldReference)referenceInstruction.getReference());
/*     */     case 3:
/* 422 */       return this.methodSection.getItemIndex((MethodReference)referenceInstruction.getReference());
/*     */     case 0:
/* 424 */       return this.stringSection.getItemIndex((StringReference)referenceInstruction.getReference());
/*     */     case 1:
/* 426 */       return this.typeSection.getItemIndex((TypeReference)referenceInstruction.getReference());
/*     */     }
/* 428 */     throw new ExceptionWithContext("Unknown reference type: %d", new Object[] { Integer.valueOf(referenceInstruction.getOpcode().referenceType) });
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.InstructionWriter
 * JD-Core Version:    0.6.0
 */