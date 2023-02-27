/*    */ package org.jf.dexlib2.immutable.debug;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import org.jf.dexlib2.iface.debug.DebugItem;
/*    */ import org.jf.dexlib2.iface.debug.EndLocal;
/*    */ import org.jf.dexlib2.iface.debug.EpilogueBegin;
/*    */ import org.jf.dexlib2.iface.debug.LineNumber;
/*    */ import org.jf.dexlib2.iface.debug.PrologueEnd;
/*    */ import org.jf.dexlib2.iface.debug.RestartLocal;
/*    */ import org.jf.dexlib2.iface.debug.SetSourceFile;
/*    */ import org.jf.dexlib2.iface.debug.StartLocal;
/*    */ import org.jf.util.ExceptionWithContext;
/*    */ import org.jf.util.ImmutableConverter;
/*    */ 
/*    */ public abstract class ImmutableDebugItem
/*    */   implements DebugItem
/*    */ {
/*    */   protected final int codeAddress;
/* 82 */   private static final ImmutableConverter<ImmutableDebugItem, DebugItem> CONVERTER = new ImmutableConverter()
/*    */   {
/*    */     protected boolean isImmutable(DebugItem item)
/*    */     {
/* 86 */       return item instanceof ImmutableDebugItem;
/*    */     }
/*    */ 
/*    */     protected ImmutableDebugItem makeImmutable(DebugItem item)
/*    */     {
/* 92 */       return ImmutableDebugItem.of(item);
/*    */     }
/* 82 */   };
/*    */ 
/*    */   public ImmutableDebugItem(int codeAddress)
/*    */   {
/* 47 */     this.codeAddress = codeAddress;
/*    */   }
/*    */ 
/*    */   public static ImmutableDebugItem of(DebugItem debugItem)
/*    */   {
/* 52 */     if ((debugItem instanceof ImmutableDebugItem)) {
/* 53 */       return (ImmutableDebugItem)debugItem;
/*    */     }
/* 55 */     switch (debugItem.getDebugItemType()) {
/*    */     case 3:
/* 57 */       return ImmutableStartLocal.of((StartLocal)debugItem);
/*    */     case 5:
/* 59 */       return ImmutableEndLocal.of((EndLocal)debugItem);
/*    */     case 6:
/* 61 */       return ImmutableRestartLocal.of((RestartLocal)debugItem);
/*    */     case 7:
/* 63 */       return ImmutablePrologueEnd.of((PrologueEnd)debugItem);
/*    */     case 8:
/* 65 */       return ImmutableEpilogueBegin.of((EpilogueBegin)debugItem);
/*    */     case 9:
/* 67 */       return ImmutableSetSourceFile.of((SetSourceFile)debugItem);
/*    */     case 10:
/* 69 */       return ImmutableLineNumber.of((LineNumber)debugItem);
/*    */     case 4:
/* 71 */     }throw new ExceptionWithContext("Invalid debug item type: %d", new Object[] { Integer.valueOf(debugItem.getDebugItemType()) });
/*    */   }
/*    */ 
/*    */   public int getCodeAddress() {
/* 75 */     return this.codeAddress;
/*    */   }
/*    */ 
/*    */   public static ImmutableList<ImmutableDebugItem> immutableListOf(Iterable<? extends DebugItem> list) {
/* 79 */     return CONVERTER.toList(list);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.debug.ImmutableDebugItem
 * JD-Core Version:    0.6.0
 */