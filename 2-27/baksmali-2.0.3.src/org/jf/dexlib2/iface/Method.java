package org.jf.dexlib2.iface;

import java.util.List;
import java.util.Set;
import org.jf.dexlib2.iface.reference.MethodReference;

public abstract interface Method extends MethodReference
{
  public abstract String getDefiningClass();

  public abstract String getName();

  public abstract List<? extends MethodParameter> getParameters();

  public abstract String getReturnType();

  public abstract int getAccessFlags();

  public abstract Set<? extends Annotation> getAnnotations();

  public abstract MethodImplementation getImplementation();
}

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.Method
 * JD-Core Version:    0.6.0
 */