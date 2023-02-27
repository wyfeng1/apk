/*    */ package org.jf.dexlib2.immutable;
/*    */ 
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Collection;
/*    */ import org.jf.dexlib2.iface.ClassDef;
/*    */ import org.jf.dexlib2.iface.DexFile;
/*    */ 
/*    */ public class ImmutableDexFile
/*    */   implements DexFile
/*    */ {
/*    */   protected final ImmutableSet<? extends ImmutableClassDef> classes;
/*    */ 
/*    */   public ImmutableDexFile(Collection<? extends ClassDef> classes)
/*    */   {
/* 47 */     this.classes = ImmutableClassDef.immutableSetOf(classes);
/*    */   }
/*    */ 
/*    */   public ImmutableSet<? extends ImmutableClassDef> getClasses()
/*    */   {
/* 61 */     return this.classes;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.ImmutableDexFile
 * JD-Core Version:    0.6.0
 */