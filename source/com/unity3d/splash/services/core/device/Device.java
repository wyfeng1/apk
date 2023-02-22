package com.unity3d.splash.services.core.device;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.provider.Settings.Global;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.properties.ClientProperties;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Device
{
  public static String getAdvertisingTrackingId()
  {
    return AdvertisingId.getAdvertisingTrackingId();
  }

  public static String getAndroidId()
  {
    Object localObject;
    try
    {
      String str = Settings.Secure.getString(ClientProperties.getApplicationContext().getContentResolver(), "android_id");
    }
    catch (Exception localObject)
    {
      DeviceLog.exception("Problems fetching androidId", localException);
      localObject = null;
    }
    return localObject;
  }

  public static int getApiLevel()
  {
    return Build.VERSION.SDK_INT;
  }

  // ERROR //
  public static String getApkDigest()
  {
    // Byte code:
    //   0: invokestatic 29	com/unity3d/splash/services/core/properties/ClientProperties:getApplicationContext	()Landroid/content/Context;
    //   3: invokevirtual 65	android/content/Context:getPackageCodePath	()Ljava/lang/String;
    //   6: astore_0
    //   7: aconst_null
    //   8: astore_1
    //   9: new 67	java/io/FileInputStream
    //   12: astore_2
    //   13: new 69	java/io/File
    //   16: astore_3
    //   17: aload_3
    //   18: aload_0
    //   19: invokespecial 72	java/io/File:<init>	(Ljava/lang/String;)V
    //   22: aload_2
    //   23: aload_3
    //   24: invokespecial 75	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   27: aload_2
    //   28: invokestatic 81	com/unity3d/splash/services/core/misc/Utilities:Sha256	(Ljava/io/InputStream;)Ljava/lang/String;
    //   31: astore_0
    //   32: aload_2
    //   33: invokevirtual 86	java/io/InputStream:close	()V
    //   36: aload_0
    //   37: areturn
    //   38: astore_0
    //   39: goto +6 -> 45
    //   42: astore_0
    //   43: aload_1
    //   44: astore_2
    //   45: aload_2
    //   46: ifnull +7 -> 53
    //   49: aload_2
    //   50: invokevirtual 86	java/io/InputStream:close	()V
    //   53: aload_0
    //   54: athrow
    //   55: astore_2
    //   56: goto -20 -> 36
    //   59: astore_2
    //   60: goto -7 -> 53
    //
    // Exception table:
    //   from	to	target	type
    //   27	32	38	finally
    //   9	27	42	finally
    //   32	36	55	java/io/IOException
    //   49	53	59	java/io/IOException
  }

  public static float getBatteryLevel()
  {
    if (ClientProperties.getApplicationContext() != null)
    {
      Intent localIntent = ClientProperties.getApplicationContext().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
      if (localIntent != null)
      {
        int i = localIntent.getIntExtra("level", -1);
        int j = localIntent.getIntExtra("scale", -1);
        return i / j;
      }
    }
    return -1.0F;
  }

  public static int getBatteryStatus()
  {
    if (ClientProperties.getApplicationContext() != null)
    {
      Intent localIntent = ClientProperties.getApplicationContext().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
      if (localIntent != null)
        return localIntent.getIntExtra("status", -1);
    }
    return -1;
  }

  public static String getBoard()
  {
    return Build.BOARD;
  }

  public static String getBootloader()
  {
    return Build.BOOTLOADER;
  }

  public static String getBrand()
  {
    return Build.BRAND;
  }

  public static String getBuildId()
  {
    return Build.ID;
  }

  public static String getBuildVersionIncremental()
  {
    return Build.VERSION.INCREMENTAL;
  }

  public static long getCPUCount()
  {
    return Runtime.getRuntime().availableProcessors();
  }

  public static String getCertificateFingerprint()
  {
    Object localObject1 = ClientProperties.getApplicationContext().getPackageManager();
    Object localObject2 = ClientProperties.getApplicationContext().getPackageName();
    Object localObject4 = null;
    Object localObject3;
    try
    {
      localObject1 = ((PackageManager)localObject1).getPackageInfo((String)localObject2, 64).signatures;
      localObject2 = localObject4;
      if (localObject1 != null)
      {
        localObject2 = localObject4;
        if (localObject1.length > 0)
        {
          localObject2 = CertificateFactory.getInstance("X.509");
          ByteArrayInputStream localByteArrayInputStream = new java/io/ByteArrayInputStream;
          localByteArrayInputStream.<init>(localObject1[0].toByteArray());
          localObject2 = (X509Certificate)((CertificateFactory)localObject2).generateCertificate(localByteArrayInputStream);
          localObject2 = Utilities.toHexString(MessageDigest.getInstance("SHA-1").digest(((X509Certificate)localObject2).getEncoded()));
        }
      }
    }
    catch (Exception localObject3)
    {
      DeviceLog.exception("Exception when signing certificate fingerprint", localException);
      localObject3 = localObject4;
    }
    return (String)(String)localObject3;
  }

  public static String getDevice()
  {
    return Build.DEVICE;
  }

  public static long getElapsedRealtime()
  {
    return SystemClock.elapsedRealtime();
  }

  public static String getFingerprint()
  {
    return Build.FINGERPRINT;
  }

  public static long getFreeMemory()
  {
    return getMemoryInfo(MemoryInfoType.FREE_MEMORY);
  }

  public static long getFreeSpace(File paramFile)
  {
    if ((paramFile != null) && (paramFile.exists()))
      return Math.round((float)(paramFile.getFreeSpace() / 1024L));
    return -1L;
  }

  public static String getHardware()
  {
    return Build.HARDWARE;
  }

  public static String getHost()
  {
    return Build.HOST;
  }

  public static String getManufacturer()
  {
    return Build.MANUFACTURER;
  }

  // ERROR //
  private static long getMemoryInfo(MemoryInfoType paramMemoryInfoType)
  {
    // Byte code:
    //   0: getstatic 267	com/unity3d/splash/services/core/device/Device$1:$SwitchMap$com$unity3d$splash$services$core$device$Device$MemoryInfoType	[I
    //   3: aload_0
    //   4: invokevirtual 270	com/unity3d/splash/services/core/device/Device$MemoryInfoType:ordinal	()I
    //   7: iaload
    //   8: istore_1
    //   9: iconst_2
    //   10: istore_2
    //   11: iload_1
    //   12: iconst_1
    //   13: if_icmpeq +13 -> 26
    //   16: iload_1
    //   17: iconst_2
    //   18: if_icmpeq +10 -> 28
    //   21: iconst_m1
    //   22: istore_2
    //   23: goto +5 -> 28
    //   26: iconst_1
    //   27: istore_2
    //   28: aconst_null
    //   29: astore_3
    //   30: aconst_null
    //   31: astore 4
    //   33: new 272	java/io/RandomAccessFile
    //   36: astore 5
    //   38: aload 5
    //   40: ldc_w 274
    //   43: ldc_w 276
    //   46: invokespecial 279	java/io/RandomAccessFile:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   49: iconst_0
    //   50: istore_1
    //   51: iload_1
    //   52: iload_2
    //   53: if_icmpge +19 -> 72
    //   56: aload 5
    //   58: astore_3
    //   59: aload 5
    //   61: invokevirtual 282	java/io/RandomAccessFile:readLine	()Ljava/lang/String;
    //   64: astore 4
    //   66: iinc 1 1
    //   69: goto -18 -> 51
    //   72: aload 5
    //   74: astore_3
    //   75: aload 4
    //   77: invokestatic 286	com/unity3d/splash/services/core/device/Device:getMemoryValueFromString	(Ljava/lang/String;)J
    //   80: lstore 6
    //   82: aload 5
    //   84: invokevirtual 287	java/io/RandomAccessFile:close	()V
    //   87: goto +11 -> 98
    //   90: astore_0
    //   91: ldc_w 289
    //   94: aload_0
    //   95: invokestatic 51	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   98: lload 6
    //   100: lreturn
    //   101: astore 4
    //   103: goto +12 -> 115
    //   106: astore_0
    //   107: goto +71 -> 178
    //   110: astore 4
    //   112: aconst_null
    //   113: astore 5
    //   115: aload 5
    //   117: astore_3
    //   118: new 291	java/lang/StringBuilder
    //   121: astore 8
    //   123: aload 5
    //   125: astore_3
    //   126: aload 8
    //   128: ldc_w 293
    //   131: invokespecial 294	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   134: aload 5
    //   136: astore_3
    //   137: aload 8
    //   139: aload_0
    //   140: invokevirtual 298	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   143: pop
    //   144: aload 5
    //   146: astore_3
    //   147: aload 8
    //   149: invokevirtual 301	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   152: aload 4
    //   154: invokestatic 51	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   157: aload 5
    //   159: invokevirtual 287	java/io/RandomAccessFile:close	()V
    //   162: goto +11 -> 173
    //   165: astore_0
    //   166: ldc_w 289
    //   169: aload_0
    //   170: invokestatic 51	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   173: ldc2_w 250
    //   176: lreturn
    //   177: astore_0
    //   178: aload_3
    //   179: invokevirtual 287	java/io/RandomAccessFile:close	()V
    //   182: goto +11 -> 193
    //   185: astore_3
    //   186: ldc_w 289
    //   189: aload_3
    //   190: invokestatic 51	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   193: aload_0
    //   194: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   82	87	90	java/io/IOException
    //   59	66	101	java/io/IOException
    //   75	82	101	java/io/IOException
    //   33	49	106	finally
    //   33	49	110	java/io/IOException
    //   157	162	165	java/io/IOException
    //   59	66	177	finally
    //   75	82	177	finally
    //   118	123	177	finally
    //   126	134	177	finally
    //   137	144	177	finally
    //   147	157	177	finally
    //   178	182	185	java/io/IOException
  }

  private static long getMemoryValueFromString(String paramString)
  {
    if (paramString != null)
    {
      Matcher localMatcher = Pattern.compile("(\\d+)").matcher(paramString);
      for (paramString = ""; localMatcher.find(); paramString = localMatcher.group(1));
      return Long.parseLong(paramString);
    }
    return -1L;
  }

  public static String getModel()
  {
    return Build.MODEL;
  }

  public static String getNetworkCountryISO()
  {
    if (ClientProperties.getApplicationContext() != null)
      return ((TelephonyManager)ClientProperties.getApplicationContext().getSystemService("phone")).getNetworkCountryIso();
    return "";
  }

  public static boolean getNetworkMetered()
  {
    if ((ClientProperties.getApplicationContext() != null) && (Build.VERSION.SDK_INT >= 16))
    {
      ConnectivityManager localConnectivityManager = (ConnectivityManager)ClientProperties.getApplicationContext().getSystemService("connectivity");
      if (localConnectivityManager == null)
        return false;
      return localConnectivityManager.isActiveNetworkMetered();
    }
    return false;
  }

  public static String getNetworkOperator()
  {
    if (ClientProperties.getApplicationContext() != null)
      return ((TelephonyManager)ClientProperties.getApplicationContext().getSystemService("phone")).getNetworkOperator();
    return "";
  }

  public static String getNetworkOperatorName()
  {
    if (ClientProperties.getApplicationContext() != null)
      return ((TelephonyManager)ClientProperties.getApplicationContext().getSystemService("phone")).getNetworkOperatorName();
    return "";
  }

  public static int getNetworkType()
  {
    if (ClientProperties.getApplicationContext() != null)
      return ((TelephonyManager)ClientProperties.getApplicationContext().getSystemService("phone")).getNetworkType();
    return -1;
  }

  private static ArrayList getNewAbiList()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.addAll(Arrays.asList(Build.SUPPORTED_ABIS));
    return localArrayList;
  }

  private static ArrayList getOldAbiList()
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(Build.CPU_ABI);
    localArrayList.add(Build.CPU_ABI2);
    return localArrayList;
  }

  public static String getOpenAdvertisingTrackingId()
  {
    return OpenAdvertisingId.getOpenAdvertisingTrackingId();
  }

  public static String getOsVersion()
  {
    return Build.VERSION.RELEASE;
  }

  // ERROR //
  public static java.util.Map getProcessInfo()
  {
    // Byte code:
    //   0: new 405	java/util/HashMap
    //   3: dup
    //   4: invokespecial 406	java/util/HashMap:<init>	()V
    //   7: astore_0
    //   8: aconst_null
    //   9: astore_1
    //   10: new 272	java/io/RandomAccessFile
    //   13: astore_2
    //   14: aload_2
    //   15: ldc_w 408
    //   18: ldc_w 276
    //   21: invokespecial 279	java/io/RandomAccessFile:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   24: aload_2
    //   25: astore_1
    //   26: aload_0
    //   27: ldc_w 410
    //   30: aload_2
    //   31: invokevirtual 282	java/io/RandomAccessFile:readLine	()Ljava/lang/String;
    //   34: invokevirtual 414	java/util/HashMap:put	(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   37: pop
    //   38: aload_2
    //   39: invokevirtual 287	java/io/RandomAccessFile:close	()V
    //   42: goto +38 -> 80
    //   45: astore_3
    //   46: goto +10 -> 56
    //   49: astore_2
    //   50: goto +33 -> 83
    //   53: astore_3
    //   54: aconst_null
    //   55: astore_2
    //   56: aload_2
    //   57: astore_1
    //   58: ldc_w 416
    //   61: aload_3
    //   62: invokestatic 51	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   65: aload_2
    //   66: invokevirtual 287	java/io/RandomAccessFile:close	()V
    //   69: goto +11 -> 80
    //   72: astore_1
    //   73: ldc_w 289
    //   76: aload_1
    //   77: invokestatic 51	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   80: aload_0
    //   81: areturn
    //   82: astore_2
    //   83: aload_1
    //   84: invokevirtual 287	java/io/RandomAccessFile:close	()V
    //   87: goto +11 -> 98
    //   90: astore_1
    //   91: ldc_w 289
    //   94: aload_1
    //   95: invokestatic 51	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   98: aload_2
    //   99: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   26	38	45	java/io/IOException
    //   10	24	49	finally
    //   10	24	53	java/io/IOException
    //   38	42	72	java/io/IOException
    //   65	69	72	java/io/IOException
    //   26	38	82	finally
    //   58	65	82	finally
    //   83	87	90	java/io/IOException
  }

  public static String getProduct()
  {
    return Build.PRODUCT;
  }

  public static int getRingerMode()
  {
    if (ClientProperties.getApplicationContext() != null)
    {
      AudioManager localAudioManager = (AudioManager)ClientProperties.getApplicationContext().getSystemService("audio");
      if (localAudioManager != null)
        return localAudioManager.getRingerMode();
      return -2;
    }
    return -1;
  }

  public static String getSIMMCC(Context paramContext)
  {
    paramContext = ((TelephonyManager)paramContext.getSystemService("phone")).getSimOperator();
    int i = 1;
    int j;
    if (paramContext != null)
      j = 1;
    else
      j = 0;
    if (paramContext.length() < 3)
      i = 0;
    if ((i & j) != 0)
      return paramContext.substring(0, 3);
    return "";
  }

  public static int getScreenBrightness()
  {
    if (ClientProperties.getApplicationContext() != null)
      return Settings.System.getInt(ClientProperties.getApplicationContext().getContentResolver(), "screen_brightness", -1);
    return -1;
  }

  public static int getScreenDensity()
  {
    if (ClientProperties.getApplicationContext() != null)
      return ClientProperties.getApplicationContext().getResources().getDisplayMetrics().densityDpi;
    return -1;
  }

  public static int getScreenHeight()
  {
    if (ClientProperties.getApplicationContext() != null)
      return ClientProperties.getApplicationContext().getResources().getDisplayMetrics().heightPixels;
    return -1;
  }

  public static int getScreenLayout()
  {
    if (ClientProperties.getApplicationContext() != null)
      return ClientProperties.getApplicationContext().getResources().getConfiguration().screenLayout;
    return -1;
  }

  public static int getScreenWidth()
  {
    if (ClientProperties.getApplicationContext() != null)
      return ClientProperties.getApplicationContext().getResources().getDisplayMetrics().widthPixels;
    return -1;
  }

  public static List getSensorList()
  {
    if (ClientProperties.getApplicationContext() != null)
      return ((SensorManager)ClientProperties.getApplicationContext().getSystemService("sensor")).getSensorList(-1);
    return null;
  }

  public static int getStreamMaxVolume(int paramInt)
  {
    if (ClientProperties.getApplicationContext() != null)
    {
      AudioManager localAudioManager = (AudioManager)ClientProperties.getApplicationContext().getSystemService("audio");
      if (localAudioManager != null)
        return localAudioManager.getStreamMaxVolume(paramInt);
      return -2;
    }
    return -1;
  }

  public static int getStreamVolume(int paramInt)
  {
    if (ClientProperties.getApplicationContext() != null)
    {
      AudioManager localAudioManager = (AudioManager)ClientProperties.getApplicationContext().getSystemService("audio");
      if (localAudioManager != null)
        return localAudioManager.getStreamVolume(paramInt);
      return -2;
    }
    return -1;
  }

  public static ArrayList getSupportedAbis()
  {
    if (getApiLevel() < 21)
      return getOldAbiList();
    return getNewAbiList();
  }

  public static String getSystemProperty(String paramString1, String paramString2)
  {
    if (paramString2 != null)
      return System.getProperty(paramString1, paramString2);
    return System.getProperty(paramString1);
  }

  public static long getTotalMemory()
  {
    return getMemoryInfo(MemoryInfoType.TOTAL_MEMORY);
  }

  public static long getTotalSpace(File paramFile)
  {
    if ((paramFile != null) && (paramFile.exists()))
      return Math.round((float)(paramFile.getTotalSpace() / 1024L));
    return -1L;
  }

  public static String getUniqueEventId()
  {
    return UUID.randomUUID().toString();
  }

  public static long getUptime()
  {
    return SystemClock.uptimeMillis();
  }

  public static boolean isActiveNetworkConnected()
  {
    if (ClientProperties.getApplicationContext() != null)
    {
      Object localObject = (ConnectivityManager)ClientProperties.getApplicationContext().getSystemService("connectivity");
      if (localObject != null)
      {
        localObject = ((ConnectivityManager)localObject).getActiveNetworkInfo();
        if ((localObject != null) && (((NetworkInfo)localObject).isConnected()))
          return true;
      }
    }
    return false;
  }

  public static Boolean isAdbEnabled()
  {
    Boolean localBoolean;
    if (getApiLevel() < 17)
      localBoolean = oldAdbStatus();
    else
      localBoolean = newAdbStatus();
    return localBoolean;
  }

  public static boolean isAppInstalled(String paramString)
  {
    Object localObject;
    if (ClientProperties.getApplicationContext() != null)
      localObject = ClientProperties.getApplicationContext().getPackageManager();
    try
    {
      localObject = ((PackageManager)localObject).getPackageInfo(paramString, 0);
      if ((localObject != null) && (((PackageInfo)localObject).packageName != null))
      {
        boolean bool = paramString.equals(((PackageInfo)localObject).packageName);
        if (bool)
          return true;
      }
      label46: return false;
    }
    catch (android.content.pm.PackageManager.NameNotFoundException paramString)
    {
      break label46;
    }
  }

  public static boolean isLimitAdTrackingEnabled()
  {
    return AdvertisingId.getLimitedAdTracking();
  }

  public static boolean isLimitOpenAdTrackingEnabled()
  {
    return OpenAdvertisingId.getLimitedOpenAdTracking();
  }

  public static boolean isRooted()
  {
    try
    {
      boolean bool = searchPathForBinary("su");
      return bool;
    }
    catch (Exception localException)
    {
      DeviceLog.exception("Rooted check failed", localException);
    }
    return false;
  }

  public static boolean isUSBConnected()
  {
    if (ClientProperties.getApplicationContext() != null)
    {
      Intent localIntent = ClientProperties.getApplicationContext().registerReceiver(null, new IntentFilter("android.hardware.usb.action.USB_STATE"));
      if (localIntent != null)
        return localIntent.getBooleanExtra("connected", false);
    }
    return false;
  }

  public static boolean isUsingWifi()
  {
    if (ClientProperties.getApplicationContext() != null)
    {
      ConnectivityManager localConnectivityManager = (ConnectivityManager)ClientProperties.getApplicationContext().getSystemService("connectivity");
      if (localConnectivityManager == null)
        return false;
      TelephonyManager localTelephonyManager = (TelephonyManager)ClientProperties.getApplicationContext().getSystemService("phone");
      NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
      if ((localNetworkInfo != null) && (localConnectivityManager.getBackgroundDataSetting()) && (localConnectivityManager.getActiveNetworkInfo().isConnected()) && (localTelephonyManager != null) && (localNetworkInfo.getType() == 1) && (localNetworkInfo.isConnected()))
        return true;
    }
    return false;
  }

  public static boolean isWiredHeadsetOn()
  {
    if (ClientProperties.getApplicationContext() != null)
      return ((AudioManager)ClientProperties.getApplicationContext().getSystemService("audio")).isWiredHeadsetOn();
    return false;
  }

  private static Boolean newAdbStatus()
  {
    Object localObject2;
    try
    {
      Object localObject1 = ClientProperties.getApplicationContext().getContentResolver();
      boolean bool = false;
      if (1 == Settings.Global.getInt((ContentResolver)localObject1, "adb_enabled", 0))
        bool = true;
      localObject1 = Boolean.valueOf(bool);
    }
    catch (Exception localObject2)
    {
      DeviceLog.exception("Problems fetching adb enabled status", localException);
      localObject2 = null;
    }
    return (Boolean)localObject2;
  }

  private static Boolean oldAdbStatus()
  {
    Object localObject2;
    try
    {
      Object localObject1 = ClientProperties.getApplicationContext().getContentResolver();
      boolean bool = false;
      if (1 == Settings.Secure.getInt((ContentResolver)localObject1, "adb_enabled", 0))
        bool = true;
      localObject1 = Boolean.valueOf(bool);
    }
    catch (Exception localObject2)
    {
      DeviceLog.exception("Problems fetching adb enabled status", localException);
      localObject2 = null;
    }
    return (Boolean)localObject2;
  }

  private static boolean searchPathForBinary(String paramString)
  {
    String[] arrayOfString = System.getenv("PATH").split(":");
    int i = arrayOfString.length;
    for (int j = 0; j < i; j++)
    {
      Object localObject = new File(arrayOfString[j]);
      if ((!((File)localObject).exists()) || (!((File)localObject).isDirectory()))
        continue;
      localObject = ((File)localObject).listFiles();
      if (localObject == null)
        continue;
      int k = localObject.length;
      for (int m = 0; m < k; m++)
        if (localObject[m].getName().equals(paramString))
          return true;
    }
    return false;
  }

  public static enum MemoryInfoType
  {
    static
    {
      MemoryInfoType localMemoryInfoType = new MemoryInfoType("FREE_MEMORY", 1);
      FREE_MEMORY = localMemoryInfoType;
      $VALUES = new MemoryInfoType[] { TOTAL_MEMORY, localMemoryInfoType };
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.device.Device
 * JD-Core Version:    0.6.0
 */