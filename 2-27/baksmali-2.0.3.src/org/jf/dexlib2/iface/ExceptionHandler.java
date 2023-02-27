package org.jf.dexlib2.iface;

public abstract interface ExceptionHandler extends Comparable<ExceptionHandler>
{
  public abstract String getExceptionType();

  public abstract int getHandlerCodeAddress();
}

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.ExceptionHandler
 * JD-Core Version:    0.6.0
 */