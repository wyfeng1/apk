package com.unity3d.splash.services.core.request;

import com.unity3d.splash.services.core.log.DeviceLog;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.HttpsURLConnection;

public class WebRequest
{
  private String _body;
  private boolean _canceled = false;
  private int _connectTimeout;
  private long _contentLength = -1L;
  private Map _headers;
  private IWebRequestProgressListener _progressListener;
  private int _readTimeout;
  private String _requestType = RequestType.GET.name();
  private int _responseCode = -1;
  private Map _responseHeaders;
  private URL _url;

  public WebRequest(String paramString1, String paramString2, Map paramMap)
  {
    this(paramString1, paramString2, paramMap, 30000, 30000);
  }

  public WebRequest(String paramString1, String paramString2, Map paramMap, int paramInt1, int paramInt2)
  {
    this._url = new URL(paramString1);
    this._requestType = paramString2;
    this._headers = paramMap;
    this._connectTimeout = paramInt1;
    this._readTimeout = paramInt2;
  }

  private HttpURLConnection getHttpUrlConnectionWithHeaders()
  {
    StringBuilder localStringBuilder2;
    if (getUrl().toString().startsWith("https://"))
      try
      {
        HttpsURLConnection localHttpsURLConnection = (HttpsURLConnection)getUrl().openConnection();
      }
      catch (IOException localIOException1)
      {
        localStringBuilder2 = new StringBuilder("Open HTTPS connection: ");
        localStringBuilder2.append(localIOException1.getMessage());
        throw new NetworkIOException(localStringBuilder2.toString());
      }
    else
      if (!getUrl().toString().startsWith("http://"))
        break label344;
    try
    {
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)getUrl().openConnection();
      localHttpURLConnection.setInstanceFollowRedirects(false);
      localHttpURLConnection.setConnectTimeout(getConnectTimeout());
      localHttpURLConnection.setReadTimeout(getReadTimeout());
      try
      {
        localHttpURLConnection.setRequestMethod(getRequestType());
        if ((getHeaders() != null) && (getHeaders().size() > 0))
        {
          Iterator localIterator1 = getHeaders().keySet().iterator();
          while (localIterator1.hasNext())
          {
            String str1 = (String)localIterator1.next();
            Iterator localIterator2 = ((List)getHeaders().get(str1)).iterator();
            while (localIterator2.hasNext())
            {
              String str2 = (String)localIterator2.next();
              localStringBuilder2 = new StringBuilder("Setting header: ");
              localStringBuilder2.append(str1);
              localStringBuilder2.append("=");
              localStringBuilder2.append(str2);
              DeviceLog.debug(localStringBuilder2.toString());
              localHttpURLConnection.setRequestProperty(str1, str2);
            }
          }
        }
        return localHttpURLConnection;
      }
      catch (ProtocolException localProtocolException)
      {
        localStringBuilder2 = new StringBuilder("Set Request Method: ");
        localStringBuilder2.append(getRequestType());
        localStringBuilder2.append(", ");
        localStringBuilder2.append(localProtocolException.getMessage());
        throw new NetworkIOException(localStringBuilder2.toString());
      }
    }
    catch (IOException localIOException2)
    {
      localStringBuilder2 = new StringBuilder("Open HTTP connection: ");
      localStringBuilder2.append(localIOException2.getMessage());
      throw new NetworkIOException(localStringBuilder2.toString());
    }
    label344: StringBuilder localStringBuilder1 = new StringBuilder("Invalid url-protocol in url: ");
    localStringBuilder1.append(getUrl().toString());
    throw new IllegalArgumentException(localStringBuilder1.toString());
  }

  public void cancel()
  {
    this._canceled = true;
  }

  public String getBody()
  {
    return this._body;
  }

  public int getConnectTimeout()
  {
    return this._connectTimeout;
  }

  public long getContentLength()
  {
    return this._contentLength;
  }

  public Map getHeaders()
  {
    return this._headers;
  }

  public String getQuery()
  {
    URL localURL = this._url;
    if (localURL != null)
      return localURL.getQuery();
    return null;
  }

  public int getReadTimeout()
  {
    return this._readTimeout;
  }

  public String getRequestType()
  {
    return this._requestType;
  }

  public int getResponseCode()
  {
    return this._responseCode;
  }

  public Map getResponseHeaders()
  {
    return this._responseHeaders;
  }

  public URL getUrl()
  {
    return this._url;
  }

  public boolean isCanceled()
  {
    return this._canceled;
  }

  public String makeRequest()
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    makeStreamRequest(localByteArrayOutputStream);
    return localByteArrayOutputStream.toString("UTF-8");
  }

  // ERROR //
  public long makeStreamRequest(java.io.OutputStream paramOutputStream)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 230	com/unity3d/splash/services/core/request/WebRequest:getHttpUrlConnectionWithHeaders	()Ljava/net/HttpURLConnection;
    //   4: astore_2
    //   5: aload_2
    //   6: iconst_1
    //   7: invokevirtual 233	java/net/HttpURLConnection:setDoInput	(Z)V
    //   10: aload_0
    //   11: invokevirtual 133	com/unity3d/splash/services/core/request/WebRequest:getRequestType	()Ljava/lang/String;
    //   14: getstatic 236	com/unity3d/splash/services/core/request/WebRequest$RequestType:POST	Lcom/unity3d/splash/services/core/request/WebRequest$RequestType;
    //   17: invokevirtual 42	com/unity3d/splash/services/core/request/WebRequest$RequestType:name	()Ljava/lang/String;
    //   20: invokevirtual 240	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   23: ifeq +234 -> 257
    //   26: aload_2
    //   27: iconst_1
    //   28: invokevirtual 243	java/net/HttpURLConnection:setDoOutput	(Z)V
    //   31: aconst_null
    //   32: astore_3
    //   33: aconst_null
    //   34: astore 4
    //   36: aload 4
    //   38: astore 5
    //   40: new 245	java/io/PrintWriter
    //   43: astore 6
    //   45: aload 4
    //   47: astore 5
    //   49: new 247	java/io/OutputStreamWriter
    //   52: astore 7
    //   54: aload 4
    //   56: astore 5
    //   58: aload 7
    //   60: aload_2
    //   61: invokevirtual 251	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   64: ldc 221
    //   66: invokespecial 254	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   69: aload 4
    //   71: astore 5
    //   73: aload 6
    //   75: aload 7
    //   77: iconst_1
    //   78: invokespecial 257	java/io/PrintWriter:<init>	(Ljava/io/Writer;Z)V
    //   81: aload_0
    //   82: invokevirtual 259	com/unity3d/splash/services/core/request/WebRequest:getBody	()Ljava/lang/String;
    //   85: ifnonnull +19 -> 104
    //   88: aload_0
    //   89: invokevirtual 260	com/unity3d/splash/services/core/request/WebRequest:getQuery	()Ljava/lang/String;
    //   92: astore 5
    //   94: aload 6
    //   96: aload 5
    //   98: invokevirtual 263	java/io/PrintWriter:print	(Ljava/lang/String;)V
    //   101: goto +12 -> 113
    //   104: aload_0
    //   105: invokevirtual 259	com/unity3d/splash/services/core/request/WebRequest:getBody	()Ljava/lang/String;
    //   108: astore 5
    //   110: goto -16 -> 94
    //   113: aload 6
    //   115: invokevirtual 266	java/io/PrintWriter:flush	()V
    //   118: aload 6
    //   120: invokevirtual 269	java/io/PrintWriter:close	()V
    //   123: goto +134 -> 257
    //   126: astore_1
    //   127: ldc_w 271
    //   130: aload_1
    //   131: invokestatic 275	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   134: aload_1
    //   135: athrow
    //   136: astore_1
    //   137: aload 6
    //   139: astore 5
    //   141: goto +91 -> 232
    //   144: astore 5
    //   146: aload 6
    //   148: astore_1
    //   149: aload 5
    //   151: astore 6
    //   153: goto +11 -> 164
    //   156: astore_1
    //   157: goto +75 -> 232
    //   160: astore 6
    //   162: aload_3
    //   163: astore_1
    //   164: aload_1
    //   165: astore 5
    //   167: ldc_w 277
    //   170: aload 6
    //   172: invokestatic 275	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   175: aload_1
    //   176: astore 5
    //   178: new 106	com/unity3d/splash/services/core/request/NetworkIOException
    //   181: astore_3
    //   182: aload_1
    //   183: astore 5
    //   185: new 94	java/lang/StringBuilder
    //   188: astore 4
    //   190: aload_1
    //   191: astore 5
    //   193: aload 4
    //   195: ldc_w 279
    //   198: invokespecial 97	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   201: aload_1
    //   202: astore 5
    //   204: aload 4
    //   206: aload 6
    //   208: invokevirtual 100	java/io/IOException:getMessage	()Ljava/lang/String;
    //   211: invokevirtual 104	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   214: pop
    //   215: aload_1
    //   216: astore 5
    //   218: aload_3
    //   219: aload 4
    //   221: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   224: invokespecial 108	com/unity3d/splash/services/core/request/NetworkIOException:<init>	(Ljava/lang/String;)V
    //   227: aload_1
    //   228: astore 5
    //   230: aload_3
    //   231: athrow
    //   232: aload 5
    //   234: ifnull +21 -> 255
    //   237: aload 5
    //   239: invokevirtual 269	java/io/PrintWriter:close	()V
    //   242: goto +13 -> 255
    //   245: astore_1
    //   246: ldc_w 271
    //   249: aload_1
    //   250: invokestatic 275	com/unity3d/splash/services/core/log/DeviceLog:exception	(Ljava/lang/String;Ljava/lang/Exception;)V
    //   253: aload_1
    //   254: athrow
    //   255: aload_1
    //   256: athrow
    //   257: aload_0
    //   258: aload_2
    //   259: invokevirtual 281	java/net/HttpURLConnection:getResponseCode	()I
    //   262: putfield 46	com/unity3d/splash/services/core/request/WebRequest:_responseCode	I
    //   265: aload_0
    //   266: aload_2
    //   267: invokevirtual 283	java/net/HttpURLConnection:getContentLength	()I
    //   270: i2l
    //   271: putfield 50	com/unity3d/splash/services/core/request/WebRequest:_contentLength	J
    //   274: aload_2
    //   275: invokevirtual 286	java/net/HttpURLConnection:getHeaderFields	()Ljava/util/Map;
    //   278: ifnull +11 -> 289
    //   281: aload_0
    //   282: aload_2
    //   283: invokevirtual 286	java/net/HttpURLConnection:getHeaderFields	()Ljava/util/Map;
    //   286: putfield 210	com/unity3d/splash/services/core/request/WebRequest:_responseHeaders	Ljava/util/Map;
    //   289: aload_2
    //   290: invokevirtual 290	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   293: astore 5
    //   295: goto +16 -> 311
    //   298: astore 6
    //   300: aload_2
    //   301: invokevirtual 293	java/net/HttpURLConnection:getErrorStream	()Ljava/io/InputStream;
    //   304: astore 5
    //   306: aload 5
    //   308: ifnull +205 -> 513
    //   311: aload_0
    //   312: getfield 295	com/unity3d/splash/services/core/request/WebRequest:_progressListener	Lcom/unity3d/splash/services/core/request/IWebRequestProgressListener;
    //   315: astore 6
    //   317: aload 6
    //   319: ifnull +29 -> 348
    //   322: aload 6
    //   324: aload_0
    //   325: invokevirtual 75	com/unity3d/splash/services/core/request/WebRequest:getUrl	()Ljava/net/URL;
    //   328: invokevirtual 78	java/net/URL:toString	()Ljava/lang/String;
    //   331: aload_0
    //   332: getfield 50	com/unity3d/splash/services/core/request/WebRequest:_contentLength	J
    //   335: aload_0
    //   336: getfield 46	com/unity3d/splash/services/core/request/WebRequest:_responseCode	I
    //   339: aload_0
    //   340: getfield 210	com/unity3d/splash/services/core/request/WebRequest:_responseHeaders	Ljava/util/Map;
    //   343: invokeinterface 301 6 0
    //   348: new 303	java/io/BufferedInputStream
    //   351: dup
    //   352: aload 5
    //   354: invokespecial 306	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   357: astore 6
    //   359: lconst_0
    //   360: lstore 8
    //   362: sipush 4096
    //   365: newarray byte
    //   367: astore 5
    //   369: iconst_0
    //   370: istore 10
    //   372: aload_0
    //   373: invokevirtual 308	com/unity3d/splash/services/core/request/WebRequest:isCanceled	()Z
    //   376: ifne +126 -> 502
    //   379: iload 10
    //   381: iconst_m1
    //   382: if_icmpeq +120 -> 502
    //   385: aload 6
    //   387: aload 5
    //   389: invokevirtual 312	java/io/BufferedInputStream:read	([B)I
    //   392: istore 11
    //   394: iload 11
    //   396: istore 10
    //   398: iload 11
    //   400: ifle -28 -> 372
    //   403: aload_1
    //   404: aload 5
    //   406: iconst_0
    //   407: iload 11
    //   409: invokevirtual 318	java/io/OutputStream:write	([BII)V
    //   412: lload 8
    //   414: iload 11
    //   416: i2l
    //   417: ladd
    //   418: lstore 12
    //   420: aload_0
    //   421: getfield 295	com/unity3d/splash/services/core/request/WebRequest:_progressListener	Lcom/unity3d/splash/services/core/request/IWebRequestProgressListener;
    //   424: astore_3
    //   425: lload 12
    //   427: lstore 8
    //   429: iload 11
    //   431: istore 10
    //   433: aload_3
    //   434: ifnull -62 -> 372
    //   437: aload_3
    //   438: aload_0
    //   439: invokevirtual 75	com/unity3d/splash/services/core/request/WebRequest:getUrl	()Ljava/net/URL;
    //   442: invokevirtual 78	java/net/URL:toString	()Ljava/lang/String;
    //   445: lload 12
    //   447: aload_0
    //   448: getfield 50	com/unity3d/splash/services/core/request/WebRequest:_contentLength	J
    //   451: invokeinterface 322 6 0
    //   456: lload 12
    //   458: lstore 8
    //   460: iload 11
    //   462: istore 10
    //   464: goto -92 -> 372
    //   467: astore 5
    //   469: new 94	java/lang/StringBuilder
    //   472: dup
    //   473: ldc_w 324
    //   476: invokespecial 97	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   479: astore_1
    //   480: aload_1
    //   481: aload 5
    //   483: invokevirtual 100	java/io/IOException:getMessage	()Ljava/lang/String;
    //   486: invokevirtual 104	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   489: pop
    //   490: new 106	com/unity3d/splash/services/core/request/NetworkIOException
    //   493: dup
    //   494: aload_1
    //   495: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   498: invokespecial 108	com/unity3d/splash/services/core/request/NetworkIOException:<init>	(Ljava/lang/String;)V
    //   501: athrow
    //   502: aload_2
    //   503: invokevirtual 327	java/net/HttpURLConnection:disconnect	()V
    //   506: aload_1
    //   507: invokevirtual 328	java/io/OutputStream:flush	()V
    //   510: lload 8
    //   512: lreturn
    //   513: new 94	java/lang/StringBuilder
    //   516: dup
    //   517: ldc_w 330
    //   520: invokespecial 97	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   523: astore_1
    //   524: aload_1
    //   525: aload 6
    //   527: invokevirtual 100	java/io/IOException:getMessage	()Ljava/lang/String;
    //   530: invokevirtual 104	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   533: pop
    //   534: new 106	com/unity3d/splash/services/core/request/NetworkIOException
    //   537: dup
    //   538: aload_1
    //   539: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   542: invokespecial 108	com/unity3d/splash/services/core/request/NetworkIOException:<init>	(Ljava/lang/String;)V
    //   545: athrow
    //   546: astore_1
    //   547: goto +4 -> 551
    //   550: astore_1
    //   551: new 94	java/lang/StringBuilder
    //   554: dup
    //   555: ldc_w 332
    //   558: invokespecial 97	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   561: astore 5
    //   563: aload 5
    //   565: aload_1
    //   566: invokevirtual 333	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   569: invokevirtual 104	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   572: pop
    //   573: new 106	com/unity3d/splash/services/core/request/NetworkIOException
    //   576: dup
    //   577: aload 5
    //   579: invokevirtual 107	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   582: invokespecial 108	com/unity3d/splash/services/core/request/NetworkIOException:<init>	(Ljava/lang/String;)V
    //   585: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   118	123	126	java/lang/Exception
    //   81	94	136	finally
    //   94	101	136	finally
    //   104	110	136	finally
    //   113	118	136	finally
    //   81	94	144	java/io/IOException
    //   94	101	144	java/io/IOException
    //   104	110	144	java/io/IOException
    //   113	118	144	java/io/IOException
    //   40	45	156	finally
    //   49	54	156	finally
    //   58	69	156	finally
    //   73	81	156	finally
    //   167	175	156	finally
    //   178	182	156	finally
    //   185	190	156	finally
    //   193	201	156	finally
    //   204	215	156	finally
    //   218	227	156	finally
    //   230	232	156	finally
    //   40	45	160	java/io/IOException
    //   49	54	160	java/io/IOException
    //   58	69	160	java/io/IOException
    //   73	81	160	java/io/IOException
    //   237	242	245	java/lang/Exception
    //   289	295	298	java/io/IOException
    //   385	394	467	java/io/IOException
    //   257	265	546	java/lang/RuntimeException
    //   257	265	550	java/io/IOException
  }

  public void setBody(String paramString)
  {
    this._body = paramString;
  }

  public void setConnectTimeout(int paramInt)
  {
    this._connectTimeout = paramInt;
  }

  public void setProgressListener(IWebRequestProgressListener paramIWebRequestProgressListener)
  {
    this._progressListener = paramIWebRequestProgressListener;
  }

  public void setReadTimeout(int paramInt)
  {
    this._readTimeout = paramInt;
  }

  public static enum RequestType
  {
    static
    {
      GET = new RequestType("GET", 1);
      RequestType localRequestType = new RequestType("HEAD", 2);
      HEAD = localRequestType;
      $VALUES = new RequestType[] { POST, GET, localRequestType };
    }
  }
}

/* Location:           C:\Users\王银峰\Downloads\dex2jar-2.1\dex-tools-2.1\classes-dex2jar.jar
 * Qualified Name:     com.unity3d.splash.services.core.request.WebRequest
 * JD-Core Version:    0.6.0
 */