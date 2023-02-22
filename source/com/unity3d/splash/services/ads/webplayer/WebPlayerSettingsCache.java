package com.unity3d.splash.services.ads.webplayer;

import java.util.HashMap;
import org.json.JSONObject;

public class WebPlayerSettingsCache
{
  private static WebPlayerSettingsCache instance;
  private HashMap _webPlayerEventSettings = new HashMap();
  private HashMap _webPlayerSettings = new HashMap();
  private HashMap _webSettings = new HashMap();

  public static WebPlayerSettingsCache getInstance()
  {
    if (instance == null)
      instance = new WebPlayerSettingsCache();
    return instance;
  }

  public void addWebPlayerEventSettings(String paramString, JSONObject paramJSONObject)
  {
    monitorenter;
    try
    {
      this._webPlayerEventSettings.put(paramString, paramJSONObject);
      monitorexit;
      return;
    }
    finally
    {
      paramString = finally;
      monitorexit;
    }
    throw paramString;
  }

  public void addWebPlayerSettings(String paramString, JSONObject paramJSONObject)
  {
    monitorenter;
    try
    {
      this._webPlayerSettings.put(paramString, paramJSONObject);
      monitorexit;
      return;
    }
    finally
    {
      paramString = finally;
      monitorexit;
    }
    throw paramString;
  }

  public void addWebSettings(String paramString, JSONObject paramJSONObject)
  {
    monitorenter;
    try
    {
      this._webSettings.put(paramString, paramJSONObject);
      monitorexit;
      return;
    }
    finally
    {
      paramString = finally;
      monitorexit;
    }
    throw paramString;
  }

  public JSONObject getWebPlayerEventSettings(String paramString)
  {
    monitorenter;
    try
    {
      if (this._webPlayerEventSettings.containsKey(paramString))
      {
        paramString = (JSONObject)this._webPlayerEventSettings.get(paramString);
        return paramString;
      }
      paramString = new JSONObject();
      return paramString;
    }
    finally
    {
      monitorexit;
    }
    throw paramString;
  }

  public JSONObject getWebPlayerSettings(String paramString)
  {
    monitorenter;
    try
    {
      if (this._webPlayerSettings.containsKey(paramString))
      {
        paramString = (JSONObject)this._webPlayerSettings.get(paramString);
        return paramString;
      }
      paramString = new JSONObject();
      return paramString;
    }
    finally
    {
      monitorexit;
    }
    throw paramString;
  }

  public JSONObject getWebSettings(String paramString)
  {
    monitorenter;
    try
    {
      if (this._webSettings.containsKey(paramString))
      {
        paramString = (JSONObject)this._webSettings.get(paramString);
        return paramString;
      }
      paramString = new JSONObject();
      return paramString;
    }
    finally
    {
      monitorexit;
    }
    throw paramString;
  }

  public void removeWebPlayerEventSettings(String paramString)
  {
    monitorenter;
    try
    {
      if (this._webPlayerEventSettings.containsKey(paramString))
        this._webPlayerEventSettings.remove(paramString);
      monitorexit;
      return;
    }
    finally
    {
      paramString = finally;
      monitorexit;
    }
    throw paramString;
  }

  public void removeWebPlayerSettings(String paramString)
  {
    monitorenter;
    try
    {
      if (this._webPlayerSettings.containsKey(paramString))
        this._webPlayerSettings.remove(paramString);
      monitorexit;
      return;
    }
    finally
    {
      paramString = finally;
      monitorexit;
    }
    throw paramString;
  }

  public void removeWebSettings(String paramString)
  {
    monitorenter;
    try
    {
      if (this._webSettings.containsKey(paramString))
        this._webSettings.remove(paramString);
      monitorexit;
      return;
    }
    finally
    {
      paramString = finally;
      monitorexit;
    }
    throw paramString;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.webplayer.WebPlayerSettingsCache
 * JD-Core Version:    0.6.0
 */