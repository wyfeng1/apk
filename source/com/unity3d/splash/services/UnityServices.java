package com.unity3d.splash.services;

import android.app.Activity;
import android.os.Build.VERSION;
import com.unity3d.splash.services.core.configuration.Configuration;
import com.unity3d.splash.services.core.configuration.EnvironmentCheck;
import com.unity3d.splash.services.core.configuration.InitializeThread;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.properties.SdkProperties;
import java.util.Date;

public class UnityServices
{
  private static boolean _configurationInitialized = false;

  public static boolean getDebugMode()
  {
    return SdkProperties.getDebugMode();
  }

  public static String getVersion()
  {
    return SdkProperties.getVersionName();
  }

  public static void initialize(Activity paramActivity, String paramString, IUnityServicesListener paramIUnityServicesListener, boolean paramBoolean1, boolean paramBoolean2)
  {
    DeviceLog.entered();
    if (_configurationInitialized)
    {
      if ((ClientProperties.getGameId() != null) && (!ClientProperties.getGameId().equals(paramString)))
        DeviceLog.warning("You are trying to re-initialize with a different gameId");
      return;
    }
    _configurationInitialized = true;
    if (!isSupported())
    {
      DeviceLog.error("Error while initializing Unity Services: device is not supported");
      return;
    }
    Object localObject = new StringBuilder("Application start initializing at ");
    ((StringBuilder)localObject).append(new Date().getTime());
    DeviceLog.info(((StringBuilder)localObject).toString());
    SdkProperties.setInitializationTime(System.currentTimeMillis());
    if ((paramString != null) && (paramString.length() != 0))
    {
      if (paramActivity == null)
      {
        DeviceLog.error("Error while initializing Unity Services: null activity, halting Unity Ads init");
        if (paramIUnityServicesListener != null)
          paramIUnityServicesListener.onUnityServicesError(UnityServicesError.INVALID_ARGUMENT, "Null activity");
        return;
      }
      StringBuilder localStringBuilder = new java/lang/StringBuilder;
      if (paramBoolean1)
      {
        localStringBuilder.<init>("Initializing Unity Services ");
        localStringBuilder.append(SdkProperties.getVersionName());
        localStringBuilder.append(" (");
        localStringBuilder.append(SdkProperties.getVersionCode());
        localStringBuilder.append(") with game id ");
        localStringBuilder.append(paramString);
        localObject = " in test mode";
      }
      else
      {
        localStringBuilder.<init>("Initializing Unity Services ");
        localStringBuilder.append(SdkProperties.getVersionName());
        localStringBuilder.append(" (");
        localStringBuilder.append(SdkProperties.getVersionCode());
        localStringBuilder.append(") with game id ");
        localStringBuilder.append(paramString);
        localObject = " in production mode";
      }
      localStringBuilder.append((String)localObject);
      DeviceLog.info(localStringBuilder.toString());
      SdkProperties.setDebugMode(SdkProperties.getDebugMode());
      SdkProperties.setListener(paramIUnityServicesListener);
      ClientProperties.setGameId(paramString);
      ClientProperties.setApplicationContext(paramActivity.getApplicationContext());
      ClientProperties.setApplication(paramActivity.getApplication());
      SdkProperties.setPerPlacementLoadEnabled(paramBoolean2);
      SdkProperties.setTestMode(paramBoolean1);
      if (EnvironmentCheck.isEnvironmentOk())
      {
        DeviceLog.info("Unity Services environment check OK");
        InitializeThread.initialize(new Configuration());
        return;
      }
      DeviceLog.error("Error during Unity Services environment check, halting Unity Services init");
      if (paramIUnityServicesListener != null)
        paramIUnityServicesListener.onUnityServicesError(UnityServicesError.INIT_SANITY_CHECK_FAIL, "Unity Services init environment check failed");
      return;
    }
    DeviceLog.error("Error while initializing Unity Services: empty game ID, halting Unity Ads init");
    if (paramIUnityServicesListener != null)
      paramIUnityServicesListener.onUnityServicesError(UnityServicesError.INVALID_ARGUMENT, "Empty game ID");
  }

  public static boolean isInitialized()
  {
    return SdkProperties.isInitialized();
  }

  public static boolean isSupported()
  {
    return Build.VERSION.SDK_INT >= 16;
  }

  public static void setDebugMode(boolean paramBoolean)
  {
    SdkProperties.setDebugMode(paramBoolean);
  }

  public static enum UnityServicesError
  {
    static
    {
      UnityServicesError localUnityServicesError = new UnityServicesError("INIT_SANITY_CHECK_FAIL", 1);
      INIT_SANITY_CHECK_FAIL = localUnityServicesError;
      $VALUES = new UnityServicesError[] { INVALID_ARGUMENT, localUnityServicesError };
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.UnityServices
 * JD-Core Version:    0.6.0
 */