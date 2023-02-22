package org.fmod;

import android.media.AudioTrack;
import android.util.Log;
import java.nio.ByteBuffer;

public class FMODAudioDevice
  implements Runnable
{
  private static int h = 0;
  private static int i = 1;
  private static int j = 2;
  private static int k = 3;
  private static int l = 4;
  private volatile Thread a = null;
  private volatile boolean b = false;
  private AudioTrack c = null;
  private boolean d = false;
  private ByteBuffer e = null;
  private byte[] f = null;
  private volatile a g;

  private native int fmodGetInfo(int paramInt);

  private native int fmodProcess(ByteBuffer paramByteBuffer);

  private void releaseAudioTrack()
  {
    AudioTrack localAudioTrack = this.c;
    if (localAudioTrack != null)
    {
      if (localAudioTrack.getState() == 1)
        this.c.stop();
      this.c.release();
      this.c = null;
    }
    this.e = null;
    this.f = null;
    this.d = false;
  }

  public void close()
  {
    monitorenter;
    try
    {
      stop();
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  native int fmodProcessMicData(ByteBuffer paramByteBuffer, int paramInt);

  public boolean isRunning()
  {
    return (this.a != null) && (this.a.isAlive());
  }

  public void run()
  {
    int m = 3;
    while (this.b)
    {
      int n = m;
      Object localObject;
      if (!this.d)
      {
        n = m;
        if (m > 0)
        {
          releaseAudioTrack();
          int i1 = fmodGetInfo(h);
          if (fmodGetInfo(l) == 1)
            n = 4;
          else
            n = 12;
          int i2 = AudioTrack.getMinBufferSize(i1, n, 2);
          int i3 = fmodGetInfo(l) * 2;
          int i4 = Math.round(i2 * 1.1F) & (i3 - 1 ^ 0xFFFFFFFF);
          int i5 = fmodGetInfo(i);
          i2 = fmodGetInfo(j) * i5 * i3;
          if (i2 <= i4)
            i2 = i4;
          localObject = new AudioTrack(3, i1, n, 2, i2, 1);
          this.c = ((AudioTrack)localObject);
          boolean bool;
          if (((AudioTrack)localObject).getState() == 1)
            bool = true;
          else
            bool = false;
          this.d = bool;
          if (bool)
          {
            localObject = ByteBuffer.allocateDirect(i5 * i3);
            this.e = ((ByteBuffer)localObject);
            this.f = new byte[((ByteBuffer)localObject).capacity()];
            this.c.play();
            n = 3;
          }
          else
          {
            localObject = new StringBuilder("AudioTrack failed to initialize (status ");
            ((StringBuilder)localObject).append(this.c.getState());
            ((StringBuilder)localObject).append(")");
            Log.e("FMOD", ((StringBuilder)localObject).toString());
            releaseAudioTrack();
            n = m - 1;
          }
        }
      }
      m = n;
      if (!this.d)
        continue;
      if (fmodGetInfo(k) == 1)
      {
        fmodProcess(this.e);
        localObject = this.e;
        ((ByteBuffer)localObject).get(this.f, 0, ((ByteBuffer)localObject).capacity());
        this.c.write(this.f, 0, this.e.capacity());
        this.e.position(0);
        m = n;
        continue;
      }
      releaseAudioTrack();
      m = n;
    }
    releaseAudioTrack();
  }

  public void start()
  {
    monitorenter;
    try
    {
      if (this.a != null)
        stop();
      Thread localThread = new java/lang/Thread;
      localThread.<init>(this, "FMODAudioDevice");
      this.a = localThread;
      this.a.setPriority(10);
      this.b = true;
      this.a.start();
      if (this.g != null)
        this.g.b();
      return;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  public int startAudioRecord(int paramInt1, int paramInt2, int paramInt3)
  {
    monitorenter;
    try
    {
      if (this.g == null)
      {
        a locala = new org/fmod/a;
        locala.<init>(this, paramInt1, paramInt2);
        this.g = locala;
        this.g.b();
      }
      paramInt1 = this.g.a();
      return paramInt1;
    }
    finally
    {
      monitorexit;
    }
    throw localObject;
  }

  // ERROR //
  public void stop()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 38	org/fmod/FMODAudioDevice:a	Ljava/lang/Thread;
    //   6: ifnull +23 -> 29
    //   9: aload_0
    //   10: iconst_0
    //   11: putfield 40	org/fmod/FMODAudioDevice:b	Z
    //   14: aload_0
    //   15: getfield 38	org/fmod/FMODAudioDevice:a	Ljava/lang/Thread;
    //   18: invokevirtual 187	java/lang/Thread:join	()V
    //   21: aload_0
    //   22: aconst_null
    //   23: putfield 38	org/fmod/FMODAudioDevice:a	Ljava/lang/Thread;
    //   26: goto -24 -> 2
    //   29: aload_0
    //   30: getfield 172	org/fmod/FMODAudioDevice:g	Lorg/fmod/a;
    //   33: ifnull +10 -> 43
    //   36: aload_0
    //   37: getfield 172	org/fmod/FMODAudioDevice:g	Lorg/fmod/a;
    //   40: invokevirtual 189	org/fmod/a:c	()V
    //   43: aload_0
    //   44: monitorexit
    //   45: return
    //   46: astore_1
    //   47: aload_0
    //   48: monitorexit
    //   49: aload_1
    //   50: athrow
    //   51: astore_1
    //   52: goto -50 -> 2
    //
    // Exception table:
    //   from	to	target	type
    //   2	14	46	finally
    //   14	26	46	finally
    //   29	43	46	finally
    //   14	26	51	java/lang/InterruptedException
  }

  public void stopAudioRecord()
  {
    monitorenter;
    try
    {
      if (this.g != null)
      {
        this.g.c();
        this.g = null;
      }
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     org.fmod.FMODAudioDevice
 * JD-Core Version:    0.6.0
 */