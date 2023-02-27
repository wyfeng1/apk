package org.jf.dexlib2.iface;

import java.util.Set;
import org.jf.dexlib2.iface.reference.FieldReference;
import org.jf.dexlib2.iface.value.EncodedValue;

public abstract interface Field extends FieldReference
{
  public abstract String getDefiningClass();

  public abstract String getName();

  public abstract String getType();

  public abstract int getAccessFlags();

  public abstract EncodedValue getInitialValue();

  public abstract Set<? extends Annotation> getAnnotations();
}

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.Field
 * JD-Core Version:    0.6.0
 */