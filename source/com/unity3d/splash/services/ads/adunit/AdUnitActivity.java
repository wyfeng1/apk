package com.unity3d.splash.services.ads.adunit;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout.LayoutParams;
import android.widget.RelativeLayout.LayoutParams;
import com.unity3d.splash.services.ads.api.AdUnit;
import com.unity3d.splash.services.ads.configuration.IAdsModuleConfiguration;
import com.unity3d.splash.services.core.configuration.Configuration;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.ViewUtilities;
import com.unity3d.splash.services.core.webview.WebViewApp;
import com.unity3d.splash.services.core.webview.WebViewEventCategory;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONArray;

public class AdUnitActivity extends Activity
{
  public static final String EXTRA_ACTIVITY_ID = "activityId";
  public static final String EXTRA_DISPLAY_CUTOUT_MODE = "displayCutoutMode";
  public static final String EXTRA_KEEP_SCREEN_ON = "keepScreenOn";
  public static final String EXTRA_KEY_EVENT_LIST = "keyEvents";
  public static final String EXTRA_ORIENTATION = "orientation";
  public static final String EXTRA_SYSTEM_UI_VISIBILITY = "systemUiVisibility";
  public static final String EXTRA_VIEWS = "views";
  private int _activityId;
  private int _displayCutoutMode;
  boolean _keepScreenOn;
  private ArrayList _keyEventList;
  protected AdUnitRelativeLayout _layout;
  private int _orientation = -1;
  private int _systemUiVisibility;
  private Map _viewHandlers;
  private String[] _views;

  private IAdUnitViewHandler createViewHandler(String paramString)
  {
    Object localObject1 = WebViewApp.getCurrentApp();
    Object localObject2 = null;
    if (localObject1 != null)
    {
      Configuration localConfiguration = WebViewApp.getCurrentApp().getConfiguration();
      localObject1 = localConfiguration.getModuleConfigurationList();
      int i = localObject1.length;
      for (int j = 0; j < i; j++)
      {
        Object localObject3 = localConfiguration.getModuleConfiguration(localObject1[j]);
        if (!(localObject3 instanceof IAdsModuleConfiguration))
          continue;
        localObject3 = ((IAdsModuleConfiguration)localObject3).getAdUnitViewHandlers();
        if ((localObject3 == null) || (!((Map)localObject3).containsKey(paramString)))
          continue;
        try
        {
          localObject1 = (IAdUnitViewHandler)((Class)((Map)localObject3).get(paramString)).newInstance();
          paramString = (String)localObject1;
        }
        catch (Exception localStringBuilder)
        {
          StringBuilder localStringBuilder = new StringBuilder("Error creating view: ");
          localStringBuilder.append(paramString);
          DeviceLog.error(localStringBuilder.toString());
          paramString = localObject2;
        }
        return paramString;
      }
    }
    return (IAdUnitViewHandler)(IAdUnitViewHandler)null;
  }

