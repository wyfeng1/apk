package org.jf.dexlib2.iface.instruction;

import java.util.List;

public abstract interface SwitchPayload extends PayloadInstruction
{
  public abstract List<? extends SwitchElement> getSwitchElements();
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.instruction.SwitchPayload
 * JD-Core Version:    0.6.0
 */