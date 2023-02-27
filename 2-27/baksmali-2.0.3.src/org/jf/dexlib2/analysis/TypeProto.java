package org.jf.dexlib2.analysis;

import org.jf.dexlib2.iface.reference.FieldReference;
import org.jf.dexlib2.iface.reference.MethodReference;

public abstract interface TypeProto
{
  public abstract ClassPath getClassPath();

  public abstract String getType();

  public abstract boolean isInterface();

  public abstract String getSuperclass();

  public abstract TypeProto getCommonSuperclass(TypeProto paramTypeProto);

  public abstract FieldReference getFieldByOffset(int paramInt);

  public abstract MethodReference getMethodByVtableIndex(int paramInt);
}

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.analysis.TypeProto
 * JD-Core Version:    0.6.0
 */