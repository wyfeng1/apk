/*    */ package org.jf.dexlib2.builder.debug;
/*    */ 
/*    */ import org.jf.dexlib2.builder.BuilderDebugItem;
/*    */ import org.jf.dexlib2.iface.debug.StartLocal;
/*    */ import org.jf.dexlib2.iface.reference.StringReference;
/*    */ import org.jf.dexlib2.iface.reference.TypeReference;
/*    */ 
/*    */ public class BuilderStartLocal extends BuilderDebugItem
/*    */   implements StartLocal
/*    */ {
/*    */   private final int register;
/*    */   private final StringReference name;
/*    */   private final TypeReference type;
/*    */   private final StringReference signature;
/*    */ 
/*    */   public BuilderStartLocal(int register, StringReference name, TypeReference type, StringReference signature)
/*    */   {
/* 52 */     this.register = register;
/* 53 */     this.name = name;
/* 54 */     this.type = type;
/* 55 */     this.signature = signature;
/*    */   }
/*    */   public int getRegister() {
/* 58 */     return this.register;
/*    */   }
/* 60 */   public StringReference getNameReference() { return this.name; } 
/* 61 */   public TypeReference getTypeReference() { return this.type; } 
/* 62 */   public StringReference getSignatureReference() { return this.signature;
/*    */   }
/*    */ 
/*    */   public int getDebugItemType()
/*    */   {
/* 76 */     return 3;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.debug.BuilderStartLocal
 * JD-Core Version:    0.6.0
 */