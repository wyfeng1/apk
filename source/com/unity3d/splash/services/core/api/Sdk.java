package com.unity3d.splash.services.core.api;

import com.unity3d.splash.services.core.configuration.Configuration;
import com.unity3d.splash.services.core.configuration.InitializationNotificationCenter;
import com.unity3d.splash.services.core.configuration.InitializeThread;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.properties.SdkProperties;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.util.Date;

public class Sdk
{
  @WebViewExposed
  public static void getDebugMode(WebViewCallback paramWebViewCallback)
  {
    paramWebViewCallback.invoke(new Object[] { Boolean.valueOf(SdkProperties.getDebugMode()) });
  }

  @WebViewExposed
  public static void initComplete(WebViewCallback paramWebViewCallback)
  {
    StringBuilder localStringBuilder = new StringBuilder("Web Application initialized at ");
    localStringBuilder.append(new Date().getTime());
    DeviceLog.info(localStringBuilder.toString());
    SdkProperties.setInitialized(true);
    WebViewApp.getCurrentApp().setWebAppInitialized(true);
    InitializationNotificationCenter.getInstance().triggerOnSdkInitialized();
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void initError(String paramString, int paramInt, WebViewCallback paramWebViewCallback)
  {
    InitializationNotificationCenter.getInstance().triggerOnSdkInitializationFailed(paramString, paramInt);
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void loadComplete(WebViewCallback paramWebViewCallback)
  {
    StringBuilder localStringBuilder = new StringBuilder("Web Application loaded at ");
    localStringBuilder.append(new Date().getTime());
    DeviceLog.info(localStringBuilder.toString());
    WebViewApp.getCurrentApp().setWebAppLoaded(true);
    paramWebViewCallback.invoke(new Object[] { ClientProperties.getGameId(), Boolean.valueOf(SdkProperties.isTestMode()), ClientProperties.getAppName(), ClientProperties.getAppVersion(), Integer.valueOf(SdkProperties.getVersionCode()), SdkProperties.getVersionName(), Boolean.valueOf(ClientProperties.isAppDebuggable()), WebViewApp.getCurrentApp().getConfiguration().getConfigUrl(), WebViewApp.getCurrentApp().getConfiguration().getWebViewUrl(), WebViewApp.getCurrentApp().getConfiguration().getWebViewHash(), WebViewApp.getCurrentApp().getConfiguration().getWebViewVersion(), Long.valueOf(SdkProperties.getInitializationTime()), Boolean.valueOf(SdkProperties.isReinitialized()), Boolean.valueOf(SdkProperties.isPerPlacementLoadEnabled()) });
  }

  @WebViewExposed
  public static void logDebug(String paramString, WebViewCallback paramWebViewCallback)
  {
    DeviceLog.debug(paramString);
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void logError(String paramString, WebViewCallback paramWebViewCallback)
  {
    DeviceLog.error(paramString);
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void logInfo(String paramString, WebViewCallback paramWebViewCallback)
  {
    DeviceLog.info(paramString);
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void logWarning(String paramString, WebViewCallback paramWebViewCallback)
  {
    DeviceLog.warning(paramString);
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void reinitialize(WebViewCallback paramWebViewCallback)
  {
    SdkProperties.setReinitialized(true);
    InitializeThread.initialize(WebViewApp.getCurrentApp().getConfiguration());
  }

  @WebViewExposed
  public static void setDebugMode(Boolean paramBoolean, WebViewCallback paramWebViewCallback)
  {
    SdkProperties.setDebugMode(paramBoolean.booleanValue());
    paramWebViewCallback.invoke(new Object[0]);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.api.Sdk
 * JD-Core Version:    0.6.0
 */