package org.antlr.runtime;

public abstract interface IntStream
{
  public abstract void consume();

  public abstract int LA(int paramInt);

  public abstract int mark();

  public abstract int index();

  public abstract void rewind(int paramInt);

  public abstract void rewind();

  public abstract void seek(int paramInt);

  public abstract int size();

  public abstract String getSourceName();
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.IntStream
 * JD-Core Version:    0.6.0
 */