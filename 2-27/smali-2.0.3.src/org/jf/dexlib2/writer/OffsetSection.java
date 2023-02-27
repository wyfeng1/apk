package org.jf.dexlib2.writer;

import java.util.Collection;
import java.util.Map.Entry;

public abstract interface OffsetSection<Key>
{
  public abstract int getItemOffset(Key paramKey);

  public abstract Collection<? extends Map.Entry<? extends Key, Integer>> getItems();
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.OffsetSection
 * JD-Core Version:    0.6.0
 */