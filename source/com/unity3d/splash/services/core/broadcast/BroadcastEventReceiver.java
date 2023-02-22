package com.unity3d.splash.services.core.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

public class BroadcastEventReceiver extends BroadcastReceiver
{
  private String _name;

  public BroadcastEventReceiver(String paramString)
  {
    this._name = paramString;
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    String str1 = paramIntent.getAction();
    if (str1 == null)
      return;
    if (paramIntent.getDataString() != null)
      paramContext = paramIntent.getDataString();
    else
      paramContext = "";
    JSONObject localJSONObject = new JSONObject();
    try
    {
      if (paramIntent.getExtras() != null)
      {
        paramIntent = paramIntent.getExtras();
        Iterator localIterator = paramIntent.keySet().iterator();
        while (localIterator.hasNext())
        {
          String str2 = (String)localIterator.next();
          localJSONObject.put(str2, paramIntent.get(str2));
        }
      }
    }
    catch (JSONException localJSONException)
    {
      paramIntent = new StringBuilder("JSONException when composing extras for broadcast action ");
      paramIntent.append(str1);
      paramIntent.append(": ");
      paramIntent.append(localJSONException.getMessage());
      DeviceLog.debug(paramIntent.toString());
      paramIntent = WebViewApp.getCurrentApp();
      if ((paramIntent != null) && (paramIntent.isWebAppLoaded()))
        paramIntent.sendEvent(WebViewEventCategory.BROADCAST, BroadcastEvent.ACTION, new Object[] { this._name, str1, paramContext, localJSONObject });
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.broadcast.BroadcastEventReceiver
 * JD-Core Version:    0.6.0
 */