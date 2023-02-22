package com.unity3d.splash.services.core.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import com.unity3d.splash.services.core.properties.ClientProperties;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BroadcastMonitor
{
  private static Map _eventReceivers;

  public static void addBroadcastListener(String paramString1, String paramString2, String[] paramArrayOfString)
  {
    removeBroadcastListener(paramString1);
    IntentFilter localIntentFilter = new IntentFilter();
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
      localIntentFilter.addAction(paramArrayOfString[j]);
    if (paramString2 != null)
      localIntentFilter.addDataScheme(paramString2);
    if (_eventReceivers == null)
      _eventReceivers = new HashMap();
    paramString2 = new BroadcastEventReceiver(paramString1);
    _eventReceivers.put(paramString1, paramString2);
    ClientProperties.getApplicationContext().registerReceiver(paramString2, localIntentFilter);
  }

  public static void removeAllBroadcastListeners()
  {
    Object localObject = _eventReceivers;
    if (localObject != null)
    {
      localObject = ((Map)localObject).keySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        String str = (String)((Iterator)localObject).next();
        ClientProperties.getApplicationContext().unregisterReceiver((BroadcastReceiver)_eventReceivers.get(str));
      }
      _eventReceivers = null;
    }
  }

  public static void removeBroadcastListener(String paramString)
  {
    Map localMap = _eventReceivers;
    if ((localMap != null) && (localMap.containsKey(paramString)))
    {
      ClientProperties.getApplicationContext().unregisterReceiver((BroadcastReceiver)_eventReceivers.get(paramString));
      _eventReceivers.remove(paramString);
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.broadcast.BroadcastMonitor
 * JD-Core Version:    0.6.0
 */