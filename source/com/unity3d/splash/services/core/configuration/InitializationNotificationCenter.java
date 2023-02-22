package com.unity3d.splash.services.core.configuration;

import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class InitializationNotificationCenter
  implements IInitializationNotificationCenter
{
  private static InitializationNotificationCenter instance;
  private HashMap _sdkListeners = new HashMap();

  public static InitializationNotificationCenter getInstance()
  {
    if (instance == null)
      instance = new InitializationNotificationCenter();
    return instance;
  }

  private void removeListener(Integer paramInteger)
  {
    this._sdkListeners.remove(paramInteger);
  }

  public void addListener(IInitializationListener paramIInitializationListener)
  {
    HashMap localHashMap1 = this._sdkListeners;
    monitorenter;
    if (paramIInitializationListener != null);
    try
    {
      HashMap localHashMap2 = this._sdkListeners;
      Integer localInteger = new java/lang/Integer;
      localInteger.<init>(paramIInitializationListener.hashCode());
      WeakReference localWeakReference = new java/lang/ref/WeakReference;
      localWeakReference.<init>(paramIInitializationListener);
      localHashMap2.put(localInteger, localWeakReference);
      return;
    }
    finally
    {
      monitorexit;
    }
    throw paramIInitializationListener;
  }

  public void removeListener(IInitializationListener paramIInitializationListener)
  {
    HashMap localHashMap = this._sdkListeners;
    monitorenter;
    if (paramIInitializationListener != null);
    try
    {
      Integer localInteger = new java/lang/Integer;
      localInteger.<init>(paramIInitializationListener.hashCode());
      removeListener(localInteger);
      return;
    }
    finally
    {
      monitorexit;
    }
    throw paramIInitializationListener;
  }

  public void triggerOnSdkInitializationFailed(String paramString, int paramInt)
  {
    synchronized (this._sdkListeners)
    {
      Object localObject1 = new java/lang/StringBuilder;
      ((StringBuilder)localObject1).<init>("SDK Failed to Initialize due to ");
      ((StringBuilder)localObject1).append(paramString);
      localObject1 = ((StringBuilder)localObject1).toString();
      DeviceLog.error((String)localObject1);
      paramString = new java/util/ArrayList;
      paramString.<init>();
      Iterator localIterator = this._sdkListeners.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Object localObject2 = (Map.Entry)localIterator.next();
        if (((WeakReference)((Map.Entry)localObject2).getValue()).get() != null)
        {
          IInitializationListener localIInitializationListener = (IInitializationListener)((WeakReference)((Map.Entry)localObject2).getValue()).get();
          localObject2 = new com/unity3d/splash/services/core/configuration/InitializationNotificationCenter$2;
          ((2)localObject2).<init>(this, localIInitializationListener, (String)localObject1, paramInt);
          Utilities.runOnUiThread((Runnable)localObject2);
          continue;
        }
        paramString.add(((Map.Entry)localObject2).getKey());
      }
      paramString = paramString.iterator();
      while (paramString.hasNext())
      {
        localObject1 = (Integer)paramString.next();
        this._sdkListeners.remove(localObject1);
      }
      return;
    }
  }

  public void triggerOnSdkInitialized()
  {
    synchronized (this._sdkListeners)
    {
      Object localObject1 = new java/util/ArrayList;
      ((ArrayList)localObject1).<init>();
      Object localObject2 = this._sdkListeners.entrySet().iterator();
      while (((Iterator)localObject2).hasNext())
      {
        Object localObject4 = (Map.Entry)((Iterator)localObject2).next();
        if (((WeakReference)((Map.Entry)localObject4).getValue()).get() != null)
        {
          localObject4 = (IInitializationListener)((WeakReference)((Map.Entry)localObject4).getValue()).get();
          1 local1 = new com/unity3d/splash/services/core/configuration/InitializationNotificationCenter$1;
          local1.<init>(this, (IInitializationListener)localObject4);
          Utilities.runOnUiThread(local1);
          continue;
        }
        ((ArrayList)localObject1).add(((Map.Entry)localObject4).getKey());
      }
      localObject1 = ((ArrayList)localObject1).iterator();
      while (((Iterator)localObject1).hasNext())
      {
        localObject2 = (Integer)((Iterator)localObject1).next();
        this._sdkListeners.remove(localObject2);
      }
      return;
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.configuration.InitializationNotificationCenter
 * JD-Core Version:    0.6.0
 */