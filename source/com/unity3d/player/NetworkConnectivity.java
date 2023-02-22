package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.ConnectivityManager.NetworkCallback;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

public class NetworkConnectivity extends Activity
{
  private final int a = 0;
  private final int b;
  private final int c;
  private int d;
  private ConnectivityManager e;
  private final ConnectivityManager.NetworkCallback f;

  public NetworkConnectivity(Context paramContext)
  {
    int i = 1;
    this.b = 1;
    this.c = 2;
    this.d = 0;
    this.f = new ConnectivityManager.NetworkCallback()
    {
      public final void onAvailable(Network paramNetwork)
      {
        super.onAvailable(paramNetwork);
      }

      public final void onCapabilitiesChanged(Network paramNetwork, NetworkCapabilities paramNetworkCapabilities)
      {
        super.onCapabilitiesChanged(paramNetwork, paramNetworkCapabilities);
        if (paramNetworkCapabilities.hasTransport(0))
          paramNetwork = NetworkConnectivity.this;
        for (int i = 1; ; i = 2)
        {
          NetworkConnectivity.a(paramNetwork, i);
          return;
          paramNetwork = NetworkConnectivity.this;
        }
      }

      public final void onLost(Network paramNetwork)
      {
        super.onLost(paramNetwork);
        NetworkConnectivity.a(NetworkConnectivity.this, 0);
      }

      public final void onUnavailable()
      {
        super.onUnavailable();
        NetworkConnectivity.a(NetworkConnectivity.this, 0);
      }
    };
    paramContext = (ConnectivityManager)paramContext.getSystemService("connectivity");
    this.e = paramContext;
    paramContext.registerDefaultNetworkCallback(this.f);
    paramContext = this.e.getActiveNetworkInfo();
    if ((paramContext != null) && (paramContext.isConnected()))
    {
      if (paramContext.getType() != 0)
        i = 2;
      this.d = i;
    }
  }

  public final int a()
  {
    return this.d;
  }

  public final void b()
  {
    this.e.unregisterNetworkCallback(this.f);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.NetworkConnectivity
 * JD-Core Version:    0.6.0
 */