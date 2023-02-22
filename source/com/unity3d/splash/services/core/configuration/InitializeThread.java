package com.unity3d.splash.services.core.configuration;

import android.app.Application;
import android.os.Build.VERSION;
import android.os.ConditionVariable;
import com.unity3d.splash.UnityAds;
import com.unity3d.splash.services.core.api.Lifecycle;
import com.unity3d.splash.services.core.connectivity.ConnectivityMonitor;
import com.unity3d.splash.services.core.connectivity.IConnectivityListener;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.properties.SdkProperties;
import com.unity3d.splash.services.core.request.WebRequest;
import com.unity3d.splash.services.core.webview.WebView;
import com.unity3d.splash.services.core.webview.WebViewApp;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

public class InitializeThread extends Thread
{
  private static InitializeThread _thread;
  private InitializeState _state;
  private boolean _stopThread = false;

  private InitializeThread(InitializeState paramInitializeState)
  {
    this._state = paramInitializeState;
  }

  public static void initialize(Configuration paramConfiguration)
  {
    monitorenter;
    try
    {
      if (_thread == null)
      {
        InitializeThread localInitializeThread = new com/unity3d/splash/services/core/configuration/InitializeThread;
        InitializeStateReset localInitializeStateReset = new com/unity3d/splash/services/core/configuration/InitializeThread$InitializeStateReset;
        localInitializeStateReset.<init>(paramConfiguration);
        localInitializeThread.<init>(localInitializeStateReset);
        _thread = localInitializeThread;
        localInitializeThread.setName("UnityAdsInitializeThread");
        _thread.start();
      }
      monitorexit;
      return;
    }
    finally
    {
      paramConfiguration = finally;
      monitorexit;
    }
    throw paramConfiguration;
  }

  public static void reset()
  {
    monitorenter;
    try
    {
      if (_thread == null)
      {
        InitializeThread localInitializeThread = new com/unity3d/splash/services/core/configuration/InitializeThread;
        InitializeStateForceReset localInitializeStateForceReset = new com/unity3d/splash/services/core/configuration/InitializeThread$InitializeStateForceReset;
        localInitializeStateForceReset.<init>();
        localInitializeThread.<init>(localInitializeStateForceReset);
        _thread = localInitializeThread;
        localInitializeThread.setName("UnityAdsResetThread");
        _thread.start();
      }
      monitorexit;
      return;
    }
    finally
    {
      localObject = finally;
      monitorexit;
    }
    throw localObject;
  }

  public void quit()
  {
    this._stopThread = true;
  }

  public void run()
  {
    while (true)
    {
      InitializeState localInitializeState = this._state;
      if ((localInitializeState == null) || ((localInitializeState instanceof InitializeStateComplete)) || (this._stopThread))
        break;
      this._state = localInitializeState.execute();
    }
    _thread = null;
  }

  private static abstract class InitializeState
  {
    public abstract InitializeState execute();
  }

  public static class InitializeStateComplete extends InitializeThread.InitializeState
  {
    private Configuration _configuration;

    public InitializeStateComplete(Configuration paramConfiguration)
    {
      super();
      this._configuration = paramConfiguration;
    }

    public InitializeThread.InitializeState execute()
    {
      for (Object localObject : this._configuration.getModuleConfigurationList())
      {
        localObject = this._configuration.getModuleConfiguration((String)localObject);
        if (localObject == null)
          continue;
        ((IModuleConfiguration)localObject).initCompleteState(this._configuration);
      }
      return (InitializeThread.InitializeState)null;
    }
  }

  public static class InitializeStateConfig extends InitializeThread.InitializeState
  {
    private Configuration _configuration;
    private int _maxRetries = 6;
    private int _retries = 0;
    private int _retryDelay = 5;

    public InitializeStateConfig(Configuration paramConfiguration)
    {
      super();
      this._configuration = paramConfiguration;
    }

    public InitializeThread.InitializeState execute()
    {
      StringBuilder localStringBuilder = new StringBuilder("Unity Ads init: load configuration from ");
      localStringBuilder.append(SdkProperties.getConfigUrl());
      DeviceLog.info(localStringBuilder.toString());
      try
      {
        this._configuration.makeRequest();
        return new InitializeThread.InitializeStateLoadCache(this._configuration);
      }
      catch (Exception localException)
      {
        int i = this._retries;
        if (i < this._maxRetries)
        {
          int j = this._retryDelay * 2;
          this._retryDelay = j;
          this._retries = (i + 1);
          return new InitializeThread.InitializeStateRetry(this, j);
        }
      }
      return new InitializeThread.InitializeStateNetworkError(localException, this, this._configuration);
    }
  }

