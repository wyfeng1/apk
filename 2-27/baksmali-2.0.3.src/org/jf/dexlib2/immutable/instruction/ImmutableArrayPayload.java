/*    */ package org.jf.dexlib2.immutable.instruction;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.iface.instruction.formats.ArrayPayload;
/*    */ 
/*    */ public class ImmutableArrayPayload extends ImmutableInstruction
/*    */   implements ArrayPayload
/*    */ {
/* 45 */   public static final Opcode OPCODE = Opcode.ARRAY_PAYLOAD;
/*    */   protected final int elementWidth;
/*    */   protected final ImmutableList<Number> arrayElements;
/*    */ 
/*    */   public ImmutableArrayPayload(int elementWidth, List<Number> arrayElements)
/*    */   {
/* 52 */     super(OPCODE);
/* 53 */     this.elementWidth = elementWidth;
/* 54 */     this.arrayElements = (arrayElements == null ? ImmutableList.of() : ImmutableList.copyOf(arrayElements));
/*    */   }
/*    */ 
/*    */   public static ImmutableArrayPayload of(ArrayPayload instruction)
/*    */   {
/* 68 */     if ((instruction instanceof ImmutableArrayPayload)) {
/* 69 */       return (ImmutableArrayPayload)instruction;
/*    */     }
/* 71 */     return new ImmutableArrayPayload(instruction.getElementWidth(), instruction.getArrayElements());
/*    */   }
/*    */ 
/*    */   public int getElementWidth()
/*    */   {
/* 76 */     return this.elementWidth; } 
/* 77 */   public List<Number> getArrayElements() { return this.arrayElements; } 
/*    */   public int getCodeUnits() {
/* 79 */     return 4 + (this.elementWidth * this.arrayElements.size() + 1) / 2; } 
/* 80 */   public Format getFormat() { return OPCODE.format;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableArrayPayload
 * JD-Core Version:    0.6.0
 */