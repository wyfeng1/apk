package com.unity3d.player;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.FrameLayout;
import com.unity3d.splash.UnityAds;
import com.unity3d.splash.services.core.device.Device;
import com.unity3d.splash.services.core.device.Storage;
import com.unity3d.splash.services.core.device.StorageManager;
import com.unity3d.splash.services.core.device.StorageManager.StorageType;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.request.WebRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

public class UnityPlayer extends FrameLayout
  implements IUnityPlayerLifecycleEvents
{
  private static final int ANR_TIMEOUT_SECONDS = 4;
  private static final String ARCORE_ENABLE_METADATA_NAME = "unity.arcore-enable";
  private static final String LAUNCH_FULLSCREEN = "unity.launch-fullscreen";
  private static final int RUN_STATE_CHANGED_MSG_CODE = 2269;
  private static final String SPLASH_ADS_GAME_ID = "unity.splash-ads-game-id";
  private static final String SPLASH_ADS_SLOGAN = "unity.splash-ads-slogan";
  private static final String SPLASH_ADS_SLOGAN_HEIGHT = "unity.splash-ads-slogan-height";
  private static final String SPLASH_CHECK_URL = "https://check.unity.cn/api/check-license";
  private static final String SPLASH_ENABLE_METADATA_NAME = "unity.splash-enable";
  private static final String SPLASH_MODE_METADATA_NAME = "unity.splash-mode";
  private static final String UNITY_BUILDER_ID = "unity.builder";
  public static Activity currentActivity;
  public static String m_AndroidFilesDir;
  private static String m_InstantGameEngine;
  private static String m_InstantGameName;
  AlertDialog ad = null;
  private boolean finishLaunchScreenAds = false;
  private Activity mActivity;
  private Context mContext;
  private SurfaceView mGlView;
  Handler mHandler = new Handler();
  private Handler mHanlder = null;
  private int mInitialScreenOrientation = -1;
  private boolean mIsFullscreen = true;
  private BroadcastReceiver mKillingIsMyBusiness = null;
  private boolean mMainDisplayOverride = false;
  private int mNaturalOrientation;
  private OrientationEventListener mOrientationListener = null;
  private boolean mProcessKillRequested = true;
  private boolean mQuitting;
  i mSoftInputDialog = null;
  private o mState = new o();
  private q mVideoPlayerProxy;
  private GoogleARCoreApi m_ARCoreApi = null;
  private boolean m_AddPhoneCallListener = false;
  private AudioVolumeHandler m_AudioVolumeHandler = null;
  private Camera2Wrapper m_Camera2Wrapper = null;
  private ClipboardManager m_ClipboardManager;
  private final ConcurrentLinkedQueue m_Events = new ConcurrentLinkedQueue();
  private a m_FakeListener = new a();
  private HFPStatus m_HFPStatus = null;
  g m_MainThread = new g(0);
  private NetworkConnectivity m_NetworkConnectivity = null;
  private OrientationLockListener m_OrientationLockListener = null;
  private h m_PersistentUnitySurface;
  private c m_PhoneCallListener = new c(0);
  private l m_SplashScreen;
  private TelephonyManager m_TelephonyManager;
  private IUnityPlayerLifecycleEvents m_UnityPlayerLifecycleEvents = null;
  private Uri m_launchUri = null;
  private k m_splashAdsScreen;
  private boolean shouldShowLaunchScreenAds = false;
  private Timer timer = new Timer();
  private TimerTask timerTask = null;

  static
  {
    new n().a();
  }

  public UnityPlayer(Context paramContext)
  {
    this(paramContext, null);
  }

  public UnityPlayer(Context paramContext, IUnityPlayerLifecycleEvents paramIUnityPlayerLifecycleEvents)
  {
    super(paramContext);
    if (paramIUnityPlayerLifecycleEvents == null)
      paramIUnityPlayerLifecycleEvents = this;
    this.m_UnityPlayerLifecycleEvents = paramIUnityPlayerLifecycleEvents;
    Object localObject1;
    if ((paramContext instanceof Activity))
    {
      paramIUnityPlayerLifecycleEvents = (Activity)paramContext;
      this.mActivity = paramIUnityPlayerLifecycleEvents;
      currentActivity = paramIUnityPlayerLifecycleEvents;
      this.mInitialScreenOrientation = paramIUnityPlayerLifecycleEvents.getRequestedOrientation();
      this.m_launchUri = this.mActivity.getIntent().getData();
      m_InstantGameName = currentActivity.getIntent().getStringExtra("instantGame");
      paramIUnityPlayerLifecycleEvents = currentActivity.getIntent().getStringExtra("engineFolder");
      m_InstantGameEngine = paramIUnityPlayerLifecycleEvents;
      if (paramIUnityPlayerLifecycleEvents == null)
        m_InstantGameEngine = "2019";
      m_AndroidFilesDir = paramContext.getFilesDir().getAbsolutePath();
      if (m_InstantGameName != null)
      {
        localObject1 = currentActivity.getIntent().getStringExtra("unity");
        paramIUnityPlayerLifecycleEvents = (IUnityPlayerLifecycleEvents)localObject1;
        if (localObject1 == null)
          paramIUnityPlayerLifecycleEvents = "";
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append(paramIUnityPlayerLifecycleEvents);
        ((StringBuilder)localObject1).append(" -instantGame ");
        ((StringBuilder)localObject1).append(m_InstantGameName);
        localObject1 = ((StringBuilder)localObject1).toString();
        paramIUnityPlayerLifecycleEvents = new StringBuilder();
        paramIUnityPlayerLifecycleEvents.append((String)localObject1);
        paramIUnityPlayerLifecycleEvents.append(" -instantGameEngine ");
        paramIUnityPlayerLifecycleEvents.append(m_InstantGameEngine);
        paramIUnityPlayerLifecycleEvents = paramIUnityPlayerLifecycleEvents.toString();
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append(m_AndroidFilesDir);
        ((StringBuilder)localObject1).append("/UnityPlayers/");
        ((StringBuilder)localObject1).append(m_InstantGameEngine);
        localObject1 = ((StringBuilder)localObject1).toString();
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append("/Managed");
        localObject2 = ((StringBuilder)localObject2).toString();
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append(paramIUnityPlayerLifecycleEvents);
        ((StringBuilder)localObject1).append(" -overrideMonoSearchPath ");
        ((StringBuilder)localObject1).append((String)localObject2);
        paramIUnityPlayerLifecycleEvents = ((StringBuilder)localObject1).toString();
        currentActivity.getIntent().putExtra("unity", paramIUnityPlayerLifecycleEvents);
      }
    }
    this.mContext = paramContext;
    EarlyEnableFullScreenIfEnabled();
    this.mNaturalOrientation = getNaturalOrientation(getResources().getConfiguration().orientation);
    if ((this.mActivity != null) && (getSplashEnabled()))
    {
      paramIUnityPlayerLifecycleEvents = new l(this.mContext, l.a.a()[getSplashMode()]);
      this.m_SplashScreen = paramIUnityPlayerLifecycleEvents;
      addView(paramIUnityPlayerLifecycleEvents);
    }
    hideStatusBar();
    if (currentActivity != null)
      this.m_PersistentUnitySurface = new h(this.mContext);
    preloadJavaPlugins();
    paramIUnityPlayerLifecycleEvents = loadNative(getUnityNativeLibraryPath(this.mContext));
    if (!o.c())
    {
      f.Log(6, "Your hardware does not support this application.");
      paramContext = new AlertDialog.Builder(this.mContext).setTitle("Failure to initialize!").setPositiveButton("OK", new DialogInterface.OnClickListener()
      {
        public final void onClick(DialogInterface paramDialogInterface, int paramInt)
        {
          UnityPlayer.this.finish();
        }
      });
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Your hardware does not support this application.");
      ((StringBuilder)localObject1).append("\n\n");
      ((StringBuilder)localObject1).append(paramIUnityPlayerLifecycleEvents);
      ((StringBuilder)localObject1).append("\n\n Press OK to quit.");
      paramContext = paramContext.setMessage(((StringBuilder)localObject1).toString()).create();
      paramContext.setCancelable(false);
      paramContext.show();
      return;
    }
    initJni(paramContext);
    this.mState.c(true);
    registerLaunch();
    paramIUnityPlayerLifecycleEvents = CreateGlView();
    this.mGlView = paramIUnityPlayerLifecycleEvents;
    paramIUnityPlayerLifecycleEvents.setContentDescription(GetGlViewContentDescription(paramContext));
    addView(this.mGlView);
    if (this.shouldShowLaunchScreenAds)
    {
      paramContext = generateSplashView(this.mContext);
      this.m_splashAdsScreen = paramContext;
      if (paramContext != null)
        addView(paramContext);
      else
        this.finishLaunchScreenAds = true;
    }
    paramContext = this.m_SplashScreen;
    if (paramContext != null)
      bringChildToFront(paramContext);
    this.mQuitting = false;
    hideStatusBar();
    this.m_TelephonyManager = ((TelephonyManager)this.mContext.getSystemService("phone"));
    this.m_ClipboardManager = ((ClipboardManager)this.mContext.getSystemService("clipboard"));
    this.m_Camera2Wrapper = new Camera2Wrapper(this.mContext);
    this.m_HFPStatus = new HFPStatus(this.mContext);
    this.m_MainThread.start();
  }

  private SurfaceView CreateGlView()
  {
    SurfaceView localSurfaceView = new SurfaceView(this.mContext);
    localSurfaceView.setId(this.mContext.getResources().getIdentifier("unitySurfaceView", "id", this.mContext.getPackageName()));
    if (IsWindowTranslucent())
    {
      localSurfaceView.getHolder().setFormat(-3);
      localSurfaceView.setZOrderOnTop(true);
    }
    else
    {
      localSurfaceView.getHolder().setFormat(-1);
    }
    localSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback()
    {
      public final void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
      {
        UnityPlayer.this.updateGLDisplay(0, paramSurfaceHolder.getSurface());
        UnityPlayer.this.sendSurfaceChangedEvent();
      }

      public final void surfaceCreated(SurfaceHolder paramSurfaceHolder)
      {
        UnityPlayer.this.updateGLDisplay(0, paramSurfaceHolder.getSurface());
        if (UnityPlayer.this.m_PersistentUnitySurface != null)
          UnityPlayer.this.m_PersistentUnitySurface.a(UnityPlayer.this);
      }

      public final void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
      {
        if (UnityPlayer.this.m_PersistentUnitySurface != null)
          UnityPlayer.this.m_PersistentUnitySurface.a(UnityPlayer.this.mGlView);
        UnityPlayer.this.updateGLDisplay(0, null);
      }
    });
    localSurfaceView.setFocusable(true);
    localSurfaceView.setFocusableInTouchMode(true);
    return localSurfaceView;
  }

  private void DisableSplashAdsScreen()
  {
    if (this.m_splashAdsScreen != null)
    {
      AlphaAnimation localAlphaAnimation = new AlphaAnimation(1.0F, 0.0F);
      localAlphaAnimation.setDuration(500L);
      localAlphaAnimation.setFillAfter(true);
      localAlphaAnimation.setAnimationListener(new Animation.AnimationListener()
      {
        public final void onAnimationEnd(Animation paramAnimation)
        {
          UnityPlayer.this.runOnUiThread(new Runnable()
          {
            public final void run()
            {
              UnityPlayer.this.removeView(UnityPlayer.this.m_splashAdsScreen);
            }
          });
        }

        public final void onAnimationRepeat(Animation paramAnimation)
        {
        }

        public final void onAnimationStart(Animation paramAnimation)
        {
        }
      });
      runOnUiThread(new Runnable(localAlphaAnimation)
      {
        public final void run()
        {
          UnityPlayer.this.m_splashAdsScreen.startAnimation(this.a);
        }
      });
    }
  }

  private void DisableStaticSplashScreen()
  {
    runOnUiThread(new Runnable()
    {
      public final void run()
      {
        UnityPlayer localUnityPlayer = UnityPlayer.this;
        localUnityPlayer.removeView(localUnityPlayer.m_SplashScreen);
        UnityPlayer.access$1302(UnityPlayer.this, null);
      }
    });
  }

  private void EarlyEnableFullScreenIfEnabled()
  {
    Object localObject = this.mActivity;
    if ((localObject != null) && (((Activity)localObject).getWindow() != null) && ((getLaunchFullscreen()) || (this.mActivity.getIntent().getBooleanExtra("android.intent.extra.VR_LAUNCH", false))))
    {
      localObject = this.mActivity.getWindow().getDecorView();
      if (localObject != null)
        ((View)localObject).setSystemUiVisibility(7);
    }
  }

  private String GetGlViewContentDescription(Context paramContext)
  {
    return paramContext.getResources().getString(paramContext.getResources().getIdentifier("game_view_content_description", "string", paramContext.getPackageName()));
  }

  private boolean IsWindowTranslucent()
  {
    Object localObject = this.mActivity;
    if (localObject == null)
      return false;
    localObject = ((Activity)localObject).getTheme().obtainStyledAttributes(new int[] { 16842840 });
    boolean bool = ((TypedArray)localObject).getBoolean(0, false);
    ((TypedArray)localObject).recycle();
    return bool;
  }

  private void ShowSplashAdsScreen()
  {
    runOnUiThread(new Runnable()
    {
      public final void run()
      {
        if (UnityPlayer.this.m_splashAdsScreen != null)
        {
          UnityPlayer localUnityPlayer = UnityPlayer.this;
          localUnityPlayer.bringChildToFront(localUnityPlayer.m_splashAdsScreen);
          UnityPlayer.this.m_splashAdsScreen.a();
        }
      }
    });
  }

  public static void UnitySendMessage(String paramString1, String paramString2, String paramString3)
  {
    if (!o.c())
    {
      paramString3 = new StringBuilder("Native libraries not loaded - dropping message for ");
      paramString3.append(paramString1);
      paramString3.append(".");
      paramString3.append(paramString2);
      f.Log(5, paramString3.toString());
      return;
    }
    try
    {
      nativeUnitySendMessage(paramString1, paramString2, paramString3.getBytes("UTF-8"));
      label58: return;
    }
    catch (UnsupportedEncodingException paramString1)
    {
      break label58;
    }
  }

  private static String bin2hex(byte[] paramArrayOfByte)
  {
    StringBuilder localStringBuilder = new StringBuilder(paramArrayOfByte.length * 2);
    int i = paramArrayOfByte.length;
    for (int j = 0; j < i; j++)
      localStringBuilder.append(String.format("%02x", new Object[] { Integer.valueOf(paramArrayOfByte[j] & 0xFF) }));
    return localStringBuilder.toString();
  }

  private void checkResumePlayer()
  {
    Activity localActivity = this.mActivity;
    boolean bool;
    if (localActivity != null)
      bool = MultiWindowSupport.getAllowResizableWindow(localActivity);
    else
      bool = false;
    if (!this.mState.e(bool))
      return;
    this.mState.d(true);
    queueGLThreadEvent(new Runnable()
    {
      public final void run()
      {
        UnityPlayer.this.nativeResume();
        UnityPlayer.this.runOnUiThread(new Runnable()
        {
          public final void run()
          {
            if (UnityPlayer.this.m_PersistentUnitySurface != null)
              UnityPlayer.this.m_PersistentUnitySurface.b(UnityPlayer.this);
          }
        });
      }
    });
    this.m_MainThread.b();
  }

  private void finish()
  {
    Activity localActivity = this.mActivity;
    if ((localActivity != null) && (!localActivity.isFinishing()))
      this.mActivity.finish();
  }

  private k generateSplashView(Context paramContext)
  {
    StorageManager.init(paramContext);
    paramContext = StorageManager.getStorage(StorageManager.StorageType.PRIVATE);
    if (paramContext == null)
      return null;
    Object localObject1 = paramContext.get("splash-show");
    if (localObject1 != null)
      try
      {
        Object localObject2 = new org/json/JSONObject;
        ((JSONObject)localObject2).<init>(localObject1.toString());
        localObject1 = new com/unity3d/player/j;
        ((j)localObject1).<init>((JSONObject)localObject2);
        if (((j)localObject1).a())
        {
          DeviceLog.info("splash show");
          if (((j)localObject1).g() >= System.currentTimeMillis())
          {
            DeviceLog.info("splash show");
            paramContext.delete("splash-show");
            localObject2 = new k(this.mContext, this, (j)localObject1);
            return localObject2;
          }
        }
      }
      catch (JSONException localJSONException)
      {
      }
    Object localObject3 = paramContext.get("splash-show-no-fill");
    if (localObject3 != null);
    try
    {
      DeviceLog.info("splash show no fill");
      paramContext = new org/json/JSONObject;
      paramContext.<init>(localObject3.toString());
      localObject3 = new com/unity3d/player/j;
      ((j)localObject3).<init>(paramContext);
      if (((j)localObject3).a())
      {
        paramContext = new k(this.mContext, this, (j)localObject3);
        return paramContext;
      }
      label168: DeviceLog.info("splash show nothing");
      return null;
    }
    catch (JSONException paramContext)
    {
      break label168;
    }
  }

  private boolean getARCoreEnabled()
  {
    try
    {
      boolean bool = getApplicationInfo().metaData.getBoolean("unity.arcore-enable");
      return bool;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private ApplicationInfo getApplicationInfo()
  {
    return this.mContext.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 128);
  }

  private boolean getLaunchFullscreen()
  {
    try
    {
      boolean bool = getApplicationInfo().metaData.getBoolean("unity.launch-fullscreen");
      return bool;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private int getNaturalOrientation(int paramInt)
  {
    int i = ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay().getRotation();
    if (((i != 0) && (i != 2)) || ((paramInt != 2) && (((i != 1) && (i != 3)) || (paramInt != 1))))
      return 1;
    return 0;
  }

  private String getProcessName()
  {
    int i = Process.myPid();
    Object localObject = ((ActivityManager)this.mContext.getSystemService("activity")).getRunningAppProcesses();
    if (localObject == null)
      return null;
    localObject = ((List)localObject).iterator();
    while (((Iterator)localObject).hasNext())
    {
      ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)((Iterator)localObject).next();
      if (localRunningAppProcessInfo.pid == i)
        return localRunningAppProcessInfo.processName;
    }
    return (String)null;
  }

  private static String getQueryString(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null)
      return "";
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator = paramJSONObject.keys();
    for (Object localObject = "?"; ; localObject = "&")
    {
      localStringBuilder.append((String)localObject);
      String str1;
      do
      {
        if (!localIterator.hasNext())
          break;
        str1 = (String)localIterator.next();
        localObject = paramJSONObject.optString(str1);
      }
      while (localObject == "");
      try
      {
        String str2 = URLEncoder.encode((String)localObject, "utf-8");
        localObject = str2;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        localUnsupportedEncodingException.printStackTrace();
      }
      localStringBuilder.append(str1);
      localStringBuilder.append("=");
      localStringBuilder.append((String)localObject);
    }
    localStringBuilder.deleteCharAt(localStringBuilder.length() - 1);
    return (String)localStringBuilder.toString();
  }

  private boolean getSplashEnabled()
  {
    try
    {
      boolean bool = getApplicationInfo().metaData.getBoolean("unity.splash-enable");
      return bool;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private String getSplashGameId()
  {
    try
    {
      String str = getApplicationInfo().metaData.getString("unity.splash-ads-game-id");
      return str;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  private static String getUnityNativeLibraryPath(Context paramContext)
  {
    return paramContext.getApplicationInfo().nativeLibraryDir;
  }

  private static String hash_sha256(String paramString)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-256");
      localMessageDigest.update(paramString.getBytes());
      paramString = bin2hex(localMessageDigest.digest());
    }
    catch (NoSuchAlgorithmException paramString)
    {
      paramString.printStackTrace();
      paramString = "";
    }
    return paramString;
  }

  private void hideStatusBar()
  {
    Activity localActivity = this.mActivity;
    if (localActivity != null)
      localActivity.getWindow().setFlags(1024, 1024);
  }

  private final native void initJni(Context paramContext);

  private static String loadNative(String paramString)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append("/libmain.so");
    localObject = ((StringBuilder)localObject).toString();
    try
    {
      System.load((String)localObject);
    }
    catch (SecurityException paramString)
    {
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
    }
    try
    {
      System.loadLibrary("main");
      if (m_InstantGameName != null)
      {
        paramString = new StringBuilder();
        paramString.append(m_AndroidFilesDir);
        paramString.append("/UnityPlayers/");
        paramString.append(m_InstantGameEngine);
        paramString = paramString.toString();
      }
      if (NativeLoader.load(paramString))
      {
        o.a();
        return "";
      }
      f.Log(6, "NativeLoader.load failure, Unity libraries were not loaded.");
      return "NativeLoader.load failure, Unity libraries were not loaded.";
      return logLoadLibMainError((String)localObject, paramString.toString());
    }
    catch (UnsatisfiedLinkError paramString)
    {
    }
    return (String)logLoadLibMainError((String)localObject, paramString.toString());
  }

  private static String logLoadLibMainError(String paramString1, String paramString2)
  {
    paramString1 = new StringBuilder("Failed to load 'libmain.so'\n\n");
    paramString1.append(paramString2);
    paramString1 = paramString1.toString();
    f.Log(6, paramString1);
    return paramString1;
  }

  private final native void nativeApplicationUnload();

  private final native boolean nativeDone();

  private final native void nativeFocusChanged(boolean paramBoolean);

  private final native boolean nativeInjectEvent(InputEvent paramInputEvent);

  private final native boolean nativeIsAutorotationOn();

  private final native void nativeLowMemory();

  private final native void nativeMuteMasterAudio(boolean paramBoolean);

  private final native void nativeOrientationChanged(int paramInt1, int paramInt2);

  private final native boolean nativePause();

  private final native void nativeRecreateGfxState(int paramInt, Surface paramSurface);

  private final native boolean nativeRender();

  private final native void nativeReportKeyboardConfigChanged();

  private final native void nativeRestartActivityIndicator();

  private final native void nativeResume();

  private final native void nativeSendSurfaceChangedEvent();

  private final native void nativeSetInputArea(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  private final native void nativeSetInputSelection(int paramInt1, int paramInt2);

  private final native void nativeSetInputString(String paramString);

  private final native void nativeSetKeyboardIsVisible(boolean paramBoolean);

  private final native void nativeSetLaunchURL(String paramString);

  private final native void nativeSoftInputCanceled();

  private final native void nativeSoftInputClosed();

  private final native void nativeSoftInputLostFocus();

  private static native void nativeUnitySendMessage(String paramString1, String paramString2, byte[] paramArrayOfByte);

  private void pauseUnity()
  {
    reportSoftInputStr(null, 1, true);
    if (!this.mState.f())
      return;
    if (o.c())
    {
      Semaphore localSemaphore = new Semaphore(0);
      Object localObject;
      if (isFinishing())
        localObject = new Runnable(localSemaphore)
        {
          public final void run()
          {
            UnityPlayer.this.shutdown();
            this.a.release();
          }
        };
      else
        localObject = new Runnable(localSemaphore)
        {
          public final void run()
          {
            if (UnityPlayer.this.nativePause())
            {
              UnityPlayer.access$2402(UnityPlayer.this, true);
              UnityPlayer.this.shutdown();
              this.a.release(2);
              return;
            }
            this.a.release();
          }
        };
      this.m_MainThread.a((Runnable)localObject);
      try
      {
        if (!localSemaphore.tryAcquire(4L, TimeUnit.SECONDS))
          f.Log(5, "Timeout while trying to pause the Unity Engine.");
      }
      catch (InterruptedException localInterruptedException)
      {
        f.Log(5, "UI thread got interrupted while trying to pause the Unity Engine.");
      }
      if (localSemaphore.drainPermits() > 0)
        destroy();
    }
    this.mState.d(false);
    this.mState.b(true);
    if (this.m_AddPhoneCallListener)
      this.m_TelephonyManager.listen(this.m_PhoneCallListener, 0);
  }

  private static void preloadJavaPlugins()
  {
    try
    {
      Class.forName("com.unity3d.JavaPluginPreloader");
      return;
    }
    catch (LinkageError localLinkageError)
    {
      StringBuilder localStringBuilder = new StringBuilder("Java class preloading failed: ");
      localStringBuilder.append(localLinkageError.getMessage());
      f.Log(6, localStringBuilder.toString());
      return;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      label38: break label38;
    }
  }

  private void queueGLThreadEvent(h paramh)
  {
    if (isFinishing())
      return;
    queueGLThreadEvent(paramh);
  }

  private void queueGLThreadEvent(Runnable paramRunnable)
  {
    if (!o.c())
      return;
    if (Thread.currentThread() == this.m_MainThread)
    {
      paramRunnable.run();
      return;
    }
    this.m_Events.add(paramRunnable);
  }

  private void registerLaunch()
  {
    Object localObject1 = this.mActivity;
    if (localObject1 != null)
    {
      Object localObject2 = ((Activity)localObject1).getSharedPreferences("game_detail", 0);
      localObject1 = ((SharedPreferences)localObject2).getString("game_id", "");
      boolean bool = ((SharedPreferences)localObject2).getBoolean("show_ads", false);
      localObject2 = Boolean.valueOf(((SharedPreferences)localObject2).getBoolean("blocked", false));
      if ((!((Boolean)localObject2).booleanValue()) && (Boolean.valueOf(bool).booleanValue()) && (localObject1 != ""))
      {
        this.shouldShowLaunchScreenAds = true;
        UnityAds.initialize(this.mActivity, (String)localObject1, null);
      }
      else
      {
        this.shouldShowLaunchScreenAds = false;
        if (((Boolean)localObject2).booleanValue())
          showBlockDialog();
      }
    }
    else
    {
      this.shouldShowLaunchScreenAds = false;
    }
    localObject1 = getContext().getPackageName();
    new e().execute(new String[] { localObject1 });
  }

  private void sendSurfaceChangedEvent()
  {
    if ((o.c()) && (this.mState.e()))
    {
      29 local29 = new Runnable()
      {
        public final void run()
        {
          UnityPlayer.this.nativeSendSurfaceChangedEvent();
        }
      };
      this.m_MainThread.d(local29);
    }
  }

  private void showBlockDialog()
  {
    this.ad = new AlertDialog.Builder(this.mContext).setTitle("Sorry").setPositiveButton("OK", new DialogInterface.OnClickListener()
    {
      public final void onClick(DialogInterface paramDialogInterface, int paramInt)
      {
        UnityPlayer.this.mHanlder.sendEmptyMessage(1);
      }
    }).setMessage("The app is using unauthorized engine, please contact the publisher!").setCancelable(false).create();
    this.mHanlder = new Handler()
    {
      public final void handleMessage(Message paramMessage)
      {
        int i = paramMessage.what;
        if (i != 0)
        {
          if ((i == 1) && (UnityPlayer.this.ad != null) && (UnityPlayer.this.ad.isShowing()))
            UnityPlayer.this.ad.hide();
        }
        else if ((UnityPlayer.this.ad != null) && (!UnityPlayer.this.ad.isShowing()))
          UnityPlayer.this.ad.show();
        super.handleMessage(paramMessage);
      }
    };
    23 local23 = new TimerTask()
    {
      public final void run()
      {
        UnityPlayer.this.mHanlder.sendEmptyMessage(0);
      }
    };
    this.timerTask = local23;
    this.timer.schedule(local23, 0L, 600000L);
  }

  private void shutdown()
  {
    this.mProcessKillRequested = nativeDone();
    this.mState.c(false);
  }

  private void swapViews(View paramView1, View paramView2)
  {
    int i;
    if (!this.mState.d())
    {
      pause();
      i = 1;
    }
    else
    {
      i = 0;
    }
    if (paramView1 != null)
    {
      ViewParent localViewParent = paramView1.getParent();
      if ((!(localViewParent instanceof UnityPlayer)) || ((UnityPlayer)localViewParent != this))
      {
        if ((localViewParent instanceof ViewGroup))
          ((ViewGroup)localViewParent).removeView(paramView1);
        addView(paramView1);
        bringChildToFront(paramView1);
        paramView1.setVisibility(0);
      }
    }
    if ((paramView2 != null) && (paramView2.getParent() == this))
    {
      paramView2.setVisibility(8);
      removeView(paramView2);
    }
    if (i != 0)
      resume();
  }

  private static void unloadNative()
  {
    if (!o.c())
      return;
    if (NativeLoader.unload())
    {
      o.b();
      return;
    }
    throw new UnsatisfiedLinkError("Unable to unload libraries from libmain.so");
  }

  private boolean updateDisplayInternal(int paramInt, Surface paramSurface)
  {
    if ((o.c()) && (this.mState.e()))
    {
      Semaphore localSemaphore = new Semaphore(0);
      30 local30 = new Runnable(paramInt, paramSurface, localSemaphore)
      {
        public final void run()
        {
          UnityPlayer.this.nativeRecreateGfxState(this.a, this.b);
          this.c.release();
        }
      };
      if (paramInt == 0)
      {
        g localg = this.m_MainThread;
        if (paramSurface == null)
          localg.b(local30);
        else
          localg.c(local30);
      }
      else
      {
        local30.run();
      }
      if ((paramSurface == null) && (paramInt == 0))
        try
        {
          if (!localSemaphore.tryAcquire(4L, TimeUnit.SECONDS))
            f.Log(5, "Timeout while trying detaching primary window.");
        }
        catch (InterruptedException paramSurface)
        {
          f.Log(5, "UI thread got interrupted while trying to detach the primary window from the Unity Engine.");
        }
      return true;
    }
    return false;
  }

  private void updateGLDisplay(int paramInt, Surface paramSurface)
  {
    if (this.mMainDisplayOverride)
      return;
    updateDisplayInternal(paramInt, paramSurface);
  }

  public void NotifySplashAdsFinished()
  {
    this.finishLaunchScreenAds = true;
    this.m_MainThread.f();
  }

  protected void addPhoneCallListener()
  {
    this.m_AddPhoneCallListener = true;
    this.m_TelephonyManager.listen(this.m_PhoneCallListener, 32);
  }

  public boolean addViewToPlayer(View paramView, boolean paramBoolean)
  {
    SurfaceView localSurfaceView;
    if (paramBoolean)
      localSurfaceView = this.mGlView;
    else
      localSurfaceView = null;
    swapViews(paramView, localSurfaceView);
    paramView = paramView.getParent();
    boolean bool = true;
    int i;
    if (paramView == this)
      i = 1;
    else
      i = 0;
    int j;
    if ((paramBoolean) && (this.mGlView.getParent() == null))
      j = 1;
    else
      j = 0;
    int k;
    if (this.mGlView.getParent() == this)
      k = 1;
    else
      k = 0;
    if (i != 0)
    {
      paramBoolean = bool;
      if (j != 0)
        break label111;
      if (k != 0)
      {
        paramBoolean = bool;
        break label111;
      }
    }
    paramBoolean = false;
    label111: if (!paramBoolean)
    {
      if (i == 0)
        f.Log(6, "addViewToPlayer: Failure adding view to hierarchy");
      if ((j == 0) && (k == 0))
        f.Log(6, "addViewToPlayer: Failure removing old view from hierarchy");
    }
    return paramBoolean;
  }

  public void configurationChanged(Configuration paramConfiguration)
  {
    paramConfiguration = this.mGlView;
    if ((paramConfiguration instanceof SurfaceView))
      paramConfiguration.getHolder().setSizeFromLayout();
    paramConfiguration = this.mVideoPlayerProxy;
    if (paramConfiguration != null)
      paramConfiguration.c();
  }

  public void destroy()
  {
    Object localObject = this.m_PersistentUnitySurface;
    if (localObject != null)
    {
      ((h)localObject).a();
      this.m_PersistentUnitySurface = null;
    }
    localObject = this.m_Camera2Wrapper;
    if (localObject != null)
    {
      ((Camera2Wrapper)localObject).a();
      this.m_Camera2Wrapper = null;
    }
    localObject = this.m_HFPStatus;
    if (localObject != null)
    {
      ((HFPStatus)localObject).a();
      this.m_HFPStatus = null;
    }
    localObject = this.m_NetworkConnectivity;
    if (localObject != null)
    {
      ((NetworkConnectivity)localObject).b();
      this.m_NetworkConnectivity = null;
    }
    this.mQuitting = true;
    if (!this.mState.d())
      pause();
    this.m_MainThread.a();
    try
    {
      this.m_MainThread.join(4000L);
    }
    catch (InterruptedException localInterruptedException)
    {
      this.m_MainThread.interrupt();
    }
    BroadcastReceiver localBroadcastReceiver = this.mKillingIsMyBusiness;
    if (localBroadcastReceiver != null)
      this.mContext.unregisterReceiver(localBroadcastReceiver);
    this.mKillingIsMyBusiness = null;
    if (o.c())
      removeAllViews();
    if (this.mProcessKillRequested)
    {
      this.m_UnityPlayerLifecycleEvents.onUnityPlayerQuitted();
      kill();
    }
    unloadNative();
  }

  protected void disableLogger()
  {
    f.a = true;
  }

  public boolean displayChanged(int paramInt, Surface paramSurface)
  {
    if (paramInt == 0)
    {
      boolean bool;
      if (paramSurface != null)
        bool = true;
      else
        bool = false;
      this.mMainDisplayOverride = bool;
      runOnUiThread(new Runnable()
      {
        public final void run()
        {
          if (UnityPlayer.this.mMainDisplayOverride)
          {
            localUnityPlayer = UnityPlayer.this;
            localUnityPlayer.removeView(localUnityPlayer.mGlView);
            return;
          }
          UnityPlayer localUnityPlayer = UnityPlayer.this;
          localUnityPlayer.addView(localUnityPlayer.mGlView);
        }
      });
    }
    return updateDisplayInternal(paramInt, paramSurface);
  }

  protected void executeGLThreadJobs()
  {
    while (true)
    {
      Runnable localRunnable = (Runnable)this.m_Events.poll();
      if (localRunnable == null)
        break;
      localRunnable.run();
    }
  }

  protected String getBuilderUserId()
  {
    try
    {
      String str = getApplicationInfo().metaData.getString("unity.builder");
      return str;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  protected String getClipboardText()
  {
    Object localObject = this.m_ClipboardManager.getPrimaryClip();
    if (localObject != null)
      localObject = ((ClipData)localObject).getItemAt(0).coerceToText(this.mContext).toString();
    else
      localObject = "";
    return (String)localObject;
  }

  public String getDeviceId()
  {
    Object localObject1 = this.mActivity;
    if (localObject1 == null)
      return "";
    Object localObject2 = ((Activity)localObject1).getSharedPreferences("device_detail", 0);
    localObject1 = ((SharedPreferences)localObject2).getString("device_id", null);
    if (localObject1 != null)
      return localObject1;
    localObject1 = Device.getUniqueEventId();
    localObject2 = ((SharedPreferences)localObject2).edit();
    ((SharedPreferences.Editor)localObject2).putString("device_id", (String)localObject1);
    ((SharedPreferences.Editor)localObject2).commit();
    return (String)(String)localObject1;
  }

  protected String getKeyboardLayout()
  {
    i locali = this.mSoftInputDialog;
    if (locali == null)
      return null;
    return locali.a();
  }

  protected String getLaunchURL()
  {
    Uri localUri = this.m_launchUri;
    if (localUri != null)
      return localUri.toString();
    return null;
  }

  protected int getNetworkConnectivity()
  {
    if (PlatformSupport.NOUGAT_SUPPORT)
    {
      if (this.m_NetworkConnectivity == null)
        this.m_NetworkConnectivity = new NetworkConnectivity(this.mContext);
      return this.m_NetworkConnectivity.a();
    }
    return 0;
  }

  public String getNetworkProxySettings(String paramString)
  {
    if (paramString.startsWith("http:"))
    {
      localObject = "http.proxyHost";
      paramString = "http.proxyPort";
    }
    else
    {
      if (!paramString.startsWith("https:"))
        break label151;
      localObject = "https.proxyHost";
      paramString = "https.proxyPort";
    }
    Object localObject = System.getProperties().getProperty((String)localObject);
    if ((localObject != null) && (!"".equals(localObject)))
    {
      localObject = new StringBuilder((String)localObject);
      paramString = System.getProperties().getProperty(paramString);
      if ((paramString != null) && (!"".equals(paramString)))
      {
        ((StringBuilder)localObject).append(":");
        ((StringBuilder)localObject).append(paramString);
      }
      paramString = System.getProperties().getProperty("http.nonProxyHosts");
      if ((paramString != null) && (!"".equals(paramString)))
      {
        ((StringBuilder)localObject).append('\n');
        ((StringBuilder)localObject).append(paramString);
      }
      return ((StringBuilder)localObject).toString();
    }
    label151: return (String)null;
  }

  public Bundle getSettings()
  {
    return Bundle.EMPTY;
  }

  protected Boolean getShowSplashSlogan()
  {
    try
    {
      boolean bool = getApplicationInfo().metaData.getBoolean("unity.splash-ads-slogan");
      return Boolean.valueOf(bool);
    }
    catch (Exception localException)
    {
    }
    return Boolean.valueOf(false);
  }

  protected int getShowSplashSloganHeight()
  {
    try
    {
      int i = getApplicationInfo().metaData.getInt("unity.splash-ads-slogan-height", 250);
      return i;
    }
    catch (Exception localException)
    {
    }
    return 150;
  }

  protected int getSplashMode()
  {
    try
    {
      int i = getApplicationInfo().metaData.getInt("unity.splash-mode");
      return i;
    }
    catch (Exception localException)
    {
    }
    return 0;
  }

  protected int getUaaLLaunchProcessType()
  {
    String str = getProcessName();
    if ((str != null) && (!str.equals(this.mContext.getPackageName())))
      return 1;
    return 0;
  }

  public View getView()
  {
    return this;
  }

  protected void hideSoftInput()
  {
    postOnUiThread(new Runnable()
    {
      public final void run()
      {
        UnityPlayer.this.reportSoftInputArea(new Rect());
        UnityPlayer.this.reportSoftInputIsVisible(false);
        if (UnityPlayer.this.mSoftInputDialog != null)
        {
          UnityPlayer.this.mSoftInputDialog.dismiss();
          UnityPlayer.this.mSoftInputDialog = null;
          UnityPlayer.this.nativeReportKeyboardConfigChanged();
        }
      }
    });
  }

  public void init(int paramInt, boolean paramBoolean)
  {
  }

  protected boolean initializeGoogleAr()
  {
    if ((this.m_ARCoreApi == null) && (this.mActivity != null) && (getARCoreEnabled()))
    {
      GoogleARCoreApi localGoogleARCoreApi = new GoogleARCoreApi();
      this.m_ARCoreApi = localGoogleARCoreApi;
      localGoogleARCoreApi.initializeARCore(this.mActivity);
      if (!this.mState.d())
        this.m_ARCoreApi.resumeARCore();
    }
    return false;
  }

  public boolean injectEvent(InputEvent paramInputEvent)
  {
    if (!o.c())
      return false;
    return nativeInjectEvent(paramInputEvent);
  }

  protected boolean isFinishing()
  {
    if (this.mQuitting)
      return true;
    Activity localActivity = this.mActivity;
    if (localActivity != null)
      this.mQuitting = localActivity.isFinishing();
    return this.mQuitting;
  }

  public boolean isLaunchScreenAdsFinished()
  {
    return (this.finishLaunchScreenAds) || (UnityAds.isSkipLaunchScreenAds());
  }

  public boolean isShouldShowLaunchScreenAds()
  {
    return this.shouldShowLaunchScreenAds;
  }

  protected boolean isUaaLUseCase()
  {
    Object localObject = this.mActivity;
    if (localObject != null)
    {
      localObject = ((Activity)localObject).getCallingPackage();
      if ((localObject != null) && (((String)localObject).equals(this.mContext.getPackageName())))
        return true;
    }
    return false;
  }

  protected void kill()
  {
    Process.killProcess(Process.myPid());
  }

  protected boolean loadLibrary(String paramString)
  {
    try
    {
      System.loadLibrary(paramString);
      return true;
    }
    catch (Exception paramString)
    {
    }
    return false;
  }

  public void lowMemory()
  {
    if (!o.c())
      return;
    queueGLThreadEvent(new Runnable()
    {
      public final void run()
      {
        UnityPlayer.this.nativeLowMemory();
      }
    });
  }

  public void newIntent(Intent paramIntent)
  {
    this.m_launchUri = paramIntent.getData();
    this.m_MainThread.e();
  }

  public boolean onGenericMotionEvent(MotionEvent paramMotionEvent)
  {
    return injectEvent(paramMotionEvent);
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    return injectEvent(paramKeyEvent);
  }

  public boolean onKeyLongPress(int paramInt, KeyEvent paramKeyEvent)
  {
    return injectEvent(paramKeyEvent);
  }

  public boolean onKeyMultiple(int paramInt1, int paramInt2, KeyEvent paramKeyEvent)
  {
    return injectEvent(paramKeyEvent);
  }

  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    return injectEvent(paramKeyEvent);
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return injectEvent(paramMotionEvent);
  }

  public void onUnityPlayerQuitted()
  {
  }

  public void onUnityPlayerUnloaded()
  {
  }

  public void pause()
  {
    Object localObject = this.m_ARCoreApi;
    if (localObject != null)
      ((GoogleARCoreApi)localObject).pauseARCore();
    localObject = this.mVideoPlayerProxy;
    if (localObject != null)
      ((q)localObject).a();
    localObject = this.m_AudioVolumeHandler;
    if (localObject != null)
    {
      ((AudioVolumeHandler)localObject).a();
      this.m_AudioVolumeHandler = null;
    }
    localObject = this.m_OrientationLockListener;
    if (localObject != null)
    {
      ((OrientationLockListener)localObject).a();
      this.m_OrientationLockListener = null;
    }
    localObject = this.m_splashAdsScreen;
    if (localObject != null)
      ((k)localObject).b();
    pauseUnity();
  }

  protected void pauseJavaAndCallUnloadCallback()
  {
    runOnUiThread(new Runnable()
    {
      public final void run()
      {
        UnityPlayer.this.pause();
        UnityPlayer.this.windowFocusChanged(false);
        UnityPlayer.this.m_UnityPlayerLifecycleEvents.onUnityPlayerUnloaded();
      }
    });
  }

  void postOnUiThread(Runnable paramRunnable)
  {
    new Handler(Looper.getMainLooper()).post(paramRunnable);
  }

  public void quit()
  {
    destroy();
  }

  public void removeViewFromPlayer(View paramView)
  {
    swapViews(this.mGlView, paramView);
    paramView = paramView.getParent();
    int i = 1;
    int j;
    if (paramView == null)
      j = 1;
    else
      j = 0;
    int k;
    if (this.mGlView.getParent() == this)
      k = 1;
    else
      k = 0;
    if ((j == 0) || (k == 0))
      i = 0;
    if (i == 0)
    {
      if (j == 0)
        f.Log(6, "removeViewFromPlayer: Failure removing view from hierarchy");
      if (k == 0)
        f.Log(6, "removeVireFromPlayer: Failure agging old view to hierarchy");
    }
  }

  public void reportError(String paramString1, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString1);
    localStringBuilder.append(": ");
    localStringBuilder.append(paramString2);
    f.Log(6, localStringBuilder.toString());
  }

  protected void reportSoftInputArea(Rect paramRect)
  {
    queueGLThreadEvent(new h(paramRect)
    {
      public final void a()
      {
        UnityPlayer.this.nativeSetInputArea(this.a.left, this.a.top, this.a.right, this.a.bottom);
      }
    });
  }

  protected void reportSoftInputIsVisible(boolean paramBoolean)
  {
    queueGLThreadEvent(new h(paramBoolean)
    {
      public final void a()
      {
        UnityPlayer.this.nativeSetKeyboardIsVisible(this.a);
      }
    });
  }

  protected void reportSoftInputSelection(int paramInt1, int paramInt2)
  {
    queueGLThreadEvent(new h(paramInt1, paramInt2)
    {
      public final void a()
      {
        UnityPlayer.this.nativeSetInputSelection(this.a, this.b);
      }
    });
  }

  protected void reportSoftInputStr(String paramString, int paramInt, boolean paramBoolean)
  {
    if (paramInt == 1)
      hideSoftInput();
    queueGLThreadEvent(new h(paramBoolean, paramString, paramInt)
    {
      public final void a()
      {
        if (this.a)
        {
          UnityPlayer.this.nativeSoftInputCanceled();
        }
        else
        {
          String str = this.b;
          if (str != null)
            UnityPlayer.this.nativeSetInputString(str);
        }
        if (this.c == 1)
          UnityPlayer.this.nativeSoftInputClosed();
      }
    });
  }

  protected void requestUserAuthorization(String paramString)
  {
    if ((paramString != null) && (!paramString.isEmpty()) && (this.mActivity != null))
    {
      UnityPermissions.ModalWaitForPermissionResponse localModalWaitForPermissionResponse = new UnityPermissions.ModalWaitForPermissionResponse();
      UnityPermissions.requestUserPermissions(this.mActivity, new String[] { paramString }, localModalWaitForPermissionResponse);
      localModalWaitForPermissionResponse.waitForResponse();
    }
  }

  public void resume()
  {
    Object localObject = this.m_ARCoreApi;
    if (localObject != null)
      ((GoogleARCoreApi)localObject).resumeARCore();
    this.mState.b(false);
    localObject = this.mVideoPlayerProxy;
    if (localObject != null)
      ((q)localObject).b();
    localObject = this.m_splashAdsScreen;
    if (localObject != null)
      ((k)localObject).c();
    checkResumePlayer();
    if (o.c())
      nativeRestartActivityIndicator();
    if (this.m_AudioVolumeHandler == null)
      this.m_AudioVolumeHandler = new AudioVolumeHandler(this.mContext);
    if ((this.m_OrientationLockListener == null) && (o.c()))
      this.m_OrientationLockListener = new OrientationLockListener(this.mContext);
  }

  void runOnAnonymousThread(Runnable paramRunnable)
  {
    new Thread(paramRunnable).start();
  }

  void runOnUiThread(Runnable paramRunnable)
  {
    Activity localActivity = this.mActivity;
    if (localActivity != null)
    {
      localActivity.runOnUiThread(paramRunnable);
      return;
    }
    if (Thread.currentThread() != Looper.getMainLooper().getThread())
    {
      this.mHandler.post(paramRunnable);
      return;
    }
    paramRunnable.run();
  }

  protected void setCharacterLimit(int paramInt)
  {
    runOnUiThread(new Runnable(paramInt)
    {
      public final void run()
      {
        if (UnityPlayer.this.mSoftInputDialog != null)
          UnityPlayer.this.mSoftInputDialog.a(this.a);
      }
    });
  }

  protected void setClipboardText(String paramString)
  {
    paramString = ClipData.newPlainText("Text", paramString);
    this.m_ClipboardManager.setPrimaryClip(paramString);
  }

  protected void setHideInputField(boolean paramBoolean)
  {
    runOnUiThread(new Runnable(paramBoolean)
    {
      public final void run()
      {
        if (UnityPlayer.this.mSoftInputDialog != null)
          UnityPlayer.this.mSoftInputDialog.a(this.a);
      }
    });
  }

  protected void setSelection(int paramInt1, int paramInt2)
  {
    runOnUiThread(new Runnable(paramInt1, paramInt2)
    {
      public final void run()
      {
        if (UnityPlayer.this.mSoftInputDialog != null)
          UnityPlayer.this.mSoftInputDialog.a(this.a, this.b);
      }
    });
  }

  protected void setSoftInputStr(String paramString)
  {
    runOnUiThread(new Runnable(paramString)
    {
      public final void run()
      {
        if ((UnityPlayer.this.mSoftInputDialog != null) && (this.a != null))
          UnityPlayer.this.mSoftInputDialog.a(this.a);
      }
    });
  }

  protected void showSoftInput(String paramString1, int paramInt1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, String paramString2, int paramInt2, boolean paramBoolean5, boolean paramBoolean6)
  {
    postOnUiThread(new Runnable(this, paramString1, paramInt1, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramString2, paramInt2, paramBoolean5, paramBoolean6)
    {
      public final void run()
      {
        UnityPlayer.this.mSoftInputDialog = new i(UnityPlayer.this.mContext, this.a, this.b, this.c, this.d, this.e, this.f, this.h, this.i, this.j, this.k);
        UnityPlayer.this.mSoftInputDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
          public final void onCancel(DialogInterface paramDialogInterface)
          {
            UnityPlayer.this.nativeSoftInputLostFocus();
            UnityPlayer.this.reportSoftInputStr(null, 1, false);
          }
        });
        UnityPlayer.this.mSoftInputDialog.show();
        UnityPlayer.this.nativeReportKeyboardConfigChanged();
      }
    });
  }

  protected boolean showVideoPlayer(String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean, int paramInt4, int paramInt5)
  {
    if (this.mVideoPlayerProxy == null)
      this.mVideoPlayerProxy = new q(this);
    paramBoolean = this.mVideoPlayerProxy.a(this.mContext, paramString, paramInt1, paramInt2, paramInt3, paramBoolean, paramInt4, paramInt5, new q.a()
    {
      public final void a()
      {
        UnityPlayer.access$3702(UnityPlayer.this, null);
      }
    });
    if (paramBoolean)
      runOnUiThread(new Runnable()
      {
        public final void run()
        {
          if ((UnityPlayer.this.nativeIsAutorotationOn()) && (UnityPlayer.this.mActivity != null))
            ((Activity)UnityPlayer.this.mContext).setRequestedOrientation(UnityPlayer.this.mInitialScreenOrientation);
        }
      });
    return paramBoolean;
  }

  protected boolean skipPermissionsDialog()
  {
    Activity localActivity = this.mActivity;
    if (localActivity != null)
      return UnityPermissions.skipPermissionsDialog(localActivity);
    return false;
  }

  public boolean startOrientationListener(int paramInt)
  {
    if (this.mOrientationListener != null);
    for (Object localObject = "Orientation Listener already started."; ; localObject = "Orientation Listener cannot detect orientation.")
    {
      f.Log(5, (String)localObject);
      return false;
      localObject = new OrientationEventListener(this.mContext, paramInt)
      {
        public final void onOrientationChanged(int paramInt)
        {
          UnityPlayer.this.m_MainThread.a(UnityPlayer.this.mNaturalOrientation, paramInt);
        }
      };
      this.mOrientationListener = ((OrientationEventListener)localObject);
      if (!((OrientationEventListener)localObject).canDetectOrientation())
        continue;
      this.mOrientationListener.enable();
      return true;
    }
  }

  public boolean stopOrientationListener()
  {
    OrientationEventListener localOrientationEventListener = this.mOrientationListener;
    if (localOrientationEventListener == null)
    {
      f.Log(5, "Orientation Listener was not started.");
      return false;
    }
    localOrientationEventListener.disable();
    this.mOrientationListener = null;
    return true;
  }

  protected void toggleGyroscopeSensor(boolean paramBoolean)
  {
    SensorManager localSensorManager = (SensorManager)this.mContext.getSystemService("sensor");
    Sensor localSensor = localSensorManager.getDefaultSensor(11);
    if (paramBoolean)
    {
      localSensorManager.registerListener(this.m_FakeListener, localSensor, 1);
      return;
    }
    localSensorManager.unregisterListener(this.m_FakeListener);
  }

  public void unload()
  {
    nativeApplicationUnload();
  }

  public void windowFocusChanged(boolean paramBoolean)
  {
    this.mState.a(paramBoolean);
    if (this.mState.e())
    {
      i locali = this.mSoftInputDialog;
      if ((locali == null) || (locali.a))
      {
        if (paramBoolean)
          this.m_MainThread.c();
        else
          this.m_MainThread.d();
        checkResumePlayer();
      }
    }
  }

  final class a
    implements SensorEventListener
  {
    a()
    {
    }

    public final void onAccuracyChanged(Sensor paramSensor, int paramInt)
    {
    }

    public final void onSensorChanged(SensorEvent paramSensorEvent)
    {
    }
  }

  static enum b
  {
  }

  private final class c extends PhoneStateListener
  {
    private c()
    {
    }

    public final void onCallStateChanged(int paramInt, String paramString)
    {
      paramString = UnityPlayer.this;
      boolean bool = true;
      if (paramInt != 1)
        bool = false;
      paramString.nativeMuteMasterAudio(bool);
    }
  }

  final class d extends AsyncTask
  {
    d()
    {
    }

    protected final Void doInBackground(String[] paramArrayOfString)
    {
      Object localObject = paramArrayOfString[0];
      String str = paramArrayOfString[1];
      try
      {
        paramArrayOfString = new org/json/JSONObject;
        paramArrayOfString.<init>();
        paramArrayOfString.put("game_bundle_id", localObject);
        paramArrayOfString.put("game_bundle_hash", str);
        localObject = new com/unity3d/splash/services/core/request/WebRequest;
        ((WebRequest)localObject).<init>("https://check.unity.cn/api/check-license", "POST", null);
        ((WebRequest)localObject).setBody(paramArrayOfString.toString());
        paramArrayOfString = ((WebRequest)localObject).makeRequest();
        if (((WebRequest)localObject).getResponseCode() == 200)
        {
          localObject = new org/json/JSONObject;
          ((JSONObject)localObject).<init>(paramArrayOfString);
          if (Boolean.valueOf(((JSONObject)localObject).optBoolean("registered")).booleanValue())
            f.Log(4, "Game Bundle Registered");
        }
      }
      catch (Exception paramArrayOfString)
      {
        paramArrayOfString.printStackTrace();
      }
      return (Void)null;
    }
  }

  final class e extends AsyncTask
  {
    e()
    {
    }

    protected final Void doInBackground(String[] paramArrayOfString)
    {
      paramArrayOfString = paramArrayOfString[0];
      try
      {
        Object localObject1 = new java/lang/StringBuilder;
        ((StringBuilder)localObject1).<init>();
        ((StringBuilder)localObject1).append(Build.MANUFACTURER);
        ((StringBuilder)localObject1).append("/");
        ((StringBuilder)localObject1).append(Build.MODEL);
        ((StringBuilder)localObject1).append("/");
        ((StringBuilder)localObject1).append(Build.DEVICE);
        Object localObject2 = ((StringBuilder)localObject1).toString();
        localObject1 = UnityPlayer.access$4200(paramArrayOfString);
        Object localObject3 = new org/json/JSONObject;
        ((JSONObject)localObject3).<init>();
        ((JSONObject)localObject3).put("game_bundle_hash", localObject1);
        ((JSONObject)localObject3).put("device_model", localObject2);
        ((JSONObject)localObject3).put("platform", "android");
        ((JSONObject)localObject3).put("unity_hash", UnityPlayer.this.getDeviceId());
        ((JSONObject)localObject3).put("splash_sdk_version", "2021.3.11f1c2");
        ((JSONObject)localObject3).put("builder", UnityPlayer.this.getBuilderUserId());
        localObject2 = new com/unity3d/splash/services/core/request/WebRequest;
        Object localObject4 = new java/lang/StringBuilder;
        ((StringBuilder)localObject4).<init>("https://check.unity.cn/api/check-license");
        ((StringBuilder)localObject4).append(UnityPlayer.access$4300((JSONObject)localObject3));
        ((WebRequest)localObject2).<init>(((StringBuilder)localObject4).toString(), "GET", null);
        localObject3 = ((WebRequest)localObject2).makeRequest();
        if (((WebRequest)localObject2).getResponseCode() == 200)
        {
          JSONObject localJSONObject = new org/json/JSONObject;
          localJSONObject.<init>((String)localObject3);
          Boolean localBoolean = Boolean.valueOf(localJSONObject.optBoolean("blocked"));
          localObject3 = Boolean.valueOf(localJSONObject.optBoolean("show_ads"));
          localObject2 = localJSONObject.optString("game_id");
          if (!Boolean.valueOf(localJSONObject.optBoolean("registered")).booleanValue())
          {
            localObject4 = new com/unity3d/player/UnityPlayer$d;
            ((UnityPlayer.d)localObject4).<init>(UnityPlayer.this);
            ((UnityPlayer.d)localObject4).execute(new String[] { paramArrayOfString, localObject1 });
          }
          if (UnityPlayer.this.mActivity != null)
          {
            localObject4 = UnityPlayer.this.getSplashGameId();
            localObject1 = localObject3;
            paramArrayOfString = (String)localObject2;
            if (localObject4 != null)
            {
              localObject1 = localObject3;
              paramArrayOfString = (String)localObject2;
              if (((String)localObject4).length() > 0)
              {
                localObject1 = Boolean.valueOf(true);
                paramArrayOfString = (String)localObject4;
              }
            }
            localObject2 = UnityPlayer.this.mActivity.getSharedPreferences("game_detail", 0).edit();
            ((SharedPreferences.Editor)localObject2).putString("game_id", paramArrayOfString);
            ((SharedPreferences.Editor)localObject2).putBoolean("show_ads", ((Boolean)localObject1).booleanValue());
            ((SharedPreferences.Editor)localObject2).putBoolean("blocked", localBoolean.booleanValue());
            ((SharedPreferences.Editor)localObject2).putString("url", localJSONObject.optString("url"));
            ((SharedPreferences.Editor)localObject2).putString("hash", localJSONObject.optString("hash"));
            ((SharedPreferences.Editor)localObject2).putString("version", localJSONObject.optString("version"));
            ((SharedPreferences.Editor)localObject2).commit();
            if ((!localBoolean.booleanValue()) && (((Boolean)localObject1).booleanValue()) && (paramArrayOfString != null) && (paramArrayOfString != ""))
              UnityAds.initialize(UnityPlayer.this.mActivity, paramArrayOfString, null);
          }
        }
      }
      catch (Exception paramArrayOfString)
      {
        paramArrayOfString.printStackTrace();
      }
      return (Void)(Void)(Void)(Void)null;
    }
  }

  static enum f
  {
    static
    {
      f localf = new f("SPLASH_ADS_DISMISS", 10);
      k = localf;
      l = new f[] { a, b, c, d, e, f, g, h, i, j, localf };
    }
  }

  private final class g extends Thread
  {
    Handler a;
    boolean b = false;
    boolean c = false;
    int d = UnityPlayer.b.b;
    int e = 0;
    int f;
    int g;
    boolean h = false;
    int i = 5;
    int j = 5;

    private g()
    {
    }

    private void a(UnityPlayer.f paramf)
    {
      Handler localHandler = this.a;
      if (localHandler != null)
        Message.obtain(localHandler, 2269, paramf).sendToTarget();
    }

    public final void a()
    {
      a(UnityPlayer.f.c);
    }

    public final void a(int paramInt1, int paramInt2)
    {
      this.f = paramInt1;
      this.g = paramInt2;
      a(UnityPlayer.f.j);
    }

    public final void a(Runnable paramRunnable)
    {
      if (this.a == null)
        return;
      a(UnityPlayer.f.a);
      Message.obtain(this.a, paramRunnable).sendToTarget();
    }

    public final void b()
    {
      a(UnityPlayer.f.b);
    }

    public final void b(Runnable paramRunnable)
    {
      if (this.a == null)
        return;
      a(UnityPlayer.f.d);
      Message.obtain(this.a, paramRunnable).sendToTarget();
    }

    public final void c()
    {
      a(UnityPlayer.f.g);
    }

    public final void c(Runnable paramRunnable)
    {
      Handler localHandler = this.a;
      if (localHandler == null)
        return;
      Message.obtain(localHandler, paramRunnable).sendToTarget();
      a(UnityPlayer.f.e);
    }

    public final void d()
    {
      a(UnityPlayer.f.f);
    }

    public final void d(Runnable paramRunnable)
    {
      Handler localHandler = this.a;
      if (localHandler != null)
        Message.obtain(localHandler, paramRunnable).sendToTarget();
    }

    public final void e()
    {
      a(UnityPlayer.f.i);
    }

    public final void f()
    {
      a(UnityPlayer.f.k);
    }

    public final void run()
    {
      setName("UnityMain");
      Looper.prepare();
      this.a = new Handler(new Handler.Callback()
      {
        private void a()
        {
          if ((UnityPlayer.g.this.d == UnityPlayer.b.c) && (UnityPlayer.g.this.c))
          {
            UnityPlayer.this.nativeFocusChanged(true);
            UnityPlayer.g.this.d = UnityPlayer.b.a;
          }
        }

        public final boolean handleMessage(Message paramMessage)
        {
          if (paramMessage.what != 2269)
            return false;
          paramMessage = (UnityPlayer.f)paramMessage.obj;
          if (paramMessage == UnityPlayer.f.h)
          {
            paramMessage = UnityPlayer.g.this;
            paramMessage.e -= 1;
            UnityPlayer.this.executeGLThreadJobs();
            if (!UnityPlayer.g.this.b)
              return true;
            if (!UnityPlayer.g.this.c)
              return true;
            if (UnityPlayer.g.this.i >= 0)
            {
              if ((UnityPlayer.g.this.i == 0) && (UnityPlayer.this.getSplashEnabled()))
                UnityPlayer.this.DisableStaticSplashScreen();
              paramMessage = UnityPlayer.g.this;
              paramMessage.i -= 1;
            }
            if ((UnityPlayer.g.this.i == 0) && (UnityPlayer.this.shouldShowLaunchScreenAds))
              UnityPlayer.this.ShowSplashAdsScreen();
            if ((UnityPlayer.g.this.h) && (UnityPlayer.g.this.j >= 0))
            {
              if (UnityPlayer.g.this.j == 0)
                UnityPlayer.this.DisableSplashAdsScreen();
              paramMessage = UnityPlayer.g.this;
              paramMessage.j -= 1;
            }
            if ((!UnityPlayer.this.isFinishing()) && (!UnityPlayer.this.nativeRender()))
              UnityPlayer.this.finish();
          }
          else if (paramMessage == UnityPlayer.f.c)
          {
            Looper.myLooper().quit();
          }
          else if (paramMessage == UnityPlayer.f.b)
          {
            UnityPlayer.g.this.b = true;
          }
          else if (paramMessage == UnityPlayer.f.a)
          {
            UnityPlayer.g.this.b = false;
          }
          else if (paramMessage == UnityPlayer.f.d)
          {
            UnityPlayer.g.this.c = false;
          }
          else
          {
            if (paramMessage == UnityPlayer.f.e)
              UnityPlayer.g.this.c = true;
            while (true)
            {
              a();
              break;
              if (paramMessage == UnityPlayer.f.f)
              {
                if (UnityPlayer.g.this.d == UnityPlayer.b.a)
                  UnityPlayer.this.nativeFocusChanged(false);
                UnityPlayer.g.this.d = UnityPlayer.b.b;
              }
              else
              {
                if (paramMessage == UnityPlayer.f.g)
                {
                  UnityPlayer.g.this.d = UnityPlayer.b.c;
                  continue;
                }
                if (paramMessage == UnityPlayer.f.i)
                {
                  UnityPlayer.this.nativeSetLaunchURL(UnityPlayer.this.getLaunchURL());
                }
                else if (paramMessage == UnityPlayer.f.j)
                {
                  UnityPlayer.this.nativeOrientationChanged(UnityPlayer.g.this.f, UnityPlayer.g.this.g);
                }
                else
                {
                  if (paramMessage != UnityPlayer.f.k)
                    break;
                  UnityPlayer.g.this.h = true;
                }
              }
            }
          }
          if ((UnityPlayer.g.this.b) && (UnityPlayer.g.this.e <= 0))
          {
            Message.obtain(UnityPlayer.g.this.a, 2269, UnityPlayer.f.h).sendToTarget();
            paramMessage = UnityPlayer.g.this;
            paramMessage.e += 1;
          }
          return true;
        }
      });
      Looper.loop();
    }
  }

  private abstract class h
    implements Runnable
  {
    private h()
    {
    }

    public abstract void a();

    public final void run()
    {
      if (!UnityPlayer.this.isFinishing())
        a();
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.player.UnityPlayer
 * JD-Core Version:    0.6.0
 */