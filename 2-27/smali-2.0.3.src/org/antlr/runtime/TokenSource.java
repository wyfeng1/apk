package org.antlr.runtime;

public abstract interface TokenSource
{
  public abstract Token nextToken();

  public abstract String getSourceName();
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.antlr.runtime.TokenSource
 * JD-Core Version:    0.6.0
 */