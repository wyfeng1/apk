package com.unity3d.splash.services.core.misc;

import android.os.Handler;
import android.os.Looper;
import com.unity3d.splash.services.core.log.DeviceLog;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Iterator;
import org.json.JSONObject;

public class Utilities
{
  public static String Sha256(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-256");
      byte[] arrayOfByte = new byte[4096];
      while (true)
      {
        int i = paramInputStream.read(arrayOfByte);
        if (i == -1)
          break;
        localMessageDigest.update(arrayOfByte, 0, i);
      }
      return toHexString(localMessageDigest.digest());
    }
    catch (java.security.NoSuchAlgorithmException paramInputStream)
    {
      DeviceLog.exception("SHA-256 algorithm not found", paramInputStream);
    }
    return null;
  }

  public static String Sha256(String paramString)
  {
    return Sha256(paramString.getBytes());
  }

  public static String Sha256(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-256");
      localMessageDigest.update(paramArrayOfByte, 0, paramArrayOfByte.length);
      return toHexString(localMessageDigest.digest());
    }
    catch (java.security.NoSuchAlgorithmException paramArrayOfByte)
    {
      DeviceLog.exception("SHA-256 algorithm not found", paramArrayOfByte);
    }
    return null;
  }

  public static JSONObject mergeJsonObjects(JSONObject paramJSONObject1, JSONObject paramJSONObject2)
  {
    if (paramJSONObject1 == null)
      return paramJSONObject2;
    if (paramJSONObject2 == null)
      return paramJSONObject1;
    JSONObject localJSONObject = new JSONObject();
    Object localObject1 = paramJSONObject2.keys();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (String)((Iterator)localObject1).next();
      localJSONObject.put((String)localObject2, paramJSONObject2.get((String)localObject2));
    }
    Object localObject2 = paramJSONObject1.keys();
    while (((Iterator)localObject2).hasNext())
    {
      localObject1 = (String)((Iterator)localObject2).next();
      if ((localJSONObject.has((String)localObject1)) && ((localJSONObject.get((String)localObject1) instanceof JSONObject)) && ((paramJSONObject1.get((String)localObject1) instanceof JSONObject)))
        paramJSONObject2 = mergeJsonObjects(paramJSONObject1.getJSONObject((String)localObject1), localJSONObject.getJSONObject((String)localObject1));
      else
        paramJSONObject2 = paramJSONObject1.get((String)localObject1);
      localJSONObject.put((String)localObject1, paramJSONObject2);
    }
    return (JSONObject)(JSONObject)localJSONObject;
  }

  public static byte[] readFileBytes(File paramFile)
  {
    if (paramFile == null)
      return null;
    FileInputStream localFileInputStream = new FileInputStream(paramFile);
    byte[] arrayOfByte = new byte[(int)paramFile.length()];
    int i = 0;
    int j;
    if (paramFile.length() < 4096L)
      j = (int)paramFile.length();
    else
      j = 4096;
    while (true)
    {
      int k = localFileInputStream.read(arrayOfByte, i, j);
      if (k <= 0)
        break;
      k = i + k;
      i = k;
      if (paramFile.length() - k >= 4096L)
        continue;
      j = (int)paramFile.length() - k;
      i = k;
    }
    localFileInputStream.close();
    return arrayOfByte;
  }

  public static void runOnUiThread(Runnable paramRunnable)
  {
    runOnUiThread(paramRunnable, 0L);
  }

  public static void runOnUiThread(Runnable paramRunnable, long paramLong)
  {
    Handler localHandler = new Handler(Looper.getMainLooper());
    if (paramLong > 0L)
    {
      localHandler.postDelayed(paramRunnable, paramLong);
      return;
    }
    localHandler.post(paramRunnable);
  }

  public static String toHexString(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length;
    Object localObject1 = "";
    for (int j = 0; j < i; j++)
    {
      int k = paramArrayOfByte[j] & 0xFF;
      Object localObject2 = localObject1;
      if (k <= 15)
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append("0");
        localObject2 = ((StringBuilder)localObject2).toString();
      }
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append(Integer.toHexString(k));
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    return (String)(String)localObject1;
  }

  // ERROR //
  public static boolean writeFile(File paramFile, String paramString)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_2
    //   2: aload_0
    //   3: ifnonnull +5 -> 8
    //   6: iconst_0
    //   7: ireturn
    //   8: aconst_null
    //   9: astore_3
    //   10: aconst_null
    //   11: astore 4
    //   13: aload 4
    //   15: astore 5
    //   17: new 163	java/io/FileOutputStream
    //   20: astore 6
    //   22: aload 4
    //   24: astore 5
    //   26: aload 6
    //   28: aload_0
    //   29: invokespecial 164	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   32: aload 6
    //   34: aload_1
    //   35: invokevirtual 53	java/lang/String:getBytes	()[B
    //   38: invokevirtual 168	java/io/FileOutputStream:write	([B)V
    //   41: aload 6
    //   43: invokevirtual 171	java/io/FileOutputStream:flush	()V
    //   46: aload 6
    //   48: invokevirtual 172	java/io/FileOutputStream:close	()V
    //   51: goto +10 -> 61
    //   54: astore_1
    //   55: ldc 174
    //   57: aload_1
    //   58: invokestatic 47	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   61: iconst_1
    //   62: istore 7
    //   64: goto +68 -> 132
    //   67: astore_0
    //   68: aload 6
    //   70: astore 5
    //   72: goto +94 -> 166
    //   75: astore 5
    //   77: aload 6
    //   79: astore_1
    //   80: aload 5
    //   82: astore 6
    //   84: goto +11 -> 95
    //   87: astore_0
    //   88: goto +78 -> 166
    //   91: astore 6
    //   93: aload_3
    //   94: astore_1
    //   95: aload_1
    //   96: astore 5
    //   98: ldc 176
    //   100: aload 6
    //   102: invokestatic 47	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   105: iload_2
    //   106: istore 7
    //   108: aload_1
    //   109: ifnull +23 -> 132
    //   112: aload_1
    //   113: invokevirtual 172	java/io/FileOutputStream:close	()V
    //   116: iload_2
    //   117: istore 7
    //   119: goto +13 -> 132
    //   122: astore_1
    //   123: ldc 174
    //   125: aload_1
    //   126: invokestatic 47	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   129: iload_2
    //   130: istore 7
    //   132: iload 7
    //   134: ifeq +29 -> 163
    //   137: new 141	java/lang/StringBuilder
    //   140: dup
    //   141: ldc 178
    //   143: invokespecial 181	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   146: astore_1
    //   147: aload_1
    //   148: aload_0
    //   149: invokevirtual 184	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   152: invokevirtual 146	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   155: pop
    //   156: aload_1
    //   157: invokevirtual 152	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   160: invokestatic 187	com/unity3d/splash/services/core/log/DeviceLog:debug	(Ljava/lang/String;)V
    //   163: iload 7
    //   165: ireturn
    //   166: aload 5
    //   168: ifnull +18 -> 186
    //   171: aload 5
    //   173: invokevirtual 172	java/io/FileOutputStream:close	()V
    //   176: goto +10 -> 186
    //   179: astore_1
    //   180: ldc 174
    //   182: aload_1
    //   183: invokestatic 47	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   186: aload_0
    //   187: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   46	51	54	java/lang/Exception
    //   32	46	67	finally
    //   32	46	75	java/lang/Exception
    //   17	22	87	finally
    //   26	32	87	finally
    //   98	105	87	finally
    //   17	22	91	java/lang/Exception
    //   26	32	91	java/lang/Exception
    //   112	116	122	java/lang/Exception
    //   171	176	179	java/lang/Exception
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.misc.Utilities
 * JD-Core Version:    0.6.0
 */