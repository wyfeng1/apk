package com.unity3d.player;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.PixelCopy;
import android.view.PixelCopy.OnPixelCopyFinishedListener;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;

final class h
  implements Application.ActivityLifecycleCallbacks
{
  WeakReference a = new WeakReference(null);
  Activity b;
  a c = null;

  h(Context paramContext)
  {
    if ((paramContext instanceof Activity))
    {
      paramContext = (Activity)paramContext;
      this.b = paramContext;
      paramContext.getApplication().registerActivityLifecycleCallbacks(this);
    }
  }

  public final void a()
  {
    Activity localActivity = this.b;
    if (localActivity != null)
      localActivity.getApplication().unregisterActivityLifecycleCallbacks(this);
  }

  public final void a(SurfaceView paramSurfaceView)
  {
    if ((PlatformSupport.NOUGAT_SUPPORT) && (this.c == null))
    {
      a locala = new a(this.b);
      this.c = locala;
      locala.a(paramSurfaceView);
    }
  }

  public final void a(ViewGroup paramViewGroup)
  {
    a locala = this.c;
    if ((locala != null) && (locala.getParent() == null))
    {
      paramViewGroup.addView(this.c);
      paramViewGroup.bringChildToFront(this.c);
    }
  }

  public final void b(ViewGroup paramViewGroup)
  {
    a locala = this.c;
    if ((locala != null) && (locala.getParent() != null))
      paramViewGroup.removeView(this.c);
  }

  public final void onActivityCreated(Activity paramActivity, Bundle paramBundle)
  {
  }

  public final void onActivityDestroyed(Activity paramActivity)
  {
  }

  public final void onActivityPaused(Activity paramActivity)
  {
  }

  public final void onActivityResumed(Activity paramActivity)
  {
    this.a = new WeakReference(paramActivity);
  }

  public final void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle)
  {
  }

  public final void onActivityStarted(Activity paramActivity)
  {
  }

  public final void onActivityStopped(Activity paramActivity)
  {
  }

  final class a extends View
    implements PixelCopy.OnPixelCopyFinishedListener
  {
    Bitmap a;

    a(Context arg2)
    {
      super();
    }

    public final void a(SurfaceView paramSurfaceView)
    {
      Bitmap localBitmap = Bitmap.createBitmap(paramSurfaceView.getWidth(), paramSurfaceView.getHeight(), Bitmap.Config.ARGB_8888);
      this.a = localBitmap;
      PixelCopy.request(paramSurfaceView, localBitmap, this, new Handler(Looper.getMainLooper()));
    }

    public final void onPixelCopyFinished(int paramInt)
    {
      if (paramInt == 0)
        setBackground(new LayerDrawable(new Drawable[] { new ColorDrawable(-16777216), new BitmapDrawable(getResources(), this.a) }));
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.h
 * JD-Core Version:    0.6.0
 */