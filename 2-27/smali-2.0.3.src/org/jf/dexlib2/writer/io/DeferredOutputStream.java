package org.jf.dexlib2.writer.io;

import java.io.IOException;
import java.io.OutputStream;

public abstract class DeferredOutputStream extends OutputStream
{
  public abstract void writeTo(OutputStream paramOutputStream)
    throws IOException;
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.io.DeferredOutputStream
 * JD-Core Version:    0.6.0
 */