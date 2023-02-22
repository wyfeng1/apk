package com.unity3d.splash.services.ads.video;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Build.VERSION;
import android.widget.VideoView;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import java.util.Timer;
import java.util.TimerTask;

public class VideoPlayerView extends VideoView
{
  private boolean _infoListenerEnabled = true;
  private MediaPlayer _mediaPlayer = null;
  private Timer _prepareTimer;
  private int _progressEventInterval = 500;
  private Timer _videoTimer;
  private String _videoUrl;
  private Float _volume = null;

  public VideoPlayerView(Context paramContext)
  {
    super(paramContext);
  }

  private void startPrepareTimer(long paramLong)
  {
    Timer localTimer = new Timer();
    this._prepareTimer = localTimer;
    localTimer.schedule(new TimerTask()
    {
      public void run()
      {
        if (!VideoPlayerView.this.isPlaying())
        {
          WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.PREPARE_TIMEOUT, new Object[] { VideoPlayerView.access$000(VideoPlayerView.this) });
          StringBuilder localStringBuilder = new StringBuilder("Video player prepare timeout: ");
          localStringBuilder.append(VideoPlayerView.this._videoUrl);
          DeviceLog.error(localStringBuilder.toString());
        }
      }
    }
    , paramLong);
  }

  private void startVideoProgressTimer()
  {
    Timer localTimer = new Timer();
    this._videoTimer = localTimer;
    1 local1 = new TimerTask()
    {
      public void run()
      {
        boolean bool;
        try
        {
          bool = VideoPlayerView.this.isPlaying();
          try
          {
            WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.PROGRESS, new Object[] { Integer.valueOf(VideoPlayerView.this.getCurrentPosition()) });
            return;
          }
          catch (IllegalStateException localIllegalStateException1)
          {
          }
        }
        catch (IllegalStateException localIllegalStateException2)
        {
          bool = false;
        }
        DeviceLog.exception("Exception while sending current position to webapp", localIllegalStateException2);
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.ILLEGAL_STATE, new Object[] { VideoPlayerEvent.PROGRESS, VideoPlayerView.access$000(VideoPlayerView.this), Boolean.valueOf(bool) });
      }
    };
    int i = this._progressEventInterval;
    localTimer.scheduleAtFixedRate(local1, i, i);
  }

  public int getProgressEventInterval()
  {
    return this._progressEventInterval;
  }

  public int[] getVideoViewRectangle()
  {
    int[] arrayOfInt = new int[2];
    getLocationInWindow(arrayOfInt);
    return new int[] { arrayOfInt[0], arrayOfInt[1], getMeasuredWidth(), getMeasuredHeight() };
  }

  public float getVolume()
  {
    return this._volume.floatValue();
  }

  public void pause()
  {
    try
    {
      super.pause();
      stopVideoProgressTimer();
      WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.PAUSE, new Object[] { this._videoUrl });
      return;
    }
    catch (Exception localException)
    {
      WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.PAUSE_ERROR, new Object[] { this._videoUrl });
      DeviceLog.exception("Error pausing video", localException);
    }
  }

  public void play()
  {
    DeviceLog.entered();
    setOnCompletionListener(new MediaPlayer.OnCompletionListener()
    {
      public void onCompletion(MediaPlayer paramMediaPlayer)
      {
        if (paramMediaPlayer != null)
          VideoPlayerView.access$102(VideoPlayerView.this, paramMediaPlayer);
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.COMPLETED, new Object[] { VideoPlayerView.access$000(VideoPlayerView.this) });
        VideoPlayerView.this.stopVideoProgressTimer();
      }
    });
    start();
    stopVideoProgressTimer();
    startVideoProgressTimer();
    WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.PLAY, new Object[] { this._videoUrl });
  }

  public boolean prepare(String paramString, float paramFloat, int paramInt)
  {
    DeviceLog.entered();
    this._videoUrl = paramString;
    setOnPreparedListener(new MediaPlayer.OnPreparedListener(paramFloat)
    {
      public void onPrepared(MediaPlayer paramMediaPlayer)
      {
        VideoPlayerView.this.stopPrepareTimer();
        if (paramMediaPlayer != null)
          VideoPlayerView.access$102(VideoPlayerView.this, paramMediaPlayer);
        VideoPlayerView.this.setVolume(Float.valueOf(this.val$initialVolume));
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.PREPARED, new Object[] { VideoPlayerView.access$000(VideoPlayerView.this), Integer.valueOf(paramMediaPlayer.getDuration()), Integer.valueOf(paramMediaPlayer.getVideoWidth()), Integer.valueOf(paramMediaPlayer.getVideoHeight()) });
      }
    });
    setOnErrorListener(new MediaPlayer.OnErrorListener()
    {
      public boolean onError(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
      {
        VideoPlayerView.this.stopPrepareTimer();
        if (paramMediaPlayer != null)
          VideoPlayerView.access$102(VideoPlayerView.this, paramMediaPlayer);
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.GENERIC_ERROR, new Object[] { VideoPlayerView.access$000(VideoPlayerView.this), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) });
        VideoPlayerView.this.stopVideoProgressTimer();
        return true;
      }
    });
    setInfoListenerEnabled(this._infoListenerEnabled);
    if (paramInt > 0)
      startPrepareTimer(paramInt);
    try
    {
      setVideoPath(this._videoUrl);
      return true;
    }
    catch (Exception localException)
    {
      WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.PREPARE_ERROR, new Object[] { this._videoUrl });
      paramString = new StringBuilder("Error preparing video: ");
      paramString.append(this._videoUrl);
      DeviceLog.exception(paramString.toString(), localException);
    }
    return false;
  }

  public void seekTo(int paramInt)
  {
    try
    {
      super.seekTo(paramInt);
      WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.SEEKTO, new Object[] { this._videoUrl });
      return;
    }
    catch (Exception localException)
    {
      WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.SEEKTO_ERROR, new Object[] { this._videoUrl });
      DeviceLog.exception("Error seeking video", localException);
    }
  }

  public void setInfoListenerEnabled(boolean paramBoolean)
  {
    this._infoListenerEnabled = paramBoolean;
    if (Build.VERSION.SDK_INT > 16)
    {
      if (this._infoListenerEnabled)
      {
        setOnInfoListener(new MediaPlayer.OnInfoListener()
        {
          public boolean onInfo(MediaPlayer paramMediaPlayer, int paramInt1, int paramInt2)
          {
            WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.INFO, new Object[] { VideoPlayerView.access$000(VideoPlayerView.this), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2) });
            return true;
          }
        });
        return;
      }
      setOnInfoListener(null);
    }
  }

  public void setProgressEventInterval(int paramInt)
  {
    this._progressEventInterval = paramInt;
    if (this._videoTimer != null)
    {
      stopVideoProgressTimer();
      startVideoProgressTimer();
    }
  }

  public void setVolume(Float paramFloat)
  {
    try
    {
      this._mediaPlayer.setVolume(paramFloat.floatValue(), paramFloat.floatValue());
      this._volume = paramFloat;
      return;
    }
    catch (Exception paramFloat)
    {
      DeviceLog.exception("MediaPlayer generic error", paramFloat);
    }
  }

  public void stop()
  {
    stopPlayback();
    stopVideoProgressTimer();
    WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.VIDEOPLAYER, VideoPlayerEvent.STOP, new Object[] { this._videoUrl });
  }

  public void stopPrepareTimer()
  {
    Timer localTimer = this._prepareTimer;
    if (localTimer != null)
    {
      localTimer.cancel();
      this._prepareTimer.purge();
      this._prepareTimer = null;
    }
  }

  public void stopVideoProgressTimer()
  {
    Timer localTimer = this._videoTimer;
    if (localTimer != null)
    {
      localTimer.cancel();
      this._videoTimer.purge();
      this._videoTimer = null;
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.video.VideoPlayerView
 * JD-Core Version:    0.6.0
 */