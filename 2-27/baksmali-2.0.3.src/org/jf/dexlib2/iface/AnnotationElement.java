package org.jf.dexlib2.iface;

import org.jf.dexlib2.iface.value.EncodedValue;

public abstract interface AnnotationElement extends Comparable<AnnotationElement>
{
  public abstract String getName();

  public abstract EncodedValue getValue();
}

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.AnnotationElement
 * JD-Core Version:    0.6.0
 */