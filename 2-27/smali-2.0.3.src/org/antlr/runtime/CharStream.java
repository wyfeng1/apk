package org.antlr.runtime;

public abstract interface CharStream extends IntStream
{
  public abstract String substring(int paramInt1, int paramInt2);

  public abstract int getLine();

  public abstract int getCharPositionInLine();
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.CharStream
 * JD-Core Version:    0.6.0
 */