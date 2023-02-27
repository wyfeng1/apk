package org.jf.dexlib2.writer;

import java.util.Collection;
import java.util.Map.Entry;

public abstract interface IndexSection<Key>
{
  public abstract int getItemIndex(Key paramKey);

  public abstract Collection<? extends Map.Entry<? extends Key, Integer>> getItems();
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.IndexSection
 * JD-Core Version:    0.6.0
 */