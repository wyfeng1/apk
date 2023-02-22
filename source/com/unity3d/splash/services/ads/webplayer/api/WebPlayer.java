package com.unity3d.splash.services.ads.webplayer.api;

import com.unity3d.splash.services.ads.adunit.AdUnitActivity;
import com.unity3d.splash.services.ads.adunit.IAdUnitViewHandler;
import com.unity3d.splash.services.ads.api.AdUnit;
import com.unity3d.splash.services.ads.webplayer.WebPlayerError;
import com.unity3d.splash.services.ads.webplayer.WebPlayerSettingsCache;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public class WebPlayer
{
  @WebViewExposed
  public static void clearSettings(String paramString, WebViewCallback paramWebViewCallback)
  {
    WebPlayerSettingsCache localWebPlayerSettingsCache = WebPlayerSettingsCache.getInstance();
    localWebPlayerSettingsCache.removeWebSettings(paramString);
    localWebPlayerSettingsCache.removeWebPlayerSettings(paramString);
    localWebPlayerSettingsCache.removeWebPlayerEventSettings(paramString);
    paramWebViewCallback.invoke(new Object[0]);
  }

  private static com.unity3d.splash.services.ads.webplayer.WebPlayer getAdUnitWebPlayer()
  {
    if (AdUnit.getAdUnitActivity() != null)
    {
      Object localObject = AdUnit.getAdUnitActivity().getViewHandler("webplayer");
      if (localObject != null)
      {
        localObject = ((IAdUnitViewHandler)localObject).getView();
        if (localObject != null)
          return (com.unity3d.splash.services.ads.webplayer.WebPlayer)localObject;
      }
    }
    return (com.unity3d.splash.services.ads.webplayer.WebPlayer)null;
  }

  @WebViewExposed
  public static void getErroredSettings(String paramString, WebViewCallback paramWebViewCallback)
  {
    paramString = getWebPlayer(paramString);
    if (paramString != null)
    {
      Object localObject = paramString.getErroredSettings();
      paramString = new JSONObject();
      try
      {
        Iterator localIterator = ((Map)localObject).entrySet().iterator();
        while (localIterator.hasNext())
        {
          localObject = (Map.Entry)localIterator.next();
          paramString.put((String)((Map.Entry)localObject).getKey(), ((Map.Entry)localObject).getValue());
        }
      }
      catch (Exception localException)
      {
        DeviceLog.exception("Error forming JSON object", localException);
        paramWebViewCallback.invoke(new Object[] { paramString });
        paramWebViewCallback.invoke(new Object[0]);
        return;
      }
    }
    paramWebViewCallback.error(WebPlayerError.WEBPLAYER_NULL, new Object[0]);
  }

  private static com.unity3d.splash.services.ads.webplayer.WebPlayer getWebPlayer(String paramString)
  {
    int i;
    if ((paramString.hashCode() == -318269643) && (paramString.equals("webplayer")))
      i = 0;
    else
      i = -1;
    if (i != 0)
      return null;
    return getAdUnitWebPlayer();
  }

  @WebViewExposed
  public static void sendEvent(JSONArray paramJSONArray, String paramString, WebViewCallback paramWebViewCallback)
  {
    paramString = getWebPlayer(paramString);
    if (paramString != null)
    {
      paramString.sendEvent(paramJSONArray);
      paramWebViewCallback.invoke(new Object[0]);
      return;
    }
    paramWebViewCallback.error(WebPlayerError.WEBPLAYER_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void setData(String paramString1, String paramString2, String paramString3, String paramString4, WebViewCallback paramWebViewCallback)
  {
    paramString4 = getWebPlayer(paramString4);
    if (paramString4 != null)
    {
      Utilities.runOnUiThread(new Runnable(paramString4, paramString1, paramString2, paramString3)
      {
        public final void run()
        {
          this.val$webPlayer.loadData(this.val$data, this.val$mimeType, this.val$encoding);
        }
      });
      paramWebViewCallback.invoke(new Object[0]);
      return;
    }
    paramWebViewCallback.error(WebPlayerError.WEBPLAYER_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void setDataWithUrl(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, WebViewCallback paramWebViewCallback)
  {
    paramString5 = getWebPlayer(paramString5);
    if (paramString5 != null)
    {
      Utilities.runOnUiThread(new Runnable(paramString5, paramString1, paramString2, paramString3, paramString4)
      {
        public final void run()
        {
          this.val$webPlayer.loadDataWithBaseURL(this.val$baseUrl, this.val$data, this.val$mimeType, this.val$encoding, null);
        }
      });
      paramWebViewCallback.invoke(new Object[0]);
      return;
    }
    paramWebViewCallback.error(WebPlayerError.WEBPLAYER_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void setEventSettings(JSONObject paramJSONObject, String paramString, WebViewCallback paramWebViewCallback)
  {
    WebPlayerSettingsCache.getInstance().addWebPlayerEventSettings(paramString, paramJSONObject);
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void setSettings(JSONObject paramJSONObject1, JSONObject paramJSONObject2, String paramString, WebViewCallback paramWebViewCallback)
  {
    WebPlayerSettingsCache.getInstance().addWebSettings(paramString, paramJSONObject1);
    WebPlayerSettingsCache.getInstance().addWebPlayerSettings(paramString, paramJSONObject2);
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void setUrl(String paramString1, String paramString2, WebViewCallback paramWebViewCallback)
  {
    paramString2 = getWebPlayer(paramString2);
    if (paramString2 != null)
    {
      Utilities.runOnUiThread(new Runnable(paramString2, paramString1)
      {
        public final void run()
        {
          this.val$webPlayer.loadUrl(this.val$url);
        }
      });
      paramWebViewCallback.invoke(new Object[0]);
      return;
    }
    paramWebViewCallback.error(WebPlayerError.WEBPLAYER_NULL, new Object[0]);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.webplayer.api.WebPlayer
 * JD-Core Version:    0.6.0
 */