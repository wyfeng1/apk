/*    */ package org.jf.dexlib2.util;
/*    */ 
/*    */ import org.jf.dexlib2.Opcode;
/*    */ 
/*    */ public final class InstructionUtil
/*    */ {
/*    */   public static boolean isInvokeStatic(Opcode opcode)
/*    */   {
/* 38 */     return (opcode == Opcode.INVOKE_STATIC) || (opcode == Opcode.INVOKE_STATIC_RANGE);
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.util.InstructionUtil
 * JD-Core Version:    0.6.0
 */