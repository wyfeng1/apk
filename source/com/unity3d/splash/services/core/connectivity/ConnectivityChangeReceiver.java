package com.unity3d.splash.services.core.connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.unity3d.splash.services.core.properties.ClientProperties;

public class ConnectivityChangeReceiver extends BroadcastReceiver
{
  private static ConnectivityChangeReceiver _receiver;

  public static void register()
  {
    if (_receiver == null)
    {
      _receiver = new ConnectivityChangeReceiver();
      ClientProperties.getApplicationContext().registerReceiver(_receiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }
  }

  public static void unregister()
  {
    if (_receiver != null)
    {
      ClientProperties.getApplicationContext().unregisterReceiver(_receiver);
      _receiver = null;
    }
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (paramIntent.getBooleanExtra("noConnectivity", false))
    {
      ConnectivityMonitor.disconnected();
      return;
    }
    paramContext = (ConnectivityManager)paramContext.getSystemService("connectivity");
    if (paramContext == null)
      return;
    paramContext = paramContext.getActiveNetworkInfo();
    if ((paramContext != null) && (paramContext.isConnected()))
      ConnectivityMonitor.connected();
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.connectivity.ConnectivityChangeReceiver
 * JD-Core Version:    0.6.0
 */