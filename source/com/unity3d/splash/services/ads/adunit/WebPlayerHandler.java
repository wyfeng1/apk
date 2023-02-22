package com.unity3d.splash.services.ads.adunit;

import android.os.Bundle;
import android.view.View;
import com.unity3d.splash.services.ads.webplayer.WebPlayer;
import com.unity3d.splash.services.ads.webplayer.WebPlayerSettingsCache;
import com.unity3d.splash.services.core.misc.ViewUtilities;

public class WebPlayerHandler
  implements IAdUnitViewHandler
{
  private WebPlayer _webPlayer;

  public boolean create(AdUnitActivity paramAdUnitActivity)
  {
    if (this._webPlayer == null)
    {
      WebPlayerSettingsCache localWebPlayerSettingsCache = WebPlayerSettingsCache.getInstance();
      paramAdUnitActivity = new WebPlayer(paramAdUnitActivity, "webplayer", localWebPlayerSettingsCache.getWebSettings("webplayer"), localWebPlayerSettingsCache.getWebPlayerSettings("webplayer"));
      this._webPlayer = paramAdUnitActivity;
      paramAdUnitActivity.setEventSettings(localWebPlayerSettingsCache.getWebPlayerEventSettings("webplayer"));
    }
    return true;
  }

  public boolean destroy()
  {
    WebPlayer localWebPlayer = this._webPlayer;
    if (localWebPlayer != null)
    {
      ViewUtilities.removeViewFromParent(localWebPlayer);
      this._webPlayer.destroy();
    }
    this._webPlayer = null;
    return true;
  }

  public View getView()
  {
    return this._webPlayer;
  }

  public void onCreate(AdUnitActivity paramAdUnitActivity, Bundle paramBundle)
  {
    create(paramAdUnitActivity);
  }

  public void onDestroy(AdUnitActivity paramAdUnitActivity)
  {
    if (paramAdUnitActivity.isFinishing())
      destroy();
  }

  public void onPause(AdUnitActivity paramAdUnitActivity)
  {
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
 * Qualified Name:     com.unity3d.splash.services.ads.adunit.WebPlayerHandler
 * JD-Core Version:    0.6.0
 */