package com.unity3d.splash.services.core.api;

import com.unity3d.splash.services.core.device.StorageError;
import com.unity3d.splash.services.core.device.StorageManager;
import com.unity3d.splash.services.core.device.StorageManager.StorageType;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import org.json.JSONArray;
import org.json.JSONObject;

public class Storage
{
  @WebViewExposed
  public static void clear(String paramString, WebViewCallback paramWebViewCallback)
  {
    com.unity3d.splash.services.core.device.Storage localStorage = getStorage(paramString);
    if (localStorage != null)
    {
      if (localStorage.clearStorage())
      {
        paramWebViewCallback.invoke(new Object[] { paramString });
        return;
      }
      paramWebViewCallback.error(StorageError.COULDNT_CLEAR_STORAGE, new Object[] { paramString });
      return;
    }
    paramWebViewCallback.error(StorageError.COULDNT_GET_STORAGE, new Object[] { paramString });
  }

  @WebViewExposed
  public static void delete(String paramString1, String paramString2, WebViewCallback paramWebViewCallback)
  {
    com.unity3d.splash.services.core.device.Storage localStorage = getStorage(paramString1);
    if (localStorage != null)
    {
      if (localStorage.delete(paramString2))
      {
        paramWebViewCallback.invoke(new Object[] { paramString1 });
        return;
      }
      paramWebViewCallback.error(StorageError.COULDNT_DELETE_VALUE, new Object[] { paramString1 });
      return;
    }
    paramWebViewCallback.error(StorageError.COULDNT_GET_STORAGE, new Object[] { paramString1 });
  }

  @WebViewExposed
  public static void get(String paramString1, String paramString2, WebViewCallback paramWebViewCallback)
  {
    com.unity3d.splash.services.core.device.Storage localStorage = getStorage(paramString1);
    if (localStorage != null)
    {
      paramString1 = localStorage.get(paramString2);
      if (paramString1 == null)
      {
        paramWebViewCallback.error(StorageError.COULDNT_GET_VALUE, new Object[] { paramString2 });
        return;
      }
      paramWebViewCallback.invoke(new Object[] { paramString1 });
      return;
    }
    paramWebViewCallback.error(StorageError.COULDNT_GET_STORAGE, new Object[] { paramString1, paramString2 });
  }

  @WebViewExposed
  public static void getKeys(String paramString1, String paramString2, Boolean paramBoolean, WebViewCallback paramWebViewCallback)
  {
    com.unity3d.splash.services.core.device.Storage localStorage = getStorage(paramString1);
    if (localStorage != null)
    {
      paramString1 = localStorage.getKeys(paramString2, paramBoolean.booleanValue());
      if (paramString1 != null)
      {
        paramWebViewCallback.invoke(new Object[] { new JSONArray(paramString1) });
        return;
      }
      paramWebViewCallback.invoke(new Object[] { new JSONArray() });
      return;
    }
    paramWebViewCallback.error(StorageError.COULDNT_GET_STORAGE, new Object[] { paramString1, paramString2 });
  }

  private static com.unity3d.splash.services.core.device.Storage getStorage(String paramString)
  {
    return StorageManager.getStorage(StorageManager.StorageType.valueOf(paramString));
  }

  @WebViewExposed
  public static void read(String paramString, WebViewCallback paramWebViewCallback)
  {
    com.unity3d.splash.services.core.device.Storage localStorage = getStorage(paramString);
    if (localStorage != null)
    {
      localStorage.readStorage();
      paramWebViewCallback.invoke(new Object[] { paramString });
      return;
    }
    paramWebViewCallback.error(StorageError.COULDNT_GET_STORAGE, new Object[] { paramString });
  }

  @WebViewExposed
  public static void set(String paramString1, String paramString2, Boolean paramBoolean, WebViewCallback paramWebViewCallback)
  {
    set(paramString1, paramString2, paramBoolean, paramWebViewCallback);
  }

  @WebViewExposed
  public static void set(String paramString1, String paramString2, Double paramDouble, WebViewCallback paramWebViewCallback)
  {
    set(paramString1, paramString2, paramDouble, paramWebViewCallback);
  }

  @WebViewExposed
  public static void set(String paramString1, String paramString2, Integer paramInteger, WebViewCallback paramWebViewCallback)
  {
    set(paramString1, paramString2, paramInteger, paramWebViewCallback);
  }

  @WebViewExposed
  public static void set(String paramString1, String paramString2, Long paramLong, WebViewCallback paramWebViewCallback)
  {
    set(paramString1, paramString2, paramLong, paramWebViewCallback);
  }

  private static void set(String paramString1, String paramString2, Object paramObject, WebViewCallback paramWebViewCallback)
  {
    com.unity3d.splash.services.core.device.Storage localStorage = getStorage(paramString1);
    if (localStorage != null)
    {
      if (localStorage.set(paramString2, paramObject))
      {
        paramWebViewCallback.invoke(new Object[] { paramString2 });
        return;
      }
      paramWebViewCallback.error(StorageError.COULDNT_SET_VALUE, new Object[] { paramString2 });
      return;
    }
    paramWebViewCallback.error(StorageError.COULDNT_GET_STORAGE, new Object[] { paramString1, paramString2 });
  }

  @WebViewExposed
  public static void set(String paramString1, String paramString2, String paramString3, WebViewCallback paramWebViewCallback)
  {
    set(paramString1, paramString2, paramString3, paramWebViewCallback);
  }

  @WebViewExposed
  public static void set(String paramString1, String paramString2, JSONArray paramJSONArray, WebViewCallback paramWebViewCallback)
  {
    set(paramString1, paramString2, paramJSONArray, paramWebViewCallback);
  }

  @WebViewExposed
  public static void set(String paramString1, String paramString2, JSONObject paramJSONObject, WebViewCallback paramWebViewCallback)
  {
    set(paramString1, paramString2, paramJSONObject, paramWebViewCallback);
  }

  @WebViewExposed
  public static void write(String paramString, WebViewCallback paramWebViewCallback)
  {
    com.unity3d.splash.services.core.device.Storage localStorage = getStorage(paramString);
    if (localStorage != null)
    {
      if (localStorage.writeStorage())
      {
        paramWebViewCallback.invoke(new Object[] { paramString });
        return;
      }
      paramWebViewCallback.error(StorageError.COULDNT_WRITE_STORAGE_TO_CACHE, new Object[] { paramString });
      return;
    }
    paramWebViewCallback.error(StorageError.COULDNT_GET_STORAGE, new Object[] { paramString });
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.api.Storage
 * JD-Core Version:    0.6.0
 */