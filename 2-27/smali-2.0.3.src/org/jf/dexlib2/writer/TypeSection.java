package org.jf.dexlib2.writer;

import org.jf.dexlib2.iface.reference.TypeReference;

public abstract interface TypeSection<StringKey, TypeKey, TypeRef extends TypeReference> extends NullableIndexSection<TypeKey>
{
  public abstract StringKey getString(TypeKey paramTypeKey);

  public abstract int getItemIndex(TypeRef paramTypeRef);
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.TypeSection
 * JD-Core Version:    0.6.0
 */