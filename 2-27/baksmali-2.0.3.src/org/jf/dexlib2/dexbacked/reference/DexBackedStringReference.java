/*    */ package org.jf.dexlib2.dexbacked.reference;
/*    */ 
/*    */ import org.jf.dexlib2.base.reference.BaseStringReference;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ 
/*    */ public class DexBackedStringReference extends BaseStringReference
/*    */ {
/*    */   public final DexBackedDexFile dexFile;
/*    */   public final int stringIndex;
/*    */ 
/*    */   public DexBackedStringReference(DexBackedDexFile dexBuf, int stringIndex)
/*    */   {
/* 45 */     this.dexFile = dexBuf;
/* 46 */     this.stringIndex = stringIndex;
/*    */   }
/*    */ 
/*    */   public String getString()
/*    */   {
/* 51 */     return this.dexFile.getString(this.stringIndex);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.reference.DexBackedStringReference
 * JD-Core Version:    0.6.0
 */