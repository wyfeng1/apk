package com.unity3d.splash.services.ads.load;

import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONObject;

public class LoadBridge
  implements ILoadBridge
{
  public void loadPlacements(Map paramMap)
  {
    try
    {
      localObject = new org/json/JSONObject;
      ((JSONObject)localObject).<init>();
      paramMap = paramMap.entrySet().iterator();
      while (paramMap.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramMap.next();
        ((JSONObject)localObject).put((String)localEntry.getKey(), ((Integer)localEntry.getValue()).intValue());
      }
      if (WebViewApp.getCurrentApp() != null)
        WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.LOAD_API, LoadEvent.LOAD_PLACEMENTS, new Object[] { localObject });
      return;
    }
    catch (Exception paramMap)
    {
      Object localObject = new StringBuilder("An exception was thrown while loading placements ");
      ((StringBuilder)localObject).append(paramMap.getLocalizedMessage());
      DeviceLog.error(((StringBuilder)localObject).toString());
    }
  }

  public static enum LoadEvent
  {
    static
    {
      LoadEvent localLoadEvent = new LoadEvent("LOAD_PLACEMENTS", 0);
      LOAD_PLACEMENTS = localLoadEvent;
      $VALUES = new LoadEvent[] { localLoadEvent };
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.load.LoadBridge
 * JD-Core Version:    0.6.0
 */