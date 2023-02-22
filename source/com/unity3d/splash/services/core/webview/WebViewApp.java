package com.unity3d.splash.services.core.webview;

import android.os.Build.VERSION;
import android.os.ConditionVariable;
import android.os.Looper;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebViewClient;
import com.unity3d.splash.services.core.configuration.Configuration;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.properties.SdkProperties;
import com.unity3d.splash.services.core.webview.bridge.CallbackStatus;
import com.unity3d.splash.services.core.webview.bridge.Invocation;
import com.unity3d.splash.services.core.webview.bridge.NativeCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewBridge;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONArray;

public class WebViewApp extends WebViewClient
{
  private static final int INVOKE_JS_CHARS_LENGTH = 22;
  private static ConditionVariable _conditionVariable;
  private static WebViewApp _currentApp;
  private Configuration _configuration;
  private boolean _initialized = false;
  private HashMap _nativeCallbacks;
  private boolean _webAppLoaded = false;
  private WebView _webView;

  public WebViewApp()
  {
  }

  private WebViewApp(Configuration paramConfiguration)
  {
    setConfiguration(paramConfiguration);
    WebViewBridge.setClassTable(getConfiguration().getWebAppApiClassList());
    paramConfiguration = new WebView(ClientProperties.getApplicationContext());
    this._webView = paramConfiguration;
    paramConfiguration.setWebViewClient(new WebAppClient(null));
    this._webView.setWebChromeClient(new WebAppChromeClient(null));
  }

  public static boolean create(Configuration paramConfiguration)
  {
    DeviceLog.entered();
    if (!Thread.currentThread().equals(Looper.getMainLooper().getThread()))
    {
      Utilities.runOnUiThread(new Runnable(paramConfiguration)
      {
        public final void run()
        {
          try
          {
            WebViewApp localWebViewApp = new WebViewApp(this.val$configuration, null);
            Object localObject1 = "?platform=android";
            Object localObject3 = localObject1;
            try
            {
              if (this.val$configuration.getWebViewUrl() != null)
              {
                localObject3 = new java/lang/StringBuilder;
                ((StringBuilder)localObject3).<init>();
                ((StringBuilder)localObject3).append("?platform=android");
                ((StringBuilder)localObject3).append("&origin=");
                ((StringBuilder)localObject3).append(URLEncoder.encode(this.val$configuration.getWebViewUrl(), "UTF-8"));
                localObject3 = ((StringBuilder)localObject3).toString();
              }
            }
            catch (UnsupportedEncodingException localObject4)
            {
              DeviceLog.exception("Unsupported charset when encoding origin url", localUnsupportedEncodingException2);
              localObject4 = localObject1;
            }
            localObject1 = localObject4;
            Object localObject2;
            try
            {
              if (this.val$configuration.getWebViewVersion() != null)
              {
                localObject1 = new java/lang/StringBuilder;
                ((StringBuilder)localObject1).<init>();
                ((StringBuilder)localObject1).append((String)localObject4);
                ((StringBuilder)localObject1).append("&version=");
                ((StringBuilder)localObject1).append(URLEncoder.encode(this.val$configuration.getWebViewVersion(), "UTF-8"));
                localObject1 = ((StringBuilder)localObject1).toString();
              }
            }
            catch (UnsupportedEncodingException localObject2)
            {
              DeviceLog.exception("Unsupported charset when encoding webview version", localUnsupportedEncodingException1);
              localObject2 = localObject4;
            }
            WebView localWebView = localWebViewApp.getWebView();
            Object localObject4 = new StringBuilder("file://");
            ((StringBuilder)localObject4).append(SdkProperties.getLocalWebViewFile());
            ((StringBuilder)localObject4).append(localObject2);
            localWebView.loadDataWithBaseURL(((StringBuilder)localObject4).toString(), this.val$configuration.getWebViewData(), "text/html", "UTF-8", null);
            WebViewApp.setCurrentApp(localWebViewApp);
            return;
          }
          catch (Exception localException)
          {
            DeviceLog.error("Couldn't construct WebViewApp");
            WebViewApp._conditionVariable.open();
          }
        }
      });
      paramConfiguration = new ConditionVariable();
      _conditionVariable = paramConfiguration;
      return (paramConfiguration.block(60000L)) && (getCurrentApp() != null);
    }
    throw new IllegalThreadStateException("Cannot call create() from main thread!");
  }

  public static WebViewApp getCurrentApp()
  {
    return _currentApp;
  }

