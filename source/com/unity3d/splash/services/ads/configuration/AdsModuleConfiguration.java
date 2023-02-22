package com.unity3d.splash.services.ads.configuration;

import android.os.ConditionVariable;
import com.unity3d.splash.IUnityAdsListener;
import com.unity3d.splash.UnityAds.UnityAdsError;
import com.unity3d.splash.services.ads.adunit.VideoPlayerHandler;
import com.unity3d.splash.services.ads.adunit.WebPlayerHandler;
import com.unity3d.splash.services.ads.adunit.WebViewHandler;
import com.unity3d.splash.services.ads.api.AdUnit;
import com.unity3d.splash.services.ads.api.Listener;
import com.unity3d.splash.services.ads.api.VideoPlayer;
import com.unity3d.splash.services.ads.webplayer.api.WebPlayer;
import com.unity3d.splash.services.core.configuration.Configuration;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AdsModuleConfiguration
  implements IAdsModuleConfiguration
{
  private InetAddress _address;

  public Map getAdUnitViewHandlers()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("videoplayer", VideoPlayerHandler.class);
    localHashMap.put("webplayer", WebPlayerHandler.class);
    localHashMap.put("webview", WebViewHandler.class);
    return localHashMap;
  }

  public Class[] getWebAppApiClassList()
  {
    return new Class[] { AdUnit.class, Listener.class, VideoPlayer.class, com.unity3d.splash.services.ads.api.Placement.class, WebPlayer.class, com.unity3d.splash.services.ads.api.AdsProperties.class };
  }

  public boolean initCompleteState(Configuration paramConfiguration)
  {
    return true;
  }

  public boolean initErrorState(Configuration paramConfiguration, String paramString1, String paramString2)
  {
    paramConfiguration = new StringBuilder("Init failed in ");
    paramConfiguration.append(paramString1);
    Utilities.runOnUiThread(new Runnable(paramConfiguration.toString())
    {
      public void run()
      {
        Iterator localIterator = com.unity3d.splash.services.ads.properties.AdsProperties.getListeners().iterator();
        while (localIterator.hasNext())
          ((IUnityAdsListener)localIterator.next()).onUnityAdsError(UnityAds.UnityAdsError.INITIALIZE_FAILED, this.val$message);
      }
    });
    return true;
  }

  public boolean initModuleState(Configuration paramConfiguration)
  {
    DeviceLog.debug("Unity Ads init: checking for ad blockers");
    try
    {
      Object localObject = new java/net/URL;
      ((URL)localObject).<init>(paramConfiguration.getConfigUrl());
      paramConfiguration = ((URL)localObject).getHost();
      localObject = new ConditionVariable();
      new Thread(paramConfiguration, (ConditionVariable)localObject)
      {
        public void run()
        {
          try
          {
            AdsModuleConfiguration.access$002(AdsModuleConfiguration.this, InetAddress.getByName(this.val$configHost));
            this.val$cv.open();
            return;
          }
          catch (Exception localException)
          {
            StringBuilder localStringBuilder = new StringBuilder("Couldn't get address. Host: ");
            localStringBuilder.append(this.val$configHost);
            DeviceLog.exception(localStringBuilder.toString(), localException);
            this.val$cv.open();
          }
        }
      }
      .start();
      if (((ConditionVariable)localObject).block(2000L))
      {
        paramConfiguration = this._address;
        if ((paramConfiguration != null) && (paramConfiguration.isLoopbackAddress()))
        {
          DeviceLog.error("Unity Ads init: halting init because Unity Ads config resolves to loopback address (due to ad blocker?)");
          Utilities.runOnUiThread(new Runnable()
          {
            public void run()
            {
              Iterator localIterator = com.unity3d.splash.services.ads.properties.AdsProperties.getListeners().iterator();
              while (localIterator.hasNext())
                ((IUnityAdsListener)localIterator.next()).onUnityAdsError(UnityAds.UnityAdsError.AD_BLOCKER_DETECTED, "Unity Ads config server resolves to loopback address (due to ad blocker?)");
            }
          });
          return false;
        }
      }
      label87: return true;
    }
    catch (java.net.MalformedURLException paramConfiguration)
    {
      break label87;
    }
  }

  public boolean resetState(Configuration paramConfiguration)
  {
    com.unity3d.splash.services.ads.placement.Placement.reset();
    return true;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.configuration.AdsModuleConfiguration
 * JD-Core Version:    0.6.0
 */