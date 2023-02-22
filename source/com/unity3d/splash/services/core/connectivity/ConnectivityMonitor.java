package com.unity3d.splash.services.core.connectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import java.util.HashSet;
import java.util.Iterator;

public class ConnectivityMonitor
{
  private static int _connected = -1;
  private static HashSet _listeners;
  private static boolean _listening = false;
  private static int _networkType = -1;
  private static boolean _webappMonitoring = false;
  private static boolean _wifi = false;

  public static void addListener(IConnectivityListener paramIConnectivityListener)
  {
    if (_listeners == null)
      _listeners = new HashSet();
    _listeners.add(paramIConnectivityListener);
    updateListeningStatus();
  }

  public static void connected()
  {
    if (_connected == 1)
      return;
    DeviceLog.debug("Unity Ads connectivity change: connected");
    initConnectionStatus();
    Object localObject = _listeners;
    if (localObject != null)
    {
      localObject = ((HashSet)localObject).iterator();
      while (((Iterator)localObject).hasNext())
        ((IConnectivityListener)((Iterator)localObject).next()).onConnected();
    }
    sendToWebview(ConnectivityEvent.CONNECTED, _wifi, _networkType);
  }

  public static void connectionStatusChanged()
  {
    int i = _connected;
    boolean bool1 = true;
    if (i != 1)
      return;
    NetworkInfo localNetworkInfo = ((ConnectivityManager)ClientProperties.getApplicationContext().getSystemService("connectivity")).getActiveNetworkInfo();
    if ((localNetworkInfo != null) && (localNetworkInfo.isConnected()))
    {
      if (localNetworkInfo.getType() != 1)
        bool1 = false;
      i = ((TelephonyManager)ClientProperties.getApplicationContext().getSystemService("phone")).getNetworkType();
      boolean bool2 = _wifi;
      if ((bool1 == bool2) && ((i == _networkType) || (bool2)))
        return;
      _wifi = bool1;
      _networkType = i;
      DeviceLog.debug("Unity Ads connectivity change: network change");
      sendToWebview(ConnectivityEvent.NETWORK_CHANGE, bool1, i);
    }
  }

  public static void disconnected()
  {
    if (_connected == 0)
      return;
    _connected = 0;
    DeviceLog.debug("Unity Ads connectivity change: disconnected");
    Object localObject = _listeners;
    if (localObject != null)
    {
      localObject = ((HashSet)localObject).iterator();
      while (((Iterator)localObject).hasNext())
        ((IConnectivityListener)((Iterator)localObject).next()).onDisconnected();
    }
    sendToWebview(ConnectivityEvent.DISCONNECTED, false, 0);
  }

  private static void initConnectionStatus()
  {
    Object localObject = (ConnectivityManager)ClientProperties.getApplicationContext().getSystemService("connectivity");
    if (localObject == null)
      return;
    localObject = ((ConnectivityManager)localObject).getActiveNetworkInfo();
    boolean bool = false;
    if ((localObject != null) && (((NetworkInfo)localObject).isConnected()))
    {
      _connected = 1;
      if (((NetworkInfo)localObject).getType() == 1)
        bool = true;
      _wifi = bool;
      if (!bool)
      {
        _networkType = ((TelephonyManager)ClientProperties.getApplicationContext().getSystemService("phone")).getNetworkType();
        return;
      }
    }
    else
    {
      _connected = 0;
    }
  }

  public static void removeListener(IConnectivityListener paramIConnectivityListener)
  {
    HashSet localHashSet = _listeners;
    if (localHashSet == null)
      return;
    localHashSet.remove(paramIConnectivityListener);
    updateListeningStatus();
  }

  private static void sendToWebview(ConnectivityEvent paramConnectivityEvent, boolean paramBoolean, int paramInt)
  {
    if (!_webappMonitoring)
      return;
    WebViewApp localWebViewApp = WebViewApp.getCurrentApp();
    if ((localWebViewApp != null) && (localWebViewApp.isWebAppLoaded()))
    {
      int i = 1.$SwitchMap$com$unity3d$splash$services$core$connectivity$ConnectivityEvent[paramConnectivityEvent.ordinal()];
      if (i != 1)
      {
        if (i != 2)
        {
          if (i == 3)
          {
            paramConnectivityEvent = WebViewEventCategory.CONNECTIVITY;
            if (paramBoolean)
            {
              localWebViewApp.sendEvent(paramConnectivityEvent, ConnectivityEvent.NETWORK_CHANGE, new Object[] { Boolean.valueOf(paramBoolean), Integer.valueOf(0) });
              return;
            }
            localWebViewApp.sendEvent(paramConnectivityEvent, ConnectivityEvent.NETWORK_CHANGE, new Object[] { Boolean.valueOf(paramBoolean), Integer.valueOf(paramInt) });
          }
          return;
        }
        localWebViewApp.sendEvent(WebViewEventCategory.CONNECTIVITY, ConnectivityEvent.DISCONNECTED, new Object[0]);
        return;
      }
      paramConnectivityEvent = WebViewEventCategory.CONNECTIVITY;
      if (paramBoolean)
      {
        localWebViewApp.sendEvent(paramConnectivityEvent, ConnectivityEvent.CONNECTED, new Object[] { Boolean.valueOf(paramBoolean), Integer.valueOf(0) });
        return;
      }
      localWebViewApp.sendEvent(paramConnectivityEvent, ConnectivityEvent.CONNECTED, new Object[] { Boolean.valueOf(paramBoolean), Integer.valueOf(paramInt) });
    }
  }

  public static void setConnectionMonitoring(boolean paramBoolean)
  {
    _webappMonitoring = paramBoolean;
    updateListeningStatus();
  }

  private static void startListening()
  {
    if (_listening)
      return;
    _listening = true;
    initConnectionStatus();
    if (Build.VERSION.SDK_INT < 21)
    {
      ConnectivityChangeReceiver.register();
      return;
    }
    ConnectivityNetworkCallback.register();
  }

  public static void stopAll()
  {
    _listeners = null;
    _webappMonitoring = false;
    updateListeningStatus();
  }

  private static void stopListening()
  {
    if (!_listening)
      return;
    _listening = false;
    if (Build.VERSION.SDK_INT < 21)
    {
      ConnectivityChangeReceiver.unregister();
      return;
    }
    ConnectivityNetworkCallback.unregister();
  }

  private static void updateListeningStatus()
  {
    if (!_webappMonitoring)
    {
      HashSet localHashSet = _listeners;
      if ((localHashSet == null) || (localHashSet.isEmpty()))
      {
        stopListening();
        return;
      }
    }
    startListening();
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.connectivity.ConnectivityMonitor
 * JD-Core Version:    0.6.0
 */