package com.unity3d.splash.services.core.properties;

import android.content.Context;
import com.unity3d.splash.BuildConfig;
import com.unity3d.splash.services.IUnityServicesListener;
import com.unity3d.splash.services.core.cache.CacheDirectory;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class SdkProperties
{
  private static final String CACHE_DIR_NAME = "UnitySplashAdsCache";
  private static final String CHINA_ISO_ALPHA_2_CODE = "CN";
  private static final String CHINA_ISO_ALPHA_3_CODE = "CHN";
  private static final String LOCAL_CACHE_FILE_PREFIX = "UnitySplashAdsCache-";
  private static final String LOCAL_STORAGE_FILE_PREFIX = "UnitySplashAdsStorage-";
  private static CacheDirectory _cacheDirectory;
  private static String _configUrl;
  private static boolean _debugMode = false;
  private static long _initializationTime = 0L;
  private static boolean _initialized = false;
  private static IUnityServicesListener _listener;
  private static boolean _perPlacementLoadEnabled = false;
  private static boolean _reinitialized = false;
  private static boolean _testMode = false;

  public static File getCacheDirectory()
  {
    return getCacheDirectory(ClientProperties.getApplicationContext());
  }

  public static File getCacheDirectory(Context paramContext)
  {
    if (_cacheDirectory == null)
      setCacheDirectory(new CacheDirectory("UnitySplashAdsCache"));
    return _cacheDirectory.getCacheDirectory(paramContext);
  }

  public static String getCacheDirectoryName()
  {
    return "UnitySplashAdsCache";
  }

  public static CacheDirectory getCacheDirectoryObject()
  {
    return _cacheDirectory;
  }

  public static String getCacheFilePrefix()
  {
    return "UnitySplashAdsCache-";
  }

  public static String getConfigUrl()
  {
    if (_configUrl == null)
      _configUrl = getDefaultConfigUrl("release");
    return _configUrl;
  }

  public static boolean getDebugMode()
  {
    return _debugMode;
  }

  public static String getDefaultConfigUrl(String paramString)
  {
    return "https://splash-ads.unitychina.cn/webview/release/native/config.json";
  }

  public static long getInitializationTime()
  {
    return _initializationTime;
  }

  public static IUnityServicesListener getListener()
  {
    return _listener;
  }

  public static String getLocalStorageFilePrefix()
  {
    return "UnitySplashAdsStorage-";
  }

  public static String getLocalWebViewFile()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(getCacheDirectory().getAbsolutePath());
    localStringBuilder.append("/UnitySplashAdsWebApp.html");
    return localStringBuilder.toString();
  }

  public static int getVersionCode()
  {
    return 3300;
  }

  public static String getVersionName()
  {
    return "3.3.0";
  }

  private static String getWebViewBranch()
  {
    if (BuildConfig.DEBUG)
      return "3.3.0";
    return getVersionName();
  }

  public static boolean isChinaLocale(String paramString)
  {
    return (paramString.equalsIgnoreCase("CN")) || (paramString.equalsIgnoreCase("CHN"));
  }

  public static boolean isInitialized()
  {
    return _initialized;
  }

  public static boolean isPerPlacementLoadEnabled()
  {
    return _perPlacementLoadEnabled;
  }

  public static boolean isReinitialized()
  {
    return _reinitialized;
  }

  public static boolean isTestMode()
  {
    return _testMode;
  }

  public static void setCacheDirectory(CacheDirectory paramCacheDirectory)
  {
    _cacheDirectory = paramCacheDirectory;
  }

  public static void setConfigUrl(String paramString)
  {
    if (paramString != null)
    {
      if ((!paramString.startsWith("http://")) && (!paramString.startsWith("https://")))
        throw new MalformedURLException();
      new URL(paramString).toURI();
      _configUrl = paramString;
      return;
    }
    throw new MalformedURLException();
  }

  public static void setDebugMode(boolean paramBoolean)
  {
    _debugMode = paramBoolean;
    if (paramBoolean);
    for (int i = 8; ; i = 4)
    {
      DeviceLog.setLogLevel(i);
      return;
    }
  }

  public static void setInitializationTime(long paramLong)
  {
    _initializationTime = paramLong;
  }

  public static void setInitialized(boolean paramBoolean)
  {
    _initialized = paramBoolean;
  }

  public static void setListener(IUnityServicesListener paramIUnityServicesListener)
  {
    _listener = paramIUnityServicesListener;
  }

  public static void setPerPlacementLoadEnabled(boolean paramBoolean)
  {
    _perPlacementLoadEnabled = paramBoolean;
  }

  public static void setReinitialized(boolean paramBoolean)
  {
    _reinitialized = paramBoolean;
  }

  public static void setTestMode(boolean paramBoolean)
  {
    _testMode = paramBoolean;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.properties.SdkProperties
 * JD-Core Version:    0.6.0
 */