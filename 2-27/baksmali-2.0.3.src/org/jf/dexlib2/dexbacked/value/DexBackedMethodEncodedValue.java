/*    */ package org.jf.dexlib2.dexbacked.value;
/*    */ 
/*    */ import org.jf.dexlib2.base.value.BaseMethodEncodedValue;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.DexReader;
/*    */ import org.jf.dexlib2.dexbacked.reference.DexBackedMethodReference;
/*    */ import org.jf.dexlib2.iface.reference.MethodReference;
/*    */ 
/*    */ public class DexBackedMethodEncodedValue extends BaseMethodEncodedValue
/*    */ {
/*    */   public final DexBackedDexFile dexFile;
/*    */   private final int MethodIndex;
/*    */ 
/*    */   public DexBackedMethodEncodedValue(DexReader reader, int valueArg)
/*    */   {
/* 47 */     this.dexFile = ((DexBackedDexFile)reader.dexBuf);
/* 48 */     this.MethodIndex = reader.readSizedSmallUint(valueArg + 1);
/*    */   }
/*    */ 
/*    */   public MethodReference getValue() {
/* 52 */     return new DexBackedMethodReference(this.dexFile, this.MethodIndex);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.value.DexBackedMethodEncodedValue
 * JD-Core Version:    0.6.0
 */