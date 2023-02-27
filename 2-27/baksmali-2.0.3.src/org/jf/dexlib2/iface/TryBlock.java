package org.jf.dexlib2.iface;

import java.util.List;

public abstract interface TryBlock<EH extends ExceptionHandler>
{
  public abstract int getStartCodeAddress();

  public abstract int getCodeUnitCount();

  public abstract List<? extends EH> getExceptionHandlers();
}

/* Location:           C:\Users\王银峰\Desktop\apk\baksmali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.iface.TryBlock
 * JD-Core Version:    0.6.0
 */