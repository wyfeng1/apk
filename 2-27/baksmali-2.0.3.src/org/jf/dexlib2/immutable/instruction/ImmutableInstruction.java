/*     */ package org.jf.dexlib2.immutable.instruction;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import org.jf.dexlib2.Format;
/*     */ import org.jf.dexlib2.Opcode;
/*     */ import org.jf.dexlib2.iface.instruction.Instruction;
/*     */ import org.jf.dexlib2.iface.instruction.formats.ArrayPayload;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction10t;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction10x;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction11n;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction11x;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction12x;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction20bc;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction20t;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction21c;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction21ih;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction21lh;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction21s;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction21t;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction22b;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction22c;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction22cs;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction22s;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction22t;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction22x;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction23x;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction30t;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction31c;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction31i;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction31t;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction32x;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction35c;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction35mi;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction35ms;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction3rc;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction3rmi;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction3rms;
/*     */ import org.jf.dexlib2.iface.instruction.formats.Instruction51l;
/*     */ import org.jf.dexlib2.iface.instruction.formats.PackedSwitchPayload;
/*     */ import org.jf.dexlib2.iface.instruction.formats.SparseSwitchPayload;
/*     */ import org.jf.dexlib2.iface.instruction.formats.UnknownInstruction;
/*     */ import org.jf.dexlib2.util.Preconditions;
/*     */ import org.jf.util.ImmutableConverter;
/*     */ 
/*     */ public abstract class ImmutableInstruction
/*     */   implements Instruction
/*     */ {
/*     */   protected final Opcode opcode;
/* 150 */   private static final ImmutableConverter<ImmutableInstruction, Instruction> CONVERTER = new ImmutableConverter()
/*     */   {
/*     */     protected boolean isImmutable(Instruction item)
/*     */     {
/* 154 */       return item instanceof ImmutableInstruction;
/*     */     }
/*     */ 
/*     */     protected ImmutableInstruction makeImmutable(Instruction item)
/*     */     {
/* 160 */       return ImmutableInstruction.of(item);
/*     */     }
/* 150 */   };
/*     */ 
/*     */   protected ImmutableInstruction(Opcode opcode)
/*     */   {
/*  48 */     Preconditions.checkFormat(opcode, getFormat());
/*  49 */     this.opcode = opcode;
/*     */   }
/*     */ 
/*     */   public static ImmutableInstruction of(Instruction instruction)
/*     */   {
/*  54 */     if ((instruction instanceof ImmutableInstruction)) {
/*  55 */       return (ImmutableInstruction)instruction;
/*     */     }
/*     */ 
/*  58 */     switch (2.$SwitchMap$org$jf$dexlib2$Format[instruction.getOpcode().format.ordinal()]) {
/*     */     case 1:
/*  60 */       return ImmutableInstruction10t.of((Instruction10t)instruction);
/*     */     case 2:
/*  62 */       if ((instruction instanceof UnknownInstruction)) {
/*  63 */         return ImmutableUnknownInstruction.of((UnknownInstruction)instruction);
/*     */       }
/*  65 */       return ImmutableInstruction10x.of((Instruction10x)instruction);
/*     */     case 3:
/*  67 */       return ImmutableInstruction11n.of((Instruction11n)instruction);
/*     */     case 4:
/*  69 */       return ImmutableInstruction11x.of((Instruction11x)instruction);
/*     */     case 5:
/*  71 */       return ImmutableInstruction12x.of((Instruction12x)instruction);
/*     */     case 6:
/*  73 */       return ImmutableInstruction20bc.of((Instruction20bc)instruction);
/*     */     case 7:
/*  75 */       return ImmutableInstruction20t.of((Instruction20t)instruction);
/*     */     case 8:
/*  77 */       return ImmutableInstruction21c.of((Instruction21c)instruction);
/*     */     case 9:
/*  79 */       return ImmutableInstruction21ih.of((Instruction21ih)instruction);
/*     */     case 10:
/*  81 */       return ImmutableInstruction21lh.of((Instruction21lh)instruction);
/*     */     case 11:
/*  83 */       return ImmutableInstruction21s.of((Instruction21s)instruction);
/*     */     case 12:
/*  85 */       return ImmutableInstruction21t.of((Instruction21t)instruction);
/*     */     case 13:
/*  87 */       return ImmutableInstruction22b.of((Instruction22b)instruction);
/*     */     case 14:
/*  89 */       return ImmutableInstruction22c.of((Instruction22c)instruction);
/*     */     case 15:
/*  91 */       return ImmutableInstruction22cs.of((Instruction22cs)instruction);
/*     */     case 16:
/*  93 */       return ImmutableInstruction22s.of((Instruction22s)instruction);
/*     */     case 17:
/*  95 */       return ImmutableInstruction22t.of((Instruction22t)instruction);
/*     */     case 18:
/*  97 */       return ImmutableInstruction22x.of((Instruction22x)instruction);
/*     */     case 19:
/*  99 */       return ImmutableInstruction23x.of((Instruction23x)instruction);
/*     */     case 20:
/* 101 */       return ImmutableInstruction30t.of((Instruction30t)instruction);
/*     */     case 21:
/* 103 */       return ImmutableInstruction31c.of((Instruction31c)instruction);
/*     */     case 22:
/* 105 */       return ImmutableInstruction31i.of((Instruction31i)instruction);
/*     */     case 23:
/* 107 */       return ImmutableInstruction31t.of((Instruction31t)instruction);
/*     */     case 24:
/* 109 */       return ImmutableInstruction32x.of((Instruction32x)instruction);
/*     */     case 25:
/* 111 */       return ImmutableInstruction35c.of((Instruction35c)instruction);
/*     */     case 26:
/* 113 */       return ImmutableInstruction35mi.of((Instruction35mi)instruction);
/*     */     case 27:
/* 115 */       return ImmutableInstruction35ms.of((Instruction35ms)instruction);
/*     */     case 28:
/* 117 */       return ImmutableInstruction3rc.of((Instruction3rc)instruction);
/*     */     case 29:
/* 119 */       return ImmutableInstruction3rmi.of((Instruction3rmi)instruction);
/*     */     case 30:
/* 121 */       return ImmutableInstruction3rms.of((Instruction3rms)instruction);
/*     */     case 31:
/* 123 */       return ImmutableInstruction51l.of((Instruction51l)instruction);
/*     */     case 32:
/* 125 */       return ImmutablePackedSwitchPayload.of((PackedSwitchPayload)instruction);
/*     */     case 33:
/* 127 */       return ImmutableSparseSwitchPayload.of((SparseSwitchPayload)instruction);
/*     */     case 34:
/* 129 */       return ImmutableArrayPayload.of((ArrayPayload)instruction);
/*     */     }
/* 131 */     throw new RuntimeException("Unexpected instruction type");
/*     */   }
/*     */ 
/*     */   public Opcode getOpcode()
/*     */   {
/* 136 */     return this.opcode;
/*     */   }
/*     */   public abstract Format getFormat();
/*     */ 
/*     */   public int getCodeUnits() {
/* 142 */     return getFormat().size / 2;
/*     */   }
/*     */ 
/*     */   public static ImmutableList<ImmutableInstruction> immutableListOf(Iterable<? extends Instruction> list)
/*     */   {
/* 147 */     return CONVERTER.toList(list);
/*     */   }
/*     */ }

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.immutable.instruction.ImmutableInstruction
 * JD-Core Version:    0.6.0
 */