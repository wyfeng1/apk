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
/*    */   public Opcode getOpcodeByName(String opcodeName)
/*    */   {
/* 59 */     return (Opcode)this.opcodesByName.get(opcodeName.toLowerCase());
/*    */   }
/*    */ }

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.Opcodes
 * JD-Core Version:    0.6.0
 */