package com.unity3d.splash.services.core.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest.Builder;
import com.unity3d.splash.services.core.properties.ClientProperties;

public class ConnectivityNetworkCallback extends ConnectivityManager.NetworkCallback
{
  private static ConnectivityNetworkCallback _impl;

  public static void register()
  {
    if (_impl == null)
    {
      _impl = new ConnectivityNetworkCallback();
      ((ConnectivityManager)ClientProperties.getApplicationContext().getSystemService("connectivity")).registerNetworkCallback(new NetworkRequest.Builder().build(), _impl);
    }
  }

  public static void unregister()
  {
    if (_impl != null)
    {
      ((ConnectivityManager)ClientProperties.getApplicationContext().getSystemService("connectivity")).unregisterNetworkCallback(_impl);
      _impl = null;
    }
  }

  public void onAvailable(Network paramNetwork)
  {
    ConnectivityMonitor.connected();
  }

  public void onCapabilitiesChanged(Network paramNetwork, NetworkCapabilities paramNetworkCapabilities)
  {
    ConnectivityMonitor.connectionStatusChanged();
  }

  public void onLinkPropertiesChanged(Network paramNetwork, LinkProperties paramLinkProperties)
  {
    ConnectivityMonitor.connectionStatusChanged();
  }

  public void onLost(Network paramNetwork)
  {
    ConnectivityMonitor.disconnected();
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.connectivity.ConnectivityNetworkCallback
 * JD-Core Version:    0.6.0
 */