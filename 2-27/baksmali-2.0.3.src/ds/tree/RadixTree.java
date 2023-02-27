package ds.tree;

public abstract interface RadixTree<T>
{
  public abstract void insert(String paramString, T paramT);

  public abstract T find(String paramString);

  public abstract boolean replace(String paramString, T paramT);

  public abstract long getSize();
}

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     ds.tree.RadixTree
 * JD-Core Version:    0.6.0
 */