package org.jf.dexlib2.iface;

import java.util.Set;

public abstract interface Annotation extends Comparable<Annotation>
{
  public abstract int getVisibility();

  public abstract String getType();

  public abstract Set<? extends AnnotationElement> getElements();
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.Annotation
 * JD-Core Version:    0.6.0
 */