/*    */ package org.jf.dexlib2.builder.instruction;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.jf.dexlib2.Format;
/*    */ import org.jf.dexlib2.Opcode;
/*    */ import org.jf.dexlib2.builder.BuilderInstruction;
/*    */ import org.jf.dexlib2.iface.instruction.formats.ArrayPayload;
/*    */ 
/*    */ public class BuilderArrayPayload extends BuilderInstruction
/*    */   implements ArrayPayload
/*    */ {
/* 45 */   public static final Opcode OPCODE = Opcode.ARRAY_PAYLOAD;
/*    */   protected final int elementWidth;
/*    */   protected final List<Number> arrayElements;
/*    */ 
/*    */   public BuilderArrayPayload(int elementWidth, List<Number> arrayElements)
/*    */   {
/* 52 */     super(OPCODE);
/* 53 */     this.elementWidth = elementWidth;
/* 54 */     this.arrayElements = (arrayElements == null ? ImmutableList.of() : arrayElements);
/*    */   }
/*    */   public int getElementWidth() {
/* 57 */     return this.elementWidth; } 
/* 58 */   public List<Number> getArrayElements() { return this.arrayElements; } 
/*    */   public int getCodeUnits() {
/* 60 */     return 4 + (this.elementWidth * this.arrayElements.size() + 1) / 2; } 
/* 61 */   public Format getFormat() { return OPCODE.format;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.builder.instruction.BuilderArrayPayload
 * JD-Core Version:    0.6.0
 */