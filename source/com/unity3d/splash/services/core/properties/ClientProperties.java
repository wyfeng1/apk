package com.unity3d.splash.services.core.properties;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;

public class ClientProperties
{
  private static final X500Principal DEBUG_CERT = new X500Principal("CN=Android Debug,O=Android,C=US");
  private static WeakReference _activity;
  private static Application _application;
  private static Context _applicationContext;
  private static String _gameId;

  public static Activity getActivity()
  {
    return (Activity)_activity.get();
  }

  public static String getAppName()
  {
    return _applicationContext.getPackageName();
  }

  public static String getAppVersion()
  {
    String str = getApplicationContext().getPackageName();
    PackageManager localPackageManager = getApplicationContext().getPackageManager();
    try
    {
      str = localPackageManager.getPackageInfo(str, 0).versionName;
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      DeviceLog.exception("Error getting package info", localNameNotFoundException);
    }
    return null;
  }

  public static Application getApplication()
  {
    return _application;
  }

  public static Context getApplicationContext()
  {
    return _applicationContext;
  }

  public static String getGameId()
  {
    return _gameId;
  }

  public static boolean isAppDebuggable()
  {
    Object localObject1 = getApplicationContext();
    int i = 0;
    if (localObject1 != null)
    {
      Object localObject2 = getApplicationContext().getPackageManager();
      localObject1 = getApplicationContext().getPackageName();
      int j = 1;
      boolean bool1 = true;
      int k;
      try
      {
        ApplicationInfo localApplicationInfo = ((PackageManager)localObject2).getApplicationInfo((String)localObject1, 0);
        k = localApplicationInfo.flags & 0x2;
        localApplicationInfo.flags = k;
        if (k == 0)
          bool1 = false;
        j = 0;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException2)
      {
        DeviceLog.exception("Could not find name", localNameNotFoundException2);
        bool1 = false;
      }
      boolean bool2 = bool1;
      if (j != 0)
      {
        boolean bool3 = bool1;
        bool2 = bool1;
        try
        {
          localObject1 = ((PackageManager)localObject2).getPackageInfo((String)localObject1, 64).signatures;
          bool3 = bool1;
          bool2 = bool1;
          k = localObject1.length;
          for (j = i; ; j++)
          {
            bool2 = bool1;
            if (j >= k)
              break;
            Object localObject3 = localObject1[j];
            bool3 = bool1;
            bool2 = bool1;
            CertificateFactory localCertificateFactory = CertificateFactory.getInstance("X.509");
            bool3 = bool1;
            bool2 = bool1;
            localObject2 = new java/io/ByteArrayInputStream;
            bool3 = bool1;
            bool2 = bool1;
            ((ByteArrayInputStream)localObject2).<init>(localObject3.toByteArray());
            bool3 = bool1;
            bool2 = bool1;
            bool1 = ((X509Certificate)localCertificateFactory.generateCertificate((InputStream)localObject2)).getSubjectX500Principal().equals(DEBUG_CERT);
            bool2 = bool1;
            if (bool1)
              break;
          }
        }
        catch (CertificateException localCertificateException)
        {
          DeviceLog.exception("Certificate exception", localCertificateException);
          bool2 = bool3;
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException1)
        {
          DeviceLog.exception("Could not find name", localNameNotFoundException1);
        }
      }
      return bool2;
    }
    return false;
  }

  public static void setActivity(Activity paramActivity)
  {
    _activity = new WeakReference(paramActivity);
  }

  public static void setApplication(Application paramApplication)
  {
    _application = paramApplication;
  }

  public static void setApplicationContext(Context paramContext)
  {
    _applicationContext = paramContext;
  }

  public static void setGameId(String paramString)
  {
    _gameId = paramString;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.properties.ClientProperties
 * JD-Core Version:    0.6.0
 */