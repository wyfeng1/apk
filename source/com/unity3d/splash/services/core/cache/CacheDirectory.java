package com.unity3d.splash.services.core.cache;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Environment;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class CacheDirectory
{
  private static final String TEST_FILE_NAME = "UnityAdsTest.txt";
  private String _cacheDirName;
  private File _cacheDirectory = null;
  private boolean _initialized = false;
  private CacheDirectoryType _type = null;

  public CacheDirectory(String paramString)
  {
    this._cacheDirName = paramString;
  }

  private void createNoMediaFile(File paramFile)
  {
    paramFile = new File(paramFile, ".nomedia");
    try
    {
      if (paramFile.createNewFile())
      {
        DeviceLog.debug("Successfully created .nomedia file");
        return;
      }
      DeviceLog.debug("Using existing .nomedia file");
      return;
    }
    catch (Exception paramFile)
    {
      DeviceLog.exception("Failed to create .nomedia file", paramFile);
    }
  }

  public File createCacheDirectory(File paramFile, String paramString)
  {
    if (paramFile == null)
      return null;
    paramFile = new File(paramFile, paramString);
    paramFile.mkdirs();
    if (paramFile.isDirectory())
      return paramFile;
    return null;
  }

  public File getCacheDirectory(Context paramContext)
  {
    if (this._initialized)
      return this._cacheDirectory;
    this._initialized = true;
    File localFile2;
    if (Build.VERSION.SDK_INT > 18)
      if ("mounted".equals(Environment.getExternalStorageState()))
      {
        try
        {
          File localFile1 = createCacheDirectory(paramContext.getExternalCacheDir(), this._cacheDirName);
        }
        catch (Exception localFile2)
        {
          DeviceLog.exception("Creating external cache directory failed", localException);
          localFile2 = null;
        }
        if (!testCacheDirectory(localFile2))
          break label124;
        createNoMediaFile(localFile2);
        this._cacheDirectory = localFile2;
        this._type = CacheDirectoryType.EXTERNAL;
        paramContext = new StringBuilder("Unity Ads is using external cache directory: ");
        paramContext.append(localFile2.getAbsolutePath());
      }
    for (paramContext = paramContext.toString(); ; paramContext = paramContext.toString())
    {
      DeviceLog.debug(paramContext);
      return this._cacheDirectory;
      DeviceLog.debug("External media not mounted");
      label124: localFile2 = paramContext.getFilesDir();
      if (!testCacheDirectory(localFile2))
        break;
      this._cacheDirectory = localFile2;
      this._type = CacheDirectoryType.INTERNAL;
      paramContext = new StringBuilder("Unity Ads is using internal cache directory: ");
      paramContext.append(localFile2.getAbsolutePath());
    }
    DeviceLog.error("Unity Ads failed to initialize cache directory");
    return null;
  }

  public CacheDirectoryType getType()
  {
    return this._type;
  }

  public boolean testCacheDirectory(File paramFile)
  {
    if ((paramFile != null) && (paramFile.isDirectory()))
      try
      {
        Object localObject1 = "test".getBytes("UTF-8");
        int i = localObject1.length;
        localObject2 = new byte[i];
        Object localObject3 = new java/io/File;
        ((File)localObject3).<init>(paramFile, "UnityAdsTest.txt");
        FileOutputStream localFileOutputStream = new java/io/FileOutputStream;
        localFileOutputStream.<init>((File)localObject3);
        localFileOutputStream.write(localObject1);
        localFileOutputStream.flush();
        localFileOutputStream.close();
        localObject1 = new java/io/FileInputStream;
        ((FileInputStream)localObject1).<init>((File)localObject3);
        int j = ((FileInputStream)localObject1).read(localObject2, 0, i);
        ((FileInputStream)localObject1).close();
        if (!((File)localObject3).delete())
        {
          localObject2 = new java/lang/StringBuilder;
          ((StringBuilder)localObject2).<init>("Failed to delete testfile ");
          ((StringBuilder)localObject2).append(((File)localObject3).getAbsoluteFile());
          DeviceLog.debug(((StringBuilder)localObject2).toString());
          return false;
        }
        if (j != i)
        {
          DeviceLog.debug("Read buffer size mismatch");
          return false;
        }
        localObject3 = new java/lang/String;
        ((String)localObject3).<init>(localObject2, "UTF-8");
        if (((String)localObject3).equals("test"))
          return true;
        DeviceLog.debug("Read buffer content mismatch");
        return false;
      }
      catch (Exception localException)
      {
        Object localObject2 = new StringBuilder("Unity Ads exception while testing cache directory ");
        ((StringBuilder)localObject2).append(paramFile.getAbsolutePath());
        ((StringBuilder)localObject2).append(": ");
        ((StringBuilder)localObject2).append(localException.getMessage());
        DeviceLog.debug(((StringBuilder)localObject2).toString());
      }
    return false;
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.cache.CacheDirectory
 * JD-Core Version:    0.6.0
 */