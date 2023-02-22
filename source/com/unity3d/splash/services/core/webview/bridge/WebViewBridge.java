package com.unity3d.splash.services.core.webview.bridge;

import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.webview.WebViewApp;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import org.json.JSONException;

public class WebViewBridge
{
  private static HashMap _classTable;

  private static Method findMethod(String paramString1, String paramString2, Object[] paramArrayOfObject)
  {
    if (_classTable.containsKey(paramString1))
    {
      paramString1 = (HashMap)_classTable.get(paramString1);
      if (paramString1.containsKey(paramString2))
        return (Method)((HashMap)paramString1.get(paramString2)).get(Integer.valueOf(Arrays.deepHashCode(getTypes(paramArrayOfObject))));
      throw new NoSuchMethodException();
    }
    throw new NoSuchMethodException();
  }

  private static Class[] getTypes(Object[] paramArrayOfObject)
  {
    Class[] arrayOfClass;
    if (paramArrayOfObject == null)
      arrayOfClass = new Class[1];
    else
      arrayOfClass = new Class[paramArrayOfObject.length + 1];
    if (paramArrayOfObject != null)
      for (int i = 0; i < paramArrayOfObject.length; i++)
        arrayOfClass[i] = paramArrayOfObject[i].getClass();
    arrayOfClass[(arrayOfClass.length - 1)] = WebViewCallback.class;
    return arrayOfClass;
  }

  private static Object[] getValues(Object[] paramArrayOfObject, WebViewCallback paramWebViewCallback)
  {
    Object[] arrayOfObject;
    if (paramArrayOfObject == null)
    {
      if (paramWebViewCallback == null)
        return null;
      arrayOfObject = new Object[1];
    }
    else
    {
      int i = paramArrayOfObject.length;
      int j;
      if (paramWebViewCallback != null)
        j = 1;
      else
        j = 0;
      arrayOfObject = new Object[i + j];
    }
    if (paramArrayOfObject != null)
      System.arraycopy(paramArrayOfObject, 0, arrayOfObject, 0, paramArrayOfObject.length);
    if (paramWebViewCallback != null)
      arrayOfObject[(arrayOfObject.length - 1)] = paramWebViewCallback;
    return arrayOfObject;
  }

  public static void handleCallback(String paramString1, String paramString2, Object[] paramArrayOfObject)
  {
    paramString1 = WebViewApp.getCurrentApp().getCallback(paramString1);
    try
    {
      paramString1.invoke(paramString2, getValues(paramArrayOfObject, null));
      return;
    }
    catch (IllegalArgumentException paramString1)
    {
    }
    catch (JSONException paramString1)
    {
    }
    catch (IllegalAccessException paramString1)
    {
    }
    catch (InvocationTargetException paramString1)
    {
    }
    DeviceLog.error("Error while invoking method");
    throw paramString1;
  }

  public static void handleInvocation(String paramString1, String paramString2, Object[] paramArrayOfObject, WebViewCallback paramWebViewCallback)
  {
    try
    {
      Method localMethod = findMethod(paramString1, paramString2, paramArrayOfObject);
      try
      {
        localMethod.invoke(null, getValues(paramArrayOfObject, paramWebViewCallback));
        return;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
      }
      catch (JSONException localJSONException1)
      {
      }
      paramWebViewCallback.error(WebViewBridgeError.INVOCATION_FAILED, new Object[] { paramString1, paramString2, paramArrayOfObject, localJSONException1.getMessage() });
      throw localJSONException1;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
    }
    catch (JSONException localJSONException2)
    {
    }
    paramWebViewCallback.error(WebViewBridgeError.METHOD_NOT_FOUND, new Object[] { paramString1, paramString2, paramArrayOfObject });
    throw localJSONException2;
  }

  public static void setClassTable(Class[] paramArrayOfClass)
  {
    if (paramArrayOfClass == null)
      return;
    _classTable = new HashMap();
    int i = paramArrayOfClass.length;
    for (int j = 0; j < i; j++)
    {
      Class localClass = paramArrayOfClass[j];
      if ((localClass == null) || ((!localClass.getPackage().getName().startsWith("com.unity3d.splash.services")) && (!localClass.getPackage().getName().startsWith("com.unity3d.splash.test"))))
        continue;
      HashMap localHashMap1 = new HashMap();
      for (Method localMethod : localClass.getMethods())
      {
        if (localMethod.getAnnotation(WebViewExposed.class) == null)
          continue;
        String str = localMethod.getName();
        HashMap localHashMap2;
        if (localHashMap1.containsKey(str))
          localHashMap2 = (HashMap)localHashMap1.get(str);
        else
          localHashMap2 = new HashMap();
        localHashMap2.put(Integer.valueOf(Arrays.deepHashCode(localMethod.getParameterTypes())), localMethod);
        localHashMap1.put(str, localHashMap2);
      }
      _classTable.put(localClass.getName(), localHashMap1);
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.webview.bridge.WebViewBridge
 * JD-Core Version:    0.6.0
 */