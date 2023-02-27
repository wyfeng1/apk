package org.jf.dexlib2.iface.instruction.formats;

import java.util.List;
import org.jf.dexlib2.iface.instruction.PayloadInstruction;

public abstract interface ArrayPayload extends PayloadInstruction
{
  public abstract int getElementWidth();

  public abstract List<Number> getArrayElements();
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.instruction.formats.ArrayPayload
 * JD-Core Version:    0.6.0
 */