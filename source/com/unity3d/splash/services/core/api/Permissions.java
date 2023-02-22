package com.unity3d.splash.services.core.api;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import com.unity3d.splash.services.ads.adunit.AdUnitActivity;
import com.unity3d.splash.services.ads.adunit.AdUnitError;
import com.unity3d.splash.services.ads.api.AdUnit;
import com.unity3d.splash.services.core.device.DeviceError;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

public class Permissions
{
  @WebViewExposed
  public static void checkPermission(String paramString, WebViewCallback paramWebViewCallback)
  {
    if (ClientProperties.getApplicationContext() == null)
    {
      paramWebViewCallback.error(DeviceError.APPLICATION_CONTEXT_NULL, new Object[0]);
      return;
    }
    try
    {
      Context localContext = ClientProperties.getApplicationContext();
      paramWebViewCallback.invoke(new Object[] { Integer.valueOf(localContext.getPackageManager().checkPermission(paramString, localContext.getPackageName())) });
      return;
    }
    catch (Exception paramString)
    {
      paramWebViewCallback.error(PermissionsError.ERROR_CHECKING_PERMISSION, new Object[] { paramString.getMessage() });
    }
  }

  @WebViewExposed
  public static void getPermissions(WebViewCallback paramWebViewCallback)
  {
    if (ClientProperties.getApplicationContext() == null)
    {
      paramWebViewCallback.error(DeviceError.APPLICATION_CONTEXT_NULL, new Object[0]);
      return;
    }
    try
    {
      JSONArray localJSONArray = new org/json/JSONArray;
      localJSONArray.<init>();
      Object localObject = ClientProperties.getApplicationContext();
      localObject = ((Context)localObject).getPackageManager().getPackageInfo(((Context)localObject).getPackageName(), 4096);
      if (((PackageInfo)localObject).requestedPermissions != null)
      {
        localObject = ((PackageInfo)localObject).requestedPermissions;
        int i = localObject.length;
        for (int j = 0; j < i; j++)
          localJSONArray.put(localObject[j]);
        paramWebViewCallback.invoke(new Object[] { localJSONArray });
        return;
      }
      paramWebViewCallback.error(PermissionsError.NO_REQUESTED_PERMISSIONS, new Object[0]);
      return;
    }
    catch (Exception localException)
    {
      paramWebViewCallback.error(PermissionsError.COULDNT_GET_PERMISSIONS, new Object[] { localException.getMessage() });
    }
  }

  @WebViewExposed
  public static void requestPermissions(JSONArray paramJSONArray, Integer paramInteger, WebViewCallback paramWebViewCallback)
  {
    if (AdUnit.getAdUnitActivity() == null)
    {
      paramWebViewCallback.error(AdUnitError.ADUNIT_NULL, new Object[0]);
      return;
    }
    if ((paramJSONArray != null) && (paramJSONArray.length() > 0))
      try
      {
        ArrayList localArrayList = new java/util/ArrayList;
        localArrayList.<init>();
        for (int i = 0; i < paramJSONArray.length(); i++)
          localArrayList.add(paramJSONArray.getString(i));
        paramJSONArray = new String[localArrayList.size()];
        AdUnit.getAdUnitActivity().requestPermissions((String[])localArrayList.toArray(paramJSONArray), paramInteger.intValue());
        paramWebViewCallback.invoke(new Object[0]);
        return;
      }
      catch (Exception paramJSONArray)
      {
        paramWebViewCallback.error(PermissionsError.ERROR_REQUESTING_PERMISSIONS, new Object[] { paramJSONArray.getMessage() });
        return;
      }
    paramWebViewCallback.error(PermissionsError.NO_REQUESTED_PERMISSIONS, new Object[0]);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.api.Permissions
 * JD-Core Version:    0.6.0
 */