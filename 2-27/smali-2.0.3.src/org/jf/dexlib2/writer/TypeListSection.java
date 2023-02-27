package org.jf.dexlib2.writer;

import java.util.Collection;

public abstract interface TypeListSection<TypeKey, TypeListKey> extends NullableOffsetSection<TypeListKey>
{
  public abstract int getNullableItemOffset(TypeListKey paramTypeListKey);

  public abstract Collection<? extends TypeKey> getTypes(TypeListKey paramTypeListKey);
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.TypeListSection
 * JD-Core Version:    0.6.0
 */