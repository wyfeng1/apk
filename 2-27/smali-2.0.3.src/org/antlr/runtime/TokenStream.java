package org.antlr.runtime;

public abstract interface TokenStream extends IntStream
{
  public abstract Token LT(int paramInt);

  public abstract String toString(int paramInt1, int paramInt2);

  public abstract String toString(Token paramToken1, Token paramToken2);
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.TokenStream
 * JD-Core Version:    0.6.0
 */