package com.unity3d.splash.services.core.device;

import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.JsonStorage;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import java.io.File;
import org.json.JSONObject;

public class Storage extends JsonStorage
{
  private String _targetFileName;
  private StorageManager.StorageType _type;

  public Storage(String paramString, StorageManager.StorageType paramStorageType)
  {
    this._targetFileName = paramString;
    this._type = paramStorageType;
  }

  public boolean clearStorage()
  {
    monitorenter;
    try
    {
      clearData();
      File localFile = new java/io/File;
      localFile.<init>(this._targetFileName);
      boolean bool = localFile.delete();
      monitorexit;
      return bool;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public StorageManager.StorageType getType()
  {
    return this._type;
  }

  public boolean initStorage()
  {
    monitorenter;
    try
    {
      readStorage();
      super.initData();
      monitorexit;
      return true;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public boolean readStorage()
  {
    monitorenter;
    try
    {
      Object localObject1 = new java/io/File;
      ((File)localObject1).<init>(this._targetFileName);
      try
      {
        Object localObject3 = Utilities.readFileBytes((File)localObject1);
        if (localObject3 == null)
        {
          monitorexit;
          return false;
        }
        localObject1 = new java/lang/String;
        ((String)localObject1).<init>(localObject3);
        localObject3 = new org/json/JSONObject;
        ((JSONObject)localObject3).<init>((String)localObject1);
        setData((JSONObject)localObject3);
        monitorexit;
        return true;
      }
      catch (Exception localException)
      {
        DeviceLog.exception("Error creating storage JSON", localException);
        monitorexit;
        return false;
      }
    }
    finally
    {
      monitorexit;
    }
    throw localObject2;
  }

  public void sendEvent(StorageEvent paramStorageEvent, Object paramObject)
  {
    monitorenter;
    try
    {
      WebViewApp localWebViewApp = WebViewApp.getCurrentApp();
      boolean bool = false;
      if (localWebViewApp != null)
        bool = WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.STORAGE, paramStorageEvent, new Object[] { this._type.name(), paramObject });
      if (!bool)
        DeviceLog.debug("Couldn't send storage event to WebApp");
      return;
    }
    finally
    {
      monitorexit;
    }
    throw paramStorageEvent;
  }

  public boolean storageFileExists()
  {
    monitorenter;
    try
    {
      File localFile = new java/io/File;
      localFile.<init>(this._targetFileName);
      boolean bool = localFile.exists();
      monitorexit;
      return bool;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public boolean writeStorage()
  {
    monitorenter;
    try
    {
      File localFile = new java/io/File;
      localFile.<init>(this._targetFileName);
      if (getData() != null)
      {
        boolean bool = Utilities.writeFile(localFile, getData().toString());
        monitorexit;
        return bool;
      }
      monitorexit;
      return false;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.device.Storage
 * JD-Core Version:    0.6.0
 */