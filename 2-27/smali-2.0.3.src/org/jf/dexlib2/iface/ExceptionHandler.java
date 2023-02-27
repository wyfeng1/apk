package org.jf.dexlib2.iface;

import org.jf.dexlib2.iface.reference.TypeReference;

public abstract interface ExceptionHandler extends Comparable<ExceptionHandler>
{
  public abstract String getExceptionType();

  public abstract TypeReference getExceptionTypeReference();

  public abstract int getHandlerCodeAddress();
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.ExceptionHandler
 * JD-Core Version:    0.6.0
 */