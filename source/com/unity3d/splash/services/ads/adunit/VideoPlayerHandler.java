package com.unity3d.splash.services.ads.adunit;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.unity3d.splash.services.ads.api.VideoPlayer;
import com.unity3d.splash.services.ads.video.VideoPlayerView;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.ViewUtilities;

public class VideoPlayerHandler
  implements IAdUnitViewHandler
{
  private RelativeLayout _videoContainer;
  private VideoPlayerView _videoView;

  public boolean create(AdUnitActivity paramAdUnitActivity)
  {
    DeviceLog.entered();
    if (this._videoContainer == null)
      this._videoContainer = new RelativeLayout(paramAdUnitActivity);
    if (this._videoView == null)
    {
      this._videoView = new VideoPlayerView(paramAdUnitActivity);
      paramAdUnitActivity = new RelativeLayout.LayoutParams(-1, -1);
      paramAdUnitActivity.addRule(11);
      paramAdUnitActivity.addRule(9);
      this._videoView.setLayoutParams(paramAdUnitActivity);
      this._videoContainer.addView(this._videoView);
      VideoPlayer.setVideoPlayerView(this._videoView);
    }
    return true;
  }

  public boolean destroy()
  {
    DeviceLog.entered();
    Object localObject = this._videoView;
    if (localObject != null)
    {
      ((VideoPlayerView)localObject).stopVideoProgressTimer();
      this._videoView.stopPlayback();
      ViewUtilities.removeViewFromParent(this._videoView);
      if (this._videoView.equals(VideoPlayer.getVideoPlayerView()))
        VideoPlayer.setVideoPlayerView(null);
      this._videoView = null;
    }
    localObject = this._videoContainer;
    if (localObject != null)
    {
      ViewUtilities.removeViewFromParent((View)localObject);
      this._videoContainer = null;
    }
    return true;
  }

  public View getView()
  {
    return this._videoContainer;
  }

  public void onCreate(AdUnitActivity paramAdUnitActivity, Bundle paramBundle)
  {
    create(paramAdUnitActivity);
  }

  public void onDestroy(AdUnitActivity paramAdUnitActivity)
  {
  }

  public void onPause(AdUnitActivity paramAdUnitActivity)
  {
    destroy();
  }

  public void onResume(AdUnitActivity paramAdUnitActivity)
  {
  }

  public void onStart(AdUnitActivity paramAdUnitActivity)
  {
  }

  public void onStop(AdUnitActivity paramAdUnitActivity)
  {
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.adunit.VideoPlayerHandler
 * JD-Core Version:    0.6.0
 */