package com.unity3d.splash.services.core.connectivity;

public enum ConnectivityEvent
{
  static
  {
    ConnectivityEvent localConnectivityEvent = new ConnectivityEvent("NETWORK_CHANGE", 2);
    NETWORK_CHANGE = localConnectivityEvent;
    $VALUES = new ConnectivityEvent[] { CONNECTED, DISCONNECTED, localConnectivityEvent };
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.connectivity.ConnectivityEvent
 * JD-Core Version:    0.6.0
 */