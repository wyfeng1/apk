package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import java.io.FileInputStream;
import java.io.IOException;

public final class p extends FrameLayout
  implements MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnVideoSizeChangedListener, SurfaceHolder.Callback, MediaController.MediaPlayerControl
{
  private static boolean a = false;
  private final Context b;
  private final SurfaceView c;
  private final SurfaceHolder d;
  private final String e;
  private final int f;
  private final int g;
  private final boolean h;
  private final long i;
  private final long j;
  private final FrameLayout k;
  private final Display l;
  private int m;
  private int n;
  private int o;
  private int p;
  private MediaPlayer q;
  private MediaController r;
  private boolean s = false;
  private boolean t = false;
  private int u = 0;
  private boolean v = false;
  private boolean w = false;
  private a x;
  private b y;
  private volatile int z = 0;

  protected p(Context paramContext, String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, long paramLong1, long paramLong2, a parama)
  {
    super(paramContext);
    this.x = parama;
    this.b = paramContext;
    this.k = this;
    paramContext = new SurfaceView(paramContext);
    this.c = paramContext;
    paramContext = paramContext.getHolder();
    this.d = paramContext;
    paramContext.addCallback(this);
    this.k.setBackgroundColor(paramInt1);
    this.k.addView(this.c);
    this.l = ((WindowManager)this.b.getSystemService("window")).getDefaultDisplay();
    this.e = paramString;
    this.f = paramInt2;
    this.g = paramInt3;
    this.h = paramBoolean;
    this.i = paramLong1;
    this.j = paramLong2;
    if (a)
    {
      paramContext = new StringBuilder("fileName: ");
      paramContext.append(this.e);
      b(paramContext.toString());
    }
    if (a)
    {
      paramContext = new StringBuilder("backgroundColor: ");
      paramContext.append(paramInt1);
      b(paramContext.toString());
    }
    if (a)
    {
      paramContext = new StringBuilder("controlMode: ");
      paramContext.append(this.f);
      b(paramContext.toString());
    }
    if (a)
    {
      paramContext = new StringBuilder("scalingMode: ");
      paramContext.append(this.g);
      b(paramContext.toString());
    }
    if (a)
    {
      paramContext = new StringBuilder("isURL: ");
      paramContext.append(this.h);
      b(paramContext.toString());
    }
    if (a)
    {
      paramContext = new StringBuilder("videoOffset: ");
      paramContext.append(this.i);
      b(paramContext.toString());
    }
    if (a)
    {
      paramContext = new StringBuilder("videoLength: ");
      paramContext.append(this.j);
      b(paramContext.toString());
    }
    setFocusable(true);
    setFocusableInTouchMode(true);
  }

  private void a(int paramInt)
  {
    this.z = paramInt;
    a locala = this.x;
    if (locala != null)
      locala.a(this.z);
  }

  private static void b(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder("VideoPlayer: ");
    localStringBuilder.append(paramString);
    Log.i("Video", localStringBuilder.toString());
  }

  private void c()
  {
    Object localObject1 = this.q;
    if (localObject1 != null)
    {
      ((MediaPlayer)localObject1).setDisplay(this.d);
      if (!this.v)
      {
        if (a)
          b("Resuming playback");
        this.q.start();
      }
      return;
    }
    a(0);
    doCleanUp();
    try
    {
      localObject1 = new android/media/MediaPlayer;
      ((MediaPlayer)localObject1).<init>();
      this.q = ((MediaPlayer)localObject1);
      if (this.h)
      {
        ((MediaPlayer)localObject1).setDataSource(this.b, Uri.parse(this.e));
      }
      else
      {
        if (this.j != 0L)
        {
          localObject1 = new java/io/FileInputStream;
          ((FileInputStream)localObject1).<init>(this.e);
          this.q.setDataSource(((FileInputStream)localObject1).getFD(), this.i, this.j);
        }
        while (true)
        {
          ((FileInputStream)localObject1).close();
          break;
          localObject1 = getResources().getAssets();
          try
          {
            localObject1 = ((AssetManager)localObject1).openFd(this.e);
            this.q.setDataSource(((AssetFileDescriptor)localObject1).getFileDescriptor(), ((AssetFileDescriptor)localObject1).getStartOffset(), ((AssetFileDescriptor)localObject1).getLength());
            ((AssetFileDescriptor)localObject1).close();
          }
          catch (IOException localObject2)
          {
            localObject2 = new java/io/FileInputStream;
            ((FileInputStream)localObject2).<init>(this.e);
            this.q.setDataSource(((FileInputStream)localObject2).getFD());
          }
        }
      }
      this.q.setDisplay(this.d);
      this.q.setScreenOnWhilePlaying(true);
      this.q.setOnBufferingUpdateListener(this);
      this.q.setOnCompletionListener(this);
      this.q.setOnPreparedListener(this);
      this.q.setOnVideoSizeChangedListener(this);
      this.q.setAudioStreamType(3);
      this.q.prepareAsync();
      Object localObject2 = new com/unity3d/player/p$b;
      ((b)localObject2).<init>(this, this);
      this.y = ((b)localObject2);
      localObject2 = new java/lang/Thread;
      ((Thread)localObject2).<init>(this.y);
      ((Thread)localObject2).start();
      return;
    }
    catch (Exception localException)
    {
      if (a)
      {
        StringBuilder localStringBuilder = new StringBuilder("error: ");
        localStringBuilder.append(localException.getMessage());
        localStringBuilder.append(localException);
        b(localStringBuilder.toString());
      }
      a(2);
    }
  }

  private void d()
  {
    if (isPlaying())
      return;
    a(1);
    if (a)
      b("startVideoPlayback");
    updateVideoLayout();
    if (!this.v)
      start();
  }

  public final void CancelOnPrepare()
  {
    a(2);
  }

  final boolean a()
  {
    return this.v;
  }

  public final boolean canPause()
  {
    return true;
  }

  public final boolean canSeekBackward()
  {
    return true;
  }

  public final boolean canSeekForward()
  {
    return true;
  }

  protected final void destroyPlayer()
  {
    if (a)
      b("destroyPlayer");
    if (!this.v)
      pause();
    doCleanUp();
  }

  protected final void doCleanUp()
  {
    Object localObject = this.y;
    if (localObject != null)
    {
      ((b)localObject).a();
      this.y = null;
    }
    localObject = this.q;
    if (localObject != null)
    {
      ((MediaPlayer)localObject).release();
      this.q = null;
    }
    this.o = 0;
    this.p = 0;
    this.t = false;
    this.s = false;
  }

  public final int getAudioSessionId()
  {
    MediaPlayer localMediaPlayer = this.q;
    if (localMediaPlayer == null)
      return 0;
    return localMediaPlayer.getAudioSessionId();
  }

  public final int getBufferPercentage()
  {
    if (this.h)
      return this.u;
    return 100;
  }

  public final int getCurrentPosition()
  {
    MediaPlayer localMediaPlayer = this.q;
    if (localMediaPlayer == null)
      return 0;
    return localMediaPlayer.getCurrentPosition();
  }

  public final int getDuration()
  {
    MediaPlayer localMediaPlayer = this.q;
    if (localMediaPlayer == null)
      return 0;
    return localMediaPlayer.getDuration();
  }

  public final boolean isPlaying()
  {
    int i1;
    if ((this.t) && (this.s))
      i1 = 1;
    else
      i1 = 0;
    MediaPlayer localMediaPlayer = this.q;
    if (localMediaPlayer == null)
      return i1 == 0;
    return (localMediaPlayer.isPlaying()) || (i1 == 0);
  }

  public final void onBufferingUpdate(MediaPlayer paramMediaPlayer, int paramInt)
  {
    if (a)
    {
      paramMediaPlayer = new StringBuilder("onBufferingUpdate percent:");
      paramMediaPlayer.append(paramInt);
      b(paramMediaPlayer.toString());
    }
    this.u = paramInt;
  }

  public final void onCompletion(MediaPlayer paramMediaPlayer)
  {
    if (a)
      b("onCompletion called");
    destroyPlayer();
    a(3);
  }

  public final boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt != 4) && ((this.f != 2) || (paramInt == 0) || (paramKeyEvent.isSystem())))
    {
      MediaController localMediaController = this.r;
      if (localMediaController != null)
        return localMediaController.onKeyDown(paramInt, paramKeyEvent);
      return super.onKeyDown(paramInt, paramKeyEvent);
    }
    destroyPlayer();
    a(3);
    return true;
  }

  public final void onPrepared(MediaPlayer paramMediaPlayer)
  {
    if (a)
      b("onPrepared called");
    paramMediaPlayer = this.y;
    if (paramMediaPlayer != null)
    {
      paramMediaPlayer.a();
      this.y = null;
    }
    int i1 = this.f;
    if ((i1 == 0) || (i1 == 1))
    {
      paramMediaPlayer = new MediaController(this.b);
      this.r = paramMediaPlayer;
      paramMediaPlayer.setMediaPlayer(this);
      this.r.setAnchorView(this);
      this.r.setEnabled(true);
      paramMediaPlayer = this.b;
      if ((paramMediaPlayer instanceof Activity))
      {
        paramMediaPlayer = (Activity)paramMediaPlayer;
        this.r.setSystemUiVisibility(paramMediaPlayer.getWindow().getDecorView().getSystemUiVisibility());
      }
      this.r.show();
    }
    this.t = true;
    if ((1 != 0) && (this.s))
      d();
  }

  public final boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i1 = paramMotionEvent.getAction();
    if ((this.f == 2) && ((i1 & 0xFF) == 0))
    {
      destroyPlayer();
      a(3);
      return true;
    }
    MediaController localMediaController = this.r;
    if (localMediaController != null)
      return localMediaController.onTouchEvent(paramMotionEvent);
    return super.onTouchEvent(paramMotionEvent);
  }

  public final void onVideoSizeChanged(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
  {
    if (a)
    {
      paramMediaPlayer = new StringBuilder("onVideoSizeChanged called ");
      paramMediaPlayer.append(paramInt1);
      paramMediaPlayer.append("x");
      paramMediaPlayer.append(paramInt2);
      b(paramMediaPlayer.toString());
    }
    if ((paramInt1 != 0) && (paramInt2 != 0))
    {
      this.s = true;
      this.o = paramInt1;
      this.p = paramInt2;
      if ((this.t) && (1 != 0))
        d();
      return;
    }
    if (a)
    {
      paramMediaPlayer = new StringBuilder("invalid video width(");
      paramMediaPlayer.append(paramInt1);
      paramMediaPlayer.append(") or height(");
      paramMediaPlayer.append(paramInt2);
      paramMediaPlayer.append(")");
      b(paramMediaPlayer.toString());
    }
  }

  public final void pause()
  {
    MediaPlayer localMediaPlayer = this.q;
    if (localMediaPlayer == null)
      return;
    if (this.w)
      localMediaPlayer.pause();
    this.v = true;
  }

  public final void seekTo(int paramInt)
  {
    MediaPlayer localMediaPlayer = this.q;
    if (localMediaPlayer == null)
      return;
    localMediaPlayer.seekTo(paramInt);
  }

  public final void start()
  {
    if (a)
      b("Start");
    MediaPlayer localMediaPlayer = this.q;
    if (localMediaPlayer == null)
      return;
    if (this.w)
      localMediaPlayer.start();
    this.v = false;
  }

  public final void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
  {
    if (a)
    {
      paramSurfaceHolder = new StringBuilder("surfaceChanged called ");
      paramSurfaceHolder.append(paramInt1);
      paramSurfaceHolder.append(" ");
      paramSurfaceHolder.append(paramInt2);
      paramSurfaceHolder.append("x");
      paramSurfaceHolder.append(paramInt3);
      b(paramSurfaceHolder.toString());
    }
    if ((this.m != paramInt2) || (this.n != paramInt3))
    {
      this.m = paramInt2;
      this.n = paramInt3;
      if (this.w)
        updateVideoLayout();
    }
  }

  public final void surfaceCreated(SurfaceHolder paramSurfaceHolder)
  {
    if (a)
      b("surfaceCreated called");
    this.w = true;
    c();
  }

  public final void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
  {
    if (a)
      b("surfaceDestroyed called");
    this.w = false;
  }

  protected final void updateVideoLayout()
  {
    if (a)
      b("updateVideoLayout");
    if (this.q == null)
      return;
    Object localObject;
    if ((this.m == 0) || (this.n == 0))
    {
      WindowManager localWindowManager = (WindowManager)this.b.getSystemService("window");
      localObject = new DisplayMetrics();
      localWindowManager.getDefaultDisplay().getMetrics((DisplayMetrics)localObject);
      this.m = ((DisplayMetrics)localObject).widthPixels;
      this.n = ((DisplayMetrics)localObject).heightPixels;
    }
    int i1 = this.m;
    int i2 = this.n;
    int i6;
    int i7;
    if (this.s)
    {
      int i3 = this.o;
      float f1 = i3;
      int i4 = this.p;
      float f2 = f1 / i4;
      f1 = i1 / i2;
      int i5 = this.g;
      if (i5 == 1)
        if (f1 > f2);
      while (true)
      {
        i6 = (int)(i1 / f2);
        i7 = i1;
        break label248;
        do
        {
          i7 = (int)(i2 * f2);
          i6 = i2;
          break label248;
          if (i5 != 2)
            break;
        }
        while (f1 < f2);
      }
      i7 = i1;
      i6 = i2;
      if (i5 == 0)
      {
        i7 = i3;
        i6 = i4;
      }
    }
    else
    {
      i7 = i1;
      i6 = i2;
      if (a)
      {
        b("updateVideoLayout: Video size is not known yet");
        i6 = i2;
        i7 = i1;
      }
    }
    label248: if ((this.m != i7) || (this.n != i6))
    {
      if (a)
      {
        localObject = new StringBuilder("frameWidth = ");
        ((StringBuilder)localObject).append(i7);
        ((StringBuilder)localObject).append("; frameHeight = ");
        ((StringBuilder)localObject).append(i6);
        b(((StringBuilder)localObject).toString());
      }
      localObject = new FrameLayout.LayoutParams(i7, i6, 17);
      this.k.updateViewLayout(this.c, (ViewGroup.LayoutParams)localObject);
    }
  }

  public static abstract interface a
  {
    public abstract void a(int paramInt);
  }

  public final class b
    implements Runnable
  {
    private p b;
    private boolean c;

    public b(p arg2)
    {
      Object localObject;
      this.b = localObject;
      this.c = false;
    }

    public final void a()
    {
      this.c = true;
    }

    public final void run()
    {
      try
      {
        Thread.sleep(5000L);
      }
      catch (InterruptedException localInterruptedException)
      {
        Thread.currentThread().interrupt();
      }
      if (!this.c)
      {
        if (p.b())
          p.a("Stopping the video player due to timeout.");
        this.b.CancelOnPrepare();
      }
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.p
 * JD-Core Version:    0.6.0
 */