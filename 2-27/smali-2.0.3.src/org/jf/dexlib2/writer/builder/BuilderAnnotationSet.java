/*    */ package org.jf.dexlib2.writer.builder;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.AbstractSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.Set;
/*    */ 
/*    */ class BuilderAnnotationSet extends AbstractSet<BuilderAnnotation>
/*    */ {
/* 43 */   public static final BuilderAnnotationSet EMPTY = new BuilderAnnotationSet(ImmutableSet.of());
/*    */   final Set<BuilderAnnotation> annotations;
/* 47 */   int offset = 0;
/*    */ 
/*    */   public BuilderAnnotationSet(Set<BuilderAnnotation> annotations) {
/* 50 */     this.annotations = annotations;
/*    */   }
/*    */ 
/*    */   public Iterator<BuilderAnnotation> iterator() {
/* 54 */     return this.annotations.iterator();
/*    */   }
/*    */ 
/*    */   public int size() {
/* 58 */     return this.annotations.size();
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.builder.BuilderAnnotationSet
 * JD-Core Version:    0.6.0
 */