  public static class InitializeStateCreate extends InitializeThread.InitializeState
  {
    private Configuration _configuration;
    private String _webViewData;

    public InitializeStateCreate(Configuration paramConfiguration, String paramString)
    {
      super();
      this._configuration = paramConfiguration;
      this._webViewData = paramString;
    }

    public InitializeThread.InitializeState execute()
    {
      DeviceLog.debug("Unity Ads init: creating webapp");
      Configuration localConfiguration = this._configuration;
      localConfiguration.setWebViewData(this._webViewData);
      try
      {
        boolean bool = WebViewApp.create(localConfiguration);
        if (bool)
          return new InitializeThread.InitializeStateComplete(this._configuration);
        DeviceLog.error("Unity Ads WebApp creation failed!");
        return new InitializeThread.InitializeStateError("create webapp", new Exception("Creation of WebApp failed!"), this._configuration);
      }
      catch (IllegalThreadStateException localIllegalThreadStateException)
      {
        DeviceLog.exception("Illegal Thread", localIllegalThreadStateException);
      }
      return new InitializeThread.InitializeStateError("create webapp", localIllegalThreadStateException, this._configuration);
    }

    public Configuration getConfiguration()
    {
      return this._configuration;
    }

    public String getWebData()
    {
      return this._webViewData;
    }
  }

  public static class InitializeStateError extends InitializeThread.InitializeState
  {
    protected Configuration _configuration;
    Exception _exception;
    String _state;

    public InitializeStateError(String paramString, Exception paramException, Configuration paramConfiguration)
    {
      super();
      this._state = paramString;
      this._exception = paramException;
      this._configuration = paramConfiguration;
    }

    public InitializeThread.InitializeState execute()
    {
      Object localObject1 = new StringBuilder("Unity Ads init: halting init in ");
      ((StringBuilder)localObject1).append(this._state);
      ((StringBuilder)localObject1).append(": ");
      ((StringBuilder)localObject1).append(this._exception.getMessage());
      DeviceLog.error(((StringBuilder)localObject1).toString());
      for (Object localObject2 : this._configuration.getModuleConfigurationList())
      {
        localObject2 = this._configuration.getModuleConfiguration((String)localObject2);
        if (localObject2 == null)
          continue;
        ((IModuleConfiguration)localObject2).initErrorState(this._configuration, this._state, this._exception.getMessage());
      }
      return (InitializeThread.InitializeState)(InitializeThread.InitializeState)null;
    }
  }

  public static class InitializeStateForceReset extends InitializeThread.InitializeStateReset
  {
    public InitializeStateForceReset()
    {
      super();
    }

    public InitializeThread.InitializeState execute()
    {
      super.execute();
      return null;
    }
  }

  public static class InitializeStateInitModules extends InitializeThread.InitializeState
  {
    private Configuration _configuration;

    public InitializeStateInitModules(Configuration paramConfiguration)
    {
      super();
      this._configuration = paramConfiguration;
    }

    public InitializeThread.InitializeState execute()
    {
      for (Object localObject : this._configuration.getModuleConfigurationList())
      {
        localObject = this._configuration.getModuleConfiguration((String)localObject);
        if ((localObject != null) && (!((IModuleConfiguration)localObject).initModuleState(this._configuration)))
          return null;
      }
      return (InitializeThread.InitializeState)new InitializeThread.InitializeStateConfig(this._configuration);
    }

    public Configuration getConfiguration()
    {
      return this._configuration;
    }
  }

  public static class InitializeStateLoadCache extends InitializeThread.InitializeState
  {
    private Configuration _configuration;

    public InitializeStateLoadCache(Configuration paramConfiguration)
    {
      super();
      this._configuration = paramConfiguration;
    }

