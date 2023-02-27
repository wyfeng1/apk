package org.jf.dexlib2.iface.value;

import java.util.Set;
import org.jf.dexlib2.iface.AnnotationElement;

public abstract interface AnnotationEncodedValue extends EncodedValue
{
  public abstract String getType();

  public abstract Set<? extends AnnotationElement> getElements();
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.value.AnnotationEncodedValue
 * JD-Core Version:    0.6.0
 */