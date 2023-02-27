package org.jf.dexlib2.iface.reference;

import java.util.List;

public abstract interface MethodReference extends Comparable<MethodReference>, Reference
{
  public abstract String getDefiningClass();

  public abstract String getName();

  public abstract List<? extends CharSequence> getParameterTypes();

  public abstract String getReturnType();

  public abstract int compareTo(MethodReference paramMethodReference);
}

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.reference.MethodReference
 * JD-Core Version:    0.6.0
 */