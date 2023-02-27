/*    */ package org.jf.dexlib2.dexbacked;
/*    */ 
/*    */ import org.jf.dexlib2.base.BaseAnnotationElement;
/*    */ import org.jf.dexlib2.dexbacked.value.DexBackedEncodedValue;
/*    */ import org.jf.dexlib2.iface.value.EncodedValue;
/*    */ 
/*    */ public class DexBackedAnnotationElement extends BaseAnnotationElement
/*    */ {
/*    */   private final DexBackedDexFile dexFile;
/*    */   public final int nameIndex;
/*    */   public final EncodedValue value;
/*    */ 
/*    */   public DexBackedAnnotationElement(DexReader reader)
/*    */   {
/* 46 */     this.dexFile = ((DexBackedDexFile)reader.dexBuf);
/* 47 */     this.nameIndex = reader.readSmallUleb128();
/* 48 */     this.value = DexBackedEncodedValue.readFrom(reader);
/*    */   }
/*    */   public String getName() {
/* 51 */     return this.dexFile.getString(this.nameIndex); } 
/* 52 */   public EncodedValue getValue() { return this.value;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.DexBackedAnnotationElement
 * JD-Core Version:    0.6.0
 */