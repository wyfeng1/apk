package com.unity3d.splash.services.core.webview.bridge;

import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.webview.WebViewApp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Invocation
{
  private static AtomicInteger _idCount = new AtomicInteger(0);
  private static Map _invocationSets;
  private int _invocationId = _idCount.getAndIncrement();
  private ArrayList _invocations;
  private ArrayList _responses;

  public Invocation()
  {
    if (_invocationSets == null)
      _invocationSets = new HashMap();
    _invocationSets.put(Integer.valueOf(this._invocationId), this);
  }

  public static Invocation getInvocationById(int paramInt)
  {
    monitorenter;
    try
    {
      if ((_invocationSets != null) && (_invocationSets.containsKey(Integer.valueOf(paramInt))))
      {
        Invocation localInvocation = (Invocation)_invocationSets.get(Integer.valueOf(paramInt));
        monitorexit;
        return localInvocation;
      }
      monitorexit;
      return null;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public void addInvocation(String paramString1, String paramString2, Object[] paramArrayOfObject, WebViewCallback paramWebViewCallback)
  {
    if (this._invocations == null)
      this._invocations = new ArrayList();
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramString1);
    localArrayList.add(paramString2);
    localArrayList.add(paramArrayOfObject);
    localArrayList.add(paramWebViewCallback);
    this._invocations.add(localArrayList);
  }

  public int getId()
  {
    return this._invocationId;
  }

  public ArrayList getResponses()
  {
    return this._responses;
  }

  public boolean nextInvocation()
  {
    Object localObject1 = this._invocations;
    if ((localObject1 != null) && (((ArrayList)localObject1).size() > 0))
    {
      Object localObject2 = (ArrayList)this._invocations.remove(0);
      String str1 = (String)((ArrayList)localObject2).get(0);
      String str2 = (String)((ArrayList)localObject2).get(1);
      localObject1 = (Object[])((ArrayList)localObject2).get(2);
      localObject2 = (WebViewCallback)((ArrayList)localObject2).get(3);
      try
      {
        WebViewBridge.handleInvocation(str1, str2, localObject1, (WebViewCallback)localObject2);
      }
      catch (Exception localException)
      {
        DeviceLog.exception(String.format("Error handling invocation %s.%s(%s)", new Object[] { str1, str2, Arrays.toString(localObject1) }), localException);
      }
      return true;
    }
    return false;
  }

  public void sendInvocationCallback()
  {
    _invocationSets.remove(Integer.valueOf(getId()));
    WebViewApp.getCurrentApp().invokeCallback(this);
  }

  public void setInvocationResponse(CallbackStatus paramCallbackStatus, Enum paramEnum, Object[] paramArrayOfObject)
  {
    if (this._responses == null)
      this._responses = new ArrayList();
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramCallbackStatus);
    localArrayList.add(paramEnum);
    localArrayList.add(paramArrayOfObject);
    this._responses.add(localArrayList);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.webview.bridge.Invocation
 * JD-Core Version:    0.6.0
 */