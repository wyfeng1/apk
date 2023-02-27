/*    */ package org.jf.dexlib2.immutable;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.jf.dexlib2.base.BaseTryBlock;
/*    */ import org.jf.dexlib2.iface.ExceptionHandler;
/*    */ import org.jf.dexlib2.iface.TryBlock;
/*    */ import org.jf.util.ImmutableConverter;
/*    */ 
/*    */ public class ImmutableTryBlock extends BaseTryBlock<ImmutableExceptionHandler>
/*    */ {
/*    */   protected final int startCodeAddress;
/*    */   protected final int codeUnitCount;
/*    */   protected final ImmutableList<? extends ImmutableExceptionHandler> exceptionHandlers;
/* 89 */   private static final ImmutableConverter<ImmutableTryBlock, TryBlock<? extends ExceptionHandler>> CONVERTER = new ImmutableConverter()
/*    */   {
/*    */     protected boolean isImmutable(TryBlock item)
/*    */     {
/* 93 */       return item instanceof ImmutableTryBlock;
/*    */     }
/*    */ 
/*    */     protected ImmutableTryBlock makeImmutable(TryBlock<? extends ExceptionHandler> item)
/*    */     {
/* 99 */       return ImmutableTryBlock.of(item);
/*    */     }
/* 89 */   };
/*    */ 
/*    */   public ImmutableTryBlock(int startCodeAddress, int codeUnitCount, List<? extends ExceptionHandler> exceptionHandlers)
/*    */   {
/* 53 */     this.startCodeAddress = startCodeAddress;
/* 54 */     this.codeUnitCount = codeUnitCount;
/* 55 */     this.exceptionHandlers = ImmutableExceptionHandler.immutableListOf(exceptionHandlers);
/*    */   }
/*    */ 
/*    */   public static ImmutableTryBlock of(TryBlock<? extends ExceptionHandler> tryBlock)
/*    */   {
/* 67 */     if ((tryBlock instanceof ImmutableTryBlock)) {
/* 68 */       return (ImmutableTryBlock)tryBlock;
/*    */     }
/* 70 */     return new ImmutableTryBlock(tryBlock.getStartCodeAddress(), tryBlock.getCodeUnitCount(), tryBlock.getExceptionHandlers());
/*    */   }
/*    */ 
/*    */   public int getStartCodeAddress()
/*    */   {
/* 76 */     return this.startCodeAddress; } 
/* 77 */   public int getCodeUnitCount() { return this.codeUnitCount; }
/*    */ 
/*    */   public ImmutableList<? extends ImmutableExceptionHandler> getExceptionHandlers() {
/* 80 */     return this.exceptionHandlers;
/*    */   }
/*    */ 
/*    */   public static ImmutableList<ImmutableTryBlock> immutableListOf(List<? extends TryBlock<? extends ExceptionHandler>> list)
/*    */   {
/* 86 */     return CONVERTER.toList(list);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.ImmutableTryBlock
 * JD-Core Version:    0.6.0
 */