package org.jf.dexlib2.iface.instruction;

import org.jf.dexlib2.Opcode;

public abstract interface Instruction
{
  public abstract Opcode getOpcode();

  public abstract int getCodeUnits();
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.instruction.Instruction
 * JD-Core Version:    0.6.0
 */