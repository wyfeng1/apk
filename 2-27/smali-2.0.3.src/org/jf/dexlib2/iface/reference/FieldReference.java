package org.jf.dexlib2.iface.reference;

public abstract interface FieldReference extends Comparable<FieldReference>, Reference
{
  public abstract String getDefiningClass();

  public abstract String getName();

  public abstract String getType();

  public abstract int compareTo(FieldReference paramFieldReference);
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.reference.FieldReference
 * JD-Core Version:    0.6.0
 */