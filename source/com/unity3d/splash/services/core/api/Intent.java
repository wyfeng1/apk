package com.unity3d.splash.services.core.api;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.lang.ref.WeakReference;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Intent
{
  private static WeakReference _activeActivity;

  @WebViewExposed
  public static void canOpenIntent(JSONObject paramJSONObject, WebViewCallback paramWebViewCallback)
  {
    try
    {
      paramWebViewCallback.invoke(new Object[] { Boolean.valueOf(checkIntentResolvable(intentFromMetadata(paramJSONObject))) });
      return;
    }
    catch (IntentException paramJSONObject)
    {
      DeviceLog.exception("Couldn't resolve intent", paramJSONObject);
      paramWebViewCallback.error(paramJSONObject.getError(), new Object[] { paramJSONObject.getField() });
    }
  }

  @WebViewExposed
  public static void canOpenIntents(JSONArray paramJSONArray, WebViewCallback paramWebViewCallback)
  {
    JSONObject localJSONObject1 = new JSONObject();
    int i = paramJSONArray.length();
    int j = 0;
    while (j < i)
    {
      JSONObject localJSONObject2 = paramJSONArray.optJSONObject(j);
      String str = localJSONObject2.optString("id");
      try
      {
        localJSONObject1.put(str, checkIntentResolvable(intentFromMetadata(localJSONObject2)));
        j++;
      }
      catch (JSONException paramJSONArray)
      {
        paramWebViewCallback.error(IntentError.JSON_EXCEPTION, new Object[] { paramJSONArray.getMessage() });
        return;
      }
      catch (IntentException paramJSONArray)
      {
        DeviceLog.exception("Exception parsing intent", paramJSONArray);
        paramWebViewCallback.error(paramJSONArray.getError(), new Object[] { paramJSONArray.getField() });
        return;
      }
    }
    paramWebViewCallback.invoke(new Object[] { localJSONObject1 });
  }

  private static boolean checkIntentResolvable(android.content.Intent paramIntent)
  {
    return ClientProperties.getApplicationContext().getPackageManager().resolveActivity(paramIntent, 0) != null;
  }

  private static Activity getStartingActivity()
  {
    Object localObject = _activeActivity;
    if ((localObject != null) && (((WeakReference)localObject).get() != null))
      localObject = (Activity)_activeActivity.get();
    else if (ClientProperties.getActivity() != null)
      localObject = ClientProperties.getActivity();
    else
      localObject = null;
    return (Activity)localObject;
  }

  private static android.content.Intent intentFromMetadata(JSONObject paramJSONObject)
  {
    String str1 = (String)paramJSONObject.opt("className");
    String str2 = (String)paramJSONObject.opt("packageName");
    Object localObject = (String)paramJSONObject.opt("action");
    String str3 = (String)paramJSONObject.opt("uri");
    String str4 = (String)paramJSONObject.opt("mimeType");
    JSONArray localJSONArray1 = (JSONArray)paramJSONObject.opt("categories");
    Integer localInteger = (Integer)paramJSONObject.opt("flags");
    JSONArray localJSONArray2 = (JSONArray)paramJSONObject.opt("extras");
    if ((str2 != null) && (str1 == null) && (localObject == null) && (str4 == null))
    {
      localObject = ClientProperties.getApplicationContext().getPackageManager().getLaunchIntentForPackage(str2);
      paramJSONObject = (JSONObject)localObject;
      if (localObject != null)
      {
        paramJSONObject = (JSONObject)localObject;
        if (localInteger.intValue() >= 0)
        {
          ((android.content.Intent)localObject).addFlags(localInteger.intValue());
          paramJSONObject = (JSONObject)localObject;
        }
      }
    }
    else
    {
      paramJSONObject = new android.content.Intent();
      if ((str1 != null) && (str2 != null))
        paramJSONObject.setClassName(str2, str1);
      if (localObject != null)
        paramJSONObject.setAction((String)localObject);
      if (str3 != null)
        paramJSONObject.setData(Uri.parse(str3));
      if (str4 != null)
        paramJSONObject.setType(str4);
      if ((localInteger != null) && (localInteger.intValue() >= 0))
        paramJSONObject.setFlags(localInteger.intValue());
      if (!setCategories(paramJSONObject, localJSONArray1))
        break label260;
      if (!setExtras(paramJSONObject, localJSONArray2))
        break label247;
    }
    return paramJSONObject;
    label247: throw new IntentException(IntentError.COULDNT_PARSE_EXTRAS, localJSONArray2);
    label260: throw new IntentException(IntentError.COULDNT_PARSE_CATEGORIES, localJSONArray1);
  }

  @WebViewExposed
  public static void launch(JSONObject paramJSONObject, WebViewCallback paramWebViewCallback)
  {
    String str1 = (String)paramJSONObject.opt("className");
    String str2 = (String)paramJSONObject.opt("packageName");
    String str3 = (String)paramJSONObject.opt("action");
    String str4 = (String)paramJSONObject.opt("uri");
    String str5 = (String)paramJSONObject.opt("mimeType");
    Object localObject = (JSONArray)paramJSONObject.opt("categories");
    Integer localInteger = (Integer)paramJSONObject.opt("flags");
    JSONArray localJSONArray = (JSONArray)paramJSONObject.opt("extras");
    if ((str2 != null) && (str1 == null) && (str3 == null) && (str5 == null))
    {
      localObject = ClientProperties.getApplicationContext().getPackageManager().getLaunchIntentForPackage(str2);
      paramJSONObject = (JSONObject)localObject;
      if (localObject != null)
      {
        paramJSONObject = (JSONObject)localObject;
        if (localInteger.intValue() >= 0)
        {
          ((android.content.Intent)localObject).addFlags(localInteger.intValue());
          paramJSONObject = (JSONObject)localObject;
        }
      }
    }
    else
    {
      paramJSONObject = new android.content.Intent();
      if ((str1 != null) && (str2 != null))
        paramJSONObject.setClassName(str2, str1);
      if (str3 != null)
        paramJSONObject.setAction(str3);
      if ((str4 != null) && (str5 != null))
        paramJSONObject.setDataAndType(Uri.parse(str4), str5);
      else if (str4 != null)
        paramJSONObject.setData(Uri.parse(str4));
      else if (str5 != null)
        paramJSONObject.setType(str5);
      if ((localInteger != null) && (localInteger.intValue() >= 0))
        paramJSONObject.setFlags(localInteger.intValue());
      if (!setCategories(paramJSONObject, (JSONArray)localObject))
        paramWebViewCallback.error(IntentError.COULDNT_PARSE_CATEGORIES, new Object[] { localObject });
      if (!setExtras(paramJSONObject, localJSONArray))
        paramWebViewCallback.error(IntentError.COULDNT_PARSE_EXTRAS, new Object[] { localJSONArray });
    }
    if (paramJSONObject != null)
    {
      if (getStartingActivity() != null)
      {
        getStartingActivity().startActivity(paramJSONObject);
        paramWebViewCallback.invoke(new Object[0]);
        return;
      }
      paramWebViewCallback.error(IntentError.ACTIVITY_WAS_NULL, new Object[0]);
      return;
    }
    paramWebViewCallback.error(IntentError.INTENT_WAS_NULL, new Object[0]);
  }

  public static void removeActiveActivity(Activity paramActivity)
  {
    WeakReference localWeakReference = _activeActivity;
    if ((localWeakReference != null) && (localWeakReference.get() != null) && (paramActivity != null) && (paramActivity.equals(_activeActivity.get())))
      _activeActivity = null;
  }

  public static void setActiveActivity(Activity paramActivity)
  {
    if (paramActivity == null)
    {
      _activeActivity = null;
      return;
    }
    _activeActivity = new WeakReference(paramActivity);
  }

  private static boolean setCategories(android.content.Intent paramIntent, JSONArray paramJSONArray)
  {
    if ((paramJSONArray != null) && (paramJSONArray.length() > 0))
    {
      int i = 0;
      while (i < paramJSONArray.length())
        try
        {
          paramIntent.addCategory(paramJSONArray.getString(i));
          i++;
        }
        catch (Exception paramIntent)
        {
          DeviceLog.exception("Couldn't parse categories for intent", paramIntent);
          return false;
        }
    }
    return true;
  }

  private static boolean setExtra(android.content.Intent paramIntent, String paramString, Object paramObject)
  {
    if ((paramObject instanceof String))
    {
      paramIntent.putExtra(paramString, (String)paramObject);
    }
    else if ((paramObject instanceof Integer))
    {
      paramIntent.putExtra(paramString, ((Integer)paramObject).intValue());
    }
    else if ((paramObject instanceof Double))
    {
      paramIntent.putExtra(paramString, ((Double)paramObject).doubleValue());
    }
    else
    {
      if (!(paramObject instanceof Boolean))
        break label88;
      paramIntent.putExtra(paramString, ((Boolean)paramObject).booleanValue());
    }
    return true;
    label88: paramIntent = new StringBuilder("Unable to parse launch intent extra ");
    paramIntent.append(paramString);
    DeviceLog.error(paramIntent.toString());
    return false;
  }

  private static boolean setExtras(android.content.Intent paramIntent, JSONArray paramJSONArray)
  {
    if (paramJSONArray != null)
    {
      int i = 0;
      while (i < paramJSONArray.length())
        try
        {
          Object localObject = paramJSONArray.getJSONObject(i);
          String str = ((JSONObject)localObject).getString("key");
          localObject = ((JSONObject)localObject).get("value");
          if (!setExtra(paramIntent, str, localObject))
            return false;
          i++;
        }
        catch (Exception paramIntent)
        {
          DeviceLog.exception("Couldn't parse extras", paramIntent);
          return false;
        }
    }
    return true;
  }

  public static enum IntentError
  {
    static
    {
      COULDNT_PARSE_CATEGORIES = new IntentError("COULDNT_PARSE_CATEGORIES", 1);
      INTENT_WAS_NULL = new IntentError("INTENT_WAS_NULL", 2);
      JSON_EXCEPTION = new IntentError("JSON_EXCEPTION", 3);
      IntentError localIntentError = new IntentError("ACTIVITY_WAS_NULL", 4);
      ACTIVITY_WAS_NULL = localIntentError;
      $VALUES = new IntentError[] { COULDNT_PARSE_EXTRAS, COULDNT_PARSE_CATEGORIES, INTENT_WAS_NULL, JSON_EXCEPTION, localIntentError };
    }
  }

  private static class IntentException extends Exception
  {
    private Intent.IntentError error;
    private Object field;

    public IntentException(Intent.IntentError paramIntentError, Object paramObject)
    {
      this.error = paramIntentError;
      this.field = paramObject;
    }

    public Intent.IntentError getError()
    {
      return this.error;
    }

    public Object getField()
    {
      return this.field;
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.api.Intent
 * JD-Core Version:    0.6.0
 */