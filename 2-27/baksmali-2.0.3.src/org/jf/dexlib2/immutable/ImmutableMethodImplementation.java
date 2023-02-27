/*    */ package org.jf.dexlib2.immutable;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.jf.dexlib2.iface.ExceptionHandler;
/*    */ import org.jf.dexlib2.iface.MethodImplementation;
/*    */ import org.jf.dexlib2.iface.TryBlock;
/*    */ import org.jf.dexlib2.iface.debug.DebugItem;
/*    */ import org.jf.dexlib2.iface.instruction.Instruction;
/*    */ import org.jf.dexlib2.immutable.debug.ImmutableDebugItem;
/*    */ import org.jf.dexlib2.immutable.instruction.ImmutableInstruction;
/*    */ 
/*    */ public class ImmutableMethodImplementation
/*    */   implements MethodImplementation
/*    */ {
/*    */   protected final int registerCount;
/*    */   protected final ImmutableList<? extends ImmutableInstruction> instructions;
/*    */   protected final ImmutableList<? extends ImmutableTryBlock> tryBlocks;
/*    */   protected final ImmutableList<? extends ImmutableDebugItem> debugItems;
/*    */ 
/*    */   public ImmutableMethodImplementation(int registerCount, Iterable<? extends Instruction> instructions, List<? extends TryBlock<? extends ExceptionHandler>> tryBlocks, Iterable<? extends DebugItem> debugItems)
/*    */   {
/* 58 */     this.registerCount = registerCount;
/* 59 */     this.instructions = ImmutableInstruction.immutableListOf(instructions);
/* 60 */     this.tryBlocks = ImmutableTryBlock.immutableListOf(tryBlocks);
/* 61 */     this.debugItems = ImmutableDebugItem.immutableListOf(debugItems);
/*    */   }
/*    */ 
/*    */   public static ImmutableMethodImplementation of(MethodImplementation methodImplementation)
/*    */   {
/* 76 */     if (methodImplementation == null) {
/* 77 */       return null;
/*    */     }
/* 79 */     if ((methodImplementation instanceof ImmutableMethodImplementation)) {
/* 80 */       return (ImmutableMethodImplementation)methodImplementation;
/*    */     }
/* 82 */     return new ImmutableMethodImplementation(methodImplementation.getRegisterCount(), methodImplementation.getInstructions(), methodImplementation.getTryBlocks(), methodImplementation.getDebugItems());
/*    */   }
/*    */ 
/*    */   public int getRegisterCount()
/*    */   {
/* 89 */     return this.registerCount; } 
/* 90 */   public ImmutableList<? extends ImmutableInstruction> getInstructions() { return this.instructions; } 
/* 91 */   public ImmutableList<? extends ImmutableTryBlock> getTryBlocks() { return this.tryBlocks; } 
/* 92 */   public ImmutableList<? extends ImmutableDebugItem> getDebugItems() { return this.debugItems;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.ImmutableMethodImplementation
 * JD-Core Version:    0.6.0
 */