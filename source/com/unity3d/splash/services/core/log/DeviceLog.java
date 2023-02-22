package com.unity3d.splash.services.core.log;

import android.util.Log;
import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;

public class DeviceLog
{
  private static boolean FORCE_DEBUG_LOG = false;
  public static final int LOGLEVEL_DEBUG = 8;
  private static final int LOGLEVEL_ERROR = 1;
  public static final int LOGLEVEL_INFO = 4;
  private static final int LOGLEVEL_WARNING = 2;
  private static boolean LOG_DEBUG = true;
  private static boolean LOG_ERROR = true;
  private static boolean LOG_INFO = true;
  private static boolean LOG_WARNING = true;
  private static final int MAX_DEBUG_MSG_LENGTH = 3072;
  private static final HashMap _deviceLogLevel;

  static
  {
    HashMap localHashMap = new HashMap();
    _deviceLogLevel = localHashMap;
    if (localHashMap.size() == 0)
    {
      _deviceLogLevel.put(UnityAdsLogLevel.INFO, new DeviceLogLevel("i"));
      _deviceLogLevel.put(UnityAdsLogLevel.DEBUG, new DeviceLogLevel("d"));
      _deviceLogLevel.put(UnityAdsLogLevel.WARNING, new DeviceLogLevel("w"));
      _deviceLogLevel.put(UnityAdsLogLevel.ERROR, new DeviceLogLevel("e"));
    }
    if (new File("/data/local/tmp/UnityAdsForceDebugMode").exists())
      FORCE_DEBUG_LOG = true;
  }

  private static String checkMessage(String paramString)
  {
    String str;
    if (paramString != null)
    {
      str = paramString;
      if (paramString.length() != 0);
    }
    else
    {
      str = "DO NOT USE EMPTY MESSAGES, use DeviceLog.entered() instead";
    }
    return str;
  }

  private static DeviceLogEntry createLogEntry(UnityAdsLogLevel paramUnityAdsLogLevel, String paramString)
  {
    Object localObject1 = Thread.currentThread().getStackTrace();
    DeviceLogLevel localDeviceLogLevel = getLogLevel(paramUnityAdsLogLevel);
    Object localObject2 = null;
    paramUnityAdsLogLevel = localObject2;
    if (localDeviceLogLevel != null)
    {
      int i = 0;
      int j = 0;
      while (i < localObject1.length)
      {
        paramUnityAdsLogLevel = localObject1[i];
        if (paramUnityAdsLogLevel.getClassName().equals(DeviceLog.class.getName()))
          j = 1;
        if ((!paramUnityAdsLogLevel.getClassName().equals(DeviceLog.class.getName())) && (j != 0))
          break;
        i++;
      }
      if (i < localObject1.length)
        localObject1 = localObject1[i];
      else
        localObject1 = null;
      paramUnityAdsLogLevel = localObject2;
      if (localObject1 != null)
        paramUnityAdsLogLevel = new DeviceLogEntry(localDeviceLogLevel, paramString, (StackTraceElement)localObject1);
    }
    return (DeviceLogEntry)paramUnityAdsLogLevel;
  }

  public static void debug(String paramString)
  {
    if ((!LOG_DEBUG) && (!FORCE_DEBUG_LOG))
      return;
    if (paramString.length() > 3072)
    {
      debug(paramString.substring(0, 3072));
      if (paramString.length() < 30720)
        debug(paramString.substring(3072));
      return;
    }
    write(UnityAdsLogLevel.DEBUG, checkMessage(paramString));
  }

  public static void debug(String paramString, Object[] paramArrayOfObject)
  {
    debug(String.format(paramString, paramArrayOfObject));
  }

  public static void entered()
  {
    debug("ENTERED METHOD");
  }

  public static void error(String paramString)
  {
    write(UnityAdsLogLevel.ERROR, checkMessage(paramString));
  }

  public static void error(String paramString, Object[] paramArrayOfObject)
  {
    error(String.format(paramString, paramArrayOfObject));
  }

