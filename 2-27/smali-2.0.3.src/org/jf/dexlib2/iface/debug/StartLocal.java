package org.jf.dexlib2.iface.debug;

import org.jf.dexlib2.iface.reference.StringReference;
import org.jf.dexlib2.iface.reference.TypeReference;

public abstract interface StartLocal extends DebugItem
{
  public abstract int getRegister();

  public abstract StringReference getNameReference();

  public abstract TypeReference getTypeReference();

  public abstract StringReference getSignatureReference();
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.debug.StartLocal
 * JD-Core Version:    0.6.0
 */