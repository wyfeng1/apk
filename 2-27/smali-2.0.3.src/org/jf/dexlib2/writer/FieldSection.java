package org.jf.dexlib2.writer;

import org.jf.dexlib2.iface.reference.FieldReference;

public abstract interface FieldSection<StringKey, TypeKey, FieldRefKey extends FieldReference, FieldKey> extends IndexSection<FieldRefKey>
{
  public abstract TypeKey getDefiningClass(FieldRefKey paramFieldRefKey);

  public abstract TypeKey getFieldType(FieldRefKey paramFieldRefKey);

  public abstract StringKey getName(FieldRefKey paramFieldRefKey);

  public abstract int getFieldIndex(FieldKey paramFieldKey);
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.FieldSection
 * JD-Core Version:    0.6.0
 */