  private void invokeJavascriptMethod(String paramString1, String paramString2, JSONArray paramJSONArray)
  {
    String str = paramJSONArray.toString();
    paramJSONArray = new StringBuilder(paramString1.length() + 22 + paramString2.length() + str.length());
    paramJSONArray.append("javascript:window.");
    paramJSONArray.append(paramString1);
    paramJSONArray.append(".");
    paramJSONArray.append(paramString2);
    paramJSONArray.append("(");
    paramJSONArray.append(str);
    paramJSONArray.append(");");
    paramString2 = paramJSONArray.toString();
    paramString1 = new StringBuilder("Invoking javascript: ");
    paramString1.append(paramString2);
    DeviceLog.debug(paramString1.toString());
    getWebView().invokeJavascript(paramString2);
  }

  public static void setCurrentApp(WebViewApp paramWebViewApp)
  {
    _currentApp = paramWebViewApp;
  }

  public void addCallback(NativeCallback paramNativeCallback)
  {
    if (this._nativeCallbacks == null)
      this._nativeCallbacks = new HashMap();
    synchronized (this._nativeCallbacks)
    {
      this._nativeCallbacks.put(paramNativeCallback.getId(), paramNativeCallback);
      return;
    }
  }

  public NativeCallback getCallback(String paramString)
  {
    synchronized (this._nativeCallbacks)
    {
      paramString = (NativeCallback)this._nativeCallbacks.get(paramString);
      return paramString;
    }
  }

  public Configuration getConfiguration()
  {
    return this._configuration;
  }

  public WebView getWebView()
  {
    return this._webView;
  }

  public boolean invokeCallback(Invocation paramInvocation)
  {
    if (!isWebAppLoaded())
    {
      DeviceLog.debug("invokeBatchCallback ignored because web app is not loaded");
      return false;
    }
    JSONArray localJSONArray = new JSONArray();
    paramInvocation = paramInvocation.getResponses();
    if ((paramInvocation != null) && (!paramInvocation.isEmpty()))
    {
      paramInvocation = paramInvocation.iterator();
      while (paramInvocation.hasNext())
      {
        Object localObject1 = (ArrayList)paramInvocation.next();
        Object localObject2 = (CallbackStatus)((ArrayList)localObject1).get(0);
        Object localObject3 = (Enum)((ArrayList)localObject1).get(1);
        localObject1 = (Object[])((ArrayList)localObject1).get(2);
        String str = (String)localObject1[0];
        Object[] arrayOfObject = Arrays.copyOfRange(localObject1, 1, localObject1.length);
        localObject1 = new ArrayList();
        ((ArrayList)localObject1).add(str);
        ((ArrayList)localObject1).add(((CallbackStatus)localObject2).toString());
        localObject2 = new JSONArray();
        if (localObject3 != null)
          ((JSONArray)localObject2).put(((Enum)localObject3).name());
        int i = arrayOfObject.length;
        for (int j = 0; j < i; j++)
          ((JSONArray)localObject2).put(arrayOfObject[j]);
        ((ArrayList)localObject1).add(localObject2);
        localObject3 = new JSONArray();
        localObject1 = ((ArrayList)localObject1).iterator();
        while (((Iterator)localObject1).hasNext())
          ((JSONArray)localObject3).put(((Iterator)localObject1).next());
        localJSONArray.put(localObject3);
      }
    }
    try
    {
      invokeJavascriptMethod("nativebridge", "handleCallback", localJSONArray);
    }
    catch (Exception paramInvocation)
    {
      DeviceLog.exception("Error while invoking batch response for WebView", paramInvocation);
    }
    return true;
  }

  public boolean invokeMethod(String paramString1, String paramString2, Method paramMethod, Object[] paramArrayOfObject)
  {
    if (!isWebAppLoaded())
    {
      DeviceLog.debug("invokeMethod ignored because web app is not loaded");
      return false;
    }
    JSONArray localJSONArray = new JSONArray();
    localJSONArray.put(paramString1);
    localJSONArray.put(paramString2);
    if (paramMethod != null)
    {
      paramString1 = new NativeCallback(paramMethod);
      addCallback(paramString1);
      paramString1 = paramString1.getId();
    }
    else
    {
      paramString1 = null;
    }
    localJSONArray.put(paramString1);
    if (paramArrayOfObject != null)
    {
      int i = paramArrayOfObject.length;
      for (int j = 0; j < i; j++)
        localJSONArray.put(paramArrayOfObject[j]);
    }
    try
    {
      invokeJavascriptMethod("nativebridge", "handleInvocation", localJSONArray);
      return true;
    }
    catch (Exception paramString1)
    {
      DeviceLog.exception("Error invoking javascript method", paramString1);
    }
    return false;
  }

