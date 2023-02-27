package org.jf.dexlib2.iface.value;

import java.util.List;

public abstract interface ArrayEncodedValue extends EncodedValue
{
  public abstract List<? extends EncodedValue> getValue();
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.value.ArrayEncodedValue
 * JD-Core Version:    0.6.0
 */