package com.unity3d.splash.services.core.configuration;

import android.content.Context;
import android.content.SharedPreferences;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.properties.SdkProperties;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Configuration
{
  private String[] _moduleConfigurationList = { "com.unity3d.splash.services.core.configuration.CoreModuleConfiguration", "com.unity3d.splash.services.ads.configuration.AdsModuleConfiguration" };
  private Map _moduleConfigurations;
  private String _url;
  private Class[] _webAppApiClassList;
  private String _webViewData;
  private String _webViewHash;
  private String _webViewUrl;
  private String _webViewVersion;

  public Configuration()
  {
  }

  public Configuration(String paramString)
  {
    this._url = paramString;
  }

  private void createWebAppApiClassList()
  {
    ArrayList localArrayList = new ArrayList();
    String[] arrayOfString = getModuleConfigurationList();
    int i = arrayOfString.length;
    for (int j = 0; j < i; j++)
    {
      IModuleConfiguration localIModuleConfiguration = getModuleConfiguration(arrayOfString[j]);
      if ((localIModuleConfiguration == null) || (localIModuleConfiguration.getWebAppApiClassList() == null))
        continue;
      localArrayList.addAll(Arrays.asList(localIModuleConfiguration.getWebAppApiClassList()));
    }
    this._webAppApiClassList = ((Class[])localArrayList.toArray(new Class[localArrayList.size()]));
  }

  protected String buildQueryString()
  {
    StringBuilder localStringBuilder = new StringBuilder("?ts=");
    localStringBuilder.append(System.currentTimeMillis());
    localStringBuilder.append("&sdkVersion=");
    localStringBuilder.append(SdkProperties.getVersionCode());
    localStringBuilder.append("&sdkVersionName=");
    localStringBuilder.append(SdkProperties.getVersionName());
    return localStringBuilder.toString();
  }

  public String getConfigUrl()
  {
    return this._url;
  }

  public IModuleConfiguration getModuleConfiguration(String paramString)
  {
    Object localObject = this._moduleConfigurations;
    if ((localObject != null) && (((Map)localObject).containsKey(paramString)))
      return (IModuleConfiguration)this._moduleConfigurations.get(paramString);
    try
    {
      IModuleConfiguration localIModuleConfiguration = (IModuleConfiguration)Class.forName(paramString).newInstance();
      if (localIModuleConfiguration != null)
      {
        if (this._moduleConfigurations == null)
        {
          localObject = new java/util/HashMap;
          ((HashMap)localObject).<init>();
          this._moduleConfigurations = ((Map)localObject);
          ((Map)localObject).put(paramString, localIModuleConfiguration);
        }
        return localIModuleConfiguration;
      }
      label79: return null;
    }
    catch (java.lang.Exception paramString)
    {
      break label79;
    }
  }

  public String[] getModuleConfigurationList()
  {
    return this._moduleConfigurationList;
  }

  public Class[] getWebAppApiClassList()
  {
    if (this._webAppApiClassList == null)
      createWebAppApiClassList();
    return this._webAppApiClassList;
  }

  public String getWebViewData()
  {
    return this._webViewData;
  }

  public String getWebViewHash()
  {
    return this._webViewHash;
  }

  public String getWebViewUrl()
  {
    return this._webViewUrl;
  }

  public String getWebViewVersion()
  {
    return this._webViewVersion;
  }

  protected void makeRequest()
  {
    Object localObject = ClientProperties.getApplicationContext().getSharedPreferences("game_detail", 0);
    this._webViewUrl = ((SharedPreferences)localObject).getString("url", null);
    this._webViewHash = ((SharedPreferences)localObject).getString("hash", null);
    this._webViewVersion = ((SharedPreferences)localObject).getString("version", null);
    localObject = this._webViewUrl;
    if ((localObject != null) && (!((String)localObject).isEmpty()))
      return;
    throw new MalformedURLException("Invalid data. Web view URL is null or empty");
  }

  public void setConfigUrl(String paramString)
  {
    this._url = paramString;
  }

  public void setWebViewData(String paramString)
  {
    this._webViewData = paramString;
  }

  public void setWebViewHash(String paramString)
  {
    this._webViewHash = paramString;
  }

  public void setWebViewUrl(String paramString)
  {
    this._webViewUrl = paramString;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.configuration.Configuration
 * JD-Core Version:    0.6.0
 */