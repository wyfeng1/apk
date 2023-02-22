package com.unity3d.splash.services.ads.load;

import android.text.TextUtils;
import com.unity3d.splash.services.core.configuration.IInitializationListener;
import com.unity3d.splash.services.core.configuration.IInitializationNotificationCenter;
import com.unity3d.splash.services.core.configuration.InitializationNotificationCenter;
import com.unity3d.splash.services.core.properties.SdkProperties;
import java.util.LinkedHashMap;
import java.util.Set;

public class LoadModule
  implements IInitializationListener
{
  private static LoadModule instance;
  private IInitializationNotificationCenter _initializationNotificationCenter;
  private ILoadBridge _loadBridge;
  private LinkedHashMap _loadEventBuffer = new LinkedHashMap();

  public LoadModule(ILoadBridge paramILoadBridge, IInitializationNotificationCenter paramIInitializationNotificationCenter)
  {
    this._loadBridge = paramILoadBridge;
    this._initializationNotificationCenter = paramIInitializationNotificationCenter;
    paramIInitializationNotificationCenter.addListener(this);
  }

  private void addPlacementId(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      if (this._loadEventBuffer.containsKey(paramString))
      {
        Integer localInteger = (Integer)this._loadEventBuffer.get(paramString);
        if (localInteger != null)
        {
          int i = localInteger.intValue();
          this._loadEventBuffer.put(paramString, Integer.valueOf(i + 1));
          return;
        }
        this._loadEventBuffer.put(paramString, new Integer(1));
        return;
      }
      this._loadEventBuffer.put(paramString, new Integer(1));
    }
  }

  public static LoadModule getInstance()
  {
    if (instance == null)
      instance = new LoadModule(new LoadBridge(), InitializationNotificationCenter.getInstance());
    return instance;
  }

  private void sendLoadEvents()
  {
    if (this._loadEventBuffer.keySet().size() > 0)
      this._loadBridge.loadPlacements(this._loadEventBuffer);
    this._loadEventBuffer = new LinkedHashMap();
  }

  public void load(String paramString)
  {
    monitorenter;
    try
    {
      addPlacementId(paramString);
      if (SdkProperties.isInitialized())
        sendLoadEvents();
      monitorexit;
      return;
    }
    finally
    {
      paramString = finally;
      monitorexit;
    }
    throw paramString;
  }

  public void onSdkInitializationFailed(String paramString, int paramInt)
  {
  }

  public void onSdkInitialized()
  {
    monitorenter;
    try
    {
      sendLoadEvents();
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
 * Qualified Name:     com.unity3d.splash.services.ads.load.LoadModule
 * JD-Core Version:    0.6.0
 */