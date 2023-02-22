package com.unity3d.splash.services.core.request;

import android.os.Bundle;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebRequestRunnable
  implements Runnable
{
  private final String _body;
  private boolean _canceled = false;
  private final int _connectTimeout;
  private WebRequest _currentRequest;
  private final Map _headers;
  private final IWebRequestListener _listener;
  private final int _readTimeout;
  private final String _type;
  private final String _url;

  public WebRequestRunnable(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, Map paramMap, IWebRequestListener paramIWebRequestListener)
  {
    this._url = paramString1;
    this._type = paramString2;
    this._body = paramString3;
    this._connectTimeout = paramInt1;
    this._readTimeout = paramInt2;
    this._headers = paramMap;
    this._listener = paramIWebRequestListener;
  }

  private Map getResponseHeaders(Bundle paramBundle)
  {
    if (paramBundle.size() > 0)
    {
      HashMap localHashMap = new HashMap();
      Iterator localIterator = paramBundle.keySet().iterator();
      while (true)
      {
        localObject = localHashMap;
        if (!localIterator.hasNext())
          break;
        String str = (String)localIterator.next();
        localObject = paramBundle.getStringArray(str);
        if (localObject == null)
          continue;
        localHashMap.put(str, new ArrayList(Arrays.asList(localObject)));
      }
    }
    Object localObject = null;
    return (Map)localObject;
  }

  private void makeRequest(String paramString1, String paramString2, Map paramMap, String paramString3, int paramInt1, int paramInt2)
  {
    if (this._canceled)
      return;
    paramString1 = new WebRequest(paramString1, paramString2, paramMap, paramInt1, paramInt2);
    this._currentRequest = paramString1;
    if (paramString3 != null)
      paramString1.setBody(paramString3);
    try
    {
      paramMap = this._currentRequest.makeRequest();
      if (this._currentRequest.isCanceled())
        return;
      paramString1 = new Bundle();
      paramString3 = this._currentRequest.getResponseHeaders().keySet().iterator();
      while (paramString3.hasNext())
      {
        paramString2 = (String)paramString3.next();
        if ((paramString2 == null) || (paramString2.contentEquals("null")))
          continue;
        String[] arrayOfString = new String[((List)this._currentRequest.getResponseHeaders().get(paramString2)).size()];
        for (paramInt1 = 0; paramInt1 < ((List)this._currentRequest.getResponseHeaders().get(paramString2)).size(); paramInt1++)
          arrayOfString[paramInt1] = ((String)((List)this._currentRequest.getResponseHeaders().get(paramString2)).get(paramInt1));
        paramString1.putStringArray(paramString2, arrayOfString);
      }
      if (this._currentRequest.isCanceled())
        return;
      onSucceed(paramMap, this._currentRequest.getResponseCode(), getResponseHeaders(paramString1));
      return;
    }
    catch (java.lang.IllegalArgumentException paramString1)
    {
    }
    catch (java.lang.IllegalStateException paramString1)
    {
    }
    catch (NetworkIOException paramString1)
    {
    }
    catch (java.io.IOException paramString1)
    {
    }
    DeviceLog.exception("Error completing request", paramString1);
    paramString2 = new StringBuilder();
    paramString2.append(paramString1.getClass().getName());
    paramString2.append(": ");
    paramString2.append(paramString1.getMessage());
    onFailed(paramString2.toString());
  }

  private void onFailed(String paramString)
  {
    this._listener.onFailed(this._url, paramString);
  }

  private void onSucceed(String paramString, int paramInt, Map paramMap)
  {
    this._listener.onComplete(this._url, paramString, paramInt, paramMap);
  }

  public void run()
  {
    StringBuilder localStringBuilder = new StringBuilder("Handling request message: ");
    localStringBuilder.append(this._url);
    localStringBuilder.append(" type=");
    localStringBuilder.append(this._type);
    DeviceLog.debug(localStringBuilder.toString());
    try
    {
      makeRequest(this._url, this._type, this._headers, this._body, this._connectTimeout, this._readTimeout);
      return;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      DeviceLog.exception("Malformed URL", localMalformedURLException);
      onFailed("Malformed URL");
    }
  }

  public void setCancelStatus(boolean paramBoolean)
  {
    this._canceled = paramBoolean;
    if (paramBoolean)
    {
      WebRequest localWebRequest = this._currentRequest;
      if (localWebRequest != null)
        localWebRequest.cancel();
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.request.WebRequestRunnable
 * JD-Core Version:    0.6.0
 */