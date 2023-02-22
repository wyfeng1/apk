package com.unity3d.splash.services.core.api;

import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.request.IWebRequestListener;
import com.unity3d.splash.services.core.request.WebRequest.RequestType;
import com.unity3d.splash.services.core.request.WebRequestError;
import com.unity3d.splash.services.core.request.WebRequestEvent;
import com.unity3d.splash.services.core.request.WebRequestThread;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;

public class Request
{
  @WebViewExposed
  public static void get(String paramString1, String paramString2, JSONArray paramJSONArray, Integer paramInteger1, Integer paramInteger2, WebViewCallback paramWebViewCallback)
  {
    JSONArray localJSONArray = paramJSONArray;
    if (paramJSONArray != null)
    {
      localJSONArray = paramJSONArray;
      if (paramJSONArray.length() == 0)
        localJSONArray = null;
    }
    try
    {
      paramJSONArray = getHeadersMap(localJSONArray);
      WebRequestThread.request(paramString2, WebRequest.RequestType.GET, paramJSONArray, null, paramInteger1, paramInteger2, new IWebRequestListener(paramString1)
      {
        public final void onComplete(String paramString1, String paramString2, int paramInt, Map paramMap)
        {
          try
          {
            paramMap = Request.getResponseHeadersMap(paramMap);
            WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.COMPLETE, new Object[] { this.val$id, paramString1, paramString2, Integer.valueOf(paramInt), paramMap });
            return;
          }
          catch (java.lang.Exception paramString2)
          {
            DeviceLog.exception("Error parsing response headers", paramString2);
            WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.FAILED, new Object[] { this.val$id, paramString1, "Error parsing response headers" });
          }
        }

        public final void onFailed(String paramString1, String paramString2)
        {
          WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.FAILED, new Object[] { this.val$id, paramString1, paramString2 });
        }
      });
      paramWebViewCallback.invoke(new Object[] { paramString1 });
      return;
    }
    catch (java.lang.Exception paramString2)
    {
      DeviceLog.exception("Error mapping headers for the request", paramString2);
      paramWebViewCallback.error(WebRequestError.MAPPING_HEADERS_FAILED, new Object[] { paramString1 });
    }
  }

  public static HashMap getHeadersMap(JSONArray paramJSONArray)
  {
    if (paramJSONArray != null)
    {
      HashMap localHashMap = new HashMap();
      for (int i = 0; ; i++)
      {
        localObject = localHashMap;
        if (i >= paramJSONArray.length())
          break;
        JSONArray localJSONArray = (JSONArray)paramJSONArray.get(i);
        List localList = (List)localHashMap.get(localJSONArray.getString(0));
        localObject = localList;
        if (localList == null)
          localObject = new ArrayList();
        ((List)localObject).add(localJSONArray.getString(1));
        localHashMap.put(localJSONArray.getString(0), localObject);
      }
    }
    Object localObject = null;
    return (HashMap)localObject;
  }

  public static JSONArray getResponseHeadersMap(Map paramMap)
  {
    JSONArray localJSONArray1 = new JSONArray();
    if ((paramMap != null) && (paramMap.size() > 0))
    {
      Iterator localIterator1 = paramMap.keySet().iterator();
      while (localIterator1.hasNext())
      {
        String str1 = (String)localIterator1.next();
        JSONArray localJSONArray2 = null;
        Iterator localIterator2 = ((List)paramMap.get(str1)).iterator();
        while (localIterator2.hasNext())
        {
          String str2 = (String)localIterator2.next();
          localJSONArray2 = new JSONArray();
          localJSONArray2.put(str1);
          localJSONArray2.put(str2);
        }
        localJSONArray1.put(localJSONArray2);
      }
    }
    return localJSONArray1;
  }

  @WebViewExposed
  public static void head(String paramString1, String paramString2, JSONArray paramJSONArray, Integer paramInteger1, Integer paramInteger2, WebViewCallback paramWebViewCallback)
  {
    JSONArray localJSONArray = paramJSONArray;
    if (paramJSONArray != null)
    {
      localJSONArray = paramJSONArray;
      if (paramJSONArray.length() == 0)
        localJSONArray = null;
    }
    try
    {
      paramJSONArray = getHeadersMap(localJSONArray);
      WebRequestThread.request(paramString2, WebRequest.RequestType.HEAD, paramJSONArray, paramInteger1, paramInteger2, new IWebRequestListener(paramString1)
      {
        public final void onComplete(String paramString1, String paramString2, int paramInt, Map paramMap)
        {
          try
          {
            paramMap = Request.getResponseHeadersMap(paramMap);
            WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.COMPLETE, new Object[] { this.val$id, paramString1, paramString2, Integer.valueOf(paramInt), paramMap });
            return;
          }
          catch (java.lang.Exception paramString2)
          {
            DeviceLog.exception("Error parsing response headers", paramString2);
            WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.FAILED, new Object[] { this.val$id, paramString1, "Error parsing response headers" });
          }
        }

        public final void onFailed(String paramString1, String paramString2)
        {
          WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.FAILED, new Object[] { this.val$id, paramString1, paramString2 });
        }
      });
      paramWebViewCallback.invoke(new Object[] { paramString1 });
      return;
    }
    catch (java.lang.Exception paramString2)
    {
      DeviceLog.exception("Error mapping headers for the request", paramString2);
      paramWebViewCallback.error(WebRequestError.MAPPING_HEADERS_FAILED, new Object[] { paramString1 });
    }
  }

  @WebViewExposed
  public static void post(String paramString1, String paramString2, String paramString3, JSONArray paramJSONArray, Integer paramInteger1, Integer paramInteger2, WebViewCallback paramWebViewCallback)
  {
    if ((paramString3 != null) && (paramString3.length() == 0))
      paramString3 = null;
    JSONArray localJSONArray = paramJSONArray;
    if (paramJSONArray != null)
    {
      localJSONArray = paramJSONArray;
      if (paramJSONArray.length() == 0)
        localJSONArray = null;
    }
    try
    {
      paramJSONArray = getHeadersMap(localJSONArray);
      WebRequestThread.request(paramString2, WebRequest.RequestType.POST, paramJSONArray, paramString3, paramInteger1, paramInteger2, new IWebRequestListener(paramString1)
      {
        public final void onComplete(String paramString1, String paramString2, int paramInt, Map paramMap)
        {
          try
          {
            paramMap = Request.getResponseHeadersMap(paramMap);
            WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.COMPLETE, new Object[] { this.val$id, paramString1, paramString2, Integer.valueOf(paramInt), paramMap });
            return;
          }
          catch (java.lang.Exception paramString2)
          {
            DeviceLog.exception("Error parsing response headers", paramString2);
            WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.FAILED, new Object[] { this.val$id, paramString1, "Error parsing response headers" });
          }
        }

        public final void onFailed(String paramString1, String paramString2)
        {
          WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.REQUEST, WebRequestEvent.FAILED, new Object[] { this.val$id, paramString1, paramString2 });
        }
      });
      paramWebViewCallback.invoke(new Object[] { paramString1 });
      return;
    }
    catch (java.lang.Exception paramString2)
    {
      DeviceLog.exception("Error mapping headers for the request", paramString2);
      paramWebViewCallback.error(WebRequestError.MAPPING_HEADERS_FAILED, new Object[] { paramString1 });
    }
  }

  @WebViewExposed
  public static void setConcurrentRequestCount(Integer paramInteger, WebViewCallback paramWebViewCallback)
  {
    WebRequestThread.setConcurrentRequestCount(paramInteger.intValue());
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void setKeepAliveTime(Integer paramInteger, WebViewCallback paramWebViewCallback)
  {
    WebRequestThread.setKeepAliveTime(paramInteger.longValue());
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void setMaximumPoolSize(Integer paramInteger, WebViewCallback paramWebViewCallback)
  {
    WebRequestThread.setMaximumPoolSize(paramInteger.intValue());
    paramWebViewCallback.invoke(new Object[0]);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.api.Request
 * JD-Core Version:    0.6.0
 */