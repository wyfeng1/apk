/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.UnknownInstruction;
/*    */ 
/*    */ public class ImmutableUnknownInstruction extends ImmutableInstruction
/*    */   implements UnknownInstruction
/*    */ {
/* 39 */   public static final Format FORMAT = Format.Format10x;
/*    */   protected final int originalOpcode;
/*    */ 
/*    */   public ImmutableUnknownInstruction(int originalOpcode)
/*    */   {
/* 44 */     super(Opcode.NOP);
/* 45 */     this.originalOpcode = originalOpcode;
/*    */   }
/*    */ 
/*    */   public static ImmutableUnknownInstruction of(UnknownInstruction instruction) {
/* 49 */     if ((instruction instanceof ImmutableUnknownInstruction)) {
/* 50 */       return (ImmutableUnknownInstruction)instruction;
/*    */     }
/* 52 */     return new ImmutableUnknownInstruction(instruction.getOriginalOpcode());
/*    */   }
/*    */   public Format getFormat() {
/* 55 */     return FORMAT; } 
/* 56 */   public int getOriginalOpcode() { return this.originalOpcode;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableUnknownInstruction
 * JD-Core Version:    0.6.0
 */