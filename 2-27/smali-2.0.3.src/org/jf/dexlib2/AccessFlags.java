/*     */ package org.jf.dexlib2;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public enum AccessFlags
/*     */ {
/*  38 */   PUBLIC(1, "public", true, true, true), 
/*  39 */   PRIVATE(2, "private", true, true, true), 
/*  40 */   PROTECTED(4, "protected", true, true, true), 
/*  41 */   STATIC(8, "static", true, true, true), 
/*  42 */   FINAL(16, "final", true, true, true), 
/*  43 */   SYNCHRONIZED(32, "synchronized", false, true, false), 
/*  44 */   VOLATILE(64, "volatile", false, false, true), 
/*  45 */   BRIDGE(64, "bridge", false, true, false), 
/*  46 */   TRANSIENT(128, "transient", false, false, true), 
/*  47 */   VARARGS(128, "varargs", false, true, false), 
/*  48 */   NATIVE(256, "native", false, true, false), 
/*  49 */   INTERFACE(512, "interface", true, false, false), 
/*  50 */   ABSTRACT(1024, "abstract", true, true, false), 
/*  51 */   STRICTFP(2048, "strictfp", false, true, false), 
/*  52 */   SYNTHETIC(4096, "synthetic", true, true, true), 
/*  53 */   ANNOTATION(8192, "annotation", true, false, false), 
/*  54 */   ENUM(16384, "enum", true, false, true), 
/*  55 */   CONSTRUCTOR(65536, "constructor", false, true, false), 
/*  56 */   DECLARED_SYNCHRONIZED(131072, "declared-synchronized", false, true, false);
/*     */ 
/*     */   private int value;
/*     */   private String accessFlagName;
/*     */   private boolean validForClass;
/*     */   private boolean validForMethod;
/*     */   private boolean validForField;
/*     */   private static final AccessFlags[] allFlags;
/*     */   private static HashMap<String, AccessFlags> accessFlagsByName;
/*     */ 
/*     */   private AccessFlags(int value, String accessFlagName, boolean validForClass, boolean validForMethod, boolean validForField)
/*     */   {
/*  80 */     this.value = value;
/*  81 */     this.accessFlagName = accessFlagName;
/*  82 */     this.validForClass = validForClass;
/*  83 */     this.validForMethod = validForMethod;
/*  84 */     this.validForField = validForField;
/*     */   }
/*     */ 
/*     */   public boolean isSet(int accessFlags) {
/*  88 */     return (this.value & accessFlags) != 0;
/*     */   }
/*     */ 
/*     */   public static AccessFlags getAccessFlag(String accessFlag)
/*     */   {
/* 175 */     return (AccessFlags)accessFlagsByName.get(accessFlag);
/*     */   }
/*     */ 
/*     */   public int getValue() {
/* 179 */     return this.value;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 183 */     return this.accessFlagName;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  70 */     allFlags = values();
/*     */ 
/*  72 */     accessFlagsByName = new HashMap();
/*  73 */     for (AccessFlags accessFlag : allFlags)
/*  74 */       accessFlagsByName.put(accessFlag.accessFlagName, accessFlag);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.AccessFlags
 * JD-Core Version:    0.6.0
 */