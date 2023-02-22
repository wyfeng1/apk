package org.fmod;

import android.media.AudioRecord;
import android.util.Log;
import java.nio.ByteBuffer;

final class a
  implements Runnable
{
  private final FMODAudioDevice a;
  private final ByteBuffer b;
  private final int c;
  private final int d;
  private final int e;
  private volatile Thread f;
  private volatile boolean g;
  private AudioRecord h;
  private boolean i;

  a(FMODAudioDevice paramFMODAudioDevice, int paramInt1, int paramInt2)
  {
    this.a = paramFMODAudioDevice;
    this.c = paramInt1;
    this.d = paramInt2;
    this.e = 2;
    this.b = ByteBuffer.allocateDirect(AudioRecord.getMinBufferSize(paramInt1, paramInt2, 2));
  }

  private void d()
  {
    AudioRecord localAudioRecord = this.h;
    if (localAudioRecord != null)
    {
      if (localAudioRecord.getState() == 1)
        this.h.stop();
      this.h.release();
      this.h = null;
    }
    this.b.position(0);
    this.i = false;
  }

  public final int a()
  {
    return this.b.capacity();
  }

  public final void b()
  {
    if (this.f != null)
      c();
    this.g = true;
    this.f = new Thread(this);
    this.f.start();
  }

  public final void c()
  {
    while (this.f != null)
    {
      this.g = false;
      try
      {
        this.f.join();
        this.f = null;
      }
      catch (InterruptedException localInterruptedException)
      {
      }
    }
  }

  public final void run()
  {
    int j = 3;
    while (this.g)
    {
      int k = j;
      if (!this.i)
      {
        k = j;
        if (j > 0)
        {
          d();
          localObject = new AudioRecord(1, this.c, this.d, this.e, this.b.capacity());
          this.h = ((AudioRecord)localObject);
          k = ((AudioRecord)localObject).getState();
          boolean bool = true;
          if (k != 1)
            bool = false;
          this.i = bool;
          if (bool)
          {
            this.b.position(0);
            this.h.startRecording();
            k = 3;
          }
          else
          {
            localObject = new StringBuilder("AudioRecord failed to initialize (status ");
            ((StringBuilder)localObject).append(this.h.getState());
            ((StringBuilder)localObject).append(")");
            Log.e("FMOD", ((StringBuilder)localObject).toString());
            k = j - 1;
            d();
          }
        }
      }
      j = k;
      if (!this.i)
        continue;
      j = k;
      if (this.h.getRecordingState() != 3)
        continue;
      Object localObject = this.h;
      ByteBuffer localByteBuffer = this.b;
      j = ((AudioRecord)localObject).read(localByteBuffer, localByteBuffer.capacity());
      this.a.fmodProcessMicData(this.b, j);
      this.b.position(0);
      j = k;
    }
    d();
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     org.fmod.a
 * JD-Core Version:    0.6.0
 */