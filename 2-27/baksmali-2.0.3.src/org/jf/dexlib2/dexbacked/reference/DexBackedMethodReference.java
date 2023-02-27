/*    */ package org.jf.dexlib2.dexbacked.reference;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.jf.dexlib2.base.reference.BaseMethodReference;
/*    */ import org.jf.dexlib2.dexbacked.DexBackedDexFile;
/*    */ import org.jf.dexlib2.dexbacked.util.FixedSizeList;
/*    */ 
/*    */ public class DexBackedMethodReference extends BaseMethodReference
/*    */ {
/*    */   public final DexBackedDexFile dexFile;
/*    */   public final int methodIdItemOffset;
/*    */   private int protoIdItemOffset;
/*    */ 
/*    */   public DexBackedMethodReference(DexBackedDexFile dexFile, int methodIndex)
/*    */   {
/* 51 */     this.dexFile = dexFile;
/* 52 */     this.methodIdItemOffset = dexFile.getMethodIdItemOffset(methodIndex);
/*    */   }
/*    */ 
/*    */   public String getDefiningClass()
/*    */   {
/* 58 */     return this.dexFile.getType(this.dexFile.readUshort(this.methodIdItemOffset + 0));
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 64 */     return this.dexFile.getString(this.dexFile.readSmallUint(this.methodIdItemOffset + 4));
/*    */   }
/*    */ 
/*    */   public List<String> getParameterTypes()
/*    */   {
/* 70 */     int protoIdItemOffset = getProtoIdItemOffset();
/* 71 */     int parametersOffset = this.dexFile.readSmallUint(protoIdItemOffset + 8);
/* 72 */     if (parametersOffset > 0) {
/* 73 */       int parameterCount = this.dexFile.readSmallUint(parametersOffset + 0);
/* 74 */       int paramListStart = parametersOffset + 4;
/* 75 */       return new FixedSizeList(paramListStart, parameterCount)
/*    */       {
/*    */         public String readItem(int index)
/*    */         {
/* 79 */           return DexBackedMethodReference.this.dexFile.getType(DexBackedMethodReference.this.dexFile.readUshort(this.val$paramListStart + 2 * index));
/*    */         }
/* 81 */         public int size() { return this.val$parameterCount; } } ;
/*    */     }
/* 84 */     return ImmutableList.of();
/*    */   }
/*    */ 
/*    */   public String getReturnType()
/*    */   {
/* 90 */     int protoIdItemOffset = getProtoIdItemOffset();
/* 91 */     return this.dexFile.getType(this.dexFile.readSmallUint(protoIdItemOffset + 4));
/*    */   }
/*    */ 
/*    */   private int getProtoIdItemOffset() {
/* 95 */     if (this.protoIdItemOffset == 0) {
/* 96 */       this.protoIdItemOffset = this.dexFile.getProtoIdItemOffset(this.dexFile.readUshort(this.methodIdItemOffset + 2));
/*    */     }
/*    */ 
/* 99 */     return this.protoIdItemOffset;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.dexbacked.reference.DexBackedMethodReference
 * JD-Core Version:    0.6.0
 */