/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.jf.dexlib2.iface.instruction.SwitchElement;
/*    */ import org.jf.util.ImmutableConverter;
/*    */ 
/*    */ public class ImmutableSwitchElement
/*    */   implements SwitchElement
/*    */ {
/*    */   protected final int key;
/*    */   protected final int offset;
/* 70 */   private static final ImmutableConverter<ImmutableSwitchElement, SwitchElement> CONVERTER = new ImmutableConverter()
/*    */   {
/*    */     protected boolean isImmutable(SwitchElement item)
/*    */     {
/* 74 */       return item instanceof ImmutableSwitchElement;
/*    */     }
/*    */ 
/*    */     protected ImmutableSwitchElement makeImmutable(SwitchElement item)
/*    */     {
/* 80 */       return ImmutableSwitchElement.of(item);
/*    */     }
/* 70 */   };
/*    */ 
/*    */   public ImmutableSwitchElement(int key, int offset)
/*    */   {
/* 48 */     this.key = key;
/* 49 */     this.offset = offset;
/*    */   }
/*    */ 
/*    */   public static ImmutableSwitchElement of(SwitchElement switchElement)
/*    */   {
/* 54 */     if ((switchElement instanceof ImmutableSwitchElement)) {
/* 55 */       return (ImmutableSwitchElement)switchElement;
/*    */     }
/* 57 */     return new ImmutableSwitchElement(switchElement.getKey(), switchElement.getOffset());
/*    */   }
/*    */ 
/*    */   public int getKey()
/*    */   {
/* 62 */     return this.key; } 
/* 63 */   public int getOffset() { return this.offset; }
/*    */ 
/*    */   public static ImmutableList<ImmutableSwitchElement> immutableListOf(List<? extends SwitchElement> list)
/*    */   {
/* 67 */     return CONVERTER.toList(list);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableSwitchElement
 * JD-Core Version:    0.6.0
 */