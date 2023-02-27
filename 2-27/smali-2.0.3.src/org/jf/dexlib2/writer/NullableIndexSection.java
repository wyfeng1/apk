package org.jf.dexlib2.writer;

public abstract interface NullableIndexSection<Key> extends IndexSection<Key>
{
  public abstract int getNullableItemIndex(Key paramKey);
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.NullableIndexSection
 * JD-Core Version:    0.6.0
 */