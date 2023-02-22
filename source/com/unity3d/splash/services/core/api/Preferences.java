package com.unity3d.splash.services.core.api;

import com.unity3d.splash.services.core.preferences.AndroidPreferences;
import com.unity3d.splash.services.core.preferences.PreferencesError;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;

public class Preferences
{
  @WebViewExposed
  public static void getBoolean(String paramString1, String paramString2, WebViewCallback paramWebViewCallback)
  {
    Boolean localBoolean = AndroidPreferences.getBoolean(paramString1, paramString2);
    if (localBoolean != null)
    {
      paramWebViewCallback.invoke(new Object[] { localBoolean });
      return;
    }
    paramWebViewCallback.error(PreferencesError.COULDNT_GET_VALUE, new Object[] { paramString1, paramString2 });
  }

  @WebViewExposed
  public static void getFloat(String paramString1, String paramString2, WebViewCallback paramWebViewCallback)
  {
    Float localFloat = AndroidPreferences.getFloat(paramString1, paramString2);
    if (localFloat != null)
    {
      paramWebViewCallback.invoke(new Object[] { localFloat });
      return;
    }
    paramWebViewCallback.error(PreferencesError.COULDNT_GET_VALUE, new Object[] { paramString1, paramString2 });
  }

  @WebViewExposed
  public static void getInt(String paramString1, String paramString2, WebViewCallback paramWebViewCallback)
  {
    Integer localInteger = AndroidPreferences.getInteger(paramString1, paramString2);
    if (localInteger != null)
    {
      paramWebViewCallback.invoke(new Object[] { localInteger });
      return;
    }
    paramWebViewCallback.error(PreferencesError.COULDNT_GET_VALUE, new Object[] { paramString1, paramString2 });
  }

  @WebViewExposed
  public static void getLong(String paramString1, String paramString2, WebViewCallback paramWebViewCallback)
  {
    Long localLong = AndroidPreferences.getLong(paramString1, paramString2);
    if (localLong != null)
    {
      paramWebViewCallback.invoke(new Object[] { localLong });
      return;
    }
    paramWebViewCallback.error(PreferencesError.COULDNT_GET_VALUE, new Object[] { paramString1, paramString2 });
  }

  @WebViewExposed
  public static void getString(String paramString1, String paramString2, WebViewCallback paramWebViewCallback)
  {
    String str = AndroidPreferences.getString(paramString1, paramString2);
    if (str != null)
    {
      paramWebViewCallback.invoke(new Object[] { str });
      return;
    }
    paramWebViewCallback.error(PreferencesError.COULDNT_GET_VALUE, new Object[] { paramString1, paramString2 });
  }

  @WebViewExposed
  public static void hasKey(String paramString1, String paramString2, WebViewCallback paramWebViewCallback)
  {
    paramWebViewCallback.invoke(new Object[] { Boolean.valueOf(AndroidPreferences.hasKey(paramString1, paramString2)) });
  }

  @WebViewExposed
  public static void removeKey(String paramString1, String paramString2, WebViewCallback paramWebViewCallback)
  {
    AndroidPreferences.removeKey(paramString1, paramString2);
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void setBoolean(String paramString1, String paramString2, Boolean paramBoolean, WebViewCallback paramWebViewCallback)
  {
    AndroidPreferences.setBoolean(paramString1, paramString2, paramBoolean);
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void setFloat(String paramString1, String paramString2, Double paramDouble, WebViewCallback paramWebViewCallback)
  {
    AndroidPreferences.setFloat(paramString1, paramString2, paramDouble);
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void setInt(String paramString1, String paramString2, Integer paramInteger, WebViewCallback paramWebViewCallback)
  {
    AndroidPreferences.setInteger(paramString1, paramString2, paramInteger);
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void setLong(String paramString1, String paramString2, Long paramLong, WebViewCallback paramWebViewCallback)
  {
    AndroidPreferences.setLong(paramString1, paramString2, paramLong);
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void setString(String paramString1, String paramString2, String paramString3, WebViewCallback paramWebViewCallback)
  {
    AndroidPreferences.setString(paramString1, paramString2, paramString3);
    paramWebViewCallback.invoke(new Object[0]);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.api.Preferences
 * JD-Core Version:    0.6.0
 */