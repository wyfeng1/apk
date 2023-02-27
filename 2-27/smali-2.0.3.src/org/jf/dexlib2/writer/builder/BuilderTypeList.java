/*    */ package org.jf.dexlib2.writer.builder;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.AbstractList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class BuilderTypeList extends AbstractList<BuilderTypeReference>
/*    */ {
/* 42 */   static final BuilderTypeList EMPTY = new BuilderTypeList(ImmutableList.of());
/*    */   final List<? extends BuilderTypeReference> types;
/* 45 */   int offset = 0;
/*    */ 
/*    */   public BuilderTypeList(List<? extends BuilderTypeReference> types) {
/* 48 */     this.types = types;
/*    */   }
/*    */ 
/*    */   public BuilderTypeReference get(int index) {
/* 52 */     return (BuilderTypeReference)this.types.get(index);
/*    */   }
/*    */ 
/*    */   public int size() {
/* 56 */     return this.types.size();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderTypeList
 * JD-Core Version:    0.6.0
 */