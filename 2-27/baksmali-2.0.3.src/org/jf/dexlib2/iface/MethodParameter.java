package org.jf.dexlib2.iface;

import java.util.Set;
import org.jf.dexlib2.iface.debug.LocalInfo;
import org.jf.dexlib2.iface.reference.TypeReference;

public abstract interface MethodParameter extends LocalInfo, TypeReference
{
  public abstract String getType();

  public abstract Set<? extends Annotation> getAnnotations();

  public abstract String getName();
}

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.MethodParameter
 * JD-Core Version:    0.6.0
 */