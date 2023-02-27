/*    */ package org.jf.dexlib2.writer.builder;
/*    */ 
/*    */ import com.google.common.collect.Ordering;
/*    */ import java.util.List;
/*    */ import org.jf.util.CharSequenceUtils;
/*    */ import org.jf.util.CollectionUtils;
/*    */ 
/*    */ public class BuilderProtoReference
/*    */   implements Comparable<BuilderProtoReference>, BuilderProtoPool.ProtoKey
/*    */ {
/*    */   final BuilderStringReference shorty;
/*    */   final BuilderTypeList parameterTypes;
/*    */   final BuilderTypeReference returnType;
/* 47 */   int index = -1;
/*    */ 
/*    */   public BuilderProtoReference(BuilderStringReference shorty, BuilderTypeList parameterTypes, BuilderTypeReference returnType)
/*    */   {
/* 51 */     this.shorty = shorty;
/* 52 */     this.parameterTypes = parameterTypes;
/* 53 */     this.returnType = returnType;
/*    */   }
/*    */ 
/*    */   public List<? extends CharSequence> getParameterTypes() {
/* 57 */     return this.parameterTypes;
/*    */   }
/*    */ 
/*    */   public String getReturnType() {
/* 61 */     return this.returnType.getType();
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 66 */     int hashCode = getReturnType().hashCode();
/* 67 */     return hashCode * 31 + getParameterTypes().hashCode();
/*    */   }
/*    */ 
/*    */   public boolean equals(Object o)
/*    */   {
/* 72 */     if ((o != null) && ((o instanceof BuilderProtoReference))) {
/* 73 */       BuilderProtoReference other = (BuilderProtoReference)o;
/* 74 */       return (this.returnType.equals(other.returnType)) && (CharSequenceUtils.listEquals(this.parameterTypes, other.parameterTypes));
/*    */     }
/*    */ 
/* 77 */     return false;
/*    */   }
/*    */ 
/*    */   public int compareTo(BuilderProtoReference o)
/*    */   {
/* 82 */     int res = this.returnType.compareTo(o.returnType);
/* 83 */     if (res != 0) return res;
/* 84 */     return CollectionUtils.compareAsIterable(Ordering.usingToString(), this.parameterTypes, o.parameterTypes);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderProtoReference
 * JD-Core Version:    0.6.0
 */