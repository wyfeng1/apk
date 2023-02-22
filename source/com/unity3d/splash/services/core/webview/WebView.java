package com.unity3d.splash.services.core.webview;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebSettings.RenderPriority;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.misc.ViewUtilities;
import com.unity3d.splash.services.core.webview.bridge.WebViewBridgeInterface;
import java.lang.reflect.Method;

public class WebView extends android.webkit.WebView
{
  private static Method _evaluateJavascript;

  public WebView(Context paramContext)
  {
    super(paramContext);
    paramContext = getSettings();
    if (Build.VERSION.SDK_INT >= 16)
    {
      paramContext.setAllowFileAccessFromFileURLs(true);
      paramContext.setAllowUniversalAccessFromFileURLs(true);
    }
    if (Build.VERSION.SDK_INT >= 19)
      try
      {
        _evaluateJavascript = android.webkit.WebView.class.getMethod("evaluateJavascript", new Class[] { String.class, ValueCallback.class });
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        DeviceLog.exception("Method evaluateJavascript not found", localNoSuchMethodException);
        _evaluateJavascript = null;
      }
    paramContext.setAppCacheEnabled(false);
    paramContext.setBlockNetworkImage(false);
    paramContext.setBlockNetworkLoads(false);
    paramContext.setBuiltInZoomControls(false);
    paramContext.setCacheMode(2);
    paramContext.setDatabaseEnabled(false);
    if (Build.VERSION.SDK_INT >= 11)
      paramContext.setDisplayZoomControls(false);
    paramContext.setDomStorageEnabled(false);
    if (Build.VERSION.SDK_INT >= 11)
      paramContext.setEnableSmoothTransition(false);
    paramContext.setGeolocationEnabled(false);
    paramContext.setJavaScriptCanOpenWindowsAutomatically(false);
    paramContext.setJavaScriptEnabled(true);
    paramContext.setLightTouchEnabled(false);
    paramContext.setLoadWithOverviewMode(false);
    paramContext.setLoadsImagesAutomatically(true);
    if (Build.VERSION.SDK_INT >= 17)
      paramContext.setMediaPlaybackRequiresUserGesture(false);
    if (Build.VERSION.SDK_INT >= 21)
      paramContext.setMixedContentMode(1);
    paramContext.setNeedInitialFocus(true);
    paramContext.setPluginState(WebSettings.PluginState.OFF);
    paramContext.setRenderPriority(WebSettings.RenderPriority.NORMAL);
    paramContext.setSaveFormData(false);
    paramContext.setSavePassword(false);
    paramContext.setSupportMultipleWindows(false);
    paramContext.setSupportZoom(false);
    paramContext.setUseWideViewPort(true);
    setHorizontalScrollBarEnabled(false);
    setVerticalScrollBarEnabled(false);
    setInitialScale(0);
    setBackgroundColor(0);
    ViewUtilities.setBackground(this, new ColorDrawable(0));
    setBackgroundResource(0);
    addJavascriptInterface(new WebViewBridgeInterface(), "webviewbridge");
  }

  public void invokeJavascript(String paramString)
  {
    Utilities.runOnUiThread(new JavaScriptInvocation(paramString, this));
  }

  public void loadUrl(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder("Loading url: ");
    localStringBuilder.append(paramString);
    DeviceLog.debug(localStringBuilder.toString());
    super.loadUrl(paramString);
  }

  private class JavaScriptInvocation
    implements Runnable
  {
    private String _jsString = null;
    private android.webkit.WebView _webView = null;

    public JavaScriptInvocation(String paramWebView, android.webkit.WebView arg3)
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
            WebView._evaluateJavascript.invoke(this._webView, new Object[] { this._jsString, null });
            return;
          }
          WebView.this.loadUrl(this._jsString);
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
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.webview.WebView
 * JD-Core Version:    0.6.0
 */