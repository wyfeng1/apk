package com.unity3d.splash.services.core.webview.bridge;

import android.webkit.JavascriptInterface;
import com.unity3d.splash.services.core.log.DeviceLog;
import org.json.JSONArray;

public class WebViewBridgeInterface
{
  private Object[] getParameters(JSONArray paramJSONArray)
  {
    Object[] arrayOfObject = new Object[paramJSONArray.length()];
    for (int i = 0; i < paramJSONArray.length(); i++)
      arrayOfObject[i] = paramJSONArray.get(i);
    return arrayOfObject;
  }

  @JavascriptInterface
  public void handleCallback(String paramString1, String paramString2, String paramString3)
  {
    Object localObject = new StringBuilder("handleCallback ");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append(" ");
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append(" ");
    ((StringBuilder)localObject).append(paramString3);
    DeviceLog.debug(((StringBuilder)localObject).toString());
    JSONArray localJSONArray = new JSONArray(paramString3);
    if (localJSONArray.length() > 0)
    {
      localObject = new Object[localJSONArray.length()];
      for (int i = 0; ; i++)
      {
        paramString3 = (String)localObject;
        if (i >= localJSONArray.length())
          break;
        localObject[i] = localJSONArray.get(i);
      }
    }
    paramString3 = null;
    WebViewBridge.handleCallback(paramString1, paramString2, paramString3);
  }

  @JavascriptInterface
  public void handleInvocation(String paramString)
  {
    Object localObject1 = new StringBuilder("handleInvocation ");
    ((StringBuilder)localObject1).append(paramString);
    DeviceLog.debug(((StringBuilder)localObject1).toString());
    paramString = new JSONArray(paramString);
    localObject1 = new Invocation();
    int i = 0;
    int k;
    for (int j = 0; ; j++)
    {
      k = i;
      if (j >= paramString.length())
        break;
      Object localObject2 = (JSONArray)paramString.get(j);
      String str1 = (String)((JSONArray)localObject2).get(0);
      String str2 = (String)((JSONArray)localObject2).get(1);
      JSONArray localJSONArray = (JSONArray)((JSONArray)localObject2).get(2);
      localObject2 = (String)((JSONArray)localObject2).get(3);
      ((Invocation)localObject1).addInvocation(str1, str2, getParameters(localJSONArray), new WebViewCallback((String)localObject2, ((Invocation)localObject1).getId()));
    }
    while (k < paramString.length())
    {
      ((Invocation)localObject1).nextInvocation();
      k++;
    }
    ((Invocation)localObject1).sendInvocationCallback();
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.webview.bridge.WebViewBridgeInterface
 * JD-Core Version:    0.6.0
 */