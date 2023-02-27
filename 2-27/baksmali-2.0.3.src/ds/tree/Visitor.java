package ds.tree;

public abstract interface Visitor<T, R>
{
  public abstract void visit(String paramString, RadixTreeNode<T> paramRadixTreeNode1, RadixTreeNode<T> paramRadixTreeNode2);

  public abstract R getResult();
}

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     ds.tree.Visitor
 * JD-Core Version:    0.6.0
 */