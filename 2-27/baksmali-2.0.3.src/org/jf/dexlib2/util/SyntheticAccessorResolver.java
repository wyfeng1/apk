/*     */ package org.jf.dexlib2.util;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import com.google.common.collect.ImmutableMap.Builder;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jf.dexlib2.AccessFlags;
/*     */ import org.jf.dexlib2.iface.ClassDef;
/*     */ import org.jf.dexlib2.iface.Method;
/*     */ import org.jf.dexlib2.iface.MethodImplementation;
/*     */ import org.jf.dexlib2.iface.instruction.ReferenceInstruction;
/*     */ import org.jf.dexlib2.iface.reference.MethodReference;
/*     */ import org.jf.dexlib2.iface.reference.Reference;
/*     */ 
/*     */ public class SyntheticAccessorResolver
/*     */ {
/*     */   private final Map<String, ClassDef> classDefMap;
/*  72 */   private final Map<String, AccessedMember> resolvedAccessors = Maps.newConcurrentMap();
/*     */ 
/*     */   public SyntheticAccessorResolver(Iterable<? extends ClassDef> classDefs) {
/*  75 */     ImmutableMap.Builder builder = ImmutableMap.builder();
/*     */ 
/*  77 */     for (ClassDef classDef : classDefs) {
/*  78 */       builder.put(classDef.getType(), classDef);
/*     */     }
/*     */ 
/*  81 */     this.classDefMap = builder.build();
/*     */   }
/*     */ 
/*     */   public static boolean looksLikeSyntheticAccessor(String methodName) {
/*  85 */     return methodName.startsWith("access$");
/*     */   }
/*     */ 
/*     */   public AccessedMember getAccessedMember(MethodReference methodReference)
/*     */   {
/*  90 */     String methodDescriptor = ReferenceUtil.getMethodDescriptor(methodReference);
/*     */ 
/*  92 */     AccessedMember accessedMember = (AccessedMember)this.resolvedAccessors.get(methodDescriptor);
/*  93 */     if (accessedMember != null) {
/*  94 */       return accessedMember;
/*     */     }
/*     */ 
/*  97 */     String type = methodReference.getDefiningClass();
/*  98 */     ClassDef classDef = (ClassDef)this.classDefMap.get(type);
/*  99 */     if (classDef == null) {
/* 100 */       return null;
/*     */     }
/*     */ 
/* 103 */     Method matchedMethod = null;
/* 104 */     MethodImplementation matchedMethodImpl = null;
/* 105 */     for (Method method : classDef.getMethods()) {
/* 106 */       MethodImplementation methodImpl = method.getImplementation();
/* 107 */       if ((methodImpl != null) && 
/* 108 */         (methodReferenceEquals(method, methodReference))) {
/* 109 */         matchedMethod = method;
/* 110 */         matchedMethodImpl = methodImpl;
/* 111 */         break;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 116 */     if (matchedMethod == null) {
/* 117 */       return null;
/*     */     }
/*     */ 
/* 121 */     if (!AccessFlags.SYNTHETIC.isSet(matchedMethod.getAccessFlags())) {
/* 122 */       return null;
/*     */     }
/*     */ 
/* 125 */     List instructions = ImmutableList.copyOf(matchedMethodImpl.getInstructions());
/*     */ 
/* 127 */     int accessType = SyntheticAccessorFSM.test(instructions);
/*     */ 
/* 129 */     if (accessType >= 0) {
/* 130 */       AccessedMember member = new AccessedMember(accessType, ((ReferenceInstruction)instructions.get(0)).getReference());
/*     */ 
/* 132 */       this.resolvedAccessors.put(methodDescriptor, member);
/* 133 */       return member;
/*     */     }
/* 135 */     return null;
/*     */   }
/*     */ 
/*     */   private static boolean methodReferenceEquals(MethodReference ref1, MethodReference ref2)
/*     */   {
/* 150 */     return (ref1.getName().equals(ref2.getName())) && (ref1.getReturnType().equals(ref2.getReturnType())) && (ref1.getParameterTypes().equals(ref2.getParameterTypes()));
/*     */   }
/*     */ 
/*     */   public static class AccessedMember
/*     */   {
/*     */     public final int accessedMemberType;
/*     */     public final Reference accessedMember;
/*     */ 
/*     */     public AccessedMember(int accessedMemberType, Reference accessedMember)
/*     */     {
/* 143 */       this.accessedMemberType = accessedMemberType;
/* 144 */       this.accessedMember = accessedMember;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.util.SyntheticAccessorResolver
 * JD-Core Version:    0.6.0
 */