  public static void exception(String paramString, Exception paramException)
  {
    Object localObject = "";
    if (paramString != null)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("");
      ((StringBuilder)localObject).append(paramString);
      localObject = ((StringBuilder)localObject).toString();
    }
    paramString = (String)localObject;
    if (paramException != null)
    {
      paramString = new StringBuilder();
      paramString.append((String)localObject);
      paramString.append(": ");
      paramString.append(paramException.getMessage());
      paramString = paramString.toString();
    }
    localObject = paramString;
    if (paramException != null)
    {
      localObject = paramString;
      if (paramException.getCause() != null)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append(": ");
        ((StringBuilder)localObject).append(paramException.getCause().getMessage());
        localObject = ((StringBuilder)localObject).toString();
      }
    }
    write(UnityAdsLogLevel.ERROR, (String)localObject);
  }

  private static DeviceLogLevel getLogLevel(UnityAdsLogLevel paramUnityAdsLogLevel)
  {
    return (DeviceLogLevel)_deviceLogLevel.get(paramUnityAdsLogLevel);
  }

  public static void info(String paramString)
  {
    write(UnityAdsLogLevel.INFO, checkMessage(paramString));
  }

  public static void info(String paramString, Object[] paramArrayOfObject)
  {
    info(String.format(paramString, paramArrayOfObject));
  }

  public static void setLogLevel(int paramInt)
  {
    if (paramInt >= 8)
    {
      LOG_ERROR = true;
      LOG_WARNING = true;
      LOG_INFO = true;
      LOG_DEBUG = true;
      return;
    }
    if (paramInt >= 4)
    {
      LOG_ERROR = true;
      LOG_WARNING = true;
      LOG_INFO = true;
      LOG_DEBUG = false;
      return;
    }
    if (paramInt >= 2)
    {
      LOG_ERROR = true;
      LOG_WARNING = true;
      LOG_INFO = false;
      LOG_DEBUG = false;
      return;
    }
    if (paramInt > 0)
    {
      LOG_ERROR = true;
      LOG_WARNING = false;
      LOG_INFO = false;
      LOG_DEBUG = false;
      return;
    }
    LOG_ERROR = false;
    LOG_WARNING = false;
    LOG_INFO = false;
    LOG_DEBUG = false;
  }

  public static void warning(String paramString)
  {
    write(UnityAdsLogLevel.WARNING, checkMessage(paramString));
  }

  public static void warning(String paramString, Object[] paramArrayOfObject)
  {
    warning(String.format(paramString, paramArrayOfObject));
  }

  private static void write(UnityAdsLogLevel paramUnityAdsLogLevel, String paramString)
  {
    int i = 1.$SwitchMap$com$unity3d$splash$services$core$log$DeviceLog$UnityAdsLogLevel[paramUnityAdsLogLevel.ordinal()];
    boolean bool1 = true;
    boolean bool2;
    if (i != 1)
    {
      if (i != 2)
      {
        if (i != 3)
        {
          if (i != 4)
            bool2 = true;
          else
            bool2 = LOG_ERROR;
        }
        else
          bool2 = LOG_WARNING;
      }
      else
        bool2 = LOG_DEBUG;
    }
    else
      bool2 = LOG_INFO;
    if (FORCE_DEBUG_LOG)
      bool2 = bool1;
    if (bool2)
      writeToLog(createLogEntry(paramUnityAdsLogLevel, paramString));
  }

  private static void writeToLog(DeviceLogEntry paramDeviceLogEntry)
  {
    if ((paramDeviceLogEntry != null) && (paramDeviceLogEntry.getLogLevel() != null))
    {
      Object localObject;
      try
      {
        Method localMethod = Log.class.getMethod(paramDeviceLogEntry.getLogLevel().getReceivingMethodName(), new Class[] { String.class, String.class });
      }
      catch (Exception localObject)
      {
        Log.e("UnityAds", "Writing to log failed!", localException);
        localObject = null;
      }
      if (localObject != null)
        try
        {
          localObject.invoke(null, new Object[] { paramDeviceLogEntry.getLogLevel().getLogTag(), paramDeviceLogEntry.getParsedMessage() });
          return;
        }
        catch (Exception paramDeviceLogEntry)
        {
          Log.e("UnityAds", "Writing to log failed!", paramDeviceLogEntry);
        }
    }
  }

  public static enum UnityAdsLogLevel
  {
    static
    {
      DEBUG = new UnityAdsLogLevel("DEBUG", 1);
      WARNING = new UnityAdsLogLevel("WARNING", 2);
      UnityAdsLogLevel localUnityAdsLogLevel = new UnityAdsLogLevel("ERROR", 3);
      ERROR = localUnityAdsLogLevel;
      $VALUES = new UnityAdsLogLevel[] { INFO, DEBUG, WARNING, localUnityAdsLogLevel };
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.log.DeviceLog
 * JD-Core Version:    0.6.0
 */