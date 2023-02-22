package com.unity3d.splash.services.ads.api;

import android.os.Build.VERSION;
import com.unity3d.splash.services.ads.video.VideoPlayerError;
import com.unity3d.splash.services.ads.video.VideoPlayerEvent;
import com.unity3d.splash.services.ads.video.VideoPlayerView;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;

public class VideoPlayer
{
  private static VideoPlayerView _videoPlayerView;

  @WebViewExposed
  public static void getCurrentPosition(WebViewCallback paramWebViewCallback)
  {
    if (getVideoPlayerView() != null)
    {
      paramWebViewCallback.invoke(new Object[] { Integer.valueOf(getVideoPlayerView().getCurrentPosition()) });
      return;
    }
    paramWebViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void getProgressEventInterval(WebViewCallback paramWebViewCallback)
  {
    if (getVideoPlayerView() != null)
    {
      paramWebViewCallback.invoke(new Object[] { Integer.valueOf(getVideoPlayerView().getProgressEventInterval()) });
      return;
    }
    paramWebViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
  }

  public static VideoPlayerView getVideoPlayerView()
  {
    return _videoPlayerView;
  }

  @WebViewExposed
  public static void getVideoViewRectangle(WebViewCallback paramWebViewCallback)
  {
    Object localObject = getVideoPlayerView();
    if (localObject != null)
    {
      localObject = ((VideoPlayerView)localObject).getVideoViewRectangle();
      paramWebViewCallback.invoke(new Object[] { Integer.valueOf(localObject[0]), Integer.valueOf(localObject[1]), Integer.valueOf(localObject[2]), Integer.valueOf(localObject[3]) });
      return;
    }
    paramWebViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void getVolume(WebViewCallback paramWebViewCallback)
  {
    if (getVideoPlayerView() != null)
    {
      paramWebViewCallback.invoke(new Object[] { Float.valueOf(getVideoPlayerView().getVolume()) });
      return;
    }
    paramWebViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void pause(WebViewCallback paramWebViewCallback)
  {
    DeviceLog.debug("Pausing current video");
    Utilities.runOnUiThread(new Runnable()
    {
      public final void run()
      {
        if (VideoPlayer.getVideoPlayerView() != null)
          VideoPlayer.getVideoPlayerView().pause();
      }
    });
    if (getVideoPlayerView() != null)
    {
      paramWebViewCallback.invoke(new Object[0]);
      return;
    }
    paramWebViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void play(WebViewCallback paramWebViewCallback)
  {
    DeviceLog.debug("Starting playback of prepared video");
    Utilities.runOnUiThread(new Runnable()
    {
      public final void run()
      {
        if (VideoPlayer.getVideoPlayerView() != null)
          VideoPlayer.getVideoPlayerView().play();
      }
    });
    if (getVideoPlayerView() != null)
    {
      paramWebViewCallback.invoke(new Object[0]);
      return;
    }
    paramWebViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void prepare(String paramString, Double paramDouble, WebViewCallback paramWebViewCallback)
  {
    prepare(paramString, paramDouble, Integer.valueOf(0), paramWebViewCallback);
  }

  @WebViewExposed
  public static void prepare(String paramString, Double paramDouble, Integer paramInteger, WebViewCallback paramWebViewCallback)
  {
    StringBuilder localStringBuilder = new StringBuilder("Preparing video for playback: ");
    localStringBuilder.append(paramString);
    DeviceLog.debug(localStringBuilder.toString());
    Utilities.runOnUiThread(new Runnable(paramString, paramDouble, paramInteger)
    {
      public final void run()
      {
        if (VideoPlayer.getVideoPlayerView() != null)
          VideoPlayer.getVideoPlayerView().prepare(this.val$url, this.val$initialVolume.floatValue(), this.val$timeout.intValue());
      }
    });
    if (getVideoPlayerView() != null)
    {
      paramWebViewCallback.invoke(new Object[] { paramString });
      return;
    }
    paramWebViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void seekTo(Integer paramInteger, WebViewCallback paramWebViewCallback)
  {
    StringBuilder localStringBuilder = new StringBuilder("Seeking video to time: ");
    localStringBuilder.append(paramInteger);
    DeviceLog.debug(localStringBuilder.toString());
    Utilities.runOnUiThread(new Runnable(paramInteger)
    {
      public final void run()
      {
        if (VideoPlayer.getVideoPlayerView() != null)
          VideoPlayer.getVideoPlayerView().seekTo(this.val$time.intValue());
      }
    });
    if (getVideoPlayerView() != null)
    {
      paramWebViewCallback.invoke(new Object[0]);
      return;
    }
    paramWebViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void setInfoListenerEnabled(boolean paramBoolean, WebViewCallback paramWebViewCallback)
  {
    if (Build.VERSION.SDK_INT > 16)
    {
      if (getVideoPlayerView() != null)
      {
        getVideoPlayerView().setInfoListenerEnabled(paramBoolean);
        paramWebViewCallback.invoke(new Object[] { WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.INFO, Boolean.valueOf(paramBoolean) });
        return;
      }
      paramWebViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
      return;
    }
    paramWebViewCallback.error(VideoPlayerError.API_LEVEL_ERROR, new Object[] { Integer.valueOf(Build.VERSION.SDK_INT), Boolean.valueOf(paramBoolean) });
  }

  @WebViewExposed
  public static void setProgressEventInterval(Integer paramInteger, WebViewCallback paramWebViewCallback)
  {
    Utilities.runOnUiThread(new Runnable(paramInteger)
    {
      public final void run()
      {
        if (VideoPlayer.getVideoPlayerView() != null)
          VideoPlayer.getVideoPlayerView().setProgressEventInterval(this.val$milliseconds.intValue());
      }
    });
    if (getVideoPlayerView() != null)
    {
      paramWebViewCallback.invoke(new Object[0]);
      return;
    }
    paramWebViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
  }

  public static void setVideoPlayerView(VideoPlayerView paramVideoPlayerView)
  {
    _videoPlayerView = paramVideoPlayerView;
  }

  @WebViewExposed
  public static void setVolume(Double paramDouble, WebViewCallback paramWebViewCallback)
  {
    StringBuilder localStringBuilder = new StringBuilder("Setting video volume: ");
    localStringBuilder.append(paramDouble);
    DeviceLog.debug(localStringBuilder.toString());
    if (getVideoPlayerView() != null)
    {
      getVideoPlayerView().setVolume(Float.valueOf(paramDouble.floatValue()));
      paramWebViewCallback.invoke(new Object[] { paramDouble });
      return;
    }
    paramWebViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void stop(WebViewCallback paramWebViewCallback)
  {
    DeviceLog.debug("Stopping current video");
    Utilities.runOnUiThread(new Runnable()
    {
      public final void run()
      {
        if (VideoPlayer.getVideoPlayerView() != null)
          VideoPlayer.getVideoPlayerView().stop();
      }
    });
    if (getVideoPlayerView() != null)
    {
      paramWebViewCallback.invoke(new Object[0]);
      return;
    }
    paramWebViewCallback.error(VideoPlayerError.VIDEOVIEW_NULL, new Object[0]);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.api.VideoPlayer
 * JD-Core Version:    0.6.0
 */