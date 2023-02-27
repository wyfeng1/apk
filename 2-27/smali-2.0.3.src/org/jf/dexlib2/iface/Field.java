package org.jf.dexlib2.iface;

import org.jf.dexlib2.iface.reference.FieldReference;
import org.jf.dexlib2.iface.value.EncodedValue;

public abstract interface Field extends FieldReference
{
  public abstract int getAccessFlags();

  public abstract EncodedValue getInitialValue();
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.Field
 * JD-Core Version:    0.6.0
 */