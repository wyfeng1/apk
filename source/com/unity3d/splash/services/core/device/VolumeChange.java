package com.unity3d.splash.services.core.device;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings.System;
import com.unity3d.splash.services.core.properties.ClientProperties;
import java.util.ArrayList;
import java.util.Iterator;

public class VolumeChange
{
  private static ContentObserver _contentObserver;
  private static ArrayList _listeners;

  public static void clearAllListeners()
  {
    ArrayList localArrayList = _listeners;
    if (localArrayList != null)
      localArrayList.clear();
    stopObservering();
    _listeners = null;
  }

  public static void registerListener(IVolumeChangeListener paramIVolumeChangeListener)
  {
    if (_listeners == null)
      _listeners = new ArrayList();
    if (!_listeners.contains(paramIVolumeChangeListener))
    {
      startObserving();
      _listeners.add(paramIVolumeChangeListener);
    }
  }

  public static void startObserving()
  {
    if (_contentObserver == null)
    {
      _contentObserver = new ContentObserver(new Handler(Looper.getMainLooper()))
      {
        public final boolean deliverSelfNotifications()
        {
          return false;
        }

        public final void onChange(boolean paramBoolean, Uri paramUri)
        {
          VolumeChange.access$000();
        }
      };
      Object localObject = ClientProperties.getApplicationContext();
      if (localObject != null)
      {
        localObject = ((Context)localObject).getContentResolver();
        if (localObject != null)
          ((ContentResolver)localObject).registerContentObserver(Settings.System.CONTENT_URI, true, _contentObserver);
      }
    }
  }

  public static void stopObservering()
  {
    if (_contentObserver != null)
    {
      Object localObject = ClientProperties.getApplicationContext();
      if (localObject != null)
      {
        localObject = ((Context)localObject).getContentResolver();
        if (localObject != null)
          ((ContentResolver)localObject).unregisterContentObserver(_contentObserver);
      }
      _contentObserver = null;
    }
  }

  private static void triggerListeners()
  {
    Object localObject = _listeners;
    if (localObject != null)
    {
      localObject = ((ArrayList)localObject).iterator();
      while (((Iterator)localObject).hasNext())
      {
        IVolumeChangeListener localIVolumeChangeListener = (IVolumeChangeListener)((Iterator)localObject).next();
        localIVolumeChangeListener.onVolumeChanged(Device.getStreamVolume(localIVolumeChangeListener.getStreamType()));
      }
    }
  }

  public static void unregisterListener(IVolumeChangeListener paramIVolumeChangeListener)
  {
    if (_listeners.contains(paramIVolumeChangeListener))
      _listeners.remove(paramIVolumeChangeListener);
    paramIVolumeChangeListener = _listeners;
    if ((paramIVolumeChangeListener == null) || (paramIVolumeChangeListener.size() == 0))
      stopObservering();
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.device.VolumeChange
 * JD-Core Version:    0.6.0
 */