    public InitializeThread.InitializeState execute()
    {
      DeviceLog.debug("Unity Ads init: check if webapp can be loaded from local cache");
      try
      {
        Object localObject1 = new java/io/File;
        ((File)localObject1).<init>(SdkProperties.getLocalWebViewFile());
        localObject2 = Utilities.readFileBytes((File)localObject1);
        localObject1 = Utilities.Sha256(localObject2);
        if ((localObject1 != null) && (((String)localObject1).equals(this._configuration.getWebViewHash())))
          try
          {
            localObject1 = new String(localObject2, "UTF-8");
            DeviceLog.info("Unity Ads init: webapp loaded from local cache");
            return new InitializeThread.InitializeStateCreate(this._configuration, (String)localObject1);
          }
          catch (UnsupportedEncodingException localUnsupportedEncodingException)
          {
            return new InitializeThread.InitializeStateError("load cache", localUnsupportedEncodingException, this._configuration);
          }
        UnityAds.setSkipLaunchScreenAds(true);
        return new InitializeThread.InitializeStateLoadWeb(this._configuration);
      }
      catch (IOException localIOException)
      {
        Object localObject2 = new StringBuilder("Unity Ads init: webapp not found in local cache: ");
        ((StringBuilder)localObject2).append(localIOException.getMessage());
        DeviceLog.debug(((StringBuilder)localObject2).toString());
      }
      return (InitializeThread.InitializeState)(InitializeThread.InitializeState)new InitializeThread.InitializeStateLoadWeb(this._configuration);
    }

    public Configuration getConfiguration()
    {
      return this._configuration;
    }
  }

  public static class InitializeStateLoadWeb extends InitializeThread.InitializeState
  {
    private Configuration _configuration;
    private int _maxRetries = 6;
    private int _retries = 0;
    private int _retryDelay = 5;

    public InitializeStateLoadWeb(Configuration paramConfiguration)
    {
      super();
      this._configuration = paramConfiguration;
    }

    public InitializeThread.InitializeState execute()
    {
      Object localObject = new StringBuilder("Unity Ads init: loading webapp from ");
      ((StringBuilder)localObject).append(this._configuration.getWebViewUrl());
      DeviceLog.info(((StringBuilder)localObject).toString());
      try
      {
        localObject = new WebRequest(this._configuration.getWebViewUrl(), "GET", null);
        try
        {
          String str = ((WebRequest)localObject).makeRequest();
          localObject = this._configuration.getWebViewHash();
          if ((localObject != null) && (!Utilities.Sha256(str).equals(localObject)))
            return new InitializeThread.InitializeStateError("load web", new Exception("Invalid webViewHash"), this._configuration);
          if (localObject != null)
            Utilities.writeFile(new File(SdkProperties.getLocalWebViewFile()), str);
          return new InitializeThread.InitializeStateCreate(this._configuration, str);
        }
        catch (Exception localException)
        {
          int i = this._retries;
          if (i < this._maxRetries)
          {
            int j = this._retryDelay * 2;
            this._retryDelay = j;
            this._retries = (i + 1);
            return new InitializeThread.InitializeStateRetry(this, j);
          }
          return new InitializeThread.InitializeStateNetworkError(localException, this, this._configuration);
        }
      }
      catch (MalformedURLException localMalformedURLException)
      {
        DeviceLog.exception("Malformed URL", localMalformedURLException);
      }
      return (InitializeThread.InitializeState)new InitializeThread.InitializeStateError("make webrequest", localMalformedURLException, this._configuration);
    }

    public Configuration getConfiguration()
    {
      return this._configuration;
    }
  }

  public static class InitializeStateNetworkError extends InitializeThread.InitializeStateError
    implements IConnectivityListener
  {
    protected static final int CONNECTED_EVENT_THRESHOLD_MS = 10000;
    protected static final int MAX_CONNECTED_EVENTS = 500;
    private static long _lastConnectedEventTimeMs;
    private static int _receivedConnectedEvents;
    private ConditionVariable _conditionVariable;
    private InitializeThread.InitializeState _erroredState;

    public InitializeStateNetworkError(Exception paramException, InitializeThread.InitializeState paramInitializeState, Configuration paramConfiguration)
    {
      super(paramException, paramConfiguration);
      this._erroredState = paramInitializeState;
    }

    private boolean shouldHandleConnectedEvent()
    {
      return (System.currentTimeMillis() - _lastConnectedEventTimeMs >= 10000L) && (_receivedConnectedEvents <= 500);
    }

    public InitializeThread.InitializeState execute()
    {
      DeviceLog.error("Unity Ads init: network error, waiting for connection events");
      this._conditionVariable = new ConditionVariable();
      ConnectivityMonitor.addListener(this);
      boolean bool = this._conditionVariable.block(600000L);
      ConnectivityMonitor.removeListener(this);
      if (bool)
        return this._erroredState;
      return new InitializeThread.InitializeStateError("network error", new Exception("No connected events within the timeout!"), this._configuration);
    }

    public void onConnected()
    {
      _receivedConnectedEvents += 1;
      DeviceLog.debug("Unity Ads init got connected event");
      if (shouldHandleConnectedEvent())
        this._conditionVariable.open();
      if (_receivedConnectedEvents > 500)
        ConnectivityMonitor.removeListener(this);
      _lastConnectedEventTimeMs = System.currentTimeMillis();
    }

    public void onDisconnected()
    {
      DeviceLog.debug("Unity Ads init got disconnected event");
    }
  }

