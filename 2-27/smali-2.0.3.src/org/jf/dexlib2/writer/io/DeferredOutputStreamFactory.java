package org.jf.dexlib2.writer.io;

import java.io.IOException;

public abstract interface DeferredOutputStreamFactory
{
  public abstract DeferredOutputStream makeDeferredOutputStream()
    throws IOException;
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.io.DeferredOutputStreamFactory
 * JD-Core Version:    0.6.0
 */