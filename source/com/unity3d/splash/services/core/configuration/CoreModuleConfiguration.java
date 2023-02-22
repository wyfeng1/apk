package com.unity3d.splash.services.core.configuration;

import com.unity3d.splash.services.core.api.Broadcast;
import com.unity3d.splash.services.core.api.Cache;
import com.unity3d.splash.services.core.api.Connectivity;
import com.unity3d.splash.services.core.api.DeviceInfo;
import com.unity3d.splash.services.core.api.Intent;
import com.unity3d.splash.services.core.api.Lifecycle;
import com.unity3d.splash.services.core.api.Permissions;
import com.unity3d.splash.services.core.api.Preferences;
import com.unity3d.splash.services.core.api.Request;
import com.unity3d.splash.services.core.api.Resolve;
import com.unity3d.splash.services.core.api.Sdk;
import com.unity3d.splash.services.core.api.SensorInfo;
import com.unity3d.splash.services.core.api.Storage;
import com.unity3d.splash.services.core.broadcast.BroadcastMonitor;
import com.unity3d.splash.services.core.cache.CacheThread;
import com.unity3d.splash.services.core.connectivity.ConnectivityMonitor;
import com.unity3d.splash.services.core.device.AdvertisingId;
import com.unity3d.splash.services.core.device.OpenAdvertisingId;
import com.unity3d.splash.services.core.device.StorageManager;
import com.unity3d.splash.services.core.device.VolumeChange;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.request.WebRequestThread;

public class CoreModuleConfiguration
  implements IModuleConfiguration
{
  public Class[] getWebAppApiClassList()
  {
    return new Class[] { Broadcast.class, Cache.class, Connectivity.class, DeviceInfo.class, Storage.class, Sdk.class, Request.class, Resolve.class, Intent.class, Lifecycle.class, Preferences.class, SensorInfo.class, Permissions.class };
  }

  public boolean initCompleteState(Configuration paramConfiguration)
  {
    return true;
  }

  public boolean initErrorState(Configuration paramConfiguration, String paramString1, String paramString2)
  {
    return true;
  }

  public boolean initModuleState(Configuration paramConfiguration)
  {
    return true;
  }

  public boolean resetState(Configuration paramConfiguration)
  {
    BroadcastMonitor.removeAllBroadcastListeners();
    CacheThread.cancel();
    WebRequestThread.cancel();
    ConnectivityMonitor.stopAll();
    StorageManager.init(ClientProperties.getApplicationContext());
    AdvertisingId.init(ClientProperties.getApplicationContext());
    OpenAdvertisingId.init(ClientProperties.getApplicationContext());
    VolumeChange.clearAllListeners();
    return true;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.configuration.CoreModuleConfiguration
 * JD-Core Version:    0.6.0
 */