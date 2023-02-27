package org.jf.dexlib2.iface;

import java.util.Set;
import org.jf.dexlib2.iface.reference.TypeReference;

public abstract interface ClassDef extends TypeReference
{
  public abstract String getType();

  public abstract int getAccessFlags();

  public abstract String getSuperclass();

  public abstract Set<String> getInterfaces();

  public abstract String getSourceFile();

  public abstract Set<? extends Annotation> getAnnotations();

  public abstract Iterable<? extends Field> getStaticFields();

  public abstract Iterable<? extends Field> getInstanceFields();

  public abstract Iterable<? extends Method> getDirectMethods();

  public abstract Iterable<? extends Method> getVirtualMethods();

  public abstract Iterable<? extends Method> getMethods();
}

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.ClassDef
 * JD-Core Version:    0.6.0
 */