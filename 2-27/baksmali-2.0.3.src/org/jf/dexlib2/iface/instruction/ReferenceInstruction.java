package org.jf.dexlib2.iface.instruction;

import org.jf.dexlib2.iface.reference.Reference;

public abstract interface ReferenceInstruction extends Instruction
{
  public abstract Reference getReference();

  public abstract int getReferenceType();
}

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.instruction.ReferenceInstruction
 * JD-Core Version:    0.6.0
 */