package com.unity3d.splash.services.ads.webplayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.ClientCertRequest;
import android.webkit.ConsoleMessage;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.HttpAuthHandler;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebChromeClient.FileChooserParams;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.misc.ViewUtilities;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class WebPlayer extends WebView
{
  private Map _erroredSettings;
  private Method _evaluateJavascript = null;
  private JSONObject _eventSettings;
  private String viewId;

  public WebPlayer(Context paramContext, String paramString, JSONObject paramJSONObject1, JSONObject paramJSONObject2)
  {
    super(paramContext);
    this.viewId = paramString;
    WebSettings localWebSettings = getSettings();
    if (Build.VERSION.SDK_INT >= 16)
    {
      localWebSettings.setAllowFileAccessFromFileURLs(false);
      localWebSettings.setAllowUniversalAccessFromFileURLs(false);
    }
    if (Build.VERSION.SDK_INT >= 19)
      try
      {
        this._evaluateJavascript = WebView.class.getMethod("evaluateJavascript", new Class[] { String.class, ValueCallback.class });
      }
      catch (java.lang.NoSuchMethodException paramContext)
      {
        DeviceLog.exception("Method evaluateJavascript not found", paramContext);
        this._evaluateJavascript = null;
      }
    localWebSettings.setAppCacheEnabled(false);
    localWebSettings.setCacheMode(2);
    localWebSettings.setDatabaseEnabled(false);
    localWebSettings.setDomStorageEnabled(false);
    localWebSettings.setGeolocationEnabled(false);
    localWebSettings.setJavaScriptEnabled(true);
    localWebSettings.setLoadsImagesAutomatically(true);
    setHorizontalScrollBarEnabled(false);
    setVerticalScrollBarEnabled(false);
    setInitialScale(0);
    setBackgroundColor(0);
    ViewUtilities.setBackground(this, new ColorDrawable(0));
    setBackgroundResource(0);
    setSettings(paramJSONObject1, paramJSONObject2);
    setWebViewClient(new WebPlayerClient(null));
    setWebChromeClient(new WebPlayerChromeClient(null));
    setDownloadListener(new WebPlayerDownloadListener(null));
    addJavascriptInterface(new WebPlayerBridgeInterface(paramString), "webplayerbridge");
  }

  private void addErroredSetting(String paramString1, String paramString2)
  {
    if (this._erroredSettings == null)
      this._erroredSettings = new HashMap();
    this._erroredSettings.put(paramString1, paramString2);
  }

  private Object getReturnValue(String paramString, Class paramClass, Object paramObject)
  {
    try
    {
      if ((this._eventSettings != null) && (this._eventSettings.has(paramString)) && (this._eventSettings.getJSONObject(paramString).has("returnValue")))
      {
        paramString = paramClass.cast(this._eventSettings.getJSONObject(paramString).get("returnValue"));
        return paramString;
      }
    }
    catch (Exception paramString)
    {
      DeviceLog.exception("Error getting default return value", paramString);
    }
    return paramObject;
  }

  private Class[] getTypes(JSONArray paramJSONArray)
  {
    if (paramJSONArray == null)
      return null;
    Class[] arrayOfClass = new Class[paramJSONArray.length()];
    if (paramJSONArray != null)
      for (int i = 0; i < paramJSONArray.length(); i++)
        if ((paramJSONArray.get(i) instanceof JSONObject))
        {
          Class localClass = Class.forName(((JSONObject)paramJSONArray.get(i)).getString("className"));
          if (localClass == null)
            continue;
          arrayOfClass[i] = localClass;
        }
        else
        {
          arrayOfClass[i] = getPrimitiveClass(paramJSONArray.get(i).getClass());
        }
    return arrayOfClass;
  }

  private Object[] getValues(JSONArray paramJSONArray)
  {
    if (paramJSONArray == null)
      return null;
    Object[] arrayOfObject1 = new Object[paramJSONArray.length()];
    Object[] arrayOfObject2 = new Object[paramJSONArray.length()];
    for (int i = 0; i < paramJSONArray.length(); i++)
      if ((paramJSONArray.get(i) instanceof JSONObject))
      {
        Object localObject1 = (JSONObject)paramJSONArray.get(i);
        Object localObject2 = ((JSONObject)localObject1).get("value");
        String str = ((JSONObject)localObject1).getString("type");
        if (((JSONObject)localObject1).has("className"))
          localObject1 = ((JSONObject)localObject1).getString("className");
        else
          localObject1 = null;
        if ((localObject1 == null) || (!str.equals("Enum")))
          continue;
        localObject1 = Class.forName((String)localObject1);
        if (localObject1 == null)
          continue;
        arrayOfObject2[i] = Enum.valueOf((Class)localObject1, (String)localObject2);
      }
      else
      {
        arrayOfObject2[i] = paramJSONArray.get(i);
      }
    if (paramJSONArray != null)
      System.arraycopy(arrayOfObject2, 0, arrayOfObject1, 0, paramJSONArray.length());
    return (Object)arrayOfObject1;
  }

  private boolean hasReturnValue(String paramString)
  {
    try
    {
      if ((this._eventSettings != null) && (this._eventSettings.has(paramString)))
      {
        boolean bool = this._eventSettings.getJSONObject(paramString).has("returnValue");
        if (bool)
          return true;
      }
    }
    catch (Exception paramString)
    {
      DeviceLog.exception("Error getting default return value", paramString);
    }
    return false;
  }

  private Object setTargetSettings(Object paramObject, JSONObject paramJSONObject)
  {
    if (paramJSONObject != null)
    {
      Iterator localIterator = paramJSONObject.keys();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        try
        {
          JSONArray localJSONArray = paramJSONObject.getJSONArray(str);
          Class[] arrayOfClass = getTypes(localJSONArray);
          paramObject.getClass().getMethod(str, arrayOfClass).invoke(paramObject, getValues(localJSONArray));
        }
        catch (Exception localException)
        {
          addErroredSetting(str, localException.getMessage());
          DeviceLog.exception("Setting errored", localException);
        }
      }
    }
    return paramObject;
  }

  private boolean shouldCallSuper(String paramString)
  {
    try
    {
      if ((this._eventSettings != null) && (this._eventSettings.has(paramString)) && (this._eventSettings.getJSONObject(paramString).has("callSuper")))
      {
        boolean bool = this._eventSettings.getJSONObject(paramString).getBoolean("callSuper");
        return bool;
      }
    }
    catch (Exception paramString)
    {
      DeviceLog.exception("Error getting super call status", paramString);
    }
    return true;
  }

  private boolean shouldSendEvent(String paramString)
  {
    try
    {
      if ((this._eventSettings != null) && (this._eventSettings.has(paramString)) && (this._eventSettings.getJSONObject(paramString).has("sendEvent")))
      {
        boolean bool = this._eventSettings.getJSONObject(paramString).getBoolean("sendEvent");
        return bool;
      }
    }
    catch (Exception paramString)
    {
      DeviceLog.exception("Error getting send event status", paramString);
    }
    return false;
  }

  public Map getErroredSettings()
  {
    return this._erroredSettings;
  }

  public Class getPrimitiveClass(Class paramClass)
  {
    String str = paramClass.getName();
    if (str.equals("java.lang.Byte"))
      return Byte.TYPE;
    if (str.equals("java.lang.Short"))
      return Short.TYPE;
    if (str.equals("java.lang.Integer"))
      return Integer.TYPE;
    if (str.equals("java.lang.Long"))
      return Long.TYPE;
    if (str.equals("java.lang.Character"))
      return Character.TYPE;
    if (str.equals("java.lang.Float"))
      return Float.TYPE;
    if (str.equals("java.lang.Double"))
      return Double.TYPE;
    if (str.equals("java.lang.Boolean"))
      return Boolean.TYPE;
    if (str.equals("java.lang.Void"))
      paramClass = Void.TYPE;
    return paramClass;
  }

  public void invokeJavascript(String paramString)
  {
    Utilities.runOnUiThread(new JavaScriptInvocation(paramString, this));
  }

  public void sendEvent(JSONArray paramJSONArray)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("javascript:window.nativebridge.receiveEvent(");
    localStringBuilder.append(paramJSONArray.toString());
    localStringBuilder.append(")");
    invokeJavascript(localStringBuilder.toString());
  }

  public void setEventSettings(JSONObject paramJSONObject)
  {
    this._eventSettings = paramJSONObject;
  }

  public void setSettings(JSONObject paramJSONObject1, JSONObject paramJSONObject2)
  {
    Map localMap = this._erroredSettings;
    if (localMap != null)
      localMap.clear();
    setTargetSettings(getSettings(), paramJSONObject1);
    setTargetSettings(this, paramJSONObject2);
  }

  private class JavaScriptInvocation
    implements Runnable
  {
    private String _jsString = null;
    private WebView _webView = null;

    public JavaScriptInvocation(String paramWebView, WebView arg3)
    {
      this._jsString = paramWebView;
      Object localObject;
      this._webView = localObject;
    }

    public void run()
    {
      if (this._jsString != null)
        try
        {
          if (Build.VERSION.SDK_INT >= 19)
          {
            WebPlayer.this._evaluateJavascript.invoke(this._webView, new Object[] { this._jsString, null });
            return;
          }
          WebPlayer.this.loadUrl(this._jsString);
          return;
        }
        catch (Exception localException)
        {
          DeviceLog.exception("Error while processing JavaScriptString", localException);
          return;
        }
      DeviceLog.error("Could not process JavaScript, the string is NULL");
    }
  }

  private class WebPlayerChromeClient extends WebChromeClient
  {
    private WebPlayerChromeClient()
    {
    }

    public void onCloseWindow(WebView paramWebView)
    {
      if (WebPlayer.this.shouldCallSuper("onCloseWindow"))
        super.onCloseWindow(paramWebView);
      if (WebPlayer.this.shouldSendEvent("onCloseWindow"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.CLOSE_WINDOW, new Object[] { WebPlayer.access$600(WebPlayer.this) });
    }

    public boolean onConsoleMessage(ConsoleMessage paramConsoleMessage)
    {
      Boolean localBoolean = Boolean.valueOf(false);
      if (WebPlayer.this.shouldCallSuper("onConsoleMessage"))
        localBoolean = Boolean.valueOf(super.onConsoleMessage(paramConsoleMessage));
      if (WebPlayer.this.shouldSendEvent("onConsoleMessage"))
      {
        if (paramConsoleMessage != null)
          paramConsoleMessage = paramConsoleMessage.message();
        else
          paramConsoleMessage = "";
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.CONSOLE_MESSAGE, new Object[] { paramConsoleMessage, WebPlayer.access$600(WebPlayer.this) });
      }
      if (WebPlayer.this.hasReturnValue("onConsoleMessage"))
        localBoolean = (Boolean)WebPlayer.this.getReturnValue("onConsoleMessage", Boolean.class, Boolean.valueOf(true));
      return localBoolean.booleanValue();
    }

    public boolean onCreateWindow(WebView paramWebView, boolean paramBoolean1, boolean paramBoolean2, Message paramMessage)
    {
      Boolean localBoolean = Boolean.valueOf(false);
      if (WebPlayer.this.shouldCallSuper("onCreateWindow"))
        paramWebView = Boolean.valueOf(super.onCreateWindow(paramWebView, paramBoolean1, paramBoolean2, paramMessage));
      else
        paramWebView = localBoolean;
      if (WebPlayer.this.shouldSendEvent("onCreateWindow"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.CREATE_WINDOW, new Object[] { Boolean.valueOf(paramBoolean1), Boolean.valueOf(paramBoolean2), paramMessage, WebPlayer.access$600(WebPlayer.this) });
      if (WebPlayer.this.hasReturnValue("onCreateWindow"))
        paramWebView = (Boolean)WebPlayer.this.getReturnValue("onCreateWindow", Boolean.class, localBoolean);
      return paramWebView.booleanValue();
    }

    public void onGeolocationPermissionsShowPrompt(String paramString, GeolocationPermissions.Callback paramCallback)
    {
      if (WebPlayer.this.shouldCallSuper("onGeolocationPermissionsShowPrompt"))
        super.onGeolocationPermissionsShowPrompt(paramString, paramCallback);
      if (WebPlayer.this.shouldSendEvent("onGeolocationPermissionsShowPrompt"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.GEOLOCATION_PERMISSIONS_SHOW, new Object[] { paramString, WebPlayer.access$600(WebPlayer.this) });
    }

    public void onHideCustomView()
    {
      if (WebPlayer.this.shouldCallSuper("onHideCustomView"))
        super.onHideCustomView();
      if (WebPlayer.this.shouldSendEvent("onHideCustomView"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.HIDE_CUSTOM_VIEW, new Object[] { WebPlayer.access$600(WebPlayer.this) });
    }

    public boolean onJsAlert(WebView paramWebView, String paramString1, String paramString2, JsResult paramJsResult)
    {
      Boolean localBoolean = Boolean.valueOf(false);
      if (WebPlayer.this.shouldCallSuper("onJsAlert"))
        localBoolean = Boolean.valueOf(super.onJsAlert(paramWebView, paramString1, paramString2, paramJsResult));
      if (WebPlayer.this.shouldSendEvent("onJsAlert"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.JS_ALERT, new Object[] { paramString1, paramString2, paramJsResult, WebPlayer.access$600(WebPlayer.this) });
      if (WebPlayer.this.hasReturnValue("onJsAlert"))
        localBoolean = (Boolean)WebPlayer.this.getReturnValue("onJsAlert", Boolean.class, Boolean.valueOf(true));
      return localBoolean.booleanValue();
    }

    public boolean onJsConfirm(WebView paramWebView, String paramString1, String paramString2, JsResult paramJsResult)
    {
      Boolean localBoolean = Boolean.valueOf(false);
      if (WebPlayer.this.shouldCallSuper("onJsConfirm"))
        localBoolean = Boolean.valueOf(super.onJsConfirm(paramWebView, paramString1, paramString2, paramJsResult));
      if (WebPlayer.this.shouldSendEvent("onJsConfirm"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.JS_CONFIRM, new Object[] { paramString1, paramString2, WebPlayer.access$600(WebPlayer.this) });
      if (WebPlayer.this.hasReturnValue("onJsConfirm"))
        localBoolean = (Boolean)WebPlayer.this.getReturnValue("onJsConfirm", Boolean.class, Boolean.valueOf(true));
      return localBoolean.booleanValue();
    }

    public boolean onJsPrompt(WebView paramWebView, String paramString1, String paramString2, String paramString3, JsPromptResult paramJsPromptResult)
    {
      Boolean localBoolean = Boolean.valueOf(false);
      if (WebPlayer.this.shouldCallSuper("onJsPrompt"))
        localBoolean = Boolean.valueOf(super.onJsPrompt(paramWebView, paramString1, paramString2, paramString3, paramJsPromptResult));
      if (WebPlayer.this.shouldSendEvent("onJsPrompt"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.JS_PROMPT, new Object[] { paramString1, paramString2, paramString3, WebPlayer.access$600(WebPlayer.this) });
      if (WebPlayer.this.hasReturnValue("onJsPrompt"))
        localBoolean = (Boolean)WebPlayer.this.getReturnValue("onJsPrompt", Boolean.class, Boolean.valueOf(true));
      return localBoolean.booleanValue();
    }

    public void onPermissionRequest(PermissionRequest paramPermissionRequest)
    {
      if (WebPlayer.this.shouldCallSuper("onPermissionRequest"))
        super.onPermissionRequest(paramPermissionRequest);
      if (WebPlayer.this.shouldSendEvent("onPermissionRequest"))
      {
        if ((paramPermissionRequest != null) && (paramPermissionRequest.getOrigin() != null))
          paramPermissionRequest = paramPermissionRequest.getOrigin().toString();
        else
          paramPermissionRequest = "";
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.PERMISSION_REQUEST, new Object[] { paramPermissionRequest, WebPlayer.access$600(WebPlayer.this) });
      }
    }

    public void onProgressChanged(WebView paramWebView, int paramInt)
    {
      if (WebPlayer.this.shouldCallSuper("onProgressChanged"))
        super.onProgressChanged(paramWebView, paramInt);
      if (WebPlayer.this.shouldSendEvent("onProgressChanged"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.PROGRESS_CHANGED, new Object[] { Integer.valueOf(paramInt), WebPlayer.access$600(WebPlayer.this) });
    }

    public void onReceivedIcon(WebView paramWebView, Bitmap paramBitmap)
    {
      if (WebPlayer.this.shouldCallSuper("onReceivedIcon"))
        super.onReceivedIcon(paramWebView, paramBitmap);
      if (WebPlayer.this.shouldSendEvent("onReceivedIcon"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.RECEIVED_ICON, new Object[] { WebPlayer.access$600(WebPlayer.this) });
    }

    public void onReceivedTitle(WebView paramWebView, String paramString)
    {
      if (WebPlayer.this.shouldCallSuper("onReceivedTitle"))
        super.onReceivedTitle(paramWebView, paramString);
      if (WebPlayer.this.shouldSendEvent("onReceivedTitle"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.RECEIVED_TITLE, new Object[] { paramString, WebPlayer.access$600(WebPlayer.this) });
    }

    public void onReceivedTouchIconUrl(WebView paramWebView, String paramString, boolean paramBoolean)
    {
      if (WebPlayer.this.shouldCallSuper("onReceivedTouchIconUrl"))
        super.onReceivedTouchIconUrl(paramWebView, paramString, paramBoolean);
      if (WebPlayer.this.shouldSendEvent("onReceivedTouchIconUrl"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.RECEIVED_TOUCH_ICON_URL, new Object[] { paramString, Boolean.valueOf(paramBoolean), WebPlayer.access$600(WebPlayer.this) });
    }

    public void onRequestFocus(WebView paramWebView)
    {
      if (WebPlayer.this.shouldCallSuper("onRequestFocus"))
        super.onRequestFocus(paramWebView);
      if (WebPlayer.this.shouldSendEvent("onRequestFocus"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.REQUEST_FOCUS, new Object[] { WebPlayer.access$600(WebPlayer.this) });
    }

    public void onShowCustomView(View paramView, WebChromeClient.CustomViewCallback paramCustomViewCallback)
    {
      if (WebPlayer.this.shouldCallSuper("onShowCustomView"))
        super.onShowCustomView(paramView, paramCustomViewCallback);
      if (WebPlayer.this.shouldSendEvent("onShowCustomView"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.SHOW_CUSTOM_VIEW, new Object[] { WebPlayer.access$600(WebPlayer.this) });
    }

    public boolean onShowFileChooser(WebView paramWebView, ValueCallback paramValueCallback, WebChromeClient.FileChooserParams paramFileChooserParams)
    {
      Object localObject = Boolean.valueOf(false);
      if (WebPlayer.this.shouldCallSuper("onShowFileChooser"))
        localObject = Boolean.valueOf(super.onShowFileChooser(paramWebView, paramValueCallback, paramFileChooserParams));
      if (WebPlayer.this.shouldSendEvent("onShowFileChooser"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.SHOW_FILE_CHOOSER, new Object[] { WebPlayer.access$600(WebPlayer.this) });
      if (WebPlayer.this.hasReturnValue("onShowFileChooser"))
      {
        paramWebView = (Boolean)WebPlayer.this.getReturnValue("onShowFileChooser", Boolean.class, Boolean.valueOf(true));
        localObject = paramWebView;
        if (paramWebView.booleanValue())
        {
          paramValueCallback.onReceiveValue(null);
          localObject = paramWebView;
        }
      }
      return ((Boolean)localObject).booleanValue();
    }
  }

  private class WebPlayerClient extends WebViewClient
  {
    private WebPlayerClient()
    {
    }

    public void onFormResubmission(WebView paramWebView, Message paramMessage1, Message paramMessage2)
    {
      if (WebPlayer.this.shouldCallSuper("onFormResubmission"))
        super.onFormResubmission(paramWebView, paramMessage1, paramMessage2);
      if (WebPlayer.this.shouldSendEvent("onFormResubmission"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.FORM_RESUBMISSION, new Object[] { WebPlayer.access$600(WebPlayer.this) });
    }

    public void onLoadResource(WebView paramWebView, String paramString)
    {
      if (WebPlayer.this.shouldCallSuper("onLoadResource"))
        super.onLoadResource(paramWebView, paramString);
      if (WebPlayer.this.shouldSendEvent("onLoadResource"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.LOAD_RESOUCE, new Object[] { paramString, WebPlayer.access$600(WebPlayer.this) });
    }

    public void onPageCommitVisible(WebView paramWebView, String paramString)
    {
      if (WebPlayer.this.shouldCallSuper("onPageCommitVisible"))
        super.onPageCommitVisible(paramWebView, paramString);
      if (WebPlayer.this.shouldSendEvent("onPageCommitVisible"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.PAGE_COMMIT_VISIBLE, new Object[] { paramString, WebPlayer.access$600(WebPlayer.this) });
    }

    public void onPageFinished(WebView paramWebView, String paramString)
    {
      if (WebPlayer.this.shouldCallSuper("onPageFinished"))
        super.onPageFinished(paramWebView, paramString);
      if (WebPlayer.this.shouldSendEvent("onPageFinished"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.PAGE_FINISHED, new Object[] { paramString, WebPlayer.access$600(WebPlayer.this) });
    }

    public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
    {
      if (WebPlayer.this.shouldCallSuper("onPageStarted"))
        super.onPageStarted(paramWebView, paramString, paramBitmap);
      if (WebPlayer.this.shouldSendEvent("onPageStarted"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.PAGE_STARTED, new Object[] { paramString, WebPlayer.access$600(WebPlayer.this) });
    }

    public void onReceivedClientCertRequest(WebView paramWebView, ClientCertRequest paramClientCertRequest)
    {
      if (WebPlayer.this.shouldCallSuper("onReceivedClientCertRequest"))
        super.onReceivedClientCertRequest(paramWebView, paramClientCertRequest);
      if (WebPlayer.this.shouldSendEvent("onReceivedClientCertRequest"))
      {
        int i;
        if (paramClientCertRequest != null)
        {
          paramWebView = paramClientCertRequest.getHost();
          i = paramClientCertRequest.getPort();
        }
        else
        {
          paramWebView = "";
          i = -1;
        }
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.CLIENT_CERT_REQUEST, new Object[] { paramWebView, Integer.valueOf(i), WebPlayer.access$600(WebPlayer.this) });
      }
    }

    public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
    {
      if (WebPlayer.this.shouldCallSuper("onReceivedError"))
        super.onReceivedError(paramWebView, paramInt, paramString1, paramString2);
      if (WebPlayer.this.shouldSendEvent("onReceivedError"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.ERROR, new Object[] { paramString2, paramString1, WebPlayer.access$600(WebPlayer.this) });
    }

    public void onReceivedError(WebView paramWebView, WebResourceRequest paramWebResourceRequest, WebResourceError paramWebResourceError)
    {
      if (WebPlayer.this.shouldCallSuper("onReceivedError"))
        super.onReceivedError(paramWebView, paramWebResourceRequest, paramWebResourceError);
      if (WebPlayer.this.shouldSendEvent("onReceivedError"))
      {
        String str = "";
        if ((paramWebResourceError != null) && (paramWebResourceError.getDescription() != null))
          paramWebView = paramWebResourceError.getDescription().toString();
        else
          paramWebView = "";
        paramWebResourceError = str;
        if (paramWebResourceRequest != null)
        {
          paramWebResourceError = str;
          if (paramWebResourceRequest.getUrl() != null)
            paramWebResourceError = paramWebResourceRequest.getUrl().toString();
        }
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.ERROR, new Object[] { paramWebResourceError, paramWebView, WebPlayer.access$600(WebPlayer.this) });
      }
    }

    public void onReceivedHttpAuthRequest(WebView paramWebView, HttpAuthHandler paramHttpAuthHandler, String paramString1, String paramString2)
    {
      if (WebPlayer.this.shouldCallSuper("onReceivedHttpAuthRequest"))
        super.onReceivedHttpAuthRequest(paramWebView, paramHttpAuthHandler, paramString1, paramString2);
      if (WebPlayer.this.shouldSendEvent("onReceivedHttpAuthRequest"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.HTTP_AUTH_REQUEST, new Object[] { paramString1, paramString2, WebPlayer.access$600(WebPlayer.this) });
    }

    public void onReceivedHttpError(WebView paramWebView, WebResourceRequest paramWebResourceRequest, WebResourceResponse paramWebResourceResponse)
    {
      if (WebPlayer.this.shouldCallSuper("onReceivedHttpError"))
        super.onReceivedHttpError(paramWebView, paramWebResourceRequest, paramWebResourceResponse);
      if (WebPlayer.this.shouldSendEvent("onReceivedHttpError"))
      {
        String str = "";
        if ((paramWebResourceRequest != null) && (paramWebResourceRequest.getUrl() != null))
          paramWebView = paramWebResourceRequest.getUrl().toString();
        else
          paramWebView = "";
        int i = -1;
        paramWebResourceRequest = str;
        if (paramWebResourceResponse != null)
        {
          i = paramWebResourceResponse.getStatusCode();
          paramWebResourceRequest = paramWebResourceResponse.getReasonPhrase();
        }
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.HTTP_ERROR, new Object[] { paramWebView, paramWebResourceRequest, Integer.valueOf(i), WebPlayer.access$600(WebPlayer.this) });
      }
    }

    public void onReceivedLoginRequest(WebView paramWebView, String paramString1, String paramString2, String paramString3)
    {
      if (WebPlayer.this.shouldCallSuper("onReceivedLoginRequest"))
        super.onReceivedLoginRequest(paramWebView, paramString1, paramString2, paramString3);
      if (WebPlayer.this.shouldSendEvent("onReceivedLoginRequest"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.LOGIN_REQUEST, new Object[] { paramString1, paramString2, paramString3, WebPlayer.access$600(WebPlayer.this) });
    }

    public void onReceivedSslError(WebView paramWebView, SslErrorHandler paramSslErrorHandler, SslError paramSslError)
    {
      if (WebPlayer.this.shouldCallSuper("onReceivedSslError"))
        super.onReceivedSslError(paramWebView, paramSslErrorHandler, paramSslError);
      if (WebPlayer.this.shouldSendEvent("onReceivedSslError"))
      {
        if (paramSslError != null)
          paramWebView = paramSslError.getUrl();
        else
          paramWebView = "";
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.SSL_ERROR, new Object[] { paramWebView, WebPlayer.access$600(WebPlayer.this) });
      }
    }

    public void onScaleChanged(WebView paramWebView, float paramFloat1, float paramFloat2)
    {
      if (WebPlayer.this.shouldCallSuper("onScaleChanged"))
        super.onScaleChanged(paramWebView, paramFloat1, paramFloat2);
      if (WebPlayer.this.shouldSendEvent("onScaleChanged"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.SCALE_CHANGED, new Object[] { Float.valueOf(paramFloat1), Float.valueOf(paramFloat2), WebPlayer.access$600(WebPlayer.this) });
    }

    public void onUnhandledKeyEvent(WebView paramWebView, KeyEvent paramKeyEvent)
    {
      if (WebPlayer.this.shouldCallSuper("onUnhandledKeyEvent"))
        super.onUnhandledKeyEvent(paramWebView, paramKeyEvent);
      if (WebPlayer.this.shouldSendEvent("onUnhandledKeyEvent"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.UNHANDLED_KEY_EVENT, new Object[] { Integer.valueOf(paramKeyEvent.getKeyCode()), Integer.valueOf(paramKeyEvent.getAction()), WebPlayer.access$600(WebPlayer.this) });
    }

    public WebResourceResponse shouldInterceptRequest(WebView paramWebView, WebResourceRequest paramWebResourceRequest)
    {
      if (WebPlayer.this.shouldCallSuper("shouldInterceptRequest"))
        paramWebView = super.shouldInterceptRequest(paramWebView, paramWebResourceRequest);
      else
        paramWebView = null;
      if (WebPlayer.this.shouldSendEvent("shouldInterceptRequest"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.SHOULD_INTERCEPT_REQUEST, new Object[] { paramWebResourceRequest.getUrl().toString(), WebPlayer.access$600(WebPlayer.this) });
      return paramWebView;
    }

    public boolean shouldOverrideKeyEvent(WebView paramWebView, KeyEvent paramKeyEvent)
    {
      Boolean localBoolean = Boolean.valueOf(false);
      if (WebPlayer.this.shouldCallSuper("shouldOverrideKeyEvent"))
        localBoolean = Boolean.valueOf(super.shouldOverrideKeyEvent(paramWebView, paramKeyEvent));
      if (WebPlayer.this.shouldSendEvent("shouldOverrideKeyEvent"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.SHOULD_OVERRIDE_KEY_EVENT, new Object[] { Integer.valueOf(paramKeyEvent.getKeyCode()), Integer.valueOf(paramKeyEvent.getAction()), WebPlayer.access$600(WebPlayer.this) });
      if (WebPlayer.this.hasReturnValue("shouldOverrideKeyEvent"))
        localBoolean = (Boolean)WebPlayer.this.getReturnValue("shouldOverrideKeyEvent", Boolean.class, Boolean.valueOf(true));
      return localBoolean.booleanValue();
    }

    public boolean shouldOverrideUrlLoading(WebView paramWebView, WebResourceRequest paramWebResourceRequest)
    {
      Boolean localBoolean = Boolean.valueOf(false);
      if (WebPlayer.this.shouldCallSuper("shouldOverrideUrlLoading"))
        localBoolean = Boolean.valueOf(super.shouldOverrideUrlLoading(paramWebView, paramWebResourceRequest));
      if (WebPlayer.this.shouldSendEvent("shouldOverrideUrlLoading"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.SHOULD_OVERRIDE_URL_LOADING, new Object[] { paramWebResourceRequest.getUrl().toString(), paramWebResourceRequest.getMethod(), WebPlayer.access$600(WebPlayer.this) });
      if (WebPlayer.this.hasReturnValue("shouldOverrideUrlLoading"))
        localBoolean = (Boolean)WebPlayer.this.getReturnValue("shouldOverrideUrlLoading", Boolean.class, Boolean.valueOf(true));
      return localBoolean.booleanValue();
    }

    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      Boolean localBoolean = Boolean.valueOf(false);
      if (WebPlayer.this.shouldCallSuper("shouldOverrideUrlLoading"))
        localBoolean = Boolean.valueOf(super.shouldOverrideUrlLoading(paramWebView, paramString));
      if (WebPlayer.this.shouldSendEvent("shouldOverrideUrlLoading"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.SHOULD_OVERRIDE_URL_LOADING, new Object[] { paramString, WebPlayer.access$600(WebPlayer.this) });
      if (WebPlayer.this.hasReturnValue("shouldOverrideUrlLoading"))
        localBoolean = (Boolean)WebPlayer.this.getReturnValue("shouldOverrideUrlLoading", Boolean.class, Boolean.valueOf(true));
      return localBoolean.booleanValue();
    }
  }

  private class WebPlayerDownloadListener
    implements DownloadListener
  {
    private WebPlayerDownloadListener()
    {
    }

    public void onDownloadStart(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong)
    {
      if (WebPlayer.this.shouldSendEvent("onDownloadStart"))
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.WEBPLAYER, WebPlayerEvent.DOWNLOAD_START, new Object[] { paramString1, paramString2, paramString3, paramString4, Long.valueOf(paramLong), WebPlayer.access$600(WebPlayer.this) });
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.webplayer.WebPlayer
 * JD-Core Version:    0.6.0
 */