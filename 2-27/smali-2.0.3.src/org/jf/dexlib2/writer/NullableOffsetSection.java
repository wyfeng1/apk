package org.jf.dexlib2.writer;

public abstract interface NullableOffsetSection<Key> extends OffsetSection<Key>
{
  public abstract int getNullableItemOffset(Key paramKey);
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.NullableOffsetSection
 * JD-Core Version:    0.6.0
 */