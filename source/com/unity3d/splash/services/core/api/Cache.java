package com.unity3d.splash.services.core.api;

import android.media.MediaMetadataRetriever;
import android.util.Base64;
import android.util.SparseArray;
import com.unity3d.splash.services.core.cache.CacheDirectory;
import com.unity3d.splash.services.core.cache.CacheDirectoryType;
import com.unity3d.splash.services.core.cache.CacheError;
import com.unity3d.splash.services.core.cache.CacheThread;
import com.unity3d.splash.services.core.device.Device;
import com.unity3d.splash.services.core.log.DeviceLog;
import com.unity3d.splash.services.core.misc.Utilities;
import com.unity3d.splash.services.core.properties.ClientProperties;
import com.unity3d.splash.services.core.properties.SdkProperties;
import com.unity3d.splash.services.core.request.WebRequestError;
import com.unity3d.splash.services.core.webview.bridge.WebViewCallback;
import com.unity3d.splash.services.core.webview.bridge.WebViewExposed;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Cache
{
  @WebViewExposed
  public static void deleteFile(String paramString, WebViewCallback paramWebViewCallback)
  {
    if (new File(fileIdToFilename(paramString)).delete())
    {
      paramWebViewCallback.invoke(new Object[0]);
      return;
    }
    paramWebViewCallback.error(CacheError.FILE_IO_ERROR, new Object[0]);
  }

  @WebViewExposed
  public static void download(String paramString1, String paramString2, JSONArray paramJSONArray, Boolean paramBoolean, WebViewCallback paramWebViewCallback)
  {
    if (CacheThread.isActive())
    {
      paramWebViewCallback.error(CacheError.FILE_ALREADY_CACHING, new Object[0]);
      return;
    }
    if (!Device.isActiveNetworkConnected())
    {
      paramWebViewCallback.error(CacheError.NO_INTERNET, new Object[0]);
      return;
    }
    try
    {
      paramJSONArray = Request.getHeadersMap(paramJSONArray);
      CacheThread.download(paramString1, fileIdToFilename(paramString2), paramJSONArray, paramBoolean.booleanValue());
      paramWebViewCallback.invoke(new Object[0]);
      return;
    }
    catch (java.lang.Exception paramJSONArray)
    {
      DeviceLog.exception("Error mapping headers for the request", paramJSONArray);
      paramWebViewCallback.error(WebRequestError.MAPPING_HEADERS_FAILED, new Object[] { paramString1, paramString2 });
    }
  }

  private static String fileIdToFilename(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(SdkProperties.getCacheDirectory());
    localStringBuilder.append("/");
    localStringBuilder.append(SdkProperties.getCacheFilePrefix());
    localStringBuilder.append(paramString);
    return localStringBuilder.toString();
  }

  @WebViewExposed
  public static void getCacheDirectoryExists(WebViewCallback paramWebViewCallback)
  {
    File localFile = SdkProperties.getCacheDirectory();
    if (localFile == null)
    {
      paramWebViewCallback.error(CacheError.CACHE_DIRECTORY_NULL, new Object[0]);
      return;
    }
    paramWebViewCallback.invoke(new Object[] { Boolean.valueOf(localFile.exists()) });
  }

  @WebViewExposed
  public static void getCacheDirectoryType(WebViewCallback paramWebViewCallback)
  {
    Object localObject = SdkProperties.getCacheDirectoryObject();
    if ((localObject != null) && (((CacheDirectory)localObject).getCacheDirectory(ClientProperties.getApplicationContext()) != null))
    {
      if (!((CacheDirectory)localObject).getCacheDirectory(ClientProperties.getApplicationContext()).exists())
      {
        paramWebViewCallback.error(CacheError.CACHE_DIRECTORY_DOESNT_EXIST, new Object[0]);
        return;
      }
      localObject = ((CacheDirectory)localObject).getType();
      if (localObject == null)
      {
        paramWebViewCallback.error(CacheError.CACHE_DIRECTORY_TYPE_NULL, new Object[0]);
        return;
      }
      paramWebViewCallback.invoke(new Object[] { ((CacheDirectoryType)localObject).name() });
      return;
    }
    paramWebViewCallback.error(CacheError.CACHE_DIRECTORY_NULL, new Object[0]);
  }

  @WebViewExposed
  public static void getFileContent(String paramString1, String paramString2, WebViewCallback paramWebViewCallback)
  {
    String str = fileIdToFilename(paramString1);
    Object localObject = new File(str);
    if (!((File)localObject).exists())
    {
      paramWebViewCallback.error(CacheError.FILE_NOT_FOUND, new Object[] { paramString1, str });
      return;
    }
    try
    {
      localObject = Utilities.readFileBytes((File)localObject);
      if (paramString2 == null)
      {
        paramWebViewCallback.error(CacheError.UNSUPPORTED_ENCODING, new Object[] { paramString1, str, paramString2 });
        return;
      }
      if (paramString2.equals("UTF-8"))
      {
        paramString1 = ByteBuffer.wrap(localObject);
        paramString1 = Charset.forName("UTF-8").decode(paramString1).toString();
      }
      else
      {
        if (!paramString2.equals("Base64"))
          break label138;
        paramString1 = Base64.encodeToString(localObject, 2);
      }
      paramWebViewCallback.invoke(new Object[] { paramString1 });
      return;
      label138: paramWebViewCallback.error(CacheError.UNSUPPORTED_ENCODING, new Object[] { paramString1, str, paramString2 });
      return;
    }
    catch (IOException paramString2)
    {
      CacheError localCacheError = CacheError.FILE_IO_ERROR;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramString2.getMessage());
      ((StringBuilder)localObject).append(", ");
      ((StringBuilder)localObject).append(paramString2.getClass().getName());
      paramWebViewCallback.error(localCacheError, new Object[] { paramString1, str, ((StringBuilder)localObject).toString() });
    }
  }

  @WebViewExposed
  public static void getFileInfo(String paramString, WebViewCallback paramWebViewCallback)
  {
    try
    {
      paramWebViewCallback.invoke(new Object[] { getFileJson(paramString) });
      return;
    }
    catch (JSONException paramString)
    {
      DeviceLog.exception("Error creating JSON", paramString);
      paramWebViewCallback.error(CacheError.JSON_ERROR, new Object[0]);
    }
  }

  private static JSONObject getFileJson(String paramString)
  {
    JSONObject localJSONObject = new JSONObject();
    localJSONObject.put("id", paramString);
    paramString = new File(fileIdToFilename(paramString));
    if (paramString.exists())
    {
      localJSONObject.put("found", true);
      localJSONObject.put("size", paramString.length());
      localJSONObject.put("mtime", paramString.lastModified());
    }
    else
    {
      localJSONObject.put("found", false);
    }
    return localJSONObject;
  }

  @WebViewExposed
  public static void getFilePath(String paramString, WebViewCallback paramWebViewCallback)
  {
    if (new File(fileIdToFilename(paramString)).exists())
    {
      paramWebViewCallback.invoke(new Object[] { fileIdToFilename(paramString) });
      return;
    }
    paramWebViewCallback.error(CacheError.FILE_NOT_FOUND, new Object[0]);
  }

  @WebViewExposed
  public static void getFiles(WebViewCallback paramWebViewCallback)
  {
    Object localObject1 = SdkProperties.getCacheDirectory();
    if (localObject1 == null)
      return;
    DeviceLog.debug("Unity Ads cache: checking app directory for Unity Ads cached files");
    localObject1 = ((File)localObject1).listFiles(new FilenameFilter()
    {
      public final boolean accept(File paramFile, String paramString)
      {
        return paramString.startsWith(SdkProperties.getCacheFilePrefix());
      }
    });
    if ((localObject1 == null) || (localObject1.length == 0))
      paramWebViewCallback.invoke(new Object[] { new JSONArray() });
    try
    {
      JSONArray localJSONArray = new org/json/JSONArray;
      localJSONArray.<init>();
      int i = localObject1.length;
      for (int j = 0; j < i; j++)
      {
        Object localObject2 = localObject1[j];
        String str = localObject2.getName().substring(SdkProperties.getCacheFilePrefix().length());
        StringBuilder localStringBuilder = new java/lang/StringBuilder;
        localStringBuilder.<init>("Unity Ads cache: found ");
        localStringBuilder.append(str);
        localStringBuilder.append(", ");
        localStringBuilder.append(localObject2.length());
        localStringBuilder.append(" bytes");
        DeviceLog.debug(localStringBuilder.toString());
        localJSONArray.put(getFileJson(str));
      }
      paramWebViewCallback.invoke(new Object[] { localJSONArray });
      return;
    }
    catch (JSONException localJSONException)
    {
      DeviceLog.exception("Error creating JSON", localJSONException);
      paramWebViewCallback.error(CacheError.JSON_ERROR, new Object[0]);
    }
  }

  @WebViewExposed
  public static void getFreeSpace(WebViewCallback paramWebViewCallback)
  {
    paramWebViewCallback.invoke(new Object[] { Long.valueOf(Device.getFreeSpace(SdkProperties.getCacheDirectory())) });
  }

  @WebViewExposed
  public static void getHash(String paramString, WebViewCallback paramWebViewCallback)
  {
    paramWebViewCallback.invoke(new Object[] { Utilities.Sha256(paramString) });
  }

  private static SparseArray getMetaData(String paramString, JSONArray paramJSONArray)
  {
    Object localObject = new File(paramString);
    SparseArray localSparseArray = new SparseArray();
    if (((File)localObject).exists())
    {
      paramString = new MediaMetadataRetriever();
      paramString.setDataSource(((File)localObject).getAbsolutePath());
      for (int i = 0; i < paramJSONArray.length(); i++)
      {
        int j = paramJSONArray.getInt(i);
        localObject = paramString.extractMetadata(j);
        if (localObject == null)
          continue;
        localSparseArray.put(j, localObject);
      }
      return localSparseArray;
    }
    paramString = new StringBuilder("File: ");
    paramString.append(((File)localObject).getAbsolutePath());
    paramString.append(" doesn't exist");
    throw new IOException(paramString.toString());
  }

  @WebViewExposed
  public static void getMetaData(String paramString, JSONArray paramJSONArray, WebViewCallback paramWebViewCallback)
  {
    paramString = fileIdToFilename(paramString);
    try
    {
      paramString = getMetaData(paramString, paramJSONArray);
      JSONArray localJSONArray = new JSONArray();
      for (int i = 0; i < paramString.size(); i++)
      {
        paramJSONArray = new JSONArray();
        paramJSONArray.put(paramString.keyAt(i));
        paramJSONArray.put(paramString.valueAt(i));
        localJSONArray.put(paramJSONArray);
      }
      paramWebViewCallback.invoke(new Object[] { localJSONArray });
      return;
    }
    catch (IOException paramString)
    {
      paramWebViewCallback.error(CacheError.FILE_IO_ERROR, new Object[] { paramString.getMessage() });
      return;
    }
    catch (RuntimeException paramString)
    {
      paramWebViewCallback.error(CacheError.INVALID_ARGUMENT, new Object[] { paramString.getMessage() });
      return;
    }
    catch (JSONException paramString)
    {
      paramWebViewCallback.error(CacheError.JSON_ERROR, new Object[] { paramString.getMessage() });
    }
  }

  @WebViewExposed
  public static void getProgressInterval(WebViewCallback paramWebViewCallback)
  {
    paramWebViewCallback.invoke(new Object[] { Integer.valueOf(CacheThread.getProgressInterval()) });
  }

  @WebViewExposed
  public static void getTimeouts(WebViewCallback paramWebViewCallback)
  {
    paramWebViewCallback.invoke(new Object[] { Integer.valueOf(CacheThread.getConnectTimeout()), Integer.valueOf(CacheThread.getReadTimeout()) });
  }

  @WebViewExposed
  public static void getTotalSpace(WebViewCallback paramWebViewCallback)
  {
    paramWebViewCallback.invoke(new Object[] { Long.valueOf(Device.getTotalSpace(SdkProperties.getCacheDirectory())) });
  }

  @WebViewExposed
  public static void isCaching(WebViewCallback paramWebViewCallback)
  {
    paramWebViewCallback.invoke(new Object[] { Boolean.valueOf(CacheThread.isActive()) });
  }

  @WebViewExposed
  public static void recreateCacheDirectory(WebViewCallback paramWebViewCallback)
  {
    if (SdkProperties.getCacheDirectory().exists())
    {
      paramWebViewCallback.error(CacheError.CACHE_DIRECTORY_EXISTS, new Object[0]);
      return;
    }
    SdkProperties.setCacheDirectory(null);
    if (SdkProperties.getCacheDirectory() == null)
    {
      paramWebViewCallback.error(CacheError.CACHE_DIRECTORY_NULL, new Object[0]);
      return;
    }
    paramWebViewCallback.invoke(new Object[0]);
  }

  // ERROR //
  @WebViewExposed
  public static void setFileContent(String paramString1, String paramString2, String paramString3, WebViewCallback paramWebViewCallback)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 20	com/unity3d/splash/services/core/api/Cache:fileIdToFilename	(Ljava/lang/String;)Ljava/lang/String;
    //   4: astore 4
    //   6: iconst_1
    //   7: istore 5
    //   9: aload_2
    //   10: ldc 178
    //   12: invokevirtual 401	java/lang/String:getBytes	(Ljava/lang/String;)[B
    //   15: astore 6
    //   17: aload 6
    //   19: astore 7
    //   21: aload_1
    //   22: ifnull +71 -> 93
    //   25: aload 6
    //   27: astore 7
    //   29: aload_1
    //   30: invokevirtual 284	java/lang/String:length	()I
    //   33: ifle +60 -> 93
    //   36: aload_1
    //   37: ldc 205
    //   39: invokevirtual 184	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   42: ifeq +13 -> 55
    //   45: aload_2
    //   46: iconst_2
    //   47: invokestatic 404	android/util/Base64:decode	(Ljava/lang/String;I)[B
    //   50: astore 7
    //   52: goto +41 -> 93
    //   55: aload 6
    //   57: astore 7
    //   59: aload_1
    //   60: ldc 178
    //   62: invokevirtual 184	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   65: ifne +28 -> 93
    //   68: aload_3
    //   69: getstatic 176	com/unity3d/splash/services/core/cache/CacheError:UNSUPPORTED_ENCODING	Lcom/unity3d/splash/services/core/cache/CacheError;
    //   72: iconst_3
    //   73: anewarray 4	java/lang/Object
    //   76: dup
    //   77: iconst_0
    //   78: aload_0
    //   79: aastore
    //   80: dup
    //   81: iconst_1
    //   82: aload 4
    //   84: aastore
    //   85: dup
    //   86: iconst_2
    //   87: aload_1
    //   88: aastore
    //   89: invokevirtual 43	com/unity3d/splash/services/core/webview/bridge/WebViewCallback:error	(Ljava/lang/Enum;[Ljava/lang/Object;)V
    //   92: return
    //   93: aconst_null
    //   94: astore 8
    //   96: aconst_null
    //   97: astore 9
    //   99: aconst_null
    //   100: astore 10
    //   102: aload 10
    //   104: astore_2
    //   105: new 406	java/io/FileOutputStream
    //   108: astore 6
    //   110: aload 10
    //   112: astore_2
    //   113: aload 6
    //   115: aload 4
    //   117: invokespecial 407	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   120: aload 6
    //   122: aload 7
    //   124: invokevirtual 411	java/io/FileOutputStream:write	([B)V
    //   127: aload 6
    //   129: invokevirtual 414	java/io/FileOutputStream:flush	()V
    //   132: aload 6
    //   134: invokevirtual 417	java/io/FileOutputStream:close	()V
    //   137: goto +124 -> 261
    //   140: astore_0
    //   141: ldc_w 419
    //   144: aload_0
    //   145: invokestatic 86	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   148: goto +113 -> 261
    //   151: astore_0
    //   152: aload 6
    //   154: astore_2
    //   155: goto +120 -> 275
    //   158: astore_2
    //   159: goto +11 -> 170
    //   162: astore_2
    //   163: goto +47 -> 210
    //   166: astore_0
    //   167: goto +108 -> 275
    //   170: aload 6
    //   172: astore_2
    //   173: aload_3
    //   174: getstatic 39	com/unity3d/splash/services/core/cache/CacheError:FILE_IO_ERROR	Lcom/unity3d/splash/services/core/cache/CacheError;
    //   177: iconst_3
    //   178: anewarray 4	java/lang/Object
    //   181: dup
    //   182: iconst_0
    //   183: aload_0
    //   184: aastore
    //   185: dup
    //   186: iconst_1
    //   187: aload 4
    //   189: aastore
    //   190: dup
    //   191: iconst_2
    //   192: aload_1
    //   193: aastore
    //   194: invokevirtual 43	com/unity3d/splash/services/core/webview/bridge/WebViewCallback:error	(Ljava/lang/Enum;[Ljava/lang/Object;)V
    //   197: aload 6
    //   199: ifnull +59 -> 258
    //   202: aload 6
    //   204: invokevirtual 417	java/io/FileOutputStream:close	()V
    //   207: goto +51 -> 258
    //   210: aload 6
    //   212: astore_2
    //   213: aload_3
    //   214: getstatic 167	com/unity3d/splash/services/core/cache/CacheError:FILE_NOT_FOUND	Lcom/unity3d/splash/services/core/cache/CacheError;
    //   217: iconst_3
    //   218: anewarray 4	java/lang/Object
    //   221: dup
    //   222: iconst_0
    //   223: aload_0
    //   224: aastore
    //   225: dup
    //   226: iconst_1
    //   227: aload 4
    //   229: aastore
    //   230: dup
    //   231: iconst_2
    //   232: aload_1
    //   233: aastore
    //   234: invokevirtual 43	com/unity3d/splash/services/core/webview/bridge/WebViewCallback:error	(Ljava/lang/Enum;[Ljava/lang/Object;)V
    //   237: aload 6
    //   239: ifnull +19 -> 258
    //   242: aload 6
    //   244: invokevirtual 417	java/io/FileOutputStream:close	()V
    //   247: goto +11 -> 258
    //   250: astore_0
    //   251: ldc_w 419
    //   254: aload_0
    //   255: invokestatic 86	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   258: iconst_0
    //   259: istore 5
    //   261: iload 5
    //   263: ifeq +11 -> 274
    //   266: aload_3
    //   267: iconst_0
    //   268: anewarray 4	java/lang/Object
    //   271: invokevirtual 33	com/unity3d/splash/services/core/webview/bridge/WebViewCallback:invoke	([Ljava/lang/Object;)V
    //   274: return
    //   275: aload_2
    //   276: ifnull +18 -> 294
    //   279: aload_2
    //   280: invokevirtual 417	java/io/FileOutputStream:close	()V
    //   283: goto +11 -> 294
    //   286: astore_1
    //   287: ldc_w 419
    //   290: aload_1
    //   291: invokestatic 86	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   294: aload_0
    //   295: athrow
    //   296: astore_2
    //   297: aload_3
    //   298: getstatic 176	com/unity3d/splash/services/core/cache/CacheError:UNSUPPORTED_ENCODING	Lcom/unity3d/splash/services/core/cache/CacheError;
    //   301: iconst_3
    //   302: anewarray 4	java/lang/Object
    //   305: dup
    //   306: iconst_0
    //   307: aload_0
    //   308: aastore
    //   309: dup
    //   310: iconst_1
    //   311: aload 4
    //   313: aastore
    //   314: dup
    //   315: iconst_2
    //   316: aload_1
    //   317: aastore
    //   318: invokevirtual 43	com/unity3d/splash/services/core/webview/bridge/WebViewCallback:error	(Ljava/lang/Enum;[Ljava/lang/Object;)V
    //   321: return
    //   322: astore_2
    //   323: aload 9
    //   325: astore 6
    //   327: goto -117 -> 210
    //   330: astore_2
    //   331: aload 8
    //   333: astore 6
    //   335: goto -165 -> 170
    //
    // Exception table:
    //   from	to	target	type
    //   132	137	140	java/lang/Exception
    //   120	132	151	finally
    //   120	132	158	java/io/IOException
    //   120	132	162	java/io/FileNotFoundException
    //   105	110	166	finally
    //   113	120	166	finally
    //   173	197	166	finally
    //   213	237	166	finally
    //   202	207	250	java/lang/Exception
    //   242	247	250	java/lang/Exception
    //   279	283	286	java/lang/Exception
    //   9	17	296	java/io/UnsupportedEncodingException
    //   105	110	322	java/io/FileNotFoundException
    //   113	120	322	java/io/FileNotFoundException
    //   105	110	330	java/io/IOException
    //   113	120	330	java/io/IOException
  }

  @WebViewExposed
  public static void setProgressInterval(Integer paramInteger, WebViewCallback paramWebViewCallback)
  {
    CacheThread.setProgressInterval(paramInteger.intValue());
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void setTimeouts(Integer paramInteger1, Integer paramInteger2, WebViewCallback paramWebViewCallback)
  {
    CacheThread.setConnectTimeout(paramInteger1.intValue());
    CacheThread.setReadTimeout(paramInteger2.intValue());
    paramWebViewCallback.invoke(new Object[0]);
  }

  @WebViewExposed
  public static void stop(WebViewCallback paramWebViewCallback)
  {
    if (!CacheThread.isActive())
    {
      paramWebViewCallback.error(CacheError.NOT_CACHING, new Object[0]);
      return;
    }
    CacheThread.cancel();
    paramWebViewCallback.invoke(new Object[0]);
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.api.Cache
 * JD-Core Version:    0.6.0
 */