package org.jf.dexlib2.writer;

import org.jf.dexlib2.iface.reference.MethodReference;

public abstract interface MethodSection<StringKey, TypeKey, ProtoKey, MethodRefKey extends MethodReference, MethodKey> extends IndexSection<MethodRefKey>
{
  public abstract TypeKey getDefiningClass(MethodRefKey paramMethodRefKey);

  public abstract ProtoKey getPrototype(MethodRefKey paramMethodRefKey);

  public abstract ProtoKey getPrototype(MethodKey paramMethodKey);

  public abstract StringKey getName(MethodRefKey paramMethodRefKey);

  public abstract int getMethodIndex(MethodKey paramMethodKey);
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.MethodSection
 * JD-Core Version:    0.6.0
 */