  private boolean handleViewPlacement(View paramView)
  {
    if (paramView == null)
    {
      finish();
      DeviceLog.error("Could not place view because it is null, finishing activity");
      return false;
    }
    if ((paramView.getParent() != null) && (paramView.getParent().equals(this._layout)))
    {
      this._layout.bringChildToFront(paramView);
    }
    else
    {
      ViewUtilities.removeViewFromParent(paramView);
      RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, -1);
      localLayoutParams.addRule(13);
      localLayoutParams.setMargins(0, 0, 0, 0);
      paramView.setPadding(0, 0, 0, 0);
      this._layout.addView(paramView, localLayoutParams);
    }
    return true;
  }

  protected void createLayout()
  {
    if (this._layout != null)
      return;
    AdUnitRelativeLayout localAdUnitRelativeLayout = new AdUnitRelativeLayout(this);
    this._layout = localAdUnitRelativeLayout;
    localAdUnitRelativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
    ViewUtilities.setBackground(this._layout, new ColorDrawable(-16777216));
  }

  public AdUnitRelativeLayout getLayout()
  {
    return this._layout;
  }

  public Map getViewFrame(String paramString)
  {
    Object localObject = getViewHandler(paramString);
    if (paramString.equals("adunit"))
    {
      localObject = (FrameLayout.LayoutParams)this._layout.getLayoutParams();
      paramString = new HashMap();
      paramString.put("x", Integer.valueOf(((FrameLayout.LayoutParams)localObject).leftMargin));
      paramString.put("y", Integer.valueOf(((FrameLayout.LayoutParams)localObject).topMargin));
      paramString.put("width", Integer.valueOf(this._layout.getWidth()));
      paramString.put("height", Integer.valueOf(this._layout.getHeight()));
      return paramString;
    }
    if (localObject != null)
      paramString = ((IAdUnitViewHandler)localObject).getView();
    else
      paramString = null;
    if (paramString != null)
    {
      RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)paramString.getLayoutParams();
      localObject = new HashMap();
      ((HashMap)localObject).put("x", Integer.valueOf(localLayoutParams.leftMargin));
      ((HashMap)localObject).put("y", Integer.valueOf(localLayoutParams.topMargin));
      ((HashMap)localObject).put("width", Integer.valueOf(paramString.getWidth()));
      ((HashMap)localObject).put("height", Integer.valueOf(paramString.getHeight()));
      return localObject;
    }
    return (Map)null;
  }

  public IAdUnitViewHandler getViewHandler(String paramString)
  {
    Object localObject = this._viewHandlers;
    if ((localObject != null) && (((Map)localObject).containsKey(paramString)))
    {
      paramString = (IAdUnitViewHandler)this._viewHandlers.get(paramString);
    }
    else
    {
      localObject = createViewHandler(paramString);
      if (localObject != null)
      {
        if (this._viewHandlers == null)
          this._viewHandlers = new HashMap();
        this._viewHandlers.put(paramString, localObject);
      }
      paramString = (String)localObject;
    }
    return (IAdUnitViewHandler)paramString;
  }

  public String[] getViews()
  {
    return this._views;
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (WebViewApp.getCurrentApp() == null)
    {
      DeviceLog.error("Unity Ads web app is null, closing Unity Ads activity from onCreate");
      finish();
      return;
    }
    AdUnit.setAdUnitActivity(this);
    com.unity3d.splash.services.core.api.Intent.setActiveActivity(this);
    createLayout();
    ViewUtilities.removeViewFromParent(this._layout);
    Object localObject = this._layout;
    addContentView((View)localObject, ((AdUnitRelativeLayout)localObject).getLayoutParams());
    if (paramBundle == null)
    {
      this._views = getIntent().getStringArrayExtra("views");
      this._keyEventList = getIntent().getIntegerArrayListExtra("keyEvents");
      if (getIntent().hasExtra("orientation"))
        this._orientation = getIntent().getIntExtra("orientation", -1);
      if (getIntent().hasExtra("systemUiVisibility"))
        this._systemUiVisibility = getIntent().getIntExtra("systemUiVisibility", 0);
      if (getIntent().hasExtra("activityId"))
        this._activityId = getIntent().getIntExtra("activityId", -1);
      if (getIntent().hasExtra("displayCutoutMode"))
        this._displayCutoutMode = getIntent().getIntExtra("displayCutoutMode", 0);
      localObject = AdUnitEvent.ON_CREATE;
    }
    else
    {
      this._views = paramBundle.getStringArray("views");
      this._orientation = paramBundle.getInt("orientation", -1);
      this._systemUiVisibility = paramBundle.getInt("systemUiVisibility", 0);
      this._keyEventList = paramBundle.getIntegerArrayList("keyEvents");
      this._keepScreenOn = paramBundle.getBoolean("keepScreenOn");
      this._activityId = paramBundle.getInt("activityId", -1);
      this._displayCutoutMode = paramBundle.getInt("displayCutoutMode", 0);
      setKeepScreenOn(this._keepScreenOn);
      localObject = AdUnitEvent.ON_RESTORE;
    }
    setOrientation(this._orientation);
    setSystemUiVisibility(this._systemUiVisibility);
    setLayoutInDisplayCutoutMode(this._displayCutoutMode);
    String[] arrayOfString = this._views;
    if (arrayOfString != null)
    {
      int i = arrayOfString.length;
      for (int j = 0; j < i; j++)
      {
        IAdUnitViewHandler localIAdUnitViewHandler = getViewHandler(arrayOfString[j]);
        if (localIAdUnitViewHandler == null)
          continue;
        localIAdUnitViewHandler.onCreate(this, paramBundle);
      }
    }
    WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.ADUNIT, (Enum)localObject, new Object[] { Integer.valueOf(this._activityId) });
  }

  protected void onDestroy()
  {
    super.onDestroy();
    if (WebViewApp.getCurrentApp() == null)
    {
      if (!isFinishing())
      {
        DeviceLog.error("Unity Ads web app is null, closing Unity Ads activity from onDestroy");
        finish();
      }
      return;
    }
    WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.ADUNIT, AdUnitEvent.ON_DESTROY, new Object[] { Boolean.valueOf(isFinishing()), Integer.valueOf(this._activityId) });
    Object localObject = this._viewHandlers;
    if (localObject != null)
    {
      localObject = ((Map)localObject).entrySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
        if (localEntry.getValue() == null)
          continue;
        ((IAdUnitViewHandler)localEntry.getValue()).onDestroy(this);
      }
    }
    if (AdUnit.getCurrentAdUnitActivityId() == this._activityId)
      AdUnit.setAdUnitActivity(null);
    com.unity3d.splash.services.core.api.Intent.removeActiveActivity(this);
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    Object localObject = this._keyEventList;
    int i = 0;
    int j = i;
    if (localObject != null)
    {
      j = i;
      if (((ArrayList)localObject).contains(Integer.valueOf(paramInt)))
      {
        WebViewApp localWebViewApp = WebViewApp.getCurrentApp();
        localObject = WebViewEventCategory.ADUNIT;
        AdUnitEvent localAdUnitEvent = AdUnitEvent.KEY_DOWN;
        long l = paramKeyEvent.getEventTime();
        j = 1;
        localWebViewApp.sendEvent((Enum)localObject, localAdUnitEvent, new Object[] { Integer.valueOf(paramInt), Long.valueOf(l), Long.valueOf(paramKeyEvent.getDownTime()), Integer.valueOf(paramKeyEvent.getRepeatCount()), Integer.valueOf(this._activityId) });
      }
    }
    return j;
  }

  protected void onPause()
  {
    super.onPause();
    if (WebViewApp.getCurrentApp() == null)
    {
      if (!isFinishing())
      {
        DeviceLog.error("Unity Ads web app is null, closing Unity Ads activity from onPause");
        finish();
      }
      return;
    }
    if (WebViewApp.getCurrentApp().getWebView() == null)
      DeviceLog.warning("Unity Ads web view is null, from onPause");
    else if (isFinishing())
      ViewUtilities.removeViewFromParent(WebViewApp.getCurrentApp().getWebView());
    Object localObject = this._viewHandlers;
    if (localObject != null)
    {
      Iterator localIterator = ((Map)localObject).entrySet().iterator();
      while (localIterator.hasNext())
      {
        localObject = (Map.Entry)localIterator.next();
        if (((Map.Entry)localObject).getValue() == null)
          continue;
        ((IAdUnitViewHandler)((Map.Entry)localObject).getValue()).onPause(this);
      }
    }
    WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.ADUNIT, AdUnitEvent.ON_PAUSE, new Object[] { Boolean.valueOf(isFinishing()), Integer.valueOf(this._activityId) });
  }

  public void onRequestPermissionsResult(int paramInt, String[] paramArrayOfString, int[] paramArrayOfInt)
  {
    try
    {
      JSONArray localJSONArray1 = new org/json/JSONArray;
      localJSONArray1.<init>();
      JSONArray localJSONArray2 = new org/json/JSONArray;
      localJSONArray2.<init>();
      int i = paramArrayOfString.length;
      for (int j = 0; j < i; j++)
        localJSONArray1.put(paramArrayOfString[j]);
      i = paramArrayOfInt.length;
      for (j = 0; j < i; j++)
        localJSONArray2.put(paramArrayOfInt[j]);
      WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.PERMISSIONS, PermissionsEvent.PERMISSIONS_RESULT, new Object[] { Integer.valueOf(paramInt), localJSONArray1, localJSONArray2 });
      return;
    }
    catch (Exception paramArrayOfString)
    {
      WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.PERMISSIONS, PermissionsEvent.PERMISSIONS_ERROR, new Object[] { paramArrayOfString.getMessage() });
    }
  }

  protected void onResume()
  {
    super.onResume();
    if (WebViewApp.getCurrentApp() == null)
    {
      if (!isFinishing())
      {
        DeviceLog.error("Unity Ads web app is null, closing Unity Ads activity from onResume");
        finish();
      }
      return;
    }
    setViews(this._views);
    Object localObject = this._viewHandlers;
    if (localObject != null)
    {
      localObject = ((Map)localObject).entrySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
        if (localEntry.getValue() == null)
          continue;
        ((IAdUnitViewHandler)localEntry.getValue()).onResume(this);
      }
    }
    WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.ADUNIT, AdUnitEvent.ON_RESUME, new Object[] { Integer.valueOf(this._activityId) });
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putInt("orientation", this._orientation);
    paramBundle.putInt("systemUiVisibility", this._systemUiVisibility);
    paramBundle.putIntegerArrayList("keyEvents", this._keyEventList);
    paramBundle.putBoolean("keepScreenOn", this._keepScreenOn);
    paramBundle.putStringArray("views", this._views);
    paramBundle.putInt("activityId", this._activityId);
  }

  protected void onStart()
  {
    super.onStart();
    if (WebViewApp.getCurrentApp() == null)
    {
      if (!isFinishing())
      {
        DeviceLog.error("Unity Ads web app is null, closing Unity Ads activity from onStart");
        finish();
      }
      return;
    }
    Object localObject = this._viewHandlers;
    if (localObject != null)
    {
      localObject = ((Map)localObject).entrySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
        if (localEntry.getValue() == null)
          continue;
        ((IAdUnitViewHandler)localEntry.getValue()).onStart(this);
      }
    }
    WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.ADUNIT, AdUnitEvent.ON_START, new Object[] { Integer.valueOf(this._activityId) });
  }

  protected void onStop()
  {
    super.onStop();
    if (WebViewApp.getCurrentApp() == null)
    {
      if (!isFinishing())
      {
        DeviceLog.error("Unity Ads web app is null, closing Unity Ads activity from onStop");
        finish();
      }
      return;
    }
    Object localObject = this._viewHandlers;
    if (localObject != null)
    {
      localObject = ((Map)localObject).entrySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
        if (localEntry.getValue() == null)
          continue;
        ((IAdUnitViewHandler)localEntry.getValue()).onStop(this);
      }
    }
    WebViewApp.getCurrentApp().sendEvent(WebViewEventCategory.ADUNIT, AdUnitEvent.ON_STOP, new Object[] { Integer.valueOf(this._activityId) });
  }

  public void onWindowFocusChanged(boolean paramBoolean)
  {
    WebViewApp localWebViewApp = WebViewApp.getCurrentApp();
    WebViewEventCategory localWebViewEventCategory = WebViewEventCategory.ADUNIT;
    if (paramBoolean)
      localWebViewApp.sendEvent(localWebViewEventCategory, AdUnitEvent.ON_FOCUS_GAINED, new Object[] { Integer.valueOf(this._activityId) });
    else
      localWebViewApp.sendEvent(localWebViewEventCategory, AdUnitEvent.ON_FOCUS_LOST, new Object[] { Integer.valueOf(this._activityId) });
    super.onWindowFocusChanged(paramBoolean);
  }

  public boolean setKeepScreenOn(boolean paramBoolean)
  {
    this._keepScreenOn = paramBoolean;
    if (getWindow() == null)
      return false;
    if (paramBoolean)
      getWindow().addFlags(128);
    else
      getWindow().clearFlags(128);
    return true;
  }

  public void setKeyEventList(ArrayList paramArrayList)
  {
    this._keyEventList = paramArrayList;
  }

  public void setLayoutInDisplayCutoutMode(int paramInt)
  {
    this._displayCutoutMode = paramInt;
    if ((Build.VERSION.SDK_INT >= 28) && (getWindow() != null))
    {
      WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
      try
      {
        localLayoutParams.getClass().getField("layoutInDisplayCutoutMode").setInt(localLayoutParams, paramInt);
        return;
      }
      catch (NoSuchFieldException localNoSuchFieldException)
      {
        DeviceLog.debug("Error getting layoutInDisplayCutoutMode", new Object[] { localNoSuchFieldException });
      }
      catch (IllegalAccessException localIllegalAccessException)
      {
        DeviceLog.debug("Error setting layoutInDisplayCutoutMode", new Object[] { localIllegalAccessException });
      }
    }
  }

  public void setOrientation(int paramInt)
  {
    this._orientation = paramInt;
    setRequestedOrientation(paramInt);
  }

  public boolean setSystemUiVisibility(int paramInt)
  {
    this._systemUiVisibility = paramInt;
    if (Build.VERSION.SDK_INT >= 11)
      try
      {
        getWindow().getDecorView().setSystemUiVisibility(paramInt);
        return true;
      }
      catch (Exception localException)
      {
        DeviceLog.exception("Error while setting SystemUIVisibility", localException);
      }
    return false;
  }

  public void setViewFrame(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Object localObject = getViewHandler(paramString);
    if (paramString.equals("adunit"))
    {
      paramString = new FrameLayout.LayoutParams(paramInt3, paramInt4);
      paramString.setMargins(paramInt1, paramInt2, 0, 0);
      this._layout.setLayoutParams(paramString);
    }
    else if (localObject != null)
    {
      paramString = ((IAdUnitViewHandler)localObject).getView();
      break label65;
    }
    paramString = null;
    label65: if (paramString != null)
    {
      localObject = new RelativeLayout.LayoutParams(paramInt3, paramInt4);
      ((RelativeLayout.LayoutParams)localObject).setMargins(paramInt1, paramInt2, 0, 0);
      paramString.setLayoutParams((ViewGroup.LayoutParams)localObject);
    }
  }

  public void setViews(String[] paramArrayOfString)
  {
    int i = 0;
    String[] arrayOfString = paramArrayOfString;
    if (paramArrayOfString == null)
      arrayOfString = new String[0];
    paramArrayOfString = new ArrayList(Arrays.asList(arrayOfString));
    if (this._views == null)
      this._views = new String[0];
    ArrayList localArrayList = new ArrayList(Arrays.asList(this._views));
    localArrayList.removeAll(paramArrayOfString);
    paramArrayOfString = localArrayList.iterator();
    while (paramArrayOfString.hasNext())
      getViewHandler((String)paramArrayOfString.next()).destroy();
    this._views = arrayOfString;
    int j = arrayOfString.length;
    while (i < j)
    {
      paramArrayOfString = arrayOfString[i];
      if (paramArrayOfString != null)
      {
        paramArrayOfString = getViewHandler(paramArrayOfString);
        paramArrayOfString.create(this);
        if (!handleViewPlacement(paramArrayOfString.getView()))
          return;
      }
      i++;
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.ads.adunit.AdUnitActivity
 * JD-Core Version:    0.6.0
 */