package org.jf.dexlib2.writer;

import org.jf.dexlib2.iface.reference.StringReference;

public abstract interface StringSection<StringKey, StringRef extends StringReference> extends NullableIndexSection<StringKey>
{
  public abstract int getItemIndex(StringRef paramStringRef);

  public abstract boolean hasJumboIndexes();
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.StringSection
 * JD-Core Version:    0.6.0
 */