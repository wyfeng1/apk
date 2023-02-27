package org.antlr.runtime.tree;

import org.antlr.runtime.IntStream;

public abstract interface TreeNodeStream extends IntStream
{
  public abstract Object LT(int paramInt);

  public abstract TreeAdaptor getTreeAdaptor();

  public abstract String toString(Object paramObject1, Object paramObject2);
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.tree.TreeNodeStream
 * JD-Core Version:    0.6.0
 */