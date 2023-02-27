/*    */ package org.jf.dexlib2;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ public class Opcodes
/*    */ {
/*    */   private final Opcode[] opcodesByValue;
/*    */   private final HashMap<String, Opcode> opcodesByName;
/*    */ 
/*    */   public Opcodes(int api)
/*    */   {
/* 44 */     this.opcodesByValue = new Opcode[256];
/* 45 */     this.opcodesByName = Maps.newHashMap();
/*    */ 
/* 47 */     for (Opcode opcode : Opcode.values()) {
/* 48 */       if ((opcode.format.isPayloadFormat) || 
/* 49 */         (api > opcode.getMaxApi()) || (api < opcode.getMinApi())) continue;
/* 50 */       this.opcodesByValue[opcode.value] = opcode;
/* 51 */       this.opcodesByName.put(opcode.name.toLowerCase(), opcode);
/*    */     }
/*    */   }
/*    */ 
/*    */   public Opcode getOpcodeByValue(int opcodeValue)
/*    */   {
/* 64 */     switch (opcodeValue) {
/*    */     case 256:
/* 66 */       return Opcode.PACKED_SWITCH_PAYLOAD;
/*    */     case 512:
/* 68 */       return Opcode.SPARSE_SWITCH_PAYLOAD;
/*    */     case 768:
/* 70 */       return Opcode.ARRAY_PAYLOAD;
/*    */     }
/* 72 */     if ((opcodeValue >= 0) && (opcodeValue < this.opcodesByValue.length)) {
/* 73 */       return this.opcodesByValue[opcodeValue];
/*    */     }
/* 75 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.Opcodes
 * JD-Core Version:    0.6.0
 */