  public boolean isWebAppInitialized()
  {
    return this._initialized;
  }

  public boolean isWebAppLoaded()
  {
    return this._webAppLoaded;
  }

  public void removeCallback(NativeCallback paramNativeCallback)
  {
    HashMap localHashMap = this._nativeCallbacks;
    if (localHashMap == null)
      return;
    monitorenter;
    try
    {
      this._nativeCallbacks.remove(paramNativeCallback.getId());
      return;
    }
    finally
    {
      monitorexit;
    }
    throw paramNativeCallback;
  }

  public boolean sendEvent(Enum paramEnum1, Enum paramEnum2, Object[] paramArrayOfObject)
  {
    if (!isWebAppLoaded())
    {
      DeviceLog.debug("sendEvent ignored because web app is not loaded");
      return false;
    }
    JSONArray localJSONArray = new JSONArray();
    localJSONArray.put(paramEnum1.name());
    localJSONArray.put(paramEnum2.name());
    int i = paramArrayOfObject.length;
    for (int j = 0; j < i; j++)
      localJSONArray.put(paramArrayOfObject[j]);
    try
    {
      invokeJavascriptMethod("nativebridge", "handleEvent", localJSONArray);
      return true;
    }
    catch (Exception paramEnum1)
    {
      DeviceLog.exception("Error while sending event to WebView", paramEnum1);
    }
    return false;
  }

  public void setConfiguration(Configuration paramConfiguration)
  {
    this._configuration = paramConfiguration;
  }

  public void setWebAppInitialized(boolean paramBoolean)
  {
    this._initialized = paramBoolean;
    _conditionVariable.open();
  }

  public void setWebAppLoaded(boolean paramBoolean)
  {
    this._webAppLoaded = paramBoolean;
  }

  public void setWebView(WebView paramWebView)
  {
    this._webView = paramWebView;
  }

  private class WebAppChromeClient extends WebChromeClient
  {
    private WebAppChromeClient()
    {
    }

    public void onConsoleMessage(String paramString1, int paramInt, String paramString2)
    {
      StringBuilder localStringBuilder;
      try
      {
        File localFile = new java/io/File;
        localFile.<init>(paramString2);
      }
      catch (Exception localStringBuilder)
      {
        DeviceLog.exception("Could not handle sourceId", localException);
        localStringBuilder = null;
      }
      if (localStringBuilder != null)
        paramString2 = localStringBuilder.getName();
      if (Build.VERSION.SDK_INT < 19)
      {
        localStringBuilder = new StringBuilder("JavaScript (sourceId=");
        localStringBuilder.append(paramString2);
        localStringBuilder.append(", line=");
        localStringBuilder.append(paramInt);
        localStringBuilder.append("): ");
        localStringBuilder.append(paramString1);
        DeviceLog.debug(localStringBuilder.toString());
      }
    }
  }

  private class WebAppClient extends WebViewClient
  {
    private WebAppClient()
    {
    }

    public void onPageFinished(android.webkit.WebView paramWebView, String paramString)
    {
      super.onPageFinished(paramWebView, paramString);
      paramWebView = new StringBuilder("onPageFinished url: ");
      paramWebView.append(paramString);
      DeviceLog.debug(paramWebView.toString());
    }

    public void onReceivedError(android.webkit.WebView paramWebView, WebResourceRequest paramWebResourceRequest, WebResourceError paramWebResourceError)
    {
      super.onReceivedError(paramWebView, paramWebResourceRequest, paramWebResourceError);
      if (paramWebView != null)
      {
        StringBuilder localStringBuilder = new StringBuilder("WEBVIEW_ERROR: ");
        localStringBuilder.append(paramWebView.toString());
        DeviceLog.error(localStringBuilder.toString());
      }
      if (paramWebResourceRequest != null)
      {
        paramWebView = new StringBuilder("WEBVIEW_ERROR: ");
        paramWebView.append(paramWebResourceRequest.toString());
        DeviceLog.error(paramWebView.toString());
      }
      if (paramWebResourceError != null)
      {
        paramWebView = new StringBuilder("WEBVIEW_ERROR: ");
        paramWebView.append(paramWebResourceError.toString());
        DeviceLog.error(paramWebView.toString());
      }
    }

    public boolean shouldOverrideUrlLoading(android.webkit.WebView paramWebView, String paramString)
    {
      paramWebView = new StringBuilder("Trying to load url: ");
      paramWebView.append(paramString);
      DeviceLog.debug(paramWebView.toString());
      return false;
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.webview.WebViewApp
 * JD-Core Version:    0.6.0
 */