  public static class InitializeStateReset extends InitializeThread.InitializeState
  {
    private Configuration _configuration;

    public InitializeStateReset(Configuration paramConfiguration)
    {
      super();
      this._configuration = paramConfiguration;
    }

    private void unregisterLifecycleCallbacks()
    {
      if (Lifecycle.getLifecycleListener() != null)
      {
        if (ClientProperties.getApplication() != null)
          ClientProperties.getApplication().unregisterActivityLifecycleCallbacks(Lifecycle.getLifecycleListener());
        Lifecycle.setLifecycleListener(null);
      }
    }

    public InitializeThread.InitializeState execute()
    {
      DeviceLog.debug("Unity Ads init: starting init");
      Object localObject1 = new ConditionVariable();
      Object localObject2 = WebViewApp.getCurrentApp();
      int i = 0;
      if (localObject2 != null)
      {
        ((WebViewApp)localObject2).setWebAppLoaded(false);
        ((WebViewApp)localObject2).setWebAppInitialized(false);
        boolean bool;
        if (((WebViewApp)localObject2).getWebView() != null)
        {
          Utilities.runOnUiThread(new Runnable((WebViewApp)localObject2, (ConditionVariable)localObject1)
          {
            public void run()
            {
              this.val$currentApp.getWebView().destroy();
              this.val$currentApp.setWebView(null);
              this.val$cv.open();
            }
          });
          bool = ((ConditionVariable)localObject1).block(10000L);
        }
        else
        {
          bool = true;
        }
        if (!bool)
          return new InitializeThread.InitializeStateError("reset webapp", new Exception("Reset failed on opening ConditionVariable"), this._configuration);
      }
      if (Build.VERSION.SDK_INT > 13)
        unregisterLifecycleCallbacks();
      SdkProperties.setCacheDirectory(null);
      if (SdkProperties.getCacheDirectory() == null)
        return new InitializeThread.InitializeStateError("reset webapp", new Exception("Cache directory is NULL"), this._configuration);
      SdkProperties.setInitialized(false);
      this._configuration.setConfigUrl(SdkProperties.getConfigUrl());
      localObject1 = this._configuration.getModuleConfigurationList();
      int j = localObject1.length;
      while (i < j)
      {
        localObject2 = localObject1[i];
        localObject2 = this._configuration.getModuleConfiguration((String)localObject2);
        if (localObject2 != null)
          ((IModuleConfiguration)localObject2).resetState(this._configuration);
        i++;
      }
      return (InitializeThread.InitializeState)(InitializeThread.InitializeState)new InitializeThread.InitializeStateInitModules(this._configuration);
    }
  }

  public static class InitializeStateRetry extends InitializeThread.InitializeState
  {
    int _delay;
    InitializeThread.InitializeState _state;

    public InitializeStateRetry(InitializeThread.InitializeState paramInitializeState, int paramInt)
    {
      super();
      this._state = paramInitializeState;
      this._delay = paramInt;
    }

    public InitializeThread.InitializeState execute()
    {
      StringBuilder localStringBuilder = new StringBuilder("Unity Ads init: retrying in ");
      localStringBuilder.append(this._delay);
      localStringBuilder.append(" seconds");
      DeviceLog.debug(localStringBuilder.toString());
      try
      {
        Thread.sleep(this._delay * 1000L);
      }
      catch (InterruptedException localInterruptedException)
      {
        DeviceLog.exception("Init retry interrupted", localInterruptedException);
      }
      return this._state;
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.configuration.InitializeThread
 * JD-Core Version:    0.6.0
 */