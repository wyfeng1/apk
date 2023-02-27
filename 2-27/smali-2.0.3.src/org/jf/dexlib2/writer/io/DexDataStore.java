package org.jf.dexlib2.writer.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract interface DexDataStore
{
  public abstract OutputStream outputAt(int paramInt);

  public abstract InputStream readAt(int paramInt);

  public abstract void close()
    throws IOException;
}

/* Location:           C:\Users\王银峰\Desktop\apk\smali-2.0.3.jar
 * Qualified Name:     org.jf.dexlib2.writer.io.DexDataStore
 * JD-Core Version:    0.6.0
 */