package com.unity3d.splash.services.ads.api;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.unity3d.splash.services.ads.adunit.AdUnitActivity;
import com.unity3d.splash.services.ads.adunit.AdUnitError;
import com.unity3d.splash.services.ads.adunit.AdUnitMotionEvent;
import com.unity3d.splash.services.ads.adunit.AdUnitRelativeLayout;
import com.unity3d.splash.services.ads.adunit.AdUnitSoftwareActivity;
import com.unity3d.splash.services.ads.adunit.AdUnitTransparentActivity;
import com.unity3d.splash.services.ads.adunit.AdUnitTransparentSoftwareActivity;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdUnit
{
  private static AdUnitActivity _adUnitActivity;
  private static int _currentActivityId = -1;

  @WebViewExposed
  public static void clearMotionEventCapture(WebViewCallback paramWebViewCallback)
  {
    if (getAdUnitActivity() != null)
    {
      if (getAdUnitActivity().getLayout() != null)
      {
        getAdUnitActivity().getLayout().clearCapture();
        paramWebViewCallback.invoke(new Object[0]);
        return;
      }
      paramWebViewCallback.error(AdUnitError.LAYOUT_NULL, new Object[0]);
      return;
    }
    paramWebViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void close(WebViewCallback paramWebViewCallback)
  {
    if (getAdUnitActivity() != null)
    {
      getAdUnitActivity().finish();
      paramWebViewCallback.invoke(new Object[0]);
      return;
    }
    paramWebViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void endMotionEventCapture(WebViewCallback paramWebViewCallback)
  {
    if (getAdUnitActivity() != null)
    {
      if (getAdUnitActivity().getLayout() != null)
      {
        getAdUnitActivity().getLayout().endCapture();
        paramWebViewCallback.invoke(new Object[0]);
        return;
      }
      paramWebViewCallback.error(AdUnitError.LAYOUT_NULL, new Object[0]);
      return;
    }
    paramWebViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
  }

  public static AdUnitActivity getAdUnitActivity()
  {
    return _adUnitActivity;
  }

  public static int getCurrentAdUnitActivityId()
  {
    return _currentActivityId;
  }

  @WebViewExposed
  public static void getCurrentMotionEventCount(WebViewCallback paramWebViewCallback)
  {
    if (getAdUnitActivity() != null)
    {
      if (getAdUnitActivity().getLayout() != null)
      {
        paramWebViewCallback.invoke(new Object[] { Integer.valueOf(getAdUnitActivity().getLayout().getCurrentEventCount()) });
        return;
      }
      paramWebViewCallback.error(AdUnitError.LAYOUT_NULL, new Object[0]);
      return;
    }
    paramWebViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
  }

  private static ArrayList getKeyEventList(JSONArray paramJSONArray)
  {
    ArrayList localArrayList = new ArrayList();
    Integer localInteger;
    for (int i = 0; ; i = localInteger.intValue() + 1)
    {
      localInteger = Integer.valueOf(i);
      if (localInteger.intValue() >= paramJSONArray.length())
        break;
      localArrayList.add(Integer.valueOf(paramJSONArray.getInt(localInteger.intValue())));
    }
    return localArrayList;
  }

  @WebViewExposed
  public static void getMotionEventCount(JSONArray paramJSONArray, WebViewCallback paramWebViewCallback)
  {
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < paramJSONArray.length(); i++)
      try
      {
        localArrayList.add(Integer.valueOf(paramJSONArray.getInt(i)));
      }
      catch (Exception localException2)
      {
        DeviceLog.exception("Error retrieving int from eventTypes", localException2);
      }
    if (getAdUnitActivity() != null)
    {
      if (getAdUnitActivity().getLayout() != null)
      {
        if (getAdUnitActivity().getLayout().getCurrentEventCount() >= getAdUnitActivity().getLayout().getMaxEventCount())
        {
          paramWebViewCallback.error(AdUnitError.MAX_MOTION_EVENT_COUNT_REACHED, new Object[0]);
          return;
        }
        paramJSONArray = getAdUnitActivity().getLayout().getEventCount(localArrayList);
        JSONObject localJSONObject = new JSONObject();
        for (i = 0; i < paramJSONArray.size(); i++)
        {
          int j = paramJSONArray.keyAt(i);
          int k = paramJSONArray.get(j);
          try
          {
            localJSONObject.put(Integer.toString(j), k);
          }
          catch (Exception localException1)
          {
            DeviceLog.exception("Error building response JSON", localException1);
          }
        }
        paramWebViewCallback.invoke(new Object[] { localJSONObject });
        return;
      }
      paramWebViewCallback.error(AdUnitError.LAYOUT_NULL, new Object[0]);
      return;
    }
    paramWebViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void getMotionEventData(JSONObject paramJSONObject, WebViewCallback paramWebViewCallback)
  {
    Object localObject1 = paramJSONObject.keys();
    Object localObject2 = new SparseArray();
    int i;
    int j;
    Object localObject4;
    while (true)
    {
      boolean bool = ((Iterator)localObject1).hasNext();
      i = 0;
      if (!bool)
        break;
      Object localObject3 = (String)((Iterator)localObject1).next();
      j = Integer.parseInt((String)localObject3);
      if (((SparseArray)localObject2).get(j) == null)
        ((SparseArray)localObject2).put(j, new ArrayList());
      localObject4 = null;
      try
      {
        localObject3 = paramJSONObject.getJSONArray((String)localObject3);
        localObject4 = localObject3;
      }
      catch (Exception localException2)
      {
        DeviceLog.exception("Couldn't fetch keyIndices", localException2);
      }
      if (localObject4 == null)
        continue;
      while (i < ((JSONArray)localObject4).length())
      {
        try
        {
          ((ArrayList)((SparseArray)localObject2).get(j)).add(Integer.valueOf(((JSONArray)localObject4).getInt(i)));
        }
        catch (Exception localException3)
        {
          DeviceLog.exception("Couldn't add value to requested infos", localException3);
        }
        i++;
      }
    }
    if (getAdUnitActivity() != null)
    {
      if (getAdUnitActivity().getLayout() != null)
      {
        if (getAdUnitActivity().getLayout().getCurrentEventCount() >= getAdUnitActivity().getLayout().getMaxEventCount())
        {
          paramWebViewCallback.error(AdUnitError.MAX_MOTION_EVENT_COUNT_REACHED, new Object[0]);
          return;
        }
        paramJSONObject = getAdUnitActivity().getLayout().getEvents((SparseArray)localObject2);
        localObject4 = new JSONObject();
        for (i = 0; i < paramJSONObject.size(); i++)
        {
          int k = paramJSONObject.keyAt(i);
          localObject1 = (SparseArray)paramJSONObject.get(k);
          JSONObject localJSONObject1 = new JSONObject();
          for (j = 0; j < ((SparseArray)localObject1).size(); j++)
          {
            JSONObject localJSONObject2 = new JSONObject();
            int m = ((SparseArray)localObject1).keyAt(j);
            localObject2 = (AdUnitMotionEvent)((SparseArray)localObject1).get(m);
            try
            {
              localJSONObject2.put("action", ((AdUnitMotionEvent)localObject2).getAction());
              localJSONObject2.put("isObscured", ((AdUnitMotionEvent)localObject2).isObscured());
              localJSONObject2.put("toolType", ((AdUnitMotionEvent)localObject2).getToolType());
              localJSONObject2.put("source", ((AdUnitMotionEvent)localObject2).getSource());
              localJSONObject2.put("deviceId", ((AdUnitMotionEvent)localObject2).getDeviceId());
              localJSONObject2.put("x", ((AdUnitMotionEvent)localObject2).getX());
              localJSONObject2.put("y", ((AdUnitMotionEvent)localObject2).getY());
              localJSONObject2.put("eventTime", ((AdUnitMotionEvent)localObject2).getEventTime());
              localJSONObject2.put("pressure", ((AdUnitMotionEvent)localObject2).getPressure());
              localJSONObject2.put("size", ((AdUnitMotionEvent)localObject2).getSize());
              localJSONObject1.put(Integer.toString(m), localJSONObject2);
            }
            catch (Exception localException1)
            {
              DeviceLog.debug("Couldn't construct event info", new Object[] { localException1 });
            }
          }
          try
          {
            ((JSONObject)localObject4).put(Integer.toString(k), localJSONObject1);
          }
          catch (Exception localException4)
          {
            DeviceLog.debug("Couldn't construct info object", new Object[] { localException4 });
          }
        }
        paramWebViewCallback.invoke(new Object[] { localObject4 });
        return;
      }
      paramWebViewCallback.error(AdUnitError.LAYOUT_NULL, new Object[0]);
      return;
    }
    paramWebViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void getOrientation(WebViewCallback paramWebViewCallback)
  {
    if (getAdUnitActivity() != null)
    {
      paramWebViewCallback.invoke(new Object[] { Integer.valueOf(getAdUnitActivity().getRequestedOrientation()) });
      return;
    }
    paramWebViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void getSafeAreaInsets(WebViewCallback paramWebViewCallback)
  {
    if ((getAdUnitActivity() != null) && (getAdUnitActivity().getLayout() != null))
    {
      if (Build.VERSION.SDK_INT >= 28)
      {
        Object localObject1 = getAdUnitActivity().getLayout().getRootWindowInsets();
        if (localObject1 != null)
        {
          JSONObject localJSONObject = new JSONObject();
          try
          {
            try
            {
              Object localObject2 = localObject1.getClass().getMethod("getDisplayCutout", new Class[0]).invoke(localObject1, new Object[0]);
              if (localObject2 != null)
              {
                localObject1 = localObject2.getClass().getMethod("getSafeInsetTop", new Class[0]).invoke(localObject2, new Object[0]);
                Object localObject3 = localObject2.getClass().getMethod("getSafeInsetRight", new Class[0]).invoke(localObject2, new Object[0]);
                Object localObject4 = localObject2.getClass().getMethod("getSafeInsetBottom", new Class[0]).invoke(localObject2, new Object[0]);
                localObject2 = localObject2.getClass().getMethod("getSafeInsetLeft", new Class[0]).invoke(localObject2, new Object[0]);
                localJSONObject.put("top", localObject1);
                localJSONObject.put("right", localObject3);
                localJSONObject.put("bottom", localObject4);
                localJSONObject.put("left", localObject2);
                paramWebViewCallback.invoke(new Object[] { localJSONObject });
                return;
              }
              paramWebViewCallback.error(AdUnitError.NO_DISPLAY_CUTOUT_AVAILABLE, new Object[0]);
              return;
            }
            catch (JSONException localJSONException)
            {
              paramWebViewCallback.error(AdUnitError.DISPLAY_CUTOUT_JSON_ERROR, new Object[0]);
              DeviceLog.debug("JSON error while constructing display cutout object", new Object[] { localJSONException });
              return;
            }
            catch (InvocationTargetException localInvocationTargetException)
            {
            }
            catch (IllegalAccessException localIllegalAccessException)
            {
            }
            paramWebViewCallback.error(AdUnitError.DISPLAY_CUTOUT_INVOKE_FAILED, new Object[0]);
            DeviceLog.debug("Error while calling displayCutout getter", new Object[] { localIllegalAccessException });
            return;
          }
          catch (NoSuchMethodException localNoSuchMethodException)
          {
            paramWebViewCallback.error(AdUnitError.DISPLAY_CUTOUT_METHOD_NOT_AVAILABLE, new Object[0]);
            DeviceLog.debug("Method getDisplayCutout not found", new Object[] { localNoSuchMethodException });
            return;
          }
        }
        else
        {
          paramWebViewCallback.error(AdUnitError.NO_DISPLAY_CUTOUT_AVAILABLE, new Object[0]);
          return;
        }
      }
      else
      {
        paramWebViewCallback.error(AdUnitError.API_LEVEL_ERROR, new Object[0]);
        return;
      }
    }
    else
      paramWebViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void getViewFrame(String paramString, WebViewCallback paramWebViewCallback)
  {
    if (getAdUnitActivity() != null)
    {
      if (getAdUnitActivity().getViewFrame(paramString) != null)
      {
        paramString = getAdUnitActivity().getViewFrame(paramString);
        paramWebViewCallback.invoke(new Object[] { paramString.get("x"), paramString.get("y"), paramString.get("width"), paramString.get("height") });
        return;
      }
      paramWebViewCallback.error(AdUnitError.UNKNOWN_VIEW, new Object[0]);
      return;
    }
    paramWebViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
  }

  private static String[] getViewList(JSONArray paramJSONArray)
  {
    String[] arrayOfString = new String[paramJSONArray.length()];
    for (int i = 0; i < paramJSONArray.length(); i++)
      arrayOfString[i] = paramJSONArray.getString(i);
    return arrayOfString;
  }

  @WebViewExposed
  public static void getViews(WebViewCallback paramWebViewCallback)
  {
    if (getAdUnitActivity() != null)
    {
      paramWebViewCallback.invoke(new Object[] { new JSONArray(Arrays.asList(getAdUnitActivity().getViews())) });
      return;
    }
    paramWebViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void open(Integer paramInteger1, JSONArray paramJSONArray, Integer paramInteger2, WebViewCallback paramWebViewCallback)
  {
    open(paramInteger1, paramJSONArray, paramInteger2, null, paramWebViewCallback);
  }

  @WebViewExposed
  public static void open(Integer paramInteger1, JSONArray paramJSONArray1, Integer paramInteger2, JSONArray paramJSONArray2, WebViewCallback paramWebViewCallback)
  {
    open(paramInteger1, paramJSONArray1, paramInteger2, paramJSONArray2, Integer.valueOf(0), Boolean.valueOf(true), paramWebViewCallback);
  }

  @WebViewExposed
  public static void open(Integer paramInteger1, JSONArray paramJSONArray1, Integer paramInteger2, JSONArray paramJSONArray2, Integer paramInteger3, Boolean paramBoolean, WebViewCallback paramWebViewCallback)
  {
    open(paramInteger1, paramJSONArray1, paramInteger2, paramJSONArray2, paramInteger3, paramBoolean, Boolean.valueOf(false), paramWebViewCallback);
  }

  @WebViewExposed
  public static void open(Integer paramInteger1, JSONArray paramJSONArray1, Integer paramInteger2, JSONArray paramJSONArray2, Integer paramInteger3, Boolean paramBoolean1, Boolean paramBoolean2, WebViewCallback paramWebViewCallback)
  {
    open(paramInteger1, paramJSONArray1, paramInteger2, paramJSONArray2, paramInteger3, paramBoolean1, paramBoolean2, Integer.valueOf(0), paramWebViewCallback);
  }

  @WebViewExposed
  public static void open(Integer paramInteger1, JSONArray paramJSONArray1, Integer paramInteger2, JSONArray paramJSONArray2, Integer paramInteger3, Boolean paramBoolean1, Boolean paramBoolean2, Integer paramInteger4, WebViewCallback paramWebViewCallback)
  {
    if ((!paramBoolean1.booleanValue()) && (paramBoolean2.booleanValue()))
    {
      DeviceLog.debug("Unity Ads opening new transparent ad unit activity, hardware acceleration disabled");
      paramBoolean1 = new Intent(ClientProperties.getActivity(), AdUnitTransparentSoftwareActivity.class);
    }
    else if ((paramBoolean1.booleanValue()) && (!paramBoolean2.booleanValue()))
    {
      DeviceLog.debug("Unity Ads opening new hardware accelerated ad unit activity");
      paramBoolean1 = new Intent(ClientProperties.getActivity(), AdUnitActivity.class);
    }
    else if ((paramBoolean1.booleanValue()) && (paramBoolean2.booleanValue()))
    {
      DeviceLog.debug("Unity Ads opening new hardware accelerated transparent ad unit activity");
      paramBoolean1 = new Intent(ClientProperties.getActivity(), AdUnitTransparentActivity.class);
    }
    else
    {
      DeviceLog.debug("Unity Ads opening new ad unit activity, hardware acceleration disabled");
      paramBoolean1 = new Intent(ClientProperties.getActivity(), AdUnitSoftwareActivity.class);
    }
    paramBoolean1.addFlags(268500992);
    if (paramInteger1 != null)
      try
      {
        paramBoolean1.putExtra("activityId", paramInteger1.intValue());
        setCurrentAdUnitActivityId(paramInteger1.intValue());
        try
        {
          paramBoolean1.putExtra("views", getViewList(paramJSONArray1));
          if (paramJSONArray2 != null)
            try
            {
              paramBoolean1.putExtra("keyEvents", getKeyEventList(paramJSONArray2));
            }
            catch (Exception paramInteger1)
            {
              DeviceLog.exception("Error parsing views from viewList", paramInteger1);
              paramWebViewCallback.error(AdUnitError.CORRUPTED_KEYEVENTLIST, new Object[] { paramJSONArray2, paramInteger1.getMessage() });
              return;
            }
          paramBoolean1.putExtra("systemUiVisibility", paramInteger3);
          paramBoolean1.putExtra("orientation", paramInteger2);
          paramBoolean1.putExtra("displayCutoutMode", paramInteger4);
          ClientProperties.getActivity().startActivity(paramBoolean1);
          paramInteger1 = new StringBuilder("Opened AdUnitActivity with: ");
          paramInteger1.append(paramJSONArray1.toString());
          DeviceLog.debug(paramInteger1.toString());
          paramWebViewCallback.invoke(new Object[0]);
          return;
        }
        catch (Exception paramInteger1)
        {
          DeviceLog.exception("Error parsing views from viewList", paramInteger1);
          paramWebViewCallback.error(AdUnitError.CORRUPTED_VIEWLIST, new Object[] { paramJSONArray1, paramInteger1.getMessage() });
          return;
        }
      }
      catch (Exception paramJSONArray1)
      {
        DeviceLog.exception("Could not set activityId for intent", paramJSONArray1);
        paramWebViewCallback.error(AdUnitError.ACTIVITY_ID, new Object[] { Integer.valueOf(paramInteger1.intValue()), paramJSONArray1.getMessage() });
        return;
      }
    DeviceLog.error("Activity ID is NULL");
    paramWebViewCallback.error(AdUnitError.ACTIVITY_ID, new Object[] { "Activity ID NULL" });
  }

  public static void setAdUnitActivity(AdUnitActivity paramAdUnitActivity)
  {
    _adUnitActivity = paramAdUnitActivity;
  }

  public static void setCurrentAdUnitActivityId(int paramInt)
  {
    _currentActivityId = paramInt;
  }

  @WebViewExposed
  public static void setKeepScreenOn(Boolean paramBoolean, WebViewCallback paramWebViewCallback)
  {
    Utilities.runOnUiThread(new Runnable(paramBoolean)
    {
      public final void run()
      {
        if (AdUnit.getAdUnitActivity() != null)
          AdUnit.getAdUnitActivity().setKeepScreenOn(this.val$screenOn.booleanValue());
      }
    });
    if (getAdUnitActivity() != null)
    {
      paramWebViewCallback.invoke(new Object[0]);
      return;
    }
    paramWebViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void setKeyEventList(JSONArray paramJSONArray, WebViewCallback paramWebViewCallback)
  {
    if (getAdUnitActivity() != null)
      try
      {
        getAdUnitActivity().setKeyEventList(getKeyEventList(paramJSONArray));
        paramWebViewCallback.invoke(new Object[] { paramJSONArray });
        return;
      }
      catch (Exception localException)
      {
        DeviceLog.exception("Error parsing views from viewList", localException);
        paramWebViewCallback.error(AdUnitError.CORRUPTED_KEYEVENTLIST, new Object[] { paramJSONArray, localException.getMessage() });
        return;
      }
    paramWebViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void setLayoutInDisplayCutoutMode(Integer paramInteger, WebViewCallback paramWebViewCallback)
  {
    Utilities.runOnUiThread(new Runnable(paramInteger)
    {
      public final void run()
      {
        if (AdUnit.getAdUnitActivity() != null)
          AdUnit.getAdUnitActivity().setLayoutInDisplayCutoutMode(this.val$displayCutoutMode.intValue());
      }
    });
    if (getAdUnitActivity() != null)
    {
      paramWebViewCallback.invoke(new Object[] { paramInteger });
      return;
    }
    paramWebViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void setOrientation(Integer paramInteger, WebViewCallback paramWebViewCallback)
  {
    Utilities.runOnUiThread(new Runnable(paramInteger)
    {
      public final void run()
      {
        if (AdUnit.getAdUnitActivity() != null)
          AdUnit.getAdUnitActivity().setOrientation(this.val$orientation.intValue());
      }
    });
    if (getAdUnitActivity() != null)
    {
      paramWebViewCallback.invoke(new Object[] { paramInteger });
      return;
    }
    paramWebViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void setSystemUiVisibility(Integer paramInteger, WebViewCallback paramWebViewCallback)
  {
    Utilities.runOnUiThread(new Runnable(paramInteger)
    {
      public final void run()
      {
        if (AdUnit.getAdUnitActivity() != null)
          AdUnit.getAdUnitActivity().setSystemUiVisibility(this.val$systemUiVisibility.intValue());
      }
    });
    if (getAdUnitActivity() != null)
    {
      paramWebViewCallback.invoke(new Object[] { paramInteger });
      return;
    }
    paramWebViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void setViewFrame(String paramString, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Integer paramInteger4, WebViewCallback paramWebViewCallback)
  {
    Utilities.runOnUiThread(new Runnable(paramString, paramInteger1, paramInteger2, paramInteger3, paramInteger4)
    {
      public final void run()
      {
        if (AdUnit.getAdUnitActivity() != null)
          AdUnit.getAdUnitActivity().setViewFrame(this.val$view, this.val$x.intValue(), this.val$y.intValue(), this.val$width.intValue(), this.val$height.intValue());
      }
    });
    if (getAdUnitActivity() != null)
    {
      paramWebViewCallback.invoke(new Object[0]);
      return;
    }
    paramWebViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void setViews(JSONArray paramJSONArray, WebViewCallback paramWebViewCallback)
  {
    int i;
    try
    {
      getViewList(paramJSONArray);
      i = 0;
    }
    catch (JSONException localJSONException)
    {
      paramWebViewCallback.error(AdUnitError.CORRUPTED_VIEWLIST, new Object[] { paramJSONArray });
      i = 1;
    }
    if (i == 0)
      Utilities.runOnUiThread(new Runnable(paramJSONArray)
      {
        public final void run()
        {
          if (AdUnit.getAdUnitActivity() != null)
            try
            {
              AdUnit.getAdUnitActivity().setViews(AdUnit.access$000(this.val$views));
              return;
            }
            catch (Exception localException)
            {
              DeviceLog.exception("Corrupted viewlist", localException);
            }
        }
      });
    if (getAdUnitActivity() != null)
    {
      paramWebViewCallback.invoke(new Object[] { paramJSONArray });
      return;
    }
    paramWebViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void startMotionEventCapture(Integer paramInteger, WebViewCallback paramWebViewCallback)
  {
    if (getAdUnitActivity() != null)
    {
      if (getAdUnitActivity().getLayout() != null)
      {
        getAdUnitActivity().getLayout().startCapture(paramInteger.intValue());
        paramWebViewCallback.invoke(new Object[0]);
        return;
      }
      paramWebViewCallback.error(AdUnitError.LAYOUT_NULL, new Object[0]);
      return;
    }
    paramWebViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.api.AdUnit
 * JD-Core Version:    0.6.0
 */